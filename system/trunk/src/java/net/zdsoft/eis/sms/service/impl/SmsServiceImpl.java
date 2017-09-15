package net.zdsoft.eis.sms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dao.SmsDao;
import net.zdsoft.eis.sms.entity.SmsQuery;
import net.zdsoft.eis.sms.entity.SmsStat;
import net.zdsoft.eis.sms.service.SmsService;
import net.zdsoft.keel.util.Pagination;

/**
 * 类说明
 * 
 * @author jiangf
 * @version 创建时间：2011-8-8 下午07:28:36
 */

public class SmsServiceImpl implements SmsService {

	private SmsDao smsDao;

	private DeptService deptService;

	private UserService userService;

	public void setSmsDao(SmsDao smsDao) {
		this.smsDao = smsDao;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<SmsQuery> getSmsSendList(String unitId, String startDate,
			String endDate, Pagination page) {
		List<SmsQuery> list = smsDao.getSmsSendList(unitId, startDate,
				endDate, page);
		for (SmsQuery smsQuery : list) {
			smsQuery.setSmsType(SmsConstant.getSmsTypeMap().get(
					smsQuery.getSmsType()));
		}
		return list;
	}

	public List<SmsStat> getDepGroupCount(String startDate, String endDate,
			String unitId) {
		// 取到所有的部门列表
		List<Dept> deptList = deptService.getDepts(unitId);
		// 返回的发送部门的发送情况信息
		List msgStatList = smsDao.getGroupCountByDep(startDate, endDate,
				unitId);
		// 返回的StatDtoList
		List<SmsStat> smsStatList = new ArrayList<SmsStat>();

		// 第一级部门统计列表
		List<SmsStat> topStatDtoList = new ArrayList<SmsStat>();

		// 部门ID找不到时的处理的相关成功、失败总数

		// 部门It,转换成通用的typeIt,统计其它部门的信息
		List<String> deptIdList = new ArrayList<String>();
		for (Dept dept : deptList) {
			deptIdList.add(dept.getId());
		}

		SmsStat otherStat = getOtherStat("其它部门", deptIdList, msgStatList,
				"dep_id");

		// 部门不存在的处理结束
		String dep_prefix = "";
		for (Dept dept : deptList) {// 按部门依次找出统计中的值
			SmsStat smsStat = new SmsStat();
			dep_prefix = "";
			smsStat.setDeptName(dep_prefix + dept.getDeptname());
			smsStat.setDeptId(dept.getId());
			SmsStat depStat = getOneDepMsgItemsIncludeSub(dept.getId(),
					deptList, msgStatList);
			smsStat.setSucessItemsCount(depStat.getSucessItemsCount());
			smsStat.setFailItemsCount(depStat.getFailItemsCount());
			smsStat.setHtmlSucessItemsCount(dep_prefix
					+ depStat.getSucessItemsCount());
			smsStat.setHtmlFailItemsCount(dep_prefix
					+ depStat.getFailItemsCount());
			smsStat.setHaveChild(depStat.isHaveChild());

			smsStatList.add(smsStat);
			if (dept.getParentid() == null
					|| Dept.TOP_GROUP_GUID.equals(dept.getParentid())) {
				topStatDtoList.add(smsStat);
			}
		}
		topStatDtoList.add(otherStat);
		SmsStat totalSmsStat = _getStatDtoSum(topStatDtoList);
		smsStatList.add(otherStat);
		smsStatList.add(totalSmsStat);
		return smsStatList;
	}

	private List<String> _getOneDepidIncludeSub(List<String> depIdList,
			String depId, List<Dept> deptList) {
		// 找出所有的depId下面的子结点
		depIdList.add(depId);
		for (Dept dept : deptList) {
			if (dept.getParentid().equals(depId)) {
				_getOneDepidIncludeSub(depIdList, dept.getId(), deptList);
			}
		}
		return depIdList;
	}

	/**
	 * 取得指定部门包含其子部门的所有短信信息
	 * 
	 * @param depId
	 *            部门id
	 * @param depList
	 *            部门列表
	 * @param statDtoList
	 *            按直接部门统计的列表
	 * @return
	 */
	private SmsStat getOneDepMsgItemsIncludeSub(String depId,
			List<Dept> deptList, List smsStatList) {
		List<String> depIdList = new ArrayList<String>();
		_getOneDepidIncludeSub(depIdList, depId, deptList);
		Iterator statMsgIt;
		long sucessItems = 0, failItems = 0;
		for (String tmp_depId : depIdList) {
			statMsgIt = smsStatList.iterator();
			while (statMsgIt.hasNext()) {
				Map map = (Map) statMsgIt.next();
				String _deptid = map.get("dep_id") == null ? null : map.get(
						"dep_id").toString();
				if (tmp_depId.equals(_deptid)) {
					sucessItems = sucessItems
							+ new Long(map.get("sucesscount").toString())
									.longValue();
					failItems = failItems
							+ new Long(map.get("failcount").toString())
									.longValue();
				}
			}
		}
		boolean haveChild = true;
		if (depIdList.size() < 2) {
			haveChild = false;
		}

		SmsStat depStatDto = new SmsStat();
		depStatDto.setId(depId);
		depStatDto.setSucessItemsCount(new Long(sucessItems).toString());
		depStatDto.setFailItemsCount(new Long(failItems).toString());
		depStatDto.setHaveChild(haveChild);
		return depStatDto;
	}

	/**
	 * 得到非统计类别的其它项信息
	 * 
	 * @param groupName
	 * @param typeList
	 * @param msgStatList
	 * @return
	 */
	private SmsStat getOtherStat(String deptName, List<String> deptIdList,
			List msgStatList, String key) {
		SmsStat otherStat = new SmsStat();
		otherStat.setDeptName(deptName);
		int sucesscount = 0;
		int failcount = 0;
		Iterator msgStatIt = msgStatList.iterator();
		Map msgStatMap;
		while (msgStatIt.hasNext()) {// 依次查找对应的depid统计的相应部门是否存在
			boolean isOther = true; // 是否是其它分类信息
			msgStatMap = (Map) msgStatIt.next();
			for (String deptId : deptIdList) {
				String tempId = msgStatMap.get(key) == null ? null : msgStatMap
						.get(key).toString();
				if (deptId.equals(tempId)) {
					isOther = false;
					break;
				}
			}
			if (isOther) {
				sucesscount = sucesscount
						+ new Integer(msgStatMap.get("sucesscount").toString())
								.intValue();
				failcount = failcount
						+ new Integer(msgStatMap.get("failcount").toString())
								.intValue();
			}
		}
		otherStat.setSucessItemsCount(new Integer(sucesscount).toString());
		otherStat.setFailItemsCount(new Integer(failcount).toString());
		return otherStat;
	}

	public List<SmsStat> getSmsTypeGroupCount(String startDate, String endDate,
			String unitId) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> typeList = new ArrayList<String>();
		List<String> smsTypeList = SmsConstant.getSmsTypeList();
		Map<String, String> smsTypeMap = SmsConstant.getSmsTypeMap();
		// 得到的短语的发送情况信息
		List msgStatList = smsDao.getGroupCountBySmsType(startDate,
				endDate, unitId);

		// 返回的短信统结果
		List<SmsStat> statDtoList = new ArrayList<SmsStat>();
		Iterator msgStatIt;
		Map msgStatMap;
		boolean isFound = false;
		// msgTypeListIt = msgTypeList.iterator();
		// 处理被删除的代码的其它统计值
		SmsStat otherStat = getOtherStat("其它类型", typeList, msgStatList,
				"sms_type");

		// 处理存在代码的内容
		for (String smsType : smsTypeList) {// 按部门依次找出统计中的值
			SmsStat msgStatDto = new SmsStat();
			msgStatDto.setDeptId(smsType);
			msgStatDto.setDeptName(smsTypeMap.get(smsType));
			msgStatIt = msgStatList.iterator();
			while (msgStatIt.hasNext()) {
				msgStatMap = (Map) msgStatIt.next();// 0:id 1:成功的条数
				// 2:失败的条数
				if (msgStatMap.get("sms_type").equals(smsType)) {
					msgStatDto.setSucessItemsCount(msgStatMap
							.get("sucesscount").toString());
					msgStatDto.setFailItemsCount(msgStatMap.get("failcount")
							.toString());
					isFound = true;
					break;
				}
			}
			if (!isFound) {// 如果在发送表中没有该单位的数据，则成功与失败都为0
				msgStatDto.setSucessItemsCount("0");
				msgStatDto.setFailItemsCount("0");
			}
			// 如果统计数据返回的值为 null 为空串，处理成0
			if (msgStatDto.getSucessItemsCount() == null
					|| msgStatDto.getSucessItemsCount().equals("")) {
				msgStatDto.setSucessItemsCount("0");
			}
			if (msgStatDto.getFailItemsCount() == null
					|| msgStatDto.getFailItemsCount().equals("")) {
				msgStatDto.setSucessItemsCount("0");
			}
			statDtoList.add(msgStatDto);
			isFound = false;
		}
		statDtoList.add(otherStat);
		statDtoList.add(_getStatDtoSum(statDtoList));
		return statDtoList;
	}

	public List<SmsStat> getUsersGroupCount(String startDate, String endDate,
			String unitId) {
		// 得到的短语的发送情况信息
		List msgStatList = smsDao.getGroupCountByUser(startDate, endDate,
				unitId);
		Iterator msgStatIt = msgStatList.iterator();
		Map msgStatMap;
		SmsStat otherUserStatDto = new SmsStat();
		// 返回的短信统结果
		List<SmsStat> statDtoList = new ArrayList<SmsStat>();
		int otherIndex = -1;
		int i = -1;

		Map userMap = userService.getUserMap(unitId);
		while (msgStatIt.hasNext()) {
			i++;
			msgStatMap = (Map) msgStatIt.next();// 0:id 1:成功的条数 2:失败的条数
			SmsStat msgStatDto = new SmsStat();
			User user = (User) userMap.get((String) msgStatMap.get("user_id"));

			if (user == null) {// 发送人不存在时帐户，一般由任务定制后，帐户删除后找到到真实姓名
				otherUserStatDto.setDeptName("其它帐户");
				otherUserStatDto.setSucessItemsCount(msgStatMap.get(
						"sucesscount").toString());
				otherUserStatDto.setFailItemsCount(msgStatMap.get("failcount")
						.toString());
				otherIndex = i;
			} else {
				msgStatDto.setDeptName(user.getRealname());
				msgStatDto.setDeptId(msgStatMap.get("user_id").toString());
				msgStatDto.setSucessItemsCount(msgStatMap.get("sucesscount")
						.toString());
				msgStatDto.setFailItemsCount(msgStatMap.get("failcount")
						.toString());
				statDtoList.add(msgStatDto);
			}
		}
		if (otherIndex != -1) {// 加上没有用户名短信
			statDtoList.add(otherUserStatDto);
		}
		statDtoList.add(_getStatDtoSum(statDtoList));
		return statDtoList;
	}

	/**
	 * 生成合计行
	 * 
	 * @param smsStatList
	 * @return
	 */
	private SmsStat _getStatDtoSum(List<SmsStat> smsStatList) {
		SmsStat totalSmsStat = new SmsStat();
		totalSmsStat.setDeptId("");
		totalSmsStat.setDeptName("合计");
		int sucesscount = 0;
		int failcount = 0;
		for (SmsStat smStat : smsStatList) {
			if (smStat != null) {
				if (smStat.getFailItemsCount() == null) {
					smStat.setFailItemsCount("0");
				}

				if (smStat.getSucessItemsCount() == null) {
					smStat.setSucessItemsCount("0");
				}
				sucesscount = sucesscount
						+ new Integer(smStat.getSucessItemsCount()).intValue();
				failcount = failcount
						+ new Integer(smStat.getFailItemsCount()).intValue();
			}
		}
		totalSmsStat.setSucessItemsCount(new Integer(sucesscount).toString());
		totalSmsStat.setFailItemsCount(new Integer(failcount).toString());

		return totalSmsStat;
	}

	public List<SmsStat> getPersonalSmsTypeCount(String startDate,
			String endDate, String userGuid) {
		List<String> smsTypeList = SmsConstant.getSmsTypeList();
		Map<String, String> smsTypeMap = SmsConstant.getSmsTypeMap();
		// 得到的短语的发送情况信息
		List msgStatList = smsDao.getPersonalSmsTypeCount(startDate,
				endDate, userGuid);

		// 返回的短信统结果
		List<SmsStat> statDtoList = new ArrayList<SmsStat>();
		Iterator msgStatIt;
		Map msgStatMap;
		boolean isFound = false;
		// 处理被删除的代码的其它统计值
		SmsStat otherStatDto = getOtherStat("其它类型", smsTypeList, msgStatList,
				"sms_type");
		// 处理存在代码的内容
		for (String smsType : smsTypeList) {

			SmsStat msgStatDto = new SmsStat();
			msgStatDto.setDeptId(smsType);
			msgStatDto.setDeptName(smsTypeMap.get(smsType));
			msgStatIt = msgStatList.iterator();
			while (msgStatIt.hasNext()) {
				msgStatMap = (Map) msgStatIt.next();// 0:id 1:成功的条数
				// 2:失败的条数
				if (msgStatMap.get("sms_type").equals(smsType)) {
					msgStatDto.setSucessItemsCount(msgStatMap
							.get("sucesscount").toString());
					msgStatDto.setFailItemsCount(msgStatMap.get("failcount")
							.toString());
					isFound = true;
					break;
				}
			}
			if (!isFound) {// 如果在发送表中没有该单位的数据，则成功与失败都为0
				msgStatDto.setSucessItemsCount("0");
				msgStatDto.setFailItemsCount("0");
			}
			// 如果统计数据返回的值为 null 为空串，处理成0
			if (msgStatDto.getSucessItemsCount() == null
					|| msgStatDto.getSucessItemsCount().equals("")) {
				msgStatDto.setSucessItemsCount("0");
			}
			if (msgStatDto.getFailItemsCount() == null
					|| msgStatDto.getFailItemsCount().equals("")) {
				msgStatDto.setSucessItemsCount("0");
			}
			statDtoList.add(msgStatDto);
			isFound = false;
		}
		statDtoList.add(otherStatDto);
		statDtoList.add(_getStatDtoSum(statDtoList));
		return statDtoList;
	}
}
