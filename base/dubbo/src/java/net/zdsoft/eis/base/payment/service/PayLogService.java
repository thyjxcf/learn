package net.zdsoft.eis.base.payment.service;

import net.zdsoft.eis.base.payment.entity.PayLog;

/**
 * 支付宝回调日志
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午04:22:02 $
 */
public interface PayLogService {
	/**
	 * 插入日志记录
	 * 
	 * @param log
	 * @return
	 */
	int addLog(PayLog log);

}
