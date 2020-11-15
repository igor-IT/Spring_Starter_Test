package com.example.demo.for_testing;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class TestUa implements Test {
    @Override
    public void hello() {
        System.out.println("UKRAINA");
    }
}
