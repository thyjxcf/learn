package net.zdsoft.leadin.dataimport.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.common.service.DividedTransactionBusinessDispose;
import net.zdsoft.leadin.common.service.DividedTransactionService;
import net.zdsoft.leadin.dataimport.core.ImportJobProcess;
import net.zdsoft.leadin.dataimport.dao.ImportDataJobDao;
import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.entity.ImportDataJobDetail;
import net.zdsoft.leadin.dataimport.param.DataImportParam;
import net.zdsoft.leadin.dataimport.service.ImportDataJobService;
import net.zdsoft.leadin.dataimport.subsystemcall.BaseSubsystemService;

public class ImportDataJobServiceImpl implements ImportDataJobService {

    private ImportDataJobDao importDataJobDao;
    private DividedTransactionService dividedTransactionService;
    private BaseSubsystemService baseSubsystemService;

    public void setDividedTransactionService(DividedTransactionService dividedTransactionService) {
        this.dividedTransactionService = dividedTransactionService;
    }

    public void setLeadinImportDataJobDao(ImportDataJobDao importDataJobDao) {
        this.importDataJobDao = importDataJobDao;
    }

    public int insertImportJob(ImportDataJob job) {
        return importDataJobDao.insertImportJob(job);
    }

    public void deleteImportJobs(String[] ids) {
        List<ImportDataJob> list = importDataJobDao.findImportJobs(ids);
        for (int i = 0; i < list.size(); i++) {
            ImportDataJob job = list.get(i);
            String strFile = job.getErrorFullFile();
            if (null != strFile && !"".equals(strFile)) {
                // 删除文件
                File file = new File(strFile);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        importDataJobDao.deleteImportJobs(ids);

        // 删除detail表
        importDataJobDao.deleteJobDetails(ids);
    }

    public void runDeleteImportJobsAtRegularTime() {
        // 删除一个月之前的记录, 每次最多取500条记录，循环删除直到找不到记录 zengzt 调整删除一年前的数据
        Date jobStartTime = DateUtils.addDay(DateUtils.currentStartDate(), -365);
        int num = 500;

        List<ImportDataJob> list = importDataJobDao.findImportJobs(jobStartTime, num);
        while (list.size() > 0) {
            List<String> ids = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                ImportDataJob job = list.get(i);
                String strFile = job.getErrorFullFile();
                if (null != strFile && !"".equals(strFile)) {
                    // 删除文件
                    File file = new File(strFile);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                ids.add(job.getId());
            }

            dividedTransactionService.dividedTransCommit(
                    new DividedTransactionBusinessDispose<String>() {

                        @Override
                        public int saveDatas(List<String> list) {
                            String[] idArr = list.toArray(new String[0]);
                            deleteImportJobsAtRegularTime(idArr);
                            return 0;
                        }
                    }, ids);

            // 继续取记录
            list = importDataJobDao.findImportJobs(jobStartTime, num);
        }
    }

    public void deleteImportJobsAtRegularTime(String[] ids) {
        // 删除detail表
        importDataJobDao.deleteJobDetails(ids);
        importDataJobDao.deleteImportJobs(ids);
    }

    public void updateJobFinished(BusinessTask bt) {
		ImportDataJob job = (ImportDataJob) bt;
        importDataJobDao.updateJobFinished(job);
    }

    public void updateJobNoHand(int resetTime) {
        importDataJobDao.updateJobNoHand(resetTime);
    }

    public void updateRunTime(int status, Date jobRunTime, BusinessTask job) {
        importDataJobDao.updateRunTime(status, jobRunTime, job.getId());
    }

    public boolean isExistsJob(String initFile, String objectName, String userId,
            String uploadFileName) {
        return importDataJobDao.isExistsJob(initFile, objectName, userId, uploadFileName);
    }

    public ImportDataJob findImportJob(String jobId) {
        ImportDataJob job = importDataJobDao.findImportJob(jobId);
        if (null == job)
            return job;

        Map<String, Integer> jobPosMap = importDataJobDao.getJobPosMap();
        Integer pos = jobPosMap.get(job.getId());
        if (null != pos) {
            job.setJobPos(pos);
        }
        return job;
    }

    public List<ImportDataJob> findImportJobs(String unitId, String objectName) {
        List<ImportDataJob> jobList = importDataJobDao.findImportJobs(unitId, objectName);
        if (jobList.size() == 0)
            return jobList;

        Map<String, Integer> jobPosMap = importDataJobDao.getJobPosMap();
        for (ImportDataJob job : jobList) {
            Integer pos = jobPosMap.get(job.getId());
            if (null != pos) {
                job.setJobPos(pos);
            }
        }

        return jobList;
    }

    public List<BusinessTask> findNextNoHandJobs(int n) {
    	List<BusinessTask> tasks = new ArrayList<BusinessTask>();
    	List<ImportDataJob> jobs = importDataJobDao.findNextNoHandJobs(n); 
    	for (ImportDataJob job : jobs) {
    		tasks.add(job);
		}
        return tasks;
    }

    public List<ImportDataJob> findImportJobs(int status) {
        return importDataJobDao.findImportJobs(status);
    }

    public int[] addJobDetails(List<ImportDataJobDetail> jobDetails) {
        return importDataJobDao.addJobDetails(jobDetails);
    }

    public List<ImportDataJobDetail> findJobDetails(String jobId) {
        return importDataJobDao.findJobDetails(jobId);
    }

    @Override
    public String getSchedulerTokenCode(){
    	return "eis.dataimport";
    }
    
	@Override
	public boolean isControlOneDataByUnitPerConcurrent() {
		return true;
	}

	@Override
	public int getConcurrentcyNum() {
		if(null != baseSubsystemService){
            return baseSubsystemService.getDataImportConcurrentcyNum();
        }
		return 1;
	}

	@Override
	public String getStartSign() {
		return "eis.import.start";
	}

	@Override
	public String getChineseTaskName() {
		return "导入";
	}

	@Override
	public String getTaskName() {
		return "dataimport";
	}

	@Override
	public void saveHandleBusinessTask(BusinessTask btjob,Reply reply) throws Exception {
		ImportDataJob job = (ImportDataJob)btjob;
		
        //-----------------------准备参数------------------ 
        List<ImportDataJobDetail> listOfDeail = findJobDetails(job.getId());
        Map<String, String> mapOfParam = new HashMap<String, String>();
        if (listOfDeail != null && listOfDeail.size() > 0) {
            for (ImportDataJobDetail detail : listOfDeail) {
                if (null == detail.getValue())
                    continue;

                mapOfParam.put(detail.getName(), detail.getValue());
            }
        }
        
        DataImportParam param = new DataImportParam(job,mapOfParam); 
        
        //-----------------------开始导入------------------
        ImportJobProcess handle = new ImportJobProcess(job.getFileFullPath(),
                param, reply, job.getInitFile());
        handle.process();

		
	}

    public void setBaseSubsystemService(BaseSubsystemService baseSubsystemService) {
        this.baseSubsystemService = baseSubsystemService;
    }
}
