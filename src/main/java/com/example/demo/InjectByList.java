package com.example.demo;


import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
@Import(InjectByTypeBeanPostProcessor.class)
public  @interface  InjectByList {
    Class<?>[] classes();
}
