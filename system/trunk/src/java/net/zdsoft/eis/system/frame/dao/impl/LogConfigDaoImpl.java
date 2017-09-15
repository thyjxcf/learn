package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.frame.dao.LogConfigDao;
import net.zdsoft.eis.system.frame.entity.LogConfig;

public class LogConfigDaoImpl extends BaseDao<LogConfig> implements LogConfigDao {

    private static final String SQL_INSERT_LOGCONFIG = "INSERT INTO sys_log_config(id,subsystem,days,"
            + "flag) " + "VALUES(?,?,?,?)";

    private static final String SQL_UPDATE_LOGCONFIG = "UPDATE sys_log_config SET subsystem=?,days=?,"
            + "flag=? WHERE id=?";

    private static final String SQL_FIND_LOGCONFIG_BY_ID = "SELECT * FROM sys_log_config WHERE id=?";

    private static final String SQL_FIND_LOGCONFIGS = "SELECT * FROM sys_log_config ";

    public LogConfig setField(ResultSet rs) throws SQLException {
        LogConfig logConfig = new LogConfig();
        logConfig.setId(rs.getLong("id"));
        logConfig.setSubSystem(rs.getInt("subsystem"));
        logConfig.setDays(rs.getInt("days"));
        logConfig.setFlag(rs.getInt("flag"));
        return logConfig;
    }

    public void insertLogConfig(LogConfig logConfig) {
        logConfig.setId(getIncrementerKey());
        update(SQL_INSERT_LOGCONFIG, new Object[] { logConfig.getId(), logConfig.getSubSystem(),
                logConfig.getDays(), logConfig.getFlag() }, new int[] { Types.INTEGER,
                Types.INTEGER, Types.INTEGER, Types.INTEGER });
    }

    public void updateLogConfig(LogConfig logConfig) {
        update(SQL_UPDATE_LOGCONFIG, new Object[] { logConfig.getSubSystem(), logConfig.getDays(),
                logConfig.getFlag(), logConfig.getId() }, new int[] { Types.INTEGER, Types.INTEGER,
                Types.INTEGER, Types.CHAR });
    }

    public void saveLogConfigDays(LogConfig logConfig) {
        if (null == logConfig.getId() || "".equals(logConfig.getId())) {
            insertLogConfig(logConfig);
        } else {
            LogConfig lc = getLogConfig(logConfig.getId());
            if (lc.getDays() != logConfig.getDays()) {
                lc.setDays(logConfig.getDays());
                updateLogConfig(lc);
            }
        }
    }

    public LogConfig getLogConfig(long logConfigId) {
        return query(SQL_FIND_LOGCONFIG_BY_ID, new Object[] { logConfigId }, new SingleRow());
    }

    public List<LogConfig> getLogConfigs() {
        return query(SQL_FIND_LOGCONFIGS, null, null, new MultiRow());
    }

}
