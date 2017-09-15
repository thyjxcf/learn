package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.action.DataImportBaseAction;
import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;

public class TeacherDataSxrrtImportAction extends DataImportBaseAction {

	private static final long serialVersionUID = -4587177809034721124L;

	// 构造方法
	public TeacherDataSxrrtImportAction() {
	}

	public String main() {
		return SUCCESS;
	}
	
	@Override
	public String subExecute() throws Exception {
		setCovered("0"); // 不覆盖

		return SUCCESS;
	}
	
	@Override
	public List<String[]> getParamsList() {
		return new ArrayList<String[]>();
	}

	@Override
	public DataImportPageParam getPageParam() {
		DataImportPageParam param = new DataImportPageParam(
				"/basedata/teacher", "teacherAdmin-sxrrt-importMain");
		param.setHasTask(false);
		param.setDisplayCovered(false);
		return param;
	}

	@Override
	public DataImportViewParam getViewParam() {
		DataImportViewParam param = new DataImportViewParam(
				"teacher_sxrrt_import.xml", getObjectName(), "teacherSxrrtImportService");
		return param;
	}

}
