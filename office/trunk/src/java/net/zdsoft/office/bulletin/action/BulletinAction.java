package net.zdsoft.office.bulletin.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
import net.zdsoft.office.bulletin.entity.OfficeBulletinSet;
import net.zdsoft.office.bulletin.entity.OfficeBulletinType;
import net.zdsoft.office.bulletin.entity.PushMessageTargetItem;
import net.zdsoft.office.bulletin.service.OfficeBulletinReadService;
import net.zdsoft.office.bulletin.service.OfficeBulletinService;
import net.zdsoft.office.bulletin.service.OfficeBulletinSetService;
import net.zdsoft.office.bulletin.service.OfficeBulletinTypeService;
import net.zdsoft.office.bulletin.service.PushMessageService;
import net.zdsoft.office.msgcenter.dto.ReadInfoDto;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;

/**
 * 通知公告ACTION
 */
@SuppressWarnings("serial")
public class BulletinAction extends PageAction {

	private OfficeBulletinService officeBulletinService;
	private OfficeBulletinReadService officeBulletinReadService;
	private OfficeBulletinTypeService officeBulletinTypeService;
	private ModuleService moduleService;
	private TeachAreaService teachAreaService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private UnitService unitService;
	
	//网站推送
	private PushMessageService pushMessageService;
	private String pushUnitTargetItemId;
	
	private List<TeachArea> teachAreaList;
	private List<OfficeBulletin> officeBulletinList;
	private List<OfficeBulletinType> officeBulletinTypelist;

	private Module module;
	private OfficeBulletin bulletin;
	private OfficeBulletinType officeBulletinType;
	
	private String idea;
	private String show;//0:全部，1：未提交（未发布），2：未审核，3：通过（已发布），4：未通过
	private String searName;
	private String bulletinId;
	private String[] bulletinIds;
	private String bulletinType;//类型
	private String bulletinName;//显示的名称
	private String startTime;
	private String endTime;
	private String publishName;
	private String searchAreaId;//校区id
	private String[] orderIds;//排序号
	private DeptService deptService;
	
	
	private boolean megAdmin;
	private boolean shheAdmin;
	private boolean needAudit;
	
	public int modelId;
	//71007，71507就中策使用
	public static int[] modelIds ={70002,70003,70004,70005,71002,71003,71004,71005,70018,71007,71007,71007,71007};
	public static int[] modelIdsEdu ={70502,70503,70504,70505,71502,71503,71504,71505,70518,71507,71507,71507,71507};
	
	//吉安六中定制a70502,a70505,a70539,a70540,a70541,a70542
	public static int[] modelIdsLZ={70002,70003,70004,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70039,70040,70041,70042,70043};
	public static int[] modelIdsEduLZ={70502,70503,70504,70505,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70005,70539,70540,70541,70542,70543};
	
	private String widescreen;
	private int bulletinNum;
	private int tabClass;//水磨沟定制需求，通知分教育局跟学校展示
	
	//推送网站
	private List<PushMessageTargetItem> pushUnitTargetItems;
	private String homePage;
	private boolean titleLink;
	private String isCheck;//是否选中
	private boolean registOff=false;//是否開啟
	
	//切换
	private boolean switchName=false;//是否開啟
	
	private Boolean xinJiangDeploy;//是否新疆部署
	private Boolean jiAnLiuZhongDeploy;//是否吉安六中部署
	
	private boolean isBulletin1New=false;
	private boolean isBulletin2New=false;
	private boolean isBulletin3New=false;
	private boolean isBulletin4New=false;
	private boolean isMsgNew=false;
	
	private TeacherService teacherService;
	
	private OfficeMsgReceivingService officeMsgReceivingService;
	
	//审核设置
	private OfficeBulletinSetService officeBulletinSetService;
	private OfficeBulletinSet officeBulletinSet;
	private List<OfficeBulletinSet> officeBulletinSetList;
	
	//通知公告
	private String type;
	private List<ReadInfoDto> readInfoDtos=new ArrayList<ReadInfoDto>();
	private String users;
	
	private boolean queryBu;
	
	public List<OfficeBulletinType> getOfficeBulletinTypelist() {
		return officeBulletinTypelist;
	}

	public void setOfficeBulletinTypelist(
			List<OfficeBulletinType> officeBulletinTypelist) {
		this.officeBulletinTypelist = officeBulletinTypelist;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String viewList(){
		String unitId = getUnitId();
		
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		if (StringUtils.equals("2", bulletinType)&&BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool())){
			officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
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
		}else shheAdmin = isPracticeAdmin(Constants.BULLETIN_AUDIT_+bulletinType);
		
		teachAreaList = teachAreaService.getTeachAreas(getUnitId());
		//教育局端没有校区控制
		if(teachAreaList==null){
			teachAreaList = new ArrayList<TeachArea>();
		}
		
		
		if((StringUtils.isBlank(type))&&StringUtils.equals(bulletinType, "1")){//通知公告
			officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"1","3"},unitId, getLoginUser().getUserId(), searchAreaId, OfficeBulletin.STATE_PASS, startTime, endTime, searName, publishName, getPage());
		}else if(StringUtils.isNotBlank(type)&&StringUtils.equals(type, "3")&&StringUtils.equals(bulletinType, "1")){
			officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"3"},unitId, getLoginUser().getUserId(), searchAreaId, OfficeBulletin.STATE_PASS, startTime, endTime, searName, publishName, getPage());
		}else{
			officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {bulletinType},unitId, getLoginUser().getUserId(), searchAreaId, OfficeBulletin.STATE_PASS, startTime, endTime, searName, publishName, getPage());
		}
		markWeek(officeBulletinList);
		return SUCCESS;
	}
	
	public String viewIsReadInfo(){//TODO
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		if((StringUtils.isBlank(type))&&StringUtils.equals(bulletinType, "1")){//通知公告
			readInfoDtos = officeBulletinService.getOfficeBulletinReadInfoDto(bulletinId, new String[] {"1","3"}, getUnitId());
		}else if(StringUtils.isNotBlank(type)&&StringUtils.equals(type, "3")&&StringUtils.equals(bulletinType, "1")){
			readInfoDtos =officeBulletinService.getOfficeBulletinReadInfoDto(bulletinId,new String[] {"3"},getUnitId());
		}else{
			readInfoDtos =officeBulletinService.getOfficeBulletinReadInfoDto(bulletinId,new String[] {bulletinType},getUnitId());
		}
		for (ReadInfoDto info : readInfoDtos) {
			List<String> userList=info.getUserIdList();
			if(CollectionUtils.isNotEmpty(userList)){
				for (String string : userList) {
					if(org.apache.commons.lang3.StringUtils.isBlank(users)){
						users=string;
					}else{
						users+=","+string;
					}
				}
			}
		}
		return SUCCESS;
	}
	
	public String sendMsg(){
		try {
			officeBulletinService.sendMsg(getUnitId(), getLoginUser().getUserId(), users, bulletinId);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("发送失败");
		}
		return SUCCESS;
	}
	public String manageList(){
		if(StringUtils.isBlank(show)){
			show = OfficeBulletin.STATE_ALL;
		}
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		if (StringUtils.equals("2", bulletinType)&&BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool())){
			officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
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
		}else shheAdmin = isPracticeAdmin(Constants.BULLETIN_AUDIT_+bulletinType);
		

		
		String userId = "";
		//如果需要审核，那么展示的是自己新增的信息
		if(isNeedAudit()){
			userId = getLoginUser().getUserId();
		}
		teachAreaList = teachAreaService.getTeachAreas(getUnitId());
		//教育局端没有校区控制
		if(teachAreaList==null){
			teachAreaList = new ArrayList<TeachArea>();
		}
		
		if((StringUtils.isBlank(type))&&StringUtils.equals(bulletinType, "1")){//通知公告
			officeBulletinList = officeBulletinService.getOfficeBulletinPage(getUnitId(),getLoginUser().getUserId(),userId,new String[]{"1","3"},show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		}else if(StringUtils.isNotBlank(type)&&StringUtils.equals(type, "3")&&StringUtils.equals(bulletinType, "1")){
			officeBulletinList = officeBulletinService.getOfficeBulletinPage(getUnitId(),getLoginUser().getUserId(),userId,new String[]{"3"},show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		}else{
			officeBulletinList = officeBulletinService.getOfficeBulletinPage(getUnitId(),getLoginUser().getUserId(),userId,new String[]{bulletinType},show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		}
		
		
		officeBulletinType = officeBulletinTypeService.getOfficeBulletinTypeByType(bulletinType);
		
		markWeek(officeBulletinList);
		return SUCCESS;
	}
	
	public void markWeek(List<OfficeBulletin> officeBulletinList){
		if(CollectionUtils.isNotEmpty(officeBulletinList)){
			for(OfficeBulletin officeBulletin:officeBulletinList){
				try {
					officeBulletin.setWeekDay(OfficeUtils.dayForWeek(officeBulletin.getCreateTime()));
				} catch (ParseException e) {
					e.printStackTrace();
					officeBulletin.setWeekDay("");
				}
			}
		}
		
	}
	public String getUnitType(){
		Unit unit = unitService.getUnit(getLoginInfo().getUnitID());
		String unitType = "";
		if(StringUtils.equals(unit.getUnitclass()+"", "1")){
			if(StringUtils.equals(unit.getParentid(), BaseConstant.ZERO_GUID)){
				//顶级教育局
				unitType = "1";
			}else{
				//下属教育局
				unitType = "2";
			}
		}else if(StringUtils.equals(unit.getUnitclass()+"", "2")){
			//学校
			unitType = "3";
		}
		return unitType;
	}
	public String bulletinEdit(){
		// 获取是否需要发短信
		officeBulletinType =  officeBulletinTypeService.getOfficeBulletinTypeByType(bulletinType);
		if(officeBulletinType == null){
			officeBulletinType  = new OfficeBulletinType();
		}
		
		if(StringUtils.isNotBlank(bulletinId)){
			bulletin = officeBulletinService.getOfficeBulletinById(bulletinId);
		}else{
			bulletin = new OfficeBulletin();
			bulletin.setOrderId(0);//默认为0
			bulletin.setCreateTime(new Date());
			bulletin.setEndType(OfficeBulletin.END_TYPE_ONEYEAR);//默认截止时间一年
			bulletin.setIsAll(true);
			bulletin.setAreaId(BaseConstant.ZERO_GUID);//默认32个0，为全校
			bulletin.setType("1");
		}
		teachAreaList = teachAreaService.getTeachAreas(getUnitId());
		//教育局端没有校区控制
		if(teachAreaList==null){
			teachAreaList = new ArrayList<TeachArea>();
		}
		return SUCCESS;
	}
	
	public String saveOrUpdate(){
		//getRequest();
		if ("1".equals(isCheck)&&StringUtils.isNotBlank(pushUnitTargetItemId)) {// 发送且推送到单位网站
			bulletin.setTitleLink(titleLink);
            String url = unitService.getUnit(getLoginInfo().getUnitID()).getHomepage();
			//String url="http://wpdcc.szxy.edu88.com/";
            String currentUrl = "http://"+ getRequest().getServerName()+":"+request.getServerPort()+request.getContextPath();
            if (!Validators.isEmpty(url)) {
                getPushMessageBean(url).pushMessage(pushUnitTargetItemId, bulletin, getLoginInfo(),currentUrl);
            }
        }
		try {
			if (bulletin.getCreateTime().after(new Date())) {
	            jsonError = "创建时间不能超过当前时间！";
	            return SUCCESS;
	        }
			
			//判断是否全校
			if(StringUtils.isNotBlank(bulletin.getAreaId())){
				if(StringUtils.equals(BaseConstant.ZERO_GUID, bulletin.getAreaId())){
					bulletin.setIsAll(true);
				}else{
					bulletin.setIsAll(false);
				}
			}else{
				bulletin.setAreaId(BaseConstant.ZERO_GUID);
				bulletin.setIsAll(false);
			}
			if(bulletin.getCreateTime() == null){
				bulletin.setCreateTime(new Date());
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(bulletin.getCreateTime());
			//设置截止时间
			if(OfficeBulletin.END_TYPE_PERMANENT.equals(bulletin.getEndType())){
				bulletin.setEndTime(null);
			}else if(OfficeBulletin.END_TYPE_ONEYEAR.equals(bulletin.getEndType())){
				calendar.add(Calendar.YEAR, 1);
				bulletin.setEndTime(calendar.getTime());
			}else if(OfficeBulletin.END_TYPE_HALFYEAR.equals(bulletin.getEndType())){
				calendar.add(Calendar.MONTH, 6);
				bulletin.setEndTime(calendar.getTime());
			}else if(OfficeBulletin.END_TYPE_ONEMONTH.equals(bulletin.getEndType())){
				calendar.add(Calendar.MONTH, 1);
				bulletin.setEndTime(calendar.getTime());
			}
			if(OfficeBulletin.STATE_PASS.equals(bulletin.getState())){
				bulletin.setAuditUserId(getLoginUser().getUserId());
				bulletin.setPublishTime(new Date());
			}
			if(StringUtils.isBlank(bulletin.getCreateUserId())){
				bulletin.setCreateUserId(getLoginUser().getUserId());
			}
			bulletin.setIsTop(false);
			if(StringUtils.isNotBlank(bulletinType)&&!(StringUtils.equals(bulletinType, "1")||StringUtils.equals(bulletinType, "3"))){
				bulletin.setType(bulletinType);
			}
			if(StringUtils.isNotBlank(bulletinType)&&StringUtils.equals(bulletinType, "1")&&(getXinJiangDeploy()||getJiAnLiuZhongDeploy())){//新疆
				bulletin.setType(bulletinType);
			}
			bulletin.setUnitId(getUnitId());
			bulletin.setIsDeleted(0);
			bulletin.setModifyUserId(getLoginUser().getUserId());
			if(StringUtils.isBlank(bulletin.getId())){
				officeBulletinService.save(bulletin);
			}else{
				officeBulletinService.update(bulletin);
			}
			if(!StringUtils.equals(bulletin.getState(),"3")){
				return SUCCESS;
			}
			Set<String> unitIdsWeike= new HashSet<String>();
			unitIdsWeike=getUnitIds(unitIdsWeike,bulletin);
			officeBulletinService.sendWeikeMsg(bulletin, unitIdsWeike.toArray(new String[0]));
			// 发短信
			if(StringUtils.isNotBlank(bulletin.getSmsContent())){
				Set<String> unitIds= new HashSet<String>();
				if(StringUtils.isNotBlank(bulletin.getReleaseScope())){
					unitIds=getUnitIds(unitIds,bulletin);
					if(CollectionUtils.isEmpty(unitIds)){
						unitIds=new HashSet<String>();
					}
				}else if(StringUtils.equals(getUnitType(),"3")){
					//学校
					if(StringUtils.equals(BaseConstant.ZERO_GUID, bulletin.getAreaId())){
						unitIds.add(getUnitId());
					}else{
						List<Dept> depts = deptService.getDeptsByAreaId(bulletin.getAreaId());
						Set<String> deptIds = new HashSet<String>();
						for(Dept dept: depts){
							deptIds.add(dept.getId());
						}
						List<Teacher> teachers = teacherService.getTeachersByDeptIds(deptIds.toArray(new String[0]));
						JSONObject json = new JSONObject();
						json.put("msg", bulletin.getSmsContent());
						if(bulletin.getTiming()!=null && bulletin.getTiming()){
							StringBuffer sb = new StringBuffer("");
							String hour = leftPadZero(String.valueOf(Integer.parseInt(bulletin.getSmsTime().substring(11,13))), 2);
							String minute = leftPadZero(String.valueOf(Integer.parseInt(bulletin.getSmsTime().substring(14,16))), 2);
							sb.append(bulletin.getSmsTime().substring(0,10).replaceAll("-","")).append(hour).append(minute).append("00");
							String sendTime = sb.toString();
							json.put("sentTime", sendTime);
						}
						JSONArray receivers = new JSONArray();
						for(Teacher teacher:teachers){
							if(StringUtils.isNotBlank(teacher.getPersonTel())){
								JSONObject receiverJ = new JSONObject();
								if(Pattern.compile("^[1][3,4,5,8][0-9]{9}$").matcher(teacher.getPersonTel()).matches()){
									receiverJ.put("phone", teacher.getPersonTel());
									receiverJ.put("unitId", teacher.getUnitid());
									receiverJ.put("username", teacher.getName());
									receiverJ.put("userId", "");
									receivers.add(receiverJ);
								}
							}
						}
						if(receivers.size()>0){
							json.put("receivers", receivers);           
							SmsThread smsThread = new SmsThread(json);
							smsThread.start();
						}
						return SUCCESS;
					}
				}
				ClienSmsThread smsThread = new ClienSmsThread(unitIds);
				smsThread.start();
			}//TODO
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	public Set<String> getUnitIds(Set<String> unitIds,OfficeBulletin bulletin){
		if(StringUtils.isNotBlank(bulletin.getReleaseScope())){
			if(StringUtils.equals(bulletin.getReleaseScope(), OfficeBulletin.SCOPE_ALL_UNIT)){
				List<Unit> units = unitService.getAllNormalUnits(getUnitId(), true);
				for(Unit unit : units){
					unitIds.add(unit.getId());
				}
			}else if (StringUtils.equals(bulletin.getReleaseScope(), OfficeBulletin.SCOPE_SELF_AND_SON_UNIT)) {
				List<Unit> units = unitService.getUnderlingUnits(getUnitId());
				for(Unit unit : units){
					unitIds.add(unit.getId());
				}
				unitIds.add(getUnitId());
			}else if (StringUtils.equals(bulletin.getReleaseScope(), OfficeBulletin.SCOPE_SELF_UNIT)){
				unitIds.add(getUnitId());
			}
		}else if(StringUtils.isNotBlank(bulletin.getAreaId())){
			//学校
			if(StringUtils.equals(BaseConstant.ZERO_GUID, bulletin.getAreaId())){
				unitIds.add(getUnitId());
			}
		}else{
			unitIds.add(getUnitId());
		}
		return unitIds;
	}
	private class ClienSmsThread extends Thread{
		private Set<String> unitIds;
		public ClienSmsThread(Set<String> unitIds){
			this.unitIds = unitIds;
		}
		@Override
		public void run() {
			try {
				for(String unitId : unitIds){
					List<Teacher> teachers = teacherService.getTeachers(unitId);
					JSONObject json = new JSONObject();
					json.put("msg", bulletin.getSmsContent());
					if(bulletin.getTiming()!=null && bulletin.getTiming()){
						StringBuffer sb = new StringBuffer("");
						String hour = leftPadZero(String.valueOf(Integer.parseInt(bulletin.getSmsTime().substring(11,13))), 2);
						String minute = leftPadZero(String.valueOf(Integer.parseInt(bulletin.getSmsTime().substring(14,16))), 2);
						sb.append(bulletin.getSmsTime().substring(0,10).replaceAll("-","")).append(hour).append(minute).append("00");
						String sendTime = sb.toString();
						json.put("sentTime", sendTime);
					}
					JSONArray receivers = new JSONArray();
					for(Teacher teacher:teachers){
						if(StringUtils.isNotBlank(teacher.getPersonTel())){
							JSONObject receiverJ = new JSONObject();
							if(Pattern.compile("^[1][3,4,5,8][0-9]{9}$").matcher(teacher.getPersonTel()).matches()){
								receiverJ.put("phone", teacher.getPersonTel());
								receiverJ.put("unitId", teacher.getUnitid());
								receiverJ.put("username", teacher.getName());
								receiverJ.put("userId", "");
								receivers.add(receiverJ);
							}
						}
					}
					if(receivers.size()>0){
						json.put("receivers", receivers);           
						SmsThread smsThread = new SmsThread(json);
						smsThread.start();
					}
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
	}
	
	private class SmsThread extends Thread{
    	private JSONObject json;
    	public SmsThread(JSONObject json){
    		this.json = json;
    	}

		@Override
		public void run() {
			try {
				SendResult sr = ZDResponse.post(json);
				System.out.println("--通知公告短信发送--"+sr.getDescription());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("--通知公告短信发送失败--");
			}
 		}
    }
	
	public String bulletinDelete(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				officeBulletinService.delete(bulletinId,getLoginUser().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "删除失败";
			}
		}
		return SUCCESS;
	}
	
	public String bulletinDeleteIds(){
		if(ArrayUtils.isEmpty(bulletinIds)){
			jsonError = "传入的值为空";
		}else{
			try {
				Set<String>idsSet=new HashSet<String>();
				Map<String, OfficeBulletin> officeBulletinMapByIds = officeBulletinService.getOfficeBulletinMapByIds(bulletinIds);
				for (Map.Entry<String, OfficeBulletin> item : officeBulletinMapByIds.entrySet()) {
					OfficeBulletin value = item.getValue();
					idsSet.add(value.getId());
				}
				officeBulletinService.deleteIds(idsSet.toArray(new String[0]),getLoginUser().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "删除失败";
			}
		}
		return SUCCESS;
	}
	public String bulletinPublishIds(){
		if(ArrayUtils.isEmpty(bulletinIds)){
			jsonError = "传入的值为空";
		}else{
			try {
				Set<String>idsSet=new HashSet<String>();
				Map<String, OfficeBulletin> officeBulletinMapByIds = officeBulletinService.getOfficeBulletinMapByIds(bulletinIds);
				for (Map.Entry<String, OfficeBulletin> item : officeBulletinMapByIds.entrySet()) {
					OfficeBulletin value = item.getValue();
					if(!"4".equals(value.getState())&&!"3".equals(value.getState())){
						idsSet.add(value.getId());
					}
				}
				officeBulletinService.publish(idsSet.toArray(new String[0]),this.getLoginUser().getUserId(),null);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "发布失败";
			}
		}
		return SUCCESS;
	}
	
	public String bulletinSubmit(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				bulletin = officeBulletinService.getOfficeBulletinById(bulletinId);
				officeBulletinService.submit(bulletinId);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "提交失败";
			}
		}
		return SUCCESS;
	}
	
	public String bulletinPublish(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				Set<String> unitIds=new HashSet<String>();
				bulletin=officeBulletinService.getOfficeBulletinById(bulletinId);
				unitIds=getUnitIds(unitIds,bulletin);
				if(CollectionUtils.isEmpty(unitIds)){
					unitIds=new HashSet<String>();
				}
				officeBulletinService.publish(new String[]{bulletinId},this.getLoginUser().getUserId(),unitIds.toArray(new String[0]));
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "发布失败";
			}
		}
		return SUCCESS;
	}
	public String bulletinQxPublish(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				officeBulletinService.qxPublish(bulletinId,"");
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "取消失败";
			}
		}
		return SUCCESS;
	}
	//不通过
	public String bulletinUnPublish(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				 if(idea.trim().length() > 200){
						jsonError = "不通过原因不能超过200个字符";
				}else{
					if(StringUtils.isNotBlank(idea)){
						officeBulletinService.unPublish(bulletinId,this.getLoginUser().getUserId(),idea);
					}else { 
						jsonError = "请输入不通过原因";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "审核失败";
			}
		}
		return SUCCESS;
	}
	//置顶
	public String bulletinTop(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				officeBulletinService.top(bulletinId);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "审核失败";
			}
		}
		return SUCCESS;
	}
	//取消置顶
	public String bulletinQxTop(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				officeBulletinService.qxTop(bulletinId);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "审核失败";
			}
		}
		return SUCCESS;
	}
	
	public String saveOrder(){
		if(ArrayUtils.isEmpty(bulletinIds)){
			jsonError = "传入的值为空";
		}else{
			try {
				officeBulletinService.saveOrder(bulletinIds,orderIds,getLoginUser().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "保存排序失败";
			}
		}
		return SUCCESS;
	}
	
	public String auditList(){
		//  通知审核
		if(StringUtils.isBlank(show) || show == ""){
			show = OfficeBulletin.STATE_AUDITALL;
		}
		
		if((StringUtils.isBlank(type))&&StringUtils.equals(bulletinType, "1")){//通知公告
			officeBulletinList = officeBulletinService.getOfficeBulletinPage(getUnitId(),getLoginUser().getUserId(),null,new String[]{"1","3"},show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		}else if(StringUtils.isNotBlank(type)&&StringUtils.equals(type, "3")&&StringUtils.equals(bulletinType, "1")){
			officeBulletinList = officeBulletinService.getOfficeBulletinPage(getUnitId(),getLoginUser().getUserId(),null,new String[]{"3"},show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		}else{
			officeBulletinList = officeBulletinService.getOfficeBulletinPage(getUnitId(),getLoginUser().getUserId(),null,new String[]{bulletinType},show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		}
		
		
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		if (StringUtils.equals("2", bulletinType)&&BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool())){
			officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
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
		}else shheAdmin = isPracticeAdmin(Constants.BULLETIN_AUDIT_+bulletinType);
		teachAreaList = teachAreaService.getTeachAreas(getUnitId());
		//教育局端没有校区控制
		if(teachAreaList==null){
			teachAreaList = new ArrayList<TeachArea>();
		}
		return SUCCESS;
	}
	
	public String queryBulletinList(){
		if("Y".equals(widescreen)){
			bulletinNum=26;
		}else{
			bulletinNum=13;
		}
		officeBulletinTypelist = officeBulletinTypeService.getOfficeBulletinTypeList(Constants.SHOW_NUMBER_BULLETIN);
		
		if(officeBulletinTypelist.size() > 0){
			String unitId = getUnitId();
			
			if(StringUtils.isBlank(bulletinType)){
				bulletinType = "1";
			}
			Pagination page = new Pagination(30, false);
			officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {bulletinType}, unitId, getLoginUser().getUserId(), searchAreaId, OfficeBulletin.STATE_PASS, startTime, endTime, searName, publishName, page);
			
			if(getJiAnLiuZhongDeploy()){
				if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
					modelId = modelIdsEduLZ[Integer.parseInt(bulletinType)-1];
				}else{
					modelId = modelIdsLZ[Integer.parseInt(bulletinType)-1];
				}
			}else{
				if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
					modelId = modelIdsEdu[Integer.parseInt(bulletinType)-1];
				}else{
					modelId = modelIds[Integer.parseInt(bulletinType)-1];
				}
			}
			module = moduleService.getModuleByIntId(modelId);
		}
		return SUCCESS;
	}
	
	
	public String smgBulletinTab(){
		modelId = modelIds[Integer.parseInt(bulletinType)-1];
		module = moduleService.getModuleByIntId(modelId);
		return SUCCESS;
	}
	
	public String smgBulletinList(){
		String unitId = getUnitId();
		
		if(StringUtils.isBlank(bulletinType)){
			bulletinType = "1";
		}
		Pagination page = new Pagination(10, false);
		officeBulletinList = officeBulletinService.getOfficeBulletinListPage(bulletinType, unitId, getLoginUser().getUserId(), OfficeBulletin.STATE_PASS, tabClass, page);
		return SUCCESS;
	}
	
	public String viewDetail(){
		bulletin = officeBulletinService.getOfficeBulletinById(bulletinId);
		OfficeBulletinRead obr = new OfficeBulletinRead();
		obr.setUnitId(getLoginUser().getUnitId());
		obr.setUserId(getLoginUser().getUserId());
		if(bulletin!=null&&StringUtils.isNotBlank(bulletin.getType())){
			obr.setBulletinType(bulletin.getType());
		}
		obr.setBulletinId(bulletinId);
		officeBulletinReadService.save(obr);
		try {
			bulletin.setWeekDay(OfficeUtils.dayForWeek(bulletin.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String onlyViewDetail(){
		bulletin = officeBulletinService.getOfficeBulletinById(bulletinId);
		try {
			bulletin.setWeekDay(OfficeUtils.dayForWeek(bulletin.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String auditSet(){//TODO
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
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
			officeBulletinSet.setRoleCode(Constants.BULLETIN_MANAGE_+bulletinType);
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
	/**
	 * 判断是否是审核人
	 */
	
	public static String leftPadZero(String str, int len) {
		if (StringUtils.isBlank(str))
			return str;

		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}
	
	public List<OfficeBulletin> getOfficeBulletinList() {
		return officeBulletinList;
	}

	public void setOfficeBulletinList(List<OfficeBulletin> officeBulletinList) {
		this.officeBulletinList = officeBulletinList;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public void setOfficeBulletinService(OfficeBulletinService officeBulletinService) {
		this.officeBulletinService = officeBulletinService;
	}
	
	public void setOfficeBulletinReadService(
			OfficeBulletinReadService officeBulletinReadService) {
		this.officeBulletinReadService = officeBulletinReadService;
	}

	public void setOfficeBulletinTypeService(
			OfficeBulletinTypeService officeBulletinTypeService) {
		this.officeBulletinTypeService = officeBulletinTypeService;
	}

	public void setTeachAreaService(TeachAreaService teachAreaService) {
		this.teachAreaService = teachAreaService;
	}

	public String getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}

	public OfficeBulletin getBulletin() {
		if(bulletin == null){
			bulletin = new OfficeBulletin();
		}
		return bulletin;
	}

	public void setBulletin(OfficeBulletin bulletin) {
		this.bulletin = bulletin;
	}

	public List<TeachArea> getTeachAreaList() {
		return teachAreaList;
	}

	public void setTeachAreaList(List<TeachArea> teachAreaList) {
		this.teachAreaList = teachAreaList;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public String getBulletinType() {
		return bulletinType;
	}

	public void setBulletinType(String bulletinType) {
		this.bulletinType = bulletinType;
	}

	public String getBulletinName() {
		if(StringUtils.isNotBlank(bulletinType)){
			OfficeBulletinType officeBulletinType = officeBulletinTypeService.getOfficeBulletinTypeByType(bulletinType);
			if(officeBulletinType!=null){
				bulletinName = officeBulletinType.getName();
			}
		}
		return bulletinName;
	}

	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getSearName() {
		return searName;
	}

	public void setSearName(String searName) {
		this.searName = searName;
	}
	
	public boolean isMegAdmin() {
		return megAdmin;
	}

	public void setMegAdmin(boolean megAdmin) {
		this.megAdmin = megAdmin;
	}

	public void setShheAdmin(boolean shheAdmin) {
		this.shheAdmin = shheAdmin;
	}

	public boolean isNeedAudit() {
		if(StringUtils.equals(bulletinType,"2")&&BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool()))
		{
			officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
			if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
				officeBulletinSet=officeBulletinSetList.get(0);
			}
			if(officeBulletinSet!=null&&StringUtils.equals("1", officeBulletinSet.getNeedAudit())){
						needAudit=true;
			}else if(officeBulletinSet==null){
				if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
					this.needAudit =  false;
				}else this.needAudit =  true;
			}else {
				this.needAudit =  false;
			}
		}else{
			String value = systemIniService.getValue(BaseConstant.BULLETIN_AUDIT);
			if(value != null && value.equals("1")){
				this.needAudit =  true;
			}else{
				this.needAudit =  false;
			}
		}
			return needAudit;
	}

	public void setNeedAudit(boolean needAudit) {
		this.needAudit = needAudit;
	}

	public String[] getBulletinIds() {
		return bulletinIds;
	}

	public void setBulletinIds(String[] bulletinIds) {
		this.bulletinIds = bulletinIds;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public String getSearchAreaId() {
		return searchAreaId;
	}

	public void setSearchAreaId(String searchAreaId) {
		this.searchAreaId = searchAreaId;
	}

	public String[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public String getWidescreen() {
		return widescreen;
	}
	
	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setWidescreen(String widescreen) {
		this.widescreen = widescreen;
	}
	
	public OfficeBulletinType getOfficeBulletinType() {
		return officeBulletinType;
	}
	
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setOfficeBulletinType(OfficeBulletinType officeBulletinType) {
		this.officeBulletinType = officeBulletinType;
	}

	public int getBulletinNum() {
		return bulletinNum;
	}

	public void setBulletinNum(int bulletinNum) {
		this.bulletinNum = bulletinNum;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getUnitName(){
		return getLoginInfo().getUnitName();
	}
	
	public String getUserName(){
		return getLoginInfo().getUser().getRealname();
	}

	public int getTabClass() {
		return tabClass;
	}

	public void setTabClass(int tabClass) {
		this.tabClass = tabClass;
	}
	public void setPushUnitTargetItems(
			List<PushMessageTargetItem> pushUnitTargetItems) {
		this.pushUnitTargetItems = pushUnitTargetItems;
	}

	public List<PushMessageTargetItem> getPushUnitTargetItems() {
        if (null == this.pushUnitTargetItems) {
            try {
                String url = unitService.getUnit(getLoginUser().getUnitId()).getHomepage();
            	//String url="http://wpdcc.szxy.edu88.com/";
                if (!Validators.isEmpty(url)) {
                    this.pushUnitTargetItems = getPushMessageBean(url).getAvailableTargetItems();
                    System.out.println(pushUnitTargetItems.size());
                }
                else {
                    this.pushUnitTargetItems = Collections.emptyList();
                }
            }
            catch (Exception e) {
                this.pushUnitTargetItems = Collections.emptyList();
                e.printStackTrace();
            }
        }
        return this.pushUnitTargetItems;
    }

	protected PushMessageService getPushMessageBean(String url) {
		url=StringUtils.trim(url);
        String serviceUrl = url + "/Service/CommonService.asmx?wsdl";
        pushMessageService.initialize(serviceUrl);
        return pushMessageService;
    }

	public void setPushMessageService(PushMessageService pushMessageService) {
		this.pushMessageService = pushMessageService;
	}

	/**
	 * @return the homePage
	 */
	public String getHomePage() {
		if(homePage==null){
			return homePage=unitService.getUnit(getLoginUser().getUnitId()).getHomepage();
		}
		return homePage;
	}

	/**
	 * @param homePage the homePage to set
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getPushUnitTargetItemId() {
		return pushUnitTargetItemId;
	}

	public void setPushUnitTargetItemId(String pushUnitTargetItemId) {
		this.pushUnitTargetItemId = pushUnitTargetItemId;
	}

	public boolean isTitleLink() {
		return titleLink;
	}

	public void setTitleLink(boolean titleLink) {
		this.titleLink = titleLink;
	}

	/**
	 * @return the isCheck
	 */
	public String getIsCheck() {
		return isCheck;
	}

	/**
	 * @param isCheck the isCheck to set
	 */
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public boolean isRegistOff() {
		String standardValue = systemIniService
				.getValue("REGIST_OFF");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}

	public void setRegistOff(boolean registOff) {
		this.registOff = registOff;
	}

	public boolean isSwitchName() {
		String standardValue = systemIniService
				.getValue("SWITCH_NAME");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}

	public void setSwitchName(boolean switchName) {
		this.switchName = switchName;
	}

	public void setOfficeBulletinSetService(
			OfficeBulletinSetService officeBulletinSetService) {
		this.officeBulletinSetService = officeBulletinSetService;
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

	public void setOfficeBulletinSetList(List<OfficeBulletinSet> officeBulletinSetList) {
		this.officeBulletinSetList = officeBulletinSetList;
	}

	public boolean isShheAdmin() {
		return shheAdmin;
	}

	public Boolean getXinJiangDeploy() {
		xinJiangDeploy = false;
		if (BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool())){
			xinJiangDeploy = true;
		}
		return xinJiangDeploy;
	}

	public void setXinJiangDeploy(Boolean xinJiangDeploy) {
		this.xinJiangDeploy = xinJiangDeploy;
	}

	public boolean isBulletin1New() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"1"}, getUnitId(),
				getLoginUser().getUserId(), null, OfficeBulletin.STATE_PASS, null, null, null, null, page);
		for(OfficeBulletin officeBulletin:officeBulletinList){
			try {
				if(!officeBulletin.getIsRead()){
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setBulletin1New(boolean isBulletin1New) {
		this.isBulletin1New = isBulletin1New;
	}

	public boolean isBulletin2New() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"2"},getUnitId(),
				getLoginUser().getUserId(), null, OfficeBulletin.STATE_PASS, null, null, null, null, page);
		for(OfficeBulletin officeBulletin:officeBulletinList){
			try {
				if(!officeBulletin.getIsRead()){
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setBulletin2New(boolean isBulletin2New) {
		this.isBulletin2New = isBulletin2New;
	}

	public boolean isBulletin3New() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"3"}, getUnitId(),
				getLoginUser().getUserId(), null, OfficeBulletin.STATE_PASS, null, null, null, null, page);
		for(OfficeBulletin officeBulletin:officeBulletinList){
			try {
				if(!officeBulletin.getIsRead()){
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setBulletin3New(boolean isBulletin3New) {
		this.isBulletin3New = isBulletin3New;
	}

	public boolean isBulletin4New() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"4"}, getUnitId(),
				getLoginUser().getUserId(), null, OfficeBulletin.STATE_PASS, null, null, null, null, page);
		for(OfficeBulletin officeBulletin:officeBulletinList){
			try {
				if(!officeBulletin.getIsRead()){
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setBulletin4New(boolean isBulletin4New) {
		this.isBulletin4New = isBulletin4New;
	}

	public boolean isMsgNew() {
		Integer unReadNum = officeMsgReceivingService.getNumber(getLoginUser().getUserId(), Constants.UNREAD+"");
		if(this.getDeptReceiver()){
			unReadNum += officeMsgReceivingService.getNumber(getLoginInfo().getUser().getDeptid(), Constants.UNREAD+"");
		}
		if(this.getUnitReceiver()){
			unReadNum += officeMsgReceivingService.getNumber(getLoginInfo().getUnitID(), Constants.UNREAD+"");
		}
		if(unReadNum > 0) 
			return true;
		else 
			return false;
	}

	public void setMsgNew(boolean isMsgNew) {
		this.isMsgNew = isMsgNew;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}
	
	public Boolean getDeptReceiver() {
		boolean deptReceiver = true;
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "dept_receiver");
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				deptReceiver = false;
				for(CustomRoleUser item : roleUserList){
					if(StringUtils.equals(getLoginInfo().getUser().getId(), item.getUserId())){
						return true;
					}
				}
			}
		}
		return deptReceiver;
	}
	
	public Boolean getUnitReceiver() {
		boolean unitReceiver = true;
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "unit_receiver");
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				unitReceiver = false;
				for(CustomRoleUser item : roleUserList){
					if(StringUtils.equals(getLoginInfo().getUser().getId(), item.getUserId())){
						return true;
					}
				}
			}
		}
		return unitReceiver;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getJiAnLiuZhongDeploy() {
		jiAnLiuZhongDeploy = false;
		if (BaseConstant.SYS_DEPLOY_SCHOOL_JIAN.equals(getSystemDeploySchool())){
			jiAnLiuZhongDeploy = true;
		}
		return jiAnLiuZhongDeploy;
	}

	public void setJiAnLiuZhongDeploy(Boolean jiAnLiuZhongDeploy) {
		this.jiAnLiuZhongDeploy = jiAnLiuZhongDeploy;
	}

	public List<ReadInfoDto> getReadInfoDtos() {
		return readInfoDtos;
	}

	public void setReadInfoDtos(List<ReadInfoDto> readInfoDtos) {
		this.readInfoDtos = readInfoDtos;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public boolean isQueryBu() {
		return queryBu;
	}

	public void setQueryBu(boolean queryBu) {
		this.queryBu = queryBu;
	}
	
}
