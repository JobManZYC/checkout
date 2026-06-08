package com.scan.pos.config;

import com.scan.pos.common.utils.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 * <p>
 * 注册登录拦截器，对 /api/** 路径进行 JWT Token 校验，
 * 放行登录接口和 Swagger 文档接口。
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user/login",           // 登录接口
                        "/swagger-ui/**",            // Swagger UI
                        "/swagger-resources/**",     // Swagger 资源
                        "/v2/api-docs/**",           // Swagger API文档
                        "/v3/api-docs/**",           // Swagger API文档 v3
                        "/webjars/**"                // Swagger webjars
                );
    }
}
