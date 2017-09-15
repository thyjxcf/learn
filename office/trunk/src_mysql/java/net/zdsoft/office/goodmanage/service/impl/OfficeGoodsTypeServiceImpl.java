package net.zdsoft.office.goodmanage.service.impl;

import java.util.*;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeService;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsTypeDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_type
 * @author 
 * 
 */
public class OfficeGoodsTypeServiceImpl implements OfficeGoodsTypeService{
	private OfficeGoodsTypeDao officeGoodsTypeDao;

	@Override
	public OfficeGoodsType save(OfficeGoodsType officeGoodsType){
		return officeGoodsTypeDao.save(officeGoodsType);
	}

	@Override
	public Integer delete(String[] ids){
		return officeGoodsTypeDao.delete(ids);
	}

	@Override
	public Integer update(OfficeGoodsType officeGoodsType){
		return officeGoodsTypeDao.update(officeGoodsType);
	}

	@Override
	public OfficeGoodsType getOfficeGoodsTypeById(String id){
		return officeGoodsTypeDao.getOfficeGoodsTypeById(id);
	}

	@Override
	public Map<String, OfficeGoodsType> getOfficeGoodsTypeMapByIds(String[] ids){
		return officeGoodsTypeDao.getOfficeGoodsTypeMapByIds(ids);
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeList(){
		return officeGoodsTypeDao.getOfficeGoodsTypeList();
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypePage(Pagination page){
		return officeGoodsTypeDao.getOfficeGoodsTypePage(page);
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeByUnitIdList(String unitId){
		return officeGoodsTypeDao.getOfficeGoodsTypeByUnitIdList(unitId);
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeByUnitIdPage(String unitId, Pagination page){
		return officeGoodsTypeDao.getOfficeGoodsTypeByUnitIdPage(unitId, page);
	}

	@Override
	public Map<String, OfficeGoodsType> getOfficeGoodsTypeMapByUnitId(String unitId){
		return officeGoodsTypeDao.getOfficeGoodsTypeMapByUnitId(unitId);
	}
	
	@Override
	public OfficeGoodsType getOfficeGoodsTypeByTypeId(String unitId, String typeId){
		List<OfficeGoodsType> typelist = officeGoodsTypeDao.getOfficeGoodsTypeByTypeId(unitId, typeId);
		if(typelist.size() > 0)
			return typelist.get(0);
		else
			return new OfficeGoodsType();
	}
	
	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeByTypeId(String unitId, String[] typeIds){
		return officeGoodsTypeDao.getOfficeGoodsTypeByTypeId(unitId, typeIds);
	}
	
	public void setOfficeGoodsTypeDao(OfficeGoodsTypeDao officeGoodsTypeDao){
		this.officeGoodsTypeDao = officeGoodsTypeDao;
	}
}
