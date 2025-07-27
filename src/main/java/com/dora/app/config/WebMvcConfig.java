package com.dora.app.config;

import com.dora.app.interceptor.HttpRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final HttpRequestInterceptor httpHeaderInterceptor;

    public WebMvcConfig(HttpRequestInterceptor httpHeaderInterceptor) {
        this.httpHeaderInterceptor = httpHeaderInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpHeaderInterceptor)
                .addPathPatterns("/**"); // all paths
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")        // or specify allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")        // allow all headers, including userId
                .allowCredentials(false)    // set true if cookies or auth headers needed
                .maxAge(3600);              // cache preflight for 1 hour
    }
}
