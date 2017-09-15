package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.dao.UserDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.RemoteApp;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.TeacherDuty;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.base.common.service.BaseRemoteApCodeService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.TeacherDutyService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.eis.base.simple.service.SimpleStudentService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author luxingmu
 * @version $Revision: 1.70 $, $Date: 2007/02/05 10:11:27 $
 * 
 */
public class UserServiceImpl extends DefaultCacheManager implements UserService {
    private Logger log = Logger.getLogger(UserServiceImpl.class);
            
    private TeacherService teacherService;
    private TeacherDutyService teacherDutyService;
    private SimpleStudentService simpleStudentService;
    private StudentFamilyService studentFamilyService;
    private UserDao userDao;
    
    private DeptService deptService;
    private UnitService unitService;
    private UserSetService userSetService;
    private StorageDirService storageDirService;
    private McodedetailService mcodedetailService;
    
    @Resource
    private BaseRemoteApCodeService baseRemoteApCodeService;
    
    @Resource
    private SystemIniService systemIniService;
    
	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}

	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setTeacherDutyService(TeacherDutyService teacherDutyService) {
		this.teacherDutyService = teacherDutyService;
	}

	public void setSimpleStudentService(
			SimpleStudentService simpleStudentService) {
		this.simpleStudentService = simpleStudentService;
	}

	public void setStudentFamilyService(
			StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	// =====================单个用户=============================
	public User getUser(String userId) {
		return userDao.getUser(userId);
	}
	
	public User getUserWithDel(String userId){
		User user = userDao.getUserWithDel(userId);
		if (user !=null && user.getIsdeleted()) {
			user.setRealname(user.getRealname() + "(已删除)");
		}
		return user;
	}
	public User getUserByUserName(String loginName) {
		return userDao.getUserByUserName(loginName);
	}
	
	public List<User> getUserByMobilePhone(String mobilePhone, int ownerType) {
		return userDao.getUserByMobilePhone(mobilePhone, ownerType);
	}

	public User getTopUser() {
		return userDao.getTopUser();
	}

	public User getUnitAdmin(String unitId) {
		return userDao.getUnitAdmin(unitId);
	}

	public User getUserByAccountId(String accountId) {
		return userDao.getUserByAccountId(accountId);
	}

	public User getUserNew(String unitId) {
		User user = new User();
		user.setOrderid(userDao.getAvailableOrder(unitId));
		user.setUnitid(unitId);
		return user;
	}

	// =====================数值=============================
	public Integer[] getUserCount(String[] teacherIds) {
		return userDao.getUserCount(teacherIds);
	}

    public String getDeptIdByUserId(String userId) {
        User user = userDao.getUser(userId);
        if(user == null){
        	return null;
        }
        Teacher teacher = teacherService.getTeacher(user.getTeacherid());
        if(teacher == null){
        	return null;
        }
        return teacher.getDeptid();
    }

	public Integer getUserNameCount(String userName) {
		return userDao.getUserNameCount(userName);
	}

	public Integer getCommonUserCount(String unitId) {
		return userDao.getCommonUserCount(unitId);
	}

	public Long getAvailableOrder(String unitId) {
		return userDao.getAvailableOrder(unitId);
	}

	// =====================列表=============================
	public List<User> getUsers(String unitId) {
		return userDao.getUsers(unitId, User.TEACHER_LOGIN);
	}

	public List<User> getUsers(String unitId, Pagination page) {
		return userDao.getUsers(unitId, User.TEACHER_LOGIN, page);
	}
	
	@Override
	public List<User> getUsersByUnitId(String unitId) {
		List<Teacher> teacherList = teacherService.getTeachers(unitId);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		Unit unit = unitService.getUnit(unitId);
		List<String> teaIds = new ArrayList<String>();
		Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			teacherMap.put(teacher.getId(), teacher);
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]));
		for(User user:users){
			user.setUnitName(unit.getName());
			user.setDeptName(teacherMap.get(user.getTeacherid()).getDeptName());
			user.setMobilePhone(teacherMap.get(user.getTeacherid()).getPersonTel());
		}
		setUserPhoto(users);
		return users;
	}
	@Override
	public List<User> getUsersByDeptId(String deptId) {
		List<Teacher> teacherList = teacherService.getTeachersByDeptId(deptId);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		Dept dept = deptService.getDept(deptId);
		Unit unit = unitService.getUnit(dept.getUnitId());
		List<String> teaIds = new ArrayList<String>();
		Map<String, String> mobileMap = new HashMap<String, String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			mobileMap.put(teacher.getId(), teacher.getPersonTel());
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]));
		for(User user:users){
			user.setUnitName(unit.getName());
			user.setDeptName(dept.getDeptname());
			user.setMobilePhone(mobileMap.get(user.getTeacherid()));
		}
		setUserPhoto(users);
		return users;
	}
	
	@Override
	public List<User> getUsersByDeptIdSimple(String deptId) {
		List<Teacher> teacherList = teacherService.getTeachersByDeptId(deptId);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		List<String> teaIds = new ArrayList<String>();
		Map<String, String> mobileMap = new HashMap<String, String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			mobileMap.put(teacher.getId(), teacher.getPersonTel());
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]));
		for(User user:users){
			user.setMobilePhone(mobileMap.get(user.getTeacherid()));
		}
		return users;
	}
	
	@Override
	public List<User> getUsersByDeptId(String deptId, Pagination page) {
		List<Teacher> teacherList = teacherService.getTeachersByDeptId(deptId);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		Dept dept = deptService.getDept(deptId);
		Unit unit = unitService.getUnit(dept.getUnitId());
		List<String> teaIds = new ArrayList<String>();
		Map<String, String> mobileMap = new HashMap<String, String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			mobileMap.put(teacher.getId(), teacher.getPersonTel());
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]), page);
		for(User user:users){
			user.setUnitName(unit.getName());
			user.setDeptName(dept.getDeptname());
			user.setMobilePhone(mobileMap.get(user.getTeacherid()));
		}
		setUserPhoto(users);
		return users;
	}
	
	@Override
	public List<User> getLeaderUsersByDeptId(Integer unitClass, String deptId) {
		List<Teacher> teacherList = teacherService.getTeachersByDeptId(deptId);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		List<String> teaIds = new ArrayList<String>();
		Map<String, String> mobileMap = new HashMap<String, String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			mobileMap.put(teacher.getId(), teacher.getPersonTel());
		}
		List<TeacherDuty> teacherDutyList = teacherDutyService.getTeacherDutyListByTeacherIds(teaIds.toArray(new String[0]));
		//没有职务
		if (CollectionUtils.isEmpty(teacherDutyList))
			return new ArrayList<User>();
		Dept dept = deptService.getDept(deptId);
		Unit unit = unitService.getUnit(dept.getUnitId());
		String MCODE_ID_OF_DUTY_TYPE_EDU = "DM-JYJZW";// 教育局职务
		String MCODE_ID_OF_DUTY_TYPE_SCHOOL = "DM-XXZW";// 学校职务
		Map<String, Mcodedetail> mcodedetailMap = new HashMap<String, Mcodedetail>();
		if(unitClass == Unit.UNIT_CLASS_EDU){
			mcodedetailMap = mcodedetailService.getMcodeDetailMap(MCODE_ID_OF_DUTY_TYPE_EDU);
		}else if(unitClass == Unit.UNIT_CLASS_SCHOOL){
			mcodedetailMap = mcodedetailService.getMcodeDetailMap(MCODE_ID_OF_DUTY_TYPE_SCHOOL);
		}
		Map<String, String> dutyNamesMap = new HashMap<String, String>();
		for(TeacherDuty duty:teacherDutyList){
			if(mcodedetailMap.get(duty.getDutyCode())!=null){
				String teacherId = duty.getTeacherId();
				String dutyNames = dutyNamesMap.get(teacherId);
				if(StringUtils.isNotBlank(dutyNames)){
					dutyNames = dutyNames+","+mcodedetailMap.get(duty.getDutyCode()).getContent();
				}else{
					dutyNames = mcodedetailMap.get(duty.getDutyCode()).getContent();
				}
				dutyNamesMap.put(teacherId,dutyNames);
			}
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				dutyNamesMap.keySet().toArray(new String[0]));
		for(User user:users){
			user.setUnitName(unit.getName());
			user.setDeptName(dept.getDeptname());
			user.setDutyNames(dutyNamesMap.get(user.getTeacherid()));
		}
		return users;
	}
	
	@Override
	public List<User> getUsersByDutyId(String unitId, String dutyId) {
		List<Teacher> teacherList = teacherService.getTeachers(unitId);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		List<String> teaIds = new ArrayList<String>();
		Unit unit = unitService.getUnit(unitId);
		Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			teacherMap.put(teacher.getId(), teacher);
		}
		List<TeacherDuty> teacherDutyList = teacherDutyService.getTeacherDutyListByTeacherIds(dutyId, teaIds.toArray(new String[0]));
		if (CollectionUtils.isEmpty(teacherDutyList))
			return new ArrayList<User>();
		teaIds = new ArrayList<String>();
		for (int i = 0; i < teacherDutyList.size(); i++) {
			TeacherDuty teacherDuty = teacherDutyList.get(i);
			teaIds.add(teacherDuty.getTeacherId());
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]));
		for(User user:users){
			user.setUnitName(unit.getName());
			user.setDeptName(teacherMap.get(user.getTeacherid()).getDeptName());
			user.setMobilePhone(teacherMap.get(user.getTeacherid()).getPersonTel());
		}
		setUserPhoto(users);
		return users;
	}
	
	@Override
	public List<User> getUsersByDutyId(String unitId, String dutyId,
			Pagination page) {
		List<Teacher> teacherList = teacherService.getTeachers(unitId);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		List<String> teaIds = new ArrayList<String>();
		Unit unit = unitService.getUnit(unitId);
		Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			teacherMap.put(teacher.getId(), teacher);
		}
		List<TeacherDuty> teacherDutyList = teacherDutyService.getTeacherDutyListByTeacherIds(dutyId, teaIds.toArray(new String[0]));
		if (CollectionUtils.isEmpty(teacherDutyList))
			return new ArrayList<User>();
		teaIds = new ArrayList<String>();
		for (int i = 0; i < teacherDutyList.size(); i++) {
			TeacherDuty teacherDuty = teacherDutyList.get(i);
			teaIds.add(teacherDuty.getTeacherId());
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]), page);
		List<User> sers=new ArrayList<User>();
		for(User user:users){
			if(StringUtils.equals("1", user.getMark()+"")){
			user.setUnitName(unit.getName());
			user.setDeptName(teacherMap.get(user.getTeacherid()).getDeptName());
			user.setMobilePhone(teacherMap.get(user.getTeacherid()).getPersonTel());
			}else sers.add(user);
		}
		users.removeAll(sers);
		setUserPhoto(users);
		return users;
	}
	
	public void setUserPhoto(List<User> users){
		List<String> userIds = new ArrayList<String>();
		for(User user:users){
			userIds.add(user.getId());
		}
		Map<String, UserSet> userSetMap = userSetService.getUserSetMapByUserIds(userIds.toArray(new String[0]));
		if(userSetMap.size() > 0){
			for(User user:users){
				UserSet userSet = userSetMap.get(user.getId());
				if (userSet != null) {
					userSet.setDirPath(storageDirService.getDir(userSet.getDirId()));
				} else {
					userSet = new UserSet();
				}
				user.setUserSet(userSet);
			}
		}
	}

	public List<User> getUsers(String unitId, String[] marks) {
		return userDao.getUsers(unitId, marks);
	}

	public List<User> getUsers(String unitId, String deptId) {
		List<Teacher> teacherList = teacherService.getTeachersByDeptId(deptId);
		if (CollectionUtils.isEmpty(teacherList))
			return null;

		List<String> teaIds = new ArrayList<String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]));
		List<User> commonUsers = new ArrayList<User>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (User.USER_TYPE_COMMON == user.getType()) {
				commonUsers.add(user);
			}
		}
		return commonUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.zdsoft.eis.base.common.service.UserService#getUsers(java.lang.String
	 * [], java.lang.String)
	 */
	@Override
	public List<User> getUsers(String[] marks, String deptId) {
		// TODO
		List<Teacher> teacherList = teacherService.getTeachersByDeptId(deptId);
		if (CollectionUtils.isEmpty(teacherList))
			return null;

		List<String> teaIds = new ArrayList<String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
		}
		List<User> users = getUsersByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]), marks);
		List<User> commonUsers = new ArrayList<User>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (User.USER_TYPE_COMMON == user.getType()) {
				commonUsers.add(user);
			}
		}
		return commonUsers;
	}

	public List<User> getUsersByGroupId(int ownerType, String groupId,
			boolean isContainAdmin) {
		List<User> users = new ArrayList<User>();

		Set<String> ownerIds = new HashSet<String>();

		// 根据部门或班级id取角色信息
		switch (ownerType) {
		case User.STUDENT_LOGIN:
		case User.FAMILY_LOGIN:
			List<SimpleStudent> students = simpleStudentService
					.getStudents(groupId);
			for (SimpleStudent e : students) {
				ownerIds.add(e.getId());
			}

			if (ownerType == User.FAMILY_LOGIN) {
				if (ownerIds.size() > 0) {
					List<Family> families = studentFamilyService
							.getFamiliesByStudentId(ownerIds
									.toArray(new String[0]));
					ownerIds.clear();// 清空学生的ownerId

					for (Family e : families) {
						ownerIds.add(e.getId());
					}
				}

			}

			break;
		case User.TEACHER_LOGIN:
			List<Teacher> teachers = teacherService
					.getTeachersByDeptId(groupId);
			for (Teacher e : teachers) {
				ownerIds.add(e.getId());
			}
			break;

		default:
			break;
		}

		if (ownerIds.size() > 0) {
			// 取角色对应的用户信息
			if (isContainAdmin) {
				users = getUsersByOwner(ownerType,
						ownerIds.toArray(new String[0]));
			} else {
				users = userDao.getUsersByOwnerWithoutAdmin(ownerType,
						ownerIds.toArray(new String[0]));
			}

		}
		return users;
	}

	public List<User> getUsersByUnitAndType(String unitId, Integer type) {
		return userDao.getUsersByUnitAndType(unitId, type);
	}

    public List<User> getUsersByFaintness(String unitId, String realName){
        return userDao.getUsersByFaintness(unitId, realName);
    }
    
    public List<User> getUsers(String[] userIds) {
        return userDao.getUsers(userIds);
    }

	public List<User> getUsersWithDel(String[] userIds) {
		List<User> list = userDao.getUsersWithDel(userIds);
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			if (user.getIsdeleted()) {
				user.setRealname(user.getRealname() + "(已删除)");
				list.set(i, user);
			}
		}
		return list;
	}

	public List<User> getUsersByOwner(String teacherId) {
		return userDao.getUsersByOwner(teacherId);
	}

	public List<User> getUsersByOwner(int ownerType, String[] ownerIds) {
		return userDao.getUsersByOwner(ownerType, ownerIds);
	}
	public List<User> getUsersByOwner(int ownerType, String[] ownerIds, Pagination page) {
		return userDao.getUsersByOwner(ownerType, ownerIds, page);
	}

	public List<User> getUsersByOwner(int ownerType, String[] ownerIds,
			String[] marks) {
		return userDao.getUsersByOwner(ownerType, ownerIds, marks);
	}

	public Map<String, User> getUsersMapByName(String[] userNames) {
		return userDao.getUsersMapByName(userNames);
	}

	public Map<String, User> getUserMap(String unitId) {
		return userDao.getUserMap(unitId, User.TEACHER_LOGIN);
	}

	public Map<String, User> getUserMapByOwner(int ownerType, String[] ownerIds) {
		return userDao.getUserMapByOwner(ownerType, ownerIds);
	}

	public Map<String, User> getAdminUserMap(String[] unitIds) {
		return userDao.getAdminUserMap(unitIds);
	}

    public Map<String, User> getUsersMap(String[] userIds) {
        return userDao.getUserMap(userIds);
    }
    
    public Map<String, User> getUserWithDelMap(String[] userIds){
    	 List<User> list = userDao.getUsersWithDel(userIds);
    	 Map<String, User> map=new HashMap<String, User>();
         for (int i = 0; i < list.size(); i++) {
             User user = list.get(i);
             if (user.getIsdeleted()) {
                 user.setRealname(user.getRealname() + "(已删除)");
             }
             map.put(user.getId(), user);
         }
         return map;
    }
    
    @Override
    public Map<String, User> getUserWithDelMap(String unitId) {
    	List<User> list = userDao.getUsersWithDel(unitId);
   	 Map<String, User> map=new HashMap<String, User>();
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getIsdeleted()) {
                user.setRealname(user.getRealname() + "(已删除)");
            }
            map.put(user.getId(), user);
        }
        return map;
    }

	public List<User> getUsersFaintness(String realName, String unitId) {
		return userDao.getUsersFaintness(realName, unitId);
	}
	
	public List<User> getUserListByUnitId(String unitId, String realName, int ownerType, Pagination page){
		return userDao.getUserListByUnitId(unitId, realName, ownerType, page);
	}
	
	@Override
	public Map<String, List<User>> getDeptUsersMap(String unitId) {
		Unit unit = unitService.getUnit(unitId);
		Map<String, User> userMap = userDao.getTeacherUserMap(unitId,User.TEACHER_LOGIN);
		List<Dept> depts = deptService.getDepts(unitId);
		List<Teacher> teachers = teacherService.getTeachers(unitId);
		Map<String, List<User>> deptUsersMap = new HashMap<String, List<User>>();
		for(Dept dept:depts){
			List<User> users = new ArrayList<User>();
			for(Teacher teacher:teachers){
				if(dept.getId().equals(teacher.getDeptid())){
					if(userMap.get(teacher.getId())!=null){
						User user = userMap.get(teacher.getId());
						user.setUnitName(unit.getName());
						user.setDeptName(dept.getDeptname());
						users.add(user);
					}
				}
			}
			deptUsersMap.put(dept.getId(), users);
		}
		return deptUsersMap;
	}
	
	@Override
	public String getUserDetailNamesStr(String[] ids) {
		List<User> users = userDao.getUsersWithDel(ids);
		Map<String, User> userMap = new HashMap<String, User>();
		for(User user:users){
			userMap.put(user.getId(), user);
		}
		Set<String> teacherIds = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for(User user:users){
			teacherIds.add(user.getTeacherid());
			unitIds.add(user.getUnitid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherWithDeletedMap(teacherIds.toArray(new String[0]));
		for(Teacher teacher:teacherMap.values()){
			deptIds.add(teacher.getDeptid());
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		StringBuffer sbf = new StringBuffer();
		int i = 0;
		for(String id:ids){
			User user = userMap.get(id);
			if(user != null){
				Unit unit = unitMap.get(user.getUnitid());
				if(unit!=null){
					user.setUnitName(unit.getName());
				}
				Teacher teacher = teacherMap.get(user.getTeacherid());
				if(teacher!=null){
					Dept dept = deptMap.get(teacher.getDeptid());
					if(dept!=null){
						user.setDeptName(dept.getDeptname());
					}
				}
				if(user.getIsdeleted()){
					if(i== 0){
						sbf.append(user.getRealname()+"(已删除)");
					}else{
						sbf.append(","+user.getRealname()+"(已删除)");
					}
				}else{
					if(i== 0){
						sbf.append(user.getRealname()+"("+user.getUnitName()+"-"+user.getDeptName()+")");
					}else{
						sbf.append(","+user.getRealname()+"("+user.getUnitName()+"-"+user.getDeptName()+")");
					}
				}
			}else{
				sbf.append("用户已彻底删除)");
			}
			i++;
		}
		return sbf.toString();
	}
	@Override
	public Map<String,String> getUserDetailNamesMap(String[] ids) {
		List<User> users = userDao.getUsersWithDel(ids);
		Map<String, User> userMap = new HashMap<String, User>();
		for(User user:users){
			userMap.put(user.getId(), user);
		}
		Set<String> teacherIds = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for(User user:users){
			teacherIds.add(user.getTeacherid());
			unitIds.add(user.getUnitid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherWithDeletedMap(teacherIds.toArray(new String[0]));
		for(Teacher teacher:teacherMap.values()){
			deptIds.add(teacher.getDeptid());
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		Map<String,String> detailNameMap=new HashMap<String, String>();
		StringBuffer sbf=null;
		for(String id:ids){
			sbf=new StringBuffer();
			User user = userMap.get(id);
			if(user != null){
				Unit unit = unitMap.get(user.getUnitid());
				if(unit!=null){
					user.setUnitName(unit.getName());
				}
				Teacher teacher = teacherMap.get(user.getTeacherid());
				if(teacher!=null){
					Dept dept = deptMap.get(teacher.getDeptid());
					if(dept!=null){
						user.setDeptName(dept.getDeptname());
					}
				}
				if(user.getIsdeleted()){
					sbf.append(user.getRealname()+"(已删除)");
				}else{
					sbf.append(user.getRealname()+"("+user.getUnitName()+"-"+user.getDeptName()+")");
				}
			}else{
				sbf.append("用户已彻底删除)");
			}
			detailNameMap.put(id, sbf.toString());
		}
		return detailNameMap;
	}
	
	@Override
	public List<User> getUsersBySearchName(String searchName, Pagination page) {
		List<User> users = userDao.getUsersBySearchName(searchName,page);
		setUserOtherInfos(users);
		return users;
	}
	
	public void setUserOtherInfos(List<User> users){
		Set<String> teacherIds = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for(User user:users){
			teacherIds.add(user.getTeacherid());
			unitIds.add(user.getUnitid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
		for(Teacher teacher:teacherMap.values()){
			deptIds.add(teacher.getDeptid());
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		for(User user:users){
			Unit unit = unitMap.get(user.getUnitid());
			if(unit!=null){
				user.setUnitName(unit.getName());
			}
			Teacher teacher = teacherMap.get(user.getTeacherid());
			if(teacher!=null){
				user.setMobilePhone(teacher.getPersonTel());
				Dept dept = deptMap.get(teacher.getDeptid());
				if(dept!=null){
					user.setDeptName(dept.getDeptname());
				}
			}
		}
	}
	
	@Override
	public List<User> getUsersWithOutPinYin() {
		return userDao.getUsersWithOutPinYin();
	}
	
	@Override
	public List<User> getUsersBySearchName(String unitId, String searchName, Pagination page) {
		List<User> users = userDao.getUsersBySearchName(unitId, searchName, page);
		setUserOtherInfos(users);
		return users;
	}

	@Override
	public List<User> getUsersByRealNameMobile(String realName, String mobilePhone, int ownerType) {
		return userDao.getUsersByRealNameMobile(realName, mobilePhone, ownerType);
	}

	@Override
	public List<User> getUsersByDeptIds(String[] deptIds) {
		List<Teacher> teacherList = teacherService.getTeachersByDeptIds(deptIds);
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		
		Map<String,Teacher> teacherMap = new HashMap<String, Teacher>();
		for (Teacher teacher : teacherList) {
			teacherMap.put(teacher.getId(), teacher);
		}
		
		Map<String,Dept> deptMap = deptService.getDeptMap(deptIds);
		Set<String> unidSet = new HashSet<String>();
		for (String deptId : deptIds) {
			Dept dept = deptMap.get(deptId);
			if(dept!=null){
				unidSet.add(dept.getUnitId());
			}
		}
		Map<String,Unit> unitMap =  unitService.getUnitMap(unidSet.toArray(new String[0]));
		List<String> teaIds = new ArrayList<String>();
		Map<String, String> mobileMap = new HashMap<String, String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			mobileMap.put(teacher.getId(), teacher.getPersonTel());
		}
		Map<String,User> userMap = getUserMapByOwner(User.TEACHER_LOGIN,
				teaIds.toArray(new String[teaIds.size()]));
		List<User> users = new ArrayList<User>();
		for(Teacher teacher:teacherList){
			User user = userMap.get(teacher.getId());
			if(user!=null){
				Unit unit = unitMap.get(user.getUnitid());
				if(unit!=null){
					user.setUnitName(unit.getName());
				}
				Dept dept = deptMap.get(teacher.getDeptid());
				if(dept!=null){
					user.setDeptName(dept.getDeptname());
				}
				user.setMobilePhone(mobileMap.get(user.getTeacherid()));
				users.add(user);
			}
		}
		//setUserPhoto(users);
		return users;
	}

	@Override
	public List<User> getLeaderUsersByDeptIds(Integer unitClass,
			String[] deptLeaderIds) {
		List<String> newDeptIds = new ArrayList<String>();
		for (String deptLeaderId : deptLeaderIds) {
			newDeptIds.add(deptLeaderId.substring(0, deptLeaderId.length()-2));
		}
		List<Teacher> teacherList = teacherService.getTeachersByDeptIds(newDeptIds.toArray(new String[0]));
		if (CollectionUtils.isEmpty(teacherList))
			return new ArrayList<User>();
		
		List<String> teaIds = new ArrayList<String>();
		Map<String, String> mobileMap = new HashMap<String, String>();
		for (int i = 0; i < teacherList.size(); i++) {
			Teacher teacher = teacherList.get(i);
			teaIds.add(teacher.getId());
			mobileMap.put(teacher.getId(), teacher.getPersonTel());
		}
		List<TeacherDuty> teacherDutyList = teacherDutyService.getTeacherDutyListByTeacherIds(teaIds.toArray(new String[0]));
		//没有职务
		if (CollectionUtils.isEmpty(teacherDutyList))
			return new ArrayList<User>();
		
		String MCODE_ID_OF_DUTY_TYPE_EDU = "DM-JYJZW";// 教育局职务
		String MCODE_ID_OF_DUTY_TYPE_SCHOOL = "DM-XXZW";// 学校职务
		Map<String, Mcodedetail> mcodedetailMap = new HashMap<String, Mcodedetail>();
		if(unitClass == Unit.UNIT_CLASS_EDU){
			mcodedetailMap = mcodedetailService.getMcodeDetailMap(MCODE_ID_OF_DUTY_TYPE_EDU);
		}else if(unitClass == Unit.UNIT_CLASS_SCHOOL){
			mcodedetailMap = mcodedetailService.getMcodeDetailMap(MCODE_ID_OF_DUTY_TYPE_SCHOOL);
		}
		Map<String, String> dutyNamesMap = new HashMap<String, String>();
		for(TeacherDuty duty:teacherDutyList){
			if(mcodedetailMap.get(duty.getDutyCode())!=null){
				String teacherId = duty.getTeacherId();
				String dutyNames = dutyNamesMap.get(teacherId);
				if(StringUtils.isNotBlank(dutyNames)){
					dutyNames = dutyNames+","+mcodedetailMap.get(duty.getDutyCode()).getContent();
				}else{
					dutyNames = mcodedetailMap.get(duty.getDutyCode()).getContent();
				}
				dutyNamesMap.put(teacherId,dutyNames);
			}
		}
		
		Map<String,Dept> deptMap = deptService.getDeptMap(newDeptIds.toArray(new String[0]));
		Set<String> unidSet = new HashSet<String>();
		for (String deptId : newDeptIds) {
			Dept dept = deptMap.get(deptId);
			if(dept!=null){
				unidSet.add(dept.getUnitId());
			}
		}
		Map<String,Unit> unitMap =  unitService.getUnitMap(unidSet.toArray(new String[0]));
		
		Map<String,User> userMap = getUserMapByOwner(User.TEACHER_LOGIN,
				dutyNamesMap.keySet().toArray(new String[0]));
		List<User> users = new ArrayList<User>();
		for(Teacher teacher:teacherList){
			String dutyNames = dutyNamesMap.get(teacher.getId());
			if(StringUtils.isNotBlank(dutyNames)){
				User user = userMap.get(teacher.getId());
				if(user!=null){
					Unit unit = unitMap.get(user.getUnitid());
					if(unit!=null){
						user.setUnitName(unit.getName());
					}
					Dept dept = deptMap.get(teacher.getDeptid());
					if(dept!=null){
						user.setDeptName(dept.getDeptname());
					}
					user.setDutyNames(dutyNames);
					users.add(user);
				}
			}
		}
		//setUserPhoto(users);
		return users;
	}

    @Override
    public List<String> getUnremovableOwnerIds(int ownerType, String... ownerIds) {
        String option = systemIniService.getValue("DATA_REMOVABLE_CHECK_WITH_AP");
        //如果开关没有打开，则不调用AP接口
        if(!StringUtils.equals("1", option)) {
            return new ArrayList<String>();
        }
        
        if(log.isDebugEnabled())
            log.debug("---开始调用删除控制接口， ownerType(1=Stu, 2=Tea, 3=Fam):" + ownerType);
        
        //获取需要进行远程调用的ap信息，2=基本信息删除校验
        List<RemoteApp> remoteApps = baseRemoteApCodeService.getAppsByBusinessType("2");
        if(CollectionUtils.isEmpty(remoteApps)) {
            if(log.isDebugEnabled())
                log.debug("---取不到AP注册信息");
            return new ArrayList<String>();
        }
        
        Set<String> ownerIdSet = new HashSet<String>();
        Set<String> familyIdSet = new HashSet<String>();
        Map<String, String> familyStudentIdMap = new HashMap<String,String>();
        switch(ownerType) {
            case User.STUDENT_LOGIN:
                CollectionUtils.addAll(ownerIdSet, ownerIds);
                List<Family> families = studentFamilyService.getFamiliesByStudentId(ownerIds);
                for(Family family : families) {
                    familyIdSet.add(family.getId());
                    familyStudentIdMap.put(family.getId(), family.getStudentId());
                }
                break;
            case User.TEACHER_LOGIN:
                CollectionUtils.addAll(ownerIdSet, ownerIds);
                break;
            case User.FAMILY_LOGIN:
                families = studentFamilyService.getFamilies(ownerIds);
                for(Family family : families) {
                    ownerIdSet.add(family.getId());
                }
                break;
            default:
                break;
        }
        Set<String> needCheckAllSet = new HashSet<String>();
        //所有的用户信息
        List<User> allOwnerUsers = getUsersByOwner(ownerType, ownerIdSet.toArray(new String[0]));
        List<User> allFamilyUsers = new ArrayList<User>();
        if(CollectionUtils.isNotEmpty(familyIdSet)) {
            allFamilyUsers = getUsersByOwner(User.FAMILY_LOGIN, familyIdSet.toArray(new String[0]));
        }
            
        //没有不可以删除的数据
        if(CollectionUtils.isEmpty(allOwnerUsers) && CollectionUtils.isEmpty(allFamilyUsers)) {
            if(log.isDebugEnabled())
                log.debug("---没有取到需要控制的数据，没有对应的用户信息");
            return new ArrayList<String>();
        }
        Map<String, String> id2NameMap = new HashMap<String, String>();
        Map<String, String> name2IdMap = new HashMap<String, String>();
        for(User user : allOwnerUsers) {
            id2NameMap.put(user.getTeacherid(), user.getName());
            name2IdMap.put(user.getName(), user.getTeacherid());
            //有用户信息的传入id，要做校验，没有用户信息的，不放进去
            if(!needCheckAllSet.contains(user.getTeacherid()))
                needCheckAllSet.add(user.getTeacherid());
        }
        for(User user : allFamilyUsers) {
            String familyId = user.getTeacherid();
            id2NameMap.put(familyId, user.getName());
            name2IdMap.put(user.getName(), familyId);
//            String studentId = familyStudentIdMap.get(familyId);
//            //由家长用户信息的对应学生传入id，要做校验
//            //TODO 这个学生肯定是没有帐号的，是否有必要加？
//            if(!needCheckAllSet.contains(studentId))
//                needCheckAllSet.add(studentId);
            //有用户的家长，本身也要做校验
            if(!needCheckAllSet.contains(familyId))
                needCheckAllSet.add(familyId);
        }
        if(log.isDebugEnabled())
            log.debug("---需要校验的id：" + StringUtils.join(needCheckAllSet.toArray(new String[0]), ","));
        
        //需要校验的数据
        List<String> dataIds = null;
        List<String> dataNames = null;
        for(RemoteApp ra : remoteApps) {
            if(dataIds != null && dataIds.size() == 0) {
                break;
            }
            String url = ra.getApNoticeUrl();
            if(StringUtils.isBlank(url))
                continue;
            
            String param = StringUtils.substringAfter(url, "?");
            String dataIdType = "1";
            if(StringUtils.isNotBlank(param)) {
                String[] params = param.split("&");
                for(String s : params) {
                    String[] ss = s.split("=");
                    if(ss.length <= 0)
                        continue;                            
                    if(StringUtils.equals(ss[0], "dataIdType")) {
                        dataIdType = ss[1];
                        break;
                    }
                }
            }
            
            if(log.isDebugEnabled())
                log.debug("-------开始ap调用：" + ra.getAppName() + ", ticketKey:" + ra.getTicketKey());
            //第一次进入，初始化dataIds值
            if(dataIds == null) {
                dataIds = new ArrayList<String>();
                dataNames = new ArrayList<String>();
                for (String s : needCheckAllSet) {
                    dataIds.add(s);
                    dataNames.add(id2NameMap.get(s));
                }
            }
            
            Map<String, String> header = new HashMap<String, String>();
            header.put("ticketKey", ra.getTicketKey());
            Map<String, String> formMap = new HashMap<String, String>();
            if(StringUtils.equals("2", dataIdType)) {
                formMap.put("dataIds", StringUtils.join(dataNames.toArray(new String[0]), ","));
                if(log.isDebugEnabled())
                    log.debug("---需要传入用户名：" + StringUtils.join(dataNames, ","));
            }
            else {
                formMap.put("dataIds", StringUtils.join(dataIds.toArray(new String[0]), ","));
                if(log.isDebugEnabled())
                    log.debug("---需要传入Id：" + StringUtils.join(dataIds, ","));
            }
            
            List<String> list = new ArrayList<String>();
            try {
                
                long time = System.currentTimeMillis();
                String content = RemoteCallUtils.sendUrl(url, header, formMap);
                if(log.isDebugEnabled()) {
                    log.debug("---调用ap耗时：" + (System.currentTimeMillis() - time)/1000.0);
                    log.debug("---调用ap结果：" + content);
                }
                //结果返回异常，需要验证的几个学生数据都不能删除
                if(!StringUtils.startsWith(content, "{")) {
                    CollectionUtils.addAll(list, ownerIds);
                    if(log.isDebugEnabled())
                        log.debug("---出现Json异常");
                    return list;
                }
                JSONObject json = JSONObject.fromObject(content);
                if(!json.containsKey("result")) {
                    CollectionUtils.addAll(list, ownerIds);
                    if(log.isDebugEnabled())
                        log.debug("---出现Json异常，没有result结果值");
                    return list;
                }
                if(!json.containsKey("data")) {
                    CollectionUtils.addAll(list, ownerIds);
                    if(log.isDebugEnabled())
                        log.debug("---出现Json异常，没有data结果值");
                    return list;
                }
                JSONArray array = json.getJSONArray("data");
                //返回的，可以删除的id/name
                List<String> dataJsonIds = new ArrayList<String>();
                for(int i= 0; i < array.size(); i ++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    if(jsonObject.containsKey("id")) {
                        String id = jsonObject.getString("id");
                        dataJsonIds.add(id);
                    }
                }
                
                dataNames.clear();
                dataIds.clear();
                //如果是用户名形式
                if (StringUtils.equals(dataIdType, "2")) {
                    for(String s : dataJsonIds) {
                        dataNames.add(s);
                        dataIds.add(name2IdMap.get(s));
                    }
                }
                else {
                    for(String s : dataJsonIds) {
                        dataIds.add(s);
                        dataNames.add(id2NameMap.get(s));
                    }
                }
            }
            catch (Exception e) {
                if(log.isDebugEnabled())
                    log.debug("---出现异常：" + e.getMessage());
                CollectionUtils.addAll(list, ownerIds);
                return list;
            }
        }
        List<String> list = new ArrayList<String>();
        //如果为空，表示没有掉接口校验，直接返回都能删除
        if(dataIds == null) {
            return list;
        }
        
        //对传入需要做校验的数据进行验证
        for(String s : needCheckAllSet) {
            //允许删除的，不放入返回列表
            if(dataIds.contains(s)) {
                continue;
            }
            
            //判断下是不是学生附带家长
            String studentId = familyStudentIdMap.get(s);
            //是附带的家长
            if(StringUtils.isNotBlank(studentId)) {
                //存在这个学生id，并且还没有放入返回列表的，放进去
                if(ArrayUtils.contains(ownerIds, studentId) && !list.contains(studentId)) {
                    list.add(studentId);
                }
            }
            //不是学生附带家长的，则表示本身是不能删除的，放入列表
            else {
                if(!list.contains(s)) {
                    list.add(s);
                }
            }
        }
        
        if(log.isDebugEnabled()){
            for(String ss : list) {
                log.debug("--------------最终不能删除的ID:" + ss);
            }
        }
        return list;
    }
	
    @Override
	public Map<String, Object> getVerifyDelete(int ownerType, String... ownerIds) {
		Map<String,Object> map=new HashMap<String, Object>();
		StringBuffer namesSbf=new StringBuffer();
		List<String> noDeleteIds=new ArrayList<String>();
		List<String> handleIds=new ArrayList<String>();
		for (String id : ownerIds) {
			handleIds.add(id);
		}
		String[] noDelIds=null;
		switch(ownerType) {
	        case User.STUDENT_LOGIN:
	        	noDeleteIds=this.getUnremovableOwnerIds(ownerType,ownerIds);
	        	noDelIds=noDeleteIds.toArray(new String[0]);
	//			noDelIds=ownerIds;
	        	Map<String, SimpleStudent> studentMap = simpleStudentService.getStudentMap(noDelIds);
	        	for (String id : noDelIds) {
	        		SimpleStudent student = studentMap.get(id);
	        		if(student!=null){
	        			if(namesSbf.length()>0){
	        				namesSbf.append(","+student.getStuname());
	        			}else{
	        				namesSbf.append(student.getStuname());
	        			}
	        		}
	        	}
	            break;
	        case User.TEACHER_LOGIN:
	        	noDeleteIds=this.getUnremovableOwnerIds(ownerType,ownerIds);
	        	noDelIds=noDeleteIds.toArray(new String[0]);
	//			noDelIds=ownerIds;
	        	Map<String, Teacher> teacherMap = teacherService.getTeacherMap(noDelIds);
	        	for (String id : noDelIds) {
	        		Teacher teacher = teacherMap.get(id);
	        		if(teacher!=null){
	        			if(namesSbf.length()>0){
	        				namesSbf.append(","+teacher.getName());
	        			}else{
	        				namesSbf.append(teacher.getName());
	        			}
	        		}
	        	}
	            break;
	        case User.FAMILY_LOGIN:
	        	noDeleteIds=this.getUnremovableOwnerIds(ownerType,ownerIds);
	        	noDelIds=noDeleteIds.toArray(new String[0]);
	//			noDelIds=ownerIds;
	        	List<Family> families = studentFamilyService.getFamilies(noDelIds);
	        	Map<String, Family> familyMap = new HashMap<String, Family>();
	        	for (Family family : families) {
	        		familyMap.put(family.getId(), family);
	        	}
	        	for (String id : noDelIds) {
	        		Family family = familyMap.get(id);
	        		if(family!=null){
	        			if(namesSbf.length()>0){
	        				namesSbf.append(","+family.getName());
	        			}else{
	        				namesSbf.append(family.getName());
	        			}
	        		}
	        	}
	            break;
	        default:
	            break;
	    }
		map.put("noIds", noDeleteIds.toArray(new String[0]));
		handleIds.removeAll(noDeleteIds);
		map.put("yesIds", handleIds.toArray(new String[0]));
		map.put("names", namesSbf.toString());
		StringBuffer msgSbf=new StringBuffer();
		if(namesSbf.toString().length()>0){
			String dataName="";
			if(User.STUDENT_LOGIN==ownerType){
				dataName="学生";
			}else if(User.TEACHER_LOGIN==ownerType){
				dataName="教师";
			}else if(User.FAMILY_LOGIN==ownerType){
				dataName="家长";
			}
			msgSbf.append("以下"+dataName+"已经被其他系统使用，不能删除。["+dataName+"：" + namesSbf.toString() + "]。");
		}
		map.put("msg", msgSbf.toString());
		return map;
	}

	@Override
	public List<User> getUsersByUnitIdAndName(String realName, String unitId) {
		return userDao.getUsersByUnitIdAndName(realName, unitId);
	}
	
	@Override
	public List<User> getUsersByUnitIds(String[] unitIds) {
		return userDao.getUsersByUnitIds(unitIds);
	}
	
	@Override
	public Map<String, List<User>> getUserMapByUnitIds(String[] unitIds){
		return userDao.getUserMapByUnitIds(unitIds);
	}
}
