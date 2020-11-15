package com.example.demo.for_testing;

import com.example.demo.InjectByList;
import org.springframework.stereotype.Component;

import java.util.List;
public class SeaHello {
    @InjectByList(classes = {TestRu.class,TestUa.class})
       public List<Test> tests;
       public void hello(){
       tests.stream().forEach(f->f.hello());
    }
}

