package net.zdsoft.eis.base.data.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.ecs.xhtml.a;

import net.zdsoft.eis.base.data.dao.StusysSectionTimeSetDao;
import net.zdsoft.eis.base.data.entity.StusysSectionTimeSet;
import net.zdsoft.eis.base.data.service.StusysSectionTimeSetService;
import net.zdsoft.keel.util.Pagination;
/**
 * stusys_section_time_set
 * @author 
 * 
 */
public class StusysSectionTimeSetServiceImpl implements StusysSectionTimeSetService{
	private StusysSectionTimeSetDao stusysSectionTimeSetDao;

	@Override
	public StusysSectionTimeSet save(StusysSectionTimeSet stusysSectionTimeSet){
		return stusysSectionTimeSetDao.save(stusysSectionTimeSet);
	}
	
	@Override
	public void batchSave(List<StusysSectionTimeSet> stusysSectionTimeSetList) {
		stusysSectionTimeSetDao.batchSave(stusysSectionTimeSetList);
	}

	@Override
	public Integer delete(String[] ids){
		return stusysSectionTimeSetDao.delete(ids);
	}
	
	@Override
	public void deleteByUnitId(String unitId) {
		stusysSectionTimeSetDao.deleteByUnitId(unitId);
	}

	@Override
	public Integer update(StusysSectionTimeSet stusysSectionTimeSet){
		return stusysSectionTimeSetDao.update(stusysSectionTimeSet);
	}

	@Override
	public StusysSectionTimeSet getStusysSectionTimeSetById(String id){
		return stusysSectionTimeSetDao.getStusysSectionTimeSetById(id);
	}

	@Override
	public Map<String, StusysSectionTimeSet> getStusysSectionTimeSetMapByIds(String[] ids){
		return stusysSectionTimeSetDao.getStusysSectionTimeSetMapByIds(ids);
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetList(){
		return stusysSectionTimeSetDao.getStusysSectionTimeSetList();
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetPage(Pagination page){
		return stusysSectionTimeSetDao.getStusysSectionTimeSetPage(page);
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetByUnitIdList(String unitId,String acadyear,String semesterStr){
		return stusysSectionTimeSetDao.getStusysSectionTimeSetByUnitIdList(unitId,acadyear,semesterStr);
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetByUnitIdPage(String unitId, Pagination page){
		return stusysSectionTimeSetDao.getStusysSectionTimeSetByUnitIdPage(unitId, page);
	}

	public void setStusysSectionTimeSetDao(StusysSectionTimeSetDao stusysSectionTimeSetDao){
		this.stusysSectionTimeSetDao = stusysSectionTimeSetDao;
	}
}
	