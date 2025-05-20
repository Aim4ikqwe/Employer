package com.codewitharjun.fullstackbackend.controller;

import com.codewitharjun.fullstackbackend.config.JwtUtil;
import com.codewitharjun.fullstackbackend.model.AuthUser;
import com.codewitharjun.fullstackbackend.repository.IdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class AuthController {
    @Autowired
     IdentificationRepository identificationRepository;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Пример авторизации: жестко проверяем пользователя (в реале - из БД)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println("Логин: " + request.getLogin());
        System.out.println("Пароль: " + request.getPassword());

        AuthUser user = identificationRepository.findByLogin(request.getLogin());
        System.out.println("Найден пользователь: " + user);

        if (user != null) {
            if (request.getPassword().equals(user.getPassword())) {
                String token = jwtUtil.generateToken(user.getLogin());
                System.out.println("Генерация токена успешна");
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

    // Защищённый эндпоинт
    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not found");
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.validateTokenAndGetUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        return ResponseEntity.ok("Hi, " + username + "! It is safety endpoint.");
    }
}

// DTO для запроса логина

class AuthRequest {
    private String login;
    private String password;
    // геттеры и сеттеры
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

// DTO для ответа с токеном
class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
