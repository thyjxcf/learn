package net.zdsoft.office.boardroom.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.boardroom.entity.OfficeApplyDetailsXj;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
/**
 * office_apply_details_xj
 * @author 
 * 
 */
public interface OfficeApplyDetailsXjDao{

	/**
	 * 新增office_apply_details_xj
	 * @param officeApplyDetailsXj
	 * @return
	 */
	public OfficeApplyDetailsXj save(OfficeApplyDetailsXj officeApplyDetailsXj);

	/**
	 * 根据ids数组删除office_apply_details_xj
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_apply_details_xj
	 * @param officeApplyDetailsXj
	 * @return
	 */
	public Integer update(OfficeApplyDetailsXj officeApplyDetailsXj);

	/**
	 * 根据id获取office_apply_details_xj
	 * @param id
	 * @return
	 */
	public OfficeApplyDetailsXj getOfficeApplyDetailsXjById(String id);

	/**
	 * 根据ids数组查询office_apply_details_xjmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeApplyDetailsXj> getOfficeApplyDetailsXjMapByIds(String[] ids);

	/**
	 * 获取office_apply_details_xj列表
	 * @return
	 */
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjList();

	/**
	 * 分页获取office_apply_details_xj列表
	 * @param page
	 * @return
	 */
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjPage(Pagination page);

	/**
	 * 根据unitId获取office_apply_details_xj列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_apply_details_xj获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据会议室及申请日期部门获取已申请的会议室信息
	 * @param roomType
	 * @param applyDate
	 * @param unitId
	 * @param userId
	 * @return
	 */
	
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByRoomId(String roomId,Date applyStartDate,Date applyEndDate,String unitId);
	
	/**
	 * 判断会议室是否占用
	 */
	public boolean isApplyByOthers(String period, Date applyDate, String roomId,String deptId);
	
	/**
	 * 删除会议室记录
	 */
	public void batchDelete(List<OfficeApplyDetailsXj> officeApplyDetailsXjs);
	
	/**
	 * 批量插入
	 */
	public void addofficeApplyDetailsXjs(List<OfficeApplyDetailsXj> officeApplyDetailsXjs);
	
	public void updateStateByIds(String[] ids, String state);
	
	public boolean getApplyByApplyStartDate(String unitId,String roomId,Date applyEndDate);
	
}