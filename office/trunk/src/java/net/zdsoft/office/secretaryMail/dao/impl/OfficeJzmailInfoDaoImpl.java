package net.zdsoft.office.secretaryMail.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.secretaryMail.entity.OfficeJzmailInfo;
import net.zdsoft.office.secretaryMail.dao.OfficeJzmailInfoDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_jzmail_info
 * @author 
 * 
 */
public class OfficeJzmailInfoDaoImpl extends BaseDao<OfficeJzmailInfo> implements OfficeJzmailInfoDao{

	@Override
	public OfficeJzmailInfo setField(ResultSet rs) throws SQLException{
		OfficeJzmailInfo officeJzmailInfo = new OfficeJzmailInfo();
		officeJzmailInfo.setId(rs.getString("id"));
		officeJzmailInfo.setUnitId(rs.getString("unit_id"));
		officeJzmailInfo.setCreateUserId(rs.getString("create_user_id"));
		Clob clob = rs.getClob("content");
		if(clob != null){
			officeJzmailInfo.setContent(clob.getSubString(1, (int)clob.length()));
		}
		return officeJzmailInfo;
	}

	@Override
	public OfficeJzmailInfo save(OfficeJzmailInfo officeJzmailInfo){
		String sql = "insert into office_jzmail_info(id, unit_id, create_user_id, content) values(?,?,?,?)";
		if (StringUtils.isBlank(officeJzmailInfo.getId())){
			officeJzmailInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeJzmailInfo.getId(), officeJzmailInfo.getUnitId(), 
			officeJzmailInfo.getCreateUserId(), officeJzmailInfo.getContent()
		};
		update(sql, args);
		return officeJzmailInfo;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_jzmail_info where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public void deleteAll() {
		String sql = "delete from office_jzmail_info";
		update(sql);
	}

	@Override
	public Integer update(OfficeJzmailInfo officeJzmailInfo){
		String sql = "update office_jzmail_info set unit_id = ?, create_user_id = ?, content = ? where id = ?";
		Object[] args = new Object[]{
			officeJzmailInfo.getUnitId(), officeJzmailInfo.getCreateUserId(), 
			officeJzmailInfo.getContent(), officeJzmailInfo.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeJzmailInfo getOfficeJzmailInfoById(String id){
		String sql = "select * from office_jzmail_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeJzmailInfo> getOfficeJzmailInfoMapByIds(String[] ids){
		String sql = "select * from office_jzmail_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoList(){
		String sql = "select * from office_jzmail_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoPage(Pagination page){
		String sql = "select * from office_jzmail_info";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdList(String unitId){
		String sql = "select * from office_jzmail_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_jzmail_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}
