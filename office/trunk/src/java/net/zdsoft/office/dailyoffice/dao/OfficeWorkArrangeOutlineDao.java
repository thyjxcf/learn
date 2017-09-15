package net.zdsoft.office.dailyoffice.dao;

import java.util.Date;
import java.util.List;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeOutline;

/**
 * office_outline 
 * @author 
 * 
 */
public interface OfficeWorkArrangeOutlineDao {
	/**
	 * @param OfficeWorkArrangeOutline
	 * @return"
	 */
	public OfficeWorkArrangeOutline save(OfficeWorkArrangeOutline OfficeWorkArrangeOutline);
	
	/**
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * @param OfficeWorkArrangeOutline
	 * @return
	 */
	public Integer update(OfficeWorkArrangeOutline OfficeWorkArrangeOutline);
	
	/**
	 * 更新状态
	 * @param id
	 * @param state
	 */
	public void updateState(String id, String state);
	
	/**
	 * @param id
	 * @return
	 */
	public OfficeWorkArrangeOutline getOfficeWorkArrangeOutlineById(String id);
	
	/**
	 * 根据年份获取单位的周工作重点
	 * @param unitId
	 * @param year
	 * @param page
	 * @return
	 */
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlines(String unitId, String year, Pagination page);
	
	/**
	 * 根据学年学期获取单位的周工作重点
	 * @param unitId
	 * @param year
	 * @param page
	 * @return
	 */
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlines(String unitId, String acadyear, String semester, Pagination page);
	
	/**
	 * 获取汇总下拉列表
	 */
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlineList(String unitId, String year, String state);
	
	/**
	 * 获取汇总下拉列表
	 */
	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlineList(String unitId, String acadyear, String semester, String state);
	
	/**
	 * 获取当前时间所在的周次
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param state
	 * @return
	 */
	public String getOfficeWorkArrangeOutline(String unitId, String acadyear, String semester, String state);
	
	/**
	 * 
	 * @param unitId
	 * @param state
	 * @return
	 */
	public String getOfficeWorkArrangeOutline(String unitId, String state);
	
	/**
	 * 根据学年学期开始时间结束时间获取单位的周工作重点
	 * @param unitId
	 * @return
	 */
	public OfficeWorkArrangeOutline getOfficeWorkArrangeOutlines(String unitId, Date startTime, Date endTime);
	
	/**
	 * 判断是否存在冲突
	 * @param unitId
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean isExistConflict(String unitId, String id, Date startTime, Date endTime);
}
