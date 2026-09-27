package com.ga.todo.service;

import com.ga.todo.exception.InformationExistException;
import com.ga.todo.model.Request.LoginRequest;
import com.ga.todo.model.Response.LoginResponse;
import com.ga.todo.model.User;
import com.ga.todo.repository.UserRepository;
import com.ga.todo.security.JWTUtils;
import com.ga.todo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    public final JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils,@Lazy AuthenticationManager authenticationManager,@Lazy MyUserDetails myUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;

    }

    public User createUser(User userObject){
        System.out.println("Service Calling createUser ==>");
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())){
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("User with email address " + userObject.getEmailAddress() + "already" + "exists.");

        }
    }

    public User findUserByEmailAddress(String email) {
        return (User) userRepository.findUserByEmailAddress(email);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest
                            .getEmail(),
                            loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            assert myUserDetails != null;
            final String jwt = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(jwt));
        }
        catch (Exception e){
            System.out.println(loginRequest.getEmail());
            System.out.println(loginRequest.getPassword());
            return ResponseEntity.ok(new LoginResponse("Error: email or password is incorrect." + e.getMessage()));

        }

    }
}


