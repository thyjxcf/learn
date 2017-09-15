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
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinReadDao;
import net.zdsoft.office.bulletin.dao.OfficeBulletinXjDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinXj;
import net.zdsoft.office.bulletin.service.OfficeBulletinXjService;

import org.apache.commons.lang.StringUtils;

/**
 * office_bulletin_xj
 * @author 
 * 
 */
public class OfficeBulletinXjServiceImpl implements OfficeBulletinXjService{
	private UserService userService;
	private DeptService deptService;
	private UnitService unitService;
	private TeacherService teacherService;
	private TeachAreaService teachAreaService;
	private OfficeBulletinXjDao officeBulletinXjDao;
	private OfficeBulletinReadDao officeBulletinReadDao;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeBulletinReadDao(OfficeBulletinReadDao officeBulletinReadDao) {
		this.officeBulletinReadDao = officeBulletinReadDao;
	}

	@Override
	public void save(OfficeBulletinXj officeBulletinXj){
		officeBulletinXjDao.save(officeBulletinXj);
	}
	
	@Override
	public Integer getLatestIssue(String unitId, String type) {
		return officeBulletinXjDao.getLatestIssue(unitId, type);
	}
	
	@Override
	public boolean isIssueExist(String unitId, String type, int issue, String bulletinId) {
		return officeBulletinXjDao.isIssueExist(unitId, type, issue, bulletinId);
	}
	
	@Override
	public Integer delete(String id,String userId){
		return officeBulletinXjDao.delete(id, userId);
	}

	@Override
	public Integer deleteIds(String[] ids,String userId){
		return officeBulletinXjDao.deleteIds(ids, userId);
	}
	
	@Override
	public Integer publish(String[] ids,String userId) {
		return officeBulletinXjDao.publish(ids,userId);
	}
	
	public Integer submit(String id){
		return officeBulletinXjDao.submit(id);
	}

	@Override
	public Integer update(OfficeBulletinXj officeBulletinXj){
		return officeBulletinXjDao.update(officeBulletinXj);
	}

	@Override
	public OfficeBulletinXj getOfficeBulletinXjById(String id){
		OfficeBulletinXj officeBulletinXj = officeBulletinXjDao.getOfficeBulletinXjById(id);
		User user = userService.getUserWithDel(officeBulletinXj.getCreateUserId());
		if(user != null){
			officeBulletinXj.setCreateUserName(user.getRealname());
			if(!user.getIsdeleted()){
				Teacher teacher = teacherService.getTeacherContainDelete(user.getTeacherid());
				if(teacher != null){
					Dept dept = deptService.getDept(teacher.getDeptid());
					if(dept != null){
						officeBulletinXj.setDeptName(dept.getDeptname());
					}
				}
			}else{
				officeBulletinXj.setDeptName("部门已删除");
			}
		}else{
			officeBulletinXj.setCreateUserName("用户已删除");
		}
		Map<String, String> clickMap = officeBulletinReadDao.getClickMapByBulletinIds(new String[]{id});
		if(clickMap!=null && StringUtils.isNotBlank(clickMap.get(id))){
			officeBulletinXj.setClickNum(clickMap.get(id));
		}else{
			officeBulletinXj.setClickNum("0");
		}
		return officeBulletinXj;
	}

	@Override
	public Map<String, OfficeBulletinXj> getOfficeBulletinXjMapByIds(String[] ids){
		return officeBulletinXjDao.getOfficeBulletinXjMapByIds(ids);
	}

	@Override
	public List<OfficeBulletinXj> getOfficeBulletinXjList(){
		return officeBulletinXjDao.getOfficeBulletinXjList();
	}

	@Override
	public List<OfficeBulletinXj> getOfficeBulletinXjPage(String unitId, String isReadUserId, String userId,String bulletinType,String state,String startTime,String endTime, String publishName, String searName, String searchAreaId, Pagination page){
		Set<String>userIdsSet1=new HashSet<String>();
		List<OfficeBulletinXj> officeBulletinXjList;
		if(StringUtils.isNotBlank(publishName)){
			List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
			for (User user : usersFaintness) {
				userIdsSet1.add(user.getId());
			}
			officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjPage(unitId,userId,bulletinType,state,startTime,endTime, searName, userIdsSet1.toArray(new String[0]), searchAreaId, page);
		}else{
			officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjPage(unitId,userId,bulletinType,state,startTime,endTime, searName, null, searchAreaId, page);
		}
		String[] ids = new String[officeBulletinXjList.size()];
		int i=0;
		Set<String>userIdsSet=new HashSet<String>();
		for(OfficeBulletinXj officeBulletinXj:officeBulletinXjList){
			userIdsSet.add(officeBulletinXj.getCreateUserId());
			if(StringUtils.isNotBlank(officeBulletinXj.getAuditUserId())){
				userIdsSet.add(officeBulletinXj.getAuditUserId());
			}
			ids[i] = officeBulletinXj.getId();
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
		for(OfficeBulletinXj officeBulletinXj:officeBulletinXjList){
			User user = usersMap.get(officeBulletinXj.getCreateUserId());
			if(user!=null){
				if(user.getIsdeleted()){
					officeBulletinXj.setCreateUserName(user.getRealname());
				}else{
					officeBulletinXj.setCreateUserName(user.getRealname());
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if(teacher!=null){
						Dept dept = deptMap.get(teacher.getDeptid());
						if(dept!=null){
							officeBulletinXj.setDeptName(dept.getDeptname());
						}
					}
				}
			}else{
				officeBulletinXj.setCreateUserName("用户已删除");
			}
			User user2 = usersMap.get(officeBulletinXj.getAuditUserId());
			if(user2!=null){
				officeBulletinXj.setPublishUserName(user2.getRealname());
			}else{
				officeBulletinXj.setPublishUserName("用户已删除");
			}
			if(BaseConstant.ZERO_GUID.equals(officeBulletinXj.getAreaId())){
				areaName = "全校";
			}else{
				if(areaMap.get(officeBulletinXj.getAreaId())!=null){
					areaName = areaMap.get(officeBulletinXj.getAreaId()).getAreaName();
				}else{
					areaName = "校区已删除";
				}
			}
			officeBulletinXj.setAreaName(areaName);
			if(str.equals(dateFormat.format(officeBulletinXj.getCreateTime()))){
				officeBulletinXj.setIsNew(true);
			}
			if(StringUtils.isNotBlank(readMap.get(officeBulletinXj.getId()))){
				officeBulletinXj.setIsRead(true);
			}
			//点击量
			if(StringUtils.isNotBlank(clickMap.get(officeBulletinXj.getId()))){
				officeBulletinXj.setClickNum(clickMap.get(officeBulletinXj.getId()));
			}else{
				officeBulletinXj.setClickNum("0");
			}
		}
		return officeBulletinXjList;
	}
	
	public List<OfficeBulletinXj> getOfficeBulletinXjListPage(String type,String unitId, String userId, String areaId, String state, String startTime, String endTime, String searName, String publishName, Pagination page){
		//TODO
		List<OfficeBulletinXj> officeBulletinXjList;
		Set<String> userIdsSet1 = new HashSet<String>();
		Unit unit = unitService.getUnit(unitId);
		
		if(StringUtils.equals(unit.getUnitclass()+"", "1")){
			if(StringUtils.equals(unit.getParentid(), BaseConstant.ZERO_GUID)){
				//顶级教育局  只看到本单位发布的
				if(StringUtils.isNotBlank(publishName)){
					List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
					for (User user : usersFaintness) {
						userIdsSet1.add(user.getId());
					}
					officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjListPage(type,unitId, areaId, state, startTime, endTime, searName, userIdsSet1.toArray(new String[0]), page);
				}else{
					officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjListPage(type,unitId, areaId, state, startTime, endTime, searName, null, page);
				}
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
					officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjListPage1(type,unitId, areaId, state, startTime, endTime, searName, userIdsSet1.toArray(new String[0]), topId, parentId, page);
				}else{
					officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjListPage1(type,unitId, areaId, state, startTime, endTime, searName, null, topId, parentId,page);
				}
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
				officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjListPage1(type,unitId, areaId, state, startTime, endTime, searName, userIdsSet1.toArray(new String[0]),topId, parentId,page);
			}else{
				officeBulletinXjList = officeBulletinXjDao.getOfficeBulletinXjListPage1(type,unitId, areaId, state, startTime, endTime, searName, null,topId, parentId,page);
			}
		}else{
			officeBulletinXjList = new ArrayList<OfficeBulletinXj>();
		}
		
		String[] ids = new String[officeBulletinXjList.size()];
		int i=0;
		Set<String> userIdsSet = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		for(OfficeBulletinXj officeBulletinXj:officeBulletinXjList){
			if(StringUtils.isNotBlank(officeBulletinXj.getAuditUserId())){
				userIdsSet.add(officeBulletinXj.getAuditUserId());
				userIdsSet.add(officeBulletinXj.getCreateUserId());
				unitIds.add(officeBulletinXj.getUnitId());
			}
			ids[i] = officeBulletinXj.getId();
			i++;
		}
		Map<String, String> readMap = officeBulletinReadDao.getReadMapByBulletinIds(userId,ids);
		Map<String, User> usersMap = userService.getUserWithDelMap(userIdsSet.toArray(new String[0]));
		Map<String, TeachArea> areaMap = teachAreaService.getTeachAreaMap(unitId);
//		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));

//		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
//		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(unitId);
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
		for(OfficeBulletinXj officeBulletinXj:officeBulletinXjList){
			User user = usersMap.get(officeBulletinXj.getCreateUserId());
			if(user!=null){
				if(user.getIsdeleted()){
					officeBulletinXj.setCreateUserName(user.getRealname());
				}else{
					officeBulletinXj.setCreateUserName(user.getRealname());
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if(teacher!=null){
					    Dept dept = deptMap.get(teacher.getDeptid());
						if(dept!=null){
							officeBulletinXj.setDeptName(dept.getDeptname());
						}
					}
				}
			}else{
				officeBulletinXj.setCreateUserName("用户已删除");
			}
			User user2 = usersMap.get(officeBulletinXj.getAuditUserId());
			if(user2!=null){
				officeBulletinXj.setPublishUserName(user2.getRealname());
			}else{
				officeBulletinXj.setPublishUserName("用户已删除");
			}
			if(BaseConstant.ZERO_GUID.equals(officeBulletinXj.getAreaId())){
				areaName = "全校";
			}else{
				if(areaMap.get(officeBulletinXj.getAreaId())!=null){
					areaName = areaMap.get(officeBulletinXj.getAreaId()).getAreaName();
				}else{
					areaName = "校区已删除";
				}
			}
			officeBulletinXj.setAreaName(areaName);
			if(str.equals(dateFormat.format(officeBulletinXj.getCreateTime()))){
				officeBulletinXj.setIsNew(true);
			}
			if(StringUtils.isNotBlank(readMap.get(officeBulletinXj.getId()))){
				officeBulletinXj.setIsRead(true);
			}
		}
		return officeBulletinXjList;
	}

	public void setOfficeBulletinXjDao(OfficeBulletinXjDao officeBulletinXjDao){
		this.officeBulletinXjDao = officeBulletinXjDao;
	}

	public OfficeBulletinXjDao getOfficeBulletinXjDao() {
		return officeBulletinXjDao;
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
		return officeBulletinXjDao.unPublish(bulletinId,userId,idea);
	}
	@Override
	public void top(String bulletinId) {
		officeBulletinXjDao.top(bulletinId);
	}
	@Override
	public void qxTop(String bulletinId) {
		officeBulletinXjDao.qxTop(bulletinId);
	}
	
	@Override
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId) {
		officeBulletinXjDao.saveOrder(bulletinIds,orderIds,userId);
		
	}

	@Override
	public void qxPublish(String bulletinId, String userId) {
		officeBulletinXjDao.qxPublish(bulletinId, userId);
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	@Override
	public List<OfficeBulletinXj> getOfficeBulletinXjListByIds(String[] ids) {
		return officeBulletinXjDao.getOfficeBulletinXjListByIds(ids);
	}
	
}	