package net.zdsoft.office.boardroom.service;

import java.util.*;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomApplyXj;
import net.zdsoft.keel.util.Pagination;
/**
 * office_boardroom_apply_xj
 * @author 
 * 
 */
public interface OfficeBoardroomApplyXjService{

	/**
	 * 新增office_boardroom_apply_xj
	 * @param officeBoardroomApplyXj
	 * @return
	 */
	public OfficeBoardroomApplyXj save(OfficeBoardroomApplyXj officeBoardroomApplyXj);
	
	public void batchSave(Map<String, OfficeBoardroomApplyXj> map);

	/**
	 * 根据ids数组删除office_boardroom_apply_xj数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_boardroom_apply_xj
	 * @param officeBoardroomApplyXj
	 * @return
	 */
	public Integer update(OfficeBoardroomApplyXj officeBoardroomApplyXj);

	/**
	 * 根据id获取office_boardroom_apply_xj
	 * @param id
	 * @return
	 */
	public OfficeBoardroomApplyXj getOfficeBoardroomApplyXjById(String id);

	/**
	 * 根据ids数组查询office_boardroom_apply_xjmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjMapByIds(String[] ids);

	/**
	 * 获取office_boardroom_apply_xj列表
	 * @return
	 */
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjList();

	/**
	 * 分页获取office_boardroom_apply_xj列表
	 * @param page
	 * @return
	 */
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjPage(Pagination page);

	/**
	 * 根据UnitId获取office_boardroom_apply_xj列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_boardroom_apply_xj
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据条件分页获取office_boardroom_apply_xj
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByDeptIdUnitIdPage(String applyStartDate,
			String applyEndDate,String auditState,String roomId,String deptId,String unitId, Pagination page);
	/**
	 * 删除申请记录以及扩张表信息也删除
	 */
	public void deleteRecord(String unitId,String[] ids);
	
	/**
	 * 批量操作审核
	 */
	public void pass(String[] ids, String userId, String auditOption,String state);
}