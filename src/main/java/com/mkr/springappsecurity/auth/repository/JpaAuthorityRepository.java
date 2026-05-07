package com.mkr.springappsecurity.auth.repository;

import com.mkr.springappsecurity.auth.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthorityRepository extends AuthorityRepository, JpaRepository<Authority, String> {
}
