package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.FamilyUser;

public interface StudentFamilyService {

    /**
     * 根据家庭信息主键GUID得到一条家庭信息
     * 
     * @param familyId
     * @return
     */
    public Family getFamily(String familyId);

    /**
     * 根据ids得到list
     * 
     * @param familyIds
     * @return
     */
    public List<Family> getFamilies(String[] familyIds);

    /**
     * 家庭信息
     * 
     * @param studentId
     * @param phoneNum
     * @return
     */
    public List<Family> getFamilies(String studentId, String phoneNum);

    /**
     * 根据学生ID查询学生的家庭信息
     * 
     * @param studentId
     * @return List
     */
    public List<Family> getFamiliesByStudentId(String studentId);
    
    public List<Family> getFamiliesByStuIdRelations(String studentId, String[] relations);

    /**
     * 根据学生IDs查询学生的家庭信息
     * 
     * @param studentId
     * @return
     */
    public List<Family> getFamiliesByStudentId(String[] studentIds);
    
    /**
     * 根据学生ID查询学生家庭信息
     * 
     * @param studentIds 学生ID
     * @return 某学生的家庭关系Map
     */
    public Map<String, List<Family>> getFamilyMap(String[] studentIds);

    /**
     * 根据学生id数组，得到该学生家长的映射
     * 
     * @param stuIds
     * @return 如果没有记录则返回一个空的Map
     */
    public Map<String, List<FamilyUser>> getFamilyUserMapByStuIds(String[] stuIds);

    /**
     * 根据学生id数组，得到该学生家长(密码为Passport数据)的映射
     * 
     * @param stuIds
     * @return 如果没有记录则返回一个空的Map
     */
    public Map<String, List<FamilyUser>> getFamilyUserMapByStuIdsPW(String[] stuIds);
    
    public List<Family> getFamiliesByUnit(String unitId);
}
