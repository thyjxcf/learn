/* 
 * @(#)SecurityUtils.java    Created on 2004-10-9
 * Copyright (c) 2004 ZDSoft Networks, Inc. All rights reserved.
 * $Id: SecurityUtils.java,v 1.49 2008/08/28 05:35:50 huangwj Exp $
 */
package net.zdsoft.keel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加解密工具类
 * 
 * @author liangxiao
 * @author huangwj
 * @version $Revision: 1.49 $, $Date: 2008/08/28 05:35:50 $
 */
public final class SecurityUtils {

    private static final char[] chs = { 'L', 'K', 'J', '4', 'D', 'G', 'F', 'V',
            'R', 'T', 'Y', 'B', 'N', 'U', 'P', 'W', '3', 'E', '5', 'H', 'M',
            '7', 'Q', '9', 'S', 'A', 'Z', 'X', '8', 'C', '6', '2' };

    // DES加密算法, 可用DES, DESede, Blowfish
    private static final String DES_ALGORITHM = "DESede";

    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    private SecurityUtils() {
    }

    /**
     * 自身混淆加密，最多只能加密30位长的字符串
     * 
     * @param source
     *            源字符串
     * @return 加密后字符串
     */
    public static String encodeBySelf(String source) {
        String pwdString = source;
        byte[] pwdBytes = pwdString.getBytes();

        /*
         * if ((pwdBytes.length%2)==0) { encodedBytes1 = new byte[pwdBytes.length];
         * encodedBytes2 = new byte[pwdBytes.length]; } else { encodedBytes1 = new
         * byte[pwdBytes.length+1]; encodedBytes2 = new byte[pwdBytes.length-1]; }
         */

        // encodedBytes1 = new byte[30];
        // encodedBytes2 = new byte[30];
        byte[] encodedBytes1 = new byte[30];
        byte[] encodedBytes2 = new byte[30];

        int n1 = 0, n2 = 0;
        for (int i = 0; i < pwdBytes.length; i++) {
            if ((i + 1) % 2 != 0) { // 奇数位
                encodedBytes1[n1++] = (byte) get32Hi(pwdBytes[i] * 4);
                // System.out.println(""+(int)pwdBytes[i]*4+":"+get32Hi((int)pwdBytes[i]*4));
                encodedBytes1[n1++] = (byte) get32Low(pwdBytes[i] * 4);
                // System.out.println(""+(int)pwdBytes[i]*4+":"+get32Low((int)pwdBytes[i]*4));
            }
            else { // 偶数位
                encodedBytes2[n2++] = (byte) get32Hi(pwdBytes[i] * 4);
                encodedBytes2[n2++] = (byte) get32Low(pwdBytes[i] * 4);
            }
        }

        while (n1 < 30)
            encodedBytes1[n1++] = (byte) getRandom(32);

        while (n2 < 30)
            encodedBytes2[n2++] = (byte) getRandom(32);

        int pos1 = getRandom(pwdBytes.length);
        int pos2 = getRandom(pwdBytes.length);
        // System.out.println(""+pos1+":"+pos2+"\n");
        sort(encodedBytes1, pos1);
        sort(encodedBytes2, pos2);
        int check = (sumSqual(encodedBytes1) + sumSqual(encodedBytes2)) % 32;

        byte[] encodedArray = new byte[64];
        encodedArray[0] = (byte) pos1;
        encodedArray[1] = (byte) pos2;
        System.arraycopy(encodedBytes1, 0, encodedArray, 2,
                encodedBytes1.length);
        System.arraycopy(encodedBytes2, 0, encodedArray,
                2 + encodedBytes1.length, encodedBytes2.length);
        // encodedArray[encodedArray.length - 2] = (byte) pwdLength;
        encodedArray[encodedArray.length - 2] = (byte) pwdString.length();
        encodedArray[encodedArray.length - 1] = (byte) check;
        byte[] ps = new byte[encodedArray.length];

        for (int i = 0; i < encodedArray.length; i++)
            ps[i] = (byte) chs[encodedArray[i]];

        return new String(ps);
    }

    /**
     * 自身混淆解密
     * 
     * @param str
     *            加密的字符串
     * @return 解密后字符串
     */
    public static String decodeBySelf(String str) {
        if (str == null || str.length() != 64) {
            return str;
        }

        byte[] sb = new byte[str.length()];
        byte[] bb = new byte[str.length()];

        sb = str.getBytes();

        for (int i = 0; i < sb.length; i++) {
            for (int j = 0; j < 32; j++) {
                if (((byte) chs[j]) == sb[i]) {
                    bb[i] = (byte) j;
                    break;
                }
            }
        }

        int sl = bb[bb.length - 2];
        int p1 = bb[0];
        int p2 = bb[1];

        byte[] bb1 = new byte[30];
        byte[] bb2 = new byte[30];

        // int bb1l;
        int bb2l;
        if (sl % 2 == 0) {
            // bb1l = sl;
            bb2l = sl;
        }
        else {
            // bb1l = sl + 1;
            bb2l = sl - 1;
        }

        /*
         * byte[] bb1,bb2; if (sl%2==0) { bb1=new byte[sl]; bb2=new byte[sl]; } else {
         * bb1=new byte[sl+1]; bb2=new byte[sl-1]; }
         */

        // System.out.println(""+bb1.length+":"+bb2.length);
        // System.out.println(""+p1+":"+p2);
        System.arraycopy(bb, 2, bb1, 0, bb1.length);
        System.arraycopy(bb, 2 + bb1.length, bb2, 0, bb2.length);

        unsort(bb1, p1);
        unsort(bb2, p2);
        byte[] oldb = new byte[sl];
        for (int i = 0; i < sl; i += 2) {
            oldb[i] = (byte) (getIntFrom32(bb1[i], bb1[i + 1]) / 4);
            if (i + 1 < bb2l)
                oldb[i + 1] = (byte) (getIntFrom32(bb2[i], bb2[i + 1]) / 4);
        }

        return new String(oldb);
    }

    private static int sumSqual(byte[] b) {
        int sum = 0;
        for (int i = 0; i < b.length; i++)
            sum += (int) Math.pow(b[i], 2);
        return sum;
    }

    private static int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    private static void sort(byte[] b, int pos) {
        if (pos < 0 || pos > b.length)
            System.out.println("pos is not validate");
        byte[] tmp = new byte[pos];
        System.arraycopy(b, 0, tmp, 0, pos);
        System.arraycopy(b, pos, b, 0, b.length - pos);
        System.arraycopy(tmp, 0, b, b.length - pos, pos);
    }

    private static void unsort(byte[] b, int pos) {
        if (pos < 0 || pos > b.length)
            System.out.println("pos is not validate");

        byte[] tmp = new byte[pos];
        System.arraycopy(b, b.length - pos, tmp, 0, pos);
        System.arraycopy(b, 0, b, pos, b.length - pos);
        System.arraycopy(tmp, 0, b, 0, pos);
    }

    private static int get32Low(int num) {
        return num % 32;
    }

    private static int get32Hi(int num) {
        return num / 32;
    }

    private static int getIntFrom32(int hi, int low) {
        return hi * 32 + low;
    }

    /**
     * 使用SHA1加密
     * 
     * @param str
     *            源字符串
     * @return 加密后字符串
     */
    public static String encodeBySHA1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            return StringUtils.toHexString(md.digest());
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not encodeBySHA1", e);
        }
    }

    /**
     * 使用MD5对字符串加密.
     * 
     * @param str
     *            源字符串
     * @return 加密后字符串
     */
    public static String encodeByMD5(String str) {
        return DigestUtils.md5Hex(str);
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(str.getBytes());
//            return StringUtils.toHexString(md.digest());
//        }
//        catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Could not encodeByMD5", e);
//        }
    }

    /**
     * 使用MD5对字节数组加密.
     * 
     * @param bytes
     *            源字符byte数组
     * @return 加密后字符串
     */
    public static String encodeByMD5(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(bytes);
//            return StringUtils.toHexString(md.digest());
//        }
//        catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Could not encodeByMD5", e);
//        }
    }

    /**
     * 使用3DES加密, 然后使用Base64编码.
     * 
     * @param str
     *            源字符串
     * @param key
     *            密钥
     * @return 加密后字符串
     */
    public static String encodeBy3DESAndBase64(String str, String key) {
        String encoded = null;

        try {
            byte[] rawkey = key.getBytes();
            DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance(DES_ALGORITHM);
            SecretKey deskey = keyfactory.generateSecret(keyspec);

            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] cipherText = cipher.doFinal(str.getBytes());
            encoded = new String(Base64.encodeBase64(cipherText));
        }
        catch (Exception e) {
            throw new RuntimeException("Could not encodeBy3DESAndBase64", e);
        }

        return encoded;
    }

    /**
     * 使用Base64解码, 然后使用3DES解密.
     * 
     * @param str
     *            加密的字符串
     * @param key
     *            密钥
     * @return 解密后字符串
     */
    public static String decodeBy3DESAndBase64(String str, String key) {
        String decoded = null;

        try {
            byte[] rawkey = key.getBytes();
            DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance(DES_ALGORITHM);
            SecretKey deskey = keyfactory.generateSecret(keyspec);

            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            byte[] clearText = cipher.doFinal(Base64.decodeBase64(str
                    .getBytes()));
            decoded = new String(clearText);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not decodeBy3DESAndBase64", e);
        }

        return decoded;
    }

    /**
     * 使用36进制解码
     * 
     * @param str
     *            编码的字符串
     * @return 解码后字符串
     */
    public static String decodeBy36Radix(String str) {
        int length = str.length();

        byte[] bytes = null;
        if (length % 11 == 0) {
            bytes = new byte[length / 11 * 7];
        }
        else {
            bytes = new byte[(length / 11 + 1) * 7];
        }

        int index = 0;
        int offset = 0;
        do {
            String sub = null;
            if (index + 11 < length) {
                sub = str.substring(index, index + 11);
            }
            else {
                sub = str.substring(index);
            }

            long l = Long.parseLong(sub, 36);
            for (int i = 0; i < 7; i++) {
                byte b = (byte) (l >> ((6 - i) * 8));

                if (b != 0) {
                    bytes[offset++] = b;
                }
            }
            index += 11;
        }
        while (index < length);

        return new String(bytes, 0, offset);
    }

    /**
     * 使用36进制进行编码
     * 
     * @param str
     *            源字符串
     * @return 编码后字符串
     */
    public static String encodeBy36Radix(String str) {
        byte[] bytes = str.getBytes();
        int index = 0;
        StringBuffer stringBuffer = new StringBuffer();
        boolean isSeven = true;

        do {
            byte[] longBytes = new byte[8];

            if (index + 7 < bytes.length) {
                System.arraycopy(bytes, index, longBytes, 1, 7);
            }
            else {
                int i = bytes.length - index;
                System.arraycopy(bytes, index, longBytes, 8 - i, i);
                isSeven = false;
            }

            long longval = toLong(longBytes);

            if (isSeven) {
                stringBuffer.append(StringUtils.enoughZero(Long.toString(
                        longval, 36), 11));
            }
            else {
                stringBuffer.append(Long.toString(longval, 36));
            }

            index += 7;
        }
        while (index < bytes.length);

        return stringBuffer.toString();
    }

    private static long toLong(byte[] bytes) {
        return ((((long) bytes[0] & 0xff) << 56)
                | (((long) bytes[1] & 0xff) << 48)
                | (((long) bytes[2] & 0xff) << 40)
                | (((long) bytes[3] & 0xff) << 32)
                | (((long) bytes[4] & 0xff) << 24)
                | (((long) bytes[5] & 0xff) << 16)
                | (((long) bytes[6] & 0xff) << 8) | (((long) bytes[7] & 0xff) << 0));
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encodeByMD5("admin2077996350611"));
        // System.out.println(SecurityUtils.decodeBySelf("D4WDUM7ELSHMUQYJ9TJFQSGLAHP9P8US8PDWL79M9MXRTLDER55FKPG7NQ2U3PRG"));

        String ipAddress = "125.120.148.41";
        System.out.println(ipAddress);
        System.out.println(SecurityUtils.encodeBySHA1(DateUtils
                .currentDate2StringByDay()
                + "admin" + ipAddress));

        //
        // System.out
        // .println((new BASE64Encoder()
        // .encode("schoolId=350107&email=lunawings@hotmail.com&act=modify&regionalism=330000"
        // .getBytes())));
        //
        // System.out
        // .println(new String(
        // new BASE64Decoder()
        // .decodeBuffer("c2Nob29sSWQ9MzMwMTE5JnNjaG9vbE5hbWU9vNLQo7ulwarR3cq+0afQoyZrZXk9ODYxSUgzNDVKTDYxJmlzVHJpYWw9MSZlbmREYXRlPTIwMDUtMTAtMzEmcmVnaW9uYWxpc209MzMwMTAw")));

        // System.out.println(encodeBy3DESAndBase64("12345678",
        // "/nq4RR0kASDRnd3Drp3nFtmo0sTB4lv0"));
        // System.out.println(decodeBy3DESAndBase64("CA9g9ryZ5vOj4InHOknP4Q==",
        // "/nq4RR0kASDRnd3Drp3nFtmo0sTB4lv0"));

        System.out
                .println(decodeBy36Radix("8vstf0uwtop7ptb2khgldd4ee4vhrj52n8c6776hke62ejdcwwov93nfltbmbjhlu8ck684k3r3g8467195h7pyt5px0psxo3n76i1nj4gynu92yeyqwzhwfo7sl2we41pv13sbq9jf22ya7t1j7yo0g30849bc1re5o01x3cg"));

        System.out
                .println(encodeBy36Radix("schoolId=350557&schoolName=霞浦县民族中学&key=3EITAU9LH962&isTrial=1&endDate=2006-07-15&regionalism=350921&act=modify"));

        String key = "/nq4RR0kASDRnd3Drp3nFtmo0sTB4lv0";
        String str = "25";

        final String algorithm = "DESede"; // 可用 DES,DESede,Blowfish

        byte[] rawkey = key.getBytes();
        DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(algorithm);
        SecretKey deskey = keyfactory.generateSecret(keyspec);

        Cipher c1 = Cipher.getInstance(algorithm);
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] cipherByte = c1.doFinal(str.getBytes());
        System.out.println("#" + StringUtils.toHexString(cipherByte));

        System.out.println(encodeByMD5("ZR289J4N1VXD16PP41V9F9EPYD4ZDB17" + 10
                + "username" + "wujb"));

        // System.out.println(encodeByMD5("wlnwyqmx"));

        // System.out.println(encodeBy3DESAndBase64("2", key));

        // for (int i = 0; i < 100; i++) {
        // String xxx = RandomUtils.getRandomStr(i + 1);
        // System.out.println(i);
        // if (!xxx.equals(decodeBySelf(encodeBySelf(xxx)))) {
        //
        // }
        // }
    }

}
