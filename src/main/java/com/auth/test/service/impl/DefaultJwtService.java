package com.auth.test.service.impl;

import com.auth.test.service.JwtService;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Data
public class DefaultJwtService implements JwtService {

    private final String secret;
    private final long expirationTimeInMilliseconds;

    public DefaultJwtService(String secret, long expirationTimeInMilliseconds) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.expirationTimeInMilliseconds = expirationTimeInMilliseconds;
    }

    @Override
    public String generateToken(String username, String password) {
        return null;
    }

    @Override
    public String validateToken(String token) {
        return null;
    }

    @Override
    public Authentication getAuthentication(String token) {
        return null;
    }

    @Override
    public String getUsername(String token) {
        return null;
    }
}
