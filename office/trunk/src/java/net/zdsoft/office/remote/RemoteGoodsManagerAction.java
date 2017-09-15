package net.zdsoft.office.remote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsChangeLog;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.office.goodmanage.service.OfficeGoodsChangeLogService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsReqService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeAuthService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 物品管理 Html5 接口
 * 
 * @todo 
 * @author wanghb
 * @date 2016-5-24
 */
public class RemoteGoodsManagerAction extends OfficeJsonBaseAction {
	private static final long serialVersionUID = 1L;

	private OfficeGoodsReqService officeGoodsReqService;
	private OfficeGoodsTypeAuthService officeGoodsTypeAuthService;
	private OfficeGoodsService officeGoodsService;
	private UserService userService;
	private UnitService unitService;
	private DeptService deptService;
	
	private OfficeGoodsTypeService officeGoodsTypeService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeGoodsChangeLogService officeGoodsChangeLogService;
	private UserSetService userSetService;

	private JSONObject jsonMap = new JSONObject();

	private String id;
	private String userId;
	private String unitId;
	private String searchType;
	private String searchName;
	private String applyType;  
	private String advice;
	
	private int applyAmount;
	private String applyRemark;
	private String auditContent;
	private boolean isGoodsAudit;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public String myGoods(){
		isGoodsAudit = isCustomRole(OfficeGoodsConstants.OFFICE_GOODS_AUDIT);
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
	/**
	 * 物品类别 
	 * @return
	 */
	public void goodsApplyType() {
		List<OfficeGoodsType> goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(unitId);
		JSONArray items = new JSONArray();
		if(CollectionUtils.isNotEmpty(goodsTypeList)){
			for(OfficeGoodsType type : goodsTypeList){
				JSONObject obj = new JSONObject();
				obj.put("id", type.getId());
				obj.put("typeId", type.getTypeId());
				obj.put("typeName", type.getTypeName());
				items.add(obj);
			}
		}
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	/**
	 * 我的物品列表
	 */
	public void myGoodsList() {
		Pagination page = getPage();
		List<OfficeGoodsReq> goodsRequestList = officeGoodsReqService
				.getOfficeGoodsReqByConditions(unitId, StringUtils
						.isBlank(searchType) ? null
						: new String[] { searchType }, searchName, applyType,
						userId, null, null, page);
		JSONArray items = new JSONArray();
		if(CollectionUtils.isNotEmpty(goodsRequestList)){
			for (OfficeGoodsReq item : goodsRequestList) {
				JSONObject obj = new JSONObject();
				obj.put("id", item.getId());
				obj.put("name", item.getReqGoods().getName());
				String typeName=item.getReqGoods().getTypeName();
				if(typeName.length() > 4){
					typeName = typeName.substring(0, 4)+"...";
				}
				obj.put("typeName", typeName);
				obj.put("amount", item.getAmount());
				obj.put("creationTime", sdf.format(item.getCreationTime()));
				obj.put("state", item.getState());
//				obj.put("price", item.getReqGoods().getPrice());
//				obj.put("model", item.getReqGoods().getModel());
//				obj.put("remark", item.getReqGoods().getRemark());
//				obj.put("reqRemark", item.getRemark()); //申请说明
				items.add(obj);
			}
		}
		jsonMap.put("page", page);
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 物品审核列表
	 * @return
	 */
	public void goodsAuditList() {
		if(StringUtils.isBlank(applyType)){
			applyType = OfficeGoodsConstants.GOODS_AUDIT_ALL + "";
		}
		Pagination page = getPage();
		List<String> typelist = new ArrayList<String>();
		List<OfficeGoodsTypeAuth> authlist = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthByUserId(unitId, userId);
		if(StringUtils.isBlank(searchType)){
			if(authlist.size() > 0){
				String[] types = authlist.get(0).getTypeId().split(",");
				typelist = Arrays.asList(types);
			}
		}else{
			if(authlist.size() > 0){
				String[] types = authlist.get(0).getTypeId().split(",");
				for (String string : types) {
					if(searchType.equals(string)){
						typelist.add(searchType);
						break;
					}
				}
			}
		}
		List<OfficeGoodsReq> goodsRequestList = officeGoodsReqService.getOfficeGoodsReqByConditions(unitId, typelist.toArray(new String[0]), searchName, applyType, null, null, null, page);
		JSONArray items = new JSONArray();
		if(CollectionUtils.isNotEmpty(goodsRequestList)){
			for(OfficeGoodsReq item : goodsRequestList){
				JSONObject obj = new JSONObject();
				obj.put("id", item.getId());
				obj.put("name", item.getReqGoods().getName());
				obj.put("amount", item.getAmount());
				String typeName=item.getReqGoods().getTypeName();
				if(typeName.length() > 4){
					typeName = typeName.substring(0, 4)+"...";
				}
				obj.put("typeName", typeName);
				obj.put("creationTime", sdf.format(item.getCreationTime()));
				obj.put("state", item.getState());
				obj.put("reqUserName", item.getReqUserName());
//				obj.put("price", item.getReqGoods().getPrice());
//				obj.put("model", item.getReqGoods().getModel());
//				obj.put("remark", item.getReqGoods().getRemark());
//				obj.put("reqRemark", item.getRemark()); //申请说明
				items.add(obj);
			}
		}
		jsonMap.put("page", page);
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 获取详情
	 * @return
	 */
	public void goodsAuditDetail(){
		OfficeGoodsReq item = officeGoodsReqService.getOfficeGoodsReqById(id);
		OfficeGoods officeGoods = officeGoodsService.getOfficeGoodsById(item.getGoodsId());
		OfficeGoodsType officeGoodsTypeById = officeGoodsTypeService.getOfficeGoodsTypeByTypeId(unitId, officeGoods.getType());
		if(officeGoodsTypeById!=null){
			officeGoods.setTypeName(officeGoodsTypeById.getTypeName());
		}
		item.setReqGoods(officeGoods);
		
		String photeUrl = userSetService.getUserPhotoUrl(item.getReqUserId());
		User user = userService.getUser(item.getReqUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		JSONObject obj = new JSONObject();
		obj.put("id", item.getId());
		obj.put("name", item.getReqGoods().getName());
		obj.put("deptName", deptName);
		obj.put("photoUrl", photeUrl);
		obj.put("amount", item.getAmount());
		obj.put("advice", item.getAdvice());
		obj.put("typeName", item.getReqGoods().getTypeName());
		obj.put("creationTime", sdf.format(item.getCreationTime()));
		obj.put("state", item.getState());
		obj.put("reqUserName", item.getReqUserName());
		obj.put("price", item.getReqGoods().getPrice());
		obj.put("model", item.getReqGoods().getModel());
		obj.put("remark", item.getReqGoods().getRemark());
		obj.put("reqRemark", item.getRemark()); //申请说明
		obj.put("isReturned",item.getReqGoods().getIsReturned());
		
		jsonMap.put(getDetailObjectName(), obj);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 领用申请列表
	 * @return
	 */
	public void goodsApplyList(){
		Pagination page = getPage();
		List<OfficeGoods> goodsList = officeGoodsService.getOfficeGoodsByConditions(unitId, StringUtils.isBlank(searchType)?null:new String[]{searchType}, searchName, true, page);
		JSONArray items = new JSONArray();
		if(CollectionUtils.isNotEmpty(goodsList)){
			for(OfficeGoods item : goodsList){
				JSONObject obj = new JSONObject();
				obj.put("id", item.getId());
				obj.put("name", item.getName());
				obj.put("typeName", item.getTypeName());
				//以下信息，申请时，可以保存到缓存里，不用再次请求
//				obj.put("model", item.getModel());
//				obj.put("goodsUnit", item.getGoodsUnit());
//				obj.put("price", item.getPrice());
//				obj.put("remark", item.getRemark());
//				obj.put("amount", item.getAmount());
				items.add(obj);
			}
		}
		jsonMap.put("page", page);
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 领用申请列表物品详情
	 * @return
	 */
	public void goodsApplyListGoodsInfo(){
		OfficeGoods item = officeGoodsService.getOfficeGoodsById(id);
		OfficeGoodsType officeGoodsTypeById = officeGoodsTypeService.getOfficeGoodsTypeByTypeId(unitId, item.getType());
		if(officeGoodsTypeById!=null){
			item.setTypeName(officeGoodsTypeById.getTypeName());
		}
		JSONObject obj = new JSONObject();
		obj.put("id", item.getId());
		obj.put("name", item.getName());
		obj.put("typeName", item.getTypeName());
		obj.put("model", item.getModel());
		obj.put("goodsUnit", item.getGoodsUnit());
		obj.put("price", item.getPrice());
		obj.put("remark", item.getRemark());
		obj.put("amount", item.getAmount());
		jsonMap.put(getDetailObjectName(), obj);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 提交申请
	 * @return
	 */
	public void goodsApplySave(){
		try{
			OfficeGoods officeGoods = officeGoodsService.getOfficeGoodsById(id);
			if(officeGoods == null){
				jsonMap.put("result", 0);
				jsonMap.put("msg", "物品不存在，无法申请！");
				responseJSON(jsonMap);
				return;
			}else{
				officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_REQ);//有申请记录或有未归还的记录，则不能删去物品
				if(officeGoods.getAmount() < applyAmount){
					jsonMap.put("result", 0);
					jsonMap.put("msg", "库存不足，无法申请！");
					responseJSON(jsonMap);
					return;
				}else{
					officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_REQ);//有申请记录或有未归还的记录，则不能删去物品
					OfficeGoodsReq officeGoodsReq = new OfficeGoodsReq();
					
					officeGoodsReq.setGoodsId(officeGoods.getId());
					officeGoodsReq.setUnitId(unitId);
					officeGoodsReq.setCreationTime(new Date());
					officeGoodsReq.setGetUserid(userId);
					officeGoodsReq.setAmount(applyAmount);
					officeGoodsReq.setRemark(applyRemark);
					User user = userService.getUser(userId);
					officeGoodsReq.setReqDeptId(user.getDeptid());
					officeGoodsReq.setReqUserId(user.getId());
					officeGoodsReq.setState(OfficeGoodsConstants.GOODS_NOT_AUDIT);
					
					officeGoodsService.update(officeGoods);
					officeGoodsReqService.submitSave(officeGoodsReq);
					
					officeGoodsService.sendMsg(userService.getUser(userId), unitService.getUnit(unitId), officeGoods, null, null, officeGoodsReq.getId());
					
					jsonMap.put("result", 1);
					responseJSON(jsonMap);
					return;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			jsonMap.put("result", 0);
			jsonMap.put("msg", e.getMessage());
			responseJSON(jsonMap);
			return;
		}
	}
	/**
	 * 物品审核
	 */
	public void goodsAuditSave(){
		try{
			OfficeGoodsReq goodsReq = officeGoodsReqService.getOfficeGoodsReqById(id);
			OfficeGoods officeGoods = officeGoodsService.getOfficeGoodsById(goodsReq.getGoodsId());
			if((OfficeGoodsConstants.GOODS_AUDIT_PASS+"").equals(applyType)
					&& officeGoods.getAmount() < goodsReq.getAmount()){
				jsonMap.put("result", 0);
				jsonMap.put("msg", "库存不足，无法通过申请！");
				responseJSON(jsonMap);
				return;
			}else{
				goodsReq.setState(Integer.parseInt(applyType));
				goodsReq.setAdvice(advice);
				goodsReq.setPassTime(new Date());
				goodsReq.setAdvice(auditContent);
				officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_NOT_REQ);
				if((OfficeGoodsConstants.GOODS_AUDIT_PASS+"").equals(applyType)){
					officeGoods.setAmount(officeGoods.getAmount() - goodsReq.getAmount());
					
					//变更记录
					OfficeGoodsChangeLog goodsChangeLog = new OfficeGoodsChangeLog();
					goodsChangeLog.setAddUserId(userId);
					goodsChangeLog.setGoodsId(officeGoods.getId());
					goodsChangeLog.setReason(OfficeGoodsConstants.GOODS_GET);
					goodsChangeLog.setAmount(-goodsReq.getAmount());
					goodsChangeLog.setRemark(OfficeGoodsConstants.GOODS_GET + ",领用人：" + goodsReq.getReqUserName());
					goodsChangeLog.setCreationTime(new Date());
					officeGoodsChangeLogService.save(goodsChangeLog);
				}
				
				officeGoodsService.update(officeGoods); 
//				officeGoodsReqService.update(goodsReq);
				officeGoodsReqService.auditSave(goodsReq);
				User user = userService.getUser(userId);
				Unit unit = null;
				if(user!=null){
					unit = unitService.getUnit(user.getUnitid());
				}
				officeGoodsService.sendMsg(user, unit, officeGoods, Integer.parseInt(applyType), new String[]{goodsReq.getGetUserid()}, goodsReq.getId());
				
				jsonMap.put("result", 1);
				responseJSON(jsonMap);
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("result", 0);
			jsonMap.put("msg", e.getMessage());
			responseJSON(jsonMap);
			return;
		}
	}
	
	/**
	 * 物品归还
	 */
	public void goodsReturnSave(){
		try{
			OfficeGoodsReq goodsReq = officeGoodsReqService.getOfficeGoodsReqById(id);
			goodsReq.setPassTime(new Date());
			goodsReq.setState(OfficeGoodsConstants.GOODS_HAS_RETURNED);
			OfficeGoods officeGoods = officeGoodsService.getOfficeGoodsById(goodsReq.getGoodsId());
			officeGoods.setAmount(officeGoods.getAmount() + goodsReq.getAmount());
			
			officeGoodsService.update(officeGoods);
			officeGoodsReqService.update(goodsReq);
			
			//变更记录
			OfficeGoodsChangeLog goodsChangeLog = new OfficeGoodsChangeLog();
			goodsChangeLog.setAddUserId(userId);
			goodsChangeLog.setGoodsId(officeGoods.getId());
			goodsChangeLog.setReason(OfficeGoodsConstants.GOODS_GET);
			goodsChangeLog.setAmount(goodsReq.getAmount());
			goodsChangeLog.setRemark(OfficeGoodsConstants.GOODS_GET + ",归还人：" + goodsReq.getReqUserName());
			goodsChangeLog.setCreationTime(new Date());
			officeGoodsChangeLogService.save(goodsChangeLog);
			
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
			return;
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("result", 0);
			jsonMap.put("msg", e.getMessage());
			responseJSON(jsonMap);
			return;
		}
	}
	
	@Override
	protected String getListObjectName() {
		return "result_array";
	}
	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	//========SET=============================================

	public String getUserId() {
		return userId;
	}
	public int getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(int applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getApplyRemark() {
		return applyRemark;
	}
	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setOfficeGoodsReqService(OfficeGoodsReqService officeGoodsReqService) {
		this.officeGoodsReqService = officeGoodsReqService;
	}

	public JSONObject getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(JSONObject jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public void setOfficeGoodsTypeAuthService(
			OfficeGoodsTypeAuthService officeGoodsTypeAuthService) {
		this.officeGoodsTypeAuthService = officeGoodsTypeAuthService;
	}

	public void setOfficeGoodsService(OfficeGoodsService officeGoodsService) {
		this.officeGoodsService = officeGoodsService;
	}
	public void setOfficeGoodsTypeService(
			OfficeGoodsTypeService officeGoodsTypeService) {
		this.officeGoodsTypeService = officeGoodsTypeService;
	}
	public boolean isGoodsAudit() {
		return isGoodsAudit;
	}
	public void setGoodsAudit(boolean isGoodsAudit) {
		this.isGoodsAudit = isGoodsAudit;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public String getAuditContent() {
		return auditContent;
	}
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	public void setOfficeGoodsChangeLogService(OfficeGoodsChangeLogService officeGoodsChangeLogService) {
		this.officeGoodsChangeLogService = officeGoodsChangeLogService;
	}
	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
}
