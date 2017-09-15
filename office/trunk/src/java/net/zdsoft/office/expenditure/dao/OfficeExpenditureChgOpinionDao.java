package net.zdsoft.office.expenditure.dao;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureChgOpinion;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_chg_opinion
 * @author 
 * 
 */
public interface OfficeExpenditureChgOpinionDao{

	/**
	 * 新增office_expenditure_chg_opinion
	 * @param officeExpenditureChgOpinion
	 * @return
	 */
	public OfficeExpenditureChgOpinion save(OfficeExpenditureChgOpinion officeExpenditureChgOpinion);

	/**
	 * 根据ids数组删除office_expenditure_chg_opinion
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_expenditure_chg_opinion
	 * @param officeExpenditureChgOpinion
	 * @return
	 */
	public Integer update(OfficeExpenditureChgOpinion officeExpenditureChgOpinion);

	/**
	 * 根据id获取office_expenditure_chg_opinion
	 * @param id
	 * @return
	 */
	public OfficeExpenditureChgOpinion getOfficeExpenditureChgOpinionById(String id);
	
	/**
	 * 根据taskId获取车管意见list
	 * @param taskId
	 * @return
	 */
	public List<OfficeExpenditureChgOpinion> getOfficeExpenditureChgOpinionByTaskIds(String...taskId);
	
	/**
	 * 根据taskId取对象
	 * @param taskId
	 * @return
	 */
	public OfficeExpenditureChgOpinion getOfficeExpenditureChgOpinionByTaskId(String taskId);

}