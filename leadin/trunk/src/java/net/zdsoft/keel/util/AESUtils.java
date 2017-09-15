/* 
 * @(#)AESUtils.java    Created on 2008-8-27
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: AESUtils.java,v 1.1 2008/08/28 06:03:55 huangwj Exp $
 */
package net.zdsoft.keel.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加密算法工具类.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2008/08/28 06:03:55 $
 */
public class AESUtils {

    private static Cipher ecipher;
    private static Cipher dcipher;
    private static SecretKey key = null;

    static {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            key = keygen.generateKey();

        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Create an 8-byte initialization vector
        byte[] iv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
                0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

        try {
            ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // CBC requires an initialization vector
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String str) {
        String encrypted = null;

        try {
            byte[] cipherText = ecipher.doFinal(str.getBytes());
            encrypted = new String(Base64.encodeBase64(cipherText));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return encrypted;
    }

    public static String decrypt(String str) {
        String decrypted = null;

        try {
            byte[] clearText = dcipher.doFinal(Base64.decodeBase64(str
                    .getBytes()));
            decrypted = new String(clearText);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return decrypted;
    }

    private AESUtils() {
    }

}
