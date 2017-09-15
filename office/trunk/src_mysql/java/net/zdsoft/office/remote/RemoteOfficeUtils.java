package net.zdsoft.office.remote;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.office.teacherLeave.dto.HisTask;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class RemoteOfficeUtils {
	
	public static McodedetailService getMcodedetailService(){
		return (McodedetailService)ContainerManager.getComponent("mcodedetailService");
	}
	
	public static StorageFileService getStorageFileService(){
		return (StorageFileService)ContainerManager.getComponent("storageFileService");
	}
	
	public static UserSetService getUserSetService(){
		return (UserSetService)ContainerManager.getComponent("userSetService");
	}
	
	/**
	 * 生成公文附件
	 * @param archDto
	 * @return
	 */
	
	public static JSONArray createAttachmentArray(List<Attachment> attachments){
		JSONArray attachmentArray = new JSONArray();
		if(CollectionUtils.isNotEmpty(attachments)){
			for (Attachment attachment : attachments) {
				JSONObject attachmentObject=new JSONObject();
				attachmentObject.put("id", attachment.getId());
				attachmentObject.put("fileName", attachment.getFileName());
				attachmentObject.put("fileSize", attachment.getFileSize());
				attachmentObject.put("extName", attachment.getExtName());
				attachmentObject.put("downloadPath", getNewDownloadPath(attachment.getId()));
				File file;
				try {
					getStorageFileService().setDirPath(attachment);
					file = attachment.getFile();
					if(file !=null){
						attachmentObject.put("fileExist", true);
					}else{
						attachmentObject.put("fileExist", false);
					}
				} catch (IOException e) {
					e.printStackTrace();
					attachmentObject.put("fileExist", false);
				}
				attachmentArray.add(attachmentObject);
			}
		}
		return attachmentArray;
	}
	
    public static String getNewDownloadPath(String attachmentId) {
    	String basePath = BootstrapManager.getBaseUrl();
    	String url = basePath + "/common/open/office/remoteDownLoad.action?attachmentId="+attachmentId+"&isExternalLink=1";
        return url;
    }
	
	/**
	 * 生成历史意见
	 * @return
	 */
	public static JSONArray createHisTaskCommentArray(List<HisTask> hisTaskList){
		JSONArray hisTaskCommentArray = new JSONArray();
		if(CollectionUtils.isNotEmpty(hisTaskList)){
			Set<String> userIds = new HashSet<String>();
			for (HisTask hisTask : hisTaskList) {
				userIds.add(hisTask.getComment()!=null?hisTask.getComment().getAssigneeId():"");
			}
			
			Map<String, String> photeMap = getUserSetService().getUserPhotoMap(userIds.toArray(new String[0]));
			
			for (HisTask hisTask : hisTaskList) {
				JSONObject hisTaskObject=new JSONObject();
				hisTaskObject.put("taskName", hisTask.getTaskName());
				hisTaskObject.put("assigneeName", hisTask.getComment().getAssigneeName());
				String assigneeId = hisTask.getComment()!=null?hisTask.getComment().getAssigneeId():"";
				if(StringUtils.isNotBlank(assigneeId) && photeMap.containsKey(hisTask.getComment().getAssigneeId())){
					hisTaskObject.put("photoUrl", photeMap.get(assigneeId));
				}else{
					hisTaskObject.put("photoUrl", "");
				}
				hisTaskObject.put("operateTime", getTimeSdf().format(hisTask.getComment().getOperateTime()));
				hisTaskObject.put("commentType", hisTask.getComment().getCommentType());
				if(hisTask.getComment().getTextComment()!=null){
					hisTaskObject.put("textComment", hisTask.getComment().getTextComment());
				}else{
					hisTaskObject.put("textComment", "");
				}
				hisTaskCommentArray.add(hisTaskObject);
			}
		}
		return hisTaskCommentArray;
	}
	
	public static JSONArray getMcodeArrays(String mcodeId){
		JSONArray arrays = new JSONArray();
		JSONObject obj = null;
		List<Mcodedetail> mcodeDetails = getMcodedetailService().getMcodeDetails(mcodeId);
		for (Mcodedetail item : mcodeDetails) {
			obj=new JSONObject();
			obj.put("content", item.getContent());
			obj.put("thisId", item.getThisId());
			arrays.add(obj);
		}
		return arrays;
	}
	
	private static SimpleDateFormat getTimeSdf(){
		SimpleDateFormat timeSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return timeSdf;
	}
	
}
