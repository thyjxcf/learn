package net.zdsoft.office.jtgoout.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
/**
 * office_jt_goout
 * @author 
 * 
 */
public interface OfficeJtGooutService{

	/**
	 * 新增office_jt_goout
	 * @param officeJtGoout
	 * @return
	 */
	public OfficeJtGoout save(OfficeJtGoout officeJtGoout);

	public void save(OfficeJtGoout officeJtGoout,GooutStudentEx gooutStudentEx,GooutTeacherEx gooutTeacherEx,List<UploadFile> files);
	/**
	 * 根据ids数组删除office_jt_goout数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_jt_goout
	 * @param officeJtGoout
	 * @return
	 */
	public Integer update(OfficeJtGoout officeJtGoout);
	
	public void update(OfficeJtGoout officeJtGoout,GooutStudentEx gooutStudentEx,GooutTeacherEx gooutTeacherEx,List<UploadFile> files,String[] removeAttachmentArray);
	
	public void startFlow(OfficeJtGoout officeJtGoout,GooutStudentEx gooutStudentEx,GooutTeacherEx gooutTeacherEx, String userId,List<UploadFile> files,String[] removeAttachmentArray,Pagination page) ;
	
	/**
	 * 获取日期范围内的集体外出记录
	 * @param startTime
	 * @param endTime
	 * @param userIds
	 * @return
	 */
	public List<OfficeJtGoout> getListByStarttimeAndEndtime(String unitId, Date startTime,Date endTime);
	
	/**
	 * 获取当前日期教师外出记录
	 * @param unitId
	 * @param date
	 * @return
	 */
	public List<OfficeJtGoout> getListByUnitIdAndDate(String unitId,Date date);

	public void deleteRevoke(String id);
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
	 * 根据UnitId获取office_jt_goout列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_jt_goout
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdAndState(String unitId,String userId,String state,Pagination page);
	
	public List<OfficeJtGoout> toDoAudit(String userId,Pagination page);
	
	public List<OfficeJtGoout> myAuditList(String userId,String unitId,Pagination page);
	
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitIds(String[] unitIds);
	
	public List<OfficeJtGoout> HaveDoneAudit(String userId,boolean invalid,Pagination page);
	
	/**
	 * 审核通过
	 * @param pass
	 * @param taskHandlerSave
	 * @param id
	 */
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String id, String currentStepId);
	
	/**
	 * 作废申请通过的申请
	 * @param id
	 * 2016年9月11日
	 */
	public void deleteInvalid(String id,String userId);
	
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitNameAndType(String[] unitId,String type,String startTime,String endTime,Pagination page);
	
	/**
	 * 保存修改的流程并启动流程
	 * @param leaveId
	 * @param userId
	 * @param flowId
	 * @param jsonResult
	 */
	public void changeFlow(String jtGoOutId, String userId, String flowId, String jsonResult,Pagination page);
	
	/**
	 * 
	 */
	public OfficeJtGoout getOfficeJtGooutByUnitIdAndUserId(OfficeJtGoout officeJtGoout,String unitId,String userId);
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitAndType(String unitId,String type,Date startTime,Date endTime);
	/**
	 * 计算所有 外出教师总人数 
	 * @param unitId
	 * @param userIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Set<String>> getMapStatistcGoOutSum(String unitId , String[] userIds, Date startTime , Date endTime);
}