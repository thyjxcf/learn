package net.zdsoft.eis.base.data.service.impl;

import java.util.List;

import net.zdsoft.eis.base.data.dao.CountOnlineTimeDao;
import net.zdsoft.eis.base.data.entity.CountOnlineTime;
import net.zdsoft.eis.base.data.service.CountOnlineTimeService;

public class CountOnlineTimeServiceImpl implements CountOnlineTimeService{
    
	private CountOnlineTimeDao countOnlineTimeDao;
	
	
	@Override
	public void save(CountOnlineTime countOnlineTime) {
		countOnlineTimeDao.save(countOnlineTime);		
	}

	public void setCountOnlineTimeDao(CountOnlineTimeDao countOnlineTimeDao) {
		this.countOnlineTimeDao = countOnlineTimeDao;
	}

	@Override
	public void update(CountOnlineTime countOnlineTime) {
		countOnlineTimeDao.update(countOnlineTime);
	}

	@Override
	public CountOnlineTime getCountOnlineTimeBySessId(String sessionId) {
		return countOnlineTimeDao.getCountOnlineTimeBySessId(sessionId);
	}

	@Override
	public List<CountOnlineTime> getCountOnlineTimeList(String beginTime,String endTime,String[] unitIds) {
		return countOnlineTimeDao.getCountOnlineTimeList(beginTime,endTime,unitIds);
	}

	@Override
	public List<CountOnlineTime> getCountOnlineTimeListByTime(String beginTime,
			String endTime,String[] unitIds) {
		return countOnlineTimeDao.getCountOnlineTimeListByTime(beginTime,endTime,unitIds);
	}

	@Override
	public List<CountOnlineTime> getCountOnlineTimeListByLastlogin() {
		return countOnlineTimeDao.getCountOnlineTimeListByLastlogin();
	}

	@Override
	public void deleteByNullLogoutTime() {
	    countOnlineTimeDao.deleteByNullLogoutTime();
	}

	
}
