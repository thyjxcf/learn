package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.subsystemcall.entity.OfficedocMsgDto;
import net.zdsoft.eis.base.subsystemcall.entity.OfficedocStepInfoDto;

public interface OfficedocSubsystemService {
	/**
	 * 收文督办未处理1
	 * 收文阅办未处理2
	 * 发文未处理3
	 * 发文未发送4
	 * 收文未签收、登记5
	 * @param officedocType
	 * @return
	 */
	public OfficedocMsgDto getIndexItem(String officedocType,String userId);
	
	public Map<String,OfficedocStepInfoDto> getStepInfoMap(boolean isSend,String unitId);
	/**
	 * 微办公获取数量
	 * @param userId
	 * @param unitId
	 * @return
	 */
	public Map<String,Integer> getOfficedocNum(String userId,String unitId);
	
	
	/**
	 * 公文待处理数
	 */
	String remoteOfficedocHaveDoNumber(String remoteParam);
	
	/**
	 * 公文编辑--保存
	 * @param remoteParam
	 * @param bytes TODO
	 * @return
	 */
	String remoteSaveAttachment(String remoteParam, byte[] bytes);
	
	/**
	 * 发文督办阅办已处理列表
	 * @param remoteParam
	 * @return
	 */
	String remoteHaveDoOfficedocList(String remoteParam);
	
	/**
	 * 发文督办阅办待处理列表
	 * @param remoteParam
	 * @return
	 */
	String remoteOfficedocList(String remoteParam);
	
	/**
	 * 发文督办阅办详情
	 * @param remoteParam
	 * @return
	 */
	String remoteOfficedoc(String remoteParam);
	
	/**
	 * 批注处理--加签
	 * @param remoteParam
	 * @return
	 */
	String remotePassWithAddStepOfficedoc(String remoteParam, byte[] bytes);
	
	/**
	 * 批注处理--退回
	 * @param remoteParam
	 * @return
	 */
	String remoteBackOfficedoc(String remoteParam, byte[] bytes);
	
	/**
	 * 批注处理--通过
	 * @param remoteParam
	 * @return
	 */
	String remotePassOfficedoc(String remoteParam, byte[] bytes);
	
	/**
	 * 签收(待签收、已签收)
	 * @param remoteParam
	 * @return
	 */
	String remoteRegisterOfficedocList(String remoteParam);
	
	/**
	 * 签收处理
	 * @param remoteParam
	 * @return
	 */
	String remoteSignOfficedoc(String remoteParam);
	
	/**
	 * 签收详情
	 * @param remoteParam
	 * @return
	 */
	String remoteRegisterOfficedoc(String remoteParam);
	
	/**
	 * 详情页主送列表
	 * @param remoteParam
	 * @return
	 */
	String remoteMainOfficedoc(String remoteParam);
	
	/**
	 * 详情页抄送列表
	 * @param remoteParam
	 * @return
	 */
	String remoteCopyOfficedoc(String remoteParam);
	/**
	 * 详情页传阅列表
	 * @param remoteParam
	 * @return
	 */
	String remoteReadOfficedoc(String remoteParam);
	
	/**
	 * 转发(未转发、已转发)
	 * @param remoteParam
	 * @return
	 */
	String remoteSendOfficedocList(String remoteParam);
	
	/**
	 * 转发处理
	 * @param remoteParam
	 * @return
	 */
	String remoteOfficedocSend(String remoteParam);
	
	/**
	 * 转发详情
	 * @param remoteParam
	 * @return
	 */
	String remoteSendOfficedoc(String remoteParam);
	/**
	 * 公文发文，收文办理中有权限模块的ids
	 * @param userId
	 * @return
	 * 2017年4月7日
	 */
	Map<String, List<String>> officedocSubModelIds(String userId);
	/**
	 * 登记跳过流程
	 * @param remoteParam
	 * @return
	 * 2017年4月12日
	 */
	String remoteDoRegisterOfficedoc(String remoteParam);
	/**
	 * 登记编辑
	 * @param remoteParam
	 * @return
	 * 2017年4月12日
	 */
	String remoteToRegisterOfficedoc(String remoteParam);
	/**
	 * 转发保存--主抄送人员
	 * @param remoteParam
	 * @return
	 * 2017年4月12日
	 */
	String remoteSaveSendOfficedoc(String remoteParam);
	
	/**
	 * 待办事项详情
	 * @param remoteParam
	 */
	public String getTodoWorkDetails(String remoteParam);
	
}
