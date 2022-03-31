package com.example.security_practice.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: kbs
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
