package com.mkr.springappsecurity.auth.repository;

import com.mkr.springappsecurity.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends RoleRepository, JpaRepository<Role, String> {
}
