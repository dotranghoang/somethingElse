package com.somethingwithjava.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
public class EnDecoder {
    private final static String ALGORITHM = "AES";
    private final static String ALGORITHM_EN_DE_CRYPT = "AES/CBC/PKCS5Padding";
    private final static String HMAC_SHA_256 = "PBKDF2WithHmacSHA256";
    private final static String PASSWORD = "AceOfBase";
    private final static String SALT = "salt";
    private final static byte[] IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static SecretKey generateRandomKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    private static SecretKey generateKeyFromPassword()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(HMAC_SHA_256);
        KeySpec spec = new PBEKeySpec(EnDecoder.PASSWORD.toCharArray(), EnDecoder.SALT.getBytes(), 100000, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
        return secret;
    }

    private static IvParameterSpec generateIv() {
        return new IvParameterSpec(IV);
    }

    public static String encrypt(String input) throws Exception {
        if (!StringUtils.hasText(input)) {
            return null;
        }
        SecretKey key = generateKeyFromPassword();
        IvParameterSpec iv = generateIv();

        Cipher cipher = Cipher.getInstance(ALGORITHM_EN_DE_CRYPT);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String input) throws Exception {
        SecretKey key = generateKeyFromPassword();
        IvParameterSpec iv = generateIv();

        Cipher cipher = Cipher.getInstance(ALGORITHM_EN_DE_CRYPT);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(input));
        return new String(plainText);
    }
}
