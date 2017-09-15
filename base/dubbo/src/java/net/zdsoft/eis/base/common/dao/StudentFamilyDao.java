package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Family;

public interface StudentFamilyDao {
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
     * 根据学生ID查询学生的家庭信息列表
     * 
     * @param studentId 学生ID
     * @return List
     */
    public List<Family> getFamiliesByStudentId(String studentId);
    
    /**
     * 根据学生ID和关系获取家庭信息
     * @param studentId
     * @param relations
     * @return
     */
    public List<Family> getFamiliesByStudentId(String studentId,String[] relations);

    /**
     * 根据学生ID查询学生家庭信息
     * 
     * @param studentIds 学生ID
     * @return
     */
    public List<Family> getFamiliesByStudentId(String[] studentIds);

    /**
     * 家庭信息列表
     * 
     * @param studentId
     * @param phoneNum
     * @return
     */
    public List<Family> getFamilies(String studentId, String phoneNum);
    
    public List<Family> getFamiliesByUnit(String unitId);
}
