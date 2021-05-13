package com.somethingwithjava.service;

import com.somethingwithjava.model.User;
import com.somethingwithjava.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IUserService {

    void save(User user);

    List<User> getAllUser();

    void delete(String userId);
}
