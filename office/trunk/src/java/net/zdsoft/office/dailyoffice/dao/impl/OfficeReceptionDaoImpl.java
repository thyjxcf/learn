package net.zdsoft.office.dailyoffice.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeReceptionDao;
import net.zdsoft.office.dailyoffice.entity.OfficeReception;

import org.apache.commons.lang.StringUtils;
/**
 * office_reception
 * @author 
 * 
 */
public class OfficeReceptionDaoImpl extends BaseDao<OfficeReception> implements OfficeReceptionDao{
	private static final String GetOfficeReceptionByUnitIdSql="select *from office_reception where unit_id=?";

	@Override
	public OfficeReception setField(ResultSet rs) throws SQLException{
		OfficeReception officeReception = new OfficeReception();
		officeReception.setId(rs.getString("id"));
		officeReception.setUnitId(rs.getString("unit_id"));
		officeReception.setPlace(rs.getString("place"));
		officeReception.setReceptionDate(rs.getTimestamp("reception_date"));
		officeReception.setStartWorkUserId(rs.getString("start_work_user_id"));
		officeReception.setContent(rs.getString("content"));
		officeReception.setIsDining(rs.getBoolean("is_dining"));
		officeReception.setPersonNumber(rs.getInt("person_number"));
		officeReception.setStandard(rs.getString("standard"));
		officeReception.setCarSituation(rs.getString("car_situation"));
		officeReception.setIsAcommodation(rs.getBoolean("is_acommodation"));
		officeReception.setReceptionUserId(rs.getString("reception_user_id"));
		officeReception.setAccompanyPerson(rs.getString("accompany_person"));
		officeReception.setCamemaPerson(rs.getString("camema_person"));
		officeReception.setCreateUserId(rs.getString("create_user_id"));
		officeReception.setCreateTime(rs.getTimestamp("create_time"));
		return officeReception;
	}

	@Override
	public OfficeReception save(OfficeReception officeReception){
		String sql = "insert into office_reception(id, unit_id, place,reception_date,start_work_user_id, content, is_dining, person_number, standard, car_situation, is_acommodation, reception_user_id, accompany_person, camema_person, create_user_id, create_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeReception.getId())){
			officeReception.setId(createId());
			officeReception.setCreateTime(new Date());
		}
		Object[] args = new Object[]{
			officeReception.getId(), officeReception.getUnitId(), 
			officeReception.getPlace(), officeReception.getReceptionDate(),
			officeReception.getStartWorkUserId(), 
			officeReception.getContent(), officeReception.getIsDining(), 
			officeReception.getPersonNumber(), officeReception.getStandard(), 
			officeReception.getCarSituation(), officeReception.getIsAcommodation(), 
			officeReception.getReceptionUserId(), officeReception.getAccompanyPerson(), 
			officeReception.getCamemaPerson(), officeReception.getCreateUserId(), 
			officeReception.getCreateTime()
		};
		update(sql, args);
		return officeReception;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_reception where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeReception officeReception){
		String sql = "update office_reception set unit_id = ?, place = ?, start_work_user_id = ?, content = ?, is_dining = ?, person_number = ?, standard = ?, car_situation = ?, is_acommodation = ?, reception_user_id = ?, accompany_person = ?, camema_person = ?, create_user_id = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeReception.getUnitId(), officeReception.getPlace(), 
			officeReception.getStartWorkUserId(), officeReception.getContent(), 
			officeReception.getIsDining(), officeReception.getPersonNumber(), 
			officeReception.getStandard(), officeReception.getCarSituation(), 
			officeReception.getIsAcommodation(), officeReception.getReceptionUserId(), 
			officeReception.getAccompanyPerson(), officeReception.getCamemaPerson(), 
			officeReception.getCreateUserId(), officeReception.getCreateTime(), 
			officeReception.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeReception getOfficeReceptionById(String id){
		String sql = "select * from office_reception where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<OfficeReception> getOfficeReceptionByUnitId(String unitId) {
		return query(GetOfficeReceptionByUnitIdSql, unitId, new MultiRow());
	}

	@Override
	public List<OfficeReception> getOfficeReceptionByUnitIdWithPage(
			Date startTime, Date endTime,
			String unitId, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_reception where unit_id = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
	
		if(startTime!=null){
			sql.append("and reception_date>=?");
			args.add(startTime);
		}if(endTime!=null){
			sql.append("and reception_date<=?");
			args.add(endTime);
		}
		sql.append(" order by reception_date desc ");
		return  query(sql.toString(), args.toArray(), new MultiRow(),page);
	}

	

}