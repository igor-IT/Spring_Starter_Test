package com.example.demo;

import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class InjectByTypeBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private  BeanFactory context;
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        Collection<Object> list ;
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectByList.class)) {
                InjectByList annotation = field.getAnnotation(InjectByList.class);
                Class<?>[] classes = annotation.classes();
                list = getCollection(field.getType().getSimpleName());
                for (Class<?> aClass : classes) {
                    list.add(context.getBean(aClass));
                }
                field.setAccessible(true);
                field.set(bean,list);
            }
        }
        return bean;
    }
    private Collection<Object> getCollection(String name){
        if (name.equalsIgnoreCase("List"))
            return new ArrayList<>();
        if (name.equalsIgnoreCase("Set"))
        return new HashSet<>();
        return new LinkedList<>();
    }

}
