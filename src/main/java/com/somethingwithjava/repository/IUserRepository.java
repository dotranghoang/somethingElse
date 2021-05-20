package com.somethingwithjava.repository;

import com.somethingwithjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    void deleteByUserName(String userName);

    User findUserByUserName(String userName);

    @Query("SELECT a FROM User a")
    List<User> findAll();

    boolean existsByUserName(String userName);
}
