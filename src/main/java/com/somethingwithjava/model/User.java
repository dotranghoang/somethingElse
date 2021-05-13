package com.somethingwithjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Entity
@Getter @Setter
public class User {
    private static final String PHONE_NUMBER_VALIDATION = "/(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b/";
    @Id
    @Size(min = 6, max = 18, message = "About Me must be between 6 and 200 characters")
    @Column(length = 18)
    private String userId;

    @Size(min = 6, max = 225)
    @Column(length = 225)
    private String userPassword;

    @Size(min = 10, max = 10, message = "Phone number invalid")
    @Pattern(regexp = PHONE_NUMBER_VALIDATION)
    @Column(length = 10)
    private String phoneNumber;

    @Size(max = 100, message = "User Name over length")
    @Column(length = 100)
    private String userName;

    @Size(min = 8, max = 8, message = "Birthday invalid")
    @Column(length = 8)
    private String birthday;

}
