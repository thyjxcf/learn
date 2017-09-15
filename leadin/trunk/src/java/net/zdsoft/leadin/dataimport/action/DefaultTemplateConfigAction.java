package net.zdsoft.leadin.dataimport.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.dataimport.entity.ImportDataTemplate;
import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;
import net.zdsoft.leadin.dataimport.service.ImportDataTemplateService;
import net.zdsoft.leadin.util.UUIDGenerator;

public class DefaultTemplateConfigAction extends DataImportBaseAction {
    private static final long serialVersionUID = 1185061750924551379L;

    private ImportDataTemplateService leadinImportDataTemplateService;

    private String importFile;
    private boolean templateConfig;// 模板配置
    private String templateRemark;// 说明

    /**
     * 显示模板配置主页面
     * 
     * @return
     */
    public String templateConfigMain() {
        return SUCCESS;
    }

    public Reply saveTemplates(String importFile, String objectName) {
        return saveTemplates(importFile, objectName, null);
    }
    
    /**
     * 保存模块
     * 
     * @param importFile
     * @param objectName
     * @param cols
     * @return
     */
    public Reply saveTemplates(String importFile, String objectName, String[] cols) {
        Reply reply = new Reply();

        try {
            String unitId = getLoginUser().getUnitId();
            List<ImportDataTemplate> templates = new ArrayList<ImportDataTemplate>();
            
            if(null != cols){
                for (int i = 0; i < cols.length; i++) {
                    ImportDataTemplate t = new ImportDataTemplate();
                    t.setId(UUIDGenerator.getUUID());
                    t.setFieldName(cols[i]);
                    templates.add(t);
                }
            }
            leadinImportDataTemplateService
                    .saveTemplates(importFile, objectName, unitId, templates);
        } catch (Exception e) {
            reply.addActionError("保存模板失败,原因：" + e.getMessage());
        }
        reply.addActionMessage("保存模板成功");

        return reply;
    }

	// ----------------------------json begin----------------------------------
    private String objectName;
    private String[] cols;

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}	

	public String jsonSaveTemplates() {
		Reply reply = saveTemplates(importFile, objectName, cols);
		return responseReplyJSON(reply);
	}

	// ----------------------------json end----------------------------------
    
    @Override
    public DataImportPageParam getPageParam() {
        DataImportPageParam param = new DataImportPageParam("/leadin/import", "base");
        return param;
    }

    @Override
    public List<String[]> getParamsList() {
        return new ArrayList<String[]>();
    }

    @Override
    public DataImportViewParam getViewParam() {
        DataImportViewParam param = new DataImportViewParam(importFile, getObjectName(),
                "defaultDataImportService");
        return param;
    }

    public boolean isTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(boolean templateConfig) {
        this.templateConfig = templateConfig;
    }

    public String getTemplateRemark() {
        return templateRemark;
    }

    public void setTemplateRemark(String templateRemark) {
        this.templateRemark = templateRemark;
    }

    public void setImportFile(String importFile) {
        this.importFile = importFile;
    }

    public void setLeadinImportDataTemplateService(
            ImportDataTemplateService leadinImportDataTemplateService) {
        this.leadinImportDataTemplateService = leadinImportDataTemplateService;
    }
    
    
}
