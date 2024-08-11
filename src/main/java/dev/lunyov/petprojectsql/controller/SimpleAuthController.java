package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SimpleAuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/generate-token")
    public Map<String, String> generateToken(@RequestParam String email) {
        String token = userService.generateJwtToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("jwt", token);
        return response;
    }
}