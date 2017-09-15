package net.zdsoft.office.dailyoffice.sync.dao;

import java.util.List;

import net.zdsoft.office.dailyoffice.sync.entity.RecordInfo;

public interface RecordInfoDao {
	/**
	 * 根据条件获取学生刷卡记录
	 * @param ControllerID
	 * @param DataDate
	 * @param StartTime
	 * @param EndTime
	 * @param StaffIDs
	 * @return
	 */
	public List<RecordInfo> getList(String ControllerID, String DataDate, String StartTime, String EndTime, String[] StaffIDs);
}
