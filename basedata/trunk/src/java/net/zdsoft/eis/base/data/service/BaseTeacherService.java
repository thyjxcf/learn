package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.ItemExistsException;

/**
 * @author yanb
 * 
 */
public interface BaseTeacherService extends TeacherService {

    /**
     * 保存teacher同时返回id
     * 
     * @param employee
     */
    public void insertTeacher(BaseTeacher teacher);
    
    /**
     * 保存teacher
     * 
     * @param employee
     */
    public void addTeacherFromMq(BaseTeacher teacher);
    
    /**
     * 批量新增
     * 
     * @param teacher
     */
    public void addTeachers(List<BaseTeacher> teacheList);

    /**
     * 新增职员
     * 
     * @param teacher
     */
    public void saveTeacher(BaseTeacher teacher) throws ItemExistsException;

    /**
     * 新增职员
     * 
     * @param teacher
     * @param signatureFile 签名图片
     */
    public void saveTeacher(BaseTeacher teacher, UploadFile signatureFile) throws ItemExistsException;

    /**
     * 更新职员信息
     * 
     * @param teacher
     */
    public void updateTeacher(BaseTeacher teacher);
    
    /**
     * 批量更新
     * 
     * @param teacher
     */
    public void updateTeachers(List<BaseTeacher> teacheList);

    /**
     * 更新职员信息
     * 
     * @param teacher
     * @throws Exception
     * @param signatureFile 签名图片
     */
    public void updateTeacher(BaseTeacher teacher, UploadFile signatureFile) throws ItemExistsException;
    
    /**
     * 教职工排序
     * @param teacherids
     * @param orderids
     */
    public void updateTeacherAsOrder(String[] teacherids,String[] orderids);

    /**
     * 批量删除职员
     * 
     * @param teacherIds
     * @param eventSource 是否发送消息
     * @throws Exception
     */
    public void deleteTeacher(String[] teacherIds, EventSourceType eventSource);

    /**
     * 新增职员同时新增用户
     * 
     * @param teacher
     * @param user
     * @return 如果该用户名已存在则返回false
     * @throws Exception
     */
    public boolean saveTeacher(BaseTeacher teacher, User user);

    /**
     * 新增职员同时新增用户
     * 
     * @param teacher
     * @param user
     * @return 如果该用户名已存在则返回false
     * @param signatureFile 签名图片
     * @throws Exception
     */
    public boolean saveTeacher(BaseTeacher teacher, User user, UploadFile signatureFile);

    /**
     * 身份证号
     * 
     * @param id
     * @param idCard
     * @return
     */
    public boolean isExistsIdCard(String id, String idCard);

    /**
     * 点到卡号
     * 
     * @param teacherId
     * @param idCard
     * @return
     */
    public boolean isExistsCardNumber(String teacherId, String cardNumber);

    /**
     * 得到单位下可用tchid
     * 
     * @param unitId
     * @return
     */
    public String getAvaTeacherCode(String unitId);
    
    /**
     * 得到各单位下职工编号
     * @param unitIds
     * @return
     */
    public Map<String, Set<String>> getTeacherCodeByUnitIds(String[] unitIds);

    /**
     * 根据单位和部门获取员工列表
     * 
     * @param unitid 单位GUID
     * @param code
     * @param name
     * @param cardNumber
     * @param deptId TODO
     * @param userName TODO
     * @return List
     */
    public List<BaseTeacher> getTeachersFaintness(String unitid, String code, String name,
            String cardNumber, String deptId, String userName);
    
    public List<BaseTeacher> getTeachersFaintnessByPage(String unitid, String name,String userName,Pagination page);
    
    /**
     * 根据员工主键ID得到教职工信息
     * 
     * @param teacherId 教职工id
     * @return Teacher
     */
    public BaseTeacher getBaseTeacher(String teacherId);
    

    /**
     * 根据单位和部门获取员工列表
     * 
     * @param unitId 单位id
     * @param deptId 部门id
     * @return List
     */
    public List<BaseTeacher> getBaseTeachers(String unitId, String deptId);
    
    /**
     * 取职工列表
     * 
     * @param teacherIds
     * @return
     */
    public List<BaseTeacher> getBaseTeachers(String[] teacherIds);
    
    /**
     * 根据部门编号得到下属职工
     * 
     * @param deptId
     * @return
     */
    public List<BaseTeacher> getBaseTeachersByDeptId(String deptId);
    
    /**
     * 根据单位获取员工列表(职工已设置所在部门名称)
     * 
     * @param unitId 单位id
     * @return List
     */
    public List<BaseTeacher> getBaseTeachers(String unitId);
    
    /**
     * 教师map
     * @param unitId
     * @param codes
     * @return
     */
    public Map<String, BaseTeacher> getTeacherMap(String unitId,String[] codes);
 
	public boolean isExistsTeacherCode(String unitid, String tchId);

    public List<BaseTeacher>  getTeachersByUnitIds(String[] unitId);
    
}
