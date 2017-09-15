package net.zdsoft.office.jtgoout.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.service.GooutStudentExService;
import net.zdsoft.office.jtgoout.dao.GooutStudentExDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
/**
 * goout_student_ex
 * @author 
 * 
 */
public class GooutStudentExServiceImpl implements GooutStudentExService{
	private GooutStudentExDao gooutStudentExDao;
	private UserService userService;
	private DeptService deptService;
	private TeacherService teacherService;

	@Override
	public GooutStudentEx save(GooutStudentEx gooutStudentEx){
		return gooutStudentExDao.save(gooutStudentEx);
	}

	@Override
	public Integer delete(String[] ids){
		return gooutStudentExDao.delete(ids);
	}

	@Override
	public void deleteByJtGoOutId(String jtGooutId) {
		gooutStudentExDao.deleteByJtGoOutId(jtGooutId);
	}

	@Override
	public Integer update(GooutStudentEx gooutStudentEx){
		return gooutStudentExDao.update(gooutStudentEx);
	}

	@Override
	public GooutStudentEx getGooutStudentExById(String id){
		return gooutStudentExDao.getGooutStudentExById(id);
	}

	@Override
	public Map<String, GooutStudentEx> getGooutStudentExMapByIds(String[] ids){
		return gooutStudentExDao.getGooutStudentExMapByIds(ids);
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExList(){
		return gooutStudentExDao.getGooutStudentExList();
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExPage(Pagination page){
		return gooutStudentExDao.getGooutStudentExPage(page);
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExByUnitIdList(String unitId){
		return gooutStudentExDao.getGooutStudentExByUnitIdList(unitId);
	}
	
	public List<GooutStudentEx> getGooutStudentExListByjtId(String[] jtGoOutIds){
		return gooutStudentExDao.getGooutStudentExListByjtId(jtGoOutIds);
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExByUnitIdPage(String unitId, Pagination page){
		return gooutStudentExDao.getGooutStudentExByUnitIdPage(unitId, page);
	}

	@Override
	public GooutStudentEx getGooutStudentExByJtGoOutId(String jtGoOutId) {
		GooutStudentEx gooutStudentEx=gooutStudentExDao.getGooutStudentExByJtGoOutId(jtGoOutId);
		Map<String,User> userMap=userService.getUserMap(gooutStudentEx.getUnitId());
		Map<String,Dept> deptMap=deptService.getDeptMap(gooutStudentEx.getUnitId());
		
		int i = 0;
		if(gooutStudentEx!=null&&StringUtils.isNotBlank(gooutStudentEx.getOtherTeacherId())){
			String[] teacherIds=StringUtils.split(gooutStudentEx.getOtherTeacherId(),",");
			
			Set<String> teacherSet=new HashSet<String>();
			for(String string: teacherIds){
				User user=userMap.get(string);
				if(user!=null){
					teacherSet.add(user.getTeacherid());
				}
			}
			
			Map<String,Teacher> teacherMap=teacherService.getTeacherMap(teacherSet.toArray(new String[0]));
			StringBuffer sbNames=new StringBuffer();
			for (String string : teacherIds) {
				User user=userMap.get(string);
				Dept dept=null;
				if(teacherMap.containsKey(user.getTeacherid())){
					Teacher teacher=teacherMap.get(user.getTeacherid());
					if(teacher!=null&&deptMap.containsKey(teacher.getDeptid())){
						dept=deptMap.get(teacher.getDeptid());
					}
				}
				if(i==0){
					if(user!=null){
						sbNames.append(user.getRealname());
					}
					if(user!=null&dept!=null){
						sbNames.append("("+dept.getDeptname()+")");
					}
				}else{
					if(user!=null){
						sbNames.append(","+user.getRealname());
					}
					if(user!=null&&dept!=null){
						sbNames.append("("+dept.getDeptname()+")");
					}
				}
				i++;
			}
			gooutStudentEx.setOtherTeacherNames(sbNames.toString());
		}
		User user=userMap.get(gooutStudentEx.getActivityLeaderId());
		if(user!=null){
			gooutStudentEx.setActivityLeaderName(user.getRealname());
		}
		User user2=userMap.get(gooutStudentEx.getLeadGroupId());
		if(user2!=null){
			gooutStudentEx.setLeadGroupName(user2.getRealname());
		}
		return gooutStudentEx;
	}

	public void setGooutStudentExDao(GooutStudentExDao gooutStudentExDao){
		this.gooutStudentExDao = gooutStudentExDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExByUnitIdJtids(String unitId,
			String[] jtIds) {
		// TODO Auto-generated method stub
		return gooutStudentExDao.getGooutStudentExByUnitIdJtids(unitId, jtIds);
	}
	
}
