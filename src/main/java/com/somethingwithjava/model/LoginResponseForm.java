package com.somethingwithjava.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class LoginResponseForm {
    private String status = "success";
    private String userName;
    private String token;
    private String responseTime;
}
