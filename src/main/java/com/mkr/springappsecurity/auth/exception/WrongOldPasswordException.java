package com.mkr.springappsecurity.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class WrongOldPasswordException extends AuthenticationException {
    public WrongOldPasswordException(String message) {
        super(message);
    }
}
