/* 
 * @(#)AssistanceDataImportAction.java    Created on 2009-10-24
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.constant.enumeration.VersionType;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.frame.action.DataImportBaseAction;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;

/**
 * @author hexq
 * @version $Revision: 1.0 $, $Date: 2009-10-28 14:40:13 PM $
 */
public class FamilyDataImportAction extends DataImportBaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8177692364403154883L;
    private String studentId;
    
    public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	// 构造方法
    public FamilyDataImportAction() {

    }

    public String main() {
        return SUCCESS;
    }

    @Override
    public String subExecute() throws Exception {
        setCovered("1"); // 默认覆盖

        return SUCCESS;
    }

    @Override
    public List<String[]> getParamsList() {
        CurrentSemester basicSemesterDto = getCurrentSemester();

        List<String[]> paramsList = new ArrayList<String[]>();
        paramsList.add(new String[] { "semester", basicSemesterDto.getSemester()});
        paramsList.add(new String[] { "acadyear", basicSemesterDto.getAcadyear()});
        paramsList.add(new String[] { "username", getLoginInfo().getUser().getName() });
        paramsList.add(new String[] { "studentId", studentId });
        return paramsList;
    }
 
    public DataImportPageParam getPageParam() {
    	if("eis".equals(getSystemDeploySchVersion())){
    		DataImportPageParam param = new DataImportPageParam(
					"/stusys/sch/student", "studentadmin-familyAdmin-importMain");
			param.setHasTask(false);
			return param;
    	}else{
			DataImportPageParam param = new DataImportPageParam(
					"/basedata/stu", "familyAdmin-importMain");
			param.setHasTask(true);
			return param;
    	}
	}

    public DataImportViewParam getViewParam() {
    	if("eis".equals(getSystemDeploySchVersion())){
    		DataImportViewParam param = new DataImportViewParam(
    				"family_import.xml", "family_import_eis",
    				"familyImportService");
    		return param;
    	}else{
    		DataImportViewParam param = new DataImportViewParam(
    				"family_import.xml", "family_import",
    				"familyImportService");
    		return param;
    	}
	}
    public String getSystemDeploySchVersion(){
		if (systemDeployService == null) {
			systemDeployService = (SystemDeployService) ContainerManager
					.getComponent("systemDeployService");
		}
		if(VersionType.EISU == systemDeployService.getVersionType()){
			return "eisu";
		}
		return "eis";
	}
}
