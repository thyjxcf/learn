package net.zdsoft.office.goodmanage.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsReqDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
/**
 * office_goods_req
 * @author 
 * 
 */
public class OfficeGoodsReqDaoImpl extends BaseDao<OfficeGoodsReq> implements OfficeGoodsReqDao{

	@Override
	public OfficeGoodsReq setField(ResultSet rs) throws SQLException{
		OfficeGoodsReq officeGoodsReq = new OfficeGoodsReq();
		officeGoodsReq.setId(rs.getString("id"));
		officeGoodsReq.setGoodsId(rs.getString("goods_id"));
		officeGoodsReq.setUnitId(rs.getString("unit_id"));
		officeGoodsReq.setReqDeptId(rs.getString("req_dept_id"));
		officeGoodsReq.setReqUserId(rs.getString("req_user_id"));
		officeGoodsReq.setRemark(rs.getString("remark"));
		officeGoodsReq.setAdvice(rs.getString("advice"));
		officeGoodsReq.setState(rs.getInt("state"));
		officeGoodsReq.setAmount(rs.getInt("amount"));
		officeGoodsReq.setCreationTime(rs.getTimestamp("creation_time"));
		officeGoodsReq.setPassTime(rs.getTimestamp("pass_time"));
		officeGoodsReq.setGetUserid(rs.getString("get_userid"));
		return officeGoodsReq;
	}

	@Override
	public OfficeGoodsReq save(OfficeGoodsReq officeGoodsReq){
		String sql = "insert into office_goods_req(id, goods_id, unit_id, req_dept_id, req_user_id, remark, advice, state, amount, creation_time, pass_time, get_userid) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeGoodsReq.getId())){
			officeGoodsReq.setId(createId());
		}
		Object[] args = new Object[]{
			officeGoodsReq.getId(), officeGoodsReq.getGoodsId(), 
			officeGoodsReq.getUnitId(), officeGoodsReq.getReqDeptId(), 
			officeGoodsReq.getReqUserId(), officeGoodsReq.getRemark(), 
			officeGoodsReq.getAdvice(), officeGoodsReq.getState(), 
			officeGoodsReq.getAmount(), officeGoodsReq.getCreationTime(), 
			officeGoodsReq.getPassTime(), officeGoodsReq.getGetUserid()
		};
		update(sql, args);
		return officeGoodsReq;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goods_req where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer deleteByGoodsIds(String[] goodsIds){
		String sql = "delete from office_goods_req where goods_id in";
		return updateForInSQL(sql, null, goodsIds);
	}

	@Override
	public Integer update(OfficeGoodsReq officeGoodsReq){
		String sql = "update office_goods_req set goods_id = ?, unit_id = ?, req_dept_id = ?, req_user_id = ?, remark = ?, advice = ?, state = ?, amount = ?, creation_time = ?, pass_time = ?, get_userid = ? where id = ?";
		Object[] args = new Object[]{
			officeGoodsReq.getGoodsId(), officeGoodsReq.getUnitId(), 
			officeGoodsReq.getReqDeptId(), officeGoodsReq.getReqUserId(), 
			officeGoodsReq.getRemark(), officeGoodsReq.getAdvice(), 
			officeGoodsReq.getState(), officeGoodsReq.getAmount(), 
			officeGoodsReq.getCreationTime(), officeGoodsReq.getPassTime(), 
			officeGoodsReq.getGetUserid(), officeGoodsReq.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeGoodsReq getOfficeGoodsReqById(String id){
		String sql = "select * from office_goods_req where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeGoodsReq> getOfficeGoodsReqMapByIds(String[] ids){
		String sql = "select * from office_goods_req where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqList(){
		String sql = "select * from office_goods_req";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqPage(Pagination page){
		String sql = "select * from office_goods_req";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdList(String unitId){
		String sql = "select * from office_goods_req where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_goods_req where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqByConditions(String unitId, String[] goodsIds,
			String applyType, String applyUserId, Date beginTime, Date endTime, Pagination page){
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_goods_req where unit_id = ? ");
		argsList.add(unitId);
		if(StringUtils.isNotBlank(applyType)){
			if(StringUtils.equals(applyType, OfficeGoodsConstants.GOODS_AUDIT_ALL+"")){
				sql.append(" and state in ").append(
						SQLUtils.toSQLInString(new String[]{"0","1","2"}));
			}else{
				sql.append(" and state = ?");
				argsList.add(applyType);
			}
		}
		if(StringUtils.isNotBlank(applyUserId)){
			sql.append(" and req_user_id = ?");
			argsList.add(applyUserId);
		}
		if(beginTime!=null){
			sql.append(" and str_to_date(date_format(creation_time,'%Y-%m-%d'),'%Y-%m-%d') >= ? ");
			argsList.add(beginTime);
		}
		if(endTime!=null){
			sql.append(" and str_to_date(date_format(creation_time,'%Y-%m-%d'),'%Y-%m-%d') <= ? ");
			argsList.add(endTime);
		}
		if(goodsIds != null){
			sql.append(" and goods_id in");
			if(page != null)
				return queryForInSQL(sql.toString(), argsList.toArray(), goodsIds, new MultiRow(), " order by state asc, creation_time desc", page);
			else
				return queryForInSQL(sql.toString(), argsList.toArray(), goodsIds, new MultiRow(), " order by state asc, creation_time desc");
		}
		else{
			sql.append(" order by state asc, creation_time desc");
			if(page != null)
				return query(sql.toString(), argsList.toArray(), new MultiRow(), page);
			else
				return query(sql.toString(), argsList.toArray(), new MultiRow());
		}
	}
}
	