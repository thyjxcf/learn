package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.TeacherDao;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class TeacherServiceImpl implements TeacherService {
	private TeacherDao teacherDao;
	private BaseDataSubsystemService baseDataSubsystemService;
	private SchoolService schoolService;
	protected UnitService unitService;
	protected DeptService deptService;
	protected UserService userService;
	protected McodedetailService mcodedetailService;
	protected StorageFileService storageFileService;
	protected CustomRoleService customRoleService;

	public void setStorageFileService(StorageFileService storageFileService) {
		this.storageFileService = storageFileService;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	
	// ====================以上是set==========================

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setBaseDataSubsystemService(
			BaseDataSubsystemService baseDataSubsystemService) {
		this.baseDataSubsystemService = baseDataSubsystemService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public Teacher getTeacher(String teacherId) {
		Teacher teacher = teacherDao.getTeacher(teacherId);
		if (teacher == null)
			return null;
		Map<String, Map<String, String>> map = mcodedetailService
				.getContent2Map(new String[] { "DM-JSZCM", "DM-MZ", "DM-XB",
						"DM-ZZMM", "DM-XL" });
		if (StringUtils.isNotBlank(teacher.getSex()))
			teacher.setSex((map.get("DM-XB")).get(teacher.getSex()));
		if (StringUtils.isNotBlank(teacher.getNation()))
			teacher.setNation((map.get("DM-MZ")).get(teacher.getNation()));
		if (StringUtils.isNotBlank(teacher.getPolity()))
			teacher.setPolity((map.get("DM-ZZMM")).get(teacher.getPolity()));
		if (StringUtils.isNotBlank(teacher.getTitle()))
			teacher.setTitle((map.get("DM-JSZCM")).get(teacher.getTitle()));
		if (StringUtils.isNotBlank(teacher.getStulive()))
			teacher.setStulive((map.get("DM-XL")).get(teacher.getStulive()));
		if (baseDataSubsystemService != null)
			teacher.setDutyName(baseDataSubsystemService
					.getTeacherDutyNames(teacherId));
		return teacher;
	}

	/**
	 * 包含删除教师
	 * 
	 * @param teacherId
	 * @return
	 */
	public Teacher getTeacherContainDelete(String teacherId) {
		return teacherDao.getTeacherContainDelete(teacherId);
	}

	@Override
	public List<Teacher> getTeacherListByPolity(String unitId, String[] polities) {
		return teacherDao.getTeacherListByPolity(unitId, polities);
	}

	public Teacher getTeacherPhoto(String teacherId) {
		Teacher teacher = getTeacher(teacherId);
		storageFileService.setDirPath(teacher);
		return teacher;
	}

	public List<Teacher> getTeachers(String unitId) {
		List<Teacher> list = teacherDao.getTeachers(unitId);
		Map<String, Dept> gmap = deptService.getDeptMap(unitId);
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			Dept gr = gmap.get(teacher.getDeptid());
			if (gr != null) {
				teacher.setDeptName(gr.getDeptname());
			}
		}
		return list;
	}

	public List<Teacher> getTeachersByDeptId(String deptId) {
		return teacherDao.getTeachersByDeptId(deptId);
	}

	public List<Teacher> getTeachers(String[] teacherIds) {
		return teacherDao.getTeachers(teacherIds);
	}

	@Override
	public List<Teacher> getTeachersWithDeleted(String[] teacherIds) {
		return teacherDao.getTeachersWithDeleted(teacherIds);
	}

	public List<Teacher> getExceptionTeachersByDeptId(String unitId,
			String deptId) {
		List<Teacher> teacherList = teacherDao
				.getExceptionTeachersByDeptId(unitId, deptId);
		String[] tchIds = new String[teacherList.size()];
		int i = 0;
		for (Teacher teacher : teacherList) {
			tchIds[i] = teacher.getId();
			i++;

		}
		List<Teacher> list = setDeptName(unitId, teacherList);
		return list;
	}

	/**
	 * 包含删除教师
	 * 
	 * @param tchIds
	 * @return
	 */
	public List<Teacher> getTeachersAll(String[] tchIds) {
		return teacherDao.getTeachersAll(tchIds);
	}

	public List<Teacher> getTeachers(String unitId, String deptId) {
		List<Teacher> teacherList;

		if (StringUtils.isEmpty(deptId)) {
			teacherList = teacherDao.getTeachers(unitId);
		} else if (deptId.equals(Dept.TOP_GROUP_GUID)) {
			teacherList = teacherDao.getTeachers(unitId);
		} else {
			teacherList = teacherDao.getTeachersByDeptId(deptId);
		}

		String[] tchIds = new String[teacherList.size()];
		int i = 0;
		for (Teacher teacher : teacherList) {
			tchIds[i] = teacher.getId();
			i++;

		}
		List<Teacher> list = setDeptName(unitId, teacherList);
		return list;
	}

	private List<Teacher> setDeptName(String unitId, List<Teacher> list) {
		if (list == null || list.size() == 0) {
			return list;
		}
		Map<String, Dept> map = deptService.getDeptMap(unitId);
		for (int i = 0; i < list.size(); i++) {
			Teacher tch = (Teacher) list.get(i);
			Dept dept = (Dept) map.get(tch.getDeptid());
			if (dept != null) {
				tch.setDeptName(dept.getDeptname());
			}
		}
		return list;
	}

	public List<Teacher> getTeachersByFaintness(String unitid, String name) {
		List<Teacher> entityList = teacherDao.getTeachersByFaintness(unitid,
				name);
		return setDeptName(unitid, entityList);
	}

	public List<Teacher> getTeachersByFaintness(String unitId,
			String teacherName, String teacherCode) {
		return teacherDao.getTeachersByFaintness(unitId, teacherName,
				teacherCode);
	}

	public Map<String, Teacher> getTeacherMap(String unitId) {
		return teacherDao.getTeacherMap(unitId);
	}

	public Map<String, Teacher> getTeacherMap(String[] teacherIds) {
		return teacherDao.getTeacherMap(teacherIds);
	}

	public Map<String, Teacher> getTeacherWithDeletedMap(String[] teacherIds) {
		return teacherDao.getTeacherWithDeletedMap(teacherIds);
	}

	public Map<String, Integer> getCountInDept(String[] deptids) {
		return teacherDao.getTeacherCount(deptids);
	}
	
	@Override
	public Map<String, Integer> getUnionSectionSexCount(String unitId,
			String unionid) {
		return teacherDao.getUnionSectionSexCount(unitId,unionid);
	}
	
	@Override
	public List<Teacher> getTeacherByInstituteId(String unitId,
			String instituteId) {
		return teacherDao.getTeacherByInstituteId(unitId, instituteId);
	}

	public List<Teacher> getTeachersOrder(String[] tchIds) {
		return teacherDao.getTeachersOrder(tchIds);
	}

	@Override
	public boolean isExistPrincipan(String userId, String deptId) {
		User user = userService.getUser(userId);
		return teacherDao.isExistPrincipan(user.getTeacherid(), deptId);
	}

	@Override
	public List<Teacher> getTeachers(String unitId, Pagination page) {
		List<Teacher> list = teacherDao.getTeachers(unitId, page);
		Map<String, Dept> gmap = deptService.getDeptMap(unitId);
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			Dept gr = gmap.get(teacher.getDeptid());
			if (gr != null) {
				teacher.setDeptName(gr.getDeptname());
			}
		}
		return list;
	}
	
	@Override
	public List<Teacher> getTeachers(String unitId, String deptId,
			String searchName, Pagination page) {
		List<Teacher> list = teacherDao.getTeachers(unitId,deptId,searchName,page);
		Map<String, Dept> gmap = deptService.getDeptMap(unitId);
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			Dept gr = gmap.get(teacher.getDeptid());
			if (gr != null) {
				teacher.setDeptName(gr.getDeptname());
			}
		}
		return list;
	}
	
	@Override
	public List<Teacher> getTeachersByName(String unitId, String unionCode, String searchName, Pagination page) {
		List<Teacher> list = teacherDao.getTeachersByName(unitId, unionCode, searchName, page);
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			if(StringUtils.isNotBlank(teacher.getUnitid())){
				unitIds.add(teacher.getUnitid());
			}
			if(StringUtils.isNotBlank(teacher.getDeptid())){
				deptIds.add(teacher.getDeptid());
			}
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			Unit unit = unitMap.get(teacher.getUnitid());
			if (unit != null) {
				teacher.setUnitName(unit.getName());
			}
			
			Dept dept = deptMap.get(teacher.getDeptid());
			if (dept != null) {
				teacher.setDeptName(dept.getDeptname());
			}
		}
		return list;
	}
	
	@Override
	public List<Teacher> getTeachersByNameAndRole(String[] unitids, String teacherName, String roleCode, Pagination page){
		//TODO
		Set<String> userIds = new HashSet<String>();
		Set<String> teacherIds = new HashSet<String>();
		
		Map<String, CustomRole> roleMap = customRoleService.getCustomRoleMap(unitids, roleCode);
		for(String key : roleMap.keySet()){
			CustomRole role = roleMap.get(key);
			String[] roleUserIds = role.getUserIds().split(",");
			if(roleUserIds != null && roleUserIds.length > 0){
				for(int i=0;i<roleUserIds.length;i++){
					if(StringUtils.isNotBlank(roleUserIds[i])){
						userIds.add(roleUserIds[i]);
					}
				}
			}
		}
		Map<String, User> userMap = userService.getUsersMap(userIds.toArray(new String[0]));
		for(User value : userMap.values()){
			if(1==value.getMark()){
				teacherIds.add(value.getTeacherid());
			}
		}
		List<Teacher> list = teacherDao.getTeachersByIdAndName(teacherIds.toArray(new String[0]), teacherName, page);
		
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			if(StringUtils.isNotBlank(teacher.getUnitid())){
				unitIds.add(teacher.getUnitid());
			}
			if(StringUtils.isNotBlank(teacher.getDeptid())){
				deptIds.add(teacher.getDeptid());
			}
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			Unit unit = unitMap.get(teacher.getUnitid());
			if (unit != null) {
				teacher.setUnitName(unit.getName());
			}
			
			Dept dept = deptMap.get(teacher.getDeptid());
			if (dept != null) {
				teacher.setDeptName(dept.getDeptname());
			}
		}
		return list;
	}

	@Override
	public List<Teacher> getTeachersByOtherName(String unitId,String unionCode, String searchName, String type,
			String runschtype, String unitName, String deptName,String dutyName, Pagination page) {
		List<Teacher> list = teacherDao.getTeachersByOtherName(unitId, unionCode, searchName, type, runschtype, unitName, deptName, dutyName, page);
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			if(StringUtils.isNotBlank(teacher.getUnitid())){
				unitIds.add(teacher.getUnitid());
			}
			if(StringUtils.isNotBlank(teacher.getDeptid())){
				deptIds.add(teacher.getDeptid());
			}
		}
		Map<String, Mcodedetail> schoolLBMap = mcodedetailService.getMcodeDetailMap("DM-XXLB");// 学校类别
		Map<String, Mcodedetail> schoolBBMap = mcodedetailService.getMcodeDetailMap("DM-XXBB");// 学校办别
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		for (int i = 0; i < list.size(); i++) {
			Teacher teacher = list.get(i);
			Unit unit = unitMap.get(teacher.getUnitid());
			School school=schoolService.getSchool(teacher.getUnitid());
			if (unit != null) {
				teacher.setUnitName(unit.getName());
				if(school!=null&&StringUtils.isNotBlank(school.getSchoolType())){
					Mcodedetail mco=schoolLBMap.get(school.getSchoolType());
					if(mco!=null){
						teacher.setCardNumber(mco.getContent());
					}
				}
				if(StringUtils.isNotBlank(unit.getRunschtype())){
					Mcodedetail mco=schoolBBMap.get(unit.getRunschtype());
					if(mco!=null){
						teacher.setCertificationType(mco.getContent());
					}
				}
			}
			
			Dept dept = deptMap.get(teacher.getDeptid());
			if (dept != null) {
				teacher.setDeptName(dept.getDeptname());
			}
		}
		return list;
	}

	@Override
	public List<Teacher> getTeachersByDeptId(String deptId, Pagination page) {
		return teacherDao.getTeachersByDeptId(deptId, page);
	}

	@Override
	public Map<String, Teacher> getTeacherMapByUnitIdNameCodes(String unitid,
			String[] nameCodes) {
		return teacherDao.getTeacherMapByUnitIdNameCodes(unitid, nameCodes);
	}

	@Override
	public List<Teacher> getTeachersByUserState(String unitId) {
		return teacherDao.getTeachersByUserState(unitId);
	}

	@Override
	public List<Teacher> getTeachersByDeptIdOrUserState(String deptId) {
		return teacherDao.getTeachersByDeptIdOrUserState(deptId);
	}

	@Override
	public List<Teacher> getTeachersByWeaveUnit(String weaveUnitId) {
		return teacherDao.getTeachersByWeaveUnit(weaveUnitId);
	}

	public List<Teacher> findTeachersByDutyCode(String[] unitIds,
			String dutyCode) {
		return teacherDao.findTeachersByDutyCode(unitIds, dutyCode);
	}

	public List<Teacher> findTeachersBySubject(String[] unitIds,
			String subjectCode) {
		return teacherDao.findTeachersBySubject(unitIds, subjectCode);
	}

	public List<Teacher> findTeachersByUnitIds(String[] unitIds,
			String searchName) {
		return teacherDao.findTeachersByUnitIds(unitIds, searchName);
	}

	public List<Teacher> findTeacheringTeachersByUnitIds(String[] unitIds) {
		return teacherDao.findTeacheringTeachersByUnitIds(unitIds);
	}

	@Override
	public List<Teacher> getTeachersByDeptAndName(String unitId, String deptId,
			String name) {
		if (StringUtils.isBlank(name)) {
			return this.getTeachers(unitId, deptId);
		} else if (StringUtils.isBlank(deptId)) {
			return this.getTeachersByFaintness(unitId, name);
		} else {
			List<Teacher> teacherList = teacherDao.getTeachersByDeptIdName(
					deptId, name);
			if (CollectionUtils.isNotEmpty(teacherList)) {
				return this.setDeptName(unitId, teacherList);
			} else {
				return null;
			}
		}
	}

	@Override
	public List<Teacher> getTeacherMapByIdentityCards(String[] identitycards) {
		return teacherDao.getTeacherMapByIdentityCards(identitycards);
	}

	@Override
	public List<Teacher> getTeachersByDeptIds(String[] deptIds) {
		return teacherDao.getTeachersByDeptIds(deptIds);
	}

	@Override
	public List<Teacher> getTeachersByBirthday(String unitId, int day) {
		String beginDate = DateUtils.date2String(new Date(), "MMdd");
		String endDate = DateUtils.date2String(
				DateUtils.addDay(new Date(), day), "MMdd");
		return teacherDao.getTeachersByBirthday(unitId, beginDate, endDate);
	}

//    @Override
//    public List<Teacher> getTeachersWithModifyTime(String unitId, String dataModifyTime, Pagination pagination) {
//        return teacherDao.getTeachersWithModifyTime(unitId, dataModifyTime, pagination);
//    }

//    @Override
//    public List<Teacher> getTeachersWithModifyTime(String dataModifyTime, Pagination pagination) {
//        return teacherDao.getTeachersWithModifyTime(dataModifyTime, pagination);
//    }

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	@Override
	public Map<String, Teacher> getTeacherByIdentityCards(String[] identitycards) {
		return teacherDao.getTeacherByIdentityCards(identitycards);
	}
	
	

}
