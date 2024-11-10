package com.swingdating.System;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHash {

    private String password_hash;
    private String password_salt;
    
    /**
     * Creates a completly new PasswordHash
     * @param password
     */
    public PasswordHash(String password) {
        this.password_salt = generateSalt();
        this.password_hash = generateSaltedHash(password, this.password_salt);
    }
    /**
     * Generates a new PasswordHash with a given salt String
     * @param password Password as String
     * @param password_salt Password Salt as String
     */
    public PasswordHash(String password, String password_salt) {
        this.password_salt = password_salt;
        this.password_hash = generateSaltedHash(password, this.password_salt);
    }
    public PasswordHash(String password_hash, String password_salt, boolean hashsalt) {
        this.password_hash =  password_hash;
        this.password_salt = password_salt;
    }
    
    private String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    private String generateSaltedHash(String input, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest((salt + input).getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(PasswordHash userPasswordHash, PasswordHash storesPasswordHash) {
        return userPasswordHash.getPasswordHash().equals(storesPasswordHash.getPasswordHash());
    }


    public String getPasswordHash() {
        return this.password_hash;
    }
    public String getPasswordSalt() {
        return this.password_salt;
    }

}
