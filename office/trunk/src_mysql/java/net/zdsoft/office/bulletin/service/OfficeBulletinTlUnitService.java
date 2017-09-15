package net.zdsoft.office.bulletin.service;

import java.util.List;

import net.zdsoft.office.bulletin.entity.OfficeBulletinTlUnit;
/**
 * office_bulletin_tl_unit
 * @author 
 * 
 */
public interface OfficeBulletinTlUnitService{

	/**
	 * 批量新增office_bulletin_tl_unit
	 * @param list
	 * @return
	 */
	public void batchSave(List<OfficeBulletinTlUnit> list);

	/**
	 * 根据通知id删除接收单位列表
	 * @param ids
	 * @return
	 */
	public void deleteByBulletinId(String bulletinId);

	/**
	 * 根据通知id获取接收单位列表
	 * @param bulletinId
	 * @return
	 */
	public List<OfficeBulletinTlUnit> getOfficeBulletinTlUnitList(String bulletinId);

}