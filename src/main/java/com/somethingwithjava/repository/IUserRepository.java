package com.somethingwithjava.repository;

import com.somethingwithjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    void deleteByUserName(String userName);

    Optional<User> findUserByUserName(String userName);
}
