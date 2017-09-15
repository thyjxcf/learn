package net.zdsoft.office.dailyoffice.sync.dao;

import java.util.List;

import net.zdsoft.office.dailyoffice.sync.entity.DoorRecord;

public interface DoorRecordDao {
	public List<DoorRecord> getList(String StartDataDate,
			String EndDataDate, String StartDataTime,  String EndDataTime);
}
