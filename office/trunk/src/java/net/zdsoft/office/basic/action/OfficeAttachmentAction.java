package net.zdsoft.office.basic.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keelcnet.config.BootstrapManager;

import org.apache.commons.lang.StringUtils;


public class OfficeAttachmentAction extends PageAction{
	
	private static final long serialVersionUID = -850330480526297221L;
	
	
	private Attachment attachment;
	private String attachmentId;
	private AttachmentService attachmentService;
	private ConverterFileTypeService converterFileTypeService;
	private SysOptionService sysOptionService;
	
	private String fileType;
	private boolean needPdf = true;
	private boolean isSend = false;
	private boolean inline = false;//pdf在线预览
	
	private String contentId;
	
	private String removeAttachmentId;
	
	private String contentName;
	//private String id;
	
	private int saveContentCopy; 
	
	public String checkAttachment(){
		attachment = attachmentService.getAttachment(attachmentId);
		if(attachment!=null){
			if(attachment.getConStatus()==0 || attachment.getConStatus()==1 || attachment.getConStatus()==4|| attachment.getConStatus()==5){
				jsonError = "系统正在转换中...请稍后点击在线预览！";
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage(jsonError);
			}else if(attachment.getConStatus()==2){
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setBusinessValue(attachmentId);
			}else if(attachment.getConStatus()==9){
				jsonError = "历史数据状态不对！请先下载再上传！";
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage(jsonError);
			}else if(attachment.getConStatus()==3){
				jsonError = "该文档暂时无法预览";
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage(jsonError);
			}
		}else{
			jsonError = "该附件不存在或已被删除，请刷新页面！";
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(jsonError);
		}
		return SUCCESS;
	}
	
	public String showAttachment(){
		attachment = attachmentService.getAttachment(attachmentId);
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
			attachment.setSwfUrl(furl+BootstrapManager.getStoreFolder()+"/"+filepth.replace("\\","/"));
			System.out.println(furl+BootstrapManager.getStoreFolder()+"/"+filepth.replace("\\","/"));
			//String url = BootstrapManager.getBaseUrl() + getRequest().getRequestURI().replace("showAttachment", "showConvertAttachment") + "?attachmentId=" +attachmentId;
			//attachment.setSwfUrl(url);
		}
		return SUCCESS;
	}
	
	
	public void showConvertAttachement(){
		try {
			attachment = attachmentService.getAttachment(attachmentId.substring(0, attachmentId.indexOf("?")));
			File file = StorageFileUtils.getFileLocalPath(attachment);
			if (file != null && file.exists()) {
				String filePath = file.toString();
				filePath =converterFileTypeService.buildFilePath(filePath, attachment.getExtName());
				System.out.println(filePath);
				InputStream in=new FileInputStream(filePath);
				byte[] byteArray=new byte[1024];
				OutputStream outputStream = getResponse().getOutputStream();
				int len=0;
				while((len=(in.read(byteArray)))!=-1){
					outputStream.write(byteArray, 0, len);
				}
				//byte[] byteArray = FileUtils.readFileToByteArray(new File(filePath));
				//outputStream.write(byteArray);
				in.close();
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String previewContent(){
		return SUCCESS;
	}
	
	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean isNeedPdf() {
		return needPdf;
	}

	public void setNeedPdf(boolean needPdf) {
		this.needPdf = needPdf;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getRemoveAttachmentId() {
		return removeAttachmentId;
	}

	public void setRemoveAttachmentId(String removeAttachmentId) {
		this.removeAttachmentId = removeAttachmentId;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public boolean isInline() {
		return inline;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}

	public int getSaveContentCopy() {
		return saveContentCopy;
	}

	public void setSaveContentCopy(int saveContentCopy) {
		this.saveContentCopy = saveContentCopy;
	}

	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}

	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}
	
}
