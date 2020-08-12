package com.websocket.board.repo.auth;

import com.websocket.board.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    int deleteByEmailAndPassword(String email, String password);

    int deleteByEmail(String email);
}
