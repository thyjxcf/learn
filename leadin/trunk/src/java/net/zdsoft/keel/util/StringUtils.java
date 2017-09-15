/*
 * @(#)StringUtils.java    Created on 2004-10-9
 * Copyright (c) 2004 ZDSoft.net, Inc. All rights reserved.
 * $Id: StringUtils.java,v 1.55 2008/07/31 11:37:29 huangwj Exp $
 */
package net.zdsoft.keel.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.keel.util.helper.PairKeyword;
import net.zdsoft.keel.util.helper.PairKeywordComparator;

/**
 * 字符串工具类.
 * 
 * @author liangxiao
 * @author yukh
 * @author xup
 * @author huangwj
 * @version $Revision: 1.55 $, $Date: 2008/07/31 11:37:29 $
 */
public final class StringUtils {
    private static final Logger log = LoggerFactory.getLogger(StringUtils.class);
    
    public static final String SEPARATOR_MULTI = ";";
    public static final String SEPARATOR_SINGLE = "#";
    public static final String SQL_REPLACE = "_";

    /**
     * 在str右边加入足够多的addStr字符串
     * 
     * @param str
     * @param addStr
     * @param length
     * @return
     */
    public static String addStringRight(String str, String addStr, int length) {
        while (str.length() < length) {
            str = str + addStr;
        }
        return str;
    }

    /**
     * 在字符串str拼接分隔符regex和字符串sub
     * 
     * @param str
     * @param sub
     * @param regex
     * @return
     */
    public static String addSubString(String str, String sub, String regex) {
        return Validators.isEmpty(str) ? sub : str + regex + sub;
    }

    /**
     * 在字符串str右边补齐0直到长度等于length
     * 
     * @param str
     * @param length
     * @return
     */
    public static String addZeroRight(String str, int length) {
        return addStringRight(str, "0", length);
    }

    /**
     * 计算字符串str中字符sub的个数
     * 
     * @param str
     * @param sub
     * @return
     */
    public static int charCount(String str, char sub) {
        int charCount = 0;
        int fromIndex = 0;

        while ((fromIndex = str.indexOf(sub, fromIndex)) != -1) {
            fromIndex++;
            charCount++;
        }
        return charCount;
    }

    /**
     * 计算字符串str右边出现多少次sub
     * 
     * @param str
     * @param sub
     * @return
     */
    public static int charCountRight(String str, String sub) {
        if (str == null) {
            return 0;
        }

        int charCount = 0;
        String subStr = str;
        int currentLength = subStr.length() - sub.length();
        while (currentLength >= 0
                && subStr.substring(currentLength).equals(sub)) {
            charCount++;
            subStr = subStr.substring(0, currentLength);
            currentLength = subStr.length() - sub.length();
        }
        return charCount;
    }

    /**
     * <p>
     * Counts how many times the substring appears in the larger String.
     * </p>
     * 
     * <p>
     * A <code>null</code> or empty ("") String input returns <code>0</code>.
     * </p>
     * 
     * <pre>
     *   StringUtils.countMatches(null, *)                           = 0
     *   StringUtils.countMatches(&quot;&quot;, *)                   = 0
     *   StringUtils.countMatches(&quot;abba&quot;, null)            = 0
     *   StringUtils.countMatches(&quot;abba&quot;, &quot;&quot;)    = 0
     *   StringUtils.countMatches(&quot;abba&quot;, &quot;a&quot;)   = 2
     *   StringUtils.countMatches(&quot;abba&quot;, &quot;ab&quot;)  = 1
     *   StringUtils.countMatches(&quot;abba&quot;, &quot;xxx&quot;) = 0
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @param sub
     *            the substring to count, may be null
     * @return the number of occurrences, 0 if either String is <code>null</code>
     */
    public static int countMatches(String str, String sub) {
        if (Validators.isEmpty(str) || Validators.isEmpty(sub)) {
            return 0;
        }

        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * 截取固定长度的字符串，剩余部分真实长度不会超过len，超长部分用suffix代替.
     * 
     * @param str
     * @param len
     * @param suffix
     * @return
     * @deprecated 请使用cutOut()方法, 格式会更整齐.
     */
    public static String cutOff(String str, int len, String suffix) {
        if (Validators.isEmpty(str)) {
            return str;
        }

        int byteIndex = 0;
        int charIndex = 0;

        while (charIndex < str.length() && byteIndex < len) {
            if (str.charAt(charIndex) >= 256) {
                byteIndex += 2;
            }
            else {
                byteIndex++;
            }

            charIndex++;
        }
        return byteIndex < len ? str.substring(0, charIndex) : str.substring(0,
                charIndex)
                + suffix;
    }

    /**
     * 截取固定长度的字符串，超长部分用suffix代替，最终字符串真实长度不会超过maxLength.
     * 
     * @param str
     * @param maxLength
     * @param suffix
     * @return
     */
    public static String cutOut(String str, int maxLength, String suffix) {
        if (Validators.isEmpty(str)) {
            return str;
        }

        int byteIndex = 0;
        int charIndex = 0;

        while (charIndex < str.length() && byteIndex <= maxLength) {
            char c = str.charAt(charIndex);
            if (c >= 256) {
                byteIndex += 2;
            }
            else {
                byteIndex++;
            }
            charIndex++;
        }

        if (byteIndex <= maxLength) {
            return str;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, charIndex));
        stringBuffer.append(suffix);

        while (getRealLength(stringBuffer.toString()) > maxLength) {
            stringBuffer.deleteCharAt(--charIndex);
        }

        return stringBuffer.toString();
    }

    /**
     * 在字符串str左边补齐0直到长度等于length
     * 
     * @param str
     * @param len
     * @return
     */
    public static String enoughZero(String str, int len) {
        while (str.length() < len) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 用来显示异常信息的html过滤器
     * 
     * @param value
     * @return
     */
    public static String exceptionFilter(String value) {
        return Validators.isEmpty(value) ? "" : value.replaceAll("\n", "<br>")
                .replaceAll("\t", "&nbsp; &nbsp; ");
    }

    /**
     * @param text
     *            将要被格式化的字符串 <br>
     *            eg:参数一:{0},参数二:{1},参数三:{2}
     * 
     * @param args
     *            将替代字符串中的参数,些参数将替换{X} <br>
     *            eg:new Object[] { "0001", "0005049", new Integer(1) }
     * @return 格式化后的字符串 <br>
     *         eg: 在上面的输入下输出为:参数一:0001,参数二:0005049,参数三:1
     */
    public static String format(String text, Object[] args) {
        if (Validators.isEmpty(text) || args == null || args.length == 0) {
            return text;
        }
        for (int i = 0, length = args.length; i < length; i++) {
            text = replace(text, "{" + i + "}", args[i].toString());
        }
        return text;
    }

    /**
     * 格式化浮点型数字成字符串, 保留两位小数位.
     * 
     * @param number
     *            浮点数字
     * @return 格式化之后的字符串
     */
    public static String formatDecimal(double number) {
        NumberFormat format = NumberFormat.getInstance();

        format.setMaximumIntegerDigits(Integer.MAX_VALUE);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        return format.format(number);
    }

    /**
     * 格式化浮点类型数据.
     * 
     * @param number
     *            浮点数据
     * @param minFractionDigits
     *            最小保留小数位
     * @param maxFractionDigits
     *            最大保留小数位
     * @return 将浮点数据格式化后的字符串
     */
    public static String formatDecimal(double number, int minFractionDigits,
            int maxFractionDigits) {
        NumberFormat format = DecimalFormat.getInstance();
        format.setMinimumFractionDigits(minFractionDigits);
        format.setMaximumFractionDigits(minFractionDigits);

        return format.format(number);
    }

    /**
     * 取得字符串的真实长度，一个汉字长度为2
     * 
     * @param str
     * @return
     */
    public static int getRealLength(String str) {
        if (str == null) {
            return 0;
        }

        char separator = 256;
        int realLength = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= separator) {
                realLength += 2;
            }
            else {
                realLength++;
            }
        }
        return realLength;
    }

    /**
     * html文本过滤, 如果value为 <code>null</code> 或为空串, 则返回"&nbsp;". 对于空格字符, 转化为"&nbsp".
     * (适用于显示普通文本)
     * 
     * @param value
     *            要过滤的文本
     * @return 过滤后的html文本
     */
    public static String htmlFilter(String value) {
        if (Validators.isEmpty(value)) {
            return "&nbsp;";
        }

        return value.replaceAll("&", "&amp;").replaceAll(" ", "&nbsp;")
                .replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll(
                        "\"", "&quot;").replaceAll("\n", "<br>");
    }

    /**
     * html文本过滤, 如果value为 <code>null</code> 或为空串, 则返回空串. 对于空格字符, 不转化为"&nbsp;".
     * (适用于显示文本框中的值)
     * 
     * @param value
     *            要过滤的文本
     * @return 过滤后的html文本
     */
    public static String htmlFilterToEmpty(String value) {
        if (Validators.isEmpty(value)) {
            return "";
        }

        return value.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
    }

    /**
     * 忽略值为 <code>null</code> 的字符串
     * 
     * @param str
     *            字符串
     * @return 如果字符串为 <code>null</code>, 则返回空字符串.
     */
    public static String ignoreNull(String str) {
        return str == null ? "" : str;
    }

    /**
     * 只否包括"\""等不利于文本框显示的字符
     * 
     * @param arg
     * @return
     */
    public static boolean isNotAllowed4TextBox(String arg) {
        if (Validators.isEmpty(arg)) {
            return false;
        }

        return arg.indexOf("\"") >= 0;
    }

    /**
     * 过滤html的"'"字符(转义为"\'")以及其他特殊字符, 主要用于链接地址的特殊字符过滤.
     * 
     * @param str
     *            要过滤的字符串
     * @return 过滤后的字符串
     */
    public static String linkFilter(String str) {
        if (Validators.isEmpty(str)) {
            return str;
        }

        return htmlFilter(htmlFilter(str.replaceAll("'", "\\\\'")));
    }

    /**
     * <p>
     * Replaces all occurrences of a String within another String.
     * </p>
     */
    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    /**
     * <p>
     * Replaces a String with another String inside a larger String, for the first
     * <code>max</code> values of the search String.
     */
    public static String replace(String text, String repl, String with, int max) {
        if (text == null || Validators.isEmpty(repl) || with == null
                || max == 0) {
            return text;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int start = 0, end = 0;
        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * 清除字符串左侧的空格
     * 
     * @param str
     * @return
     */
    public static String ltrim(String str) {
        return ltrim(str, " ");
    }

    /**
     * 清除字符串左侧的指定字符串
     * 
     * @param str
     * @param remove
     * @return
     */
    public static String ltrim(String str, String remove) {
        if (str == null || str.length() == 0 || remove == null
                || remove.length() == 0) {
            return str;
        }

        while (str.startsWith(remove)) {
            str = str.substring(remove.length());
        }
        return str;
    }

    /**
     * 清除字符串右侧的空格
     * 
     * @param str
     * @return
     */
    public static String rtrim(String str) {
        return rtrim(str, " ");
    }

    /**
     * 清除字符串右侧的指定字符串
     * 
     * @param str
     * @param remove
     * @return
     */
    public static String rtrim(String str, String remove) {
        if (str == null || str.length() == 0 || remove == null
                || remove.length() == 0) {
            return str;
        }

        while (str.endsWith(remove) && (str.length() - remove.length()) >= 0) {
            str = str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    /**
     * 把字符串按照规则分割，比如str为“id=123&name=test”，rule为“id=#&name=#”，分隔后为["123", "test"];
     * 
     * @param str
     * @param rule
     * @return
     */
    public static String[] split(String str, String rule) {
        if (rule.indexOf(SEPARATOR_SINGLE) == -1
                || rule.indexOf(SEPARATOR_SINGLE + SEPARATOR_SINGLE) != -1) {
            throw new IllegalArgumentException("Could not parse rule");
        }

        String[] rules = rule.split(SEPARATOR_SINGLE);
        // System.out.println(rules.length);

        if (str == null || str.length() < rules[0].length()) {
            return new String[0];
        }

        boolean endsWithSeparator = rule.endsWith(SEPARATOR_SINGLE);

        String[] strs = new String[endsWithSeparator ? rules.length
                : rules.length - 1];
        if (rules[0].length() > 0 && !str.startsWith(rules[0])) {
            return new String[0];
        }

        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < strs.length; i++) {
            startIndex = str.indexOf(rules[i], endIndex) + rules[i].length();
            if (i + 1 == strs.length && endsWithSeparator) {
                endIndex = str.length();
            }
            else {
                endIndex = str.indexOf(rules[i + 1], startIndex);
            }

            // System.out.println(startIndex + "," + endIndex);

            if (startIndex == -1 || endIndex == -1) {
                return new String[0];
            }
            strs[i] = str.substring(startIndex, endIndex);
        }

        return strs;
    }

    /**
     * 替换sql like的字段中的通配符，包括[]%_
     * 
     * @param str
     * @return
     */
    public static String sqlWildcardFilter(String str) {
        if (Validators.isEmpty(str)) {
            return str;
        }

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '[') {
                stringBuffer.append("[[]");
            }
            else if (c == ']') {
                stringBuffer.append("[]]");
            }
            else if (c == '%') {
                stringBuffer.append("[%]");
            }
            else if (c == '_') {
                stringBuffer.append("[_]");
            }
            else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 把字符串按照指定的字符集进行编码
     * 
     * @param str
     * @param charSetName
     * @return
     */
    public static String toCharSet(String str, String charSetName) {
        try {
            return new String(str.getBytes(), charSetName);
        }
        catch (UnsupportedEncodingException e) {
            log.error(e.toString());            
            return str;
        }
    }

    /**
     * 把一个字节数组转换为16进制表达的字符串
     * 
     * @param bytes
     * @return
     */
    public static String toHexString(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            hexString
                    .append(enoughZero(Integer.toHexString(bytes[i] & 0xff), 2));
        }
        return hexString.toString();
    }

    /**
     * 把16进制表达的字符串转换为整数
     * 
     * @param hexString
     * @return
     */
    public static int hexString2Int(String hexString) {
        return Integer.valueOf(hexString, 16).intValue();
    }

    /**
     * 将以ASCII字符表示的16进制字符串以每两个字符分割转换为16进制表示的byte数组.<br>
     * e.g. "e024c854" --> byte[]{0xe0, 0x24, 0xc8, 0x54}
     * 
     * @param src
     *            原16进制字符串
     * @return 16进制表示的byte数组
     */
    public static byte[] hexString2Bytes(String str) {
        if (Validators.isEmpty(str)) {
            return null;
        }

        if (str.length() % 2 != 0) {
            str = "0" + str;
        }

        byte[] result = new byte[str.length() / 2];
        for (int i = 0; i < result.length; i++) {
            // High bit
            byte high = (byte) (Byte.decode("0x" + str.charAt(i * 2))
                    .byteValue() << 4);
            // Low bit
            byte low = Byte.decode("0x" + str.charAt(i * 2 + 1)).byteValue();
            result[i] = (byte) (high ^ low);
        }
        return result;
    }

    /**
     * 清除字符串两边的空格，null不处理
     * 
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? str : str.trim();
    }

    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String
     * returning an empty String ("") if the String is empty ("") after the trim or
     * if it is <code>null</code>.
     * </p>
     * 
     * @param str
     *            the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if <code>null</code> input
     */
    public static String trimToEmpty(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 清除字符串中的回车和换行符
     * 
     * @param str
     * @return
     */
    public static String ignoreEnter(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        return str.replaceAll("\r|\n", "");
    }

    /**
     * 清除下划线，把下划线后面字母转换成大写字母
     * 
     * @param str
     * @return
     */
    public static String underline2Uppercase(String str) {
        if (Validators.isEmpty(str)) {
            return str;
        }

        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '_' && i < charArray.length - 1) {
                charArray[i + 1] = Character.toUpperCase(charArray[i + 1]);
            }
        }

        return new String(charArray).replaceAll("_", "");
    }

    /**
     * 获得成对出现的第一个关键字对应的关键字的位置
     * 
     * @param str
     * @param keyword
     *            关键字，例如：select
     * @param oppositeKeyword
     *            对应的关键字，例如：from
     * @return 第一个关键字对应的关键字的位置
     */
    public static int getFirstPairIndex(String str, String keyword,
            String oppositeKeyword) {
        ArrayList<PairKeyword> keywordArray = new ArrayList<PairKeyword>();
        int index = -1;
        while ((index = str.indexOf(keyword, index)) != -1) {
            keywordArray.add(new PairKeyword(keyword, index));
            index += keyword.length();
        }

        index = -1;
        while ((index = str.indexOf(oppositeKeyword, index)) != -1) {
            keywordArray.add(new PairKeyword(oppositeKeyword, index));
            index += oppositeKeyword.length();
        }

        if (keywordArray.size() < 2) {
            return -1;
        }

        Collections.sort(keywordArray, new PairKeywordComparator());

        PairKeyword firstKeyword = (PairKeyword) keywordArray.get(0);
        if (!firstKeyword.getName().equals(keyword)) {
            return -1;
        }

        while (keywordArray.size() > 2) {
            boolean hasOpposite = false;
            for (int i = 2; i < keywordArray.size(); i++) {
                PairKeyword keyword0 = (PairKeyword) keywordArray.get(i - 1);
                PairKeyword keyword1 = (PairKeyword) keywordArray.get(i);
                if (keyword0.getName().equals(keyword)
                        && keyword1.getName().equals(oppositeKeyword)) {
                    keywordArray.remove(i);
                    keywordArray.remove(i - 1);
                    hasOpposite = true;
                    break;
                }
            }
            if (!hasOpposite) {
                return -1;
            }
        }

        if (keywordArray.size() != 2) {
            return -1;
        }

        PairKeyword lastKeyword = (PairKeyword) keywordArray.get(1);
        if (!lastKeyword.getName().equals(oppositeKeyword)) {
            return -1;
        }

        return lastKeyword.getIndex();
    }

    private StringUtils() {
    }

    public static void main(String[] args) {
        System.out.println(sqlWildcardFilter("[[中文test]]"));
        System.out.println(split("id=123&name=test", "id=#&name=#")[0]);
        System.out.println(cutOff("寂寞沙洲", 8, "…"));
        System.out.println(cutOut("寂寞沙州", 8, "…"));
        System.out.println(RandomUtils.getRandomStr(32));
        System.out.println(toHexString(hexString2Bytes("854")));
    }

    //==============2010-10-29 合并关于字符串的工具类===========================
    private static Character maxChar = "Z".charAt(0);

    private static Character minChar = "A".charAt(0);

    /**
     * 获取下一个字母顺序的字符,如果已达到最大字符如"ZZZ",则不超越长度而是再次从"AAA"开始 该方法暂只处理长度为2的字符串
     * 
     * @param str
     * @return
     */
    public static String getNextUpCaseChar(String str) {
        if (str == null) {
            return null;
        }

        Character c = null;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (!Character.isUpperCase(c)) {
                c = Character.toUpperCase(c);
            }
        }

        str = StringUtils.upChar(str, str.length() - 1);
        return str;
    }

    /**
     * 将指定index处的str字符在A~Z的区域内循环加1, 但不超过原str的长度,超过则从aaa循环开始
     * 
     * @param str
     * @param index
     * @return
     */
    public static String upChar(String str, int index) {
        StringBuffer sb = new StringBuffer(str);
        if (str == null || index >= str.length()) {
            return str;
        }
        Character c = sb.charAt(index);
        c = Character.toUpperCase(c);
        if (c == StringUtils.maxChar) {
            sb.setCharAt(index, StringUtils.minChar);
            if (index == 0) {
                return sb.toString();
            } else {
                sb = new StringBuffer(upChar(sb.toString(), --index));
            }
        } else {
            sb.setCharAt(index, ++c);
        }
        return sb.toString();
    }

    /**
     * 生成counts个symbol字符组成的字符串
     * 
     * @param symbol
     * @param counts
     * @return
     */
    public static String createCountsSymbol(String symbol, Integer counts) {
        if (symbol == null || counts == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < counts; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    /**
     * 根据入学学年和学制得到毕业学年（格式为：2006-2007）
     * 
     * @param acadyear 入学学年 2004-2005
     * @param schoolinglen 学制 3
     * @return String
     */
    public static String calAcadyear(String acadyear, int schoolinglen) {
        if (org.apache.commons.lang.StringUtils.isBlank(acadyear) || acadyear.length() != 9
                || schoolinglen < 1)
            return "";

        String tempStartYear = acadyear.substring(0, 4);
        String tempEndYear = acadyear.substring(5);
        String startYear = String.valueOf(Integer.parseInt(tempStartYear) + (schoolinglen - 1));
        String endYear = String.valueOf(Integer.parseInt(tempEndYear) + (schoolinglen - 1));

        return startYear + "-" + endYear;
    }


}
