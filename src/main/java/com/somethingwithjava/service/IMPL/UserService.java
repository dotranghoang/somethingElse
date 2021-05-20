package com.somethingwithjava.service.IMPL;

import com.somethingwithjava.model.User;
import com.somethingwithjava.repository.IUserRepository;
import com.somethingwithjava.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements IUserService  {
    @Autowired
    private static IUserRepository userRepository;

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
    public User getUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public boolean comparePassword(String inputPassword, String targetPassword) {
        return Arrays.equals(new String[]{inputPassword}, new String[]{targetPassword});
    }

    public static synchronized boolean existsByUserName(String userName) {
        boolean isExist = userRepository.existsByUserName(userName);
        return isExist;
    }
}
