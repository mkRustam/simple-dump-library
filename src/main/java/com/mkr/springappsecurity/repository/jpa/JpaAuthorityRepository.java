package com.mkr.springappsecurity.repository.jpa;

import com.mkr.springappsecurity.entity.model.security.Authority;
import com.mkr.springappsecurity.repository.AuthorityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthorityRepository extends AuthorityRepository, JpaRepository<Authority, Long> {
    
}
