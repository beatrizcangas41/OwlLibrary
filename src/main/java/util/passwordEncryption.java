package util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class passwordEncryption {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}