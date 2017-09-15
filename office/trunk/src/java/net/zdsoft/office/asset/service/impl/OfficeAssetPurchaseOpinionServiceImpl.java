package net.zdsoft.office.asset.service.impl;

import java.util.*;

import net.zdsoft.office.asset.entity.OfficeAssetPurchaseOpinion;
import net.zdsoft.office.asset.service.OfficeAssetPurchaseOpinionService;
import net.zdsoft.office.asset.dao.OfficeAssetPurchaseOpinionDao;
import net.zdsoft.keel.util.Pagination;
/**
 * 采购意见信息表
 * @author 
 * 
 */
public class OfficeAssetPurchaseOpinionServiceImpl implements OfficeAssetPurchaseOpinionService{
	private OfficeAssetPurchaseOpinionDao officeAssetPurchaseOpinionDao;

	@Override
	public OfficeAssetPurchaseOpinion save(OfficeAssetPurchaseOpinion officeAssetPurchaseOpinion){
		return officeAssetPurchaseOpinionDao.save(officeAssetPurchaseOpinion);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAssetPurchaseOpinionDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAssetPurchaseOpinion officeAssetPurchaseOpinion){
		return officeAssetPurchaseOpinionDao.update(officeAssetPurchaseOpinion);
	}

	@Override
	public OfficeAssetPurchaseOpinion getOfficeAssetPurchaseOpinionById(String id){
		return officeAssetPurchaseOpinionDao.getOfficeAssetPurchaseOpinionById(id);
	}

	@Override
	public Map<String, OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionMapByIds(String[] ids){
		return officeAssetPurchaseOpinionDao.getOfficeAssetPurchaseOpinionMapByIds(ids);
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionList(){
		return officeAssetPurchaseOpinionDao.getOfficeAssetPurchaseOpinionList();
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionPage(Pagination page){
		return officeAssetPurchaseOpinionDao.getOfficeAssetPurchaseOpinionPage(page);
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionByUnitIdList(String unitId){
		return officeAssetPurchaseOpinionDao.getOfficeAssetPurchaseOpinionByUnitIdList(unitId);
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionByUnitIdPage(String unitId, Pagination page){
		return officeAssetPurchaseOpinionDao.getOfficeAssetPurchaseOpinionByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionList(String unitId, String type, String content){
		return officeAssetPurchaseOpinionDao.getOfficeAssetPurchaseOpinionList(unitId, type, content);
	}

	@Override
	public List<String> getOpinionByType(String unitId, String type){
		List<OfficeAssetPurchaseOpinion> opinionlist = officeAssetPurchaseOpinionDao
				.getOfficeAssetPurchaseOpinionListByType(unitId, type);
		List<String> list = new ArrayList<String>();
		for(OfficeAssetPurchaseOpinion opinion : opinionlist){
			list.add(opinion.getContent());
		}
		return list;
	}
	
	public void setOfficeAssetPurchaseOpinionDao(OfficeAssetPurchaseOpinionDao officeAssetPurchaseOpinionDao){
		this.officeAssetPurchaseOpinionDao = officeAssetPurchaseOpinionDao;
	}
}
