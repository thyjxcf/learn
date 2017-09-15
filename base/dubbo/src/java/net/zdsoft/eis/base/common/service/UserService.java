package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;

public interface UserService {

	// =====================单个用户=============================
	/**
	 * 取用户信息
	 * 
	 * @param userId
	 */
	public User getUser(String userId);
 	/**
	 * 根据id获取用户对象 含被删除的用户
	 * @param userId
	 * @return
	 */
	public User getUserWithDel(String userId) ;
	/**
	 * 根据登陆名，取得一个用户帐号对象
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByUserName(String loginName);

	/**
	 * 根据手机号码，取得多个用户帐号对象
	 * @param mobilePhone
	 * @return
	 */
	public List<User> getUserByMobilePhone(String mobilePhone, int ownerType);
	
	/**
	 * 得到单位负责人
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

	/**
	 * 生成新用户的相关信息
	 * 
	 * @param unitId
	 * @return
	 */
	public User getUserNew(String unitId);

	// =====================数值=============================

	/**
	 * 根据用户id取其所在部门id
	 * 
	 * @param userId
	 * @return
	 */
	public String getDeptIdByUserId(String userId);

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
	 * 获取可用的用户排序编号
	 * 
	 * @param unitId
	 * @return
	 */
	public Long getAvailableOrder(String unitId);

	/**
	 * 得到职工ids串关联用户数量
	 * 
	 * @param teacherIds
	 * @return
	 */
	public Integer[] getUserCount(String[] teacherIds);

	// =====================根据单位查询用户列表=============================
	/**
	 * 取得某个单位的教师用户帐号列表
	 * 
	 * @return
	 */
	public List<User> getUsers(String unitId);

	public List<User> getUsers(String unitId, Pagination page);
	/**
	 * 根据单位id分页获取用户信息，包含头像信息
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<User> getUsersByUnitId(String unitId);
	/**
	 * 根据部门id获取用户信息
	 * @param deptId
	 * @param page
	 * @return
	 */
	public List<User> getUsersByDeptId(String deptId);
	
	/**
	 * 根据部门id获取简单的用户信息
	 * @param deptId
	 * @return
	 */
	public List<User> getUsersByDeptIdSimple(String deptId);
	/**
	 * 根据部门id分页获取用户信息
	 * @param deptId
	 * @param page
	 * @return
	 */
	public List<User> getUsersByDeptId(String deptId, Pagination page);
	
	/**
	 * 获取维护过职务的部门人员
	 * @param unitClass
	 * @param deptId
	 * @return
	 */
	public List<User> getLeaderUsersByDeptId(Integer unitClass, String deptId);
	/**
	 * 根据职务id获取用户信息
	 * @param dutyId
	 * @return
	 */
	public List<User> getUsersByDutyId(String unitId, String dutyId);
	/**
	 * 根据职务id分页获取用户信息
	 * @param dutyId
	 * @param page
	 * @return
	 */
	public List<User> getUsersByDutyId(String unitId, String dutyId, Pagination page);

	/**
	 * 根据单位id和用户状态数组取得用户帐号列表
	 * 
	 * @param unitId
	 * @param marks
	 *            0-未审核1-正常2-锁定3-离职锁定
	 * @return
	 */
	public List<User> getUsers(String unitId, String[] marks);

	/**
	 * 得到部门所有用户，不包括管理员
	 * 
	 * @param unitId
	 * @param groupId
	 * 
	 * @return
	 */
	public List<User> getUsers(String unitId, String groupId);
	
	  /**
     * 根据用户状态数组得到部门用户，不包括管理员
     * 
     * @param groupId
     * @param marks
     *      0-未审核1-正常2-锁定3-离职锁定
     * @return
     */
    public List<User> getUsers(String[] marks,String groupId);

	/**
	 * 根据班级或部门返回用户id
	 * 
	 * @param ownerType
	 * @param groupId
	 * @param isContainAdmin
	 *            是否包含管理员
	 * @return
	 */
	public List<User> getUsersByGroupId(int ownerType, String groupId,
			boolean isContainAdmin);

	/**
	 * 取得单位下type类型用户列表
	 * 
	 * @param unitId
	 * @param type
	 * @return
	 */
	public List<User> getUsersByUnitAndType(String unitId, Integer type);
  /**
     * 根据单位id和真实姓名模糊查询教师用户
     * @param unitId
     * @param realName
     * @return
     */
    public List<User> getUsersByFaintness(String unitId, String realName);

	// =====================根据userId查询用户列表=============================
	/**
	 * 根据id串得到用户列表
	 * 
	 * @param userIds
	 * @return
	 */
	public List<User> getUsers(String[] userIds);

	/**
	 * 返回指定id串所标识的用户list，如果某id对应的用户已经删除则把真实名称置为‘姓名(已删除)’
	 * 
	 * @param userIds
	 * @return
	 */
	public List<User> getUsersWithDel(String[] userIds);

	// =====================根据owner_id查询用户列表=============================
	/**
	 * 取得关联该职工的用户
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<User> getUsersByOwner(String teacherId);

	/**
	 * 取得关联角色的用户
	 * 
	 * @param teacherIds
	 * @return
	 */
	public List<User> getUsersByOwner(int ownerType, String[] ownerIds);
 	/**
     * 取得关联该职工的用户
     * 
     * @param teacherId
     * @param marks
     *      0-未审核1-正常2-锁定3-离职锁定
     * @return
     */
    public List<User> getUsersByOwner(int ownerType, String[] ownerIds,String[] marks);

	// =====================Map=============================

    /**
     * 根据用户guid得到用户map
     * 
     * @param userIds
     * @return
     */
    public Map<String, User> getUsersMap(String[] userIds);
    
    /**
     * 取用户map含删除的用户
     * @param userIds
     * @return
     */
    public Map<String, User> getUserWithDelMap(String[] userIds);
    
    /**
     * 取用户map含删除的用户
     * @param unitId
     * @return
     */
    public Map<String, User> getUserWithDelMap(String unitId);

	/**
	 * 根据用户登陆名得到用户map
	 * 
	 * @param userNames
	 *            登陆名的数组
	 * @return key=userName
	 */
	public Map<String, User> getUsersMapByName(String[] userNames);

	/**
	 * 取得用户信息
	 * 
	 * @param ownerIds
	 * @return key=ownerId
	 */
	public Map<String, User> getUserMapByOwner(int ownerType, String[] ownerIds);

	/**
	 * 取得某个单位的教师用户账号
	 * 
	 * @param unitId
	 * @return key=userId
	 */
	public Map<String, User> getUserMap(String unitId);

	/**
	 * 根据单位Ids返回该单位管理员map
	 * 
	 * @param unitIds
	 * @return key=unitId
	 */
	public Map<String, User> getAdminUserMap(String[] unitIds);
	
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
	 * 获取单位下面的部门人员信息
	 * @param unitId
	 * @return
	 */
	public Map<String, List<User>> getDeptUsersMap(String unitId);
	
	/**
	 * 根据用户ids获取组装的names信息，通讯录使用
	 * @param ids
	 * @return
	 */
	public String getUserDetailNamesStr(String[] ids);
	
	/**
	 * 根据用户ids获取组装的names信息，通讯录使用
	 * @param ids
	 * @return key userId
	 */
	public Map<String,String> getUserDetailNamesMap(String[] ids);
	
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
	 * 根据deptIds获得用户
	 * @param deptIds
	 * @return
	 */
	public List<User> getUsersByDeptIds(String[] deptIds);
	
	/**
	 * 根据deptids获得部门领导
	 * @param unitClass
	 * @param deptLeaderIds
	 * @return
	 */
	public List<User> getLeaderUsersByDeptIds(Integer unitClass,
			String[] deptLeaderIds);
	
	/**
	 * 返回不能删除的ownerId集合（因为要对接网校、微课、在线学习等，一旦被使用了，用户就不允许再删除）
	 * @param ownerType
	 * @param ownerIds
	 * @return
	 */
	public List<String> getUnremovableOwnerIds(int ownerType, String... ownerIds);
	
	/**
	 * 校验数据是否可删除
	 * @param ownerType 
	 * @param ownerIds 
	 * @return 
	 *   key        value
	 * "noIds" -> String[] 不能被删除的ownerIds
	 * "yesIds" -> String[] 可以被删除的ownerIds
	 * "names" -> String 不能被删除的ownerNames中间逗号隔开
	 * "msg" -> String 默认提示语
	 */
	public Map<String,Object> getVerifyDelete(int ownerType, String... ownerIds);
	
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
	 * 根据单位ID获取用户，key为单位ID
	 * @param unitIds
	 * @return
	 */
	public Map<String, List<User>> getUserMapByUnitIds(String[] unitIds);
}
