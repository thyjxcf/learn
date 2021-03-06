package net.zdsoft.office.dailyoffice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
/**
 * office_business_trip
 * @author 
 * 
 */
public interface OfficeBusinessTripService{

	/**
	 * 新增office_business_trip
	 * @param officeBusinessTrip
	 * @return
	 */
	public OfficeBusinessTrip save(OfficeBusinessTrip officeBusinessTrip);

	/**
	 * 根据ids数组删除office_business_trip数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_business_trip
	 * @param officeBusinessTrip
	 * @return
	 */
	public Integer update(OfficeBusinessTrip officeBusinessTrip);

	/**
	 * 根据id获取office_business_trip
	 * @param id
	 * @return
	 */
	public OfficeBusinessTrip getOfficeBusinessTripById(String id);

	/**
	 * 根据ids数组查询office_business_tripmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBusinessTrip> getOfficeBusinessTripMapByIds(String[] ids);

	/**
	 * 获取office_business_trip列表
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripList();

	/**
	 * 分页获取office_business_trip列表
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripPage(Pagination page);

	/**
	 * 根据UnitId获取office_business_trip列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_business_trip
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据状态查询所有office_business_trip列表
	 * @param unitId
	 * @param userId
	 * @param States
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdUserIdPage(String unitId,String userId,String States, Pagination page);
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
	public void startFlow(OfficeBusinessTrip officeBusinessTrip, String userId,UploadFile file);
	
	public void update(OfficeBusinessTrip officeBusinessTrip, UploadFile file);

	public void add(OfficeBusinessTrip officeBusinessTrip, UploadFile file);
	/**
	 * 根据ids数组查询
	 * @param ids
	 * @return
	 */
	public List<OfficeBusinessTrip> getOfficeBusinessTripByIds(String[] ids);
	/**
	 * 未审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> toDoAudit(String userId, Pagination page);
	/**
	 * 已审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessTrip> HaveDoAudit(String userId, Pagination page);
	/**
	 * 审核通过
	 * @param pass
	 * @param taskHandlerSave
	 * @param id
	 */
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String id);
	
}