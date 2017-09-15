package net.zdsoft.office.expense.service;

import java.util.*;

import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
/**
 * office_expense
 * @author 
 * 
 */
public interface OfficeExpenseService{

	/**
	 * 新增office_expense
	 * @param officeExpense
	 * @return
	 */
	public OfficeExpense save(OfficeExpense officeExpense);

	/**
	 * 根据ids数组删除office_expense数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_expense
	 * @param officeExpense
	 * @return
	 */
	public Integer update(OfficeExpense officeExpense);

	/**
	 * 根据id获取office_expense
	 * @param id
	 * @return
	 */
	public OfficeExpense getOfficeExpenseById(String id);

	/**
	 * 根据ids数组查询office_expensemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExpense> getOfficeExpenseMapByIds(String[] ids);

	/**
	 * 获取office_expense列表
	 * @return
	 */
	public List<OfficeExpense> getOfficeExpenseList();

	/**
	 * 分页获取office_expense列表
	 * @param page
	 * @return
	 */
	public List<OfficeExpense> getOfficeExpensePage(Pagination page);

	/**
	 * 根据UnitId获取office_expense列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExpense> getOfficeExpenseByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_expense
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeExpense> getOfficeExpenseByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据单位、审核状态获取list
	 * @param unitId
	 * @param searchType
	 * @param applyUserId
	 * @param page
	 * @return
	 */
	public List<OfficeExpense> getOfficeExpenseListByCondition(String unitId, String searchType, String applyUserId, Pagination page);
	
	public void insert(OfficeExpense officeExpense, List<UploadFile> uploadFileList,boolean isMobile);
	
	public void update(OfficeExpense officeExpense, List<UploadFile> uploadFileList,boolean isMobile,String[] removeAttachmentArray);
	
	public void startFlow(OfficeExpense officeExpense, String userId,  List<UploadFile> uploadFileList,boolean isMobile,String[] removeAttachmentArray);
	
	public List<OfficeExpense> getOfficeExpenseByIds(String[] ids);
	
	public List<OfficeExpense> getAuditList(String searchType, String userId, Pagination page);
	
	/**
	 * 报销审核
	 * @param pass
	 * @param taskHandlerSave
	 * @param id
	 */
	public void doAudit(boolean pass, TaskHandlerSave taskHandlerSave, String id, String currentStepId);
	
	public Map<String, OfficeExpense> getOfficeExpenseMapByFlowIds(String[] flowIds);
	
	public List<OfficeExpense> getAuditedList(String userId, String[] state, Pagination page);
	
	public void changeFlow(String expenseId, String userId, String modelId, String jsonResult);
	
	public List<OfficeExpense> getOfficeExpensesByUnitIdAndOthers(String unitId,String State,Date startTime,Date Endtime,String applyUserName,Pagination page);
}