package com.mkr.springappsecurity.runner;

import com.mkr.springappsecurity.entity.model.Person;
import com.mkr.springappsecurity.entity.model.security.Authority;
import com.mkr.springappsecurity.entity.model.security.Role;
import com.mkr.springappsecurity.entity.model.security.User;
import com.mkr.springappsecurity.entity.model.security.enums.Authorities;
import com.mkr.springappsecurity.entity.model.security.enums.Roles;
import com.mkr.springappsecurity.entity.model.security.error.UserAlreadyExists;
import com.mkr.springappsecurity.repository.AuthorityRepository;
import com.mkr.springappsecurity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final UserDetailsManager userDetailsManager;

    @Override
    public void run(String... args) {
        log.info("Initializing security data...");
        addAuthorities();
        addRoles();
        addUsers();
        log.info("Initializing security data...Done");
    }

    private void addUsers() {
        log.info("Adding users...");
        createIfAbsent("user1", "password1", "User One", Roles.ROLE_USER);
        createIfAbsent("admin1", "admin1", "Admin One", Roles.ROLE_ADMIN);
        log.info("Adding users...Done");
    }

    private void createIfAbsent(String username, String password, String displayName, Roles role) {
        try {
            userDetailsManager.createUser(buildUser(username, password, displayName, role));
        } catch (UserAlreadyExists ex) {
            log.info("User '{}' already exists, skipping", username);
        }
    }

    private void addRoles() {
        log.info("Adding roles...");
        for (Roles roleEnum : Roles.values()) {
            roleRepository.save(roleEnum.toRole());
        }
        log.info("Adding roles...Done");
    }

    private void addAuthorities() {
        log.info("Adding authorities...");
        for (Authorities authorityEnum : Authorities.values()) {
            authorityRepository.save(toAuthority(authorityEnum));
        }
        log.info("Adding authorities...Done");
    }

    private Authority toAuthority(Authorities authorities) {
        Authority authority = new Authority();
        authority.setName(authorities.name());
        return authority;
    }

    private User buildUser(String username, String password, String displayName, Roles roles) {
        Person person = new Person();
        person.setName(displayName);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(roles.toRole());
        // Person сохранится каскадом вместе с User (CascadeType.PERSIST)
        user.setPerson(person);
        return user;
    }
}
