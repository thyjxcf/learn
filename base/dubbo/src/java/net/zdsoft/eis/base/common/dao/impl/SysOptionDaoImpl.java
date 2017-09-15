package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.basedata.remote.service.SysOptionRemoteService;
import net.zdsoft.eis.base.common.dao.SysOptionDao;
import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.frame.client.BaseDao;

public class SysOptionDaoImpl extends BaseDao<SysOption> implements SysOptionDao {
    private static final String SQL_FIND_SYSOPTION_BY_CODE = "SELECT * FROM base_sys_option WHERE option_code=? AND is_deleted=0";

    @Autowired
    private SysOptionRemoteService optionRemoteService;
    
    @Override
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

    public SysOption getSysOption(String optionCode) {
        return SysOption.dc(optionRemoteService.findValue(optionCode));
    	
    	//return query(SQL_FIND_SYSOPTION_BY_CODE, optionCode, new SingleRow());
    }

	@Override
	public List<SysOption> getSysOptions() {
		return SysOption.dt(optionRemoteService.findAll());
		
		//return query("select * from base_sys_option", new MultiRow());
	}
    
    

}
