package com.auth.test.mapper;

import com.auth.test.model.JwtUser;
import com.auth.test.model.User;
import com.auth.test.model.dto.UserDto;

public interface UserMapper {

    JwtUser toJwtUser(User user);

    User fromUserDto(UserDto userDto);

    UserDto toUserDto(User user);
}
