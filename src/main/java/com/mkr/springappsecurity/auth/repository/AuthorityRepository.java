package com.mkr.springappsecurity.auth.repository;

import com.mkr.springappsecurity.auth.model.Authority;

public interface AuthorityRepository {
    Authority save(Authority entity);

    void deleteAllInBatch();
}
