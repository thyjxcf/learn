package net.zdsoft.office.expenditure.dao;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureKjOpinion;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_kj_opinion
 * @author 
 * 
 */
public interface OfficeExpenditureKjOpinionDao{

	/**
	 * 新增office_expenditure_kj_opinion
	 * @param officeExpenditureKjOpinion
	 * @return
	 */
	public OfficeExpenditureKjOpinion save(OfficeExpenditureKjOpinion officeExpenditureKjOpinion);

	/**
	 * 根据ids数组删除office_expenditure_kj_opinion
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_expenditure_kj_opinion
	 * @param officeExpenditureKjOpinion
	 * @return
	 */
	public Integer update(OfficeExpenditureKjOpinion officeExpenditureKjOpinion);

	/**
	 * 根据id获取office_expenditure_kj_opinion
	 * @param id
	 * @return
	 */
	public OfficeExpenditureKjOpinion getOfficeExpenditureKjOpinionById(String id);
	
	/**
	 * 获取会计审核意见map
	 * @param taskId
	 * @return
	 */
	public List<OfficeExpenditureKjOpinion> getOfficeExpenditureKjOpinionByTaskIds(String...taskId);
	
	public OfficeExpenditureKjOpinion getOfficeExpenditureKjOpinionByTaskId(String taskId);

}