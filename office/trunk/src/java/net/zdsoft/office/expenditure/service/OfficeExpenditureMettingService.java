package net.zdsoft.office.expenditure.service;

import java.util.*;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureMetting;
import net.zdsoft.keel.util.Pagination;
/**
 * 会议费
 * @author 
 * 
 */
public interface OfficeExpenditureMettingService{

	/**
	 * 新增会议费
	 * @param officeExpenditureMetting
	 * @return
	 */
	public OfficeExpenditureMetting save(OfficeExpenditureMetting officeExpenditureMetting);

	/**
	 * 根据ids数组删除会议费数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新会议费
	 * @param officeExpenditureMetting
	 * @return
	 */
	public Integer update(OfficeExpenditureMetting officeExpenditureMetting);
	
	/**
	 * 根据exids获取map
	 * @param officeExpenditureIds
	 * @return
	 */
	Map<String, OfficeExpenditureMetting> getOfficeExpenditureMettingByExIds(
			String[] officeExpenditureIds);

	/**
	 * 根据id获取会议费
	 * @param id
	 * @return
	 */
	public OfficeExpenditureMetting getOfficeExpenditureMettingById(String id);
	
	public OfficeExpenditureMetting getOfficeExpenditureMettingByPrimarId(String officeExpenditureId);
	
	public void deleteByExId(String officeExpenditureId);
}