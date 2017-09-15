package net.zdsoft.office.expense.dao;

import java.util.*;

import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expense
 * @author 
 * 
 */
public interface OfficeExpenseDao{

	/**
	 * 新增office_expense
	 * @param officeExpense
	 * @return
	 */
	public OfficeExpense save(OfficeExpense officeExpense);

	/**
	 * 根据ids数组删除office_expense
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
	 * 根据unitId获取office_expense列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExpense> getOfficeExpenseByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_expense获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeExpense> getOfficeExpenseByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeExpense> getOfficeExpenseListByCondition(String unitId, String searchType, String applyUserId, Pagination page);
	
	public List<OfficeExpense> getOfficeExpenseByIds(String[] ids);
	
	public Map<String, OfficeExpense> getOfficeExpenseMapByFlowIds(String[] flowIds);
	
	/**
	 * 根据审核人ID获取审核过的报销记录
	 * @param userId
	 * @param state
	 * @return
	 */
	public List<OfficeExpense> getAuditedList(String userId, String[] state, Pagination page);
	
	public List<OfficeExpense> getOfficeExpensesByUnitIdAndOthers(String unitId,String State,Date startTime,Date Endtime,String applyUserName,Pagination page);
	
}