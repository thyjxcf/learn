package net.zdsoft.eis.sms.service;

import java.util.List;

import net.zdsoft.background2.message.eschool.SmsReceiveMessage;
import net.zdsoft.background2.message.eschool.SmsReportMessage;
import net.zdsoft.background2.message.eschool.SmsResultMessage;
import net.zdsoft.eis.sms.dto.EisSmsReportMessage;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.ResultDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eschool.smsclient.SmsException;
import net.zdsoft.smsc.client.SmscException;

public interface SmsClientService {

	/**
	 * 是否允许发送短信
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	public boolean isSmsUsed(String unitId) throws Exception;
	
	/**
	 * 删除短信
	 * @param ids
	 */
	public void deleteSms(String... ids) ;
	
	/**
	 * 保存短信内容到数据库（短信批信息、短信明细信息），并且调用短信发送机制发送短信。<br>
	 * 接口调用示例：<br>
	 * 	MsgDto msgDto = new MsgDto();<br>
		List<SendDetailDto> sendDetailDtoList = new ArrayList<SendDetailDto>();<br>
		msgDto.setUnitId("0000000020a966890120acff3d250067");<br>
		msgDto.setUserId("402880F520ACB92E0120ACC610280022");<br>
		msgDto.setUserName("xtsch01");<br>
		msgDto.setDepId("402880F520ACB92E0120ACC4E944001C");<br>
		msgDto.setDep("校长办公室");<br>
		msgDto.setSmsType(String.valueOf(SmsConstant.MSG_ARCHIVE));<br>
		msgDto.setContent("一条测试短信");<br>
		msgDto.setTiming(false);<br>
		msgDto.setSendDate("20090610");<br>
		msgDto.setSendHour(13);<br>
		msgDto.setSendMinutes(20);<br>
		
		SendDetailDto detail = new SendDetailDto();<br>
		detail.setMobile("13967112111");<br>
		detail.setReceiverId("0000000020ad34a40120ad7bbfb40006");<br>
		detail.setAccountId("0000000020AD34A40120AD7BBFA80004");<br>
		detail.setReceiverName("李三");<br>
		detail.setReceiverType(SystemConstant.TEACHER_LOGIN);<br>
		detail.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);//公文目前为按条计费短信<br>
		sendDetailDtoList.add(detail);<br>
		
		smsClientService.saveAndSendSms(msgDto, sendDetailDtoList);<br>
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param msgDto 封装短信发送的主体信息
	 * @param sendDetailDtoList 短信明细信息
	 * @return
	 */
	public ResultDto saveSmsBatch(MsgDto msgDto, List<SendDetailDto> sendDetailDtoList);
	
	/**
	 * 
     * 保存 短信发送结果信息（即到达用户手机的结果报告）
	 */
	public void saveSmsResult(SmsResultMessage result);
	
	/**
     * 保存回执信息（即到达网关的结果报告）
     */
	public void saveSmsReport(EisSmsReportMessage report);
	
	/**
     * 保存回执信息（即到达网关的结果报告）
     */
	public void saveSmsReport(SmsReportMessage report);

	/**
	 * 保存短信上行信息
	 */
	public void saveSmsReceive(SmsReceiveMessage receive);
	
	/**
	 * 查询企业Sp扩展号码
	 * @param clientId
	 * @return
	 * @throws SmsException
	 */
	public String queryExtSpNumber(String clientId) throws SmscException;
	
	 /**
     * 判断本单位的余额是否足够发送当前手机列表的短信
     * 
     * @param unitId
     *            单位unitId
     * @param smsContent
     *            封装好的完整的短信内容，比如需要已经带上签名等信息
     * @param sendDetailDtoList
     *            发送对象列表（这里只需要保证发送对象中的手机号码就行）
     */
    public String getBalanceForSend(String unitId, String smsContent,
            List<SendDetailDto> sendDetailDtoList);
	
}
