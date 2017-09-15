package net.zdsoft.office.dutyinformation.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyApply;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSet;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyPlace;
import net.zdsoft.office.dutyinformation.entity.OfficePatrolPlace;
import net.zdsoft.office.dutyinformation.service.OfficeDutyApplyService;
import net.zdsoft.office.dutyinformation.service.OfficeDutyInformationSetService;
import net.zdsoft.office.dutyinformation.service.OfficeDutyPlaceService;
import net.zdsoft.office.dutyinformation.service.OfficePatrolPlaceService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * 
* @Package net.zdsoft.office.dutyinformation.action 
* @author songxq  
* @date 2017-6-19 下午2:21:26 
* @version V1.0
 */
@SuppressWarnings("serial")
public class OfficeDutyInformationSetAction extends PageSemesterAction{
	
	private OfficeDutyInformationSetService officeDutyInformationSetService;
	private OfficeDutyApplyService officeDutyApplyService;
	private OfficePatrolPlaceService officePatrolPlaceService;
	private OfficeDutyPlaceService officeDutyPlaceService;
	private TeacherService teacherService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private UserService userService;
	
	private OfficeDutyInformationSet officeDutyInformationSet=new OfficeDutyInformationSet();
	private List<OfficeDutyInformationSet> officeDutyInformationSets=new ArrayList<OfficeDutyInformationSet>();
	private List<OfficePatrolPlace> officePatrolPlaces=new ArrayList<OfficePatrolPlace>();
	private OfficePatrolPlace officePatrolPlace=new OfficePatrolPlace();
	private OfficeDutyPlace officeDutyPlace=new OfficeDutyPlace();
	private List<OfficeDutyPlace> officeDutyPlaces=new ArrayList<OfficeDutyPlace>();
	private Map<String,OfficeDutyPlace> officeDutyPlaceMap=new HashMap<String, OfficeDutyPlace>();
	private List<OfficeDutyApply> officeDutyApplies=new ArrayList<OfficeDutyApply>();
	private OfficeDutyApply officeDutyApply=new OfficeDutyApply();
	private String years;
	private String dutyName;
	private String dutyId;
	
	private Map<String, OfficeDutyApply> applyMap=new HashMap<String, OfficeDutyApply>();//申请信息
	private List<String> periods=new ArrayList<String>();//上下午
	private Map<String,String> periodMap=new HashMap<String, String>();
	private List<String> dateList=new ArrayList<String>();//报名日期列表
	private String userName;//用户名电话
	
	private String applyState;//申请或者撤销
	private String[] applyRooms;
	
	private boolean admin=false;//超管
	private String backUserId;//
	
	private String officePatrolPlaceId;
	private Date applyDate;
	
	private String itemId;//周次
	private Date queryStartTime;
	private Date queryEndTime;
	
	//对比时间
	private String compareDate;
	
	private Map<String,String> dateMap=new HashMap<String, String>();
	
	private List<String> xdateList=new ArrayList<String>();
	private Map<String,User> userMap=new HashMap<String,User>();
	private Map<String,List<String>> timeMap=new HashMap<String, List<String>>();
	
	public String execute(){
		
		return SUCCESS;
	}
	public String dutyInformationAdmin(){
		return SUCCESS;
	}
	public String dutyInformationList(){
		officeDutyInformationSets=officeDutyInformationSetService.getOfficeDutyInformationSetsByUnitIdName(getUnitId(), years, dutyName, getPage());
		return SUCCESS;
	}
	public String dutyInformationEdit(){
		if(StringUtils.isNotBlank(officeDutyInformationSet.getId())){
			officeDutyInformationSet=officeDutyInformationSetService.getOfficeDutyInformationSetById(officeDutyInformationSet.getId());
		}
		return SUCCESS;
	}
	public String dutyInformationView(){
		if(StringUtils.isNotBlank(officeDutyInformationSet.getId())){
			officeDutyInformationSet=officeDutyInformationSetService.getOfficeDutyInformationSetById(officeDutyInformationSet.getId());
		}
		return SUCCESS;
	}
	public String dutyInformationSave(){
		try {
			boolean flag=false;
			flag=officeDutyInformationSetService.isExistConflict(getUnitId(), dutyName, officeDutyInformationSet.getId());
			if(flag){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("值班名称不能重复");
				return SUCCESS;
			}
			if(StringUtils.isBlank(officeDutyInformationSet.getId())){
				officeDutyInformationSet.setCreateTime(new Date());
				officeDutyInformationSet.setCreateUserId(getLoginUser().getUserId());
				officeDutyInformationSet.setUnitId(getUnitId());
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
				String sdftime=sdf.format(new Date());
				officeDutyInformationSet.setYear(sdftime);
				officeDutyInformationSetService.save(officeDutyInformationSet);
			}else{
				officeDutyInformationSetService.update(officeDutyInformationSet);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败");
		}
		return SUCCESS;
	}
	public String dutyInformationDelete(){
		try {
			if(StringUtils.isNotBlank(officeDutyInformationSet.getId())){
				officeDutyInformationSetService.delete(new String[]{officeDutyInformationSet.getId()});
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败");
		}
		return SUCCESS;
	} 
	public String dutyInformationApply(){
		officeDutyInformationSets=officeDutyInformationSetService.getOfficeDutyInformationSetByUnitIdList(getUnitId());
		return SUCCESS;
	}
	public String dutyInformationApplyList(){
		User user=getLoginInfo().getUser();
		if(StringUtils.isNotBlank(user.getTeacherid())){
			Teacher teacher=teacherService.getTeacher(user.getTeacherid());
			if(teacher!=null&&StringUtils.isNotBlank(teacher.getPersonTel())){
				userName=user.getRealname()+"("+teacher.getPersonTel()+")";
			}else{
				userName=user.getRealname();
			}
		}
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		
		officeDutyInformationSet=officeDutyInformationSetService.getOfficeDutyInformationSetById(dutyId);
		this.dealUserView(officeDutyInformationSet, getLoginUser().getUserId(),isAdmin());
		boolean desTime=false;
		Date todate=new Date();
		String todayStr=sdf1.format(todate);
		try {
			todate=sdf1.parse(todayStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(officeDutyInformationSet.getRegistrationStartTime().compareTo(todate)<=0&&officeDutyInformationSet.getRegistrationEndTime().compareTo(todate)>=0){
			desTime=true;
		}
		if(desTime&&officeDutyInformationSet.isCanEdit()){
			officeDutyInformationSet.setCanEdit(true);
		}else{
			officeDutyInformationSet.setCanEdit(false);
		}
		
		if(officeDutyInformationSet!=null){
			if(StringUtils.equals("0", officeDutyInformationSet.getType())){//上下午
				periods.add("1");periods.add("2");
				periodMap.put("1", "上午");periodMap.put("2", "下午");
			}else{//全天
				periods.add("3");
				periodMap.put("3", "全天");
			}
			Date dutyStartTime=officeDutyInformationSet.getDutyStartTime();
			Date dutyEndTime=officeDutyInformationSet.getDutyEndTime();
			while(dutyStartTime.before(dutyEndTime)||dutyStartTime.compareTo(dutyEndTime)==0){
				dateList.add(sdf2.format(dutyStartTime));
				dateMap.put(sdf2.format(dutyStartTime), sdf1.format(dutyStartTime));
				dutyStartTime =DateUtils.addDay(dutyStartTime, 1);
			}
		}
		
		compareDate=sdf2.format(new Date());
		
		applyMap=officeDutyApplyService.getOfficeDutyAppliesMap(getUnitId(), dutyId);
		return SUCCESS;
	}
	
	private void dealUserView(OfficeDutyInformationSet officeDutyInformationSet,String userId,boolean admin){
		Set<String> userSet=officeDutyInformationSet.getUserSet();
		if(CollectionUtils.isNotEmpty(userSet)){
			for (String string : userSet) {
				if(StringUtils.equals(string, userId)){
					officeDutyInformationSet.setCanEdit(true);
					break;
				}
			}
		}
	}
	public String saveApplyInfo(){
		try {
			boolean flag=false;
			flag=officeDutyApplyService.saveOfficeDutyApplyInfo(applyRooms, dutyId, getUnitId(),getLoginUser().getUserId(), isAdmin());
			if(flag){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("你申请的值班已被别人报名或者同一天不能同时值班，请重新刷新页面，重新申请");
				return SUCCESS;
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("报名成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("报名失败");
		}
		return SUCCESS;
	}

	public String patrolPlaceList(){//TODO
		officePatrolPlaces=officePatrolPlaceService.getOfficePatrolPlaceByUnitIdPage(getUnitId(), getPage());
		return SUCCESS;
	}
	public String patrolPlaceEdit(){
		if(StringUtils.isNotBlank(officePatrolPlace.getId())){
			officePatrolPlace=officePatrolPlaceService.getOfficePatrolPlaceById(officePatrolPlace.getId());
		}else{
			if(officePatrolPlace==null){
				officePatrolPlace=new OfficePatrolPlace();
			}
		}
		return SUCCESS;
	}
	public String patrolPlaceSave(){
		try {
			if(StringUtils.isNotBlank(officePatrolPlace.getId())){
				officePatrolPlaceService.update(officePatrolPlace);
			}else{
				officePatrolPlace.setUnitId(getUnitId());
				officePatrolPlaceService.save(officePatrolPlace);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败");
		}
		return SUCCESS;
	}
	public String patrolPlaceDelete(){
		try {
			if(StringUtils.isNotBlank(officePatrolPlaceId)){
				officePatrolPlaceService.delete(new String[]{officePatrolPlaceId});
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败");
		}
		return SUCCESS;
	}
	public String applyGetPhoneNumber(){
		if(StringUtils.isNotBlank(backUserId)){
			try {
				User user = userService.getUser(backUserId);
				Teacher teacher = teacherService.getTeacher(user.getTeacherid());
				String phoneNumber = teacher.getPersonTel();
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage(phoneNumber);
			}catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("获取手机号码出错");
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	public String dutyRegisterAdmin(){
		if(applyDate==null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			applyDate=new Date();
			String date=sdf.format(applyDate);
			try {
				applyDate=sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		officeDutyApplies=officeDutyApplyService.getOfficeDutyAppliesByUnitIdAndUserId(getUnitId(), applyDate, null);
		return SUCCESS;
	}
	public String dutyRegisterUser(){
		officeDutyApplies=officeDutyApplyService.getOfficeDutyAppliesByUnitIdAndUserId(getUnitId(), applyDate, null);
		return SUCCESS;
	}
	public String dutyRegister(){//TODO
		officePatrolPlaces=officePatrolPlaceService.getOfficePatrolPlaceByUnitIdList(getUnitId());
		if(applyDate==null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			applyDate=new Date();
			String date=sdf.format(applyDate);
			try {
				applyDate=sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(StringUtils.isBlank(backUserId)){
			backUserId=getLoginUser().getUserId();
		}
		User user=userService.getUser(backUserId);
		if(user!=null){
			userName=user.getRealname();
		}
		officeDutyApplies=officeDutyApplyService.getOfficeDutyAppliesByUnitIdAndUserId(getUnitId(), applyDate, backUserId);
		if(CollectionUtils.isNotEmpty(officeDutyApplies)){
			officeDutyApply=officeDutyApplies.get(0);
			if(officeDutyApply!=null){
				String dutyId=officeDutyApply.getDutyInformationId();
				if(StringUtils.isNotBlank(dutyId)){
					officeDutyInformationSet=officeDutyInformationSetService.getOfficeDutyInformationSetById(dutyId);
				}
			}
		}
		officeDutyPlaces=officeDutyPlaceService.getOfficeDutyPlacesByUnitIdAndUserId(getUnitId(), backUserId, officeDutyInformationSet.getId(), applyDate);
		if(CollectionUtils.isEmpty(officeDutyPlaces)){
			for (OfficePatrolPlace officePatrolPlace : officePatrolPlaces) {
				OfficeDutyPlace officeDutyPlace=new OfficeDutyPlace();
				officeDutyPlace.setPlaceName(officePatrolPlace.getPlaceName());
				officeDutyPlace.setPatrolPlaceId(officePatrolPlace.getId());
				officeDutyPlace.setDutyUserId(backUserId);
				officeDutyPlaces.add(officeDutyPlace);
			}
		}
		if(CollectionUtils.isNotEmpty(officeDutyPlaces)&&CollectionUtils.isNotEmpty(officeDutyPlaces)){
			Set<String> idSet=new HashSet<String>();
			for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
				idSet.add(officeDutyPlace.getPatrolPlaceId());
			}
			Set<String> result = new HashSet<String>();
			for (OfficePatrolPlace officePatrolPlace : officePatrolPlaces) {
				result.add(officePatrolPlace.getId());
			}
			result.removeAll(idSet);
			Map<String,OfficePatrolPlace> officePMap=officePatrolPlaceService.getOfficePMap(result.toArray(new String[0]));
			for (String string : result) {
				OfficeDutyPlace officeDutyPlace=new OfficeDutyPlace();
				OfficePatrolPlace office=officePMap.get(string);
				officeDutyPlace.setPlaceName(office.getPlaceName());
				officeDutyPlace.setPatrolPlaceId(string);
				officeDutyPlace.setDutyUserId(backUserId);
				officeDutyPlaces.add(officeDutyPlace);
			}
		}
		return SUCCESS;
	}
	public String dutyRegisterSave(){
		try {
			List<OfficeDutyPlace> insertList=new ArrayList<OfficeDutyPlace>();
			List<OfficeDutyPlace> updateList=new ArrayList<OfficeDutyPlace>();
			for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
				if(StringUtils.isNotBlank(officeDutyPlace.getId())){
					updateList.add(officeDutyPlace);
				}else{
					officeDutyPlace.setCreateTime(new Date());
					//officeDutyPlace.setDutyUserId(getLoginUser().getUserId());
					if(applyDate==null){
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						applyDate=new Date();
						String date=sdf.format(applyDate);
						try {
							applyDate=sdf.parse(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					officeDutyPlace.setDutyTime(applyDate);
					officeDutyPlace.setUnitId(getUnitId());
					insertList.add(officeDutyPlace);
				}
			}
			officeDutyPlaceService.batchUpdateOrSave(insertList, updateList);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败");
		}
		return SUCCESS;
	}
	public String dutyQuery(){
		officeDutyInformationSets=officeDutyInformationSetService.getOfficeDutyInformationSetByUnitIdList(getUnitId());
		return SUCCESS;
	}
	public String dutyQueryList(){
		if(StringUtils.isNotBlank(itemId)){
			officeDutyInformationSet=officeDutyInformationSetService.getOfficeDutyInformationSetById(itemId);
			if(queryStartTime==null||queryStartTime.before(officeDutyInformationSet.getDutyStartTime())){
				queryStartTime=officeDutyInformationSet.getDutyStartTime();
			}
			if(queryEndTime==null||queryEndTime.after(officeDutyInformationSet.getDutyEndTime())){
				queryEndTime=officeDutyInformationSet.getDutyEndTime();
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
			//SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd");
			String startStr=sdf3.format(queryStartTime);
			String endStr=sdf3.format(queryEndTime);
			try {
				queryStartTime=sdf3.parse(startStr);
				queryEndTime=sdf3.parse(endStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			officePatrolPlaces=officePatrolPlaceService.getOfficePatrolPlaceByUnitIdList(getUnitId());
			officeDutyPlaceMap=officeDutyPlaceService.getOfficeDutyPlacesByUnitIdAndOthers(getUnitId(), itemId, queryStartTime, queryEndTime);
			
			timeMap=officeDutyPlaceService.getOfficeDMap(getUnitId(), itemId);
			
			while(queryStartTime.before(queryEndTime)||queryStartTime.compareTo(queryEndTime)==0){
				xdateList.add(sdf.format(queryStartTime));
				//timeMap.put(sdf3.format(date1), sdf2.format(date1));
				queryStartTime =DateUtils.addDay(queryStartTime, 1);
			}
			userMap=userService.getUserMap(getUnitId());
		}
		return SUCCESS;
	}
	
	public String dutyExport(){
		if(StringUtils.isNotBlank(itemId)){
			officeDutyInformationSet=officeDutyInformationSetService.getOfficeDutyInformationSetById(itemId);
			if(queryStartTime==null||queryStartTime.before(officeDutyInformationSet.getDutyStartTime())){
				queryStartTime=officeDutyInformationSet.getDutyStartTime();
			}
			if(queryEndTime==null||queryEndTime.after(officeDutyInformationSet.getDutyEndTime())){
				queryEndTime=officeDutyInformationSet.getDutyEndTime();
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
			//SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd");
			String startStr=sdf3.format(queryStartTime);
			String endStr=sdf3.format(queryEndTime);
			try {
				queryStartTime=sdf3.parse(startStr);
				queryEndTime=sdf3.parse(endStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			officePatrolPlaces=officePatrolPlaceService.getOfficePatrolPlaceByUnitIdList(getUnitId());
			officeDutyPlaceMap=officeDutyPlaceService.getOfficeDutyPlacesByUnitIdAndOthers(getUnitId(), itemId, queryStartTime, queryEndTime);
			
			timeMap=officeDutyPlaceService.getOfficeDMap(getUnitId(), itemId);
			
			while(queryStartTime.before(queryEndTime)||queryStartTime.compareTo(queryEndTime)==0){
				xdateList.add(sdf.format(queryStartTime));
				//timeMap.put(sdf3.format(date1), sdf2.format(date1));
				queryStartTime =DateUtils.addDay(queryStartTime, 1);
			}
			userMap=userService.getUserMap(getUnitId());
		}
		
		ZdExcel zdExcel=new ZdExcel();
		ZdStyle style=new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style2=new ZdStyle(ZdStyle.BORDER);
		
		zdExcel.add(new ZdCell("值班统计",officePatrolPlaces.size()+1,2,new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT,18)));
		List<ZdCell> zdCellList=new ArrayList<ZdCell>();
		zdCellList.add(new ZdCell("",1,style));
		for(OfficePatrolPlace off:officePatrolPlaces){
			zdCellList.add(new ZdCell(off.getPlaceName(),1,style));
		}
		zdExcel.add(zdCellList.toArray(new ZdCell[0]));
		
		for (String xStr : xdateList) {
			ZdCell[] cells=new ZdCell[officePatrolPlaces.size()+1];
			if(CollectionUtils.isNotEmpty(timeMap.get(xStr))){
				List<String> userList=timeMap.get(xStr);
				for (String string : userList) {
					cells[0]=new ZdCell(xStr+"("+userMap.get(string).getRealname()+")",1,style2);
					for(int i=0;i<officePatrolPlaces.size();i++){
						if(officeDutyPlaceMap.get(xStr+"_"+officePatrolPlaces.get(i).getId()+"_"+string)!=null){
							cells[i+1]=new ZdCell(officeDutyPlaceMap.get(xStr+"_"+officePatrolPlaces.get(i).getId()+"_"+string).getPatrolContent(),1,style2);
						}else{
							cells[i+1]=new ZdCell("",1,style2);
						}
					}
					zdExcel.add(cells);
				}
			}else{
				cells[0]=new ZdCell(xStr,1,style2);
				for(int i=0;i<officePatrolPlaces.size();i++){
					cells[i+1]=new ZdCell("",1,style2);
				}
				zdExcel.add(cells);
			}
		}
		Sheet sheet=zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("值班统计");
		return NONE;
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
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public void setOfficeDutyInformationSetService(
			OfficeDutyInformationSetService officeDutyInformationSetService) {
		this.officeDutyInformationSetService = officeDutyInformationSetService;
	}
	public OfficeDutyInformationSet getOfficeDutyInformationSet() {
		return officeDutyInformationSet;
	}
	public void setOfficeDutyInformationSet(OfficeDutyInformationSet officeDutyInformationSet) {
		this.officeDutyInformationSet = officeDutyInformationSet;
	}
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSets() {
		return officeDutyInformationSets;
	}
	public void setOfficeDutyInformationSets(
			List<OfficeDutyInformationSet> officeDutyInformationSets) {
		this.officeDutyInformationSets = officeDutyInformationSets;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getDutyId() {
		return dutyId;
	}
	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}
	public Map<String, OfficeDutyApply> getApplyMap() {
		return applyMap;
	}
	public void setApplyMap(Map<String, OfficeDutyApply> applyMap) {
		this.applyMap = applyMap;
	}
	public List<String> getPeriods() {
		return periods;
	}
	public void setPeriods(List<String> periods) {
		this.periods = periods;
	}
	public Map<String,String> getPeriodMap() {
		return periodMap;
	}
	public void setPeriodMap(Map<String,String> periodMap) {
		this.periodMap = periodMap;
	}
	public List<String> getDateList() {
		return dateList;
	}
	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}
	public void setOfficeDutyApplyService(
			OfficeDutyApplyService officeDutyApplyService) {
		this.officeDutyApplyService = officeDutyApplyService;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public String getApplyState() {
		return applyState;
	}
	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}
	public String[] getApplyRooms() {
		return applyRooms;
	}
	public void setApplyRooms(String[] applyRooms) {
		this.applyRooms = applyRooms;
	}
	public boolean isAdmin() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "office_duty_manage");
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
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public String getBackUserId() {
		return backUserId;
	}
	public void setBackUserId(String backUserId) {
		this.backUserId = backUserId;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List<OfficePatrolPlace> getOfficePatrolPlaces() {
		return officePatrolPlaces;
	}
	public void setOfficePatrolPlaces(List<OfficePatrolPlace> officePatrolPlaces) {
		this.officePatrolPlaces = officePatrolPlaces;
	}
	public void setOfficePatrolPlaceService(
			OfficePatrolPlaceService officePatrolPlaceService) {
		this.officePatrolPlaceService = officePatrolPlaceService;
	}
	public OfficePatrolPlace getOfficePatrolPlace() {
		return officePatrolPlace;
	}
	public void setOfficePatrolPlace(OfficePatrolPlace officePatrolPlace) {
		this.officePatrolPlace = officePatrolPlace;
	}
	public String getOfficePatrolPlaceId() {
		return officePatrolPlaceId;
	}
	public void setOfficePatrolPlaceId(String officePatrolPlaceId) {
		this.officePatrolPlaceId = officePatrolPlaceId;
	}
	public OfficeDutyPlace getOfficeDutyPlace() {
		return officeDutyPlace;
	}
	public void setOfficeDutyPlace(OfficeDutyPlace officeDutyPlace) {
		this.officeDutyPlace = officeDutyPlace;
	}
	public List<OfficeDutyPlace> getOfficeDutyPlaces() {
		return officeDutyPlaces;
	}
	public void setOfficeDutyPlaces(List<OfficeDutyPlace> officeDutyPlaces) {
		this.officeDutyPlaces = officeDutyPlaces;
	}
	public List<OfficeDutyApply> getOfficeDutyApplies() {
		return officeDutyApplies;
	}
	public void setOfficeDutyApplies(List<OfficeDutyApply> officeDutyApplies) {
		this.officeDutyApplies = officeDutyApplies;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public void setOfficeDutyPlaceService(
			OfficeDutyPlaceService officeDutyPlaceService) {
		this.officeDutyPlaceService = officeDutyPlaceService;
	}
	public OfficeDutyApply getOfficeDutyApply() {
		return officeDutyApply;
	}
	public void setOfficeDutyApply(OfficeDutyApply officeDutyApply) {
		this.officeDutyApply = officeDutyApply;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Date getQueryStartTime() {
		return queryStartTime;
	}
	public void setQueryStartTime(Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}
	public Date getQueryEndTime() {
		return queryEndTime;
	}
	public void setQueryEndTime(Date queryEndTime) {
		this.queryEndTime = queryEndTime;
	}
	public Map<String, OfficeDutyPlace> getOfficeDutyPlaceMap() {
		return officeDutyPlaceMap;
	}
	public void setOfficeDutyPlaceMap(
			Map<String, OfficeDutyPlace> officeDutyPlaceMap) {
		this.officeDutyPlaceMap = officeDutyPlaceMap;
	}
	public List<String> getXdateList() {
		return xdateList;
	}
	public void setXdateList(List<String> xdateList) {
		this.xdateList = xdateList;
	}
	public Map<String,User> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<String,User> userMap) {
		this.userMap = userMap;
	}
	public Map<String, List<String>> getTimeMap() {
		return timeMap;
	}
	public void setTimeMap(Map<String, List<String>> timeMap) {
		this.timeMap = timeMap;
	}
	public String getCompareDate() {
		return compareDate;
	}
	public void setCompareDate(String compareDate) {
		this.compareDate = compareDate;
	}
	public Map<String,String> getDateMap() {
		return dateMap;
	}
	public void setDateMap(Map<String,String> dateMap) {
		this.dateMap = dateMap;
	}
	
}
