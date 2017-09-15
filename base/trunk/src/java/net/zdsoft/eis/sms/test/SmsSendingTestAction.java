package net.zdsoft.eis.sms.test;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.service.SmsClientService;

public class SmsSendingTestAction extends BaseAction {
    private SmsClientService smsClientService;
	private static final long serialVersionUID = 1L;

	public void setSmsClientService(SmsClientService smsClientService) {
        this.smsClientService = smsClientService;
    }

    @Override
	public String execute() throws Exception {
		System.out.println(">>>短信测试开始。。。");
		
		MsgDto msgDto = new MsgDto();
		List<SendDetailDto> sendDetailDtoList = new ArrayList<SendDetailDto>();
		msgDto.setUnitId("0000000020a966890120acff3d250067");
		msgDto.setUserId("402880F520ACB92E0120ACC610280022");
		msgDto.setUserName("xtsch01");
		msgDto.setDepId("402880F520ACB92E0120ACC4E944001C");
		msgDto.setDep("校长办公室");
		msgDto.setSmsType(String.valueOf(SmsConstant.MSG_ARCHIVE));
		msgDto.setContent("一条测试短信");
		msgDto.setTiming(false);
		msgDto.setSendDate("20090610");
		msgDto.setSendHour(13);
		msgDto.setSendMinutes(20);
		
		SendDetailDto detail = new SendDetailDto();
		detail.setMobile("13967112926");
		detail.setReceiverId("0000000020ad34a40120ad7bbfb40006");
		detail.setAccountId("0000000020AD34A40120AD7BBFA80004");
		detail.setReceiverName("李三");
		detail.setReceiverType(User.TEACHER_LOGIN);
		detail.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);//公文目前为按条计费短信
		sendDetailDtoList.add(detail);
		
		smsClientService.saveSmsBatch(msgDto, sendDetailDtoList);
		System.out.println(">>>短信测试结束，正发送到网关。。。");
		
		return SUCCESS;
	}
	

}
