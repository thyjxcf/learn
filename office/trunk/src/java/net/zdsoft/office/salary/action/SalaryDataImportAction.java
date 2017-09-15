package net.zdsoft.office.salary.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.action.DataImportBaseAction;
import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;
import net.zdsoft.leadin.util.excel.ZdCell;

@SuppressWarnings("serial")
public class SalaryDataImportAction extends DataImportBaseAction{
	
	public String subExecute() throws Exception {
		return SUCCESS;
	}
	
	@Override
	public List<String[]> getParamsList() {
		List<String[]> paramsList = new ArrayList<String[]>();
		paramsList.add(new String[] { "unitId", getUnitId() });
		return paramsList;
	}

	@Override
	public DataImportPageParam getPageParam() {
		DataImportPageParam param = new DataImportPageParam("/"+getNamespace(), "salarymanage-import");
		param.setHasTask(false);
		param.setDisplayCovered(false);
		param.setUserDefinedUrl(getNamespace()+"/salarymanage-import-viewRecord");
		return param;
	}

	@Override
	public DataImportViewParam getViewParam() {
		DataImportViewParam param = new DataImportViewParam("template/salaryTemplete_import.xml", "office_salary_information",
				"OfficeSalaryImportDataService");
		return param;
	}
	
	public String getNamespace() {
		return "office/salarymanage";
	}

	public static void main(String[] args) {//<#if officeSalaryImport.salary1?default("")!="">
        //<th style="width:10%">列项1：</th>
       // <td style="width:15%">
        	//<input type="text" class="input-txt fn-left" id="salary1" style="width:50px;" maxlength="25" name="officeSalarySort.salary1" value="${officeSalarySort.salary1!}">
        //</td>
        //</#if>
		int k=1;
		for (int i = 1; i <98; i++) {
			k++;
			if(i%5==0){
				System.out.println("\n");
			}
				System.out.println("<#if officeSalaryImport.salary"+i+"?default(\"\")!=\"\">");
				System.out.println("<th style=\"width:10%\">列项"+i+"：</th>");
				System.out.println("<td style=\"width:15%\">");
				System.out.println("<input type=\"text\" class=\"input-txt fn-left\" id=\"salary"+i+" style=\"width:50px;\" maxlength=\"25\" name=\"officeSalarySort.salary"+i+"value=\"${officeSalarySort.salary"+i+"!}\">");
				System.out.println("</td>");
				System.out.println("</#if>");
		}
	}
}
