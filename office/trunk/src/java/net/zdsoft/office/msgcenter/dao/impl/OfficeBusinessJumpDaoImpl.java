package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.office.msgcenter.dao.OfficeBusinessJumpDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_business_jump
 * @author 
 * 
 */
public class OfficeBusinessJumpDaoImpl extends BaseDao<OfficeBusinessJump> implements OfficeBusinessJumpDao{

	@Override
	public OfficeBusinessJump setField(ResultSet rs) throws SQLException{
		OfficeBusinessJump officeBusinessJump = new OfficeBusinessJump();
		officeBusinessJump.setId(rs.getString("id"));
		officeBusinessJump.setUnitId(rs.getString("unit_id"));
		officeBusinessJump.setMsgId(rs.getString("msg_id"));
		officeBusinessJump.setReceivers(rs.getString("receivers"));
		officeBusinessJump.setReceiverType(rs.getString("receiver_type"));
		officeBusinessJump.setModules(rs.getString("modules"));
		officeBusinessJump.setContent(rs.getString("content"));
		officeBusinessJump.setCreateTime(rs.getTimestamp("create_time"));
		return officeBusinessJump;
	}

	@Override
	public OfficeBusinessJump save(OfficeBusinessJump officeBusinessJump){
		String sql = "insert into office_business_jump(id, unit_id, msg_id, receivers, receiver_type, modules, content, create_time) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeBusinessJump.getId())){
			officeBusinessJump.setId(createId());
		}
		Object[] args = new Object[]{
			officeBusinessJump.getId(), officeBusinessJump.getUnitId(), 
			officeBusinessJump.getMsgId(), officeBusinessJump.getReceivers(), 
			officeBusinessJump.getReceiverType(), officeBusinessJump.getModules(), 
			officeBusinessJump.getContent(), officeBusinessJump.getCreateTime()
		};
		update(sql, args);
		return officeBusinessJump;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_business_jump where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeBusinessJump officeBusinessJump){
		String sql = "update office_business_jump set unit_id = ?, msg_id = ?, receivers = ?, receiver_type = ?, modules = ?, content = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeBusinessJump.getUnitId(), officeBusinessJump.getMsgId(), 
			officeBusinessJump.getReceivers(), officeBusinessJump.getReceiverType(), 
			officeBusinessJump.getModules(), officeBusinessJump.getContent(), 
			officeBusinessJump.getCreateTime(), officeBusinessJump.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBusinessJump getOfficeBusinessJumpById(String id){
		String sql = "select * from office_business_jump where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBusinessJump> getOfficeBusinessJumpMapByIds(String[] ids){
		String sql = "select * from office_business_jump where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpList(){
		String sql = "select * from office_business_jump";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpPage(Pagination page){
		String sql = "select * from office_business_jump";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdList(String unitId){
		String sql = "select * from office_business_jump where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_business_jump where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpByMsgId(String msgId){
		String sql = "select * from office_business_jump where msg_id = ?";
		return query(sql, new Object[]{msgId}, new MultiRow());
	}
	
	@Override
	public void batchSave(List<OfficeBusinessJump> list){
		String sql = "insert into office_business_jump(id, unit_id, msg_id, receivers, receiver_type, modules, content, create_time) values(?,?,?,?,?,?,?,?)";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for(OfficeBusinessJump officeBusinessJump : list){
			if (StringUtils.isBlank(officeBusinessJump.getId())){
				officeBusinessJump.setId(createId());
			}
			Object[] args = new Object[]{
					officeBusinessJump.getId(), officeBusinessJump.getUnitId(), 
					officeBusinessJump.getMsgId(), officeBusinessJump.getReceivers(), 
					officeBusinessJump.getReceiverType(), officeBusinessJump.getModules(), 
					officeBusinessJump.getContent(), officeBusinessJump.getCreateTime()
			};
			listOfArgs.add(args);
        }
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.VARCHAR, 
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE};
		batchUpdate(sql, listOfArgs, argTypes);
	}
}
