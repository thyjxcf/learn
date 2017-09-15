package net.zdsoft.eis.sms.core;

import java.util.Date;

import net.zdsoft.background2.message.eschool.SmsReceiveMessage;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.EisSmsReportMessage;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.smsc.client.SmscHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmscMsgHandlerImpl implements SmscHandler {
	
	private static final Logger log = LoggerFactory.getLogger(SmscMsgHandlerImpl.class);
	private SmsClientService smsClientService;
	
	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

  

    public void sended(String clientId, String smsId, int pageCount) {
    	log.info("sended:" + clientId + "," + smsId + "," + pageCount);
        EisSmsReportMessage message = new EisSmsReportMessage();
        message.setRecordId(smsId);
        message.setReportResult(SmsConstant.SMS_REPORT_CODE_SUCCESS);
        message.setReportTime(new Date());
        
        smsClientService.saveSmsReport(message);	
   	
    }

    public void sendFailed(String clientId, String smsId, int errorCode,
            String errorMessage) {
        log.info("sendFailed:" + clientId + "," + smsId + ","
                + errorCode + "," + errorMessage);
        
        EisSmsReportMessage message = new EisSmsReportMessage();
        message.setRecordId(smsId);
        message.setReportResult(SmsConstant.SMS_REPORT_FAIL);
        message.setReportTime(new Date());
        message.setReportMessage("Error:"+errorCode+",Detail:"+errorMessage);
        
        smsClientService.saveSmsReport(message);	
    }

    public void batchSended(String clientId, String batchId) {
        log.info("batchSended:" + clientId + "," + batchId);
    }

    public void batchSendFailed(String clientId, String batchId, int errorCode,
            String errorMessage) {
        log.info("batchSendFailed:" + clientId + "," + batchId + ","
                + errorCode + "," + errorMessage);
    }

    /**
     * 短信发送成功返回的消息
     */
    public void reported(String clientId, String smsId) {
    	log.info("reported:" + clientId + "," + smsId);
        EisSmsReportMessage message = new EisSmsReportMessage();
        message.setRecordId(smsId);
        message.setReportResult(SmsConstant.SMS_REPORT_CODE_SUCCESS);
        message.setReportTime(new Date());
        
        smsClientService.saveSmsReport(message);	
    }

    
    public void reportFailed(String clientId, String smsId, int errorCode,
            String errorMessage) {
    	log.error("reportFailed:" + clientId + "," + smsId + ","
                + errorCode + "," + errorMessage);
    	
        EisSmsReportMessage message = new EisSmsReportMessage();
        message.setRecordId(smsId);
        message.setReportResult(SmsConstant.SMS_REPORT_FAIL);
        message.setReportTime(new Date());
        message.setReportMessage("Error:"+errorCode+",Detail:"+errorMessage);
        
        smsClientService.saveSmsReport(message);	
    	
    }

    /**
     * 短信回复
     */
    public void received(String clientId, String spNumberSuffix, String smsId,
            String mobilePhone, String smsContent) {
        log.info("received:" + clientId + "," + spNumberSuffix + ","
                + smsId + "," + mobilePhone + "," + smsContent);
        SmsReceiveMessage message = new SmsReceiveMessage();
        message.setContent(smsContent);
        message.setPhone(mobilePhone);
        message.setMtRecordId(smsId);

        //没有数据表可以存放回复短信,功能暂时未实现
        smsClientService.saveSmsReceive(message);        
    }

    public void clientVary(String sequence, String clientId, int state) {
        log.info("clientVary:" + sequence + "," + clientId + ","
                + state);
    }

    public void auditing(String sequence, String clientId, String serviceCode,
            int state, String reason) {
        log.info("auditing:" + sequence + "," + clientId + ","
                + serviceCode + "," + state + "," + reason);

    }

    public void orderService(String sequence, String clientId,
            String spNumberSuffix, String mobilePhone, String linkId,
            String smsContent) {
        log.info("orderService:" + sequence + "," + clientId + ","
                + spNumberSuffix + "," + mobilePhone + "," + linkId + ","
                + smsContent);

    }

    public void subscribeService(String sequence, String clientId,
            String spNumberSuffix, String mobilePhone, String serviceCode,
            String smsContent) {
        log.info("subscribeService:" + sequence + "," + clientId
                + "," + spNumberSuffix + "," + mobilePhone + "," + serviceCode
                + "," + smsContent);

    }

    public void subscribeServiceRemove(String sequence, String clientId,
            int mode, String spNumberSuffix, String mobilePhone,
            String serviceCode, String smsContent) {
        log.info("subscribeServiceRemove:" + sequence + ","
                + clientId + "," + mode + "," + spNumberSuffix + ","
                + mobilePhone + "," + serviceCode + "," + smsContent);

    }
  
	
	
	
	
	
	
	
	
	
}
