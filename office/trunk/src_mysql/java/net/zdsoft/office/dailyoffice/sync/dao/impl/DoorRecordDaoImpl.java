package net.zdsoft.office.dailyoffice.sync.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.office.dailyoffice.sync.dao.DoorRecordDao;
import net.zdsoft.office.dailyoffice.sync.entity.DoorRecord;
import net.zdsoft.office.dailyoffice.sync.entity.RecordInfo;

public class DoorRecordDaoImpl extends BaseDao<DoorRecord> implements DoorRecordDao {

	@Override
	public DoorRecord setField(ResultSet rs) throws SQLException {
		DoorRecord doorRecord = new DoorRecord();
		doorRecord.setStaffID(rs.getString("StaffID"));
		doorRecord.setDataDate(rs.getString("DataDate"));
		doorRecord.setDataTime(rs.getString("DataTime"));
		doorRecord.setControllerID(rs.getString("ControllerID"));
		doorRecord.setReaderID(rs.getInt("ReaderID"));
		doorRecord.setDataDoorNo(rs.getInt("DataDoorNo"));
		return doorRecord;
	}

	@Override
	public List<DoorRecord> getList(String StartDataDate,
			String EndDataDate, String StartDataTime,  String EndDataTime) {
		String sql = "select * from DAS_IODataDetail where  DataDate >= ? and DataDate <= ? and DataTime >= ? and DataTime <= ?";
		return query(sql, new Object[] {StartDataDate, EndDataDate, StartDataTime, EndDataTime}, new MultiRow());
	}
}
