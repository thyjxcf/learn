package net.zdsoft.office.studentBackSchool.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.studentBackSchool.dto.StudentBackSchoolDto;
import net.zdsoft.office.studentBackSchool.entity.InspectionHolidaysInfo;
import net.zdsoft.office.studentBackSchool.service.InspectionHolidaysInfoService;
import net.zdsoft.office.studentcard.entity.StudentCardRecord;
import net.zdsoft.office.studentcard.service.StudentCardRecordService;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class StudentBackSchoolAction extends PageSemesterAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InspectionHolidaysInfoService inspectionHolidaysInfoService;
	private List<InspectionHolidaysInfo> holidayList=new ArrayList<InspectionHolidaysInfo>();
	private InspectionHolidaysInfo holidayInfo=new InspectionHolidaysInfo();
	private String actionName;
	
	private String holidayId;
	private String backState;
	private StudentService studentService;
	private List<StudentBackSchoolDto> stuBackList;
	private String classid;
	private BasicClassService basicClassService;
	private StudentFamilyService studentFamilyService;
	private TeacherService teacherService;
	
	private String studentid;//用于发送的参数
	
	private StudentCardRecordService studentCardRecordService;
	//1：已返校，0：未返校
	private final static String STUDENT_BACK="1"; 
	private final static String STUDENT_NOTBACK="0"; 
	//学生返校考勤
		public String backSchoolAdmin(){
			actionName="backSchoolAdmin";
			return SUCCESS;
		}
		//节假日设置
		public String holidyList(){
			holidayList=inspectionHolidaysInfoService.findInspectionHolidaysInfobyUnitId(getUnitId(),getPage());
			return SUCCESS;
		}
		public String holidayEdit(){
			if(StringUtils.isNotBlank(holidayInfo.getId())){
				holidayInfo=inspectionHolidaysInfoService.findInspectionHolidaysInfobyId(holidayInfo.getId());
			}else{
				holidayInfo.setUnitId(getUnitId());
			}
			return SUCCESS;
		}
		
		public String holidaySave(){
			try{
				inspectionHolidaysInfoService.updateInspectionHolidaysInfo(holidayInfo);
				promptMessageDto.setPromptMessage("保存成功！");
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败:" + e.getMessage());
			}
			return SUCCESS;
		}
		public String holidayDelete(){
			try{
				inspectionHolidaysInfoService.deleteInspectionHolidaysInfobyId(holidayInfo.getId());
				promptMessageDto.setPromptMessage("删除成功！");
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("删除失败！");
				promptMessageDto.setErrorMessage("删除失败:" + e.getMessage());
			}
			return SUCCESS;
		}
		
		//学生返校考勤
		public String backSchoolhead(){
			actionName="backSchoolAdmin-head";
			holidayList=inspectionHolidaysInfoService.findInspectionHolidaysInfobyUnitId(getUnitId(),null);
			if(StringUtils.isBlank(holidayId) && holidayList!=null && holidayList.size()>0){
				holidayId = holidayList.get(0).getId();
			}
			return SUCCESS;
		}
		public String backSchoolList(){
			if(StringUtils.isBlank(holidayId) || StringUtils.isBlank(classid)){
				return SUCCESS;
			}
			
			//拿到班级所有student
			InspectionHolidaysInfo holiday = inspectionHolidaysInfoService.findInspectionHolidaysInfobyId(holidayId);
			stuBackList=new ArrayList<StudentBackSchoolDto>();
			//返校学生集合
			ArrayList<StudentBackSchoolDto> stuBackList1 = new ArrayList<StudentBackSchoolDto>();
			//未返校学生集合
			ArrayList<StudentBackSchoolDto> stuBackList2 = new ArrayList<StudentBackSchoolDto>();
			
			
			if(holiday==null){
				return SUCCESS;
			}
			List<Student> list = studentService.getAllStudents(classid);
			BasicClass seachClass = basicClassService.getClass(classid);
			//学生入校考勤
			List<StudentCardRecord> stuCardRecordList = studentCardRecordService.findStudentCardRecordbyTimes(holiday.getStartDate(), holiday.getEndDate(), StudentCardRecord.IN_SCHOOL);
			//有返校记录的student
			Set<String> studentIdSet=new HashSet<String>();
			if(CollectionUtils.isNotEmpty(stuCardRecordList)){
				for(StudentCardRecord stuCard:stuCardRecordList){
					studentIdSet.add(stuCard.getStudentId());
				}
			}
			
			if(CollectionUtils.isNotEmpty(list)){
				for(Student stu:list){
					StudentBackSchoolDto stuback=new StudentBackSchoolDto();
					stuback.setStudentId(stu.getId());
					stuback.setStuname(stu.getStuname());
					stuback.setClassId(stu.getClassid());
					if(seachClass!=null){
					stuback.setClassName(seachClass.getClassname());
					}
					//先默认为1：已返校，0：未返校
					if(studentIdSet.contains(stu.getId())){
						stuback.setBackstate(STUDENT_BACK);
						stuback.setBackstateText("已返校");
						stuBackList1.add(stuback);
					}else{
					stuback.setBackstate(STUDENT_NOTBACK);
					stuback.setBackstateText("未返校");
					stuBackList2.add(stuback);
					}
					stuBackList.add(stuback);
				}
			}
			if(StringUtils.isBlank(backState)){
				return SUCCESS;
			}else if("1".equals(backState)){
				stuBackList=stuBackList1;
			}else{
				stuBackList=stuBackList2;
			}
			return SUCCESS;
		}
		
		public void backSchoolExport(){
			/*if(StringUtils.isBlank(holidayId) || StringUtils.isBlank(classid)){
				return SUCCESS;
			}*/
			
			//拿到班级所有student
			InspectionHolidaysInfo holiday = inspectionHolidaysInfoService.findInspectionHolidaysInfobyId(holidayId);
			stuBackList=new ArrayList<StudentBackSchoolDto>();
			//返校学生集合
			ArrayList<StudentBackSchoolDto> stuBackList1 = new ArrayList<StudentBackSchoolDto>();
			//未返校学生集合
			ArrayList<StudentBackSchoolDto> stuBackList2 = new ArrayList<StudentBackSchoolDto>();
			
			
			/*if(holiday==null){
				return SUCCESS;
			}*/
			List<Student> list = studentService.getAllStudents(classid);
			BasicClass seachClass = basicClassService.getClass(classid);
			//学生入校考勤
			List<StudentCardRecord> stuCardRecordList = studentCardRecordService.findStudentCardRecordbyTimes(holiday.getStartDate(), holiday.getEndDate(), StudentCardRecord.IN_SCHOOL);
			//有返校记录的student
			Set<String> studentIdSet=new HashSet<String>();
			if(CollectionUtils.isNotEmpty(stuCardRecordList)){
				for(StudentCardRecord stuCard:stuCardRecordList){
					studentIdSet.add(stuCard.getStudentId());
				}
			}
			
			if(CollectionUtils.isNotEmpty(list)){
				for(Student stu:list){
					StudentBackSchoolDto stuback=new StudentBackSchoolDto();
					stuback.setStudentId(stu.getId());
					stuback.setStuname(stu.getStuname());
					stuback.setClassId(stu.getClassid());
					if(seachClass!=null){
					stuback.setClassName(seachClass.getClassname());
					}
					//先默认为1：已返校，0：未返校
					if(studentIdSet.contains(stu.getId())){
						stuback.setBackstate(STUDENT_BACK);
						stuback.setBackstateText("已返校");
						stuBackList1.add(stuback);
					}else{
					stuback.setBackstate(STUDENT_NOTBACK);
					stuback.setBackstateText("未返校");
					stuBackList2.add(stuback);
					}
					stuBackList.add(stuback);
				}
			}
			if(StringUtils.isBlank(backState)){
				//return null;
			}else if("1".equals(backState)){
				stuBackList=stuBackList1;
			}else{
				stuBackList=stuBackList2;
			}
			
			ZdExcel excel = new ZdExcel();
			excel.add(new ZdCell("学生返校情况列表", 6, new ZdStyle(ZdStyle.ALIGN_CENTER|ZdStyle.BOLD|ZdStyle.VERTICAL_TOP, 16))); 
			excel.add(new ZdCell[]{new ZdCell("班级", 2, new ZdStyle(ZdStyle.BORDER)), new ZdCell("姓名", 2, new ZdStyle(ZdStyle.BORDER)),new ZdCell("返校状态", 2, new ZdStyle(ZdStyle.BORDER))});
			for(StudentBackSchoolDto studentBackSchoolDto : stuBackList){
				excel.add(new ZdCell[]{new ZdCell(studentBackSchoolDto.getClassName(), 2, new ZdStyle(ZdStyle.BORDER)), new ZdCell(studentBackSchoolDto.getStuname(), 2, new ZdStyle(ZdStyle.BORDER)),new ZdCell(studentBackSchoolDto.getBackstateText(), 2, new ZdStyle(ZdStyle.BORDER))});
			} 
			excel.createSheet("学生返校情况列表"); 
			excel.export("学生返校情况列表");
			
			//return null;			
		}
		
		
		public String tosetMessage(){
			final String userId = getLoginUser().getUserId();
			final Map<String, String> msgMap = new HashMap<String, String>();
			//班主任
			Student stu = studentService.getStudent(studentid);
			InspectionHolidaysInfo holiday = inspectionHolidaysInfoService.findInspectionHolidaysInfobyId(holidayId);
			
			String msgStr = holiday.getName()+" "+stu.getStuname()+"同学还未返校";
			
			String errorMsg = "";
			boolean isSend = false;
			if(StringUtils.isNotBlank(stu.getClassid())){
				BasicClass cls = basicClassService.getClass(stu.getClassid());
				if(StringUtils.isNotBlank(cls.getTeacherid())){
					Teacher teacher = teacherService.getTeacher(cls.getTeacherid());
					if(teacher != null){
						if(StringUtils.isNotBlank(teacher.getPersonTel())){
							msgMap.put(teacher.getPersonTel(), msgStr);
							isSend = true;
						}else{
							errorMsg=teacher.getName()+"(班主任)未维护手机号)";
						}
					}else{
						errorMsg=cls.getClassname()+"未维护班主任)";
					}
				}else{
					errorMsg=cls.getClassname()+"未维护班主任)";
				}
			}
			//家长
			StringBuffer errorStr = new StringBuffer();
			List<Family> familys = studentFamilyService.getFamiliesByStudentId(studentid);
			if(familys !=null && familys.size()>0){
				for(Family f : familys){
					if(StringUtils.isNotBlank(f.getMobilePhone())){
						if(!isSend){
							isSend = true;
						}
						msgMap.put(f.getMobilePhone(), msgStr);
					}else{
						if(StringUtils.isNotBlank(errorStr.toString())){
							errorStr.append(",").append(f.getName()+"(家长)未维护手机号");
						}else{
							errorStr.append(f.getName()+"(家长)未维护手机号");
						}
					}
				}
			}else{
				errorStr.append("未维护家长信息");
			}
			
			if(StringUtils.isNotBlank(errorStr.toString())){
				String str = StringUtils.isBlank(errorMsg)?"":(errorMsg + ",");
				errorMsg = str + errorStr.toString();
			}
			
			
			if(isSend){
				new Thread(){
					public void run() {
						sendMsg(userId, msgMap);
					};
				}.start();
				
				promptMessageDto.setPromptMessage("操作成功！"+errorMsg);
				promptMessageDto.setOperateSuccess(true);
			}else{
				promptMessageDto.setErrorMessage(errorMsg);
				return SUCCESS;
			}
			
			return SUCCESS;
		}
		
		
		private void sendMsg(String userId, Map<String, String> map){
			for(String key : map.keySet()){
		        JSONObject json = new JSONObject();
		        JSONArray receivers = new JSONArray();
		        
				json.put("msg", map.get(key));
				JSONObject receiverJ = new JSONObject();
				receiverJ.put("phone", key);
				receiverJ.put("userId", userId);
				receivers.add(receiverJ);
				if(receivers.size()>0){
					json.put("receivers", receivers);           
					try {
						SendResult sr = ZDResponse.post(json);
						System.out.println("--发送短信--"+sr.getDescription());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("--发送短信失败--");
					}
				}
			}

		}
		
		public List<InspectionHolidaysInfo> getHolidayList() {
			return holidayList;
		}
		public void setHolidayList(List<InspectionHolidaysInfo> holidayList) {
			this.holidayList = holidayList;
		}
		public InspectionHolidaysInfo getHolidayInfo() {
			return holidayInfo;
		}
		public void setHolidayInfo(InspectionHolidaysInfo holidayInfo) {
			this.holidayInfo = holidayInfo;
		}
		public void setInspectionHolidaysInfoService(
				InspectionHolidaysInfoService inspectionHolidaysInfoService) {
			this.inspectionHolidaysInfoService = inspectionHolidaysInfoService;
		}
		public String getActionName() {
			return actionName;
		}
		public void setActionName(String actionName) {
			this.actionName = actionName;
		}
		public String getHolidayId() {
			return holidayId;
		}
		public void setHolidayId(String holidayId) {
			this.holidayId = holidayId;
		}
		public String getBackState() {
			return backState;
		}
		public void setBackState(String backState) {
			this.backState = backState;
		}
		public List<StudentBackSchoolDto> getStuBackList() {
			return stuBackList;
		}
		public void setStuBackList(List<StudentBackSchoolDto> stuBackList) {
			this.stuBackList = stuBackList;
		}
		public String getClassid() {
			return classid;
		}
		public void setClassid(String classid) {
			this.classid = classid;
		}
		public void setStudentService(StudentService studentService) {
			this.studentService = studentService;
		}
		public void setBasicClassService(BasicClassService basicClassService) {
			this.basicClassService = basicClassService;
		}
		public void setStudentCardRecordService(
				StudentCardRecordService studentCardRecordService) {
			this.studentCardRecordService = studentCardRecordService;
		}
		public String getStudentid() {
			return studentid;
		}
		public void setStudentid(String studentid) {
			this.studentid = studentid;
		}
		
		public void setStudentFamilyService(
				StudentFamilyService studentFamilyService) {
			this.studentFamilyService = studentFamilyService;
		}
		
		public void setTeacherService(TeacherService teacherService) {
			this.teacherService = teacherService;
		}
		
}
