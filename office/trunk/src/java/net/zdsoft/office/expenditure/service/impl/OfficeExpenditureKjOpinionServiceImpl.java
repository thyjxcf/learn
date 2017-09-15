package net.zdsoft.office.expenditure.service.impl;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureKjOpinion;
import net.zdsoft.office.expenditure.service.OfficeExpenditureKjOpinionService;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureKjOpinionDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_kj_opinion
 * @author 
 * 
 */
public class OfficeExpenditureKjOpinionServiceImpl implements OfficeExpenditureKjOpinionService{
	private OfficeExpenditureKjOpinionDao officeExpenditureKjOpinionDao;

	@Override
	public OfficeExpenditureKjOpinion save(OfficeExpenditureKjOpinion officeExpenditureKjOpinion){
		return officeExpenditureKjOpinionDao.save(officeExpenditureKjOpinion);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExpenditureKjOpinionDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpenditureKjOpinion officeExpenditureKjOpinion){
		return officeExpenditureKjOpinionDao.update(officeExpenditureKjOpinion);
	}

	@Override
	public OfficeExpenditureKjOpinion getOfficeExpenditureKjOpinionByTaskId(
			String taskId) {
		return officeExpenditureKjOpinionDao.getOfficeExpenditureKjOpinionByTaskId(taskId);
	}

	@Override
	public OfficeExpenditureKjOpinion getOfficeExpenditureKjOpinionById(String id){
		return officeExpenditureKjOpinionDao.getOfficeExpenditureKjOpinionById(id);
	}
	
	public List<OfficeExpenditureKjOpinion> getOfficeExpenditureKjOpinionByTaskIds(String...taskId){
		return officeExpenditureKjOpinionDao.getOfficeExpenditureKjOpinionByTaskIds(taskId);
	}

	public void setOfficeExpenditureKjOpinionDao(OfficeExpenditureKjOpinionDao officeExpenditureKjOpinionDao){
		this.officeExpenditureKjOpinionDao = officeExpenditureKjOpinionDao;
	}
}
