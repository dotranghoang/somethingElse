package com.somethingwithjava.service.IMPL;

import com.somethingwithjava.model.User;
import com.somethingwithjava.repository.IUserRepository;
import com.somethingwithjava.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService  {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void delete(String userName) {
        userRepository.deleteByUserName(userName);
    }

    @Override
    public User getUserByUserID(String userName) {
        return userRepository.findUserByUserName(userName);
    }

}
