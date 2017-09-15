package net.zdsoft.eis.sms.core;

import net.zdsoft.background2.message.eschool.ControlResponseMessage;
import net.zdsoft.background2.message.eschool.SmsReceiveMessage;
import net.zdsoft.background2.message.eschool.SmsReportMessage;
import net.zdsoft.background2.message.eschool.SmsResultMessage;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.eschool.smsclient.SmsHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsMsgHandlerImpl implements SmsHandler {
	
	private static final Logger log = LoggerFactory.getLogger(SmsMsgHandlerImpl.class);
	private SmsClientService smsClientService;
	
	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

	/**
     * 收到回执信息（即到达网关的结果报告）
     */
    public void reported(SmsReportMessage message) {
    	
    	smsClientService.saveSmsReport(message);	
    }
    
    /**
     * 收到短信发送结果信息（即到达用户手机的结果报告）
     */
    public void sended(SmsResultMessage message) {

    	smsClientService.saveSmsResult(message);
    }
    

    public void received(SmsReceiveMessage message) {

    	smsClientService.saveSmsReceive(message);
    }

    public void saveUpResult(int result, int balance) {
    	log.debug("收到充值响应：" + result + ",余额：" + balance);

    }

	public void contrlMsgResult(ControlResponseMessage controlresponsemessage) {
		// TODO Auto-generated method stub
		
	}	
	
  
	
	
	
	
	
	
	
	
	
}
