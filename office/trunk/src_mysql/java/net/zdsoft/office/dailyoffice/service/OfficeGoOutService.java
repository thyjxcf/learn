package net.zdsoft.office.dailyoffice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
/**
 * office_go_out
 * @author 
 * 
 */
public interface OfficeGoOutService{

	/**
	 * 新增office_go_out
	 * @param officeGoOut
	 * @return
	 */
	public OfficeGoOut save(OfficeGoOut officeGoOut);

	/**
	 * 根据ids数组删除office_go_out数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_go_out
	 * @param officeGoOut
	 * @return
	 */
	public Integer update(OfficeGoOut officeGoOut);

	/**
	 * 根据id获取office_go_out
	 * @param id
	 * @return
	 */
	public OfficeGoOut getOfficeGoOutById(String id);

	/**
	 * 根据ids数组查询office_go_outmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeGoOut> getOfficeGoOutMapByIds(String[] ids);

	/**
	 * 获取office_go_out列表
	 * @return
	 */
	public List<OfficeGoOut> getOfficeGoOutList();

	/**
	 * 分页获取office_go_out列表
	 * @param page
	 * @return
	 */
	public List<OfficeGoOut> getOfficeGoOutPage(Pagination page);

	/**
	 * 根据UnitId获取office_go_out列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoOut> getOfficeGoOutByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_go_out
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoOut> getOfficeGoOutByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据状态查询所有office_go_out列表
	 * @param unitId
	 * @param userId
	 * @param States
	 * @param page
	 * @return
	 */
	public List<OfficeGoOut> getOfficeGoOutByUnitIdUserIdPage(String unitId,String userId,String states, Pagination page);
	/**
	 * 判断是否存在冲突
	 * @param applyUserId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public boolean isExistConflict(String id, String applyUserId, Date beginTime, Date endTime);
	/**
	 * 开始出差申请流程
	 * @param officeTeacherLeaveNh
	 * @param userId
	 */
	public void startFlow(OfficeGoOut officeGoOut, String userId,UploadFile file);
	
	public void update(OfficeGoOut officeGoOut, UploadFile file);

	public void add(OfficeGoOut officeGoOut, UploadFile file);
	
	public void deleteRevoke(String id);
	/**
	 * 查找对应条件下的外出统计数据
	 * 
	 */
	public List<OfficeGoOut> getStatistics(String unitId,Date startTime,Date endTime,String deptId);
	/**
	 * 未审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeGoOut> toDoAudit(String userId, Pagination page);
	/**
	 * 已审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeGoOut> HaveDoAudit(String userId,boolean invalid,Pagination page);
	/**
	 * 审核通过
	 * @param pass
	 * @param taskHandlerSave
	 * @param id
	 */
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String id);

	public void deleteInvalid(String id,String userId);
}