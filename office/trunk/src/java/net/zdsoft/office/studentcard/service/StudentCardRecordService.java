package net.zdsoft.office.studentcard.service;

import java.util.Date;
import java.util.List;

import net.zdsoft.office.studentcard.entity.StudentCardRecord;

public interface StudentCardRecordService {

	/**
	 * 查询某段时间的刷卡记录
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<StudentCardRecord> findStudentCardRecordbyTimes(Date startTime,
			Date endTime,Integer flag);
}
