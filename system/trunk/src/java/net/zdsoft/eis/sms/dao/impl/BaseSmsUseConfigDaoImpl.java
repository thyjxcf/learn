package net.zdsoft.eis.sms.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dao.BaseSmsUseConfigDao;
import net.zdsoft.eis.sms.entity.SmsUseConfig;

/**
 * @author jiangf
 * @version 创建时间：2011-8-5 下午03:24:22
 */

public class BaseSmsUseConfigDaoImpl extends BaseDao<SmsUseConfig> implements
		BaseSmsUseConfigDao {

	private static final String SQL_INSERT_SMS_USE_CONFIG = "insert into base_sms_use_config(id,unit_id,sign,name,isused,nowvalue) values(?,?,?,?,?,?)";

	private static final String SQL_UPDATE_SMS_USE_CONFIG = "update base_sms_use_config set unit_id=?,sign=?,name=?,isused=?,nowvalue=? where id=?";

	private static final String SQL_DELETE_SMS_USE_CONFIG = "delete from base_sms_use_config where unit_id=?";

	private static final String SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID = "select * from base_sms_use_config where unit_id = ?";

	private static final String SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID_CLIENT_ID = "select unit_id from base_sms_use_config where unit_id <> ? and sign=? and nowvalue=?";

	private static final String SQL_FIND_SMS_USE_CONFIG_COUNT_BY_UNIT_ID = "select count(*) from base_sms_use_config where unit_id = ? ";

	private static final String SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID_AND_SIGN = "select * from base_sms_use_config where unit_id =? and sign = ?";

	private static final String SQL_FIND_UNIT_ID_BY_CLIENT_ID = "select unit_id from base_sms_use_config where sign=? and nowvalue=?";

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
	public String getUnitIdByClientId(String clientId) {
		return queryForString(SQL_FIND_UNIT_ID_BY_CLIENT_ID, new Object[] {
				SmsConstant.CONFIG_SIGN_CLIENTID, clientId });
	}

	@Override
	public List<SmsUseConfig> getUseConfigByUnitId(String unitId) {
		return query(SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID, unitId, new MultiRow());
	}

	@Override
	public SmsUseConfig getUseConfig(String unitId, String sign) {
		return query(SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID_AND_SIGN, new Object[] {
				unitId, sign }, new SingleRow());
	}

	@Override
	public boolean ieExistsConfiginfo(String unitId) {
		int num = queryForInt(SQL_FIND_SMS_USE_CONFIG_COUNT_BY_UNIT_ID,
				new Object[] { unitId });
		return num > 0 ? true : false;
	}

	@Override
	public String isExistClientId(String unitId, String clientId) {
		return queryForNotNullString(SQL_FIND_SMS_USE_CONFIG_BY_UNIT_ID_CLIENT_ID,
				new Object[] { unitId, SmsConstant.CONFIG_SIGN_CLIENTID,
						clientId });
	}

	@Override
	public void addUseConfig(SmsUseConfig config) {
		config.setId(getGUID());
		update(SQL_INSERT_SMS_USE_CONFIG, new Object[] { config.getId(),
				config.getUnitId(), config.getSign(), config.getName(),
				config.getIsused(), config.getNowvalue() }, new int[] {
				Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
				Types.INTEGER, Types.VARCHAR });
	}

	@Override
	public void updateUseConfig(SmsUseConfig config) {
		update(SQL_UPDATE_SMS_USE_CONFIG, new Object[] { config.getUnitId(),
				config.getSign(), config.getName(), config.getIsused(),
				config.getNowvalue(), config.getId() }, new int[] { Types.CHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
				Types.VARCHAR });
	}

	public void deleteUseConfig(String unitId) {
		update(SQL_DELETE_SMS_USE_CONFIG, new Object[] { unitId});
	}

}
