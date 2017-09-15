package net.zdsoft.office.msgcenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgFolderDetailDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolderDetail;
import net.zdsoft.office.msgcenter.entity.OfficeMsgRecycle;
import net.zdsoft.office.msgcenter.service.OfficeMsgFolderDetailService;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgRecycleService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;
/**
 * 文件夹详细信息
 * @author 
 * 
 */
public class OfficeMsgFolderDetailServiceImpl implements OfficeMsgFolderDetailService{
	
	private OfficeMsgRecycleService officeMsgRecycleService;
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	
	private OfficeMsgFolderDetailDao officeMsgFolderDetailDao;

	@Override
	public OfficeMsgFolderDetail save(OfficeMsgFolderDetail officeMsgFolderDetail){
		return officeMsgFolderDetailDao.save(officeMsgFolderDetail);
	}
	
	@Override
	public void batchSave(List<OfficeMsgFolderDetail> officeMsgFolderDetails) {
		officeMsgFolderDetailDao.batchSave(officeMsgFolderDetails);
	}
	
	@Override
	public void revertMessage(String id) {
		OfficeMsgFolderDetail officeMsgFolderDetail = officeMsgFolderDetailDao.getOfficeMsgFolderDetailById(id);
		if(Constants.MSG_STATE_DRAFT == officeMsgFolderDetail.getReferenceState() 
				|| Constants.MSG_STATE_SEND == officeMsgFolderDetail.getReferenceState()){
			officeMsgSendingService.revertById(officeMsgFolderDetail.getReferenceId(),officeMsgFolderDetail.getReferenceState());
		}else if(Constants.MSG_STATE_RECEIVE == officeMsgFolderDetail.getReferenceState()){
			officeMsgReceivingService.revertById(officeMsgFolderDetail.getReferenceId());
		}
		officeMsgFolderDetailDao.deleteById(id);
	}
	
	@Override
	public void turnToFolder(String[] ids, String folderId) {
		officeMsgFolderDetailDao.updateFolderIdByIds(ids,folderId);
	}
	
	@Override
	public void turnSingleToFolder(String id, String folderId) {
		officeMsgFolderDetailDao.updateFolderIdByIds(new String[]{id},folderId);
	}

	@Override
	public Integer delete(String[] ids){
		List<OfficeMsgFolderDetail> officeMsgFolderDetails = officeMsgFolderDetailDao.getOfficeMsgFolderDetailList(ids);
		List<OfficeMsgRecycle> officeMsgRecycles = new ArrayList<OfficeMsgRecycle>();
		for(OfficeMsgFolderDetail officeMsgFolderDetail:officeMsgFolderDetails){
			OfficeMsgRecycle officeMsgRecycle = new OfficeMsgRecycle();
			officeMsgRecycle.setDeleteTime(new Date());
			officeMsgRecycle.setIsEmergency(officeMsgFolderDetail.getIsEmergency());
			officeMsgRecycle.setMsgtype(officeMsgFolderDetail.getMsgType());
			officeMsgRecycle.setReferenceId(officeMsgFolderDetail.getId());
			officeMsgRecycle.setCustomFolderId(officeMsgFolderDetail.getFolderId());
			officeMsgRecycle.setSendTime(officeMsgFolderDetail.getSendTime());
			officeMsgRecycle.setState(Constants.MSG_STATE_CUSTOMER);
			officeMsgRecycle.setTitle(officeMsgFolderDetail.getTitle());
			officeMsgRecycle.setUserId(officeMsgFolderDetail.getUserId());
			officeMsgRecycles.add(officeMsgRecycle);
		}
		officeMsgRecycleService.batchSave(officeMsgRecycles);
		return officeMsgFolderDetailDao.delete(ids);
	}
	
	@Override
	public boolean isExist(String folderId) {
		return officeMsgFolderDetailDao.isExist(folderId);
	}
	
	@Override
	public void revertById(String id) {
		officeMsgFolderDetailDao.updateRevertById(id);
	}

	@Override
	public Integer update(OfficeMsgFolderDetail officeMsgFolderDetail){
		return officeMsgFolderDetailDao.update(officeMsgFolderDetail);
	}

	@Override
	public OfficeMsgFolderDetail getOfficeMsgFolderDetailById(String id){
		return officeMsgFolderDetailDao.getOfficeMsgFolderDetailById(id);
	}

	@Override
	public Map<String, OfficeMsgFolderDetail> getOfficeMsgFolderDetailMapByIds(String[] ids){
		return officeMsgFolderDetailDao.getOfficeMsgFolderDetailMapByIds(ids);
	}

	@Override
	public List<OfficeMsgFolderDetail> getOfficeMsgFolderDetailPage(String searchTitle, String folderId, Pagination page){
		return officeMsgFolderDetailDao.getOfficeMsgFolderDetailPage(searchTitle, folderId, page);
	}
	
	public void setOfficeMsgRecycleService(
			OfficeMsgRecycleService officeMsgRecycleService) {
		this.officeMsgRecycleService = officeMsgRecycleService;
	}
	
	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public void setOfficeMsgFolderDetailDao(OfficeMsgFolderDetailDao officeMsgFolderDetailDao){
		this.officeMsgFolderDetailDao = officeMsgFolderDetailDao;
	}
}