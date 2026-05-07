package com.mkr.springappsecurity.repository.jpa;

import com.mkr.springappsecurity.entity.model.security.User;
import com.mkr.springappsecurity.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, Long> {

}
