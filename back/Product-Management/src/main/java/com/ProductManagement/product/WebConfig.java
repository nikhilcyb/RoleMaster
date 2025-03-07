package com.ProductManagement.product;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow CORS for all endpoints
                .allowedOrigins("http://localhost:3000")  // Allow React frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow relevant HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true); // Allow credentials if required (cookies, authorization headers)
    }
}
