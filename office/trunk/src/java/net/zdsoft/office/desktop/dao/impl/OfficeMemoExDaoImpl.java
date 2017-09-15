package net.zdsoft.office.desktop.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.desktop.entity.OfficeMemoEx;
import net.zdsoft.office.desktop.dao.OfficeMemoExDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_memo_ex
 * @author 
 * 
 */
public class OfficeMemoExDaoImpl extends BaseDao<OfficeMemoEx> implements OfficeMemoExDao{

	@Override
	public OfficeMemoEx setField(ResultSet rs) throws SQLException{
		OfficeMemoEx officeMemoEx = new OfficeMemoEx();
		officeMemoEx.setId(rs.getString("id"));
		officeMemoEx.setUseUserId(rs.getString("use_user_id"));
		officeMemoEx.setMemoId(rs.getString("memo_id"));
		officeMemoEx.setSend(rs.getString("is_send"));
		return officeMemoEx;
	}

	@Override
	public OfficeMemoEx save(OfficeMemoEx officeMemoEx){
		String sql = "insert into office_memo_ex(id, use_user_id, memo_id,is_send) values(?,?,?,?)";
		if (StringUtils.isBlank(officeMemoEx.getId())){
			officeMemoEx.setId(createId());
		}
		Object[] args = new Object[]{
			officeMemoEx.getId(), officeMemoEx.getUseUserId(), 
			officeMemoEx.getMemoId(),officeMemoEx.getSend()
		};
		update(sql, args);
		return officeMemoEx;
	}

	@Override
	public void allSave(List<OfficeMemoEx> officeMemoExs) {
		for (OfficeMemoEx officeMemoEx : officeMemoExs) {
			if(StringUtils.isBlank(officeMemoEx.getId())){
				officeMemoEx.setId(getGUID());
			}
		}
		String sql = "insert into office_memo_ex(id, use_user_id, memo_id,is_send) values(?,?,?,?)";
		List<Object[]> objs=new ArrayList<Object[]>();
		for (OfficeMemoEx officeMemoEx : officeMemoExs) {
			Object[] obj=new Object[]{
					officeMemoEx.getId(),officeMemoEx.getUseUserId(),officeMemoEx.getMemoId(),officeMemoEx.getSend()
			};
			objs.add(obj);
		}
		int[] argTypes=new int[]{
			Types.CHAR,Types.CHAR,Types.CHAR,Types.VARCHAR
		};
		batchUpdate(sql, objs, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_memo_ex where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public void deletebyMemoId(String[] memoId) {
		String sql="delete from office_memo_ex where memo_id in";
		updateForInSQL(sql, null, memoId);
	}

	@Override
	public Integer update(OfficeMemoEx officeMemoEx){
		String sql = "update office_memo_ex set use_user_id = ?, memo_id = ?,is_send=? where id = ?";
		Object[] args = new Object[]{
			officeMemoEx.getUseUserId(), officeMemoEx.getMemoId(), officeMemoEx.getSend(),
			officeMemoEx.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMemoEx getOfficeMemoExById(String id){
		String sql = "select * from office_memo_ex where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMemoEx> getOfficeMemoExMapByIds(String[] ids){
		String sql = "select * from office_memo_ex where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMemoEx> getOfficeMemoExList(){
		String sql = "select * from office_memo_ex";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeMemoEx> getOfficeMemoExPage(Pagination page){
		String sql = "select * from office_memo_ex";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeMemoEx> getOfficeMemoExListByMemoId(String memoId) {
		String sql="select * from office_memo_ex where memo_id = ? and is_send=1";
		return query(sql, new String[]{memoId}, new MultiRow());
	}
}
