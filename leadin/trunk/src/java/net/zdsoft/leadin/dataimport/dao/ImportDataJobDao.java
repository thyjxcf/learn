package net.zdsoft.leadin.dataimport.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.entity.ImportDataJobDetail;

public interface ImportDataJobDao {

    /**
     * 批量增加任务明细
     * 
     * @param jobDetails
     * @return
     */
    public int[] addJobDetails(List<ImportDataJobDetail> jobDetails);

    /**
     * 删除参数
     * 
     * @param ids
     */
    public void deleteJobDetails(String[] ids);

    /**
     * 根据jobid，检索job明细信息
     * 
     * @param jobId
     * @return
     * @throws Exception
     */
    public List<ImportDataJobDetail> findJobDetails(String jobId);

    /**
     * 新增一条记录
     * 
     * @param job
     * @return
     * @throws Exception
     */
    public int insertImportJob(ImportDataJob job);

    /**
     * 删除任务
     * 
     * @param ids
     */
    public void deleteImportJobs(String[] ids);

    /**
     * 取任务
     * 
     * @param jobStartTime
     * @param num 数量
     * @return
     */
    public List<ImportDataJob> findImportJobs(Date jobStartTime, int num);
    
    /**
     * 更新任务完成
     * @param job
     */
    public void updateJobFinished(ImportDataJob job);

    /**
     * 更新正在处理的任务为待执行
     * @param resetTime
     * 
     * @param job
     */
    public void updateJobNoHand(int resetTime);

    /**
     * 更新任务开始运行时间
     * 
     * @param jobRunTime
     * @param id
     * @return
     */
    public void updateRunTime(int status, Date jobRunTime, String id);

    /**
     * 未完成处理的任务中是否存在同名文件
     * 
     * @param initFile
     * @param objectName
     * @param userId
     * @param uploadFileName
     * @return
     */
    public boolean isExistsJob(String initFile, String objectName, String userId,
            String uploadFileName);

    /**
     * 根据jobid检索job信息
     * 
     * @param jobId
     * @return
     */
    public ImportDataJob findImportJob(String jobId);

    /**
     * 检索任务
     * 
     * @param jobIds
     * @return
     */
    public List<ImportDataJob> findImportJobs(String[] jobIds);

    /**
     * 根据单位查找导入任务
     * 
     * @param unitId
     * @param objectName
     * @return
     */
    public List<ImportDataJob> findImportJobs(String unitId, String objectName);

    /**
     * 待处理的任务位置
     * 
     * @return
     */
    public Map<String, Integer> getJobPosMap();

    /**
     * 获取接下来要处理的n条记录数
     * 
     * @param cnt
     * @return
     */
    public List<ImportDataJob> findNextNoHandJobs(int n);

    /**
     * 某状态的记录
     * 
     * @param status
     * @return
     */
    public List<ImportDataJob> findImportJobs(int status);

}
