package com.lhy.blog.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public TokenInterceptor authInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login/")
                .excludePathPatterns("/admin/login/check")
                .excludePathPatterns("/admin/oss/uploadByBatch");
        super.addInterceptors(registry);
    }
}
