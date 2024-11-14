package com.swingdating.System;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CredentialHash {

    private String password_hash;
    private String password_salt;
    
    /**
     * Creates a completly new CredentialHash
     * @param password
     */
    public CredentialHash(String password) {
        this.password_salt = generateSalt();
        this.password_hash = generateSaltedHash(password, this.password_salt);
    }
    /**
     * Generates a new CredentialHash with a given salt String
     * @param password Password as String
     * @param password_salt Password Salt as String
     */
    public CredentialHash(String password, String password_salt) {
        this.password_salt = password_salt;
        this.password_hash = generateSaltedHash(password, this.password_salt);
    }
    public CredentialHash(String password_hash, String password_salt, boolean hashNSalt) {
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

    public boolean verifyPassword(String pwtocompare) {
        return (new CredentialHash(pwtocompare, this.password_salt)).getCredentialHash().equals(password_hash);
    }
    public static boolean verifyPassword(CredentialHash userPWHash, String ComparingPWHash) {
        return userPWHash.getCredentialHash().equals(ComparingPWHash);
    }

    public String getCredentialHash() {
        return this.password_hash;
    }
    public String getPasswordSalt() {
        return this.password_salt;
    }

}
