package com.mkr.springappsecurity.auth.repository;

import com.mkr.springappsecurity.auth.model.Role;

public interface RoleRepository {
    Role save(Role role);

    void deleteAllInBatch();
}
