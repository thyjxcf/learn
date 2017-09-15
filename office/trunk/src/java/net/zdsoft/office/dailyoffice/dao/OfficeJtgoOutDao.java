package net.zdsoft.office.dailyoffice.dao;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeJtgoOut;
import net.zdsoft.keel.util.Pagination;
/**
 * office_jtgo_out
 * @author 
 * 
 */
public interface OfficeJtgoOutDao{

	/**
	 * 新增office_jtgo_out
	 * @param officeJtgoOut
	 * @return
	 */
	public OfficeJtgoOut save(OfficeJtgoOut officeJtgoOut);

	/**
	 * 根据ids数组删除office_jtgo_out
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_jtgo_out
	 * @param officeJtgoOut
	 * @return
	 */
	public Integer update(OfficeJtgoOut officeJtgoOut);

	/**
	 * 根据id获取office_jtgo_out
	 * @param id
	 * @return
	 */
	public OfficeJtgoOut getOfficeJtgoOutById(String id);

	/**
	 * 根据ids数组查询office_jtgo_outmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeJtgoOut> getOfficeJtgoOutMapByIds(String[] ids);

	/**
	 * 获取office_jtgo_out列表
	 * @return
	 */
	public List<OfficeJtgoOut> getOfficeJtgoOutList();

	/**
	 * 分页获取office_jtgo_out列表
	 * @param page
	 * @return
	 */
	public List<OfficeJtgoOut> getOfficeJtgoOutPage(Pagination page);

	/**
	 * 根据unitId获取office_jtgo_out列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_jtgo_out获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 查询集体外出
	 * @param unitId
	 * @param userId
	 * @param states
	 * @param page
	 * @return
	 */
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdAndStates(String unitId,String states,Pagination page);
	
	/**
	 * 根据流程id查询OfficeJtgoOut
	 */
	public Map<String, OfficeJtgoOut> getOfficeBusinessTripMapByFlowIds(
			String[] array);
	
	public List<OfficeJtgoOut> doneAudit(String userId,boolean invalid, Pagination page);
	
}