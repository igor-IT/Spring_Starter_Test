package com.example.demo.for_testing;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class TestRu implements Test {
    @Override
    public void hello() {
        System.out.println("RUSSIA");
    }
}
