package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.exception.BusinessErrorException;

/**
 * @author yanb
 * 
 */
public interface BaseUserService extends UserService {
    /**
     * 保存用户,同时设置用户状态为userMark
     * 
     * @param user
     * @param userMark
     * @return 如果该用户登录名已存在则返回false
     */
    public boolean saveUser(User user, Integer userMark) throws BusinessErrorException;
    
    /**
     * 保存用户FROMMq
     * @param userList
     */
    public void addUsersFromMq(List<User> userList);
    
    /**
     * 修改用户FROMMq
     * @param userList
     */
    public void updateUsersFromMq(List<User> userList);

    /**
     * 保存用户
     * 
     * @param users
     * @throws Exception
     */
    public void saveUsers(User[] users) throws Exception;

    /**
     * 保存用户
     * 
     * @param user
     * @param saveWithoutPassport
     * @return
     * @throws Exception
     */
    public void saveUserByOtherObject(User user, boolean saveWithoutPassport) throws Exception;

    /**
     * 保存用户
     * 
     * @param user 可以为空表示只修改教职工信息
     * @param teacher
     * @throws Exception
     */
    public void saveUserByTeacher(User user, BaseTeacher teacher) throws Exception;

    /**
     * 自注册用户,同时设置用户状态为userMark,contextURL为当前发布路径
     * 
     * @param user
     * @param userMark
     * @param contextURL
     * @return 如果该用户登录名已存在则返回false
     */
    public boolean saveUserRegister(User user, Integer userMark, String contextURL)
            throws Exception;

    /**
     * 批量软删除users
     * 
     * @param ids
     * @param eventSource 是否需要发送消息
     * @throws Exception
     */
    public String[] deleteUsers(String[] ids, EventSourceType eventSource)
            throws BusinessErrorException;
    
    /**
     * 根据所有者删除用户
     * 
     * @param ownerIds
     * @return 返回定购了商品，不能删除的ownerId
     * @throws BusinessErrorException 删除passport账号出错
     */
    public Set<String> deleteUsersByOwner(int ownerType,String[] ownerIds) throws BusinessErrorException;
    
    /**
     * 批量删除users 硬删除
     * @param ids
     */
    public void deleteUsers(String[] ids) ;

    /**
     * 更新用户 如果name有相同，则返回false
     * 
     * @param user
     * @throws Exception
     */
    public boolean updateUser(User user, boolean saveWithoutPassport) throws Exception;

    /**
     * 更新用户 如果name有相同，则返回false
     * 
     * @param user
     * @throws Exception
     */
    public boolean updateUser(User user) throws BusinessErrorException;

    /**
     * 由passport更新用户
     * 
     * @param user
     * @throws Exception
     */
    public void updateUserByPassport(User user) throws Exception;

    /**
     * 批量审核账号
     * 
     * @param ids
     * @throws Exception
     */
    public void updateUsersAudit(String[] ids) throws Exception;

    /**
     * 同步职工和用户的信息
     * 
     * @param teacher
     * @throws Exception
     */
    public void updateSynchronizeUser4Teacher(BaseTeacher teacher) throws Exception;

    /**
     * 批量锁定帐号ids
     * 
     * @param ids
     * @throws Exception
     */
    public void updateUsersLock(String[] ids) throws Exception;

    /**
     * 批量解锁帐号ids
     * 
     * @param ids
     * @throws Exception
     */
    public void updateUsersUnlock(String[] ids) throws Exception;

    /**
     * 用户密码清空
     * 
     * @param ids
     * @throws Exception
     */
    public void updateUserPasswordReset(String[] ids, UnitIni unitIni) throws Exception;

    /**
     * 用户密码设置成用户名
     * 
     * @param ids
     * @throws Exception
     */
    public Map<String, String> updateUserPasswordResetUsername(String[] ids) throws Exception;

    /**
     * 根据职工编号更新相关用户状态正常
     * 
     * @param teacherId
     * @throws Exception
     */
    public void updateUserAuditByTeacher(String teacherId) throws Exception;

    /**
     * 根据职工编号更新相关用状态离职锁定
     * 
     * @param teacherIds
     * @throws Exception
     */
    public void updateUserDimissionLockByTeacher(String[] teacherIds) throws Exception;

    /**
     * 根据所有者更新用户状态
     * 
     * @param ownerIds
     * @param mark
     * @throws BusinessErrorException
     */
    public void updateUserMarkByOwner(String[] ownerIds, int mark) throws BusinessErrorException;
    
    /**
     * 激活账号
     * 
     * @param userGuid
     * @throws Exception
     */
    public void updateUserActivation(String userGuid) throws Exception;

    /**
     * 锁定单位下所有用户
     * 
     * @param unitId
     */
    public void updateUserLockInUnit(String unitId);

    /**
     * 更新用户
     * 
     * @param users
     * @param unitIds
     */
    public void updateUsers(User[] users, String[] unitIds);

    /**
     * 保存user的chargeNumber信息，不更新passport
     * 
     * @param user
     */
    public void updateUserWithoutPassport(User user);

    /**
     * 修改用户关联的职工、学生或者家长信息（目前只包含邮箱、手机、个人首页）
     * 
     * @param user
     * @param coPassportInfo，是否调用保存passport的account信息
     * @return
     */
    public boolean updateUserRealInfo(User user, boolean coPassportInfo);

    /**
     * 修改用户密码
     * 
     * @param id
     * @param oldPassword
     * @param newPassword
     * @param coPassportInfo，是否更新到passport
     * @throws Exception
     */
    public void updatePassword(String id, String oldPassword, String newPassword,
            boolean coPassportInfo) throws Exception;

    /**
     * 根据所有者更新用户学校信息
     * @param ownerIds
     * @param schoolIds
     * @return
     */
    public void updateUsersByOwner(int ownerType,String[] ownerIds,String[] schoolIds);
    
    /**
     * 邮箱是否存在
     * 
     * @param email
     * @return
     */
    public boolean isExistsEmail(String email);

    /**
     * 获取用户名称数量列表
     * 
     * @param userNames
     * @return
     */
    public Map<String, Integer> getCountsByUserNames(String[] userNames);
    
    /**
     * 统计单位user的总数
     * @param unitId
     * @param ownerType
     * @param userType
     * @return
     */
    public int getUserCount(String unitId,int ownerType,int userType);

    /**
     * 获取单位管理员
     * 
     * @param unitIds
     * @return
     */
    public Map<String, User> getUnitAdmins(String[] unitIds);

    /**
     * 根据用户Ids得到用户名称，用“、”分隔
     * 
     * @param ids
     * @return
     */
    public String getUserNameByIds(String[] ids);

    /**
     * 根据用户名,如果已存在的话,提示一个当前可用的用户登录名 提示规则为原userName+"_"+(整数)
     * 
     * @param userName
     * @return
     */
    public String getAdviceUserName4Register(String userName);

    /**
     * 根据用户登录名称，搜索unitid单位下用户,restrict限制是否仅在unitid单位下搜索
     * 
     * @param userName
     * @param unitId
     * @param restrict
     * @return
     */
    public List<User> getUserSearchName(String userName, String unitId, boolean restrict);

    /**
     * 根据登录名或真实姓名取用户对象
     * 
     * @param unitId
     * @param ownerType
     * @param userTypes 用户类型，为空时不作为过滤条件。不为空时如果多个以,分开
     * @param userName 登录名
     * @param realName 真实姓名
     * @param page 分页 可以为空
     * @return
     */
    public List<User> getUserByNameRealName(String unitId, int ownerType, String userTypes,
            String userName, String realName, Pagination page);
    /**
     * 根据单位id 和用户名 模糊查询 用户对象
     * @param unitId
     * @param ownerType
     * @param userName
     * @return
     */
    public Map<String , User> getUserByName(String unitId, int ownerType , String userName );
    /**
     * 用户account_id,sequence,owner_type,region_code的初始化接口 陕西从数字校园1.0升级到数字校园2.0中，
     * 原来的user表没有这四个字段，需要在部署的时候从原来的库及passport初始化这四个字段
     * 
     * @return List<String> 返回accountId为null，并且在Passport上没有对应的Account信息的用户主键列表
     */
    public List<String> initAccountId();

    /**
     * 根据userIds取出用户信息，包含教师、学生、家长，真实姓名写入inteName，性别写入sex, 单位名称写入unitName
     * 
     * @param coPassportInfo 是否从passport中取出account信息进行合并
     * @param userIds
     * @return
     */
    public User getUserRealInfoByUserIds(boolean coPassportInfo, String userId);

    /**
     * 取单位管理员
     * 
     * @param unitids
     * 
     * @return
     */
    public Map<String, User> takeUnitAdmins(String[] unitids);
    
    /**
     * 根据用户Names获取用户列表
     * @param userNames
     * @return
     */
    public List<User> getUsersByUserNames(String[] userNames);

    // ===================与用户对应的学生、教师、家长信息=============
    /**
     * 取用户对应的实体对象信息
     * 
     * @param studentIds
     * @return
     */
    public List<User> getStudentUsers(String... studentIds);
    
    /**
     * 更新用户拼音
     * 
     * @param users
     */
    public void updateUsersPinYin(List<User> users);

    /**
     * 根据ownerId更改用户手机号
     * @param familyMobPho
     * @param ownerId
     */
	public void updateUsersMobilePhoneByOwnerId(String familyMobPho, String ownerId);

	/**
	 * 更新用户信息以及对应的基本信息
	 * @param user
	 * @param tea
	 * @param stu
	 * @param fam
	 * @param withPhone TODO
	 * @return
	 * @throws BusinessErrorException
	 */
    public boolean updateUserWithTea(User user, BaseTeacher tea, Student stu, Family fam, boolean withPhone)
            throws BusinessErrorException;
    
    /**
	 * 定制删除家长信息的时候账号不删除只做情况user表的手机号码
	 * @param ownerIds
	 */
	public void updateUserMobilePhone(String... ownerIds);
	
	/**
     * 根据所有者删除用户  东莞学籍 不删除用户 
     * 
     * @param ownerIds
     * @return 返回定购了商品，不能删除的ownerId
     * @throws BusinessErrorException 删除passport账号出错
     */
    public Set<String> deleteDgUsersByOwner(int ownerType,String[] ownerIds) throws BusinessErrorException;
    
    /**
	 * 获取班级学生用户名流水号最大值 （学校版）
	 * @param unitId
	 * @param classCode
	 * @return
	 */
	public int getUserNameMaxCodeByClassCode(String unitId, String classCode);
}
