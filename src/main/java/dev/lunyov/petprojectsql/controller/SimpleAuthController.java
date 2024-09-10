package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.service.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SimpleAuthController {

    private JwtService jwtService;

    public SimpleAuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/generate-token")
    public Map<String, String> generateToken(@RequestParam String email) {
        String token = jwtService.generateJwtToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("jwt", token);
        return response;
    }
}