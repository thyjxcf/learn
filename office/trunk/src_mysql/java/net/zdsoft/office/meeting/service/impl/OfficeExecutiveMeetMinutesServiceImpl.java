package net.zdsoft.office.meeting.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveMeetMinutesDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetMinutes;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetMinutesService;
/**
 * office_executive_meet_minutes
 * @author 
 * 
 */
public class OfficeExecutiveMeetMinutesServiceImpl implements OfficeExecutiveMeetMinutesService{
	
	private DeptService deptService;
	private OfficeExecutiveMeetMinutesDao officeExecutiveMeetMinutesDao;

	@Override
	public OfficeExecutiveMeetMinutes save(OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes){
		return officeExecutiveMeetMinutesDao.save(officeExecutiveMeetMinutes);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExecutiveMeetMinutesDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes){
		return officeExecutiveMeetMinutesDao.update(officeExecutiveMeetMinutes);
	}

	@Override
	public OfficeExecutiveMeetMinutes getOfficeExecutiveMeetMinutesById(String id){
		return officeExecutiveMeetMinutesDao.getOfficeExecutiveMeetMinutesById(id);
	}

	@Override
	public Map<String, OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesMapByIds(String[] ids){
		return officeExecutiveMeetMinutesDao.getOfficeExecutiveMeetMinutesMapByIds(ids);
	}

	@Override
	public List<OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesList(String unitId, String deptId, String meetId){
		List<OfficeExecutiveMeetMinutes> list = officeExecutiveMeetMinutesDao.getOfficeExecutiveMeetMinutesList(deptId, meetId);
		if(CollectionUtils.isNotEmpty(list)){
			Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
			for(OfficeExecutiveMeetMinutes minutes:list){
				StringBuffer sbf = new StringBuffer();
				String[] deptIds = minutes.getDeptIds().split(",");
				for(String deptId_:deptIds){
					if(deptMap.containsKey(deptId_)){
						sbf.append(deptMap.get(deptId_).getDeptname()+",");
					}
				}
				minutes.setDeptNames(sbf.toString().substring(0, sbf.toString().length()-1));
			}
		}
		return list;
	}

	@Override
	public List<OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesPage(Pagination page){
		return officeExecutiveMeetMinutesDao.getOfficeExecutiveMeetMinutesPage(page);
	}
	
	@Override
	public void batchSave(String meetId, List<OfficeExecutiveMeetMinutes> list) {
		officeExecutiveMeetMinutesDao.deleteByMeetId(meetId);
		officeExecutiveMeetMinutesDao.batchSave(list);
	}
	
	@Override
	public Map<String, String> getMinutesMap(String unitId, String[] meetIds, String deptId) {
		return officeExecutiveMeetMinutesDao.getMinutesMap(unitId, meetIds, deptId);
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setOfficeExecutiveMeetMinutesDao(OfficeExecutiveMeetMinutesDao officeExecutiveMeetMinutesDao){
		this.officeExecutiveMeetMinutesDao = officeExecutiveMeetMinutesDao;
	}
}