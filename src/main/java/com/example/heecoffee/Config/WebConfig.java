package com.example.heecoffee.Config;

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
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000",       // Khi test trên máy local
                                "https://sont.click",          // <--- PHẢI THÊM: Domain HTTPS chính thức
                                "http://150.95.109.44",        // Nếu FE chạy trên IP này
                                "http://150.95.109.44:3000"    // Nếu FE chạy trên IP này với port 3000
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*") // Thêm headers cho an toàn
                        .allowCredentials(true);
            }
        };
    }
}
