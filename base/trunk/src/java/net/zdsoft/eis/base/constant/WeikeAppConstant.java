package net.zdsoft.eis.base.constant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Unit;

import org.apache.commons.lang.StringUtils;

public class WeikeAppConstant {
	/**审核页*/
	public static int AUDIT_URL = 1;//审核页
	/**详情页*/
	public static int DETAILE_URL = 0;//详情页
	
	//===================公众号推送消息参数=============================
	/**跳h5页面---或不跳转（url为空）*/
	public static final int JUMP_TYPE_0 = 0;
	/**跳原生页面*/
	public static final int JUMP_TYPE_1 = 1;
	
	/**Token(token值)*/
	public static final int USERIDENTITY_1 = 1;
	/**userId(微课用户ID)*/
	public static final int USERIDENTITY_2 = 2;
	/**Sync_userId(数字校园用户ID)*/
	public static final int USERIDENTITY_3 = 3;
	
	
	//================应用code================================
	
	/**待我审批*/
	public static final String AUDIT_DATA = "W01";
	/**我发起的*/
	public static final String APPLY_DATA = "W02";
	/**邮件消息*/
	public static final String MESSAGE = "W03";
	
	/**发文*/
	public static final String OFFICEDOC_SEND = "W04";
	/**收文*/
	public static final String OFFICEDOC_RECEIVE = "W05";
	
	//==============公文类型=============================================
	/**发文--处理*/
	public static final String OFFICEDOC_SEND_1 = "W0401";
	/**收文--督办*/
	public static final String OFFICEDOC_RECEIVE_1 = "W0501";
	/**收文--阅办*/
	public static final String OFFICEDOC_RECEIVE_2 = "W0502";
	/**收文--签收*/
	public static final String OFFICEDOC_RECEIVE_3 = "W0503";
	/**收文--转发*/
	public static final String OFFICEDOC_RECEIVE_4 = "W0504";
	/**收文--传阅*/
	public static final String OFFICEDOC_RECEIVE_5 = "W0505";
	
	/**请假*/
	public static final String TEACHER_LEAVE = "W06";
	/**外出*/
	public static final String GO_OUT = "W07";
	/**出差*/
	public static final String EVECTION = "W08";
	/**报销*/
	public static final String EXPENSE = "W09";
	/**物品领用*/
	public static final String GOODS = "W10";
	/**听课登记*/
	public static final String ATTEND_LECTURE = "W11";
	/**工作汇报*/
	public static final String WORK_REPORT = "W12";
	/**工作日志*/
	public static final String WORK_SCHEDULE = "W13";
	/**报修*/
	public static final String REPAIR = "W14";
	/**通知公告*/
	public static final String BULLETIN = "W15";
	/**集体外出*/
	public static final String JT_GO_OUT = "W16";
	/**教师考勤打卡*/
	public static final String TEACHER_ATTENDANCE = "W17";
	/**排课调代课*/
	public static final String TIMETABLE_CHANGE = "W18";
	
	/**
	 * 获取应用moduleId
	 * @param moduleType moduleType
	 * @return
	 */
	public static final Long getAppModuleId(String moduleType, int unitClass){
		
		if(StringUtils.equals(WORK_SCHEDULE, moduleType)){
			if(Unit.UNIT_CLASS_EDU == unitClass){
				return Long.valueOf("70580");
			}else if(Unit.UNIT_CLASS_SCHOOL == unitClass){
				return Long.valueOf("70080");
			}
		}else if(StringUtils.equals(REPAIR, moduleType)){
			if(Unit.UNIT_CLASS_EDU == unitClass){
				return Long.valueOf("70578");
			}else if(Unit.UNIT_CLASS_SCHOOL == unitClass){
				return Long.valueOf("70078");
			}
		}else if(StringUtils.equals(BULLETIN, moduleType)){
			if(Unit.UNIT_CLASS_EDU == unitClass){
				return Long.valueOf("70579");
			}else if(Unit.UNIT_CLASS_SCHOOL == unitClass){
				return Long.valueOf("70079");
			}
		}else if(StringUtils.equals(WORK_REPORT, moduleType)){
			if(Unit.UNIT_CLASS_EDU == unitClass){
				return Long.valueOf("70571");
			}else if(Unit.UNIT_CLASS_SCHOOL == unitClass){
				return Long.valueOf("70071");
			}
		}else if(StringUtils.equals(GOODS, moduleType)){
			if(Unit.UNIT_CLASS_EDU == unitClass){
				return Long.valueOf("70576");
			}else if(Unit.UNIT_CLASS_SCHOOL == unitClass){
				return Long.valueOf("70076");
			}
		}else{
			return null;
		}
		return null;
	}
	
	/**
	 * 获取公文对应的字功能点类型
	 * @param list
	 * @return
	 */
	public static final Set<String> getOfficedocSubApps(List<String> list){
		Set<String> subs = new HashSet<String>();
		if(list == null || list.size()<=0)
			return subs;
		for(String str : list){
			if(StringUtils.equals(str, "17013")
					|| StringUtils.equals(str, "17113")){//发文处理
				subs.add(OFFICEDOC_SEND_1);
			}
			if(StringUtils.equals(str, "17022")
					|| StringUtils.equals(str, "17122")){//签收
				subs.add(OFFICEDOC_RECEIVE_3);
			}
			if(StringUtils.equals(str, "17023")
					|| StringUtils.equals(str, "17123")){//收文督办、阅办
				subs.add(OFFICEDOC_RECEIVE_1);
				subs.add(OFFICEDOC_RECEIVE_2);
			}
			if(StringUtils.equals(str, "17024")
					|| StringUtils.equals(str, "17124")){//收文转发
				subs.add(OFFICEDOC_RECEIVE_4);
			}
			if(StringUtils.equals(str, "17028")
					|| StringUtils.equals(str, "17128")){//收文传阅
				subs.add(OFFICEDOC_RECEIVE_5);
			}
		}
		
		return subs;
	}
}
