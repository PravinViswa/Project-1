package com.pravinviswa.agecalculator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // or "/**" if you want everything
                .allowedOrigins("https://pravinviswa.github.io")
                .allowedMethods("GET", "POST")
                .allowedHeaders("*");
    }
}
