package net.zdsoft.office.bulletin.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.component.push.client.WeikePushClient;
import net.zdsoft.eis.component.push.entity.WKPushParm;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinDao;
import net.zdsoft.office.bulletin.dao.OfficeBulletinReadDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
import net.zdsoft.office.bulletin.service.OfficeBulletinService;
import net.zdsoft.office.enums.WeikeAppUrlEnum;
import net.zdsoft.office.msgcenter.dto.ReadInfoDto;
import net.zdsoft.office.msgcenter.service.OfficeBusinessJumpService;
import net.zdsoft.office.util.EntityUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * office_bulletin
 * @author 
 * 
 */
public class OfficeBulletinServiceImpl implements OfficeBulletinService{
	private UserService userService;
	private DeptService deptService;
	private UnitService unitService;
	private TeacherService teacherService;
	private TeachAreaService teachAreaService;
	private OfficeBulletinDao officeBulletinDao;
	private OfficeBulletinReadDao officeBulletinReadDao;
	private ModuleService moduleService;
	private SystemIniService systemIniService;
	private OfficeSubsystemService officeSubsystemService;
	private OfficeBusinessJumpService officeBusinessJumpService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeBulletinReadDao(OfficeBulletinReadDao officeBulletinReadDao) {
		this.officeBulletinReadDao = officeBulletinReadDao;
	}

	@Override
	public void save(OfficeBulletin officeBulletin){
		officeBulletin.setId(EntityUtils.generateUUID());
		officeBulletinDao.save(officeBulletin);
	}
	
	@Override
	public Integer delete(String id,String userId){
		return officeBulletinDao.delete(id, userId);
	}

	@Override
	public Integer deleteIds(String[] ids,String userId){
		return officeBulletinDao.deleteIds(ids, userId);
	}
	
	@Override
	public Integer publish(String[] ids,String userId,String[] unitIds) {
		if(ids!=null && ids.length>0){
			OfficeBulletin bulletin=officeBulletinDao.getOfficeBulletinById(ids[0]);
			if(bulletin!=null && ArrayUtils.isNotEmpty(unitIds)){
				sendWeikeMsg(bulletin, unitIds);
			}
		}
		return officeBulletinDao.publish(ids,userId);
	}
	
	public Integer submit(String id){
		return officeBulletinDao.submit(id);
	}

	@Override
	public Integer update(OfficeBulletin officeBulletin){
		return officeBulletinDao.update(officeBulletin);
	}

	@Override
	public OfficeBulletin getOfficeBulletinById(String id){
		OfficeBulletin officeBulletin = officeBulletinDao.getOfficeBulletinById(id);
		User user = userService.getUserWithDel(officeBulletin.getCreateUserId());
		if(user != null){
			officeBulletin.setCreateUserName(user.getRealname());
			if(!user.getIsdeleted()){
				Teacher teacher = teacherService.getTeacherContainDelete(user.getTeacherid());
				if(teacher != null){
					Dept dept = deptService.getDept(teacher.getDeptid());
					if(dept != null){
						officeBulletin.setDeptName(dept.getDeptname());
					}
				}
			}else{
				officeBulletin.setDeptName("部门已删除");
			}
		}else{
			officeBulletin.setCreateUserName("用户已删除");
		}
		Map<String, String> clickMap = officeBulletinReadDao.getClickMapByBulletinIds(new String[]{id});
		if(clickMap!=null && StringUtils.isNotBlank(clickMap.get(id))){
			officeBulletin.setClickNum(clickMap.get(id));
		}else{
			officeBulletin.setClickNum("0");
		}
		return officeBulletin;
	}

	@Override
	public Map<String, OfficeBulletin> getOfficeBulletinMapByIds(String[] ids){
		return officeBulletinDao.getOfficeBulletinMapByIds(ids);
	}

	@Override
	public List<OfficeBulletin> getOfficeBulletinList(){
		return officeBulletinDao.getOfficeBulletinList();
	}
	@Override
	public void sendWeikeMsg(OfficeBulletin bulletin,String[] unitIds){
		User user=userService.getUser(bulletin.getCreateUserId());
		WKPushParm parm = new WKPushParm();
		String typeName=bulletin.getType();
		if(StringUtils.isNotBlank(typeName)){
			if(typeName.equals("1")){
				typeName="通知";
			}else if(typeName.equals("3")){ 
				typeName="公告";
			}else{
				return;
			}
		}else{
			return;
		}
		parm.setMsgTitle(typeName);
		parm.setHeadContent(bulletin.getTitle());
		parm.setBodyTitle(typeName);
		List<String> rows = new ArrayList<String>();
//		rows.add("通知内容:"+bulletin.getContent());
		rows.add("您有一份新的"+typeName+"需要查看");
		parm.setRowsContent(rows.toArray(new String[0]));
		String domain = RedisUtils.get("EIS.BASE.PATH.V6");
		if(StringUtils.isNotBlank(domain)){
			parm.setFootContent("详情");
			parm.setJumpType(WeikeAppConstant.JUMP_TYPE_0);
			if(user !=null){
				Unit unit = unitService.getUnit(user.getUnitid());
				if(unit!=null){
					String url = WeikeAppUrlEnum.getWeikeUrl(WeikeAppConstant.BULLETIN, WeikeAppConstant.DETAILE_URL)+"&id="+bulletin.getId();
					parm.setUrl(domain + url);
				}
			}
		}
		
		List<User> userList=null;
		if((unitIds==null || unitIds.length==0)&& 
				StringUtils.isNotBlank(bulletin.getAreaId())&& !StringUtils.equals(BaseConstant.ZERO_GUID, bulletin.getAreaId())){
			List<Dept> depts = deptService.getDeptsByAreaId(bulletin.getAreaId());
			Set<String> deptIds = new HashSet<String>();
			for(Dept dept: depts){
				deptIds.add(dept.getId());
			}
			userList=userService.getUsersByDeptIds(deptIds.toArray(new String[0]));
		}else{
			userList=userService.getUsersByUnitIds(unitIds);
		}
		
		Set<String> userIds=new HashSet<String>();
		if(CollectionUtils.isNotEmpty(userList)){
			for(User user1:userList){
				//目前通知只推送给教师，不能推送给学和家长
				if(User.TEACHER_LOGIN == user1.getOwnerType()){
					userIds.add(user1.getId());
				}
			}
		}
		WeikePushClient.getInstance().pushMessage(WeikeAppConstant.BULLETIN, userIds.toArray(new String[0]), parm);
	}
	@Override
	public List<OfficeBulletin> getOfficeBulletinPage(String unitId, String isReadUserId, String userId,String[] bulletinType,String state,String startTime,String endTime, String publishName, String searName, String searchAreaId, Pagination page){
		Set<String>userIdsSet1=new HashSet<String>();
		List<OfficeBulletin> officeBulletinList;
		if(StringUtils.isNotBlank(publishName)){
			List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
			for (User user : usersFaintness) {
				userIdsSet1.add(user.getId());
			}
			officeBulletinList = officeBulletinDao.getOfficeBulletinPage(unitId,userId,bulletinType,state,startTime,endTime, searName, userIdsSet1.toArray(new String[0]), searchAreaId, page);
		}else{
			officeBulletinList = officeBulletinDao.getOfficeBulletinPage(unitId,userId,bulletinType,state,startTime,endTime, searName, null, searchAreaId, page);
		}
		String[] ids = new String[officeBulletinList.size()];
		int i=0;
		Set<String>userIdsSet=new HashSet<String>();
		for(OfficeBulletin officeBulletin:officeBulletinList){
			userIdsSet.add(officeBulletin.getCreateUserId());
			if(StringUtils.isNotBlank(officeBulletin.getAuditUserId())){
				userIdsSet.add(officeBulletin.getAuditUserId());
			}
			ids[i] = officeBulletin.getId();
			i++;
		}
		Map<String, String> readMap = officeBulletinReadDao.getReadMapByBulletinIds(isReadUserId,ids);
		Map<String, String> clickMap = officeBulletinReadDao.getClickMapByBulletinIds(ids);
		Map<String, User> usersMap = userService.getUserWithDelMap(userIdsSet.toArray(new String[0]));
		Map<String, TeachArea> areaMap = teachAreaService.getTeachAreaMap(unitId);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(unitId);
		String areaName = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = dateFormat.format(date);
		for(OfficeBulletin officeBulletin:officeBulletinList){
			User user = usersMap.get(officeBulletin.getCreateUserId());
			if(user!=null){
				if(user.getIsdeleted()){
					officeBulletin.setCreateUserName(user.getRealname());
				}else{
					officeBulletin.setCreateUserName(user.getRealname());
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if(teacher!=null){
						Dept dept = deptMap.get(teacher.getDeptid());
						if(dept!=null){
							officeBulletin.setDeptName(dept.getDeptname());
						}
					}
				}
			}else{
				officeBulletin.setCreateUserName("用户已删除");
			}
			User user2 = usersMap.get(officeBulletin.getAuditUserId());
			if(user2!=null){
				officeBulletin.setPublishUserName(user2.getRealname());
			}else{
				officeBulletin.setPublishUserName("用户已删除");
			}
			if(BaseConstant.ZERO_GUID.equals(officeBulletin.getAreaId())){
				areaName = "全校";
			}else{
				if(areaMap.get(officeBulletin.getAreaId())!=null){
					areaName = areaMap.get(officeBulletin.getAreaId()).getAreaName();
				}else{
					areaName = "校区已删除";
				}
			}
			officeBulletin.setAreaName(areaName);
			if(str.equals(dateFormat.format(officeBulletin.getCreateTime()))){
				officeBulletin.setIsNew(true);
			}
			if(StringUtils.isNotBlank(readMap.get(officeBulletin.getId()))){
				officeBulletin.setIsRead(true);
			}
			//点击量
			if(StringUtils.isNotBlank(clickMap.get(officeBulletin.getId()))){
				officeBulletin.setClickNum(clickMap.get(officeBulletin.getId()));
			}else{
				officeBulletin.setClickNum("0");
			}
		}
		return officeBulletinList;
	}
	
	public List<OfficeBulletin> getOfficeBulletinListPage(String[] types,String unitId, String userId, String areaId, String state, String startTime, String endTime, String searName, String publishName, Pagination page){
		//TODO
		List<OfficeBulletin> officeBulletinList;
		Set<String> userIdsSet1 = new HashSet<String>();
		Unit unit = unitService.getUnit(unitId);
		
		Set<String> userIdAllSet=new HashSet<String>();//总人数
		Set<String> unitIdSet=new HashSet<String>();
		
		boolean lala=false;
		for(String type:types){
			if(org.apache.commons.lang3.StringUtils.equals("2", type)){
				lala=true;
			}
		}
		//新疆定制领导活动每日报本单位只能见本单位发布信息
		if("xinjiang".equalsIgnoreCase(systemIniService.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL))&&lala){
			if(StringUtils.isNotBlank(publishName)){
				List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
				for (User user : usersFaintness) {
					userIdsSet1.add(user.getId());
				}
				officeBulletinList = officeBulletinDao.getOfficeBulletinListPage(types,unitId, areaId, state, startTime, endTime, searName, userIdsSet1.toArray(new String[0]), page);
			}else{
				officeBulletinList = officeBulletinDao.getOfficeBulletinListPage(types,unitId, areaId, state, startTime, endTime, searName, null, page);
			}
			List<User> users=userService.getUsers(unitId);
			for (User user : users) {
				userIdAllSet.add(user.getId());
			}
			unitIdSet.add(unitId);
		}else{
			if(StringUtils.equals(unit.getUnitclass()+"", "1")){
				if(StringUtils.equals(unit.getParentid(), BaseConstant.ZERO_GUID)){
					//顶级教育局  只看到本单位发布的
					if(StringUtils.isNotBlank(publishName)){
						List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
						for (User user : usersFaintness) {
							userIdsSet1.add(user.getId());
						}
						officeBulletinList = officeBulletinDao.getOfficeBulletinListPage(types,unitId, areaId, state, startTime, endTime, searName, userIdsSet1.toArray(new String[0]), page);
					}else{
						officeBulletinList = officeBulletinDao.getOfficeBulletinListPage(types,unitId, areaId, state, startTime, endTime, searName, null, page);
					}
					
					List<User> users=userService.getUsers(unitId);
					for (User user : users) {
						userIdAllSet.add(user.getId());
					}
					unitIdSet.add(unitId);
				}else{
					//下属教育局 可以看到本单位和上级单位和顶级的
					String parentId = unit.getParentid();
					Unit unitTop = unitService.getTopEdu();
					String topId = unitTop.getId();
					if(StringUtils.isNotBlank(publishName)){
						List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
						for (User user : usersFaintness) {
							userIdsSet1.add(user.getId());
						}
						List<User> usersFaintness1 = userService.getUsersFaintness(publishName, parentId);
						for (User user : usersFaintness1) {
							userIdsSet1.add(user.getId());
						}
						List<User> usersFaintness2 = userService.getUsersFaintness(publishName, topId);
						for (User user : usersFaintness2) {
							userIdsSet1.add(user.getId());
						}
						officeBulletinList = officeBulletinDao.getOfficeBulletinListPage1(types,unitId, areaId, state, startTime, endTime, searName, userIdsSet1.toArray(new String[0]), topId, parentId, page);
					}else{
						officeBulletinList = officeBulletinDao.getOfficeBulletinListPage1(types,unitId, areaId, state, startTime, endTime, searName, null, topId, parentId,page);
					}
					
					//List<User> users=userService.getUsersByUnitIds(new String[]{topId,parentId,unitId});
					List<User> users=userService.getUsersByUnitIds(new String[]{unitId});
					for (User user : users) {
						userIdAllSet.add(user.getId());
					}
					//unitIdSet.add(topId);//unitIdSet.add(parentId);
					unitIdSet.add(unitId);
				}
			}else if(StringUtils.equals(unit.getUnitclass()+"", "2")){
				//学校
				String parentId = unit.getParentid();
				Unit unitTop = unitService.getTopEdu();
				String topId = unitTop.getId();
				if(StringUtils.isNotBlank(publishName)){
					List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
					for (User user : usersFaintness) {
						userIdsSet1.add(user.getId());
					}
					List<User> usersFaintness1 = userService.getUsersFaintness(publishName, parentId);
					for (User user : usersFaintness1) {
						userIdsSet1.add(user.getId());
					}
					List<User> usersFaintness2 = userService.getUsersFaintness(publishName, topId);
					for (User user : usersFaintness2) {
						userIdsSet1.add(user.getId());
					}
					officeBulletinList = officeBulletinDao.getOfficeBulletinListPage1(types,unitId, areaId, state, startTime, endTime, searName, userIdsSet1.toArray(new String[0]),topId, parentId,page);
				}else{
					officeBulletinList = officeBulletinDao.getOfficeBulletinListPage1(types,unitId, areaId, state, startTime, endTime, searName, null,topId, parentId,page);
				}
				
				List<User> users=userService.getUsers(unitId);
				for (User user : users) {
					userIdAllSet.add(user.getId());
				}
				unitIdSet.add(unitId);
			}else{
				officeBulletinList = new ArrayList<OfficeBulletin>();
			}
		}
		
		if(officeBulletinList==null){
			officeBulletinList = new ArrayList<OfficeBulletin>();
		}
		
		
		String[] ids = new String[officeBulletinList.size()];
		int i=0;
		Set<String> userIdsSet = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		Set<String> bulletinIds=new HashSet<String>();
		for(OfficeBulletin officeBulletin:officeBulletinList){
			if(StringUtils.isNotBlank(officeBulletin.getAuditUserId())){
				userIdsSet.add(officeBulletin.getAuditUserId());
				userIdsSet.add(officeBulletin.getCreateUserId());
				unitIds.add(officeBulletin.getUnitId());
			}
			bulletinIds.add(officeBulletin.getId());
			ids[i] = officeBulletin.getId();
			i++;
		}
		Map<String, String> readMap = officeBulletinReadDao.getReadMapByBulletinIds(userId,ids);
		Map<String, TeachArea> areaMap = teachAreaService.getTeachAreaMap(unitId);
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		
		//Map<String,String> bulletinReadMap=officeBulletinReadDao.getClickMapByBulletinIds(bulletinIds.toArray(new String[0]));
		Map<String, List<OfficeBulletinRead>> bulletinReadMap=officeBulletinReadDao.getOfficeBulletinReadMap(bulletinIds.toArray(new String[0]));

//		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
//		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(unitId);
		//Set<String> readSet=new HashSet<String>();
		if(MapUtils.isNotEmpty(bulletinReadMap)){
			for (List<OfficeBulletinRead> string : bulletinReadMap.values()) {
				for (OfficeBulletinRead officeBulletinRead : string) {
					userIdsSet.add(officeBulletinRead.getUserId());
				}
			}
		}
		Map<String, User> usersMap = userService.getUserWithDelMap(userIdsSet.toArray(new String[0]));
		
		String areaName = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = dateFormat.format(date);
		String[] teacherIds = new String[usersMap.size()];
		int j = 0;
		for(User user:usersMap.values()){
			teacherIds[j] = user.getTeacherid();
			j++;
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds);
		String[] deptIds  = new String[teacherMap.size()];
		int k = 0;
		for(Teacher teacher:teacherMap.values()){
			deptIds[k] = teacher.getDeptid();
			k++;
		}
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds);
		for(OfficeBulletin officeBulletin:officeBulletinList){
			
			int total=userIdAllSet.size();
			int read=0;
			if(bulletinReadMap.containsKey(officeBulletin.getId())){//未读和已读人数
				List<OfficeBulletinRead> redList=bulletinReadMap.get(officeBulletin.getId());
				if(CollectionUtils.isNotEmpty(redList)){
					for (OfficeBulletinRead officeBulletinRead : redList) {
						//if(officeBulletinRead!=null&&StringUtils.isNotBlank(officeBulletinRead.getUnitId())&&StringUtils.equals(officeBulletinRead.getUnitId(), unitId)){//是否需要过滤，看测试
							//read++;
						//}
						if(usersMap.containsKey(officeBulletinRead.getUserId())){
							User user3=usersMap.get(officeBulletinRead.getUserId());
							if(user3!=null&&!user3.getIsdeleted()&&unitIdSet.contains(user3.getUnitid())){
								read++;
							}
						}
					}
					//read=redList.size();
				}
				officeBulletin.setRead(Integer.valueOf(read));
				int unRead=total-read;
				officeBulletin.setUnRead(unRead);
			}else{
				officeBulletin.setUnRead(total);
			}
			
			
			User user = usersMap.get(officeBulletin.getCreateUserId());
			if(user!=null){
				if(user.getIsdeleted()){
					officeBulletin.setCreateUserName(user.getRealname());
				}else{
					officeBulletin.setCreateUserName(user.getRealname());
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if(teacher!=null){
					    Dept dept = deptMap.get(teacher.getDeptid());
						if(dept!=null){
							officeBulletin.setDeptName(dept.getDeptname());
						}
					}
				}
			}else{
				officeBulletin.setCreateUserName("用户已删除");
			}
			User user2 = usersMap.get(officeBulletin.getAuditUserId());
			if(user2!=null){
				officeBulletin.setPublishUserName(user2.getRealname());
			}else{
				officeBulletin.setPublishUserName("用户已删除");
			}
			Unit bUnit = unitMap.get(officeBulletin.getUnitId());
			if(StringUtils.equals(bUnit.getUnitclass()+"", "1")){
				if("pingdu".equals(systemIniService.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL))){
					areaName = "教体局";
				}else{
					areaName = "教育局";
				}
			}else{
				if(BaseConstant.ZERO_GUID.equals(officeBulletin.getAreaId())){
					areaName = "全校";
				}else{
					if(areaMap.get(officeBulletin.getAreaId())!=null){
						areaName = areaMap.get(officeBulletin.getAreaId()).getAreaName();
					}else{
						areaName = "校区已删除";
					}
				}
			}
			officeBulletin.setAreaName(areaName);
			if(str.equals(dateFormat.format(officeBulletin.getCreateTime()))){
				officeBulletin.setIsNew(true);
			}
			if(StringUtils.isNotBlank(readMap.get(officeBulletin.getId()))){
				officeBulletin.setIsRead(true);
			}
		}
		return officeBulletinList;
	}
	
	@Override
	public List<ReadInfoDto> getOfficeBulletinReadInfoDto(String bulletinId,String[] types,String unitId) {//TODO
		boolean lala=false;
		for(String type:types){
			if(org.apache.commons.lang3.StringUtils.equals("2", type)){
				lala=true;
			}
		}
		Set<String> userIdAllSet=new HashSet<String>();//总人数
		Set<String> unitIdSet=new HashSet<String>();
		Unit unit = unitService.getUnit(unitId);
		
		//新疆定制领导活动每日报本单位只能见本单位发布信息
		if("xinjiang".equalsIgnoreCase(systemIniService.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL))&&lala){
			List<User> users=userService.getUsers(unitId);
			for (User user : users) {
				userIdAllSet.add(user.getId());
			}
			unitIdSet.add(unitId);
		}else{
			if(StringUtils.equals(unit.getUnitclass()+"", "1")){
				if(StringUtils.equals(unit.getParentid(), BaseConstant.ZERO_GUID)){
					List<User> users=userService.getUsers(unitId);
					for (User user : users) {
						userIdAllSet.add(user.getId());
					}
					unitIdSet.add(unitId);
				}else{
					//下属教育局 可以看到本单位和上级单位和顶级的
					String parentId = unit.getParentid();
					Unit unitTop = unitService.getTopEdu();
					String topId = unitTop.getId();
					//List<User> users=userService.getUsersByUnitIds(new String[]{topId,parentId,unitId});
					List<User> users=userService.getUsersByUnitIds(new String[]{unitId});
					for (User user : users) {
						userIdAllSet.add(user.getId());
					}
					//unitIdSet.add(topId);
					//unitIdSet.add(parentId);
					unitIdSet.add(unitId);
				}
			}else if(StringUtils.equals(unit.getUnitclass()+"", "2")){
				//学校
				List<User> users=userService.getUsers(unitId);
				for (User user : users) {
					userIdAllSet.add(user.getId());
				}
				unitIdSet.add(unitId);
			}
		}
		
		Map<String, List<OfficeBulletinRead>> bulletinReadMap=officeBulletinReadDao.getOfficeBulletinReadMap(new String[]{bulletinId});
		
		Set<String> readSet=new HashSet<String>();
		if(bulletinReadMap.containsKey(bulletinId)){//未读和已读人数
			List<OfficeBulletinRead> redList=bulletinReadMap.get(bulletinId);
			if(CollectionUtils.isNotEmpty(redList)){
				for (OfficeBulletinRead officeBulletinRead : redList) {
					readSet.add(officeBulletinRead.getUserId());
				}
			}
			//officeBulletin.setRead(Integer.valueOf(read));
			//int unRead=total-read;
			//officeBulletin.setUnRead(unRead);
		}
		
		Set<String> unReadSet=new HashSet<String>();
		if(CollectionUtils.isNotEmpty(readSet)){
			unReadSet.clear();
			unReadSet.addAll(userIdAllSet);
			System.out.println(unReadSet.size());
			unReadSet.removeAll(readSet);
			System.out.println(unReadSet.size());
		}else{
			unReadSet=userIdAllSet;
		}
		
		List<ReadInfoDto> readInfoDtos = new ArrayList<ReadInfoDto>();
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIdSet.toArray(new String[0]));
		Map<String,User> userMap=userService.getUserWithDelMap(unReadSet.toArray(new String[0]));
		
		Set<String> teacherIds = new HashSet<String>();
		for(User user:userMap.values()){
			teacherIds.add(user.getTeacherid());
		}
		
		Set<String> deptIds = new HashSet<String>();
		Map<String, Teacher> teacherMap = teacherService.getTeacherWithDeletedMap(teacherIds.toArray(new String[0]));
		for (Teacher teacher : teacherMap.values()) {
			deptIds.add(teacher.getDeptid());
		}
		
		//Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		
		
		for (Unit unit2 : unitMap.values()) {
			ReadInfoDto readInfoDto = new ReadInfoDto();
			List<String> userDeptNames = new ArrayList<String>();
			List<String> detailNames=new ArrayList<String>();
			//人员 
			List<String> useridList = new ArrayList<String>();
			for(User user:userMap.values()){
				if(unit2.getId().equals(user.getUnitid())&&!user.getIsdeleted()){
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if (teacher != null) {
						//Dept dept = deptMap.get(teacher.getDeptid());
						userDeptNames.add(//(dept == null ? "" : dept.getDeptname())+
								"<span>"
								+ teacher.getName()
								+ "</span>");
					}
					useridList.add(user.getId());
				}
			}
			readInfoDto.setUnitName(unit2.getName());
			readInfoDto.setUserDeptNameList(userDeptNames);
			readInfoDto.setUserIdList(useridList);
			readInfoDto.setDetailNames(detailNames);
			readInfoDtos.add(readInfoDto);
		}
		return readInfoDtos;
	}

	@Override
	public void sendMsg(String unitId, String senduserId, String reciveUserids,String bulletinId) {
		User user=userService.getUser(senduserId);
		Unit unit = unitService.getUnit(user.getUnitid());
		OfficeBulletin officeBulletin=officeBulletinDao.getOfficeBulletinById(bulletinId);
		String title=officeBulletin.getTitle();
		Integer msgType=BaseConstant.MSG_TYPE_BULLETIN;
		String content="您好，您有一条 《"+title+"》 还没有阅读，请点击详情了解。";
		String[] userIds={};
		if(org.apache.commons.lang3.StringUtils.isNotBlank(reciveUserids)){
			userIds=reciveUserids.split(",");
		}
		if(org.apache.commons.lang3.ArrayUtils.isNotEmpty(userIds)){
			String msgId=officeSubsystemService.sendMsgDetail(user, unit, title, content, content, false, msgType, userIds, null);
			officeBusinessJumpService.pushMsgUrl(officeBulletin, msgType, msgId);
		}
		
		//发送微课
		WKPushParm parm = new WKPushParm();
		String typeName=officeBulletin.getType();
		if(StringUtils.isNotBlank(typeName)){
			if(typeName.equals("1")){
				typeName="通知";
			}else if(typeName.equals("3")){ 
				typeName="公告";
			}else{
				return;
			}
		}else{
			return;
		}
		parm.setMsgTitle(typeName);
		parm.setHeadContent(officeBulletin.getTitle());
		parm.setBodyTitle(typeName);
		List<String> rows = new ArrayList<String>();
//		rows.add("通知内容:"+bulletin.getContent());
		rows.add(content);
		parm.setRowsContent(rows.toArray(new String[0]));
		String domain = RedisUtils.get("EIS.BASE.PATH.V6");
		if(StringUtils.isNotBlank(domain)){
			parm.setFootContent("详情");
			parm.setJumpType(WeikeAppConstant.JUMP_TYPE_0);
			if(user !=null){
				Unit unit2 = unitService.getUnit(user.getUnitid());
				if(unit2!=null){
					String url = WeikeAppUrlEnum.getWeikeUrl(WeikeAppConstant.BULLETIN, WeikeAppConstant.DETAILE_URL)+"&id="+officeBulletin.getId();
					parm.setUrl(domain + url);
				}
			}
		}
		if(org.apache.commons.lang3.ArrayUtils.isNotEmpty(userIds)){
			WeikePushClient.getInstance().pushMessage(WeikeAppConstant.BULLETIN, userIds, parm);
		}
	}

	@Override
	public List<OfficeBulletin> getOfficeBulletinListPage(String bulletinType,
			String unitId, String userId, String state, int tabClass, Pagination page) {
		Unit unit = unitService.getUnit(unitId);
		List<OfficeBulletin> officeBulletinList = officeBulletinDao.getOfficeBulletinListPage(bulletinType, unitId, unit.getParentid(), state, tabClass,page);
		String[] ids = new String[officeBulletinList.size()];
		int i=0;
		for(OfficeBulletin officeBulletin:officeBulletinList){
			ids[i] = officeBulletin.getId();
			i++;
		}
		Map<String, String> readMap = officeBulletinReadDao.getReadMapByBulletinIds(userId,ids);
		Map<String, TeachArea> areaMap = teachAreaService.getTeachAreaMap(unitId);
		String areaName = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = dateFormat.format(date);
		for(OfficeBulletin officeBulletin:officeBulletinList){
			if(BaseConstant.ZERO_GUID.equals(officeBulletin.getAreaId())){
				areaName = "全校";
			}else{
				if(areaMap.get(officeBulletin.getAreaId())!=null){
					areaName = areaMap.get(officeBulletin.getAreaId()).getAreaName();
				}else{
					areaName = "校区已删除";
				}
			}
			officeBulletin.setAreaName(areaName);
			if(str.equals(dateFormat.format(officeBulletin.getCreateTime()))){
				officeBulletin.setIsNew(true);
			}
			if(StringUtils.isNotBlank(readMap.get(officeBulletin.getId()))){
				officeBulletin.setIsRead(true);
			}
		}
		return officeBulletinList;
	}

	public void setOfficeBulletinDao(OfficeBulletinDao officeBulletinDao){
		this.officeBulletinDao = officeBulletinDao;
	}

	public OfficeBulletinDao getOfficeBulletinDao() {
		return officeBulletinDao;
	}

	public void setTeachAreaService(TeachAreaService teachAreaService) {
		this.teachAreaService = teachAreaService;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	public Integer unPublish(String bulletinId,String userId,String idea) {
		return officeBulletinDao.unPublish(bulletinId,userId,idea);
	}
	@Override
	public void top(String bulletinId) {
		officeBulletinDao.top(bulletinId);
	}
	@Override
	public void qxTop(String bulletinId) {
		officeBulletinDao.qxTop(bulletinId);
	}
	
	@Override
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId) {
		officeBulletinDao.saveOrder(bulletinIds,orderIds,userId);
		
	}

	@Override
	public void qxPublish(String bulletinId, String userId) {
		officeBulletinDao.qxPublish(bulletinId, userId);
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setOfficeBusinessJumpService(
			OfficeBusinessJumpService officeBusinessJumpService) {
		this.officeBusinessJumpService = officeBusinessJumpService;
	}

	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}
	
}	