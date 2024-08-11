package com.example.UserServiceApp.repository;

import com.example.UserServiceApp.model.Session;
import com.example.UserServiceApp.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

    @Override
     Session save(Session session);

    Optional<Session> findByToken(String token);
}
