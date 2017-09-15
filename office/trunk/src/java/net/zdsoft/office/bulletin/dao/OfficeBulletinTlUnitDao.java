package net.zdsoft.office.bulletin.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.office.bulletin.entity.OfficeBulletinTlUnit;
/**
 * office_bulletin_tl_unit
 * @author 
 * 
 */
public interface OfficeBulletinTlUnitDao{

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
	
	/**
	 * 根据通知ID获取接收单位MAP，key为通知ID，value为接收单位列表
	 * @param bulletinIds
	 * @return
	 */
	public Map<String, List<OfficeBulletinTlUnit>> getOfficeBulletinTlUnitMap(String[] bulletinIds);
}