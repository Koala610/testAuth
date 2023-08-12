package com.auth.test.service;

import com.auth.test.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateToken(User user);

    boolean validateToken(String token);

    String getToken(HttpServletRequest request);

    Authentication getAuthentication(String token);

    String getUsername(String token);
}
