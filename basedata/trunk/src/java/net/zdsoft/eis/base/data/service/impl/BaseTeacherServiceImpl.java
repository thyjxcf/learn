package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.UnitDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.common.service.impl.TeacherServiceImpl;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dao.BaseTeacherDao;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseTeacherDuty;
import net.zdsoft.eis.base.data.service.BaseMcodeDetailService;
import net.zdsoft.eis.base.data.service.BaseTeacherDutyService;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.eis.system.frame.serial.SerialManager;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.util.RegUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author yanb
 * 
 */
public class BaseTeacherServiceImpl extends TeacherServiceImpl implements
		BaseTeacherService {
	private UnitDao unitDao;
	private BaseTeacherDao baseTeacherDao;
	private BaseUserService baseUserService;
	private UnitIniService unitIniService;
	private SystemIniService systemIniService;
	private RoleService roleService;
	private UserRoleRelationService userRoleRelationService;
	private BaseTeacherDutyService baseTeacherDutyService;
	private BaseMcodeDetailService baseMcodeDetailService;
	private SerialManager serialManager;
	protected SystemDeployService systemDeployService;
    
    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }
	public void setBaseTeacherDao(BaseTeacherDao baseTeacherDao) {
		this.baseTeacherDao = baseTeacherDao;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}

	/**
	 * 设置systemIniService
	 * @param systemIniService systemIniService
	 */
	public void setSystemIniService(SystemIniService systemIniService) {
	    this.systemIniService = systemIniService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUserRoleRelationService(
			UserRoleRelationService userRoleRelationService) {
		this.userRoleRelationService = userRoleRelationService;
	}

	public void setBaseTeacherDutyService(
			BaseTeacherDutyService baseTeacherDutyService) {
		this.baseTeacherDutyService = baseTeacherDutyService;
	}

	public void setBaseMcodeDetailService(
			BaseMcodeDetailService baseMcodeDetailService) {
		this.baseMcodeDetailService = baseMcodeDetailService;
	}

	// =========================以上是set==================================

	private void insertTeacherDuty(BaseTeacher teacher) {
		// 如果传回的duty列表是空的，且是修改教师操作，则删除原有的职务
		if (StringUtils.isBlank(teacher.getDuty())) {
			if (StringUtils.isNotBlank(teacher.getId())) {
				baseTeacherDutyService
						.deleteTeacherDutiesByTeacherIds(new String[] { teacher
								.getId() }, EventSourceType.LOCAL);
			}
		} else {
			String[] dutyCodes = teacher.getDuty().split(",");
			if (StringUtils.isBlank(teacher.getId())) {
				// 新增教师时直接插入职务
				List<BaseTeacherDuty> dutyList = new ArrayList<BaseTeacherDuty>();
				BaseTeacherDuty duty = null;
				for (int i = 0; i < dutyCodes.length; i++) {
					duty = new BaseTeacherDuty();
					duty.setTeacherId(teacher.getId());
					duty.setDutyCode(dutyCodes[i]);
					dutyList.add(duty);
				}
				baseTeacherDutyService.addTeacherDuties(dutyList);
			} else {
				// 修改教师时，先从原数据中，删除此次页面选择时没选中的数据
				List<BaseTeacherDuty> oldDutyList = baseTeacherDutyService
						.getTeacherDutyListByTeacherId(teacher.getId());
				for (BaseTeacherDuty oldDuty : oldDutyList) {
					boolean needRemove = true;
					for (String dutyCode : dutyCodes) {
						if (oldDuty.getDutyCode().equals(dutyCode)) {
							needRemove = false;
							break;
						}
					}
					if (needRemove) {
						baseTeacherDutyService.deleteTeacherDutiesByIds(
								new String[] { oldDuty.getId() },
								EventSourceType.LOCAL);
					}
				}
				List<BaseTeacherDuty> dutyList = new ArrayList<BaseTeacherDuty>();
				BaseTeacherDuty duty = null;
				// 然后插入页面选中，但库中不存在的数据
				for (int i = 0; i < dutyCodes.length; i++) {
					boolean needInsert = true;
					for (BaseTeacherDuty oldDuty : oldDutyList) {
						if (oldDuty.getDutyCode().equals(dutyCodes[i])) {
							needInsert = false;
						}
					}
					if (needInsert) {
						duty = new BaseTeacherDuty();
						duty.setTeacherId(teacher.getId());
						duty.setDutyCode(dutyCodes[i]);
						dutyList.add(duty);
					}
				}
				baseTeacherDutyService.addTeacherDuties(dutyList);
			}
		}

	}

	public void insertTeacher(BaseTeacher teacher) {
		baseTeacherDao.insertTeacher(teacher);
		insertTeacherDuty(teacher);
	}

	public void addTeacherFromMq(BaseTeacher teacher) {
		baseTeacherDao.insertTeacher(teacher);
	}

	public void addTeachers(List<BaseTeacher> teacheList) {
		baseTeacherDao.insertTeachers(teacheList);
	}

	public void saveTeacher(BaseTeacher teacher) throws ItemExistsException {
		if (baseTeacherDao.isExistsTeacherCode(teacher.getUnitid(), teacher
				.getTchId())) {
			throw new ItemExistsException("编号已存在！", "employeeDto.tchId");
		}
		baseTeacherDao.insertTeacher(teacher);
		insertTeacherDuty(teacher);
	}

	public void saveTeacher(BaseTeacher teacher, UploadFile signatureFile)
			throws ItemExistsException {
		teacher.setObjectUnitId(teacher.getUnitid());
		storageFileService.saveFile(teacher, signatureFile);

		saveTeacher(teacher);
	}

	public void updateTeacher(BaseTeacher teacher) {
		BaseTeacher oldTeacher = getBaseTeacher(teacher.getId());
		if (!oldTeacher.getTchId().trim().equals(teacher.getTchId().trim())) {
			if (baseTeacherDao.isExistsTeacherCode(teacher.getUnitid(), teacher
					.getTchId())) {
				throw new ItemExistsException("该编号已存在！", "teacher.tchId");
			}
		}
		baseTeacherDao.updateTeacher(teacher);
		if (teacher.isSendMq()) {
			this.updateTeacherWithUser(teacher);
		}
	}

	public void updateTeachers(List<BaseTeacher> teacheList) {
		baseTeacherDao.updateTeachers(teacheList);
	}

	private void updateTeacherWithUser(BaseTeacher teacher)
			throws ItemExistsException {
		this.updateUserMarkByTeacher(teacher);
		insertTeacherDuty(teacher);
		try {
			baseUserService.updateSynchronizeUser4Teacher(teacher);
		} catch (Exception e) {
			throw new RuntimeException("User和Teachert同步错误：" + e.getMessage());
		}
	}

	public void updateTeacher(BaseTeacher teacherDto, UploadFile signatureFile)
			throws ItemExistsException {
		BaseTeacher oldTeacher = getBaseTeacher(teacherDto.getId());
		teacherDto.setDirId(oldTeacher.getDirId());
		teacherDto.setFilePath(oldTeacher.getFilePath());
		teacherDto.setDirPath(oldTeacher.getDirPath());
		teacherDto.setObjectUnitId(teacherDto.getUnitid());

		storageFileService.saveFile(teacherDto, signatureFile);
		baseTeacherDao.updatePhoto(teacherDto);

		updateTeacher(teacherDto);
		insertTeacherDuty(teacherDto);
	}

	private void updateUserMarkByTeacher(BaseTeacher teacher) {
		if (teacher == null) {
			return;
		}

		String[] normal = new String[] { BasedataConstants.EMPLOYEE_INCUMBENCY,
				BasedataConstants.EMPLOYEE_TEMP,
				BasedataConstants.EMPLOYEE_PLURALITY,
				BasedataConstants.EMPLOYEE_PROBATION,
				BasedataConstants.EMPLOYEE_BORROW };
		String[] others = new String[] { BasedataConstants.EMPLOYEE_DIMISSION,
				BasedataConstants.EMPLOYEE_RETIRE,
				BasedataConstants.EMPLOYEE_STELLENBOSCH,
				BasedataConstants.EMPLOYEE_DEAD,
				BasedataConstants.EMPLOYEE_REMOVE,
				BasedataConstants.EMPLOYEE_RESIGNATION,
				BasedataConstants.EMPLOYEE_OTHER_DIMISSION };
		for (int i = 0; i < normal.length; i++) {
			if (normal[i].trim().equals(teacher.getEusing())) {
				try {
					baseUserService.updateUserAuditByTeacher(teacher.getId());
				} catch (Exception e) {
					throw new RuntimeException("更新用户状态为正常时出错。", e);
				}
				return;
			}
		}
		for (int i = 0; i < others.length; i++) {
			if (others[i].trim().equals(teacher.getEusing())) {
				try {
					baseUserService
							.updateUserDimissionLockByTeacher(new String[] { teacher
									.getId() });
				} catch (Exception e) {
					throw new RuntimeException("更新用户状态为离职锁定是出错。", e);
				}
				return;
			}
		}
	}

	public void updateTeacherAsOrder(String[] teacherids, String[] orderids) {
		baseTeacherDao.updateAsOrderByTeacher(teacherids, orderids);
	}

	public void deleteTeacher(String[] teacherIds, EventSourceType eventSource) {
		List<User> listOfUser = baseUserService.getUsersByOwner(
				User.TEACHER_LOGIN, teacherIds);
		Set<String> setOfUserId = new HashSet<String>();
		for (User userDto : listOfUser) {
			setOfUserId.add(String.valueOf(userDto.getId()));
		}
		baseTeacherDao.deleteTeacher(teacherIds, eventSource);
		baseTeacherDutyService.deleteTeacherDutiesByTeacherIds(teacherIds,
				eventSource);
		if (eventSource == EventSourceType.LOCAL) {
			deleteTeacherWithUsers(setOfUserId.toArray(new String[0]),
					eventSource);
		}
	}

	public void deleteTeacherWithUsers(String[] teacherIds,
			EventSourceType eventSource) {
		try {
			baseUserService.deleteUsers(teacherIds, eventSource);
			baseTeacherDutyService.deleteTeacherDutiesByTeacherIds(teacherIds,
					eventSource);
		} catch (Exception e) {
			throw new RuntimeException("删除用户出错。", e);
		}
	}

	public boolean saveTeacher(BaseTeacher teacher, User user) {
		if (!RegUtils.regexValidator(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION), user.getName())) {
			throw new ItemExistsException("必须是3-20位的英文(A-Z，a-z)或数字(0-9)及下划线。",
					"user.name");
		}

		List<User> totalUsersInUnit = userService.getUsers(teacher.getUnitid());
		int userCountLimit = serialManager.getUserCountLimit();
		if (userCountLimit != 0 && totalUsersInUnit.size() >= userCountLimit) {
			throw new RuntimeException("保存用户错误,单位用户数已达最大限制" + userCountLimit);
		}

		if (baseUserService.getUserNameCount(user.getName()) > 0) {
			throw new ItemExistsException("账号已存在！", "user.name");
		}

		if (baseTeacherDao.isExistsTeacherCode(teacher.getUnitid(), teacher
				.getTchId())) {
			throw new ItemExistsException("编号已存在！", "teacher.tchId");
		}

		baseTeacherDao.insertTeacher(teacher);
		insertTeacherDuty(teacher);
		if (null == user.getOwnerType()) {
			user.setOwnerType(User.TEACHER_LOGIN);
		}
		user.setTeacherid(teacher.getId());
		user.setUnitid(teacher.getUnitid());
		user.setDeptid(teacher.getDeptid());
		user.setRealname(teacher.getName());
		user.setCreationTime(new Date());
		user.setChargeNumber(teacher.getChargeNumber());
		user.setSex(teacher.getSex());
		user.setEmail(teacher.getEmail());
		if(StringUtils.isNotBlank(teacher.getPersonTel())){
			user.setMobilePhone(teacher.getPersonTel());   //新增时保存手机号
		}
		user = unitIniService.getUserPass(user);// 返回的对象中密码未加密

		// 判断密码的位数是否正确
		if (user.getPassword() != null && user.getPassword().length() < 4) {
			throw new ItemExistsException("当设置账号为登录密码时，账号不能小于四位！",
					"user.name");
		}

		try {
			baseUserService.saveUserByTeacher(user, teacher);
		} catch (Exception e) {
			throw new RuntimeException("保存用户时错误[" + e.getMessage() + "]", e);
		}

		this.updateUserMarkByTeacher(teacher);

		// 设置新增用户为默认角色
		Role role = roleService.getRole(user.getUnitid(),
				Role.ROLE_IDENTIFIER_DEFAULT);
		if (role != null) {
			// 得到此新增用户的id
			User userTemp = baseUserService.getUserByUserName(user.getName());
			String[] userids = new String[] { userTemp.getId() };

			// 得到用户所在单位的默认角色id
			String[] roleids = new String[] { role.getId() };

			userRoleRelationService.saveUserRolesFromUser(userids, roleids,
					teacher.getUnitid());
		}

		return true;
	}

	public boolean saveTeacher(BaseTeacher teacher, User user,
			UploadFile signatureFile) {
		teacher.setObjectUnitId(teacher.getUnitid());
		storageFileService.saveFile(teacher, signatureFile);

		boolean result = saveTeacher(teacher, user);

		return result;
	}

	public boolean isExistsIdCard(String id, String idCard) {
		return baseTeacherDao.isExistsIdCard(id, idCard);
	}

	public boolean isExistsCardNumber(String teacherId, String cardNumber) {
		boolean isExistsCardNumber = baseTeacherDao.isExistsCardNumber(
				teacherId, cardNumber);
		if (!isExistsCardNumber) {
			try {
				return baseTeacherDao.isExistsCardNumberInStu(cardNumber);
			} catch (Exception e) {
				return false;
			}
		}
		return isExistsCardNumber;
	}

	public String getAvaTeacherCode(String unitId) {
		List<String> subList = baseTeacherDao.getTeacherCodes(unitId);
		int max = 0;
		if(CollectionUtils.isNotEmpty(subList)){
			for(String sc : subList){
				max = Math.max(max, NumberUtils.toInt(sc));
			}
		}
		double length = Math.pow(10, String.valueOf(max).length());
		StringBuffer sb = new StringBuffer("");
		boolean flag = false;
		for (int i = 1; i < length; i++) {
			sb.setLength(0);
			for (int j = 0; j < BaseTeacher.EMPID_LENGTH
					- String.valueOf(i).length(); j++) {
				sb.append("0");
			}
			sb.append(String.valueOf(i));
			if(!subList.contains(sb.toString())){
				flag = true;
				break;
			}
		}
		if (!flag) {
			sb = new StringBuffer();
			for (int i = 0; i < BaseTeacher.EMPID_LENGTH - 1; i++) {
				sb.append("0");
			}
			return sb.append("1").toString();
		}
		return sb.toString();
	}
	
	public Map<String, Set<String>> getTeacherCodeByUnitIds(String[] unitIds){
		List<BaseTeacher> teacherList = baseTeacherDao.getTeachers(unitIds);
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		for(BaseTeacher teacher : teacherList){
			Set<String> set = map.get(teacher.getUnitid());
			if(CollectionUtils.isEmpty(set)){
				set = new HashSet<String>();
			}
			set.add(teacher.getTchId());
			map.put(teacher.getUnitid(), set);
		}
		return map;
	}

	public List<BaseTeacher> getTeachersFaintness(String unitid, String code,
			String name, String cardNumber, String deptId, String userName) {
		List<BaseTeacher> entityList = baseTeacherDao.getTeachersFaintness(
				unitid, code, name, cardNumber, deptId);
		String[] tchIds = new String[entityList.size()];
		int i = 0;
		for (BaseTeacher dto : entityList) {
			tchIds[i] = dto.getId();
			i++;
		}

		Map<String, User> mapOfUser = userService.getUserMapByOwner(
				User.TEACHER_LOGIN, tchIds);
		List<BaseTeacher> removeList = new ArrayList<BaseTeacher>();
		for (BaseTeacher dto : entityList) {
			User userDto = mapOfUser.get(dto.getId());
			if(StringUtils.isNotBlank(userName)){//账号过滤  此方法不适合列表分页的情况（教职工管理这里暂时没有分页）
				if(userDto != null){
					String dtoname = userDto.getName();
					if(dtoname.contains(userName)){
						dto.setLoginName(userDto.getName());
						dto.setUserState(userDto.getMark());
						dto.setSequence(userDto.getSequence());
					}else{
						removeList.add(dto);
					}
				}else{
					removeList.add(dto);
				}
			}else{
				if (userDto != null) {
					dto.setLoginName(userDto.getName());
					dto.setUserState(userDto.getMark());
					dto.setSequence(userDto.getSequence());
				}
			}
		}
		if(removeList != null && removeList.size()>0){
			entityList.removeAll(removeList);
		}
		return setDeptName(unitid, entityList);
	}
	public List<BaseTeacher> getTeachersFaintnessByPage(String unitid,String name, String userName,Pagination page) {
		List<BaseTeacher> entityList = baseTeacherDao.getTeachersFaintness(
				unitid, null, name, null, null);
		String[] tchIds = new String[entityList.size()];
		int i = 0;
		for (BaseTeacher dto : entityList) {
			tchIds[i] = dto.getId();
			i++;
		}
		Map<String, User> mapOfUser = userService.getUserMapByOwner(
				User.TEACHER_LOGIN, tchIds);
		List<BaseTeacher> removeList = new ArrayList<BaseTeacher>();
		for (BaseTeacher dto : entityList) {
			User userDto = mapOfUser.get(dto.getId());
			if(StringUtils.isNotBlank(userName)){
				if(userDto != null){
					String dtoname = userDto.getName();
					if(dtoname.contains(userName)){
						dto.setLoginName(userDto.getName());
						dto.setUserState(userDto.getMark());
						dto.setSequence(userDto.getSequence());
					}else{
						removeList.add(dto);
					}
				}else{
					removeList.add(dto);
				}
			}else{
				if (userDto != null) {
					dto.setLoginName(userDto.getName());
					dto.setUserState(userDto.getMark());
					dto.setSequence(userDto.getSequence());
				}
			}
		}
		if(removeList != null && removeList.size()>0){
			entityList.removeAll(removeList);
		}	
		List<BaseTeacher> listOutput = new ArrayList<BaseTeacher>();
		if (CollectionUtils.isNotEmpty(entityList) && page != null) {
			// 对page进行 重新 初始化
			page.setMaxRowCount(entityList.size());
			page.initialize();
			if (entityList.size() > 0) {
				int max = 0;
				if (page.getPageIndex() == page.getMaxPageIndex()) {
					max = page.getMaxRowCount();

				} else {
					max = page.getPageIndex() * page.getPageSize();

				}
				// 显示list进行输出设置
				for (int j = (page.getPageIndex() - 1) * page.getPageSize(); j < max; j++) {
					listOutput.add(entityList.get(j));
				}
			}
			return listOutput;
		} else {
			return entityList;
		}
		
	}
	private List<BaseTeacher> setDeptName(String unitId, List<BaseTeacher> list) {
		if (list == null || list.size() == 0) {
			return list;
		}
		Map<String, Dept> map = deptService.getDeptMap(unitId);
		for (int i = 0; i < list.size(); i++) {
			BaseTeacher tch = (BaseTeacher) list.get(i);
			Dept dept = (Dept) map.get(tch.getDeptid());
			if (dept != null) {
				tch.setDeptName(dept.getDeptname());
			}
		}
		return list;
	}

	public BaseTeacher getBaseTeacher(String teacherId) {
		BaseTeacher baseTeacher = baseTeacherDao.getBaseTeacher(teacherId);
		// 设置路径
		storageFileService.setDirPath(baseTeacher);
//		setCommonAttr(baseTeacher);
		return setTeacherDutyName(baseTeacher);
	}

	public List<BaseTeacher> getBaseTeachers(String unitId, String deptId) {
		List<BaseTeacher> teacherList;

		if (StringUtils.isEmpty(deptId)) {
			teacherList = baseTeacherDao.getBaseTeachers(unitId);
		} else if (deptId.equals(Dept.TOP_GROUP_GUID)) {
			teacherList = baseTeacherDao.getBaseTeachers(unitId);
		} else {
			teacherList = baseTeacherDao.getBaseTeachersByDeptId(deptId);
		}

		String[] tchIds = new String[teacherList.size()];
		int i = 0;
		for (BaseTeacher teacher : teacherList) {
			tchIds[i] = teacher.getId();
			i++;

		}
		Map<String, User> mapOfUser = userService.getUserMapByOwner(
				User.TEACHER_LOGIN, tchIds);
		for (BaseTeacher teacher : teacherList) {
			User user = mapOfUser.get(teacher.getId());
			if (user != null) {
				teacher.setLoginName(user.getName());
				teacher.setSequence(user.getSequence());
				teacher.setUserState(user.getMark());

			}
		}
		List<BaseTeacher> list = setBaseDeptName(unitId, teacherList);
		return setTeacherDutyName(unitId, list);
	}
	
	private List<BaseTeacher> setBaseDeptName(String unitId,
			List<BaseTeacher> list) {
		if (list == null || list.size() == 0) {
			return list;
		}
		Map<String, Dept> map = deptService.getDeptMap(unitId);
		for (int i = 0; i < list.size(); i++) {
			BaseTeacher tch = list.get(i);
			Dept dept = map.get(tch.getDeptid());
			if (dept != null) {
				tch.setDeptName(dept.getDeptname());
			}
		}
		return list;
	}

	private List<BaseTeacher> setTeacherDutyName(String unitId,
			List<BaseTeacher> list) {
		if (list == null || list.size() == 0) {
			return list;
		}
		Map<String, Mcodedetail> map ;
		Unit unit = unitDao.getUnit(unitId);
		if(unit.getUnitclass() != 1){
			map = baseMcodeDetailService
				.getMcodeDetailMap("DM-XXZW");
		}else{
			map = baseMcodeDetailService
					.getMcodeDetailMap("DM-JYJZW");
		}
		Set<String> teacherIds = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			BaseTeacher tch = list.get(i);
			teacherIds.add(tch.getId());
		}
		Map<String, List<BaseTeacherDuty>> dutyMap = baseTeacherDutyService
				.getTeacherDutyMapByTeacherIds(teacherIds
						.toArray(new String[0]));
		for (int i = 0; i < list.size(); i++) {
			BaseTeacher tch = list.get(i);
			if (dutyMap.containsKey(tch.getId())) {
				List<BaseTeacherDuty> dutyList = dutyMap.get(tch.getId());
				String dutyNames = "";
				String dutyCode = "";
				for (BaseTeacherDuty duty : dutyList) {
					if(map.get(duty.getDutyCode())!=null)
						dutyNames += map.get(duty.getDutyCode()).getContent() + ",";
					dutyCode += duty.getDutyCode() + ",";
				}
				if(StringUtils.isNotBlank(dutyNames))
					tch.setDutyName(dutyNames.substring(0, dutyNames.length() - 1));
				if(StringUtils.isNotBlank(dutyCode))
					tch.setDuty(dutyCode.substring(0, dutyCode.length() - 1));
			}
		}
		return list;
	}

	public List<BaseTeacher> getBaseTeachers(String[] teacherIds) {
		return baseTeacherDao.getBaseTeachers(teacherIds);
	}

	public List<BaseTeacher> getBaseTeachersByDeptId(String deptId) {
		return baseTeacherDao.getBaseTeachersByDeptId(deptId);
	}

	public List<BaseTeacher> getBaseTeachers(String unitId) {
		List<BaseTeacher> list = baseTeacherDao.getBaseTeachers(unitId);
		Map<String, Dept> gmap = deptService.getDeptMap(unitId);
		for (int i = 0; i < list.size(); i++) {
			BaseTeacher teacher = list.get(i);
			Dept gr = gmap.get(teacher.getDeptid());
			if (gr != null) {
				teacher.setDeptName(gr.getDeptname());
			}
		}
		return list;
	}

	public Map<String, BaseTeacher> getTeacherMap(String unitId, String[] codes) {
		return baseTeacherDao.getTeacherMap(unitId, codes);
	}

	public List<BaseTeacher>  getTeachersByUnitIds(String[] unitId){
		return baseTeacherDao.getTeachers(unitId);
	}
//	/**
//	 * 设置公共属性
//	 * @param teachers
//	 */
//	private void setCommonAttr(List<BaseTeacher> teachers){
//		if (CollectionUtils.isNotEmpty(teachers)){
//			for (BaseTeacher teacher : teachers) {
//				setCommonAttr(teacher);
//			}
//		}
//	}
	
//	/**
//	 * 设置公共属性
//	 * @param teacher
//	 */
//	private void setCommonAttr(BaseTeacher teacher){
//		if (teacher != null){
//			//年龄
//			setAge(teacher);
//		}
//	}
	
//	/**
//	 * 计算年龄
//	 * @param teacher
//	 */
//	private void setAge(BaseTeacher teacher){
//		if (teacher.getBirthday() != null){
//			Calendar birthday = Calendar.getInstance();
//			birthday.setTime(teacher.getBirthday());
//			Calendar now = Calendar.getInstance();
//			int year = birthday.get(Calendar.YEAR);
//			int currentYear = now.get(Calendar.YEAR);
//			int age = currentYear - year;
//			if (year <= currentYear){
//				int month = birthday.get(Calendar.MONTH);
//				int currentMonth = now.get(Calendar.MONTH);
//				if (month < currentMonth) {
//					age++;
//				}else if(month == currentMonth){
//					int day = birthday.get(Calendar.DATE);
//					int currentDay = now.get(Calendar.DATE);
//					if (day <= currentDay){
//						age ++;
//					}
//				}
//			}
//			teacher.setAge(Integer.toString(age));
//		}
//	}
	
	/**
	 * 设置职务名称
	 * @param baseTeacher
	 * @return
	 */
	private BaseTeacher setTeacherDutyName(BaseTeacher baseTeacher) {
		Map<String, Mcodedetail> map ;
		Unit unit = unitDao.getUnit(baseTeacher.getUnitid());
		if(unit.getUnitclass() != 1){
			map = baseMcodeDetailService
				.getMcodeDetailMap("DM-XXZW");
		}else{
			map = baseMcodeDetailService
					.getMcodeDetailMap("DM-JYJZW");
		}

		List<BaseTeacherDuty> dutyList = baseTeacherDutyService
				.getTeacherDutyListByTeacherId(baseTeacher.getId());
		if (dutyList != null && dutyList.size() > 0) {
			String dutyNames = "";
			String dutyCode = "";
			for (BaseTeacherDuty duty : dutyList) {
				if(map.get(duty.getDutyCode())!=null)
					dutyNames += map.get(duty.getDutyCode()).getContent() + ",";
				dutyCode += duty.getDutyCode() + ",";
			}
			if(StringUtils.isNotBlank(dutyNames))
				baseTeacher.setDutyName(dutyNames.substring(0,
					dutyNames.length() - 1));
			if(StringUtils.isNotBlank(dutyCode))
			baseTeacher.setDuty(dutyCode.substring(0, dutyCode.length() - 1));
		}
		return baseTeacher;
	}
	
	public void setSerialManager(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public SerialManager getSerialManager() {
		return this.serialManager;
	}
	public void setUnitDao(UnitDao unitDao) {
		this.unitDao = unitDao;
	}
	@Override
	public boolean isExistsTeacherCode(String unitid, String tchId) {
		return baseTeacherDao.isExistsTeacherCode(unitid, tchId);
	}

}
