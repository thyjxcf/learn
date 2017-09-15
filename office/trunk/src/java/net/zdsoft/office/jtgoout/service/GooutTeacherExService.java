package net.zdsoft.office.jtgoout.service;

import java.util.*;

import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.keel.util.Pagination;
/**
 * goout_teacher_ex
 * @author 
 * 
 */
public interface GooutTeacherExService{

	/**
	 * 新增goout_teacher_ex
	 * @param gooutTeacherEx
	 * @return
	 */
	public GooutTeacherEx save(GooutTeacherEx gooutTeacherEx);

	/**
	 * 根据ids数组删除goout_teacher_ex数据
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
	 * 根据UnitId获取goout_teacher_ex列表
	 * @param unitId
	 * @return
	 */
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdList(String unitId);
	
	/**
	 * 根据ids获取list
	 * @param jtGoOutIds
	 * @return
	 */
	List<GooutTeacherEx> getGooutTeacherExListByjtId(String[] jtGoOutIds);

	/**
	 * 根据UnitId分页获取goout_teacher_ex
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdPage(String unitId, Pagination page);
	
	public GooutTeacherEx getGooutTeacherExByJtGooutId(String jtGooutId);
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdJtIds(String unitId,String[] jtIds);
}