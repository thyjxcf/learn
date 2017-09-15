/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.frame.serial;

import java.net.URL;

import net.zdsoft.leadin.util.ConfigFileUtils;




public class SerialRegister {
    private static SerialUtil instance = new SerialUtil();
//	private static Regtool3 instance = new Regtool3();
    private static int MAX_FUNC_LENGTH = 47;
    
    /**
     * 序列号校验
     * @param name
     * @param serial
     * @param prodid
     * @return
     */
    public static int VerifySerial(String name, String serial, String prodid)
    {
        return instance.VerifySerial0(prodid, name, " ", serial);
    }
    
    /**
     * 取得过期日期
     * @param serial
     * @return
     */
    public static String GetExpireDate(String serial)
    {
        String s1;
        try
        {
            String dateStrings[] = instance.GetExpireDate0(serial).split("/");
            if(dateStrings[1].length() < 2)
                dateStrings[1] = "0".concat(String.valueOf(String.valueOf(dateStrings[1])));
            if(dateStrings[2].length() < 2)
                dateStrings[2] = "0".concat(String.valueOf(String.valueOf(dateStrings[2])));
            String s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(dateStrings[1])))).append("/").append(dateStrings[2]).append("/").append(dateStrings[0])));
            return s;
        }
        catch(Exception ex)
        {
            s1 = "";
        }
        return s1;
    }

    /**
     * 授权给用户的可用单位数
     * @param funStr
     * @return
     */
    public static int getUnitCountLimit(String serial){
        String funStr = GetFuncSerial(serial);
        if(funStr==null || funStr.length()!=MAX_FUNC_LENGTH){
            return -1;
        }
        String countStr = funStr.substring(32, 37);
        return Integer.valueOf(countStr);
    }

    /**
     *返回32位的子系统权限串（如：00101010000010000000000000000011）
     * @param serial
     * @return
     */
    public static String getFunStr(String serial){
        String funStr =  GetFuncSerial(serial);
        
        return funStr.substring(0, 32);
    }
    
    /**
     * 返回模块的字符串
     * @param serial
     * @return
     */
    public static String getModIds(String serial){    	
    	String module ;
    	try{
    	 URL url = ConfigFileUtils.getConfigFileUrl("modules.dat");
         String path = url.getPath().substring(1);
    	 module=instance.getModule0(path, serial, 0);//SerialProcessor.getModule
//    	 module=SerialProcessor.getModule(path, serial, 0);
    	}catch(Exception e){
    		module="0";
    		System.out.println(e);
    		System.out.println("读取modules.dat文件发生错误");
    	}
    	return module;
    }
    
    private static String GetFuncSerial(String serial)
    {
        return instance.GetFuncSerial0(serial);
    }

    
    
    /**
     * 取校验序列号返回值对应的提示信息
     * @param resultInt
     * @return
     */
    public static String getVerifyStatus(int resultInt)
    {
        String rebyte = "";
        switch(resultInt)
        {
        case 1: // '\001'
            rebyte = "";
            break;
        case -1: 
            rebyte = "序列号长度不对或不合法";
            break;
        case -2: 
            rebyte = "序列号过期";
            break;
        case -3: 
            rebyte = "产品标识不一致";
            break;
        case -4: 
            rebyte = "用户名称不一致";
            break;
        case -5: 
            rebyte = "";
            break;
        case 0: // '\0'
        default:
            rebyte = "未知错误";
            break;
        }
        return rebyte;
    }

    public static void main(String args[])
    {        
//        SerialRegister regtool1 = new SerialRegister();
//        int flag = 10;
//        String name = "宝鸡市渭滨区教育局";
//        String prod_id = "EIS-E";
        String serial ="R3A5K-HCLIC-Q13RL-L3CZ9-Z5S3N-TSIV3-UVQNC";
//        byte rebyte[] = "error".getBytes();
//        String expireDate = "";
//        String func = "";
//        int count = 0;
        try
        {
           
        	System.out.println(getModIds(serial));
        	System.out.println(GetFuncSerial(serial));
        	System.out.println(GetExpireDate(serial));

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }


}
