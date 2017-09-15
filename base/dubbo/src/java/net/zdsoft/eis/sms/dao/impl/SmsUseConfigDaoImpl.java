package net.zdsoft.eis.sms.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.sms.dao.SmsUseConfigDao;
import net.zdsoft.eis.sms.entity.SmsUseConfig;

/**
 * 类说明
 * 
 * @author jiangf
 * @version 创建时间：2011-8-6 上午11:57:26
 */

public class SmsUseConfigDaoImpl extends BaseDao<SmsUseConfig> implements
		SmsUseConfigDao {

	private static final String SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID_AND_SIGN = "select * from base_sms_use_config where unit_id =? and sign = ?";

	@Override
	public SmsUseConfig setField(ResultSet rs) throws SQLException {
		SmsUseConfig config = new SmsUseConfig();
		config.setId(rs.getString("id"));
		config.setUnitId(rs.getString("unit_id"));
		config.setSign(rs.getString("sign"));
		config.setName(rs.getString("name"));
		config.setIsused(rs.getInt("isused"));
		config.setNowvalue(rs.getString("nowvalue"));
		return config;
	}

	@Override
	public SmsUseConfig getUseConfig(String unitId, String sign) {
		return query(SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID_AND_SIGN, new Object[] {
				unitId, sign }, new SingleRow());
	}
}
