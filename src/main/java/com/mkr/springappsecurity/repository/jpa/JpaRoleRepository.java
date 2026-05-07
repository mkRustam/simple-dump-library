package com.mkr.springappsecurity.repository.jpa;

import com.mkr.springappsecurity.entity.model.security.Role;
import com.mkr.springappsecurity.repository.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends RoleRepository, JpaRepository<Role, Long> {

}
