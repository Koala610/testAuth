package com.auth.test.controller;

import com.auth.test.model.User;
import com.auth.test.model.dto.LoginDto;
import com.auth.test.model.dto.UserDto;
import com.auth.test.service.JwtService;
import com.auth.test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registry")
    public Object registry(@RequestBody @Valid UserDto user) {
        try {
            return ResponseEntity.ok(userService.register(user));

        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.badRequest();
        }

        String token = jwtService.generateToken(user);

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

}
