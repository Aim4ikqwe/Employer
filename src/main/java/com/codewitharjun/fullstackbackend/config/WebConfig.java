package com.codewitharjun.fullstackbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // для всех путей
                        .allowedOrigins("http://localhost:3000") // разрешаем фронт с этого адреса
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // методы
                        .allowCredentials(true); // если нужны куки или авторизация
            }
        };
    }
}
