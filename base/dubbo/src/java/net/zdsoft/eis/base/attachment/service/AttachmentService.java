package net.zdsoft.eis.base.attachment.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.FileUploadFailException;

/**
 * 公文附件管理接口类
 * 
 * @author taoy
 * @since 1.0
 * @version $Id: AttachmentService.java,v 1.2 2007/01/09 11:00:40 chenzy Exp $
 */
public interface AttachmentService {

    /**
     * 保存附件
     * 
     * @param attachment
     * @return
     */
    public void saveAttachment(Attachment attachment);
    
    /**
     * 批量新增附件
     * @param list
     */
    public void batchInsertAttachment(List<Attachment> list);

    /**
     * 保存附件和附件文件，并删除临时文件uploadedfile
     * 
     * @param attachment
     * @param uploadedfile
     * @return
     * @throws IOException
     */
    public void saveAttachment(Attachment attachment, UploadFile uploadedfile)
            throws FileUploadFailException;

    /**
     * 保存附件和附件文件
     * 
     * @param attachment
     * @param uploadedfile
     * @param isDeleteUploadedFile 是否删除上传文件
     * @return
     * @throws IOException
     */
    public void saveAttachment(Attachment attachment, UploadFile uploadedfile,
            boolean isDeleteUploadedFile) throws FileUploadFailException;
    
    /**
     * 更新附件和附件文件
     * 
     * @param attachment
     * @param uploadedfile
     * @param isDeleteUploadedFile 是否删除上传文件
     * @return
     * @throws IOException
     */
    public void updateAttachment(Attachment attachment, UploadFile uploadedfile,
            boolean isDeleteUploadedFile) throws FileUploadFailException;
    
    /**
     * 删除附件和附件文件
     * 
     * @param id
     */
    public void deleteAttachments(String[] ids);

    /**
     * 获得附件
     * 
     * @param id 附件id
     * @return
     */
    public Attachment getAttachment(String id);

    /**
     * 批量获得附件
     * 
     * @param ids
     * @return
     */
    public List<Attachment> getAttachments(Set<String> ids);
    
    /**
     * 获取附件
     * @param objectId
     * @param objectType
     * @return
     */
    public List<Attachment> getAttachments(String objectId, String objectType);
    
    /**
     * 获取单位附件
     * @param unitId
     * @param objectType
     * @return
     */
    public List<Attachment> getAttachmentsByUnitId(String unitId, String objectType);

    /**
     * 取附件限制大小
     * 
     * @return
     */
    public long getAttachmentLimitedSize();
    /**
     * 获取附件
     * @param objectIds
     * @return
     */
    public Map<String, List<Attachment>> getAttachmentsMap(String... objectIds);
    
    /**
     * 获取附件
     * @param objectType
     * @param objectIds
     * @return
     */
    public Map<String, List<Attachment>> getAttachmentsMap(String objectType,String... objectIds);
    
    /**
     * 获取附件
     * @param objectTypes
     * @return
     */
    public Map<String, Attachment> getAttachmentsByTypesMap(String[] objectTypes);

    /**
     * 获取附件
     * @param unitId
     * @param objectType
     * @param attachmentName
     * @return
     */
	public List<Attachment> getAttachmentsByUnitId(String unitId,
			String objectType, String attachmentName);
	/**
	 * 
	 * @param unitId
	 * @param objectType
	 * @param attachmentName
	 * @return
	 */
	public List<Attachment> getAttachmentsByLikeTypeOrName(String unitId,
			String objectType, String attachmentName);
	

    /**
     * 保存附件文件，并删除临时文件uploadedfile
     * 更新相关信息
     * @param attachment
     * @param uploadedfile
     * @return
     * @throws IOException
     */
    public void saveAndUpdateAttachmentFile(Attachment attachment, UploadFile uploadedfile)
            throws FileUploadFailException,IOException;
    
    /**
     * 更新附件 不更新文件
     * @param attachment
     */
    public void updateAttachment(Attachment attachment);

}
