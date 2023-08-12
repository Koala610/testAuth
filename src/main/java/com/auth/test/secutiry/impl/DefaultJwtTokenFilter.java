package com.auth.test.secutiry.impl;

import com.auth.test.secutiry.JwtTokenFilter;
import com.auth.test.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultJwtTokenFilter
        extends GenericFilter
        implements JwtTokenFilter {

    private final JwtService jwtService;

    public DefaultJwtTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtService.getToken((HttpServletRequest) request);
        if (token != null && !token.isEmpty() && jwtService.validateToken(token)) {
            Authentication authentication = jwtService.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
