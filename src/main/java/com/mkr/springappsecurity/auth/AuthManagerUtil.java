package com.mkr.springappsecurity.auth;

import com.mkr.springappsecurity.auth.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthManagerUtil {

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
        .getContextHolderStrategy();

    public Long getCurrentUserId() {
        var currentAuthorization = securityContextHolderStrategy.getContext().getAuthentication();
        if (currentAuthorization == null) {
            throw new AccessDeniedException("You are not authorized to do this");
        }
        return ((User) currentAuthorization.getPrincipal()).getId();
    }

    public Long getPersonId(UserDetails userDetails) {
        return ((User) userDetails).getPerson().getId();
    }
}
