package net.zdsoft.eis.base.data.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.entity.CountOnlineTime;
import net.zdsoft.eis.base.data.service.BaseDeptService;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.CountOnlineTimeService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;

import org.apache.commons.lang.StringUtils;

public class CountOnlineTimeAction extends PageAction {

	/**
	 * 在线时长统计 wangmq
	 */
	private static final long serialVersionUID = 1L;

	private List<CountOnlineTime> countOnlineTimeList;

	private CountOnlineTimeService countOnlineTimeService;

	private BaseUserService baseUserService;

	private BaseUnitService baseUnitService;

	private BaseDeptService baseDeptService;

	private String beginTime;

	private String endTime;

	private RegionService regionService;

	private Map<String, String> shiRegionMap = new HashMap<String, String>();

	private String shiId;

	private Map<String, String> quxianMap = new HashMap<String, String>();

	private String quxianId;

	private String unitName;

	private BaseTeacherService baseTeacherService;

	private String queryType;

	private int regionLevel;

	private String nowTime;

	private int unitType;

	public String execute() {
		/*
		 * if (getLoginInfo().getUser().getType()!=User.TYPE_TOPADMIN) {
		 * promptMessageDto.setErrorMessage("非顶级账号没有权限查看！"); return PROMPTMSG; }
		 */

		// 获取登录账户单位的行政等级
		String schoolUnitId = getLoginInfo().getUser().getUnitid();
		BaseUnit bu = baseUnitService.getBaseUnit(schoolUnitId);
		regionLevel = bu.getRegionlevel();
		String regionCode = bu.getRegion();
		unitType = bu.getUnittype();

		// System.out.println(regionLevel);
		// 获取市区县下拉列表
		Map<String, String> regionMap = regionService.getRegionMap();
		if (regionLevel == 2) {
			for (Entry<String, String> en : regionMap.entrySet()) {
				if (en.getKey().length() == 4
						&& en.getKey().startsWith(regionCode.substring(0, 2))) {
					shiRegionMap.put(en.getKey(), en.getValue());
				}
			}
		} else if (regionLevel == 3) {
			for (Entry<String, String> en : regionMap.entrySet()) {
				if (en.getKey().length() == 6
						&& en.getKey().startsWith(regionCode.substring(0, 4))) {
					quxianMap.put(en.getKey(), en.getValue());
				}
			}
		} else {
			quxianId = regionCode;
		}

		if (StringUtils.isNotBlank(shiId)) {
			getQuxian();
		}
		if (StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)) {
			return SUCCESS;
		}
		
		// 根据区县emcode和单位名称取单位数组
		Set<String> unitIdSet = new HashSet<String>();
		/*
		 * if(StringUtils.isNotBlank(unitName)){ List<Unit> unitList =
		 * baseUnitService.getUnitsBySearchName(unitName,null); for(Unit u :
		 * unitList){ unitIdSet.add(u.getId()); } }else{ List<Unit> unitList =
		 * baseUnitService.getUnitsByRegion(quxianId); for(Unit u : unitList){
		 * unitIdSet.add(u.getId()); } }
		 */
		Set<String> unitIdSet1 = new HashSet<String>();
		Set<String> unitIdSet2 = new HashSet<String>();
		Set<String> unitIdSet12 = new HashSet<String>();
		if (StringUtils.isNotBlank(unitName)) {
			List<Unit> unitList = baseUnitService.getUnitsBySearchName(
					unitName, null);
			for (Unit u : unitList) {
				unitIdSet1.add(u.getId());
			}
		}
		if (StringUtils.isNotBlank(quxianId)) {
			List<Unit> unitList = baseUnitService.getUnitsByRegion(quxianId);
			for (Unit u : unitList) {
				unitIdSet2.add(u.getId());
			}
		}

		if (unitType != 1 && unitType != 2) {
			unitIdSet.add(schoolUnitId);
		} else {

			if (StringUtils.isNotBlank(unitName)
					&& StringUtils.isBlank(quxianId)) {
				List<Unit> unitList;
				if (regionLevel == 2) {
					unitList = baseUnitService
							.getUnitsByRegion(regionCode.substring(0, 2) + "%");
				} else if (regionLevel == 3) {
					unitList = baseUnitService
							.getUnitsByRegion(regionCode.substring(0, 4) + "%");
				} else {
					unitList = baseUnitService
							.getUnitsByRegion(regionCode);
				}
				for (Unit u : unitList) {
					unitIdSet12.add(u.getId());
				}
				unitIdSet.addAll(unitIdSet12);
				unitIdSet.retainAll(unitIdSet1);
			} else if (StringUtils.isBlank(unitName)
					&& StringUtils.isNotBlank(quxianId)) {
				unitIdSet = unitIdSet2;
			} else {
				unitIdSet.addAll(unitIdSet1);
				unitIdSet.retainAll(unitIdSet2);
			}

		}
		try {
			Date begin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(beginTime);
			Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(endTime);
			List<CountOnlineTime> countTimeList = countOnlineTimeService
					.getCountOnlineTimeListByTime(beginTime, endTime,
							unitIdSet.toArray(new String[0]));
			countOnlineTimeList = countOnlineTimeService
					.getCountOnlineTimeList(beginTime, endTime,
							unitIdSet.toArray(new String[0]));
			List<String> arrList = new ArrayList<String>();
			List<String> arrList2 = new ArrayList<String>();// 放unitId
			List<String> arrList3 = new ArrayList<String>();// 放部门id
			List<String> arrList4 = new ArrayList<String>();// 放ownerId
			for (CountOnlineTime item : countOnlineTimeList) {
				int countTime = 0;
				arrList.add(item.getUserId());
				for (CountOnlineTime itemTime : countTimeList) {
					if (item.getUserId().equals(itemTime.getUserId())) {
						if (itemTime.getLoginTime().getTime() >= begin
								.getTime()
								&& itemTime.getLoginTime().getTime() <= end
										.getTime()
								&& itemTime.getLogoutTime() == null) {
							if (end.getTime() > new Date().getTime()) {
								countTime = (int) (countTime + (new Date()
										.getTime() - itemTime.getLoginTime()
										.getTime()) / 1000);
							} else {
								countTime = (int) (countTime + (end.getTime() - itemTime
										.getLoginTime().getTime()) / 1000);
							}
							// countTime = (int)
							// (countTime+(end.getTime()-itemTime.getLoginTime().getTime())/1000);
						} else if (itemTime.getLoginTime().getTime() <= begin
								.getTime()
								&& itemTime.getLogoutTime().getTime() >= begin
										.getTime()
								&& itemTime.getLogoutTime().getTime() <= end
										.getTime()) {
							countTime = (int) (countTime + (itemTime
									.getLogoutTime().getTime() - begin
									.getTime()) / 1000);
						} else if (itemTime.getLoginTime().getTime() >= begin
								.getTime()
								&& itemTime.getLoginTime().getTime() <= end
										.getTime()
								&& itemTime.getLogoutTime().getTime() >= begin
										.getTime()
								&& itemTime.getLogoutTime().getTime() <= end
										.getTime()) {
							countTime = (int) (countTime + (itemTime
									.getLogoutTime().getTime() - itemTime
									.getLoginTime().getTime()) / 1000);
						} else if (itemTime.getLoginTime().getTime() >= begin
								.getTime()
								&& itemTime.getLoginTime().getTime() <= end
										.getTime()
								&& itemTime.getLogoutTime().getTime() >= end
										.getTime()) {
							countTime = (int) (countTime + (end.getTime() - itemTime
									.getLoginTime().getTime()) / 1000);
						} else if (itemTime.getLoginTime().getTime() <= begin
								.getTime()
								&& itemTime.getLogoutTime().getTime() >= end
										.getTime()) {
							countTime = (int) (countTime + (end.getTime() - begin
									.getTime()) / 1000);
						}
					}
					item.setOnlineTime(countTime);
				}
			}
			Map<String, User> userMap = baseUserService
					.getUserWithDelMap(arrList.toArray(new String[0]));
			for (Entry<String,User> en : userMap.entrySet()) {
				arrList2.add(en.getValue().getUnitid());
				arrList3.add(en.getValue().getDeptid());
				arrList4.add(en.getValue().getTeacherid());
			}
			Map<String, Teacher> teacherMap = baseTeacherService
					.getTeacherMap(arrList4.toArray(new String[0]));
			Map<String, BaseUnit> unitMap = baseUnitService
					.getBaseUnitMap(arrList2.toArray(new String[0]));
			Map<String, Dept> deptMap = baseDeptService.getDeptMap(arrList3
					.toArray(new String[0]));
			// 获取各个user最后登录的那条数据集合
			List<CountOnlineTime> loginTimeList = countOnlineTimeService
					.getCountOnlineTimeListByLastlogin();
			Map<String, Integer> loginTimeMap = new HashMap<String, Integer>();
			for (CountOnlineTime item : loginTimeList) {
				loginTimeMap.put(item.getUserId(), item.getOnlineTime());
			}
			//过滤掉学生
			List<CountOnlineTime> countOnlineTimeStuList = new ArrayList<CountOnlineTime>();
			for (CountOnlineTime item : countOnlineTimeList){
				User us = userMap.get(item.getUserId());
				if(2 != us.getOwnerType()){
					countOnlineTimeStuList.add(item);
				}
			}
			countOnlineTimeList.removeAll(countOnlineTimeStuList);
			for (CountOnlineTime item : countOnlineTimeList) {
				User us = userMap.get(item.getUserId());
				if (us != null) {
					item.setUserName(us.getName());// 用户名
					item.setName(us.getRealname());// 真实姓名
					if (us.getSex().equals("1")) {
						item.setSex("男");
					} else {
						item.setSex("女");
					}
					if (teacherMap.containsKey(us.getTeacherid())) {
						item.setPhone(teacherMap.get(us.getTeacherid())
								.getPersonTel());
					}
					if (unitMap.containsKey(us.getUnitid())) {
						item.setUnitName(unitMap.get(us.getUnitid()).getName());
					}
					if (StringUtils.isNotEmpty(us.getDeptid()) 
							&& deptMap.containsKey(us.getDeptid())) {
						item.setDepartment(deptMap.get(us.getDeptid())
								.getDeptname());
					}
					
				} else {
					item.setPhone("");
				}
				if (loginTimeMap.get(item.getUserId()) == 0) {
					item.setOnlineStaus("是");
				} else {
					item.setOnlineStaus("否");
				}
				
				int countMiao = item.getOnlineTime();
				// 登录三秒以下特殊情况 时间记0
				if (countMiao <= 3) {
					countMiao = 0;
				}
				String[] str = String.valueOf((double) countMiao / 3600.0)
						.split("\\.");
				String timeString = str[0] + "." + str[1].substring(0, 1);
				item.setOnlineHourTime(timeString);
				int a = Integer.parseInt(str[0] + str[1].substring(0, 1));
				item.setCountTime(a);
			}
			// 将list按照时长大小排序
			Collections.sort(countOnlineTimeList,
					new Comparator<CountOnlineTime>() {
						@Override
						public int compare(CountOnlineTime o1,
								CountOnlineTime o2) {
							return (o2.getCountTime()) - (o1.getCountTime());
						}
					});

			// 重新分页			
			Integer maxRow = countOnlineTimeList.size();
			getPage().setMaxRowCount(maxRow);
			if (countOnlineTimeList.size() != 0 && "1".equals(queryType)) {
				getPage().setMaxRowCount(maxRow);
				getPage().initialize();
				Pagination page = getPage();
				Integer oldCur = page.getCurRowNum();
				//page.setPageSize(page.getPageSize());
				System.out.println(page.getPageSize());
				Integer newCur = page.getPageIndex() * page.getPageSize();
				newCur = newCur > maxRow ? maxRow : newCur;
				List<CountOnlineTime> newList = new ArrayList<CountOnlineTime>();

				newList.addAll(countOnlineTimeList.subList(oldCur - 1, newCur));
				countOnlineTimeList = newList;
			}
			// over
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 获取市级Map
	/*
	 * public void getShiMap(){ Map<String,String> regionMap =
	 * regionService.getRegionMap(); for (String key : regionMap.keySet()) {
	 * if(key.length()==4){ shiRegionMap.put(key, regionMap.get(key)); } } }
	 */

	public String getQuxian() {
		if (StringUtils.isBlank(shiId)) {
			return SUCCESS;
		}
		Map<String, String> regionMap = regionService.getRegionMap();
		for (String key : regionMap.keySet()) {
			if (key.length() != 4 && key.startsWith(shiId)) {
				quxianMap.put(key, regionMap.get(key));
			}
		}
		return SUCCESS;
	}

	public void doExport() {
		execute();
		ZdExcel excel = new ZdExcel();
		excel.add(new ZdCell("在线时长统计" + "(" + beginTime + "到" + endTime + ")",
				16, new ZdStyle(ZdStyle.ALIGN_CENTER | ZdStyle.BOLD
						| ZdStyle.VERTICAL_TOP, 16)));
		excel.add(new ZdCell[] {
				new ZdCell("姓名", 2, new ZdStyle(ZdStyle.BORDER)),
				new ZdCell("性别", 2, new ZdStyle(ZdStyle.BORDER)),
				new ZdCell("手机号", 2, new ZdStyle(ZdStyle.BORDER)),
				new ZdCell("用户名", 2, new ZdStyle(ZdStyle.BORDER)),
				new ZdCell("单位", 2, new ZdStyle(ZdStyle.BORDER)),
				new ZdCell("部门", 2, new ZdStyle(ZdStyle.BORDER)),
				new ZdCell("在线状态", 2, new ZdStyle(ZdStyle.BORDER)),
				new ZdCell("在线时长（小时）", 2, new ZdStyle(ZdStyle.BORDER)) });
		if (countOnlineTimeList == null || countOnlineTimeList.size() == 0) {
			excel.createSheet("在线时长统计");
			excel.export("在线时长统计");
		} else {
			for (CountOnlineTime item : countOnlineTimeList) {
				excel.add(new ZdCell[] {
						new ZdCell(item.getName(), 2, new ZdStyle(
								ZdStyle.BORDER)),
						new ZdCell(item.getSex(), 2,
								new ZdStyle(ZdStyle.BORDER)),
						new ZdCell(item.getPhone(), 2, new ZdStyle(
								ZdStyle.BORDER)),
						new ZdCell(item.getUserName(), 2, new ZdStyle(
								ZdStyle.BORDER)),
						new ZdCell(item.getUnitName(), 2, new ZdStyle(
								ZdStyle.BORDER)),
						new ZdCell(item.getDepartment(), 2, new ZdStyle(
								ZdStyle.BORDER)),
						new ZdCell(item.getOnlineStaus(), 2, new ZdStyle(
								ZdStyle.BORDER)),
						new ZdCell(item.getOnlineHourTime(), 2, new ZdStyle(
								ZdStyle.BORDER)) });
			}
			excel.createSheet("在线时长统计");
			excel.export("在线时长统计");
		}

	}

	public List<CountOnlineTime> getCountOnlineTimeList() {
		return countOnlineTimeList;
	}

	public void setCountOnlineTimeList(List<CountOnlineTime> countOnlineTimeList) {
		this.countOnlineTimeList = countOnlineTimeList;
	}

	public void setCountOnlineTimeService(
			CountOnlineTimeService countOnlineTimeService) {
		this.countOnlineTimeService = countOnlineTimeService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setBaseDeptService(BaseDeptService baseDeptService) {
		this.baseDeptService = baseDeptService;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public Map<String, String> getShiRegionMap() {
		return shiRegionMap;
	}

	public void setShiRegionMap(Map<String, String> shiRegionMap) {
		this.shiRegionMap = shiRegionMap;
	}

	public String getShiId() {
		return shiId;
	}

	public void setShiId(String shiId) {
		this.shiId = shiId;
	}

	public Map<String, String> getQuxianMap() {
		return quxianMap;
	}

	public void setQuxianMap(Map<String, String> quxianMap) {
		this.quxianMap = quxianMap;
	}

	public String getQuxianId() {
		return quxianId;
	}

	public void setQuxianId(String quxianId) {
		this.quxianId = quxianId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public int getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(int regionLevel) {
		this.regionLevel = regionLevel;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}

}
