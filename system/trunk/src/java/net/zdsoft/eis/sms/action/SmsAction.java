package net.zdsoft.eis.sms.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.entity.SmsQuery;
import net.zdsoft.eis.sms.entity.SmsStat;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.eis.sms.service.SmsService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.DateUtils;

import org.apache.commons.lang.StringUtils;

/**
 * 短信统计Action
 * 
 * @author jiangf
 * @version 创建时间：2011-8-8 下午07:13:56
 */

public class SmsAction extends PageAction {

	private static final long serialVersionUID = -7039545776350124477L;

	private String startDate;

	private String endDate;

	private List<SmsStat> statList = new ArrayList<SmsStat>();

	private List<SmsQuery> queryList = new ArrayList<SmsQuery>();

	private int tabIndex = 1;

	private String message;

	private String labelName = "短信统计";

	private SmsClientService smsClientService;

	private SmsService smsService;

	private boolean isStatisticUnitSms = false;

	public String smsStat() throws Exception {
		if (getLoginInfo().getAllOperSet().contains(
				SmsConstant.STATISTIC_UNIT_SMS))
			isStatisticUnitSms = true;
		// 先检查该单位有没有启用短信功能,没有就转向提示页面
		if (!smsClientService.isSmsUsed(getUnitId())) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("短信没有开通,不能查询");
			return PROMPTMSG;
		}
		if (!isStatisticUnitSms && tabIndex == 1) {
			tabIndex = 3;
		}
		if (StringUtils.isBlank(startDate)) {
			startDate = DateUtils.currentDate2StringByDay();
			return SUCCESS;
		}
		if (StringUtils.isBlank(endDate)) {
			endDate = DateUtils.currentDate2StringByDay();
		}

		java.util.Date _beginDate = DateUtils.string2Date(startDate);
		java.util.Date _endDate = DateUtils.string2Date(endDate);
		if (DateUtils.compareIgnoreSecond(_beginDate, _endDate) > 0) {
			message = "开始时间必须小于结束时间";
			return SUCCESS;
		}

		String _strBeginDate = DateUtils.date2String(_beginDate, "yyyyMMdd");
		String _strEndDate = DateUtils.date2String(_endDate, "yyyyMMdd");
		switch (tabIndex) {
		case 1:
			deptGroupStat(_strBeginDate, _strEndDate);
			break;
		case 2:
			userSmsStat(_strBeginDate, _strEndDate);
			break;
		case 3:
			smsTypeStat(_strBeginDate, _strEndDate);
			break;
		default:
			deptGroupStat(_strBeginDate, _strEndDate);
			tabIndex = 1;
		}
		return SUCCESS;
	}

	public String smsManage() throws Exception {
		// 先检查该单位有没有启用短信功能,没有就转向提示页面
		if (!smsClientService.isSmsUsed(getUnitId())) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("短信没有开通,不能查询");
			return PROMPTMSG;
		}
		if (StringUtils.isBlank(startDate)) {
			startDate = DateUtils.currentDate2StringByDay();
			return SUCCESS;
		}
		if (StringUtils.isBlank(endDate)) {
			endDate = DateUtils.currentDate2StringByDay();
		}

		java.util.Date _beginDate = DateUtils.string2Date(startDate);
		java.util.Date _endDate = DateUtils.string2Date(endDate);
		if (DateUtils.compareIgnoreSecond(_beginDate, _endDate) > 0) {
			message = "开始时间必须小于结束时间";
			return SUCCESS;
		}

		String _strBeginDate = DateUtils.date2String(_beginDate, "yyyyMMdd");
		String _strEndDate = DateUtils.date2String(_endDate, "yyyyMMdd");
		queryList = smsService.getSmsSendList(getUnitId(), _strBeginDate,
				_strEndDate, getPage());
		return SUCCESS;
	}

	public Reply remoteRemoveSms(String idss) {
		Reply reply = new Reply();
		try {
			smsClientService.deleteSms(idss.split("&"));
			reply.addActionMessage("删除成功");
		} catch (Exception e) {
			reply.addActionError("删除失败" + e.getMessage());
		}
		return reply;
	}

	/**
	 * 判断用户是否有部门短信统计+帐户短信统计+单位范围内的分类短信统计的权限
	 */
	public boolean getIsStatisticUnitSms() {
		return isStatisticUnitSms;
	}

	/**
	 * 部门短信统计
	 */
	public void deptGroupStat(String strStartDate, String strEndDate) {
		statList = smsService.getDepGroupCount(strStartDate, strEndDate,
				getUnitId());
	}

	/**
	 * 帐户短信统计
	 */
	public void userSmsStat(String strStartDate, String strEndDate) {
		statList = smsService.getUsersGroupCount(strStartDate, strEndDate,
				getUnitId());
	}

	/**
	 * 分类短信统计
	 */
	public void smsTypeStat(String strStartDate, String strEndDate) {
		if (isStatisticUnitSms) {
			statList = smsService.getSmsTypeGroupCount(strStartDate,
					strEndDate, getUnitId());
		} else {
			statList = smsService.getPersonalSmsTypeCount(strStartDate,
					strEndDate, getLoginInfo().getUser().getId());
		}
	}

	public String getMessage() {
		return message;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public List<SmsStat> getStatList() {
		return statList;
	}

	public List<SmsQuery> getQueryList() {
		return queryList;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}
}
