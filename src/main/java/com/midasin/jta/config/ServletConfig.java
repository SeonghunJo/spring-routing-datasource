package com.midasin.jta.config;

import com.midasin.jta.Interceptor.DomainInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServletConfig implements WebMvcConfigurer {

    @Bean
    DomainInterceptor domainInterceptor() {
        return new DomainInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(domainInterceptor());
    }
}
