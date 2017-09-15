package net.zdsoft.office.meeting.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.meeting.entity.OfficeDeptLeader;
import net.zdsoft.office.meeting.entity.OfficeExecutiveFixedDept;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssue;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssueAttend;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeet;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetMinutes;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMinutesUser;
import net.zdsoft.office.meeting.service.OfficeDeptLeaderService;
import net.zdsoft.office.meeting.service.OfficeExecutiveFixedDeptService;
import net.zdsoft.office.meeting.service.OfficeExecutiveIssueAttendService;
import net.zdsoft.office.meeting.service.OfficeExecutiveIssueService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetMinutesService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMinutesUserService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

public class ExecutiveMeetAction extends PageAction{

	private static final long serialVersionUID = 3419211634820491768L;
	
	private OfficeExecutiveMeetService officeExecutiveMeetService;
	private OfficeExecutiveIssueService officeExecutiveIssueService;
	private OfficeExecutiveIssueAttendService officeExecutiveIssueAttendService;
	private OfficeExecutiveMeetMinutesService officeExecutiveMeetMinutesService;
	private OfficeDeptLeaderService officeDeptLeaderService;
	private OfficeExecutiveMinutesUserService officeExecutiveMinutesUserService;
	private OfficeExecutiveFixedDeptService officeExecutiveFixedDeptService;
	private UserService userService;
	private DeptService deptService;
	private String meetId;
	private String queryName;
	private String startTime;
	private String endTime;
	private boolean view;
	private String submitState;
	private String queryIssueState;
	private String[] removeAttachment;//已删除附件的id
	
	private String deptIdsStr;
	private String deptNamesStr;
	private String userIdsStr;
	private String userNamesStr;
	
	private String[] minutesContent;
	private String[] deptIds;
	private String[] issueIds;//分配议题到会议对应的ids
	private String unitName;
	private String myDeptName;
	private String myRedDeptName;
	private String allReplyInfo;//统一回复
	
	private OfficeExecutiveMeet officeExecutiveMeet;
	private OfficeExecutiveIssue officeExecutiveIssue;
	private OfficeExecutiveIssueAttend officeExecutiveIssueAttend;
	private UnitService unitService;
	
	private List<OfficeExecutiveMeet> officeExecutiveMeetList = new ArrayList<OfficeExecutiveMeet>();
	private List<OfficeExecutiveIssue> officeExecutiveIssueList = new ArrayList<OfficeExecutiveIssue>();
	private List<OfficeExecutiveIssueAttend> officeExecutiveIssueAttendList = new ArrayList<OfficeExecutiveIssueAttend>();
	private List<OfficeExecutiveMeetMinutes> officeExecutiveMeetMinuteList = new ArrayList<OfficeExecutiveMeetMinutes>();
	
	private boolean hasPermission;
	private boolean canManageMinutes;

	public String executiveMeetAdmin(){
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			hasPermission = getLoginInfo().validateAllModelOpera(70021,Constants.EXECUTIVE_MEETING_ADD);
		}else if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			hasPermission = getLoginInfo().validateAllModelOpera(70521,Constants.EXECUTIVE_MEETING_ADD);
		}
		canManageMinutes = officeExecutiveMinutesUserService.isMinutesUser(getUnitId(),getLoginUser().getUserId());
		return SUCCESS;
	}
	
	/**
	 * 会议添加
	 * @return
	 */
	public String meetManage(){
		return SUCCESS;
	}
	
	public String meetManageList(){
		officeExecutiveMeetList = officeExecutiveMeetService.getOfficeExecutiveMeetByUnitIdPage(getUnitId(), queryName, startTime, endTime, getPage());
		return SUCCESS;
	}
	
	public String meetEdit(){
		if(StringUtils.isNotBlank(meetId)){
			officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
			if(officeExecutiveMeet.getState() == Constants.HASPUBLISH && officeExecutiveMeet.getMeetDate().compareTo(new Date())<0){
				officeExecutiveMeet.setStart(true);
			}else{
				officeExecutiveMeet.setStart(false);
			}
		}else{
			officeExecutiveMeet = new OfficeExecutiveMeet();
			officeExecutiveMeet.setStart(false);
			String systemDeploySchool = getSystemDeploySchool();
			if(Unit.UNIT_EDU_TOP == getLoginInfo().getUnitType() && systemDeploySchool.equals(BaseConstant.SYS_DEPLOY_SCHOOL_GDDG)){
				Calendar a=Calendar.getInstance();
				officeExecutiveMeet.setName(a.get(Calendar.YEAR)+"年第  次局务会议");
				officeExecutiveMeet.setPlace("局第一会议室");
			}
		}
		List<OfficeExecutiveFixedDept> fixedDepts = officeExecutiveFixedDeptService.getOfficeExecutiveFixedDeptByUnitIdList(getUnitId());
		if(CollectionUtils.isNotEmpty(fixedDepts)){
			Map<String, Dept> deptMap = deptService.getDeptMap(getUnitId());
			for(OfficeExecutiveFixedDept fixedDept:fixedDepts){
				if(deptMap.containsKey(fixedDept.getDeptId())){
					if(StringUtils.isNotBlank(deptNamesStr)){
						deptNamesStr += ","+deptMap.get(fixedDept.getDeptId()).getDeptname();
					}else{
						deptNamesStr = deptMap.get(fixedDept.getDeptId()).getDeptname();
					}
				}
			}
		}
		return SUCCESS;
	}
	
	public String meetView(){
		officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
		return SUCCESS;
	}
	
	public String meetSave(){
		if(new Date().compareTo(officeExecutiveMeet.getMeetDate())>0){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("会议时间不能小于当前时间!");
			return SUCCESS;
		}
		try {
			if(StringUtils.isNotBlank(officeExecutiveMeet.getId())){
				officeExecutiveMeetService.update(officeExecutiveMeet);
			}else{
				officeExecutiveMeet.setCreateUserId(getLoginUser().getUserId());
				officeExecutiveMeet.setUnitId(getLoginInfo().getUnitID());
				officeExecutiveMeet.setCreateTime(new Date());
				officeExecutiveMeetService.save(officeExecutiveMeet);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败");
		}
		return SUCCESS;
	}
	
	public String publishMeet(){
		officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
		if(new Date().compareTo(officeExecutiveMeet.getMeetDate())>0){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("会议时间已过期，不能发布!");
			return SUCCESS;
		}
		try {
			officeExecutiveMeetService.publishMeet(meetId);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("发布成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("发布失败");
		}
		return SUCCESS;
	}
	
	public String deleteMeet(){
		try {
			officeExecutiveMeetService.delete(new String[]{meetId});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败");
		}
		return SUCCESS;
	}
	
	public String manageIssues(){
		officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
		officeExecutiveIssueList = officeExecutiveIssueService.getOfficeExecutiveIssueList(meetId, getPage());
		unitName = unitService.getUnit(officeExecutiveMeet.getUnitId()).getName();
		return SUCCESS;
	}
	
	public String export(){
		//TODO 议题导出
		officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
		officeExecutiveIssueList = officeExecutiveIssueService.getOfficeExecutiveIssueList(meetId, null);
		unitName = unitService.getUnit(officeExecutiveMeet.getUnitId()).getName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		parameterSet();
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		zdExcel.add(new ZdCell(unitName+"局党组、局长办公会议议题总表", 6, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(new ZdCell(officeExecutiveMeet.getName(), 6, 2, new ZdStyle(ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 12)));
		zdExcel.add(new ZdCell[]{new ZdCell("制表：局办公室", 3, 1, new ZdStyle(ZdStyle.WRAP_TEXT)),new ZdCell("会议时间："+sdf.format(officeExecutiveMeet.getMeetDate()), 3, 1, new ZdStyle(ZdStyle.ALIGN_RIGHT))});
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("序号",1,style2));
		zdlist.add(new ZdCell("议题名称",1,style2));
		zdlist.add(new ZdCell("提报领导",1,style2));
		zdlist.add(new ZdCell("主办科室",1,style2));
		zdlist.add(new ZdCell("列席科室",1,style2));
		zdlist.add(new ZdCell("备注",1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		int i = 0;
		for(OfficeExecutiveIssue sue : officeExecutiveIssueList){
			i++;
			ZdCell[] cells = new ZdCell[6];
			cells[0] = new ZdCell(i+"", 1, style3);
			cells[1] = new ZdCell(sue.getName(), 1, style3);
			cells[2] = new ZdCell(sue.getLeaderNameStr(), 1, style3);
			cells[3] = new ZdCell(sue.getHostDeptNameStr(), 1, style3);
			cells[4] = new ZdCell(sue.getAttendDeptNameStr(), 1, style3);
			cells[5] = new ZdCell("", 1, style3);
			zdExcel.add(cells);
		}
		if(StringUtils.isBlank(deptNamesStr)){
			zdExcel.add(new ZdCell("固定参会科室：无", 6, 1, new ZdStyle(ZdStyle.ALIGN_CENTER)));
		}
		else{
			zdExcel.add(new ZdCell("固定参会科室："+deptNamesStr, 6, 1, new ZdStyle(ZdStyle.ALIGN_CENTER)));
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("议题列表");
		return NONE;
	}
	
	public String meetIssueAdd(){
		officeExecutiveIssueList = officeExecutiveIssueService.getOfficeExecutiveIssues(getUnitId(), getPage());
		return SUCCESS;
	}
	
	/**
	 * 将议题从本次会议移除
	 * @return
	 */
	public String removeIssue(){
		try {
			officeExecutiveIssueService.removeIssue(getUnitId(), officeExecutiveIssue.getId(), meetId);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("移除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("移除失败");
		}
		return SUCCESS;
	}
	
	
	public String sortIssue(){
		try {
			Set<Integer> num = new HashSet<Integer>();
			for(int i = 0; i < officeExecutiveIssueList.size(); i++){
				num.add(officeExecutiveIssueList.get(i).getSerialNumber());
			}
			if(num.size() != officeExecutiveIssueList.size()){
				jsonError = "议题序号重复，请修改！";
				return SUCCESS;
			}
			officeExecutiveIssueService.sortIssue(officeExecutiveIssueList);
		} catch (Exception e) {
			jsonError = e.getMessage();
		}
		return SUCCESS;
	}
	
	/**
	 * 将议题添加到会议
	 * @return
	 */
	public String btnAddIssue(){
		try {
			if(org.apache.commons.lang.ArrayUtils.isEmpty(issueIds)){
				jsonError = "请选择议题";
				return SUCCESS;
			}
			officeExecutiveIssueService.addToMeet(getUnitId(), meetId, issueIds, true);
		} catch (Exception e) {
			jsonError = e.getMessage();
		}
		return SUCCESS;
	}

	/**
	 * 我的会议
	 * @return
	 */
	public String myMeet(){
		return SUCCESS;
	}
	
	public String myMeetList(){
		officeExecutiveMeetList = officeExecutiveMeetService.getMyMeetListPage(getUnitId(), getLoginUser().getUserId(), queryName, startTime, endTime, getPage());
		return SUCCESS;
	}
	
	/**
	 * 议题提报
	 * @return
	 */
	public String issueApply(){
		return SUCCESS;
	}
	
	public String issueApplyList(){
		officeExecutiveIssueList = officeExecutiveIssueService.getOfficeExecutiveIssueByConditions(getUnitId(), getLoginUser().getUserId(), queryName, new String[]{"1","2","3","4"}, getPage());
		List<OfficeExecutiveMeet> meetlist = officeExecutiveMeetService.getOfficeExecutiveMeetByUnitIdList(getUnitId());
		for(OfficeExecutiveIssue issue : officeExecutiveIssueList){
			issue.setCanManageOpinion(true);
			if(StringUtils.isNotBlank(issue.getMeetingId())){
				for(OfficeExecutiveMeet meet : meetlist){
					//当会议结束，不能修改意见等
					if(issue.getMeetingId().equals(meet.getId()) && new Date().compareTo(meet.getMeetDate()) > 0){
						issue.setCanManageOpinion(false);
					}
				}
			}
		}
		
		myDeptName = getLoginInfo().getUser().getDeptName();
		StringBuffer redname = new StringBuffer();
		redname.append("<font color='red'>");
		redname.append(myDeptName);
		redname.append("</font>");
		myRedDeptName = redname.toString();
		return SUCCESS;
	}
	
	public String issueApplyEdit(){
		officeExecutiveIssue = officeExecutiveIssueService.getOfficeExecutiveIssueById(officeExecutiveIssue.getId());
		if(officeExecutiveIssue == null){
			officeExecutiveIssue = new OfficeExecutiveIssue();
			officeExecutiveIssue.setHostDeptId(getLoginInfo().getUser().getDeptid());
			officeExecutiveIssue.setHostDeptNameStr(getLoginInfo().getUser().getDeptName());
		}
		return SUCCESS;
	}
	
	public String issueSubmit(){
		try{
			if(StringUtils.isNotBlank(officeExecutiveIssue.getId())){
				officeExecutiveIssueService.submitIssue(officeExecutiveIssue.getId());
			}else{
				jsonError="数据出错！";
			}
		}catch (Exception e){
			jsonError="提交失败！";
		}
		return SUCCESS;
	}
	
	public String issueApplySave(){
		try{
			if(StringUtils.isNotBlank(submitState)){
				officeExecutiveIssue.setState(Integer.parseInt(submitState));
				if(StringUtils.isBlank(officeExecutiveIssue.getId())){
					officeExecutiveIssue.setUnitId(getUnitId());
					officeExecutiveIssue.setCreateUserId(getLoginUser().getUserId());
					officeExecutiveIssue.setCreateTime(new Date());
				}
				else{
					officeExecutiveIssue.setRemoveAttachment(removeAttachment);
				}
				officeExecutiveIssueService.saveIssue(officeExecutiveIssue);
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
			jsonError=e.getMessage();
		}
		return SUCCESS;
	}

	public String issueDelete(){
		try{
			officeExecutiveIssueService.deleteIssue(officeExecutiveIssue.getId());
		}catch (Exception e){
			System.out.println(e.getMessage());
			jsonError=e.getMessage();
		}
		return SUCCESS;
	}
	
	/**
	 * 意见维护
	 * @return
	 */
	public String issueOpinionList(){
		officeExecutiveIssueAttendList = officeExecutiveIssueAttendService
				.getOfficeExecutiveIssueAttendList(new String[]{officeExecutiveIssue.getId()});
		return SUCCESS;
	}
	
	public String issueOpinionEdit(){
		officeExecutiveIssue = officeExecutiveIssueService.getOfficeExecutiveIssueById(officeExecutiveIssue.getId());
		List<OfficeExecutiveIssueAttend> issueAttendList = officeExecutiveIssueAttendService
				.getOfficeExecutiveIssueAttendList(new String[]{officeExecutiveIssue.getId()});
		List<OfficeDeptLeader> leaderList = officeDeptLeaderService.getOfficeDeptLeaderByUnitIdList(getUnitId());
		String deptId = "";
		for(OfficeDeptLeader leader : leaderList){
			if(getLoginUser().getUserId().equals(leader.getUserId())){
				deptId = leader.getDeptId();
			}
		}
		officeExecutiveIssueAttend = new OfficeExecutiveIssueAttend();
		if(StringUtils.isNotBlank(deptId)){
			for(OfficeExecutiveIssueAttend attend : issueAttendList){
				if(attend.getType() == 4 && deptId.equals(attend.getObjectId())){
					officeExecutiveIssueAttend = attend;
				}
			}
		}
		return SUCCESS;
	}
	
	public String issueOpinionListSave(){
		try{
			if(StringUtils.isNotBlank(allReplyInfo)){
				for(OfficeExecutiveIssueAttend attend : officeExecutiveIssueAttendList){
					if(StringUtils.isNotBlank(attend.getRemark())){
						attend.setIsReplyed(true);
						attend.setReplyInfo(allReplyInfo);
					}
					else{
						attend.setIsReplyed(false);
					}
				}
			}
			else{
				for(OfficeExecutiveIssueAttend attend : officeExecutiveIssueAttendList){
					if(StringUtils.isNotBlank(attend.getReplyInfo()))
						attend.setIsReplyed(true);
					else
						attend.setIsReplyed(false);
				}
			}
			officeExecutiveIssueAttendService.batchUpdate(officeExecutiveIssueAttendList);
		}catch (Exception e){
			System.out.println(e.getMessage());
			jsonError=e.getMessage();
		}
		return SUCCESS;
	}
	
	public String issueOpinionEditSave(){
		try{
			if(StringUtils.isNotBlank(officeExecutiveIssueAttend.getReplyInfo())){
				officeExecutiveIssueAttend.setIsReplyed(true);
			}else{
				officeExecutiveIssueAttend.setIsReplyed(false);
			}
			officeExecutiveIssueAttendService.update(officeExecutiveIssueAttend);
		} catch (Exception e){
			System.out.println(e.getMessage());
			jsonError=e.getMessage();
		}
		return SUCCESS;
	}
	
	/**
	 * 议题审批
	 * @return
	 */
	public String issueAudit(){
		return SUCCESS;
	}
	
	public String issueAuditList(){
		if(StringUtils.isBlank(queryIssueState) || "2".equals(queryIssueState)){
			queryIssueState = "2";
			officeExecutiveIssueList = officeExecutiveIssueService.getOfficeExecutiveIssueByConditions(getUnitId(), null, queryName, new String[]{"2"}, getPage());
		}
		else{
			officeExecutiveIssueList = officeExecutiveIssueService.getOfficeExecutiveIssueByConditions(getUnitId(), null, queryName, new String[]{"3", "4"}, getPage());
		}
		return SUCCESS;
	}
	
	public String issueAuditEdit(){
		//议题详情
		officeExecutiveIssue = officeExecutiveIssueService.getOfficeExecutiveIssueById(officeExecutiveIssue.getId());
		//科室意见
		officeExecutiveIssueAttendList = officeExecutiveIssueAttendService
				.getOfficeExecutiveIssueAttendList(new String[]{officeExecutiveIssue.getId()});
		//已提报但未开始的会议
		officeExecutiveMeetList = new ArrayList<OfficeExecutiveMeet>();
		List<OfficeExecutiveMeet> meetlist = officeExecutiveMeetService.getOfficeExecutiveMeetByUnitIdPage(getUnitId(), null, null, null, getPage());
		for(OfficeExecutiveMeet meet : meetlist){
			if(!meet.isStart() && meet.getState() == 1){
				officeExecutiveMeetList.add(meet);
			}
		}
		if(StringUtils.isBlank(queryIssueState))
			queryIssueState = "2";
		return SUCCESS;
	}
	
	public String issueAuditSave(){
		try{
			queryIssueState = "2";
			if(StringUtils.isNotBlank(submitState)){
				officeExecutiveIssue.setState(Integer.parseInt(submitState));
				officeExecutiveIssueService.saveIssueAudit(officeExecutiveIssue);
				//分两个事务提交，主要涉及到上面议题列席科室有调整，会影响具体的参会科室
				if(StringUtils.isNotBlank(officeExecutiveIssue.getMeetingId()) && Integer.parseInt(submitState) == Constants.APPLY_STATE_PASS){
					officeExecutiveIssueService.addToMeet(getUnitId(), officeExecutiveIssue.getMeetingId(), new String[]{officeExecutiveIssue.getId()}, false);
				}
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
			jsonError=e.getMessage();
		}
		return SUCCESS;
	}
	
	/**
	 * 纪要维护 
	 * @return
	 */
	public String minutesManage(){
		return SUCCESS;
	}
	public String minutesManageList(){
		officeExecutiveMeetList = officeExecutiveMeetService.getOfficeExecutiveMeetOverduePage(getUnitId(), queryName, startTime, endTime, getPage());
		return SUCCESS;
	}
	public String minutesEdit(){
		officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
		officeExecutiveMeetMinuteList = officeExecutiveMeetMinutesService.getOfficeExecutiveMeetMinutesList(getUnitId(),null,meetId);
		return SUCCESS;
	}
	
	/**
	 * 查看所有纪要
	 * @return
	 */
	public String minutesView(){
		officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
		officeExecutiveMeetMinuteList = officeExecutiveMeetMinutesService.getOfficeExecutiveMeetMinutesList(getUnitId(),null,meetId);
		return SUCCESS;
	}
	/**
	 * 查看有权限的纪要
	 * @return
	 */
	public String minutesViewLimit(){
		officeExecutiveMeet = officeExecutiveMeetService.getOfficeExecutiveMeetById(meetId);
		officeExecutiveMeetMinuteList = officeExecutiveMeetMinutesService.getOfficeExecutiveMeetMinutesList(getUnitId(),getLoginInfo().getUser().getDeptid(),meetId);
		return SUCCESS;
	}
	//TODO
	public String minutesSave(){
		try {
			if(Validators.isEmpty(minutesContent)){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("没有纪要需要保存");
			}else{
				officeExecutiveMeetMinuteList = new ArrayList<OfficeExecutiveMeetMinutes>();
				for(int i=0;i<minutesContent.length;i++){
					OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes = new OfficeExecutiveMeetMinutes();
					officeExecutiveMeetMinutes.setContent(minutesContent[i]);
					officeExecutiveMeetMinutes.setCreateTime(new Date());
					officeExecutiveMeetMinutes.setDeptIds(deptIds[i]);
					officeExecutiveMeetMinutes.setMeetingId(meetId);
					officeExecutiveMeetMinutes.setUnitId(getUnitId());
					officeExecutiveMeetMinuteList.add(officeExecutiveMeetMinutes);
				}
				officeExecutiveMeetMinutesService.batchSave(meetId,officeExecutiveMeetMinuteList);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("移除失败");
		}
		return SUCCESS;
	}
	
	/**
	 * 参数设置
	 * @return
	 */
	public String parameterSet(){
		List<OfficeExecutiveFixedDept> fixedDepts = officeExecutiveFixedDeptService.getOfficeExecutiveFixedDeptByUnitIdList(getUnitId());
		if(CollectionUtils.isNotEmpty(fixedDepts)){
			Map<String, Dept> deptMap = deptService.getDeptMap(getUnitId());
			for(OfficeExecutiveFixedDept fixedDept:fixedDepts){
				if(deptMap.containsKey(fixedDept.getDeptId())){
					if(StringUtils.isNotBlank(deptIdsStr)){
						deptIdsStr += ","+fixedDept.getDeptId();
						deptNamesStr += ","+deptMap.get(fixedDept.getDeptId()).getDeptname();
					}else{
						deptIdsStr = fixedDept.getDeptId();
						deptNamesStr = deptMap.get(fixedDept.getDeptId()).getDeptname();
					}
				}
			}
		}
		List<OfficeExecutiveMinutesUser> minutesUsers = officeExecutiveMinutesUserService.getOfficeExecutiveMinutesUserByUnitIdList(getUnitId());
		if(CollectionUtils.isNotEmpty(minutesUsers)){
			Map<String, User> userMap = userService.getUserMap(getUnitId());
			for(OfficeExecutiveMinutesUser minutesUser:minutesUsers){
				if(userMap.containsKey(minutesUser.getUserId())){
					if(StringUtils.isNotBlank(userIdsStr)){
						userIdsStr += ","+minutesUser.getUserId();
						userNamesStr += ","+userMap.get(minutesUser.getUserId()).getRealname();
					}else{
						userIdsStr = minutesUser.getUserId();
						userNamesStr = userMap.get(minutesUser.getUserId()).getRealname();
					}
				}
			}
		}
		return SUCCESS;
	}
	
	public String fixedDepts(){
		try {
			if(StringUtils.isNotBlank(deptIdsStr)){
				officeExecutiveFixedDeptService.batchUpdate(getUnitId(),deptIdsStr);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("设置成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("请选择固定列席科室");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("设置失败");
		}
		return SUCCESS;
	}
	
	public String minutesUsers(){
		try {
			if(StringUtils.isNotBlank(userIdsStr)){
				officeExecutiveMinutesUserService.batchUpdate(getUnitId(),userIdsStr);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("设置成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("请选择纪要维护人员");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("设置失败");
		}
		return SUCCESS;
	}

	public String getMeetId() {
		return meetId;
	}

	public void setMeetId(String meetId) {
		this.meetId = meetId;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
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

	public OfficeExecutiveMeet getOfficeExecutiveMeet() {
		return officeExecutiveMeet;
	}

	public void setOfficeExecutiveMeet(OfficeExecutiveMeet officeExecutiveMeet) {
		this.officeExecutiveMeet = officeExecutiveMeet;
	}

	public void setOfficeExecutiveMeetService(
			OfficeExecutiveMeetService officeExecutiveMeetService) {
		this.officeExecutiveMeetService = officeExecutiveMeetService;
	}

	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetList() {
		return officeExecutiveMeetList;
	}

	public OfficeExecutiveIssue getOfficeExecutiveIssue() {
		return officeExecutiveIssue;
	}

	public void setOfficeExecutiveIssue(OfficeExecutiveIssue officeExecutiveIssue) {
		this.officeExecutiveIssue = officeExecutiveIssue;
	}

	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueList() {
		return officeExecutiveIssueList;
	}

	public void setOfficeExecutiveIssueService(
			OfficeExecutiveIssueService officeExecutiveIssueService) {
		this.officeExecutiveIssueService = officeExecutiveIssueService;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public String getSubmitState() {
		return submitState;
	}

	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}

	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendList() {
		return officeExecutiveIssueAttendList;
	}
	
	public void setOfficeExecutiveIssueAttendList(
			List<OfficeExecutiveIssueAttend> officeExecutiveIssueAttendList) {
		this.officeExecutiveIssueAttendList = officeExecutiveIssueAttendList;
	}

	public void setOfficeExecutiveIssueAttendService(
			OfficeExecutiveIssueAttendService officeExecutiveIssueAttendService) {
		this.officeExecutiveIssueAttendService = officeExecutiveIssueAttendService;
	}

	public String getQueryIssueState() {
		return queryIssueState;
	}

	public void setQueryIssueState(String queryIssueState) {
		this.queryIssueState = queryIssueState;
	}

	public List<OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinuteList() {
		return officeExecutiveMeetMinuteList;
	}

	public void setOfficeExecutiveMeetMinuteList(
			List<OfficeExecutiveMeetMinutes> officeExecutiveMeetMinuteList) {
		this.officeExecutiveMeetMinuteList = officeExecutiveMeetMinuteList;
	}

	public void setOfficeExecutiveMeetMinutesService(
			OfficeExecutiveMeetMinutesService officeExecutiveMeetMinutesService) {
		this.officeExecutiveMeetMinutesService = officeExecutiveMeetMinutesService;
	}
	
	public String getDeptIdsStr() {
		return deptIdsStr;
	}

	public void setDeptIdsStr(String deptIdsStr) {
		this.deptIdsStr = deptIdsStr;
	}

	public String getUserIdsStr() {
		return userIdsStr;
	}

	public void setUserIdsStr(String userIdsStr) {
		this.userIdsStr = userIdsStr;
	}

	public String getDeptNamesStr() {
		return deptNamesStr;
	}

	public void setDeptNamesStr(String deptNamesStr) {
		this.deptNamesStr = deptNamesStr;
	}

	public String getUserNamesStr() {
		return userNamesStr;
	}

	public void setUserNamesStr(String userNamesStr) {
		this.userNamesStr = userNamesStr;
	}

	public String[] getMinutesContent() {
		return minutesContent;
	}

	public void setMinutesContent(String[] minutesContent) {
		this.minutesContent = minutesContent;
	}

	public String[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String[] deptIds) {
		this.deptIds = deptIds;
	}

	public OfficeExecutiveIssueAttend getOfficeExecutiveIssueAttend() {
		return officeExecutiveIssueAttend;
	}

	public void setOfficeExecutiveIssueAttend(
			OfficeExecutiveIssueAttend officeExecutiveIssueAttend) {
		this.officeExecutiveIssueAttend = officeExecutiveIssueAttend;
	}

	public void setOfficeDeptLeaderService(
			OfficeDeptLeaderService officeDeptLeaderService) {
		this.officeDeptLeaderService = officeDeptLeaderService;
	}

	public void setOfficeExecutiveMinutesUserService(
			OfficeExecutiveMinutesUserService officeExecutiveMinutesUserService) {
		this.officeExecutiveMinutesUserService = officeExecutiveMinutesUserService;
	}

	public void setOfficeExecutiveFixedDeptService(
			OfficeExecutiveFixedDeptService officeExecutiveFixedDeptService) {
		this.officeExecutiveFixedDeptService = officeExecutiveFixedDeptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String[] getRemoveAttachment() {
		return removeAttachment;
	}

	public void setRemoveAttachment(String[] removeAttachment) {
		this.removeAttachment = removeAttachment;
	}

	public boolean isHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
	}

	public boolean isCanManageMinutes() {
		return canManageMinutes;
	}

	public void setCanManageMinutes(boolean canManageMinutes) {
		this.canManageMinutes = canManageMinutes;
	}
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public String[] getIssueIds() {
		return issueIds;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setIssueIds(String[] issueIds) {
		this.issueIds = issueIds;
	}

	public String getMyDeptName() {
		return myDeptName;
	}

	public void setMyDeptName(String myDeptName) {
		this.myDeptName = myDeptName;
	}

	public String getMyRedDeptName() {
		return myRedDeptName;
	}

	public void setMyRedDeptName(String myRedDeptName) {
		this.myRedDeptName = myRedDeptName;
	}

	public String getAllReplyInfo() {
		return allReplyInfo;
	}

	public void setAllReplyInfo(String allReplyInfo) {
		this.allReplyInfo = allReplyInfo;
	}
	
}
