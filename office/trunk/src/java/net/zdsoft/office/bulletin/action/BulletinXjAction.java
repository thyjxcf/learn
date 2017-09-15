package net.zdsoft.office.bulletin.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.common.TaskUtil;
import net.zdsoft.leadin.dataimport.common.ZipUtil;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
import net.zdsoft.office.bulletin.entity.OfficeBulletinSet;
import net.zdsoft.office.bulletin.entity.OfficeBulletinType;
import net.zdsoft.office.bulletin.entity.OfficeBulletinXj;
import net.zdsoft.office.bulletin.service.OfficeBulletinReadService;
import net.zdsoft.office.bulletin.service.OfficeBulletinSetService;
import net.zdsoft.office.bulletin.service.OfficeBulletinTypeService;
import net.zdsoft.office.bulletin.service.OfficeBulletinXjService;
import net.zdsoft.office.dailyoffice.entity.OfficeTempComment;
import net.zdsoft.office.dailyoffice.service.OfficeTempCommentService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tools.zip.ZipOutputStream;

import com.google.common.collect.Lists;

/**
 * 新疆定制公告、教育信息ACTION
 */
@SuppressWarnings("serial")
public class BulletinXjAction extends PageAction {

	private OfficeBulletinXjService officeBulletinXjService;
	private OfficeBulletinReadService officeBulletinReadService;
	private OfficeBulletinTypeService officeBulletinTypeService;
	private ModuleService moduleService;
	private TeachAreaService teachAreaService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private UnitService unitService;
	private OfficeTempCommentService officeTempCommentService;
	
	private List<TeachArea> teachAreaList;
	private List<OfficeBulletinXj> officeBulletinXjList;
	private List<OfficeBulletinType> officeBulletinTypelist;

	private Module module;
	private OfficeBulletinXj bulletin;
	private OfficeBulletinType officeBulletinType;
	private OfficeTempComment officeTempComment;
	
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
	private String defaultStartWeekDay;//默认当前周的开始时间
	private String defaultEndWeekDay;//默认当前周的开始时间
	
	private boolean megAdmin;
	private boolean shheAdmin;
	private boolean needAudit;
	
	public int modelId;
	public static int[] modelIds ={70104,70105};
	public static int[] modelIdsEdu ={70604,70605};
	
	private String widescreen;
	private int bulletinNum;
	private int issue;
	
	//审核设置
	private OfficeBulletinSetService officeBulletinSetService;
	private OfficeBulletinSet officeBulletinSet;
	private List<OfficeBulletinSet> officeBulletinSetList;
	
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
		if (StringUtils.equals(bulletinType,"12")){
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

		officeBulletinXjList = officeBulletinXjService.getOfficeBulletinXjListPage(bulletinType,unitId, getLoginUser().getUserId(), searchAreaId, OfficeBulletinXj.STATE_PASS, startTime, endTime, searName, publishName, getPage());
		markWeek(officeBulletinXjList);
		return SUCCESS;
	}
	
	public String manageList(){
		if(StringUtils.isBlank(show)){
			show = OfficeBulletinXj.STATE_ALL;
		}
		if(StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)){
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 Calendar c = Calendar.getInstance();
			 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			 startTime = df.format(c.getTime());
			 c.add(Calendar.DAY_OF_MONTH, 6);
			 endTime = df.format(c.getTime());
		}
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		if (StringUtils.equals(bulletinType,"12")){
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
		officeBulletinXjList = officeBulletinXjService.getOfficeBulletinXjPage(getUnitId(),getLoginUser().getUserId(),userId,bulletinType,show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		
		officeBulletinType = officeBulletinTypeService.getOfficeBulletinTypeByType(bulletinType);
		
		markWeek(officeBulletinXjList);
		return SUCCESS;
	}
	
	public void markWeek(List<OfficeBulletinXj> officeBulletinXjList){
		if(CollectionUtils.isNotEmpty(officeBulletinXjList)){
			for(OfficeBulletinXj officeBulletinXj:officeBulletinXjList){
				try {
					officeBulletinXj.setWeekDay(OfficeUtils.dayForWeek(officeBulletinXj.getCreateTime()));
				} catch (ParseException e) {
					e.printStackTrace();
					officeBulletinXj.setWeekDay("");
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
		
		officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
		if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
			officeBulletinSet=officeBulletinSetList.get(0);
		}
		if(officeBulletinSet==null){
			officeBulletinSet=new OfficeBulletinSet();
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
				officeBulletinSet.setNeedAudit("0");
			}else officeBulletinSet.setNeedAudit("1");
		}
		
		if(StringUtils.isNotBlank(bulletinId)){
			bulletin = officeBulletinXjService.getOfficeBulletinXjById(bulletinId);
			officeTempComment = officeTempCommentService.getOfficeTempCommentById(bulletin.getTempCommentId());
			if(officeTempComment!=null){
				bulletin.setTempCommentName(officeTempComment.getTitle());
			}
		}else{
			bulletin = new OfficeBulletinXj();
			bulletin.setOrderId(0);//默认为0
			bulletin.setCreateTime(new Date());
			bulletin.setEndType(OfficeBulletinXj.END_TYPE_ONEYEAR);//默认截止时间一年
			bulletin.setIsAll(true);
			bulletin.setAreaId(BaseConstant.ZERO_GUID);//默认32个0，为全校
		}
		teachAreaList = teachAreaService.getTeachAreas(getUnitId());
		//教育局端没有校区控制
		if(teachAreaList==null){
			teachAreaList = new ArrayList<TeachArea>();
		}
		return SUCCESS;
	}
	
	public String saveOrUpdate(){
		try {
			if (bulletin.getCreateTime().after(new Date())) {
	            jsonError = "创建时间不能超过当前时间！";
	            return SUCCESS;
	        }
			//教育信息要对期号重复进行判断
			if(OfficeBulletinType.JYXX.equals(bulletinType)){
				boolean flag = officeBulletinXjService.isIssueExist(getUnitId(),bulletinType,bulletin.getIssue(),bulletin.getId());
				if(flag){
					jsonError = "期号已被使用，请修改";
					return SUCCESS;
				}
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
			if(OfficeBulletinXj.END_TYPE_PERMANENT.equals(bulletin.getEndType())){
				bulletin.setEndTime(null);
			}else if(OfficeBulletinXj.END_TYPE_ONEYEAR.equals(bulletin.getEndType())){
				calendar.add(Calendar.YEAR, 1);
				bulletin.setEndTime(calendar.getTime());
			}else if(OfficeBulletinXj.END_TYPE_HALFYEAR.equals(bulletin.getEndType())){
				calendar.add(Calendar.MONTH, 6);
				bulletin.setEndTime(calendar.getTime());
			}else if(OfficeBulletinXj.END_TYPE_ONEMONTH.equals(bulletin.getEndType())){
				calendar.add(Calendar.MONTH, 1);
				bulletin.setEndTime(calendar.getTime());
			}
			if(OfficeBulletinXj.STATE_PASS.equals(bulletin.getState())){
				bulletin.setAuditUserId(getLoginUser().getUserId());
				bulletin.setPublishTime(new Date());
			}
			if(StringUtils.isBlank(bulletin.getCreateUserId())){
				bulletin.setCreateUserId(getLoginUser().getUserId());
			}
			bulletin.setIsTop(false);
			bulletin.setType(bulletinType);
			bulletin.setUnitId(getUnitId());
			bulletin.setIsDeleted(0);
			bulletin.setModifyUserId(getLoginUser().getUserId());
			if(StringUtils.isBlank(bulletin.getId())){
				officeBulletinXjService.save(bulletin);
			}else{
				officeBulletinXjService.update(bulletin);
			}
			if(!StringUtils.equals(bulletin.getState(),"3")){
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	public String getLatestIssue(){
		try {
			Integer maxIssue = officeBulletinXjService.getLatestIssue(getUnitId(),bulletinType);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage(maxIssue+1+"");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("获取期号失败");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//TODO
	public String validateIssue(){
		try {
			boolean flag = officeBulletinXjService.isIssueExist(getUnitId(),bulletinType,issue,bulletinId);
			if(flag){
				jsonError = "期号已被使用，请修改";
			}
		} catch (Exception e) {
			jsonError = "期号已被使用，请修改";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String bulletinDelete(){
		if(StringUtils.isBlank(bulletinId)){
			jsonError = "传入的值为空";
		}else{
			try {
				officeBulletinXjService.delete(bulletinId,getLoginUser().getUserId());
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
				Map<String, OfficeBulletinXj> officeBulletinXjMapByIds = officeBulletinXjService.getOfficeBulletinXjMapByIds(bulletinIds);
				for (Map.Entry<String, OfficeBulletinXj> item : officeBulletinXjMapByIds.entrySet()) {
					OfficeBulletinXj value = item.getValue();
					idsSet.add(value.getId());
				}
				officeBulletinXjService.deleteIds(idsSet.toArray(new String[0]),getLoginUser().getUserId());
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
				Map<String, OfficeBulletinXj> officeBulletinXjMapByIds = officeBulletinXjService.getOfficeBulletinXjMapByIds(bulletinIds);
				for (Map.Entry<String, OfficeBulletinXj> item : officeBulletinXjMapByIds.entrySet()) {
					OfficeBulletinXj value = item.getValue();
					if(!"4".equals(value.getState())&&!"3".equals(value.getState())){
						idsSet.add(value.getId());
					}
				}
				officeBulletinXjService.publish(idsSet.toArray(new String[0]),this.getLoginUser().getUserId());
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
				bulletin = officeBulletinXjService.getOfficeBulletinXjById(bulletinId);
				officeBulletinXjService.submit(bulletinId);
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
				officeBulletinXjService.publish(new String[]{bulletinId},this.getLoginUser().getUserId());
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
				officeBulletinXjService.qxPublish(bulletinId,"");
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
						officeBulletinXjService.unPublish(bulletinId,this.getLoginUser().getUserId(),idea);
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
				officeBulletinXjService.top(bulletinId);
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
				officeBulletinXjService.qxTop(bulletinId);
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
				officeBulletinXjService.saveOrder(bulletinIds,orderIds,getLoginUser().getUserId());
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
			show = OfficeBulletinXj.STATE_AUDITALL;
		}
		officeBulletinXjList = officeBulletinXjService.getOfficeBulletinXjPage(getUnitId(),getLoginUser().getUserId(),null,bulletinType,show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		if (StringUtils.equals(bulletinType,"12")){
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
		officeBulletinTypelist = officeBulletinTypeService.getOfficeBulletinTypeList(Constants.SHOW_NUMBER_BULLETIN_XJ);
		
		if(officeBulletinTypelist.size() > 0){
			String unitId = getUnitId();
			
			if(StringUtils.isBlank(bulletinType)){
				bulletinType = "11";
			}
			Pagination page = new Pagination(30, false);
			officeBulletinXjList = officeBulletinXjService.getOfficeBulletinXjListPage(bulletinType, unitId, getLoginUser().getUserId(), searchAreaId, OfficeBulletinXj.STATE_PASS, startTime, endTime, searName, publishName, page);
			
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
				modelId = modelIdsEdu[Integer.parseInt(bulletinType)-11];
			}else{
				modelId = modelIds[Integer.parseInt(bulletinType)-11];
			}
			module = moduleService.getModuleByIntId(modelId);
		}
		return SUCCESS;
	}
	
	public String viewDetail(){
		OfficeBulletinRead obr = new OfficeBulletinRead();
		obr.setUnitId(getLoginUser().getUnitId());
		obr.setUserId(getLoginUser().getUserId());
		obr.setBulletinType(bulletinType);
		obr.setBulletinId(bulletinId);
		officeBulletinReadService.save(obr);
		bulletin = officeBulletinXjService.getOfficeBulletinXjById(bulletinId);
		try {
			bulletin.setWeekDay(OfficeUtils.dayForWeek(bulletin.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		officeTempComment = officeTempCommentService.getOfficeTempCommentById(bulletin.getTempCommentId());
		return SUCCESS;
	}
	
	public String onlyViewDetail(){
		bulletin = officeBulletinXjService.getOfficeBulletinXjById(bulletinId);
		try {
			bulletin.setWeekDay(OfficeUtils.dayForWeek(bulletin.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		officeTempComment = officeTempCommentService.getOfficeTempCommentById(bulletin.getTempCommentId());
		return SUCCESS;
	}
	
	public String auditSet(){//TODO
		megAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_+bulletinType);
		officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
		if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
			officeBulletinSet=officeBulletinSetList.get(0);
		}
		if (BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool())){
			if(officeBulletinSet!=null&&officeBulletinSet.getAuditId()!=null){
				for(String str:StringUtils.split(officeBulletinSet.getAuditId(), ",")){
					if(StringUtils.equals(str, getLoginUser().getUserId())){
						shheAdmin=true;
					}
				}
			}else shheAdmin=false;
		}else{
			shheAdmin = isPracticeAdmin(Constants.BULLETIN_AUDIT_+bulletinType);
		}
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
	 * 导出
	 * @return
	 */
	public void doExport() {//TODO
		if(StringUtils.isBlank(show)){
			show = OfficeBulletinXj.STATE_ALL;
		}
		String userId = "";
		if(isNeedAudit()){
			userId = getLoginUser().getUserId();
		}
		List<String> ids = null;
		if(StringUtils.isNotEmpty(bulletinIds[0])){
			ids = Lists.newArrayList(bulletinIds[0].split(","));
			officeBulletinXjList=officeBulletinXjService.getOfficeBulletinXjListByIds(ids.toArray(new String[0]));
		}else{
			officeBulletinXjList = officeBulletinXjService.getOfficeBulletinXjPage(getUnitId(),getLoginUser().getUserId(),userId,bulletinType,show,startTime,endTime, publishName, searName, searchAreaId, getPage());
		}
		
		Set<String> officeTempCommentIds = new HashSet<String>();
		for(OfficeBulletinXj item : officeBulletinXjList){
			officeTempCommentIds.add(item.getTempCommentId());
		}
		Map<String, OfficeTempComment> officeTempCommentMap = officeTempCommentService.getOfficeTempCommentMapByIds(officeTempCommentIds.toArray(new String[0]));
		
		try {
			Date creationTime = new Date();
			String time = DateUtils.date2String(creationTime, "yyyyMMdd");
			String fileName = "新疆教育_"+time;
			
			 // 创建目录
            String path = TaskUtil.createStoreSubdir(new String[] {
                    DataImportConstants.FILE_PATH_EXPORT_TEMPLATE, fileName });
			
			for(OfficeBulletinXj item : officeBulletinXjList){
				if(StringUtils.equals(item.getState(),String.valueOf(Constants.APPLY_STATE_PASS))){
					OfficeTempComment tempComment = officeTempCommentMap.get(item.getTempCommentId());
					String title = item.getTitle();
					String content = item.getContent();
					String messageNumber = item.getMessageNumber();
					String htmlContent = tempComment.getHtmlContent();
					if(StringUtils.isNotBlank(htmlContent)){
						htmlContent = htmlContent.replace("<span id=\"title\">&nbsp;</span>", "<span id=\"title\">"+ title +"</span>");
						htmlContent = htmlContent.replace("<span id=\"content\">&nbsp;</span>", "<span id=\"content\">"+ content +"</span>");
						htmlContent = htmlContent.replace("<span id=\"messageNumber\">&nbsp;</span>", "<span id=\"messageNumber\">"+ messageNumber +"</span>");
						
						String html = "<html><body>" + htmlContent + "</body></html>";
						this.htmlToWord(path, title, html);
					}
				}
			}
			// 压缩包
			getResponse().setHeader("Cache-Control", "");
			getResponse().setContentType("application/x-zip-compressed");
			getResponse().setHeader("Content-Disposition",
					"attachment; filename=" + URLUtils.encode(fileName+".zip", "UTF-8"));
	        OutputStream zos = new ZipOutputStream(getResponse().getOutputStream());
//			OutputStream zos = new ZipOutputStream(new FileOutputStream(path + ".zip"));
			ZipUtil.makeZip(path, (ZipOutputStream) zos);
			ZipUtil.deleteFile(path);// 先删再建，以便打包
            File file = new File(path);
            file.delete();
            zos.flush();
            zos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void htmlToWord(String path, String title, String html) throws IOException {
		InputStream is = new ByteArrayInputStream(html.getBytes("GBK"));
		String filePath = path + File.separator + title + ".doc";
		File file = new File(filePath);
		OutputStream os = new FileOutputStream(file);
		POIFSFileSystem fs = new POIFSFileSystem();
		fs.createDocument(is, "WordDocument");
		fs.writeFilesystem(os);
		os.flush();
		os.close();
		is.close();
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

	public List<OfficeBulletinXj> getOfficeBulletinXjList() {
		return officeBulletinXjList;
	}

	public void setOfficeBulletinXjList(List<OfficeBulletinXj> officeBulletinXjList) {
		this.officeBulletinXjList = officeBulletinXjList;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public void setOfficeBulletinXjService(OfficeBulletinXjService officeBulletinXjService) {
		this.officeBulletinXjService = officeBulletinXjService;
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

	public OfficeBulletinXj getBulletin() {
		if(bulletin == null){
			bulletin = new OfficeBulletinXj();
		}
		return bulletin;
	}

	public void setBulletin(OfficeBulletinXj bulletin) {
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

	public boolean isShheAdmin() {
		return shheAdmin;
	}

	public void setShheAdmin(boolean shheAdmin) {
		this.shheAdmin = shheAdmin;
	}

	public boolean isNeedAudit() {
		if(StringUtils.equals(bulletinType,"12")&&BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool()))
		{
			officeBulletinSetList=officeBulletinSetService.getOfficeBulletinSetByUnitIdList(getUnitId(),Constants.BULLETIN_MANAGE_+bulletinType);
			if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
				officeBulletinSet=officeBulletinSetList.get(0);
			}
			if(officeBulletinSet!=null&&StringUtils.equals("1", officeBulletinSet.getNeedAudit())){
						this.needAudit=true;
			}else if(officeBulletinSet==null){
				if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
					this.needAudit =  false;
				}else this.needAudit =  true;
			}else this.needAudit =  false;
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

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
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
	
	public void setOfficeBulletinType(OfficeBulletinType officeBulletinType) {
		this.officeBulletinType = officeBulletinType;
	}

	public int getBulletinNum() {
		return bulletinNum;
	}

	public void setBulletinNum(int bulletinNum) {
		this.bulletinNum = bulletinNum;
	}
	
	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public String getUnitName(){
		return getLoginInfo().getUnitName();
	}
	
	public String getUserName(){
		return getLoginInfo().getUser().getRealname();
	}

	public void setOfficeTempCommentService(
			OfficeTempCommentService officeTempCommentService) {
		this.officeTempCommentService = officeTempCommentService;
	}

	public OfficeTempComment getOfficeTempComment() {
		return officeTempComment;
	}

	public void setOfficeTempComment(OfficeTempComment officeTempComment) {
		this.officeTempComment = officeTempComment;
	}

	public String getDefaultStartWeekDay() {
		Calendar currentDate = new GregorianCalendar();   
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
		Date dateCursor = (Date)currentDate.getTime().clone();  
		defaultStartWeekDay = DateUtils.date2String(dateCursor, "yyyy-MM-dd");
		return defaultStartWeekDay;
	}

	public void setDefaultStartWeekDay(String defaultStartWeekDay) {
		this.defaultStartWeekDay = defaultStartWeekDay;
	}
	
	public String getDefaultEndWeekDay() {
		Calendar currentDate = new GregorianCalendar();   
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
		Date dateCursor = (Date)currentDate.getTime().clone();  
		defaultEndWeekDay = DateUtils.date2String(dateCursor, "yyyy-MM-dd");
		return defaultEndWeekDay;
	}

	public void setDefaultEndWeekDay(String defaultEndWeekDay) {
		this.defaultEndWeekDay = defaultEndWeekDay;
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
	
}
