package net.zdsoft.eis.base.data.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.dao.EisBaseStudentDao;
import net.zdsoft.eis.base.data.entity.EisBaseStudent;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.EisBaseStudentService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.simple.service.impl.AbstractStudentServiceImpl;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.common.dao.SystemCommonDao;
import net.zdsoft.leadin.exception.OperationNotAllowedException;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.lang.StringUtils;

public class EisBaseStudentServiceImpl extends AbstractStudentServiceImpl<EisBaseStudent> implements EisBaseStudentService{

	protected EisBaseStudentDao eisBaseStudentDao;
	private SystemCommonDao systemCommonDao; // 通用的处理类
	private SystemDeployService systemDeployService;
	private BaseUserService baseUserService;
	private PassportAccountService passportAccountService;
	
	public EisBaseStudentDao getStudentDao() {
		return eisBaseStudentDao;
	}

	public void setEisBaseStudentDao(EisBaseStudentDao eisBaseStudentDao) {
		this.eisBaseStudentDao = eisBaseStudentDao;
	}

	public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
		this.systemCommonDao = systemCommonDao;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

	@Override
	public void addStudent(EisBaseStudent student){
		eisBaseStudentDao.addStudent(student);
	}
	
	@Override
	public void updateStudent(EisBaseStudent student){
		eisBaseStudentDao.updateStudent(student);
	}
	
	@Override
	public void deleteStudents(String[] ids){
		eisBaseStudentDao.deleteStudents(ids);
	}
	
	@Override
	public EisBaseStudent getStudentByStuId(String stuId){
		return eisBaseStudentDao.getStudentByStuId(stuId);
	}

	@Override
	public List<EisBaseStudent> getStudentsByClassId(String clsId){
		return eisBaseStudentDao.getStudentsByClassId(clsId);
	}
	
	@Override
	public void insertStudent(EisBaseStudent student){
		this.addStudent(student);
		this.insertUser(student);
	}
	
	public void insertUser(EisBaseStudent student){
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlValue = new StringBuffer();
		sql.append(" insert into base_user(id, owner_type, real_name, owner_id, unit_id,creation_time,user_type, user_state, is_deleted,event_source, modify_time,display_order,region_code,sex,email");
		
		// 账号
		if (StringUtils.isNotBlank(student.getUserName())) {
			sql.append(",username");
			sqlValue.append(",'" + student.getUserName() + "'");
		}
		// 密码
		if (StringUtils.isNotBlank(student.getPassword())) {
			sql.append(",password");
			sqlValue.append(",'" + student.getPassword() + "'");
		}
		
		String userId = UUIDGenerator.getUUID();
		
		sql.append(") values ('")
		.append(userId)
		.append("',")
		.append(User.STUDENT_LOGIN)
		.append(",'")
		.append(student.getStuname())
		.append("','")
		.append(student.getId())
		.append("','")
		.append(student.getSchid())
		.append("',sysdate, 2, ")
		.append("1")
		.append(",0,")
		.append(EventSourceType.LOCAL.getValue())
		.append(",sysdate")
		.append(",(select max(display_order)+1 from base_user where is_deleted = 0 and unit_id = '")
		.append(student.getSchid() + "')")
		.append(",'")
		.append(student.getRegionCode())
		.append("'")
		.append(",")
		.append(student.getSex())
		.append(",'")
		.append(StringUtils.isBlank(student.getEmail()) ? "" : student
				.getEmail()).append("'").append(sqlValue + ")");
		systemCommonDao.batchUpdate(new String[]{sql.toString()});
		
		// 数字校园进行帐户同步
		if (systemDeployService.isConnectPassport()) {
			try{
				User user = baseUserService.getUser(userId);
				user.setOwnerType(User.STUDENT_LOGIN);
				user.setAccountId(UUIDGenerator.getUUID());
				
				User[] retUsers = passportAccountService
						.addAccounts(new User[]{user});
				String[] sqls = new String[retUsers.length];
				int i = 0;
				for (User retuser : retUsers) {
					sqls[i] = "update base_user set owner_type = "
							+ retuser.getOwnerType() + ", account_id = '"
							+ retuser.getAccountId() + "',sequence = "
							+ retuser.getSequence() + ",event_source="
							+ EventSourceType.LOCAL.getValue()
							+ " where id = '" + retuser.getId() + "'";
					i++;
				}
				systemCommonDao.batchUpdate(sqls);
			}catch (OperationNotAllowedException pe){
				//出错则删除对应的用户信息和学生信息
				baseUserService.deleteUsers(new String[]{userId});
				this.deleteStudents(new String[]{student.getId()});
				throw new OperationNotAllowedException(pe.getMessage());
			}
		}
	}

	@Override
	public List<EisBaseStudent>  getStudentBySchoolIdName(String schoolId,
			String studentName) {
		// TODO Auto-generated method stub
		return this.eisBaseStudentDao.getStudentBySchoolIdName(schoolId, studentName);
	}
}
