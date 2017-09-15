/* 
 * @(#)AttachmentDaoImpl.java    Created on Dec 3, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.attachment.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.attachment.dao.AttachmentDao;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 3, 2010 10:05:01 AM $
 */
public class AttachmentDaoImpl extends BaseDao<Attachment> implements AttachmentDao {
    private static final String SQL_INSERT_ATTACHMENT = "INSERT INTO sys_attachment(id,objecttype,filename,"
            + "filesize,contenttype,description,creation_time,modify_time,obj_id,unit_id,"
            + "dir_id,file_path,ext_name,status) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_ATTACHMENT_BY_IDS = "DELETE FROM sys_attachment WHERE id IN ";

    private static final String SQL_UPDATE_ATTACHMENT = "UPDATE sys_attachment SET objecttype=?,filename=?,"
            + "filesize=?,contenttype=?,description=?,modify_time=?,obj_id=?,unit_id=?,"
            + "dir_id=?,file_path=?,ext_name=?,status=? WHERE id=?";

    private static final String SQL_FIND_ATTACHMENT_BY_ID = "SELECT * FROM sys_attachment WHERE id=?";

    private static final String SQL_FIND_ATTACHMENTS_BY_IDS = "SELECT * FROM sys_attachment WHERE id IN";

    private static final String SQL_FIND_ATTACHMENTS_BY_OBJECTID_OBJECTTYPE = "SELECT * FROM sys_attachment WHERE obj_id=? AND objecttype=?";
    private static final String SQL_FIND_ATTACHMENTS_BY_UNITTID_OBJECTTYPE = "SELECT * FROM sys_attachment WHERE unit_id=? AND objecttype=?";
    private static final String SQL_FIND_ATTACHMENTS_BY_UNITTID_OBJECTTYPEFILENAME = "SELECT * FROM sys_attachment WHERE unit_id=? AND objecttype=? AND filename like ?";
    private static final String SQL_FIND_ATTACHMENTS_BY_OBJECTIDS = "SELECT * FROM sys_attachment WHERE obj_id IN";
    
    private static final String SQL_FIND_ATTACHMENTS_BY_OBJECTTYPE_OBJECTIDS = "SELECT * FROM sys_attachment WHERE objecttype=? and obj_id IN";
    
    @Override
    public Attachment setField(ResultSet rs) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(rs.getString("id"));
        attachment.setObjectType(rs.getString("objecttype"));
        attachment.setFileName(rs.getString("filename"));
        attachment.setFileSize(rs.getInt("filesize"));
        attachment.setContentType(rs.getString("contenttype"));
        attachment.setDescription(rs.getString("description"));
        attachment.setCreationTime(rs.getDate("creation_time"));
        attachment.setModifyTime(rs.getDate("modify_time"));
        attachment.setObjectId(rs.getString("obj_id"));
        attachment.setUnitId(rs.getString("unit_id"));
        attachment.setDirId(rs.getString("dir_id"));
        attachment.setFilePath(rs.getString("file_path"));
        attachment.setExtName(rs.getString("ext_name"));
        attachment.setConStatus(rs.getInt("status"));
        attachment.setResultMsg(rs.getString("result_msg"));
        return attachment;
    }

    public void insertAttachment(Attachment attachment) {
        attachment.setCreationTime(new Date());
        attachment.setModifyTime(new Date());
        update(SQL_INSERT_ATTACHMENT, new Object[] { attachment.getId(),
                attachment.getObjectType(), attachment.getFileName(), attachment.getFileSize(),
                attachment.getContentType(), attachment.getDescription(),
                attachment.getCreationTime(), attachment.getModifyTime(), attachment.getObjectId(),
                attachment.getUnitId(), attachment.getDirId(), attachment.getFilePath(),
                attachment.getExtName(), attachment.getConStatus()},
                new int[] { Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
                        Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.CHAR, Types.CHAR,
                        Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER });
    }
    
    public void batchInsertAttachment(List<Attachment> list) {
    	List<Object[]> objs = new ArrayList<Object[]>();
    	for(Attachment attachment : list){
	        attachment.setCreationTime(new Date());
	        attachment.setModifyTime(new Date());
	        if(StringUtils.isBlank(attachment.getId())){
	        	attachment.setId(createId());
	        }
	        objs.add(new Object[] { attachment.getId(),
	                attachment.getObjectType(), attachment.getFileName(), attachment.getFileSize(),
	                attachment.getContentType(), attachment.getDescription(),
	                attachment.getCreationTime(), attachment.getModifyTime(), attachment.getObjectId(),
	                attachment.getUnitId(), attachment.getDirId(), attachment.getFilePath(),
	                attachment.getExtName(), attachment.getConStatus()});
    	}
        batchUpdate(SQL_INSERT_ATTACHMENT, objs,
                new int[] { Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR,
                        Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.CHAR, Types.CHAR,
                        Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER });
    }

    public void deleteAttachment(String[] attachmentIds) {
        updateForInSQL(SQL_DELETE_ATTACHMENT_BY_IDS, null, attachmentIds);
    }

    public void updateAttachment(Attachment attachment) {
        attachment.setModifyTime(new Date());
        update(SQL_UPDATE_ATTACHMENT, new Object[] { attachment.getObjectType(),
                attachment.getFileName(), attachment.getFileSize(), attachment.getContentType(),
                attachment.getDescription(), attachment.getModifyTime(), attachment.getObjectId(),
                attachment.getUnitId(), attachment.getDirId(), attachment.getFilePath(),
                attachment.getExtName(), attachment.getConStatus(), 
                attachment.getId() }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.CHAR, Types.CHAR, Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.CHAR });
    }

    public Attachment getAttachment(String attachmentId) {
        return query(SQL_FIND_ATTACHMENT_BY_ID, attachmentId, new SingleRow());
    }

    public List<Attachment> getAttachments(String[] ids) {
        return queryForInSQL(SQL_FIND_ATTACHMENTS_BY_IDS, null, ids, new MultiRow());
    }

    public List<Attachment> getAttachments(String objectId, String objectType) {
        return query(SQL_FIND_ATTACHMENTS_BY_OBJECTID_OBJECTTYPE, new Object[] { objectId,
                objectType }, new MultiRow());
    }
    
    public List<Attachment> getAttachmentsByUnitId(String unitId, String objectType){
    	return query(SQL_FIND_ATTACHMENTS_BY_UNITTID_OBJECTTYPE, new Object[] { unitId,
                objectType }, new MultiRow());
    }
    
    public Map<String, Attachment> getAttachmentMap(String[] attachmentIds) {
        return queryForInSQL(SQL_FIND_ATTACHMENTS_BY_IDS, null, attachmentIds, new MapRow());
    }
    public Map<String, List<Attachment>> getAttachmentsMap(String... objectIds){
    	List<Attachment> list = queryForInSQL(SQL_FIND_ATTACHMENTS_BY_OBJECTIDS, new Object[]{}, objectIds, new MultiRowMapper<Attachment>() {
			@Override
			public Attachment mapRow(ResultSet rs, int arg1) throws SQLException {
				StrutsPrepareAndExecuteFilter a;
				return setField(rs);
			}
		});
    	Map<String, List<Attachment>> map = new HashMap<String, List<Attachment>>();
    	for(Attachment a : list){
    		String objId = a.getObjectId();
    		List<Attachment> l = map.get(objId);
    		if(l == null){
    			l = new ArrayList<Attachment>();
    			map.put(objId, l);
    		}
    		l.add(a);
    	}
    	return map;
    }
    
    public Map<String, List<Attachment>> getAttachmentsMap(String objectType,String... objectIds){
    	List<Attachment> list = queryForInSQL(SQL_FIND_ATTACHMENTS_BY_OBJECTTYPE_OBJECTIDS, new Object[]{objectType}, objectIds, new MultiRowMapper<Attachment>() {
			@Override
			public Attachment mapRow(ResultSet rs, int arg1) throws SQLException {
				return setField(rs);
			}
		});
    	Map<String, List<Attachment>> map = new HashMap<String, List<Attachment>>();
    	for(Attachment a : list){
    		String objId = a.getObjectId();
    		List<Attachment> l = map.get(objId);
    		if(l == null){
    			l = new ArrayList<Attachment>();
    			map.put(objId, l);
    		}
    		l.add(a);
    	}
    	return map;
    }
    
    @Override
	public Map<String, Attachment> getAttachmentsByTypesMap(String[] objectTypes) {
		String sql="select * from sys_attachment where obj_id=? and unit_id =? and objecttype in";
		return this.queryForInSQL(sql, new Object[]{BaseConstant.ZERO_GUID,BaseConstant.ZERO_GUID},objectTypes, new MapRowMapper<String,Attachment>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("objecttype");
			}
			@Override
			public Attachment mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<Attachment> getAttachmentsByUnitId(String unitId,
			String objectType, String attachmentName) {
		if(StringUtils.isNotBlank(attachmentName)){
			return query(SQL_FIND_ATTACHMENTS_BY_UNITTID_OBJECTTYPEFILENAME, new Object[]{unitId,objectType,attachmentName+"%"}, new MultiRow());
		}else{
			return query(SQL_FIND_ATTACHMENTS_BY_UNITTID_OBJECTTYPE, new Object[] { unitId,
	                objectType }, new MultiRow());
		}
	}
	@Override
	public List<Attachment> getAttachmentsByLikeTypeOrName(String unitId,
			String objectType, String attachmentName) {
		if(StringUtils.isNotBlank(attachmentName)){
			String sql="SELECT * FROM sys_attachment WHERE unit_id=? AND objecttype like ? AND filename like ?";
			return query(sql, new Object[]{unitId,objectType+"%",attachmentName+"%"}, new MultiRow());
		}else{
			String sql="SELECT * FROM sys_attachment WHERE unit_id=? AND objecttype like ?";
			return query(sql, new Object[] { unitId,
	                objectType +"%"}, new MultiRow());
		}
	}

}
