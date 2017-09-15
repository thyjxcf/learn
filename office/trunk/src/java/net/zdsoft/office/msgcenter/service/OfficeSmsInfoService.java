package net.zdsoft.office.msgcenter.service;

import java.util.*;
import net.zdsoft.office.msgcenter.entity.OfficeSmsInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_sms_info
 * @author 
 * 
 */
public interface OfficeSmsInfoService{

	/**
	 * 新增office_sms_info
	 * @param officeSmsInfo
	 * @return
	 */
	public OfficeSmsInfo save(OfficeSmsInfo officeSmsInfo);

	/**
	 * 根据ids数组删除office_sms_info数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_sms_info
	 * @param officeSmsInfo
	 * @return
	 */
	public Integer update(OfficeSmsInfo officeSmsInfo);

	/**
	 * 根据id获取office_sms_info
	 * @param id
	 * @return
	 */
	public OfficeSmsInfo getOfficeSmsInfoById(String id);

	/**
	 * 根据ids数组查询office_sms_infomap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSmsInfo> getOfficeSmsInfoMapByIds(String[] ids);

	/**
	 * 获取office_sms_info列表
	 * @return
	 */
	public List<OfficeSmsInfo> getOfficeSmsInfoList();

	/**
	 * 分页获取office_sms_info列表
	 * @param page
	 * @return
	 */
	public List<OfficeSmsInfo> getOfficeSmsInfoPage(Pagination page);

	/**
	 * 根据UnitId获取office_sms_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSmsInfo> getOfficeSmsInfoByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_sms_info
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSmsInfo> getOfficeSmsInfoByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeSmsInfo> getOfficeSmsInfoByConditions(String unitId, String userId, 
			String searchBeginTime, String searchEndTime, Pagination page);
	
	public void batchDelete(String[] ids);
}