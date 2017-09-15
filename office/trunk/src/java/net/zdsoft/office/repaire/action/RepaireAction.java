package net.zdsoft.office.repaire.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BaseStringService;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.repaire.entity.OfficeRepaire;
import net.zdsoft.office.repaire.entity.OfficeRepaireSms;
import net.zdsoft.office.repaire.entity.OfficeRepaireType;
import net.zdsoft.office.repaire.entity.OfficeTeachAreaAuth;
import net.zdsoft.office.repaire.entity.OfficeTypeAuth;
import net.zdsoft.office.repaire.service.OfficeRepaireService;
import net.zdsoft.office.repaire.service.OfficeRepaireSmsService;
import net.zdsoft.office.repaire.service.OfficeRepaireTypeService;
import net.zdsoft.office.repaire.service.OfficeTeachAreaAuthService;
import net.zdsoft.office.repaire.service.OfficeTypeAuthService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

public class RepaireAction extends PageAction {
	private static final long serialVersionUID = -5460693464852423761L;

	private static final String REPAIRE_DEFAULT_TYPE ="ACHI.SCORE.CALCULATE.SWITCH";
	
	private UserService userService;
	private TeacherService teacherService;
	private TeachAreaService teachAreaService;
	private CustomRoleService customRoleService;
	private McodedetailService mcodedetailService;
	private BasicClassService basicClassService;
	private OfficeRepaireService officeRepaireService;
	private OfficeTypeAuthService officeTypeAuthService;
	private CustomRoleUserService customRoleUserService;
	private OfficeRepaireTypeService officeRepaireTypeService;
	private OfficeTeachAreaAuthService officeTeachAreaAuthService;
	private BaseStringService baseStringService;
	private OfficeRepaireSmsService officeRepaireSmsService;

	private List<Mcodedetail> mcodelist;
	private List<TeachArea> teachAreaList;
	private List<OfficeRepaireType> officeRepaireTypeList;
	private List<OfficeTypeAuth> typelist = new ArrayList<OfficeTypeAuth>();
	private List<OfficeRepaire> repaireList = new ArrayList<OfficeRepaire>();
	private List<OfficeRepaire> repaireAllList = new ArrayList<OfficeRepaire>();
	private List<OfficeTeachAreaAuth> arealist = new ArrayList<OfficeTeachAreaAuth>();
	private List<OfficeRepaireSms> typeSMSlist = new ArrayList<OfficeRepaireSms>();
	private OfficeRepaire officeRepaire;
	private OfficeRepaireType officeRepaireType;
	private String areaId;
	private Date startTime;
	private Date endTime;
	private String type;
	private String state;
	private String[] userIds;
	private String[] typeIds;
	private String[] areaIds;
	private String userId;
	private String userName;
	private String id;
	private String thisId;// 微代码的thisId
	private String[] typeNames;// 二级类别维护使用
	private boolean hasUser;
	private boolean hasMange;
	@SuppressWarnings("unused")
	private boolean hasDeleteAuth;//是否有删除权限
	private String typeId;
	private List<BasicClass> basicClassList; //班级列表
	private String repaireDefaultType;//新建报修是，类别默认是电教还是总务
	
	private boolean needPrint;
	private boolean binjiangDeploy;
	
	private String years;//查询
	private String change;//切换统计
	private List<String> months=new ArrayList<String>();
	private Map<String,String> desMap;
	private Map<String,String> desTalMap;
	private Map<String,String> countMap;
	private List<User> users=new ArrayList<User>();
	
	private ConverterFileTypeService converterFileTypeService;
	

	public String repaireAdmin() {
		hasMange = isPracticeAdmin("repaire_manage");
		hasUser = isPracticeAdmin("repaire_auth");
		
		SystemIni systemIni = systemIniService.getSystemIni(REPAIRE_DEFAULT_TYPE);
		if(systemIni != null && "0".equals(systemIni.getNowValue())){//默认选择 总务
			repaireDefaultType = "1";
		}else{
			repaireDefaultType = "2";
		}
		return SUCCESS;
	}

	/** 最新报修 **/
	public String latestRepaire() {
		return SUCCESS;
	}

	/** 最新报修列表 **/
	public String latestRepaireList() {
		repaireList = officeRepaireService.getOfficeRepaireList(null, this
				.getLoginInfo().getUnitID(), areaId, type, state, startTime,
				endTime, null, getPage());
		if(isNeedPrint()){
			repaireAllList = officeRepaireService.getOfficeRepaireList(null, this
					.getLoginInfo().getUnitID(), areaId, type, state, startTime,
					endTime, null, null);
		}
		return SUCCESS;
	}
	/**最新报修导出**/
	public String latestRepaireListExport(){
		mcodelist = getMcodelist();
		Map<String,String> map = new HashMap<String, String>();
		for(Mcodedetail m : mcodelist){
			map.put(m.getThisId(), m.getContent());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		repaireAllList = officeRepaireService.getOfficeRepaireList(null, this
				.getLoginInfo().getUnitID(), areaId, type, state, startTime,
				endTime, null, null);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL && getTeachAreaList().size() > 1) 
		    zdlist.add(new ZdCell("校区",1,style2));
		zdlist.add(new ZdCell("类别",1,style2));
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL && 
		        !StringUtils.equals(BaseConstant.SYS_DEPLOY_SCHOOL_HZZC, getSystemDeploySchool()))
		    zdlist.add(new ZdCell("班级信息",1,style2));
		zdlist.add(new ZdCell("报修人",1,style2));
		zdlist.add(new ZdCell("联系电话",1,style2));
		zdlist.add(new ZdCell("报修时间",1,style2));
		zdlist.add(new ZdCell("设备名称",1,style2));
		zdlist.add(new ZdCell(baseStringService.getValue("offce.repaire.sblx", "设备类型"),1,style2));
		zdlist.add(new ZdCell("故障设备地点",1,style2));
		zdlist.add(new ZdCell("故障详情",1,style2));
		zdlist.add(new ZdCell("维修状态",1,style2));
		zdlist.add(new ZdCell("维修备注",1,style2));
		
		zdExcel.add(new ZdCell("报修列表", zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(OfficeRepaire re : repaireAllList){
		    int index = 0;
			ZdCell[] cells = new ZdCell[zdlist.size()];
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL && getTeachAreaList().size() > 1) {
			    cells[index++] = new ZdCell(re.getTeachAreaName(), 1, style3);
			}
			if(map.containsKey(re.getType()))
				cells[index++] = new ZdCell(map.get(re.getType())+(StringUtils.isNotBlank(re.getRepaireTypeName())?"("+re.getRepaireTypeName()+")":""), 1, style3);
			else
				cells[index++] = new ZdCell("", 1, style3);
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL 
			        && !StringUtils.equals(BaseConstant.SYS_DEPLOY_SCHOOL_HZZC, getSystemDeploySchool()))
			    cells[index++] = new ZdCell(re.getClassName(), 1, style3);
			cells[index++] = new ZdCell(re.getUserName(), 1, style3);
			cells[index++] = new ZdCell(re.getPhone(), 1, style3);
			cells[index++] = new ZdCell(sdf.format(re.getDetailTime()), 1, style3);
			cells[index++] = new ZdCell(re.getGoodsName(), 1, style3);
			if(StringUtils.isBlank(re.getEquipmentType()))
				cells[index++] = new ZdCell("", 1, style3);
			else
				cells[index++] = new ZdCell(mcodedetailService.getMcodeDetail("DM-SBLX", re.getEquipmentType()).getContent(),1,style3);
			cells[index++] = new ZdCell(re.getGoodsPlace(), 1, style3);
			cells[index++] = new ZdCell(re.getRemark(), 1, style3);
			if(StringUtils.equals(re.getState(), "1"))
				cells[index++] = new ZdCell("未维修", 1, style3);
			else if(StringUtils.equals(re.getState(), "2"))
				cells[index++] = new ZdCell("维修中", 1, style3);
			else if(StringUtils.equals(re.getState(), "3"))
				cells[index++] = new ZdCell("已维修", 1, style3);
			else
				cells[index++] = new ZdCell("", 1, style3);
				
			cells[index++] = new ZdCell(re.getRepaireRemark(), 1, style3);
			zdExcel.add(cells);
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("repairelist");
		return NONE;
	}
	/** 我的报修 **/
	public String myRepaire() {
		return SUCCESS;
	}

	/** 我的报修列表 **/
	public String myRepaireList() {
		repaireList = officeRepaireService.getOfficeRepaireList(this
				.getLoginUser().getUserId(), this.getLoginInfo().getUnitID(),
				areaId, type, state, startTime, endTime, null, getPage());
		return SUCCESS;
	}

	public String myAdd() {
		if (StringUtils.isNotBlank(id)) {
			officeRepaire = officeRepaireService.getOfficeRepaireById(id);
		} else {
			officeRepaire = new OfficeRepaire();
			OfficeRepaire lastOfficeRepaire = officeRepaireService.getOfficeRepaireByUserIdLastTime(getLoginInfo().getUser().getId());
			officeRepaire
					.setUserName(getLoginInfo().getUser().getRealname());
			Teacher teacher = teacherService.getTeacher(getLoginInfo().getUser().getTeacherid());
			officeRepaire.setPhone(teacher != null ? StringUtils.trimToEmpty(teacher.getPersonTel()) : "");
			officeRepaire.setDetailTime(new Date());// 报修时间默认当天
			if(lastOfficeRepaire!=null){
				officeRepaire.setGoodsPlace(lastOfficeRepaire.getGoodsPlace());
			}
		}
		basicClassList = basicClassService.getClasses(getLoginInfo().getUnitID());
		return SUCCESS;
	}

	public String view() {
		officeRepaire = officeRepaireService.getOfficeRepaireById(id);
		return SUCCESS;
	}

	public String myDel() {
		if (StringUtils.isBlank(id)) {
			jsonError = "没有选择要删除的记录！";
			return SUCCESS;
		}
		try {
			officeRepaireService.delete(id.split(","));
		} catch (Exception e) {
			jsonError = "删除失败";
		}
		return SUCCESS;
	}
	
	public String allSave(){
		if(StringUtils.isBlank(id)){
			jsonError="没有选择要操作的记录";
			return SUCCESS;
		}
		try {
			officeRepaireService.updateState(id.split(","), state,getLoginUser().getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			jsonError="操作失败";
		}
		return SUCCESS;
	}
	
	public String saveAdd() {
		if (StringUtils.isBlank(officeRepaire.getGoodsName())) {
			jsonError = "设备名称不能为空";
			return SUCCESS;
		}
		if (StringUtils.isBlank(officeRepaire.getGoodsPlace())) {
			jsonError = "报修地点不能为空";
			return SUCCESS;
		}
		if (StringUtils.isBlank(officeRepaire.getPhone())) {
			jsonError = "电话不能为空";
			return SUCCESS;
		}
		if (officeRepaire.getDetailTime() == null) {
			jsonError = "报修时间不能为空";
			return SUCCESS;
		}
		// if(DateUtils.compareIgnoreSecond(officeRepaire.getDetailTime(),
		// DateUtils.currentStartDate())<0){
		// jsonError="报修时间不能为之前的时间";
		// return SUCCESS;
		// }
		
		//不是教室维修和寝室维修的，相关班级id为空
		if(!StringUtils.equals(officeRepaire.getEquipmentType(), "1") && !StringUtils.equals(officeRepaire.getEquipmentType(), "2")){
		    officeRepaire.setClassId(null);
		}
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU) {
		    officeRepaire.setTeachAreaId(BaseConstant.ZERO_GUID);
		}
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			if (StringUtils.isBlank(officeRepaire.getId())) {
				officeRepaire.setUnitId(getLoginInfo().getUnitID());
				officeRepaire.setUserId(getLoginUser().getUserId());
				officeRepaire.setCreateTime(new Date());
				officeRepaire.setState(OfficeRepaire.STATE_ONE);
				officeRepaire.setIsDeleted(false);
				officeRepaire.setIsFeedback(false);
				officeRepaireService.add(officeRepaire,file);
			} else {
				officeRepaireService.update(officeRepaire,file);
			}
		} catch (Exception e) {
			jsonError = "保存失败:"+e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 反馈
	 * @return
	 */
	public String feedback(){
		officeRepaire = officeRepaireService.getOfficeRepaireById(id);
		return SUCCESS;
	}
	
	/**
	 * 保存反馈信息
	 * @return
	 */
	public String saveFeedback(){
		try {
			officeRepaireService.updateFeedBack(officeRepaire);
		} catch (Exception e) {
			jsonError = "反馈失败";
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** 权限设置 **/
	public String audit() {
		typelist = officeTypeAuthService.getOfficeTypeAuthUserPage(
				getLoginInfo().getUnitID(), Constants.REPAIRE_STATE, getPage());
		return SUCCESS;
	}

	public String auditAdd() {
		if (StringUtils.isNotBlank(userId)) {
			typelist = officeTypeAuthService.getOfficeTypeAuthList(
					Constants.REPAIRE_STATE, userId);
			arealist = officeTeachAreaAuthService.getOfficeTeachAreaAuthList(
					Constants.REPAIRE_STATE, userId);
			userName = userService.getUser(userId).getRealname();
		}
		return SUCCESS;
	}

	public String auditSave() {
	    boolean isEdu = getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU;
		if (typeIds == null || typeIds.length == 0) {
			jsonError = "没有选择类别！";
			return SUCCESS;
		}
		
		if(isEdu) {
		    areaIds = new String[] {BaseConstant.ZERO_GUID};
		}
		if(getTeachAreaList().size() == 0) {
		    areaIds = new String[] {BaseConstant.ZERO_GUID};
		}
		else if(getTeachAreaList().size() == 1) {
		    areaIds = new String[] {getTeachAreaList().get(0).getId()};
		}
		else {
    		if (areaIds == null || areaIds.length == 0) {
    			jsonError = "没有选择校区！";
    			return SUCCESS;
    		}
		}
		try {
			officeTypeAuthService.saveAuth(userId, typeIds, areaIds);
		} catch (Exception e) {
			jsonError = "保存失败";
		}
		return SUCCESS;
	}

	public String auditDel() {
		if (StringUtils.isBlank(userId)) {
			jsonError = "没有选择要删除的记录！";
			return SUCCESS;
		}
		try {
			officeTypeAuthService.deleteAuthByUser(userId.split(","));
		} catch (Exception e) {
			jsonError = "删除失败";
		}
		return SUCCESS;
	}

	/** 报修管理 **/
	public String manage() {
		userId = this.getLoginUser().getUserId();
		typelist = officeTypeAuthService.getOfficeTypeAuthList(
				Constants.REPAIRE_STATE, userId);
		arealist = officeTeachAreaAuthService.getOfficeTeachAreaAuthList(
				Constants.REPAIRE_STATE, userId);
		return SUCCESS;
	}

	public String manageList() {
		repaireList = officeRepaireService.getOfficeRepaireMangeList(this
				.getLoginUser().getUserId(), this.getLoginInfo().getUnitID(),
				areaId, type, state, startTime, endTime, null, getPage());
		return SUCCESS;
	}

	public String mangeEdit() {
		officeRepaire = officeRepaireService.getOfficeRepaireById(id);
		if (officeRepaire.getRepaireTime() == null) {
			officeRepaire.setRepaireTime(new Date());// 维修时间默认当天
		}
		return SUCCESS;
	}

	public String saveManage() {
		try {
			OfficeRepaire oldRp = officeRepaireService.getOfficeRepaireById(officeRepaire.getId());
			oldRp.setRepaireUserId(this.getLoginUser().getUserId());
			oldRp.setState(officeRepaire.getState());
			oldRp.setRepaireTime(officeRepaire.getRepaireTime());
			oldRp.setRepaireRemark(officeRepaire.getRepaireRemark());
			officeRepaireService.updateState(oldRp);
		} catch (Exception e) {
			jsonError = "保存失败";
		}
		return SUCCESS;
	}

	public String typeManage() {
		return SUCCESS;
	}

	public String typeManageList() {
		officeRepaireTypeList = officeRepaireTypeService
				.getOfficeRepaireTypeByUnitIdList(getLoginInfo().getUnitID(),
						thisId);
		return SUCCESS;
	}

	public String typeManageEdit() {
		officeRepaireType = officeRepaireTypeService
				.getOfficeRepaireTypeById(id);
		if (officeRepaireType == null) {
			officeRepaireType = new OfficeRepaireType();
		}
		return SUCCESS;
	}

	public String saveType() {
		try {
			if (StringUtils.isBlank(officeRepaireType.getId())) {
				officeRepaireType.setIsDeleted(false);
				officeRepaireType.setUnitId(getLoginInfo().getUnitID());
				officeRepaireTypeService.save(officeRepaireType);
			} else {
				officeRepaireTypeService.update(officeRepaireType);
			}
		} catch (Exception e) {
			jsonError = "保存失败";
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String deleteType() {
		if (StringUtils.isNotBlank(id)) {
			try {
				List<OfficeRepaire> repaires = officeRepaireService
						.getOfficeRepaireByUnitIdList(getUnitId(), id);
				if (CollectionUtils.isEmpty(repaires)) {
					officeRepaireTypeService.delete(id);
				} else {
					jsonError = "二级类别已经被使用，不能删除！";
				}
			} catch (Exception e) {
				jsonError = "删除失败";
				e.printStackTrace();
			}
		} else {
			jsonError = "传入的值为空";
		}
		return SUCCESS;
	}

	/**
	 * 为一级类别设置是否接收短信
	 * @return
	 */
	public String typeSMSEdit() {//TODO
		List<OfficeRepaireSms> list = officeRepaireSmsService.getOfficeRepaireSmsByUnitIdList(getUnitId());
		mcodelist = getMcodelist();
		for(Mcodedetail mcodedetail : mcodelist){
			OfficeRepaireSms typeSMS = isExistTypeSMS(list, mcodedetail.getThisId());
			if(StringUtils.isBlank(typeSMS.getId())){//如果没有设置过，则初始化
				typeSMS.setUnitId(getUnitId());
				typeSMS.setThisId(mcodedetail.getThisId());
				typeSMS.setTypeName(mcodedetail.getContent());
				typeSMS.setIsNeedSms(false);
			}else{
				typeSMS.setTypeName(mcodedetail.getContent());
			}
			typeSMSlist.add(typeSMS);
		}
		return SUCCESS;
	}
	
	public String typeSMSSave() {
		try {
			List<OfficeRepaireSms> insertList = new ArrayList<OfficeRepaireSms>();
			List<OfficeRepaireSms> updateList = new ArrayList<OfficeRepaireSms>();
			for(OfficeRepaireSms item : typeSMSlist){
				item.setIsNeedSms(StringUtils.equals(item.getIsNeedSMSStr(), "1"));
				if(StringUtils.isBlank(item.getId())){
					insertList.add(item);
				}else{
					updateList.add(item);
				}
			}
			if(insertList.size() > 0){
				officeRepaireSmsService.batchInsert(insertList);
			}
			if(updateList.size() > 0){
				officeRepaireSmsService.batchUpdate(updateList);
			}
		} catch (Exception e) {
			jsonError = "保存失败";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private OfficeRepaireSms isExistTypeSMS(List<OfficeRepaireSms> list, String typeId){
		if(list.size() > 0){
			for(OfficeRepaireSms item : list){
				if(StringUtils.equals(item.getThisId(), typeId))
					return item;
			}
		}
		return new OfficeRepaireSms();
	}
	public String statisticAdmin(){//TODO
		return SUCCESS;
	}
	public String statisticList(){
		for (int i = 1; i < 13; i++) {
			months.add(i+"");
		}
		typelist = officeTypeAuthService.getOfficeTypeAuthUserPage(
				getLoginInfo().getUnitID(), Constants.REPAIRE_STATE, null);
		Set<String> userIds=new HashSet<String>();
		for (OfficeTypeAuth it : typelist) {
			userIds.add(it.getUserId());
		}
		users=userService.getUsers(userIds.toArray(new String[0]));
		desMap=officeRepaireService.getOfficeRepaireByUnitIdAndYearsMap(getUnitId(), years, change);
		desTalMap=officeRepaireService.getOfficeRepaireByUnitIdAndYearsTalMap(getUnitId(), years, change);
		if(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "2")){
			countMap=officeRepaireService.getOfficeRepaireByUnitIdAndYearsCountMap(getUnitId(), years);
		}
		return SUCCESS;
	}
	public String applySummaryExport(){
		for (int i = 1; i < 13; i++) {
			months.add(i+"");
		}
		typelist = officeTypeAuthService.getOfficeTypeAuthUserPage(
				getLoginInfo().getUnitID(), Constants.REPAIRE_STATE, null);
		Set<String> userIds=new HashSet<String>();
		for (OfficeTypeAuth it : typelist) {
			userIds.add(it.getUserId());
		}
		users=userService.getUsers(userIds.toArray(new String[0]));
		desMap=officeRepaireService.getOfficeRepaireByUnitIdAndYearsMap(getUnitId(), years, change);
		desTalMap=officeRepaireService.getOfficeRepaireByUnitIdAndYearsTalMap(getUnitId(), years, change);
		if(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "2")){
			countMap=officeRepaireService.getOfficeRepaireByUnitIdAndYearsCountMap(getUnitId(), years);
		}
		if(CollectionUtils.isEmpty(mcodelist)){
			mcodelist=getMcodelist();
		}
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1")?"报修次数统计":"报修评价统计",(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1"))?(mcodelist.size()+1):(users.size()+1), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell> zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("月份",1,style2));
		if(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1")){
			for(Mcodedetail mcodedetail:mcodelist){
				zdlist.add(new ZdCell(mcodedetail.getContent(),1,style2));
			}
		}else{
			for (User user : users) {
				zdlist.add(new ZdCell(user.getRealname(),1,style2));
			}
		}
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for (String month : months) {
			ZdCell[] cells = new ZdCell[StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1")?mcodelist.size()+1:users.size()+1];
			cells[0] = new ZdCell(month, 1, style3);
			if(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1")){
				for (int i=0;i<getMcodelist().size();i++) {
					if(desMap.get(month+"_"+mcodelist.get(i).getThisId())!=null){
						cells[i+1]=new ZdCell(desMap.get(month+"_"+mcodelist.get(i).getThisId())+"次", 1, style3);
					}else{
						cells[i+1] = new ZdCell("0次", 1, style3);
					}
				}
			}else{
				for (int i=0;i<users.size();i++) {
					if(desMap.get(month+"_"+users.get(i).getId())!=null){
						if(StringUtils.isNotBlank(countMap.get(month+"_"+users.get(i).getId()))){
							cells[i+1]=new ZdCell(desMap.get(month+"_"+users.get(i).getId())+"分"+"("+countMap.get(month+"_"+users.get(i).getId())+"次)", 1, style3);
						}else{
							cells[i+1]=new ZdCell(desMap.get(month+"_"+users.get(i).getId())+"分", 1, style3);
						}
					}else{
						cells[i+1]=new ZdCell("0分",1,style3);
					}
				}
			}
			zdExcel.add(cells);
		}
		
		ZdCell[] cells = new ZdCell[StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1")?mcodelist.size()+1:users.size()+1];
		cells[0] = new ZdCell(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1")?"总计":"总评", 1, style3);
		if(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "1")){
			for (int i=0;i<mcodelist.size();i++) {
				if(desTalMap.get(mcodelist.get(i).getThisId())!=null){
					cells[i+1]=new ZdCell(desTalMap.get(mcodelist.get(i).getThisId())+"次", 1, style3);
				}else{
					cells[i+1] = new ZdCell("0次", 1, style3);
				}
			}
		}else{
			for (int i=0;i<users.size();i++) {
				if(desTalMap.get(users.get(i).getId())!=null){
					cells[i+1]=new ZdCell(desTalMap.get(users.get(i).getId())+"分", 1, style3);
				}else{
					cells[i+1]=new ZdCell("0分",1,style3);
				}
			}
		}
		zdExcel.add(cells);
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("office_repaire");
		return NONE;
	}
	/**
	 * 判断其是否为各种管理员
	 */
	private boolean isPracticeAdmin(String str) {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(
				getUnitId(), str);
		boolean flag;
		if (role == null) {
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService
				.getCustomRoleUserListByUserId(getLoginInfo().getUser().getId());
		if (CollectionUtils.isNotEmpty(roleUs)) {
			for (CustomRoleUser ru : roleUs) {
				if (StringUtils.equals(ru.getRoleId(), role.getId())) {
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}

	public List<TeachArea> getTeachAreaList() {
		if (teachAreaList == null) {
			teachAreaList = teachAreaService.getTeachAreas(getUnitId());
		}
		return teachAreaList;
	}

	// 判断是否可以做删除操作
	public boolean isHasDeleteAuth() {
		return isPracticeAdmin("repaire_manage_del");
	}

	public String getRepaireType() {
		if (StringUtils.isNotBlank(thisId))
			officeRepaireTypeList = officeRepaireTypeService
					.getOfficeRepaireTypeByUnitIdList(getUnitId(), thisId);
		else
			officeRepaireTypeList = new ArrayList<OfficeRepaireType>();

		return SUCCESS;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setTeachAreaService(TeachAreaService teachAreaService) {
		this.teachAreaService = teachAreaService;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<OfficeRepaire> getRepaireList() {
		return repaireList;
	}

	public void setRepaireList(List<OfficeRepaire> repaireList) {
		this.repaireList = repaireList;
	}

	public void setOfficeRepaireService(
			OfficeRepaireService officeRepaireService) {
		this.officeRepaireService = officeRepaireService;
	}

	public void setOfficeTeachAreaAuthService(
			OfficeTeachAreaAuthService officeTeachAreaAuthService) {
		this.officeTeachAreaAuthService = officeTeachAreaAuthService;
	}

	public void setOfficeTypeAuthService(
			OfficeTypeAuthService officeTypeAuthService) {
		this.officeTypeAuthService = officeTypeAuthService;
	}

	public List<OfficeTypeAuth> getUserlist() {
		return typelist;
	}

	public void setUserlist(List<OfficeTypeAuth> userlist) {
		this.typelist = userlist;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OfficeTeachAreaAuth> getArealist() {
		return arealist;
	}

	public boolean isHasUser() {
		return hasUser;
	}

	public void setHasUser(boolean hasUser) {
		this.hasUser = hasUser;
	}

	public void setArealist(List<OfficeTeachAreaAuth> arealist) {
		this.arealist = arealist;
	}

	public List<Mcodedetail> getMcodelist() {
		if (mcodelist == null) {
			mcodelist = mcodedetailService.getMcodeDetails("DM-BXLB");
		}
		return mcodelist;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public List<OfficeTypeAuth> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<OfficeTypeAuth> typelist) {
		this.typelist = typelist;
	}

	public void setTypeIds(String[] typeIds) {
		this.typeIds = typeIds;
	}

	public String[] getTypeIds() {
		return typeIds;
	}

	public String[] getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String[] areaIds) {
		this.areaIds = areaIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OfficeRepaire getOfficeRepaire() {
		return officeRepaire;
	}

	public void setOfficeRepaire(OfficeRepaire officeRepaire) {
		this.officeRepaire = officeRepaire;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(
			CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public boolean isHasMange() {
		return hasMange;
	}

	public void setHasMange(boolean hasMange) {
		this.hasMange = hasMange;
	}

	public List<OfficeRepaireType> getOfficeRepaireTypeList() {
		return officeRepaireTypeList;
	}

	public void setOfficeRepaireTypeList(
			List<OfficeRepaireType> officeRepaireTypeList) {
		this.officeRepaireTypeList = officeRepaireTypeList;
	}

	public void setOfficeRepaireTypeService(
			OfficeRepaireTypeService officeRepaireTypeService) {
		this.officeRepaireTypeService = officeRepaireTypeService;
	}

	public String getThisId() {
		return thisId;
	}

	public void setThisId(String thisId) {
		this.thisId = thisId;
	}

	public String[] getTypeNames() {
		return typeNames;
	}

	public void setTypeNames(String[] typeNames) {
		this.typeNames = typeNames;
	}

	public OfficeRepaireType getOfficeRepaireType() {
		return officeRepaireType;
	}

	public void setOfficeRepaireType(OfficeRepaireType officeRepaireType) {
		this.officeRepaireType = officeRepaireType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<OfficeRepaire> getRepaireAllList() {
		return repaireAllList;
	}

	public void setRepaireAllList(List<OfficeRepaire> repaireAllList) {
		this.repaireAllList = repaireAllList;
	}

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public List<BasicClass> getBasicClassList() {
        return basicClassList;
    }

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setBaseStringService(BaseStringService baseStringService) {
        this.baseStringService = baseStringService;
    }

	public List<OfficeRepaireSms> getTypeSMSlist() {
		return typeSMSlist;
	}

	public void setOfficeRepaireSmsService(
			OfficeRepaireSmsService officeRepaireSmsService) {
		this.officeRepaireSmsService = officeRepaireSmsService;
	}

	public String getRepaireDefaultType() {
		return repaireDefaultType;
	}

	public void setRepaireDefaultType(String repaireDefaultType) {
		this.repaireDefaultType = repaireDefaultType;
	}

	public boolean isNeedPrint() {
		String value = systemIniService.getValue(BaseConstant.REPAIRE_NEED_PRINT);
		if(value != null && value.equals("1")){
			this.needPrint =  true;
		}else{
			this.needPrint =  false;
		}
		return needPrint;
	}

	public void setNeedPrint(boolean needPrint) {
		this.needPrint = needPrint;
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
			years = DateUtils.date2String(new Date(),"yyyy");
		}
		return years;
	}
	
	public boolean isBinjiangDeploy() {
		binjiangDeploy = false;
		if (BaseConstant.SYS_DEPLOY_SCHOOL_HZBJ.equals(getSystemDeploySchool())){
			binjiangDeploy = true;
		}
		return binjiangDeploy;
	}

	public void setBinjiangDeploy(boolean binjiangDeploy) {
		this.binjiangDeploy = binjiangDeploy;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}

	public Map<String,String> getDesMap() {
		return desMap;
	}

	public void setDesMap(Map<String,String> desMap) {
		this.desMap = desMap;
	}

	public Map<String,String> getDesTalMap() {
		return desTalMap;
	}

	public void setDesTalMap(Map<String,String> desTalMap) {
		this.desTalMap = desTalMap;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Map<String,String> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String,String> countMap) {
		this.countMap = countMap;
	}
	public String getExtNames(){
		String[] extNames = converterFileTypeService.getAllExtNames();
		StringBuffer sb = new StringBuffer();
		if(extNames!=null){//rtf,doc,docx,xls,xlsx,csv,ppt,pptx,pdf,bmp,jpg,jpeg,png,gif
			for (String extName : extNames) {
				boolean contain=extName.contains("bmp")||extName.contains("jpg")||extName.contains("jpeg")||extName.contains("png")||extName.contains("gif");
				boolean contain2=extName.contains("doc")||extName.contains("docx")||extName.contains("xls")||extName.contains("xlsx")||extName.contains("pdf");
				boolean contain3=extName.contains("ppt")||extName.contains("pptx");
				if(contain||contain2||contain3){
					sb.append(extName+",");
				}
			}
		}
		return sb.toString();
	}

	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}

}
