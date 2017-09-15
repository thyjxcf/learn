package net.zdsoft.office.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingAttendDao;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingAttend;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingAttendService;
/**
 * office_work_meeting_attend
 * @author 
 * 
 */
public class OfficeWorkMeetingAttendServiceImpl implements OfficeWorkMeetingAttendService{
	private OfficeWorkMeetingAttendDao officeWorkMeetingAttendDao;

	@Override
	public OfficeWorkMeetingAttend save(OfficeWorkMeetingAttend officeWorkMeetingAttend){
		return officeWorkMeetingAttendDao.save(officeWorkMeetingAttend);
	}

	@Override
	public Integer delete(String[] ids){
		return officeWorkMeetingAttendDao.delete(ids);
	}

	@Override
	public Integer update(OfficeWorkMeetingAttend officeWorkMeetingAttend){
		return officeWorkMeetingAttendDao.update(officeWorkMeetingAttend);
	}

	@Override
	public OfficeWorkMeetingAttend getOfficeWorkMeetingAttendById(String id){
		return officeWorkMeetingAttendDao.getOfficeWorkMeetingAttendById(id);
	}

	@Override
	public Map<String, OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendMapByIds(String[] ids){
		return officeWorkMeetingAttendDao.getOfficeWorkMeetingAttendMapByIds(ids);
	}

	@Override
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendList(){
		return officeWorkMeetingAttendDao.getOfficeWorkMeetingAttendList();
	}

	@Override
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendPage(Pagination page){
		return officeWorkMeetingAttendDao.getOfficeWorkMeetingAttendPage(page);
	}
	@Override
	public Map<String, List<OfficeWorkMeetingAttend>> getOfficeWorkMeetingAttendMap(
			String[] meetingIds) {
		Map<String,List<OfficeWorkMeetingAttend>> attmap = new HashMap<String, List<OfficeWorkMeetingAttend>>();
		List<OfficeWorkMeetingAttend> attlist = officeWorkMeetingAttendDao.getOfficeWorkMeetingAttendList(meetingIds); 
		List<OfficeWorkMeetingAttend> att1;
		for(OfficeWorkMeetingAttend att : attlist){
			if(attmap.containsKey(att.getMeetingId() + "," + att.getType())){
				att1 = attmap.get(att.getMeetingId() + "," + att.getType());
			}else{
				att1 = new ArrayList<OfficeWorkMeetingAttend>();
			}
			att1.add(att);
			attmap.put(att.getMeetingId() + "," + att.getType(), att1);
		}
		return attmap;
	}
	@Override
	public List<String> getMeetingsIds(String userId, String deptId,
			String[] types) {
		return officeWorkMeetingAttendDao.getMeetingsIds(userId,deptId,types);
	}
	public void setOfficeWorkMeetingAttendDao(OfficeWorkMeetingAttendDao officeWorkMeetingAttendDao){
		this.officeWorkMeetingAttendDao = officeWorkMeetingAttendDao;
	}

	@Override
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendListByMeetingIds(
			String[] ids) {
		return officeWorkMeetingAttendDao.getOfficeWorkMeetingAttendByMeetingIds(ids);
	}

	@Override
	public Map<String, OfficeWorkMeetingAttend> getattendMapByMeetingId(
			String[] ids) {
		return officeWorkMeetingAttendDao.getattendMap(ids);
	}
	
	@Override
	public void batchSave(List<OfficeWorkMeetingAttend> meetingsAttendsList) {
		officeWorkMeetingAttendDao.batchSave(meetingsAttendsList);
	}
	@Override
	public List<OfficeWorkMeetingAttend> getMeetingsAttendsListByMeetingIds(
			String[] meetingIds) {
		return officeWorkMeetingAttendDao.getMeetingsAttendsListByMeetingIds(meetingIds);
	}
	@Override
	public void deleteByMeetingId(String meetingId, int type) {
		officeWorkMeetingAttendDao.deleteByMeetingId(meetingId,type);
	}
}
