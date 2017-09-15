package net.zdsoft.office.customer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.customer.dao.OfficeCustomerFollowRecordDao;
import net.zdsoft.office.customer.entity.OfficeCustomerFollowRecord;

import org.apache.commons.lang.StringUtils;

/**
 * office_customer_follow_record 
 * @author 
 * 
 */
public class OfficeCustomerFollowRecordDaoImpl extends BaseDao<OfficeCustomerFollowRecord> implements OfficeCustomerFollowRecordDao{
	@Override
	public OfficeCustomerFollowRecord setField(ResultSet rs) throws SQLException{
		OfficeCustomerFollowRecord officeCustomerFollowRecord = new OfficeCustomerFollowRecord();
		officeCustomerFollowRecord.setId(rs.getString("id"));
		officeCustomerFollowRecord.setApplyId(rs.getString("apply_id"));
		officeCustomerFollowRecord.setCarbonCopyId(rs.getString("carbon_copy_id"));
		officeCustomerFollowRecord.setProgressState(rs.getString("progress_state"));
		officeCustomerFollowRecord.setRemark(rs.getString("remark"));
		officeCustomerFollowRecord.setCreateTime(rs.getTimestamp("create_time"));
		return officeCustomerFollowRecord;
	}
	
	public OfficeCustomerFollowRecord save(OfficeCustomerFollowRecord officeCustomerFollowRecord){
		String sql = "insert into office_customer_follow_record(id, apply_id, carbon_copy_id, progress_state, remark, create_time) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeCustomerFollowRecord.getId())){
			officeCustomerFollowRecord.setId(createId());
		}
		Object[] args = new Object[]{
			officeCustomerFollowRecord.getId(), officeCustomerFollowRecord.getApplyId(), 
			officeCustomerFollowRecord.getCarbonCopyId(), officeCustomerFollowRecord.getProgressState(), 
			officeCustomerFollowRecord.getRemark(), officeCustomerFollowRecord.getCreateTime()
		};
		update(sql, args);
		return officeCustomerFollowRecord;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_customer_follow_record where id in";
		return updateForInSQL(sql, null, ids);
	}
	@Override
	public List<OfficeCustomerFollowRecord> getFollowerRecordApplyId(String userId){
		String sql="select a.create_time,a.apply_id from (select max(create_time) as create_time,apply_id from office_customer_follow_record" 
				+" group by apply_id) a , office_customer_follow_record b where  a.apply_id = b.apply_id and"
				+" a.create_time=b.create_time and b.CARBON_COPY_ID = ?";
		return query(sql, new Object[]{userId},
				new MultiRowMapper<OfficeCustomerFollowRecord>() {
			@Override
			public OfficeCustomerFollowRecord mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				OfficeCustomerFollowRecord entity = new OfficeCustomerFollowRecord();
				entity.setCreateTime(rs.getDate("create_time"));
				entity.setApplyId(rs.getString("apply_id"));
				return entity;
			}
		});
	}
	@Override
	public Integer update(OfficeCustomerFollowRecord officeCustomerFollowRecord){
		String sql = "update office_customer_follow_record set apply_id = ?, carbon_copy_id = ?, progress_state = ?, remark = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeCustomerFollowRecord.getApplyId(), officeCustomerFollowRecord.getCarbonCopyId(), 
			officeCustomerFollowRecord.getProgressState(), officeCustomerFollowRecord.getRemark(), 
			officeCustomerFollowRecord.getCreateTime(), officeCustomerFollowRecord.getId()
		};
		return update(sql, args);
	}
	@Override
	public Integer updateApplyId(String newApplyId,String everApplyId){
		String sql = "update office_customer_follow_record set apply_id = ? where apply_id = ?";
		return update(sql, new Object[]{newApplyId,everApplyId});
	}

	@Override
	public OfficeCustomerFollowRecord getOfficeCustomerFollowRecordById(String id){
		String sql = "select * from office_customer_follow_record where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCustomerFollowRecord> getOfficeCustomerFollowRecordMapByIds(String[] ids){
		String sql = "select * from office_customer_follow_record where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCustomerFollowRecord> getOfficeCustomerFollowRecordList(String applyId){
		String sql = "select * from office_customer_follow_record where apply_id=? order by create_time desc";
		return query(sql,new Object[]{applyId} ,new MultiRow());
	}

	@Override
	public List<OfficeCustomerFollowRecord> getOfficeCustomerFollowRecordPage(Pagination page){
		String sql = "select * from office_customer_follow_record";
		return query(sql, new MultiRow(), page);
	}
	
}
