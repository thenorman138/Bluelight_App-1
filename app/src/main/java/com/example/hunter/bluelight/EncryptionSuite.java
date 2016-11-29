package com.example.hunter.bluelight;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import static android.content.ContentValues.TAG;

/**
 * Created by Tyler on 11/28/2016.
 */

public class EncryptionSuite {
    private Key publicKey;
    private Key privateKey;
    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    protected EncryptionSuite() {
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

    protected EncryptionSuite(Key pub, Key priv) {
        publicKey = pub;
        privateKey = priv;
    }

    protected String encodeMessage(String message) {
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

    protected String decodeMessage(String encodedString) {
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

    protected String hashPassword(String password) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
            byte[] bytes = password.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            hash = bytesToHex( bytes );
        }
        catch( NoSuchAlgorithmException | UnsupportedEncodingException e ) {
            e.printStackTrace();
        }
        return hash;

    }

    private static String bytesToHex( byte[] bytes ) {
        char[] hexChars = new char[ bytes.length * 2 ];
        for( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[ j ] & 0xFF;
            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
        }
        return new String( hexChars );
    }

}
