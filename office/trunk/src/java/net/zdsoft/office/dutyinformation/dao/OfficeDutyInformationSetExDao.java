package net.zdsoft.office.dutyinformation.dao;

import java.util.*;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSetEx;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_information_set_ex
 * @author 
 * 
 */
public interface OfficeDutyInformationSetExDao{

	/**
	 * 新增office_duty_information_set_ex
	 * @param officeDutyInformationSetEx
	 * @return
	 */
	public OfficeDutyInformationSetEx save(OfficeDutyInformationSetEx officeDutyInformationSetEx);

	public void batchSave(List<OfficeDutyInformationSetEx> officeDutyInformationSetExs);
	/**
	 * 根据ids数组删除office_duty_information_set_ex
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void delete(String dutyId);

	/**
	 * 更新office_duty_information_set_ex
	 * @param officeDutyInformationSetEx
	 * @return
	 */
	public Integer update(OfficeDutyInformationSetEx officeDutyInformationSetEx);

	/**
	 * 根据id获取office_duty_information_set_ex
	 * @param id
	 * @return
	 */
	public OfficeDutyInformationSetEx getOfficeDutyInformationSetExById(String id);

	/**
	 * 根据ids数组查询office_duty_information_set_exmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDutyInformationSetEx> getOfficeDutyInformationSetExMapByIds(String[] ids);

	/**
	 * 获取office_duty_information_set_ex列表
	 * @return
	 */
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExList();

	/**
	 * 分页获取office_duty_information_set_ex列表
	 * @param page
	 * @return
	 */
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExPage(Pagination page);
	
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExsByDutyId(String dutyId);
}