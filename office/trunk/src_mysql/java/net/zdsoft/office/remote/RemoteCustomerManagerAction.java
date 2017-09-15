package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowApplyService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowAuditService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.PinyinUtil;
import net.zdsoft.office.customer.constant.OfficeCustomerConstants;
import net.zdsoft.office.customer.entity.OfficeCustomerApply;
import net.zdsoft.office.customer.entity.OfficeCustomerFollowRecord;
import net.zdsoft.office.customer.entity.OfficeCustomerInfo;
import net.zdsoft.office.customer.entity.SearchCustomer;
import net.zdsoft.office.customer.service.OfficeCustomerApplyService;
import net.zdsoft.office.customer.service.OfficeCustomerDeptLeaderApplyService;
import net.zdsoft.office.customer.service.OfficeCustomerFollowRecordService;
import net.zdsoft.office.customer.service.OfficeCustomerInfoService;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Administrator
 *
 */
public class RemoteCustomerManagerAction extends OfficeJsonBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//列表类型
	private static final String DATA_TYPE_1 = "1";//我的客户列表
	private static final String DATA_TYPE_2 = "2";//延期申请列表
	private static final String DATA_TYPE_3 = "3";//我的申请列表
	private static final String DATA_TYPE_4 = "4";//转发申请列表
	private static final String DATA_TYPE_5 = "5";//资源库列表
	private static final String DATA_TYPE_6 = "6";//地区--本部--所有客户列表
	private static final String DATA_TYPE_7 = "7";//申请审核列表
	private static final String DATA_TYPE_8 = "8";//延期审核列表
	private static final String DATA_TYPE_9 = "9";//转发审核列表
	
	private static final String subsystemId = "70";
	
	private String userId;
	private String unitId;
	private String id;//申请id
	private String customerId;
	private String followUserId;//跟进人Id
	private boolean pass;//true通过，false不通过
	private String reason;//不通过理由
	private String dataType;//列表类型
	private String roleType;//用户角色1.基层销售 2.地区负责人 3.事业部负责人 4.运营人员
	private String delayApllyInfo;//延期信息
	private String progressState;//客户状态
	
	private String applyId;
	private String auditId;
	private String type;//跟进人--抄送人
	
	//列表搜索用
	private String searchStr;//客户官方名称搜索
	private String customerType;//客户类型
	private String customerRegion;//区域码
	private String customerStatus;//查询状态1.待审核、2已审核      5、6、7、8、9----审核状态
	private String opinion;//审核意见
	
	private McodedetailService mcodedetailService;
	private OfficeCustomerApplyService officeCustomerApplyService;
	private OfficeCustomerDeptLeaderApplyService officeCustomerDeptLeaderApplyService;
	private OfficeCustomerFollowRecordService officeCustomerFollowRecordService;
	private SchFlowApplyService schFlowApplyService;
	private SchFlowAuditService schFlowAuditService;
	private UserService userService;
	private DeptService deptService;
	private RegionService regionService;
	
	private OfficeCustomerInfo officeCustomerInfo;
	private SearchCustomer searchCustomer = new SearchCustomer();
	private List<OfficeCustomerApply> customerApplyList;
	private OfficeCustomerFollowRecord officeCustomerFollowRecord =  new OfficeCustomerFollowRecord();
	private OfficeCustomerApply officeCustomerApply;
	
	protected CustomRoleService customRoleService;
	protected OfficeCustomerInfoService officeCustomerInfoService;

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	} 
	
	public boolean isDeptLeader(){
		return officeCustomerApplyService.isDeptLeader(unitId, userId);
	}
	
	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isCustomRole(String roleCode){
		if(StringUtils.isNotBlank(roleCode)){
			List<CustomRole> customRoles = customRoleService.getCustomRoleListByUserId(unitId, userId, subsystemId);
			for(CustomRole customRole : customRoles){
				if(roleCode.equals(customRole.getRoleCode())){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String execute() throws Exception {
		officeCustomerApplyService.deletePutOffCustomer(unitId);
		JSONObject json = new JSONObject();

		json.put("roleType",getRoleType());
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
		return null;
	}
	/**
	 * 列表页
	 * 2016年9月29日
	 */
	public void customerList() {

		Pagination page = getPage();
		JSONArray array = new JSONArray();
		searchCustomer.setName(searchStr);
		searchCustomer.setRegion(customerRegion);
		searchCustomer.setType(customerType);
		searchCustomer.setProgressState(customerStatus);
		searchCustomer.setDataType(dataType);
		if(DATA_TYPE_1.equals(dataType)||DATA_TYPE_2.equals(dataType)){
			customerApplyList=officeCustomerApplyService.getMyCustomerByUnitIdPage(unitId,userId,searchCustomer,page);
		}else if(DATA_TYPE_3.equals(dataType)||DATA_TYPE_4.equals(dataType)){
			customerApplyList=officeCustomerApplyService.getOfficeCustomerApplyByUnitIdPage(unitId ,userId,searchCustomer,page);
		}else if(DATA_TYPE_5.equals(dataType)){
			customerApplyList = new ArrayList<OfficeCustomerApply>();
			List<OfficeCustomerInfo> customerInfoList = officeCustomerInfoService.getCustomerLibraryByUnitIdPage(searchCustomer,unitId,page);
			for(OfficeCustomerInfo customerInfo:customerInfoList){
				OfficeCustomerApply customerApply = new OfficeCustomerApply();
				customerApply.setOfficeCustomerInfo(customerInfo);
				customerApplyList.add(customerApply);
			}
		}else if(DATA_TYPE_6.equals(dataType)){
			getRoleType();
			customerApplyList=officeCustomerApplyService.getAllList(searchCustomer,unitId,userId,"4".equals(roleType)?true:false,"3".equals(roleType)?true:false,"2".equals(roleType)?true:false,page);
		}else {//审核
			
			String[] applyTypes = new String[2]; 
			if(StringUtils.isNotBlank(dataType)){
				if(DATA_TYPE_7.equals(dataType)){
					applyTypes[0]= OfficeCustomerConstants.APPLY_NEW;
					applyTypes[1]= OfficeCustomerConstants.APPLY_OLD;
				}else if(DATA_TYPE_8.equals(dataType)){
					applyTypes[0]= OfficeCustomerConstants.APPLY_PUT_OFF;
				}else if(DATA_TYPE_9.equals(dataType)){
					applyTypes[0]= OfficeCustomerConstants.APPLY_TRANS;
				}
			}
			
			OfficeCustomerApply customer = new OfficeCustomerApply();
			customer.setUnitId(unitId);
			
			CustomRole role = null;
			if(isCustomRole(OfficeCustomerConstants.OFFICE_CLIENT_MANAGER)){//如果是运营人员 则按运营人员来搜索
				role = customRoleService.getCustomRoleByRoleCode(unitId, OfficeCustomerConstants.OFFICE_CLIENT_MANAGER);
			}else if(isDeptLeader()){
				role = customRoleService.getCustomRoleByRoleCode(unitId, OfficeCustomerConstants.OFFCIE_DEPT_LEADER);
			}
			
			customerApplyList = officeCustomerApplyService.getCustomerApplyAuditList(customer, userId, customerStatus, role.getRoleCode(), searchCustomer, applyTypes, page);
		}
		for(OfficeCustomerApply customerApply:customerApplyList){
			JSONObject json = new JSONObject();
			if(StringUtils.isNotBlank(customerApply.getId())){
				json.put("id", customerApply.getId());
			}else{
				json.put("id", customerApply.getOfficeCustomerInfo().getId());
			}
			json.put("name", customerApply.getOfficeCustomerInfo().getName());
			if(userId.equals(customerApply.getFollowerId())){
				json.put("isMine", "1");//1.是自己的，0.是队友的
			}else{
				json.put("isMine", "0");//1.是自己的，0.是队友的
			}
			json.put("backgroundInfo",customerApply.getOfficeCustomerInfo().getBackgroundInfo());
			json.put("expiryDate", customerApply.getValidTime());//有效期
			json.put("region", customerApply.getOfficeCustomerInfo().getRegionName());//客户所在区域
			if(DATA_TYPE_1.equals(dataType)||DATA_TYPE_6.equals(dataType)){
				json.put("applyDate", DateUtils.date2String(customerApply.getDeadline(), "yyyy-MM-dd"));//截止日期
			}else{
				json.put("applyDate", DateUtils.date2String(customerApply.getCreateTime(), "yyyy-MM-dd"));//申请日期
			}
					
			Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-JZZT",
					customerApply.getOfficeCustomerInfo().getProgressState());
			Mcodedetail mcodedetail2 = mcodedetailService.getMcodeDetail("DM-OAKHLB",
					customerApply.getOfficeCustomerInfo().getType());
			json.put("progressStateVal",customerApply.getOfficeCustomerInfo().getProgressState());
			json.put("progressState", mcodedetail.getContent());
			if(DATA_TYPE_7.equals(dataType)||DATA_TYPE_8.equals(dataType)||DATA_TYPE_9.equals(dataType)){
				json.put("auditState", customerApply.getAuditStatus());
			}else{
				json.put("auditState", customerApply.getState());
			}
			json.put("type", mcodedetail2.getContent());
			json.put("applyUserName", customerApply.getFollowerName());
			
			//审核列表用
			json.put("applyId", customerApply.getApplyId());
			json.put("auditId", customerApply.getAuditId());
			
			array.add(json);
		}
			
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("result", 1);
		jsonMap.put("page", page);
		responseJSON(jsonMap);
	}
	
	/**
	 * 新客户申请、修改
	 * id
	 * 2016年9月28日
	 */
	public void applyCustomer() {
		
		OfficeCustomerApply apply = null;
		OfficeCustomerInfo officeCustomerInfo = null;
		if (StringUtils.isNotEmpty(id)) {
			//根据id查询
			apply = officeCustomerApplyService.getOfficeCustomerApplyById(id);
			officeCustomerInfo = officeCustomerInfoService.getOfficeCustomerInfoById(apply.getCustomerId());
			
			if(apply!=null){
				User user = userService.getUser(apply.getFollowerId());
				apply.setFollowerName(user==null?"":user.getRealname());
			}else{
				apply = new OfficeCustomerApply();
			}
			
			if(officeCustomerInfo != null){
				//officeCustomerInfo.setRegionName(regionService.getFullName(officeCustomerInfo.getRegion()));
			}else{
				officeCustomerInfo = new OfficeCustomerInfo();
			}
		}else{
			apply = new OfficeCustomerApply();
			officeCustomerInfo = new OfficeCustomerInfo();
		}
		
		Map<String, Mcodedetail> typeMap = mcodedetailService.getMcodeDetailMap("DM-OAKHLB");
		Mcodedetail typedetail = typeMap.get(officeCustomerInfo.getType());
		String typeTxt = "";
		if(typedetail != null){
			typeTxt = typedetail.getContent();
		}
		
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("followUserName", apply.getFollowerName());
		json.put("name", officeCustomerInfo.getName());
		json.put("nickName", officeCustomerInfo.getNickName());
		json.put("region", officeCustomerInfo.getRegion());
		json.put("regionName", officeCustomerInfo.getRegionName());
		json.put("type", officeCustomerInfo.getType());
		json.put("typeTxt", typeTxt);
		json.put("infoSource", officeCustomerInfo.getInfoSource());
		json.put("backgroundInfo", officeCustomerInfo.getBackgroundInfo());
		json.put("progressState", officeCustomerInfo.getProgressState());
		json.put("product", officeCustomerInfo.getProduct());
		json.put("contact", officeCustomerInfo.getContact());
		json.put("phone", officeCustomerInfo.getPhone());
		json.put("contactInfo", officeCustomerInfo.getContactInfo());
		json.put("progressStateMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-JZZT"));
		json.put("typeMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-OAKHLB"));
		json.put("productMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-OAYXHZCP"));
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	/**
	 * 保存客户信息
	 * userId, unitId,officeCustomerInfo,id
	 * 2016年9月28日
	 */
	public void saveCustomer() {
		try {
			if(officeCustomerInfoService.getSameCustomerName(officeCustomerInfo)!=0){
				jsonMap.put("result", 0);
				jsonMap.put("msg", "保存失败:有相同客户名称！");
				responseJSON(jsonMap);
				return;
			}
			if(isCustomRole(OfficeCustomerConstants.OFFICE_CLIENT_MANAGER)){//运营人员
				officeCustomerApply = new OfficeCustomerApply();
				officeCustomerApply.setUnitId(unitId);
				officeCustomerInfo.setUnitId(unitId);
				if(StringUtils.isNotBlank(followUserId)){
					//选择跟进人
					officeCustomerInfo.setState(OfficeCustomerConstants.INFO_FLOWING);
					officeCustomerInfo=officeCustomerInfoService.save(officeCustomerInfo);
					officeCustomerApply.setFollowerId(followUserId);
					officeCustomerApply.setCustomerId(officeCustomerInfo.getId());
					officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
					officeCustomerApplyService.addCustomer(officeCustomerApply);
				}else{
					officeCustomerInfo.setAddTime(new Date());//直接入库
					officeCustomerInfo.setState(OfficeCustomerConstants.INFO_LIBARAY);
					officeCustomerInfoService.save(officeCustomerInfo);
				}
			}else{//基层销售、事业部负责人
				if(StringUtils.isNotBlank(id)){
					//新增的编辑
					OfficeCustomerApply ent = officeCustomerApplyService.getOfficeCustomerApplyById(id);
					OfficeCustomerInfo customerInfo = officeCustomerInfoService.getOfficeCustomerInfoById(ent.getCustomerId());
					customerInfo.setNickName(officeCustomerInfo.getNickName());
					customerInfo.setInfoSource(officeCustomerInfo.getInfoSource());
					customerInfo.setBackgroundInfo(officeCustomerInfo.getBackgroundInfo());
					customerInfo.setProgressState(officeCustomerInfo.getProgressState());
					customerInfo.setProduct(officeCustomerInfo.getProduct());
					customerInfo.setContact(officeCustomerInfo.getContact());
					customerInfo.setPhone(officeCustomerInfo.getPhone());
					customerInfo.setContactInfo(officeCustomerInfo.getContactInfo());
					//未提交或再申请
					if(OfficeCustomerConstants.APPLY_PUT_OFF.equals(ent.getApplyType())||!(OfficeCustomerConstants.LEVEL_APPLY_FLOW_FINALLY_PASS==ent.getState())){
						customerInfo.setName(officeCustomerInfo.getName());
						customerInfo.setRegion(officeCustomerInfo.getRegion());
						customerInfo.setType(officeCustomerInfo.getType());
						officeCustomerInfoService.update(customerInfo);
						FlowApply flowapply = schFlowApplyService.getFlowApplyInFlow(id);
						ent.setApplyId(flowapply.getId());
						if(isDeptLeader()){
							officeCustomerDeptLeaderApplyService.update(ent);
						}else{
							officeCustomerApplyService.update(ent);
						}
					}else{//我的客户列表只要保存客户信息
						officeCustomerInfoService.update(customerInfo);
						if(StringUtils.isNotBlank(followUserId)&&!StringUtils.equals(userId, followUserId)){
							ent.setFollowerId(followUserId);
						}
						officeCustomerApplyService.updateFollowerId(ent);
					}
					
				}else{
					User user = userService.getUser(userId);
					OfficeCustomerApply apply = new OfficeCustomerApply();
					apply.setApplyUserId(user.getId()); 
					apply.setApplyUserName(user.getRealname());
					//新增申请 只有一种新增客户申请类型。跟进人都是自己  
					apply.setApplyType(OfficeCustomerConstants.APPLY_NEW);
					apply.setFollowerId(userId);
					officeCustomerInfo.setAuditState(1);
					officeCustomerInfo.setUnitId(unitId);
					apply.setOfficeCustomerInfo(officeCustomerInfo); 
					apply.setUnitId(unitId);
					//如果是事业部负责人新增调用对应的service
					if(isDeptLeader()){
						officeCustomerDeptLeaderApplyService.save(apply);
					}else{
						officeCustomerApplyService.save(apply);
					}
				}
			}
			jsonMap.put("result",1);
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	
	/**
	 * 修改跟进人
	 * unitId,userId,id,customerId(客户表id),followUserId(跟进人id)
	 * 2016年10月8日
	 */
	public void doSaveFollowMan() {
		try {
			if(StringUtils.isNotBlank(dataType) && dataType.equals(DATA_TYPE_5) && !isCustomRole(OfficeCustomerConstants.OFFICE_CLIENT_MANAGER)){//资源库--直接修改跟进人
				//转发客户申请
				officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(customerId);
				officeCustomerInfo.setState(OfficeCustomerConstants.INFO_APPLY);//客户申请中
				officeCustomerInfo.setAuditState(1);//提交审核
				officeCustomerInfo.setProgressState(progressState);//客户状态
				String applyType=null;
				if(StringUtils.isBlank(followUserId)||(StringUtils.isNotBlank(userId)&&userId.equals(followUserId))){
					applyType=OfficeCustomerConstants.APPLY_OLD;
				}else{
					applyType=OfficeCustomerConstants.APPLY_TRANS;
				}
				User user = userService.getUser(userId);
				officeCustomerApply = new OfficeCustomerApply();
				if(StringUtils.isBlank(followUserId)){
					officeCustomerApply.setFollowerId(userId);
				}else{
					officeCustomerApply.setFollowerId(followUserId);
				}
				officeCustomerApply.setApplyType(applyType);
				officeCustomerApply.setApplyUserId(user.getId()); 
				officeCustomerApply.setApplyUserName(user.getRealname());
				officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
				officeCustomerApply.setUnitId(unitId);
				if(isDeptLeader()){
					officeCustomerDeptLeaderApplyService.save(officeCustomerApply);
				}else{
					officeCustomerApplyService.save(officeCustomerApply);
				}
			}else{
				if(StringUtils.isNotBlank(dataType) && dataType.equals(DATA_TYPE_5)){//添加记录
					officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(id);//获取客户全部信息
					officeCustomerInfo.setState(OfficeCustomerConstants.INFO_FLOWING);//将客户状态设成跟进中
					officeCustomerInfo.setProgressState(progressState);//客户状态
					officeCustomerInfoService.update(officeCustomerInfo);//修改客户状态
					
					officeCustomerApply.setCustomerId(officeCustomerInfo.getId());
					officeCustomerApply.setUnitId(unitId);
					officeCustomerApply.setFollowerId(followUserId);
					officeCustomerApplyService.addCustomer(officeCustomerApply);//添加跟进人 申请信息
				}else{//只修改跟进人
					OfficeCustomerApply ent = new OfficeCustomerApply();
					ent.setId(id);
					ent.setFollowerId(followUserId);
					officeCustomerApplyService.updateFollowerId(ent);
				}
			}
			jsonMap.put("result",1);
			
		} catch (Exception e) {
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	/**
	 * 进入跟进信息页面
	 * id
	 * 2016年10月8日
	 */
	public void followInfo() {
		
		List<OfficeCustomerFollowRecord> recordList=officeCustomerFollowRecordService.getOfficeCustomerFollowRecordList(id);
//		Map<String, Mcodedetail> pMap = mcodedetailService.getMcodeDetailMap("DM-JZZT");
		
//		JSONArray array = new JSONArray();
		String copyToName = "";
		String copyToId = "";
		String progressState = "01";
		int index = 0;
		for(OfficeCustomerFollowRecord ent : recordList){
			if(index == 0){
				copyToName = ent.getCarbonCopyName();
				copyToId = ent.getCarbonCopyId();
				progressState = ent.getProgressState();
			}
			
//			JSONObject json = new JSONObject();
//			json.put("copyUserName", ent.getCarbonCopyName());
//			String progressStateStr = "";
//			if(pMap.containsKey(ent.getProgressState())){
//				Mcodedetail mcode = pMap.get(ent.getProgressState());
//				if(mcode != null){
//					progressStateStr = mcode.getContent();
//				}
//			}
//			json.put("progressState", progressStateStr);
//			json.put("remark", ent.getRemark());//跟进信息
//			array.add(json);
			index++;
		}
		
		JSONObject json = new JSONObject();
		json.put("copyToName",copyToName);
		json.put("copyTo",copyToId);
		json.put("progressState",progressState);
		json.put("progressStateMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-JZZT"));
		
//		json.put("progressList", array);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	
	/**
	 * 保存跟进信息
	 * officeCustomerFollowRecord
	 * 2016年10月8日
	 */
	public void doSaveFollowInfo() {
		try {
			try{
				officeCustomerFollowRecord.setApplyId(officeCustomerApply.getId());
				officeCustomerFollowRecord.setCreateTime(new Date());
				officeCustomerFollowRecordService.save(officeCustomerFollowRecord);
				officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
				officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerApply.getCustomerId());
				officeCustomerInfo.setProgressState(officeCustomerFollowRecord.getProgressState());
				officeCustomerInfoService.update(officeCustomerInfo);
				if((officeCustomerInfo.getProgressState().equals("04") ||officeCustomerInfo.getProgressState().equals("05") ||officeCustomerInfo.getProgressState().equals("09")) 
						&&StringUtils.isNotBlank(officeCustomerApply.getId())){
						officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
						officeCustomerApply.setFollowerId(null);//不需要修改跟进人
						officeCustomerApplyService.updateFollowerId(officeCustomerApply);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			jsonMap.put("result",1);
			
		} catch (Exception e) {
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	/**
	 * 保存延期信息
	 * officeCustomerApply(表单中需要包含id,customerId,Deadline,DelayInfo,applyUserId,applyUserName,unitId) applyUserName也可以不传
	 * 2016年10月8日
	 */
	public void doSaveDelayInfo() {
		try {
			officeCustomerApply = officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
			officeCustomerApply.setApplyUserId(userId);
			User user = userService.getUser(userId);
			if(user!=null){
				officeCustomerApply.setApplyUserName(user.getRealname());
			}
			officeCustomerApply.setDelayInfo(delayApllyInfo);
			if(officeCustomerApply!=null){
				officeCustomerInfoService.updateState(officeCustomerApply.getCustomerId(), OfficeCustomerConstants.INFO_APPLY, null);
				if("3".equals(roleType)){
					officeCustomerDeptLeaderApplyService.putOffApply(officeCustomerApply);
				}else{
					officeCustomerApplyService.putOffApply(officeCustomerApply);
				}
			}
			jsonMap.put("result",1);
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	
	/**
	 * 查看详情
	 * id
	 * 2016年9月28日
	 */
	public void canReadDetail() {
		OfficeCustomerApply apply = officeCustomerApplyService.getOfficeCustomerApplyById(id);
		OfficeCustomerInfo info = officeCustomerInfoService.getOfficeCustomerInfoById(apply.getCustomerId());
		List<OfficeCustomerFollowRecord> recordList=officeCustomerFollowRecordService.getOfficeCustomerFollowRecordList(id);
		
		Map<String, Mcodedetail> pMap = mcodedetailService.getMcodeDetailMap("DM-JZZT");
		
		//1.根据id查询
		JSONObject json = new JSONObject();
		json.put("id", id);
		//客户信息
		setCodeToName(info);
		json.put("officeCustomerInfo", info);
		//跟进记录
		JSONArray array = new JSONArray();
		for(OfficeCustomerFollowRecord ent : recordList){
			JSONObject obj = new JSONObject();
			if(StringUtils.isNotBlank(ent.getCarbonCopyId())){
				obj.put("copyUserName", ent.getCarbonCopyName());
			}
			String progressStateStr = "";
			if(pMap.containsKey(ent.getProgressState())){
				Mcodedetail mcode = pMap.get(ent.getProgressState());
				if(mcode != null){
					progressStateStr = mcode.getContent();
				}
			}
			obj.put("progressState", progressStateStr);
			obj.put("remark", ent.getRemark());//跟进信息
			array.add(obj);
		}
		json.put("followUserName", apply.getFollowerName());
		json.put("followUser", apply.getFollowerId());
		json.put("followArray",array);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	/**
	 * 跟踪详情
	 * id
	 * 2016年9月28日
	 */
	public void followDetail() {
		OfficeCustomerApply apply = officeCustomerApplyService.getOfficeCustomerApplyById(id);
		List<OfficeCustomerFollowRecord> recordList=officeCustomerFollowRecordService.getOfficeCustomerFollowRecordList(id);
		
		Map<String, Mcodedetail> pMap = mcodedetailService.getMcodeDetailMap("DM-JZZT");
		JSONObject json = new JSONObject();
		Date d1=new Date();
		if(apply.getDeadline()!=null){
			long d=DateUtils.string2Date(DateUtils.date2String(d1, "yyyy-MM-dd"), "yyyy-MM-dd").getTime()-
					DateUtils.string2Date(DateUtils.date2String(apply.getDeadline(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
			apply.setValidTime(d/(24*60*60*1000));
			json.put("delayTime",apply.getValidTime());
			json.put("delayCurrentTime",DateUtils.date2String(apply.getDeadline(), "yyyy-MM-dd"));
			Calendar c = Calendar.getInstance();
			c.setTime(apply.getDeadline());
			c.add(Calendar.MONTH, 1);
			json.put("delayEndTime",DateUtils.date2String(c.getTime(), "yyyy-MM-dd"));
		}
		json.put("id", id);
		json.put("applyType", apply.getApplyType());
		//跟进记录
		JSONArray array = new JSONArray();
		for(OfficeCustomerFollowRecord ent : recordList){
			JSONObject obj = new JSONObject();
			if(StringUtils.isNotBlank(ent.getCarbonCopyId())){
				obj.put("copyUserName", ent.getCarbonCopyName());
			}
			String progressStateStr = "";
			if(pMap.containsKey(ent.getProgressState())){
				Mcodedetail mcode = pMap.get(ent.getProgressState());
				if(mcode != null){
					progressStateStr = mcode.getContent();
				}
			}
			obj.put("progressState", progressStateStr);
			obj.put("remark", ent.getRemark());//跟进信息
			array.add(obj);
		}
		json.put("followUserName", apply.getFollowerName());
		json.put("followArray",array);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 审核详情
	 * id,applyId,auditId
	 * 2016年9月28日
	 */
	public void auditDetail() {
		OfficeCustomerApply customer = officeCustomerApplyService.getOfficeCustomerApplyById(id);
		List<FlowApply> applys = schFlowApplyService.getFlowApplysByBusinessIds(new String[] {id} );
		if(applys!=null && applys.size()>0){
			String applyId = applys.get(0).getId();
			User user = userService.getUser(applys.get(0).getApplyUserId());
			if(user != null){
				customer.setApplyUserName(user.getRealname());
				
				Dept dept = deptService.getDept(user.getDeptid());
				if(dept != null){
					customer.setDeptName(dept.getDeptname());
				}
			}
			
			
			OfficeCustomerInfo info = officeCustomerInfoService.getOfficeCustomerInfoById(customer.getCustomerId());
			customer.setOfficeCustomerInfo(info);
			
//			boolean isBusinessTypeTwo = false;
			if(StringUtils.isNotBlank(auditId)){
				FlowAudit adent = schFlowAuditService.getFlowAudit(auditId);
				customer.setAuditId(adent.getId());
				customer.setAuditStatus(String.valueOf(adent.getStatus()));
//				if(adent.getBusinessType() == OfficeCustomerConstants.BUSINESS_TYPE_2){
//					isBusinessTypeTwo = true;
//				}
				
			}
			List<FlowAudit> auditlist = schFlowAuditService.getFlowAudits(applyId);
			if(auditlist!=null && auditlist.size()>0){
				if(auditlist.size()<=1){
					customer.setOperateOpinion(auditlist.get(0).getOpinion());
				}else{
					customer.setDeptOpinion(auditlist.get(0).getOpinion());
					customer.setOperateOpinion(auditlist.get(1).getOpinion());
				}
			}
		}
		
		
		//1.根据id查询
		JSONObject json = new JSONObject();
		//put申请信息
		json.put("id", customer.getId());
		setCodeToName(customer.getOfficeCustomerInfo());
		json.put("officeCustomerInfo", customer.getOfficeCustomerInfo());
		json.put("applyType",customer.getApplyType());//申请类型0.新客户申请
		json.put("auditState",customer.getState());//审核状态
		json.put("waitAudit",customer.getAuditStatus());//1.待审核、2.已审核
		
		//
		json.put("auditId", customer.getAuditId());
		json.put("customerId", customer.getCustomerId());
		json.put("applyUserName", customer.getApplyUserName());
		json.put("followUserName", customer.getFollowerName());
		json.put("deptName", customer.getDeptName());
//		json.put("isBusinessTypeTwo", isBusinessTypeTwo);//事业部负责人直接发起的申请 
		json.put("deptOpinion", customer.getDeptOpinion());//部门负责人意见
		json.put("operateOpinion", customer.getOperateOpinion());//运营人员意见
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	/**
	 * 资源库详情
	 * id
	 * 2016年9月28日
	 */
	public void zykDetail() {
		
		OfficeCustomerInfo info = new OfficeCustomerInfo();
		try{
			info = officeCustomerInfoService.getOfficeCustomerInfoById(id);
		}catch (Exception e){
			e.printStackTrace();
		}
		//1.根据id查询
		JSONObject json = new JSONObject();
		//put申请信息
		json.put("id", id);
		String progressState = info.getProgressState();//资源库申请状态可改
		setCodeToName(info);
		info.setProgressState(progressState);
		json.put("officeCustomerInfo", info);
		json.put("progressStateMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-JZZT"));
//		json.put("applyType","");//申请类型
//		json.put("auditState","");//审核状态
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	//删除
	public void doDelete(){
		try{
			if(StringUtils.isNotBlank(id)){
				officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(id);
				List<FlowApply> applys = schFlowApplyService.getFlowApplysByBusinessIds(new String[] {id} );
				if(applys!=null && applys.size()>0)
					applyId = applys.get(0).getApplyUserId();
				officeCustomerApplyService.deleteAll(officeCustomerApply,applyId);
			}
		jsonMap.put("result",1);
	} catch (Exception e) {
		jsonMap.put("result",0);
	}
	responseJSON(jsonMap);
	}
	
	/**
	 * 放弃申请
	 * id,
	 * 2016年9月28日
	 */
	public void doGiveUpApply() {
		try {
			//id
			try{
				if(StringUtils.isNotBlank(id)){
					officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(id);
					officeCustomerInfoService.updateState(officeCustomerApply.getCustomerId(), OfficeCustomerConstants.INFO_LIBARAY, null);
					officeCustomerApplyService.updateIsDelete(true,id);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			
			jsonMap.put("result",1);
			
		} catch (Exception e) {
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	
	/**
	 * 审核
	 * userId,unitId,officeCustomerApply
	 * 2016年9月28日
	 */
	public void doAudit() {
		try {
			//id,pass,reason
			User user = userService.getUser(userId);
			FlowAudit audit = new FlowAudit();
			audit.setAuditUnitId(unitId);
			audit.setAuditUserId(userId);
			audit.setAuditUsername(user.getRealname());
			audit.setStatus(Integer.valueOf(officeCustomerApply.getAuditStatus()));
			audit.setOpinion(officeCustomerApply.getOpinion());
			audit.setAuditDate(new Date());
			
			try {
				CustomRole role = null;
				if(isCustomRole(OfficeCustomerConstants.OFFICE_CLIENT_MANAGER)){//如果是运营人员 则按运营人员来搜索
					role = customRoleService.getCustomRoleByRoleCode(unitId, OfficeCustomerConstants.OFFICE_CLIENT_MANAGER);
				}else if(isDeptLeader()){
					role = customRoleService.getCustomRoleByRoleCode(unitId, OfficeCustomerConstants.OFFCIE_DEPT_LEADER);
				}
				
				officeCustomerApplyService.saveContestStususclaAudit(audit, officeCustomerApply, officeCustomerApply.getAuditId(), role.getRoleCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			jsonMap.put("result",1);
			
		} catch (Exception e) {
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	
	//跟进人
	/**
	 * unitId,userId,type(1:跟进人，2：抄送人)
	 */
	public void getFollowUserList(){
		JSONArray jsonArray = new JSONArray();
		List<User> tlist = new ArrayList<User>();
		if("2".equals(type)){
			tlist = officeCustomerApplyService.getCpopyUserList(unitId, userId);
		}else{
			tlist = officeCustomerApplyService.getFollowUserList(unitId, userId);
		}
		
		JSONObject jsob=null;
		//字母拼音检索
		Map<String,JSONArray> pinyinMap=new HashMap<String, JSONArray>();
		JSONArray jsa=null;
		String mat="[a-zA-Z]+";
		for (User user : tlist) {
			String key = PinyinUtil.toHanyuPinyin(user.getRealname(), false);
			if(StringUtils.isBlank(key)){
				jsa = pinyinMap.get("#");
				if(jsa==null){
					jsa=new JSONArray();
					pinyinMap.put("#", jsa);
				}
			}else{
				key = key.substring(0, 1).toUpperCase();//转为大写
				if(!key.matches(mat)){//是否是字母
					jsa = pinyinMap.get("#");
					if(jsa==null){
						jsa=new JSONArray();
						pinyinMap.put("#", jsa);
					}
				}else{
					jsa = pinyinMap.get(key);
					if(jsa==null){
						jsa=new JSONArray();
						pinyinMap.put(key, jsa);
					}
				}
			}
			jsob=new JSONObject();
			jsob.put("id", user.getId());
			jsob.put("name", user.getRealname());
			jsob.put("mobilePhone", user.getMobilePhone());
			jsa.add(jsob);
		}
		List<JSONObject> jsList=new ArrayList<JSONObject>();
		JSONArray otherJarr=null;
		for (Map.Entry<String,JSONArray> entry : pinyinMap.entrySet()) {
			jsob=new JSONObject();
			if("#".equals(entry.getKey())){
				otherJarr = entry.getValue();
				continue;
			}
			jsob.put("key", entry.getKey());
			jsob.put("value", entry.getValue());
			jsList.add(jsob);
		}
		// 排序
		Collections.sort(jsList, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				return o1.getString("key").compareTo(o2.getString("key"));
			}
		});
		for (JSONObject item : jsList) {
			jsonArray.add(item);
		}
		if(otherJarr!=null){
			jsob=new JSONObject();
			jsob.put("key", "#");
			jsob.put("value", otherJarr);
			jsonArray.add(jsob);
		}
		jsonMap.put(getListObjectName(), jsonArray);
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
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRoleType() {
		if(isCustomRole(OfficeCustomerConstants.OFFICE_CLIENT_MANAGER)){
			roleType = "4";
		}else if(officeCustomerApplyService.isDeptLeader(unitId,userId)){
			roleType = "3";
		}else if(isCustomRole(OfficeCustomerConstants.OFFICE_REGION_LEADER)){
			roleType = "2";
		}else{
			roleType = "1";
		}
		return roleType;
	}
	/**
	 * 客户信息--根据微代码设置名称
	 * @param info
	 * 2016年10月15日
	 */
	private void setCodeToName(OfficeCustomerInfo info) {
		Mcodedetail progressState = mcodedetailService.getMcodeDetail("DM-JZZT",
				info.getProgressState());
		info.setProgressState(progressState.getContent());
		Mcodedetail type = mcodedetailService.getMcodeDetail("DM-OAKHLB",
				info.getType());
		info.setType(type.getContent());
		Map<String, Mcodedetail> productMap = mcodedetailService.getMcodeDetailMap("DM-OAYXHZCP");
		String product = "";
		if(StringUtils.isNotBlank(info.getProduct())){
			String[] products = info.getProduct().split(",");
			for(String prod:products){
				if(productMap.containsKey(prod.trim())){
					Mcodedetail mcode = productMap.get(prod.trim());
					if(mcode != null){
						if(StringUtils.isNotBlank(product)){
							product+=","+mcode.getContent();
						}else{
							product+=mcode.getContent();
						}
					}
				}
			}
		}
		info.setProduct(product);
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public OfficeCustomerInfo getOfficeCustomerInfo() {
		return officeCustomerInfo;
	}

	public void setOfficeCustomerInfo(OfficeCustomerInfo officeCustomerInfo) {
		this.officeCustomerInfo = officeCustomerInfo;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerRegion() {
		return customerRegion;
	}

	public void setCustomerRegion(String customerRegion) {
		this.customerRegion = customerRegion;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	
	public void setOfficeCustomerApplyService(
			OfficeCustomerApplyService officeCustomerApplyService) {
		this.officeCustomerApplyService = officeCustomerApplyService;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getFollowUserId() {
		return followUserId;
	}

	public void setFollowUserId(String followUserId) {
		this.followUserId = followUserId;
	}

	public OfficeCustomerFollowRecord getOfficeCustomerFollowRecord() {
		return officeCustomerFollowRecord;
	}

	public void setOfficeCustomerFollowRecord(
			OfficeCustomerFollowRecord officeCustomerFollowRecord) {
		this.officeCustomerFollowRecord = officeCustomerFollowRecord;
	}

	public OfficeCustomerApply getOfficeCustomerApply() {
		return officeCustomerApply;
	}

	public void setOfficeCustomerApply(OfficeCustomerApply officeCustomerApply) {
		this.officeCustomerApply = officeCustomerApply;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


	public List<OfficeCustomerApply> getCustomerApplyList() {
		return customerApplyList;
	}

	public void setCustomerApplyList(List<OfficeCustomerApply> customerApplyList) {
		this.customerApplyList = customerApplyList;
	}

	public void setOfficeCustomerInfoService(
			OfficeCustomerInfoService officeCustomerInfoService) {
		this.officeCustomerInfoService = officeCustomerInfoService;
	}

	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	
	public void setOfficeCustomerDeptLeaderApplyService(
			OfficeCustomerDeptLeaderApplyService officeCustomerDeptLeaderApplyService) {
		this.officeCustomerDeptLeaderApplyService = officeCustomerDeptLeaderApplyService;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	
	public void setSchFlowApplyService(SchFlowApplyService schFlowApplyService) {
		this.schFlowApplyService = schFlowApplyService;
	}

	public String getDelayApllyInfo() {
		return delayApllyInfo;
	}

	public void setDelayApllyInfo(String delayApllyInfo) {
		this.delayApllyInfo = delayApllyInfo;
	}
	public void setSchFlowAuditService(SchFlowAuditService schFlowAuditService) {
		this.schFlowAuditService = schFlowAuditService;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setOfficeCustomerFollowRecordService(
			OfficeCustomerFollowRecordService officeCustomerFollowRecordService) {
		this.officeCustomerFollowRecordService = officeCustomerFollowRecordService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProgressState() {
		return progressState;
	}

	public void setProgressState(String progressState) {
		this.progressState = progressState;
	}
	
	
}
