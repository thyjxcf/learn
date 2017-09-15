package net.zdsoft.office.jtgoout.dao;

import java.util.*;

import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.keel.util.Pagination;
/**
 * goout_teacher_ex
 * @author 
 * 
 */
public interface GooutTeacherExDao{

	/**
	 * 新增goout_teacher_ex
	 * @param gooutTeacherEx
	 * @return
	 */
	public GooutTeacherEx save(GooutTeacherEx gooutTeacherEx);

	/**
	 * 根据ids数组删除goout_teacher_ex
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void deleteByjtGooutId(String jtGooutId);

	/**
	 * 更新goout_teacher_ex
	 * @param gooutTeacherEx
	 * @return
	 */
	public Integer update(GooutTeacherEx gooutTeacherEx);

	/**
	 * 根据id获取goout_teacher_ex
	 * @param id
	 * @return
	 */
	public GooutTeacherEx getGooutTeacherExById(String id);
	
	public GooutTeacherEx getGooutTeacherExByJtGooutId(String jtGooutId);

	/**
	 * 根据ids数组查询goout_teacher_exmap
	 * @param ids
	 * @return
	 */
	public Map<String, GooutTeacherEx> getGooutTeacherExMapByIds(String[] ids);

	/**
	 * 获取goout_teacher_ex列表
	 * @return
	 */
	public List<GooutTeacherEx> getGooutTeacherExList();

	/**
	 * 分页获取goout_teacher_ex列表
	 * @param page
	 * @return
	 */
	public List<GooutTeacherEx> getGooutTeacherExPage(Pagination page);

	/**
	 * 根据unitId获取goout_teacher_ex列表
	 * @param unitId
	 * @return
	 */
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdList(String unitId);
	/**
	 * 根据unitId获取goout_teacher_ex列表
	 * @param unitId
	 * @return
	 */
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdJtIds(String unitId,String[] jtIds);

	/**
	 * 根据unitId分页goout_teacher_ex获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdPage(String unitId, Pagination page);

	/**
	 * 根据ids获取list
	 * @param jtGoOutIds
	 * @return
	 */
	List<GooutTeacherEx> getGooutTeacherExListByjtId(String[] jtGoOutIds);
}