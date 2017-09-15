package net.zdsoft.eis.base.attachment.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.dao.AttachmentDao;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.exception.FileUploadFailException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * <p>城域综合信息平台</p>
 * <p>CNet3.0</p>
 * <p>Copyright (c) 2003</p>
 * <p>Company: ZDSoft</p>
 * @author taoy
 * @since 1.0
 * @version $Id: AttachmentServiceImpl.java,v 1.2 2007/01/09 11:00:32 chenzy Exp $
 */
public class AttachmentServiceImpl extends DefaultCacheManager implements AttachmentService {
	private Logger log = LoggerFactory.getLogger(getClass());

	private AttachmentDao attachmentDao;
	private SystemIniService systemIniService;
	private StorageFileService storageFileService;

	public void setStorageFileService(StorageFileService storageFileService) {
		this.storageFileService = storageFileService;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void saveAttachment(Attachment attachment) {
		attachmentDao.insertAttachment(attachment);
	}
	
    public void batchInsertAttachment(List<Attachment> list){
    	attachmentDao.batchInsertAttachment(list);
    }

	public void saveAttachment(Attachment attachment, UploadFile uploadedfile)
			throws FileUploadFailException {
		saveAttachment(attachment, uploadedfile, true);
		this.putInCache("dg_Attachment_"+attachment.getId(), attachment);
	}

	public void saveAttachment(Attachment attachment, UploadFile uploadedfile,
			boolean isDeleteUploadedFile) throws FileUploadFailException {
		attachment.setObjectUnitId(attachment.getUnitId());
		storageFileService.saveFile(attachment, uploadedfile,
				isDeleteUploadedFile);

		if (StringUtils.isEmpty(attachment.getExtName())) {
			attachment.setExtName(net.zdsoft.keel.util.FileUtils
					.getExtension(attachment.getFileName()));
		}
		attachmentDao.insertAttachment(attachment);
	}

	public void updateAttachment(Attachment attachment,
			UploadFile uploadedfile, boolean isDeleteUploadedFile)
			throws FileUploadFailException {
		attachment.setObjectUnitId(attachment.getUnitId());
		if (uploadedfile != null && uploadedfile.getFile() != null) {
			storageFileService.saveFile(attachment, uploadedfile,
					isDeleteUploadedFile);
		}
		attachmentDao.updateAttachment(attachment);
	}

	public void deleteAttachments(String[] attachmentIds) {
		try {
			List<Attachment> attachments = attachmentDao
					.getAttachments(attachmentIds);
			for (Attachment attachment : attachments) {
				storageFileService.setDirPath(attachment);
				File file = attachment.getFile();
				FileUtils.forceDelete(file);

				if (log.isDebugEnabled()) {
					log.debug("删除附件: " + attachment.getFileName());
				}
				if(attachment.getConStatus()==BusinessTask.TASK_STATUS_SUCCESS){
					if(StringUtils.isBlank(attachment.getExtName())&& StringUtils.isNotBlank(attachment.getFileName())){
						attachment.setExtName(attachment.getFileName().substring(attachment.getFileName().indexOf(".")+1));
					}
					File f01 = new File(file.getPath().replace(attachment.getExtName(), "pdf"));
					if (f01.exists()){//.pdf
						FileUtils.forceDelete(f01);
					}
					File f02 = new File(file.getPath().replace(attachment.getExtName(), "swf"));
					if (f02.exists()){//.swf
						FileUtils.forceDelete(f02);
					}
					File f03 = new File(file.getPath().replace("."+attachment.getExtName(), "_picture.jpg"));
					if (f03.exists()){//_picture.jpg
						FileUtils.forceDelete(f03);
					}
					File f06 = new File(file.getPath().replace("."+attachment.getExtName(), "_hand.pdf"));
					if (f06.exists()){//_hand.pdf
						FileUtils.forceDelete(f06);
					}
					File f04 = new File(file.getPath().replace("."+attachment.getExtName(), "_preview.jpg"));
					if (f04.exists()){//_preview.jpg
						FileUtils.forceDelete(f04);
					}
					File f05 = new File(file.getPath().replace("."+attachment.getExtName(), "flv"));
					if (f05.exists()){//.flv
						FileUtils.forceDelete(f05);
					}
				}
			}
			attachmentDao.deleteAttachment(attachmentIds);
		} catch (Exception e) {
			log.error("附件删除错误! - " + e.getMessage());
		}
	}

	public Attachment getAttachment(String id) {
		Attachment attach = attachmentDao.getAttachment(id);
		if (attach != null) {
			storageFileService.setDirPath(attach);
		}

		return attach;
	}

	public List<Attachment> getAttachments(Set<String> ids) {
		return attachmentDao.getAttachments(ids.toArray(new String[0]));
	}

	public List<Attachment> getAttachments(final String objectId, final String objectType) {
		return attachmentDao.getAttachments(objectId, objectType);
//		return getEntityFromCache(new CacheEntityParam<List<Attachment>>() {
//            public List<Attachment> fetchObject() {
//                return attachmentDao.getAttachments(objectId, objectType);
//            }
//
//            public String fetchKey() {
//                return "dg_Attachment_" + objectId+objectType;
//            }
//        });
	}

	public List<Attachment> getAttachmentsByUnitId(String unitId,
			String objectType) {
		return attachmentDao.getAttachmentsByUnitId(unitId, objectType);
	}

	public long getAttachmentLimitedSize() {
		SystemIni systemIni = systemIniService
				.getSystemIni(BaseConstant.SYSTEM_SMARTUPLOAD_MAXFILESIZE);

		long attachSize = 0;
		if (null != systemIni) {
			String attachMax = systemIni.getNowValue();
			attachSize = Integer.parseInt(attachMax) * 1024 * 1024;
		}
		return attachSize;
	}

	public Map<String, List<Attachment>> getAttachmentsMap(String... objectIds) {
		return attachmentDao.getAttachmentsMap(objectIds);
	}

	public Map<String, List<Attachment>> getAttachmentsMap(String objectType,
			String... objectIds) {
		return attachmentDao.getAttachmentsMap(objectType, objectIds);
	}
	
	@Override
	public Map<String, Attachment> getAttachmentsByTypesMap(String[] objectTypes) {
		return attachmentDao.getAttachmentsByTypesMap(objectTypes);
	}


	@Override
	public List<Attachment> getAttachmentsByUnitId(String unitId,
			String objectType, String attachmentName) {
		return attachmentDao.getAttachmentsByUnitId(unitId,objectType,attachmentName);
	}
	
	@Override
	public List<Attachment> getAttachmentsByLikeTypeOrName(String unitId,
			String objectType, String attachmentName) {
		return attachmentDao.getAttachmentsByLikeTypeOrName(unitId,objectType,attachmentName);
	}

	@Override
	public void saveAndUpdateAttachmentFile(Attachment attachment,
			UploadFile uploadedfile) throws FileUploadFailException, IOException {
		attachment.setObjectUnitId(attachment.getUnitId());
		attachment.setCustomFilePath(attachment.getFilePath().substring(0, attachment.getFilePath().lastIndexOf(File.separator)));;
		if(StringUtils.isNotEmpty(attachment.getFilePath())){
			
			attachment.setFilePath(attachment.getFilePath().substring(0,attachment.getFilePath().lastIndexOf("."))+".pdf");
		}
		
//		File file
		
		storageFileService.saveFile(attachment, uploadedfile,
				true);
		Attachment oldAttachment = getAttachment(attachment.getId());
		if(oldAttachment!=null){
			String path = oldAttachment.getDirPath()+File.separator+oldAttachment.getFilePath();
			
			String handPdfPath = path.substring(0, path.lastIndexOf('.'))+"_hand.pdf";
			File file = new File(handPdfPath);
			file.createNewFile();
			oldAttachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
			attachmentDao.updateAttachment(oldAttachment);
		}
		
	}

	@Override
	public void updateAttachment(Attachment attachment) {
		attachmentDao.updateAttachment(attachment);
	}
}
