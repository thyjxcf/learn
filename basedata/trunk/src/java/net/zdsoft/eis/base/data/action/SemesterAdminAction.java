package net.zdsoft.eis.base.data.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.data.service.BaseSemesterService;
import net.zdsoft.eis.base.data.service.BaseSystemIniService;
import net.zdsoft.eis.base.subsystemcall.service.SubsystemCallService;
import net.zdsoft.eis.base.subsystemcall.util.SubsystemLoadHelper;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.keel.util.DateUtils;

import com.opensymphony.xwork2.ModelDriven;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: JwSemesterAdminAction.java,v 1.6 2007/01/11 04:07:02 zhanghh Exp $
 */
public class SemesterAdminAction extends BaseAction implements ModelDriven<Semester> {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2808665304921775452L;
	private BaseSemesterService baseSemesterService;
	private McodeService mcodeService;
	// 传到页面上去的信息
	protected String cntid; // 当前学年学期的ID
	protected List<Semester> semesterList; // 学年列表
	protected String currentadadyear; // 当前学年学期
	protected List<String[]> semesterMcodeList; // 学期微代码数组列表
	protected String semesterName; // 学期名称（取自微代码context）
	protected String semesterHtml; // 学期微代码页面显示选择框
	protected String[] checkid;
	// 学校主键GUID，直接从logInfo中取得
	protected String schid;
	private String operType = ""; // 操作类型
	private Semester jwsemester = new Semester();

	//年级升级开关 
	private BaseSystemIniService baseSystemIniService;
	private boolean gradeUpgrade = false;
	private String gradeUpgradeDate;//下次升级时间
    private static final String STUSYS_AUTOMATIC_UPGRADE = "STUSYS.AUTOMATIC.UPGRADE";//自动升级
    private static final String STUSYS_AUTOMATIC_UPGRADE_START_TIME = "STUSYS.AUTOMATIC.UPGRADE.START.TIME";
    //private static final String STUSYS_AUTOMATIC_UPGRADE_END_TIME = "STUSYS.AUTOMATIC.UPGRADE.END.TIME";
	
	
	/**
	 * 是否是顶级教育局
	 */
	public boolean isUnitEduTop() {
		LoginInfo loginInfo = this.getLoginInfo();
		if (Unit.UNIT_EDU_TOP == loginInfo.getUnitType().intValue()) {
			return true;
		} else {
			return false;
		}
	}

	public Semester getModel() {
		return jwsemester;
	}

	// 打开学年学期主页面，即列表页面
	public String execute() throws Exception {
		log.info("Action SemesterAdmin execute()......");

		schid = this.getLoginInfo().getUnitID();

		if (schid == null || "".equals(schid)) {
			this.addActionError("没有取到单位（学校）GUID编号！");
			return ERROR;
		}

		// 得到学期列表
		semesterList = baseSemesterService.getSemesters();

		// 当前学期的DTO
		CurrentSemester sem = baseSemesterService.getCurrentSemester();
		if (sem == null) {
			currentadadyear = null;
		} else {
			jwsemester = baseSemesterService.getSemester(sem.getAcadyear(), sem
					.getSemester());

			cntid = jwsemester.getId();
			currentadadyear = jwsemester.getAcadyear()
					+ mcodeService.getMcodeContext("DM-XQXN", jwsemester
							.getSemester());
		}
		semesterMcodeList = mcodeService.getMcodeAarray("DM-XQXN");

		//年级升级开关
		SystemIni systemini= systemIniService.getSystemIni(STUSYS_AUTOMATIC_UPGRADE);
		if("Y".equals(systemini.getNowValue())){
			gradeUpgrade = true;
			String graStartDate= baseSystemIniService.getValue(STUSYS_AUTOMATIC_UPGRADE_START_TIME);
			String[] md = graStartDate.split("-");
			setGradeUpgradeDate(md[0]+"月"+md[1]+"日");
		}
				
		return SUCCESS;
	}

	// 新增
	public String add() throws Exception {
		operType = "new";
		log.info("Action SemesterAdd add()......");

		schid = this.getLoginInfo().getUnitID();

		if (schid == null || "".equals(schid)) {
			this.addActionError("没有取到单位（学校）Id编号！");
			return ERROR;
		}

		// 得到要增加的下一个学年学期
		jwsemester.setId(null);
		jwsemester = baseSemesterService.getDefaultSemester();

		return SUCCESS;
	}

	// 编辑
	public String edit() throws Exception {
		log.info("Action SemesterEdit edit()......");
		operType = "edit";
		jwsemester = baseSemesterService.getSemester(jwsemester.getId());

		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
        if (checkid != null && checkid.length > 0) {
            for (String id : checkid) {
                Semester jwSemesterDto = baseSemesterService.getSemester(id);
                if (jwSemesterDto != null) {
                    String acadyear = jwSemesterDto.getAcadyear();
                    if (acadyear != null) {
                        List<SubsystemCallService> callServices = SubsystemLoadHelper.getSubsystemCallServices();
                        for (SubsystemCallService subsystemCallService : callServices) {
                            String tip = subsystemCallService.isSemesterRef(acadyear);
                            if (null != tip) {
                            	promptMessageDto.setOperateSuccess(false);
                                promptMessageDto.setPromptMessage(acadyear + "学年已经存在" + tip
                                        + "，不能删除。");
                                return SUCCESS;
                            }
                        }
                    }
                }
            }
            try {
                baseSemesterService.deleteSemester(checkid, EventSourceType.LOCAL);
            } catch (Exception ex) {
                promptMessageDto.setOperateSuccess(false);
                promptMessageDto.setPromptMessage("未知错误原因，请查看日志！");
                log.error("删除教育局端学年学期失败：" + ex.getMessage());
            }
            promptMessageDto.setOperateSuccess(true);
            promptMessageDto.setPromptMessage("删除提示：成功删除！");
        }
        return SUCCESS;
    }

	// 保存
	public String save() throws Exception {
		try {
            baseSemesterService.saveSemester(jwsemester);
        } catch (Exception e) {
            promptMessageDto.setErrorMessage(e.getMessage());
            promptMessageDto.setOperateSuccess(false);

            if (jwsemester.getId() == null || "".equals(jwsemester.getId())) {
                // 新增 组合学期选择框
                semesterHtml = mcodeService.getMcode("DM-XQXN")
                        .getHtmlTag(jwsemester.getSemester());
                
                promptMessageDto.addHiddenText(new String[] { "semesterHtml", semesterHtml });
            } else {
                // 编辑 学期微代码名称
                semesterName = mcodeService.getMcodeContext("DM-XQXN", jwsemester.getSemester());
                
                promptMessageDto.addHiddenText(new String[] { "semesterName", semesterName });
                promptMessageDto.addHiddenText(new String[] { "id", jwsemester.getId() });
            }
            promptMessageDto.addHiddenText(new String[] { "acadyear", jwsemester.getAcadyear() });
            promptMessageDto.addHiddenText(new String[] { "semester", jwsemester.getSemester() });
            promptMessageDto.addHiddenText(new String[] { "workBegin",
                    DateUtils.date2StringByDay(jwsemester.getWorkBegin()) });
            promptMessageDto.addHiddenText(new String[] { "workEnd",
                    DateUtils.date2StringByDay(jwsemester.getWorkEnd()) });

            promptMessageDto.addOperation(new String[] { "确定", "semesterAdmin-reEdit.action" });
            if (jwsemester.getId() != null && !"".equals(jwsemester.getId())){
            	promptMessageDto.addOperation(new String[] { "取消", "semesterAdmin.action" });
            }
            return SUCCESS;
        }
        promptMessageDto.setOperateSuccess(true);
        promptMessageDto.setPromptMessage("保存学年学期信息成功！");
        promptMessageDto.addOperation(new String[] { "返回", "semesterAdmin.action" });
        promptMessageDto.addOperation(new String[] { "新增下一个", "semesterAdmin-add.action" });
		return SUCCESS;
	}

	// 再次打开编辑页面（用于保存失败时返回原来的编辑页面）
	public String reEdit() {
		log.info("Action SemesterReEdit reEdit().....");

		return SUCCESS;
	}

	public String[] getCheckid() {
		return checkid;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}

	public String getCurrentadadyear() {
		return currentadadyear;
	}

	public void setCurrentadadyear(String currentadadyear) {
		this.currentadadyear = currentadadyear;
	}

	public String getSemesterHtml() {
		return semesterHtml;
	}

	public void setSemesterHtml(String semesterHtml) {
		this.semesterHtml = semesterHtml;
	}

	public List<Semester> getSemesterList() {
		return semesterList;
	}

	public void setSemesterList(List<Semester> semesterList) {
		this.semesterList = semesterList;
	}

	public List<String[]> getSemesterMcodeList() {
		return semesterMcodeList;
	}

	public void setSemesterMcodeList(List<String[]> semesterMcodeList) {
		this.semesterMcodeList = semesterMcodeList;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	public String getCntid() {
		return cntid;
	}

	public void setCntid(String cntid) {
		this.cntid = cntid;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public void setBaseSemesterService(BaseSemesterService baseSemesterService) {
		this.baseSemesterService = baseSemesterService;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

	public void setBaseSystemIniService(BaseSystemIniService baseSystemIniService) {
		this.baseSystemIniService = baseSystemIniService;
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

}
