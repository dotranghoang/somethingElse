package com.somethingwithjava.controller;

import com.somethingwithjava.common.DateUtil;
import com.somethingwithjava.common.EnDecoder;
import com.somethingwithjava.common.JwtProvider;
import com.somethingwithjava.common.RedisUtil;
import com.somethingwithjava.model.LoginRequestForm;
import com.somethingwithjava.model.LoginResponseForm;
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

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private DateUtil dateUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody User user) throws Exception {
        User userExist = userService.getUserByUserName(user.getUserName());
        if (userExist != null) {
            ResponseWithoutResult response =
                    new ResponseWithoutResult(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), "User name is exist");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        String encryptPassword = EnDecoder.encrypt(user.getUserPassword());
        if (!StringUtils.hasText(encryptPassword)) {
            ResponseWithoutResult response =
                    new ResponseWithoutResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Error when encrypt password!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setUserPassword(encryptPassword);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestForm loginRequestForm) throws Exception {
        User user = userService.getUserByUserName(loginRequestForm.getUserName());
        if (user == null) {
            ResponseWithoutResult response =
                    new ResponseWithoutResult(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), "User name or password incorrect");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        boolean isCorrectPassword = userService.comparePassword(loginRequestForm.getUserPassword(), EnDecoder.decrypt(user.getUserPassword()));
        if (isCorrectPassword) {
            String token = jwtProvider.generateToken(user);
            LoginResponseForm responseForm = new LoginResponseForm();
            responseForm.setResponseTime(dateUtil.getCurrentDateTime());
            responseForm.setToken(token);
            responseForm.setUserName(user.getUserName());
            String statusRedisSet = RedisUtil.saveString(responseForm.getUserName(), responseForm.getUserName());
            if (!statusRedisSet.equals("OK")){
                ResponseWithoutResult responseWithoutResult = new ResponseWithoutResult(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        "Error at Redis");
                return new ResponseEntity<>(responseWithoutResult, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(responseForm, HttpStatus.OK);
        }
        ResponseWithoutResult response =
                new ResponseWithoutResult(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), "User name or password incorrect");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
