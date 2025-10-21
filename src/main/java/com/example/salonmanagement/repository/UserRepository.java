package com.example.salonmanagement.repository;

import com.example.salonmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Hàm này rất quan trọng cho Spring Security
    Optional<User> findByUsername(String username);
}