package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolSemesterService;
import net.zdsoft.eis.base.data.service.BaseSystemIniService;
import net.zdsoft.eis.frame.action.ModelBaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.exception.OperationNotAllowedException;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: BasicSemesterAdminAction.java,v 1.9 2007/01/26 01:34:35 zhanghh Exp $
 */
public class BasicSemesterAdminAction extends ModelBaseAction {

	private static final long serialVersionUID = 1L;
	//service由spring容器来初始化此对象
	protected BaseSchoolSemesterService baseSchoolSemesterService;
	private BaseGradeService baseGradeService;
	protected McodeService mcodeService;
	//学年学期DTO,从页面传来的学年信息封装成此Model
	SchoolSemester schoolSemester = new SchoolSemester();

	//传到页面上去的信息
	protected String cntid; //当前学年学期的ID		
	protected List<SchoolSemester> semesterList; //学年列表
	protected String currentadadyear; //当前学年学期
	protected List<String[]> semesterMcodeList; //学期微代码数组列表
	protected String semesterName; //学期名称（取自微代码context）
	protected String semesterHtml; //学期微代码页面显示选择框
	protected String acadyearHtml; //学年页面显示选择框
	
	private List<String> yearsList = new ArrayList<String>();
	//页面上传来的信息
	protected String[] checkid;
	protected String semesterid;

	//学校主键GUID，直接从logInfo中取得
	protected String schId;
	
	//年级升级开关 
	private BaseSystemIniService baseSystemIniService;
	private boolean gradeUpgrade = false;
	private String gradeUpgradeDate;//下次升级时间
    private static final String STUSYS_AUTOMATIC_UPGRADE = "STUSYS.AUTOMATIC.UPGRADE";//自动升级
    private static final String STUSYS_AUTOMATIC_UPGRADE_START_TIME = "STUSYS.AUTOMATIC.UPGRADE.START.TIME";
    //private static final String STUSYS_AUTOMATIC_UPGRADE_END_TIME = "STUSYS.AUTOMATIC.UPGRADE.END.TIME";
		

	public Object getModel() {
		return schoolSemester;
	}

	//列表信息
	public String execute() throws Exception {
		log.info("Action basicSemesterAdmin execute()......");

		schId = this.getLoginInfo().getUnitID();

		if (schId == null || "".equals(schId)) {
			this.addActionError("没有取到单位（学校）Id编号！");
			return ERROR;
		}

		//得到学期列表
		semesterList = baseSchoolSemesterService.getSemesters(schId);
		if (semesterList == null) {
			semesterList = new ArrayList<SchoolSemester>();
		}

		//当前学期的
		SchoolSemester schSem = baseSchoolSemesterService
				.getCurrentAcadyear(schId);
		if (schSem != null) {
			cntid = schSem.getId();
			currentadadyear = schSem.getAcadyear()
					+ mcodeService.getMcodeContext("DM-XQXN", schSem
							.getSemester());
			semesterMcodeList = mcodeService.getMcodeAarray("DM-XQXN");
		}
		//年级升级开关
		SystemIni systemini= systemIniService.getSystemIni(STUSYS_AUTOMATIC_UPGRADE);
		if("Y".equals(systemini.getNowValue())){
			gradeUpgrade = true;
			String graStartDate= baseSystemIniService.getValue(STUSYS_AUTOMATIC_UPGRADE_START_TIME);
			String[] md = graStartDate.split("-");
			gradeUpgradeDate = md[0]+"月"+md[1]+"日";
		}

		return SUCCESS;
	}

	//新增
	public String add() throws Exception {
		log.info("Action basicSemesterAdd add()......");
		schId = this.getLoginInfo().getUnitID();
		String acadyear, semester;
		acadyear = schoolSemester.getAcadyear();
		semester = schoolSemester.getSemester();
		//      若指定学年学期
		if (acadyear != null && acadyear.length() > 0 && semester != null
				&& semester.length() > 0) {
			schoolSemester.setId("");
			schoolSemester = baseSchoolSemesterService.getNewSemester(schId,
					acadyear, semester);
		} else {
		    //不自动增加下一学年学期，新增时跟着教育局的学年学期
//			// 得到要增加的下一个学年学期
//			schoolSemester.setId("");
//			schoolSemester = baseSchoolSemesterService
//					.getDefaultSemester(schId);
		}
		if (null == schoolSemester) {
			promptMessageDto = new PromptMessageDto();
			promptMessageDto.setErrorMessage("教育局学年学期找不到对应的记录，请联系管理员。");
			promptMessageDto
					.addHiddenText(new String[] { "acadyear", acadyear });
			promptMessageDto
					.addHiddenText(new String[] { "semester", semester });
			return SUCCESS;

		}
		//得到学期下拉列表Html语句
//		semesterHtml = mcodeService.getMcode("DM-XQXN").getHtmlTag(
//				schoolSemester.getSemester());
		//得到学年名称下拉列表Html语句<option value=''>--请选择--</option>
		int year=Integer.valueOf(DateUtils.date2String(new Date(), "yyyy")).intValue();
		for (int i = year-10; i < year+10; i++) {
			String yearStr = i+("-")+(i+1);
			yearsList.add(yearStr);
		}
		return SUCCESS;
	}
	
	public String valiAcadyearOrSem(){
		//      若指定学年学期
		this.promptMessageDto.setOperateSuccess(true);
		if (StringUtils.isNotBlank(schoolSemester.getAcadyear()) && StringUtils.isNotBlank(schoolSemester.getSemester())) { 
			schoolSemester.setId("");
			schoolSemester = baseSchoolSemesterService.getNewSemester(schId,
					schoolSemester.getAcadyear(), schoolSemester.getSemester());
		} else {
		    //不自动增加下一学年学期，新增时跟着教育局的学年学期
//			// 得到要增加的下一个学年学期
//			schoolSemester.setId("");
//			schoolSemester = baseSchoolSemesterService
//					.getDefaultSemester(schId);
		}
		if (null == schoolSemester) {
			this.promptMessageDto.setOperateSuccess(false);
			this.promptMessageDto.setErrorMessage("教育局学年学期找不到对应的记录，请联系管理员。");
			return SUCCESS;
		}
		return SUCCESS;
	}

	//编辑
	public String edit() throws Exception {
		log.info("Action basicSemesterEdit edit()......");
		log.debug("semesterid == " + semesterid);
		schoolSemester = baseSchoolSemesterService.getSemester(semesterid);
		//学期微代码名称
		semesterName = mcodeService.getMcodeContext("DM-XQXN", schoolSemester.getSemester());
		return SUCCESS;
	}

	//删除
	public String delete() throws Exception {
		if (checkid != null && checkid.length > 0) {
			//返回一个PormptMessageDto
			this.promptMessageDto = baseSchoolSemesterService.deleteSemester(checkid);
		}
		return SUCCESS;
	}

	//保存
	public String save() throws Exception {
		//检查学校ID是否已有值
		if (schoolSemester.getSchid() == null
				|| "".equals(schoolSemester.getSchid())) {
			schoolSemester.setSchid(this.getLoginInfo().getUnitID());
		}

		//返回一个PormptMessageDto
		this.promptMessageDto = baseSchoolSemesterService.saveSemester(schoolSemester);

		if (promptMessageDto.getOperateSuccess()) {
			if (schoolSemester.getId() == null
					|| "".equals(schoolSemester.getId())) {
				promptMessageDto.addOperation(new String[] { "新增下一个",
						"basicSemesterAdmin-add.action" });
			}
			promptMessageDto.addOperation(new String[] { "返回",
					"basicSemesterAdmin.action" });
			promptMessageDto.setPromptMessage("保存学年学期信息成功！");
		} else {
			if (schoolSemester.getId() == null
					|| "".equals(schoolSemester.getId())) {
				//新增 组合学期选择框		
				semesterHtml = mcodeService.getMcode("DM-XQXN").getHtmlTag(
						schoolSemester.getSemester());

				promptMessageDto.addHiddenText(new String[] { "semesterHtml",
						semesterHtml });
			} else {
				//编辑 学期微代码名称
				semesterName = mcodeService.getMcodeContext("DM-XQXN",
						schoolSemester.getSemester());

				promptMessageDto.addHiddenText(new String[] { "semesterName",
						semesterName });
				promptMessageDto.addHiddenText(new String[] { "id",
						schoolSemester.getId() });
			}
			promptMessageDto.addHiddenText(new String[] { "schid",
					schoolSemester.getSchid() });
			promptMessageDto.addHiddenText(new String[] { "acadyear",
					schoolSemester.getAcadyear() });
			promptMessageDto.addHiddenText(new String[] { "semester",
					schoolSemester.getSemester() });
			promptMessageDto
					.addHiddenText(new String[] {
							"workbegin",
							DateUtils.date2StringByDay(schoolSemester
									.getWorkbegin()) });
			promptMessageDto.addHiddenText(new String[] {
					"semesterbegin",
					DateUtils.date2StringByDay(schoolSemester
							.getSemesterbegin()) });
			promptMessageDto
					.addHiddenText(new String[] {
							"registerdate",
							DateUtils.date2StringByDay(schoolSemester
									.getRegisterdate()) });
			promptMessageDto
					.addHiddenText(new String[] {
							"semesterend",
							DateUtils.date2StringByDay(schoolSemester
									.getSemesterend()) });
			promptMessageDto.addHiddenText(new String[] { "workend",
					DateUtils.date2StringByDay(schoolSemester.getWorkend()) });

			promptMessageDto.addHiddenText(new String[] { "edudays",
					Short.toString(schoolSemester.getEdudays()) });
			promptMessageDto.addHiddenText(new String[] { "classhour",
					Short.toString(schoolSemester.getClasshour()) });
			promptMessageDto.addHiddenText(new String[] { "amperiods",
					Short.toString(schoolSemester.getAmperiods()) });
			promptMessageDto.addHiddenText(new String[] { "pmperiods",
					Short.toString(schoolSemester.getPmperiods()) });
			promptMessageDto.addHiddenText(new String[] { "nightperiods",
					Short.toString(schoolSemester.getNightperiods()) });

			promptMessageDto.addOperation(new String[] { "确定",
					"basicSemesterAdmin-reEdit.action" });
			promptMessageDto.addOperation(new String[] { "取消",
					"basicSemesterAdmin.action" });
		}

		promptMessageDto.addHiddenText(new String[] { "ec_p", this.ec_p });
		promptMessageDto.addHiddenText(new String[] { "ec_crd", this.ec_crd });
		
//		try {
//            if(promptMessageDto.getOperateSuccess()){
//            	baseGradeService.initGrades(getLoginInfo().getUnitID());
//            }
//        } catch (OperationNotAllowedException e) {
//        	promptMessageDto.setOperateSuccess(false);
//            promptMessageDto.setPromptMessage(e.getMessage());
//        }
		return SUCCESS;
	}

	//再次打开编辑页面（用于保存失败时返回原来的编辑页面）
	public String reEdit() {
		log.info("Action basicSemesterAdmin-reEdit reEdit().....");

		return SUCCESS;
	}

	public String getCntid() {
		return cntid;
	}

	public List<SchoolSemester> getSemesterList() {
		return semesterList;
	}

	public String getCurrentadadyear() {
		return currentadadyear;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public String getSemesterHtml() {
		return semesterHtml;
	}

	public void setSemesterHtml(String semesterHtml) {
		this.semesterHtml = semesterHtml;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}

	public void setSemesterid(String semesterid) {
		this.semesterid = semesterid;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

	public String getSchId() {
		return schId;
	}

	public void setSchId(String schId) {
		this.schId = schId;
	}

	public void setBaseSchoolSemesterService(
			BaseSchoolSemesterService baseSchoolSemesterService) {
		this.baseSchoolSemesterService = baseSchoolSemesterService;
	}

	public void setBaseGradeService(BaseGradeService baseGradeService) {
		this.baseGradeService = baseGradeService;
	}

	public SchoolSemester getSchoolSemester() {
		return schoolSemester;
	}

	public void setSchoolSemester(SchoolSemester schoolSemester) {
		this.schoolSemester = schoolSemester;
	}

	public boolean isGradeUpgrade() {
		return gradeUpgrade;
	}

	public void setGradeUpgrade(boolean gradeUpgrade) {
		this.gradeUpgrade = gradeUpgrade;
	}

	public String getGradeUpgradeDate() {
		return gradeUpgradeDate;
	}

	public void setGradeUpgradeDate(String gradeUpgradeDate) {
		this.gradeUpgradeDate = gradeUpgradeDate;
	}
	public void setBaseSystemIniService(BaseSystemIniService baseSystemIniService) {
		this.baseSystemIniService = baseSystemIniService;
	}
	public void setAcadyearHtml(String acadyearHtml) {
		this.acadyearHtml = acadyearHtml;
	}
	public String getAcadyearHtml() {
		return acadyearHtml;
	}
	public void setYearsList(List<String> yearsList) {
		this.yearsList = yearsList;
	}
	public List<String> getYearsList() {
		return yearsList;
	}

}
