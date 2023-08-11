package com.auth.test.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateToken(String username, String password);

    String validateToken(String token);

    Authentication getAuthentication(String token);

    String getUsername(String token);
}
