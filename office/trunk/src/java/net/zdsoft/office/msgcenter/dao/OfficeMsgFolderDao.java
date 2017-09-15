package net.zdsoft.office.msgcenter.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolder;
/**
 * 信息文件夹
 * @author 
 * 
 */
public interface OfficeMsgFolderDao{

	/**
	 * 新增信息文件夹
	 * @param officeMsgFolder
	 * @return
	 */
	public OfficeMsgFolder save(OfficeMsgFolder officeMsgFolder);
	
	/**
	 * 是否已经存在该名称
	 * @param id
	 * @param userId
	 * @param folderName
	 * @return
	 */
	public boolean isExist(String id, String userId, String folderName);

	/**
	 * 根据ids数组删除信息文件夹
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新信息文件夹
	 * @param officeMsgFolder
	 * @return
	 */
	public Integer update(OfficeMsgFolder officeMsgFolder);

	/**
	 * 根据id获取信息文件夹
	 * @param id
	 * @return
	 */
	public OfficeMsgFolder getOfficeMsgFolderById(String id);

	/**
	 * 根据ids数组查询信息文件夹map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgFolder> getOfficeMsgFolderMapByIds(String[] ids);

	/**
	 * 获取信息文件夹列表
	 * @return
	 */
	public List<OfficeMsgFolder> getOfficeMsgFolderList(String userId);

	/**
	 * 分页获取信息文件夹列表
	 * @param page
	 * @return
	 */
	public List<OfficeMsgFolder> getOfficeMsgFolderPage(Pagination page);
}