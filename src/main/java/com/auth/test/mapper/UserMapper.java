package com.auth.test.mapper;

import com.auth.test.model.JwtUser;
import com.auth.test.model.User;

public interface UserMapper {

    JwtUser toJwtUser(User user);
}
