package net.zdsoft.office.repaire.dao;

import java.util.*;

import net.zdsoft.office.repaire.entity.OfficeRepaireSms;
import net.zdsoft.keel.util.Pagination;
/**
 * office_repaire_sms
 * @author 
 * 
 */
public interface OfficeRepaireSmsDao{

	/**
	 * 新增office_repaire_sms
	 * @param officeRepaireSms
	 * @return
	 */
	public OfficeRepaireSms save(OfficeRepaireSms officeRepaireSms);

	/**
	 * 根据ids数组删除office_repaire_sms
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_repaire_sms
	 * @param officeRepaireSms
	 * @return
	 */
	public Integer update(OfficeRepaireSms officeRepaireSms);

	/**
	 * 根据id获取office_repaire_sms
	 * @param id
	 * @return
	 */
	public OfficeRepaireSms getOfficeRepaireSmsById(String id);

	/**
	 * 根据ids数组查询office_repaire_smsmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeRepaireSms> getOfficeRepaireSmsMapByIds(String[] ids);

	/**
	 * 获取office_repaire_sms列表
	 * @return
	 */
	public List<OfficeRepaireSms> getOfficeRepaireSmsList();

	/**
	 * 分页获取office_repaire_sms列表
	 * @param page
	 * @return
	 */
	public List<OfficeRepaireSms> getOfficeRepaireSmsPage(Pagination page);

	/**
	 * 根据unitId获取office_repaire_sms列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_repaire_sms获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdPage(String unitId, Pagination page);
	
	public void batchInsert(List<OfficeRepaireSms> insertList);
	
	public void batchUpdate(List<OfficeRepaireSms> updateList);
	
	public List<OfficeRepaireSms> getOfficeRepaireSmsByTypeId(String unitId, String typeId);
}