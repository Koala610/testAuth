package com.auth.test.mapper;

import com.auth.test.model.Role;
import org.springframework.security.core.GrantedAuthority;

public interface RoleMapper {

    GrantedAuthority toGrantedAuthority(Role role);
}
