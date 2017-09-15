package net.zdsoft.eis.support.dao;

import java.util.List;

import net.zdsoft.eis.support.entity.RecommendSchool;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft数字校园（support）</p>
 * @author Cuibz 2007/08/18
 */
public interface RecommendSchoolDao {
    /**
     * 根据学校区域编码查询推荐学校
     * 
     * @param schRegionCode 学校schoolRegionCode
     * @return List
     */
    public List<RecommendSchool> getRecommendSchoolList(String schRegionCode);

    /**
     * 根据学校区域编码查询推荐学校，带有分页功能
     * 
     * @param schRegionCode 学校schoolRegionCode
     * @param page 分页参数
     * @return List
     */
    public List<RecommendSchool> getRecommendSchoolList(String schRegionCode, Pagination page);

    /**
     * 查询出全部推荐学校
     * 
     * @param top
     * 
     * @return List
     */
    public List<RecommendSchool> getAllRecommendSchoolList(int top);

    /**
     * 根据学校Id得到推荐学校的详细信息
     * 
     * @param schId 学校ID
     * @return List
     */
    public RecommendSchool getRecommendSchoolInfo(String schId);

    /**
     * 修改推荐学校的详细信息
     * 
     * @param updateParametes 学校信息数组
     * @return int
     */
    public int updateRecommendSchool(Object[] recommendSchool);

    /**
     * 修改学校的推荐状态
     * 
     * @param schId [] 要修改推荐状态的学校ID数组
     * @return int
     */
    public int updateSchoolRecommendState(String schId[]);

    /**
     * 获得全部的学校
     * 
     * @return List
     */
    public List<RecommendSchool> getAllSchool();

    /**
     * 根据学校名称获得学校列表
     * 
     * @return
     */
    public List<RecommendSchool> getSchools(String name, Pagination page);

    /**
     * 根据学校名称获得学校信息
     * 
     * @return
     */
    public List<RecommendSchool> getSchool(String SchName);

    /**
     * 添加推荐学校
     * 
     * @return int
     */
    public int addRecommendSchool(Object[] recommendSchoolInfo);

}
