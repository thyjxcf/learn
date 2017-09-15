package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.data.dao.BaseSysOptionDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

public class BaseSysOptionDaoImpl extends BaseDao<SysOption> implements
        BaseSysOptionDao {
    private static final String SQL_INSERT_SYSOPTION = "INSERT INTO base_sys_option(id,option_code,name,"
            + "default_value,description,now_value,validate_js,viewable,event_source,is_deleted) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_SYSOPTION = "UPDATE base_sys_option SET option_code=?,name=?,"
            + "default_value=?,description=?,now_value=?,validate_js=?,viewable=?,event_source=? WHERE id=?";
    private static final String SQL_UPDATE_NOWVALUE_SYSOPTION = "update base_sys_option set now_value=?, event_source=? where option_code=?";
    private static final String SQL_DELETE_SYSOPTION_BY_ID = "UPDATE base_sys_option SET is_deleted=1, event_source=? WHERE id=?";
    private static final String SQL_FIND_SYSOPTION = "SELECT * FROM base_sys_option WHERE is_deleted=0";

    public SysOption setField(ResultSet rs) throws SQLException {
        SysOption sysOption = new SysOption();
        sysOption.setId(rs.getString("id"));
        sysOption.setOptionCode(rs.getString("option_code"));
        sysOption.setName(rs.getString("name"));
        sysOption.setDefaultValue(rs.getString("default_value"));
        sysOption.setDescription(rs.getString("description"));
        sysOption.setNowValue(rs.getString("now_value"));
        sysOption.setValidateJS(rs.getString("validate_js"));
        sysOption.setViewable(rs.getInt("viewable"));
        return sysOption;
    }

    public void insertSysOption(SysOption sysOption) {
        if (StringUtils.isBlank(sysOption.getId()))
            sysOption.setId(createId());
        sysOption.setCreationTime(new Date());
        sysOption.setIsdeleted(false);
        update(SQL_INSERT_SYSOPTION, new Object[] { sysOption.getId(),
                sysOption.getOptionCode(), sysOption.getName(),
                sysOption.getDefaultValue(), sysOption.getDescription(),
                sysOption.getNowValue(), sysOption.getValidateJS(),
                sysOption.getViewable(), sysOption.getEventSourceValue(),
                sysOption.getIsdeleted() ? 1 : 0 }, new int[] { Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
                Types.INTEGER });
    }

    public void updateSysOption(SysOption sysOption) {
        update(SQL_UPDATE_SYSOPTION, new Object[] { sysOption.getOptionCode(),
                sysOption.getName(), sysOption.getDefaultValue(),
                sysOption.getDescription(), sysOption.getNowValue(),
                sysOption.getValidateJS(), sysOption.getViewable(),
                sysOption.getEventSourceValue(), sysOption.getId() },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    public void deleteSysOption(String sysOptionId, EventSourceType eventSource) {
        update(SQL_DELETE_SYSOPTION_BY_ID, new Object[] {
                eventSource.getValue(), sysOptionId });
    }

    public void batchUpdateSysOption(SysOption[] sysOptions) {
        if (ArrayUtils.isNotEmpty(sysOptions)) {
            List<Object[]> listOfArgs = new ArrayList<Object[]>();
            for (SysOption opt : sysOptions) {
                listOfArgs.add(new Object[] { opt.getNowValue(),
                        opt.getEventSourceValue(), opt.getOptionCode() });
            }
            int[] argTypes = { Types.CHAR, Types.INTEGER, Types.CHAR };
            this.batchUpdate(SQL_UPDATE_NOWVALUE_SYSOPTION, listOfArgs,
                    argTypes);
        }
    }

    public List<SysOption> getSysOptions() {
        return query(SQL_FIND_SYSOPTION, new MultiRow());
    }
}
