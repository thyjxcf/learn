package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportEx;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportExService;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


@SuppressWarnings("serial")
public class RemoteOfficeWorkReportAction extends OfficeJsonBaseAction{
	private static final String DATA_TYPE_1 = "1";
	private static final String DATA_TYPE_2 = "2";
	

	private OfficeWorkReportService officeWorkReportService;
	private OfficeWorkReportExService officeWorkReportExService;
	private UserService userService;
	private TeacherService teacherService;
	
	private List<OfficeWorkReport> reportlist = new ArrayList<OfficeWorkReport>();
	private List<OfficeWorkReportEx> reportExlist = new ArrayList<OfficeWorkReportEx>();
	private OfficeWorkReport report = new OfficeWorkReport();
	private OfficeWorkReportEx workReportEx = new OfficeWorkReportEx();
	
	private String unitId;
	private String id;
	private String dataType;//2--我发出的,1--我收到的
	private String userId;//登陆用户
	private String searchStr;//搜索内容
	private String reportType;//汇报类型
	private String sendTime;//汇报日期
	private String everAttachId;
	
	private String startTime;
	private String endTime;
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public void workReportList(){
		Pagination page = getPage();
		if(!"1".equals(reportType)&&!"2".equals(reportType)){
			reportType = "";
		}
		int receiveNum = 0;
		if(DATA_TYPE_1.equals(dataType)){//我收到的
			List<OfficeWorkReport> officeWorkReports = officeWorkReportService.getOfficeWorkReportList(userId, null, null, null,null,null,null);
			if(CollectionUtils.isNotEmpty(officeWorkReports)){
				receiveNum = officeWorkReports.size();
			}
			reportlist = officeWorkReportService.getOfficeWorkReportList(userId, null, searchStr, null,reportType,sendTime,page);
		}else if((DATA_TYPE_2.equals(dataType))){//我发出的
			reportlist = officeWorkReportService.getOfficeWorkReportList(null, userId, null, searchStr,reportType,sendTime,page);
		}else{
			reportlist = officeWorkReportService.getOfficeWorkReportListICanRead(userId, reportType, page);
		}
		
		JSONArray array = new JSONArray();
		for(OfficeWorkReport ent : reportlist){
			JSONObject json = new JSONObject();
			json.put("id", ent.getId());
			json.put("sendUserName", ent.getCreateUserName());
			json.put("sendUserImg", ent.getUserPhoto());
			json.put("reportType", ent.getReportType());
			json.put("sendTime", DateUtils.date2String(ent.getCreateTime(), "MM-dd HH:mm"));
			json.put("state", ent.getState());
			json.put("read", ent.getRead());
			json.put("readNum", ent.getReadNum());
			json.put("sendNum", ent.getSendNum());
			json.put("time", ent.getTime());
			json.put("count", ent.getCount());
			if(StringUtils.isNotBlank(ent.getContent())){
				String content = ent.getContent();
				if(content.length() > 50){
					ent.setContent(content.substring(0, 50)+"...");
				}
			}
			json.put("content", ent.getContent());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("receiveNum", receiveNum);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void getReportById(){
		OfficeWorkReport ent=officeWorkReportService.getOfficeWorkReportByIdForMobile(id);
		JSONObject json = new JSONObject();
		json.put("id", ent.getId());
		json.put("reportType", ent.getReportType());
		json.put("beginTime", DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd"));
		json.put("endTime", DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd"));
		json.put("receiveUserIds", ent.getReceiveUserId());
		json.put("receiveUserNames", ent.getReceiveUserNameStr());
		json.put("userPhotos", ent.getUserPhoto());
		json.put("content", ent.getContent());
		json.put("attachmentArray", RemoteOfficeUtils.createAttachmentArray(ent.getAttachments()));
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void cancelReport(){
		OfficeWorkReport officeWorkReport=officeWorkReportService.getOfficeWorkReportById(id);
		officeWorkReport.setState("8");
		officeWorkReport.setInvalidUserId(userId);
		officeWorkReport.setInvalidTime(new Date());
		officeWorkReportService.update(officeWorkReport);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void workReportDetail(){
		//设置已读
		if(StringUtils.isNotBlank(userId)){
			officeWorkReportService.updateReadUserId(userId, id);
		}
		OfficeWorkReport ent = officeWorkReportService.getOfficeWorkReportDetailsById(id);
		
		JSONObject json = new JSONObject();
		json.put("id", ent.getId());
		json.put("sendUserName", ent.getCreateUserName());
		json.put("reportType", ent.getReportType());
		json.put("sendTime", DateUtils.date2String(ent.getCreateTime(), "MM-dd HH:mm"));
		json.put("content", ent.getContent());
//		json.put("receiveUserId", ent.getReceiveUserId());
		json.put("sendUserImg", ent.getUserPhoto());
		if(StringUtils.isNotBlank(ent.getReadUserNameStr())){
			json.put("readUserArray", ent.getReadUserNameStr().split(","));
		}else{
			json.put("readUserArray", new JSONArray());
		}
		if(StringUtils.isNotBlank(ent.getUnreadUserNameStr())){
			json.put("unReadUserArray", ent.getUnreadUserNameStr().split(","));
		}else{
			json.put("unReadUserArray", new JSONArray());
		}
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(ent.getAttachments()));
		reportExlist = officeWorkReportExService.getOfficeWorkReportExByrepotIdList(id);
		JSONArray replyArray = new JSONArray();
		for(OfficeWorkReportEx reportEx : reportExlist){
			JSONObject jsonEx = new JSONObject();
			jsonEx.put("id", reportEx.getId());
			jsonEx.put("createTime", DateUtils.date2String(reportEx.getCreateTime(), "MM-dd HH:mm"));
			jsonEx.put("content", reportEx.getContent());
			jsonEx.put("userName", reportEx.getUserName());//姓名==头像路径
			replyArray.add(jsonEx);
		}
		json.put("reply", replyArray);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void saveWorkReport(){
		UploadFile file = null;
		OfficeWorkReport ent = null;
		if(StringUtils.isNotBlank(report.getId())){
			ent = officeWorkReportService.getOfficeWorkReportById(report.getId());
		}else{
			ent = new OfficeWorkReport();
			User user = userService.getUser(userId);
			ent.setCreateUserId(userId);
			if(user != null){
				ent.setUnitId(user.getUnitid());
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
			file = StorageFileUtils.handleFile(new String[] {}, 5 * 1024);
			officeWorkReportService.save(ent,file,everAttachId);
			json.put("result",1);
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result",0);
		}
		responseJSON(json);
	}
	public void saveWorkReportEx(){
		OfficeWorkReportEx reportEx = new OfficeWorkReportEx();
		User user = userService.getUser(userId);
		reportEx.setUnitId(user.getUnitid());
		reportEx.setUserId(userId);
		reportEx.setReportId(id);
		reportEx.setCreateTime(new Date());
		
		reportEx.setContent(workReportEx.getContent());
		JSONObject json = new JSONObject();
		try {
			officeWorkReportExService.save(reportEx);
			json.put("result",1);
			
		} catch (Exception e) {
			json.put("result",0);
		}
		responseJSON(json);
	}
	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public void setOfficeWorkReportService(
			OfficeWorkReportService officeWorkReportService) {
		this.officeWorkReportService = officeWorkReportService;
	}

	public void setOfficeWorkReportExService(
			OfficeWorkReportExService officeWorkReportExService) {
		this.officeWorkReportExService = officeWorkReportExService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public List<OfficeWorkReport> getReportlist() {
		return reportlist;
	}

	public void setReportlist(List<OfficeWorkReport> reportlist) {
		this.reportlist = reportlist;
	}

	public OfficeWorkReport getReport() {
		return report;
	}

	public void setReport(OfficeWorkReport report) {
		this.report = report;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<OfficeWorkReportEx> getReportExlist() {
		return reportExlist;
	}

	public void setReportExlist(List<OfficeWorkReportEx> reportExlist) {
		this.reportExlist = reportExlist;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public OfficeWorkReportEx getWorkReportEx() {
		return workReportEx;
	}

	public void setWorkReportEx(OfficeWorkReportEx workReportEx) {
		this.workReportEx = workReportEx;
	}

	public String getEverAttachId() {
		return everAttachId;
	}

	public void setEverAttachId(String everAttachId) {
		this.everAttachId = everAttachId;
	}

}
