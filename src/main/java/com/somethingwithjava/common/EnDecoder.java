package com.somethingwithjava.common;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Slf4j
public class EnDecoder {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'A', 'C', 'E', 'O', 'F', 'B', 'A', 'S', 'E'};

    public static String encrypt(String string) {
        String resultEncrypt = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] encVal = c.doFinal(string.getBytes());
            resultEncrypt = Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultEncrypt;
    }

    public static String decrypt(String string) {
        String resultDecrypt = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] base64DecodedValue = Base64.getDecoder().decode(string);
            byte[] deValue = c.doFinal(base64DecodedValue);
            resultDecrypt = new String(deValue);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultDecrypt;
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGO);
    }
}
