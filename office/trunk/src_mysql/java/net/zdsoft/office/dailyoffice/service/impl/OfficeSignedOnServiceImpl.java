package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.impl.TeacherServiceImpl;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeSignedOnDao;
import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.office.dailyoffice.entity.OfficeSignedOn;
import net.zdsoft.office.dailyoffice.service.OfficeSignedOnService;
/**
 * office_signed_on
 * @author 
 * 
 */
public class OfficeSignedOnServiceImpl implements OfficeSignedOnService{
	private OfficeSignedOnDao officeSignedOnDao;
	private TeacherService teacherService;
	private UserService userService;
	private DeptService deptService;

	@Override
	public OfficeSignedOn save(OfficeSignedOn officeSignedOn){
		return officeSignedOnDao.save(officeSignedOn);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSignedOnDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSignedOn officeSignedOn){
		return officeSignedOnDao.update(officeSignedOn);
	}

	@Override
	public OfficeSignedOn getOfficeSignedOnById(String id){
		return officeSignedOnDao.getOfficeSignedOnById(id);
	}

	@Override
	public Map<String, OfficeSignedOn> getOfficeSignedOnMapByIds(String[] ids){
		return officeSignedOnDao.getOfficeSignedOnMapByIds(ids);
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnList(){
		return officeSignedOnDao.getOfficeSignedOnList();
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnPage(Pagination page){
		return officeSignedOnDao.getOfficeSignedOnPage(page);
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdList(String unitId){
		return officeSignedOnDao.getOfficeSignedOnByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdPage(String unitId, Pagination page){
		return officeSignedOnDao.getOfficeSignedOnByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdDeptPage(String year,
			String semester, String time, String deptId, String signed,
			String unitId, Pagination page) {
		//Set<String> userSet=new HashSet<String>();
		List<OfficeSignedOn> officeSigList=new ArrayList<OfficeSignedOn>();
		Set<String> ownId=new HashSet<String>();
		List<Teacher> teachList=teacherService.getTeachers(unitId, deptId, null, null);
		for (Teacher teacher: teachList) {
			ownId.add(teacher.getId());
		}
		Map<String, User> usertMap=userService.getUserMapByOwner(2, ownId.toArray(new String[0]));
		Map<String, OfficeSignedOn> officeSignedMap = new HashMap<String, OfficeSignedOn>();
		officeSigList=officeSignedOnDao.getOfficeSignedOnByOtherPage(null, unitId, year, semester, time, null);
		List<OfficeSignedOn> desList=new ArrayList<OfficeSignedOn>();
		if(CollectionUtils.isNotEmpty(officeSigList)){
			for (OfficeSignedOn officeSignedOn : officeSigList) {
				officeSignedMap.put(officeSignedOn.getCreateUserId(), officeSignedOn);
				}
			}
		if(StringUtils.isEmpty(signed)){
//		for (OfficeSignedOn officeSignedOn : officeSigList) {
//			userSet.add(officeSignedOn.getCreateUserId());
//		}
		//Map<String, User> userMap=userService.getUsersMap(userSet.toArray(new String[0]));
		for(Teacher teacher: teachList){
			boolean canNew=false;
			for(OfficeSignedOn officeSignedOn : officeSigList){
				if(usertMap.get(teacher.getId())!=null&&StringUtils.equals(officeSignedOn.getCreateUserId(), usertMap.get(teacher.getId()).getId())){
					canNew=true;
					break;
				}
			}
			OfficeSignedOn officeSignedOn=new OfficeSignedOn();
			User user=usertMap.get(teacher.getId());
			if(user!=null){
				officeSignedOn.setUserName(user.getRealname());
			}else{
				officeSignedOn.setUserName("用户已删除");
				
			}
			officeSignedOn.setDeptName(teacher.getDeptName());
			if(canNew){
				officeSignedOn.setCreateTime(officeSignedMap.get(usertMap.get(teacher.getId()).getId()).getCreateTime());
			}
			desList.add(officeSignedOn);
		}
			return desList;
		}if(StringUtils.equals("0", signed)){
			for(Teacher teacher: teachList){
				boolean canNew=true;
				for(OfficeSignedOn officeSignedOn : officeSigList){
					if(usertMap.get(teacher.getId())!=null&&StringUtils.equals(officeSignedOn.getCreateUserId(), usertMap.get(teacher.getId()).getId())){
						canNew=false;
						break;
					}
				}
				if(canNew){
				OfficeSignedOn officeSignedOn=new OfficeSignedOn();
				User user=usertMap.get(teacher.getId());
				if(user!=null){
					officeSignedOn.setUserName(user.getRealname());
				}else{
					officeSignedOn.setUserName("用户已删除");
					
				}
				officeSignedOn.setDeptName(teacher.getDeptName());
				desList.add(officeSignedOn);
				}
			}
			return desList;
		}if(StringUtils.equals("1", signed)){
			for(Teacher teacher: teachList){
				boolean canNew=false;
				for(OfficeSignedOn officeSignedOn : officeSigList){
					if(usertMap.get(teacher.getId())!=null&&StringUtils.equals(officeSignedOn.getCreateUserId(), usertMap.get(teacher.getId()).getId())){
						canNew=true;
						break;
					}
				}
				if(canNew){
				OfficeSignedOn officeSignedOn=new OfficeSignedOn();
				User user=usertMap.get(teacher.getId());
				if(user!=null){
					officeSignedOn.setUserName(user.getRealname());
				}else{
					officeSignedOn.setUserName("用户已删除");
					
				}
				officeSignedOn.setDeptName(teacher.getDeptName());
				officeSignedOn.setCreateTime(officeSignedMap.get(usertMap.get(teacher.getId()).getId()).getCreateTime());
				desList.add(officeSignedOn);
				}
			}
			return desList;
		}
		return desList;
	}
	
	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdTimePage(String userId,String year,
			String semester, String time, String unitId,Pagination page) {
		List<OfficeSignedOn> officeSigList= officeSignedOnDao.getOfficeSignedOnByOtherPage(userId, unitId, year, semester, time, null);
		if(CollectionUtils.isNotEmpty(officeSigList)){
			for (OfficeSignedOn officeSignedOn : officeSigList) {
				Dept dept=deptService.getDept(userService.getUser(userId).getDeptid());
				officeSignedOn.setDeptName(dept.getDeptname());
				officeSignedOn.setUserName(userService.getUser(userId).getRealname());
			}
		}else{
			Dept dept=deptService.getDept(userService.getUser(userId).getDeptid());
			OfficeSignedOn officeSignedOn=new OfficeSignedOn();
			officeSignedOn.setDeptName(dept.getDeptname());
			officeSignedOn.setUserName(userService.getUser(userId).getRealname());
			officeSigList.add(officeSignedOn);
		}
		return officeSigList;
	}
	
	@Override
	public List<OfficeSignedOn> getOfficeSignedOnCountByUnitIdDeptPage(
			String year, String semester, String startTime, String endTime,
			String deptId, String unitId, Pagination page) {
		List<OfficeSignedOn> officeSigList=new ArrayList<OfficeSignedOn>();
		Set<String> ownId=new HashSet<String>();
		List<Teacher> teachList=teacherService.getTeachers(unitId, deptId, null, page);
		for (Teacher teacher: teachList) {
			ownId.add(teacher.getId());
		}
		Map<String, User> usertMap=userService.getUserMapByOwner(2, ownId.toArray(new String[0]));
		Map<String, OfficeSignedOn> officeSignedMap = new HashMap<String, OfficeSignedOn>();
		officeSigList=officeSignedOnDao.getOfficeSignedOnCountByManager(null, unitId, year, semester, startTime, endTime, null);
		List<OfficeSignedOn> desList=new ArrayList<OfficeSignedOn>();
		if(CollectionUtils.isNotEmpty(officeSigList)){
			for (OfficeSignedOn officeSignedOn : officeSigList) {
				officeSignedMap.put(officeSignedOn.getCreateUserId(), officeSignedOn);
				}
			}
		for(Teacher teacher: teachList){
			List<OfficeSignedOn> countList=new ArrayList<OfficeSignedOn>();
			boolean canNew=false;
			for(OfficeSignedOn officeSignedOn : officeSigList){
				if(usertMap.get(teacher.getId())!=null&&StringUtils.equals(officeSignedOn.getCreateUserId(), usertMap.get(teacher.getId()).getId())){
					countList.add(officeSignedOn);
					canNew=true;
					continue;
				}
			}
			if(canNew){
			OfficeSignedOn officeSignedOn=new OfficeSignedOn();
			User user=usertMap.get(teacher.getId());
			if(user!=null){
				officeSignedOn.setUserName(user.getRealname());
			}else{
				officeSignedOn.setUserName("用户已删除");
				
			}
			officeSignedOn.setDeptName(teacher.getDeptName());
			officeSignedOn.setCount(String.valueOf(countList.size()));
			countList.clear();
			desList.add(officeSignedOn);
			}else{
				OfficeSignedOn officeSignedOn=new OfficeSignedOn();
				User user=usertMap.get(teacher.getId());
				if(user!=null){
					officeSignedOn.setUserName(user.getRealname());
				}else{
					officeSignedOn.setUserName("用户已删除");
					
				}
				officeSignedOn.setDeptName(teacher.getDeptName());
				desList.add(officeSignedOn);
			}
		}
		return desList;
	}
	
	@Override
	public List<OfficeSignedOn> getOfficeSignedOnCountByUnitIdTimePage(
			String userId, String year, String semester, String startTime,
			String endTime, String unitId,Pagination page) {
		List<OfficeSignedOn> officeSignedList=officeSignedOnDao.getOfficeSignedOnCountByManager(userId, unitId, year, semester, startTime, endTime, null);
		List<OfficeSignedOn> officeSignedCount=new ArrayList<OfficeSignedOn>();
		if(CollectionUtils.isNotEmpty(officeSignedList)){
			for (OfficeSignedOn officeSignedOn : officeSignedList) {
				Dept dept=deptService.getDept(userService.getUser(userId).getDeptid());
				officeSignedOn.setDeptName(dept.getDeptname());
				officeSignedOn.setUserName(userService.getUser(userId).getRealname());
				officeSignedOn.setCount(String.valueOf(officeSignedList.size()));
				officeSignedCount.add(officeSignedOn);
				break;
			}
		}else{
			Dept dept=deptService.getDept(userService.getUser(userId).getDeptid());
			OfficeSignedOn officeSignedOn=new OfficeSignedOn();
			officeSignedOn.setDeptName(dept.getDeptname());
			officeSignedOn.setUserName(userService.getUser(userId).getRealname());
			officeSignedOn.setCount("0");
			officeSignedCount.add(officeSignedOn);
		}
		return officeSignedCount;
	}
	
	@Override
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdTime(String userId,
			String year, String semester, String unitId,String time) {
		return officeSignedOnDao.getOfficeSignedOnByOtherPage(userId, unitId, year, semester, time, null);
	}
	public void setOfficeSignedOnDao(OfficeSignedOnDao officeSignedOnDao){
		this.officeSignedOnDao = officeSignedOnDao;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

}