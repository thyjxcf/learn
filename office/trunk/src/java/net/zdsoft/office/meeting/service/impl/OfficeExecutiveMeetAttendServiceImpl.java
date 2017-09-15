package net.zdsoft.office.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveMeetAttendDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetAttend;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetAttendService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
/**
 * office_executive_meet_attend
 * @author 
 * 
 */
public class OfficeExecutiveMeetAttendServiceImpl implements OfficeExecutiveMeetAttendService{
	
	private DeptService deptService;
	
	private OfficeExecutiveMeetAttendDao officeExecutiveMeetAttendDao;

	@Override
	public OfficeExecutiveMeetAttend save(OfficeExecutiveMeetAttend officeExecutiveMeetAttend){
		return officeExecutiveMeetAttendDao.save(officeExecutiveMeetAttend);
	}
	
	@Override
	public void batchSave(List<OfficeExecutiveMeetAttend> list) {
		officeExecutiveMeetAttendDao.batchSave(list);
	}
	
	@Override
	public void deleteByMeetId(String meetId) {
		officeExecutiveMeetAttendDao.deleteByMeetId(meetId);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExecutiveMeetAttendDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExecutiveMeetAttend officeExecutiveMeetAttend){
		return officeExecutiveMeetAttendDao.update(officeExecutiveMeetAttend);
	}

	@Override
	public OfficeExecutiveMeetAttend getOfficeExecutiveMeetAttendById(String id){
		return officeExecutiveMeetAttendDao.getOfficeExecutiveMeetAttendById(id);
	}

	@Override
	public Map<String, OfficeExecutiveMeetAttend> getOfficeExecutiveMeetAttendMapByIds(String[] ids){
		return officeExecutiveMeetAttendDao.getOfficeExecutiveMeetAttendMapByIds(ids);
	}

	@Override
	public List<OfficeExecutiveMeetAttend> getOfficeExecutiveMeetAttendPage(Pagination page){
		return officeExecutiveMeetAttendDao.getOfficeExecutiveMeetAttendPage(page);
	}
	
	@Override
	public List<String> getMeetIds(String unitId, String[] objIds) {
		List<String> meetIdList = officeExecutiveMeetAttendDao.getMeetIds(unitId,objIds);
		return meetIdList;
	}
	
	@Override
	public Map<String, String> getAttendDeptMap(String unitId, String[] meetIds) {
		List<OfficeExecutiveMeetAttend> meetAttends = officeExecutiveMeetAttendDao.getOfficeExecutiveMeetAttendList(unitId, meetIds);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		Map<String, String> meetAttendMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(meetAttends)){
			for(OfficeExecutiveMeetAttend meetAttend:meetAttends){
				if(meetAttend.getType() == Constants.ATTEND_DEPT){
					if(deptMap.containsKey(meetAttend.getObjectId())){
						String deptName = deptMap.get(meetAttend.getObjectId()).getDeptname();
						if(meetAttendMap.containsKey(meetAttend.getMeetingId())){
							meetAttendMap.put(meetAttend.getMeetingId(), meetAttendMap.get(meetAttend.getMeetingId())+","+deptName);
						}else{
							meetAttendMap.put(meetAttend.getMeetingId(), deptName);
						}
					}
				}
			}
		}
		return meetAttendMap;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setOfficeExecutiveMeetAttendDao(OfficeExecutiveMeetAttendDao officeExecutiveMeetAttendDao){
		this.officeExecutiveMeetAttendDao = officeExecutiveMeetAttendDao;
	}
}