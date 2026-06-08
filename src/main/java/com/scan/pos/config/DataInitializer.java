package com.scan.pos.config;
import com.scan.pos.common.utils.PasswordUtil;
import com.scan.pos.pojo.entity.Merchant;
import com.scan.pos.pojo.entity.Product;
import com.scan.pos.pojo.entity.User;
import com.scan.pos.mapper.MerchantMapper;
import com.scan.pos.mapper.ProductMapper;
import com.scan.pos.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public void run(String... args) throws Exception {
        // 1. 创建默认商户（如果没有任何商户）
        if (merchantMapper.findAll().isEmpty()) {
            Merchant defaultMerchant = new Merchant();
            defaultMerchant.setName("默认商户");
            defaultMerchant.setContactName("管理员");
            defaultMerchant.setContactPhone("13800000000");
            defaultMerchant.setAddress("默认地址");
            defaultMerchant.setRemark("系统初始化默认商户");
            defaultMerchant.setStatus(1);
            merchantMapper.insert(defaultMerchant);
        }

        Long defaultMerchantId = merchantMapper.findAll().get(0).getId();

        // 2. 初始化超级管理员（如果没有任何用户）
        if (userMapper.findAllUsers().isEmpty()) {
            User superAdmin = new User();
            superAdmin.setId(1L);
            superAdmin.setMerchantId(0L); // 0 = 超级管理员
            superAdmin.setUsername("superadmin");
            superAdmin.setPassword(PasswordUtil.encode("123456"));
            superAdmin.setRealName("超级管理员");
            superAdmin.setPhone("13900000000");
            superAdmin.setRole(0); // 0 = 超级管理员
            superAdmin.setStatus(1);
            userMapper.insert(superAdmin);

            // 默认商户的管理员
            User merchantAdmin = new User();
            merchantAdmin.setMerchantId(defaultMerchantId);
            merchantAdmin.setUsername("admin");
            merchantAdmin.setPassword(PasswordUtil.encode("123456"));
            merchantAdmin.setRealName("商户管理员");
            merchantAdmin.setPhone("13800000000");
            merchantAdmin.setRole(1); // 1 = 商户管理员
            merchantAdmin.setStatus(1);
            userMapper.insert(merchantAdmin);

            // 默认商户的普通销售
            User cashier = new User();
            cashier.setMerchantId(defaultMerchantId);
            cashier.setUsername("cashier");
            cashier.setPassword(PasswordUtil.encode("123456"));
            cashier.setRealName("普通销售");
            cashier.setPhone("13800000001");
            cashier.setRole(2); // 2 = 普通销售
            cashier.setStatus(1);
            userMapper.insert(cashier);
        }

        // 3. 初始化默认商户的示例商品（如果该商户没有任何商品）
        List<Product> existingProducts = productMapper.findProductsByMerchantId(defaultMerchantId);
        if (existingProducts.isEmpty()) {
            String[] barcodes = {"6901028012345","6901028012352","6901028012369","6901028012376","6901028012383","6901028012390","6901028012406","6901028012413"};
            String[] names = {"可口可乐330ml","雪碧330ml","康师傅方便面","农夫山泉550ml","奥利奥饼干","伊利纯牛奶250ml","卫龙辣条","乐事薯片"};
            String[] categories = {"饮料","饮料","食品","饮料","零食","乳品","零食","零食"};
            BigDecimal[] prices = {new BigDecimal("3.00"), new BigDecimal("3.00"), new BigDecimal("4.50"), new BigDecimal("2.00"), new BigDecimal("8.50"), new BigDecimal("5.00"), new BigDecimal("1.50"), new BigDecimal("7.00")};
            for (int i = 0; i < barcodes.length; i++) {
                Product p = new Product();
                p.setMerchantId(defaultMerchantId);
                p.setBarcode(barcodes[i]);
                p.setName(names[i]);
                p.setCategory(categories[i]);
                p.setPrice(prices[i]);
                p.setCost(prices[i].multiply(new BigDecimal("0.6")));
                p.setStock(100);
                p.setUnit("件");
                p.setStatus(1);
                productMapper.insert(p);
            }
        }
    }
}
