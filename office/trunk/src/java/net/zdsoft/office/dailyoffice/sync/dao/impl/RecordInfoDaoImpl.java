package net.zdsoft.office.dailyoffice.sync.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.office.dailyoffice.sync.dao.RecordInfoDao;
import net.zdsoft.office.dailyoffice.sync.entity.RecordInfo;

public class RecordInfoDaoImpl extends BaseDao<RecordInfo> implements RecordInfoDao {

	@Override
	public RecordInfo setField(ResultSet rs) throws SQLException {
		RecordInfo recordInfo = new RecordInfo();
		recordInfo.setStaffID(rs.getString("StaffID"));
		recordInfo.setControllerID(rs.getString("ControllerID"));
		recordInfo.setDataDate(rs.getString("DataDate"));
		recordInfo.setDataTime(rs.getString("DataTime"));
		return recordInfo;
	}

	@Override
	public List<RecordInfo> getList(String ControllerID, String DataDate,
			String StartTime, String EndTime, String[] StaffIDs) {
		String sql = "select * from TC_IODataDetail where ControllerID = ? "
				+ "and DataDate = ? and DataTime >= ? and DataTime <= ? and StaffID in";
		return queryForInSQL(sql, new Object[] {ControllerID, DataDate, StartTime, EndTime}, StaffIDs, new MultiRow());
	}
}
