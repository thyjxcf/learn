package net.zdsoft.eis.base.payment.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import net.zdsoft.eis.base.payment.dao.PayLogDao;
import net.zdsoft.eis.base.payment.entity.PayLog;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Validators;

import org.springframework.stereotype.Repository;

/**
 * 支付宝回调日志
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午04:21:01 $
 */
@Repository
public class PayLogDaoImpl extends BaseDao<PayLog> implements PayLogDao {
	/**
	 * 插入记录
	 */
	private static final String SQL_INSERT_PAY_LOG = "INSERT INTO sys_pay_log(id,trade_no,log_content,"
			+ "creation_time) " + "VALUES(?,?,?,?)";

	@Override
	public PayLog setField(ResultSet rs) throws SQLException {
		PayLog log = new PayLog();
		log.setId(rs.getString("id"));
		log.setTradeNo(rs.getString("trade_no"));
		log.setLogContent(rs.getString("log_content"));
		log.setCreationTime(rs.getTimestamp("creation_time"));
		return log;
	}

	@Override
	public int addLog(PayLog log) {
		if (Validators.isEmpty(log.getId())) {
			log.setId(createId());
		}
		if (null == log.getCreationTime()) {
			log.setCreationTime(new Date());
		}
		return update(
				SQL_INSERT_PAY_LOG,
				new Object[] { log.getId(), log.getTradeNo(), log.getLogContent(),
						log.getCreationTime() }, new int[] { Types.CHAR, Types.CHAR, Types.VARCHAR,
						Types.TIMESTAMP });

	}

}
