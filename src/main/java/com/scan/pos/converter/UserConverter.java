package com.scan.pos.converter;

import com.scan.pos.pojo.dto.UserSaveDTO;
import com.scan.pos.pojo.entity.User;
import com.scan.pos.pojo.vo.LoginVO;
import com.scan.pos.pojo.vo.UserVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    /** DTO → Entity */
    public static User toEntity(UserSaveDTO dto) {
        if (dto == null) return null;
        User u = new User();
        u.setId(dto.getId());
        u.setMerchantId(dto.getMerchantId());
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setRealName(dto.getRealName());
        u.setPhone(dto.getPhone());
        u.setRole(dto.getRole());
        if (dto.getStatus() != null) {
            u.setStatus(dto.getStatus());
        }
        return u;
    }

    /** Entity → VO（过滤密码） */
    public static UserVO toVO(User entity) {
        if (entity == null) return null;
        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setMerchantId(entity.getMerchantId());
        vo.setUsername(entity.getUsername());
        vo.setRealName(entity.getRealName());
        vo.setPhone(entity.getPhone());
        vo.setRole(entity.getRole());
        vo.setRoleName(roleName(entity.getRole()));
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static List<UserVO> toVOList(List<User> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(UserConverter::toVO).collect(Collectors.toList());
    }

    /** Entity → LoginVO */
    public static LoginVO toLoginVO(User entity) {
        if (entity == null) return null;
        LoginVO vo = new LoginVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        vo.setRealName(entity.getRealName());
        vo.setRole(entity.getRole());
        vo.setRoleName(roleName(entity.getRole()));
        vo.setMerchantId(entity.getMerchantId());
        return vo;
    }

    private static String roleName(Integer role) {
        if (role == null) return null;
        switch (role) {
            case 0: return "超级管理员";
            case 1: return "商户管理员";
            case 2: return "普通销售";
            default: return "未知";
        }
    }
}
