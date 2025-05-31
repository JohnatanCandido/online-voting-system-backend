package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface LoginRepository extends JpaRepository<Login, UUID> {

    UserDetails findByUsername(String username);
}
