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
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportTl;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportTlService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
					officeWorkReportTl.setUnitOrderId(unit.getOrderid());
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
			years = DateUtils.date2String(new Date(), "yyyy");
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
