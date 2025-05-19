package com.br.tasks.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
public class EncodingUtil {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String data) {
        return encoder.encode(data);
    }

    public static boolean hashMatches(String rawData, String hashData) {
        return encoder.matches(rawData, hashData);
    }
}
