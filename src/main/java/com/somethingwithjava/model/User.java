package com.somethingwithjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Entity
@Getter @Setter
public class User {
    @Id
    @Size(min = 6, max = 18, message = "About Me must be between 6 and 200 characters")
    @Column(length = 18)
    private String userName;

    @Size(min = 6, max = 225)
    @Column(length = 225)
    private String userPassword;

    @Size(min = 10, max = 10, message = "Phone number invalid")
    @Column(length = 10)
    private String phoneNumber;

    @Size(max = 100, message = "User Name over length")
    @Column(length = 100)
    private String Name;

    @Size(min = 8, max = 8, message = "Birthday invalid")
    @Column(length = 8)
    private String birthday;

    @Size(min = 6, max = 255, message = "Mail invalid")
    @Email(message = "email invalid")
    @Column(length = 225)
    private String email;
}
