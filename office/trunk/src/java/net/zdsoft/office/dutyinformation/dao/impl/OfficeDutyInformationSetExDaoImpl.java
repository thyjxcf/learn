package net.zdsoft.office.dutyinformation.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.org.objectweb.asm.Type;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSetEx;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyInformationSetExDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.UUIDGenerator;
/**
 * office_duty_information_set_ex
 * @author 
 * 
 */
public class OfficeDutyInformationSetExDaoImpl extends BaseDao<OfficeDutyInformationSetEx> implements OfficeDutyInformationSetExDao{

	@Override
	public OfficeDutyInformationSetEx setField(ResultSet rs) throws SQLException{
		OfficeDutyInformationSetEx officeDutyInformationSetEx = new OfficeDutyInformationSetEx();
		officeDutyInformationSetEx.setId(rs.getString("id"));
		officeDutyInformationSetEx.setDutyInformationId(rs.getString("duty_information_id"));
		officeDutyInformationSetEx.setUserId(rs.getString("user_id"));
		return officeDutyInformationSetEx;
	}

	@Override
	public OfficeDutyInformationSetEx save(OfficeDutyInformationSetEx officeDutyInformationSetEx){
		String sql = "insert into office_duty_information_set_ex(id, duty_information_id, user_id) values(?,?,?)";
		if (StringUtils.isBlank(officeDutyInformationSetEx.getId())){
			officeDutyInformationSetEx.setId(createId());
		}
		Object[] args = new Object[]{
			officeDutyInformationSetEx.getId(), officeDutyInformationSetEx.getDutyInformationId(), 
			officeDutyInformationSetEx.getUserId()
		};
		update(sql, args);
		return officeDutyInformationSetEx;
	}

	@Override
	public void batchSave(List<OfficeDutyInformationSetEx> officeDutyInformationSetExs) {
		String sql = "insert into office_duty_information_set_ex(id, duty_information_id, user_id) values(?,?,?)";
		List<Object[]> args=new ArrayList<Object[]>();
		for (OfficeDutyInformationSetEx officeDutyInformationSetEx : officeDutyInformationSetExs) {
			if(StringUtils.isBlank(officeDutyInformationSetEx.getId())){
				officeDutyInformationSetEx.setId(UUIDGenerator.getUUID());
			}
			Object[] obs=new Object[]{officeDutyInformationSetEx.getId(),officeDutyInformationSetEx.getDutyInformationId(),
					officeDutyInformationSetEx.getUserId()};
			args.add(obs);
		}
		int[] argTypes=new int[]{Types.CHAR,Types.CHAR,Types.CHAR};
		batchUpdate(sql, args, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_duty_information_set_ex where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public void delete(String dutyId) {
		String sql="delete from office_duty_information_set_ex where duty_information_id=?";
		update(sql, new String[]{dutyId});
	}

	@Override
	public Integer update(OfficeDutyInformationSetEx officeDutyInformationSetEx){
		String sql = "update office_duty_information_set_ex set duty_information_id = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeDutyInformationSetEx.getDutyInformationId(), officeDutyInformationSetEx.getUserId(), 
			officeDutyInformationSetEx.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDutyInformationSetEx getOfficeDutyInformationSetExById(String id){
		String sql = "select * from office_duty_information_set_ex where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyInformationSetEx> getOfficeDutyInformationSetExMapByIds(String[] ids){
		String sql = "select * from office_duty_information_set_ex where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExList(){
		String sql = "select * from office_duty_information_set_ex";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExPage(Pagination page){
		String sql = "select * from office_duty_information_set_ex";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExsByDutyId(
			String dutyId) {
		String sql="select * from office_duty_information_set_ex where duty_information_id=?";
		return query(sql, new String[]{dutyId}, new MultiRow());
	}
	
}
