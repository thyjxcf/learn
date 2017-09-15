/**
 * 
 */
package net.zdsoft.eis.base.data.dao;


import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseSemesterDao {
    /**
     * 增加
     * 
     * @param semester
     */
    public void insertSemester(Semester semester);

    /**
     * 删除
     * 
     * @param semesterIds
     * @param eventSource 
     */
    public void deleteSemester(String[] semesterIds, EventSourceType eventSource);

    /**
     * 更新
     * 
     * @param semester
     */
    public void updateSemester(Semester semester);

    /**
     * 根据id得到学年学期信息
     * 
     * @param semesterId
     * @return
     */
    public Semester getSemester(String semesterId);

    /**
     * 获得教育局中最大的一条学年学期记录信息，如果没有数据则返回null
     * 
     * @return JwSemester
     */
    public Semester getMaxSemester();

    /**
     * 
     * @param lastAcadyear
     * @param lastSemester
     * @param nextAcadyear
     * @param nextSemester
     * @param workBegin
     * @param workEnd
     * @return
     */
    public boolean isSemesterCross(String lastAcadyear, String lastSemester, String nextAcadyear,
            String nextSemester, String workBegin, String workEnd);
}
