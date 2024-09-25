package dev.lunyov.petprojectsql.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SignatureGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SignatureGenerator.class);

    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        logger.info("Loading a private key from a file: {}", filePath);

        String privateKeyPEM = new String(Files.readAllBytes(Paths.get(filePath)));

        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        logger.debug("Private key in PEM format (without headers): {}", privateKeyPEM);

        // Base64 decoding
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);

        // Creates a key specification for PKCS#8 formatting from a byte key array
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(spec);
        logger.info("Private key successfully uploaded.");
        return privateKey;
    }


    public static String loadPublicKey(String filePath) throws Exception {
        logger.info("Loading a public key from a file: {}", filePath);

        String publicKeyPEM = new String(Files.readAllBytes(Paths.get(filePath)));

        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        logger.debug("Public key in PEM format (without headers): {}", publicKeyPEM);
        return publicKeyPEM;
    }

    public static String generateSignature(String data, PrivateKey privateKey) throws Exception {
        logger.info("Generate a signature for data: {}", data);

//        returns a Signature object that uses SHA-256 as a hash function and RSA as a cryptographic algorithm
        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = signature.sign();

        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    public static void main(String[] args) throws Exception {
        try {
            String privateKeyPath = "src/main/resources/keys/private_key.pem";
            PrivateKey privateKey = loadPrivateKey(privateKeyPath);
            String data = "timestamp=1609459200";
            String signature = generateSignature(data, privateKey);
            System.out.println("Generated signature: " + signature);
        } catch (Exception e) {
            logger.error("Error loading a key or generating a signature", e);
        }

        String publicKeyPath = "src/main/resources/keys/public_key.pem";
        String publicKey = loadPublicKey(publicKeyPath);
        System.out.println("Public key: " + publicKey);
    }
}