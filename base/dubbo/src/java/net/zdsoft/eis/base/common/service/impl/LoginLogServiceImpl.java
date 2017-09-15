package net.zdsoft.eis.base.common.service.impl;

import java.util.Date;

import net.zdsoft.eis.base.common.dao.LoginLogDao;
import net.zdsoft.eis.base.common.entity.LoginLog;
import net.zdsoft.eis.base.common.service.LoginLogService;

public class LoginLogServiceImpl implements LoginLogService {
	
	private LoginLogDao loginLogDao;

	public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }
	
	public void insert(LoginLog log) {
		loginLogDao.insertLoginLog(log);
	}

	@Override
	public Date getUserLastLoginTime(String accountId) {
		return loginLogDao.getUserLastLoginTime(accountId);
	}
	
	
	@Override
	public boolean isExistsTable(int year) {
		return loginLogDao.isExistsTable(year);
	}
	
	@Override
	public void createTable(int year) {
		loginLogDao.createTable(year);
	}
	
	@Override
	public void insertIntoHistoryTable(int year) {
		//将一个月前的数据移动到历史表
		loginLogDao.insertIntoHistoryTable(year);
		//删除一个月之前的数据
		loginLogDao.deleteOneMonthAgoData();
	}

}
