package net.zdsoft.eis.base.payment.service.impl;

import javax.annotation.Resource;

import net.zdsoft.eis.base.payment.dao.PayLogDao;
import net.zdsoft.eis.base.payment.entity.PayLog;
import net.zdsoft.eis.base.payment.service.PayLogService;

import org.springframework.stereotype.Service;

/**
 * 日志记录-支付宝回调日志
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午04:21:55 $
 */
@Service
public class PayLogServiceImpl implements PayLogService {
	@Resource
	private PayLogDao payLogDao;

	@Override
	public int addLog(PayLog log) {
		return payLogDao.addLog(log);
	}

}
