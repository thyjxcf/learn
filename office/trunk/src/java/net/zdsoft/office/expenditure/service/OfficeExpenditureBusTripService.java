package net.zdsoft.office.expenditure.service;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureBusTrip;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_bus_trip
 * @author 
 * 
 */
public interface OfficeExpenditureBusTripService{

	/**
	 * 新增office_expenditure_bus_trip
	 * @param officeExpenditureBusTrip
	 * @return
	 */
	public OfficeExpenditureBusTrip save(OfficeExpenditureBusTrip officeExpenditureBusTrip);

	/**
	 * 根据ids数组删除office_expenditure_bus_trip数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_expenditure_bus_trip
	 * @param officeExpenditureBusTrip
	 * @return
	 */
	public Integer update(OfficeExpenditureBusTrip officeExpenditureBusTrip);
	
	/**
	 * 根据exIds获取map
	 * @param officeExpenditureIds
	 * @return
	 */
	Map<String, OfficeExpenditureBusTrip> getOfficeExpenditureBusTripByExIds(
			String[] officeExpenditureIds);

	/**
	 * 根据id获取office_expenditure_bus_trip
	 * @param id
	 * @return
	 */
	public OfficeExpenditureBusTrip getOfficeExpenditureBusTripById(String id);
	
	public OfficeExpenditureBusTrip getOfficeExpenditureBusTripByExId(String expenditureId);
	
	public void deleteByExId(String officeExpenditureId);

}