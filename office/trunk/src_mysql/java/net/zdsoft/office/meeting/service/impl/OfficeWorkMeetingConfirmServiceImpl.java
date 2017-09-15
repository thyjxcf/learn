package net.zdsoft.office.meeting.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingConfirmDao;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingConfirm;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingConfirmService;

import org.apache.commons.lang.StringUtils;
/**
 * office_work_meeting_confirm
 * @author 
 * 
 */
public class OfficeWorkMeetingConfirmServiceImpl implements OfficeWorkMeetingConfirmService{
	private OfficeWorkMeetingConfirmDao officeWorkMeetingConfirmDao;

	@Override
	public void save(OfficeWorkMeetingConfirm meetingsConfirm){
		boolean flag = officeWorkMeetingConfirmDao.isExist(meetingsConfirm.getMeetingId(),meetingsConfirm.getAttendUserId());
		if(flag){
			String id = officeWorkMeetingConfirmDao.getConfirmId(meetingsConfirm.getMeetingId(), meetingsConfirm.getAttendUserId());
			meetingsConfirm.setId(id);
			officeWorkMeetingConfirmDao.update(meetingsConfirm);
		}else{
			meetingsConfirm.setCreateTime(new Date());
			officeWorkMeetingConfirmDao.save(meetingsConfirm);
		}
	}

	@Override
	public Integer delete(String[] ids){
		return officeWorkMeetingConfirmDao.delete(ids);
	}

	@Override
	public Integer update(OfficeWorkMeetingConfirm officeWorkMeetingConfirm){
		return officeWorkMeetingConfirmDao.update(officeWorkMeetingConfirm);
	}

	@Override
	public OfficeWorkMeetingConfirm getOfficeWorkMeetingConfirmById(String id){
		return officeWorkMeetingConfirmDao.getOfficeWorkMeetingConfirmById(id);
	}

	@Override
	public Map<String, OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmMapByIds(String[] ids){
		return officeWorkMeetingConfirmDao.getOfficeWorkMeetingConfirmMapByIds(ids);
	}

	@Override
	public List<OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmList(){
		return officeWorkMeetingConfirmDao.getOfficeWorkMeetingConfirmList();
	}

	@Override
	public List<OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmPage(Pagination page){
		return officeWorkMeetingConfirmDao.getOfficeWorkMeetingConfirmPage(page);
	}
	
	@Override
	public List<String> getMeetingsIdByUserId(String userId) {
		return officeWorkMeetingConfirmDao.getMeetingsIdByUserId(userId);
	}
	@Override
	public Map<String, String> getTransferMapByMeetingId(String meetingId) {
		return officeWorkMeetingConfirmDao.getTransferMapByMeetingsId(meetingId);
	}
	public void setOfficeWorkMeetingConfirmDao(OfficeWorkMeetingConfirmDao officeWorkMeetingConfirmDao){
		this.officeWorkMeetingConfirmDao = officeWorkMeetingConfirmDao;
	}
	
	@Override
	public boolean isTransfered(String meetingId, String transferUserId) {
		return officeWorkMeetingConfirmDao.isTransfered(meetingId, transferUserId);
	}
	
	@Override
	public boolean isHeTransfered(String meetingsId, String transferUserId,
			String loginUserId) {
		boolean flag = false;
		Map<String, String> transferMap = officeWorkMeetingConfirmDao.getTransferMapByMeetingsId(meetingsId);
		String userId = "";
		for(int i=0;i<=transferMap.size();i++){
			userId = transferMap.get(loginUserId);
			if(StringUtils.isBlank(userId)){
				break;
			}else if(transferUserId.equals(userId)){
				flag = true;
				break;
			}
			loginUserId = userId;
		}
		return flag;
	}
	@Override
	public void saveTransfer(OfficeWorkMeetingConfirm meetingsConfirm) {
		//保存转交人信息
		boolean flag = officeWorkMeetingConfirmDao.isExist(meetingsConfirm.getMeetingId(),meetingsConfirm.getAttendUserId());
		if(flag){
			String id = officeWorkMeetingConfirmDao.getConfirmId(meetingsConfirm.getMeetingId(),meetingsConfirm.getAttendUserId());
			meetingsConfirm.setId(id);
			officeWorkMeetingConfirmDao.update(meetingsConfirm);
		}else{
			meetingsConfirm.setCreateTime(new Date());
			officeWorkMeetingConfirmDao.save(meetingsConfirm);
		}
		//处理被转交人信息
		flag = officeWorkMeetingConfirmDao.isExist(meetingsConfirm.getMeetingId(),meetingsConfirm.getTransferUserId());
		if(!flag){
			OfficeWorkMeetingConfirm mc = new OfficeWorkMeetingConfirm();
			mc.setMeetingId(meetingsConfirm.getMeetingId());
			mc.setAttendUserId(meetingsConfirm.getTransferUserId());
			mc.setAttendType(OfficeWorkMeetingConfirm.TYPE_ZERO);
			mc.setCreateTime(new Date());
			officeWorkMeetingConfirmDao.save(mc);
		}
	}
	@Override
	public Map<String, OfficeWorkMeetingConfirm> getMeetingsConfirmMapByMeetingsId(
			String meetingId) {
		return officeWorkMeetingConfirmDao.getMeetingsConfirmMapByMeetingsId(meetingId);
	}
	@Override
	public Map<String, OfficeWorkMeetingConfirm> getMeetingConfirmMapByMeetingId(
			String meetingId) {
		return officeWorkMeetingConfirmDao.getMeetingConfirmMapByMeetingId(meetingId);
	}
}
