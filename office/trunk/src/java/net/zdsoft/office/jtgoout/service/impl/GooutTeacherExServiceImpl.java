package net.zdsoft.office.jtgoout.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.jtgoout.dao.GooutTeacherExDao;
import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.office.jtgoout.service.GooutTeacherExService;

import org.apache.commons.lang.StringUtils;
/**
 * goout_teacher_ex
 * @author 
 * 
 */
public class GooutTeacherExServiceImpl implements GooutTeacherExService{
	private GooutTeacherExDao gooutTeacherExDao;
	private UserService userService;
	private DeptService deptService;
	private TeacherService teacherService;

	@Override
	public GooutTeacherEx save(GooutTeacherEx gooutTeacherEx){
		return gooutTeacherExDao.save(gooutTeacherEx);
	}

	@Override
	public Integer delete(String[] ids){
		return gooutTeacherExDao.delete(ids);
	}

	@Override
	public void deleteByjtGooutId(String jtGooutId) {
		gooutTeacherExDao.deleteByjtGooutId(jtGooutId);
	}

	@Override
	public Integer update(GooutTeacherEx gooutTeacherEx){
		return gooutTeacherExDao.update(gooutTeacherEx);
	}

	@Override
	public GooutTeacherEx getGooutTeacherExById(String id){
		return gooutTeacherExDao.getGooutTeacherExById(id);
	}

	@Override
	public GooutTeacherEx getGooutTeacherExByJtGooutId(String jtGooutId) {
		GooutTeacherEx gooutTeacherEx=gooutTeacherExDao.getGooutTeacherExByJtGooutId(jtGooutId);
		if(gooutTeacherEx!=null&&StringUtils.isNotBlank(gooutTeacherEx.getPartakePersonId())){
			Map<String,User> userMap=userService.getUserMap(gooutTeacherEx.getUnitId());
			Map<String,Dept> deptMap=deptService.getDeptMap(gooutTeacherEx.getUnitId());
			int i = 0;
			String[] teacherIds=StringUtils.split(gooutTeacherEx.getPartakePersonId(),",");
			
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
				if(i==0){
					if(user!=null){
						sbNames.append(user.getRealname());
						if(teacherMap.containsKey(user.getTeacherid())){
							Teacher teacher=teacherMap.get(user.getTeacherid());
							if(teacher!=null&&deptMap.containsKey(teacher.getDeptid())){
								dept=deptMap.get(teacher.getDeptid());
								if(dept!=null){
									sbNames.append("("+dept.getDeptname()+")");
								}
							}
						}
					}
				}else{
					if(user!=null){
						sbNames.append(","+user.getRealname());
						if(teacherMap.containsKey(user.getTeacherid())){
							Teacher teacher=teacherMap.get(user.getTeacherid());
							if(teacher!=null&&deptMap.containsKey(teacher.getDeptid())){
								dept=deptMap.get(teacher.getDeptid());
								if(dept!=null){
									sbNames.append("("+dept.getDeptname()+")");
								}
							}
						}
					}
				}
				i++;
			}
			gooutTeacherEx.setPartakePersonNames(sbNames.toString());
		}
		return gooutTeacherEx;
	}

	@Override
	public Map<String, GooutTeacherEx> getGooutTeacherExMapByIds(String[] ids){
		return gooutTeacherExDao.getGooutTeacherExMapByIds(ids);
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExList(){
		return gooutTeacherExDao.getGooutTeacherExList();
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExPage(Pagination page){
		return gooutTeacherExDao.getGooutTeacherExPage(page);
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdList(String unitId){
		return gooutTeacherExDao.getGooutTeacherExByUnitIdList(unitId);
	}
 
	public List<GooutTeacherEx> getGooutTeacherExListByjtId(String[] jtGoOutIds){
		return gooutTeacherExDao.getGooutTeacherExListByjtId(jtGoOutIds);
	}
	
	@Override
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdPage(String unitId, Pagination page){
		return gooutTeacherExDao.getGooutTeacherExByUnitIdPage(unitId, page);
	}

	public void setGooutTeacherExDao(GooutTeacherExDao gooutTeacherExDao){
		this.gooutTeacherExDao = gooutTeacherExDao;
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
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdJtIds(String unitId,
			String[] jtIds) {
		// TODO Auto-generated method stub
		return gooutTeacherExDao.getGooutTeacherExByUnitIdJtIds(unitId, jtIds);
	}
	
}
