package com.somethingwithjava.service;

import com.somethingwithjava.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

    void save(User user);

    List<User> getAllUser();

    void delete(String userName);

    User getUserByUserName(String userId);

    boolean comparePassword(String inputPassword, String targetPassword);
}
