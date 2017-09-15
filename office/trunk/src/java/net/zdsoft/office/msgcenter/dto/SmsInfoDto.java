package net.zdsoft.office.msgcenter.dto;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.sms.dto.SendDetailDto;

/**
 * @author chens
 * @version 创建时间：2015-4-2 上午9:22:10
 * 
 */
public class SmsInfoDto {
	
	
	private Map<String, Boolean> smsMap;//是否维护手机号码
	private List<SendDetailDto> sendDetailDtos;//需要发送短信的对象list
	
	public Map<String, Boolean> getSmsMap() {
		return smsMap;
	}
	public void setSmsMap(Map<String, Boolean> smsMap) {
		this.smsMap = smsMap;
	}
	public List<SendDetailDto> getSendDetailDtos() {
		return sendDetailDtos;
	}
	public void setSendDetailDtos(List<SendDetailDto> sendDetailDtos) {
		this.sendDetailDtos = sendDetailDtos;
	}
	
}
