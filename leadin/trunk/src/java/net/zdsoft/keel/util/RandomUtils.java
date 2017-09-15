/* 
 * @(#)RandomUtils.java    Created on 2005-1-6
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Id: RandomUtils.java,v 1.7 2008/07/31 11:30:55 huangwj Exp $
 */
package net.zdsoft.keel.util;


/**
 * 生成随机数字、字符串的工具类.
 * 
 * @author liangxiao
 * @version $Revision: 1.7 $, $Date: 2008/07/31 11:30:55 $
 */
public final class RandomUtils {

    private RandomUtils() {
    }

    /**
     * 产生一个固定范围的随机正整数
     * 
     * @param min
     *            最小值
     * @param max
     *            最大值
     * @return 随机整数
     */
    public static int getRandomInt(int min, int max) {
        return (int) (Math.random() * max + min);
    }

    /**
     * 产生固定长度的随机数字串
     * 
     * @param length
     *            长度
     * @return 随机数字串
     */
    public static String getRandomNum(int length) {
        return (Double.toString(Math.random())).substring(2, (2 + length));
    }

    /**
     * 产生固定长度的随机字母数字串
     * 
     * @param length
     *            长度
     * @return 随机字母数字串
     */
    public static String getRandomStr(int length) {
        String allChars = "123456789ABCDEFGHJKLMNPQRSTUVXYZ123456789";
        char[] randomBytes = new char[length];

        for (int i = 0; i < length; i++) {
            randomBytes[i] = allChars.charAt(getRandomInt(0,
                    allChars.length() - 1));
        }
        return new String(randomBytes);
    }

    public static long getRandomInt(int length) {
        long ret = 0;
        for (int i = 0; i < length; i++) {
            int r = org.apache.commons.lang.math.RandomUtils.nextInt(9);
            ret += (r == 0 ? 1 : r) * Math.pow(10, i);
        }
        return ret;
    }
    
    public static void main(String[] args) {
        System.out.println(getRandomStr(32));
    }

}
