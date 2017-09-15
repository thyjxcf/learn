package net.zdsoft.eis.base.common.service;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.UserLog;
import net.zdsoft.keel.util.Pagination;

public interface UserLogService {

    /**
     * 新增日志
     * 
     * @param userLogDto
     */
    public void insertUserLog(UserLog userLogDto);

    /**
     * 删除日志In ids
     * 
     * @param ids
     */
    public void deleteUserLogs(String[] ids);

    /**
     * 定时删除日志,根据子系统和时间段
     * 
     * @param subSystem
     * @param date
     */
    public void deleteUserLogs(Integer subSystem, Integer date);

    /**
     * 查找某个时间段内相应子系统(模块）的日志
     * 
     * @param beginTime
     * @param endTime
     * @param subSystem 如果为Null,则不限制
     * @param unitid 如果为Null,则不限制
     * @param unitClass 单位类型
     * @param page 分页信息（可以为空）
     * @return
     */
    public List<UserLog> getUserLogs(Date beginTime, Date endTime, Integer subSystem,
            String unitid, Integer unitClass,Pagination page);

    /**
     * 取日志条数
     * 
     * @return
     */
    public Integer getLogCount();
}
