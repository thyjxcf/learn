package net.zdsoft.leadin.dataimport.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.ArrayUtils;
import net.zdsoft.leadin.cache.SimpleCacheManager;
import net.zdsoft.leadin.cache.CacheCall.CacheObjectParam;
import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.common.TaskUtil;
import net.zdsoft.leadin.dataimport.core.ImportObject;
import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.core.ImportThread;
import net.zdsoft.leadin.dataimport.core.ImportXML;
import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.entity.ImportDataJobDetail;
import net.zdsoft.leadin.dataimport.param.DataImportParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;
import net.zdsoft.leadin.dataimport.service.DataImportService;
import net.zdsoft.leadin.dataimport.service.ImportDataJobService;
import net.zdsoft.leadin.dataimport.template.ExcelTemplateUtil;
import net.zdsoft.leadin.util.UUIDGenerator;

public abstract class DataImportBaseAction extends LeadinActionSupport implements DataImportable {

    private static final long serialVersionUID = 5549332602939832959L;
    protected transient final Logger log = LoggerFactory.getLogger(getClass());

    private ExcelTemplateUtil excelTemplateUtil;
    private List<ImportObjectNode> listOfNode;
    private String replyCacheId;// 随机串 = jobId

    private String errorDataPath; // 服务器上是否有错误信息
    private String covered; // 是否覆盖

    // --------------------action中动态设置------------
    private String objectName; // 对应xml文件中的object节点的name值

    private SimpleCacheManager simpleCacheManager;

    /**
     * 子类不可覆盖
     */
    @Override
    final public String execute() throws Exception {
        DataImportViewParam viewParam = getViewParam();
        objectName = viewParam.getObjectName();
    
        replyCacheId = UUIDGenerator.getUUID();
        errorDataPath = URLEncoder.encode(remoteGetErrorData(objectName), "UTF-8");

        return subExecute();
    }

    /**
     * 主页面，供子类覆盖
     * 
     * @return
     */
    public String subExecute() throws Exception {
        return SUCCESS;
    }

    // 做页面响应，调用thread处理导入过程    
    final public String dataImport() {
        String replyId = replyCacheId;
        final String finalReplyId = replyId;
        Reply reply = simpleCacheManager.getObjectFromCache(new CacheObjectParam<Reply>() {

            public Reply fetchObject() {
                return new Reply();
            }

            public String fetchKey() {
                return finalReplyId;
            }
        });
        
        try{          
    
            HttpServletRequest request = getRequest();
            // 表示文件上传的form
            if (request instanceof MultiPartRequestWrapper) {
                MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
                Enumeration<String> e = requestWrapper.getFileParameterNames();
                if (!e.hasMoreElements()) {
                    reply.addActionError("-->所选择的文件无效，请确认文件是否存在！");
                    reply.setValue(DataImportConstants.STATUS_END);
                    simpleCacheManager.put(replyId, reply);
                    return ERROR;
                }
                // 有上传文件
                while (e.hasMoreElements()) {
                    DataImportParam param = getWrappedParam();
                    DataImportViewParam viewParam = param.getViewParam();
                    String fileType = viewParam.getFileType();
    
                    // 得到文件名，导入文件的文件名
                    String fieldName = String.valueOf(e.nextElement());
                    // 取得上传文件
                    File uploadedFile = (((File[]) requestWrapper.getFiles(fieldName))[0]);
                    // 上传文件的到本地后的文件名
                    String fileRealname = requestWrapper.getFileNames(fieldName)[0];
                    //对文件名进行截取
                    String useName=fileRealname.substring(0, fileRealname.lastIndexOf("."));
                    viewParam.setSubtitle(useName);
                    int pos = fileRealname.lastIndexOf(".");
                    String[] fileTypes = DataImportConstants.fileTypeMap.get(fileType);
                    // 导入文件格式不对
                    boolean hasError = false;
                    if (pos <= 0){
                        hasError = true;
                    }else{
                        hasError = true;                        
                        for (String ft : fileTypes) {
                            if (fileRealname.substring(pos + 1).equalsIgnoreCase(ft)) {
                                hasError = false;
                                
                                // 以用户实际上传的文件类型为准
                                fileType = ft;
                                viewParam.setFileType(fileType);
                                break;
                            }
                        }
                    }                    
                    
                    if (hasError) {
                        reply.addActionError("-->导入文件格式不正确，必须以" + ArrayUtils.toString(fileTypes) + "为后缀！");
                        reply.setValue(DataImportConstants.STATUS_END);
                        simpleCacheManager.put(replyId, reply);
                        return ERROR;
                    }
    
                    // 读取文件内容
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(uploadedFile));
    
                    byte[] buffer = new byte[2048];
    
                    // 将内容写入到本地文件
                    String path = TaskUtil.createStoreSubdir(new String[] {
                            DataImportConstants.FILE_PATH_IMPORT, objectName });
                    String fileName = TaskUtil.getFilePath(new String[] { path,
                            replyId + "." + fileType });
    
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
                    int length = -1;
                    while ((length = bis.read(buffer)) != -1) {
                        bos.write(buffer, 0, length);
                    }
                    bos.close();
                    bis.close();
    
                    reply.addActionMessage("-->上传文件完成。");
                    simpleCacheManager.put(replyId, reply);
                    
                    // 处理导入                
                    if (getPageParam().isHasTask()) {
                        // 判断该用户上传的同名文件是否还在处理中
                        boolean exists = importDataJobService.isExistsJob(param.getImportFile(),
                                objectName, param.getUserId(), fileRealname);
                        if(exists){
                            reply.addActionError("-->同名文件已经在排队等待处理，不需要再次上传该文件，您可以从任务列表中查看导入情况。");
                            reply.setValue(DataImportConstants.STATUS_END);
                            simpleCacheManager.put(replyId, reply);
                            return ERROR;
                        }                    
                        
                        // 将参数也置入mapOfParam中，以便保存到job参数中去
                        Map<String, String> mapOfParam = param.extractParamMap();
        
                        String result = importTask(mapOfParam, fileRealname, replyId, viewParam);
                        
                        // 更新文件上传完成标志，文件上传完成后，再开始取任务消息
                        reply.addActionMessage("-->已保存上传文件。");
                        reply.setValue(DataImportConstants.STATUS_FILE_UPLOAD_SUCCESS);
                        simpleCacheManager.put(replyId, reply);
                        
                        return result;
                    } else {
                        // 启动导入线程
                        ImportThread thread = new ImportThread(fileName, param, reply, viewParam
                                .getImportFile());
                        thread.start();
                    }
                }
            }
        } catch (Exception e) {
            reply.addActionError("上传导入文件过程中出错:" + e.getMessage());
            reply.setValue(DataImportConstants.STATUS_END);
            simpleCacheManager.put(replyId, reply);
            log.error("上传导入文件过程中出错", e);
        } 
        return SUCCESS;
    }
    
    /**
     * 取参数
     * 
     * @return
     */
    private DataImportParam getWrappedParam() {
        // 导入参数
        DataImportViewParam viewParam = getViewParam();
        DataImportParam param = new DataImportParam(viewParam, getLoginUser(), getParamsList());

        if (null == this.covered) {
            this.covered = "0";
        }
        param.setCovered(covered);
        param.setReplyId(replyCacheId);
        param.setObjectName(objectName);

        DataImportService dataImportService = viewParam.getDataImportService();
        List<ImportObjectNode> dynamicFields = dataImportService.getDynamicFields(param);// 动态列
        Set<String> filterFields = dataImportService.getFilterDefineFields();// 过滤的中文字段列表
        Map<String, Map<String, String>> constraintFields = dataImportService.getConstraintFields(param); // 限选列 
        
        param.setConstraintFields(constraintFields);
        param.setFilterFields(filterFields);
        param.setDynamicFields(dynamicFields);

        return param;
    }

    // 生成选择列
    final public String columnList() throws Exception {
        // 导入参数
        DataImportParam param = getWrappedParam();

        // 读取解析xml文件
        ImportXML importXML = new ImportXML(param.getImportFile(), param);
        ImportObject importObject = importXML.getConfigObject();
        // 取得文件定义的列
        listOfNode = importObject.getListOfNode();
        return SUCCESS;
    }

    // 下载模板
    @SuppressWarnings("unchecked")
    final public String getTemplate() throws Exception {
        // 导入参数
        DataImportParam param = getWrappedParam();
        DataImportViewParam viewParam = param.getViewParam();

        HttpServletRequest request = getRequest();
        String[] cols = request.getParameterValues("ckb");

        // 列标题
        String[] temp = new String[2];

        // 得到各列的中文列名信息
        ImportXML importXML = new ImportXML(viewParam.getImportFile(), param);
        ImportObject importObject = importXML.getConfigObject();

        temp[0] = importObject.getDefine();
        temp[1] = importXML.getXlsSheetName();

        Map<String, ImportObjectNode> nameNodeMap = importObject.getNameNodeMap(); 
        for (int i = 0; i < cols.length; i++) {
            ImportObjectNode node = nameNodeMap.get(cols[i]);
            if("Y".equals(node.getRequired())){
            	cols[i] = "*"+node.getDefine();
            }else{
            	cols[i] = node.getDefine();
            }
        }

        List listOfNodeContent = new ArrayList();
        listOfNodeContent.add(temp);
        listOfNodeContent.add(cols);

        // 添加数据信息
        List dataRows = buildDataRows(importObject, cols, viewParam);
        if (null != dataRows && dataRows.size() > 0) {
            if (viewParam.isHasSubtitle() || viewParam.isHasMoreFileData()) {
                listOfNodeContent.addAll(dataRows);
            } else {
                listOfNodeContent.addAll((List) dataRows.get(0));
            }
        }

        excelTemplateUtil = new ExcelTemplateUtil(listOfNodeContent, objectName, viewParam
                .isHasSubtitle(), viewParam.isHasTitle(), getLoginUser().getUnitId(), viewParam.isHasMoreFileData(),
                importObject.getDefineSelectMap());

        return SUCCESS;
    }

    /**
     * 组织模板数据行
     * 
     * @param importObject
     * @param cols
     * @param viewParam
     * @return
     */
    private List<List<String[]>> buildDataRows(ImportObject importObject, String[] cols,
            DataImportViewParam viewParam) {
        
        DataImportService dataImportService = viewParam.getDataImportService();
        return dataImportService.exportDatas(importObject, cols);
    }

    // ---------------------------消息处理--------------------------
    final public Reply remoteGetReplyById(final String replyId) {
        Reply reply = simpleCacheManager.getObjectFromCache(new CacheObjectParam<Reply>() {
            public Reply fetchObject() {
                return new Reply();
            }

            public String fetchKey() {
                return replyId;
            }
        });

        // 作为任务运行时
        if (getPageParam().isHasTask()) { 
            Collection<String> c = new ArrayList<String>();// 清理原来的提示
            ImportDataJob job = importDataJobService.findImportJob(replyId);
            // 此种情况的原因：任务还未入库 或任务被删除，则页面会一直取，故取三次
            for (int i = 0; i < 3; i++) {
                if (null == job) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                    }
                    job = importDataJobService.findImportJob(replyId);
                } else {
                    break;
                }
            }
            if (null == job) {
                String replyValue = reply.getValue() == null ? "" : (String) reply.getValue();
                if (!(DataImportConstants.STATUS_FILE_UPLOAD_SUCCESS.equals(replyValue))) {
                    return reply;
                }
                
                c.add("文件可能还未上传或已被删除。");
                reply.setActionMessages(c);
                reply.setValue(DataImportConstants.STATUS_END);
                simpleCacheManager.put(replyId, reply);
                return reply;
            }

            switch (job.getStatus()) {
            case DataImportConstants.TASK_STATUS_NO_HAND:// 待处理
                c.add("系统正在导入其他文件，本次导入排在第 " + String.valueOf(job.getJobPos()) + " 位");
                c.add("您可以等待；或者 离开该功能，去处理其它事务，稍后从任务列表中查看导入情况");
                reply.setActionMessages(c);
                break;
                
            case DataImportConstants.TASK_STATUS_PRE_HAND:// 预处理
                c.add("系统正在预处理······");
                reply.setActionMessages(c);
                break;

            case DataImportConstants.TASK_STATUS_IN_HAND:// 正在处理
                c.add("系统正在处理······");
                reply.setActionMessages(c);
                break;

            default:
                c.add("系统处理完成······");
                String msg = job.getResultMsg();
                if (StringUtils.isNotBlank(msg))
                    c.add(msg);
                reply.setActionMessages(c);
                reply.setActionErrors(null);

                reply.setValue(DataImportConstants.STATUS_END);
                break;
            }
            
            simpleCacheManager.put(replyId, reply);
        }

        return reply;
    }

    final public Reply remoteRemoveReplyById(String replyId) {
        simpleCacheManager.remove(replyId);

        // 第一次时自动生成，如果页面不刷新连续导入多次时，即在任务完成后重新生成此值
        replyCacheId = UUIDGenerator.getUUID();
        Reply reply = new Reply();
        reply.setValue(replyCacheId);
        return reply;
    }

    // ---------------------------错误数据--------------------------------------
    final public String remoteGetErrorData(String objectName) {
        this.objectName = objectName;
        String postfix = ".xls";
        if (getViewParam().isBatchImport()) {
            postfix = ".zip";
        }

        String filePath = TaskUtil.getStoreFilePath(new String[] { DataImportConstants.FILE_PATH_ERROR,
                objectName, getLoginUser().getUnitId() + postfix });

        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        } else {
            return "";
        }
    }

    final public String getErrorData() {
        try {
            TaskUtil.getErrorData(ServletActionContext.getResponse(), objectName, errorDataPath);
        } catch (IOException e) {
            log.error("IO异常");
        }
        return NONE;
    }

    // ---------------------------任务处理-------------------------------------
    private ImportDataJobService importDataJobService;

    private String importTask(Map<String, String> mapOfParam, String fileRealname, String id,
            DataImportViewParam viewParam) throws Exception {

        String filePath = TaskUtil.getFilePath(new String[] { DataImportConstants.FILE_PATH_IMPORT,
                objectName, id + "." + viewParam.getFileType() });

        // 创建导入任务对像
        ImportDataJob job = new ImportDataJob();
        job.setId(id);
        job.setFilePath(filePath);
        job.setName(fileRealname);
        job.setUnitId(getLoginUser().getUnitId());
        job.setUpdatestamp(new Date());
        job.setUserId(getLoginUser().getUserId());
        job.setSection(null);
        job.setImportDependOn(null);
        job.setInitFile(viewParam.getImportFile());
        job.setObjectName(objectName);
        job.setIsDeleted("0");
        job.setStatus(0); // 表示正在导入
        importDataJobService.insertImportJob(job);

        // 写入任务明细
        List<ImportDataJobDetail> listOfJobDetail = new ArrayList<ImportDataJobDetail>();
        if (mapOfParam != null && mapOfParam.size() > 0) {
            for (String s : mapOfParam.keySet()) {
                if (StringUtils.isEmpty(mapOfParam.get(s)))
                    continue;

                ImportDataJobDetail dataJobDetail = new ImportDataJobDetail();
                dataJobDetail.setId(UUIDGenerator.getUUID());
                dataJobDetail.setJobId(job.getId());
                dataJobDetail.setName(s);
                dataJobDetail.setValue(mapOfParam.get(s));
                listOfJobDetail.add(dataJobDetail);
            }
            importDataJobService.addJobDetails(listOfJobDetail);
        }

        return SUCCESS;
    }


    public void setLeadinImportDataJobService(ImportDataJobService importDataJobService) {
        this.importDataJobService = importDataJobService;
    }
    // ----------------------------------------------------------------

    public ExcelTemplateUtil getExcelTemplateUtil() {
        return excelTemplateUtil;
    }

    public void setExcelTemplateUtil(ExcelTemplateUtil excelTemplateUtil) {
        this.excelTemplateUtil = excelTemplateUtil;
    }

    public List<ImportObjectNode> getListOfNode() {
        return listOfNode;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setCovered(String convered) {
        this.covered = convered;
    }

    public String getCovered() {
        return covered;
    }

    public String getErrorDataPath() {
        return errorDataPath;
    }

    public void setErrorDataPath(String hasErrorData) {
        this.errorDataPath = hasErrorData;
    }

    public String getReplyCacheId() {
        return replyCacheId;
    }

    public void setReplyCacheId(String replyCacheId) {
        this.replyCacheId = replyCacheId;
    }

    public void setSimpleCacheManager(SimpleCacheManager simpleCacheManager) {
        this.simpleCacheManager = simpleCacheManager;
    }

    // ---------------------------json begin-------------------------------------
    private String replyId;
    
    public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	final public String jsonGetReplyById() {		
        Reply reply = remoteGetReplyById(replyId);
        return responseReplyJSON(reply);
    }    

    final public String jsonRemoveReplyById() {
    	Reply reply = remoteRemoveReplyById(replyId);
    	return responseReplyJSON(reply);
    }

    final public String jsonGetErrorData() {
    	String filePath = remoteGetErrorData(objectName);
    	jsonMap.put("filePath", filePath);
    	return responseJSON(jsonMap);
    }

    // ---------------------------json end-------------------------------------
}
