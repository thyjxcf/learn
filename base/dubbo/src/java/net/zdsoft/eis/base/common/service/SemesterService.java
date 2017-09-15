package net.zdsoft.eis.base.common.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Semester;

public interface SemesterService {
    /**
     * 得到当前学年
     * 
     * @return
     */
    public String getCurrentAcadyear();

    /**
     * 得到教育局端的当前学年学期
     * 
     * @return
     */
    public CurrentSemester getCurrentSemester();

    /**
     * 根据学年学期取得学期信息
     * 
     * @param acadyear
     * @param semester
     * @return
     */
    public Semester getSemester(String acadyear, String semester);
    
    /**
     * 得到教育局端的所有学年学期列表
     * 
     * @return List
     */
    public List<Semester> getSemesters();

    /**
     * 得到学年列表（根据acadyear倒序排列）
     * 
     * @return List
     */
    public List<String> getAcadyears();
    
    /**
     * 取学年列表（当前学年及以往学年）
     * @return
     */
    public List<String> getPreAcadyears();
    
    /**
     * 得到真实的当前学年学期信息
     * @return
     */
    public CurrentSemester getRealCurrentSemester();
    /**
     * 根据学年需求获取数据走缓存模式
     * @param acadyear
     * @param semester
     * @return
     */
    public Semester getSemesterCache(final String acadyear, final String semester);

}
