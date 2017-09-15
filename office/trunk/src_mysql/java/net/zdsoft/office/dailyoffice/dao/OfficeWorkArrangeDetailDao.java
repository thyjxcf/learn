package net.zdsoft.office.dailyoffice.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeDetail;

/**
 * office_outline_content 
 * @author 
 * 
 */
public interface OfficeWorkArrangeDetailDao {
	/**
	 * @param OfficeWorkArrangeDetail
	 * @return"
	 */
	public OfficeWorkArrangeDetail save(OfficeWorkArrangeDetail OfficeWorkArrangeDetail);
	
	/**
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 
	 * @param outlineId
	 * @param deptId
	 * @param state
	 * @return
	 */
	public Integer delete(String outlineId, String deptId, String state);
	
	/**
	 * @param OfficeWorkArrangeDetail
	 * @return
	 */
	public Integer update(OfficeWorkArrangeDetail OfficeWorkArrangeDetail);
	
	/**
	 * 发布和取消发布时更新状态
	 * @param outLineId
	 * @param state
	 */
	public void updateStateByOutLineId(String outLineId, String state);
	
	/**
	 * 修改备注
	 * @param id
	 * @param remark
	 */
	public void updateRemark(String id, String remark);
	
	/**
	 * @param id);
	 * @return
	 */
	public OfficeWorkArrangeDetail getOfficeWorkArrangeDetailById(String id);
	
	/**
	 * 通过outlineId判断是否已经存在数据信息
	 * @param outlineId
	 * @return
	 */
	public boolean isExistDetailByOutlineId(String outlineId);
	
	/**
	 * key:outLineId
	 * @param outLineIds
	 * @param deptId
	 * @return
	 */
	public Map<String, OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailMap(String[] outLineIds, String deptId);
	
	/**
	 * 根据工作大纲id获取各部门具体信息
	 * @param outlineId
	 * @param state
	 * @return
	 */
	public List<OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailListByOutLineId(String outlineId, String state);
	
	/**
	 * 根据工作大纲id获取各部门具体信息
	 * @param outlineId
	 * @param state
	 * @return
	 */
	public List<OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailListByOutLineId(String outlineId, String deptId, String state);
	
	/**
	 * 按outlineId、deptId查询某个部门下的工作内容
	 * @param outlineId
	 * @param deptId
	 * @return
	 */
	public OfficeWorkArrangeDetail getOfficeWorkArrangeDetail(String outlineId, String deptId);
	
	/**
	 * 获取列表
	 * @param unitId
	 * @param year
	 * @param workOutlineId
	 * @param deptId
	 * @param state TODO
	 * @param page
	 * @return
	 */
	public List<OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailList(String unitId, String year, String workOutlineId, String deptId, String state, Pagination page);
	
	/**
	 * 判断是否已经维护工作安排
	 * @param outLineIds
	 * @return
	 */
	public Map<String, String> getOutLineIdsMap(String[] outLineIds);
}
