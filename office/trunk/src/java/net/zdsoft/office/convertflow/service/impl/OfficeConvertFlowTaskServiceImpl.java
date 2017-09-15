package net.zdsoft.office.convertflow.service.impl;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.dao.OfficeConvertFlowTaskDao;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlowTask;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowTaskService;
/**
 * office_convert_flow_task
 * @author 
 * 
 */
public class OfficeConvertFlowTaskServiceImpl implements OfficeConvertFlowTaskService{
	private OfficeConvertFlowTaskDao officeConvertFlowTaskDao;
	
	public void setOfficeConvertFlowTaskDao(
			OfficeConvertFlowTaskDao officeConvertFlowTaskDao) {
		this.officeConvertFlowTaskDao = officeConvertFlowTaskDao;
	}
	
	public void save(OfficeConvertFlowTask ent){
		officeConvertFlowTaskDao.save(ent);
	}

	public void batchInsert(List<OfficeConvertFlowTask> list){
		officeConvertFlowTaskDao.batchInsert(list);
	}
	
	public List<OfficeConvertFlowTask> getListByCfId(String convertFlowId){
		return officeConvertFlowTaskDao.getListByCfId(convertFlowId);
	}

	@Override
	public Integer update(OfficeConvertFlowTask officeConvertFlowTask){
		return officeConvertFlowTaskDao.update(officeConvertFlowTask);
	}
	
	public void updateByParm(String auditUserId, int status, String parm){
		officeConvertFlowTaskDao.updateByParm(auditUserId, status, parm);
	}
	
	public void updateByConvertFlowId(int status, String convertFlowId){
		officeConvertFlowTaskDao.updateByConvertFlowId(status, convertFlowId);
	}

	@Override
	public int deleteByConvertFlowId(String convertFlowId) {
		return officeConvertFlowTaskDao.deleteByConvertFlowId(convertFlowId);
	}
	
	@Override
	public OfficeConvertFlowTask getConvertFlowByTaskId(String taskId){
		return officeConvertFlowTaskDao.getConvertFlowByTaskId(taskId);
	}
	
	@Override
	public int delete(String convertFlowId, String[] taskIds){
		return officeConvertFlowTaskDao.delete(convertFlowId, taskIds);
	}
}