package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.ServerAuthorize;
import net.zdsoft.eis.base.subsystemcall.entity.StusysSectionTimeSetDto;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.OperationNotAllowedException;
import net.zdsoft.passport.entity.Account;

public interface BaseDataSubsystemService {
    /**
     * 更新单位短信余额
     * 
     * @param unitId
     * @param balance
     */
    public void updateUnitBalance(String unitId, int balance);

    /**
     * 根据学生更新家长的在校离校状态，及家长用户的所属学校
     * 
     * @param studentId
     * @param leaveSchool
     */
    public void updateFamilyByStudent(String studentId, int leaveSchool);
    
    /**
     * 根据学生更新家长的在校离校状态和所属学校信息，及家长用户的所属学校
     * 
     * @param studentIds
     * @param leaveSchools
     */
    public void updateFamilyByStudent(String[] studentIds, int[] leaveSchools);
    
    /**
     * 新增家长信息
     */
    public void addFamily(Family family);

    /**
     * 修改家长信息
     * @param family
     */
    public void updateFamily(Family family);
    
    /**
     * 根据学生更新学生对应用户的所属学校信息
     * 
     * @param studentId
     */
    public void updateUserByStudent(String studentId);
    
    /**
     * 修改密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @param coPassportInfo
     * @throws Exception
     */
    public void updatePassword(String id, String oldPassword,
			String newPassword, boolean coPassportInfo) throws Exception;
    
    /**
     * 修改密码、用户名、教师姓名、身份证号、手机号
     * @param id
     * @param oldPassword
     * @param newPassword
     * @param coPassportInfo
     * @param username
     * @param teacherName
     * @param mobilePhone
     * @param identityCard
     * @throws Exception
     */
    public void updatePersonInfo(String id, String oldPassword,
			String newPassword, boolean coPassportInfo, String username, String teacherName, String mobilePhone, String identityCard) throws Exception;
    
    /**
     * 更新教师及其用户的电子邮箱
     * @param teacherId
     * @param email
     */
    public void updateEmail(String teacherId, String email);
    
    /**
     * 根据学生列表删除家庭关系数据
     * 
     * @param studentIds
     * @return 返回不能删除家长的学生id（原因：学生对应的家长用户存在个人定购关系）
     */
    public Set<String> deleteFamilyByStudentIds(String[] studentIds);

    /**
     * 根据所有者更新用户状态
     * 
     * @param ownerIds
     * @param mark
     * @throws OperationNotAllowedException 更新passport账号出错
     */
    public void updateUserMarkByOwner(String[] ownerIds, int mark) throws BusinessErrorException;

    /**
     * 根据所有者删除用户
     * 
     * @param ownerIds
     * @return 返回定购了商品，不能删除的ownerId
     * @throws BusinessErrorException 删除passport账号出错
     */
    public Set<String> deleteUsersByOwner(int ownerType,String[] ownerIds) throws BusinessErrorException;
    
    /**
     * 删除第三方授权
     * 
     * @param userIds
     * @param serverKind 服务类别
     */
    public void deleteServerAuthorizes(String[] userIds, int serverKind);

    /**
     * 保存第三方授权
     * 
     * @param authList
     */
    public void addServerAuthorizes(List<ServerAuthorize> authList);
    
    /**
     * 获取教师的职务
     * @param teacherId
     * @return
     */
    public String getTeacherDutyNames(String teacherId);
    
    /**
     * 获取单位的节次时间设置
     * @param unitId
     * @param acadyear
     * @param semester
     * @return
     */
    public List<StusysSectionTimeSetDto> getSectionTimeSets(String unitId, String acadyear, String semester);
    /**
     * 软删除教师，同步删除用户
     * @param teacherIds
     * @param eventSource
     * @author huy
     * @date 2014-7-3下午02:44:27
     */
    public String deleteTeacher(String[] teacherIds, EventSourceType eventSource);
    /**
     * 根据父级单位ID和单位类型生成unionId
     * @param parentUnitId
     * @param unitClass
     * @param unitType 为非教育局非学校单位时需要生成特殊的unionId
     * @return
     * @author zhangkc
     * @date 2014年10月15日 上午11:42:25
     */
    public String createUnionId(String parentUnitId, int unitClass, int unitType);
    
    /**
     * 根据用户名获取passport账号
     * @param userName
     * @return
     */
    public Account queryAccountByUsername(String userName);
    /**
     * 年级升级 班级升级
     * @param unitId
     * @param acadyear
     * @throws OperationNotAllowedException
     */
    public void initGrades(String unitId,String acadyear) throws Exception;

    /**
     * 得到本教育局中要添加的下一个学年学期各字段的默认值
     * 
     * @return
     */
	public Semester getDefaultSemester();

	/**
     * 保存教育局端新增的学年学期，返回保存结果提示信息
     * 
     * @param semester
     *            学年学期
     */
	public void saveSemester(Semester jwsemester);
    
    /**
     * 身份证号
     * 
     * @param id
     * @param idCard
     * @return
     */
    public boolean isExistsIdCard(String id, String idCard);

}
