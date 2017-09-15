package net.zdsoft.eis.ueditor.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.ueditor.PathFormat;
import net.zdsoft.eis.ueditor.define.AppInfo;
import net.zdsoft.eis.ueditor.define.BaseState;
import net.zdsoft.eis.ueditor.define.State;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

public class BinaryUploader {
	
	private static StorageDirService storageDirService = (StorageDirService) ContainerManager
			.getComponent("storageDirService");
	
	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		// 表示文件上传的form
		if (request instanceof MultiPartRequestWrapper) {
			
			MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
			Enumeration<String> e = requestWrapper.getFileParameterNames();
			if (!e.hasMoreElements()) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
			// 有上传文件
			if (e.hasMoreElements()) {
				long maxSize = ((Long) conf.get("maxSize")).longValue();
				String fieldName = e.nextElement();
				File uploadedFile = (((File[]) requestWrapper.getFiles(fieldName))[0]);
				String fileName = requestWrapper.getFileNames(fieldName)[0];

				if (uploadedFile.length() > maxSize) {
					
					return new BaseState(false, AppInfo.MAX_SIZE);
				}
				// 不能上传空文档
				if (uploadedFile.length() == 0) {
					return new BaseState(false, AppInfo.ZERO_LENGTH);
				}

				try {
					InputStream in = new FileInputStream(uploadedFile);
				byte[] data = null;
				try {
					data = readInputStream(in);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				StorageDir dir = storageDirService.getActiveStorageDir();
				
				String savePath = (String) conf.get("savePath");
				SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
				String filePath = "photo" + File.separator + "attached" +File.separator + savePath + File.separator + df1.format(new Date());
				//检查扩展名
				String fileExt1 = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt1;
				String detailPath = dir.getDir() + File.separator + filePath + File.separator;
				File saveDirFile = new File(detailPath);
				//如果不存在，那么创建一个
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
				File tempFile = new File(detailPath+newFileName);
				FileOutputStream outStream = new FileOutputStream(tempFile);
		        //写入数据 
		        outStream.write(data);
		        outStream.close();
		        String filename = newFileName;
		        State storageState = new BaseState(true);
				if (storageState.isSuccess()) {
					storageState.putInfo("url", PathFormat.format(request.getContextPath()+"/common/downloadFile.action?filePath="+filePath+"&filename="+filename));
					storageState.putInfo("type", fileExt1);
					storageState.putInfo("original", fileName);//这个存的是老文件的名称，word文档图片转存用到
				}

				return storageState;
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }
}
