package com.somethingwithjava.controller;

import com.somethingwithjava.common.EnDecoder;
import com.somethingwithjava.model.ResponseWithoutResult;
import com.somethingwithjava.model.User;
import com.somethingwithjava.service.IMPL.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody User user) {
        String encryptPassword = EnDecoder.encrypt(user.getUserPassword());
        if (StringUtils.hasText(encryptPassword)) {
            user.setUserPassword(encryptPassword);
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        ResponseWithoutResult response =
                new ResponseWithoutResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
