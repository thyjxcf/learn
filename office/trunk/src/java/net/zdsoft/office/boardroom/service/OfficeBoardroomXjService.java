package net.zdsoft.office.boardroom.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomXj;
/**
 * office_boardroom_xj
 * @author 
 * 
 */
public interface OfficeBoardroomXjService{

	/**
	 * 新增office_boardroom_xj
	 * @param officeBoardroomXj
	 * @return
	 */
	public OfficeBoardroomXj save(OfficeBoardroomXj officeBoardroomXj);

	/**
	 * 根据ids数组删除office_boardroom_xj数据
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
	 * 根据UnitId获取office_boardroom_xj列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_boardroom_xj
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 判断会议室名称唯一
	 */
	public boolean isExistConflict(String unitId, String name,String id);
	
	/**
	 * 生成map会议室
	 */
	public Map<String,OfficeBoardroomXj> getOfficeBoardroomMapByUnitId(String unitId);
}