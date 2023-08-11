package com.auth.test.service.impl;

import com.auth.test.model.Role;
import com.auth.test.model.User;
import com.auth.test.model.type.Status;
import com.auth.test.repository.UserRepository;
import com.auth.test.service.RoleService;
import com.auth.test.service.UserService;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String DEFAULT_ROLE = "USER_ROLE";

    public DefaultUserService(
            UserRepository userRepository,
            RoleService roleService,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User register(User user) {
        List<Role> defaultRoles = getDefaultRoles();
        user.setRoles(defaultRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    private List<Role> getDefaultRoles() {
        Role defaultRole = roleService.findByName(DEFAULT_ROLE);
        List<Role> roles = new ArrayList<>(1);
        roles.add(defaultRole);
        return roles;
    }
}
