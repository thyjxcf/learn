package net.zdsoft.office.schedule.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.dao.UserDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.schedule.dao.OfficeWorkOutlineDao;
import net.zdsoft.office.schedule.entity.OfficeWorkOutline;
import net.zdsoft.office.schedule.service.OfficeWorkOutlineService;
import net.zdsoft.office.schedule.util.ScheduleConstant;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDConstant;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
/**
 * office_work_outline
 * @author 
 * 
 */
public class OfficeWorkOutlineServiceImpl implements OfficeWorkOutlineService{
	private OfficeWorkOutlineDao officeWorkOutlineDao;
	private DeptService deptService;
	private UserDao userDao;
	private TeacherService teacherService;

	@Override
	public OfficeWorkOutline save(OfficeWorkOutline officeWorkOutline){
		sendMsg(officeWorkOutline);
		return officeWorkOutlineDao.save(officeWorkOutline);
	}

	@Override
	public Integer delete(String[] ids){
		return officeWorkOutlineDao.delete(ids);
	}

	@Override
	public Integer update(OfficeWorkOutline officeWorkOutline){
		sendMsg(officeWorkOutline);
		return officeWorkOutlineDao.update(officeWorkOutline);
	}
	
	/**
	 * 短信发送相关
	 * @param officeWorkOutline
	 */
	private void sendMsg(OfficeWorkOutline officeWorkOutline){
		if(officeWorkOutline.getIsSmsAlarm() == 1){
			officeWorkOutline.setVersion(UUIDGenerator.getUUID());
			JSONObject json = new JSONObject();
			JSONArray receivers = new JSONArray();
			JSONObject receiverJ = new JSONObject();
			User u = userDao.getUser(officeWorkOutline.getOperator());
			if (u != null) {
				Teacher teacher = teacherService.getTeacher(u.getTeacherid());
				if (teacher != null) {
					json.put("msg",
							getScheduleMessageContent(officeWorkOutline));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					json.put("sendTime", sdf.format(officeWorkOutline.getSmsAlarmTime()));
					receiverJ.put("phone", teacher.getPersonTel());
					receiverJ.put("unitName", "");
					receiverJ.put("unitId", teacher.getUnitid());
					receiverJ.put("username", teacher.getName());
					receiverJ.put("userId", ZDConstant.DEFAULT_USER_ID);
					receivers.add(receiverJ);
					if (receivers.size() > 0) {
						json.put("receivers", receivers);
						SmsThread smsThread = new SmsThread(json);
						smsThread.start();
					}
				}
			}
		}
	}
	
	private String getScheduleMessageContent(OfficeWorkOutline schedule) {
        StringBuffer sb = new StringBuffer();
        sb.append("您好！请在").append(DateUtils.date2StringByMinute(schedule.getCalendarTime()));
        sb.append("参加").append(net.zdsoft.keel.util.StringUtils.htmlFilterToEmpty(schedule.getContent()));
        if(StringUtils.isNotBlank(schedule.getPlace())){
        	sb.append("，地点：").append(net.zdsoft.keel.util.StringUtils.htmlFilterToEmpty(schedule.getPlace()));
        }
        sb.append("。请注意安排好时间。");
        return sb.toString();
    }
	
	private class SmsThread extends Thread{
    	private JSONObject json;
    	public SmsThread(JSONObject json){
    		this.json = json;
    	}

		@Override
		public void run() {
			try {
				SendResult sr = ZDResponse.post(json);
				System.out.println("--科室周重点工作安排发送短信--"+sr.getDescription());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("--科室周重点工作安排发送短信失败--");
			}
 		}
    }

	@Override
	public OfficeWorkOutline getOfficeWorkOutlineById(String id){
		return officeWorkOutlineDao.getOfficeWorkOutlineById(id);
	}

	@Override
	public Map<String, OfficeWorkOutline> getOfficeWorkOutlineMapByIds(String[] ids){
		return officeWorkOutlineDao.getOfficeWorkOutlineMapByIds(ids);
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineList(){
		return officeWorkOutlineDao.getOfficeWorkOutlineList();
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlinePage(Pagination page){
		return officeWorkOutlineDao.getOfficeWorkOutlinePage(page);
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByUnitIdList(String unitId){
		return officeWorkOutlineDao.getOfficeWorkOutlineByUnitIdList(unitId);
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByUnitIdPage(String unitId, Pagination page){
		return officeWorkOutlineDao.getOfficeWorkOutlineByUnitIdPage(unitId, page);
	}

	public void setOfficeWorkOutlineDao(OfficeWorkOutlineDao officeWorkOutlineDao){
		this.officeWorkOutlineDao = officeWorkOutlineDao;
	}

	@Override
	public Map<String,List<OfficeWorkOutline>> getOfficeWorkOutlinesBySearchParams(
			List<Date> weekDate,String unitId, String deptId, Date startTime, Date endTime) {
		Map<String, List<OfficeWorkOutline>> outLineMap=new HashMap<String, List<OfficeWorkOutline>>();
		List<OfficeWorkOutline> weekList=officeWorkOutlineDao.getOfficeWorkOutlineBySearchParams(unitId, deptId, startTime, endTime);
		for(Date date:weekDate){
			List<OfficeWorkOutline> llist=new ArrayList<OfficeWorkOutline>();
			for(OfficeWorkOutline off:weekList){
				if(compareDate(off.getCalendarTime(), date)<=0&&compareDate(off.getEndTime(), date)>=0){
					llist.add(off);
				}
			}
			if(CollectionUtils.isNotEmpty(llist)){
				setData(unitId, llist);
			}
			List<OfficeWorkOutline> amlist=new ArrayList<OfficeWorkOutline>();
			List<OfficeWorkOutline> noonlist=new ArrayList<OfficeWorkOutline>();
			List<OfficeWorkOutline> pmlist=new ArrayList<OfficeWorkOutline>();
			List<OfficeWorkOutline> nightlist=new ArrayList<OfficeWorkOutline>();
			for(OfficeWorkOutline off:llist){
				if((off.getPeriod()==ScheduleConstant.PERIOD_AM)||(off.getPeriod()==ScheduleConstant.PERIOD_ALLDAY)){
					amlist.add(off);
				}
				if((off.getPeriod()==ScheduleConstant.PERIOD_NOON)||(off.getPeriod()==ScheduleConstant.PERIOD_ALLDAY)){
					noonlist.add(off);
				}
				if((off.getPeriod()==(ScheduleConstant.PERIOD_PM))||(off.getPeriod()==ScheduleConstant.PERIOD_ALLDAY)){
					pmlist.add(off);
				}
				if((off.getPeriod()==ScheduleConstant.PERIOD_NIGHT)||(off.getPeriod()==ScheduleConstant.PERIOD_ALLDAY)){
					nightlist.add(off);
				}
			}
			outLineMap.put(DateUtils.date2StringByDay(date)+ScheduleConstant.PERIOD_AM, amlist);
			outLineMap.put(DateUtils.date2StringByDay(date)+ScheduleConstant.PERIOD_NOON, noonlist);
			outLineMap.put(DateUtils.date2StringByDay(date)+ScheduleConstant.PERIOD_PM, pmlist);
			outLineMap.put(DateUtils.date2StringByDay(date)+ScheduleConstant.PERIOD_NIGHT, nightlist);
		}
		return outLineMap;
	}

	// 日期比较方法 去除时分秒
	public int compareDate(Date dateBefore, Date dateAfter) {
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		before.setTime(dateBefore);
		after.setTime(dateAfter);
		if (before.get(Calendar.YEAR) == after.get(Calendar.YEAR)) {
			if (before.get(Calendar.MONTH) == after.get(Calendar.MONTH)) {
				if (before.get(Calendar.DAY_OF_MONTH) == after
						.get(Calendar.DAY_OF_MONTH)) {
					return 0;
				} else {
					return before.get(Calendar.DAY_OF_MONTH) > after
							.get(Calendar.DAY_OF_MONTH) ? 1 : -1;
				}
			} else {
				return before.get(Calendar.MONTH) > after.get(Calendar.MONTH) ? 1
						: -1;
			}
		} else {
			return before.get(Calendar.YEAR) > after.get(Calendar.YEAR) ? 1
					: -1;
		}
	}

	@Override
	public boolean isExistConflict(String unitId, String deptId,String id,
			Date startTime, Date endTime) {
		return officeWorkOutlineDao.isExistConflict(unitId, deptId, id,startTime, endTime);
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByDay(String unitId,
			String deptId, Date date) {
		List<OfficeWorkOutline> dayList=new ArrayList<OfficeWorkOutline>();
		dayList=officeWorkOutlineDao.getOfficeWorkOutlineByDay(unitId, deptId, date);
		setData(unitId, dayList);
		return dayList;
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByPeriodOfDay(
			String unitId, String deptId, Date date, int period) {
		List<OfficeWorkOutline> periodList=new ArrayList<OfficeWorkOutline>();
		periodList=officeWorkOutlineDao.getOfficeWorkOutlineByPeriodOfDay(unitId, deptId, date, period);
		if(CollectionUtils.isNotEmpty(periodList)){
			setData(unitId, periodList);
		}
		return periodList;
	}

	@Override
	public Map<String,List<OfficeWorkOutline>> getOfficeWorkOutlinesByMonth(String unitId,
			String deptId,Date firstDay,Date lastDay, List<Date> dates) {
		List<OfficeWorkOutline> outlineList=new ArrayList<OfficeWorkOutline>();
		outlineList=officeWorkOutlineDao.getOfficeWorklinesByMonth(unitId, deptId, firstDay, lastDay);
		Map<String, List<OfficeWorkOutline>> dateMap=new HashMap<String, List<OfficeWorkOutline>>();
		for(Date date:dates){
			List<OfficeWorkOutline> tempList=new ArrayList<OfficeWorkOutline>();
			for(OfficeWorkOutline off:outlineList){
				if(compareDate(off.getCalendarTime(), date)<=0&&compareDate(off.getEndTime(), date)>=0){
					tempList.add(off);
				}
			}
			setData(unitId, tempList);
			dateMap.put(DateUtils.date2String(date), tempList);
		}
		return dateMap;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setData(String unitId,List<OfficeWorkOutline> outlineList){
		Map<String, Dept> deptMap=deptService.getDeptMap(unitId);
		if(deptMap==null){
			deptMap=new HashMap<String, Dept>();
		}
		for(OfficeWorkOutline off:outlineList){
			if(deptMap.get(off.getCreateDept())!=null&&StringUtils.isNotBlank(deptMap.get(off.getCreateDept()).getDeptname())){
				off.setDeptName(deptMap.get(off.getCreateDept()).getDeptname());
			}else{
				off.setDeptName("该部门已删除！");
			}
		}
	}

}
