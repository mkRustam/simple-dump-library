package com.mkr.springappsecurity.entity.model.security.error;

import org.springframework.security.core.AuthenticationException;

public class WrongOldPassword extends AuthenticationException {
    public WrongOldPassword(String message) {
        super(message);
    }
}
