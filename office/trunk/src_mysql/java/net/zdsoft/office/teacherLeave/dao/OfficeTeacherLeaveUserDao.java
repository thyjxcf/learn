package net.zdsoft.office.teacherLeave.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveUser;
/**
 * office_teacher_leave_user
 * @author 
 * 
 */
public interface OfficeTeacherLeaveUserDao{

	/**
	 * 新增office_teacher_leave_user
	 * @param officeTeacherLeaveUser
	 * @return
	 */
	public OfficeTeacherLeaveUser save(OfficeTeacherLeaveUser officeTeacherLeaveUser);
	
	/**
	 * 批量保存
	 * @param officeTeacherLeaveUsers
	 * @return
	 */
	public void batchSave(List<OfficeTeacherLeaveUser> officeTeacherLeaveUsers);

	/**
	 * 根据ids数组删除office_teacher_leave_user
	 * @param ids
	 * @return
	 */
	public Integer deleteByLeaveIds(String[] ids);
	
	/**
	 * 删除原先老的数据
	 * @param leaveId
	 */
	public void deleteByLeaveId(String leaveId);

	/**
	 * 更新office_teacher_leave_user
	 * @param officeTeacherLeaveUser
	 * @return
	 */
	public Integer update(OfficeTeacherLeaveUser officeTeacherLeaveUser);

	/**
	 * 根据id获取office_teacher_leave_user
	 * @param id
	 * @return
	 */
	public OfficeTeacherLeaveUser getOfficeTeacherLeaveUserById(String id);

	/**
	 * 根据ids数组查询office_teacher_leave_usermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserMapByIds(String[] ids);

	/**
	 * 获取office_teacher_leave_user列表
	 * @return
	 */
	public List<OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserList(String leaveId);

	/**
	 * 分页获取office_teacher_leave_user列表
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserPage(Pagination page);
}