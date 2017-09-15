package net.zdsoft.office.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.EisuClassService;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.repaire.entity.OfficeRepaire;
import net.zdsoft.office.repaire.entity.OfficeRepaireType;
import net.zdsoft.office.repaire.entity.OfficeTypeAuth;
import net.zdsoft.office.repaire.service.OfficeRepaireService;
import net.zdsoft.office.repaire.service.OfficeRepaireTypeService;
import net.zdsoft.office.repaire.service.OfficeTypeAuthService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.remote 
* @author songxq  
* @date 2016-6-15 下午3:12:49 
* @version V1.0
 */
@SuppressWarnings("serial")
public class RemoteRepaireAction extends OfficeJsonBaseAction{
	
	private UserService userService;
	private OfficeRepaireService officeRepaireService;
	private McodedetailService mcodedetailService; 
	private TeacherService teacherService;
	private UnitService unitService;
	private OfficeTypeAuthService officeTypeAuthService;
	private EisuClassService eisuClassService;
	private BasicClassService basicClassService;
	private TeachAreaService teachAreaService;
	private OfficeRepaireTypeService officeRepaireTypeService;
	
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private OfficeRepaire officeRepaire;
	
	private String userId;
	private String unitId;
	private String id;
	private String searchType;
	private String applyType;
	private String unitClass;
	private boolean isManager;
	private String searchContent;
	
	
	public String repair(){
		isManager = isCustomRole("repaire_manage");
		return SUCCESS;
	}
	public void powerManager(){
		isManager = isCustomRole("repaire_manage");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("isManager", isManager);
		jsonMap.put(getDetailObjectName(), jsonObj);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
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
	public void applyRepaireType(){
		List<Mcodedetail> mcodeDetails = mcodedetailService.getMcodeDetails("DM-BXLB");
		JSONArray array=new JSONArray();
		for(Mcodedetail item: mcodeDetails){
			JSONObject json=new JSONObject();
			json.put("typeId", item.getThisId());
			json.put("typeName", item.getContent());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void applyList(){
		Pagination page = getPage();
		List<OfficeRepaire> repaireList=officeRepaireService.getOfficeRepaireList(userId, unitId, null, searchType, applyType, null, null, searchContent, page);
		JSONArray array=new JSONArray();
		for(OfficeRepaire repaire: repaireList){
			JSONObject json=new JSONObject();
			json.put("id", repaire.getId());
			json.put("detailTime", DateUtils.date2String(repaire.getDetailTime(), "MM-dd"));
//			String type=mcodedetailService.getMcodeDetail("DM-BXLB", repaire.getType()).getContent()+(StringUtils.isNotBlank(repaire.getRepaireTypeName())?"("+repaire.getRepaireTypeName()+")":"");
//			if(type.length() > 4){
//				type = type.substring(0, 4)+"...";
//			}
			String type=mcodedetailService.getMcodeDetail("DM-BXLB", repaire.getType()).getContent();
			json.put("type", type);
			json.put("state", repaire.getState());
			json.put("isFeedback", repaire.getIsFeedback());
			String goodsName = repaire.getGoodsName();
			if(goodsName.length()>5){
				goodsName = goodsName.substring(0, 5)+"...";
			}
			json.put("goodsName", goodsName);
			String remark = repaire.getRemark();
			if(remark.length()>22){
				remark = remark.substring(0, 22)+"...";
			}
			json.put("remark", remark);
			String goodsPlace = repaire.getGoodsPlace();
			if(goodsPlace.length()>22){
				goodsPlace = goodsPlace.substring(0, 22)+"...";
			}
			json.put("goodsPlace", goodsPlace);
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("result", 1);
		jsonMap.put("page", page);
		responseJSON(jsonMap);
	}
	public void applyRepaire(){
		JSONObject json=new JSONObject();
		Unit unit=null;
		if(StringUtils.isNotBlank(id)){
			officeRepaire=officeRepaireService.getOfficeRepaireById(id);
			if(officeRepaire==null){
				jsonMap.put("msg", "信息未找到或已被删除");
				jsonMap.put("result", 0);
				responseJSON(jsonMap);
				return;
			}
			unit = unitService.getUnit(officeRepaire.getUnitId());
			json.put("id",officeRepaire.getId());
			json.put("userId",officeRepaire.getUserId());
			json.put("unitId",officeRepaire.getUnitId());
			User user = userService.getUser(officeRepaire.getUserId());
			if(user != null){
				json.put("userName", user.getRealname());
			}else{
				json.put("userName", "");
			}
			json.put("teachAreaId", officeRepaire.getTeachAreaId());
			json.put("equipmentType",officeRepaire.getEquipmentType());
			json.put("classId", officeRepaire.getClassId());
			json.put("goodsPlace", officeRepaire.getGoodsPlace());
			json.put("phone", officeRepaire.getPhone());
			json.put("goodsName", officeRepaire.getGoodsName());
			json.put("type", officeRepaire.getType());
			json.put("repaireTypeId", officeRepaire.getRepaireTypeId());
			json.put("detailTime", DateUtils.date2String(officeRepaire.getDetailTime(), "yyyy-MM-dd HH:mm"));
			json.put("remark", officeRepaire.getRemark());
			
			json.put("repaireRemark", officeRepaire.getRepaireRemark());
			json.put("feedback", officeRepaire.getFeedback());
			
			json.put("state", officeRepaire.getState());
			json.put("isFeedback", officeRepaire.getIsFeedback());
			json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeRepaire.getAttachments()));
		}else{
			unit = unitService.getUnit(unitId);
			officeRepaire=new OfficeRepaire();
			OfficeRepaire lastOfficeRepaire = officeRepaireService.getOfficeRepaireByUserIdLastTime(userId);
			json.put("id","");
			json.put("userId",userId);
			json.put("unitId",unitId);
			if(lastOfficeRepaire!=null){
				json.put("goodsPlace", lastOfficeRepaire.getGoodsPlace());
			}
			officeRepaire.setUserId(userId);
			officeRepaire.setUnitId(unitId);
			User user=userService.getUser(userId);
			if(user!=null){
				json.put("userName", user.getRealname());
				Teacher teacher = teacherService.getTeacher(user.getTeacherid());
				if(teacher!=null){
					json.put("phone", StringUtils.isNotBlank(teacher.getPersonTel())?teacher.getPersonTel():"");
				}
			}else{
				json.put("userName", "");
			}
			json.put("detailTime", DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm"));
		}
		if(unit!=null){
			json.put("unitClass", unit.getUnitclass());
		}else{
			json.put("unitClass", "");
		}
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		if(unit!=null && unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
			List<TeachArea> teachAreas = teachAreaService.getTeachAreas(getUnitId());
			for (TeachArea item : teachAreas) {
				jsonObject = new JSONObject();
				jsonObject.put("id", item.getId());
				jsonObject.put("name", item.getAreaName());
				jsonArray.add(jsonObject);
			}
		}
		json.put("teachAreaIdList", jsonArray);
		List<Mcodedetail> mcodeDetails = mcodedetailService.getMcodeDetails("DM-SBLX");
		jsonArray = new JSONArray();
		for (Mcodedetail item : mcodeDetails) {
			jsonObject = new JSONObject();
			jsonObject.put("id", item.getThisId());
			jsonObject.put("name", item.getContent());
			jsonArray.add(jsonObject);
		}
		json.put("equipmentTypeList", jsonArray);
		jsonArray = new JSONArray();
//		List<EisuClass> classes = eisuClassService.getClasses(StringUtils.isNotBlank(officeRepaire.getUnitId())?officeRepaire.getUnitId():unitId);
//		if(unit!=null && unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
//			for (EisuClass item : classes) {
//				jsonObject = new JSONObject();
//				jsonObject.put("id", item.getId());
//				jsonObject.put("name", item.getClassnamedynamic());
//				jsonArray.add(jsonObject);
//			}
//		}
		List<BasicClass> classes = basicClassService.getClasses(StringUtils.isNotBlank(officeRepaire.getUnitId())?officeRepaire.getUnitId():unitId);
		if(unit!=null && unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
			for (BasicClass item : classes) {
				jsonObject = new JSONObject();
				jsonObject.put("id", item.getId());
				jsonObject.put("name", item.getClassnamedynamic());
				jsonArray.add(jsonObject);
			}
		}
		json.put("classIdList", jsonArray);
		mcodeDetails = mcodedetailService.getMcodeDetails("DM-BXLB");
		jsonArray = new JSONArray();
		for (Mcodedetail item : mcodeDetails) {
			jsonObject = new JSONObject();
			jsonObject.put("id", item.getThisId());
			jsonObject.put("name", item.getContent());
			jsonArray.add(jsonObject);
		}
		json.put("typeList", jsonArray);
		
		jsonArray = new JSONArray();
		List<OfficeRepaireType> officeRepaireTypeList = officeRepaireTypeService.getOfficeRepaireTypeByUnitIdList(officeRepaire.getUnitId());
		for (OfficeRepaireType item : officeRepaireTypeList) {
			jsonObject = new JSONObject();
			jsonObject.put("key", item.getThisId());
			jsonObject.put("id", item.getId());
			jsonObject.put("name", item.getTypeName());
			jsonArray.add(jsonObject);
		}
		json.put("officeRepaireTypeList", jsonArray);
		jsonArray = new JSONArray();
		if (StringUtils.isNotBlank(officeRepaire.getType())){
			for (OfficeRepaireType item : officeRepaireTypeList) {
				if(officeRepaire.getType().equals(item.getThisId())){
					jsonObject = new JSONObject();
					jsonObject.put("id", item.getId());
					jsonObject.put("name", item.getTypeName());
					jsonArray.add(jsonObject);
				}
			}
		}
		json.put("repaireTypeIdList", jsonArray);
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void saveRepaire(){
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			if(!StringUtils.equals(officeRepaire.getEquipmentType(), "1") && !StringUtils.equals(officeRepaire.getEquipmentType(), "2")){
			    officeRepaire.setClassId(null);
			}
			if(unitClass.equals(String.valueOf(Unit.UNIT_CLASS_EDU))) {
			    officeRepaire.setTeachAreaId(BaseConstant.ZERO_GUID);
			}
			if(StringUtils.isNotEmpty(officeRepaire.getId())){
				OfficeRepaire officeRepaireById = officeRepaireService.getOfficeRepaireById(officeRepaire.getId());
				if(officeRepaireById!=null){
					if("1".equals(officeRepaireById.getState())){
						officeRepaireService.update(officeRepaire,file);
					}else{
						jsonMap.put("msg", "操作失败，维修状态已改变");
						jsonMap.put("result", 0);
						responseJSON(jsonMap);
						return;
					}
				}else{
					jsonMap.put("msg", "信息未找到或已被删除");
					jsonMap.put("result", 0);
					responseJSON(jsonMap);
					return;
				}
			}else{
				officeRepaire.setCreateTime(new Date());
				officeRepaire.setState(OfficeRepaire.STATE_ONE);
				officeRepaire.setIsDeleted(false);
				officeRepaire.setIsFeedback(false);
				officeRepaireService.add(officeRepaire,file);
			}
			jsonMap.put("result",1);
		}catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("msg", "操作失败");
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	public void deleteRepaire(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeRepaireService.delete(new String[]{id});
				jsonMap.put("result",1);
			} catch (Exception e) {
				jsonMap.put("result",0);
			}
		}else{
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	public void repaireManagerList(){
		Pagination page=getPage();
		if(StringUtils.isNotBlank(searchType)){
			List<OfficeTypeAuth> arealist = officeTypeAuthService.getOfficeTypeAuthList(Constants.REPAIRE_STATE, userId);
			boolean isHas = false;
			for (OfficeTypeAuth item : arealist) {
				if(searchType.equals(item.getType())){
					isHas=true;
					break;
				}
			}
			if(!isHas){
				searchType="没权限";
			}
		}
		List<OfficeRepaire> repaireList=officeRepaireService.getOfficeRepaireMangeList(userId, unitId, null, searchType, applyType, null, null, null, page);
		Set<String>userIds=new HashSet<String>();
		for(OfficeRepaire item: repaireList){
			userIds.add(item.getUserId());
		}
		Map<String, User> usersMap = new HashMap<String, User>();
		if(CollectionUtils.isNotEmpty(userIds)){
			usersMap = userService.getUsersMap(userIds.toArray(new String[0]));
		}
		JSONArray array=new JSONArray();
		for(OfficeRepaire repaire: repaireList){
			JSONObject json=new JSONObject();
			json.put("id", repaire.getId());
			json.put("detailTime", DateUtils.date2String(repaire.getDetailTime(), "MM-dd"));
//			String type=mcodedetailService.getMcodeDetail("DM-BXLB", repaire.getType()).getContent()+(StringUtils.isNotBlank(repaire.getRepaireTypeName())?"("+repaire.getRepaireTypeName()+")":"");
//			if(type.length() > 4){
//				type = type.substring(0, 4)+"...";
//			}
			String type=mcodedetailService.getMcodeDetail("DM-BXLB", repaire.getType()).getContent();
			json.put("type", type);
			json.put("state", repaire.getState());
			json.put("isFeedback", repaire.getIsFeedback());
			String goodsName = repaire.getGoodsName();
			if(goodsName.length()>5){
				goodsName = goodsName.substring(0, 5)+"...";
			}
			json.put("goodsName", goodsName);
			String remark = repaire.getRemark();
			if(remark.length()>21){
				remark = remark.substring(0, 21)+"...";
			}
			json.put("remark", remark);
			String goodsPlace = repaire.getGoodsPlace();
			if(goodsPlace.length()>22){
				goodsPlace = goodsPlace.substring(0, 22)+"...";
			}
			json.put("goodsPlace", goodsPlace);
			User user = usersMap.get(repaire.getUserId());
			if(user!=null){
				json.put("userName", user.getRealname());
			}else{
				json.put("userName", "用户或被删除");
			}
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void repaireManagerListH5Count(){
		if(StringUtils.isNotBlank(searchType)){
			List<OfficeTypeAuth> arealist = officeTypeAuthService.getOfficeTypeAuthList(Constants.REPAIRE_STATE, userId);
			boolean isHas = false;
			for (OfficeTypeAuth item : arealist) {
				if(searchType.equals(item.getType())){
					isHas=true;
					break;
				}
			}
			if(!isHas){
				searchType="没权限";
			}
		}
		int repaireCount = officeRepaireService.getOfficeRepaireMangeListH5Count(userId, unitId, null, null, StringUtils.isNotBlank(applyType)?applyType.split(","):null, null, null, null);
		jsonMap.put("count", repaireCount);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void repaireManagerListH5(){
		Pagination page=getPage();
		if(StringUtils.isNotBlank(searchType)){
			List<OfficeTypeAuth> arealist = officeTypeAuthService.getOfficeTypeAuthList(Constants.REPAIRE_STATE, userId);
			boolean isHas = false;
			for (OfficeTypeAuth item : arealist) {
				if(searchType.equals(item.getType())){
					isHas=true;
					break;
				}
			}
			if(!isHas){
				searchType="没权限";
			}
		}
		List<OfficeRepaire> repaireList=officeRepaireService.getOfficeRepaireMangeListH5(userId, unitId, null, searchType, StringUtils.isNotBlank(applyType)?applyType.split(","):null, null, null, searchContent, page);
		Set<String>userIds=new HashSet<String>();
		for(OfficeRepaire item: repaireList){
			userIds.add(item.getUserId());
		}
		Map<String, User> usersMap = new HashMap<String, User>();
		if(CollectionUtils.isNotEmpty(userIds)){
			usersMap = userService.getUsersMap(userIds.toArray(new String[0]));
		}
		JSONArray array=new JSONArray();
		for(OfficeRepaire repaire: repaireList){
			JSONObject json=new JSONObject();
			json.put("id", repaire.getId());
			json.put("detailTime", DateUtils.date2String(repaire.getDetailTime(), "MM-dd"));
//			String type=mcodedetailService.getMcodeDetail("DM-BXLB", repaire.getType()).getContent()+(StringUtils.isNotBlank(repaire.getRepaireTypeName())?"("+repaire.getRepaireTypeName()+")":"");
//			if(type.length() > 4){
//				type = type.substring(0, 4)+"...";
//			}
			String type=mcodedetailService.getMcodeDetail("DM-BXLB", repaire.getType()).getContent();
			json.put("type", type);
			json.put("state", repaire.getState());
			json.put("isFeedback", repaire.getIsFeedback());
			String goodsName = repaire.getGoodsName();
			if(goodsName.length()>5){
				goodsName = goodsName.substring(0, 5)+"...";
			}
			json.put("goodsName", goodsName);
			String remark = repaire.getRemark();
			if(remark.length()>21){
				remark = remark.substring(0, 21)+"...";
			}
			json.put("remark", remark);
			String goodsPlace = repaire.getGoodsPlace();
			if(goodsPlace.length()>22){
				goodsPlace = goodsPlace.substring(0, 22)+"...";
			}
			json.put("goodsPlace", goodsPlace);
			User user = usersMap.get(repaire.getUserId());
			if(user!=null){
				json.put("userName", user.getRealname());
			}else{
				json.put("userName", "用户或被删除");
			}
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void aduitRepaire(){
		officeRepaire=officeRepaireService.getOfficeRepaireById(id);
		if(officeRepaire==null){
			jsonMap.put("msg", "信息未找到或已被删除");
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
			return;
		}
		String[] mcodeIds = { "DM-BXLB", "DM-SBLX"};
		Map<String, Map<String, String>> detailMap = mcodedetailService.getContent2Map(mcodeIds);
		
		JSONObject json=new JSONObject();
		json.put("id",officeRepaire.getId());
		json.put("userId",officeRepaire.getUserId());
		json.put("unitId",officeRepaire.getUnitId());
		User user = userService.getUser(officeRepaire.getUserId());
		if(user != null){
			json.put("userName", user.getRealname());
		}else{
			json.put("userName", "");
		}
		Unit unit = unitService.getUnit(officeRepaire.getUnitId());
		if(unit!=null){
			json.put("unitClass", unit.getUnitclass());
		}else{
			json.put("unitClass", "");
		}
		if(StringUtils.isNotBlank(officeRepaire.getTeachAreaId())){
			TeachArea teachArea = teachAreaService.getTeachArea(officeRepaire.getTeachAreaId());
			if(teachArea!=null){
				json.put("teachAreaId", teachArea.getAreaName());
			}else{
				json.put("teachAreaId", "");
			}
		}else{
			json.put("teachAreaId", "");
		}
		json.put("equipmentType", detailMap.get("DM-SBLX").get(String.valueOf(officeRepaire.getEquipmentType())));
		if(StringUtils.isNotBlank(officeRepaire.getClassId())){
			BasicClass class1 = basicClassService.getClass(officeRepaire.getClassId());
			if(class1!=null){
				json.put("classId", class1.getClassnamedynamic());
			}else{
				json.put("classId", "");
			}
		}else{
			json.put("classId", "");
		}
		json.put("goodsPlace", officeRepaire.getGoodsPlace());
		json.put("phone", officeRepaire.getPhone());
		json.put("goodsName", officeRepaire.getGoodsName());
		json.put("type", detailMap.get("DM-BXLB").get(String.valueOf(officeRepaire.getType())));
		if(StringUtils.isNotBlank(officeRepaire.getRepaireTypeId())){
			OfficeRepaireType officeRepaireTypeById = officeRepaireTypeService.getOfficeRepaireTypeById(officeRepaire.getRepaireTypeId());
			if(officeRepaireTypeById!=null){
				json.put("repaireTypeId", officeRepaireTypeById.getTypeName());
			}else{
				json.put("repaireTypeId", "");
			}
		}else{
			json.put("repaireTypeId", "");
		}
		json.put("detailTime", DateUtils.date2String(officeRepaire.getDetailTime(), "yyyy-MM-dd HH:mm"));
		json.put("remark", officeRepaire.getRemark());
		json.put("repaireTime", officeRepaire.getRepaireTime()!=null?DateUtils.date2String(officeRepaire.getRepaireTime(), "yyyy-MM-dd HH:mm"):DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm"));
		json.put("state", officeRepaire.getState());
		json.put("repaireRemark", officeRepaire.getRepaireRemark());
		json.put("feedback", officeRepaire.getFeedback());
		json.put("mark", officeRepaire.getMark());
		json.put("isFeedback", officeRepaire.getIsFeedback());
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeRepaire.getAttachments()));
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void aduitRepaireSave(){
		try {
			OfficeRepaire oldRp = officeRepaireService.getOfficeRepaireById(officeRepaire.getId());
			if(oldRp==null){
				jsonMap.put("msg", "信息未找到或已被删除");
				jsonMap.put("result", 0);
				responseJSON(jsonMap);
				return;
			}
			if(StringUtils.isNotBlank(officeRepaire.getFeedback())){
				oldRp.setFeedback(officeRepaire.getFeedback());
				oldRp.setMark(officeRepaire.getMark());
				oldRp.setIsFeedback(true);
				officeRepaireService.updateFeedBack(oldRp);
			}else{
				oldRp.setRepaireUserId(userId);
				oldRp.setState(officeRepaire.getState());
				oldRp.setRepaireTime(officeRepaire.getRepaireTime());
				oldRp.setRepaireRemark(officeRepaire.getRepaireRemark());
				officeRepaireService.updateState(oldRp);
			}
			jsonMap.put("result",1);
		} catch (Exception e) {
			e.getStackTrace();
			jsonMap.put("msg", "操作失败");
			jsonMap.put("result",0);
		}
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}
	public void setOfficeRepaireTypeService(OfficeRepaireTypeService officeRepaireTypeService) {
		this.officeRepaireTypeService = officeRepaireTypeService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setOfficeRepaireService(OfficeRepaireService officeRepaireService) {
		this.officeRepaireService = officeRepaireService;
	}
	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
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
	public void setOfficeTypeAuthService(OfficeTypeAuthService officeTypeAuthService) {
		this.officeTypeAuthService = officeTypeAuthService;
	}
	public void setEisuClassService(EisuClassService eisuClassService) {
		this.eisuClassService = eisuClassService;
	}
	public void setTeachAreaService(TeachAreaService teachAreaService) {
		this.teachAreaService = teachAreaService;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public String getId() {
		return id;
	}
	public boolean isManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public OfficeRepaire getOfficeRepaire() {
		return officeRepaire;
	}
	public void setOfficeRepaire(OfficeRepaire officeRepaire) {
		this.officeRepaire = officeRepaire;
	}
	public String getUnitClass() {
		return unitClass;
	}
	public void setUnitClass(String unitClass) {
		this.unitClass = unitClass;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	
}
