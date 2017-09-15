package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.leadin.dataimport.action.DataImportBaseAction;
import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;

public class UnitImportAction extends DataImportBaseAction{
	private static final long serialVersionUID = 1L;
	public static final String UNIT_IMPORT= "unit_import";
	
	// 构造方法
	public UnitImportAction() {
	}
	public String main() {
		return SUCCESS;
	}

	@Override
	public String subExecute() throws Exception {
		return SUCCESS;
	}

	public DataImportPageParam getPageParam() {
		DataImportPageParam param = new DataImportPageParam(
				"/basedata/unit", "unitAdmin-importMain");
		param.setHasTask(false);
		param.setDisplayCovered(false);
		return param;
	}

	public DataImportViewParam getViewParam() {
		DataImportViewParam param = new DataImportViewParam(
				"unit_import.xml", getObjectName(), "unitImportService");
		return param;
	}

	@Override
	public List<String[]> getParamsList() {
//		List paramsList = new ArrayList<String[]>();
//		paramsList.add(new String[]{"unitId",getLoginInfo().getUnitID()});
//		return paramsList;
		return new ArrayList<String[]>();
	}
}

