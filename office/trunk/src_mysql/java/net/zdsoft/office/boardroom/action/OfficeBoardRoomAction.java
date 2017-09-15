package net.zdsoft.office.boardroom.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.office.boardroom.entity.OfficeApplyDetailsXj;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomApplyXj;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomXj;
import net.zdsoft.office.boardroom.service.OfficeApplyDetailsXjService;
import net.zdsoft.office.boardroom.service.OfficeBoardroomApplyXjService;
import net.zdsoft.office.boardroom.service.OfficeBoardroomXjService;
import net.zdsoft.office.bulletin.entity.OfficeBulletinSet;
import net.zdsoft.office.bulletin.service.OfficeBulletinSetService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.boardroom.action 
* @author songxq  
* @date 2016-9-9 上午11:17:15 
* @version V1.0
 */
@SuppressWarnings("serial")
public class OfficeBoardRoomAction extends PageAction{
	
	private List<OfficeBoardroomXj> officeBoardroomXjList;
	private OfficeBoardroomXj officeBoardroomXj;
	private String officeBoardroomXjId;
	private String OfficeBoardroomApplyXjId;
	private List<Dept> deptList;
	private String deptId;
	private List<String> periodList;//节次或时间
	private List<String> applyDateList;//日期
	private Map<String, String> timeMap;
	private Map<String, OfficeApplyDetailsXj> applyMap;//会议室申请信息
	
	private OfficeBoardroomXjService officeBoardroomXjService;
	private OfficeApplyDetailsXjService officeApplyDetailsXjService;
	private OfficeBoardroomApplyXjService officeBoardroomApplyXjService;
	private DeptService deptService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	public static final String APPLY_STATE = "1"; //申请
    public static final String CANCEL_STATE = "2"; //撤销
    private String applyState;
    private String[] applyTimes;
	//预约
	private String applyStartDate;
	private String applyEndDate;
	private OfficeApplyDetailsXj officeApplyDetailsXj=new OfficeApplyDetailsXj();
	private OfficeBoardroomApplyXj officeBoardroomApplyXj;
	private List<OfficeBoardroomApplyXj> officeBoardroomApplyXjs=new ArrayList<OfficeBoardroomApplyXj>();
	
	private String show;
	private String[] applyRoomIds;
	private String auditOpinion;
	
	//时间
	private String prev;
	
	//判断是否有管理权限
	private boolean megAdmin=false;
	private boolean shheAdmin=false;
	private boolean applyAdmin=false;
	//审核设置
	private OfficeBulletinSetService officeBulletinSetService;
	private OfficeBulletinSet officeBulletinSet;
	private List<OfficeBulletinSet> officeBulletinSetList;
	
	//对比时间
	private String compareDate;
	
	public String execute() throws Exception{
		megAdmin = isPracticeAdmin(Constants.BOARDROOM_MANAGE);
		officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BOARDROOM_AUDIT);
		if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
			officeBulletinSet=officeBulletinSetList.get(0);
		}
		if(officeBulletinSet!=null&&officeBulletinSet.getAuditId()!=null){
			for(String str:StringUtils.split(officeBulletinSet.getAuditId(), ",")){
				if(StringUtils.equals(str, getLoginUser().getUserId())){
					shheAdmin=true;
				}
			}
		}else shheAdmin=false;
		return SUCCESS;
	}
	public String boardRoomAdmin(){
		megAdmin = isPracticeAdmin(Constants.BOARDROOM_MANAGE);
		return SUCCESS;
	}
	public String boardRoomList(){
		megAdmin = isPracticeAdmin(Constants.BOARDROOM_MANAGE);
		applyAdmin=isPracticeAdmin(Constants.BOARDROOM_APPLY);
		officeBoardroomXjList=officeBoardroomXjService.getOfficeBoardroomXjByUnitIdPage(getUnitId(), getPage());
		return SUCCESS;
	}
	public String addBoardRoom(){
		if(officeBoardroomXj==null){
			officeBoardroomXj=new OfficeBoardroomXj();
		}
		if(StringUtils.equals(String.valueOf(getLoginInfo().getUnitClass()),"1")){
			officeBoardroomXj.setNeedAudit("0");
		}else officeBoardroomXj.setNeedAudit("1");
		return SUCCESS;
	}
	public String editBoardRoom(){
		officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BOARDROOM_AUDIT);
		if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
			officeBulletinSet=officeBulletinSetList.get(0);
		}
		if(officeBulletinSet==null){
			officeBulletinSet=new OfficeBulletinSet();
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
				officeBulletinSet.setNeedAudit("0");
			}else officeBulletinSet.setNeedAudit("1");
		}
		if(officeBulletinSet!=null&&officeBulletinSet.getAuditId()!=null){
			for(String str:StringUtils.split(officeBulletinSet.getAuditId(), ",")){
				if(StringUtils.equals(str, getLoginUser().getUserId())){
					shheAdmin=true;
				}
			}
		}else shheAdmin=false;
		if(StringUtils.isBlank(officeBoardroomXjId)){
			officeBoardroomXj = new OfficeBoardroomXj();
			if(officeBulletinSet!=null&&StringUtils.isNotBlank(officeBulletinSet.getNeedAudit())){
				officeBoardroomXj.setNeedAudit(officeBulletinSet.getNeedAudit());
			}else{if((getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU)){
				officeBoardroomXj.setNeedAudit("0");
			}else officeBoardroomXj.setNeedAudit("1");
			}
		}else{
			officeBoardroomXj = officeBoardroomXjService.getOfficeBoardroomXjById(officeBoardroomXjId);
		}
		return SUCCESS;
	}
	public String saveBoardRoom(){
			try {
				boolean flag = false;//判断会议室名称是否唯一
				flag=officeBoardroomXjService.isExistConflict(getUnitId(), officeBoardroomXj.getName().trim(), officeBoardroomXj.getId());
				if(flag){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("会议室名称不能重复!");
					return SUCCESS;
				}
				if(StringUtils.isBlank(officeBoardroomXj.getId())){
					officeBoardroomXj.setUnitId(getUnitId());
					officeBoardroomXj.setCreateTime(new Date());
					if(officeBoardroomXj.getNeedAudit()==null){
						officeBoardroomXj.setNeedAudit("0");
					}
					officeBoardroomXjService.save(officeBoardroomXj);
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("操作成功!");
				}else{
					boolean isUpdate=false;
					isUpdate=officeApplyDetailsXjService.getApplyByApplyStartDate(getUnitId(), officeBoardroomXj.getId(), new Date());
					if(isUpdate){
						OfficeBoardroomXj officeBoardroomXjOld = officeBoardroomXjService.getOfficeBoardroomXjById(officeBoardroomXj.getId());
						if(!StringUtils.equals(officeBoardroomXjOld.getTimeInterval(), officeBoardroomXj.getTimeInterval())
								||!StringUtils.equals(officeBoardroomXjOld.getStartTime(), officeBoardroomXj.getStartTime())
								||!StringUtils.equals(officeBoardroomXjOld.getEndTime(), officeBoardroomXj.getEndTime())
								||!StringUtils.equals(officeBoardroomXjOld.getNoonStartTime(), officeBoardroomXj.getNoonStartTime())
								||!StringUtils.equals(officeBoardroomXjOld.getNoonEndTime(), officeBoardroomXj.getNoonEndTime())){
							promptMessageDto.setOperateSuccess(false);
							promptMessageDto.setErrorMessage("有未过期的预约记录,不能修改时段");
							return SUCCESS;
						}
					}
					officeBoardroomXjService.update(officeBoardroomXj);
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("操作成功!");
				}
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败:"+e.getCause().getMessage());
			}
			
			return SUCCESS;
		}

	public String deleteBoardRoom(){
		try {
			if(StringUtils.isNotBlank(officeBoardroomXjId)){
				boolean isDelete=false;
				isDelete=officeApplyDetailsXjService.getApplyByApplyStartDate(getUnitId(), officeBoardroomXjId, new Date());
				if(isDelete){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("有未过期的预约记录");
					return SUCCESS;
				}else{
				officeBoardroomXjService.delete(new String[]{officeBoardroomXjId});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功!");
				}
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败:");
			}
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	public String viewBoardRoom(){
		officeBoardroomXj = officeBoardroomXjService.getOfficeBoardroomXjById(officeBoardroomXjId);
		return SUCCESS;
	}
	public String boardRoomOrderAdmin(){
		officeBoardroomXj = officeBoardroomXjService.getOfficeBoardroomXjById(officeBoardroomXjId);
		if(officeBoardroomXj==null){
			officeBoardroomXj=new OfficeBoardroomXj();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		if(StringUtils.isBlank(applyStartDate) && StringUtils.isBlank(applyEndDate)){
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			applyStartDate = sdf.format(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, 6);
			applyEndDate = sdf.format(c.getTime());
		}else{
			try {
				c.setTime(sdf.parse(applyStartDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if("1".equals(prev)){// 上一个
				c.add(Calendar.DATE, -7);
				applyStartDate=sdf.format(c.getTime());
				c.add(Calendar.DAY_OF_MONTH, 6);
				applyEndDate = sdf.format(c.getTime());
			} else if("2".equals(prev)){// 下一个
				c.add(Calendar.DATE, 7);
				applyStartDate=sdf.format(c.getTime());
				c.add(Calendar.DAY_OF_MONTH, 6);
				applyEndDate = sdf.format(c.getTime());
			}
		}
		deptId=getLoginInfo().getUser().getDeptid();
		deptList=deptService.getDepts(getUnitId());
		return SUCCESS;
	}
	public String boardRoomOrderList() throws ParseException{//TODO
		officeBoardroomXj = officeBoardroomXjService.getOfficeBoardroomXjById(officeBoardroomXjId);
		if(officeBoardroomXj==null){
			officeBoardroomXj=new OfficeBoardroomXj();
		}
		officeApplyDetailsXj.setDeptId(deptId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		if(StringUtils.isBlank(applyStartDate) && StringUtils.isBlank(applyEndDate)){
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			applyStartDate = sdf.format(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, 5);
			applyEndDate = sdf.format(c.getTime());
		}
//		}else{
//			c.setTime(sdf.parse(applyStartDate));
//			if("1".equals(prev)){// 上一个
//				c.add(Calendar.DATE, -7);
//				applyStartDate=sdf.format(c.getTime());
//				c.add(Calendar.DAY_OF_MONTH, 4);
//				applyEndDate = sdf.format(c.getTime());
//			} else if("2".equals(prev)){// 下一个
//				c.add(Calendar.DATE, 7);
//				applyStartDate=sdf.format(c.getTime());
//				c.add(Calendar.DAY_OF_MONTH, 5);
//				applyEndDate = sdf.format(c.getTime());
//			}
//		}
		Date applyStartDate1 = sdf.parse(applyStartDate); 
		Date applyEndDate2 = sdf.parse(applyEndDate); 
		applyMap = officeApplyDetailsXjService.getApplyMap(officeBoardroomXjId, applyStartDate1, applyEndDate2, getUnitId(),deptId);
		periodList = new ArrayList<String>();
		applyDateList=new ArrayList<String>();
		timeMap = new HashMap<String, String>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		//SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmm");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy.MM.dd");
		Date date = new Date();
		Date date1 = new Date();
		Date date2 = new Date();
		compareDate=sdf4.format(date);
		try {
			date1 = sdf1.parse(sdf.format(date)+" "+officeBoardroomXj.getStartTime()+":00");
			date2 = sdf1.parse(sdf.format(date)+" "+officeBoardroomXj.getEndTime()+":00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String periodTime = officeBoardroomXj.getTimeInterval();
		while(date1.before(date2)){
			periodList.add(sdf2.format(date1)+"-"
					+sdf2.format(new Date(date1.getTime() + Integer.parseInt(periodTime) * 60 * 1000)));
			date1 = new Date(date1.getTime() + Integer.parseInt(periodTime) * 60 * 1000);
		}
		while(applyStartDate1.before(applyEndDate2)||applyStartDate1.compareTo(applyEndDate2)==0){
			applyDateList.add(sdf4.format(applyStartDate1));
			timeMap.put(sdf4.format(applyStartDate1), sdf5.format(applyStartDate1)+"<br/> "
					+OfficeUtils.dayForWeek(applyStartDate1));
			Calendar   calendar   =   new   GregorianCalendar(); 
		     calendar.setTime(applyStartDate1); 
		     calendar.add(Calendar.DAY_OF_MONTH,1);//把日期往后增加一天.整数往后推,负数往前移动 
		     applyStartDate1=calendar.getTime(); 
		}
		return SUCCESS;
	}
	public String boardRoomOrderSave(){
		OfficeApplyDetailsXj officeApplyDetailsXj = new OfficeApplyDetailsXj();
		officeApplyDetailsXj.setApplyTimes(applyTimes);
		officeApplyDetailsXj.setUnitId(getUnitId());
		officeApplyDetailsXj.setUserId(getLoginUser().getUserId());
		officeApplyDetailsXj.setRoomId(officeBoardroomXjId);
		officeApplyDetailsXj.setDeptId(deptId);
		if(APPLY_STATE.equals(applyState)){
			try {
				boolean flag = officeApplyDetailsXjService.saveRoom(officeApplyDetailsXj);
				if(flag){
					jsonError = "您申请的会议室已被占用，请刷新界面重新申请！";
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "申请失败！";
			}
		}else if(CANCEL_STATE.equals(applyState)){
			officeApplyDetailsXj.setState(String.valueOf(Constants.APPLY_STATE_PASS));
			try {
				officeApplyDetailsXjService.cancel(officeApplyDetailsXj);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "撤销失败！";
			}
		}else{
			jsonError = "传入数据有误！";
		}
		return SUCCESS;
	}
	public String boardRoomOrderManageAdmin(){
		if(StringUtils.isBlank(applyStartDate) && StringUtils.isBlank(applyEndDate)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			applyStartDate = df.format(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, 6);
			applyEndDate = df.format(c.getTime());
		}
		officeBoardroomXjList=officeBoardroomXjService.getOfficeBoardroomXjByUnitIdList(getUnitId());
		deptId=getLoginInfo().getUser().getDeptid();
		deptList=deptService.getDepts(getUnitId());
		return SUCCESS;
	}
	public String boardRoomOrderManageList(){//TODO
		officeBoardroomApplyXjs=officeBoardroomApplyXjService.getOfficeBoardroomApplyXjByDeptIdUnitIdPage(applyStartDate, applyEndDate,null, officeBoardroomXjId, deptId, getUnitId(), getPage());
		megAdmin=isPracticeAdmin(Constants.BOARDROOM_MANAGE);
		return SUCCESS;
	}
	public String boardRoomOrderManageDelete(){
		try {
			if(StringUtils.isNotBlank(OfficeBoardroomApplyXjId)){
				officeBoardroomApplyXjService.deleteRecord(getUnitId(), new String[]{OfficeBoardroomApplyXjId});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功!");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败:");
			}
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	public String boardRoomAuditAdmin(){
		if(StringUtils.isBlank(show)){
			show = "0";
		}
		officeBoardroomXjList=officeBoardroomXjService.getOfficeBoardroomXjByUnitIdList(getUnitId());
		deptId=getLoginInfo().getUser().getDeptid();
		deptList=deptService.getDepts(getUnitId());
		return SUCCESS;
	}
	public String boardRoomAuditList(){
		officeBoardroomApplyXjs=officeBoardroomApplyXjService.getOfficeBoardroomApplyXjByDeptIdUnitIdPage(applyStartDate, applyEndDate,show, officeBoardroomXjId, deptId, getUnitId(), getPage());
		return SUCCESS;
	}
	public String boardRoomAuditSave(){
		if(ArrayUtils.isEmpty(applyRoomIds)){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("传入的值为空");
		}else{
			try {
				officeBoardroomApplyXjService.pass(applyRoomIds, getLoginUser().getUserId(), auditOpinion, show);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功!");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("审核失败:"+e.getCause().getMessage());
			}
		}
		return SUCCESS;
	}
	
	public String auditSet(){//TODO
		megAdmin = isPracticeAdmin(Constants.BOARDROOM_MANAGE);
		officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BOARDROOM_AUDIT);
		if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
			officeBulletinSet=officeBulletinSetList.get(0);
		}
		if(officeBulletinSet!=null&&officeBulletinSet.getAuditId()!=null){
			for(String str:StringUtils.split(officeBulletinSet.getAuditId(), ",")){
				if(StringUtils.equals(str, getLoginUser().getUserId())){
					shheAdmin=true;
				}
			}
		}else shheAdmin=false;
		if(officeBulletinSet==null){
			officeBulletinSet=new OfficeBulletinSet();
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
				officeBulletinSet.setNeedAudit("0");
			}else officeBulletinSet.setNeedAudit("1");
		}
		return SUCCESS;
	}
	
	public String auditSetSave(){
		try {
			officeBulletinSet.setUnitId(getUnitId());
			officeBulletinSet.setRoleCode(Constants.BOARDROOM_AUDIT);
			int strlength=officeBulletinSet.getAuditId().length();
			if(strlength>=1000){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("维护人数超过30人");
				return SUCCESS;
			}
			if(StringUtils.isBlank(officeBulletinSet.getId())){
				officeBulletinSetService.save(officeBulletinSet);
				promptMessageDto.setOperateSuccess(true);
			}else{
				officeBulletinSetService.update(officeBulletinSet);
				promptMessageDto.setOperateSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败:"+e.getCause().getMessage());
		}
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
	public List<OfficeBoardroomXj> getOfficeBoardroomXjList() {
		return officeBoardroomXjList;
	}
	public void setOfficeBoardroomXjList(List<OfficeBoardroomXj> officeBoardroomXjList) {
		this.officeBoardroomXjList = officeBoardroomXjList;
	}
	public void setOfficeBoardroomXjService(
			OfficeBoardroomXjService officeBoardroomXjService) {
		this.officeBoardroomXjService = officeBoardroomXjService;
	}
	public OfficeBoardroomXj getOfficeBoardroomXj() {
		return officeBoardroomXj;
	}
	public void setOfficeBoardroomXj(OfficeBoardroomXj officeBoardroomXj) {
		this.officeBoardroomXj = officeBoardroomXj;
	}
	public String getOfficeBoardroomXjId() {
		return officeBoardroomXjId;
	}
	public void setOfficeBoardroomXjId(String officeBoardroomXjId) {
		this.officeBoardroomXjId = officeBoardroomXjId;
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
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<String> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<String> periodList) {
		this.periodList = periodList;
	}
	public Map<String, String> getTimeMap() {
		return timeMap;
	}
	public void setTimeMap(Map<String, String> timeMap) {
		this.timeMap = timeMap;
	}
	public Map<String, OfficeApplyDetailsXj> getApplyMap() {
		return applyMap;
	}
	public void setApplyMap(Map<String, OfficeApplyDetailsXj> applyMap) {
		this.applyMap = applyMap;
	}
	public void setOfficeApplyDetailsXjService(
			OfficeApplyDetailsXjService officeApplyDetailsXjService) {
		this.officeApplyDetailsXjService = officeApplyDetailsXjService;
	}
	public String getApplyStartDate() {
		return applyStartDate;
	}
	public void setApplyStartDate(String applyStartDate) {
		this.applyStartDate = applyStartDate;
	}
	public String getApplyEndDate() {
		return applyEndDate;
	}
	public void setApplyEndDate(String applyEndDate) {
		this.applyEndDate = applyEndDate;
	}
	public List<String> getApplyDateList() {
		return applyDateList;
	}
	public void setApplyDateList(List<String> applyDateList) {
		this.applyDateList = applyDateList;
	}
	public OfficeApplyDetailsXj getOfficeApplyDetailsXj() {
		return officeApplyDetailsXj;
	}
	public void setOfficeApplyDetailsXj(OfficeApplyDetailsXj officeApplyDetailsXj) {
		this.officeApplyDetailsXj = officeApplyDetailsXj;
	}
	public OfficeBoardroomApplyXj getOfficeBoardroomApplyXj() {
		return officeBoardroomApplyXj;
	}
	public void setOfficeBoardroomApplyXj(OfficeBoardroomApplyXj officeBoardroomApplyXj) {
		this.officeBoardroomApplyXj = officeBoardroomApplyXj;
	}
	public void setOfficeBoardroomApplyXjService(
			OfficeBoardroomApplyXjService officeBoardroomApplyXjService) {
		this.officeBoardroomApplyXjService = officeBoardroomApplyXjService;
	}
	public String[] getApplyTimes() {
		return applyTimes;
	}
	public void setApplyTimes(String[] applyTimes) {
		this.applyTimes = applyTimes;
	}
	public String getApplyState() {
		return applyState;
	}
	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjs() {
		return officeBoardroomApplyXjs;
	}
	public void setOfficeBoardroomApplyXjs(List<OfficeBoardroomApplyXj> officeBoardroomApplyXjs) {
		this.officeBoardroomApplyXjs = officeBoardroomApplyXjs;
	}
	public String getOfficeBoardroomApplyXjId() {
		return OfficeBoardroomApplyXjId;
	}
	public void setOfficeBoardroomApplyXjId(String officeBoardroomApplyXjId) {
		OfficeBoardroomApplyXjId = officeBoardroomApplyXjId;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String[] getApplyRoomIds() {
		return applyRoomIds;
	}
	public void setApplyRoomIds(String[] applyRoomIds) {
		this.applyRoomIds = applyRoomIds;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public String getPrev() {
		return prev;
	}
	public void setPrev(String prev) {
		this.prev = prev;
	}
	public boolean isMegAdmin() {
		return megAdmin;
	}
	public void setMegAdmin(boolean megAdmin) {
		this.megAdmin = megAdmin;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public OfficeBulletinSet getOfficeBulletinSet() {
		return officeBulletinSet;
	}
	public void setOfficeBulletinSet(OfficeBulletinSet officeBulletinSet) {
		this.officeBulletinSet = officeBulletinSet;
	}
	public List<OfficeBulletinSet> getOfficeBulletinSetList() {
		return officeBulletinSetList;
	}
	public void setOfficeBulletinSetList(
			List<OfficeBulletinSet> officeBulletinSetList) {
		this.officeBulletinSetList = officeBulletinSetList;
	}
	public void setOfficeBulletinSetService(
			OfficeBulletinSetService officeBulletinSetService) {
		this.officeBulletinSetService = officeBulletinSetService;
	}
	public boolean isShheAdmin() {
		return shheAdmin;
	}
	public void setShheAdmin(boolean shheAdmin) {
		this.shheAdmin = shheAdmin;
	}
	public boolean isApplyAdmin() {
		return applyAdmin;
	}
	public void setApplyAdmin(boolean applyAdmin) {
		this.applyAdmin = applyAdmin;
	}
	public String getCompareDate() {
		return compareDate;
	}
	public void setCompareDate(String compareDate) {
		this.compareDate = compareDate;
	}
	
}
