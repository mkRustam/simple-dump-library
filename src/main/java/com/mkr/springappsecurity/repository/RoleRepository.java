package com.mkr.springappsecurity.repository;

import com.mkr.springappsecurity.entity.model.security.Role;

public interface RoleRepository {
    Role save(Role role);

    void deleteAllInBatch();
}
