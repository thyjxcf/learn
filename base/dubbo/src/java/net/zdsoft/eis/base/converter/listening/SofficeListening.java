package net.zdsoft.eis.base.converter.listening;import java.io.File;import java.net.ConnectException;import java.util.Timer;import java.util.TimerTask;import net.zdsoft.eis.base.converter.util.CommandUtil;import net.zdsoft.eis.base.converter.util.OSUtil;import org.apache.commons.lang.StringUtils;import org.springframework.web.context.ContextLoader;import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;/** * openffice转换服务监听（自动重启） * @author shenl * */public class SofficeListening {    private OpenOfficeConnection openOfficeConnection;    private Boolean isUse;    	public void setOpenOfficeConnection(OpenOfficeConnection openOfficeConnection) {		this.openOfficeConnection = openOfficeConnection;	}				public void setIsUse(Boolean isUse) {		this.isUse = Boolean.valueOf(isUse);	}		/**	 * 监听服务	 */	public void init(){		//jvm启动参数        String start = System.getProperty("eis.fileconvert.start");		if (isUse && "true".equals(start)) {			new Thread(new Runnable() {								@Override				public void run() {					isWebInited();                    checkSofficeService();				}			}, "soffice服务监听工具").start();			//			schedule(new TimerTask(){//                @Override//                public void run() {//                    isWebInited();//                    checkSofficeService();//                }//            }, 1000, 2 * 10 * 1000, "soffice服务监听工具");		}	}	/**	 * 检查soffice服务是否开启	 */	private void checkSofficeService() {		System.out.println("提示：openoffice转换服务监听（自动重启）启动");		while (true) {			try {				if (openOfficeConnection == null) {					System.out.println("提示：SofficeListening监听中openOfficeConnection为空");				}else if (!openOfficeConnection.isConnected()) {					try {						openOfficeConnection.connect();					} catch (ConnectException e) {						//连接失败，说明soffice服务没启动						startupSoffice();					}				}				Thread.sleep(10 * 1000);			} catch (Throwable e) {				e.printStackTrace();			}		}	}	/**	 * 启动服务	 * @throws Exception 	 */	private void startupSoffice() throws Exception{		System.out.println("提示：监听程序自动启动soffice服务");				String openofficePath = System.getenv("OPENOFFICE_HOME");		if(openofficePath == null){			openofficePath = "";		}		File openoffice = new File(openofficePath);        if (OSUtil.isWindows()) {        	if (!openoffice.exists()) {        		openoffice = new File("D:/Program Files (x86)/OpenOffice.org 3/program/soffice.exe");        		if (openoffice.exists()) {        			openofficePath = openoffice.getAbsolutePath();        			System.out.println("提示：使用openoffice默认路径,path="+openofficePath);        		}else{        			System.out.println("提示：openoffice服务不存在,path="+openofficePath);        			openofficePath = "";        		}        	}        	if (StringUtils.isNotBlank(openofficePath)) {//    			String commend = openofficePath + " \"-headless -accept=socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard\"";    			CommandUtil.execCommand(openofficePath,"-headless","-accept=socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard");//    			CommandUtil.execCommand(commend);    		}        	        }else {            if (!openoffice.exists()) {                File openofficeV3_5 = new File("/opt/openoffice.org3/program/soffice");                File openofficeV4 = new File("/opt/openoffice4/program/soffice");                File libreoffice5 = new File("/opt/libreoffice5.1/program/soffice");                if (openofficeV3_5.exists() && openofficeV3_5.isFile()) {                    //openoffice3.5版本                    openofficePath = openofficeV3_5.getAbsolutePath();                    System.out.println("提示：使用openoffice默认路径,path="+openofficePath);                }else if (libreoffice5.exists() && libreoffice5.isFile()) {                    //libreoffice5版本                    openofficePath = libreoffice5.getAbsolutePath();                    System.out.println("提示：使用openoffice默认路径,path="+openofficePath);                }else if (openofficeV4.exists() && openofficeV4.isFile()) {                	//openoffice4.0版本                	openofficePath = openofficeV4.getAbsolutePath();                	System.out.println("提示：使用openoffice默认路径,path="+openofficePath);                }else {                	System.out.println("提示：openoffice服务不存在,path="+openofficePath);                	openofficePath = "";                }            }            if (StringUtils.isNotBlank(openofficePath)) {                String commend = openofficePath + " \"-accept=socket,host=127.0.0.1,port=8100;urp;StarOffice.ServiceManager\" -nologo -headless -nofirststartwizard &";                String[] commends = { "/bin/sh", "-c", commend };                CommandUtil.execCommand(commends);            }		}	}	/**	 * 关闭soffice服务	 * @throws Exception	 */	public static void stopSoffice() throws Exception{	    if (OSUtil.isWindows()) {	        System.out.println("暂不支持windows版openoffice服务的关闭功能");	    }else {            String commend = "killall -9 soffice.bin";            String[] commends = { "/bin/sh", "-c", commend };            CommandUtil.execCommand(commends);        }	}		public void destroy() {        if (openOfficeConnection != null) {            openOfficeConnection.disconnect();            openOfficeConnection = null;        }    }	    /**     * 安排指定的任务在指定的延迟后开始进行重复的固定速率执行     * @param task     * @param delay     * @param period     */    private static void schedule(TimerTask task, long delay, long period, String name){        Timer timer = new Timer(name, true);        timer.schedule(task, delay, period);    }        /**     * 等待web初始化完成     * @return     */    private static Boolean isWebInited(){        //等待初始化完成        while (ContextLoader.getCurrentWebApplicationContext() == null) {            try {                Thread.sleep(1 * 1000);            } catch (InterruptedException e) {                e.printStackTrace();            }        }        return true;    }}