package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.data.entity.CountOnlineTime;

public interface CountOnlineTimeService {
     public void save(CountOnlineTime countOnlineTime);
     
     public void update(CountOnlineTime countOnlineTime);
     
     public CountOnlineTime getCountOnlineTimeBySessId(String sessionId);
     
     public List<CountOnlineTime> getCountOnlineTimeList(String beginTime,String endTime,String[] unitIds);
     
     public List<CountOnlineTime> getCountOnlineTimeListByTime(String beginTime,String endTime,String[] unitIds);
     
     public List<CountOnlineTime> getCountOnlineTimeListByLastlogin();
     
     public void deleteByNullLogoutTime();
}
