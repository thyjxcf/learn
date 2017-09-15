/**
 * 
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseStudentFamilyService extends StudentFamilyService {
    /**
     * 新增学生家庭
     * 
     * @param family
     */
    public void addFamily(Family family);

    /**
     * 批量新增学生家庭
     * 
     * @param familyList
     */
    public void addFamilies(List<Family> familyList);

    /**
     * 保存学生家庭信息
     * 
     * @param family
     */
    public void saveFamily(Family family);

    /**
     * 更新
     * 
     * @param family
     */
    public void updateFamily(Family family);

    /**
     * 根据学生更新家长的在校离校状态和所属学校信息
     * 
     * @param studentIds
     * @param leaveSchools
     */
    public void updateFamilyByStudent(String[] studentIds, int[] leaveSchools);

    /**
     * 批量更新
     * 
     * @param familyList
     */
    public void updateFamilies(List<Family> familyList);

    /**
     * 根据学生列表删除家庭关系数据
     * 
     * @param studentIds
     * @return 返回不能删除家长的学生id（原因：学生对应的家长用户存在个人定购关系）
     */
    public Set<String> deleteFamilyByStudentIds(String[] studentIds);

    /**
     * 根据学生家庭ids批量删除学生家庭
     * 
     * @param familyIds
     * @param eventSource
     * @return 返回未成功删除的familyIds
     */
    public Set<String> deleteFamiliesByFamilyIds(String[] familyIds, EventSourceType eventSource);

    /**
     * 更新手机号
     * @param familyMobPho
     * @param id
     */
	public void updateFamily(String familyMobPho, String id);
	
	/**
     * 根据学生家庭ids批量删除学生家庭 东莞学籍
     * 
     * @param familyIds
     * @param eventSource
     * @return 返回未成功删除的familyIds
     */
    public Set<String> deleteDgFamiliesByFamilyIds(String[] familyIds, EventSourceType eventSource);

}
