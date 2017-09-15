package net.zdsoft.office.convertflow.service;

import net.sf.json.JSONObject;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;

public interface OfficeMobileValidateService {
	
	
	/**
	 * 邮件消息预校验
	 * @param unitId TODO
	 * @param userId
	 * @param id
	 * @return
	 */
	JSONObject validateMessage(String unitId, String userId, String id);
	
	/**
	 * 工作汇报预校验
	 * @param id
	 * @return
	 */
	JSONObject validateWorkReport(String id);
	
	/**
	 * 预校验流程详情
	 * @param moduleType
	 * @param id
	 * @return
	 */
	JSONObject validateFlowDetail(String moduleType, String id);
	
	/**
	 * 预校验流程审核
	 * @param moduleType
	 * @param id
	 * @param taskId
	 * @param userId
	 * @return
	 */
	JSONObject validateFlowAudit(String moduleType, String id, String taskId, String userId);
	
	
	/**
	 * 通知公告详情预校验
	 * @param id
	 * @return
	 */
	public JSONObject validateBulletinDeatil(String id);
	
	/**
	 * 物品审核预校验
	 * @param unitId
	 * @param userId
	 * @param id
	 * @return
	 */
	public JSONObject validateGoodsAudit(String unitId, String userId, String id);
	
	/**
	 * 物品详情
	 * @param unitId
	 * @param userId
	 * @param id
	 * @return
	 */
	public JSONObject validateGoodsDeatil(String unitId, String userId,String id);
	
	/**
	 * 报修详情
	 * @param userId
	 * @param id
	 * @return
	 */
	public JSONObject validateRepairDetail(String userId, String id);
	
	/**
	 *-报修处理
	 * @param userId
	 * @param id
	 * @return
	 */
	public JSONObject validateRepairAudit(String userId, String id);
	
}
