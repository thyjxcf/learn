package net.zdsoft.office.expenditure.dao;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditure;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure（财务开支）
 * @author 
 * 
 */
public interface OfficeExpenditureDao{

	/**
	 * 新增office_expenditure（财务开支）
	 * @param officeExpenditure
	 * @return
	 */
	public OfficeExpenditure save(OfficeExpenditure officeExpenditure);

	/**
	 * 根据ids数组删除office_expenditure（财务开支）
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_expenditure（财务开支）
	 * @param officeExpenditure
	 * @return
	 */
	public Integer update(OfficeExpenditure officeExpenditure);

	/**
	 * 根据id获取office_expenditure（财务开支）
	 * @param id
	 * @return
	 */
	public OfficeExpenditure getOfficeExpenditureById(String id);
	
	public List<OfficeExpenditure> getOfficeExpenditures(String unitId,String applyUserId,String type,String state, Pagination page);
	
	public Map<String,OfficeExpenditure> getOfficeEMap(String[] flowIds);
	
	public List<OfficeExpenditure> HaveDoAudit(String userId, Pagination page);
	
	public List<OfficeExpenditure> getOfficeExpendituresByUnitId(String unitId);

	/**
	 * 根据applyuserIds查询数据
	 * @param unitId
	 * @param applyUserIds
	 * @param type
	 * @param state
	 * @param page
	 * @return
	 */
	List<OfficeExpenditure> getOfficeExpendituresByUserIds(String unitId,
			String[] applyUserIds, String type, String state, Pagination page);

}