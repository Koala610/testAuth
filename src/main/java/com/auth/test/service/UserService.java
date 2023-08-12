package com.auth.test.service;

import com.auth.test.model.User;
import com.auth.test.model.dto.UserDto;

public interface UserService {

    User findByUsername(String username);

    UserDto register(UserDto user);
}
