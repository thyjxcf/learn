package net.zdsoft.office.dailyoffice.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeContent;
/**
 * office_work_arrange_content
 * @author 
 * 
 */
public interface OfficeWorkArrangeContentDao{

	/**
	 * 新增office_work_arrange_content
	 * @param officeWorkArrangeContent
	 * @return
	 */
	public OfficeWorkArrangeContent save(OfficeWorkArrangeContent officeWorkArrangeContent);
	
	/**
	 * 批量保存内容
	 * @param owacList
	 */
	public void batchSave(List<OfficeWorkArrangeContent> owacList);

	/**
	 * 根据ids数组删除office_work_arrange_content
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据工作详细id删除内容信息
	 * @param detailId
	 */
	public void deleteByDetailId(String detailId);

	/**
	 * 更新office_work_arrange_content
	 * @param officeWorkArrangeContent
	 * @return
	 */
	public Integer update(OfficeWorkArrangeContent officeWorkArrangeContent);
	
	/**
	 * 发布和取消发布时更新状态
	 * @param outLineId
	 * @param state
	 */
	public void updateStateByOutLineId(String outLineId, String state);

	/**
	 * 根据id获取office_work_arrange_content
	 * @param id
	 * @return
	 */
	public OfficeWorkArrangeContent getOfficeWorkArrangeContentById(String id);

	/**
	 * 根据ids数组查询office_work_arrange_contentmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkArrangeContent> getOfficeWorkArrangeContentMapByIds(String[] ids);

	/**
	 * 获取office_work_arrange_content列表
	 * @return
	 */
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentListByDetailId(String detailId);
	
	/**
	 * 获取office_work_arrange_content列表
	 * @return
	 */
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentListByOutLineId(String outLineId, String state);
	
	/**
	 * 获取office_work_arrange_content列表
	 * @return
	 */
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentListByOutLineId(String outLineId, String deptId, String state);

	/**
	 * 分页获取office_work_arrange_content列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentPage(Pagination page);
}