package com.somethingwithjava.controller;


import com.somethingwithjava.common.JwtProvider;
import com.somethingwithjava.model.User;
import com.somethingwithjava.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private JwtProvider jwtProvider;


    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String jwt = jwtProvider.generateToken(user);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUser() {
        List<User> users = (List<User>) userService.getAllUser();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
}
