package com.mkr.springappsecurity.security;

import com.mkr.springappsecurity.entity.model.security.User;
import com.mkr.springappsecurity.entity.model.security.error.UserAlreadyExists;
import com.mkr.springappsecurity.entity.model.security.error.WrongOldPassword;
import com.mkr.springappsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.Collection;

@Slf4j
public class JpaUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
        .getContextHolderStrategy();

    public JpaUserDetailsManager(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        validateUserDetails(userDetails);
        if (userExists(userDetails.getUsername())) {
            throw new UserAlreadyExists("User with username already exists");
        }

        User user = (User) userDetails;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        validateUserDetails(userDetails);

        var existsUserOptional = userRepository.findUserByUsername(userDetails.getUsername());
        if (existsUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        var existsUser = existsUserOptional.get();
        var user = (User) userDetails;

        // Обновляем только необходимые поля
        existsUser.setRole(user.getRole());

        userRepository.save(existsUser);
    }

    @Override
    public void deleteUser(String username) {
        if (!userExists(username)) {
            throw new UsernameNotFoundException("User not found");
        }

        userRepository.deleteUserByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        var currentAuthentication = securityContextHolderStrategy.getContext().getAuthentication();
        if (currentAuthentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }

        var username = currentAuthentication.getName();
        var currentUser = (User) loadUserByUsername(username);
        // Проверяем если исходный пароль после кодирования совпадает с закодированным паролем из хранилища
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new WrongOldPassword("Old password is wrong");
        }

        // Сбрасываем аутентификацию со старым паролем
        log.info("Reauthenticating user '{}'", username);
//        authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(username, oldPassword));
        // Обновление пароля в БД
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
        // Повторная аутентификация с новым паролем
//        Authentication newAuthentication = createNewAuthentication(currentAuthentication, newPassword);
//        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
//        context.setAuthentication(newAuthentication);
//        securityContextHolderStrategy.setContext(context);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository
            .existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }

    private void validateUserDetails(UserDetails userDetails) {
        Assert.hasText(userDetails.getUsername(), "Username must not be empty");
        Assert.isInstanceOf(User.class, userDetails, "User must not be an instance of User");
        validateAuthorities(userDetails.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notEmpty(authorities, "Authorities must not be null or empty");
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority must not be null");
            Assert.hasText(grantedAuthority.getAuthority(), "getAuthority() must not be empty");
        }
    }

    private Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());
        UsernamePasswordAuthenticationToken newAuthentication = UsernamePasswordAuthenticationToken.authenticated(user,
            null, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());
        return newAuthentication;
    }
}
