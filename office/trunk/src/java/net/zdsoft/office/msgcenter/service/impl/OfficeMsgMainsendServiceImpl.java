package net.zdsoft.office.msgcenter.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgMainsendDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgMainsend;
import net.zdsoft.office.msgcenter.service.OfficeMsgMainsendService;
/**
 * 信息主送信息表
 * @author 
 * 
 */
public class OfficeMsgMainsendServiceImpl implements OfficeMsgMainsendService{
	private OfficeMsgMainsendDao officeMsgMainsendDao;

	@Override
	public OfficeMsgMainsend save(OfficeMsgMainsend officeMsgMainsend){
		return officeMsgMainsendDao.save(officeMsgMainsend);
	}
	
	@Override
	public void batchSave(List<OfficeMsgMainsend> officeMsgMainsends) {
		officeMsgMainsendDao.batchSave(officeMsgMainsends);
	}

	@Override
	public Integer delete(String[] ids){
		return officeMsgMainsendDao.delete(ids);
	}
	
	@Override
	public void deleteByMessageId(String messageId) {
		officeMsgMainsendDao.deleteByMessageId(messageId);
	}

	@Override
	public Integer update(OfficeMsgMainsend officeMsgMainsend){
		return officeMsgMainsendDao.update(officeMsgMainsend);
	}

	@Override
	public OfficeMsgMainsend getOfficeMsgMainsendById(String id){
		return officeMsgMainsendDao.getOfficeMsgMainsendById(id);
	}

	@Override
	public Map<String, OfficeMsgMainsend> getOfficeMsgMainsendMapByIds(String[] ids){
		return officeMsgMainsendDao.getOfficeMsgMainsendMapByIds(ids);
	}

	@Override
	public List<OfficeMsgMainsend> getOfficeMsgMainsendList(String messageId){
		return officeMsgMainsendDao.getOfficeMsgMainsendList(messageId);
	}
	@Override
	public List<OfficeMsgMainsend> getOfficeMsgMainsendLists(String[] messageIds){
		return officeMsgMainsendDao.getOfficeMsgMainsendLists(messageIds);
	}

	@Override
	public List<OfficeMsgMainsend> getOfficeMsgMainsendPage(Pagination page){
		return officeMsgMainsendDao.getOfficeMsgMainsendPage(page);
	}

	public void setOfficeMsgMainsendDao(OfficeMsgMainsendDao officeMsgMainsendDao){
		this.officeMsgMainsendDao = officeMsgMainsendDao;
	}
}