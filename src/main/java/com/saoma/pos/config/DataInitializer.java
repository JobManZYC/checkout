package com.saoma.pos.config;
import com.saoma.pos.entity.Product;
import com.saoma.pos.entity.User;
import com.saoma.pos.mapper.ProductMapper;
import com.saoma.pos.mapper.UserMapper;
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

    @Override
    public void run(String... args) throws Exception {
        if (productMapper.findAll().isEmpty()) {
            String[] barcodes = {"6901028012345","6901028012352","6901028012369","6901028012376","6901028012383","6901028012390","6901028012406","6901028012413"};
            String[] names = {"可口可乐330ml","雪碧330ml","康师傅方便面","农夫山泉550ml","奥利奥饼干","伊利纯牛奶250ml","卫龙辣条","乐事薯片"};
            String[] categories = {"饮料","饮料","食品","饮料","零食","乳品","零食","零食"};
            BigDecimal[] prices = {new BigDecimal("3.00"), new BigDecimal("3.00"), new BigDecimal("4.50"), new BigDecimal("2.00"), new BigDecimal("8.50"), new BigDecimal("5.00"), new BigDecimal("1.50"), new BigDecimal("7.00")};
            for (int i = 0; i < barcodes.length; i++) {
                Product p = new Product();
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
        if (userMapper.findAll().isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setRealName("管理员");
            admin.setPhone("13800000000");
            admin.setRole(1);
            admin.setStatus(1);
            userMapper.insert(admin);

            User cashier = new User();
            cashier.setUsername("cashier");
            cashier.setPassword("123456");
            cashier.setRealName("收银员");
            cashier.setPhone("13800000001");
            cashier.setRole(2);
            cashier.setStatus(1);
            userMapper.insert(cashier);
        }
    }
}
