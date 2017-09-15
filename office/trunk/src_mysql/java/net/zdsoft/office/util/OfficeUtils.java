package net.zdsoft.office.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chens
 * @version 创建时间：2015-5-25 下午3:01:33
 * 
 */
public class OfficeUtils {

	public static String dayForWeek(Date date) throws ParseException{
  	  Calendar c = Calendar.getInstance();
  	  c.setTime(date);
  	  int dayForWeek = 0;
  	  if(c.get(Calendar.DAY_OF_WEEK) == 1){
  	   dayForWeek = 7;
  	  }else{
  	   dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
  	  }
  	  String s="";
  	  if(dayForWeek==7){
  		  s="星期天";
  	  }else if(dayForWeek==6){
  		  s="星期六";
  	  }else if(dayForWeek==5){
  		  s="星期五";
  	  }else if(dayForWeek==4){
  		  s="星期四";
  	  }else if(dayForWeek==3){
  		  s="星期三";
  	  }else if(dayForWeek==2){
  		  s="星期二";
  	  }else if(dayForWeek==1){
  		  s="星期一";
  	  }
  	  return s;
	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
