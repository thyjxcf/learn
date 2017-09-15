package net.zdsoft.office.boardroom.dao;

import java.util.*;

import net.zdsoft.office.boardroom.entity.OfficeBoardroomXj;
import net.zdsoft.keel.util.Pagination;
/**
 * office_boardroom_xj
 * @author 
 * 
 */
public interface OfficeBoardroomXjDao{

	/**
	 * 新增office_boardroom_xj
	 * @param officeBoardroomXj
	 * @return
	 */
	public OfficeBoardroomXj save(OfficeBoardroomXj officeBoardroomXj);

	/**
	 * 根据ids数组删除office_boardroom_xj
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_boardroom_xj
	 * @param officeBoardroomXj
	 * @return
	 */
	public Integer update(OfficeBoardroomXj officeBoardroomXj);

	/**
	 * 根据id获取office_boardroom_xj
	 * @param id
	 * @return
	 */
	public OfficeBoardroomXj getOfficeBoardroomXjById(String id);

	/**
	 * 根据ids数组查询office_boardroom_xjmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBoardroomXj> getOfficeBoardroomXjMapByIds(String[] ids);

	/**
	 * 获取office_boardroom_xj列表
	 * @return
	 */
	public List<OfficeBoardroomXj> getOfficeBoardroomXjList();

	/**
	 * 分页获取office_boardroom_xj列表
	 * @param page
	 * @return
	 */
	public List<OfficeBoardroomXj> getOfficeBoardroomXjPage(Pagination page);

	/**
	 * 根据unitId获取office_boardroom_xj列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_boardroom_xj获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdPage(String unitId, Pagination page);
	/**
	 * 判断会议室唯一
	 * @param unitId
	 * @param name
	 * @return
	 */
	public boolean isExistConflict(String unitId, String name,String id);
	
	/**
	 * 生成map会议室
	 */
	public Map<String,OfficeBoardroomXj> getOfficeBoardroomMapByUnitId(String unitId);
}