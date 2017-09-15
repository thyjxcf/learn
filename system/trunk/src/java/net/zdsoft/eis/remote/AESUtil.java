package net.zdsoft.eis.remote;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <Description> aes加密、解密辅助类 1、AES加、解密时的Key已经内置,给客户的调用提供便利; <br>
 * 
 * @author qss<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年11月9日 <br>
 */
public class AESUtil {
    /**
     * 编码常量
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 用于加密的根据 key
     * 
     *    key=u1u6c5c1p0
     * 
     */
    //private static String defalutKey = new ProperiesUtil("aes.properties").getProperty("defalutKey");
    private static String defalutKey = "u1u6c5c1p0";
    
    
    /**
     * AES字符
     */
    private static final String AES = "AES";
    /**
     * 初始化key
     */
    private static final int INITKEYNUM = 128;
    /**
     * 8位数的字节数组
     */
    private static final int ARRLENGTH = 8;
    /**
     * 相加数
     */
    private static final int ADDNUM = 256;
    /**
     * 比较数
     */
    private static final int COMPARENUM = 16;
    /**
     * encryptCipher
     */
    private Cipher encryptCipher = null;

    /**
     * decryptCipher
     */
    private Cipher decryptCipher = null;
    
    /**
     * 构造
     */
    public AESUtil() {
        this(defalutKey);
    }

    /**
     * 构造
     * @param key <br>
     */
    public AESUtil(String key) {
        KeyGenerator kgen;
        try {
            kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());// 实现各个操作操作系统产生相同的key
            kgen.init(INITKEYNUM, secureRandom);

            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keyTmp = new SecretKeySpec(enCodeFormat, AES);

            encryptCipher = Cipher.getInstance(AES);
            encryptCipher.init(Cipher.ENCRYPT_MODE, keyTmp);

            decryptCipher = Cipher.getInstance(AES);
            decryptCipher.init(Cipher.DECRYPT_MODE, keyTmp);
        } catch (Exception e) {
            throw new ParseException("some error when init AESUtil .", e);
        }
    }

    /**
     * Description: 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位 <br>
     * 
     * @author qss<br>
     * @taskId <br>
     * @param bytes 构成该字符串的字节数组<br>
     * @return 生成的密钥<br>
     */
    public Key getKey(byte[] bytes) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[ARRLENGTH];

        // 将原始字节数组转换为8位
        for (int i = 0; i < bytes.length && i < arrB.length; i++) {
            System.arraycopy(bytes, i, arrB, i, 1);
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, AES);

        return key;
    }

    /**
     * Description:加密字节数组 <br>
     * 
     * @author qss<br>
     * @taskId <br>
     * @param bytes 需加密的字节数组<br>
     * @return 加密后的字节数组<br>
     */
    public byte[] encrypt(byte[] bytes) {
        byte[] result = null;
        try {
            result =  encryptCipher.doFinal(bytes);
        } catch (Exception e) {
            throw new ParseException("some error when encrpt .", e);
        }
        return result;
    }

    /**
     * Description: 解密字节数组<br>
     * 
     * @author qss<br>
     * @taskId <br>
     * @param bytes 需解密的字节数组<br>
     * @return 解密后的字节数组 <br>
     */
    public byte[] decrypt(byte[] bytes) {
        byte[] result = null;
        try {
            result = decryptCipher.doFinal(bytes);
        } catch (Exception e) {
            throw new ParseException("some error when decrypt .", e);
        }
        
        return result;
    }

    /**
     * Description: 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] hexStr2ByteArr(String strIn) 互为可逆的转换过程 <br>
     * 
     * @author qss<br>
     * @taskId <br>
     * @param bytes 需要转换的byte数组<br>
     * @return 转换后的字符串<br>
     */
    public static String byteArr2HexStr(byte[] bytes) {
        int iLen = bytes.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = bytes[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + ADDNUM;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < COMPARENUM) {
                sb.append('0');
            }
            sb.append(Integer.toString(intTmp, COMPARENUM));
        }
        return sb.toString();
    }

    /**
     * Description: 将表示16进制值的字符串转换为byte数组.互为可逆的转换过程<br>
     * 
     * @author qss<br>
     * @taskId <br>
     * @param text 需要转换的字符串<br>
     * @return 转换后的byte数组<br>
     * @throws UnsupportedEncodingException 
     */
    public byte[] hexStr2ByteArr(String text) throws UnsupportedEncodingException {
        byte[] arrB = text.getBytes(UTF8);
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, COMPARENUM);
        }
        return arrOut;
    }

    /**
     * Description: 加密字符串<br>
     * 
     * @author qss<br>
     * @taskId <br>
     * @param content <br>
     * @return <br>
     */
    public static String encrypt(String content) {
        try {
            AESUtil aes = new AESUtil();
            return byteArr2HexStr(aes.encrypt(content.getBytes(UTF8)));
        } catch (Exception e) {
            throw new ParseException("some error when encrypt string ." ,e);
        }
        
    }

    /**
     * Description: 解密字符串 <br>
     * 
     * @author qss<br>
     * @taskId <br>
     * @param content 需解密的字符串<br>
     * @return <br>
     */
    public static String decrypt(String content) {
        
        byte[] bytes;
        try {
            AESUtil aes = new AESUtil();
            bytes = aes.decrypt(aes.hexStr2ByteArr(content));
        } catch (Exception e) { 
            throw new ParseException("some error when decrypt string ." , e);
        }
        
        if (null != bytes) {
            try {
                return new String(bytes, UTF8);
            } catch (UnsupportedEncodingException e) {
                throw new ParseException(e);
            }
        } else {
            return content;
        }
    }
}
