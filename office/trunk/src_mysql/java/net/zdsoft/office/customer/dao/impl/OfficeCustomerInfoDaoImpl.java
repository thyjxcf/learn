package net.zdsoft.office.customer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.customer.dao.OfficeCustomerInfoDao;
import net.zdsoft.office.customer.entity.OfficeCustomerApply;
import net.zdsoft.office.customer.entity.OfficeCustomerInfo;
import net.zdsoft.office.customer.entity.SearchCustomer;

import org.apache.commons.lang.StringUtils;

/**
 * office_customer_info 
 * @author 
 * 
 */
public class OfficeCustomerInfoDaoImpl extends BaseDao<OfficeCustomerInfo> implements OfficeCustomerInfoDao{
	@Override
	public OfficeCustomerInfo setField(ResultSet rs) throws SQLException{
		OfficeCustomerInfo officeCustomerInfo = new OfficeCustomerInfo();
		officeCustomerInfo.setId(rs.getString("id"));
		officeCustomerInfo.setUnitId(rs.getString("unit_id"));
		officeCustomerInfo.setName(rs.getString("name"));
		officeCustomerInfo.setNickName(rs.getString("nick_name"));
		officeCustomerInfo.setRegion(rs.getString("region"));
		officeCustomerInfo.setType(rs.getString("type"));
		officeCustomerInfo.setInfoSource(rs.getString("info_source"));
		officeCustomerInfo.setBackgroundInfo(rs.getString("background_info"));
		officeCustomerInfo.setProgressState(rs.getString("progress_state"));
		officeCustomerInfo.setProduct(rs.getString("product"));
		officeCustomerInfo.setContact(rs.getString("contact"));
		officeCustomerInfo.setPhone(rs.getString("phone"));
		officeCustomerInfo.setContactInfo(rs.getString("contact_info"));
		officeCustomerInfo.setAddTime(rs.getTimestamp("add_time"));
		officeCustomerInfo.setState(rs.getInt("state"));
		officeCustomerInfo.setCreateTime(rs.getTimestamp("create_time"));
		return officeCustomerInfo;
	}
	
	public OfficeCustomerInfo save(OfficeCustomerInfo officeCustomerInfo){
		String sql = "insert into office_customer_info(id, unit_id, name, nick_name, region, type, info_source, background_info, progress_state, product, contact, phone, contact_info, add_time, state, create_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeCustomerInfo.getId())){
			officeCustomerInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeCustomerInfo.getId(), officeCustomerInfo.getUnitId(), 
			officeCustomerInfo.getName(), officeCustomerInfo.getNickName(), 
			officeCustomerInfo.getRegion(), officeCustomerInfo.getType(), 
			officeCustomerInfo.getInfoSource(), officeCustomerInfo.getBackgroundInfo(), 
			officeCustomerInfo.getProgressState(), officeCustomerInfo.getProduct(), 
			officeCustomerInfo.getContact(), officeCustomerInfo.getPhone(), 
			officeCustomerInfo.getContactInfo(), officeCustomerInfo.getAddTime(), 
			officeCustomerInfo.getState(), officeCustomerInfo.getCreateTime()
		};
		update(sql, args);
		return officeCustomerInfo;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_customer_info where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeCustomerInfo officeCustomerInfo){
		String sql = "update office_customer_info set unit_id = ?, name = ?, nick_name = ?, region = ?, type = ?, info_source = ?, background_info = ?, progress_state = ?, product = ?, contact = ?, phone = ?, contact_info = ?, add_time = ?, state = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeCustomerInfo.getUnitId(), officeCustomerInfo.getName(), 
			officeCustomerInfo.getNickName(), officeCustomerInfo.getRegion(), 
			officeCustomerInfo.getType(), officeCustomerInfo.getInfoSource(), 
			officeCustomerInfo.getBackgroundInfo(), officeCustomerInfo.getProgressState(), 
			officeCustomerInfo.getProduct(), officeCustomerInfo.getContact(), 
			officeCustomerInfo.getPhone(), officeCustomerInfo.getContactInfo(), 
			officeCustomerInfo.getAddTime(), officeCustomerInfo.getState(), 
			officeCustomerInfo.getCreateTime(), officeCustomerInfo.getId()
		};
		return update(sql, args);
	}
	
	public void updateState(String id, int state, Date addTime){
		String sql = "update office_customer_info set state = ? ";
		if(addTime != null){
			sql += " , add_time = ? where id = ?";
			update(sql, new Object[]{state, addTime, id});
		}else{
			sql += " where id = ?";
			update(sql, new Object[]{state, id});
		}
	}
	@Override
	public List<OfficeCustomerInfo> getSameCustomerName(OfficeCustomerInfo officeCustomerInfo){
		String sql = "select * from office_customer_info where name = ? ";
		if(StringUtils.isNotEmpty(officeCustomerInfo.getId())){
			sql+="and id !=? ";
			return query(sql, new Object[]{officeCustomerInfo.getName(),officeCustomerInfo.getId()},new MultiRow());
		}
		return query(sql, new Object[]{officeCustomerInfo.getName()},new MultiRow());
	}
	public Integer saveAddTime(Date addTime){
		String sql="update office_customer_info set add_time=? where  state=2 and add_time is null";
		return update(sql,new Object[]{addTime});
	}
	@Override
	public List<OfficeCustomerInfo> getCustomerLibraryByUnitIdPage(
			SearchCustomer searchCustomer, String unitId, Pagination page) {
		StringBuilder sql=new StringBuilder(" select * from office_customer_info where unit_id =? and state =2 ");
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(searchCustomer.getName())){
			sql.append(" and name like ? ");
			args.add("%"+searchCustomer.getName().trim()+"%");
		}
		if(StringUtils.isNotBlank(searchCustomer.getRegion())){
			sql.append(" and region like ?");
			args.add(searchCustomer.getRegion()+"%");
		}
		if(StringUtils.isNotBlank(searchCustomer.getType())){
			sql.append(" and type = ?");
			args.add(searchCustomer.getType());
		}
		sql.append(" order by add_time desc,type,region");
		return query(sql.toString(),args.toArray(),new MultiRow(),page);
	}
	@Override
	public OfficeCustomerInfo getOfficeCustomerInfoById(String id){
		String sql = "select * from office_customer_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCustomerInfo> getOfficeCustomerInfoMapByIds(String[] ids){
		String sql = "select * from office_customer_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCustomerInfo> getOfficeCustomerInfoList(){
		String sql = "select * from office_customer_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCustomerInfo> getOfficeCustomerInfoPage(Pagination page){
		String sql = "select * from office_customer_info";
		return query(sql, new MultiRow(), page);
	}
	

	@Override
	public List<OfficeCustomerInfo> getOfficeCustomerInfoByUnitIdList(String unitId){
		String sql = "select * from office_customer_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCustomerInfo> getOfficeCustomerInfoByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_customer_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}
