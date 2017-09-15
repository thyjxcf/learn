/* 
 * @(#)BaseDataSubsystemServiceImpl.java    Created on Jan 5, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.subsystemcall.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.ServerAuthorize;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dao.BaseTeacherDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.StusysSectionTimeSet;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.data.service.BaseSemesterService;
import net.zdsoft.eis.base.data.service.BaseServerAuthorizeService;
import net.zdsoft.eis.base.data.service.BaseStudentFamilyService;
import net.zdsoft.eis.base.data.service.BaseTeacherDutyService;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.data.service.StusysSectionTimeSetService;
import net.zdsoft.eis.base.subsystemcall.entity.StusysSectionTimeSetDto;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.ClassNameFactory;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.OperationNotAllowedException;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.passport.entity.Account;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 5, 2010 4:47:43 PM $
 */
public class BaseDataSubsystemServiceImpl implements BaseDataSubsystemService {
	private BaseUnitService baseUnitService;
	private BaseStudentFamilyService baseStudentFamilyService;
	private BaseUserService baseUserService;
	private StudentService studentService;
	private BaseServerAuthorizeService baseServerAuthorizeService;
	private BaseTeacherDutyService baseTeacherDutyService;
	private StusysSectionTimeSetService stusysSectionTimeSetService;
	private BaseTeacherService baseTeacherService;
	private PassportAccountService passportAccountService;
	private BaseSchoolService baseSchoolService;
	private BaseGradeService baseGradeService;
	private BaseClassService baseClassService;
	private UserService userService;
	private BaseSemesterService baseSemesterService;
	private BaseTeacherDao baseTeacherDao;

	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}

	public void setBaseGradeService(BaseGradeService baseGradeService) {
		this.baseGradeService = baseGradeService;
	}

	public void setStusysSectionTimeSetService(
			StusysSectionTimeSetService stusysSectionTimeSetService) {
		this.stusysSectionTimeSetService = stusysSectionTimeSetService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setBaseServerAuthorizeService(
			BaseServerAuthorizeService baseServerAuthorizeService) {
		this.baseServerAuthorizeService = baseServerAuthorizeService;
	}

	public void setBaseStudentFamilyService(
			BaseStudentFamilyService baseStudentFamilyService) {
		this.baseStudentFamilyService = baseStudentFamilyService;
	}

	public void setBaseTeacherDutyService(
			BaseTeacherDutyService baseTeacherDutyService) {
		this.baseTeacherDutyService = baseTeacherDutyService;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}
	
	public void setBaseSemesterService(BaseSemesterService baseSemesterService) {
		this.baseSemesterService = baseSemesterService;
	}

	public void updateUnitBalance(String unitId, int balance) {
		baseUnitService.updateUnitBalance(unitId, balance);
	}

	public void updateFamilyByStudent(String studentId, int leaveSchool) {
		baseStudentFamilyService.updateFamilyByStudent(
				new String[] { studentId }, new int[] { leaveSchool });
	}

	public void updateFamilyByStudent(String[] studentIds, int[] leaveSchools) {
		baseStudentFamilyService
				.updateFamilyByStudent(studentIds, leaveSchools);
	}

	@Override
	public void addFamily(Family family) {
		baseStudentFamilyService.saveFamily(family);
	}

	@Override
	public void updateFamily(Family family) {
		baseStudentFamilyService.updateFamily(family);
	}

	public void updateUserByStudent(String studentId) {
		Student stu = studentService.getStudent(studentId);
		if (null == stu)
			return;
		baseUserService.updateUsersByOwner(User.STUDENT_LOGIN,
				new String[] { studentId }, new String[] { stu.getSchid() });
	}
	
	public void updateEmail(String teacherId, String email) {
		if(StringUtils.isNotBlank(email)
				&& !email.matches(BasedataConstants.EMAIL_REGEX)){
				throw new RuntimeException("email地址不符合规则");
		}
			
		BaseTeacher tea = baseTeacherService.getBaseTeacher(teacherId);
		if(tea == null){
			return;
		}
		tea.setEmail(email);
		baseTeacherService.updateTeacher(tea);
		List<User> users = baseUserService.getUsersByOwner(teacherId);
		for(User us : users){
			us.setEmail(email);
			baseUserService.updateUser(us);
		}
	}

	public void updatePassword(String id, String oldPassword,
			String newPassword, boolean coPassportInfo) throws Exception {
		baseUserService.updatePassword(id, oldPassword, newPassword,
				coPassportInfo);
	}
	
	//由于passport没有提供修改用户名的接口 所以 passport的同步通过TR_SYNC_USERNAME_TO_PASSPORT这个触发器来进行
	@Override
	public void updatePersonInfo(String id, String oldPassword,
			String newPassword, boolean coPassportInfo, String username,
			String teacherName, String mobilePhone, String identityCard)
			throws Exception {
		User user = userService.getUser(id);
		if (user == null) {
			throw new Exception("找不到用户信息！");
		}
		String password = user.findClearPassword();
		if (!oldPassword.equals(password)) {
			throw new Exception("输入的原密码错误！");
		}
		user.setPassword(newPassword);
		user.setName(username);
		user.setRealname(teacherName);
		user.setMobilePhone(mobilePhone);
		if(!baseUserService.updateUser(user, true)) {
			throw new RuntimeException("已存在姓名首字母和身份证末6位相同的用户！");
		}
		BaseTeacher teacher = baseTeacherService.getBaseTeacher(user.getTeacherid());
		teacher.setName(teacherName);
		teacher.setIdcard(identityCard);
		teacher.setPersonTel(mobilePhone);
		baseTeacherDao.updateTeacher(teacher);
	}

	public Set<String> deleteFamilyByStudentIds(String[] studentIds) {
		return baseStudentFamilyService.deleteFamilyByStudentIds(studentIds);
	}

	public void updateUserMarkByOwner(String[] ownerIds, int mark)
			throws BusinessErrorException {
		baseUserService.updateUserMarkByOwner(ownerIds, mark);
	}

	public Set<String> deleteUsersByOwner(int ownerType, String[] ownerIds)
			throws BusinessErrorException {
		return baseUserService.deleteUsersByOwner(ownerType, ownerIds);
	}

	public void deleteServerAuthorizes(String[] userIds, int serverKind) {
		baseServerAuthorizeService.deleteServerAuthorizes(userIds, serverKind);
	}

	public void addServerAuthorizes(List<ServerAuthorize> authList) {
		baseServerAuthorizeService.addServerAuthorizes(authList);
	}

	@Override
	public String getTeacherDutyNames(String teacherId) {
		return baseTeacherDutyService.getTeacherDutyNames(teacherId);
	}

	public List<StusysSectionTimeSetDto> getSectionTimeSets(String unitId,
			String acadyear, String semester) {
		List<StusysSectionTimeSetDto> dtos = new ArrayList<StusysSectionTimeSetDto>();
		List<StusysSectionTimeSet> sets = stusysSectionTimeSetService
				.getStusysSectionTimeSetByUnitIdList(unitId, acadyear, semester);
		if (CollectionUtils.isNotEmpty(sets)) {
			StusysSectionTimeSetDto dto;
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			for (StusysSectionTimeSet s : sets) {
				dto = new StusysSectionTimeSetDto();
				dto.setUnitId(s.getUnitId());
				dto.setBeginTime(s.getBeginTime());
				dto.setEndTime(s.getEndTime());
				dto.setSectionNumber(s.getSectionNumber());

				String[] bts = s.getBeginTime().split(":");
				if (ArrayUtils.getLength(bts) == 2) {
					c1.set(Calendar.HOUR_OF_DAY, NumberUtils.toInt(bts[0]));
					c1.set(Calendar.MINUTE, NumberUtils.toInt(bts[1]));
					dto.setBeginCt(c1);
				}
				String[] bts1 = s.getEndTime().split(":");
				if (ArrayUtils.getLength(bts1) == 2) {
					c2.set(Calendar.HOUR_OF_DAY, NumberUtils.toInt(bts1[0]));
					c2.set(Calendar.MINUTE, NumberUtils.toInt(bts1[1]));
					dto.setEndCt(c2);
				}
				dtos.add(dto);
				dto = null;
			}
		}
		return dtos;
	}

	@Override
	public String deleteTeacher(String[] teacherIds, EventSourceType eventSource) {
		Map<String, Object> verifyDataDeleteMap = userService.getVerifyDelete(User.TEACHER_LOGIN,teacherIds);
		String[] ownerIds=(String[]) verifyDataDeleteMap.get("yesIds");
		String msgStr=(String) verifyDataDeleteMap.get("msg");
		
		baseTeacherService.deleteTeacher(ownerIds, eventSource);
		return msgStr;
	}

	@Override
	public String createUnionId(String parentUnitId, int unitClass, int unitType) {
		if (unitType == Unit.UNIT_NOTEDU_NOTSCH) {
			return baseUnitService.createSpecialUnionid(parentUnitId);
		}
		return baseUnitService.createUnionid(parentUnitId, unitClass);
	}

	@Override
	public Account queryAccountByUsername(String userName) {
		return passportAccountService.queryAccountByUsername(userName);
	}

	@Override
	public void initGrades(String unitId, String acadyear)
			throws Exception {
		List<String[]> sectionList = baseSchoolService.getSchoolSections(unitId);
        if (CollectionUtils.isEmpty(sectionList)) {
            throw new OperationNotAllowedException("无法取得学校的学段信息！");
        }
        for (String[] s : sectionList) {
            int section = NumberUtils.toInt(s[1]);
            initGrades(unitId, section, acadyear);
        }
        //升级班级
        baseClassService.saveClassWithJob(unitId, acadyear);
	}
	
	 /**
     * 初始化年级信息
     */
    public void initGrades(String schoolId, int section, String acadyear) {
        // 取得学校信息
        BaseSchool basicSchoolinfoDto = baseSchoolService.getBaseSchool(schoolId);

        int studyYear = 0;
        // 分别取得小学、初中、高中和幼儿园的学制
        if (section == NumberUtils.toInt(BaseConstant.SECTION_INFANT)) {
            studyYear = basicSchoolinfoDto.getInfantyear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_PRIMARY)) {
            studyYear = basicSchoolinfoDto.getGradeyear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_JUNIOR)) {
            studyYear = basicSchoolinfoDto.getJunioryear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_HIGH_SCHOOL)) {
            studyYear = basicSchoolinfoDto.getSenioryear();
        }
        int currentYear = Integer.valueOf(acadyear.substring(0, 4));

        List<Grade> listOfBasicGradeDto = baseGradeService.getOverSchoolinglenGrades(schoolId, acadyear);
        List<String> gradeIds = new LinkedList<String>();
        for (Grade basicGrade : listOfBasicGradeDto) {
        	baseGradeService.updateGraduate(schoolId, basicGrade.getAcadyear(), basicGrade.getSection(), basicGrade.getSchoolinglen());
        	gradeIds.add(basicGrade.getId());
        }
        ///设置班级毕业操作
        if(CollectionUtils.isNotEmpty(gradeIds)){
        	List<BasicClass> clslist = baseClassService.getClassesByGradeIds(schoolId, gradeIds.toArray(new String[0]));
        	for(BasicClass bcl : clslist){
        		bcl.setGraduatedate(new Date());
        		bcl.setGraduatesign(1);
        		baseClassService.updateClass(bcl);
        	}
        }
        
        List<Grade> grades = baseGradeService.getBaseGradesWithGraduated(schoolId, section);
        Set<String> acadyearSet = new HashSet<String>();
        List<Grade> upgradeGrades = new ArrayList<Grade>();
        for(Grade grade : grades) {
            if(grade.getSection() == section)
                acadyearSet.add(grade.getAcadyear());
            
                String oldGradeName = grade.getGradename();
                // 根据年级生成规则获取当前学年(比如:2013-2014)的年级名称
                ClassNameFactory.getInstance().setGradeDyn(grade, acadyear);
                String newGradeName = grade.getGradename();
                // 如果根据规则生成的年级名称和库里的年级名称一致,说明已经升级过了，不再需要升级,否则继续升级
                if (newGradeName.equals(oldGradeName)) {
                    continue;
                }
                // 获取当前学年的下一学年(比如当前学年是2013-2014,下一学年是2012-2013)
                String acadyear_ = (Integer.parseInt(acadyear.substring(0, 4)) - 1) + "-"
                        + (Integer.parseInt(acadyear.substring(5)) - 1);
                // 根据年级生成规则获取下一学年的年级名称
                ClassNameFactory.getInstance().setGradeDyn(grade, acadyear_);
                String newGradeName_ = grade.getGradename();
                // 如果根据规则生成的年级名称和库里的年级名称不一致,说明年级名称用户维护过了，不再升级
                if (!oldGradeName.equals(newGradeName_)) {
                    grade.setGradename(oldGradeName);
                }
                else {
                    grade.setGradename(newGradeName);
                }
                upgradeGrades.add(grade);
        }
        
        for(Grade grade : upgradeGrades)
        	baseGradeService.updateGrade(grade);
                
        String acadYear;
        Grade basicGradeDto;
        for (int i = 0; i < studyYear; i++) {
            acadYear = String.valueOf(currentYear - i) + "-" + String.valueOf(currentYear - i + 1);
            if(acadyearSet.contains(acadYear)) {
                continue;
            }
            basicGradeDto = new Grade();
            basicGradeDto.setAmLessonCount(4);
            basicGradeDto.setPmLessonCount(4);
            basicGradeDto.setNightLessonCount(0);
            basicGradeDto.setId(UUIDGenerator.getUUID());
            basicGradeDto.setAcadyear(acadYear);
            basicGradeDto.setSchid(schoolId);
            basicGradeDto.setSection(section);
            basicGradeDto.setSchoolinglen(studyYear);
            basicGradeDto.setIsGraduated(0);
            ClassNameFactory.getInstance().setGradeDyn(basicGradeDto);
            baseGradeService.saveGrade(basicGradeDto);
        }
    }
    
    @Override
    public boolean isExistsIdCard(String id, String idCard) {
    	return baseTeacherDao.isExistsIdCard(id, idCard);
    }

	public void setBaseClassService(BaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}

	@Override
	public Semester getDefaultSemester() {
		return baseSemesterService.getDefaultSemester();
	}

	@Override
	public void saveSemester(Semester semester) {
		baseSemesterService.saveSemester(semester);
	}

	public void setBaseTeacherDao(BaseTeacherDao baseTeacherDao) {
		this.baseTeacherDao = baseTeacherDao;
	}

}
