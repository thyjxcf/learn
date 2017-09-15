package net.zdsoft.office.msgcenter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgFolderDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolder;
import net.zdsoft.office.msgcenter.service.OfficeMsgFolderService;
/**
 * 信息文件夹
 * @author 
 * 
 */
public class OfficeMsgFolderServiceImpl implements OfficeMsgFolderService{
	private OfficeMsgFolderDao officeMsgFolderDao;

	@Override
	public OfficeMsgFolder save(OfficeMsgFolder officeMsgFolder){
		officeMsgFolder.setCreationTime(new Date());
		return officeMsgFolderDao.save(officeMsgFolder);
	}
	
	@Override
	public boolean isExist(String id, String userId, String folderName) {
		// TODO Auto-generated method stub
		return officeMsgFolderDao.isExist(id, userId, folderName);
	}

	@Override
	public Integer delete(String[] ids){
		return officeMsgFolderDao.delete(ids);
	}

	@Override
	public Integer update(OfficeMsgFolder officeMsgFolder){
		return officeMsgFolderDao.update(officeMsgFolder);
	}

	@Override
	public OfficeMsgFolder getOfficeMsgFolderById(String id){
		return officeMsgFolderDao.getOfficeMsgFolderById(id);
	}

	@Override
	public Map<String, OfficeMsgFolder> getOfficeMsgFolderMapByIds(String[] ids){
		return officeMsgFolderDao.getOfficeMsgFolderMapByIds(ids);
	}

	@Override
	public List<OfficeMsgFolder> getOfficeMsgFolderList(String userId){
		return officeMsgFolderDao.getOfficeMsgFolderList(userId);
	}

	@Override
	public List<OfficeMsgFolder> getOfficeMsgFolderPage(Pagination page){
		return officeMsgFolderDao.getOfficeMsgFolderPage(page);
	}

	public void setOfficeMsgFolderDao(OfficeMsgFolderDao officeMsgFolderDao){
		this.officeMsgFolderDao = officeMsgFolderDao;
	}
}