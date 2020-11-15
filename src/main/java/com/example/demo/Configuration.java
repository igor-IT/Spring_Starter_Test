package com.example.demo;

import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public InjectByTypeBeanPostProcessor injectByTypeBeanPostProcessor(){
        return new InjectByTypeBeanPostProcessor();
    }
}
