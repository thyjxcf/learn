package net.zdsoft.eis.base.data.dao;


import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.entity.StusysSectionTimeSet;
import net.zdsoft.keel.util.Pagination;

/**
 * stusys_section_time_set
 * @author 
 * 
 */
public interface StusysSectionTimeSetDao{

	/**
	 * 新增stusys_section_time_set
	 * @param stusysSectionTimeSet
	 * @return
	 */
	public StusysSectionTimeSet save(StusysSectionTimeSet stusysSectionTimeSet);

	/**
	 * 新增stusys_section_time_set
	 * @param stusysSectionTimeSet
	 * @return
	 */
	public void batchSave(List<StusysSectionTimeSet> stusysSectionTimeSetList);
	
	/**
	 * 根据ids数组删除stusys_section_time_set
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 根据单位id删除相应的节次信息
	 * @param unitId
	 */
	public void deleteByUnitId(String unitId);
	
	/**
	 * 更新stusys_section_time_set
	 * @param stusysSectionTimeSet
	 * @return
	 */
	public Integer update(StusysSectionTimeSet stusysSectionTimeSet);

	/**
	 * 根据id获取stusys_section_time_set
	 * @param id
	 * @return
	 */
	public StusysSectionTimeSet getStusysSectionTimeSetById(String id);

	/**
	 * 根据ids数组查询stusys_section_time_setmap
	 * @param ids
	 * @return
	 */
	public Map<String, StusysSectionTimeSet> getStusysSectionTimeSetMapByIds(String[] ids);

	/**
	 * 获取stusys_section_time_set列表
	 * @return
	 */
	public List<StusysSectionTimeSet> getStusysSectionTimeSetList();

	/**
	 * 分页获取stusys_section_time_set列表
	 * @param page
	 * @return
	 */
	public List<StusysSectionTimeSet> getStusysSectionTimeSetPage(Pagination page);

	/**
	 * 根据unitId获取stusys_section_time_set列表
	 * @param unitId
	 * @return
	 */
	public List<StusysSectionTimeSet> getStusysSectionTimeSetByUnitIdList(String unitId,String acadyear,String semesterStr);

	/**
	 * 根据unitId分页stusys_section_time_set获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<StusysSectionTimeSet> getStusysSectionTimeSetByUnitIdPage(String unitId, Pagination page);
}