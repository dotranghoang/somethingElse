package com.somethingwithjava.service.IMPL;

import com.somethingwithjava.model.User;
import com.somethingwithjava.model.UserDetail;
import com.somethingwithjava.repository.IUserRepository;
import com.somethingwithjava.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService  {
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
    public Optional<User> getUserByUserID(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUserName(userName);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(userName);
        }
        return new UserDetail(user);
    }
}
