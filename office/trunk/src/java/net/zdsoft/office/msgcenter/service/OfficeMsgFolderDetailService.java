package net.zdsoft.office.msgcenter.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolderDetail;

/**
 * 文件夹详细信息
 * @author 
 * 
 */
public interface OfficeMsgFolderDetailService{

	/**
	 * 新增文件夹详细信息
	 * @param officeMsgFolderDetail
	 * @return
	 */
	public OfficeMsgFolderDetail save(OfficeMsgFolderDetail officeMsgFolderDetail);
	
	/**
	 * 批量保存数据
	 * @param officeMsgFolderDetails
	 */
	public void batchSave(List<OfficeMsgFolderDetail> officeMsgFolderDetails);
	
	/**
	 * 移动到其它文件夹
	 * @param ids
	 * @param folderId
	 */
	public void turnToFolder(String[] ids,String folderId);
	
	/**
	 * 移动单条信息到自定义信息夹
	 * @param id
	 * @param folderId
	 */
	public void turnSingleToFolder(String id, String folderId);
	
	/**
	 * 还原到最开始的文件夹
	 * @param id
	 */
	public void revertMessage(String id);

	/**
	 * 根据ids数组删除文件夹详细信息数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 删除自定义文件夹时，判断是否包含列表数据
	 * @param folderId
	 * @return
	 */
	public boolean isExist(String folderId);
	
	/**
	 * 还原
	 * @param id
	 */
	public void revertById(String id);

	/**
	 * 更新文件夹详细信息
	 * @param officeMsgFolderDetail
	 * @return
	 */
	public Integer update(OfficeMsgFolderDetail officeMsgFolderDetail);

	/**
	 * 根据id获取文件夹详细信息
	 * @param id
	 * @return
	 */
	public OfficeMsgFolderDetail getOfficeMsgFolderDetailById(String id);

	/**
	 * 根据ids数组查询文件夹详细信息map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgFolderDetail> getOfficeMsgFolderDetailMapByIds(String[] ids);

	/**
	 * 分页获取文件夹详细信息列表
	 * @param searchTitle
	 * @param folderId
	 * @param page
	 * @return
	 */
	public List<OfficeMsgFolderDetail> getOfficeMsgFolderDetailPage(String searchTitle, String folderId, Pagination page);
}