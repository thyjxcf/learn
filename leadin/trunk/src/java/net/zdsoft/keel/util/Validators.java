/* 
 * @(#)Validators.java    Created on 2004-10-10
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/Validators.java,v 1.39 2008/09/09 12:33:42 yeq Exp $
 */
package net.zdsoft.keel.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.zdsoft.keel.util.helper.TabooedWords;

/**
 * 对字符串按照常用规则进行验证的工具类.
 * 
 * @author liangxiao
 * @author huangwj
 * @author yukh
 * @version $Revision: 1.39 $, $Date: 2008/09/09 12:33:42 $
 */
public final class Validators {

    /**
     * 过滤词汇表
     */
    private static TabooedWords tabooedWords = new TabooedWords();

    /**
     * 判断字符串是否只包含字母和数字.
     * 
     * @param str
     *            字符串
     * @return 如果字符串只包含字母和数字, 则返回 <code>true</code>, 否则返回 <code>false</code>.
     */
    public static boolean isAlphanumeric(String str) {
        if (Validators.isEmpty(str)) {
            return false;
        }

        String regex = "[a-zA-Z0-9]+";
        return Pattern.matches(regex, str);
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     * 
     * <pre>
     *   StringUtils.isBlank(null)                = true
     *   StringUtils.isBlank(&quot;&quot;)        = true
     *   StringUtils.isBlank(&quot; &quot;)       = true
     *   StringUtils.isBlank(&quot;bob&quot;)     = false
     *   StringUtils.isBlank(&quot;  bob  &quot;) = false
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为中国移动手机号码.
     * 
     * @param str
     *            字符串
     * @return 如果是移动号码, 返回 <code>true</code>, 否则返回 <code>false</code>.
     */
    public static boolean isChinaMobile(String str) {
        if (Validators.isEmpty(str)) {
            return false;
        }

        // Regex for checking ChinaMobile
        String regex = "1(3[4-9]|5[01789])\\d{8}";
        return Pattern.matches(regex, str);
    }

    /**
     * 判断是否为小灵通手机(Personal Access Phone System, PAS).
     * 
     * @param str
     *            字符串
     * @return 如果是小灵通号码, 返回 <code>true</code>, 否则返回 <code>false</code>.
     */
    public static boolean isChinaPAS(String str) {
        if (Validators.isEmpty(str)) {
            return false;
        }

        // Deal with 00 & 013 & 015
        if (str.startsWith("00") || str.startsWith("013")
                || str.startsWith("015")) {
            return false;
        }

        // Regex for checking PAS
        String regex = "0\\d{9,11}";
        return Pattern.matches(regex, str);
    }

    /**
     * 是否为中国联通手机号码.
     * 
     * @param str
     *            字符串
     * @return 如果是联通号码, 返回 <code>true</code>, 否则返回 <code>false</code>.
     */
    public static boolean isChinaUnicom(String str) {
        if (Validators.isEmpty(str)) {
            return false;
        }

        // Regex for checking ChinaUnicom
        String regex = "1(3[0-3]|5[356])\\d{8}";
        return Pattern.matches(regex, str);
    }

    /**
     * 是否是合法的日期字符串
     * 
     * @param str
     *            日期字符串
     * @return 是true，否则false
     */
    public static boolean isDate(String str) {
		if (isEmpty(str) || str.length() > 10) {
            return false;
        }
        
        List<String> listOfPatten = new ArrayList<String>();
        listOfPatten.add("yyyy-MM-dd");
        listOfPatten.add("yyyy/MM/dd");
        listOfPatten.add("yyyy/M/d");
        listOfPatten.add("yyyy-M-d");
        listOfPatten.add("yyyyMMdd");
        
		try {
			org.apache.commons.lang.time.DateUtils.parseDateStrictly(str, listOfPatten.toArray(new String[0]));
			return true;
		} catch (ParseException e) {
		}
			
       	return false;
    }

    /**
     * 是否是合法的日期时间字符串
     * 
     * @param str
     *            日期时间字符串
     * @return 是true，否则false
     */
    public static boolean isDateTime(String str) {
        if (isEmpty(str) || str.length() > 20) {
            return false;
        }

        String[] items = str.split(" ");

        if (items.length != 2) {
            return false;
        }

        return isDate(items[0]) && isTime(items[1]);
    }

    /**
     * 是否是合法的电子邮箱
     * 
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        return !isEmpty(str) && str.indexOf("@") > 0;
    }

    /**
     * 当数组为<code>null</code>, 或者长度为0, 或者长度为1且元素的值为<code>null</code>时返回
     * <code>true</code>.
     * 
     * @param args
     * @return
     */
    public static boolean isEmpty(Object[] args) {
        return args == null || args.length == 0
                || (args.length == 1 && args[0] == null);
    }

    /**
     * 字符串是否为Empty，null和空格都算是Empty
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * <p>
     * Validating for ID card number.
     * </p>
     * 
     * @param str
     *            string to be validated
     * @return If the str is valid ID card number return <code>true</code>,
     *         otherwise return <code>false</code>.
     */
    public static boolean isIdCardNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }

        // Regex for checking ID card number
        // 15位或18数字, 14数字加x(X)字符或17数字加x(X)字符才是合法的
        String regex = "(\\d{14}|\\d{17})(\\d|x|X)";
        return Pattern.matches(regex, str);
    }

    /**
     * 是否为手机号码, 包括移动, 联通, 小灵通等手机号码.
     * 
     * @param str
     *            字符串
     * @return 若是合法的手机号码返回 <code>true</code>, 否则返回 <code>false</code>.
     */
    public static boolean isMobile(String str) {
        if (Validators.isEmpty(str)) {
            return false;
        }

        // Regex for Mobile
        String regex = "(13\\d{9})|(0\\d{9,11})|(15\\d{9})";
        return !str.startsWith("00") && !str.startsWith("013")
                && !str.startsWith("015") && Pattern.matches(regex, str);
    }

    /**
     * 是否为数字的字符串
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > '9' || str.charAt(i) < '0') {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是固定范围内的数字的字符串
     * 
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isNumber(String str, int min, int max) {
        if (!isNumber(str)) {
            return false;
        }

        int number = Integer.parseInt(str);
        return number >= min && number <= max;
    }

    /**
     * 判断字符是否为整数或浮点数. <br>
     * 
     * @param str
     *            字符
     * @return 若为整数或浮点数则返回 <code>true</code>, 否则返回 <code>false</code>
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }

        String regex = "(\\+|-){0,1}(\\d+)([.]?)(\\d*)"; // 整数或浮点数
        return Pattern.matches(regex, str);
    }

    /**
     * 判断字符是否为符合精度要求的整数或浮点数. <br>
     * 
     * @param str
     *            字符
     * @param fractionNum
     *            小数部分的最多允许的位数
     * @return 若为整数或浮点数则返回 <code>true</code>, 否则返回 <code>false</code>
     */
    public static boolean isNumeric(String str, int fractionNum) {
        if (isEmpty(str)) {
            return false;
        }

        // 整数或浮点数
        String regex = "(\\+|-){0,1}(\\d+)([.]?)(\\d{0," + fractionNum + "})";
        return Pattern.matches(regex, str);
    }
    
    /**
     * 判断字符是否为符合精度要求的非负整数或浮点数. <br>
     * 
     * @param str
     *            字符
     * @param fractionNum
     *            小数部分的最多允许的位数
     * @return 若为整数或浮点数则返回 <code>true</code>, 否则返回 <code>false</code>
     */
    public static boolean isNonNegativeNumeric(String str, int fractionNum) {
        if (isEmpty(str)) {
            return false;
        }

        // 整数或浮点数
        String regex = "(\\+){0,1}(\\d+)([.]?)(\\d{0," + fractionNum + "})";
        return Pattern.matches(regex, str);
    }

    /**
     * <p>
     * Validating for phone number.
     * </p>
     * e.g.
     * <li>78674585 --> valid</li>
     * <li>6872-4585 --> valid</li>
     * <li>(6872)4585 --> valid</li>
     * <li>0086-10-6872-4585 --> valid</li>
     * <li>0086-(10)-6872-4585 --> invalid</li>
     * <li>0086(10)68724585 --> invalid</li>
     * 
     * @param str
     *            string to be validated
     * @return If the str is valid phone number return <code>true</code>,
     *         otherwise return <code>false</code>.
     */
    public static boolean isPhoneNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }

        // Regex for checking phone number
        String regex = "(([\\(（]\\d+[\\)）])?|(\\d+[-－]?)*)\\d+";
        return Pattern.matches(regex, str);
    }

    /**
     * 判断是否是合法的邮编
     * 
     * @param str
     * @return
     */
    public static boolean isPostcode(String str) {
        if (isEmpty(str)) {
            return false;
        }

        if (str.length() != 6 || !Validators.isNumber(str)) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否是固定长度范围内的字符串
     * 
     * @param str
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean isString(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }

        if (minLength < 0) {
            return str.length() <= maxLength;
        }
        else if (maxLength < 0) {
            return str.length() >= minLength;
        }
        else {
            return str.length() >= minLength && str.length() <= maxLength;
        }
    }

    /**
     * 判断是否是合法的时间字符串
     * 
     * @param str
     * @return
     */
    public static boolean isTime(String str) {
        if (isEmpty(str) || str.length() > 8) {
            return false;
        }

        String[] items = str.split(":");

        if (items.length != 2 && items.length != 3) {
            return false;
        }

        for (int i = 0; i < items.length; i++) {
            if (items[i].length() != 2 && items[i].length() != 1) {
                return false;
            }
        }

        return !(!isNumber(items[0], 0, 23) || !isNumber(items[1], 0, 59) || (items.length == 3 && !isNumber(
                items[2], 0, 59)));
    }

    /**
     * 判断是否包含敏感词汇，敏感词汇定义在tabooed.words里
     * 
     * @param str
     * @return
     */
    public static boolean isTabooed(String str) {
        return tabooedWords.isTabooed(str);
    }

    private Validators() {
    }

}
