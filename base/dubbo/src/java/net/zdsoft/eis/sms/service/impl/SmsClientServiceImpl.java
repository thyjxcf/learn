package net.zdsoft.eis.sms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.background2.message.eschool.SmsReceiveMessage;
import net.zdsoft.background2.message.eschool.SmsReportMessage;
import net.zdsoft.background2.message.eschool.SmsResultMessage;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.core.SmsClientWrapper;
import net.zdsoft.eis.sms.dao.SmsBatchDao;
import net.zdsoft.eis.sms.dao.SmsSendDao;
import net.zdsoft.eis.sms.dto.EisSmsReportMessage;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.ResultDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.entity.SmsBatch;
import net.zdsoft.eis.sms.entity.SmsSend;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.eis.sms.util.SmsUtil;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.smsc.client.SmscException;
import net.zdsoft.smsplatform.client.Account;
import net.zdsoft.smsplatform.client.Receiver;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDPack;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsClientServiceImpl implements SmsClientService {

	private static Logger log = LoggerFactory
			.getLogger(SmsClientServiceImpl.class);
	private static SmsClientWrapper smsClient;

	private SmsBatchDao smsBatchDao;
	private SmsSendDao smsSendDao;
	private UnitService unitService;
	private ServerService serverService;
	private SystemIniService systemIniService;
	private SystemDeployService systemDeployService;
	
	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	private synchronized static SmsClientWrapper getSmsClient() {
		if (smsClient == null) {
			smsClient = (SmsClientWrapper) ContainerManager
					.getComponent("oaMsgClient");
		}

		return smsClient;
	}

	public void setSmsBatchDao(SmsBatchDao smsBatchDao) {
		this.smsBatchDao = smsBatchDao;
	}

	public void setSmsSendDao(SmsSendDao smsSendDao) {
		this.smsSendDao = smsSendDao;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public boolean isSmsUsed(String unitId) throws Exception {
		return getSmsClient().isSmsUsed(unitId);
	}

	public void deleteSms(String... ids) {
		smsBatchDao.deleteByIds(ids);
	}

	public ResultDto saveSmsBatch(MsgDto msgDto,
			List<SendDetailDto> sendDetailDtoList) {
		ResultDto resultDto = new ResultDto();
		resultDto.setOperateResult(true);
		
		String ticket = systemIniService.getValue("SMS.TICKET");
		if(StringUtils.isBlank(ticket)){
			resultDto.setOperateResult(false);
			resultDto.setOperateResultMsg("没有有效的短信服务器ticket");
			return resultDto;
		}
//		Server se = serverService.getServerByServerCode(Server.SERVERCODE_STUSYS);
//		String eisUrl = se.getUrl();
		String eisUrl = systemDeployService.getEisUrl();
		sendDetailDtoList = filterSameMobile(sendDetailDtoList);
		if(CollectionUtils.isEmpty(sendDetailDtoList)){
			resultDto.setOperateResult(false);
			resultDto.setOperateResultMsg("没有有效的接收人信息");
			return resultDto;
		}
		// 1.发送消息
		// 创建帐号信息
		Account account = new Account();
		// 短信服务器地址，只要主域名+contextPath就可以，后面具体目录不需要
		account.setSmsServerUrl(eisUrl);
		// ticket是短信服务器生成，用之前，需要申请
		account.setTicket(ticket);

		// 2.组建短信包
		ZDPack zdPack = new ZDPack();
		// 接收人信息
		Receiver receiver;
		for (SendDetailDto detail : sendDetailDtoList) {
			receiver = new Receiver();
			// 手机号码，不能为空
			receiver.setPhone(detail.getMobile());
			// 接收人所在单位名称，可以为空
			receiver.setUnitName(null);
			// 接收人单位ID，可以为空
			receiver.setUnitId(detail.getUnitId());
			// 接收人名字，可以为空
			receiver.setUsername(detail.getReceiverName());
			// 接收人ID，可以为空
			receiver.setUserId(detail.getReceiverId());
			// 加入列表
			zdPack.addReciever(receiver);
		}

		// 3.短信信息
		// 短信内容，不能为空
		zdPack.setMsg(msgDto.getContent());
		// 定时发送时间，格式为yyyyMMddHHmmss，如果为空，表示马上发送
		if (msgDto.isTiming()) {
			zdPack.setSendTime(msgDto.getSendTime());// 定时发送时间
		}
		
		// 发送者用户ID，必须要写，如果是系统发送，填写默认用户ZDConstant.DEFAULT_USER_ID
		zdPack.setSendUserId(msgDto.getUserId());
		// 发送人所在单位名称，可以空
		zdPack.setSendUnitName(msgDto.getUnitName());
		zdPack.setSendUnitId(msgDto.getUnitId());
		// 发送人姓名，可以为空
		zdPack.setSendUsername(msgDto.getUserName());
		// 采用同步或者异步方式发送，0表示异步，1或者为空表示同步
		// 如果采用异步方式发送，需要再定时调用短信结果接口来获取发送结果。
		zdPack.setIsSync("1");
		try {
			SendResult sr = ZDResponse.post(account, zdPack);
			System.out.println(sr.getCode());
			System.out.println(sr.getDescription());
			System.out.println("短信内容："+msgDto.getContent());
			
			resultDto.setOperateResultMsg(sr.getDescription());
		} catch (IOException e) {
			log.error("发送短信出错:" + e, e);
			resultDto.setOperateResult(false);
			resultDto.setOperateResultMsg("发送短信出错:" + e.getMessage());
			return resultDto;
		}
		
		
		// 保存短信发送批次信息和明细信息
//		20140716 不再使用 by weixh
//		SmsBatch batch = getFixedSmsEntity(msgDto, sendDetailDtoList);
//		smsBatchDao.save(batch);
//
//		// 封装用于发送短信需要的信息
//		SendSmsMessageVO sms = new SendSmsMessageVO();
//		sms.setUnitId(msgDto.getUnitId());
//		sms.setMsg(msgDto.getContent());
//		// if(StringUtils.isBlank(msgDto.getUserName())) {
//		// String loginName = userService.getUser(msgDto.getUserId()).getName();
//		// // 用户登录名
//		// sms.setUserName(loginName); // 发送人的登录名
//		// }
//		sms.setTiming(msgDto.isTiming()); // 是否定时发送
//		if (msgDto.isTiming()) {
//			sms.setSendTime(msgDto.getSendTime());// 定时发送时间
//		}
//		int feeType = unitService.getUnit(msgDto.getUnitId()).getFeeType();// 短信计费方式
//		sms.setFeeType(feeType);
//
//		// 设置具体的手机发送明细信息
//		Set smsDetailSet = batch.getSendSet();
//		Set<String> setOfUserAccountId = new HashSet<String>();
//		for (Iterator it = smsDetailSet.iterator(); it.hasNext();) {
//			SmsSend sendDetail = (SmsSend) it.next();
//			setOfUserAccountId.add(sendDetail.getAccountId());
//			if (SmsConstant.SMS_BUSINESS_PAY == sendDetail.getBusinessType()) {
//				log.debug("======手机号:" + sendDetail.getMobile());
//				sms.addMobile(sendDetail.getMobile(), sendDetail.getId());
//			} else if (SmsConstant.SMS_BUSINESS_ETOH == sendDetail
//					.getBusinessType()) {
//				log.debug("======帐号:" + sendDetail.getAccountId());
//				sms.addAccount(sendDetail.getAccountId(), sendDetail.getId());
//			}
//		}
//
//		// 以下正式发送这个批次的短信
//		try {
//
//			// 发送短信到网关
//			getSmsClient().sendMsg(sms);
//		} catch (Exception e) {
//			log.error("发送短信出错:" + e);
//			resultDto.setOperateResult(false);
//			resultDto.setOperateResultMsg("发送短信出错:" + e.getMessage());
//			return resultDto;
//		}

		resultDto.setOperateResult(true);
//		resultDto.setOperateResultMsg("短信已经成功发送到网关！");
		return resultDto;
	}

	// 设置短信批次表和明细表信息
	// 20140716 不再使用 by weixh
	@SuppressWarnings("unused")
	private SmsBatch getFixedSmsEntity(MsgDto msgDto,
			List<SendDetailDto> sendDetailDtoList) {

		// 以下过滤掉相同的目标手机号码
		List<SendDetailDto> mobileList = new ArrayList<SendDetailDto>();
		List<SendDetailDto> accountList = new ArrayList<SendDetailDto>();
		for (Iterator it = sendDetailDtoList.iterator(); it.hasNext();) {
			SendDetailDto dto = (SendDetailDto) it.next();
			if (SmsConstant.SMS_BUSINESS_PAY == dto.getBusinessType()) {
				mobileList.add(dto);
			} else {
				accountList.add(dto);
			}
		}
		if (CollectionUtils.isNotEmpty(mobileList)) {
			sendDetailDtoList = filterSameMobile(mobileList);
			sendDetailDtoList.addAll(accountList);
		}

		SmsBatch batch = new SmsBatch();
		batch.setSmsType(msgDto.getSmsType());
		batch.setContent(msgDto.getContent());
		batch.setSendDate(msgDto.getSendDate());
		batch.setSendHour(msgDto.getSendHour());
		batch.setSendMinutes(msgDto.getSendMinutes());
		batch.setUserId(msgDto.getUserId());
		batch.setUserName(msgDto.getUserName());
		batch.setUnitId(msgDto.getUnitId());
		batch.setDepId(msgDto.getDepId());
		batch.setDep(msgDto.getDep());

		// 设置该批次下面所有短信的发送状态
		batch.setStatus(SmsConstant.BATCH_STATUS_WAIT);// 默认 向服务器请求信息 代码
		batch.setStatusdesc(SmsConstant.BATCH_STATUS_WAIT_DESC);
		// 暂不实现家校互动短信
		// Map<String, String> accountMobileMap = studentFamilyService
		// .getFamilyMobileMapByAccount(msgDto.getUnitId());
		if (sendDetailDtoList != null) {
			for (Iterator it = sendDetailDtoList.iterator(); it.hasNext();) {
				SendDetailDto dto = (SendDetailDto) it.next();
				SmsSend entity = new SmsSend();
				entity.setId(UUIDUtils.newId());
				entity.setBusinessType(dto.getBusinessType());
				entity.setMobile(dto.getMobile());
				entity.setAccountId(dto.getAccountId());
				// 暂不实现家校互动短信
				// if (SmsConstant.SMS_BUSINESS_ETOH == dto.getBusinessType()) {
				// String familyMobile = accountMobileMap.get(dto
				// .getAccountId());
				// if (StringUtils.isNotBlank(familyMobile))
				// entity.setMobile(familyMobile);
				// }
				entity.setReceiverId(dto.getReceiverId());
				entity.setReceiverName(dto.getReceiverName());
				entity.setReceiverType(dto.getReceiverType());
				if (dto.getItemCount() == null) {
					entity.setItemCount(1);
					;//
				}
				entity.setUnitId(msgDto.getUnitId());
				entity.setStatus(SmsConstant.SMS_STATUS_WAIT);// 设置此时的短信状态为：已发送至网关
				entity.setStatusdesc(SmsConstant.SMS_REPORT_WAIT);
				entity.setUserId(msgDto.getUserId());
				entity.setUserName(msgDto.getUserName());
				entity.setDeptId(msgDto.getDepId());
				entity.setDeptName(msgDto.getDep());
				batch.addSmsDetailEntity(entity);
			}
			Set set = batch.getSendSet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				SmsSend send = (SmsSend) it.next();
				send.setId(null);
			}
		}
		return batch;
	}
	
	// 过滤相同的手机号码
	private List<SendDetailDto> filterSameMobile(
			List<SendDetailDto> sendDetailDtoList) {
		log.debug("=========filterSameMobile=====");
		if (sendDetailDtoList == null) {
			return null;
		}
		Set<String> mobileSet = new HashSet<String>();
		for (int i = sendDetailDtoList.size() - 1; i >= 0; i--) {
			SendDetailDto dto = sendDetailDtoList.get(i);
			if (mobileSet.contains(dto.getMobile())) {
				// log.debug("手机号码重复,过滤发送手机号:" + dto.getMobile());
				sendDetailDtoList.remove(i);
			} else {
				// log.debug("临时的手机号码：" + dto.getMobile());
				mobileSet.add(dto.getMobile());
			}
		}
		return sendDetailDtoList;
	}

	public void saveSmsReport(EisSmsReportMessage report) {
		if (report == null)
			return;

		log.debug("收到手机结果报告:" + "reportResult:" + report.getReportResult()
				+ ",recordId:" + report.getRecordId() + ",reportTime:"
				+ report.getReportTime());

		// 将单条短信发送的最终结果保存在数据库中
		if (StringUtils.isNotBlank(report.getRecordId())) {
			SmsSend sendDetail = smsSendDao
					.getSmsSendById(report.getRecordId());
			SmsBatch smsBatch = null;
			if (sendDetail != null)
				smsBatch = sendDetail.getParent();
			else
				return;

			String status = report.getReportResult();
			if (SmsConstant.SMS_REPORT_CODE_SUCCESS.equals(status)) {
				sendDetail.setStatusdesc(SmsConstant.SMS_REPORT_SUCCESS);
				smsBatch.setStatusdesc(SmsConstant.SMS_REPORT_SUCCESS);
			} else {
				if (StringUtils.isEmpty(report.getReportMessage())) {
					sendDetail.setStatusdesc(report.getReportMessage());
					smsBatch.setStatusdesc(report.getReportMessage());
				} else {
					sendDetail.setStatusdesc(SmsConstant.SMS_REPORT_FAIL);
					smsBatch.setStatusdesc(SmsConstant.SMS_REPORT_FAIL);
				}
			}
			smsBatch.setStatus(status);
			sendDetail.setStatus(status);
			smsSendDao.save(sendDetail);
			smsBatchDao.save(smsBatch);

		}
	}

	public void saveSmsReport(SmsReportMessage report) {
		if (report == null)
			return;

		log.debug("收到手机结果报告:" + "reportResult:" + report.getReportResult()
				+ ",recordId:" + report.getRecordId() + ",reportTime:"
				+ report.getReportTime());

		// 将单条短信发送的最终结果保存在数据库中
		if (StringUtils.isNotBlank(report.getRecordId())) {
			SmsSend sendDetail = smsSendDao
					.getSmsSendById(report.getRecordId());
			SmsBatch smsBatch = null;
			if (sendDetail != null)
				smsBatch = sendDetail.getParent();
			else
				return;

			String status = report.getReportResult();
			if (SmsConstant.SMS_REPORT_CODE_SUCCESS.equals(status)) {
				sendDetail.setStatusdesc(SmsConstant.SMS_REPORT_SUCCESS);
				smsBatch.setStatusdesc(SmsConstant.SMS_REPORT_SUCCESS);
			} else {
				sendDetail.setStatusdesc(SmsConstant.SMS_REPORT_FAIL);
				smsBatch.setStatusdesc(SmsConstant.SMS_REPORT_FAIL);
			}
			smsBatch.setStatus(status);
			sendDetail.setStatus(status);
			smsSendDao.save(sendDetail);
			smsBatchDao.save(smsBatch);
		}
	}

	public void saveSmsResult(SmsResultMessage result) {
		if (result == null)
			return;

		log.debug("收到短信到达网关结果报告:" + "sendResult:" + result.getSendResult()
				+ ",recordId:" + result.getRecordId() + ",gatewayTime:"
				+ result.getGatewayTime() + ",pageCount:"
				+ result.getPageCount() + ",balance:" + result.getBalance());

		// 将单条短信发送的网关结果保存在数据库中
		if (StringUtils.isNotBlank(result.getRecordId())) {
			SmsSend sendDetail = smsSendDao
					.getSmsSendById(result.getRecordId());
			sendDetail.setStatus(String.valueOf(result.getSendResult()));
			Map<Integer, String> smsResultMsgMap = SmsConstant.smsResultMsgMap;
			sendDetail
					.setStatusdesc(smsResultMsgMap.get(result.getSendResult()));
			sendDetail.setItemCount(result.getPageCount());
			smsSendDao.save(sendDetail);
		}
	}

	public void saveSmsReceive(SmsReceiveMessage receive) {
		// 收到上行短信:暂不实现，因base库短信留言表未上移

	}

	public String queryExtSpNumber(String clientId) throws SmscException {
		return getSmsClient().queryExtSpNumber(clientId);
	}

	public String getBalanceForSend(String unitId, String smsContent,
			List<SendDetailDto> sendDetailDtoList) {
		String message = null;
		if (smsContent == null || smsContent.trim().length() == 0
				|| sendDetailDtoList == null || sendDetailDtoList.size() == 0) {
			message = "因短信内容为空或者发送对象为空，" + "不需要发送短信，所以余额满足";
			return message;
		}
		int _num = SmsUtil.getItemNumBySmsContent(smsContent);
		List _list = SmsUtil.filterSameMobile(sendDetailDtoList);

		double balanceValue = -1;
		try {
			balanceValue = getSmsClient().queryBalance(unitId);
		} catch (Exception e) {
			return e.getMessage();
		}

		if (balanceValue >= 0) {
			if (Math.round(balanceValue * 100) >= Math.round(_list.size()
					* _num * 0.1d * 100)) {
			} else {
				message = "当前余额为" + balanceValue + "元，已不够发送" + _list.size()
						+ "个手机，共计" + _list.size() * _num + "条短信";
			}
		} else {
			message = "余额不足";
		}
		return message;
	}

}
