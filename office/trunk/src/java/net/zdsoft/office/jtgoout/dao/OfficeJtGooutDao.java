package net.zdsoft.office.jtgoout.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
/**
 * office_jt_goout
 * @author 
 * 
 */
public interface OfficeJtGooutDao{

	/**
	 * 新增office_jt_goout
	 * @param officeJtGoout
	 * @return
	 */
	public OfficeJtGoout save(OfficeJtGoout officeJtGoout);
	
	/**
	 * 根据ids数组删除office_jt_goout
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void deleteRevoke(String id);

	/**
	 * 更新office_jt_goout
	 * @param officeJtGoout
	 * @return
	 */
	public Integer update(OfficeJtGoout officeJtGoout);

	/**
	 * 根据id获取office_jt_goout
	 * @param id
	 * @return
	 */
	public OfficeJtGoout getOfficeJtGooutById(String id);

	/**
	 * 根据ids数组查询office_jt_gooutmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeJtGoout> getOfficeJtGooutMapByIds(String[] ids);

	/**
	 * 获取office_jt_goout列表
	 * @return
	 */
	public List<OfficeJtGoout> getOfficeJtGooutList();

	/**
	 * 分页获取office_jt_goout列表
	 * @param page
	 * @return
	 */
	public List<OfficeJtGoout> getOfficeJtGooutPage(Pagination page);

	/**
	 * 根据unitId获取office_jt_goout列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdList(String unitId);
	
	/**
	 * 获取日期范围内的集体外出记录
	 * @param startTime
	 * @param endTime
	 * @param userIds
	 * @return
	 */
	public List<OfficeJtGoout> getListByStarttimeAndEndtime(String unitId, Date startTime,Date endTime);
	
	/**
	 * 获取当前日期教师集体外出记录
	 * @param unitId
	 * @param date
	 * @return
	 */
	public List<OfficeJtGoout> getListByUnitIdAndDate(String unitId,Date date);

	/**
	 * 根据unitId分页office_jt_goout获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdAndState(String unitId,String userId,String state,Pagination page);
	
	public Map<String,OfficeJtGoout> getOfficeJtGooutMapByFlowId(String[] flowId,Pagination page);
	
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitIds(String[] unitIds);
	
	public List<OfficeJtGoout> getOfficeJtGooutsByFlowIds(String[] flowIds,Pagination page);
	
	public List<OfficeJtGoout> HaveDoneAudit(String userId,boolean invalid,Pagination page);//是否作废
	
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitNameAndType(String[] unitIds,String type,String startTime,String endTime,Pagination page);
	
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitAndType(String unitId,String type,Date startTime,Date endTime);
}