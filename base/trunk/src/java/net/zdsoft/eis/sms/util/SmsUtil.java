package net.zdsoft.eis.sms.util;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.SendDetailDto;

/**
 * 类说明
 * 
 * @author jiangf
 * @version 创建时间：2011-8-9 上午09:47:11
 */

public class SmsUtil {

	/**
	 * 得到当前 年
	 * 
	 * @return
	 */
	public static int getNowYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到当前 两位的月份
	 * 
	 * @return
	 */
	public static String getNowMonth() {
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		String _month = String.valueOf(month);

		if (_month.length() == 1) {
			_month = "0" + _month;
		}

		return _month;
	}

	/**
	 * 得到当前 两位的具体某天
	 * 
	 * @return
	 */
	public static String getNowDay() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		String _day = String.valueOf(day);

		if (_day.length() == 1) {
			_day = "0" + _day;
		}

		return _day;
	}

	/**
	 * 得到当前 小时
	 * 
	 * @return
	 */
	public static int getNowHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 得到当前 分钟
	 * 
	 * @return
	 */
	public static int getNowMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 得到当前 星期几
	 * 
	 * @return
	 */
	public static int getNowWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 取得8位的当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return String.valueOf(getNowYear()) + getNowMonth() + getNowDay();
	}

	/**
	 * 取得从当前日期开始的未来几天、前几天的日期
	 * 
	 * @param range
	 *            天数，正数表示将来几天、负数表示倒退几天
	 * @return
	 */
	public static String getComingDate(int range) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, range);

		String tempMonth = new Integer(cal.get(Calendar.MONTH) + 1).toString();

		if (tempMonth.length() < 2) {
			tempMonth = "0" + tempMonth;
		}

		String tempDays = new Integer(cal.get(Calendar.DATE)).toString();

		if (tempDays.length() < 2) {
			tempDays = "0" + tempDays;
		}

		return String.valueOf(cal.get(Calendar.YEAR)) + tempMonth + tempDays;
	}

	/**
	 * 得到公文发送同步发送的签了名的短信内容
	 * 
	 * @param msgContent
	 *            没签名的短信内容
	 * @return
	 */
	public static String getSignContentForArchiveSend(String msgContent) {
		return (msgContent == null) ? null : ("请查收公文：" + msgContent.trim());
	}

	/**
	 * 得到通知发送同步发送的签了名的短信内容
	 * 
	 * @param msgContent
	 *            没签名的短信内容
	 * @return
	 */
	public static String getSignContentForNoticeSend(String msgContent) {
		return (msgContent == null) ? null : ("给您发送了一条通知，标题为："
				+ msgContent.trim() + "。请注意查收。");
	}

	/**
	 * 根据短信内容及签名信息得到签了名的短信内容
	 * 
	 * @param msgContent
	 *            没签名的短信内容
	 * @param sign
	 *            签名信息，一般是短信发送人的姓名
	 * @return
	 */
	public static String getSignContentForSmsSend(String msgContent, String sign) {
		if (msgContent == null) {
			return null;
		}
		if (sign == null || sign.trim().length() == 0) {
			return msgContent.trim();
		} else {
			return msgContent.trim() + " " + sign.trim();
		}
	}

	/**
	 * 根据MsgDto获取封装好的完整的短信内容，比如需要已经带上签名等信息
	 * 
	 * @param dto
	 * @return
	 */
	public static String getSignContent(int msmType, String content,
			String userName, String unitName) {
		if (content == null) {
			content = "";
		}
		content = content.trim();
		if (content.length() > 0) {
			if (SmsConstant.MSG_ARCHIVE == msmType) {
				content = getSignContentForArchiveSend(content);
			} else if (SmsConstant.MSG_NOTICE == msmType) {
				content = getSignContentForNoticeSend(content);
			} else {
				String addContent = "(";
				if (userName == null) {
					userName = "";
				}
				if (unitName != null) {
					if (userName != null)
						unitName = "-" + unitName;
				} else {
					unitName = "";
				}

				addContent += userName + unitName + ")";
				content = getSignContentForSmsSend(content, addContent);
			}
		}
		return content;
	}

	/**
	 * 过滤相同的手机号码
	 * 
	 * @param sendDetailDtoList
	 * @return
	 */
	public static List<SendDetailDto> filterSameMobile(
			List<SendDetailDto> sendDetailDtoList) {
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

	/**
	 * 得到一条待发送内容拆分的短信发送条数
	 * 
	 * @param content
	 *            短信待发送内容
	 * @return
	 */
	public static int getItemNumBySmsContent(String content) {
		int item = 1;
		int len = content.length();
		boolean ischinese = isChineseString(content);

		if (ischinese) {
			int pageNum = (len > SmsConstant.MSG_WORDNUM_CH) ? (SmsConstant.MSG_WORDNUM_CH - SmsConstant.MSG_WORDNUM_PAGE)
					: SmsConstant.MSG_WORDNUM_CH;
			item = len / pageNum;

			if ((len % pageNum) > 0) {
				item++;
			}
		} else {
			int pageNum = (len > SmsConstant.MSG_WORDNUM_EN) ? (SmsConstant.MSG_WORDNUM_EN - SmsConstant.MSG_WORDNUM_PAGE)
					: SmsConstant.MSG_WORDNUM_EN;
			item = len / pageNum;

			if ((len % pageNum) > 0) {
				item++;
			}
		}

		return item;
	}

	/**
	 * 判断一个字符串是否含有中文汉字
	 * 
	 * @param str
	 *            字符串
	 * @return 包含？true:false
	 */
	public static boolean isChineseString(String str) {
		boolean flag = false;

		if (str != null) {
			int len = str.length();

			for (int i = 0; i < len; i++) {
				char ch = str.charAt(i);

				if (ch > 255) {
					flag = true;

					return flag;
				}
			}
		}

		return flag;
	}
}
