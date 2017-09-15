package net.zdsoft.office.secretaryMail.dao;

import java.util.*;
import net.zdsoft.office.secretaryMail.entity.OfficeJzmailInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_jzmail_info
 * @author 
 * 
 */
public interface OfficeJzmailInfoDao{

	/**
	 * 新增office_jzmail_info
	 * @param officeJzmailInfo
	 * @return
	 */
	public OfficeJzmailInfo save(OfficeJzmailInfo officeJzmailInfo);

	/**
	 * 根据ids数组删除office_jzmail_info
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void deleteAll();

	/**
	 * 更新office_jzmail_info
	 * @param officeJzmailInfo
	 * @return
	 */
	public Integer update(OfficeJzmailInfo officeJzmailInfo);

	/**
	 * 根据id获取office_jzmail_info
	 * @param id
	 * @return
	 */
	public OfficeJzmailInfo getOfficeJzmailInfoById(String id);

	/**
	 * 根据ids数组查询office_jzmail_infomap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeJzmailInfo> getOfficeJzmailInfoMapByIds(String[] ids);

	/**
	 * 获取office_jzmail_info列表
	 * @return
	 */
	public List<OfficeJzmailInfo> getOfficeJzmailInfoList();

	/**
	 * 分页获取office_jzmail_info列表
	 * @param page
	 * @return
	 */
	public List<OfficeJzmailInfo> getOfficeJzmailInfoPage(Pagination page);

	/**
	 * 根据unitId获取office_jzmail_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_jzmail_info获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdPage(String unitId, Pagination page);
}