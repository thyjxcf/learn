package net.zdsoft.leadin.dataimport.template;

/**
 * <p>Title: Excel处理辅助类</p>
 * <p>Description: 处理字符串转换</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ZDsoft</p>
 * @author lvl
 * @version 1.0
 */

public class Util {
  public Util() {
  }

  /**
   * 字符串转换处理，如果传入字符串是空串或是null值则转换为"-"输出。
   * @param val 需要转换的字符串。
   * @return 转换后的字符串。
   */
  public static String stringParse(String val){
    if(val==null) val = "-";
    if(val.trim().equals("")) val = "-";
    return val;
  }
  /**
   * 字符串转换处理，如果传入字符串是空串或是null值则转换为"-"输出，否则
   * 将传入的字符串（数值）以保留两位小数的形式输出。
   * @param val 需要转换的字符串，要求是数值字符串、空字符串或null值。
   * @return 转换后的字符串。
   */
  public static String sParse(String val){
    val = stringParse(val);
    if(!val.equals("-")) {
      val = Float.toString((float)Math.round(Float.parseFloat(val) * 100) / 100)
          + "000";
      //保留两位小数
      val=val.substring(0,val.indexOf(".")+3);
    }
    return val;
  }
  /**
  * 字符串转换处理，根据bl的值处理。如果bl是true则执行sParse操作，否则
  * 执行stringParse 操作。
  * @param val 需要转换的字符串。
  * @return 转换后的字符串。
  */
  public static String stringParse(String val,boolean bl){
    if(bl) val = sParse(val);else val = stringParse(val);
    return val;
  }
  /**
  * 字符串转换处理，将传入的字符串转换成float数据。
  * @param val 需要转换的字符串。
  * @return 转换后的float型数值。
  */
  public static float parseToFloat(String val){
    if(val==null) val = "0";
    if(val.trim().equals("")) val = "0";
    return Float.parseFloat(val);
  }
  /**
  * 字符串转换处理，将传入的字符串转换成int数据。
  * @param val 需要转换的字符串。
  * @return 转换后的int型数值。
  */
  public static int parseToInt(String val){
    if(val==null) val = "0";
    if(val.trim().equals("")) val = "0";
    return Integer.parseInt(val);
  }
  /**
   * 字符串转换处理，如果传入字符串是空串或是null值则转换为缺省值输出
   * @param val 需要转换的字符串，要求是数值字符串、空字符串或null值。
   * @return 转换后的字符串。
   */
  public static String isNullTo(String val,String tostring){
    if(val==null) return tostring;
    val = val.trim();
    if(val.equals("null")) return tostring;
    return val;
  }

}
