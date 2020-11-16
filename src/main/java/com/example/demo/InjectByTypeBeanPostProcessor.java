package com.example.demo;

import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class InjectByTypeBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private  BeanFactory context;
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        Collection<Object> list ;
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(InjectByList.class)) {
                if (method.getName().startsWith("set")) {
                    InjectByList annotation = method.getAnnotation(InjectByList.class);
                    Class<?>[] classes = annotation.classes();
                    Class<?> parameterType = method.getParameterTypes()[0];
                    Collection<Object> beans = getCollection(parameterType.getSimpleName());
                    for (Class<?> aClass : classes) {
                        beans.add(context.getBean(aClass));
                    }
                    method.invoke(bean, beans);
                }else {
                    throw new IllegalArgumentException("Аннотация работает только для сеттеров и филдов");
                }
            }
        }
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
