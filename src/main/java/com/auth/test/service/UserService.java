package com.auth.test.service;

import com.auth.test.model.User;

public interface UserService {

    User findByUsername(String username);

    User register(User user);
}
