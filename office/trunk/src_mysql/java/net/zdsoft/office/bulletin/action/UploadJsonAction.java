package net.zdsoft.office.bulletin.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.json.simple.JSONObject;

/**
 * @author chens
 * @version 创建时间：2014-9-1 下午7:11:21
 * 
 */
public class UploadJsonAction{

	private String dir;
	
	private StorageDirService storageDirService;
	
	public void execute1(){
		
	/**
	 * KindEditor JSP
	 * 
	 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
	 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
	 * 
	 */

//	//文件保存目录路径
//	String savePath = "E://attached/";
//
//	//文件保存目录URL
//	String saveUrl  = "E://attached/";
	
	StorageDir dir2 = storageDirService.getActiveStorageDir();
	
	String filePath = "photo" + File.separator + "attached";
	
	
	
	
	
	//定义允许上传的文件扩展名
	HashMap<String, String> extMap = new HashMap<String, String>();
	extMap.put("image", "gif,jpg,jpeg,png,bmp");
	extMap.put("flash", "swf,flv");
	extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
	extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

	//最大文件大小
	long maxSize = 1000000;

	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	response.setContentType("text/html; charset=UTF-8");
	PrintWriter out = null;
	try {
		out = response.getWriter();
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	if(!ServletFileUpload.isMultipartContent(request)){
		out.println(getError("请选择文件。"));
		return;
	}
	
//// =====================================	
//	//检查目录
//	File uploadDir = new File(savePath);
//	if(!uploadDir.isDirectory()){
//		out.println(getError("上传目录不存在。"));
//		return;
//	}
//	//检查目录写权限
//	if(!uploadDir.canWrite()){
//		out.println(getError("上传目录没有写权限。"));
//		return;
//	}
//// ===========================================
	
	
	
	
	
	
	
	
	
	String dirName = dir;
	if (dirName == null) {
		dirName = "image";
	}
	if(!extMap.containsKey(dirName)){
		out.println(getError("目录名不正确。"));
		return;
	}
//	//创建文件夹
//	savePath += dirName + "/";
//	saveUrl += dirName + "/";
	
	filePath =filePath + File.separator + dirName;
	
//	File saveDirFile = new File(savePath);
//	//File saveDirFile = new File(filePath);
//	if (!saveDirFile.exists()) {
//		saveDirFile.mkdirs();
//	}
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String ymd = sdf.format(new Date());
//	savePath += ymd + "/";
//	saveUrl += ymd + "/";
	
	
	
	filePath =filePath + File.separator + ymd;
	File file = new File(dir2.getDir() + File.separator+filePath);
    if (!file.exists()) {
        file.mkdirs();
    }
	
	
//	File dirFile = new File(savePath);
//	//File dirFile = new File(filePath);
//	if (!dirFile.exists()) {
//		dirFile.mkdirs();
//	}
	
	// 表示文件上传的form
	if (request instanceof MultiPartRequestWrapper) {
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
		Enumeration<String> e = requestWrapper.getFileParameterNames();
		if (!e.hasMoreElements()) {
			out.println("-->所选择的文件无效，请确认文件是否存在！");
			return;
		}
		// 有上传文件
		if (e.hasMoreElements()) {
			String fieldName = e.nextElement();
			File uploadedFile = (((File[]) requestWrapper.getFiles(fieldName))[0]);
			String fileName = requestWrapper.getFileNames(fieldName)[0];

			String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(fileName);

			if (uploadedFile.length() / 1024 > 100 * 1024) {
				
				out.println("-->上传文档超过100MB，请上传正确的文档！");
				return;
			}
			// 不能上传空文档
			if (uploadedFile.length() == 0) {
				
				out.println("-->上传文档内容为空!");
				return;
			}

			try {
				InputStream in = new FileInputStream(uploadedFile);
			byte[] data = null;
			try {
				data = readInputStream(in);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			//检查扩展名
			String fileExt1 = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt1;
//			File imageFile = new File(savePath, newFileName);
			
			//File imageFile = new File(filePath, newFileName);
			//创建输出流  
	        //FileOutputStream outStream = new FileOutputStream(imageFile);
			 File tempFile = new File(dir2.getDir() + File.separator + filePath +File.separator+ newFileName);
			 FileOutputStream outStream = new FileOutputStream(tempFile);
	        //写入数据  
	        outStream.write(data);  
	        outStream.close();
	        
	        
	        String str = dir2.getDir() + File.separator + filePath +File.separator+ newFileName;
	        
	        
	        JSONObject obj = new JSONObject();
			obj.put("error", 0);
			//obj.put("url", str);
			
			String filename = newFileName;
			obj.put("url", request.getContextPath()+"/common/downloadFile.action?filePath="+filePath+"&filename="+filename);
			out.println(obj.toJSONString());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			String fieldName = String.valueOf(e.nextElement());
//	        File[] tempFiles=requestWrapper.getFiles(fieldName);
//	        String[] fileNames = requestWrapper.getFileNames(fieldName);
//	        String[] contentTypes = requestWrapper.getContentTypes(fieldName);
//	        
//			DiskFileItemFactory factory = new DiskFileItemFactory();
//			for(int i=0;i<tempFiles.length;i++){
//				FileItem f = factory.createItem("fileItems"+i,contentTypes[i], false, fileNames[i]); 
//	
//				try {
//					File tempfile = tempFiles[i];
//					factory.setRepository(tempfile);
//					//检查扩展名
//					String fileExt = fileNames[i].substring(fileNames[i].lastIndexOf(".") + 1).toLowerCase();
//					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//					String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
//					File uploadedFile = new File(savePath, newFileName);
//					f.write(uploadedFile);
//				} catch (Exception ex) {
//					out.println("发送失败，可能的原因：" + ex.getStackTrace());
//					return;
//				}
//	
//			}
 catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
//	FileItemFactory factory = new DiskFileItemFactory();
//	ServletFileUpload upload = new ServletFileUpload(factory);
//	upload.setHeaderEncoding("UTF-8");
//	List items = null;
//	try {
//		items = upload.parseRequest(request);
//	} catch (FileUploadException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//	org.apache.commons.fileupload.DiskFileUpload diskFileUpload = new org.apache.commons.fileupload.DiskFileUpload();
//    List fileItems = null;
//
//    try {
//        fileItems = diskFileUpload.parseRequest(request);
//    }
//    catch (FileUploadException e) {
//        return;
//    }
//	Iterator itr = items.iterator();
//	while (itr.hasNext()) {
//		FileItem item = (FileItem) itr.next();
//		String fileName = item.getName();
//		long fileSize = item.getSize();
//		if (!item.isFormField()) {
//			//检查文件大小
//			if(item.getSize() > maxSize){
//				out.println(getError("上传文件大小超过限制。"));
//				return;
//			}
//			//检查扩展名
//			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//			if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
//				out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
//				return;
//			}
//
//			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
//			try{
//				File uploadedFile = new File(savePath, newFileName);
//				item.write(uploadedFile);
//			}catch(Exception e){
//				out.println(getError("上传文件失败。"));
//				return;
//			}
//
//			JSONObject obj = new JSONObject();
//			obj.put("error", 0);
//			obj.put("url", saveUrl + newFileName);
//			out.println(obj.toJSONString());
//		}
//	}
}
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
	
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
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
	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}
	
}
