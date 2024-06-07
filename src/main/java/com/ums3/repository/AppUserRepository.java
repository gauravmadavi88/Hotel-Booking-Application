package com.ums3.repository;

import com.ums3.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsernameOrEmailId(String username, String emailId);

    Optional<AppUser> findByUsername(String username);
}