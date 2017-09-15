package net.zdsoft.office.dailyoffice.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
/**
 * office_go_out
 * @author 
 * 
 */
public interface OfficeGoOutDao{
	
	public List<OfficeGoOut> getOfficeGoOutByIds(String[] ids);

	/**
	 * 新增office_go_out
	 * @param officeGoOut
	 * @return
	 */
	public OfficeGoOut save(OfficeGoOut officeGoOut);

	/**
	 * 根据ids数组删除office_go_out
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
	 * 根据unitId获取office_go_out列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoOut> getOfficeGoOutByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_go_out获取
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
	public Map<String, OfficeGoOut> getOfficeBusinessTripMapByFlowIds(
			String[] array);
	
	public List<OfficeGoOut> HaveDoAudit(String userId,boolean invalid, Pagination page);
	
	public List<OfficeGoOut> getStatistics(String unitId,Date startTime,Date endTime,String[] userIds);
}