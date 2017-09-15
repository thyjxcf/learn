package net.zdsoft.office.officeFlow.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.dto.TaskDesc;

public interface OfficeFlowService {

	public List<HisTask> getHisTask(String flowId);
	
	/**
	 * 校验是否可以撤回，并返回json格式信息
	 * @param hisTaskList
	 * @return
	 */
	public String checkRetract(List<HisTask> hisTaskList, String userId, String flowId);
	
	/**
	 * 获取流程中自己审核过的流程
	 * @param list
	 * @param userId
	 * @param page
	 * @return
	 */
	public <T> List<T> haveDoneAudit(List<T> list, String userId, Pagination page) ;
	
	/**
	 * 获取下一步信息
	 * @param unitId
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public List<TaskDesc> getTaskDescList(String unitId, String userId, String taskId);
	
	/**
	 * 获取下一步任务个数
	 * @param unitId
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public int getTaskDescNum(String unitId, String userId, String taskId);
	
	/**
	 * 获取流程下一步的任务信息
	 * @param unitId
	 * @param userId
	 * @param state 0-未进入流程；1-进入流程
	 * @param flowId
	 * @param taskId
	 * @param condition 条件，如：${day > 1}
	 * @return
	 */
	public String getNextTaskInfo(String unitId, String userId, String state, String flowId, String taskId, String condition);
	
	public String getFirstTask(String unitId, String userId, String flowId, String condition);
	
	public String getNextTask(String unitId, String userId, String taskId, String condition);
	
	public String getCurrentTasks(String flowId);
	
	/**
	 * 撤回
	 * @param nowTaskId 当前任务
	 * @param oldTaskId 欲撤回到的任务（撤回后，taskId会重新生成）
	 * @param taskDefinitionKey 欲撤回的任务key
	 */
	public void retractFlow(String nowTaskId, String oldTaskId, String taskDefinitionKey);
	
	/**
	 * 判断是否需要发送消息
	 * @param flowId
	 * @param taskDefinitionKey
	 * @return
	 */
	public boolean checkSendFlowMsg(String flowId, String taskDefinitionKey);
}
