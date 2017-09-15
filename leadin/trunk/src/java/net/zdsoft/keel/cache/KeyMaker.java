/*
 * Created on 2004-10-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.cache;

/**
 * 键的生成工具，用来生成放到缓存中的键，分隔符为Tab键
 * 
 * @author liangxiao
 * @version $Revision: 1.5 $, $Date: 2007/01/11 03:59:32 $
 */
public class KeyMaker {
    private static final String SEPARATOR = "\t";

    /**
     * 构造方法
     */
    public KeyMaker() {
    }

    /**
     * 把两个字符串组成一个键
     * 
     * @param arg1
     *            字符串1
     * @param arg2
     *            字符串2
     * @return 键
     */
    public static String getKey(String arg1, String arg2) {
        StringBuffer key = new StringBuffer();
        key.append(arg1);
        key.append(SEPARATOR);
        key.append(arg2);
        return key.toString();
    }

    /**
     * 把三个字符串组成一个键
     * 
     * @param arg1
     *            字符串1
     * @param arg2
     *            字符串2
     * @param arg3
     *            字符串3
     * @return 键
     */
    public static String getKey(String arg1, String arg2, String arg3) {
        StringBuffer key = new StringBuffer();
        key.append(arg1);
        key.append(SEPARATOR);
        key.append(arg2);
        key.append(SEPARATOR);
        key.append(arg3);
        return key.toString();
    }

    /**
     * 把字符串数组的内容组成一个键
     * 
     * @param args
     *            字符串数组
     * @return 键
     */
    public static String getKey(String[] args) {
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                key.append(SEPARATOR);
            }
            key.append(args[i]);
        }
        return key.toString();
    }

    /**
     * 分割键成为字符串数组
     * 
     * @param key
     *            键
     * @return 字符串数组
     */
    public static String[] splitKey(String key) {
        return key.split(SEPARATOR);
    }

    public static void main(String[] args) {
        System.out.println(getKey(new String[] { "a", "b", "c" }));
        String[] s = splitKey(getKey(new String[] { "a", "b", "c" }));
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }
    }
}