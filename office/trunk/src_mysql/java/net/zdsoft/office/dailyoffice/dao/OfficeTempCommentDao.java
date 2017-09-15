package net.zdsoft.office.dailyoffice.dao;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeTempComment;
import net.zdsoft.keel.util.Pagination;

/**
 * office_temp_comment 
 * @author 
 * 
 */
public interface OfficeTempCommentDao {
	/**
	 * 新增office_temp_comment
	 * @param officeTempComment
	 * @return"
	 */
	public OfficeTempComment save(OfficeTempComment officeTempComment);
	
	/**
	 * 根据ids数组删除office_temp_comment
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_temp_comment
	 * @param officeTempComment
	 * @return
	 */
	public Integer update(OfficeTempComment officeTempComment);
	
	/**
	 * 根据id获取office_temp_comment;
	 * @param id);
	 * @return
	 */
	public OfficeTempComment getOfficeTempCommentById(String id);
		
	/**
	 * 根据ids数组查询office_temp_commentmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTempComment> getOfficeTempCommentMapByIds(String[] ids);
	
	/**
	 * 获取office_temp_comment列表
	 * @return
	 */
	public List<OfficeTempComment> getOfficeTempCommentList();
		
	/**
	 * 分页获取office_temp_comment列表
	 * @param page
	 * @return
	 */
	public List<OfficeTempComment> getOfficeTempCommentPage(Pagination page);
	/**
	 * 通过object_id获取office_temp_comment列表
	 * @return
	 */
	public List<OfficeTempComment> getOfficeTempCommentListByObjectId(String objectId);
	
}
