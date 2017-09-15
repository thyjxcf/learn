package net.zdsoft.leadin.dataimport.action;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.common.job.BusinessTaskQuartzJob;
import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.common.TaskUtil;
import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.entity.ImportMonitor;
import net.zdsoft.leadin.dataimport.service.ImportDataJobService;
import net.zdsoft.leadin.dataimport.subsystemcall.BaseSubsystemService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 导入任务
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 23, 2010 11:01:19 AM $
 */
public class TaskAction extends LeadinActionSupport {

    private static final long serialVersionUID = 5549332602939832959L;
    protected transient final Logger log = LoggerFactory.getLogger(getClass());

    private String objectName; // 对应xml文件中的object节点的name值
    private String errorDataPath; // 服务器上是否有错误信息
    private String url;
    
    /**
     *  导入类型：
     *  空或0或1预先保留，
     *  2为导入临时表,可以对导入的数据再修改后提交审核，或正式更新。
     */
    private String importType;
    
    /**
     * 用户自定义url，在导入列表上的点击去向url，和上面的变量url（返回url）组成链路。
     */
    private String userDefinedUrl;

    // ---------------------------任务处理-------------------------------------
    private List<BusinessTask> ququeJobList;// 正在队列中的任务
    private List<ImportDataJob> listOfJob;
    private String[] jobId; // 用于删除
    private ImportMonitor importMonitor;// 查看任务状态

    private BusinessTaskQuartzJob dataImportJob;
    private ImportDataJobService importDataJobService;
    protected BaseSubsystemService baseSubsystemService;

    /**
     * 列出任务的详细信息
     */
    public String listJobDetail() {
        String unitId = getLoginUser().getUnitId();
        try {
            listOfJob = importDataJobService.findImportJobs(unitId, objectName);
            Set<String> setOfIds = new HashSet<String>();
            for (ImportDataJob dataJob : listOfJob) {
                setOfIds.add(dataJob.getUserId());
            }
            if (setOfIds.size() == 0)
                return SUCCESS;
            Map<String, String> mapOfUser = baseSubsystemService.getUsersMap(setOfIds
                    .toArray(new String[0]));

            for (ImportDataJob dataJob : listOfJob) {
                dataJob.setUserId(mapOfUser.get(dataJob.getUserId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 删除选定的记录
     * 
     * @return
     */
    public String deleteJobs() {
        try {
            // 删除
            importDataJobService.deleteImportJobs(jobId);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return SUCCESS;
    }

    /**
     * 改变任务运行状态
     * 
     * @return
     */
    public void changeJobSign() {
        dataImportJob.changeRunning();
    }

    /**
     * 设置从数据库取任务停止标志
     * 
     * @return
     */
    public void stopSubmitTaskSign() {
        dataImportJob.setSubmitTaskRunning(false);
    }
    
    /**
     * 设置从处理任务停止标志
     * 
     * @return
     */
    public void stopTakeTaskSign() {
        dataImportJob.setTakeTaskRunning(false);
    }
    
	// ----------------------------json begin---------------------------------- 
	public String jsonChangeJobSign() {
		changeJobSign();
		return SUCCESS;
	}
	public String jsonStopSubmitTaskSign() {
		stopSubmitTaskSign();
		return SUCCESS;
	}
	public String jsonStopTakeTaskSign() {
		stopTakeTaskSign();
		return SUCCESS;
	}
	// ----------------------------json end----------------------------------
	
    /**
     * 查询任务标识
     * 
     * @return
     */
    public String queryJobSign() {
        importMonitor = dataImportJob.getImportMonitor();     

        // 阻塞的任务列表
        ququeJobList = dataImportJob.getJobs();

        // 正在执行
        listOfJob = importDataJobService.findImportJobs(DataImportConstants.TASK_STATUS_IN_HAND);

        // 待执行
        listOfJob.addAll(importDataJobService.findImportJobs(DataImportConstants.TASK_STATUS_NO_HAND));

        return SUCCESS;
    }

    // ----------------------------------------------------------------

    final public String getErrorData() {
        try {
			if (StringUtils.isNotBlank(errorDataPath)) {
				TaskUtil.getErrorData(ServletActionContext.getResponse(), objectName, errorDataPath);
			}
        } catch (IOException e) {
            log.error("IO异常");
        }
        return NONE;
    }

    public void setLeadinImportDataJobService(ImportDataJobService importDataJobService) {
        this.importDataJobService = importDataJobService;
    }

    public void setBaseSubsystemService(BaseSubsystemService baseSubsystemService) {
        this.baseSubsystemService = baseSubsystemService;
    }

    public void setLeadinDataImportJob(BusinessTaskQuartzJob dataImportJob) {
        this.dataImportJob = dataImportJob;
    }
    
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
	 * 获取导入类型：
	 * @return 导入类型：
	 */
	public String getImportType() {
	    return importType;
	}

	/**
	 * 设置导入类型：
	 * @param importType 导入类型：
	 */
	public void setImportType(String importType) {
	    this.importType = importType;
	}

	/**
	 * 获取用户自定义url，在导入列表上的点击去向url，和上面的变量url（返回url）组成链路。
	 * @return 用户自定义url，在导入列表上的点击去向url，和上面的变量url（返回url）组成链路。
	 */
	public String getUserDefinedUrl() {
	    return userDefinedUrl;
	}

	/**
	 * 设置用户自定义url，在导入列表上的点击去向url，和上面的变量url（返回url）组成链路。
	 * @param userDefinedUrl 用户自定义url，在导入列表上的点击去向url，和上面的变量url（返回url）组成链路。
	 */
	public void setUserDefinedUrl(String userDefinedUrl) {
	    this.userDefinedUrl = userDefinedUrl;
	}

	public List<BusinessTask> getQuqueJobList() {
        return ququeJobList;
    }

    public void setJobId(String[] jobId) {
        this.jobId = jobId;
    }

    public List<ImportDataJob> getListOfJob() {
        return listOfJob;
    }

    public ImportMonitor getImportMonitor() {
        return importMonitor;
    }

    public void setErrorDataPath(String hasErrorData) {
        this.errorDataPath = hasErrorData;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
    
}
