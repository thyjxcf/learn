package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;

public interface UserDao {
	// =====================单个用户=============================
	/**
	 * 根据ID得到用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public User getUser(String userId);

	/**
	 * 根据id获取用户对象 含被删除的用户
	 * @param userId
	 * @return
	 */
	public User getUserWithDel(String userId) ;

	/**
	 * 根据用户登陆名取得一个用户对象
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByUserName(String loginName);
	
	/**
	 * 根据手机号码取得一个用户对象
	 * @param mobilePhone
	 * @return
	 */
	public List<User> getUserByMobilePhone(String mobilePhone, int ownerType);

	/**
	 * 得到单位管理员
	 * 
	 * @param unitId
	 * @return
	 */
	public User getUnitAdmin(String unitId);

	/**
	 * 取顶级管理员用户
	 */
	public User getTopUser();

	/**
	 * 根据account_id得到用户
	 * 
	 * @param accountId
	 * @return 如果不存在，则返回null
	 */
	public User getUserByAccountId(String accountId);

	// =====================数量=============================
	/**
	 * 获取单位下未关联职工用户数量,不包括管理员在内
	 * 
	 * @param unitId
	 * @return
	 */
	public Integer getCommonUserCount(String unitId);

	/**
	 * 获取用户名称的数量
	 * 
	 * @param userName
	 * @return
	 */
	public Integer getUserNameCount(String userName);

	/**
	 * 获取unitId单位的可用orderid
	 * 
	 * @param unitId
	 * @return
	 */
	public Long getAvailableOrder(String unitId);

	/**
	 * 得到该职工关联用户数量
	 * 
	 * @param teacherIds
	 * @return
	 */
	public Integer[] getUserCount(String[] teacherIds);

	// =====================列表=============================
	/**
	 * 取得某个单位的用户帐号列表
	 * 
	 * @param unitId
	 * @param ownerType
	 * @return
	 */
	public List<User> getUsers(String unitId, int ownerType);

	public List<User> getUsers(String unitId, int ownerType, Pagination page);

	/**
	 * 根据单位id和用户状态数组取得用户帐号列表，普通教师，不含管理员
	 * 
	 * @param unitId
	 * @param marks
	 *            0-未审核1-正常2-锁定3-离职锁定
	 * @return
	 */
	public List<User> getUsers(String unitId, String marks[]);

	/**
	 * 用户列表
	 * 
	 * @param userIds
	 * @return
	 */
	public List<User> getUsers(String[] userIds);

	/**
	 * 返回指定id串所标识的用户list，包括已软删除的用户
	 * 
	 * @param userIds
	 * @return
	 */
	public List<User> getUsersWithDel(String[] userIds);
	
	/**
	 * 
	 * @param unitId
	 * @return
	 */
	public List<User> getUsersWithDel(String unitId);

	/**
	 * 取得该职工关联用户
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<User> getUsersByOwner(String teacherId);

	/**
	 * 取得该单位用户列表
	 * 
	 * @param unitId
	 * @return
	 */
	public List<User> getUsersByUnitId(String unitId);

	/**
	 * 取得关联角色的用户
	 * 
	 * @param teacherIds
	 * @return
	 */
	public List<User> getUsersByOwner(int ownerType, String[] ownerIds);
	
	/**
	 * 取得关联角色的用户
	 * 
	 * @param teacherIds
	 * @return
	 */
	public List<User> getUsersByOwner(int ownerType, String[] ownerIds, Pagination page);

	/**
	 * 根据帐户状态取得关联角色的用户
	 * 
	 * @param ownerType
	 * @param ownerIds
	 * @param marks
	 *            0-未审核1-正常2-锁定3-离职锁定
	 * @return
	 */
	public List<User> getUsersByOwner(int ownerType, String[] ownerIds,
			String[] marks);

	/**
	 * 取得关联角色的用户
	 * 
	 * @param teacherIds
	 * @return
	 */
	public List<User> getUsersByOwnerWithoutAdmin(int ownerType,
			String[] ownerIds);

	/**
	 * 取得某个单位的用户帐号列表
	 * 
	 * @param unitId
	 *            单位id
	 * @param type
	 *            用户类型
	 * @return
	 */
	public List<User> getUsersByUnitAndType(String unitId, int type);

	/**
	 * 根据单位id和真实姓名模糊查询教师用户
	 * 
	 * @param unitId
	 * @param realName
	 * @return
	 */
	public List<User> getUsersByFaintness(String unitId, String realName);

	// =====================Map=============================

	/**
	 * 取用户map
	 * 
	 * @param userIds
	 * @return key=userId
	 */
	public Map<String, User> getUserMap(String[] userIds);

	/**
	 * 取得某个单位的用户账号
	 * 
	 * @param unitId
	 * @param ownerType
	 * @return key=userId
	 */
	public Map<String, User> getUserMap(String unitId, int ownerType);
	
	/**
	 * 取得某个单位教师对应的用户Map
	 * 
	 * @param unitId
	 * @param ownerType
	 * @return key=teacherId
	 */
	public Map<String, User> getTeacherUserMap(String unitId, int ownerType);

	/**
	 * 根据单位Id得到单位管理员列表
	 * 
	 * @param unitIds
	 * @return key=unitId
	 */
	public Map<String, User> getAdminUserMap(String[] unitIds);

	/**
	 * 根据用户登陆名数组取得用户对象
	 * 
	 * @param loginName
	 * @return key=userName
	 */
	public Map<String, User> getUsersMapByName(String names[]);

	/**
	 * 取得用户信息
	 * 
	 * @param ownerIds
	 * @return key=ownerId
	 */
	public Map<String, User> getUserMapByOwner(int ownerType, String[] ownerIds);

	/**
	 * 获取用户
	 * @param realName
	 * @param unitId
	 * @return
	 */
	public List<User> getUsersFaintness(String realName, String unitId);
	
		/**
	 * 获取user list 分页
	 * @param unitId
	 * @param realName TODO
	 * @param ownerType
	 * @param page
	 * @return
	 */
	public List<User> getUserListByUnitId(String unitId, String realName, int ownerType, Pagination page);

	/**
	 * 根据姓名模糊查询用户
	 * @param searchName
	 * @param page
	 * @return
	 */
	public List<User> getUsersBySearchName(String searchName, Pagination page);
	
	/**
	 * 获取未设置拼音的人员及修改过姓名的人员（默认日期为当前日期前两天有修改动作）
	 * @return
	 */
	public List<User> getUsersWithOutPinYin();
	
	/**
	 * 拼音检索
	 * @param unitId
	 * @param searchName
	 * @return
	 */
	public List<User> getUsersBySearchName(String unitId, String searchName, Pagination page);
	
	/**
	 * 查找用户
	 * @param realName 真实姓名
	 * @param mobilePhone 手机号码
	 * @param ownerType 身份类型
	 * @return
	 */
	public List<User> getUsersByRealNameMobile(String realName, String mobilePhone, int ownerType);
	
	/**
	 * 获取用户
	 * @param realName
	 * @param unitId
	 * @return
	 */
	public List<User> getUsersByUnitIdAndName(String realName, String unitId);
	
	/**
	 * 根据unitIds获取用户
	 * @param unitIds
	 * @return
	 */
	public List<User> getUsersByUnitIds(String[] unitIds);
	
	/**
	 * 根据deptIds获取用户
	 * @param deptIds
	 * @return
	 */
	public List<User> getUsersByDeptIds(String[] deptIds);
	
	
	/**
	 * 根据单位ID获取用户，key为单位ID
	 * @param unitIds
	 * @return
	 */
	public Map<String, List<User>> getUserMapByUnitIds(String[] unitIds);
}
