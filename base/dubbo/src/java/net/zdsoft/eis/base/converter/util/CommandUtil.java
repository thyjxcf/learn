package net.zdsoft.eis.base.converter.util;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStream;import java.io.InputStreamReader;import java.util.List;import org.slf4j.Logger;import org.slf4j.LoggerFactory;/** * 执行本地命令 * @author shenl * */public class CommandUtil {	private static Logger log = LoggerFactory.getLogger(CommandUtil.class);		public static void execCommand(List<String> commend) throws Exception{		execCommand(commend.toArray(new String[]{}));	}		public static void execCommand(String...commend) throws Exception {		ProcessBuilder builder = new ProcessBuilder();		builder.command(commend);		builder.redirectErrorStream(true);		Process p = null;		WatchThread wt = null;		try {			p = builder.start();			boolean finished = false; // Set to true when p is finished			wt = new WatchThread(p);			wt.start();			while (!finished) {				try {					log.debug("commond=" + commend.toString());										int waitValue = -1;					try {						waitValue = p.waitFor();					} catch (InterruptedException e) {						e.printStackTrace();						continue;					}					if (waitValue != 0) {						String error = "执行命令出错:commond=" + getArrayStr(commend) + ",error=" + wt.getError();						throw new Exception(error);					}					int exitValue = p.exitValue();					if (exitValue != 0) {						String error = "执行命令出错:commond=" + getArrayStr(commend) + ",error=" + wt.getError();						throw new Exception(error);					}					finished = true;				} catch (IllegalThreadStateException e) {					Thread.sleep(500);				}			}		} finally {			if (wt != null) {				wt.setOver(true);			}			if (p != null) {				p.destroy();			}		}	}		/**     * 监控进程，以防子线程的输出流或输入流缓存太小造成死锁，所以必须自己手动清空缓存     *      * @author zhaosf     * @version $Revision: 1.0 $, $Date: Mar 21, 2012 4:12:37 PM $     */    private static class WatchThread extends Thread {        Process p;        boolean over;        String error = "";         public WatchThread(Process p) {        	super("命令行调用监控线程");            this.p = p;            over = false;        }        public void run() {            InputStream in = p.getInputStream();            BufferedReader br = null;            try {                if (p == null || in == null)                    return;                br = new BufferedReader(new InputStreamReader(in));                while (true) {                    if (p == null || over) {                        break;                    }                    // while (br.readLine() != null)                    // ;                    StringBuilder sb = new StringBuilder();                    String line;                    while ((line = br.readLine()) != null) {                        sb.append(line);                    }                    this.error = "信息：" + sb.toString();                    log.debug(error);                }            } catch (IOException e) {//                log.error(e.toString());            } catch (Exception e) {                log.error(e.toString());            } finally {                try {                    if (null != br) {                        br.close();                    }                    if (null != in) {                        in.close();                    }                } catch (IOException e) {                    log.error(e.toString());                }            }        }        public void setOver(boolean over) {            this.over = over;        }		public String getError() {			return error;		}    }    	/**	 * 组合字符串	 * @param args	 * @return	 */	public static String getArrayStr(Object[] args){		StringBuffer buffer = new StringBuffer();		for (Object string : args) {			buffer.append(string);		}		return buffer.toString();	}}