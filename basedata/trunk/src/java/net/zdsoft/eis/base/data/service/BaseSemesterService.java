/**
 * 
 */
package net.zdsoft.eis.base.data.service;


import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseSemesterService extends SemesterService {
    /**
     * 保存教育局端新增的学年学期，返回保存结果提示信息
     * 
     * @param semester
     *            学年学期
     */
    public void saveSemester(Semester semester);
    
    /**
     * 增加
     * @param semester
     */
    public void addSemester(Semester semester);
    
    /**
     * 更新
     * 
     * @param semester
     */
    public void updateSemester(Semester semester);

    /**
     * 删除ID数组中指定的教育局学期
     * 
     * @param delArray
     *            待删除ID数组
     * @param eventSource
     *            是否发送消息
     */
    public void deleteSemester(String[] delArray, EventSourceType eventSource);

    /**
     * 根据ID取得学年学期信息
     * 
     * @param semesterid
     * @return Semester
     */
    public Semester getSemester(String semesterid);

    /**
     * 获得教育局中最大的一条学年学期记录信息，如果没有数据则返回null
     * 
     * @return Semester
     */
    public Semester getMaxSemester();

    /**
     * 得到本教育局中要添加的下一个学年学期各字段的默认值
     * 
     * @return
     */
    public Semester getDefaultSemester();

}
