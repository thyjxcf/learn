/**
 * 
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.impl.StudentFamilyServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseStudentFamilyDao;
import net.zdsoft.eis.base.data.service.BaseStudentFamilyService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public class BaseStudentFamilyServiceImpl extends StudentFamilyServiceImpl implements
        BaseStudentFamilyService {
    private BaseStudentFamilyDao baseStudentFamilyDao;
    private PassportAccountService passportAccountService;
    private BaseUserService baseUserService;
    private SystemDeployService systemDeployService;
    private StudentService studentService;
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }

    public void setBaseStudentFamilyDao(BaseStudentFamilyDao baseStudentFamilyDao) {
        this.baseStudentFamilyDao = baseStudentFamilyDao;
    }

    public void setPassportAccountService(PassportAccountService passportAccountService) {
        this.passportAccountService = passportAccountService;
    }

    public void setBaseUserService(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    // =======================以上是set============================

    public void addFamily(Family family) {
        baseStudentFamilyDao.insertFamily(family);
    }

    public void saveFamily(Family family) {
        String familyId = family.getId();
        if (null != familyId && !"".equals(familyId)) {
            baseStudentFamilyDao.updateFamily(family);
        } else {
            baseStudentFamilyDao.insertFamily(family);
        }
        if (systemDeployService.isConnectPassport()) {
            if (!StringUtils.isBlank(familyId)) {
                List<User> listOfUser = baseUserService.getUsersByOwner(familyId);
                for (User user : listOfUser) {
                    user.setChargeNumber(family.getChargeNumber());
                    baseUserService.updateUserWithoutPassport(user);
                    if (user.getOwnerType() == null) {
                        user.setOwnerType(User.FAMILY_LOGIN);
                    }
                    try {
                        passportAccountService.modifyAccount(user, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void addFamilies(List<Family> familyList) {
        baseStudentFamilyDao.insertFamilies(familyList);
    }

    public void updateFamily(Family family) {
        baseStudentFamilyDao.updateFamily(family);
    }

    public void updateFamilyByStudent(String[] studentIds, int[] leaveSchools) {
        if (ArrayUtils.isEmpty(studentIds))
            return;

        List<Family> familyList = new ArrayList<Family>();
        Map<String, Student> stuMap = studentService.getStudentMap(studentIds);
        for (int i = 0; i < studentIds.length; i++) {
            String studentId = studentIds[i];
            int leaveSchool = leaveSchools[i];
            Student stu = stuMap.get(studentId);
            if (null == stu) {
                continue;
            }

            Family family = new Family();
            family.setStudentId(studentId);
            family.setSchoolId(stu.getSchid());
            family.setLeaveSchool(leaveSchool);
            familyList.add(family);
        }

        baseStudentFamilyDao.updateFamilyByStudent(familyList);
        
        // 更新家长用户所属学校信息
        List<Family> families = getFamiliesByStudentId(studentIds);
        if (families.size() == 0) {
            return ;
        }

        String[] familyIds = new String[families.size()];
        String[] schoolIds = new String[families.size()];
        for (int i = 0; i < families.size(); i++) {
            Family family = families.get(i);
            familyIds[i] = family.getId();
            schoolIds[i] = family.getSchoolId();
        }

        baseUserService.updateUsersByOwner(User.FAMILY_LOGIN,familyIds, schoolIds);
    }

    public void updateFamilies(List<Family> familyList) {
        baseStudentFamilyDao.updateFamilies(familyList);
    }

    public Set<String> deleteFamilyByStudentIds(String[] studentIds) {
        Set<String> noDeleteStudentIdSet = new HashSet<String>();

        
        List<Family> families = getFamiliesByStudentId(studentIds);
        if (families.size() == 0) {
            return noDeleteStudentIdSet;
        }

        String[] familyIds = new String[families.size()];
        Map<String, String> studentFamilyMap = new HashMap<String, String>();
        for (int i = 0; i < families.size(); i++) {
            Family family = families.get(i);
            String familyId = family.getId();
            familyIds[i] = familyId;
            studentFamilyMap.put(familyId, family.getStudentId());
        }

        Set<String> noDeleteFamilyIdSet = deleteFamiliesByFamilyIds(familyIds,
                EventSourceType.LOCAL);
        for (String familyId : noDeleteFamilyIdSet) {
            String studentId = studentFamilyMap.get(familyId);
            if (null != studentId) {
                noDeleteStudentIdSet.add(studentId);
            }
        }
        return noDeleteStudentIdSet;
    }

    public Set<String> deleteFamiliesByFamilyIds(String[] familyIds, EventSourceType eventSource) {
        Set<String> noDeletedFamilyIds = null;
        if (eventSource == EventSourceType.LOCAL) {
            // 删除用户信息，如果有个人定购业务，则不允许删除
            noDeletedFamilyIds = baseUserService.deleteUsersByOwner(User.FAMILY_LOGIN,familyIds);
            Set<String> deletedFamilyIds = new HashSet<String>();
            for (String id : familyIds) {
                if (!(noDeletedFamilyIds.contains(id))) {
                    deletedFamilyIds.add(id);
                }
            }
            if (deletedFamilyIds.size() > 0) {
                baseStudentFamilyDao.deleteFamiliesByFamilyIds(deletedFamilyIds
                        .toArray(new String[0]), eventSource);
            }
            return noDeletedFamilyIds;
        } else {
            baseStudentFamilyDao.deleteFamiliesByFamilyIds(familyIds, eventSource);
            noDeletedFamilyIds = new HashSet<String>();
            return noDeletedFamilyIds;
        }
    }

	@Override
	public void updateFamily(String familyMobPho, String id) {
		baseStudentFamilyDao.updateFamily(familyMobPho,id);
	}

	@Override
	public Set<String> deleteDgFamiliesByFamilyIds(String[] familyIds,
			EventSourceType eventSource) {
		Set<String> noDeletedFamilyIds = null;
        if (eventSource == EventSourceType.LOCAL) {
            // 删除用户信息，如果有个人定购业务，则不允许删除
            noDeletedFamilyIds = baseUserService.deleteDgUsersByOwner(User.FAMILY_LOGIN,familyIds);
            Set<String> deletedFamilyIds = new HashSet<String>();
            for (String id : familyIds) {
                if (!(noDeletedFamilyIds.contains(id))) {
                    deletedFamilyIds.add(id);
                }
            }
            if (deletedFamilyIds.size() > 0) {
                baseStudentFamilyDao.deleteFamiliesByFamilyIds(deletedFamilyIds
                        .toArray(new String[0]), eventSource);
            }
            return noDeletedFamilyIds;
        } else {
            baseStudentFamilyDao.deleteFamiliesByFamilyIds(familyIds, eventSource);
            noDeletedFamilyIds = new HashSet<String>();
            return noDeletedFamilyIds;
        }
	}



}
