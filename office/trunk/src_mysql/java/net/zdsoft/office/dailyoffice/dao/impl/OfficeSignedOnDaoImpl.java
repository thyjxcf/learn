package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeSignedOnDao;
import net.zdsoft.office.dailyoffice.entity.OfficeSignedOn;

import org.apache.commons.lang.StringUtils;
/**
 * office_signed_on
 * @author 
 * 
 */
public class OfficeSignedOnDaoImpl extends BaseDao<OfficeSignedOn> implements OfficeSignedOnDao{

	@Override
	public OfficeSignedOn setField(ResultSet rs) throws SQLException{
		OfficeSignedOn officeSignedOn = new OfficeSignedOn();
		officeSignedOn.setId(rs.getString("id"));
		officeSignedOn.setYear(rs.getString("acadyear"));
		officeSignedOn.setSemester(rs.getInt("semester"));
		officeSignedOn.setCreateUserId(rs.getString("create_user_id"));
		officeSignedOn.setCreateTime(rs.getTimestamp("create_time"));
		officeSignedOn.setUnitId(rs.getString("unit_id"));
		return officeSignedOn;
	}

	@Override
	public OfficeSignedOn save(OfficeSignedOn officeSignedOn){
		String sql = "insert into office_signed_on(id, acadyear, semester, create_user_id, create_time, unit_id) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSignedOn.getId())){
			officeSignedOn.setId(createId());
		}
		Object[] args = new Object[]{
			officeSignedOn.getId(), officeSignedOn.getYear(), 
			officeSignedOn.getSemester(), 
			officeSignedOn.getCreateUserId(), officeSignedOn.getCreateTime(), 
			officeSignedOn.getUnitId()
		};
		update(sql, args);
		return officeSignedOn;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_signed_on where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSignedOn officeSignedOn){
		String sql = "update office_signed_on set acadyear = ?, semester = ?, create_user_id = ?, create_time = ?, unit_id = ? where id = ?";
		Object[] args = new Object[]{
			officeSignedOn.getYear(), officeSignedOn.getSemester(), 
			 officeSignedOn.getCreateUserId(), 
			officeSignedOn.getCreateTime(), officeSignedOn.getUnitId(), 
			officeSignedOn.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSignedOn getOfficeSignedOnById(String id){
		String sql = "select * from office_signed_on where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSignedOn> getOfficeSignedOnMapByIds(String[] ids){
		String sql = "select * from office_signed_on where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnList(){
		String sql = "select * from office_signed_on";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnPage(Pagination page){
		String sql = "select * from office_signed_on";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdList(String unitId){
		String sql = "select * from office_signed_on where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_signed_on where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByOtherPage(String userId,String unitId,
			String year, String semseter,String time, Pagination page) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select * from office_signed_on where unit_id=?");
		args.add(unitId);
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and create_user_id = ?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(year)){
			sql.append(" and acadyear=?");
			args.add(year);
		}
		if(StringUtils.isNotBlank(semseter)){
			sql.append(" and semester=?");
			args.add(semseter);
		}
		if(StringUtils.isNotBlank(time)){
			sql.append(" and str_to_date(?, '%Y-%m-%d')=str_to_date(date_format(create_time,'%Y-%m-%d'), '%Y-%m-%d')");
			args.add(time);
		}
		if(page == null)
			return query(sql.toString(), args.toArray(), new MultiRow());
		
		return query(sql.toString(), args.toArray(), new MultiRow(), page);
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnCountByManager(String userId,
			String unitId, String year, String semseter, String startTime,
			String endTime, Pagination page) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select * from office_signed_on where unit_id=?");
		args.add(unitId);
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and create_user_id = ?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(year)){
			sql.append(" and acadyear=?");
			args.add(year);
		}
		if(StringUtils.isNotBlank(semseter)){
			sql.append(" and semester=?");
			args.add(semseter);
		}
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') <= str_to_date(date_format(create_time,'%Y-%m-%d'), '%Y-%m-%d')");
			args.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(create_time,'%Y-%m-%d'), '%Y-%m-%d')");
			args.add(endTime);
		}
		if(page == null)
			return query(sql.toString(), args.toArray(), new MultiRow());
		
		return query(sql.toString(), args.toArray(), new MultiRow(), page);
	}
}

