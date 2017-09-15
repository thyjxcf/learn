package net.zdsoft.office.dailyoffice.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeSignedOn;
/**
 * office_signed_on
 * @author 
 * 
 */
public interface OfficeSignedOnDao{

	/**
	 * 新增office_signed_on
	 * @param officeSignedOn
	 * @return
	 */
	public OfficeSignedOn save(OfficeSignedOn officeSignedOn);

	/**
	 * 根据ids数组删除office_signed_on
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_signed_on
	 * @param officeSignedOn
	 * @return
	 */
	public Integer update(OfficeSignedOn officeSignedOn);

	/**
	 * 根据id获取office_signed_on
	 * @param id
	 * @return
	 */
	public OfficeSignedOn getOfficeSignedOnById(String id);

	/**
	 * 根据ids数组查询office_signed_onmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSignedOn> getOfficeSignedOnMapByIds(String[] ids);

	/**
	 * 获取office_signed_on列表
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnList();

	/**
	 * 分页获取office_signed_on列表
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnPage(Pagination page);

	/**
	 * 根据unitId获取office_signed_on列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_signed_on获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据条件office_signed_on获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByOtherPage(String userId,String unitId,String year,String semseter,String time,Pagination page);
	/**
	 * 根据条件office_signed_on获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnCountByManager(String userId,String unitId,String year,String semseter,String startTime,String endTime,Pagination page);
}