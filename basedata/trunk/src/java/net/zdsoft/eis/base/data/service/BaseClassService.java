package net.zdsoft.eis.base.data.service;

import java.util.List;


import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.data.entity.BasicClassModelDto;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseClassService extends BasicClassService {
    /**
     * 保存class
     * 
     * @param cls
     */
    public void addClass(BasicClass cls);

    /**
     * 更新班级信息
     * 
     * @param cls
     */
    public void updateClass(BasicClass cls);

    /**
     * 保存班级信息（包括新增和更改）
     * @param dto
     * @throws Exception
     */
    public void saveClass(BasicClass dto);

    /**
     * 批量创建班级
     * 
     * @param dto
     */
    public void createClassBatch(BasicClassModelDto dto);

    /**
     * 删除班级信息（软删除）
     * 
     * @param classIds
     *            待删除班级的ID数组
     * @throws Exception
     */
    public void deleteClasses(String[] classIds) throws Exception;

    /**
     * 删除班级信息（软删除）,主要用于接收MQ消息来删除本地数据
     * 
     * @param id
     * @param eventSource
     */
    public void deleteClass(String id, EventSourceType eventSource);

    /**
     * 更新班级所在年级信息
     * 
     * @param schoolId
     * @param acadyear
     * @param section
     * @param schoolingLength
     */
    @Deprecated
    public void updateGradeId(String schoolId, String gradeId, String acadyear, int section,
            String schoolingLength);

    /**
     * 根据学校ID、学段和入学学年得到要添加班级的班级编号（系统默认班级编号的规则是：4位毕业年份+2位序号），
     * 如：编号20040202表示2004级初二（2）班
     * 
     * @param schid 学校ID
     * @param acadyear 入学学年（格式：2005-2006）
     * @return String
     */
    public String getNextClassCodeWithGraduatedYear(String schid, int section, String acadyear);

    /**
     * 判断班级是否在用，返回的是在用的地方
     * 
     * @param schoolId 学校ID
     * @param classId 班级ID
     * @return List
     */
    public List<String> isUseClass(String schoolId, String classId);

    /**
     * 将数值型字符串转换为数字，对从右边开始数起第一个非数值型字符的后面部分进行转换
     * 
     * @param str 字符串
     * @return long -1：失败；>0：成功转换的数值
     */
    public long convertStrToNumRight(String str);
    
    /**
     * 根据学校ID得到班级数量
     * 
     * @param schoolId 学校ID
     * @return int
     */
    public boolean isExistsClass(String schoolId);

    /**
     * 根所学校ID和学段得到学校中的所在用班级数
     * 
     * @param schoolId 学校ID
     * @param section 学段
     * @return int
     */
    public boolean isExistsClass(String schoolId, int section);

    /**
     * 根据开设学年查找班级信息
     * 
     * @param schoolId
     * @param enrollyear
     * 
     * @return
     */
    public boolean isExistsClass(String schoolId, String enrollyear);
    /**
     * 定时器 定时每个学段新增一个班级（包括：学校没有设置学年学期时 自动新增学年学期；没有想对应年级 如：小一 初一 高一 时 ， 自动新增年级；自动新增班级）
     */
    public void saveClassWithJob(String schoolId, String acadyear) throws Exception;

	/**
     * 取得班级的下一个编号
     * @param schoolId
     * @param section
     * @param acadyear 班级所在的开设学年
     * @param schoolingLength
     * @return
     */
    public String getNextClassCode(String schoolId, String section, String acadyear, int schoolingLength);

    /**
     * 修改班级对应的学制
     * @param updataList
     */
	public void updateClassSchLength(List<BasicClass> updataList);
    

}
