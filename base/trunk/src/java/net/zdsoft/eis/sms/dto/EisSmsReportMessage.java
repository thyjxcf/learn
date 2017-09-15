package net.zdsoft.eis.sms.dto;

import java.io.Serializable;

import net.zdsoft.background2.message.eschool.SmsReportMessage;

import org.apache.commons.lang.StringUtils;








public class EisSmsReportMessage extends SmsReportMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String reportMessage;//保存短信网关返回的消息内容

	public String getReportMessage() {
		return reportMessage;
	}

	public void setReportMessage(String reportMessage) {
		this.reportMessage = reportMessage;
	}

	

}
