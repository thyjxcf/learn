package net.zdsoft.office.expenditure.service;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureReception;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_reception
 * @author 
 * 
 */
public interface OfficeExpenditureReceptionService{

	/**
	 * 新增office_expenditure_reception
	 * @param officeExpenditureReception
	 * @return
	 */
	public OfficeExpenditureReception save(OfficeExpenditureReception officeExpenditureReception);

	/**
	 * 根据ids数组删除office_expenditure_reception数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_expenditure_reception
	 * @param officeExpenditureReception
	 * @return
	 */
	public Integer update(OfficeExpenditureReception officeExpenditureReception);
	
	/**
	 * 根据exIds获取map
	 * @param officeExpenditureIds
	 * @return
	 */
	Map<String, OfficeExpenditureReception> getOfficeExpenditureReceptionByExIds(
			String[] officeExpenditureIds);

	/**
	 * 根据id获取office_expenditure_reception
	 * @param id
	 * @return
	 */
	public OfficeExpenditureReception getOfficeExpenditureReceptionById(String id);
	
	public OfficeExpenditureReception getOfficeExpenditureReceptionByExId(String officeExpenditureId);
	
	public void deleteByExId(String officeExpenditureId);
}