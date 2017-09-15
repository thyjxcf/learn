package net.zdsoft.office.msgcenter.dao;

import java.util.*;

import net.zdsoft.office.msgcenter.entity.OfficeSmsDetail;
import net.zdsoft.keel.util.Pagination;
/**
 * office_sms_detail
 * @author 
 * 
 */
public interface OfficeSmsDetailDao{

	/**
	 * 新增office_sms_detail
	 * @param officeSmsDetail
	 * @return
	 */
	public OfficeSmsDetail save(OfficeSmsDetail officeSmsDetail);

	/**
	 * 根据ids数组删除office_sms_detail
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_sms_detail
	 * @param officeSmsDetail
	 * @return
	 */
	public Integer update(OfficeSmsDetail officeSmsDetail);

	/**
	 * 根据id获取office_sms_detail
	 * @param id
	 * @return
	 */
	public OfficeSmsDetail getOfficeSmsDetailById(String id);

	/**
	 * 根据ids数组查询office_sms_detailmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSmsDetail> getOfficeSmsDetailMapByIds(String[] ids);

	/**
	 * 获取office_sms_detail列表
	 * @return
	 */
	public List<OfficeSmsDetail> getOfficeSmsDetailList();

	/**
	 * 分页获取office_sms_detail列表
	 * @param page
	 * @return
	 */
	public List<OfficeSmsDetail> getOfficeSmsDetailPage(Pagination page);
	
	public void batchSave(List<OfficeSmsDetail> officeSmsDetailList);
	
	public void deleteByInfoIds(String[] infoIds);
	
	public List<OfficeSmsDetail> getOfficeSmsDetailsByInfoId(String infoId);
}