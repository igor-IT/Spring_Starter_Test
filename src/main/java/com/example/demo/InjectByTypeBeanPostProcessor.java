package com.example.demo;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectByTypeBeanPostProcessor<T> implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {
    private Map<Class<?>,Object> beanHolder = new HashMap<>();
    private List<Object> annotaitedBeans = new ArrayList<>();

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Field[] declaredFields = bean.getClass().getFields();
        beanHolder.put(bean.getClass(),bean);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectByList.class)) {
                annotaitedBeans.add(bean);
            }
        }
        return bean;
    }
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
       annotaitedBeans.stream().forEach(bean->fieldInjection(bean));
    }
    @SneakyThrows
    private void fieldInjection(Object beanObj){
        Field[] declaredFields = beanObj.getClass().getDeclaredFields();
        //Проблема с этим листом, я не нашел как из поля получить дженерик тайп, я знаю что это ошибка но я старался как мог
        List<Object> list = new ArrayList<>();
        for (Field field : declaredFields){
            if (field.isAnnotationPresent(InjectByList.class)){
                InjectByList annotation = field.getAnnotation(InjectByList.class);
                Class<?>[] classes = annotation.classes();
                for (Class<?> aClass : classes) {
                    list.add(beanHolder.get(aClass));
                }
                field.setAccessible(true);
                field.set(beanObj,list);
            }
        }
    }
}
