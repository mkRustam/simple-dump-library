package com.mkr.springappsecurity.auth.model.enums;

import com.mkr.springappsecurity.auth.model.Authority;
import com.mkr.springappsecurity.auth.model.Role;

import java.util.List;

import static com.mkr.springappsecurity.auth.model.enums.Authorities.*;

public enum Roles {
    ROLE_ADMIN(List.of(BOOK_READ, BOOK_MANAGE, USER_READ, USER_MANAGE, LOAN_MANAGE, REVIEW_MANAGE, ADMIN_PANEL_ACCESS)),
    ROLE_LIBRARIAN(List.of(BOOK_READ, BOOK_MANAGE, LOAN_MANAGE, REVIEW_MANAGE)),
    ROLE_USER(List.of(BOOK_READ, LOAN_BASIC, REVIEW_CREATE)),
    ROLE_GUEST(List.of(BOOK_READ));

    private final List<Authorities> authorities;

    Roles(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    public Role toRole() {
        Role role = new Role();
        role.setName(name());
        role.setAuthorities(authorities.stream().map(Authorities::toAuthority).toList());
        return role;
    }
}
