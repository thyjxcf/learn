package net.zdsoft.office.studentLeave.service.impl;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeLeaveTypeDao;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;
import net.zdsoft.office.studentLeave.service.OfficeLeaveTypeService;
/**
 * office_leave_type
 * @author 
 * 
 */
public class OfficeLeaveTypeServiceImpl implements OfficeLeaveTypeService{
	private OfficeLeaveTypeDao officeLeaveTypeDao;

	@Override
	public OfficeLeaveType save(OfficeLeaveType officeLeaveType){
		return officeLeaveTypeDao.save(officeLeaveType);
	}

	@Override
	public Integer delete(String[] ids){
		return officeLeaveTypeDao.delete(ids);
	}

	@Override
	public Integer update(OfficeLeaveType officeLeaveType){
		return officeLeaveTypeDao.update(officeLeaveType);
	}

	@Override
	public OfficeLeaveType getOfficeLeaveTypeById(String id){
		return officeLeaveTypeDao.getOfficeLeaveTypeById(id);
	}

	@Override
	public Map<String, OfficeLeaveType> getOfficeLeaveTypeMapByIds(String[] ids){
		return officeLeaveTypeDao.getOfficeLeaveTypeMapByIds(ids);
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypeList(){
		return officeLeaveTypeDao.getOfficeLeaveTypeList();
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypePage(Pagination page){
		return officeLeaveTypeDao.getOfficeLeaveTypePage(page);
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdList(String unitId, Integer state){
		return officeLeaveTypeDao.getOfficeLeaveTypeByUnitIdList(unitId, state);
	}

	@Override
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdPage(String unitId, Pagination page){
		return officeLeaveTypeDao.getOfficeLeaveTypeByUnitIdPage(unitId, page);
	}

	@Override
	public Map<String,OfficeLeaveType> getLeaveTypeNameByLeaveIds(String[] leaveIds) {
		return officeLeaveTypeDao.getleaveTypeNameByLeaveIds(leaveIds);
	}
	
	public void setOfficeLeaveTypeDao(OfficeLeaveTypeDao officeLeaveTypeDao){
		this.officeLeaveTypeDao = officeLeaveTypeDao;
	}

}
