package com.somethingwithjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Getter @Setter
public class User {
    @Id
    @Size(min = 6, max = 18, message = "About Me must be between 6 and 200 characters")
    @Column(length = 18)
    private String userName;

    @Size(min = 6, max = 225)
    @Column(length = 225)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;

    @Size(min = 10, max = 10, message = "Phone number invalid")
    @Column(length = 10)
    private String phoneNumber;

    @Size(max = 100, message = "User Name over length")
    @Column(length = 100)
    private String Name;

    private Date birthday;

    @Size(min = 6, max = 255, message = "Mail invalid")
    @Email(message = "email invalid")
    @Column(length = 225)
    private String email;
}
