package net.zdsoft.office.meeting.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveFixedDeptDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveFixedDept;
import net.zdsoft.office.meeting.service.OfficeExecutiveFixedDeptService;
/**
 * office_executive_fixed_dept
 * @author 
 * 
 */
public class OfficeExecutiveFixedDeptServiceImpl implements OfficeExecutiveFixedDeptService{
	private OfficeExecutiveFixedDeptDao officeExecutiveFixedDeptDao;

	@Override
	public OfficeExecutiveFixedDept save(OfficeExecutiveFixedDept officeExecutiveFixedDept){
		return officeExecutiveFixedDeptDao.save(officeExecutiveFixedDept);
	}
	
	@Override
	public void batchUpdate(String unitId, String deptIds) {
		String[] deptIds_ = deptIds.split(",");
		List<OfficeExecutiveFixedDept> list = new ArrayList<OfficeExecutiveFixedDept>();
		for(String deptId:deptIds_){
			OfficeExecutiveFixedDept oefd = new OfficeExecutiveFixedDept();
			oefd.setUnitId(unitId);
			oefd.setDeptId(deptId);
			list.add(oefd);
		}
		officeExecutiveFixedDeptDao.deleteByUnitId(unitId);
		officeExecutiveFixedDeptDao.batchSave(list);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExecutiveFixedDeptDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExecutiveFixedDept officeExecutiveFixedDept){
		return officeExecutiveFixedDeptDao.update(officeExecutiveFixedDept);
	}

	@Override
	public OfficeExecutiveFixedDept getOfficeExecutiveFixedDeptById(String id){
		return officeExecutiveFixedDeptDao.getOfficeExecutiveFixedDeptById(id);
	}

	@Override
	public Map<String, OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptMapByIds(String[] ids){
		return officeExecutiveFixedDeptDao.getOfficeExecutiveFixedDeptMapByIds(ids);
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptList(){
		return officeExecutiveFixedDeptDao.getOfficeExecutiveFixedDeptList();
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptPage(Pagination page){
		return officeExecutiveFixedDeptDao.getOfficeExecutiveFixedDeptPage(page);
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptByUnitIdList(String unitId){
		return officeExecutiveFixedDeptDao.getOfficeExecutiveFixedDeptByUnitIdList(unitId);
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptByUnitIdPage(String unitId, Pagination page){
		return officeExecutiveFixedDeptDao.getOfficeExecutiveFixedDeptByUnitIdPage(unitId, page);
	}
	
	@Override
	public boolean isFixedDept(String deptId) {
		return officeExecutiveFixedDeptDao.isFixedDept(deptId);
	}

	public void setOfficeExecutiveFixedDeptDao(OfficeExecutiveFixedDeptDao officeExecutiveFixedDeptDao){
		this.officeExecutiveFixedDeptDao = officeExecutiveFixedDeptDao;
	}
}