package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.msgcenter.entity.OfficeSmsDetail;
import net.zdsoft.office.msgcenter.dao.OfficeSmsDetailDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_sms_detail
 * @author 
 * 
 */
public class OfficeSmsDetailDaoImpl extends BaseDao<OfficeSmsDetail> implements OfficeSmsDetailDao{

	@Override
	public OfficeSmsDetail setField(ResultSet rs) throws SQLException{
		OfficeSmsDetail officeSmsDetail = new OfficeSmsDetail();
		officeSmsDetail.setId(rs.getString("id"));
		officeSmsDetail.setInfoId(rs.getString("info_id"));
		officeSmsDetail.setReceiverId(rs.getString("receiver_id"));
		officeSmsDetail.setPhone(rs.getString("phone"));
		officeSmsDetail.setSendState(rs.getInt("send_state"));
		return officeSmsDetail;
	}

	@Override
	public OfficeSmsDetail save(OfficeSmsDetail officeSmsDetail){
		String sql = "insert into office_sms_detail(id, info_id, receiver_id, phone, send_state) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeSmsDetail.getId())){
			officeSmsDetail.setId(createId());
		}
		Object[] args = new Object[]{
			officeSmsDetail.getId(), officeSmsDetail.getInfoId(), 
			officeSmsDetail.getReceiverId(), officeSmsDetail.getPhone(), 
			officeSmsDetail.getSendState()
		};
		update(sql, args);
		return officeSmsDetail;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_sms_detail where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSmsDetail officeSmsDetail){
		String sql = "update office_sms_detail set info_id = ?, receiver_id = ?, phone = ?, send_state = ? where id = ?";
		Object[] args = new Object[]{
			officeSmsDetail.getInfoId(), officeSmsDetail.getReceiverId(), 
			officeSmsDetail.getPhone(), officeSmsDetail.getSendState(), 
			officeSmsDetail.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSmsDetail getOfficeSmsDetailById(String id){
		String sql = "select * from office_sms_detail where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSmsDetail> getOfficeSmsDetailMapByIds(String[] ids){
		String sql = "select * from office_sms_detail where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSmsDetail> getOfficeSmsDetailList(){
		String sql = "select * from office_sms_detail";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSmsDetail> getOfficeSmsDetailPage(Pagination page){
		String sql = "select * from office_sms_detail";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public void batchSave(List<OfficeSmsDetail> officeSmsDetailList){
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < officeSmsDetailList.size(); i++) {
			OfficeSmsDetail officeSmsDetail = officeSmsDetailList.get(i);
			if (StringUtils.isBlank(officeSmsDetail.getId()))
				officeSmsDetail.setId(getGUID());
			Object[] args = new Object[]{
					officeSmsDetail.getId(), officeSmsDetail.getInfoId(), 
					officeSmsDetail.getReceiverId(), officeSmsDetail.getPhone(), 
					officeSmsDetail.getSendState()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
				Types.VARCHAR, Types.INTEGER };
		String sql = "insert into office_sms_detail(id, info_id, receiver_id, phone, send_state) values(?,?,?,?,?)";
		batchUpdate(sql, listOfArgs, argTypes);
	}
	
	@Override
	public void deleteByInfoIds(String[] infoIds){
		String sql = "delete from office_sms_detail where info_id in";
		updateForInSQL(sql, null, infoIds);
	}
	
	@Override
	public List<OfficeSmsDetail> getOfficeSmsDetailsByInfoId(String infoId){
		String sql = "select * from office_sms_detail where info_id = ?";
		return query(sql, new Object[]{infoId}, new MultiRow());
	}
}
