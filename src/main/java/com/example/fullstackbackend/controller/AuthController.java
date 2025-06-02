package com.example.fullstackbackend.controller;

import com.example.fullstackbackend.config.JwtUtil;
import com.example.fullstackbackend.model.AuthUser;
import com.example.fullstackbackend.repository.IdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {
    @Autowired
     IdentificationRepository identificationRepository;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        AuthUser user = identificationRepository.findByLogin(request.getLogin());

        if (user != null) {
            if (request.getPassword().equals(user.getPassword())) {
                String token = jwtUtil.generateToken(user.getLogin());
                System.out.println("Авторизован");
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                System.out.println("Неправильный пароль");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
            }
        } else {
            System.out.println("Пользователь не найден");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }
}


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
