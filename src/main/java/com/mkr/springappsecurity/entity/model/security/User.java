package com.mkr.springappsecurity.entity.model.security;

import com.mkr.springappsecurity.entity.model.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "credentials_user")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    // Много User могут иметь одну и ту же роль
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // Каждому User соответствует ровно один Person.
    // Owning side — FK person_id живёт в таблице credentials_user.
    // CascadeType.PERSIST/MERGE — чтобы создание/обновление User тянуло за собой Person,
    // но удаление User не каскадировалось в Person (у Person есть FK от books).
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
