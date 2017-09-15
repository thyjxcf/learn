/**
 * 
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.OperationNotAllowedException;

/**
 * @author yanb
 * 
 */
public interface BaseGradeService extends GradeService {
    /**
     * 新增
     * @param grade
     */
    public void addGrade(Grade grade);
    
    /**
     * 更新
     * @param grade
     */
    public void updateGrade(Grade grade);
    
    /**
     * 保存年级
     * 
     * @param grade
     */
    public void saveGrade(Grade grade);

    /**
     * 批量保存年级
     * 
     * @param gradeList
     */
    public void saveGrades(List<Grade> gradeList) throws Exception;

    /**
     * 删除年级 主要用于接收MQ消息来删除本地数据
     * 
     * @param gradeId
     * @param eventSource 
     */
    public void deleteGrade(String gradeId, EventSourceType eventSource);

    /**
     * 修改指定单位的课时数
     * 
     * @param schId 学校编号
     * @param amNumber 上午课时
     * @param pmNumber 下午课时
     * @param nightNumber 晚上课时
     * @return
     * @throws Exception
     */
    public int updateGrade(String schId, int amNumber, int pmNumber, int nightNumber);

    /**
     * 初始化年级信息
     * 
     * @param schoolId
     * @param section
     * @return
     */
    public List<Grade> initGrades(String schoolId, int section);

    /**
     * 初始化年级信息
     * 
     * @param unitId
     * @return
     * @throws Exception
     */
    public List<Grade> initGrades(String unitId) throws OperationNotAllowedException;
    /**
     * 根据年级id取得年级信息
     * 
     * @param gradeId
     * @return
     */
    public Grade getBaseGrade(String gradeId);
    /**
     * 年级MAP
     * 
     * @param schoolId
     * @return key = schid + acadyear + section + schoolingLength
     */
    public Map<String, Grade> getGradeMap(String schoolId);
    
    /**
     * 获取新生的年级
     * @param schoolId
     * @param acadyear
     * @param section
     * @return
     */
	public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId, String acadyear, Integer[] section);
	
	/**
	 * 年级map
	 * @param gradeIds
	 * @return
	 */
	public Map<String, Grade> getGradeDynMap(String[] gradeIds);
	/**
	 * 年级列表，含毕业
	 * 
	 * @param schoolId
	 * @param section
	 * @return
	 */
	public List<Grade> getBaseGradesWithGraduated(String schoolId, int section);
	/**
	 * 超出学制的年级信息
	 * 
	 * @param schoolId
	 * @param curAcadyear
	 * @return
	 */
	public List<Grade> getOverSchoolinglenGrades(String schoolId,
			String curAcadyear);
	
	/**
	 * 查找该年级组长的所有负责年级
	 * @param schoolId
	 * @param teacherId
	 * @return
	 */
    public List<Grade> getBaseGradesByTeacherId(String schoolId, String teacherId);

    /**
     * 新增年级，判断入学学年
     * @param unitId
     * @param int1
     * @return
     */
	public List<String> getOpenYearList(String unitId, int int1);

	/**
	 * 获取年级列表，包括已经删除的
	 * @param cuUnitId
	 * @param int1
	 * @return
	 */
	public List<Grade> getGradesContainDelete(String cuUnitId, int int1);

	/**
	 * 动态获取年级名字
	 * @param newgrade
	 * @return
	 */
	public String getGradeNameByDyn(Grade newgrade);

	/**
	 * 修改年级，同步班级内容修改
	 * @param list
	 * @throws Exception 
	 */
	public void saveGradeAsyncClass(List<Grade> list) throws Exception; 
}
