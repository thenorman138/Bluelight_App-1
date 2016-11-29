package com.example.hunter.bluelight;

import android.util.Base64;
import android.util.Log;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

import static android.content.ContentValues.TAG;

/**
 * Created by Tyler Adams on 11/28/2016.
 */

public class EncryptionSuite {
    private Key publicKey;
    private Key privateKey;

    private void EncryptionSuite() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            Log.e(TAG, "RSA key pair error");
        }
    }

    private String encodeMessage(String message) {
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(message.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "RSA encryption error");
        }
        return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
    }

    private String decodeMessage(String encodedString) {
        byte[] decodedBytes = null;
        byte[] encodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e(TAG, "RSA decryption error");
        }
        return Base64.encodeToString(decodedBytes, Base64.DEFAULT);
    }

    private String encodePassword(String password) {
        String temp=password;
        return temp;
    }

}
