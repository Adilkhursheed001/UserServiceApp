package com.example.UserServiceApp.repository;

import com.example.UserServiceApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Override
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmailID(String emailID);
}