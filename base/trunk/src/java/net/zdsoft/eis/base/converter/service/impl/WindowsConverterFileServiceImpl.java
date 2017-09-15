package net.zdsoft.eis.base.converter.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.converter.entity.ConverterFile;
import net.zdsoft.eis.base.converter.service.WindowsConverterFileService;

public class WindowsConverterFileServiceImpl implements WindowsConverterFileService{
	
	private SystemIniService systemIniService;
	private AttachmentService attachmentService;
	
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}
	
//	SYSTEM_CONVERTOR_WINDOW_URL
	/**
	 * convertionFileList 转换文件的对象List array<object>  
	     resId 调用方传来的资源标识id string  
	     resName 调用方传来的资源名 string  
	     resUrl 资源远程地址 string 包含请求参数可以直接http请求到文件 
 		returnUrl 回调请求url string 转换成功后回调的地址， 
		serverId 调用方的服务器id string 
	 */
	
	@Override
	public boolean conver(ConverterFile task)  {
		try {
			if("0".equals(getWindowUrl())||"0".equals(getServiceId())){
				throw new Exception("window转换未配置地址和servieid，无法使用windows转换");
			}
			Attachment attachment = attachmentService.getAttachment(task.getId());
			JSONObject postJson =  new JSONObject();
			JSONObject fileObject = new JSONObject();
			fileObject.put("resId", attachment.getId());
			fileObject.put("resName", attachment.getFileName());
			fileObject.put("resUrl", getEisUrl()+"/common/downloadAttachment.action?attachmentId=" + attachment.getId());
			JSONArray array = new JSONArray();
			array.add(fileObject);
			Map<String,String> paramMap = new HashMap<String,String>();
			postJson.put("convertionFileList", array);
			postJson.put("returnUrl",getEisUrl()+"/common/open/windowConverterAttachment.action?attachmentId="+task.getId());
			postJson.put("serverId", getServiceId());
			System.out.println(postJson.toString());
			paramMap.put("convertionFileProviderJsonStr", postJson.toString());
			//http://192.168.0.65:8080
			String converterUrl = getWindowUrl()+"/setConvertionFileInfoByJson";
//			String converterUrl = "http://192.168.0.26:8080/setConvertionFileInfoByJson";
			sendPost(converterUrl, postJson, attachment.getId(), true);
			return true;
		} catch (Exception e) {
			//System.out.println("请求失败"+e.getMessage());
			//e.printStackTrace();
			return false;
		}
	}
	
	private void sendPost(String converterUrl, JSONObject postJson,String id,boolean isNeedSecond) throws Exception{
		HttpResponse result = null;
		try {
			result = httpPost(converterUrl, postJson);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("连接windows服务器失败");
			long nowTime = System.currentTimeMillis();
			System.out.println("等待一分钟重新连接：当前时间--"+nowTime);
			long oldTime= nowTime+60*1000;
			while (oldTime>System.currentTimeMillis()) {
			}
			try {
				result = httpPost(converterUrl, postJson);
			} catch (Exception e2) {
				 throw new RuntimeException("window转换服务连接失败，重置任务"); 
			}
		}
		
		if (result.getStatusLine().getStatusCode() == 200) {
			 BufferedReader in = new BufferedReader(new InputStreamReader(result.getEntity().getContent(), "utf-8"));
			 String line, resultStr = "";
			 while ((line = in.readLine()) != null) {
				 resultStr += line;
			 }
			 if(resultStr.indexOf(id)>=0){
				 System.out.println("提交到windows转服服务器成功 attachmentid"+id);
			 }else{
				 if(isNeedSecond){
					 System.out.println("提交到windows转服服务器失败，处理结果没用需要转换的附件 attachmentid"+id);
					 long nowTime = System.currentTimeMillis();
					 System.out.println("等待一分钟重新提交请求：当前时间--"+nowTime);
					 long oldTime= nowTime+60*1000;
					 while (oldTime>System.currentTimeMillis()) {
					 }
					 sendPost(converterUrl, postJson, id, false);
				 }else{
					 throw new RuntimeException("window转换服务提交失败，重置任务"); 
				 }
			 }
		}else{
			 if(isNeedSecond){
				 System.out.println("提交到windows转服服务器失败，请求未成功 attachmentid"+id);
				 long nowTime = System.currentTimeMillis();
				 System.out.println("等待一分钟重新提交请求：当前时间--"+nowTime);
				 long oldTime= nowTime+60*1000;
				 while (oldTime>System.currentTimeMillis()) {
				 }
				 sendPost(converterUrl, postJson, id, false);
			 }else{
				 throw new RuntimeException("window转换服务提交失败，重置任务");
			 }
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("提交到windows转服服务器失败 attachmentid");
		 long nowTime = System.currentTimeMillis();
		 System.out.println("等待一分钟重新提交请求：当前时间--"+nowTime);
		 long oldTime= nowTime+60*1000;
		 while (oldTime>System.currentTimeMillis()) {
			 System.out.println("等待时间---1");
		 }
		 System.out.println("提交到windows转服服务器失败 attachmentid");
	}
	
	private String getWindowUrl(){
		String type =  systemIniService.getValue(BaseConstant.SYSTEM_CONVERTOR_WINDOW_URL);
		if (StringUtils.isNotBlank(type)) {
			return type;
		}else{
			return "0";
		}
	}
	
	private String getEisUrl(){
		String type =  systemIniService.getValue(BaseConstant.SYSTEM_CONVERTOR_EIS_URL);
		if (StringUtils.isNotBlank(type)) {
			return type;
		}else{
			return "0";
		}
	}
	
	private String getServiceId(){
		String type =  systemIniService.getValue(BaseConstant.SYSTEM_CONVERTOR_WINDOW_SERVER);
		if (StringUtils.isNotBlank(type)) {
			return type;
		}else{
			return "0";
		}
	}
	
	
    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static HttpResponse httpPost(String url,JSONObject jsonParam)  throws Exception{
        //post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        HttpResponse result = null;
//        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                method.setEntity(entity);
            }
            result = httpClient.execute(method);
//            if (result.getStatusLine().getStatusCode() == 200) {
//                String str = "";
//                try {
//                    /**读取服务器返回过来的json字符串数据**/
//                    str = EntityUtils.toString(result.getEntity());
//                    /**把json字符串转换成json对象**/
////                    jsonResult = JSONObject.fromObject(str);
//                } catch (Exception e) {
////                    logger.error("post请求提交失败:" + url, e);
//                }
//            }
            
//        } catch (IOException e) {
//        	e.printStackTrace();
//        }
        return result;
    }
    
}
