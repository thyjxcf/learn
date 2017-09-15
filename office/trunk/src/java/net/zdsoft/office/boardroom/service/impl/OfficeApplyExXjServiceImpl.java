package net.zdsoft.office.boardroom.service.impl;

import java.util.*;

import net.zdsoft.office.boardroom.entity.OfficeApplyExXj;
import net.zdsoft.office.boardroom.service.OfficeApplyExXjService;
import net.zdsoft.office.boardroom.dao.OfficeApplyExXjDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_apply_ex_xj
 * @author 
 * 
 */
public class OfficeApplyExXjServiceImpl implements OfficeApplyExXjService{
	private OfficeApplyExXjDao officeApplyExXjDao;

	@Override
	public OfficeApplyExXj save(OfficeApplyExXj officeApplyExXj){
		return officeApplyExXjDao.save(officeApplyExXj);
	}

	@Override
	public Integer delete(String[] ids){
		return officeApplyExXjDao.delete(ids);
	}

	@Override
	public Integer update(OfficeApplyExXj officeApplyExXj){
		return officeApplyExXjDao.update(officeApplyExXj);
	}

	@Override
	public OfficeApplyExXj getOfficeApplyExXjById(String id){
		return officeApplyExXjDao.getOfficeApplyExXjById(id);
	}

	@Override
	public Map<String, OfficeApplyExXj> getOfficeApplyExXjMapByIds(String[] ids){
		return officeApplyExXjDao.getOfficeApplyExXjMapByIds(ids);
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjList(){
		return officeApplyExXjDao.getOfficeApplyExXjList();
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjPage(Pagination page){
		return officeApplyExXjDao.getOfficeApplyExXjPage(page);
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjByUnitIdList(String unitId){
		return officeApplyExXjDao.getOfficeApplyExXjByUnitIdList(unitId);
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjByUnitIdPage(String unitId, Pagination page){
		return officeApplyExXjDao.getOfficeApplyExXjByUnitIdPage(unitId, page);
	}

	public void setOfficeApplyExXjDao(OfficeApplyExXjDao officeApplyExXjDao){
		this.officeApplyExXjDao = officeApplyExXjDao;
	}

	@Override
	public void addOfficeApplyExXjs(List<OfficeApplyExXj> officeApplyExXjs) {
		officeApplyExXjDao.addOfficeApplyExXjs(officeApplyExXjs);
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjByApplyId(String unitId,
			String officeBoardroomApplyId) {
		return officeApplyExXjDao.getOfficeApplyExXjByApplyId(unitId, officeBoardroomApplyId);
	}

	@Override
	public String[] getOfficeApplyDetailsXjByIds(String[] applyRoomIds) {
		return officeApplyExXjDao.getOfficeApplyDetailsXjByIds(applyRoomIds);
	}
}
