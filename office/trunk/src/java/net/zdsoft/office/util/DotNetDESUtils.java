package net.zdsoft.office.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.codehaus.xfire.util.Base64;

public class DotNetDESUtils {

    private static final String CHARSET_NAME = "UTF-8";

    private static final String CryptAlgorithm = "DES/CBC/PKCS5Padding";
    private static final String KeyAlgorithm = "DES";

    private static final String defaultKey = "Guz(%&hj";
    private static final String defaultIv = "E4ghj*Gh";

    public static String desEncryptAndBase64(String message) {
        return Base64.encode(desEncrypt(message, defaultKey));
    }

    public static byte[] desEncrypt(String message, String key) {
        try {
            Cipher cipher = Cipher.getInstance(CryptAlgorithm);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHARSET_NAME));
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance(KeyAlgorithm);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(defaultIv
                    .getBytes(CHARSET_NAME));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return cipher.doFinal(message.getBytes(CHARSET_NAME));
        }
        catch (Exception e) {
            throw new RuntimeException("could not desEncrypt:" + e);
        }
    }

    public static byte[] desDecryptAndBase64(String message) {
        return desDecrypt(Base64.decode(message), defaultKey);
    }

    public static byte[] desDecrypt(byte[] message, String key) {
        try {
            Cipher cipher = Cipher.getInstance(CryptAlgorithm);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHARSET_NAME));
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance(KeyAlgorithm);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(defaultIv
                    .getBytes(CHARSET_NAME));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return cipher.doFinal(message);
        }
        catch (Exception e) {
            throw new RuntimeException("could not desDecrypt:" + e);
        }
    }

}
