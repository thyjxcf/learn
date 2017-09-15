/* 
 * @(#)DataImportViewParam.java    Created on Aug 4, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.leadin.dataimport.core.ImportData;
import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.service.DataImportService;
import net.zdsoft.leadin.dataimport.subsystemcall.LoginUser;
import net.zdsoft.leadin.util.Assert;

/**
 * 数据导入参数：与任务相关的，动态字段列表等
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 4, 2010 3:16:59 PM $
 */
public class DataImportParam {
    //----------------------常量------------------------
    private static final String LOGIN_UNIT_ID = "login_unit_id";
    private static final String LOGIN_USER_ID = "login_user_id";//整型id
    private static final String LOGIN_USER_GUID = "login_user_guid";
    
    //----------------------成员------------------------
    private DataImportViewParam viewParam;
    private LoginUser loginUser;// 登录用户
    private Map<String, String> customParamMap;// 自定义参数
    private ImportData importData;// 导入数据
    private DataImportDisposeParam disposeParam;// 处理参数

    private String covered; // 是否覆盖
    private String replyId;// 消息反馈id
    private String objectName; // 对应xml文件中的object节点的name值
    private List<ImportObjectNode> dynamicFields;// 动态字段
    private Set<String> filterFields;// 过滤的中文字段列表
    private Map<String, Map<String,String>> constraintFields;// 限选字段

    public DataImportParam(DataImportViewParam viewParam, LoginUser loginUser,
            List<String[]> customParams) {
        Assert.notNull(viewParam, "viewParam must not be null");
        Assert.notNull(loginUser, "loginUser must not be null");
        Assert.notNull(customParams, "customParams must not be null");

        this.viewParam = viewParam;
        this.loginUser = loginUser;

        // 自定义参数信息
        this.customParamMap = new HashMap<String, String>();
        for (String[] tmp : customParams) {
            this.customParamMap.put(tmp[0], tmp[1]);
        }

        this.disposeParam = new DataImportDisposeParam();
    }


    /**
     * 从数据库参数中抽取param对象
     * @param job 导入任务
     * @param paramMap 任务参数
     * @return
     */
    public DataImportParam(ImportDataJob job, Map<String, String> paramMap) {
        //viewParam
        this.viewParam = new DataImportViewParam(job, paramMap);

        // 登录用户信息
        loginUser = new LoginUser();
        loginUser.setUnitId(paramMap.remove(LOGIN_UNIT_ID));
        loginUser.setUserId(paramMap.remove(LOGIN_USER_GUID));
        loginUser.setUserIntId(Long.parseLong(paramMap.remove(LOGIN_USER_ID)));

        // 自定义参数信息
        this.customParamMap = new HashMap<String, String>();        
        customParamMap.putAll(paramMap);

        //处理参数
        this.disposeParam = new DataImportDisposeParam();
        disposeParam.setJobId(job.getId());
        
        
        // 自身
        this.covered = paramMap.get("covered");
        this.replyId = job.getId();
        this.objectName = job.getObjectName();    
        this.dynamicFields = getDataImportService().getDynamicFields(this);
        this.filterFields = getDataImportService().getFilterDefineFields();
        this.constraintFields = getDataImportService().getConstraintFields(this);
    }
    
    /**
     * 抽取paramMap，走任务时将这些参数保存到数据库中
     * @return
     */
    public Map<String, String> extractParamMap() {
        Map<String, String> paramMap = new HashMap<String, String>();

        //viewParam
        viewParam.fillParamMap(paramMap);

        // 登录用户信息
        paramMap.put(LOGIN_USER_ID, String.valueOf(loginUser.getUserIntId()));
        paramMap.put(LOGIN_USER_GUID, loginUser.getUserId());
        paramMap.put(LOGIN_UNIT_ID, loginUser.getUnitId());

        // 自定义参数信息
        paramMap.putAll(customParamMap);

        // 自身
        if (null == this.covered)
            this.covered = "0";
        paramMap.put("covered", covered);

        return paramMap;
    }
    
    // -------------------customParam参数-----------------------

    /**
     * @return Returns the customParamMap.
     */
    public Map<String, String> getCustomParamMap() {
        return customParamMap;
    }

    // -------------------ViewParam参数-----------------------
    /**
     * @return Returns the viewParam.
     */
    public DataImportViewParam getViewParam() {
        return viewParam;
    }
    
    /**
     * @return Returns the importFile.
     */
    public String getImportFile() {
        return viewParam.getImportFile();
    }

    /**
     * @return Returns the dataImportService.
     */
    public DataImportService getDataImportService() {
        return viewParam.getDataImportService();
    }

    /**
     * @return Returns the hasSubtitle.
     */
    public boolean isHasSubtitle() {
        return viewParam.isHasSubtitle();
    }

    /**
     * @return Returns the fileType.
     */
    public String getFileType() {
        return viewParam.getFileType();
    }

    /**
     * @return Returns the batchImport.
     */
    public boolean isBatchImport() {
        return viewParam.isBatchImport();
    }

    /**
     * @return Returns the ignoreInvalidCol.
     */
    public boolean isIgnoreInvalidCol() {
        return viewParam.isIgnoreInvalidCol();
    }

    /**
     * @return Returns the onlyUpdate.
     */
    public boolean isOnlyUpdate() {
        return viewParam.isOnlyUpdate();
    }
    
    /**
     * @return Returns the zipExecPath.
     */
    public String getZipExecPath() {
        return viewParam.getZipExecPath();
    }

    /**
     * @return Returns the importFileVersion.
     */
    public String getImportFileVersion() {
        return viewParam.getImportFileVersion();
    }

    /**
     * @return Returns the importFilePwd.
     */
    public String getImportFilePwd() {
        return viewParam.getImportFilePwd();
    }

    /**
     * @return Returns the subtitle.
     */
    public String getSubtitle() {
        return viewParam.getSubtitle();
    }
    
    /**
     * @param subtitle The subtitle to set.
     */
    public void setSubtitle(String subtitle) {
        viewParam.setSubtitle(subtitle);
    }
    

	public boolean isHasTitle() {
		return viewParam.isHasTitle();
	}

    // -------------------loginUser参数-----------------------

    /**
     * @return Returns the userId.
     */
    public String getUserId() {
        return loginUser.getUserId();
    }

    /**
     * @return Returns the unitId.
     */
    public String getUnitId() {
        return loginUser.getUnitId();
    }

    /**
     * @return Returns the userIntId.
     */
    public long getUserIntId() {
        return loginUser.getUserIntId();
    }

    // -------------------disposeParam自身参数-----------------------
    /**
     * @return Returns the disposeParam.
     */
    public DataImportDisposeParam getDisposeParam() {
        return disposeParam;
    }

    /**
     * @param disposeParam The disposeParam to set.
     */
    public void setDisposeParam(DataImportDisposeParam disposeParam) {
        this.disposeParam = disposeParam;
    }

    // -------------------importData自身参数-----------------------
    /**
     * @return Returns the importData.
     */
    public ImportData getImportData() {
        return importData;
    }


    /**
     * @param importData The importData to set.
     */
    public void setImportData(ImportData importData) {
        this.importData = importData;
    }
    
    // -------------------param自身参数-----------------------
    /**
     * @return Returns the replyId.
     */
    public String getReplyId() {
        return replyId;
    }

    /**
     * @param replyId The replyId to set.
     */
    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    /**
     * @return Returns the objectName.
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName The objectName to set.
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return Returns the filterFields.
     */
    public Set<String> getFilterFields() {
        return filterFields;
    }

    /**
     * @param filterFields The filterFields to set.
     */
    public void setFilterFields(Set<String> filterFields) {
        this.filterFields = filterFields;
    }

    /**
     * @return Returns the dynamicFields.
     */
    public List<ImportObjectNode> getDynamicFields() {
        return dynamicFields;
    }

    /**
     * @param dynamicFields The dynamicFields to set.
     */
    public void setDynamicFields(List<ImportObjectNode> dynamicFields) {
        this.dynamicFields = dynamicFields;
    }

    /**
     * @return Returns the covered.
     */
    public String getCovered() {
        return covered;
    }

    /**
     * @param covered The covered to set.
     */
    public void setCovered(String covered) {
        this.covered = covered;
    }

	public Map<String, Map<String, String>> getConstraintFields() {
		return constraintFields;
	}

	public void setConstraintFields(
			Map<String, Map<String, String>> constraintFields) {
		this.constraintFields = constraintFields;
	}
}
