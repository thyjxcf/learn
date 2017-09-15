package net.zdsoft.eis.base.attachment.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.attachment.entity.Attachment;

/**
 * @author taoy
 * @since 1.0
 * @version $Id: AttachmentDao.java,v 1.2 2007/01/09 11:00:41 chenzy Exp $
 */
public interface AttachmentDao {
    /**
     * 增加附件
     * 
     * @param attachment
     */
    public void insertAttachment(Attachment attachment);
    
    /**
     * 批量新增附件
     * @param list
     */
    public void batchInsertAttachment(List<Attachment> list);
    
    /**
     * 更新附件
     * 
     * @param attachment
     */
    public void updateAttachment(Attachment attachment);

    /**
     * 删除附件
     * 
     * @param attachmentIds
     */
    public void deleteAttachment(String[] attachmentIds);

    /**
     * 获取附件信息
     * 
     * @param id
     * @return
     */
    public Attachment getAttachment(String id);

    /**
     * 获取附件信息
     * 
     * @param ids
     * @return
     */
    public List<Attachment> getAttachments(String[] ids);
    
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
     * 获取附件
     * 
     * @param attachmentIds
     * @return
     */
    public Map<String, Attachment> getAttachmentMap(String[] attachmentIds);
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
     * @param researchtype
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
}
