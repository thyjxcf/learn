package net.zdsoft.office.dailyoffice.service;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeLabSet;
import net.zdsoft.keel.util.Pagination;
/**
 * office_lab_set
 * @author 
 * 
 */
public interface OfficeLabSetService{

	/**
	 * 新增office_lab_set
	 * @param officeLabSet
	 * @return
	 */
	public OfficeLabSet save(OfficeLabSet officeLabSet);

	/**
	 * 根据ids数组删除office_lab_set数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_lab_set
	 * @param officeLabSet
	 * @return
	 */
	public Integer update(OfficeLabSet officeLabSet);

	/**
	 * 根据id获取office_lab_set
	 * @param id
	 * @return
	 */
	public OfficeLabSet getOfficeLabSetById(String id);

	/**
	 * 根据ids数组查询office_lab_setmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLabSet> getOfficeLabSetMapByIds(String[] ids);

	/**
	 * 获取office_lab_set列表
	 * @return
	 */
	public List<OfficeLabSet> getOfficeLabSetList();

	/**
	 * 分页获取office_lab_set列表
	 * @param page
	 * @return
	 */
	public List<OfficeLabSet> getOfficeLabSetPage(Pagination page);

	/**
	 * 根据UnitId获取office_lab_set列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLabSet> getOfficeLabSetByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_lab_set
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLabSet> getOfficeLabSetByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据学科搜索
	 * @param unitId
	 * @param subject
	 * @param grade
	 * @param page
	 * @return
	 */
	public List<OfficeLabSet> getOfficeLabSetBySubjectPage(String unitId, String subject, String grade, Pagination page);
	
	/**
	 * 根据条件搜索
	 * @param unitId
	 * @param searchName
	 * @param searchSubject
	 * @param searchGrade
	 * @return
	 */
	public Map<String, OfficeLabSet> getOfficeLabSetMapByConditions(String unitId, String searchName, String searchSubject, String searchGrade);
}