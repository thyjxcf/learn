package net.zdsoft.eis.base.data.dao;


import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseClassDao {
    /**
     * 增加
     * 
     * @param baseClass
     */
    public void insertClass(BasicClass baseClass);

    /**
     * 更新
     * 
     * @param baseClass
     */
    public void updateClass(BasicClass baseClass);

    /**
     * 删除
     * 
     * @param classId
     * @param eventSource 
     */
    public void deleteClass(String classId, EventSourceType eventSource);

    /**
     * 更新班级所在年级信息
     * 
     * @param schoolId
     * @param acadyear
     * @param section
     * @param schoolingLength
     */
    public void updateGradeId(String schoolId, String gradeId, String acadyear, int section,
            String schoolingLength);

    /**
     * 判断学校ID、班级编码是否存在
     * 
     * @param schoolId 学校ID
     * @param classCode 班级编码
     * @return true？存在：不存在
     */
    public boolean isExistsClassCode(String schoolId, String classCode);

    /**
     * 判断同一个学校，同一个学段，同一个入学学年（即同一个年级）中班级名称是否存在
     * 
     * @param schoolId 学校ID
     * @param section 学段
     * @param acadyear 入学学年
     * @param className 班级名称
     * @return boolean
     */
    public boolean isExistsClassName(String schoolId, int section, String acadyear, String className);

    /**
     * 根据学校id、学段、入学学年、学制获取最大排序号
     * 
     * @param schoolId
     * @param acadyear
     * @param section
     * @param schoolingLength
     */
    public int getMaxOrder(String schoolId, String acadyear, int section, int schoolingLength);

    /**
     * 根据学校ID、学段和入学学年获取班级的最大编码
     * 
     * @param schid 学校ID
     * @param section 学段（微代码）
     * @param acadyear 入学学年（格式是2005-2006）
     * @return String
     */
    public String getMaxClassCode(String schid, int section, String acadyear);
    
    /**
     * 是否存在未毕业的班级
     * 
     * @param schoolId 学校ID
     * @return int
     */
    public boolean isExistsClass(String schoolId);

    /**
     * 是否存在未毕业的班级
     * 
     * @param schoolId
     * @param section
     * @return
     */
    public boolean isExistsClass(String schoolId, int section);

    /**
     * 是否存在该开设学年下的班级
     * 
     * @param schoolId
     * @param enrollyear
     * @return
     */
    public boolean isExistsClass(String schoolId, String enrollyear);

    /**
     * 取得这个学校下的最大班级编号
     * @param schoolId
     * @param prefix 班级编号前缀
     * @param length 班级编号最大长度
     * @return
     */
	public String getMaxClassCodeByPrefix(String schoolId, String string, int i);
}
