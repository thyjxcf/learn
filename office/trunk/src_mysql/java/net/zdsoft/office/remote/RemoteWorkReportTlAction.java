package net.zdsoft.office.remote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportTl;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportTlService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.remote 
* @author songxq  
* @date 2016-6-12 上午10:53:07 
* @version V1.0
 */
@SuppressWarnings("serial")
public class RemoteWorkReportTlAction extends OfficeJsonBaseAction{
	
	private UserService userService;
	private UnitService unitService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeWorkReportTlService officeWorkReportTlService;
	private List<OfficeWorkReportTl> officeWorkReportList;
	private OfficeWorkReportTl officeWorkReport;
	
	private String id;
	private String userId;
	private String unitId;
	
	private String searchName;
	private boolean isManager;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public boolean isManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	public String workReport(){
		Unit unit = unitService.getUnit(unitId);
		if(unit!=null && Unit.UNIT_CLASS_EDU == unit.getUnitclass()){
			isManager = isCustomRole("work_report_tl");
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
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getUserId());
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
	public void myWorkReportList(){
		Pagination page = getPage();
		officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportByUnitIdPageContent(unitId, page, userId,null, null, null,searchName,null);
		JSONArray array=new JSONArray();
		for (OfficeWorkReportTl officeWorkReport : officeWorkReportList) {
			JSONObject json=new JSONObject();
			json.put("id", officeWorkReport.getId());
			json.put("year", officeWorkReport.getYear());
			json.put("semester", officeWorkReport.getSemester());
			json.put("week", officeWorkReport.getWeek());
			json.put("createTime", simpleDateFormat.format(officeWorkReport.getCreateTime()));
			json.put("applyStatus", officeWorkReport.getState());
			String content=officeWorkReport.getContent();
			if(content.length()>25){
				content=content.substring(0, 25)+"...";
			}
			json.put("content", content);
			json.put("state", officeWorkReport.getState());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void applyWorkReport(){
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)){
			officeWorkReport=officeWorkReportTlService.getOfficeWorkReportTlById(id);
			if(officeWorkReport==null){
				jsonMap.put("msg", "信息未找到或已被删除");
				jsonMap.put("result", 0);
				responseJSON(jsonMap);
				return;
			}
			json.put("id", officeWorkReport.getId());
			json.put("semester", officeWorkReport.getSemester());
			json.put("createTime", simpleDateFormat2.format(officeWorkReport.getCreateTime()));
			json.put("year", officeWorkReport.getYear());
			json.put("week",officeWorkReport.getWeek());
			json.put("content", officeWorkReport.getContent());
			json.put("createUserId", officeWorkReport.getCreateUserId());
			User user = userService.getUser(officeWorkReport.getCreateUserId());
			if(user!=null){
				json.put("userName", user.getRealname());
			}
			json.put("parentUnitId", officeWorkReport.getParentUnitId());
			json.put("applyStatus", officeWorkReport.getState());
			json.put("teacherOrderId", officeWorkReport.getTeacherOrderId());
			json.put("unitId", officeWorkReport.getUnitId());
			Unit unit = unitService.getUnit(officeWorkReport.getUnitId());
			if(unit!=null){
				json.put("unitName", unit.getName());
			}
			json.put("unitOrderId", officeWorkReport.getUnitOrderId());
			json.put("unitClass", officeWorkReport.getUnitClass());
			json.put("state", officeWorkReport.getState());
		}else{
			officeWorkReport=new OfficeWorkReportTl();
			officeWorkReport.setCreateUserId(userId);
			officeWorkReport.setUnitId(unitId);
			User user = userService.getUser(userId);
			if(user!=null){
				json.put("userName", user.getRealname());
			}
			Unit unit = unitService.getUnit(unitId);
			if(unit!=null){
				json.put("unitName", unit.getName());
			}
			json.put("id", "");
			json.put("createUserId", officeWorkReport.getCreateUserId());
			json.put("unitId", officeWorkReport.getUnitId());
			json.put("year", DateUtils.date2String(new Date(), "yyyy"));
			json.put("semester", getSemester());
			json.put("week", getWeek());
		}
		json.put("weeks", getWeekTimeList());
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public String getWeek() {
        int i;
        String weeks="";
		weeks = officeWorkReportTlService.findMaxWeek(userId,this.getCurrentSemester().getSemester(),unitId,null);
        if (weeks == null || weeks.equals("")) {
            weeks = "1";
        }
        else {
            i = Integer.parseInt(weeks);
            weeks = Integer.toString(i + 1);
        }
		return weeks;
	}
	public List<String> getWeekTimeList(){
		List<String> list=new ArrayList<String>();
		  for(int i = 1; i < 27; i++){
		    list.add(String.valueOf(i));
		 }
		return  list;
	}
	public void saveWorkReportTl(){
		try {
			List<OfficeWorkReportTl> officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportTlByUnitId(unitId,userId, officeWorkReport.getYear(), String.valueOf(officeWorkReport.getSemester()), String.valueOf(officeWorkReport.getWeek()));
			Unit unit=unitService.getUnit(unitId);
			if(StringUtils.isBlank(officeWorkReport.getId())){
				if(CollectionUtils.isEmpty(officeWorkReportList)){
					officeWorkReport.setUnitId(unitId);
					officeWorkReport.setCreateUserId(userId);
					officeWorkReport.setCreateTime(new Date());
					officeWorkReport.setUnitClass(unit.getUnitclass());
					officeWorkReport.setParentUnitId(unit.getParentid());
					officeWorkReport.setState(Constants.LEAVE_APPLY_SAVE);
					officeWorkReport.setUnitOrderId(unit.getOrderid());
					User user=userService.getUser(userId);
					officeWorkReport.setTeacherOrderId(Integer.valueOf(user.getOrderid().toString()));
					officeWorkReportTlService.save(officeWorkReport);
					jsonMap.put("result",1);
				}else{
					jsonMap.put("result", 0);
					jsonMap.put("msg", "保存失败，该周次的工作汇报已存在！");
					responseJSON(jsonMap);
					return;
				}
			}else{
				boolean canUpdate = true;
				if(CollectionUtils.isEmpty(officeWorkReportList)){
					officeWorkReport.setCreateTime(new Date());
					officeWorkReport.setState(Constants.LEAVE_APPLY_SAVE);
					officeWorkReportTlService.update(officeWorkReport);
					jsonMap.put("result",1);
				}else{
					for (OfficeWorkReportTl oTl : officeWorkReportList) {
						if(!StringUtils.equals(oTl.getId(), officeWorkReport.getId())){
							canUpdate = false;
							break;
						}
				}
					if(canUpdate){
						officeWorkReport.setCreateTime(new Date());
						officeWorkReport.setState(Constants.LEAVE_APPLY_SAVE);
						officeWorkReportTlService.update(officeWorkReport);
						jsonMap.put("result",1);
					}	
					else{
						jsonMap.put("result", 0);
						jsonMap.put("msg", "保存失败，该周次的工作汇报已存在！");
						responseJSON(jsonMap);
						return;
					}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	public void submitWorkReport(){
		try {
			List<OfficeWorkReportTl> officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportTlByUnitId(officeWorkReport.getUnitId(),officeWorkReport.getCreateUserId(), officeWorkReport.getYear(), String.valueOf(officeWorkReport.getSemester()), String.valueOf(officeWorkReport.getWeek()));
			Unit unit=unitService.getUnit(officeWorkReport.getUnitId());
			if(StringUtils.isBlank(officeWorkReport.getId())){
				if(CollectionUtils.isEmpty(officeWorkReportList)){
					officeWorkReport.setCreateTime(new Date());
					officeWorkReport.setUnitClass(unit.getUnitclass());
					officeWorkReport.setParentUnitId(unit.getParentid());
					officeWorkReport.setUnitOrderId(unit.getOrderid());
					officeWorkReport.setState(Constants.LEAVE_APPLY_FLOWING);
					User user=userService.getUser(officeWorkReport.getCreateUserId());
					officeWorkReport.setTeacherOrderId(Integer.valueOf(user.getOrderid().toString()));
					officeWorkReportTlService.save(officeWorkReport);
					jsonMap.put("result",1);
				}else{
					jsonMap.put("result", 0);
					jsonMap.put("msg", "保存失败，该周次的工作汇报已存在！");
					responseJSON(jsonMap);
					return;
				}
			}else{
				boolean canUpdate = true;
				if(CollectionUtils.isEmpty(officeWorkReportList)){
					officeWorkReport.setCreateTime(new Date());
					officeWorkReport.setState(Constants.LEAVE_APPLY_FLOWING);
					officeWorkReportTlService.update(officeWorkReport);
					jsonMap.put("result",1);
				}else{
					for (OfficeWorkReportTl oTl : officeWorkReportList) {
						if(!StringUtils.equals(oTl.getId(), officeWorkReport.getId())){
							canUpdate = false;
							break;
						}
					}
				if(canUpdate){
					officeWorkReport.setCreateTime(new Date());
					officeWorkReport.setState(Constants.LEAVE_APPLY_FLOWING);
					officeWorkReportTlService.update(officeWorkReport);
					jsonMap.put("result",1);
				}	
				else{
					jsonMap.put("result", 0);
					jsonMap.put("msg", "保存失败，该周次的工作汇报已存在！");
					responseJSON(jsonMap);
					return;
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	public void deleteWorkReport(){
		JSONObject json = new JSONObject();
		if(StringUtils.isNotEmpty(id)){
			try {
				officeWorkReportTlService.delete(new String[]{id});
				jsonMap.put("result",1);
			} catch (Exception e) {
				jsonMap.put("result",0);
			}
		}else{
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	public void workReportSearchList(){
		Pagination page=getPage();
		officeWorkReportList=officeWorkReportTlService.getOfficeWorkReportTlByUnitIdPageContentCreateUserName(page, null, null, null, null, searchName);
		JSONArray array = new JSONArray();
		for(OfficeWorkReportTl officeWorkReport : officeWorkReportList){
			JSONObject json=new JSONObject();
			json.put("id", officeWorkReport.getId());
			json.put("year", officeWorkReport.getYear());
			json.put("semester", officeWorkReport.getSemester());
			json.put("week", officeWorkReport.getWeek());
			json.put("createTime", simpleDateFormat.format(officeWorkReport.getCreateTime()));
			json.put("applyStatus", officeWorkReport.getState());
			json.put("createUserName", officeWorkReport.getCreateUserName());
			String content=officeWorkReport.getContent();
			if(content.length()>25){
				content=content.substring(0, 25)+"...";
			}
			json.put("content", content);
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void aduitWorkReport(){
		officeWorkReport=officeWorkReportTlService.getOfficeWorkReportTlById(id);
		JSONObject json=new JSONObject();
		json.put("id", officeWorkReport.getId());
		json.put("semester", officeWorkReport.getSemester());
		json.put("createTime", officeWorkReport.getCreateTime());
		json.put("year", officeWorkReport.getYear());
		json.put("week",officeWorkReport.getWeek());
		json.put("content", officeWorkReport.getContent());
		json.put("createUserId", officeWorkReport.getCreateUserId());
		json.put("parentUnitId", officeWorkReport.getParentUnitId());
		json.put("applyStatus", officeWorkReport.getState());
		json.put("teacherOrderId", officeWorkReport.getTeacherOrderId());
		json.put("unitId", officeWorkReport.getUnitId());
		json.put("unitOrderId", officeWorkReport.getUnitOrderId());
		json.put("unitClass", officeWorkReport.getUnitClass());
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void aduitWorkReportSave(){
		try {
			List<OfficeWorkReportTl> officeWorkReportTls=officeWorkReportTlService.getOfficeWorkReportTlByUnitId(officeWorkReport.getUnitId(),officeWorkReport.getCreateUserId(), officeWorkReport.getYear(), String.valueOf(officeWorkReport.getSemester()), String.valueOf(officeWorkReport.getWeek()));
			boolean canUpdate = true;
			if(CollectionUtils.isEmpty(officeWorkReportTls)){
				officeWorkReport.setModifyTime(new Date());
				officeWorkReport.setModifyUserId(userId);
				officeWorkReportTlService.update(officeWorkReport);
				jsonMap.put("result",1);
			}else{
				for (OfficeWorkReportTl oTl : officeWorkReportTls) {
					if(!StringUtils.equals(oTl.getId(), officeWorkReport.getId())){
						canUpdate = false;
						break;
					}
				}
				if(canUpdate){
					officeWorkReport.setModifyTime(new Date());
					officeWorkReport.setModifyUserId(userId);
					officeWorkReportTlService.update(officeWorkReport);
					jsonMap.put("result",1);
				}else{
					jsonMap.put("result", 0);
					jsonMap.put("msg", "保存失败，该周次的工作汇报已存在！");
					responseJSON(jsonMap);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	public void applyDetail(){
		officeWorkReport=officeWorkReportTlService.getOfficeWorkReportTlById(id);
		if(officeWorkReport==null){
			officeWorkReport=new OfficeWorkReportTl();
		}
		JSONObject json = new JSONObject();
		json.put("applyUserName", userService.getUser(officeWorkReport.getCreateUserId()).getRealname());
		json.put("year", officeWorkReport.getYear());
		json.put("semester", officeWorkReport.getSemester());
		json.put("week", officeWorkReport.getWeek());
		json.put("applyStatus", unitService.getUnit(officeWorkReport.getUnitId()).getName());
		json.put("content", officeWorkReport.getContent());
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List<OfficeWorkReportTl> getOfficeWorkReportList() {
		return officeWorkReportList;
	}
	public void setOfficeWorkReportList(List<OfficeWorkReportTl> officeWorkReportList) {
		this.officeWorkReportList = officeWorkReportList;
	}
	public OfficeWorkReportTl getOfficeWorkReport() {
		return officeWorkReport;
	}
	public void setOfficeWorkReport(OfficeWorkReportTl officeWorkReport) {
		this.officeWorkReport = officeWorkReport;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public void setOfficeWorkReportTlService(
			OfficeWorkReportTlService officeWorkReportTlService) {
		this.officeWorkReportTlService = officeWorkReportTlService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
