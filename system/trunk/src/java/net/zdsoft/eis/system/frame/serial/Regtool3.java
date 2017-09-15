/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.frame.serial;


public class Regtool3
{

    public Regtool3()
    {
    }
    public native String getModule0(String path, String serial, int type);

    public native int VerifySerial0(String s, String s1, String s2, String s3);

    public native String GetExpireDate0(String s);

    public native int GetUserCount0(String s);

    public native String GetFuncSerial0(String s);

    static 
    {
    	
        try{
        	System.loadLibrary("JSerial");
        }catch(Exception e){
        	System.loadLibrary("JSerial3");
        	System.out.println("[java.library.path:] "+System.getProperty("java.library.path"));
        	System.out.println(e);
        }
    }
}
