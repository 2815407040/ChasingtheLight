package com.ChasingtheLight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许前端域名访问
        corsConfiguration.addAllowedOrigin("http://localhost:5173");
        // 允许携带凭证
        corsConfiguration.setAllowCredentials(true);
        // 允许所有请求头
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        // 允许所有HTTP方法（包括预检请求的OPTIONS）
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        // 设置预检请求缓存时间（1小时）
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有接口生效
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}