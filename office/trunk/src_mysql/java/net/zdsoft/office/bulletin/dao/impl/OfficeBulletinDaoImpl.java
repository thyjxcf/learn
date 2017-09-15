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

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;

import org.apache.commons.lang.StringUtils;

import com.alibaba.druid.sql.SQLUtils;
/**
 * office_bulletin
 * @author 
 * 
 */
public class OfficeBulletinDaoImpl extends BaseDao<OfficeBulletin> implements OfficeBulletinDao{

	@Override
	public OfficeBulletin setField(ResultSet rs) throws SQLException{
		OfficeBulletin officeBulletin = new OfficeBulletin();
		officeBulletin.setId(rs.getString("id"));
		officeBulletin.setUnitId(rs.getString("unit_id"));
		officeBulletin.setTitle(rs.getString("title"));
		Clob clob = rs.getClob("content");
		if(clob != null){
			officeBulletin.setContent(clob.getSubString(1, (int)clob.length()));
		}
		officeBulletin.setCreateTime(rs.getTimestamp("create_time"));
		officeBulletin.setEndTime(rs.getTimestamp("end_time"));
		officeBulletin.setPublishTime(rs.getTimestamp("publish_time"));
		officeBulletin.setCreateUserId(rs.getString("create_user_id"));
		officeBulletin.setAuditUserId(rs.getString("audit_user_id"));
		officeBulletin.setAuditTime(rs.getTimestamp("audit_time"));
		officeBulletin.setIdea(rs.getString("idea"));
		officeBulletin.setIsTop(rs.getBoolean("is_top"));
		officeBulletin.setPlaceTopTime(rs.getTimestamp("place_top_time"));
		officeBulletin.setState(rs.getString("state"));
		officeBulletin.setType(rs.getString("type"));
		officeBulletin.setIsAll(rs.getBoolean("is_all"));
		officeBulletin.setOrderId(rs.getObject("order_id")==null?null:rs.getInt("order_id"));
		officeBulletin.setScope(rs.getString("scope"));
		officeBulletin.setEndType(rs.getString("end_type"));
		officeBulletin.setAreaId(rs.getString("area_id"));
		officeBulletin.setModifyUserId(rs.getString("modify_user_id"));
		officeBulletin.setIsDeleted(rs.getInt("is_deleted"));
		officeBulletin.setReleaseScope(rs.getString("release_scope"));
		return officeBulletin;
	}

	@Override
	public OfficeBulletin save(OfficeBulletin officeBulletin){
		String sql = "insert into office_bulletin(id, unit_id, title, content, create_time, end_time, publish_time, create_user_id, audit_user_id, audit_time, idea, is_top, place_top_time, state, type, is_all,order_id,scope,end_type,area_id,modify_user_id,is_deleted,release_scope) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeBulletin.getId())){
			officeBulletin.setId(createId());
		}
		Object[] args = new Object[]{
			officeBulletin.getId(),officeBulletin.getUnitId(), officeBulletin.getTitle(), 
			officeBulletin.getContent(), officeBulletin.getCreateTime(), 
			officeBulletin.getEndTime(), officeBulletin.getPublishTime(), 
			officeBulletin.getCreateUserId(), officeBulletin.getAuditUserId(), 
			officeBulletin.getAuditTime(), officeBulletin.getIdea(), 
			officeBulletin.getIsTop(), officeBulletin.getPlaceTopTime(), 
			officeBulletin.getState(), officeBulletin.getType(), 
			officeBulletin.getIsAll(),officeBulletin.getOrderId(),
			officeBulletin.getScope(),officeBulletin.getEndType(),
			officeBulletin.getAreaId(),officeBulletin.getModifyUserId(),
			officeBulletin.getIsDeleted(),officeBulletin.getReleaseScope()
		};
		update(sql, args);
		return officeBulletin;
	}

	@Override
	public Integer delete(String id, String userId){
		String sql = "update office_bulletin set is_deleted = 1, modify_user_id = ? where id = ? ";
		return update(sql, new Object[]{userId,id});
	}
	
	@Override
	public Integer deleteIds(String[] ids,String userId){
		String sql = "update office_bulletin set is_deleted = 1, modify_user_id = ? where id in ";
		return updateForInSQL(sql, new Object[]{userId}, ids);
	}
	
	@Override
	public Integer publish(String[] ids,String userId) {
		String sql = "update office_bulletin set state = 3, publish_time = sysdate,  audit_user_id = ?, audit_time = sysdate where id in";
		return updateForInSQL(sql, new Object[]{userId}, ids);
	}
	@Override
	public Integer qxPublish(String id,String userId) {
		String sql = "update office_bulletin set state = 1, publish_time = '',  audit_user_id = ?, audit_time = sysdate where id = ? ";
		return update(sql, new Object[]{userId,id});
	}
	
	@Override
	public Integer unPublish(String id,String userId,String idea) {
		String sql = "update office_bulletin set state = 4, publish_time = sysdate, audit_user_id = ?, audit_time = sysdate, idea = ? where id = ? ";
		return update(sql, new Object[]{userId,idea,id});
	}
	@Override
	public void top(String bulletinId) {
		String sql = "update office_bulletin set place_top_time = sysdate, is_top = 1 where id = ? ";
		update(sql, bulletinId);
	}
	@Override
	public void qxTop(String bulletinId) {
		String sql = "update office_bulletin set place_top_time = '', is_top = 0 where id = ? ";
		update(sql, bulletinId);
	}
	@Override
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId) {
		String sql = "update office_bulletin set order_id = ?, modify_user_id = ? where id = ? ";
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
		String sql = "update office_bulletin set state = 2 where id = ? ";
		return update(sql, id);
	}

	@Override
	public Integer update(OfficeBulletin officeBulletin){
		String sql = "update office_bulletin set unit_id=?,  title = ?, content = ?, create_time = ?, end_time = ?, create_user_id = ?, audit_user_id = ?, audit_time = ?, idea = ?, is_top = ?, place_top_time = ?, state = ?, type = ?, is_all = ?, order_id=?, scope=?, publish_time=?, end_type=?, area_id=?, modify_user_id=?, is_deleted=?,release_scope=? where id = ?";
		Object[] args = new Object[]{
			officeBulletin.getUnitId(),
			officeBulletin.getTitle(), officeBulletin.getContent(), officeBulletin.getCreateTime(),
			officeBulletin.getEndTime(), officeBulletin.getCreateUserId(), 
			officeBulletin.getAuditUserId(), officeBulletin.getAuditTime(), 
			officeBulletin.getIdea(), officeBulletin.getIsTop(), 
			officeBulletin.getPlaceTopTime(), officeBulletin.getState(), 
			officeBulletin.getType(), officeBulletin.getIsAll(),
			officeBulletin.getOrderId(), officeBulletin.getScope(),
			officeBulletin.getPublishTime(), officeBulletin.getEndType(),
			officeBulletin.getAreaId(), officeBulletin.getModifyUserId(),
			officeBulletin.getIsDeleted(), officeBulletin.getReleaseScope(),
			officeBulletin.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBulletin getOfficeBulletinById(String id){
		String sql = "select * from office_bulletin where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBulletin> getOfficeBulletinMapByIds(String[] ids){
		String sql = "select * from office_bulletin where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBulletin> getOfficeBulletinList(){
		String sql = "select * from office_bulletin where is_deleted = 0 ";
		return query(sql, new MultiRow());
	}
	
	@Override
	public List<OfficeBulletin> getOfficeBulletinPage(String unitId, String userId,String bulletinType,String state,String startTime,String endTime, String searName, String[] userIds, String searchAreaId, Pagination page){
		String sql = "select * from office_bulletin where is_deleted = 0 and unit_id = ? and type = ? ";
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		args.add(bulletinType);
		StringBuffer str = new StringBuffer(sql);
		if(StringUtils.isNotBlank(userId)){
			str.append(" and create_user_id = ? ");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(startTime)){
			str.append(" and  str_to_date(date_format(create_time, '%Y-%m-%d'), '%Y-%m-%d') >= ?");
			try {
				args.add(new SimpleDateFormat("yyyy-MM-dd").parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endTime)){
			str.append(" and str_to_date(date_format(create_time, '%Y-%m-%d'), '%Y-%m-%d') <= ?");
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
		if(StringUtils.isNotBlank(state) && !OfficeBulletin.STATE_ALL.equals(state) && !OfficeBulletin.STATE_AUDITALL.equals(state)){
			str.append(" and state = ? ");
			args.add(state);
		}else if(StringUtils.isNotBlank(state) && OfficeBulletin.STATE_AUDITALL.equals(state)){
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
	public List<OfficeBulletin> getOfficeBulletinListPage1(String[] types,
			String unitId, String areaId, String state, String startTime,
			String endTime, String searName, String[] userIds, String topId,
			String parentId, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_bulletin where is_deleted = 0");
		List<Object> objs = new ArrayList<Object>();
		sql.append(" and type in ");
		sql.append(net.zdsoft.leadin.util.SQLUtils.toSQLInString(types));
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
			sql.append(" and  str_to_date(date_format(create_time, '%Y-%m-%d'), '%Y-%m-%d') >= ?");
			try {
				objs.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(date_format(create_time, '%Y-%m-%d'), '%Y-%m-%d') <= ?");
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
		
		if(StringUtils.isNotBlank(state) && !OfficeBulletin.STATE_ALL.equals(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}
		sql.append(" and (end_time-str_to_date(date_format(sysdate, '%Y-%m-%d'), '%Y-%m-%d') >= 0 or end_time is null) ");
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
	public List<OfficeBulletin> getOfficeBulletinListPage(String[] types,String unitId, 
			String areaId, String state, String startTime, String endTime, String searName, 
			String[] userIds, Pagination page){
		StringBuffer sql = new StringBuffer("select * from office_bulletin where is_deleted = 0 and unit_id = ?");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		sql.append(" and type in ");
		sql.append(net.zdsoft.leadin.util.SQLUtils.toSQLInString(types));
		
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and  str_to_date(date_format(create_time, '%Y-%m-%d'), '%Y-%m-%d') >= ?");
			try {
				objs.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(date_format(create_time, '%Y-%m-%d'), '%Y-%m-%d') <= ?");
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
		
		if(StringUtils.isNotBlank(state) && !OfficeBulletin.STATE_ALL.equals(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}
		sql.append(" and (end_time-str_to_date(date_format(sysdate, '%Y-%m-%d'), '%Y-%m-%d') >= 0 or end_time is null) ");
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
	public List<OfficeBulletin> getOfficeBulletinListPage(String type,
			String unitId, String parentId, String state, int tabClass,
			Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_bulletin where is_deleted = 0 and type = ? and state = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(type);
		objs.add(state);
		if(Unit.UNIT_CLASS_EDU == tabClass){
			sql.append(" and ((unit_id = ? and release_scope = '3') or release_scope = '1') ");
			objs.add(parentId);
		}else{
			sql.append(" and unit_id = ? ");
			objs.add(unitId);
		}
		sql.append(" order by order_id desc, create_time desc ");
		return query(sql.toString(), objs.toArray(new Object[0]), new MultiRow(), page);
	}
}