/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author wangsn
 * @since 1.0
 * @version $Id: PhotoResult.java,v 1.3 2006/11/07 01:29:46 zhangza Exp $
 */
package net.zdsoft.leadin.photo.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

import net.zdsoft.leadin.photo.PhotoConstant;

public class PhotoResult implements Result {
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = LoggerFactory.getLogger(PhotoResult.class);

	public void execute(ActionInvocation arg0) throws Exception {
	   ValueStack stack = ActionContext.getContext().getValueStack();
	   HttpServletResponse response = ServletActionContext.getResponse();
	   HttpServletRequest request = ServletActionContext.getRequest();
       response.setContentType("image/jpeg");

       String path=stack.findString("photoPath");

       BufferedOutputStream out = null;
       InputStream is = null;
       BufferedInputStream in = null;
       try{
    	   out = new BufferedOutputStream(response
                   .getOutputStream());
           //往servlet的outputstream写数据
           int readbyte = -1;
           byte[] buffer = new byte[PhotoConstant.BUFFER_SIZE];
           
           if(path.startsWith("http://")){
               URLConnection urlConn = new URL(path).openConnection();
               is = urlConn.getInputStream();
           }else if(path.contains(PhotoConstant.NOPICTURE_URI)) {
               path = request.getScheme() + "://" + request.getServerName() + ":"
                        + request.getServerPort() + path;
               URLConnection urlConn = new URL(path).openConnection();
               is = urlConn.getInputStream();

           }else {
                 is = new FileInputStream(path);
           } 
           
           in = new BufferedInputStream(is);

           while ((readbyte = in.read(buffer)) != -1) {
               out.write(buffer, 0, readbyte);
           }
          
           out.flush();
       }catch(Exception ex){
    	   log.error("PhotoResult.execute is failed: " + ex.toString(), ex);
       } finally{
           is.close();
           in.close();           
           out.close();
       }
	}

}


