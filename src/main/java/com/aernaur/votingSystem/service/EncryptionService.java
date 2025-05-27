package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.exceptions.EncryptionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {

    @Value("${security.encryption.key}")
    private String secretKeyValue;

    public String encrypt(String text) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] bytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new EncryptionException(e);
        }
    }

    public String decrypt(String text) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] encryptedBytes = Base64.getDecoder().decode(text);
            byte[] bytes = cipher.doFinal(encryptedBytes);
            return new String(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new EncryptionException(e);
        }
    }

    private SecretKey getSecretKey() throws NoSuchAlgorithmException {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyValue);
        return new SecretKeySpec(keyBytes, "AES");
    }
}
