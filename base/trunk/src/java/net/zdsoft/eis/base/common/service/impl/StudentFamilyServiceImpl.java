package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.dao.StudentFamilyDao;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.FamilyUser;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.leadin.util.PWD;

public class StudentFamilyServiceImpl implements StudentFamilyService {
	private StudentFamilyDao studentFamilyDao;
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setStudentFamilyDao(StudentFamilyDao studentFamilyDao) {
		this.studentFamilyDao = studentFamilyDao;
	}

	// ====================以上是set=======================

	public Family getFamily(String familyId) {
		return studentFamilyDao.getFamily(familyId);
	}

	public List<Family> getFamilies(String[] familyIds) {
		return studentFamilyDao.getFamilies(familyIds);
	}

	public List<Family> getFamiliesByStudentId(String studentId) {
		return studentFamilyDao.getFamiliesByStudentId(studentId);
	}
	
	public List<Family> getFamiliesByStuIdRelations(String studentId, String[] relations){
		return studentFamilyDao.getFamiliesByStudentId(studentId, relations);
	}

	public List<Family> getFamiliesByStudentId(String[] studentIds){
	    return studentFamilyDao.getFamiliesByStudentId(studentIds);
	}
	
	public List<Family> getFamilies(String studentId, String phoneNum) {
		return studentFamilyDao.getFamilies(studentId, phoneNum);
	}

	public Map<String, List<Family>> getFamilyMap(String[] studentIds) {

		Map<String, List<Family>> map = new HashMap<String, List<Family>>();
		List<Family> stuFamilyList = new ArrayList<Family>();
		List<Family> list = studentFamilyDao.getFamiliesByStudentId(studentIds);
		if (list.size() > 0) {
			String preStuid = "";// 初始化
			Family family = (Family) list.get(0);
			preStuid = family.getStudentId();

			for (Object object : list) {
				family = (Family) object;
				String stuid = family.getStudentId();

				if (preStuid.equals(stuid)) {
					stuFamilyList.add(family);
				} else {
					map.put(preStuid, stuFamilyList);
					stuFamilyList = new ArrayList<Family>();
					stuFamilyList.add(family);
				}
				preStuid = stuid;
			}

			// 最后一项
			map.put(preStuid, stuFamilyList);
		}
		return map;
	}

	public Map<String, List<FamilyUser>> getFamilyUserMapByStuIds(
			String[] stuIds) {
		Map<String, List<FamilyUser>> map = new HashMap<String, List<FamilyUser>>();

		if (stuIds == null || stuIds.length == 0)
			return map;

		// 得到学生家长的映射
		Map<String, List<Family>> familyMap = getFamilyMap(stuIds);

		if (familyMap == null || familyMap.isEmpty())
			return map;

		// 得到家长的用户映射
		List<String> familyIds = new ArrayList<String>();
		for (Iterator<Map.Entry<String, List<Family>>> itr = familyMap
				.entrySet().iterator(); itr.hasNext();) {
			List<Family> list = itr.next().getValue();
			if (list != null && !list.isEmpty())
				for (Family family : list)
					familyIds.add(family.getId());
		}
		Map<String, User> userMap = userService.getUserMapByOwner(User.FAMILY_LOGIN,familyIds
				.toArray(new String[0]));

		// 迭代学生家长映射，构造最终的映射
		for (Iterator<Map.Entry<String, List<Family>>> itr = familyMap
				.entrySet().iterator(); itr.hasNext();) {
			Map.Entry<String, List<Family>> entry = itr.next();
			List<Family> list = entry.getValue();
			String studentId = entry.getKey();

			if (null == list || list.isEmpty()) {
				map.put(studentId, new ArrayList<FamilyUser>());
			} else {
				List<FamilyUser> userDtoList = new ArrayList<FamilyUser>();
				for (Family family : list) {
					FamilyUser familyUser = new FamilyUser();
					familyUser.setId(family.getId());
					familyUser.setFamilyName(family.getName());
					familyUser.setRelation(family.getRelation());
					familyUser.setMobile(family.getMobilePhone());
					familyUser.setStudentId(family.getStudentId());
					if(StringUtils.isBlank(family.getChargeNumber())){
						familyUser.setChargeNumber(family.getMobilePhone());
					}else{
						familyUser.setChargeNumber(family.getChargeNumber());
					}
					familyUser.setMobile(family.getMobilePhone());
					familyUser.setEmail(family.getEmail());
					User user = userMap.get(family.getId());
					if (user != null) {
						familyUser.setUserId(user.getId());
						familyUser.setFamilyLoginName(user.getName());
						familyUser.setFamilyPassword(user.findClearPassword());
					}

					userDtoList.add(familyUser);
				}

				map.put(studentId, userDtoList);
			}
		}

		return map;
	}

	public Map<String, List<FamilyUser>> getFamilyUserMapByStuIdsPW(
			String[] stuIds) {
		Map<String, List<FamilyUser>> map = new HashMap<String, List<FamilyUser>>();
		if (stuIds == null || stuIds.length == 0)
			return map;

		// 得到学生家长的映射
		Map<String, List<Family>> familyMap = getFamilyMap(stuIds);

		if (familyMap == null || familyMap.isEmpty())
			return map;

		// 得到家长的用户映射
		List<String> familyIds = new ArrayList<String>();
		for (Iterator<Map.Entry<String, List<Family>>> itr = familyMap
				.entrySet().iterator(); itr.hasNext();) {
			List<Family> list = itr.next().getValue();
			if (list != null && !list.isEmpty())
				for (Family family : list)
					familyIds.add(family.getId());
		}
		Map<String, User> userMap = userService.getUserMapByOwner(User.FAMILY_LOGIN,familyIds
				.toArray(new String[0]));

		// 迭代学生家长映射，构造最终的映射
		for (Iterator<Map.Entry<String, List<Family>>> itr = familyMap
				.entrySet().iterator(); itr.hasNext();) {
			Map.Entry<String, List<Family>> entry = itr.next();
			List<Family> list = entry.getValue();
			String studentId = entry.getKey();

			if (null == list || list.isEmpty()) {
				map.put(studentId, new ArrayList<FamilyUser>());
			} else {
				List<FamilyUser> userDtoList = new ArrayList<FamilyUser>();
				for (Family family : list) {
					FamilyUser familyUser = new FamilyUser();
					familyUser.setId(family.getId());
					familyUser.setFamilyName(family.getName());
					familyUser.setRelation(family.getRelation());
					familyUser.setMobile(family.getMobilePhone());
					familyUser.setStudentId(family.getStudentId());
					familyUser.setIdentitycard(family.getIdentityCard());
					
					User user = userMap.get(family.getId());
					if (user != null) {
						familyUser.setFamilyLoginName(user.getName());
						familyUser.setFamilyPassword(PWD.decode(user
								.findClearPassword()));
						familyUser.setStudentId(user.getId());
					}

					userDtoList.add(familyUser);
				}

				map.put(studentId, userDtoList);
			}
		}
		return map;
	}

	@Override
	public List<Family> getFamiliesByUnit(String unitId) {
		return studentFamilyDao.getFamiliesByUnit(unitId);
	}
}
