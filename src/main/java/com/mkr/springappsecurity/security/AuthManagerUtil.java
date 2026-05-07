package com.mkr.springappsecurity.security;

import com.mkr.springappsecurity.entity.model.security.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

public class AuthManagerUtil {

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
        .getContextHolderStrategy();

    public Long getCurrentUserId() {
        var currentAuthorization = securityContextHolderStrategy.getContext().getAuthentication();
        if (currentAuthorization == null) {
            throw new AccessDeniedException("You are not authorized to do this");
        }

        /*
    *
    * currentAuthorization.getPrincipal()
	    result = {User@14584}
 	        id = {Long@14586} 2
 	        username = "admin1"
 	        password = "$2a$10$iCmD0kd8DIL9TJ8PKbo9KuDbV2lEV.RuD0iXXZWByCCExQLBbhffy"
 	        role = {Role@14589}
         		name = "ROLE_ADMIN"
     	    	authorities = {PersistentBag@14591}  size = 7
    * */

        return ((User) currentAuthorization.getPrincipal()).getId();
    }
}
