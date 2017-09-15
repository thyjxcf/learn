package net.zdsoft.eis.system.frame.serial;



/**
 * 框架调用dll的接口程序，所有的Jni本地调用都通过该类处理。
 */

public class SerialProcessor {
    private SerialProcessor() {
    }

    // public synchronized String getFunction(String serial) {
    // return getFunction0(serial);
    // }
    //
    // public synchronized int getVersion(String serial) {
    // return getVersion0(serial);
    // }
    //
    // public synchronized String getTime(String serial) {
    // return getTime0(serial);
    // }
    //
    // public synchronized int getUserCount(String serial) {
    // return getUserCount0(serial);
    // }
    //
    // public synchronized int verifySerial(String productId, String userName,
    // String userId, String serial) {
    // return verifySerial0(productId, userName, userId, serial);
    // }

    public static synchronized String getModule(String path, String serial,
            int type) {
        return getModule0(path, serial, type);
    }

    // private static native String getFunction0(String serial);
    //
    // private static native int getVersion0(String serial);
    //
    // private static native int getUserCount0(String serial);
    //
    // private static native String getTime0(String serial);
    //
    // /**
    // *
    // * 1 验证通过 -1 序列号转化失败,包括长度不对，不合法等 -2 序列号过期 -3 产品标识不一致 -4 用户名称不一致 -5 用户编号不一致
    // */
    // private static native int verifySerial0(String productId, String
    // userName,
    // String userId, String serial);

    /**
     * bstrFile:配置文件全路径 bstrSerial:序列号
     * bALL：为1时得到所有的模块列表，为0的时候得到可用的模块列表，为-1的时候得到所有不可用的模块列表
     */
    private static native String getModule0(String path, String serial, int type);

    private static String dllName = "JSerial";

    static {
//        try{
//        Properties properties = FileUtils.readProperties(ConfigFileUtils.getConfigFileStream("load.properties"));
//        dllName = properties.getProperty("road.dllName");
//        }
//        catch(Exception e){
//            e.printStackTrace();            
//        }
        try {
            System.loadLibrary(dllName);
        }
        catch (Throwable ex) {
        	System.loadLibrary("JSerial3");
            System.out.println("native lib '" + dllName
                    + "' not found in 'java.library.path': "
                    + System.getProperty("java.library.path"));
            ex.printStackTrace();            
        }

    }

    public static void main(String[] args) throws Exception {

    }

    /*
     * B)验证组件 Serial 方法： (GetFunction) BSTR bstrSerial //序列号 返回功能权限
     * 
     * (GetVersion) BSTR bstrSerial 返回版本号，为int型
     * 
     * (GetUserCount) BSTR bstrSerial 返回用户数，为int型
     * 
     * (GetTime) BSTR bstrSerial 返回限制时间, 格式为 "年/月/日"
     * 
     * (VerifySerial) BSTR bstrProdID, //产品ID BSTR bstrName, //用户名 BSTR
     * bstrUserID, //用户ID BSTR bstrSerial, //序列号 VerifySerial方法返回值修改: 1 验证通过 -1
     * 序列号转化失败,包括长度不对，不合法等 -2 序列号过期 -3 产品标识不一致 -4 用户名称不一致 -5 用户编号不一致
     * 
     * C)ATLSerial组件新增方法： CSerial::GetModule(BSTR bstrFile, BSTR bstrSerial, int
     * bALL) bstrFile:配置文件全路径 bstrSerial:序列号
     * bALL：为1时得到所有的模块列表，为0的时候得到可用的模块列表，为-1的时候得到所有不可用的模块列表
     * 
     * 
     * 2、Encrypt.dll 用于加密存放的模块列表的文件（modules.txt），在ATLSerial组件GetModule中解密获取
     * 组件：Encrypt.EncFile 方法：EncFile(BSTR bstrSrcFile, BSTR bstrFile)
     * bstrSrcFile:加密源文件 bstrFile：加密之后的文件 均要求为有完整路径文件名
     * 
     * 3、modules.txt 存放模块列表的文件，加密后变为modules.dat
     */
}
