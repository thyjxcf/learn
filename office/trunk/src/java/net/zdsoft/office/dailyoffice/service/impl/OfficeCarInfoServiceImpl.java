package net.zdsoft.office.dailyoffice.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarInfoDao;
import net.zdsoft.office.dailyoffice.entity.OfficeCarInfo;
import net.zdsoft.office.dailyoffice.service.OfficeCarInfoService;
/**
 * office_car_info
 * @author 
 * 
 */
public class OfficeCarInfoServiceImpl implements OfficeCarInfoService{
	private OfficeCarInfoDao officeCarInfoDao;

	@Override
	public OfficeCarInfo save(OfficeCarInfo officeCarInfo){
		return officeCarInfoDao.save(officeCarInfo);
	}
	
	@Override
	public void delete(String id) {
		officeCarInfoDao.delete(id);
	}

	@Override
	public void delete(String[] ids){
		officeCarInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeCarInfo officeCarInfo){
		return officeCarInfoDao.update(officeCarInfo);
	}

	@Override
	public OfficeCarInfo getOfficeCarInfoById(String id){
		return officeCarInfoDao.getOfficeCarInfoById(id);
	}

	@Override
	public Map<String, OfficeCarInfo> getOfficeCarInfoMapByIds(String[] ids){
		return officeCarInfoDao.getOfficeCarInfoMapByIds(ids);
	}
	
	@Override
	public Map<String, OfficeCarInfo> getOfficeCarInfoMapByUnitId(String unitId) {
		return officeCarInfoDao.getOfficeCarInfoMapByUnitId(unitId);
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoList(){
		return officeCarInfoDao.getOfficeCarInfoList();
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoPage(Pagination page){
		return officeCarInfoDao.getOfficeCarInfoPage(page);
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoByUnitIdList(String unitId){
		return officeCarInfoDao.getOfficeCarInfoByUnitIdList(unitId);
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoByUnitIdPage(String unitId, String carNumber, Pagination page){
		return officeCarInfoDao.getOfficeCarInfoByUnitIdPage(unitId, carNumber, page);
	}

	public void setOfficeCarInfoDao(OfficeCarInfoDao officeCarInfoDao){
		this.officeCarInfoDao = officeCarInfoDao;
	}
}