package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SubSchool;

/**
 * @author yanb
 * 
 */
public interface BaseSubSchoolDao {
    /**
     * 增加
     * 
     * @param subSchool
     */
    public void insertSubSchool(SubSchool subSchool);

    /**
     * 删除（软）
     * 
     * @param subSchoolIds
     */
    public void deleteSubSchool(String[] subSchoolIds);

    /**
     * 更新
     * 
     * @param subSchool
     */
    public void updateSubSchool(SubSchool subSchool);

    /**
     * 根据学校ID得到分校区的列表
     * 
     * @param schoolId 学校GUID
     * @return List
     */
    public List<SubSchool> getSubSchools(String schoolId);
}
