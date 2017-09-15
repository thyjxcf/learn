package net.zdsoft.office.dailyoffice.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dailyoffice.entity.OfficeSignedOn;
import net.zdsoft.office.dailyoffice.entity.OfficeSigntimeSet;
import net.zdsoft.office.dailyoffice.service.OfficeSignedOnService;
import net.zdsoft.office.dailyoffice.service.OfficeSigntimeSetService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

/**
* @Package net.zdsoft.office.dailyoffice.action 
* @author songxq  
* @date 2016-8-8 下午3:26:53 
* @version V1.0
 */
@SuppressWarnings("serial")
public class SignedOnAction extends PageSemesterAction{
	private DeptService deptService;
	private OfficeSignedOnService officeSignedOnService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeSigntimeSetService officeSigntimeSetService;
	
	private List<Dept> deptList;
	private String currentDeptId;//当前部门id
	private String years;
	private String semesters;
	private String startTime;
	private String endTime;
	private String signed;
	private boolean officeSignOn=false;
	private OfficeSignedOn officeSignedOn=new OfficeSignedOn();
	private List<OfficeSignedOn> officeSignedOnList=new ArrayList<OfficeSignedOn>();
	private OfficeSigntimeSet officeSigntimeSet;
	
	public static final String OFFICE_SIGN_MANAGE="office_sign_manage";
	
	@Override
	public String execute() throws Exception{
		return SUCCESS;
	}
	public String signmanage(){
		deptList=deptService.getDepts(getUnitId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar c = Calendar.getInstance();
		 startTime=df.format(c.getTime());
		return SUCCESS;
	}
	public String signmanageList(){
		if(isCustomRole(OFFICE_SIGN_MANAGE)){
			officeSignedOnList=officeSignedOnService.getOfficeSignedOnByUnitIdDeptPage(years, semesters, startTime, currentDeptId, signed, getLoginInfo().getUnitID(), null);
		}else{
			officeSignedOnList=officeSignedOnService.getOfficeSignedOnByUnitIdTimePage(getLoginUser().getUserId(), years, semesters, startTime, getLoginInfo().getUnitID(),null);
		}
		//重新分页
		Pagination page = getPage();
		//page.setPageSize(2);
		Integer maxRow = officeSignedOnList.size();
		page.setMaxRowCount(maxRow);
		page.initialize();
		if(CollectionUtils.isNotEmpty(officeSignedOnList)){
			Integer oldCur = page.getCurRowNum();
			Integer newCur = page.getPageIndex()*page.getPageSize();
			newCur = newCur>maxRow?maxRow:newCur;
			List<OfficeSignedOn> newList = new ArrayList<OfficeSignedOn>();
			
			newList.addAll(officeSignedOnList.subList(oldCur-1, newCur));
			officeSignedOnList = newList;
		}
		
		return SUCCESS;
	}
	public String signmanageSave(){
		try {
			officeSignedOn.setUnitId(getUnitId());
			officeSignedOn.setCreateTime(new Date());
			officeSignedOn.setCreateUserId(getLoginUser().getUserId());
			String years = this.getCurrentSemester().getAcadyear();
			officeSignedOn.setYear(years);
			officeSignedOn.setSemester(Integer.valueOf(this.getCurrentSemester().getSemester()));
			if(StringUtils.isBlank(officeSignedOn.getId())){
				officeSignedOnService.save(officeSignedOn);
			}else{
				officeSignedOnService.update(officeSignedOn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	public String signmanageCount(){
		deptList=deptService.getDepts(getUnitId());
		if(StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)){
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 Calendar c = Calendar.getInstance();
			 endTime = df.format(c.getTime());
			 c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			 startTime = df.format(c.getTime());
		}
		return SUCCESS;
	}
	public String signmanageCountList(){
		if(isOfficeSignOn()){
			officeSignedOnList=officeSignedOnService.getOfficeSignedOnCountByUnitIdDeptPage(years, semesters, startTime, endTime, currentDeptId, getLoginInfo().getUnitID(), getPage());
			//将list按照次数大小排序
			Collections.sort(officeSignedOnList, new Comparator<OfficeSignedOn>() {
				@Override
				public int compare(OfficeSignedOn o1, OfficeSignedOn o2) {
					Integer t1 = Integer.parseInt(StringUtils.isEmpty(o1.getCount())?"0":o1.getCount());
					Integer t2 = Integer.parseInt(StringUtils.isEmpty(o2.getCount())?"0":o2.getCount());
					return t2.compareTo(t1);
				}
			});
		}else{
			officeSignedOnList=officeSignedOnService.getOfficeSignedOnCountByUnitIdTimePage(getLoginUser().getUserId(), years, semesters, startTime, endTime, getLoginUser().getUnitId(),getPage());
		}
		return SUCCESS;
	}
	
	/**
	 * 导出
	 * @return
	 */
	public String doExport(){
		if(isOfficeSignOn()){
			officeSignedOnList=officeSignedOnService.getOfficeSignedOnCountByUnitIdDeptPage(years, semesters, startTime, endTime, currentDeptId, getLoginInfo().getUnitID(), null);
			//将list按照次数大小排序
			Collections.sort(officeSignedOnList, new Comparator<OfficeSignedOn>() {
				@Override
				public int compare(OfficeSignedOn o1, OfficeSignedOn o2) {
					Integer t1 = Integer.parseInt(StringUtils.isEmpty(o1.getCount())?"0":o1.getCount());
					Integer t2 = Integer.parseInt(StringUtils.isEmpty(o2.getCount())?"0":o2.getCount());
					return t2.compareTo(t1);
				}
			});
		}else{
			officeSignedOnList=officeSignedOnService.getOfficeSignedOnCountByUnitIdTimePage(getLoginUser().getUserId(), years, semesters, startTime, endTime, getLoginUser().getUnitId(),null);
		}
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		
		zdlist.add(new ZdCell("编号",1,style2));
		zdlist.add(new ZdCell("姓名",1,style2));
		zdlist.add(new ZdCell("部门",1,style2));
		zdlist.add(new ZdCell("签到次数",1,style2));
		
		zdExcel.add(new ZdCell("签到统计", zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		int j = 1;
		for(OfficeSignedOn item : officeSignedOnList){
			int index = 0;
			ZdCell[] cells = new ZdCell[zdlist.size()];
			cells[index++] = new ZdCell(j+"", 1, style3);
			cells[index++] = new ZdCell(item.getUserName(), 1, style3);
			cells[index++] = new ZdCell(item.getDeptName(), 1, style3);
			cells[index++] = new ZdCell(item.getCount(), 1, style3);
			j++;
			zdExcel.add(cells);
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("attendance_statistics");
		return NONE;
	}
	
	public String signmanageTimeSet(){
		List<OfficeSigntimeSet> officeSigntimeSetList=officeSigntimeSetService.getOfficeSigntimeSetByUnitIdList(getLoginInfo().getUnitID());
		if(CollectionUtils.isNotEmpty(officeSigntimeSetList)){
			officeSigntimeSet=officeSigntimeSetList.get(0);
		}
		if(officeSigntimeSet==null){
			officeSigntimeSet=new OfficeSigntimeSet();
		}
		return SUCCESS;
	}
	public String signmanageTimeSetSave(){
		try {
			officeSigntimeSet.setUnitId(getUnitId());
			if(StringUtils.isBlank(officeSigntimeSet.getId())){
				officeSigntimeSetService.save(officeSigntimeSet);
			}else{
				officeSigntimeSetService.update(officeSigntimeSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
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
	public boolean isOfficeSignOn(){
		return isCustomRole(OFFICE_SIGN_MANAGE);
	}
	
	public void setOfficeSignOn(boolean officeSignOn) {
		this.officeSignOn = officeSignOn;
	}
	public List<String> getYearList() {
		List<String> acadyears = new ArrayList<String>();
		String currentYear = DateUtils.date2String(new Date(), "yyyy");
		acadyears.add(Integer.parseInt(currentYear)+3+"-"+(Integer.parseInt(currentYear)+4));
		acadyears.add(Integer.parseInt(currentYear)+2+"-"+(Integer.parseInt(currentYear)+3));
		acadyears.add(Integer.parseInt(currentYear)+1+"-"+(Integer.parseInt(currentYear)+2));
		acadyears.add(Integer.parseInt(currentYear)+"-"+(Integer.parseInt(currentYear)+1));
		acadyears.add(Integer.parseInt(currentYear)-1+"-"+Integer.parseInt(currentYear));
		acadyears.add(Integer.parseInt(currentYear)-2+"-"+(Integer.parseInt(currentYear)-1));
		acadyears.add(Integer.parseInt(currentYear)-3+"-"+(Integer.parseInt(currentYear)-2));
		return acadyears;
	}
	public String getYear() {
		if (StringUtils.isBlank(years)) {
			return this.getCurrentSemester().getAcadyear();
		}
		return years;
	}
	public String getSemester() {
		if (StringUtils.isBlank(semesters)) {
			return this.getCurrentSemester().getSemester();
		}
		return semesters;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getYears() {
		return years;
	}
	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getCurrentDeptId() {
		return currentDeptId;
	}

	public void setCurrentDeptId(String currentDeptId) {
		this.currentDeptId = currentDeptId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public OfficeSignedOn getOfficeSignedOn() {
		return officeSignedOn;
	}
	public void setOfficeSignedOn(OfficeSignedOn officeSignedOn) {
		this.officeSignedOn = officeSignedOn;
	}
	public List<OfficeSignedOn> getOfficeSignedOnList() {
		return officeSignedOnList;
	}
	public void setOfficeSignedOnList(List<OfficeSignedOn> officeSignedOnList) {
		this.officeSignedOnList = officeSignedOnList;
	}
	public String getSemesters() {
		return semesters;
	}
	public void setSemesters(String semesters) {
		this.semesters = semesters;
	}
	public void setOfficeSignedOnService(OfficeSignedOnService officeSignedOnService) {
		this.officeSignedOnService = officeSignedOnService;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public String getSigned() {
		return signed;
	}
	public void setSigned(String signed) {
		this.signed = signed;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setOfficeSigntimeSetService(
			OfficeSigntimeSetService officeSigntimeSetService) {
		this.officeSigntimeSetService = officeSigntimeSetService;
	}
	public OfficeSigntimeSet getOfficeSigntimeSet() {
		return officeSigntimeSet;
	}
	public void setOfficeSigntimeSet(OfficeSigntimeSet officeSigntimeSet) {
		this.officeSigntimeSet = officeSigntimeSet;
	}
	
}
