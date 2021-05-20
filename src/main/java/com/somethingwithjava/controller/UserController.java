package com.somethingwithjava.controller;


import com.somethingwithjava.common.JwtProvider;
import com.somethingwithjava.model.User;
import com.somethingwithjava.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private JwtProvider jwtProvider;


    @GetMapping("")
    public ResponseEntity<?> getAllUser() {
        List<User> users = userService.getAllUser();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
}
