package net.zdsoft.office.studentcard.dao;

import java.util.Date;
import java.util.List;

import net.zdsoft.office.studentcard.entity.StudentCardRecord;

public interface StudentCardRecordDao {
	
	public List<StudentCardRecord> findStudentCardRecordbyTimes(Date startTime,Date endTime,Integer flag);

}
