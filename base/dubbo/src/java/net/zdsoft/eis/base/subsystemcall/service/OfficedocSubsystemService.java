package net.zdsoft.eis.base.subsystemcall.service;

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
	
}
