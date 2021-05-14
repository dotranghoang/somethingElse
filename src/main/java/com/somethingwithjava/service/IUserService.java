package com.somethingwithjava.service;

import com.somethingwithjava.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    void save(User user);

    List<User> getAllUser();

    void delete(String userName);

    Optional<User> getUserByUserID(String userId);
}
