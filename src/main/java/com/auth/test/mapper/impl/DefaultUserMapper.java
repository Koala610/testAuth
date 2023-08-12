package com.auth.test.mapper.impl;

import com.auth.test.mapper.RoleMapper;
import com.auth.test.mapper.UserMapper;
import com.auth.test.model.JwtUser;
import com.auth.test.model.Role;
import com.auth.test.model.User;
import com.auth.test.model.dto.UserDto;
import com.auth.test.model.type.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
public class DefaultUserMapper implements UserMapper {

    private final RoleMapper roleMapper;

    public DefaultUserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public JwtUser toJwtUser(User user) {
        return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                compareDateToNow(user.getAccountExpirationDate()) != -1,
                user.getStatus() == Status.ACTIVE,
                compareDateToNow(user.getCredentialsExpirationDate()) != -1,
                getGrantedAuthorities(user.getRoles()),
                user.getStatus() == Status.ACTIVE


        );
    }

    public User fromUserDto(UserDto userDto) {
        User user = new User();
        user.setStatus(Status.DRAFT);
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return user;
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    private int compareDateToNow(Date date) {
        return date.compareTo(Date.from(Instant.now()));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        return roles
                .stream()
                .map(roleMapper::toGrantedAuthority)
                .toList();
    }
}
