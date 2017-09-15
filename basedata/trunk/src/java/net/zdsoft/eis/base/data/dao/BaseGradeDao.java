/**
 * 
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;




import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseGradeDao {
    /**
     * 增加
     * 
     * @param grade
     */
    public void insertGrade(Grade grade);

    /**
     * 更新
     * 
     * @param grade
     */
    public void updateGrade(Grade grade);

    /**
     * 删除
     * 
     * @param gradeId
     * @param isSendMq 
     */
    public void deleteGrade(String gradeId, EventSourceType sourceType );

    /**
     * 根据学段删除年级
     * 
     * @param schoolId
     * @param section
     */
    public void deleteGrade(String schoolId, int section);

    /**
     * 修改指定单位的课时数
     * 
     * @param schId 学校编号
     * @param amNumber 上午课时
     * @param pmNumber 下午课时
     * @param nightNumber 晚上课时
     * @return
     */
    public int updateGrade(String schId, int amNumber, int pmNumber, int nightNumber);

    /**
     * 最大排序号
     * 
     * @param schoolId
     * @return
     */
    public int getMaxOrder(String schoolId);

    /**
     * 学段信息
     * 
     * @param schoolId
     * @return
     */
    public List<Integer> getSections(String schoolId);

    /**
     * 超出学制的年级信息
     * 
     * @param schoolId
     * @param curAcadyear
     * @return
     */
    public List<Grade> getOverSchoolinglenGrades(String schoolId, String curAcadyear);
    /**
     * 取年级信息
     * 
     * @param gradeId
     * @return
     */
    public Grade getGrade(String gradeId);
    /**
     * 年级Map
     * 
     * @param schId
     * @return key = schid + acadyear + section + schoolingLength
     */
    public Map<String, Grade> getGradeMap(String schId);
    /**
     * 年级列表
     * 
     * @param schoolId
     * @param section
     * @return
     */
    public List<Grade> getBaseGrades(String schoolId, int section);

    /**
     * 新生年级list
     * @param schoolId
     * @param curAcadyear
     * @param section
     * @return
     */
    public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId, String curAcadyear, Integer[] section);
    /**
	 * 年级列表，含毕业
	 * 
	 * @param schoolId
	 * @param section
	 * @return
	 */
	public List<Grade> getBaseGradesWithGraduated(String schoolId, int section);
	
	/**
	 * 查找该年级组长的所有负责年级
	 * @param schoolId
	 * @param teacherId
	 * @return
	 */
    public List<Grade> getBaseGradesByTeacherId(String schoolId, String teacherId);
 
    /**
	 * 获取年级列表，包括已经删除的
	 * @param cuUnitId
	 * @param int1
	 * @return
	 */
	public List<Grade> getGrades(String cuUnitId, int section);
    
}
