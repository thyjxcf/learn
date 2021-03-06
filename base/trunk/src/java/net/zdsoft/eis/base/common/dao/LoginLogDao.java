package net.zdsoft.eis.base.common.dao;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.LoginLog;

public interface LoginLogDao {

    public void insertLoginLog(LoginLog log);
    
    /**
     * 获取用户最近的登陆时间
     * @param accountId
     * @return
     */
    public Date getUserLastLoginTime(String accountId);
    
    /**
     * 判断历史表是否存在
     * @param year
     * @return
     */
    public boolean isExistsTable(int year);
    
    /**
     * 创建历史表
     * @param year
     */
    public void createTable(int year);
    
    /**
     * 插入到历史表
     * @param year
     * @return
     */
    public void insertIntoHistoryTable(int year);
    
    /**
     * 删除一个月之前的数据
     * @return
     */
    public void deleteOneMonthAgoData();

}
