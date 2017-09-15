package net.zdsoft.office.dailyoffice.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeJtgoOut;
/**
 * office_jtgo_out
 * @author 
 * 
 */
public interface OfficeJtgoOutService{

	/**
	 * 新增office_jtgo_out
	 * @param officeJtgoOut
	 * @return
	 */
	public OfficeJtgoOut save(OfficeJtgoOut officeJtgoOut);

	/**
	 * 根据ids数组删除office_jtgo_out数据
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
	 * 根据UnitId获取office_jtgo_out列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_jtgo_out
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
	
	public void save(OfficeJtgoOut officeJtgoOut,UploadFile file);
	
	public void update(OfficeJtgoOut officeJtgoOut,UploadFile file);
	
	public void startFlow(OfficeJtgoOut officeJtgoOut,String userId,UploadFile file);
	
	/**
	 * 根据流程id查询OfficeJtgoOut
	 */
	public Map<String, OfficeJtgoOut> getOfficeBusinessTripMapByFlowIds(
			String[] array);
	/**
	 * 通过作废
	 */
	public void deleteInvalid(String officeJtgoOutId,String userId);
	
	/**
	 * 审核通过
	 */
	public void passOfficeJtgoOut(boolean pass,TaskHandlerSave taskHandlerSave,String id);
	
	public List<OfficeJtgoOut> doneAudit(String userId,boolean invalid, Pagination page);
	
	/**
	 * 保存修改的流程并启动流程
	 * @param leaveId
	 * @param userId
	 * @param flowId
	 * @param jsonResult
	 */
	public void changeFlow(String leaveId, String userId, String flowId, String jsonResult);
}