package com.somethingwithjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
public class LoginRequestForm {
    @Size(min = 6, max = 18, message = "User name must be between 6 and 18 characters")
    private String userName;

    @Size(min = 6, max = 18, message = "Password must be between 6 and 18 characters")
    private String userPassword;
}
