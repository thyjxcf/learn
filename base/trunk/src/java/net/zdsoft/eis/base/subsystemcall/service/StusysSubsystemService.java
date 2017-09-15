package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.subsystemcall.entity.StuAbnFlowDto;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

public interface StusysSubsystemService {
    
	 /**
     * 更新学生基本信息(只有某几个字段)
     * @param student
     * @return
     */
    public void updateStudentSimple(Student student);
        
    /**
     * 更改学生的学区id
     * 
     * @param formerXQId 未更改前的学区id
     * @param newXQId 更改后的学区id
     * @param schId 学校id
     */
    public void updateSchDistrict(String formerXQId, String newXQId, String schId);
	
    /**
     * 获取学生银行卡号
     * @param studentIds
     * @return
     */
	public Map<String, String> getStuBankCardNoMap(String... studentIds) ;
	
	/** 
	 * 获取指定异动类型的学生ids
	 */
	public List<String> getStuAbnormalFlows(String unitId, String[] flowTypes);
	/** 
	 * 获取指定异动类型的学生id
	 */
	public List<StuAbnFlowDto> getStuAbnormalFlowBystuId(String stuId,
			String acadyear, String semester,String[] flowTypes);
	 /**
     * 删除学生信息
     * @param schoolId
     * @param studentIds
     * @author zhangkc
     * @date 2014年7月28日 下午5:05:57
     * @return TODO
     */
    public String deleteStudent(String schoolId, String[] studentIds);
    /**
	 * 验证各种号码的有效性：这里只校验身份证号码的唯一性，其他一概不做
	 * 
	 * @param stu 学生信息
     * @param checkIdCardUniqueness 是否检查身份证号的唯一性
     * @return 说明信息
	 */
	public PromptMessageDto checkCodes(Student stu, boolean checkIdCardUniqueness);
	
	/**
	 * 获取学生座位信息
	 * @param classId
	 * @return
	 */
	public JSONArray getStudentSeatInfo(String classId);
	
	/**
	 * 根据学生id找处分，返回拼接好的字符串
	 * @param unitId 不能为空
	 * @param acadyear
	 * @param semester
	 * @param stuIds
	 * @return
	 */
	public Map<String,List<String>> getStuPunish(String unitId,String acadyear,String semester,String[]stuIds);

}
