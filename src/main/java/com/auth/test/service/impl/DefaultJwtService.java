package com.auth.test.service.impl;

import com.auth.test.model.JwtUser;
import com.auth.test.model.User;
import com.auth.test.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Data
@RequiredArgsConstructor
public class DefaultJwtService implements JwtService {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long expirationTimeInMilliseconds;
    private final UserDetailsService userDetailsService;

    @Override
    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getRoles());

        Date todaysDate = new Date();
        Date jwtValidity = getJwtValidity(todaysDate);
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(todaysDate)
                .setExpiration(jwtValidity)
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer")) {
            return null;
        }
        return token.substring(7);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(
                jwtUser,
                "",
                jwtUser.getAuthorities());
    }

    @Override
    public String getUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Date getJwtValidity(Date todaysDate) {
        return new Date(todaysDate.getTime() + expirationTimeInMilliseconds);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
