package net.zdsoft.leadin.dataimport.service;

import java.util.List;

import net.zdsoft.leadin.common.service.BusinessTaskService;
import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.entity.ImportDataJobDetail;

public interface ImportDataJobService extends BusinessTaskService {

    /**
     * 新增一条记录
     * 
     * @param job
     * @return
     * @throws Exception
     */
    public int insertImportJob(ImportDataJob job);

    /**
     * 删除
     * 
     * @param ids
     * @throws Exception
     */
    public void deleteImportJobs(String[] ids);
    
    /**
     * 定时删除任务及对应文件
     */
    public void runDeleteImportJobsAtRegularTime();
    
    /**
     * 删除任务及对应文件
     */
    public void deleteImportJobsAtRegularTime(String[] ids);

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
     * @return
     * @throws Exception
     */
    public ImportDataJob findImportJob(String jobId);

    /**
     * 根据单位查找导入任务
     * 
     * @param unitId
     * @return
     * @throws Exception
     */
    public List<ImportDataJob> findImportJobs(String unitId, String objectName);

    /**
     * 某状态的记录
     * 
     * @param status
     * @return
     */
    public List<ImportDataJob> findImportJobs(int status);

    // ==========================ImportDataJobDetail=========================
    /**
     * 根据jobid，检索job明细信息
     * 
     * @param jobId
     * @return
     * @throws Exception
     */
    public List<ImportDataJobDetail> findJobDetails(String jobId);

    /**
     * 批量新增任务明细
     * 
     * @param jobDetails
     * @return
     */
    public int[] addJobDetails(List<ImportDataJobDetail> jobDetails);
}
