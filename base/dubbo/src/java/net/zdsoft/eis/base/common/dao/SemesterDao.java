package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Semester;

public interface SemesterDao {
    /**
     * 得到当前学年学期信息
     * 
     * @return
     */
    public CurrentSemester getCurrentSemester();
    
    /**
     * 得到真实的当前学年学期信息
     * @return
     */
    public CurrentSemester getRealCurrentSemester();

    /**
     * 根据学年学期取得学期信息
     * 
     * @param acadyear
     * @param semester
     * @return
     */
    public Semester getSemester(String acadyear, String semester);

    /**
     * 得到教育局的学年列表（根据acadyear倒序排列）
     * 
     * @return List
     */
    public List<String> getAcadyears();

    /**
     * 得到学年学期列表
     * 
     * @return
     */
    public List<Semester> getSemesters();
    /**
     * 取学年列表（当前学年及以往学年）
     * @return
     */
    public List<String> getPreAcadyears();

}
