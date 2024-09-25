package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.service.SignatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure")
public class SecureApiController {

    private final SignatureService signatureService;

    public SecureApiController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @PostMapping("/resource")
    public ResponseEntity<String> handleRequest(
            @RequestParam String data,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestHeader("Public-Key") String publicKeyBase64) {

        // Extracting a signature from the header
        String signatureBase64 = extractSignatureFromHeader(authorizationHeader);

        // Signature verification
        if (!signatureService.verifySignature(data, signatureBase64, publicKeyBase64)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature. Request denied.");
        }

        // If the signature is valid, process the request
        return ResponseEntity.ok("Request processed successfully.");
    }

    // Method for extracting a signature from the authorization header
    private String extractSignatureFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Signature ")) {
            return authorizationHeader.substring("Signature ".length());
        }
        throw new IllegalArgumentException("Invalid authorization header format.");
    }
}