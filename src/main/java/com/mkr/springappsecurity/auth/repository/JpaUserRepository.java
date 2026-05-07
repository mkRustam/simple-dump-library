package com.mkr.springappsecurity.auth.repository;

import com.mkr.springappsecurity.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, Long> {
}
