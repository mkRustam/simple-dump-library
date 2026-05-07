package com.mkr.springappsecurity.entity.model.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "credentials_authority")
@Getter
@Setter
public class Authority implements GrantedAuthority {

    @Id
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
