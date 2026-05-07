package com.mkr.springappsecurity.entity.model.security.error;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExists extends AuthenticationException {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
