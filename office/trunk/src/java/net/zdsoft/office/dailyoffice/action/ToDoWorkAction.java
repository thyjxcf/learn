package net.zdsoft.office.dailyoffice.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.entity.OfficedocMsgDto;
import net.zdsoft.eis.base.subsystemcall.service.OfficedocSubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.SchsecuritySubsystemService;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.OfficeBulletinType;
import net.zdsoft.office.bulletin.service.OfficeBulletinService;
import net.zdsoft.office.bulletin.service.OfficeBulletinTypeService;
import net.zdsoft.office.dailyoffice.dto.ToDoDto;
import net.zdsoft.office.expenditure.entity.OfficeExpenditure;
import net.zdsoft.office.expenditure.service.OfficeExpenditureService;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author chens
 * @version 创建时间：2015-9-14 上午9:28:35
 * 
 */
@SuppressWarnings("serial")
public class ToDoWorkAction extends RemoteBaseAction  {

	private List<ToDoDto> toDoDtos;
	private List<Module> modules;
	
	private ModuleService moduleService;
	private ServerService serverService;
	private OfficedocSubsystemService officedocSubsystemService;
	private SchsecuritySubsystemService schsecuritySubsystemService;
	private UnitService unitService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private Boolean gdmmjyDeploy;//茂名部署
	private OfficeExpenditureService officeExpenditureService;
	
	//切换
	private boolean switchName=false;//是否切换
	private OfficeMsgReceivingService officeMsgReceivingService;
	private OfficeBulletinService officeBulletinService;
	private OfficeBulletinTypeService officeBulletinTypeService;
	private boolean isNewsNew=false;
	private boolean isBulletinNew2=false;
	private boolean isBulletinNew5=false;
	private boolean isBulletinNew3=false;
	private boolean isBulletinNew6=false;//吉安
	private boolean isBulletinNew4=false;
	private boolean isBulletinNew7=false;
	private boolean isBulletinNew8=false;
	private boolean isBulletinNew9=false;
	private boolean isBulletinNew10=false;
	private boolean isDocNew=false;
	private boolean isEdu;
	
	@Override
	public String execute() throws Exception {
		toDoDtos = new ArrayList<ToDoDto>();
		ToDoDto toDoDto = new ToDoDto();
		if(officedocSubsystemService != null) {
		    OfficedocMsgDto officedocMsgDto = officedocSubsystemService.getIndexItem("1", getLoginUser().getUserId());
		
    		if(officedocMsgDto.getCounnt() > 0){
    			toDoDto.setModuleSimpleName("公文");
    			toDoDto.setModuleContent("您有<span class='c-orange'>"+officedocMsgDto.getCounnt()+"</span>条收文督办未处理");
    			int moduleId = Integer.parseInt(officedocMsgDto.getModelId());
    			//学校端id+100
    			if(getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_SCHOOL){
    				moduleId = moduleId+100;
    			}
    			Module module = moduleService.getModuleByIntId(moduleId);
    			toDoDto.setModule(module);
    			toDoDtos.add(toDoDto);
    		}
    		toDoDto = new ToDoDto();
    		officedocMsgDto = officedocSubsystemService.getIndexItem("2", getLoginUser().getUserId());
    		if(officedocMsgDto.getCounnt() > 0){
    			toDoDto.setModuleSimpleName("公文");
    			toDoDto.setModuleContent("您有<span class='c-orange'>"+officedocMsgDto.getCounnt()+"</span>条收文阅办未处理");
    			int moduleId = Integer.parseInt(officedocMsgDto.getModelId());//学校端
    			//学校端
    			if(getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_SCHOOL){
    				moduleId = moduleId+100;
    			}
    			Module module = moduleService.getModuleByIntId(moduleId);
    			toDoDto.setModule(module);
    			toDoDtos.add(toDoDto);
    		}
    		toDoDto = new ToDoDto();
    		officedocMsgDto = officedocSubsystemService.getIndexItem("3", getLoginUser().getUserId());
    		if(officedocMsgDto.getCounnt() > 0){
    			toDoDto.setModuleSimpleName("公文");
    			toDoDto.setModuleContent("您有<span class='c-orange'>"+officedocMsgDto.getCounnt()+"</span>条发文未处理");
    			int moduleId = Integer.parseInt(officedocMsgDto.getModelId());//学校端
    			//学校端
    			if(getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_SCHOOL){
    				moduleId = moduleId+100;
    			}
    			Module module = moduleService.getModuleByIntId(moduleId);
    			toDoDto.setModule(module);
    			toDoDtos.add(toDoDto);
    		}
    		toDoDto = new ToDoDto();
    		officedocMsgDto = officedocSubsystemService.getIndexItem("4", getLoginUser().getUserId());
    		if(officedocMsgDto.getCounnt() > 0){
    			toDoDto.setModuleSimpleName("公文");
    			toDoDto.setModuleContent("您有<span class='c-orange'>"+officedocMsgDto.getCounnt()+"</span>条发文未发送");
    			int moduleId = Integer.parseInt(officedocMsgDto.getModelId());//学校端
    			//学校端
    			if(getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_SCHOOL){
    				moduleId = moduleId+100;
    			}
    			Module module = moduleService.getModuleByIntId(moduleId);
    			toDoDto.setModule(module);
    			toDoDtos.add(toDoDto);
    		}
    		toDoDto = new ToDoDto();
    		officedocMsgDto = officedocSubsystemService.getIndexItem("5", getLoginUser().getUserId());
    		if(officedocMsgDto.getCounnt() > 0){
    			toDoDto.setModuleSimpleName("公文");
    			toDoDto.setModuleContent("您有<span class='c-orange'>"+officedocMsgDto.getCounnt()+"</span>条收文未签收、登记");
    			int moduleId = Integer.parseInt(officedocMsgDto.getModelId());//学校端
    			//学校端
    			if(getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_SCHOOL){
    				moduleId = moduleId+100;
    			}
    			Module module = moduleService.getModuleByIntId(moduleId);
    			if(module!=null){
    				module.setUrl(module.getUrl()+"?jsParameter=noRegisterAll");
    			}
    			toDoDto.setModule(module);
    			toDoDtos.add(toDoDto);
    		}
		}
		//TODO
//		ToDoDto toDoDto = new ToDoDto();
//		toDoDto.setModuleSimpleName("会议");
//		toDoDto.setModuleContent("您有<span class='c-orange'>1</span>个会议取消 ");
//		Module module = moduleService.getModuleByIntId(70002);//通知模块id
//		toDoDto.setModule(module);
//		toDoDtos.add(toDoDto);
		if(getGdmmjyDeploy()){
			List<OfficeExpenditure> officeExpenditures=officeExpenditureService.toDoAudit(getUnitId(), getLoginUser().getUserId(), new Pagination(100, false));
			if(CollectionUtils.isNotEmpty(officeExpenditures)){
				ToDoDto toDoDto2 = new ToDoDto();
				toDoDto2.setModuleSimpleName("办公");
				toDoDto2.setModuleContent("您有<span class='c-orange'>"+officeExpenditures.size()+"</span>条财务开支申请未审核");
				int moduleId;
				if(getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_SCHOOL){
					moduleId =70085;
    			}else{
    				moduleId =70585;
    			}
				Module module = moduleService.getModuleByIntId(moduleId);
				if(module!=null){
    				module.setUrl(module.getUrl()+"?queryExpenditure=true");
    			}
				toDoDto2.setModule(module);
    			toDoDtos.add(toDoDto2);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 潍坊待办工作
	 * @return
	 */
	public String weiFangTodoWork(){
		JSONObject json = new JSONObject();
		json.put("userId", getLoginUser().getUserId());
//		json.put("userId", "FF8080812E945A2D012E9467F23A000A");
		// json.put(RemoteCallUtils.REMOTE_ZIP, 0); 这一行代码表示提供方返回的内容是不是经过压缩和编码，如果不写，默认是1，表示要压缩和编码
		//获取5.0公文地址
		Server server = serverService.getServerByServerCode(Server.SERVERCODE_ARCHIVE);
		if(server != null){
	        String url = server.getUrl();
	        if(!url.endsWith("/")){
	        	url +="/";
	        }
			String result = RemoteCallUtils.sendUrl(url+"archive3/officedata/archiveCountForSix.action", json);
//			String result = RemoteCallUtils.sendUrl("http://192.168.0.26:8080/archive3/officedata/archiveCountForSix.action", json);
			JSONObject j = JSONObject.fromObject(result);
			if (RemoteCallUtils.isSuccess(j) && !"0".equals(RemoteCallUtils.getResultStr(j))) {             //直接对返回结果进行操作
				JSONObject object = RemoteCallUtils.getResultObject(j);
				String readArcCount = object.getString("readArcCount");//待收阅发文条数
				String readReArchCount = object.getString("readReArchCount");//待收阅收文条数
				String archiveSendCount = object.getString("archiveSendCount");//发文发送
				String disposingCount = object.getString("disposingCount");//发文待处理条数
				String reDisposingCount = object.getString("reDisposingCount");//收文待处理条数
				String reRegistCount = object.getString("reRegistCount");//待登记公文列表
				url = object.getString("url");//跳转界面
				toDoDtos = new ArrayList<ToDoDto>();
				//待收阅发文条数
				if(StringUtils.isNotBlank(readArcCount) && !"0".equals(readArcCount)){
					ToDoDto toDoDto = new ToDoDto();
					toDoDto.setModuleSimpleName("公文");
	    			toDoDto.setModuleContent("您有<span class='c-orange'>"+readArcCount+"</span>条待收阅发文");
	    			toDoDto.setOpenUrl(url);
	    			toDoDtos.add(toDoDto);
				}
				//待收阅收文条数
				if(StringUtils.isNotBlank(readReArchCount) && !"0".equals(readReArchCount)){
					ToDoDto toDoDto = new ToDoDto();
					toDoDto.setModuleSimpleName("公文");
	    			toDoDto.setModuleContent("您有<span class='c-orange'>"+readReArchCount+"</span>条待收阅收文");
	    			toDoDto.setOpenUrl(url);
	    			toDoDtos.add(toDoDto);
				}
				//发文发送
				if(StringUtils.isNotBlank(archiveSendCount) && !"0".equals(archiveSendCount)){
					ToDoDto toDoDto = new ToDoDto();
					toDoDto.setModuleSimpleName("公文");
	    			toDoDto.setModuleContent("您有<span class='c-orange'>"+archiveSendCount+"</span>条发文发送");
	    			toDoDto.setOpenUrl(url);
	    			toDoDtos.add(toDoDto);
				}
				//发文条数
				if(StringUtils.isNotBlank(disposingCount) && !"0".equals(disposingCount)){
					ToDoDto toDoDto = new ToDoDto();
					toDoDto.setModuleSimpleName("公文");
	    			toDoDto.setModuleContent("您有<span class='c-orange'>"+disposingCount+"</span>条发文待处理");
	    			toDoDto.setOpenUrl(url);
	    			toDoDtos.add(toDoDto);
				}
				//收文待处理条数
				if(StringUtils.isNotBlank(reDisposingCount) && !"0".equals(reDisposingCount)){
					ToDoDto toDoDto = new ToDoDto();
					toDoDto.setModuleSimpleName("公文");
	    			toDoDto.setModuleContent("您有<span class='c-orange'>"+reDisposingCount+"</span>条收文待处理");
	    			toDoDto.setOpenUrl(url);
	    			toDoDtos.add(toDoDto);
				}
				//待登记公文列表
				if(StringUtils.isNotBlank(reRegistCount) && !"0".equals(reRegistCount)){
					ToDoDto toDoDto = new ToDoDto();
					toDoDto.setModuleSimpleName("公文");
	    			toDoDto.setModuleContent("您有<span class='c-orange'>"+reRegistCount+"</span>条待登记公文列表");
	    			toDoDto.setOpenUrl(url);
	    			toDoDtos.add(toDoDto);
				}
			}else {
				System.out.println("获取5.0公文数据失败");
			}
		}else {
			System.out.println("未配置5.0公文地址，无法获取相关待办数据");
		}
		return SUCCESS;
	}

	/**
	 * 校安代办工作
	 * @return
	 * @author huy
	 * @date 2016-1-13下午04:46:02
	 */
	public String schSecurityToDoWork(){
		String unitId = getLoginInfo().getUnitID();
		Map<String,Integer> toDoMap = schsecuritySubsystemService.queryToBeConfirmedCount(unitId);
		Unit unit = unitService.getUnit(unitId);
		String state = "";
		if(Unit.UNIT_CLASS_EDU == unit.getUnitclass()){
			if(Unit.UNIT_REGION_PROVINCE == unit.getRegionlevel()){
				state = "3";
			}else if(Unit.UNIT_REGION_CITY == unit.getRegionlevel()){
				state = "2";
			}else if(Unit.UNIT_REGION_COUNTY == unit.getRegionlevel()){
				state = "1";
			}
			toDoDtos = new ArrayList<ToDoDto>();
			ToDoDto toDoDto = new ToDoDto();
			toDoDto.setModuleSimpleName("非正常死亡上报");
			toDoDto.setModuleContent("您有<span class='c-orange'>"+toDoMap.get("abnormalDeathCount")+"</span>笔记录待确认");
			Module abnormalM = moduleService.getModuleByIntId(Module.SCHSECURITY_MODULE_FZCSWSB);
			abnormalM.setUrl(abnormalM.getUrl()+"?schsecurityAbnormalDeath.state="+state+"&isToDoWork=1");
			toDoDto.setModule(abnormalM);
			toDoDtos.add(toDoDto);
			
			toDoDto = new ToDoDto();
			toDoDto.setModuleSimpleName("猝死信息上报");
			toDoDto.setModuleContent("您有<span class='c-orange'>"+toDoMap.get("suddenDeathCount")+"</span>笔记录待确认");
			Module suddenM = moduleService.getModuleByIntId(Module.SCHSECURITY_MODULE_FZCSWSB);
			suddenM.setUrl(suddenM.getUrl()+"?schsecuritySuddenDeath.state="+state+"&isToDoWork=1");
			toDoDto.setModule(suddenM);
			toDoDtos.add(toDoDto);
			
			toDoDto = new ToDoDto();
			toDoDto.setModuleSimpleName("事故上报");
			toDoDto.setModuleContent("您有<span class='c-orange'>"+toDoMap.get("accidentConformingCount")+"</span>笔记录待确认");
			Module accidentM = moduleService.getModuleByIntId(Module.SCHSECURITY_MODULE_SGSB);
			accidentM.setUrl(accidentM.getUrl()+"?searchByKeyWordsDto.state="+state+"&isToDoWork=1");
			toDoDto.setModule(accidentM);
			toDoDtos.add(toDoDto);
		}
		else if(unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
			toDoDtos = new ArrayList<ToDoDto>();
			ToDoDto toDoDto = new ToDoDto();
			toDoDto.setModuleSimpleName("猝死信息上报");
			toDoDto.setModuleContent("您有<span class='c-orange'>"+toDoMap.get("suddenDeathNotComCount")+"</span>笔记录待填写");
			Module suddenM2 = moduleService.getModuleByIntId(Module.SCHSECURITY_MODULE_SCHFZCSWSB);
			suddenM2.setUrl(suddenM2.getUrl()+"?schsecuritySuddenDeath.state=0&isToDoWork=1");
			toDoDto.setModule(suddenM2);
			toDoDtos.add(toDoDto);
		}
		return SUCCESS;
	}
	
	/**
	 * //TODO
	 * 滨江最新消息
	 * @return
	 */
	public String bjLatestNews(){
		List<OfficeBulletinType> types = officeBulletinTypeService.getOfficeBulletinTypeList(Constants.SHOW_NUMBER_BULLETIN);
		Set<Integer> intIds = new HashSet<Integer>();
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			intIds.add(69052);
			for(OfficeBulletinType type : types){
				if(type.getType().equals("1")){
					intIds.add(70502);
				}
				if(type.getType().equals("2")){
					intIds.add(70503);
				}
				if(type.getType().equals("3")){
					intIds.add(70504);
				}
			}
		}else{
			intIds.add(69002);
			for(OfficeBulletinType type : types){
				if(type.getType().equals("1")){
					intIds.add(70002);
				}
				if(type.getType().equals("2")){
					intIds.add(70003);
				}
				if(type.getType().equals("3")){
					intIds.add(70004);
				}
			}
		}
		modules = moduleService.getModules(intIds.toArray(new Integer[0]));
		return SUCCESS;
	}
	
	/**
	 * 吉安六中最新消息
	 * @return
	 */
	public String jianlzLatestNews(){
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			Integer[] intIds = {69052,70502,70505,70539,70540,70541,70542,70543};
			modules = moduleService.getModules(intIds);
		}else{
			Integer[] intIds = {69002,70002,70005,70039,70040,70041,70042,70043};
			modules = moduleService.getModules(intIds);
		}
		return SUCCESS;
	}
	
	public List<ToDoDto> getToDoDtos() {
		return toDoDtos;
	}

	public void setToDoDtos(List<ToDoDto> toDoDtos) {
		this.toDoDtos = toDoDtos;
	}
	
	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public void setOfficedocSubsystemService(
			OfficedocSubsystemService officedocSubsystemService) {
		this.officedocSubsystemService = officedocSubsystemService;
	}

	public void setSchsecuritySubsystemService(
			SchsecuritySubsystemService schsecuritySubsystemService) {
		this.schsecuritySubsystemService = schsecuritySubsystemService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
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

	public boolean isNewsNew() {
		List<OfficeMsgReceiving> officeMsgReceivings = new ArrayList<OfficeMsgReceiving>();
		MessageSearch search = new MessageSearch();
//		search.setReadType(Constants.UNREAD+"");
//		search.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		//判断消息是否是最新的
		search.setReceiveUserId(getLoginUser().getUserId());
		Pagination page = new Pagination(10, false);
		List<OfficeMsgReceiving> list1 = officeMsgReceivingService.getOfficeMsgReceivingList(search, page);
		officeMsgReceivings.addAll(list1);
		if(this.getDeptReceiver()){
			search.setReceiveUserId(getLoginInfo().getUser().getDeptid());
			list1 = officeMsgReceivingService.getOfficeMsgReceivingList(search, page);
			officeMsgReceivings.addAll(list1);
		}
		if(this.getUnitReceiver()){
			search.setReceiveUserId(getLoginInfo().getUnitID());
			list1 = officeMsgReceivingService.getOfficeMsgReceivingList(search, page);
			officeMsgReceivings.addAll(list1);
		}
		Collections.sort(officeMsgReceivings, new Comparator<OfficeMsgReceiving>(){
			@Override
			public int compare(OfficeMsgReceiving o1, OfficeMsgReceiving o2){
				return o2.getSendTime().compareTo(o1.getSendTime());
			}
		});
		for(int i = 0; i < (officeMsgReceivings.size()>10?10:officeMsgReceivings.size()); i++){
			OfficeMsgReceiving officeMsgReceiving = officeMsgReceivings.get(i);
			try {
				if(StringUtils.equals(String.valueOf(officeMsgReceiving.getIsRead()),"0")){
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setNewsNew(boolean isNewsNew) {
		this.isNewsNew = isNewsNew;
	}

	public boolean isBulletinNew3() {
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

	public void setBulletinNew3(boolean isBulletinNew3) {
		this.isBulletinNew3 = isBulletinNew3;
	}

	public boolean isBulletinNew2() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"2"}, getUnitId(),
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

	public void setBulletinNew2(boolean isBulletinNew2) {
		this.isBulletinNew2 = isBulletinNew2;
	}

	public boolean isDocNew() {
		try{
			if(officedocSubsystemService != null) {
				OfficedocMsgDto officedocMsgDto = officedocSubsystemService.getIndexItem("1", getLoginUser().getUserId());
				
				if(officedocMsgDto.getCounnt() > 0){
					return true;
				}
				officedocMsgDto = officedocSubsystemService.getIndexItem("2", getLoginUser().getUserId());
				if(officedocMsgDto.getCounnt() > 0){
					return true;
				}
				officedocMsgDto = officedocSubsystemService.getIndexItem("3", getLoginUser().getUserId());
				if(officedocMsgDto.getCounnt() > 0){
					return true;
				}
				officedocMsgDto = officedocSubsystemService.getIndexItem("4", getLoginUser().getUserId());
				if(officedocMsgDto.getCounnt() > 0){
					return true;
				}
				officedocMsgDto = officedocSubsystemService.getIndexItem("5", getLoginUser().getUserId());
				if(officedocMsgDto.getCounnt() > 0){
					return true;
				}
			}
		}catch (Exception e) {
			return false;
		}
		return false;
	}

	public void setDocNew(boolean isDocNew) {
		this.isDocNew = isDocNew;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public void setOfficeBulletinService(OfficeBulletinService officeBulletinService) {
		this.officeBulletinService = officeBulletinService;
	}
	
	public boolean getDeptReceiver() {
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

	public boolean getUnitReceiver() {
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

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public boolean isEdu() {
		return getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU;
	}

	public void setEdu(boolean isEdu) {
		this.isEdu = isEdu;
	}

	public boolean isBulletinNew5() {
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

	public void setBulletinNew5(boolean isBulletinNew5) {
		this.isBulletinNew5 = isBulletinNew5;
	}

	public boolean isBulletinNew6() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"21"}, getUnitId(),
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

	public void setBulletinNew6(boolean isBulletinNew6) {
		this.isBulletinNew6 = isBulletinNew6;
	}

	public boolean isBulletinNew7() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"22"}, getUnitId(),
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

	public void setBulletinNew7(boolean isBulletinNew7) {
		this.isBulletinNew7 = isBulletinNew7;
	}

	public boolean isBulletinNew8() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"23"}, getUnitId(),
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

	public void setBulletinNew8(boolean isBulletinNew8) {
		this.isBulletinNew8 = isBulletinNew8;
	}

	public boolean isBulletinNew9() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"24"}, getUnitId(),
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

	public void setBulletinNew9(boolean isBulletinNew9) {
		this.isBulletinNew9 = isBulletinNew9;
	}

	public boolean isBulletinNew4() {
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

	public void setBulletinNew4(boolean isBulletinNew4) {
		this.isBulletinNew4 = isBulletinNew4;
	}

	public boolean isBulletinNew10() {
		Pagination page = new Pagination(10, false);
		List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {"25"}, getUnitId(),
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

	public void setBulletinNew10(boolean isBulletinNew10) {
		this.isBulletinNew10 = isBulletinNew10;
	}

	public void setOfficeBulletinTypeService(
			OfficeBulletinTypeService officeBulletinTypeService) {
		this.officeBulletinTypeService = officeBulletinTypeService;
	}

	public Boolean getGdmmjyDeploy() {
		gdmmjyDeploy = false;
		if (BaseConstant.SYS_DEPLOY_SCHOOL_GDMMJY.equals(getSystemDeploySchool())){
			gdmmjyDeploy = true;
		}
		return gdmmjyDeploy;
	}

	public void setGdmmjyDeploy(Boolean gdmmjyDeploy) {
		this.gdmmjyDeploy = gdmmjyDeploy;
	}

	public void setOfficeExpenditureService(
			OfficeExpenditureService officeExpenditureService) {
		this.officeExpenditureService = officeExpenditureService;
	}
	
}
