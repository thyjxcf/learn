package net.zdsoft.eis.base.data.service;

import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.service.SchoolSemesterService;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

/**
 * @author yanb
 * 
 */
public interface BaseSchoolSemesterService extends SchoolSemesterService {

    /**
     * 插入
     * 
     * @param schoolSemester
     */
    public void insertSemester(SchoolSemester schoolSemester);

    /**
     * 保存学年学期信息，返回保存成功还是失败信息
     * 
     * @param dto
     * @return SchoolMessageDto
     */
    public PromptMessageDto saveSemester(SchoolSemester dto);

    /**
     * 删除学年学期信息（软删除，并且只能对没在用的学年学期删除）
     * 
     * @param semesterIds 删除的学年学期主键数组
     * @return PromptMessageDto
     */
    public PromptMessageDto deleteSemester(String[] semesterIds);

    /**
     * 根据学校ID，获得该学校中最大的一条学年学期记录信息，如果没有数据则返回null
     * 
     * @param String 学校ID
     * @return BasicSemester
     */
    public SchoolSemester getMaxSemester(String schoolId);

    /**
     * 由所属教育局的学年学期、本校前面的学年学期或配置文件，得到添加的下一个学年学期各字段的默认值 String schoolId 学校ID
     * 
     * @return
     */
    public SchoolSemester getDefaultSemester(String schoolId);

    /**
     * 根据指定学年学期得到一个初始化的学年学期信息。
     * 3.0修改：如果教育局有设置相应学年学期，则使用相应的设置，否则返回null
     * 
     * @param schid
     * @param acadyear
     * @param semester
     * @return
     */
    public SchoolSemester getNewSemester(String schid, String acadyear, String semester);
    
    
    /**
     * 由ID取得学年学期信息
     * 
     * @param semesterId 学年学期ID
     * @return
     */
    public SchoolSemester getSemester(String semesterId);
}
