package net.zdsoft.office.convertflow.dao;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlowTask;
/**
 * office_convert_flow_task
 * @author 
 * 
 */
public interface OfficeConvertFlowTaskDao{
	
	/**
	 * 新增
	 * @param ent
	 */
	public void save(OfficeConvertFlowTask ent);

	/**
	 * 批量新增
	 * @param list
	 */
	public void batchInsert(List<OfficeConvertFlowTask> list);
	
	
	/**
	 * 根据cfId获取所有的task数据
	 * @param convertFlowId
	 * @return
	 */
	public List<OfficeConvertFlowTask> getListByCfId(String convertFlowId);
	
	/**
	 * 更新office_convert_flow_task
	 * @param officeConvertFlowTask
	 * @return
	 */
	public Integer update(OfficeConvertFlowTask officeConvertFlowTask);
	
	/**
	 * 通过taskId更新信息
	 * @param auditUserId
	 * @param status
	 * @param parm
	 */
	public void updateByParm(String auditUserId, int status, String parm);
	
	/**
	 * 通过convertFlowId更新信息
	 * @param status
	 * @param convertFlowId
	 */
	public void updateByConvertFlowId(int status, String convertFlowId);
	/**
	 * 通过convertFlowId删除
	 * @param convertFlowId
	 * @return
	 * 2016年9月11日
	 */
	public int deleteByConvertFlowId(String convertFlowId);
	
	public OfficeConvertFlowTask getConvertFlowByTaskId(String taskId);
	
	public int delete(String convertFlowId, String[] taskIds);
}