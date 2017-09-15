package net.zdsoft.office.jtgoout.service;

import java.util.*;

import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.keel.util.Pagination;
/**
 * goout_student_ex
 * @author 
 * 
 */
public interface GooutStudentExService{

	/**
	 * 新增goout_student_ex
	 * @param gooutStudentEx
	 * @return
	 */
	public GooutStudentEx save(GooutStudentEx gooutStudentEx);

	/**
	 * 根据ids数组删除goout_student_ex数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	public void deleteByJtGoOutId(String jtGooutId);
	
	/**
	 * 更新goout_student_ex
	 * @param gooutStudentEx
	 * @return
	 */
	public Integer update(GooutStudentEx gooutStudentEx);

	/**
	 * 根据id获取goout_student_ex
	 * @param id
	 * @return
	 */
	public GooutStudentEx getGooutStudentExById(String id);

	/**
	 * 根据ids数组查询goout_student_exmap
	 * @param ids
	 * @return
	 */
	public Map<String, GooutStudentEx> getGooutStudentExMapByIds(String[] ids);

	/**
	 * 获取goout_student_ex列表
	 * @return
	 */
	public List<GooutStudentEx> getGooutStudentExList();

	/**
	 * 分页获取goout_student_ex列表
	 * @param page
	 * @return
	 */
	public List<GooutStudentEx> getGooutStudentExPage(Pagination page);

	/**
	 * 根据UnitId获取goout_student_ex列表
	 * @param unitId
	 * @return
	 */
	public List<GooutStudentEx> getGooutStudentExByUnitIdList(String unitId);
	
	/**
	 * 根据ids获取list
	 * @param jtGoOutIds
	 * @return
	 */
	public List<GooutStudentEx> getGooutStudentExListByjtId(String[] jtGoOutIds);

	/**
	 * 根据UnitId分页获取goout_student_ex
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<GooutStudentEx> getGooutStudentExByUnitIdPage(String unitId, Pagination page);
	
	public GooutStudentEx getGooutStudentExByJtGoOutId(String jtGoOutId);
	public List<GooutStudentEx> getGooutStudentExByUnitIdJtids(String unitId , String[] jtIds);

}