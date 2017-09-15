package net.zdsoft.eis.base.common.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keelcnet.config.BootstrapManager;

import org.apache.commons.lang.StringUtils;

public class OnlinePreviewsFileAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String redirectResultMsg;//提示还是错误用，
	private String retUrl;//返回的页面url，
	private AttachmentService attachmentService;
	private SysOptionService sysOptionService;
	private ConverterFileTypeService converterFileTypeService;
	private String id;
	private Attachment attachment;
	private String divId="container";
	private String fileType;
	private boolean isDown=false; 
	public Attachment getAttachment() {
		return attachment;
	}

	public String execute() {
		 attachment = attachmentService.getAttachment(id);
		 boolean ret =false;
		//9不需要转换，0 待转换 1正在转换 2 转换成功 3 转换失败 4 预转换
		if(attachment!=null){
			if(attachment.getConStatus()==0 || attachment.getConStatus()==1 || attachment.getConStatus()==4){
				try {
					ret=true;
					redirectResultMsg=URLEncoder.encode("false,系统正在转换中...请稍后点击在线预览！","utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(attachment.getConStatus()==3){
				try {
					ret=true;
					redirectResultMsg=URLEncoder.encode("true,"+attachment.getResultMsg(),"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(attachment.getConStatus()==9){
				try {
					ret=true;
					redirectResultMsg=URLEncoder.encode("false,历史数据状态不对！请先下载再上传！","utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String filepth=attachment.getFilePath();
			if(converterFileTypeService.isDocument(attachment.getExtName())){
				fileType="doc";
			}else if(converterFileTypeService.isVideo(attachment.getExtName())){
				fileType="video";
			}else if(converterFileTypeService.isPicture(attachment.getExtName())){
				fileType="pic";
			}else if(converterFileTypeService.isAudio(attachment.getExtName())){
				fileType="audio";
			}
			
			if(StringUtils.isNotBlank(filepth)){
				filepth =converterFileTypeService.buildFilePath(filepth, attachment.getExtName());
				String furl=sysOptionService.getValue("FILE.URL");
				if(StringUtils.isNotBlank(furl)){
					//System.out.println(furl+BootstrapManager.getStoreFolder()+"/"+filepth.replace("\\","/"));
					attachment.setSwfUrl(furl+BootstrapManager.getStoreFolder()+"/"+filepth.replace("\\","/"));
				}else{
					try {
						ret=true;
						redirectResultMsg=URLEncoder.encode("false,请联系管理员配置文件服务器地址【FILE.URL】！","utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			try {
				ret=true;
				redirectResultMsg=URLEncoder.encode("true,该附件不存在或已被删除，请刷新页面！","utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(ret){
			if(StringUtils.isNotBlank(redirectResultMsg)){
				if(StringUtils.isNotBlank(retUrl) && retUrl.indexOf("?")>=0){
					retUrl+="&redirectResultMsg="+redirectResultMsg;
				}else{
					retUrl+="?redirectResultMsg="+redirectResultMsg;
				}
			}
			return "rmsg";
		}
		
		return SUCCESS;
	}
	
	public String convertSuccess(){
		attachment = attachmentService.getAttachment(id);
		//9不需要转换，0 待转换 1正在转换 2 转换成功 3 转换失败 4 预转换
		if(attachment!=null){
			if(converterFileTypeService.isDocument(attachment.getExtName())){
				fileType="doc";
			}else if(converterFileTypeService.isPicture(attachment.getExtName())){
				fileType="pic";
			}else {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("该附件不支持预览");
				return SUCCESS;
			}
			if("pic".equals(fileType)){
				promptMessageDto.setOperateSuccess(true);
				return SUCCESS;
			}else{
				if(attachment.getConStatus()==0 || attachment.getConStatus()==1 || attachment.getConStatus()==4){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("系统正在转换中...请稍后点击在线预览！");
					return SUCCESS;
				}else if(attachment.getConStatus()==3){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage(attachment.getResultMsg());
					return SUCCESS;
				}else if(attachment.getConStatus()==9){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("历史数据状态不对！请先下载再上传！");
					return SUCCESS;
				}else if(attachment.getConStatus()==2){
					String filepth=attachment.getFilePath();
					if(StringUtils.isNotBlank(filepth)){
						filepth =converterFileTypeService.buildFilePath(filepth, attachment.getExtName());
						String furl=sysOptionService.getValue("FILE.URL");
						if(StringUtils.isNotBlank(furl)){
							attachment.setSwfUrl(furl+BootstrapManager.getStoreFolder()+"/"+filepth.replace("\\","/"));
						}else{
							promptMessageDto.setOperateSuccess(false);
							promptMessageDto.setErrorMessage("请联系管理员配置文件服务器地址【FILE.URL】！");
							return SUCCESS;
						}
					}else{
						promptMessageDto.setOperateSuccess(false);
						promptMessageDto.setErrorMessage("附件路径有问题！");
						return SUCCESS;
					}
					promptMessageDto.setOperateSuccess(true);
					return SUCCESS;
				}else{
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("文件不存在！");
					return SUCCESS;
				}
			}
			
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件不存在！");
			return SUCCESS;
		}
	}
	
	public String showAttachmentView(){
		attachment = attachmentService.getAttachment(id);
		String filepth=attachment.getFilePath();
		if(converterFileTypeService.isDocument(attachment.getExtName())){
			fileType="doc";
		}else if(converterFileTypeService.isPicture(attachment.getExtName())){
			fileType="pic";
		}
		filepth =converterFileTypeService.buildFilePath(filepth, attachment.getExtName());
		String furl=sysOptionService.getValue("FILE.URL");
		attachment.setSwfUrl(furl+BootstrapManager.getStoreFolder()+"/"+filepth.replace("\\","/"));
		return SUCCESS;
	}

	public String getRedirectResultMsg() {
		return redirectResultMsg;
	}

	public void setRedirectResultMsg(String redirectResultMsg) {
		this.redirectResultMsg = redirectResultMsg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}

	public String getFileType() {
		return fileType;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}
	
}
