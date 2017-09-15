package net.zdsoft.office.dailyoffice.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportTl;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportTlService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
/**
* @Package net.zdsoft.office.dailyoffice.action 
* @author songxq  
* @date 2016-4-28 下午2:41:07 
* @version V1.0
 */
public class WorkReportTlAction extends PageSemesterAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 工作汇报，人员权限设置
	 */
	public static final String WORK_REPORT_TL = "work_report_tl";
	private String years;
	private String semesters;
	private String weeks;
	private String contents;
	private String states;
	private String createUserName;
	private OfficeWorkReportTlService officeWorkReportTlService;
	private UnitService unitService;
	private UserService userService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private List<OfficeWorkReportTl> officeWorkReportList=new ArrayList<OfficeWorkReportTl>();
	private OfficeWorkReportTl officeWorkReportTl=new OfficeWorkReportTl();
	private boolean workReportTl;
	public String execute() throws Exception {
		workReportTl = isCustomRole(WORK_REPORT_TL);
		return SUCCESS;
	}
	public String myWorkReport(){
		
		return SUCCESS;
	}
	public String myWorkReportList(){
		officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportByUnitIdPageContent(getUnitId(), getPage(), getLoginUser().getUserId(),years, semesters, weeks,contents,states);
		return SUCCESS;
	}
	
	public String workReportExport(){
		officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportByUnitIdPageContent(getUnitId(), null, getLoginUser().getUserId(),years, semesters, weeks,contents,states);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("工作汇报",4, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell> zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("学年",1,style2));
		zdlist.add(new ZdCell("学期",1,style2));
		zdlist.add(new ZdCell("周次",1,style2));
		zdlist.add(new ZdCell("汇报内容",1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for (OfficeWorkReportTl officeWorkReportTl : officeWorkReportList) {
			zdlist=new ArrayList<ZdCell>();
			zdlist.add(new ZdCell(officeWorkReportTl.getYear(), 1, style3));
			zdlist.add(new ZdCell("第"+officeWorkReportTl.getSemester()+"学期", 1, style3));
			zdlist.add(new ZdCell("第"+officeWorkReportTl.getWeek()+"周", 1, style3));
			zdlist.add(new ZdCell(officeWorkReportTl.getContent(), 1, new ZdStyle(ZdStyle.BORDER|ZdStyle.WRAP_TEXT)));
			zdExcel.add(zdlist.toArray(new ZdCell[0]));
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("office_workReport_export");
		return NONE;
	}
	public String myWorkReportEdit(){
		if(StringUtils.isNotBlank(officeWorkReportTl.getId())){
			officeWorkReportTl=officeWorkReportTlService.getOfficeWorkReportTlById(officeWorkReportTl.getId());
		}
		return SUCCESS;
	}
	
	public String myWorkReportSave(){
		try {
			List<OfficeWorkReportTl> officeWorkReportTls=officeWorkReportTlService.getOfficeWorkReportTlByUnitId(getLoginUser().getUnitId(),getLoginUser().getUserId(), officeWorkReportTl.getYear(), String.valueOf(officeWorkReportTl.getSemester()), String.valueOf(officeWorkReportTl.getWeek()));
			if(StringUtils.isBlank(officeWorkReportTl.getId())){
				if(CollectionUtils.isEmpty(officeWorkReportTls)){
					officeWorkReportTl.setUnitId(getUnitId());
					officeWorkReportTl.setCreateUserId(getLoginUser().getUserId());
					officeWorkReportTl.setCreateTime(new Date());
					officeWorkReportTl.setUnitClass(getLoginInfo().getUnitClass());
					Unit unit = unitService.getUnit(getLoginInfo().getUnitID());
					officeWorkReportTl.setParentUnitId(unit.getParentid());
					officeWorkReportTl.setUnitOrderId(unit.getOrderid()+"");
					User user=userService.getUser(getLoginUser().getUserId());
					officeWorkReportTl.setTeacherOrderId(Integer.valueOf(user.getOrderid().toString()));
					officeWorkReportTlService.save(officeWorkReportTl);
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("操作成功!");
				}else{
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该周次的工作汇报已存在！");
					//addActionError("该工作汇报在第"+officeWorkReportTl.getWeek()+" 周已被提交！");
				}
			}else{
				boolean canUpdate = true;
				if(CollectionUtils.isEmpty(officeWorkReportTls)){
					officeWorkReportTl.setCreateTime(new Date());
					officeWorkReportTlService.update(officeWorkReportTl);
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("操作成功!");
				}else{
					for (OfficeWorkReportTl oTl : officeWorkReportTls) {
						if(!StringUtils.equals(oTl.getId(), officeWorkReportTl.getId())){
							canUpdate = false;
							break;
						}
				}
					if(canUpdate){
						officeWorkReportTl.setCreateTime(new Date());
						officeWorkReportTlService.update(officeWorkReportTl);
						promptMessageDto.setOperateSuccess(true);
						promptMessageDto.setPromptMessage("操作成功!");
					}	
					else{
						promptMessageDto.setOperateSuccess(false);
						promptMessageDto.setErrorMessage("该周次的工作汇报已存在！");
						//addActionError("该工作汇报在第"+officeWorkReportTl.getWeek()+" 周已被提交！");
					}
			}
		}
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败："+e.getMessage());
		}
		
		return SUCCESS;
	}
	public String myWorkReportSubmit(){
		try {
			officeWorkReportTl=officeWorkReportTlService.getOfficeWorkReportTlById(officeWorkReportTl.getId());
			officeWorkReportTl.setState(2);
			officeWorkReportTlService.update(officeWorkReportTl);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功!");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("提交失败:"+e.getMessage());
		}
		return SUCCESS;
	}
	public String myWorkReportView(){
		officeWorkReportTl=officeWorkReportTlService.getOfficeWorkReportTlById(officeWorkReportTl.getId());
		return SUCCESS;
	}
	
	public String cancel(){
		try {
			officeWorkReportTl=officeWorkReportTlService.getOfficeWorkReportTlById(officeWorkReportTl.getId());
			officeWorkReportTl.setState(8);
			officeWorkReportTlService.update(officeWorkReportTl);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("撤回成功!");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("撤回失败！");
		}
		return SUCCESS;
	}
	
	public String delete(){
		try {
			String officeWorkReportId=officeWorkReportTl.getId();
			officeWorkReportTlService.delete(new String[]{officeWorkReportId});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功!");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败："+e.getMessage());
		}
		return SUCCESS;
	}
	public String workReportSearch(){
		return SUCCESS;
	}
	public String workReportSearchList(){
		officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportTlByUnitIdPageContentCreateUserName(getPage(), years, semesters, weeks, contents, createUserName);
		return SUCCESS;
	}
	
	public String workReportSearchExport(){
		officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportTlByUnitIdPageContentCreateUserName(null, years, semesters, weeks, contents, createUserName);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("工作汇报",7, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell> zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("学年",1,style2));
		zdlist.add(new ZdCell("学期",1,style2));
		zdlist.add(new ZdCell("周次",1,style2));
		zdlist.add(new ZdCell("汇报内容",1,style2));
		zdlist.add(new ZdCell("汇报人",1,style2));
		zdlist.add(new ZdCell("单位类别",1,style2));
		zdlist.add(new ZdCell("汇报人单位",1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for (OfficeWorkReportTl officeWorkReportTl : officeWorkReportList) {
			zdlist=new ArrayList<ZdCell>();
			zdlist.add(new ZdCell(officeWorkReportTl.getYear(), 1, style3));
			zdlist.add(new ZdCell("第"+officeWorkReportTl.getSemester()+"学期", 1, style3));
			zdlist.add(new ZdCell("第"+officeWorkReportTl.getWeek()+"周", 1, style3));
			zdlist.add(new ZdCell(officeWorkReportTl.getContent(), 1, new ZdStyle(ZdStyle.BORDER|ZdStyle.WRAP_TEXT)));
			zdlist.add(new ZdCell(officeWorkReportTl.getCreateUserName()));
			zdlist.add(new ZdCell(officeWorkReportTl.getUnitClass()==1?"教育局":"学校"));
			zdlist.add(new ZdCell(officeWorkReportTl.getUnitName()));
			zdExcel.add(zdlist.toArray(new ZdCell[0]));
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("office_workReport_export");
		return NONE;
	}
	
	public String workReportSearchView(){
		officeWorkReportTl=officeWorkReportTlService.getOfficeWorkReportTlById(officeWorkReportTl.getId());
		String unitId=officeWorkReportTl.getUnitId();
		String unitName=unitService.getUnit(unitId).getName();
		officeWorkReportTl.setUnitName(unitName);
		String crateUserId=officeWorkReportTl.getCreateUserId();
		officeWorkReportTl.setCreateUserName(userService.getUser(crateUserId).getRealname());
		return SUCCESS;
	}
	public String workReportSearchEdit(){
		if(StringUtils.isNotBlank(officeWorkReportTl.getId())){
			officeWorkReportTl=officeWorkReportTlService.getOfficeWorkReportTlById(officeWorkReportTl.getId());
		}
		return SUCCESS;
	}
	public String workReportSearchSave(){
		try {
			List<OfficeWorkReportTl> officeWorkReportTls=officeWorkReportTlService.getOfficeWorkReportTlByUnitId(officeWorkReportTl.getUnitId(),officeWorkReportTl.getCreateUserId(), officeWorkReportTl.getYear(), String.valueOf(officeWorkReportTl.getSemester()), String.valueOf(officeWorkReportTl.getWeek()));
			boolean canUpdate = true;
			if(CollectionUtils.isEmpty(officeWorkReportTls)){
				officeWorkReportTl.setModifyTime(new Date());
				officeWorkReportTl.setModifyUserId(getLoginUser().getUserId());
				officeWorkReportTlService.update(officeWorkReportTl);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功!");
			}else{
				for (OfficeWorkReportTl oTl : officeWorkReportTls) {
					if(!StringUtils.equals(oTl.getId(), officeWorkReportTl.getId())){
						canUpdate = false;
						break;
					}
			}
			if(canUpdate){
				officeWorkReportTl.setModifyTime(new Date());
				officeWorkReportTl.setModifyUserId(getLoginUser().getUserId());
				officeWorkReportTlService.update(officeWorkReportTl);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功!");
			}else{
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该周次的工作汇报已存在！");
				}
			}
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败："+e.getMessage());
		}
		
		return SUCCESS;
	}
	public List<String> getWeekTimeList(){
		List<String> list=new ArrayList<String>();
		  for(int i = 1; i < 27; i++){
		    list.add(String.valueOf(i));
		 }
		return  list;
	}
	public List<String> getYearList() {
		List<String> acadyears = new ArrayList<String>();
		String currentYear = DateUtils.date2String(new Date(), "yyyy");
		acadyears.add(Integer.parseInt(currentYear)+3+"");
		acadyears.add(Integer.parseInt(currentYear)+2+"");
		acadyears.add(Integer.parseInt(currentYear)+1+"");
		acadyears.add(Integer.parseInt(currentYear)+"");
		acadyears.add(Integer.parseInt(currentYear)-1+"");
		acadyears.add(Integer.parseInt(currentYear)-2+"");
		acadyears.add(Integer.parseInt(currentYear)-3+"");
		return acadyears;
	}
	public String getYear() {
		if (StringUtils.isBlank(years)) {
			years = getCurrentSemester().getAcadyear().split("-")[0];
		}
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}
	public String getWeek() {
        int i;
		if (StringUtils.isBlank(weeks)) {
			weeks = officeWorkReportTlService.findMaxWeek(getLoginUser().getUserId(),this.getCurrentSemester().getSemester(),getUnitId(),null);
            if (weeks == null || weeks.equals("")) {
                weeks = "1";
            }
            else {
                i = Integer.parseInt(weeks);
                weeks = Integer.toString(i + 1);
            }
		}
		return weeks;
	}
	public String getSearchWeek() {
		int i;
		if (StringUtils.isBlank(weeks)) {
			weeks = officeWorkReportTlService.findMaxWeek(null,this.getCurrentSemester().getSemester(),null,"2");
			if (weeks == null || weeks.equals("")) {
				weeks = "1";
			}
			else {
				i = Integer.parseInt(weeks);
				weeks = Integer.toString(i);
			}
		}
		return weeks;
	}
	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isCustomRole(String roleCode){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), roleCode);
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
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	
	public String getYears() {
		return years;
	}
	public String getSemesters() {
		return semesters;
	}
	public String getWeeks() {
		return weeks;
	}
	public String getSemester() {
		if (StringUtils.isBlank(semesters)) {
			return this.getCurrentSemester().getSemester();
		}
		return semesters;
	}

	public void setSemesters(String semesters) {
		this.semesters = semesters;
	}
	public OfficeWorkReportTlService getOfficeWorkReportTlService() {
		return officeWorkReportTlService;
	}
	public void setOfficeWorkReportTlService(OfficeWorkReportTlService officeWorkReportTlService) {
		this.officeWorkReportTlService = officeWorkReportTlService;
	}
	public List<OfficeWorkReportTl> getOfficeWorkReportList() {
		return officeWorkReportList;
	}
	public void setOfficeWorkReportList(List<OfficeWorkReportTl> officeWorkReportList) {
		this.officeWorkReportList = officeWorkReportList;
	}
	public OfficeWorkReportTl getOfficeWorkReportTl() {
		return officeWorkReportTl;
	}
	public void setOfficeWorkReportTl(OfficeWorkReportTl officeWorkReportTl) {
		this.officeWorkReportTl = officeWorkReportTl;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents= contents;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public boolean isWorkReportTl() {
		return workReportTl;
	}
	public void setWorkReportTl(boolean workReportTl) {
		this.workReportTl = workReportTl;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	
}
