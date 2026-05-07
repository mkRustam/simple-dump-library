package com.mkr.springappsecurity.entity.model.security.enums;

import com.mkr.springappsecurity.entity.model.security.Authority;
import com.mkr.springappsecurity.entity.model.security.Role;
import lombok.Getter;

import java.util.List;

import static com.mkr.springappsecurity.entity.model.security.enums.Authorities.*;
import static com.mkr.springappsecurity.entity.model.security.enums.Authorities.ADMIN_PANEL_ACCESS;
import static com.mkr.springappsecurity.entity.model.security.enums.Authorities.LOAN_MANAGE;
import static com.mkr.springappsecurity.entity.model.security.enums.Authorities.REVIEW_MANAGE;
import static com.mkr.springappsecurity.entity.model.security.enums.Authorities.USER_MANAGE;

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
