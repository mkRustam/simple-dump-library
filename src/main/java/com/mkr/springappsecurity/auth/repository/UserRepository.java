package com.mkr.springappsecurity.auth.repository;

import com.mkr.springappsecurity.auth.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User entity);

    void deleteUserByUsername(String username);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    void deleteAllInBatch();
}
