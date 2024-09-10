package dev.lunyov.petprojectsql.controller;


import dev.lunyov.petprojectsql.service.EmailService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

}