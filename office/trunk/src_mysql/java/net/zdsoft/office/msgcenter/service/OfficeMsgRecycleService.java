package net.zdsoft.office.msgcenter.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.entity.OfficeMsgRecycle;
/**
 * 信息回收
 * @author 
 * 
 */
public interface OfficeMsgRecycleService{

	/**
	 * 新增信息回收
	 * @param officeMsgRecycle
	 * @return
	 */
	public OfficeMsgRecycle save(OfficeMsgRecycle officeMsgRecycle);
	
	/**
	 * 批量保存
	 * @param officeMsgRecycles
	 */
	public void batchSave(List<OfficeMsgRecycle> officeMsgRecycles);

	/**
	 * 根据ids数组删除信息回收数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 从废件箱移动到其它文件夹
	 * @param userId
	 * @param deleteIds
	 * @param folderId
	 */
	public void turnToFolder(String userId, String[] ids,String folderId);
	
	/**
	 * 移动单条信息到自定义信息夹
	 * @param id
	 * @param folderId
	 */
	public void turnSingleToFolder(String id, String folderId);

	/**
	 * 更新信息回收
	 * @param officeMsgRecycle
	 * @return
	 */
	public Integer update(OfficeMsgRecycle officeMsgRecycle);

	/**
	 * 根据id获取信息回收
	 * @param id
	 * @return
	 */
	public OfficeMsgRecycle getOfficeMsgRecycleById(String id);

	/**
	 * 根据ids数组查询信息回收map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgRecycle> getOfficeMsgRecycleMapByIds(String[] ids);

	/**
	 * 分页获取信息回收列表
	 * @param page
	 * @return
	 */
	public List<OfficeMsgRecycle> getOfficeMsgRecyclePage(String userId, String searchTitle, Pagination page);

	/**
	 * 从废件箱还原信息
	 * @param id
	 */
	public void revertMessage(String id);
}