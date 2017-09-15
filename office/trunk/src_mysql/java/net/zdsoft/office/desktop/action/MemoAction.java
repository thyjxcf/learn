package net.zdsoft.office.desktop.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.office.desktop.dto.MemoDto;
import net.zdsoft.office.desktop.entity.Memo;
import net.zdsoft.office.desktop.entity.MemoForWeek;
import net.zdsoft.office.desktop.service.MemoService;
import net.zdsoft.office.util.IndexUtilsV4;
import net.zdsoft.office.util.Lunar;

/**
 * 备忘录ACTION
 */
public class MemoAction extends PageAction {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2904807980981757976L;
    
    private String schedule;
    private String workweek;
//    private List<Schedule> schedules;
//    private List<WorkPlanItem> workPlans;
//    private List<CalendarDayInfo> calendars;
    private List<MemoDto> memoDtoList = new ArrayList<MemoDto>();
    private List<List<MemoDto>> weekMemoDto = new ArrayList<List<MemoDto>>();
    private List<Date> weekDate;
    
    private MemoService memoService;
    private UnitService unitService;
    private UserService userService;
    
    private String jsonString;
    
    private String[] Dayary = {"日", "一", "二", "三", "四", "五", "六"};
    private Date baseDate;
    private String now;
    private Date nowTime;
    private List<MenoCount> resultList = new ArrayList<MenoCount>();
    
    private int totalCount;
    private List<MemoDto> currentMemoDto = new ArrayList<MemoDto>();
    
    /**
     * 单条备忘记录
     */
    private Memo memo = new Memo();
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 通用变量
     */
    private String type;
    private String parent;
    /**
     * 备忘信息列表
     */
    private List<Memo> memos;
    /**
     * 备忘录周列表中的详细信息
     */
    private List<MemoForWeek> weekMess;
    private Date date;

    /**
     * 备忘记录ID，用于记录的修改和内容查询
     */
    private String memoId = "";
    /**
     * 备忘记录的ID集合，用于批量删除功能
     */
    private String[] ids;
    
    public String memoIndex(){
        nowTime = DateUtils.currentStartDate();
        weekDate = makeNextSevenDate(nowTime);
        List<List<Memo>> memos = memoService.getWeekMemos(getUserId(),weekDate);
        for (int i = 0; i < weekDate.size(); i++) {
			List<MemoDto> memoDto = new ArrayList<MemoDto>();
			List<Memo> memoList = memos.get(i);
			memoDto = makeMemoDto(memoList);
			
			if(DateUtils.date2StringByDay(nowTime).equals(DateUtils.date2StringByDay(weekDate.get(i)))){
				currentMemoDto = memoDto;
			}else{
				weekMemoDto.add(memoDto);
			}
		}
        totalCount = memoService.findTotalMemoCountByDate(getUserId(),nowTime);
    	return SUCCESS;
    }
    
    public String memorandum(){
    	return SUCCESS;
    }
    
    public String memorandumWeek(){
    	return SUCCESS;
    }
    public String memorandumMonth(){
    	return SUCCESS;
    }

    public class MenoCount {
        private String index;
        private int count;
        private String calendarStr;

        public MenoCount(String index, int count,String calendarStr) {
            this.index = index;
            this.count = count;
            this.calendarStr = calendarStr;
        };

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

		public String getCalendarStr() {
			return calendarStr;
		}

		public void setCalendarStr(String calendarStr) {
			this.calendarStr = calendarStr;
		}
    }

    public MemoAction() {
    }

    private String getUserId() {
        return getLoginInfo().getUser().getId();
    }
    
    private String getLoginUnitId(){
    	return getLoginInfo().getUser().getId();
    }

    public String memoCount() {
    	List<Date> dates=new ArrayList<Date>(); 
//    	List<Integer> scheduleResultList = new ArrayList<Integer>();
//    	Map<String,Integer> workCount = new HashMap<String, Integer>();
    	Calendar calendar = Calendar.getInstance();
        Map<String, Integer> allInfo = memoService.getMenoCount(getUserId(), DateUtils.getStartDate(startDate),
                DateUtils.getEndDate(endDate));
//        if (null == allInfo || allInfo.keySet().isEmpty()) {
//            return;
//        }
        Date newBegin = DateUtils.getStartDate(startDate);
		calendar.setTime(newBegin);
		int days=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= days; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, i);
			dates.add(calendar.getTime());
		}
//        if("schedule".equals(schedule)){
//			scheduleResultList = this.getScheduleBean().getMouthCount(false,getLoginInfo().getUser().getId(),"selfDept",dates,new String[]{getLoginUnitId()});
//        }  
//        if("workweek".equals(workweek)){
//        	workCount = this.getWorkPlanBean().getWorkCount(getUserId(),DateUtils.getStartDate(startDate),DateUtils.getEndDate(endDate));
//        }
//        String parentUnitId = "";
		//获取直属教育局id
//		if(getLoginInfo().getUnitClass() == 1 || getLoginInfo().getUnitType() == Constants.UNIT_TYPE_DJ){
//			parentUnitId = getUnitInfo().getId();
//		}else{
//			parentUnitId = getUnitInfo().getParentid();
//		}
//		Map<String,CalendarDayInfo> calendars = this.getCalendarDayInfoBean().getCalendarDayInfoMapByUnitId(DateUtils.getStartDate(startDate), DateUtils.getEndDate(endDate), parentUnitId);
//		if(calendars==null){
//			calendars = new HashMap<String, CalendarDayInfo>();
//		}
        if ("month".equalsIgnoreCase(type)) {
        	for (int i = 0; i < dates.size(); i++) {
        		Calendar today = Calendar.getInstance();
        		today.setTime(dates.get(i));
        		Lunar lunar = new Lunar(today);
        		resultList.add(new MenoCount(Integer.toString(i+1),0,lunar.toString().substring(lunar.toString().indexOf("月")+1).equals("初一")?lunar.toString().substring(lunar.toString().indexOf("年")+1):lunar.toString().substring(lunar.toString().indexOf("月")+1)));
			}
            for (String key : allInfo.keySet()) {
                calendar.setTime(DateUtils.string2Date(key, "yyyy-MM-dd"));
                MenoCount menoCount = resultList.get(calendar.get(Calendar.DAY_OF_MONTH)-1);
                menoCount.setCount(menoCount.getCount()+allInfo.get(key));
               // resultList.add(new MenoCount(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)), allInfo.get(key)));
            }
//            for (int i = 0; i < scheduleResultList.size(); i++) {
//       		 	MenoCount menoCount = resultList.get(i);
//       		 	menoCount.setCount(menoCount.getCount()+scheduleResultList.get(i));
//			}
//            for (String key : workCount.keySet()) {
//            	calendar.setTime(DateUtils.string2Date(key, "yyyy-MM-dd"));
//                MenoCount menoCount = resultList.get(calendar.get(Calendar.DAY_OF_MONTH)-1);
//                menoCount.setCount(menoCount.getCount()+workCount.get(key));
//            }
//            for (String key : calendars.keySet()) {
//            	if(calendars.get(key)!=null){
//            		calendar.setTime(DateUtils.string2Date(key, "yyyy-MM-dd"));
//            		MenoCount menoCount = resultList.get(calendar.get(Calendar.DAY_OF_MONTH)-1);
//            		menoCount.setCount(menoCount.getCount()+1);
//            	}
//            }
        }

//        JSON json = JSONSerializer.toJSON(resultList);

//        responseJSON(json);
        return SUCCESS;
    }

    public String saveOrUpdate() {

        if (memo.getTime().before(new Date())) {
            jsonError = "请选择当前时间之后的时间建立备忘！";
            return SUCCESS;
        }
        if (Validators.isEmpty(memo.getContent())) {
            jsonError = "内容不能为空!";
            return SUCCESS;
        }
        if (StringUtils.getRealLength(memo.getContent()) > 1000) {
            jsonError = "内容长度不能大于1000个字符!";
            return SUCCESS;
        }
        if (memo.getIsSmsAlarm() == 1) {
            if (null == date) {
                jsonError = "请设定短信提醒的时间!";
                return SUCCESS;
            }
            if (date.before(new Date())) {
                jsonError = "请选择当前时间之后的时间作为短信提醒的时间!";
                return SUCCESS;
            }
            memo.setSmsAlarmTime(date);
        }
        memo.setUserId(getUserId());
        memoService.modifyMemo(memo);
        return SUCCESS;
    }

    /**
     * 添加用户备忘录
     */
    public String saveMemo() {
    	JSONObject json = JSONObject.fromObject(jsonString);
    	memo.setId((String)json.get("id"));
    	memo.setTimeString((String)json.get("memoDate")+":00");
    	memo.setContent((String)json.get("content"));
    	memo.setIsSmsAlarm(Integer.parseInt((String)json.get("memoSmsAlerm")));
    	
    	if (null == memo.getTimeString() || "".equals(memo.getTimeString())) {
            jsonError = "请输入时间!";
            return SUCCESS;
        }
        else if (!Validators.isDateTime(memo.getTimeString())) {
            jsonError = "时间格式错误!";
            return SUCCESS;
        }
        Date time = DateUtils.string2DateTime(memo.getTimeString());
        if (time.before(new Date())) {
            jsonError = "请选择当前时间之后的时间建立备忘！";
            return SUCCESS;
        }
        memo.setTime(time);
        if (StringUtils.getRealLength(memo.getContent()) > 1000) {
            jsonError = "内容长度不能大于1000个字符!";
            return SUCCESS;
        }

        memo.setUserId(getUserId());
        if (memo.getIsSmsAlarm() == 1) {
        	memo.setAlarmTimeString((String)json.get("memoAlarmDate")+":00");
            if (Validators.isEmpty(memo.getAlarmTimeString())) {
                jsonError = "请设定短信提醒的时间!";
                return SUCCESS;
            }
            if (!Validators.isDateTime(memo.getAlarmTimeString())) {
                jsonError = "短信提醒的时间格式错误!";
                return SUCCESS;
            }
            Date alarmDate = DateUtils.string2DateTime(memo.getAlarmTimeString());

            if (alarmDate.before(new Date())) {
                jsonError = "请选择当前时间之后的时间作为短信提醒的时间!";
                return SUCCESS;
            }
            memo.setSmsAlarmTime(alarmDate);
        }
        memoService.modifyMemo(memo);

        return SUCCESS;
    }

    public String newMemo() {
        return SUCCESS;
    }

    public String addMon() {
        if (!Validators.isEmpty(memoId)) {
            memo = memoService.getMemo(memoId);
            if (null != memo) {
                memo.setTimeString(DateUtils.date2StringByMinute(memo.getTime()));
                if (memo.getIsSmsAlarm() == 1) {
                    memo.setAlarmTimeString(DateUtils.date2String(memo.getSmsAlarmTime(), "yyyy-MM-dd HH:mm"));
                }
                date = memo.getTime();
            }
            else {
                if (null == date) {
                    date = new Date();
                }
            }
        }
        return SUCCESS;
    }

    public String viewMemo() {
        memo = memoService.getMemo(memoId);
        memo.setTimeString(DateUtils.date2StringByMinute(memo.getTime()));
        if (memo.getIsSmsAlarm() == 1) {
            memo.setAlarmTimeString(DateUtils.date2StringByMinute(memo.getSmsAlarmTime()));
        }
        return SUCCESS;
    }

    /**
     * 备忘内容查询类型（用户需要获取那天或那个时间段内的列表信息）：0.全部、1.最近三天、2.最近一个月、3.最近六个月、4.六个月之后、5.上一月或下月、6.上一周或下周、7指定日期
     */
    private String timeType = "0";
    private String TabType = "";
    private String TabLabelType = "";

    /**
     * 备忘列表
     * 
     * @return
     */
    public String listMemos() {
        /* timeType：0.全部、1.最近三天、2.最近一个月、3.最近六个月、4.六个月之后 */
        int type = Integer.parseInt(timeType);
        baseDate = DateUtils.getStartDate(new Date());
        getMenos(type);
        return SUCCESS;
    }

    public String listMon() {
    	baseDate = DateUtils.getStartDate(new Date());
        getMenos(7);
        return SUCCESS;
    }

    private void getMenos(int type) {
        Date currDate = new Date();
        Date nextDate = null;
        Pagination page = this.getPage();
//        String userId = getUserId();
//        String unitId = getLoginUnitId();
//        String parentUnitId = "";
		//获取直属教育局id
//		if(getLoginInfo().getUnitClass() == 1 || getLoginInfo().getUnitType() == Constants.UNIT_TYPE_DJ){
//			parentUnitId = getUnitInfo().getId();
//		}else{
//			parentUnitId = getUnitInfo().getParentid();
//		}
        page.setPageSize(3);
        int index = page.getPageIndex();
        if(index==0){
        	index=1;
        }
        List<MemoDto> memoDtos = new ArrayList<MemoDto>();
        boolean usePage = true;
        //private String schedule;
       // private String workweek;
        switch (type) {
        case 0:// 显示全部
            memos = memoService.getMemos(getUserId(),null);
            //schedules = this.getScheduleBean().findSchedules(false,userId, currDate, nextDate,"self", new String[]{userId}, null);
            //workPlans = this.getWorkPlanBean().getWorkPlanItemList(beginDate, nextDate, userId, unitId)
            if("schedule".equals(schedule)){
            	
            }
            if("workweek".equals(workweek)){
            	
            }
            break;
        case 1:// 最近三天
            nextDate = DateUtils.addDay(currDate, 2);
            memos = memoService.getMemosByTime(getUserId(), currDate, nextDate, null, false);
//            calendars = this.getCalendarDayInfoBean().getCalendarDayInfoMapByTime(currDate, nextDate, parentUnitId);
//            if("schedule".equals(schedule)){
//            	schedules = this.getScheduleBean().findSchedules(false,userId, currDate, nextDate,"selfDept", new String[]{unitId},null);
//            }
//            if("workweek".equals(workweek)){
//            	workPlans = this.getWorkPlanBean().getWorkPlanItemList(currDate, nextDate, userId, unitId);
//            }
            break;
        case 2:// 最近一个月
            nextDate = IndexUtilsV4.addMonth(currDate, 1);
            memos = memoService.getMemosByTime(getUserId(), currDate, nextDate, null, false);
//            calendars = this.getCalendarDayInfoBean().getCalendarDayInfoMapByTime(currDate, nextDate, parentUnitId);
//            if("schedule".equals(schedule)){
//            	schedules = this.getScheduleBean().findSchedules(false,userId, currDate, nextDate,"selfDept", new String[]{unitId}, null);
//            }
//            if("workweek".equals(workweek)){
//            	workPlans = this.getWorkPlanBean().getWorkPlanItemList(currDate, nextDate, userId, unitId);
//            }
            break;
        case 3:// 最近六个月
            nextDate = IndexUtilsV4.addMonth(currDate, 6);
            memos = memoService.getMemosByTime(getUserId(), currDate, nextDate, null, false);
//            calendars = this.getCalendarDayInfoBean().getCalendarDayInfoMapByTime(currDate, nextDate, parentUnitId);
//            if("schedule".equals(schedule)){
//            	schedules = this.getScheduleBean().findSchedules(false,userId, currDate, nextDate,"selfDept", new String[]{unitId}, null);
//            }
//            if("workweek".equals(workweek)){
//            	workPlans = this.getWorkPlanBean().getWorkPlanItemList(currDate, nextDate, userId, unitId);
//            }
            break;
        case 4:// 六个月之后
        	currDate = IndexUtilsV4.addMonth(currDate, 6);
        	nextDate = null;
            // System.out.println(DateUtils.date2StringByDay(currDate));
            memos = memoService.getMemosByTime(getUserId(), currDate, null, null, false);
//            calendars = this.getCalendarDayInfoBean().getCalendarDayInfoMapByTime(currDate, nextDate, parentUnitId);
//            if("schedule".equals(schedule)){
//            	schedules = this.getScheduleBean().findSchedules(false,userId, currDate, nextDate,"selfDept", new String[]{unitId}, null);
//            }
//            if("workweek".equals(workweek)){
//            	workPlans = this.getWorkPlanBean().getWorkPlanItemList(currDate, nextDate, userId, unitId);
//            }
            break;
        case 5:// 上一月或下月
               // TODO map(天，list)
            break;
        case 6:// 上一周或下周
               // TODO map(天，list)->list(map(小时，List))

            break;
        case 7:// 指定日期
              // TODO map(天，list)->list(map(小时，List))
        	usePage = false;
            currDate = DateUtils.getStartDate(date);
            nextDate = DateUtils.getEndDate(date);
            memos = memoService.getMemosByTime(getUserId(), currDate, nextDate, null, false);
//            calendars = this.getCalendarDayInfoBean().getCalendarDayInfoMapByTime(currDate, nextDate, parentUnitId);
//            if("schedule".equals(schedule)){
//            	schedules = this.getScheduleBean().findSchedules(false,userId, currDate, nextDate,"selfDept", new String[]{unitId}, null);
//            }
//            if("workweek".equals(workweek)){
//            	workPlans = this.getWorkPlanBean().getWorkPlanItemList(currDate, nextDate, userId, unitId);
//            }
            break;
        default:// 显示全部
            memos = memoService.getMemos(getUserId(), null);
            //schedules = this.getScheduleBean().findSchedules(false,userId, currDate, nextDate,"self", new String[]{userId}, null);
            if("schedule".equals(schedule)){
            }
            if("workweek".equals(workweek)){
            }
            break;
        }
        if(usePage){
//        	memoDtos = makeMemoDto(memos,schedules,workPlans,calendars);
        	memoDtos = makeMemoDto(memos);
        	int count = (index-1)*page.getPageSize();
        	if(count<=(memoDtos.size()-1)){
        		memoDtoList.add(memoDtos.get(count));
        	}
        	if((count+1)<=(memoDtos.size()-1)){
        		memoDtoList.add(memoDtos.get(count+1));
        	}
        	if((count+2)<=(memoDtos.size()-1)){
        		memoDtoList.add(memoDtos.get(count+2));
        	}
        	page.setMaxRowCount(memoDtos.size());
        	page.initialize();
        	page.setPageIndex(index);
        }else{
//        	memoDtos = makeMemoDto(memos,schedules,workPlans,calendars);
        	memoDtos = makeMemoDto(memos);
        	memoDtoList=memoDtos;
        }
    	
    }

    private List<String> beforeToday(Date startDate, Date now) {
        List<String> result = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        for (int i = 0; i < 7; i++) {
            if (c.getTime().before(now)) {
                result.add(DateUtils.date2StringByDay(c.getTime()));

            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return result;
    }

    private List<String> afterToday(Date startDate, Date now) {
        List<String> result = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        for (int i = 0; i < 7; i++) {
            if (c.getTime().after(now)) {
                result.add(DateUtils.date2StringByDay(c.getTime()));

            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return result;
    }

    /**
     * 周备忘信息列表
     * 
     * @return
     */
    public String week() {
//    	startDate;
//      Date now = DateUtils.currentStartDate();
//    	String parentUnitId = "";
    	now = DateUtils.currentDate2StringByDay();
        nowTime = DateUtils.currentStartDate();
//  		//获取直属教育局id
//  		if(getLoginInfo().getUnitClass() == 1 || getLoginInfo().getUnitType() == Constants.UNIT_TYPE_DJ){
//  			parentUnitId = getUnitInfo().getId();
//  		}else{
//  			parentUnitId = getUnitInfo().getParentid();
//  		}
        List<Date> weekDate = makeWeekDate(startDate);
//        List<List<Schedule>> weekSchedules = new ArrayList<List<Schedule>>();
//        List<List<WorkPlanItem>> workPlans = new ArrayList<List<WorkPlanItem>>();
//        List<List<CalendarDayInfo>> calendars = new ArrayList<List<CalendarDayInfo>>();
        List<List<Memo>> memos = memoService.getWeekMemos(getUserId(),weekDate);
//        calendars = this.getCalendarDayInfoBean().getWeekCalendar(parentUnitId,weekDate);
//        if("schedule".equals(schedule)){
//        	weekSchedules = this.getScheduleBean().getWeekSchedules(false,getLoginInfo().getUser().getId(),"selfDept",weekDate, new String[]{getLoginUnitId()});
//        }
//        if("workweek".equals(workweek)){
//        	workPlans = this.getWorkPlanBean().getWeekWorkPlan(getUserId(),getLoginUnitId(),weekDate);
//        }
      //  List<MemoDto> tempMemoDto = new ArrayList<MemoDto>();
        for (int i = 0; i < weekDate.size(); i++) {
			List<MemoDto> memoDto = new ArrayList<MemoDto>();
			List<Memo> memoList = memos.get(i);
//			List<Schedule> scheduleList = new ArrayList<Schedule>();
//			List<WorkPlanItem> workPlanList = new ArrayList<WorkPlanItem>();
//			List<CalendarDayInfo> calendarList = new ArrayList<CalendarDayInfo>();
//			if(weekSchedules!=null&&weekSchedules.size()==7){
//				scheduleList=weekSchedules.get(i);
//			}
//			if(workPlans!=null&&workPlans.size()==7){
//				workPlanList = workPlans.get(i);
//			}
//			if(calendars!=null&&calendars.size()==7){
//				calendarList = calendars.get(i);
//			}
//			memoDto = makeMemoDto(memoList,scheduleList,workPlanList,calendarList);
			memoDto = makeMemoDto(memoList);
			weekMemoDto.add(memoDto);
		}
        
        
//        List<String> beforeDate = beforeToday(startDate, now);
//
//        List<String> afterDate = afterToday(startDate, now);
//
//        List<Memo> beforeMemos = memoService.getMemosByDayDesc(getUserId(), dateString.toArray(new String[0]),
//                null);
//        List<Memo> totayMemos = memoService.getMemosByCurentDay(getUserId(), 2);
//        List<Memo> afterMemos = memoService
//                .getMemosByDayAsc(getUserId(), afterDate.toArray(new String[0]), null);
//        Calendar c = Calendar.getInstance();
//        weekMess = new ArrayList<MemoForWeek>();
//        c.setTime(startDate);
//        for (int i = 0; i < 7; i++) {
//            MemoForWeek tmp = new MemoForWeek();
//            tmp.setDate(c.getTime());
//            if (c.getTime().after(now)) {
//                List<Memo> me = getMemos(afterMemos, c.getTime());
//                if (null != me && !me.isEmpty()) {
//                    tmp.setCount(me.size());
//                    if (me.size() > 1) {
//                        me = me.subList(0, 2);
//                    }
//                    tmp.setMemos(me);
//                }
//            }
//            else if (c.getTime().before(now)) {
//                List<Memo> me = getMemos(beforeMemos, c.getTime());
//                if (null != me && !me.isEmpty()) {
//                    tmp.setCount(me.size());
//                    if (me.size() > 1) {
//                        me = me.subList(0, 2);
//                        Collections.reverse(me);
//                    }
//                    tmp.setMemos(me);
//
//                }
//            }
//            else {
//                int count = memoService.getMemoCount(getUserId(), now);
//                tmp.setCount(count);
//                if (null != totayMemos && !totayMemos.isEmpty()) {
//                    tmp.setMemos(totayMemos);
//                }
//            }
//
//            weekMess.add(tmp);
//            c.add(Calendar.DAY_OF_MONTH, 1);
//        }
        return SUCCESS;
    }
    
    private List<Date> makeWeekDate(Date beginDate) {
    	weekDate = new ArrayList<Date>();
    	//Date newBegin = DateUtils.string2Date(beginDate);
    	Date newBegin = beginDate;
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(newBegin);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date Sunday = calendar.getTime();
		weekDate.add(Sunday);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date Monday = calendar.getTime();
        weekDate.add(Monday);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        Date Tuesday = calendar.getTime();
        weekDate.add(Tuesday);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        Date Wednesday = calendar.getTime();
        weekDate.add(Wednesday);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        Date Thursday = calendar.getTime();
        weekDate.add(Thursday);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date Friday = calendar.getTime();
        weekDate.add(Friday);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date Saturday = calendar.getTime();
        weekDate.add(Saturday);
        return weekDate;
	}
    
    //设置从今天起的下一个七天
    private List<Date> makeNextSevenDate(Date beginDate) {
    	String[] DayaryTemp = {"日", "一", "二", "三", "四", "五", "六"};
    	weekDate = new ArrayList<Date>();
    	//Date newBegin = DateUtils.string2Date(beginDate);
    	Date newBegin = beginDate;
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(newBegin);
		Date Firstday = calendar.getTime();
		Dayary[0] = DayaryTemp[calendar.get(calendar.DAY_OF_WEEK)-1];
		weekDate.add(Firstday);
		calendar.add(Calendar.DATE, 1);
        Date Secondday = calendar.getTime();
        Dayary[1] = DayaryTemp[calendar.get(calendar.DAY_OF_WEEK)-1];
        weekDate.add(Secondday);
        calendar.add(Calendar.DATE, 1);
        Date Threeday = calendar.getTime();
        Dayary[2] = DayaryTemp[calendar.get(calendar.DAY_OF_WEEK)-1];
        weekDate.add(Threeday);
        calendar.add(Calendar.DATE, 1);
        Date Fourday = calendar.getTime();
        Dayary[3] = DayaryTemp[calendar.get(calendar.DAY_OF_WEEK)-1];
        weekDate.add(Fourday);
        calendar.add(Calendar.DATE, 1);
        Date Fiveday = calendar.getTime();
        Dayary[4] = DayaryTemp[calendar.get(calendar.DAY_OF_WEEK)-1];
        weekDate.add(Fiveday);
        calendar.add(Calendar.DATE, 1);
        Date Sixday = calendar.getTime();
        Dayary[5] = DayaryTemp[calendar.get(calendar.DAY_OF_WEEK)-1];
        weekDate.add(Sixday);
        calendar.add(Calendar.DATE, 1);
        Date Sevenday = calendar.getTime();
        Dayary[6] = DayaryTemp[calendar.get(calendar.DAY_OF_WEEK)-1];
        weekDate.add(Sevenday);
        return weekDate;
	}
    
    

    List<Memo> getMemos(List<Memo> memos, Date date) {
        String dateStr = DateUtils.date2StringByDay(date);
        List<Memo> result = new ArrayList<Memo>();
        if (null != memos && !memos.isEmpty()) {
            for (Memo ite : memos) {
                if (DateUtils.date2StringByDay(ite.getTime()).equals(dateStr)) {
                    result.add(ite);
                }
            }
        }
        return result;
    }

    /**
     * 删除备忘录
     */
    public String removeMemos() {
        if (!Validators.isEmpty(ids)) {
            memoService.removeMemos(ids);
//            responseSuccessJson("删除成功！");
        }
        else {
            jsonError = "请选择删除内容！";
        }
        return SUCCESS;

    }
    
//    private List<MemoDto> makeMemoDto(List<Memo> memos,List<Schedule> schedules,List<WorkPlanItem> workPlans,List<CalendarDayInfo> calendars){
//    	List<MemoDto> memoDtos = new ArrayList<MemoDto>();
//    	if(memos!=null&&memos.size()>0){
//    		for (Memo memo : memos) {
//    			MemoDto memoDto = new MemoDto();
//    			memoDto.setType("memo";
//    			memoDto.setMemo(memo);
//    			memoDtos.add(memoDto);
//			}
//    	}
//    	if(calendars!=null&&calendars.size()>0){
//    		for (CalendarDayInfo calendar : calendars) {
//    			MemoDto memoDto = new MemoDto();
//    			memoDto.setType("calendarDayInfo";
//    			memoDto.setCalendarDayInfo(calendar);
//    			memoDtos.add(memoDto);
//			}
//    	}
//    	if(schedules!=null&&schedules.size()>0){
//    		for (Schedule schedule : schedules) {
//    			MemoDto memoDto = new MemoDto();
//    			memoDto.setType("schedule";
//    			memoDto.setSchedule(schedule);
//    			memoDtos.add(memoDto);
//			}
//    	}
//    	if(workPlans!=null&&workPlans.size()>0){
//    		for (WorkPlanItem workPlan : workPlans) {
//    			MemoDto memoDto = new MemoDto();
//    			memoDto.setType("workPlan";
//    			memoDto.setWorkPlanItem(workPlan);
//    			memoDtos.add(memoDto);
//			}
//    	}
//    	return memoDtos;
//    }
    
    private List<MemoDto> makeMemoDto(List<Memo> memos){
    	List<MemoDto> memoDtos = new ArrayList<MemoDto>();
    	if(memos!=null&&memos.size()>0){
    		for (Memo memo : memos) {
    			MemoDto memoDto = new MemoDto();
    			memoDto.setType("memo");
    			memoDto.setMemo(memo);
    			memoDtos.add(memoDto);
			}
    	}
    	return memoDtos;
    }
    
    /**
     * 桌面待办事项
     * @return
     */
    public String queryMemoList(){
    	memos = memoService.getMemoListByUserId(getUserId());
    	DateFormat f = new SimpleDateFormat("MM.dd");
    	DateFormat f1 = new SimpleDateFormat("HH:mm");
    	
        if (null != memos && !memos.isEmpty()) {
            for (Memo tmpMemo : memos) {
                tmpMemo.setTimeString(f.format(tmpMemo.getTime())+" 周"+getWeekByDate(tmpMemo.getTime())+ " "+f1.format(tmpMemo.getTime()));
            }
        }else{
        	memos = Collections.emptyList();
        }
    	return SUCCESS;
    }
    
    private String getWeekByDate(Date date){
    	int m = DateUtils.getDayOfWeek(date);
    	if(1 == m){
    		return "日";
    	}else if(2 == m){
    		return "一";
    	}else if(3 == m){
    		return "二";
    	}else if(4 == m){
    		return "三";
    	}else if(5 == m){
    		return "四";
    	}else if(6 == m){
    		return "五";
    	}else{
    		return "六";
    	}
    }
    
    
    /**
     * @return 备忘信息列表
     */
    public List<Memo> getMemos() {
        return memos;
    }

    /**
     * @return 单条备忘记录
     */
    public Memo getMemo() {
        return memo;
    }

    /**
     * @param 单条备忘记录
     */
    public void setMemo(Memo memo) {
        this.memo = memo;
    }

    /**
     * @return 备忘内容查询类型（用户需要获取那天或那个时间段内的列表信息）：0.全部、1.最近三天、2.最近一个月、3.最近六个月、4.六个月之后、5.上一月或下月、6.上一周或下周、7指定日期
     */
    public String getTimeType() {
        return timeType;
    }

    /**
     * @param 备忘内容查询类型
     *            （用户需要获取那天或那个时间段内的列表信息）：0.全部、1.最近三天、2.最近一个月、3.最近六个月、4.六个月之后、5.上一月或下月、6.上一周或下周、7指定日期
     */
    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    /**
     * @return Returns the tabType.
     */
    public String getTabType() {
        return TabType;
    }

    /**
     * @param tabType
     *            The tabType to set.
     */
    public void setTabType(String tabType) {
        TabType = tabType;
    }

    /**
     * @return Returns the tabLabelType.
     */
    public String getTabLabelType() {
        return TabLabelType;
    }

    /**
     * @param tabLabelType
     *            The tabLabelType to set.
     */
    public void setTabLabelType(String tabLabelType) {
        TabLabelType = tabLabelType;
    }

    /**
     * @return ID
     */
    public String getMemoId() {
        return memoId;
    }

    /**
     * @param ID
     */
    public void setMemoId(String memoId) {
        this.memoId = memoId;
    }

    /**
     * @return ID集合
     */
    public String[] getIds() {
        return ids;
    }

    /**
     * @param ID集合
     */
    public void setIds(String[] ids) {
        this.ids = ids;
    }

    /**
     * @return 指定日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param 指定日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Returns the startDate.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            The startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return Returns the endDate.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     *            The endDate to set.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 备忘录周列表中的详细信息
     */
    public List<MemoForWeek> getWeekMess() {
        return weekMess;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getWorkweek() {
		return workweek;
	}

	public void setWorkweek(String workweek) {
		this.workweek = workweek;
	}

//	public List<Schedule> getSchedules() {
//		return schedules;
//	}
//
//	public void setSchedules(List<Schedule> schedules) {
//		this.schedules = schedules;
//	}
//
//	public List<WorkPlanItem> getWorkPlans() {
//		return workPlans;
//	}

//	public void setWorkPlans(List<WorkPlanItem> workPlans) {
//		this.workPlans = workPlans;
//	}

	public List<MemoDto> getMemoDtoList() {
		return memoDtoList;
	}

	public void setMemoDtoList(List<MemoDto> memoDtoList) {
		this.memoDtoList = memoDtoList;
	}
    
	
	/** *****************根据用户id获取用户信息（显示需要）*************************** */
    public User getUserById(String userId) {
        return userService.getUser(userId);
    }

	public List<List<MemoDto>> getWeekMemoDto() {
		return weekMemoDto;
	}

	public void setWeekMemoDto(List<List<MemoDto>> weekMemoDto) {
		this.weekMemoDto = weekMemoDto;
	}

	public List<Date> getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(List<Date> weekDate) {
		this.weekDate = weekDate;
	}
	
	private Unit getUnitInfo(){
		String unitId = this.getLoginInfo().getUser().getUnitid();
		Unit unit = unitService.getUnit(unitId);
		return unit;
	}

	public void setMemoService(MemoService memoService) {
		this.memoService = memoService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String[] getDayary() {
		return Dayary;
	}

	public void setDayary(String[] dayary) {
		Dayary = dayary;
	}

	public Date getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}
	
//	public List<CalendarDayInfo> getCalendars() {
//		return calendars;
//	}
//
//	public void setCalendars(List<CalendarDayInfo> calendars) {
//		this.calendars = calendars;
//	}
	
//	public int getCurrentYear(){
//    	Calendar calendar=Calendar.getInstance();
//    	return calendar.get(Calendar.YEAR);
//    }
    
    public int getCurrentMonth(){
    	Calendar calendar=Calendar.getInstance();
    	return calendar.get(Calendar.MONTH)+1;
    }

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public List<MenoCount> getResultList() {
		return resultList;
	}

	public void setResultList(List<MenoCount> resultList) {
		this.resultList = resultList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<MemoDto> getCurrentMemoDto() {
		return currentMemoDto;
	}

	public void setCurrentMemoDto(List<MemoDto> currentMemoDto) {
		this.currentMemoDto = currentMemoDto;
	}

}
