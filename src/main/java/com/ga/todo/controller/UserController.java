package com.ga.todo.controller;

import com.ga.todo.model.Request.LoginRequest;
import com.ga.todo.model.User;
import com.ga.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping(path = "/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostConstruct
    public void init() {
        System.out.println(">>> UserController LOADED <<<");
    }


    @PostMapping("/register")
    public User createUser(@RequestBody User userObject) {
        System.out.println("Calling createUser ==>");
        return userService.createUser(userObject);


    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Calling loginUser ==>");
        return userService.loginUser(loginRequest);
    }

}
