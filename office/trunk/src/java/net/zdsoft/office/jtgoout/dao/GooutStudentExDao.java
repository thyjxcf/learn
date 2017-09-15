package net.zdsoft.office.jtgoout.dao;

import java.util.*;

import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.keel.util.Pagination;
/**
 * goout_student_ex
 * @author 
 * 
 */
public interface GooutStudentExDao{

	/**
	 * 新增goout_student_ex
	 * @param gooutStudentEx
	 * @return
	 */
	public GooutStudentEx save(GooutStudentEx gooutStudentEx);

	/**
	 * 根据ids数组删除goout_student_ex
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
	 * 根据unitId获取goout_student_ex列表
	 * @param unitId
	 * @return
	 */
	public List<GooutStudentEx> getGooutStudentExByUnitIdList(String unitId);
	
	public List<GooutStudentEx> getGooutStudentExByUnitIdJtids(String unitId , String[] jtIds);

	/**
	 * 根据unitId分页goout_student_ex获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<GooutStudentEx> getGooutStudentExByUnitIdPage(String unitId, Pagination page);
	
	public GooutStudentEx getGooutStudentExByJtGoOutId(String jtGoOutId);

	List<GooutStudentEx> getGooutStudentExListByjtId(String[] jtGoOutIds);;
}