package com.auth.test.service.impl;

import com.auth.test.mapper.UserMapper;
import com.auth.test.model.User;
import com.auth.test.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;

    public DefaultUserDetailsService(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            return null;
        }
        return userMapper.toJwtUser(user);
    }
}
