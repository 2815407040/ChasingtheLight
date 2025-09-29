package com.ChasingtheLight.config;

import com.ChasingtheLight.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**") // 拦截所有接口
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/user/activate",
                        "/camera/*",
                        "/home/**"

                );
        }
}