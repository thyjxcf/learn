package net.zdsoft.office.bulletin.dao.impl;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinXjDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinXj;

import org.apache.commons.lang.StringUtils;
/**
 * office_bulletin_xj
 * @author 
 * 
 */
public class OfficeBulletinXjDaoImpl extends BaseDao<OfficeBulletinXj> implements OfficeBulletinXjDao{

	@Override
	public OfficeBulletinXj setField(ResultSet rs) throws SQLException{
		OfficeBulletinXj officeBulletinXj = new OfficeBulletinXj();
		officeBulletinXj.setId(rs.getString("id"));
		officeBulletinXj.setUnitId(rs.getString("unit_id"));
		officeBulletinXj.setTitle(rs.getString("title"));
		Clob clob = rs.getClob("content");
		if(clob != null){
			officeBulletinXj.setContent(clob.getSubString(1, (int)clob.length()));
		}
		officeBulletinXj.setCreateTime(rs.getTimestamp("create_time"));
		officeBulletinXj.setEndTime(rs.getTimestamp("end_time"));
		officeBulletinXj.setPublishTime(rs.getTimestamp("publish_time"));
		officeBulletinXj.setCreateUserId(rs.getString("create_user_id"));
		officeBulletinXj.setAuditUserId(rs.getString("audit_user_id"));
		officeBulletinXj.setAuditTime(rs.getTimestamp("audit_time"));
		officeBulletinXj.setIdea(rs.getString("idea"));
		officeBulletinXj.setIsTop(rs.getBoolean("is_top"));
		officeBulletinXj.setPlaceTopTime(rs.getTimestamp("place_top_time"));
		officeBulletinXj.setState(rs.getString("state"));
		officeBulletinXj.setType(rs.getString("type"));
		officeBulletinXj.setIsAll(rs.getBoolean("is_all"));
		officeBulletinXj.setOrderId(rs.getObject("order_id")==null?null:rs.getInt("order_id"));
		officeBulletinXj.setScope(rs.getString("scope"));
		officeBulletinXj.setEndType(rs.getString("end_type"));
		officeBulletinXj.setAreaId(rs.getString("area_id"));
		officeBulletinXj.setModifyUserId(rs.getString("modify_user_id"));
		officeBulletinXj.setIsDeleted(rs.getInt("is_deleted"));
		officeBulletinXj.setReleaseScope(rs.getString("release_scope"));
		officeBulletinXj.setIssue(rs.getInt("issue"));
		officeBulletinXj.setTempCommentId(rs.getString("temp_comment_id"));
		officeBulletinXj.setMessageNumber(rs.getString("message_number"));
		return officeBulletinXj;
	}

	@Override
	public OfficeBulletinXj save(OfficeBulletinXj officeBulletinXj){
		String sql = "insert into office_bulletin_xj(id, title, content, create_time, end_time, publish_time, create_user_id, audit_user_id, audit_time, idea, is_top, place_top_time, state, type, is_all, unit_id, order_id, end_type, area_id, modify_user_id, is_deleted, scope, release_scope, issue, temp_comment_id, message_number) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeBulletinXj.getId())){
			officeBulletinXj.setId(createId());
		}
		Object[] args = new Object[]{
			officeBulletinXj.getId(), officeBulletinXj.getTitle(), 
			officeBulletinXj.getContent(), officeBulletinXj.getCreateTime(), 
			officeBulletinXj.getEndTime(), officeBulletinXj.getPublishTime(), 
			officeBulletinXj.getCreateUserId(), officeBulletinXj.getAuditUserId(), 
			officeBulletinXj.getAuditTime(), officeBulletinXj.getIdea(), 
			officeBulletinXj.getIsTop(), officeBulletinXj.getPlaceTopTime(), 
			officeBulletinXj.getState(), officeBulletinXj.getType(), 
			officeBulletinXj.getIsAll(), officeBulletinXj.getUnitId(), 
			officeBulletinXj.getOrderId(), officeBulletinXj.getEndType(), 
			officeBulletinXj.getAreaId(), officeBulletinXj.getModifyUserId(), 
			officeBulletinXj.getIsDeleted(), officeBulletinXj.getScope(), 
			officeBulletinXj.getReleaseScope(), officeBulletinXj.getIssue(), 
			officeBulletinXj.getTempCommentId(), officeBulletinXj.getMessageNumber()
		};
		update(sql, args);
		return officeBulletinXj;
	}
	
	@Override
	public Integer getLatestIssue(String unitId, String type) {
		String sql = "select max(issue) from office_bulletin_xj where is_deleted = 0 and unit_id = ? and type = ? ";
		return queryForInt(sql, new Object[]{unitId, type});
	}
	
	@Override
	public boolean isIssueExist(String unitId, String type, int issue, String bulletinId) {
		String sql = "select count(1) from office_bulletin_xj where is_deleted = 0 and unit_id = ? and type = ? and issue = ? ";
		if(StringUtils.isNotBlank(bulletinId)){
			sql += " and id <> ? ";
			int i = queryForInt(sql, new Object[]{unitId, type, issue, bulletinId});
			if(i > 0){
				return true;
			}
			return false;
		}else{
			int i = queryForInt(sql, new Object[]{unitId, type, issue});
			if(i > 0){
				return true;
			}
			return false;
		}
	}

	@Override
	public Integer delete(String id, String userId){
		String sql = "update office_bulletin_xj set is_deleted = 1, modify_user_id = ? where id = ? ";
		return update(sql, new Object[]{userId,id});
	}
	
	@Override
	public Integer deleteIds(String[] ids,String userId){
		String sql = "update office_bulletin_xj set is_deleted = 1, modify_user_id = ? where id in ";
		return updateForInSQL(sql, new Object[]{userId}, ids);
	}
	
	@Override
	public Integer publish(String[] ids,String userId) {
		String sql = "update office_bulletin_xj set state = 3, publish_time = sysdate,  audit_user_id = ?, audit_time = sysdate where id in";
		return updateForInSQL(sql, new Object[]{userId}, ids);
	}
	@Override
	public Integer qxPublish(String id,String userId) {
		String sql = "update office_bulletin_xj set state = 1, publish_time = '',  audit_user_id = ?, audit_time = sysdate where id = ? ";
		return update(sql, new Object[]{userId,id});
	}
	
	@Override
	public Integer unPublish(String id,String userId,String idea) {
		String sql = "update office_bulletin_xj set state = 4, publish_time = sysdate, audit_user_id = ?, audit_time = sysdate, idea = ? where id = ? ";
		return update(sql, new Object[]{userId,idea,id});
	}
	@Override
	public void top(String bulletinId) {
		String sql = "update office_bulletin_xj set place_top_time = sysdate, is_top = 1 where id = ? ";
		update(sql, bulletinId);
	}
	@Override
	public void qxTop(String bulletinId) {
		String sql = "update office_bulletin_xj set place_top_time = '', is_top = 0 where id = ? ";
		update(sql, bulletinId);
	}
	@Override
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId) {
		String sql = "update office_bulletin_xj set order_id = ?, modify_user_id = ? where id = ? ";
		List<Object[]> objs = new ArrayList<Object[]>();
		int j = 0;
		for(int i=0;i<orderIds.length;i++){
			try {
				j = Integer.parseInt(orderIds[i]);
			} catch (NumberFormatException e) {
				j = 0;
			}
			objs.add(new Object[]{j,userId,bulletinIds[i]});
		}
		batchUpdate(sql, objs, new int[] {Types.INTEGER, Types.CHAR, Types.CHAR});
	}
	public Integer submit(String id) {
		String sql = "update office_bulletin_xj set state = 2 where id = ? ";
		return update(sql, id);
	}

	@Override
	public Integer update(OfficeBulletinXj officeBulletinXj){
		String sql = "update office_bulletin_xj set title = ?, content = ?, create_time = ?, end_time = ?, publish_time = ?, create_user_id = ?, audit_user_id = ?, audit_time = ?, idea = ?, is_top = ?, place_top_time = ?, state = ?, type = ?, is_all = ?, unit_id = ?, order_id = ?, end_type = ?, area_id = ?, modify_user_id = ?, is_deleted = ?, scope = ?, release_scope = ?, issue = ?, temp_comment_id = ?, message_number = ? where id = ?";
		Object[] args = new Object[]{
			officeBulletinXj.getTitle(), officeBulletinXj.getContent(), 
			officeBulletinXj.getCreateTime(), officeBulletinXj.getEndTime(), 
			officeBulletinXj.getPublishTime(), officeBulletinXj.getCreateUserId(), 
			officeBulletinXj.getAuditUserId(), officeBulletinXj.getAuditTime(), 
			officeBulletinXj.getIdea(), officeBulletinXj.getIsTop(), 
			officeBulletinXj.getPlaceTopTime(), officeBulletinXj.getState(), 
			officeBulletinXj.getType(), officeBulletinXj.getIsAll(), 
			officeBulletinXj.getUnitId(), officeBulletinXj.getOrderId(), 
			officeBulletinXj.getEndType(), officeBulletinXj.getAreaId(), 
			officeBulletinXj.getModifyUserId(), officeBulletinXj.getIsDeleted(), 
			officeBulletinXj.getScope(), officeBulletinXj.getReleaseScope(), 
			officeBulletinXj.getIssue(), officeBulletinXj.getTempCommentId(), 
			officeBulletinXj.getMessageNumber(), officeBulletinXj.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBulletinXj getOfficeBulletinXjById(String id){
		String sql = "select * from office_bulletin_xj where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBulletinXj> getOfficeBulletinXjMapByIds(String[] ids){
		String sql = "select * from office_bulletin_xj where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBulletinXj> getOfficeBulletinXjList(){
		String sql = "select * from office_bulletin_xj where is_deleted = 0 ";
		return query(sql, new MultiRow());
	}
	
	@Override
	public List<OfficeBulletinXj> getOfficeBulletinXjPage(String unitId, String userId,String bulletinType,String state,String startTime,String endTime, String searName, String[] userIds, String searchAreaId, Pagination page){
		String sql = "select * from office_bulletin_xj where is_deleted = 0 and unit_id = ? and type = ? ";
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		args.add(bulletinType);
		StringBuffer str = new StringBuffer(sql);
		if(StringUtils.isNotBlank(userId)){
			str.append(" and create_user_id = ? ");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(startTime)){
			str.append(" and to_date(to_char(create_time, 'yyyy-MM-dd'), 'yyyy-MM-dd') >= ?");
			try {
				args.add(new SimpleDateFormat("yyyy-MM-dd").parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endTime)){
			str.append(" and to_date(to_char(create_time, 'yyyy-MM-dd'), 'yyyy-MM-dd') <= ?");
			try {
				args.add(new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotBlank(searName)){
			str.append(" and title like ? ");
			args.add("%"+searName+"%");
		}
		
		//如果具体校区，那么把全校和该校区的都查出来
		if(StringUtils.isNotBlank(searchAreaId)){
			str.append(" and (area_id = ? or area_id = ?) ");
			args.add(BaseConstant.ZERO_GUID);
			args.add(searchAreaId);
		}
		if(StringUtils.isNotBlank(state) && !OfficeBulletinXj.STATE_ALL.equals(state) && !OfficeBulletinXj.STATE_AUDITALL.equals(state)){
			str.append(" and state = ? ");
			args.add(state);
		}else if(StringUtils.isNotBlank(state) && OfficeBulletinXj.STATE_AUDITALL.equals(state)){
			str.append(" and state <> 1 ");
		}
		if(userIds!=null){
			str.append(" and create_user_id in ");
		}else{
			str.append(" order by order_id desc, create_time desc ");
		}
		if(page == null){
			if(userIds!=null){
				return queryForInSQL(str.toString(), args.toArray(new Object[0]), userIds, new MultiRow()," order by order_id desc, create_time desc ");
			}else{
				return query(str.toString(),args.toArray(new Object[0]), new MultiRow());
			}
		}else{
			if(userIds!=null){
				return queryForInSQL(str.toString(), args.toArray(), userIds, new MultiRow()," order by order_id desc, create_time desc ", page);
			}else{
				return query(str.toString(),args.toArray(new Object[0]), new MultiRow(), page);
			}
		}
	}
	
	
	
	@Override
	public List<OfficeBulletinXj> getOfficeBulletinXjListPage1(String type,
			String unitId, String areaId, String state, String startTime,
			String endTime, String searName, String[] userIds, String topId,
			String parentId, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_bulletin_xj where is_deleted = 0 and type = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(type);
		if(StringUtils.isNotBlank(parentId) && !StringUtils.equals(parentId, BaseConstant.ZERO_GUID)){
			if(StringUtils.isNotBlank(topId) && !StringUtils.equals(topId, BaseConstant.ZERO_GUID)){
				sql.append(" and (unit_id = ? or (unit_id = ? and release_scope = '3') or (unit_id = ? and release_scope = '1')) ");
				objs.add(unitId);
				objs.add(parentId);
				objs.add(topId);
			}else{
				sql.append(" and (unit_id = ? or (unit_id = ? and release_scope = '3')) ");
				objs.add(unitId);
				objs.add(parentId);
			}
		}else{
			sql.append(" and unit_id = ?");
			objs.add(unitId);
		}
		
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and  to_date(to_char(create_time, 'yyyy-MM-dd'), 'yyyy-MM-dd') >= ?");
			try {
				objs.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and to_date(to_char(create_time, 'yyyy-MM-dd'), 'yyyy-MM-dd') <= ?");
			try {
				objs.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(searName)){
			sql.append(" and title like ?");
			objs.add("%"+searName+"%");
		}
		
		//如果具体校区，那么把全校和该校区的都查出来
		if(StringUtils.isNotBlank(areaId)){
			sql.append(" and (area_id = ? or area_id = ?) ");
			objs.add(BaseConstant.ZERO_GUID);
			objs.add(areaId);
		}
		
		if(StringUtils.isNotBlank(state) && !OfficeBulletinXj.STATE_ALL.equals(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}
		sql.append(" and (end_time-to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-MM-dd') >= 0 or end_time is null) ");
		if(userIds!=null){
			sql.append(" and create_user_id in ");
		}else{
			sql.append(" order by order_id desc, create_time desc ");
		}
		if(page == null){
			if(userIds!=null){
				return queryForInSQL(sql.toString(), objs.toArray(new Object[0]), userIds, new MultiRow()," order by order_id desc, create_time desc");
			}else{
				return query(sql.toString(),objs.toArray(new Object[0]), new MultiRow());
			}
		}else{
			if(userIds!=null){
				return queryForInSQL(sql.toString(), objs.toArray(), userIds, new MultiRow()," order by order_id desc, create_time desc", page);
			}else{
				return query(sql.toString(),objs.toArray(new Object[0]), new MultiRow(), page);
			}
		}
	}
	public List<OfficeBulletinXj> getOfficeBulletinXjListPage(String type,String unitId, 
			String areaId, String state, String startTime, String endTime, String searName, 
			String[] userIds, Pagination page){
		StringBuffer sql = new StringBuffer("select * from office_bulletin_xj where is_deleted = 0 and unit_id = ? and type = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		objs.add(type);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and  to_date(to_char(create_time, 'yyyy-MM-dd'), 'yyyy-MM-dd') >= ?");
			try {
				objs.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and to_date(to_char(create_time, 'yyyy-MM-dd'), 'yyyy-MM-dd') <= ?");
			try {
				objs.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(searName)){
			sql.append(" and title like ?");
			objs.add("%"+searName+"%");
		}
		
		//如果具体校区，那么把全校和该校区的都查出来
		if(StringUtils.isNotBlank(areaId)){
			sql.append(" and (area_id = ? or area_id = ?) ");
			objs.add(BaseConstant.ZERO_GUID);
			objs.add(areaId);
		}
		
		if(StringUtils.isNotBlank(state) && !OfficeBulletinXj.STATE_ALL.equals(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}
		sql.append(" and (end_time-to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-MM-dd') >= 0 or end_time is null) ");
		if(userIds!=null){
			sql.append(" and create_user_id in ");
		}else{
			sql.append(" order by order_id desc, create_time desc ");
		}
		if(page == null){
			if(userIds!=null){
				return queryForInSQL(sql.toString(), objs.toArray(new Object[0]), userIds, new MultiRow()," order by order_id desc, create_time desc");
			}else{
				return query(sql.toString(),objs.toArray(new Object[0]), new MultiRow());
			}
		}else{
			if(userIds!=null){
				return queryForInSQL(sql.toString(), objs.toArray(), userIds, new MultiRow()," order by order_id desc, create_time desc", page);
			}else{
				return query(sql.toString(),objs.toArray(new Object[0]), new MultiRow(), page);
			}
		}
		
	}

	@Override
	public List<OfficeBulletinXj> getOfficeBulletinXjListByIds(String[] ids) {
		String sql = "select * from office_bulletin_xj where id in ";
//		StringBuilder sb = new StringBuilder(sql);
//		int i = 0; 
//		for(String id :ids){
//			sb.append(i == 0?" ( ": StringUtils.EMPTY);
//			sb.append("'"+id+"'");
//			i++;
//			sb.append(i == ids.length?StringUtils.EMPTY:",)");
//		}
//		return query(sb.toString(), new MultiRow());
		return queryForInSQL(sql, null, ids, new MultiRow());
		//return queryForInSQL(sql, null, ids, new MultiRow(),null );
	}
}