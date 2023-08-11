package com.auth.test.service;

import com.auth.test.model.Role;

public interface RoleService {

    Role findByName(String name);
}
