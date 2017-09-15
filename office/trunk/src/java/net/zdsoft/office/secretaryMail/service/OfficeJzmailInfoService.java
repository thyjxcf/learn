package net.zdsoft.office.secretaryMail.service;

import java.util.*;
import net.zdsoft.office.secretaryMail.entity.OfficeJzmailInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_jzmail_info
 * @author 
 * 
 */
public interface OfficeJzmailInfoService{

	/**
	 * 新增office_jzmail_info
	 * @param officeJzmailInfo
	 * @return
	 */
	public OfficeJzmailInfo save(OfficeJzmailInfo officeJzmailInfo);

	/**
	 * 根据ids数组删除office_jzmail_info数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

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
	 * 根据UnitId获取office_jzmail_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_jzmail_info
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdPage(String unitId, Pagination page);
}