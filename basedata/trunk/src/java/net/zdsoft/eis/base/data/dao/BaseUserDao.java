package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;


import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.keel.util.Pagination;

/**
 * @author yanb
 * 
 */
public interface BaseUserDao {

    // ==========================维护=====================
    /**
     * 保存用户信息
     * 
     * @param users
     */
    public void insertUsers(User[] users);

    /**
     * 批量删除users 软删除
     * 
     * @param ids
     * @param eventSource 是否发送消息
     */
    public void deleteUsers(String[] ids, EventSourceType eventSource);
    
    /**
     * 批量删除users 硬删除
     * @param ids
     */
    public void deleteUsers(String[] ids) ;

    /**
     * 更新用户信息
     * 
     * @param users
     */
    public void updateUsers(User[] users);

    /**
     * 更新用户
     * 
     * @param user
     */
    public void updateUser(User user);

    /**
     * 新增user
     * 
     * @param user
     */
    public void insertUser(User user);

    /**
     * 更新passport返回的数据，目前主要是sequenceId
     * @param users
     */
    public void updateUserForPassport(User... users);
    
    /**
     * 批量更新用户状态
     * 
     * @param ids
     */
    public void updateUserMark(String[] ids, int mark);

    /**
     * 批量设置用户状态
     * 
     * @param ownerIds
     * @param state
     */
    public void updateUserMarkByOwner(String[] ownerIds, int state);

    /**
     * 用户密码初始化
     * 
     * @param ids
     * @param passwordInit
     */
    public void updateUserPassword(String[] ids, String passwordInit);

    /**
     * 设置用户密码
     * 
     * @param Map <String,String> <用户ID,密码)>
     */
    public void updateUserPassword(Map<String, String> map);

    /**
     * 锁定单位下所有用户
     * 
     * @param unitId
     */
    public void updateUserLock(String unitId);

    // ===================与用户对应的学生、教师、家长信息=============
    /**
     * 取用户对应的实体对象信息
     * 
     * @param studentIds
     * @return
     */
    public List<User> getStudentUsers(String... studentIds);

    // ==========================数值=====================
    /**
     * 获取该邮箱数量
     * 
     * @param email
     * @return
     */
    public boolean isExistsEmail(String email);

    /**
     * 统计该登录名user的总数
     * 
     * @param loginName
     * @return
     */
    public int getUserNameCount(String loginName);
    
    /**
     * 统计单位user的总数
     * @param unitId
     * @param ownerType
     * @param userType
     * @return
     */
    public int getUserCount(String unitId,int ownerType,int userType);

    // ==========================一般查询=====================

    /**
     * 根据单位id取单位下的管理员用户
     * 
     * @param unitIds 单位id
     * @return
     */
    public List<User> getAdminUsers(String[] unitIds);
    
    /**
     * 根据用户Names获取用户列表
     * @param userNames
     * @return
     */
    public List<User> getUsersByUserNames(String... userNames);

    /**
     * 得到整个系统中用户表中的account_id为null的用户列表
     * 
     * @return
     */
    public List<User> getUsersWithAccountNull();

    // ==========================模糊般查询=====================
    /**
     * 根据用户登录名，搜索unitid单位用户
     * 
     * @param userName
     * @param unitId
     * @return
     */
    public List<User> getUsersFaintness(String userName, String unitId);
    
    

    /**
     * 根据用户登录名，搜索用户(不限制所在单位)
     * 
     * @param userName
     * @return
     */
    public List<User> getUsersFaintness(String userName);

    /**
     * 根据登录名或真实姓名取用户对象
     * 
     * @param unitId
     * @param ownerType
     * @param userTypes 用户类型，为空时不作为过滤条件。不为空时如果多个以,分开
     * @param userName 登录名
     * @param realName 真实姓名
     * @return
     */
    public List<User> getUsersFaintness(String unitId, int ownerType, String userTypes, String userName,
            String realName, Pagination page);

    // ==========================Map=====================
    /**
     * 取单位管理员
     * @param unitids 
     * 
     * @return
     */
    public Map<String, User> getUnitAdmins(String[] unitids);

    /**
     * 获取用户名称数量列表
     * 
     * @param userNames
     * @return
     */
    public Map<String, Integer> getUserNameCount(String[] userNames);
    
    /**
     * 更新用户拼音
     * 
     * @param users
     */
    public void updateUsersPinYin(List<User> users);

	public void updateUsersMobilePhoneByOwnerId(String familyMobPho, String ownerId);

	    /**
     * 获取单位所有用户信息，包括所有角色
     * @param unitIds
     * @return
     */
    public List<User> getUsersByUnitIds(String[] unitIds);
    /**
     * 获取用户信息根据用户名模糊查询  
     * @param unitId
     * @param ownerType
     * @param userName
     * @return
     */
    public Map<String , User> getUserByName(String unitId, int ownerType , String userName );
    /**
	 * 定制删除家长信息的时候账号不删除只做情况user表的手机号码
	 * @param ownerIds
	 */
	public void updateUserMobilePhone(String... ownerIds);
	/**
	 * 获取班级学生用户名流水号最大值 （学校版）
	 * @param unitId
	 * @param classCode
	 * @return
	 */
	public int getUserNameMaxCodeByClassCode(String unitId, String classCode);
}
