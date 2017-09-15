package net.zdsoft.office.dailyoffice.service.impl;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.office.dailyoffice.service.OfficeRoomOrderSetService;
import net.zdsoft.office.dailyoffice.dao.OfficeRoomOrderSetDao;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_room_order_set
 * @author 
 * 
 */
public class OfficeRoomOrderSetServiceImpl implements OfficeRoomOrderSetService{
	private OfficeRoomOrderSetDao officeRoomOrderSetDao;
	private McodedetailService mcodedetailService;

	@Override
	public OfficeRoomOrderSet save(OfficeRoomOrderSet officeRoomOrderSet){
		return officeRoomOrderSetDao.save(officeRoomOrderSet);
	}

	@Override
	public Integer delete(String[] ids){
		return officeRoomOrderSetDao.delete(ids);
	}

	@Override
	public Integer update(OfficeRoomOrderSet officeRoomOrderSet){
		return officeRoomOrderSetDao.update(officeRoomOrderSet);
	}

	@Override
	public OfficeRoomOrderSet getOfficeRoomOrderSetById(String id){
		return officeRoomOrderSetDao.getOfficeRoomOrderSetById(id);
	}

	@Override
	public Map<String, OfficeRoomOrderSet> getOfficeRoomOrderSetMapByIds(String[] ids){
		return officeRoomOrderSetDao.getOfficeRoomOrderSetMapByIds(ids);
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetList(){
		return officeRoomOrderSetDao.getOfficeRoomOrderSetList();
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetPage(Pagination page){
		return officeRoomOrderSetDao.getOfficeRoomOrderSetPage(page);
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdList(String unitId){
		return officeRoomOrderSetDao.getOfficeRoomOrderSetByUnitIdList(unitId);
	}

	@Override
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdPage(String unitId, Pagination page){
		return officeRoomOrderSetDao.getOfficeRoomOrderSetByUnitIdPage(unitId, page);
	}
	
	@Override
	public OfficeRoomOrderSet getOfficeRoomOrderSetByType(String unitId, String type){
		List<OfficeRoomOrderSet> list = officeRoomOrderSetDao.getOfficeRoomOrderSetByType(unitId, type);
		OfficeRoomOrderSet item = new OfficeRoomOrderSet();
		if(list != null && list.size() > 0)
			item = list.get(0);
		Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-CDLX", type);
		if(mcodedetail != null)
			item.setName(mcodedetail.getContent());
		return item;
	}

	@Override
	public OfficeRoomOrderSet getOfficeRoomOrderSetByThisId(String thisId,
			String unitId) {
		return officeRoomOrderSetDao.getOfficeRoomOrderSetByThisId(thisId, unitId);
	}
	@Override
	public OfficeRoomOrderSet getOfficeRoomOrderSetBySelect(String unitId) {
		return officeRoomOrderSetDao.getOfficeRoomOrderSetBySelect(unitId);
	}

	public void setOfficeRoomOrderSetDao(OfficeRoomOrderSetDao officeRoomOrderSetDao){
		this.officeRoomOrderSetDao = officeRoomOrderSetDao;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}
}
