package net.zdsoft.eis.base.common.dao;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.UserLog;
import net.zdsoft.keel.util.Pagination;

public interface UserLogDao {
    /**
     * 增加
     * 
     * @param userLog
     */
    public void insertUserLog(UserLog userLog);

    /**
     * 批量删除日志in ids
     * 
     * @param ids
     */
    public void deleteUserLogs(String[] ids);

    /**
     * 定时删除日志,根据子系统,起始时间和截至时间
     * 
     * @param subSystem
     * @param endTime
     */
    public void deleteUserLogs(Integer subSystem, Date endTime);

    /**
     * 查找某个时间段内相应子系统(模块）的日志
     * 
     * @param startTime
     * @param endTime
     * @param subSystem 如果为null,则不限制
     * @param unitId 如果为Null,则不限制
     * @param page 分页参数，如果为null则不分页
     * @return
     */
    public List<UserLog> getUserLogs(Date startTime, Date endTime, Integer subSystem, String unitId,Pagination page);

    /**
     * 取日志表中条数
     * 
     * @return
     */
    public Integer getLogCount();

}
