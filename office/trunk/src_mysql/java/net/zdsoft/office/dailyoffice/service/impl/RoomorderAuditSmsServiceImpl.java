package net.zdsoft.office.dailyoffice.service.impl;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.RoomorderAuditSms;
import net.zdsoft.office.dailyoffice.service.RoomorderAuditSmsService;
import net.zdsoft.office.dailyoffice.dao.RoomorderAuditSmsDao;
import net.zdsoft.keel.util.Pagination;
/**
 * roomorder_audit_sms
 * @author 
 * 
 */
public class RoomorderAuditSmsServiceImpl implements RoomorderAuditSmsService{
	private RoomorderAuditSmsDao roomorderAuditSmsDao;

	@Override
	public RoomorderAuditSms save(RoomorderAuditSms roomorderAuditSms){
		return roomorderAuditSmsDao.save(roomorderAuditSms);
	}

	@Override
	public Integer delete(String[] ids){
		return roomorderAuditSmsDao.delete(ids);
	}
	
	@Override
	public Integer deleteByUserId(String unitId, String userId){
		return roomorderAuditSmsDao.deleteByUserId(unitId, userId);
	}

	@Override
	public Integer update(RoomorderAuditSms roomorderAuditSms){
		return roomorderAuditSmsDao.update(roomorderAuditSms);
	}

	@Override
	public RoomorderAuditSms getRoomorderAuditSmsById(String id){
		return roomorderAuditSmsDao.getRoomorderAuditSmsById(id);
	}

	@Override
	public Map<String, RoomorderAuditSms> getRoomorderAuditSmsMapByIds(String[] ids){
		return roomorderAuditSmsDao.getRoomorderAuditSmsMapByIds(ids);
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsList(){
		return roomorderAuditSmsDao.getRoomorderAuditSmsList();
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsPage(Pagination page){
		return roomorderAuditSmsDao.getRoomorderAuditSmsPage(page);
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsByUnitIdList(String unitId){
		return roomorderAuditSmsDao.getRoomorderAuditSmsByUnitIdList(unitId);
	}

	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsByUnitIdPage(String unitId, Pagination page){
		return roomorderAuditSmsDao.getRoomorderAuditSmsByUnitIdPage(unitId, page);
	}
	
	@Override
	public RoomorderAuditSms getRoomorderAuditSmsByUserId(String unitId, String userId){
		return roomorderAuditSmsDao.getRoomorderAuditSmsByUserId(unitId, userId);
	}
	
	@Override
	public List<RoomorderAuditSms> getRoomorderAuditSmsList(String unitId){
		return roomorderAuditSmsDao.getRoomorderAuditSmsList(unitId);
	}

	public void setRoomorderAuditSmsDao(RoomorderAuditSmsDao roomorderAuditSmsDao){
		this.roomorderAuditSmsDao = roomorderAuditSmsDao;
	}
}
