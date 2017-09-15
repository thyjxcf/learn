package net.zdsoft.eis.support.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.support.entity.RecommendSchool;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft数字校园（support）</p>
 * 
 * 获得推荐学校列表及推荐学校详细信息
 * 
 * @author Cuibz 2007/08/18
 */

public interface RecommendSchoolService {

	/**
     * 根据学校区域编码得到推荐学校的列表
     * 
     * @param schRegionCode
     *            学校schoolRegionCode
     * @return List
     */
	public List<RecommendSchool> getRecommendSchoolList(String schRegionCode);
	/**
     * 根据学校区域编码得到推荐学校的列表，带有分页功能
     * 
     * @param schRegionCode
     *            学校schoolRegionCode
     * @param page 
     * 			  分页功能类
     * @return List
     */
	
	public List<RecommendSchool> getRecommendSchoolList(String region, Pagination page);
	/**
     * 获得全部推荐学校的列表
	 * @param top 
     * 
     * @return List
     */
	
	public List<RecommendSchool> getAllRecommendSchoolList(int top);
	/**
     * 根据学校id得到推荐学校的详细信息
     * 
     * @param schId
     *            学校ID
     * @return List
     */
	
	public RecommendSchool getRecommendSchoolInfo(String schId);
	/**
     * 修改推荐学校的详细信息
     * 
     * @param recommendSchool
     *            学校信息
     * @return Boolean
     */
	public boolean updateRecommendSchoolInfo( Object[] recommendSchool );
	/**
     * 获得全部的学校
     * 
     * @return List
     */
	public List<RecommendSchool> getAllSchool();
	/**
     * 修改学校的推荐状态
     * 
     * @param schId[]
     *            要修改推荐状态的学校ID数组
     * @return int
     */
	public boolean updateSchoolRecommendState(String[] schId);
	 /**
     * 根据学校名称获得学校列表
     * 
     * @return List
     */
    public List<RecommendSchool> getSchools(String name , Pagination page);
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
    public int addRecommendSchool( Object[] recommendSchoolInfo );
    /**
     * 上传图片
     * 
     * @return int
     */
    public boolean picUpload(HttpServletRequest request,String fileName, String filePath)throws ServletException, IOException;
}
