package com.mkr.springappsecurity.auth.model.enums;

import com.mkr.springappsecurity.auth.model.Authority;

public enum Authorities {
    BOOK_READ,
    BOOK_MANAGE,
    USER_READ,
    USER_MANAGE,
    LOAN_BASIC,
    LOAN_MANAGE,
    REVIEW_CREATE,
    REVIEW_MANAGE,
    ADMIN_PANEL_ACCESS;

    public Authority toAuthority() {
        Authority authority = new Authority();
        authority.setName(name());
        return authority;
    }
}
