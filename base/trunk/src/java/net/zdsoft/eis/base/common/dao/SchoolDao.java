package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.keel.util.Pagination;

public interface SchoolDao {

    /**
     * 取学校信息
     * 
     * @param schoolId
     * @return
     */
    public School getSchool(String schoolId);

    /**
     * 根据学校统一编号得到学校信息
     * 
     * @param code 学校统一编号
     * @return
     */
    public School getSchoolByCode(String code);

    /**
     * 得到指定学校，某一学段的学制
     * 
     * @param schoolId 学校ID
     * @param section 学段（微代码）
     * @return int
     */
    public int getSchoolingLen(String schoolId, int section);
    
    /**
     * 根据学校办别和学校名称（模糊）取学校id
     * 
     * @param parentId 父结点id
     * @param runschtype 学校办别
     * @param schoolName 学校名称
     * @param section
     * @return
     */
    public List<String> getSchoolIds(String parentId, String runschtype, String schoolName,
            int section);

    /**
     * 根据学校id数组取得学校列表
     * 
     * @param schoolIds 学校id数组
     * @return 学校列表
     */
    public List<School> getSchools(String[] schoolIds);

    /**
     * 根据学区id取得该学区下属学校列表
     * 
     * @param districtId 学区id
     * @return 该学区下属学校列表
     */
    public List<School> getUnderlingSchools(String districtId);

    /**
     * 取得指定教育局的直属学校信息
     * 
     * @param parentId 教育局id
     * @param section 学段
     * @param schoolName 左右匹配
     * @return
     */
    public List<School> getUnderlingSchoolsFaintness(String parentId, int section, String schoolName);

    /**
     * 取得指定教育局的所有下属学校信息(直属学校和所有下级学校)
     * 
     * @param unionCode 教育局编号
     * @param section 学段
     * @param schoolName 左右匹配
     * @return
     */
    public List<School> getAllSchoolsFaintness(String unionCode, int section, String schoolName);

    /**
     * 学校类型
     * 
     * @return <schoolId,type>
     */
    public Map<String, String> getSchoolTypeMap();

    public Map<String, School> getSchoolMap();
    
    public Map<String,School> getSchoolsById(String[] schoolIds);
    
    /**
     * 根据学校名称进行查询，导入时判断学校名称时使用
     * 
     * @param names
     * @return <name,School>
     */
    public Map<String, School> getSchoolMapByNames(String[] names);
    

    /**
     * 根据学校类型取得该类型所包含的学段字符串
     * 
     * @param schtype 学校类型
     * @return 如小学、初中，就返回字符串"1,2"；如果没有符合该学校类型的学段则返回null
     */
    public String getSections(String schoolType);
    
    /**
     * 根据学校 区域级别regiontype和学校级别section获取学校列表
     * @param parentid TODO
     * @param regiontype  
     * @param section
     * @param page TODO
     * @return
     */
    public List<School> getBaseSchoolsByRegiontypeSections(String parentid, String regiontype, String section, Pagination page);
    
    /**
     * 根据学校编码前10位编号获取学校
     * @param code
     * @return
     * @author huy
     * @date 2015-8-15下午03:12:12
     */
    public School getSchoolBy10Code(String code);
}
