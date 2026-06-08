package com.scan.pos.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scan.pos.common.exception.BusinessException;
import com.scan.pos.common.utils.JwtUtil;
import com.scan.pos.common.utils.PasswordUtil;
import com.scan.pos.converter.UserConverter;
import com.scan.pos.mapper.MerchantMapper;
import com.scan.pos.mapper.UserMapper;
import com.scan.pos.pojo.dto.UserLoginDTO;
import com.scan.pos.pojo.dto.UserSaveDTO;
import com.scan.pos.pojo.entity.Merchant;
import com.scan.pos.pojo.entity.User;
import com.scan.pos.pojo.vo.LoginVO;
import com.scan.pos.pojo.vo.UserVO;
import com.scan.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /** 登录失败最大次数 */
    private static final int MAX_FAIL_COUNT = 5;
    /** 锁定时间（分钟） */
    private static final int LOCK_MINUTES = 15;
    /** 密码强度正则：至少6位，包含字母和数字 */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$");
    /** Redis Key 前缀：登录失败计数 */
    private static final String LOGIN_FAIL_KEY = "login:fail:";

    @Override
    public List<UserVO> findAll() {
        List<User> list = baseMapper.findAllUsers();
        return UserConverter.toVOList(list);
    }

    @Override
    public List<UserVO> findByMerchantId(Long merchantId) {
        List<User> list = baseMapper.findUsersByMerchantId(merchantId);
        return UserConverter.toVOList(list);
    }

    @Override
    public Page<UserVO> page(Long merchantId, int page, int pageSize, String keyword) {
        Page<User> pageParam = new Page<>(page, pageSize);
        Page<User> result = baseMapper.selectUserPage(pageParam, merchantId, keyword);
        Page<UserVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(UserConverter.toVOList(result.getRecords()));
        return voPage;
    }

    @Override
    public UserVO findById(Long id) {
        return UserConverter.toVO(baseMapper.selectById(id));
    }

    @Override
    public LoginVO login(UserLoginDTO dto) {
        String username = dto.getUsername();
        String failKey = LOGIN_FAIL_KEY + username;

        // 1. 检查是否已被锁定
        String failCountStr = stringRedisTemplate.opsForValue().get(failKey);
        int failCount = failCountStr != null ? Integer.parseInt(failCountStr) : 0;
        if (failCount >= MAX_FAIL_COUNT) {
            Long ttl = stringRedisTemplate.getExpire(failKey);
            long remainingMinutes = (ttl != null && ttl > 0) ? ttl / 60 + 1 : LOCK_MINUTES;
            throw new BusinessException("账号已被锁定，请" + remainingMinutes + "分钟后重试");
        }

        // 2. 根据用户名查询用户（SQL 层已过滤 status=1 和 deleted=0）
        User user = baseMapper.login(username);

        // 3. 密码校验（兼容 BCrypt 和旧版 SHA-256）
        if (user == null || !PasswordUtil.matches(dto.getPassword(), user.getPassword())) {
            // 登录失败：计数+1，首次设置过期时间
            failCount = failCount + 1;
            if (failCount == 1) {
                stringRedisTemplate.opsForValue().set(failKey, String.valueOf(failCount), LOCK_MINUTES, TimeUnit.MINUTES);
            } else {
                stringRedisTemplate.opsForValue().increment(failKey);
                // 续期锁定窗口
                stringRedisTemplate.expire(failKey, LOCK_MINUTES, TimeUnit.MINUTES);
            }
            int remaining = MAX_FAIL_COUNT - failCount;
            if (remaining > 0) {
                throw new BusinessException("用户名或密码错误，还剩" + remaining + "次尝试机会");
            } else {
                throw new BusinessException("账号已被锁定，请" + LOCK_MINUTES + "分钟后重试");
            }
        }

        // 4. 登录成功：清除失败计数
        stringRedisTemplate.delete(failKey);

        // 5. 非超级管理员（merchantId != 0），校验商户是否被禁用
        if (user.getMerchantId() != null && user.getMerchantId() != 0) {
            Merchant merchant = merchantMapper.selectById(user.getMerchantId());
            if (merchant == null) {
                throw new BusinessException("所属商户不存在");
            }
            if (merchant.getStatus() != null && merchant.getStatus() == 0) {
                throw new BusinessException("该商户已被禁用，请联系管理员");
            }
        }

        // 6. 生成 JWT Token
        String token = JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole(), user.getMerchantId());

        // 7. 组装返回
        LoginVO vo = UserConverter.toLoginVO(user);
        vo.setToken(token);
        return vo;
    }

    @Override
    public int save(UserSaveDTO dto) {
        User entity = UserConverter.toEntity(dto);
        if (entity.getId() == null) {
            // ========== 新增 ==========
            // 密码强度校验
            validatePasswordStrength(entity.getPassword());

            // 同一商户下不允许有用户名相同的用户
            User existByName = baseMapper.selectOne(
                    new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                            .eq(User::getUsername, entity.getUsername())
                            .eq(User::getMerchantId, entity.getMerchantId()));
            if (existByName != null) {
                throw new BusinessException("同一商户下已存在相同用户名的用户");
            }
            entity.setStatus(1);
            entity.setPassword(PasswordUtil.encode(entity.getPassword()));
            return baseMapper.insert(entity);
        }
        // ========== 修改 ==========
        User existById = baseMapper.selectById(entity.getId());
        if (existById == null) {
            throw new BusinessException("用户不存在");
        }
        // 用户名不允许修改
        entity.setUsername(existById.getUsername());
        // 如果传了新密码，校验强度并加密
        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            validatePasswordStrength(entity.getPassword());
            entity.setPassword(PasswordUtil.encode(entity.getPassword()));
        } else {
            entity.setPassword(existById.getPassword());
        }
        // 如果未传 status，保持原有状态
        if (entity.getStatus() == null) {
            entity.setStatus(existById.getStatus());
        }
        return baseMapper.updateById(entity);
    }

    /**
     * 密码强度校验：至少6位，包含字母和数字
     */
    private void validatePasswordStrength(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new BusinessException("密码不能为空");
        }
        if (!PASSWORD_PATTERN.matcher(rawPassword).matches()) {
            throw new BusinessException("密码至少6位，且必须包含字母和数字");
        }
    }

    @Override
    public int deleteById(Long id) {
        User user = baseMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setDeleted(true);
        return baseMapper.updateById(user);
    }
}
