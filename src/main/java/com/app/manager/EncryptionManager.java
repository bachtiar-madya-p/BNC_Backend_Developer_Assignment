package com.app.manager;

import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class EncryptionManager extends BaseManager {

    private static EncryptionManager instance;

    private static final String ALGO = "PBEWithMD5AndDES";
    private static final String KEY = "@#1D+78!7F&1C8%8728FEF*99&%!@d^7d+-";
    private static final int ITRCOUNT = 17;
    private static final String HASH = "SHA-256";

    private MessageDigest messageDigest;
    private SecureRandom secureRandom;

    // 8-byte Salt
    private static final byte[] SALT = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35,
            (byte) 0xE3, (byte) 0x03};

    private static Cipher ecipher;
    private static Cipher dcipher;

    private EncryptionManager() {
        log = getLogger(EncryptionManager.class);
        init();
    }

    private synchronized void init() {
        final String methodName = "init";
        start(methodName);

        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(KEY.toCharArray(), SALT, ITRCOUNT);
            SecretKey key = SecretKeyFactory.getInstance(ALGO).generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            messageDigest = MessageDigest.getInstance(HASH);
            secureRandom = new SecureRandom();

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITRCOUNT);

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | InvalidKeyException ex) {
            log.error(methodName, ex.getMessage());
        }

        completed(methodName);
    }

    public synchronized void shutdown() {
        ecipher = null;
        dcipher = null;
    }

    /**
     * Encrypts the given plaintext and returns an encrypted ciphertext.
     *
     * @param plaintext The text to be encrypted.
     * @return A ciphertext
     */
    public synchronized String encrypt(String plaintext) {

        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = plaintext.getBytes(StandardCharsets.UTF_8);

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new String(Base64Utils.encode(enc), StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException ex) {
            log.error("encrypt", ex.getMessage());
        }
        return null;
    }

    /**
     * Decrypts the given ciphertext and returns an decrypted plaintext.
     *
     * @param ciphertext The text to be decrypted.
     * @return A plaintext
     */
    public synchronized String decrypt(String ciphertext) {

        try {
            // Decode base64 to get bytes
            byte[] dec = Base64Utils.decode(ciphertext.getBytes(StandardCharsets.UTF_8));

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, StandardCharsets.UTF_8);

        } catch (BadPaddingException | IllegalBlockSizeException ex) {
            log.error("decrypt", ex.getMessage());
        }
        return "";
    }

    public static EncryptionManager getInstance() {
        if (instance == null) {
            instance = new EncryptionManager();
        }
        return instance;
    }

    public String generateRandomString(int length) {
        return new BigInteger(length * 5, secureRandom).toString(32);
    }

    public String hash(String text, String salt) {
        String hashed = "";
        messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] bytes;
        bytes = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        hashed = sb.toString();
        messageDigest.reset();

        return hashed;
    }
}
