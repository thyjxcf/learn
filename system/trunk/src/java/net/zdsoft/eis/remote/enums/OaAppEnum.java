package net.zdsoft.eis.remote.enums;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


public enum OaAppEnum {
	/*
	 * 配置移动OA办公中的应用配置（权限仍然通过角色授权控制，其中url,name,picture通过此配置做调整）
	 */
	//学校端
	WORK_REPORT_SCH("70071","工作汇报","/common/open/officemobile/workReport.action","img_workreport"),
	GO_OUT_SCH("70072","外出管理","/common/open/officemobile/goOut.action","img_goout"),
	EVECTION_SCH("70073","出差管理","/common/open/officemobile/evection.action","img_evection"),
	EXPENSE_SCH("70074","报销管理","/common/open/officemobile/expense.action","img_apply"),
	TAHCER_LEAVE_SCH("70075","教师请假","/common/open/officemobile/teacherLeave.action","img_leave"),
	GOODS_SCH("70076","物品管理","/common/open/officemobile/goods.action","img_goods"),
	WORK_REPORT_TL_SCH("70077","工作汇报","/common/open/officemobile/workReportTl.action","img_workreport"),
	REPAIR_SCH("70078","报修管理","/common/open/officemobile/repair.action","img_repair"),
	BULLETIN_SCH("70079","通知公告","/common/open/officemobile/bulletin.action","img_memo"),
	WORK_SCHEDULE_SCH("70080","工作日志","/common/open/officemobile/schedule.action","img_worklog"),
	
	//教育局端
	WORK_REPORT_EDU("70571","工作汇报","/common/open/officemobile/workReport.action","img_workreport"),
	GO_OUT_EDU("70572","外出管理","/common/open/officemobile/goOut.action","img_goout"),
	EVECTION_EDU("70573","出差管理","/common/open/officemobile/evection.action","img_evection"),
	EXPENSE_EDU("70574","报销管理","/common/open/officemobile/expense.action","img_apply"),
	TAHCER_LEAVE_EDU("70575","教师请假","/common/open/officemobile/teacherLeave.action","img_leave"),
	GOODS_EDU("70576","物品管理","/common/open/officemobile/goods.action","img_goods"),
	WORK_REPORT_TL_EDU("70577","工作汇报","/common/open/officemobile/workReportTl.action","img_workreport"),
	REPAIR_EDU("70578","报修管理","/common/open/officemobile/repair.action","img_repair"),
	BULLETIN_EDU("70579","通知公告","/common/open/officemobile/bulletin.action","img_memo"),
	WORK_SCHEDULE_EDU("70580","工作日志","/common/open/officemobile/schedule.action","img_worklog"),
	;
	
	private String code;
	private String name;
	private String url;
	private String picture;
	
	private OaAppEnum(String code, String name, String url, String picture){
		this.code = code;
		this.name = name;
		this.url = url;
		this.picture = picture;
	}
	
	/**
	 * 获取模块id  set集
	 * @return
	 */
	public static Set<String> getCodeSets(){
		Set<String>  set = new HashSet<String>();
		for(OaAppEnum ent : OaAppEnum.values()){
			set.add(ent.getCode());
		}
		return set;
	}
	
	/**
	 * 获取code对应的模块配置
	 * @param code
	 * @return
	 */
	public static OaAppEnum getOaAppEnumByCode(String code){
		OaAppEnum app = null;
		if(StringUtils.isNotBlank(code)){
			for(OaAppEnum ent : OaAppEnum.values()){
				if(ent.getCode().equals(code)){
					app = ent;
					break;
				}
			}
		}
		return app;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
