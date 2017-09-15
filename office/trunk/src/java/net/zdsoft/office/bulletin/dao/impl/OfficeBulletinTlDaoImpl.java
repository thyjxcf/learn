package net.zdsoft.office.bulletin.dao.impl;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinTlDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTl;

import org.apache.commons.lang.StringUtils;
/**
 * office_bulletin_tl
 * @author 
 * 
 */
public class OfficeBulletinTlDaoImpl extends BaseDao<OfficeBulletinTl> implements OfficeBulletinTlDao{

	@Override
	public OfficeBulletinTl setField(ResultSet rs) throws SQLException{
		OfficeBulletinTl officeBulletinTl = new OfficeBulletinTl();
		officeBulletinTl.setId(rs.getString("id"));
		officeBulletinTl.setTitle(rs.getString("title"));
		Clob clob = rs.getClob("content");
		if(clob != null){
			officeBulletinTl.setContent(clob.getSubString(1, (int)clob.length()));
		}
		officeBulletinTl.setCreateTime(rs.getTimestamp("create_time"));
		officeBulletinTl.setEndTime(rs.getTimestamp("end_time"));
		officeBulletinTl.setPublishTime(rs.getTimestamp("publish_time"));
		officeBulletinTl.setCreateUserId(rs.getString("create_user_id"));
		officeBulletinTl.setIsTop(rs.getBoolean("is_top"));
		officeBulletinTl.setPlaceTopTime(rs.getTimestamp("place_top_time"));
		officeBulletinTl.setState(rs.getString("state"));
		officeBulletinTl.setUnitId(rs.getString("unit_id"));
		officeBulletinTl.setOrderId(rs.getInt("order_id"));
		officeBulletinTl.setEndType(rs.getString("end_type"));
		officeBulletinTl.setModifyUserId(rs.getString("modify_user_id"));
		officeBulletinTl.setIsDeleted(rs.getInt("is_deleted"));
		return officeBulletinTl;
	}

	@Override
	public OfficeBulletinTl save(OfficeBulletinTl officeBulletinTl){
		String sql = "insert into office_bulletin_tl(id, title, content, create_time, end_time, publish_time, create_user_id, is_top, place_top_time, state, unit_id, order_id, end_type, modify_user_id, is_deleted) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeBulletinTl.getId())){
			officeBulletinTl.setId(createId());
		}
		Object[] args = new Object[]{
			officeBulletinTl.getId(), officeBulletinTl.getTitle(), 
			officeBulletinTl.getContent(), officeBulletinTl.getCreateTime(), 
			officeBulletinTl.getEndTime(), officeBulletinTl.getPublishTime(), 
			officeBulletinTl.getCreateUserId(), officeBulletinTl.getIsTop(), 
			officeBulletinTl.getPlaceTopTime(), officeBulletinTl.getState(), 
			officeBulletinTl.getUnitId(), officeBulletinTl.getOrderId(), 
			officeBulletinTl.getEndType(), officeBulletinTl.getModifyUserId(), 
			officeBulletinTl.getIsDeleted()
		};
		update(sql, args);
		return officeBulletinTl;
	}

	@Override
	public Integer delete(String[] ids, String userId){
		String sql = "update  office_bulletin_tl set is_deleted = 1, modify_user_id = ? where id in";
		return updateForInSQL(sql, new Object[]{userId}, ids);
	}
	
	@Override
	public void publish(String[] ids, String userId) {
		String sql = "update office_bulletin_tl set state = 3, publish_time = sysdate, modify_user_id = ? where id in";
		updateForInSQL(sql, new Object[]{userId}, ids);
	}
	
	@Override
	public void qxPublish(String id,String userId) {
		String sql = "update office_bulletin_tl set state = 1, publish_time = '',  modify_user_id = ? where id = ? ";
		update(sql, new Object[]{userId,id});
	}
	
	@Override
	public void top(String id, Integer topState) {
		String sql = "update office_bulletin_tl set place_top_time = sysdate, is_top = ? where id = ? ";
		update(sql, new Object[]{topState,id});
	}

	@Override
	public Integer update(OfficeBulletinTl officeBulletinTl){
		String sql = "update office_bulletin_tl set title = ?, content = ?, create_time = ?, end_time = ?, publish_time = ?, create_user_id = ?, is_top = ?, place_top_time = ?, state = ?, unit_id = ?, order_id = ?, end_type = ?, modify_user_id = ?, is_deleted = ? where id = ?";
		Object[] args = new Object[]{
			officeBulletinTl.getTitle(), officeBulletinTl.getContent(), 
			officeBulletinTl.getCreateTime(), officeBulletinTl.getEndTime(), 
			officeBulletinTl.getPublishTime(), officeBulletinTl.getCreateUserId(), 
			officeBulletinTl.getIsTop(), officeBulletinTl.getPlaceTopTime(), 
			officeBulletinTl.getState(), officeBulletinTl.getUnitId(), 
			officeBulletinTl.getOrderId(), officeBulletinTl.getEndType(), 
			officeBulletinTl.getModifyUserId(), officeBulletinTl.getIsDeleted(), 
			officeBulletinTl.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId) {
		String sql = "update office_bulletin_tl set order_id = ?, modify_user_id = ? where id = ? ";
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

	@Override
	public OfficeBulletinTl getOfficeBulletinTlById(String id){
		String sql = "select * from office_bulletin_tl where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<OfficeBulletinTl> getOfficeBulletinManageListPage(
			String unitId, String state, String startTime, String endTime,
			String searchName, String[] userIds, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_bulletin_tl where is_deleted = 0 and unit_id = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
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
		if(StringUtils.isNotBlank(searchName)){
			sql.append(" and title like ?");
			objs.add("%"+searchName+"%");
		}
		
		if(StringUtils.isNotBlank(state) && !OfficeBulletin.STATE_ALL.equals(state)){
			sql.append(" and state = ? ");
			objs.add(state);
		}
		if(userIds!=null){
			sql.append(" and create_user_id in ");
		}else{
			sql.append(" order by order_id desc, create_time desc ");
		}
		if(userIds!=null){
			return queryForInSQL(sql.toString(), objs.toArray(), userIds, new MultiRow()," order by order_id desc, create_time desc", page);
		}else{
			return query(sql.toString(),objs.toArray(new Object[0]), new MultiRow(), page);
		}
	}
	
	@Override
	public List<OfficeBulletinTl> getOfficeBulletinTlListViewPage(
			String unitId, String startTime, String endTime, String searchName,
			String[] userIds, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_bulletin_tl obt where is_deleted = 0 and state = 3 ");
		List<Object> objs = new ArrayList<Object>();
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
		if(StringUtils.isNotBlank(searchName)){
			sql.append(" and title like ?");
			objs.add("%"+searchName+"%");
		}
		sql.append(" and exists (select obtu.bulletin_id from office_bulletin_tl_unit obtu where obtu.bulletin_id = obt.id and obtu.receive_unit_id = ? )");
		objs.add(unitId);
		if(userIds!=null){
			sql.append(" and create_user_id in ");
		}else{
			sql.append(" order by order_id desc, create_time desc ");
		}
		if(userIds!=null){
			return queryForInSQL(sql.toString(), objs.toArray(), userIds, new MultiRow()," order by order_id desc, create_time desc", page);
		}else{
			return query(sql.toString(),objs.toArray(new Object[0]), new MultiRow(), page);
		}
	}
	
	public List<OfficeBulletinTl> getBulletinTlByIds(String[] ids){
		String sql = "select * from office_bulletin_tl where is_deleted = 0 and id in ";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}
}