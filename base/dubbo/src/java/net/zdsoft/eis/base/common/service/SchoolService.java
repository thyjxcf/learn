package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.keel.util.Pagination;

public interface SchoolService {
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
     * 根据学校统一编号得到学校ID
     * 
     * @param code 学校统一编号
     * @return schoolId
     */
    public String getSchoolIdByCode(String code);

    /**
     * 得到指定学校，某一学段的学制
     * 
     * @param schoolId 学校ID
     * @param section 学段
     * @return int
     */
    public int getSchoolingLen(String schoolId, int section);
    
    /**
     * 根据学校id得到该校的所有学段
     * 
     * @param schid 学校GUID
     * @return <schid,section,sectionName>
     */
    public List<String[]> getSchoolSections(String schid);

    /**
     * 根据学校办别和学校名称（模糊）取学校
     * 
     * @param parentId 父结点id
     * @param runschtype 学校办别
     * @param schoolName 学校名称
     * @param section
     * 
     * @return schoolId
     */
    public List<String> getSchoolIds(String parentId, String runschtype, String schoolName,
            int section);

    /**
     * 根据学校id检索列表
     * 
     * @param schoolIds
     * @return
     */
    public List<School> getSchools(String[] schoolIds);

    /**
     * 学区下的学校
     * 
     * @param districtId
     * @return
     */
    public List<School> getUnderlingSchools(String districtId);

    /**
     * 取得指定教育局的直属学校信息
     * 
     * @param parentId 教育局id
     * @param section 学段
     * @param schName 学校名称，支持模糊查询
     * @return List(BasicSchoolinfoDto)，BasicSchoolinfoDto内部暂时只有单位id、单位名称有值
     */
    public List<School> getUnderlingSchoolsFaintness(String parentId, int section, String schName);

    /**
     * 取得指定教育局的所有下属学校信息(直属学校和所有下级学校)
     * 
     * @param unionCode 教育局id
     * @param section 学段
     * @param schName 学校名称，支持模糊查询
     * @return List(BasicSchoolinfoDto)，BasicSchoolinfoDto内部暂时只有单位id、单位名称有值
     */
    public List<School> getAllSchoolsFaintness(String unionCode, int section, String schName);

    /**
     * 学校类型
     * 
     * @return <schoolId,type>
     */
    public Map<String, String> getSchoolTypeMap();
    
    public Map<String, School> getSchoolMap();

    /**
     * 根据学校名称进行查询，导入时判断学校名称时使用
     * 
     * @param names
     * @return <name,School>
     */
    public Map<String, School> getSchoolMapByNames(String[] names);

    /**
     * 根据学校ID得到分校区DTO的列表
     * 
     * @param schoolId 学校ID
     * @return List
     */
    public List<SubSchool> getSubSchools(String schoolId);
    
    /**
     * 根据学校类型取得该类型所包含的学段字符串
     * 
     * @param schtype 学校类型
     * @return 如小学、初中，就返回字符串"1,2"；如果没有符合该学校类型的学段则返回null
     */
    public String getSections(String schoolType);
    
    /**
     * 根据学校 区域级别regiontype和学校级别sections获取学校列表
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
