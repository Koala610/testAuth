package com.auth.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class GreetingsController {

    @GetMapping
    public Object greet(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(String.format("Hi %s!", username));
    }
}
