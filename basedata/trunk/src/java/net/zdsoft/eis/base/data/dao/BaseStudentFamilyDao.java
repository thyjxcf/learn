/**
 * 
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseStudentFamilyDao {
    /**
     * 增加
     * 
     * @param family
     */
    public void insertFamily(Family family);

    /**
     * 批量增加
     * 
     * @param famList
     */
    public void insertFamilies(List<Family> famList);

    /**
     * 更新
     * 
     * @param family
     */
    public void updateFamily(Family family);

    /**
     * 批量更新
     * 
     * @param famList
     */
    public void updateFamilies(List<Family> famList);

    /**
     * 根据学生更新家长的在校离校状态和所属学校信息
     * 
     * @param familyList
     */
    public void updateFamilyByStudent(List<Family> familyList);

    /**
     * 删除
     * 
     * @param familyIds
     * @param eventSource
     */
    public void deleteFamiliesByFamilyIds(String[] familyIds, EventSourceType eventSource);

    /**
     * 根据学生ID列表删除数据
     * 
     * @param stuids
     */
    public void deleteFamilyByStudentIds(String[] stuids);
    
	
	/**
	 * 学生的某个家庭成员信息是否存在
	 * 
	 * @param unitId
	 * @param stuId
	 * @param realName
	 * @param relation
	 * @return
	 */
	public boolean isFamilyExist(String unitId, String stuId, String realName, String relation);

	public void updateFamily(String familyMobPho, String id);
	
}
