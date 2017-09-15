package net.zdsoft.eis.base.subsystemcall.service;

import java.util.Map;
/**
 * 校安
 * @author huy
 */
public interface SchsecuritySubsystemService {
	
	/**
	 * 查询当前登录单位代办事项数目
	 * @return
	 * @author huy
	 * @date 2016-1-13下午04:57:32
	 */
	public Map<String,Integer> queryToBeConfirmedCount(String unitId);
	
	/**
	 * 修改指定学校的所在地编码
	 * @param schoolId
	 * @param regionCode
	 * 
	 * @author huy
	 * @date 2016-1-27下午02:32:09
	 */
	public void updateRegionCode(String schoolId,String regionCode, String parentUnitId, int regionLevel);
}
