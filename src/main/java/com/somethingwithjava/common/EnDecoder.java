package com.somethingwithjava.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class EnDecoder {
    public static String encrypt(String string) {
        String resultEncrypt = "";
        try {
            byte[] keyBase64 = Base64.getDecoder().decode("TEST");
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(keyBase64, 16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] encVal = cipher.doFinal(string.getBytes());
            resultEncrypt = Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultEncrypt;
    }

//    public static String decrypt(String string) {
//        String resultDecrypt = "";
//        try {
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            byte[] base64DecodedValue = Base64.getDecoder().decode(string);
//            byte[] deValue = cipher.doFinal(base64DecodedValue);
//            resultDecrypt = new String(deValue);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return resultDecrypt;
//    }

}
