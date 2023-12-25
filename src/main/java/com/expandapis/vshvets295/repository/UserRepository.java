package com.expandapis.vshvets295.repository;

import java.util.Optional;

import com.expandapis.vshvets295.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
