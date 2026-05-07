package com.mkr.springappsecurity.repository;

import com.mkr.springappsecurity.entity.model.security.Authority;

public interface AuthorityRepository {
    Authority save(Authority entity);

    void deleteAllInBatch();
}
