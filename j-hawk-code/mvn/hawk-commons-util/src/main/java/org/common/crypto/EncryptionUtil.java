package org.common.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptionUtil {

    public static String encrypt(String plainPassword) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1"); // explicit algorithm
        byte[] raw = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(raw);
    }
}
