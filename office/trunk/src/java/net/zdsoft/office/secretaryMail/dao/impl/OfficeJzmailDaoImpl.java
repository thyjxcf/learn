package net.zdsoft.office.secretaryMail.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.secretaryMail.entity.OfficeJzmail;
import net.zdsoft.office.secretaryMail.dao.OfficeJzmailDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_jzmail
 * @author 
 * 
 */
public class OfficeJzmailDaoImpl extends BaseDao<OfficeJzmail> implements OfficeJzmailDao{

	@Override
	public OfficeJzmail setField(ResultSet rs) throws SQLException{
		OfficeJzmail officeJzmail = new OfficeJzmail();
		officeJzmail.setId(rs.getString("id"));
		officeJzmail.setUnitId(rs.getString("unit_id"));
		officeJzmail.setCreateUserId(rs.getString("create_user_id"));
		officeJzmail.setTitle(rs.getString("title"));
		officeJzmail.setPhone(rs.getString("phone"));
		officeJzmail.setMail(rs.getString("mail"));
		officeJzmail.setContent(rs.getString("content"));
		officeJzmail.setIsDeleted(rs.getBoolean("is_deleted"));
		officeJzmail.setCreateTime(rs.getTimestamp("create_time"));
		officeJzmail.setState(rs.getInt("state"));
		officeJzmail.setDealUserId(rs.getString("deal_user_id"));
		officeJzmail.setDealTime(rs.getTimestamp("deal_time"));
		officeJzmail.setAnonymous(rs.getBoolean("anonymous"));
		return officeJzmail;
	}

	@Override
	public OfficeJzmail save(OfficeJzmail officeJzmail){
		String sql = "insert into office_jzmail(id, unit_id, create_user_id, title, phone, mail, content, is_deleted, create_time, state, deal_user_id, deal_time,anonymous) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeJzmail.getId())){
			officeJzmail.setId(createId());
		}
		Object[] args = new Object[]{
			officeJzmail.getId(), officeJzmail.getUnitId(), 
			officeJzmail.getCreateUserId(), officeJzmail.getTitle(), 
			officeJzmail.getPhone(), officeJzmail.getMail(), 
			officeJzmail.getContent(), officeJzmail.getIsDeleted(), 
			officeJzmail.getCreateTime(), officeJzmail.getState(), 
			officeJzmail.getDealUserId(), officeJzmail.getDealTime(),
			officeJzmail.isAnonymous()
		};
		update(sql, args);
		return officeJzmail;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_jzmail where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeJzmail officeJzmail){
		String sql = "update office_jzmail set unit_id = ?, create_user_id = ?, title = ?, phone = ?, mail = ?, content = ?, is_deleted = ?, create_time = ?, state = ?, deal_user_id = ?, deal_time = ?,anonymous=? where id = ?";
		Object[] args = new Object[]{
			officeJzmail.getUnitId(), officeJzmail.getCreateUserId(), 
			officeJzmail.getTitle(), officeJzmail.getPhone(), 
			officeJzmail.getMail(), officeJzmail.getContent(), 
			officeJzmail.getIsDeleted()==false?"0":"1", officeJzmail.getCreateTime(), 
			officeJzmail.getState(), officeJzmail.getDealUserId(), 
			officeJzmail.getDealTime(),officeJzmail.isAnonymous()==false?"0":"1", officeJzmail.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeJzmail getOfficeJzmailById(String id){
		String sql = "select * from office_jzmail where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeJzmail> getOfficeJzmailMapByIds(String[] ids){
		String sql = "select * from office_jzmail where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailList(){
		String sql = "select * from office_jzmail";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailPage(Pagination page){
		String sql = "select * from office_jzmail";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdList(String unitId){
		String sql = "select * from office_jzmail where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_jzmail where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdAndTitle(String unitId,
			String userId, String startTime, String endTime, String title,
			Pagination page) {
		StringBuffer sb=new StringBuffer("select * from office_jzmail where unit_id = ? and create_user_id=? and is_deleted=0");
		List<Object> objs=new ArrayList<Object>();
		objs.add(unitId);
		objs.add(userId);
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and to_date(?,'yyyy-MM-dd')<=to_date(to_char(create_time,'yyyy-MM-dd'),'yyyy-MM-dd')");
			objs.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and to_date(?,'yyyy-MM-dd')>=to_date(to_char(create_time,'yyyy-MM-dd'),'yyyy-MM-dd')");
			objs.add(endTime);
		}
		if(StringUtils.isNotBlank(title)){
			sb.append(" and title like '%"+title+"%'");
		}
		sb.append(" order by create_time");
		if(page!=null){
			return query(sb.toString(), objs.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), objs.toArray(), new MultiRow());
		}
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdAndTitleAndOther(
			String startTime, String endTime, String title,
			String createUserName, String unitName, String state,
			Pagination page,boolean anonymous) {
		StringBuffer sb=new StringBuffer("select * from office_jzmail where is_deleted=0");
		List<Object> objs=new ArrayList<Object>();
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and to_date(?,'yyyy-MM-dd')<=to_date(to_char(create_time,'yyyy-MM-dd'),'yyyy-MM-dd')");
			objs.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and to_date(?,'yyyy-MM-dd')>=to_date(to_char(create_time,'yyyy-MM-dd'),'yyyy-MM-dd')");
			objs.add(endTime);
		}
		if(StringUtils.isNotBlank(title)){
			sb.append(" and title like '%"+title+"%'");
		}
		if(anonymous){
			sb.append(" and anonymous=1");
		}
		if(StringUtils.isNotBlank(createUserName)){
			sb.append(" and exists(select 1 from base_user where base_user.id=office_jzmail.create_user_id and base_user.real_name like '%"+createUserName+"%')");
			sb.append(" and anonymous<>1");
		}
		if(StringUtils.isNotBlank(unitName)){
			sb.append(" and exists(select 1 from base_unit where base_unit.id=office_jzmail.unit_id and base_unit.unit_name like '%"+unitName+"%' )");
		}
		if(StringUtils.isNotBlank(state)){
			sb.append(" and state=?");
			objs.add(state);
		}
		sb.append(" order by create_time");
		if(page!=null){
			return query(sb.toString(), objs.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), objs.toArray(), new MultiRow());
		}
	}
}
