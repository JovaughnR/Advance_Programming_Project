package common;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Generator implements Serializable {
    private static final long serialVersionUID = 12l;

    public static String hash(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(string.getBytes());

            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedBytes)
                hexStringBuilder.append(String.format("%02x", b));

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}