package com.example.demo;


import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(InjectByTypeBeanPostProcessor.class)
public  @interface  InjectByList {
    Class<?>[] classes();
}
