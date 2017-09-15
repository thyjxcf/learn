package net.zdsoft.office.expenditure.service.impl;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureChgOpinion;
import net.zdsoft.office.expenditure.service.OfficeExpenditureChgOpinionService;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureChgOpinionDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_chg_opinion
 * @author 
 * 
 */
public class OfficeExpenditureChgOpinionServiceImpl implements OfficeExpenditureChgOpinionService{
	private OfficeExpenditureChgOpinionDao officeExpenditureChgOpinionDao;

	@Override
	public OfficeExpenditureChgOpinion save(OfficeExpenditureChgOpinion officeExpenditureChgOpinion){
		return officeExpenditureChgOpinionDao.save(officeExpenditureChgOpinion);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExpenditureChgOpinionDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpenditureChgOpinion officeExpenditureChgOpinion){
		return officeExpenditureChgOpinionDao.update(officeExpenditureChgOpinion);
	}

	@Override
	public OfficeExpenditureChgOpinion getOfficeExpenditureChgOpinionByTaskId(
			String taskId) {
		return officeExpenditureChgOpinionDao.getOfficeExpenditureChgOpinionByTaskId(taskId);
	}

	@Override
	public OfficeExpenditureChgOpinion getOfficeExpenditureChgOpinionById(String id){
		return officeExpenditureChgOpinionDao.getOfficeExpenditureChgOpinionById(id);
	}
	
	public List<OfficeExpenditureChgOpinion> getOfficeExpenditureChgOpinionByTaskIds(String...taskId){
		return officeExpenditureChgOpinionDao.getOfficeExpenditureChgOpinionByTaskIds(taskId);
	}

	public void setOfficeExpenditureChgOpinionDao(OfficeExpenditureChgOpinionDao officeExpenditureChgOpinionDao){
		this.officeExpenditureChgOpinionDao = officeExpenditureChgOpinionDao;
	}
}
	