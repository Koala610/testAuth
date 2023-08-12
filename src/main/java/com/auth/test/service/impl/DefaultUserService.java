package com.auth.test.service.impl;

import com.auth.test.mapper.UserMapper;
import com.auth.test.model.Role;
import com.auth.test.model.User;
import com.auth.test.model.dto.UserDto;
import com.auth.test.model.type.Status;
import com.auth.test.repository.UserRepository;
import com.auth.test.service.RoleService;
import com.auth.test.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${spring.security.account-expiration}")
    private long accountExpirationMs;
    @Value("${spring.security.credentials-expiration}")
    private long credentialsExpirationMs;
    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    @Transactional
    public UserDto register(UserDto userDto) {
        User user = userMapper.fromUserDto(userDto);
        List<Role> defaultRoles = getDefaultRoles();
        user.setRoles(defaultRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setAccountExpirationDate(getFutureDate(accountExpirationMs));
        user.setCredentialsExpirationDate(getFutureDate(credentialsExpirationMs));
        user.setCreationTs(Timestamp.from(Instant.now()));
        return userMapper.toUserDto(userRepository.save(user));
    }

    private List<Role> getDefaultRoles() {
        Role defaultRole = roleService.findByName(DEFAULT_ROLE);
        List<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        return roles;
    }

    private Date getFutureDate(long milliseconds) {
        return new Date(Instant.now().toEpochMilli() + milliseconds * 1000);
    }
}
