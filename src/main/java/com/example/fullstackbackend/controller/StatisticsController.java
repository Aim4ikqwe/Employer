package com.example.fullstackbackend.controller;

import com.example.fullstackbackend.model.User;
import com.example.fullstackbackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    private final UserRepository userRepository;

    public StatisticsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getStatistics() {
        List<User> users = userRepository.findAll();

        Map<String, Long> jobCounts = users.stream()
                .collect(Collectors.groupingBy(User::getUsername, Collectors.counting()));

        long totalUsers = users.size();

        long gmailCount = users.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().endsWith("@gmail.com"))
                .count();

        Map<String, Object> result = new HashMap<>();
        result.put("totalUsers", totalUsers);
        result.put("byJob", jobCounts);
        result.put("gmailUsers", gmailCount);

        return ResponseEntity.ok(result);
    }
}
