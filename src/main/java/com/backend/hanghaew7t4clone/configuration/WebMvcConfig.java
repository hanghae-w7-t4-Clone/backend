package com.backend.hanghaew7t4clone.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://13.124.123.173")
                .allowedMethods("*")
                .exposedHeaders("Authorization","Refresh-Token")
                .allowCredentials(true)//make client read header("jwt-token")
        ;
    }
}
