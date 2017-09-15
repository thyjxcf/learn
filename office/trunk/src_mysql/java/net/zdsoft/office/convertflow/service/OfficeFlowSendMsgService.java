package net.zdsoft.office.convertflow.service;

import net.zdsoft.jbpm.core.entity.TaskHandlerResult;

public interface OfficeFlowSendMsgService {
	
	/**
	 * 启动流程时发送消息 通知下一步审核人
	 * @param obj
	 * @param type
	 */
	public void startFlowSendMsg(Object obj, Integer type);
	
	/**
	 * 处理流程时发送消息，流转结束时通知申请人
	 * @param curUserId
	 * @param isPass
	 * @param obj
	 * @param type
	 * @param result
	 */
	public void completeTaskSendMsg(String curUserId, boolean isPass, Object obj, Integer type, TaskHandlerResult result);
}
