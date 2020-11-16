package com.example.demo;

import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InjectByTypeBeanPostProcessor<T> implements BeanPostProcessor {
    @Autowired
    private  BeanFactory context;


    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Field[] declaredFields = bean.getClass().getFields();
        List<Object> list = new ArrayList<>();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectByList.class)) {
                InjectByList annotation = field.getAnnotation(InjectByList.class);
                Class<?>[] classes = annotation.classes();
                for (Class<?> aClass : classes) {
                    list.add(context.getBean(aClass));
                }
                field.setAccessible(true);
                field.set(bean,list);
            }
        }
        return bean;
    }
}
