package com.ga.todo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCodeSnippets {
    @GetMapping("hello")
    public String hello() {
        System.out.println("hello");
        return "Whatever";
    }
}