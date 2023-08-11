package com.auth.test.mapper.impl;

import com.auth.test.mapper.RoleMapper;
import com.auth.test.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class DefaultRoleMapper implements RoleMapper {

    @Override
    public GrantedAuthority toGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getName());
    }
}
