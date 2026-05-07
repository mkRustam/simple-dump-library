package com.mkr.springappsecurity.entity.model.security.enums;

import com.mkr.springappsecurity.entity.model.security.Authority;

public enum Authorities {
    // просмотр информации о книгах
    BOOK_READ,

    // создание, редактирование и удаление книг
    BOOK_MANAGE,

    // просмотр информации о пользователях
    USER_READ,

    // создание, редактирование и удаление пользователей
    USER_MANAGE,

    // подача заявки на получение и возврат книг
    LOAN_BASIC,

    // подтверждение выдачи и возврата книг
    LOAN_MANAGE,

    // право на добавление отзывов о книгах
    REVIEW_CREATE,

    // добавление и удаление отзывов
    REVIEW_MANAGE,

    // доступ к административной панели
    ADMIN_PANEL_ACCESS;

    public Authority toAuthority() {
        Authority authority = new Authority();
        authority.setName(name());
        return authority;
    }
}
