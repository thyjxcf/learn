package net.zdsoft.office.studentLeave.action;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;
import net.zdsoft.office.studentLeave.entity.OfficeStudentLeave;
import net.zdsoft.office.studentLeave.service.OfficeLeaveTypeService;
import net.zdsoft.office.studentLeave.service.OfficeStudentLeaveService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
/**                                        
 *			  ***** ***** ***** ***** *****
 *      *******   ***   ***   ***   ***   *******
 *      |                @author                |
 *      |                zhounan                |
 *       
 *
 */

public class StudentLeaveAction extends PageAction{
	
	private static final long serialVersionUID = 8274311016090322973L;
	
	private String leaveTypeId;
	private String typeName;//请假类型名称
	private Date startTime;
	private Date endTime;
	private int leaveStatus;
	private int num;//序号
	private CurrentSemester semester;
	private String acader;
	private String applyId; 
	private boolean view;
	private String remark;
	private String classId;
	private boolean isBetweenSemester=true;//是否学期范围内
	private boolean canAudit;//是否有审核权限
	
	private List<Student> students;
	private Map<String, String> stuLevMap=new HashMap<String, String>();
	private OfficeLeaveType officeLeaveType=new OfficeLeaveType();
	private OfficeStudentLeave officeStudentLeave=new OfficeStudentLeave();
	private List<OfficeStudentLeave> studentLeaveList;
	private List<OfficeLeaveType> leaveTypeList;
	private OfficeLeaveTypeService officeLeaveTypeService;
	private OfficeStudentLeaveService officeStudentLeaveService;
	private SemesterService semesterService;
	private StudentService studentService;
	private UserService userService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	public String execute(){
		canAudit = isPracticeAdmin("student_leave_audit");
		return SUCCESS;
	}
	
	/**
	 * 判断其是否为各种管理员
	 */
	private boolean isPracticeAdmin(String str){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), str);
		boolean flag;
		if(role == null){
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}
	
	public String studentLeaveApply(){
		//不在学年学期范围内  新增屏蔽
		semester=semesterService.getCurrentSemester();
		if(semester==null){
			isBetweenSemester=false;
		}
		return SUCCESS;
	}
	
	public String studentLeaveApplyList(){
		try {
			studentLeaveList=officeStudentLeaveService.getOfficeStudentLeavePageByParams(startTime, endTime, leaveStatus,getPage(),this.getUnitId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String addApply(){
		// 新增申请
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.STUDENT_LEAVE_TYPE);
		if(StringUtils.isNotBlank(applyId)){
			try {
				officeStudentLeave=officeStudentLeaveService.getOfficeStudentLeaveById(applyId);
				Student student=studentService.getStudent(officeStudentLeave.getStudentId());
				officeStudentLeave.setStuName(student.getStuname());
				OfficeLeaveType ltp=officeLeaveTypeService.getOfficeLeaveTypeById(officeStudentLeave.getLeaveTypeId());
				officeStudentLeave.setLeaveTypeName(ltp.getName());
				//审核过的显示审核信息
				if(StringUtils.isNotBlank(officeStudentLeave.getAuditUserId())){
					officeStudentLeave.setAuditUserName(userService.getUser(officeStudentLeave.getAuditUserId()).getRealname());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String saveApply(){
		// 保存申请
		boolean flag=officeStudentLeaveService.isExistConflict(getUnitId(), officeStudentLeave.getId(),officeStudentLeave.getStudentId(), officeStudentLeave.getStartTime(), officeStudentLeave.getEndTime());
		if(flag){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("保存失败，该时间段跟已经维护过的时间段有交叉！");
			return SUCCESS;
		}
		if(StringUtils.isNotBlank(officeStudentLeave.getId())){
			officeStudentLeave.setCreateTime(new Date());
			officeStudentLeave.setCreateUserId(getLoginInfo().getUser().getId());
			officeStudentLeave.setUnitId(this.getUnitId());
			try {
				officeStudentLeaveService.update(officeStudentLeave);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("保存失败！");
			}
		}else{
			semester=semesterService.getCurrentSemester();
			acader=semesterService.getCurrentAcadyear();
			// is_deleted为0  
			officeStudentLeave.setAcadyear(acader);
			officeStudentLeave.setSemester(Integer.parseInt(semester.getSemester()));
			officeStudentLeave.setUnitId(this.getUnitId());
			if(StringUtils.isNotBlank(officeStudentLeave.getStudentId())){
				Student student=studentService.getStudent(officeStudentLeave.getStudentId());
				officeStudentLeave.setClassId(student.getClassid());
			}
			officeStudentLeave.setCreateUserId(getLoginInfo().getUser().getId());
			officeStudentLeave.setCreateTime(new Date());
			//审核状态未提交
			officeStudentLeave.setState(Constants.APPLY_STATE_SAVE);
			officeStudentLeave.setIsDeleted(0);
			try {
				officeStudentLeaveService.save(officeStudentLeave);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("保存失败！");
			}
		}
		return SUCCESS;
	}
	
	//提交审核
	public String saveToApprove(){
		boolean flag=officeStudentLeaveService.isExistConflict(getUnitId(), officeStudentLeave.getId(), officeStudentLeave.getStudentId(), officeStudentLeave.getStartTime(), officeStudentLeave.getEndTime());
		if(flag){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("保存失败，该时间段跟已经维护过的时间段有交叉！");
			return SUCCESS;
		}
		if(StringUtils.isNotBlank(officeStudentLeave.getId())){
			officeStudentLeave.setUnitId(this.getUnitId());
			officeStudentLeave.setState(Constants.APPLY_STATE_NEED_AUDIT);
			officeStudentLeave.setCreateUserId(getLoginInfo().getUser().getId());
			officeStudentLeave.setCreateTime(new Date());
			try {
				officeStudentLeaveService.update(officeStudentLeave);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("提交审核成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("提交审核失败！");
			}
		}else{
			semester=semesterService.getCurrentSemester();
			acader=semesterService.getCurrentAcadyear();
			officeStudentLeave.setAcadyear(acader);
			officeStudentLeave.setSemester(Integer.parseInt(semester.getSemester()));
			officeStudentLeave.setUnitId(this.getUnitId());
			if(StringUtils.isNotBlank(officeStudentLeave.getStudentId())){
				Student student=studentService.getStudent(officeStudentLeave.getStudentId());
				officeStudentLeave.setClassId(student.getClassid());
			}
			officeStudentLeave.setCreateUserId(getLoginInfo().getUser().getId());
			officeStudentLeave.setCreateTime(new Date());
			//审核状态未提交
			officeStudentLeave.setState(Constants.APPLY_STATE_NEED_AUDIT);
			officeStudentLeave.setIsDeleted(0);
			try {
				officeStudentLeaveService.save(officeStudentLeave);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("保存失败！");
			}
		}
		return SUCCESS;
	}
	
	public String deleteApply(){
		// 删除申请
		if(StringUtils.isNotEmpty(applyId)){
			officeStudentLeave=officeStudentLeaveService.getOfficeStudentLeaveById(applyId);
			officeStudentLeave.setIsDeleted(1);
			try {
				officeStudentLeaveService.update(officeStudentLeave);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("删除失败！");
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败：找不到删除的对象");
		}
		return SUCCESS;
	}
	
	/*--------审核--------*/
	
	public String studentLeaveApprove(){
		return SUCCESS;
	}
	
	public String studentLeaveApproveList(){
		try {
			studentLeaveList=officeStudentLeaveService.getOfficeStudentLeavesByAuditParams(startTime, endTime, leaveStatus, this.getPage(), this.getUnitId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String approveEdit(){
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.STUDENT_LEAVE_TYPE);
		if(StringUtils.isNotBlank(applyId)){
			officeStudentLeave=officeStudentLeaveService.getOfficeStudentLeaveById(applyId);
			Student student=studentService.getStudent(officeStudentLeave.getStudentId());
			officeStudentLeave.setStuName(student.getStuname());
			OfficeLeaveType typ=officeLeaveTypeService.getOfficeLeaveTypeById(officeStudentLeave.getLeaveTypeId());
			officeStudentLeave.setLeaveTypeName(typ.getName());
		}
		return SUCCESS;
	}
	
	public String auditPass(){
		// 审核通过
		officeStudentLeave=officeStudentLeaveService.getOfficeStudentLeaveById(applyId);
		officeStudentLeave.setState(Constants.APPLY_STATE_PASS);
		remark = getRequest().getParameter("remark");
		try {
			remark = java.net.URLDecoder.decode(remark, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(remark)){
			officeStudentLeave.setAuditRemark(remark);
		}
		officeStudentLeave.setAuditUserId(getLoginUser().getUserId());
		officeStudentLeave.setAuditTime(new Date());
		try {
			officeStudentLeaveService.update(officeStudentLeave);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("审核成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("审核失败！");
		}
		return SUCCESS;
	}
	
	public String auditNoPass(){
		// 审核不通过
		officeStudentLeave=officeStudentLeaveService.getOfficeStudentLeaveById(applyId);
		officeStudentLeave.setState(Constants.APPLY_STATE_NOPASS);
		remark = getRequest().getParameter("remark");
		try {
			remark = java.net.URLDecoder.decode(remark, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		officeStudentLeave.setAuditRemark(remark);
		officeStudentLeave.setAuditUserId(getLoginUser().getUserId());
		officeStudentLeave.setAuditTime(new Date());
		try {
			officeStudentLeaveService.update(officeStudentLeave);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败！");
		}
		return SUCCESS;
	}
	
	public String auditView(){
		//审核查看
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.STUDENT_LEAVE_TYPE);
		if(StringUtils.isNotBlank(applyId)){
			officeStudentLeave=officeStudentLeaveService.getOfficeStudentLeaveById(applyId);
			Student student=studentService.getStudent(officeStudentLeave.getStudentId());
			officeStudentLeave.setStuName(student.getStuname());
			OfficeLeaveType typ=officeLeaveTypeService.getOfficeLeaveTypeById(officeStudentLeave.getLeaveTypeId());
			officeStudentLeave.setLeaveTypeName(typ.getName());
			//审核人
			officeStudentLeave.setAuditUserName(userService.getUser(officeStudentLeave.getAuditUserId()).getRealname());
		}
		return SUCCESS;
	}
	/*--------------请假查询---------------*/
	
	public String leaveQuery(){
		
		return SUCCESS;
	}
	
	public String queryList(){
		try {
			leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.STUDENT_LEAVE_TYPE);
			List<String> leaveIds=new ArrayList<String>();
			for(OfficeLeaveType off:leaveTypeList){
				leaveIds.add(off.getId());
			}
			studentLeaveList=officeStudentLeaveService.getOfficeStudentLeavesByCountParams(startTime, endTime, leaveStatus, this.getPage(), this.getUnitId(),leaveIds.toArray(new String[]{}));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String queryView(){
		try {
			officeStudentLeave=officeStudentLeaveService.getOfficeStudentLeaveById(applyId);
			Student stu=studentService.getStudent(officeStudentLeave.getStudentId());
			officeStudentLeave.setStuName(stu.getStuname());
			OfficeLeaveType typ=officeLeaveTypeService.getOfficeLeaveTypeById(officeStudentLeave.getLeaveTypeId());
			officeStudentLeave.setLeaveTypeName(typ.getName());
			officeStudentLeave.setAuditUserName(userService.getUser(officeStudentLeave.getAuditUserId()).getRealname());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/*------------------请假统计------------------*/
	
	public String leaveCount(){
		Calendar calendar=Calendar.getInstance();
		endTime=calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		startTime=calendar.getTime();
		return SUCCESS;
	}
	
	public String countList(){
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.STUDENT_LEAVE_TYPE);
		List<String> leaveIds=new ArrayList<String>();
		for(OfficeLeaveType off:leaveTypeList){
			leaveIds.add(off.getId());
		}
		String[] stuIds=officeStudentLeaveService.getStuIds(this.getUnitId(), startTime, endTime, classId,leaveIds.toArray(new String[0]));
		students=studentService.getStudentsByIds(stuIds);
		try {
			stuLevMap=officeStudentLeaveService.getSumMap(getLoginInfo().getUnitID(), startTime, endTime, classId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String leaveCountExport(){
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.STUDENT_LEAVE_TYPE);
		List<String> leaveIds=new ArrayList<String>();
		for(OfficeLeaveType off:leaveTypeList){
			leaveIds.add(off.getId());
		}
		String[] stuIds=officeStudentLeaveService.getStuIds(this.getUnitId(), startTime, endTime, classId,leaveIds.toArray(new String[]{}));
		students=studentService.getStudentsByIds(stuIds);
		try {
			stuLevMap=officeStudentLeaveService.getSumMap(this.getUnitId(), startTime, endTime, classId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ZdExcel zdExcel=new ZdExcel();
		ZdStyle style=new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style2=new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("学生请假统计",leaveTypeList.size()+1,2,new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT,18)));
		List<ZdCell> zdCellList=new ArrayList<ZdCell>();
		zdCellList.add(new ZdCell("姓名",1,style));
		for(OfficeLeaveType off:leaveTypeList){
			zdCellList.add(new ZdCell(off.getName(),1,style));
		}
		zdExcel.add(zdCellList.toArray(new ZdCell[0]));
		for(Student stu:students){
			ZdCell[] cells=new ZdCell[leaveTypeList.size()+1];
			cells[0]=new ZdCell(stu.getStuname(),1,style2);
			for(int i=0;i<leaveTypeList.size();i++){
				if(StringUtils.isNotEmpty(stuLevMap.get(stu.getId()+"_"+leaveTypeList.get(i).getId()))){
					cells[i+1]=new ZdCell(stuLevMap.get(stu.getId()+"_"+leaveTypeList.get(i).getId())+"天",1,style2);
				}else{
					cells[i+1]=new ZdCell("0天", 1, style2);
				}
			}
			zdExcel.add(cells);
		}
		Sheet sheet=zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("学生请假");
		return NONE;
	}
	
	/*-------------请假类型--------------*/
	public String leaveTypeList(){
		try {
			leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.STUDENT_LEAVE_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String addLeaveType(){
		if(StringUtils.isNotBlank(leaveTypeId)){
			officeLeaveType=officeLeaveTypeService.getOfficeLeaveTypeById(leaveTypeId);
		}
		return SUCCESS;
	}
	
	public String saveLeaveType(){
		if(StringUtils.isNotBlank(officeLeaveType.getId())){
			try {
				officeLeaveTypeService.update(officeLeaveType);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("修改成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("修改失败！");
			}
		}else{
			try {
				officeLeaveType.setUnitId(this.getUnitId());
				officeLeaveType.setState(Constants.STUDENT_LEAVE_TYPE);
				officeLeaveType.setIsDeleted(0);
				officeLeaveTypeService.save(officeLeaveType);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("添加成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("添加失败！");
			}
			
		}
		return SUCCESS;
	}
	
	public String deleteLeaveType(){
		try {
			officeLeaveTypeService.delete(new String[]{leaveTypeId});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	/*----------get/set------------------*/
	
	public void setOfficeLeaveTypeService(
			OfficeLeaveTypeService officeLeaveTypeService) {
		this.officeLeaveTypeService = officeLeaveTypeService;
	}

	public void setOfficeStudentLeaveService(
			OfficeStudentLeaveService officeStudentLeaveService) {
		this.officeStudentLeaveService = officeStudentLeaveService;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<OfficeStudentLeave> getStudentLeaveList() {
		return studentLeaveList;
	}

	public void setStudentLeaveList(List<OfficeStudentLeave> studentLeaveList) {
		this.studentLeaveList = studentLeaveList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(int leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public List<OfficeLeaveType> getLeaveTypeList() {
		return leaveTypeList;
	}

	public void setLeaveTypeList(List<OfficeLeaveType> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}

	public OfficeLeaveType getOfficeLeaveType() {
		return officeLeaveType;
	}

	public void setOfficeLeaveType(OfficeLeaveType officeLeaveType) {
		this.officeLeaveType = officeLeaveType;
	}

	public String getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public int getNum() {
		return num;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	
	public CurrentSemester getSemester() {
		return semester;
	}

	public void setSemester(CurrentSemester semester) {
		this.semester = semester;
	}

	public String getAcader() {
		return acader;
	}

	public void setAcader(String acader) {
		this.acader = acader;
	}

	public OfficeStudentLeave getOfficeStudentLeave() {
		return officeStudentLeave;
	}

	public void setOfficeStudentLeave(OfficeStudentLeave officeStudentLeave) {
		this.officeStudentLeave = officeStudentLeave;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	
	public String getApplyId() {
		return applyId;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Map<String, String> getStuLevMap() {
		return stuLevMap;
	}
	
	public void setStuLevMap(Map<String, String> stuLevMap) {
		this.stuLevMap = stuLevMap;
	}

	public List<Student> getStudents() {
		return students;
	}

	public Boolean getIsBetweenSemester() {
		return isBetweenSemester;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public boolean isCanAudit() {
		return canAudit;
	}

	public void setCanAudit(boolean canAudit) {
		this.canAudit = canAudit;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	
}
