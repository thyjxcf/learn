package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;

import org.apache.commons.lang.StringUtils;

/**
 * @author chens
 * @version 创建时间：2015-3-10 上午11:03:53
 * 
 */
@SuppressWarnings("serial")
public class RemoteWorkReportAction extends OfficeJsonBaseAction{
	
	private static final String DATA_TYPE_1 = "1";
	private static final String DATA_TYPE_2 = "2";
	
	private OfficeWorkReportService officeWorkReportService;
	private UserService userService;
	private TeacherService teacherService;
	private DeptService deptService;
	private UnitService unitService;
	
	private List<OfficeWorkReport> reportlist = new ArrayList<OfficeWorkReport>();
	private OfficeWorkReport report = new OfficeWorkReport();
	
	private String unitId;
	private String id;
	private String dataType;//1--我收到的,2--我发出的
	private String userId;//登陆用户
	private String searchStr;//搜索内容
	
	private String startTime;
	private String endTime;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public void workReportList(){
		Pagination page = getPage();
//		page.setPageSize(15);
		if(DATA_TYPE_1.equals(dataType)){
			reportlist = officeWorkReportService.getOfficeWorkReportList(null, userId, null, searchStr, page);
		}else{
			reportlist = officeWorkReportService.getOfficeWorkReportList(userId, null, searchStr, null, page);
		}
		
		JSONArray array = new JSONArray();
		for(OfficeWorkReport ent : reportlist){
			JSONObject json = new JSONObject();
			json.put("id", ent.getId());
			json.put("sendUserName", ent.getCreateUserName());
			json.put("reportType", ent.getReportType());
			json.put("sendTime", DateUtils.date2String(ent.getCreateTime(), "MM-dd HH:mm"));
			json.put("receiveUserName", ent.getReceiveUserNameStr());
			json.put("state", ent.getState());
			if(StringUtils.isNotBlank(ent.getContent())){
				String content = ent.getContent();
				if(content.length() > 25){
					ent.setContent(content.substring(0, 25)+"...");
				}
			}
			json.put("content", ent.getContent());
			
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void workReportDetail(){
		OfficeWorkReport ent = officeWorkReportService.getOfficeWorkReportById(id);
		Set<String> userIds = new HashSet<String>();
		if(StringUtils.isNotBlank(ent.getCreateUserId())){
			userIds.add(ent.getCreateUserId());
		}
		if(StringUtils.isNotBlank(ent.getReceiveUserId())){
			String[] ids = ent.getReceiveUserId().split(",");
			for(String id : ids){
				userIds.add(id);
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		
		JSONObject json = new JSONObject();
		if(StringUtils.isNotBlank(ent.getCreateUserId())){
			User user = userMap.get(ent.getCreateUserId());
			if(user != null){
				json.put("sendUserName", user.getRealname());
				
				Unit unit = unitService.getUnit(user.getUnitid());
				if(unit != null){
					json.put("unitName", unit.getName());
				}
			}
			Teacher t = teacherService.getTeacher(user.getTeacherid());
			if(t !=null && StringUtils.isNotBlank(t.getDeptid())){
				Dept dept = deptService.getDept(t.getDeptid());
				if(dept!=null){
					json.put("deptName", dept.getDeptname());
				}
			}
		}
		if(StringUtils.isNotBlank(ent.getReceiveUserId())){
			String[] ids = ent.getReceiveUserId().split(",");
			StringBuffer names = new StringBuffer();
			int m = 0;
			for(String id : ids){
				if(userMap.containsKey(id)){
					User user = userMap.get(id);
					if(user != null){
						if(m == 0){
							names.append(user.getRealname());
						}else{
							names.append(",").append(user.getRealname());
						}
						m++;
					}
				}
			}
			ent.setReceiveUserNameStr(names.toString());
			json.put("receiveUserName", names.toString());
		}
		json.put("id", ent.getId());
		json.put("reportType", ent.getReportType());
		json.put("startTime", DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd"));
		json.put("endTime", DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd"));
		json.put("sendTime", DateUtils.date2String(ent.getCreateTime(), "yyyy-MM-dd HH:mm"));
		json.put("content", ent.getContent());
		json.put("receiveUserId", ent.getReceiveUserId());
		
		if(!json.containsKey("sendUserName")){
			json.put("sendUserName", "");
		}
		if(!json.containsKey("deptName")){
			json.put("deptName", "");
		}
		if(!json.containsKey("unitName")){
			json.put("unitName", "");
		}
		if(!json.containsKey("receiveUserName")){
			json.put("receiveUserName", "");
		}
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void saveWorkReport(){
		OfficeWorkReport ent = null;
		if(StringUtils.isNotBlank(report.getId())){
			ent = officeWorkReportService.getOfficeWorkReportById(report.getId());
		}else{
			ent = new OfficeWorkReport();
			User user = userService.getUser(userId);
			ent.setUnitId(user.getUnitid());
			ent.setCreateUserId(userId);
			if(user != null){
				Teacher t = teacherService.getTeacher(user.getTeacherid());
				if(t !=null && StringUtils.isNotBlank(t.getDeptid())){
					ent.setDeptId(t.getDeptid());
				}
			}
			ent.setCreateUserName(user.getRealname());
			ent.setCreateTime(new Date());
		}
		
		
		//维护数据
		ent.setState(report.getState());
		ent.setReportType(report.getReportType());
		ent.setBeginTime(report.getBeginTime());
		ent.setEndTime(report.getEndTime());
		ent.setReceiveUserId(report.getReceiveUserId());
		ent.setContent(report.getContent());
		
		JSONObject json = new JSONObject();
		try {
			if(StringUtils.isNotBlank(report.getId())){
				officeWorkReportService.update(ent);
			}else{
				officeWorkReportService.save(ent);
			}
			json.put("result",1);
			
		} catch (Exception e) {
			json.put("result",0);
		}
		responseJSON(json);
	}
	
	
	public void setOfficeWorkReportService(
			OfficeWorkReportService officeWorkReportService) {
		this.officeWorkReportService = officeWorkReportService;
	}
	
	public List<OfficeWorkReport> getReportlist() {
		return reportlist;
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public OfficeWorkReport getReport() {
		return report;
	}

	public void setReport(OfficeWorkReport report) {
		this.report = report;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}
