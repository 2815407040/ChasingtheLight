package com.ChasingtheLight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1. 允许具体的前端域名（替代 *，避免与 credentials 冲突）
        corsConfiguration.addAllowedOrigin("http://localhost:5173");
        // 2. 允许携带凭证（如 Cookie、Token）
        corsConfiguration.setAllowCredentials(true);
        // 3. 允许所有请求头
        corsConfiguration.addAllowedHeader("*");
        // 4. 允许所有请求方法（包括 OPTIONS）
        corsConfiguration.addAllowedMethod("*");
        // 5. 设置预检请求缓存时间（1小时）
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}