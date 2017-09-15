package net.zdsoft.office.repaire.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.repaire.entity.OfficeRepaireSms;
import net.zdsoft.office.repaire.dao.OfficeRepaireSmsDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_repaire_sms
 * @author 
 * 
 */
public class OfficeRepaireSmsDaoImpl extends BaseDao<OfficeRepaireSms> implements OfficeRepaireSmsDao{

	@Override
	public OfficeRepaireSms setField(ResultSet rs) throws SQLException{
		OfficeRepaireSms officeRepaireSms = new OfficeRepaireSms();
		officeRepaireSms.setId(rs.getString("id"));
		officeRepaireSms.setUnitId(rs.getString("unit_id"));
		officeRepaireSms.setThisId(rs.getString("this_id"));
		officeRepaireSms.setIsNeedSms(rs.getBoolean("is_need_sms"));
		return officeRepaireSms;
	}

	@Override
	public OfficeRepaireSms save(OfficeRepaireSms officeRepaireSms){
		String sql = "insert into office_repaire_sms(id, unit_id, this_id, is_need_sms) values(?,?,?,?)";
		if (StringUtils.isBlank(officeRepaireSms.getId())){
			officeRepaireSms.setId(createId());
		}
		Object[] args = new Object[]{
			officeRepaireSms.getId(), officeRepaireSms.getUnitId(), 
			officeRepaireSms.getThisId(), officeRepaireSms.getIsNeedSms()
		};
		update(sql, args);
		return officeRepaireSms;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_repaire_sms where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeRepaireSms officeRepaireSms){
		String sql = "update office_repaire_sms set unit_id = ?, this_id = ?, is_need_sms = ? where id = ?";
		Object[] args = new Object[]{
			officeRepaireSms.getUnitId(), officeRepaireSms.getThisId(), 
			officeRepaireSms.getIsNeedSms(), officeRepaireSms.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeRepaireSms getOfficeRepaireSmsById(String id){
		String sql = "select * from office_repaire_sms where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeRepaireSms> getOfficeRepaireSmsMapByIds(String[] ids){
		String sql = "select * from office_repaire_sms where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsList(){
		String sql = "select * from office_repaire_sms";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsPage(Pagination page){
		String sql = "select * from office_repaire_sms";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdList(String unitId){
		String sql = "select * from office_repaire_sms where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_repaire_sms where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public void batchInsert(List<OfficeRepaireSms> insertList){
		List<Object[]> objs = new ArrayList<Object[]>();
		for(OfficeRepaireSms officeRepaireSms : insertList){
			if(StringUtils.isBlank(officeRepaireSms.getId()))
				officeRepaireSms.setId(getGUID());
			Object[] args = new Object[]{
				officeRepaireSms.getId(), officeRepaireSms.getUnitId(), 
				officeRepaireSms.getThisId(), officeRepaireSms.getIsNeedSms()
			};
			objs.add(args);
		}
		int[] argTypes = new int[] { 
			Types.CHAR, Types.CHAR, Types.VARCHAR, Types.BOOLEAN
		};
		String sql = "insert into office_repaire_sms(id, unit_id, this_id, is_need_sms) values(?,?,?,?)";
		batchUpdate(sql, objs, argTypes);
	}
	
	@Override
	public void batchUpdate(List<OfficeRepaireSms> updateList){
		List<Object[]> objs = new ArrayList<Object[]>();
		for(OfficeRepaireSms officeRepaireSms : updateList){
			Object[] args = new Object[]{
				officeRepaireSms.getUnitId(), officeRepaireSms.getThisId(), 
				officeRepaireSms.getIsNeedSms(), officeRepaireSms.getId()
			};
			objs.add(args);
		}
		int[] argTypes = new int[] { 
			Types.CHAR, Types.VARCHAR, Types.BOOLEAN, Types.CHAR
		};
		String sql = "update office_repaire_sms set unit_id = ?, this_id = ?, is_need_sms = ? where id = ?";
		batchUpdate(sql, objs, argTypes);
	}
	
	@Override
	public List<OfficeRepaireSms> getOfficeRepaireSmsByTypeId(String unitId, String typeId){
		String sql = "select * from office_repaire_sms where unit_id = ? and this_id = ?";
		return query(sql, new Object[]{unitId, typeId}, new MultiRow());
	}
}
