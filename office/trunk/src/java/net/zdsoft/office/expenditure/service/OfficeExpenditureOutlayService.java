package net.zdsoft.office.expenditure.service;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureOutlay;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_outlay
 * @author 
 * 
 */
public interface OfficeExpenditureOutlayService{

	/**
	 * 新增office_expenditure_outlay
	 * @param officeExpenditureOutlay
	 * @return
	 */
	public OfficeExpenditureOutlay save(OfficeExpenditureOutlay officeExpenditureOutlay);

	/**
	 * 根据ids数组删除office_expenditure_outlay数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_expenditure_outlay
	 * @param officeExpenditureOutlay
	 * @return
	 */
	public Integer update(OfficeExpenditureOutlay officeExpenditureOutlay);
	
	/**
	 * 根据exIds获取map
	 * @param officeExpenditureIds
	 * @return
	 */
	Map<String, OfficeExpenditureOutlay> getOfficeExpenditureOutlayByExIds(
			String[] officeExpenditureIds);

	/**
	 * 根据id获取office_expenditure_outlay
	 * @param id
	 * @return
	 */
	public OfficeExpenditureOutlay getOfficeExpenditureOutlayById(String id);
	
	public OfficeExpenditureOutlay getOfficeExpenditureOutlayByExId(String expenditureId);
	
	public void deleteByExId(String officeExpenditureId);

}