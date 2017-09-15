package net.zdsoft.office.expenditure.service;

import java.util.List;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.expenditure.entity.OfficeExpenditure;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureBusTrip;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureChgOpinion;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureKjOpinion;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureMetting;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureOutlay;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureReception;
/**
 * office_expenditure（财务开支）
 * @author 
 * 
 */
public interface OfficeExpenditureService{

	/**
	 * 新增office_expenditure（财务开支）
	 * @param officeExpenditure
	 * @return
	 */
	public OfficeExpenditure save(OfficeExpenditure officeExpenditure);
	
	public void saveThing(OfficeExpenditure officeExpenditure,OfficeExpenditureMetting officeExpenditureMetting,OfficeExpenditureReception officeExpenditureReception,
			OfficeExpenditureOutlay officeExpenditureOutlay,OfficeExpenditureBusTrip officeExpenditureBusTrip);
	
	public void updateThing(OfficeExpenditure officeExpenditure,OfficeExpenditureMetting officeExpenditureMetting,OfficeExpenditureReception officeExpenditureReception,
			OfficeExpenditureOutlay officeExpenditureOutlay,OfficeExpenditureBusTrip officeExpenditureBusTrip);
	/**
	 * 根据ids数组删除office_expenditure（财务开支）数据
	 * @param ids
	 * @return
	 */
	public void delete(String[] ids);

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
	
	public List<OfficeExpenditure> getOfficeExpenditures(String unitId,String applyUserId,String type,String state, Pagination page);
	
	public List<OfficeExpenditure> toDoAudit(String unitId,String userId, Pagination page);
	
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,OfficeExpenditureChgOpinion officeExpenditureChgOpinion,OfficeExpenditureKjOpinion officeExpenditureKjOpinion,String officeExpenditureId);
	
	public List<OfficeExpenditure> HaveDoAudit(String userId, Pagination page);
	
	public List<OfficeExpenditure> getOfficeExpendituresByUnitId(String unitId);

}