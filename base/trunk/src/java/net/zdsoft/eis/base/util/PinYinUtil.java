package net.zdsoft.eis.base.util;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;

public class PinYinUtil {
        
    /**
     * 传入汉字字符串，拼接成对应的拼音,返回拼音的集合
     */
    public static String getPinYinSet(String src){
        Set<String> lstResult = new HashSet<String>();
        char[] t1 = null;  //字符串转换成char数组
        t1 = src.toCharArray();
        //①迭代汉字
        for(char ch : t1){  
        	//过滤不是中文的
        	if(!Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")){
        		continue;
        	}
    		Set<String> s = getPinYin(ch);
            Set<String> lstNew = new HashSet<String>();
            //②迭代每个汉字的拼音数组
            for(String str : s){
                if(lstResult.size()==0){
                        lstNew.add(str);
                }else{
                    for(String ss : lstResult){
                            ss += str;
                            lstNew.add(ss);
                    }
                }
            }
            lstResult.clear();
            lstResult = lstNew;
        }
        return StringUtils.join(lstResult,",");
    }
    
    public static void main(String[] args) {
        String lst = PinYinUtil.getPinYinSet("吾肉孜白克·阿不都许可尔");
        System.out.println(lst);
        String[] sss = getPinYin("吾肉孜白克·阿不都许可尔");
        System.out.println("全拼："+sss[0]+"---简拼："+sss[1]);
    }
    
    /**
     * 传入中文汉字，转换出对应拼音及首字母简拼，作为数组返回
     * 注：出现同音字，默认选择汉字全拼的第一种读音
     */
    public static String[] getPinYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];

        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String[] t4 = new String[2];
        String str1 = "";
        String str2 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                	//庒 在PinyinHelper中转化为空，特殊处理
                	if(StringUtils.equals("庒",Character.toString(t1[0]))){
                    	t2 = new String[]{"zhuang"};
                    }else{
                    	t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
                    }
                    str1 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                    str2 += t2[0].substring(0,1);// 取出该汉字全拼的第一种读音首字母并连接到字符串t4后
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
        }
        t4[0] = str1;
        t4[1] = str2;
        return t4;
    }
    
    /**
     * 将单个汉字转换成汉语拼音，考虑到同音字问题，返回字符串数组的形式
     */
    public static Set<String> getPinYin(char src){
        char[] t1 = {src};
        String[] t2 = new String[t1.length];
        
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        Set<String> strSet = new HashSet<String>();
        // 判断能否为汉字字符
        if (Character.toString(t1[0]).matches("[\\u4E00-\\u9FA5]+")) {
            try {
            	//庒 在PinyinHelper中转化为空，特殊处理
            	if(StringUtils.equals("庒",Character.toString(t1[0]))){
                	t2 = new String[]{"zhuang"};
                }else{
                	// 将汉字的几种全拼都存到t2数组中
                	t2 = PinyinHelper.toHanyuPinyinStringArray(t1[0], t3);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            try {
				for(String str:t2){
					strSet.add(str);
					strSet.add(str.substring(0,1));
				}
			} catch (Exception e) {
				System.out.println("1111111");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return strSet;
    }
    
    /**
     * 传入没有多音字的中文汉字，转换出对应拼音
     * 注：如果传入的中文中有任一同音字都会返回字符串信息：false
     */
    public static String getNoPolyphone(String src){
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];

        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字字符
                // System.out.println(t1[i]);
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
                    if(t2.length>1){
                            return "false";
                    }else{
                            t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                    }
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后
                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
        }
        return t4;
    }
}
