package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.frame.dao.ModuleLogDao;
import net.zdsoft.eis.system.frame.entity.ModuleLog;

public class ModuleLogDaoImpl extends BaseDao<ModuleLog> implements ModuleLogDao {

    private static final String SQL_INSERT_MODULELOG = "INSERT INTO log_module_use(module_id,region_code,unit_id,"
            + "account_id,server_type_id,server_id,creation_time,user_type,subsystem_id) "
            + "VALUES(?,?,?,?,?,?,?,?,?)";

    @Override
    public ModuleLog setField(ResultSet rs) throws SQLException {
        ModuleLog moduleLog = new ModuleLog();
        moduleLog.setModuleId(rs.getString("module_id"));
        moduleLog.setRegionCode(rs.getString("region_code"));
        moduleLog.setUnitId(rs.getString("unit_id"));
        moduleLog.setAccountId(rs.getString("account_id"));
        moduleLog.setServerTypeId(rs.getInt("server_type_id"));
        moduleLog.setServerId(rs.getInt("server_id"));
        moduleLog.setCreationTime(rs.getTimestamp("creation_time"));
        moduleLog.setUserType(rs.getInt("user_type"));
        moduleLog.setSubsystemId(rs.getInt("subsystem_id"));
        return moduleLog;
    }

    public void insertModuleLog(ModuleLog moduleLog) {
        moduleLog.setId(createId());
        moduleLog.setCreationTime(new Date());
        update(SQL_INSERT_MODULELOG, new Object[] { moduleLog.getModuleId(),
                moduleLog.getRegionCode(), moduleLog.getUnitId(), moduleLog.getAccountId(),
                moduleLog.getServerTypeId(), moduleLog.getServerId(), moduleLog.getCreationTime(),
                moduleLog.getUserType(), moduleLog.getSubsystemId() }, new int[] { Types.CHAR,
                Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER, Types.INTEGER, Types.DATE,
                Types.INTEGER, Types.INTEGER });
    }

}
