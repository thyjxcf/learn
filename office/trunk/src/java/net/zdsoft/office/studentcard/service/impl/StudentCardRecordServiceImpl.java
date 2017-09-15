package net.zdsoft.office.studentcard.service.impl;

import java.util.Date;
import java.util.List;

import net.zdsoft.office.studentcard.dao.StudentCardRecordDao;
import net.zdsoft.office.studentcard.entity.StudentCardRecord;
import net.zdsoft.office.studentcard.service.StudentCardRecordService;

public class StudentCardRecordServiceImpl implements StudentCardRecordService{

	private StudentCardRecordDao studentCardRecordDao;
	@Override
	public List<StudentCardRecord> findStudentCardRecordbyTimes(Date startTime,
			Date endTime,Integer flag) {
		return studentCardRecordDao.findStudentCardRecordbyTimes(startTime, endTime,flag);
	}
	public void setStudentCardRecordDao(StudentCardRecordDao studentCardRecordDao) {
		this.studentCardRecordDao = studentCardRecordDao;
	}
	

}
