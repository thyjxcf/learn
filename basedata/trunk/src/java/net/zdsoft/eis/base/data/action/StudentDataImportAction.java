package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.DataImportBaseAction;
import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;

/**
 * 学生数据导入
 * 
 * @author weixh
 * @since 2016-3-1 下午2:20:33
 */
public class StudentDataImportAction extends DataImportBaseAction {
	private SystemIniService systemIniService;
	private String systemDeploySchool = null;
	
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8177692364403154883L;

    // 构造方法
    public StudentDataImportAction() {

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
        return paramsList;
    }
 
    public DataImportPageParam getPageParam() {
		DataImportPageParam param = new DataImportPageParam(
				"/basedata/stu", "studentImportAdmin-importMain");
		param.setHasTask(false);
		return param;
	}

    public DataImportViewParam getViewParam() {
		DataImportViewParam param;
		String val = getSystemDeploySchool();
        if(BaseConstant.SYS_DEPLOY_SCHOOL_SXRRT.equals(val)){
			param = new DataImportViewParam("student_import_rrt.xml",
					getObjectName(), "studentImportSxrrtService");
		} else {
			param = new DataImportViewParam("student_import_rrt.xml",
					getObjectName(), "studentImportSxrrtService");
		}
		return param;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public String getSystemDeploySchool() {
		if(systemDeploySchool == null){
			systemDeploySchool = systemIniService.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
		}
//		systemDeploySchool = BaseConstant.SYS_DEPLOY_SCHOOL_SXRRT;
		return systemDeploySchool;
	}
    
}
