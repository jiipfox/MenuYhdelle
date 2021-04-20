package com.example.menuyhdelle;

import java.security.AlgorithmParameters;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;


/**
 * Usage:
 *         Krypto decrypter = new Krypto("ABCDEFGHIJKL");
 *         String encrypted = decrypter.encrypt("I am sensible");
 *         String decrypted = decrypter.decrypt(encrypted);
 *         System.out.println(decrypted);
 */
public class Krypto {

    Cipher dcipher;
    byte[] salt = new String("1234567890123456").getBytes(); // length = 16
    int keyStrength = 128; // equals to 16 characters
    int iterationCount = 1024;
    SecretKey key;
    byte[] iv;

    Krypto(String passPhrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    public String encrypt(String data) throws Exception {
        dcipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = dcipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] utf8EncryptedData = dcipher.doFinal(data.getBytes());
        String base64EncryptedData = Base64.encodeToString(utf8EncryptedData, Base64.DEFAULT);

        System.out.println("Encrypted Data " + base64EncryptedData);
        return base64EncryptedData;
    }

    public String decrypt(String base64EncryptedData) throws Exception {
        dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decryptedData = Base64.decode(base64EncryptedData, Base64.DEFAULT);
        byte[] utf8 = dcipher.doFinal(decryptedData);
        return new String(utf8, "UTF8");
    }

}
