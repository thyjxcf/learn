package net.zdsoft.office.dailyoffice.service;


import java.util.Date;
import java.util.List;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeReception;
/**
 * office_reception
 * @author 
 * 
 */
public interface OfficeReceptionService{

	/**
	 * 新增office_reception
	 * @param officeReception
	 * @return
	 */
	public OfficeReception save(OfficeReception officeReception);

	/**
	 * 根据ids数组删除office_reception数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_reception
	 * @param officeReception
	 * @return
	 */
	public Integer update(OfficeReception officeReception);

	/**
	 * 根据id获取office_reception
	 * @param id
	 * @return
	 */
	public OfficeReception getOfficeReceptionById(String id);
	
	/**
	 * 根据unitId获取office_reception列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeReception> getOfficeReceptionByUnitId(String unitId);

	
	/**
	 * 根据unitId获取获取office_reception列表带分页
	 * @param unitId
	 * @param page
	 * @return
	 */

	public List<OfficeReception> getOfficeReceptionByUnitIdWithPage(
			Date startTime, Date endTime,
			String unitId, Pagination page);
}

