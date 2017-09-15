package net.zdsoft.office.msgcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgRecycleDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgRecycle;
import net.zdsoft.office.msgcenter.service.OfficeMsgFolderDetailService;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgRecycleService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;
/**
 * 信息回收
 * @author 
 * 
 */
public class OfficeMsgRecycleServiceImpl implements OfficeMsgRecycleService{
	
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private OfficeMsgFolderDetailService officeMsgFolderDetailService;
	
	private OfficeMsgRecycleDao officeMsgRecycleDao;

	@Override
	public OfficeMsgRecycle save(OfficeMsgRecycle officeMsgRecycle){
		return officeMsgRecycleDao.save(officeMsgRecycle);
	}
	
	@Override
	public void batchSave(List<OfficeMsgRecycle> officeMsgRecycles) {
		officeMsgRecycleDao.batchSave(officeMsgRecycles);
	}

	@Override
	public Integer delete(String[] ids){
		return officeMsgRecycleDao.delete(ids);
	}
	
	@Override
	public void turnToFolder(String userId, String[] ids, String folderId) {
		List<OfficeMsgRecycle> officeMsgRecycles = officeMsgRecycleDao.getOfficeMsgRecycleList(ids);
		List<String> receiveIds = new ArrayList<String>();
		List<String> sendIds = new ArrayList<String>();
		List<String> draftIds = new ArrayList<String>();
		List<String> folderDetailIds = new ArrayList<String>();
		for(OfficeMsgRecycle officeMsgRecycle:officeMsgRecycles){
			if(Constants.MSG_STATE_DRAFT == officeMsgRecycle.getState()){
				draftIds.add(officeMsgRecycle.getReferenceId());
			}else if(Constants.MSG_STATE_SEND == officeMsgRecycle.getState()){
				sendIds.add(officeMsgRecycle.getReferenceId());
			}else if(Constants.MSG_STATE_RECEIVE == officeMsgRecycle.getState()){
				receiveIds.add(officeMsgRecycle.getReferenceId());
			}else if(Constants.MSG_STATE_CUSTOMER == officeMsgRecycle.getState()){
				folderDetailIds.add(officeMsgRecycle.getReferenceId());
			}
		}
		if(receiveIds.size()>0){
			officeMsgReceivingService.turnToFolderFromDraft(receiveIds.toArray(new String[0]),folderId);
		}else if(sendIds.size()>0){
			officeMsgSendingService.turnToFolder(sendIds.toArray(new String[0]),folderId,Constants.MSG_STATE_SEND,false);
		}else if(draftIds.size()>0){
			officeMsgSendingService.turnToFolder(draftIds.toArray(new String[0]),folderId,Constants.MSG_STATE_DRAFT,false);
		}else if(folderDetailIds.size()>0){
			officeMsgFolderDetailService.turnToFolder(folderDetailIds.toArray(new String[0]),folderId);
		}
		officeMsgRecycleDao.delete(ids);
	}
	
	@Override
	public void turnSingleToFolder(String id, String folderId) {
		OfficeMsgRecycle officeMsgRecycle = officeMsgRecycleDao.getOfficeMsgRecycleById(id);
		if(Constants.MSG_STATE_DRAFT == officeMsgRecycle.getState() || Constants.MSG_STATE_SEND == officeMsgRecycle.getState()){
			officeMsgSendingService.turnSingleToFolder(officeMsgRecycle.getReferenceId(),folderId, officeMsgRecycle.getState(), false);
		}else if(Constants.MSG_STATE_RECEIVE == officeMsgRecycle.getState()){
			officeMsgReceivingService.turnSingleToFolder(officeMsgRecycle.getReferenceId(),folderId, officeMsgRecycle.getState(), false);
		}else if(Constants.MSG_STATE_CUSTOMER == officeMsgRecycle.getState()){
			officeMsgFolderDetailService.turnSingleToFolder(officeMsgRecycle.getReferenceId(),folderId);
		}
		officeMsgRecycleDao.delete(new String[]{id});
	}

	@Override
	public Integer update(OfficeMsgRecycle officeMsgRecycle){
		return officeMsgRecycleDao.update(officeMsgRecycle);
	}

	@Override
	public OfficeMsgRecycle getOfficeMsgRecycleById(String id){
		return officeMsgRecycleDao.getOfficeMsgRecycleById(id);
	}

	@Override
	public Map<String, OfficeMsgRecycle> getOfficeMsgRecycleMapByIds(String[] ids){
		return officeMsgRecycleDao.getOfficeMsgRecycleMapByIds(ids);
	}

	@Override
	public List<OfficeMsgRecycle> getOfficeMsgRecyclePage(String userId, String searchTitle, Pagination page){
		return officeMsgRecycleDao.getOfficeMsgRecyclePage(userId, searchTitle, page);
	}
	
	@Override
	public void revertMessage(String id) {
		OfficeMsgRecycle officeMsgRecycle = officeMsgRecycleDao.getOfficeMsgRecycleById(id);
		if(Constants.MSG_STATE_DRAFT == officeMsgRecycle.getState() 
				|| Constants.MSG_STATE_SEND == officeMsgRecycle.getState()){
			officeMsgSendingService.revertById(officeMsgRecycle.getReferenceId(),officeMsgRecycle.getState());
		}else if(Constants.MSG_STATE_RECEIVE == officeMsgRecycle.getState()){
			officeMsgReceivingService.revertById(officeMsgRecycle.getReferenceId());
		}else if(Constants.MSG_STATE_CUSTOMER == officeMsgRecycle.getState()){
			officeMsgFolderDetailService.revertById(officeMsgRecycle.getReferenceId());
		}
		officeMsgRecycleDao.delete(new String[]{id});
	}
	
	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public void setOfficeMsgFolderDetailService(
			OfficeMsgFolderDetailService officeMsgFolderDetailService) {
		this.officeMsgFolderDetailService = officeMsgFolderDetailService;
	}

	public void setOfficeMsgRecycleDao(OfficeMsgRecycleDao officeMsgRecycleDao){
		this.officeMsgRecycleDao = officeMsgRecycleDao;
	}
}