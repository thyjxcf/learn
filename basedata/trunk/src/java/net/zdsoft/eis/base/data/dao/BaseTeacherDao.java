package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseTeacherDao {
	/**
	 * 获取教职工信息
	 * @param unitId
	 * @param code
	 * @return
	 */
	public BaseTeacher getTeacherByCodeAndUnit(String unitId, String code);
    /**
     * 新增
     * 
     * @param teacher
     */
    public void insertTeacher(BaseTeacher teacher);
    
    /**
     * 批量新增
     * 
     * @param teacheList
     */
    public void insertTeachers(List<BaseTeacher> teacheList);

    /**
     * 更新职员信息
     * 
     * @param teacher
     */
    public void updateTeacher(BaseTeacher teacher);
    
    /**
     * 更新电子签名照片信息
     * @param t
     */    
    public void updatePhoto(BaseTeacher t);
    
    /**
     * 批量更新
     * 
     * @param teacheList
     */
    public void updateTeachers(List<BaseTeacher> teacheList);

    /**
     * 更新教职工排序字段
     * 
     * @param teacherid
     * @param orderid
     */
    public void updateAsOrderByTeacher(String[] teacherids, String[] orderids);

    /**
     * 批量软删除职员
     * 
     * @param teacherIds
     * @param eventSource 是否发送消息
     */
    public void deleteTeacher(String[] teacherIds, EventSourceType eventSource);

    /**
     * 1、id==null时，检查是否有身份证号存在；</br> 2、id!=null时，检查 除这个id的教师外，是否存在这个身份证号；
     * 
     * @param id 教师id
     * @param idCard 身份证号
     * @return
     */
    public boolean isExistsIdCard(String id, String idCard);

    /**
     * 1、id==null时，检查是否有点到卡号存在；</br> 2、id!=null时，除这个id的教师外，检查是否存在这个点到卡号；
     * 
     * @param teacherId
     * @param cardNumber
     * @return
     */
    public boolean isExistsCardNumber(String teacherId, String cardNumber);

    /**
     * 直接从学生表中，检查是否使用了这个点到卡号
     * 
     * @param cardNumber
     * @return
     */
    public boolean isExistsCardNumberInStu(String cardNumber) throws Exception;

    /**
     * 判断该单位中该职工编号是否已存在
     * 
     * @param unitId
     * @param code
     * 
     * @return
     */
    public boolean isExistsTeacherCode(String unitId, String code);

    /**
     * 根据单位取得下属职工编号列表
     * 
     * @param unitId
     * @return
     */
    public List<String> getTeacherCodes(String unitId);

    /**
     * 根据单位和部门获取员工DTO列表
     * 
     * @param unitId 单位GUID
     * @param code
     * @param name
     * @param cardNumber
     * @param deptId TODO
     * @return List
     */
    public List<BaseTeacher> getTeachersFaintness(String unitId, String code, String name,
            String cardNumber, String deptId);

    /**
     * 根据员工主键ID得到教职工信息
     * 
     * @param teacherId 教职工id
     * @return Teacher
     */
    public BaseTeacher getBaseTeacher(String teacherId);


    /**
     * 根据单位获取员工列表
     * 
     * @param unitId 单位id
     * @return List
     */
    public List<BaseTeacher> getBaseTeachers(String unitId);

    /**
     * 根据部门编号得到下属职工
     * 
     * @param deptId
     * @return
     */
    public List<BaseTeacher> getBaseTeachersByDeptId(String deptId);
    
    /**
     * 取职工列表
     * 
     * @param teacherIds
     * @return
     */
    public List<BaseTeacher> getBaseTeachers(String[] teacherIds);

    /**
     * 根据身份证号ids获取教职工列表
     * @param identitycards
     * @return
     */
    public List<BaseTeacher> getTeacherMapByIdentityCards(String[] identitycards);
    
    /**
     * 教师map
     * @param unitId
     * @param codes
     * @return
     */
    public Map<String, BaseTeacher> getTeacherMap(String unitId,String[] codes);
    
    /**
     * 根据单位取得下属职工
     * @param unitIds
     * @return
     */
    public List<BaseTeacher> getTeachers(String[] unitIds);
}
