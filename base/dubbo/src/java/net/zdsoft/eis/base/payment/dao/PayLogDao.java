package net.zdsoft.eis.base.payment.dao;

import net.zdsoft.eis.base.payment.entity.PayLog;

/**
 * 支付宝回调日志
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午04:22:10 $
 */
public interface PayLogDao {
	/**
	 * 添加方法
	 * 
	 * @param log
	 */
	int addLog(PayLog log);
}
