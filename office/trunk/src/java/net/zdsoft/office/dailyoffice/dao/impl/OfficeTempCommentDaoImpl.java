package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeTempComment;
import net.zdsoft.office.dailyoffice.dao.OfficeTempCommentDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringUtils;

/**
 * office_temp_comment 
 * @author 
 * 
 */
public class OfficeTempCommentDaoImpl extends BaseDao<OfficeTempComment> implements OfficeTempCommentDao{
	@Override
	public OfficeTempComment setField(ResultSet rs) throws SQLException{
		OfficeTempComment officeTempComment = new OfficeTempComment();
		officeTempComment.setId(rs.getString("id"));
		officeTempComment.setObjectId(rs.getString("object_id"));
		officeTempComment.setTitle(rs.getString("title"));
		Clob clob = rs.getClob("html_content");
		if(clob != null){
			officeTempComment.setHtmlContent(clob.getSubString(1, (int)clob.length()));
		}
		return officeTempComment;
	}
	
	@Override
	public OfficeTempComment save(OfficeTempComment officeTempComment){
		String sql = "insert into office_temp_comment(id, object_id, title, html_content) values(?,?,?,?)"; 
		if (StringUtils.isBlank(officeTempComment.getId())){
			officeTempComment.setId(createId());
		}
		Object[] args = new Object[]{
			officeTempComment.getId(), officeTempComment.getObjectId(), 
			officeTempComment.getTitle(), officeTempComment.getHtmlContent()
		};
		update(sql, args);
		return officeTempComment;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_temp_comment where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeTempComment officeTempComment){
		String sql = "update office_temp_comment set object_id = ?, title = ?, html_content = ? where id = ?";
		Object[] args = new Object[]{
			officeTempComment.getObjectId(), officeTempComment.getTitle(), 
			officeTempComment.getHtmlContent(), officeTempComment.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeTempComment getOfficeTempCommentById(String id){
		String sql = "select * from office_temp_comment where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTempComment> getOfficeTempCommentMapByIds(String[] ids){
		String sql = "select * from office_temp_comment where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTempComment> getOfficeTempCommentList(){
		String sql = "select * from office_temp_comment";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeTempComment> getOfficeTempCommentPage(Pagination page){
		String sql = "select * from office_temp_comment";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeTempComment> getOfficeTempCommentListByObjectId(String objectId) {
		String sql = "select * from office_temp_comment where object_id = ?";
		return query(sql, new Object[]{objectId },new MultiRow());
	}
	
}


	