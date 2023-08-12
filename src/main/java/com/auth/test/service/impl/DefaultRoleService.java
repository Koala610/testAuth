package com.auth.test.service.impl;

import com.auth.test.model.Role;
import com.auth.test.repository.RoleRepository;
import com.auth.test.service.RoleService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class DefaultRoleService implements RoleService {

    private final RoleRepository roleRepository;

    public DefaultRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
