package com.order.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Hasher {

    private static final int ITERATION = 1000;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int KEY_LENGTH = 128;

    public String hash(String value) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(value.toCharArray(), salt, ITERATION, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return ITERATION + ":" + Base64.getEncoder().encodeToString(salt) + ":" +
                    Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException();
        }
    }

    public boolean validatePassword(String providedPassword, String storedPassword) {
        try {
            String[] parts = storedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] hash = Base64.getDecoder().decode(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(providedPassword.toCharArray(), salt, iterations, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            return Arrays.equals(hash, testHash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
