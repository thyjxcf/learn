package net.zdsoft.eis.system.frame.dao;

import java.sql.SQLException;

import net.zdsoft.eis.system.frame.dto.ResultPack;

public interface ExceptionDataDao {
    /**
     * 执行异常数据处理接口
     * 
     * @param sql
     * @return
     */
    public ResultPack executeQuery(String sql);

    /**
     * 执行更新及删除语句接口
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public Integer executeUpdate(String sql) throws SQLException;

}
