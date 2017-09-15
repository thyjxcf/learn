package net.zdsoft.eis.support.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.support.dao.RecommendSchoolDao;
import net.zdsoft.eis.support.entity.RecommendSchool;
import net.zdsoft.eis.support.service.RecommendSchoolService;
import net.zdsoft.keel.util.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

/* 
 * <p>ZDSoft数字校园（support）</p>
 * 
 * @author Cuibz 2007/08/18
 */
public class RecommendSchoolServiceImpl implements RecommendSchoolService {

    private static final Logger log = LoggerFactory.getLogger(RecommendSchoolServiceImpl.class);

    private RecommendSchoolDao recommendSchoolDao;

    private static final int maxImageHeight = 232;

    private static final int maxImageWidth = 350;

    /*
     * 根据学校区域编码获得学校列表
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#getRecommendSchoolList(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getRecommendSchoolList(String region) {
        List<RecommendSchool> listORecommendSchool = recommendSchoolDao
                .getRecommendSchoolList(region);
        return listORecommendSchool;
    }

    /*
     * 根据学校区域编码获得学校列表，带有分页功能
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#getRecommendSchoolList(java.lang.String,
     *      net.zdsoft.keel.util.Pagination)
     */
    public List<RecommendSchool> getRecommendSchoolList(String region,
            Pagination page) {
        if (region == null || "".equals(region)) {
            region = "%";
        }
        else {
            if (region.length() < 6) {
                region = region + "%";
            }
        }
        return recommendSchoolDao.getRecommendSchoolList(region, page);
    }

    /*
     * 无条件获得学校列表
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#getAllRecommendSchoolList()
     */
    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getAllRecommendSchoolList(int top) {
//        if (recommendSchoolCache == null) {
//            recommendSchoolCache = new Cache(true, false, false);
//            log.debug("初始化推荐学校cache");
//        }
        List<RecommendSchool> listOfRecommendSchool = null;
        try {
//            listOfRecommendSchool = (List<RecommendSchool>) recommendSchoolCache
//                    .getFromCache(RECOMMEND_SCHOOL_CACHE);
            if (listOfRecommendSchool == null) {
                listOfRecommendSchool = recommendSchoolDao
                        .getAllRecommendSchoolList(top);
//                recommendSchoolCache.putInCache(RECOMMEND_SCHOOL_CACHE,
//                        listOfRecommendSchool);
//                log.debug("cache中没有推荐学校对象,设置cache");
            }
//            else{
//                log.debug("从cache中取得推荐学校信息.");
//            }
        }
//        catch (NeedsRefreshException e) {
//            log.debug("cache中推荐学校失效,重新设置.");
//            if (listOfRecommendSchool == null)
//                listOfRecommendSchool = recommendSchoolDao
//                        .getAllRecommendSchoolList(top);
//            recommendSchoolCache.putInCache(RECOMMEND_SCHOOL_CACHE,
//                    listOfRecommendSchool);
//        }
        catch (Exception e) {
            log.debug("cache中推荐学校出现异常,重新设置.");
            if (listOfRecommendSchool == null)
                listOfRecommendSchool = recommendSchoolDao
                        .getAllRecommendSchoolList(top);
//            recommendSchoolCache.putInCache(RECOMMEND_SCHOOL_CACHE,
//                    listOfRecommendSchool);
        }
        
        return listOfRecommendSchool;
    }

    public void setRecommendSchoolDao(RecommendSchoolDao recommendSchoolDao) {
        this.recommendSchoolDao = recommendSchoolDao;
    }

    /*
     * 根据学校Id获得学校的详细信息
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#getRecommendSchoolInfo(java.lang.String)
     */
    public RecommendSchool getRecommendSchoolInfo(String schId) {
        return recommendSchoolDao.getRecommendSchoolInfo(schId);
    }

    /*
     * 修改学校的详细信息
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#updateRecommendSchoolInfo(net.zdsoft.support.entity.RecommendSchool)
     */
    public boolean updateRecommendSchoolInfo(Object[] recommendSchool) {
        int updateNum = recommendSchoolDao
                .updateRecommendSchool(recommendSchool);
        if (updateNum == 1) {
            return true;
        }
        return false;
    }

    /*
     * 修改学校的推荐状态
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#updateSchoolRecommendState(java.lang.String[],
     *      int)
     */
    public boolean updateSchoolRecommendState(String[] schId) {
        recommendSchoolDao.updateSchoolRecommendState(schId);
        return false;
    }

    /*
     * 获得全部学校
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#getAllSchool(net.zdsoft.keel.util.Pagination)
     */
    public List<RecommendSchool> getAllSchool() {
        return recommendSchoolDao.getAllSchool();
    }

    public List<RecommendSchool> getSchools(String name, Pagination page) {
        if (name != null)
            name = "%" + name + "%";
        else
            name = "%";
        return recommendSchoolDao.getSchools(name, page);
    }

    /*
     * 添加推荐学校
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#addRecommendSchool(java.lang.String[])
     */
    public int addRecommendSchool(Object[] recommendSchoolInfo) {
        return recommendSchoolDao.addRecommendSchool(recommendSchoolInfo);
    }

    /*
     * 上传推荐学校图片
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#picUpload(java.lang.String)
     */
    public boolean picUpload(HttpServletRequest request, String fileName,
            String filePath) throws ServletException, IOException {

        File uploadedFile = null;
        String fileRealname = null;
        if (request instanceof MultiPartRequestWrapper) {
            MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
            Enumeration<String> e = requestWrapper.getFileParameterNames();
            if (e.hasMoreElements()) {
                String fieldName = String.valueOf(e.nextElement());
                uploadedFile = (((File[]) requestWrapper.getFiles(fieldName))[0]);
                fileRealname = requestWrapper.getFileNames(fieldName)[0];
            }
        }

        try {
            RecommendSchoolPhoto photo = new RecommendSchoolPhoto();

            photo.saveRecSchPhoto(filePath, fileName, fileName, uploadedFile,
                    fileRealname, ServletActionContext.getServletContext(),
                    maxImageWidth, maxImageHeight);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 根据学校的名称获得学校的信息
     * 
     * @see net.zdsoft.support.service.RecommendSchoolService#getSchools(java.lang.String)
     */
    public List<RecommendSchool> getSchool(String SchName) {
        return recommendSchoolDao.getSchool(SchName);
    }
}
