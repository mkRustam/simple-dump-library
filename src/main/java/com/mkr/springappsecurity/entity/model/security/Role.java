package com.mkr.springappsecurity.entity.model.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "credentials_role")
@Getter
@Setter
public class Role {

    @Id
    private String name;

    // Много Role могут иметь много Authority
    @ManyToMany(fetch = FetchType.EAGER)
    // В таблице связности:
    // - joinColumns — это столбцы ключа, которые ссылаются на Role
    // - inverseJoinColumns — это столбцы ключа, которые ссылаются на Authority
    // в итоге каждое поле таблицы является связкой role_id - authority_id
    @JoinTable(
        name = "role_authority",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities;
}
