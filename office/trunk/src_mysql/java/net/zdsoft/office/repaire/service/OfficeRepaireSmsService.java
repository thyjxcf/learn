package net.zdsoft.office.repaire.service;

import java.util.*;
import net.zdsoft.office.repaire.entity.OfficeRepaireSms;
import net.zdsoft.keel.util.Pagination;
/**
 * office_repaire_sms
 * @author 
 * 
 */
public interface OfficeRepaireSmsService{

	/**
	 * 新增office_repaire_sms
	 * @param officeRepaireSms
	 * @return
	 */
	public OfficeRepaireSms save(OfficeRepaireSms officeRepaireSms);

	/**
	 * 根据ids数组删除office_repaire_sms数据
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
	 * 根据UnitId获取office_repaire_sms列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_repaire_sms
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdPage(String unitId, Pagination page);
	
	public void batchInsert(List<OfficeRepaireSms> insertList);
	
	public void batchUpdate(List<OfficeRepaireSms> updateList);
	
	/**
	 * 通过单位ID、及类型ID获取短信接收信息
	 * @param unitId
	 * @param typeId
	 * @return
	 */
	public OfficeRepaireSms getOfficeRepaireSmsByTypeId(String unitId, String typeId);
}