package net.zdsoft.office.salary.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.salary.entity.OfficeSalary;
import net.zdsoft.office.salary.entity.OfficeSalaryImport;
import net.zdsoft.office.salary.entity.OfficeSalarySort;
import net.zdsoft.office.salary.service.OfficeSalaryImportService;
import net.zdsoft.office.salary.service.OfficeSalaryService;
import net.zdsoft.office.salary.service.OfficeSalarySortService;

/**
* @Package net.zdsoft.office.salary.action 
* @author songxq  
* @date 2016-11-9 下午7:33:41 
* @version V1.0
 */
@SuppressWarnings("serial")
public class SalaryAction extends PageSemesterAction{
	
	private OfficeSalaryService officeSalaryService;
	private OfficeSalaryImportService officeSalaryImportService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeSalarySortService officeSalarySortService;
	
	private OfficeSalary officeSalary=new OfficeSalary();
	private List<OfficeSalary> officeSalaryList=new ArrayList<OfficeSalary>();
	private OfficeSalaryImport officeSalaryImport=new OfficeSalaryImport();
	private List<OfficeSalaryImport> officeSalaryImports=new ArrayList<OfficeSalaryImport>();
	private OfficeSalarySort officeSalarySort=new OfficeSalarySort();
	private String startTime;
	private String importId;
	private String salaryId;
	private String sortId;
	private String cardnumber;
	private String[] checkid;
	
	private List<String> times=new ArrayList<String>();
	private Map<String,Integer> colsMap=new HashMap<String, Integer>();
	
	//是否是管理员
	private boolean salaryManage=false;
	public static final String OFFICE_SALARY_MANAGE="office_salary_manage";
	
	private boolean isRegistOff=false;
	
	public String execute() throws Exception{
		return SUCCESS;
	}
	public String salaryManageAdmin(){
		if(StringUtils.isBlank(startTime)){
			//officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportByTime(getUnitId(),startTime);
			//if(officeSalaryImport!=null&&officeSalaryImport.getSalaryTime()!=null){
			//	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			//	startTime=sdf.format(officeSalaryImport.getSalaryTime());
			//}else{
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
				startTime=sdf.format(date);
			//}
		}
		officeSalaryImports=officeSalaryImportService.getOfficeSalaryImportByUnitIdAndTime(getUnitId(),startTime);
		return SUCCESS;
	}
	public String getSalaryTime(){
		officeSalaryImports=officeSalaryImportService.getOfficeSalaryImportByUnitIdAndTime(getUnitId(),startTime);
		return SUCCESS;
	}
	public String salaryManageList(){
		if(StringUtils.isNotBlank(importId)){
			officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(importId);
			officeSalarySort=officeSalarySortService.getOfficeSalarySortByImportId(importId);
			if(officeSalarySort==null){
				officeSalarySort=new OfficeSalarySort();
			}
			if(officeSalarySort!=null){
				int time=officeSalarySort.getSortAmount();
				String canbreak="1";
				int k=4;
				times.add("1");
				if(org.apache.commons.lang3.StringUtils.isNotBlank(officeSalarySort.getSort1())){
					colsMap.put(canbreak, 3);
				}
				for (int i = 1; i <= time; i++) {
					if(org.apache.commons.lang3.StringUtils.isNotBlank(getProperty(officeSalarySort,"sort"+i))){
						times.add(getProperty(officeSalarySort,"sort"+i));
						canbreak=getProperty(officeSalarySort,"sort"+i);
						k=1;
					}
						colsMap.put(canbreak, k);
					k++;
				}
			}
			officeSalaryList=officeSalaryService.getOfficeSalaryByUnitIdAndImportId(getUnitId(), importId, getPage());
			Collections.sort(officeSalaryList, new Comparator<OfficeSalary>() {
				@Override
				public int compare(OfficeSalary o1, OfficeSalary o2) {
					int i=StringUtils.isBlank(o1.getSerialNumbers())?0:Integer.parseInt(o1.getSerialNumbers());
					int j=StringUtils.isBlank(o2.getSerialNumbers())?0:Integer.parseInt(o2.getSerialNumbers());
					return i-j;
				}
			});
		}else{
			officeSalaryImport=new OfficeSalaryImport();
			officeSalaryList=new ArrayList<OfficeSalary>();
			officeSalarySort=new OfficeSalarySort();
		}
		return SUCCESS;
	}
	public String salaryManageEdit(){
		if(StringUtils.isNotBlank(salaryId)){
			officeSalary=officeSalaryService.getOfficeSalaryById(salaryId);
			officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(officeSalary.getImportId());
		}
		if(officeSalary==null){
			officeSalary=new OfficeSalary();
		}
		return SUCCESS;
	}
	public String salarySortEdit(){//TODO
		if(StringUtils.isNotBlank(sortId)){
			officeSalarySort=officeSalarySortService.getOfficeSalarySortById(sortId);
			officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(officeSalarySort.getImportId());
		}
		if(officeSalarySort==null){
			officeSalarySort=new OfficeSalarySort();
		}
		return SUCCESS;
	}
	public String salaryImportEdit(){
		if(StringUtils.isNotBlank(importId)){
			officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(importId);
		}
		if(officeSalaryImport==null){
			officeSalaryImport=new OfficeSalaryImport();
		}
		return SUCCESS;
	}
	public String salaryManageDelete(){
		try {
			if(StringUtils.isNotBlank(salaryId)){
				officeSalaryService.delete(new String[]{salaryId});
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("id不存在!");
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败：");
		}
		return SUCCESS;
	}
	public String salaryManageSave(){
		try {
			OfficeSalary officeSalarys=null;
			if(StringUtils.isNotBlank(officeSalary.getId())){
				officeSalarys=officeSalaryService.getOfficeSalaryById(officeSalary.getId());
			}
			if(officeSalarys!=null){
				officeSalarys.setSalary1(officeSalary.getSalary1());officeSalarys.setSalary2(officeSalary.getSalary2());
				officeSalarys.setSalary3(officeSalary.getSalary3());officeSalarys.setSalary4(officeSalary.getSalary4());
				officeSalarys.setSalary5(officeSalary.getSalary5());officeSalarys.setSalary6(officeSalary.getSalary6());
				officeSalarys.setSalary7(officeSalary.getSalary7());officeSalarys.setSalary8(officeSalary.getSalary8());
				officeSalarys.setSalary9(officeSalary.getSalary9());officeSalarys.setSalary10(officeSalary.getSalary10());
				officeSalarys.setSalary11(officeSalary.getSalary11());officeSalarys.setSalary12(officeSalary.getSalary12());
				officeSalarys.setSalary13(officeSalary.getSalary13());officeSalarys.setSalary14(officeSalary.getSalary14());
				officeSalarys.setSalary15(officeSalary.getSalary15());officeSalarys.setSalary16(officeSalary.getSalary16());
				officeSalarys.setSalary17(officeSalary.getSalary17());officeSalarys.setSalary18(officeSalary.getSalary18());
				officeSalarys.setSalary19(officeSalary.getSalary19());officeSalarys.setSalary20(officeSalary.getSalary20());
				officeSalarys.setSalary21(officeSalary.getSalary21());officeSalarys.setSalary22(officeSalary.getSalary22());
				officeSalarys.setSalary23(officeSalary.getSalary23());officeSalarys.setSalary24(officeSalary.getSalary24());
				officeSalarys.setSalary25(officeSalary.getSalary25());officeSalarys.setSalary26(officeSalary.getSalary26());
				officeSalarys.setSalary27(officeSalary.getSalary27());officeSalarys.setSalary28(officeSalary.getSalary28());
				officeSalarys.setSalary29(officeSalary.getSalary29());officeSalarys.setSalary30(officeSalary.getSalary30());
				officeSalarys.setSalary31(officeSalary.getSalary31());officeSalarys.setSalary32(officeSalary.getSalary32());
				officeSalarys.setSalary33(officeSalary.getSalary33());officeSalarys.setSalary34(officeSalary.getSalary34());
				officeSalarys.setSalary35(officeSalary.getSalary35());officeSalarys.setSalary36(officeSalary.getSalary36());
				officeSalarys.setSalary37(officeSalary.getSalary37());officeSalarys.setSalary38(officeSalary.getSalary38());
				officeSalarys.setSalary39(officeSalary.getSalary39());officeSalarys.setSalary40(officeSalary.getSalary40());
				officeSalarys.setSalary41(officeSalary.getSalary41());officeSalarys.setSalary42(officeSalary.getSalary42());
				officeSalarys.setSalary43(officeSalary.getSalary43());officeSalarys.setSalary44(officeSalary.getSalary44());
				officeSalarys.setSalary45(officeSalary.getSalary45());officeSalarys.setSalary46(officeSalary.getSalary46());
				officeSalarys.setSalary47(officeSalary.getSalary47());officeSalarys.setSalary48(officeSalary.getSalary48());
				officeSalarys.setSalary49(officeSalary.getSalary49());officeSalarys.setSalary50(officeSalary.getSalary50());
				officeSalarys.setSalary51(officeSalary.getSalary51());officeSalarys.setSalary52(officeSalary.getSalary52());
				officeSalarys.setSalary53(officeSalary.getSalary53());officeSalarys.setSalary54(officeSalary.getSalary54());
				officeSalarys.setSalary55(officeSalary.getSalary55());officeSalarys.setSalary56(officeSalary.getSalary56());
				officeSalarys.setSalary57(officeSalary.getSalary57());officeSalarys.setSalary58(officeSalary.getSalary58());
				officeSalarys.setSalary59(officeSalary.getSalary59());officeSalarys.setSalary60(officeSalary.getSalary60());
				officeSalarys.setSalary61(officeSalary.getSalary61());officeSalarys.setSalary62(officeSalary.getSalary62());
				officeSalarys.setSalary63(officeSalary.getSalary63());officeSalarys.setSalary64(officeSalary.getSalary64());
				officeSalarys.setSalary65(officeSalary.getSalary65());officeSalarys.setSalary66(officeSalary.getSalary66());
				officeSalarys.setSalary67(officeSalary.getSalary67());officeSalarys.setSalary68(officeSalary.getSalary68());
				officeSalarys.setSalary69(officeSalary.getSalary69());officeSalarys.setSalary70(officeSalary.getSalary70());
				officeSalarys.setSalary71(officeSalary.getSalary71());officeSalarys.setSalary72(officeSalary.getSalary72());
				officeSalarys.setSalary73(officeSalary.getSalary73());officeSalarys.setSalary74(officeSalary.getSalary74());
				officeSalarys.setSalary75(officeSalary.getSalary75());officeSalarys.setSalary76(officeSalary.getSalary76());
				officeSalarys.setSalary77(officeSalary.getSalary77());officeSalarys.setSalary78(officeSalary.getSalary78());
				officeSalarys.setSalary79(officeSalary.getSalary79());officeSalarys.setSalary80(officeSalary.getSalary80());
				officeSalarys.setSalary81(officeSalary.getSalary81());officeSalarys.setSalary82(officeSalary.getSalary82());
				officeSalarys.setSalary83(officeSalary.getSalary83());officeSalarys.setSalary84(officeSalary.getSalary84());
				officeSalarys.setSalary85(officeSalary.getSalary85());officeSalarys.setSalary86(officeSalary.getSalary86());
				officeSalarys.setSalary87(officeSalary.getSalary87());officeSalarys.setSalary88(officeSalary.getSalary88());
				officeSalarys.setSalary89(officeSalary.getSalary89());officeSalarys.setSalary90(officeSalary.getSalary90());
				officeSalarys.setSalary91(officeSalary.getSalary91());officeSalarys.setSalary92(officeSalary.getSalary92());
				officeSalarys.setSalary93(officeSalary.getSalary93());officeSalarys.setSalary94(officeSalary.getSalary94());
				officeSalarys.setSalary95(officeSalary.getSalary95());officeSalarys.setSalary96(officeSalary.getSalary96());
				officeSalarys.setSalary97(officeSalary.getSalary97());

				officeSalaryService.update(officeSalarys);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("id不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	public String salaryImportSave(){
		try {
			OfficeSalaryImport officeSalaryImportss=null;
			if(StringUtils.isNotBlank(officeSalaryImport.getId())){
				officeSalaryImportss=officeSalaryImportService.getOfficeSalaryImportById(officeSalaryImport.getId());
			}
			if(officeSalaryImportss!=null){
				officeSalaryImportss.setSalary1(officeSalaryImport.getSalary1());officeSalaryImportss.setSalary2(officeSalaryImport.getSalary2());
				officeSalaryImportss.setSalary3(officeSalaryImport.getSalary3());officeSalaryImportss.setSalary4(officeSalaryImport.getSalary4());
				officeSalaryImportss.setSalary5(officeSalaryImport.getSalary5());officeSalaryImportss.setSalary6(officeSalaryImport.getSalary6());
				officeSalaryImportss.setSalary7(officeSalaryImport.getSalary7());officeSalaryImportss.setSalary8(officeSalaryImport.getSalary8());
				officeSalaryImportss.setSalary9(officeSalaryImport.getSalary9());officeSalaryImportss.setSalary10(officeSalaryImport.getSalary10());
				officeSalaryImportss.setSalary11(officeSalaryImport.getSalary11());officeSalaryImportss.setSalary12(officeSalaryImport.getSalary12());
				officeSalaryImportss.setSalary13(officeSalaryImport.getSalary13());officeSalaryImportss.setSalary14(officeSalaryImport.getSalary14());
				officeSalaryImportss.setSalary15(officeSalaryImport.getSalary15());officeSalaryImportss.setSalary16(officeSalaryImport.getSalary16());
				officeSalaryImportss.setSalary17(officeSalaryImport.getSalary17());officeSalaryImportss.setSalary18(officeSalaryImport.getSalary18());
				officeSalaryImportss.setSalary19(officeSalaryImport.getSalary19());officeSalaryImportss.setSalary20(officeSalaryImport.getSalary20());
				officeSalaryImportss.setSalary21(officeSalaryImport.getSalary21());officeSalaryImportss.setSalary22(officeSalaryImport.getSalary22());
				officeSalaryImportss.setSalary23(officeSalaryImport.getSalary23());officeSalaryImportss.setSalary24(officeSalaryImport.getSalary24());
				officeSalaryImportss.setSalary25(officeSalaryImport.getSalary25());officeSalaryImportss.setSalary26(officeSalaryImport.getSalary26());
				officeSalaryImportss.setSalary27(officeSalaryImport.getSalary27());officeSalaryImportss.setSalary28(officeSalaryImport.getSalary28());
				officeSalaryImportss.setSalary29(officeSalaryImport.getSalary29());officeSalaryImportss.setSalary30(officeSalaryImport.getSalary30());
				officeSalaryImportss.setSalary31(officeSalaryImport.getSalary31());officeSalaryImportss.setSalary32(officeSalaryImport.getSalary32());
				officeSalaryImportss.setSalary33(officeSalaryImport.getSalary33());officeSalaryImportss.setSalary34(officeSalaryImport.getSalary34());
				officeSalaryImportss.setSalary35(officeSalaryImport.getSalary35());officeSalaryImportss.setSalary36(officeSalaryImport.getSalary36());
				officeSalaryImportss.setSalary37(officeSalaryImport.getSalary37());officeSalaryImportss.setSalary38(officeSalaryImport.getSalary38());
				officeSalaryImportss.setSalary39(officeSalaryImport.getSalary39());officeSalaryImportss.setSalary40(officeSalaryImport.getSalary40());
				officeSalaryImportss.setSalary41(officeSalaryImport.getSalary41());officeSalaryImportss.setSalary42(officeSalaryImport.getSalary42());
				officeSalaryImportss.setSalary43(officeSalaryImport.getSalary43());officeSalaryImportss.setSalary44(officeSalaryImport.getSalary44());
				officeSalaryImportss.setSalary45(officeSalaryImport.getSalary45());officeSalaryImportss.setSalary46(officeSalaryImport.getSalary46());
				officeSalaryImportss.setSalary47(officeSalaryImport.getSalary47());officeSalaryImportss.setSalary48(officeSalaryImport.getSalary48());
				officeSalaryImportss.setSalary49(officeSalaryImport.getSalary49());officeSalaryImportss.setSalary50(officeSalaryImport.getSalary50());
				officeSalaryImportss.setSalary51(officeSalaryImport.getSalary51());officeSalaryImportss.setSalary52(officeSalaryImport.getSalary52());
				officeSalaryImportss.setSalary53(officeSalaryImport.getSalary53());officeSalaryImportss.setSalary54(officeSalaryImport.getSalary54());
				officeSalaryImportss.setSalary55(officeSalaryImport.getSalary55());officeSalaryImportss.setSalary56(officeSalaryImport.getSalary56());
				officeSalaryImportss.setSalary57(officeSalaryImport.getSalary57());officeSalaryImportss.setSalary58(officeSalaryImport.getSalary58());
				officeSalaryImportss.setSalary59(officeSalaryImport.getSalary59());officeSalaryImportss.setSalary60(officeSalaryImport.getSalary60());
				officeSalaryImportss.setSalary61(officeSalaryImport.getSalary61());officeSalaryImportss.setSalary62(officeSalaryImport.getSalary62());
				officeSalaryImportss.setSalary63(officeSalaryImport.getSalary63());officeSalaryImportss.setSalary64(officeSalaryImport.getSalary64());
				officeSalaryImportss.setSalary65(officeSalaryImport.getSalary65());officeSalaryImportss.setSalary66(officeSalaryImport.getSalary66());
				officeSalaryImportss.setSalary67(officeSalaryImport.getSalary67());officeSalaryImportss.setSalary68(officeSalaryImport.getSalary68());
				officeSalaryImportss.setSalary69(officeSalaryImport.getSalary69());officeSalaryImportss.setSalary70(officeSalaryImport.getSalary70());
				officeSalaryImportss.setSalary71(officeSalaryImport.getSalary71());officeSalaryImportss.setSalary72(officeSalaryImport.getSalary72());
				officeSalaryImportss.setSalary73(officeSalaryImport.getSalary73());officeSalaryImportss.setSalary74(officeSalaryImport.getSalary74());
				officeSalaryImportss.setSalary75(officeSalaryImport.getSalary75());officeSalaryImportss.setSalary76(officeSalaryImport.getSalary76());
				officeSalaryImportss.setSalary77(officeSalaryImport.getSalary77());officeSalaryImportss.setSalary78(officeSalaryImport.getSalary78());
				officeSalaryImportss.setSalary79(officeSalaryImport.getSalary79());officeSalaryImportss.setSalary80(officeSalaryImport.getSalary80());
				officeSalaryImportss.setSalary81(officeSalaryImport.getSalary81());officeSalaryImportss.setSalary82(officeSalaryImport.getSalary82());
				officeSalaryImportss.setSalary83(officeSalaryImport.getSalary83());officeSalaryImportss.setSalary84(officeSalaryImport.getSalary84());
				officeSalaryImportss.setSalary85(officeSalaryImport.getSalary85());officeSalaryImportss.setSalary86(officeSalaryImport.getSalary86());
				officeSalaryImportss.setSalary87(officeSalaryImport.getSalary87());officeSalaryImportss.setSalary88(officeSalaryImport.getSalary88());
				officeSalaryImportss.setSalary89(officeSalaryImport.getSalary89());officeSalaryImportss.setSalary90(officeSalaryImport.getSalary90());
				officeSalaryImportss.setSalary91(officeSalaryImport.getSalary91());officeSalaryImportss.setSalary92(officeSalaryImport.getSalary92());
				officeSalaryImportss.setSalary93(officeSalaryImport.getSalary93());officeSalaryImportss.setSalary94(officeSalaryImport.getSalary94());
				officeSalaryImportss.setSalary95(officeSalaryImport.getSalary95());officeSalaryImportss.setSalary96(officeSalaryImport.getSalary96());
				officeSalaryImportss.setSalary97(officeSalaryImport.getSalary97());
				
				officeSalaryImportService.update(officeSalaryImportss);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("id不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	public String salarySortSave(){//TODO
		try {
			OfficeSalarySort officeSorts=null;
			if(StringUtils.isNotBlank(officeSalarySort.getId())){
				officeSorts=officeSalarySortService.getOfficeSalarySortById(officeSalarySort.getId());
			}
			if(officeSorts!=null){
				officeSorts.setSort1(officeSalarySort.getSort1());officeSorts.setSort2(officeSalarySort.getSort2());
				officeSorts.setSort3(officeSalarySort.getSort3());officeSorts.setSort4(officeSalarySort.getSort4());
				officeSorts.setSort5(officeSalarySort.getSort5());officeSorts.setSort6(officeSalarySort.getSort6());
				officeSorts.setSort7(officeSalarySort.getSort7());officeSorts.setSort8(officeSalarySort.getSort8());
				officeSorts.setSort9(officeSalarySort.getSort9());officeSorts.setSort10(officeSalarySort.getSort10());
				officeSorts.setSort11(officeSalarySort.getSort11());officeSorts.setSort12(officeSalarySort.getSort12());
				officeSorts.setSort13(officeSalarySort.getSort13());officeSorts.setSort14(officeSalarySort.getSort14());
				officeSorts.setSort15(officeSalarySort.getSort15());officeSorts.setSort16(officeSalarySort.getSort16());
				officeSorts.setSort17(officeSalarySort.getSort17());officeSorts.setSort18(officeSalarySort.getSort18());
				officeSorts.setSort19(officeSalarySort.getSort19());officeSorts.setSort20(officeSalarySort.getSort20());
				officeSorts.setSort21(officeSalarySort.getSort21());officeSorts.setSort22(officeSalarySort.getSort22());
				officeSorts.setSort23(officeSalarySort.getSort23());officeSorts.setSort24(officeSalarySort.getSort24());
				officeSorts.setSort25(officeSalarySort.getSort25());officeSorts.setSort26(officeSalarySort.getSort26());
				officeSorts.setSort27(officeSalarySort.getSort27());officeSorts.setSort28(officeSalarySort.getSort28());
				officeSorts.setSort29(officeSalarySort.getSort29());officeSorts.setSort30(officeSalarySort.getSort30());
				officeSorts.setSort31(officeSalarySort.getSort31());officeSorts.setSort32(officeSalarySort.getSort32());
				officeSorts.setSort33(officeSalarySort.getSort33());officeSorts.setSort34(officeSalarySort.getSort34());
				officeSorts.setSort35(officeSalarySort.getSort35());officeSorts.setSort36(officeSalarySort.getSort36());
				officeSorts.setSort37(officeSalarySort.getSort37());officeSorts.setSort38(officeSalarySort.getSort38());
				officeSorts.setSort39(officeSalarySort.getSort39());officeSorts.setSort40(officeSalarySort.getSort40());
				officeSorts.setSort41(officeSalarySort.getSort41());officeSorts.setSort42(officeSalarySort.getSort42());
				officeSorts.setSort43(officeSalarySort.getSort43());officeSorts.setSort44(officeSalarySort.getSort44());
				officeSorts.setSort45(officeSalarySort.getSort45());officeSorts.setSort46(officeSalarySort.getSort46());
				officeSorts.setSort47(officeSalarySort.getSort47());officeSorts.setSort48(officeSalarySort.getSort48());
				officeSorts.setSort49(officeSalarySort.getSort49());officeSorts.setSort50(officeSalarySort.getSort50());
				officeSorts.setSort51(officeSalarySort.getSort51());officeSorts.setSort52(officeSalarySort.getSort52());
				officeSorts.setSort53(officeSalarySort.getSort53());officeSorts.setSort54(officeSalarySort.getSort54());
				officeSorts.setSort55(officeSalarySort.getSort55());officeSorts.setSort56(officeSalarySort.getSort56());
				officeSorts.setSort57(officeSalarySort.getSort57());officeSorts.setSort58(officeSalarySort.getSort58());
				officeSorts.setSort59(officeSalarySort.getSort59());officeSorts.setSort60(officeSalarySort.getSort60());
				officeSorts.setSort61(officeSalarySort.getSort61());officeSorts.setSort62(officeSalarySort.getSort62());
				officeSorts.setSort63(officeSalarySort.getSort63());officeSorts.setSort64(officeSalarySort.getSort64());
				officeSorts.setSort65(officeSalarySort.getSort65());officeSorts.setSort66(officeSalarySort.getSort66());
				officeSorts.setSort67(officeSalarySort.getSort67());officeSorts.setSort68(officeSalarySort.getSort68());
				officeSorts.setSort69(officeSalarySort.getSort69());officeSorts.setSort70(officeSalarySort.getSort70());
				officeSorts.setSort71(officeSalarySort.getSort71());officeSorts.setSort72(officeSalarySort.getSort72());
				officeSorts.setSort73(officeSalarySort.getSort73());officeSorts.setSort74(officeSalarySort.getSort74());
				officeSorts.setSort75(officeSalarySort.getSort75());officeSorts.setSort76(officeSalarySort.getSort76());
				officeSorts.setSort77(officeSalarySort.getSort77());officeSorts.setSort78(officeSalarySort.getSort78());
				officeSorts.setSort79(officeSalarySort.getSort79());officeSorts.setSort80(officeSalarySort.getSort80());
				officeSorts.setSort81(officeSalarySort.getSort81());officeSorts.setSort82(officeSalarySort.getSort82());
				officeSorts.setSort83(officeSalarySort.getSort83());officeSorts.setSort84(officeSalarySort.getSort84());
				officeSorts.setSort85(officeSalarySort.getSort85());officeSorts.setSort86(officeSalarySort.getSort86());
				officeSorts.setSort87(officeSalarySort.getSort87());officeSorts.setSort88(officeSalarySort.getSort88());
				officeSorts.setSort89(officeSalarySort.getSort89());officeSorts.setSort90(officeSalarySort.getSort90());
				officeSorts.setSort91(officeSalarySort.getSort91());officeSorts.setSort92(officeSalarySort.getSort92());
				officeSorts.setSort93(officeSalarySort.getSort93());officeSorts.setSort94(officeSalarySort.getSort94());
				officeSorts.setSort95(officeSalarySort.getSort95());officeSorts.setSort96(officeSalarySort.getSort96());
				officeSorts.setSort97(officeSalarySort.getSort97());
				
				officeSalarySortService.update(officeSorts);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("id不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	public String doExport(){
		if(StringUtils.isNotBlank(importId)){
			officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(importId);
			officeSalaryList=officeSalaryService.getOfficeSalaryByUnitIdAndImportId(getUnitId(), importId, null);
		}
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		
		List<ZdCell>zdlist2=new ArrayList<ZdCell>();
		if(this.isRegistOff()){
			officeSalarySort=officeSalarySortService.getOfficeSalarySortByImportId(importId);
			if(officeSalarySort!=null){
				int time=officeSalarySort.getSortAmount();
				String canbreak="1";
				int k=4;
				times.add("1");
				if(org.apache.commons.lang3.StringUtils.isNotBlank(officeSalarySort.getSort1())){
					colsMap.put(canbreak, 3);
				}
				for (int i = 1; i <= time; i++) {
					if(org.apache.commons.lang3.StringUtils.isNotBlank(getProperty(officeSalarySort,"sort"+i))){
						times.add(getProperty(officeSalarySort,"sort"+i));
						canbreak=getProperty(officeSalarySort,"sort"+i);
						k=1;
					}
						colsMap.put(canbreak, k);
					k++;
				}
			}
			for (String time : times) {
				if(org.apache.commons.lang3.StringUtils.equals("1", time)){
					zdlist2.add(new ZdCell("", colsMap.get(time), 1, style2));
				}else{
					zdlist2.add(new ZdCell(time, colsMap.get(time), 1, style2));
				}
			}
		}
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		if(StringUtils.isNotBlank(importId)){
			zdlist.add(new ZdCell(officeSalaryImport.getSerialNumbers(),1,style2));
			zdlist.add(new ZdCell(officeSalaryImport.getRealname(),1,style2));
			zdlist.add(new ZdCell(officeSalaryImport.getCardnumber(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary1())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary1(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary2())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary2(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary3())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary3(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary4())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary4(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary5())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary5(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary6())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary6(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary7())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary7(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary8())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary8(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary9())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary9(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary10())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary10(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary11())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary11(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary12())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary12(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary13())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary13(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary14())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary14(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary15())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary15(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary16())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary16(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary17())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary17(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary18())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary18(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary19())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary19(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary20())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary20(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary21())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary21(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary22())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary22(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary23())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary23(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary24())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary24(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary25())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary25(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary26())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary26(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary27())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary27(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary28())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary28(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary29())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary29(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary30())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary30(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary31())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary31(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary32())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary32(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary33())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary33(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary34())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary34(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary35())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary35(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary36())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary36(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary37())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary37(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary38())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary38(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary39())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary39(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary40())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary40(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary41())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary41(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary42())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary42(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary43())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary43(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary44())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary44(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary45())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary45(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary46())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary46(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary47())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary47(),1,style2));
		}
		
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary48())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary48(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary49())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary49(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary50())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary50(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary51())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary51(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary52())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary52(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary53())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary53(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary54())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary54(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary55())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary55(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary56())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary56(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary57())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary57(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary58())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary58(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary59())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary59(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary60())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary60(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary61())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary61(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary62())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary62(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary63())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary63(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary64())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary64(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary65())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary65(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary66())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary66(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary67())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary67(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary68())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary68(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary69())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary69(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary70())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary70(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary71())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary71(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary72())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary72(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary73())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary73(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary74())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary74(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary75())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary75(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary76())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary76(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary77())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary77(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary78())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary78(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary79())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary79(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary80())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary80(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary81())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary81(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary82())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary82(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary83())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary83(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary84())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary84(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary85())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary85(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary86())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary86(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary87())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary87(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary88())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary88(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary89())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary89(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary90())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary90(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary91())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary91(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary92())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary92(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary93())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary93(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary94())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary94(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary95())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary95(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary96())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary96(),1,style2));
		}
		if(StringUtils.isNotBlank(officeSalaryImport.getSalary97())){
			zdlist.add(new ZdCell(officeSalaryImport.getSalary97(),1,style2));
		}
		
		zdExcel.add(new ZdCell(officeSalaryImport.getMonthtime(), zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdlist2.toArray(new ZdCell[0]));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for (OfficeSalary item : officeSalaryList) {
			int index = 0;
			ZdCell[] cells = new ZdCell[zdlist.size()];
			cells[index++] = new ZdCell(item.getSerialNumbers(), 1, style3);
			cells[index++] = new ZdCell(item.getRealname(), 1, style3);
			cells[index++] = new ZdCell(item.getCardnumber(), 1, style3);
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary1())){
				cells[index++] = new ZdCell(item.getSalary1(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary2())){
				cells[index++] = new ZdCell(item.getSalary2(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary3())){
				cells[index++] = new ZdCell(item.getSalary3(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary4())){
				cells[index++] = new ZdCell(item.getSalary4(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary5())){
				cells[index++] = new ZdCell(item.getSalary5(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary6())){
				cells[index++] = new ZdCell(item.getSalary6(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary7())){
				cells[index++] = new ZdCell(item.getSalary7(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary8())){
				cells[index++] = new ZdCell(item.getSalary8(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary9())){
				cells[index++] = new ZdCell(item.getSalary9(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary10())){
				cells[index++] = new ZdCell(item.getSalary10(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary11())){
				cells[index++] = new ZdCell(item.getSalary11(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary12())){
				cells[index++] = new ZdCell(item.getSalary12(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary13())){
				cells[index++] = new ZdCell(item.getSalary13(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary14())){
				cells[index++] = new ZdCell(item.getSalary14(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary15())){
				cells[index++] = new ZdCell(item.getSalary15(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary16())){
				cells[index++] = new ZdCell(item.getSalary16(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary17())){
				cells[index++] = new ZdCell(item.getSalary17(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary18())){
				cells[index++] = new ZdCell(item.getSalary18(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary19())){
				cells[index++] = new ZdCell(item.getSalary19(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary20())){
				cells[index++] = new ZdCell(item.getSalary20(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary21())){
				cells[index++] = new ZdCell(item.getSalary21(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary22())){
				cells[index++] = new ZdCell(item.getSalary22(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary23())){
				cells[index++] = new ZdCell(item.getSalary23(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary24())){
				cells[index++] = new ZdCell(item.getSalary24(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary25())){
				cells[index++] = new ZdCell(item.getSalary25(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary26())){
				cells[index++] = new ZdCell(item.getSalary26(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary27())){
				cells[index++] = new ZdCell(item.getSalary27(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary28())){
				cells[index++] = new ZdCell(item.getSalary28(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary29())){
				cells[index++] = new ZdCell(item.getSalary29(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary30())){
				cells[index++] = new ZdCell(item.getSalary30(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary31())){
				cells[index++] = new ZdCell(item.getSalary31(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary32())){
				cells[index++] = new ZdCell(item.getSalary32(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary33())){
				cells[index++] = new ZdCell(item.getSalary33(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary34())){
				cells[index++] = new ZdCell(item.getSalary34(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary35())){
				cells[index++] = new ZdCell(item.getSalary35(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary36())){
				cells[index++] = new ZdCell(item.getSalary36(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary37())){
				cells[index++] = new ZdCell(item.getSalary37(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary38())){
				cells[index++] = new ZdCell(item.getSalary38(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary39())){
				cells[index++] = new ZdCell(item.getSalary39(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary40())){
				cells[index++] = new ZdCell(item.getSalary40(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary41())){
				cells[index++] = new ZdCell(item.getSalary41(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary42())){
				cells[index++] = new ZdCell(item.getSalary42(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary43())){
				cells[index++] = new ZdCell(item.getSalary43(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary44())){
				cells[index++] = new ZdCell(item.getSalary44(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary45())){
				cells[index++] = new ZdCell(item.getSalary45(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary46())){
				cells[index++] = new ZdCell(item.getSalary46(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary47())){
				cells[index++] = new ZdCell(item.getSalary47(), 1, style3);
			}
			
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary48())){
			    cells[index++] = new ZdCell(item.getSalary48(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary49())){
			    cells[index++] = new ZdCell(item.getSalary49(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary50())){
			    cells[index++] = new ZdCell(item.getSalary50(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary51())){
			    cells[index++] = new ZdCell(item.getSalary51(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary52())){
			    cells[index++] = new ZdCell(item.getSalary52(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary53())){
			    cells[index++] = new ZdCell(item.getSalary53(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary54())){
			    cells[index++] = new ZdCell(item.getSalary54(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary55())){
			    cells[index++] = new ZdCell(item.getSalary55(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary56())){
			    cells[index++] = new ZdCell(item.getSalary56(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary57())){
			    cells[index++] = new ZdCell(item.getSalary57(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary58())){
			    cells[index++] = new ZdCell(item.getSalary58(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary59())){
			    cells[index++] = new ZdCell(item.getSalary59(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary60())){
			    cells[index++] = new ZdCell(item.getSalary60(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary61())){
			    cells[index++] = new ZdCell(item.getSalary61(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary62())){
			    cells[index++] = new ZdCell(item.getSalary62(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary63())){
			    cells[index++] = new ZdCell(item.getSalary63(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary64())){
			    cells[index++] = new ZdCell(item.getSalary64(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary65())){
			    cells[index++] = new ZdCell(item.getSalary65(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary66())){
			    cells[index++] = new ZdCell(item.getSalary66(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary67())){
			    cells[index++] = new ZdCell(item.getSalary67(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary68())){
			    cells[index++] = new ZdCell(item.getSalary68(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary69())){
			    cells[index++] = new ZdCell(item.getSalary69(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary70())){
			    cells[index++] = new ZdCell(item.getSalary70(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary71())){
			    cells[index++] = new ZdCell(item.getSalary71(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary72())){
			    cells[index++] = new ZdCell(item.getSalary72(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary73())){
			    cells[index++] = new ZdCell(item.getSalary73(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary74())){
			    cells[index++] = new ZdCell(item.getSalary74(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary75())){
			    cells[index++] = new ZdCell(item.getSalary75(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary76())){
			    cells[index++] = new ZdCell(item.getSalary76(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary77())){
			    cells[index++] = new ZdCell(item.getSalary77(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary78())){
			    cells[index++] = new ZdCell(item.getSalary78(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary79())){
			    cells[index++] = new ZdCell(item.getSalary79(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary80())){
			    cells[index++] = new ZdCell(item.getSalary80(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary81())){
			    cells[index++] = new ZdCell(item.getSalary81(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary82())){
			    cells[index++] = new ZdCell(item.getSalary82(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary83())){
			    cells[index++] = new ZdCell(item.getSalary83(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary84())){
			    cells[index++] = new ZdCell(item.getSalary84(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary85())){
			    cells[index++] = new ZdCell(item.getSalary85(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary86())){
			    cells[index++] = new ZdCell(item.getSalary86(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary87())){
			    cells[index++] = new ZdCell(item.getSalary87(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary88())){
			    cells[index++] = new ZdCell(item.getSalary88(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary89())){
			    cells[index++] = new ZdCell(item.getSalary89(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary90())){
			    cells[index++] = new ZdCell(item.getSalary90(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary91())){
			    cells[index++] = new ZdCell(item.getSalary91(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary92())){
			    cells[index++] = new ZdCell(item.getSalary92(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary93())){
			    cells[index++] = new ZdCell(item.getSalary93(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary94())){
			    cells[index++] = new ZdCell(item.getSalary94(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary95())){
			    cells[index++] = new ZdCell(item.getSalary95(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary96())){
			    cells[index++] = new ZdCell(item.getSalary96(), 1, style3);
			}
			if(StringUtils.isNotBlank(officeSalaryImport.getSalary97())){
			    cells[index++] = new ZdCell(item.getSalary97(), 1, style3);
			}

			zdExcel.add(cells);
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("salary_import");
		return NONE;
	}
	public String mySalaryAdmin(){
		if(StringUtils.isBlank(startTime)){
			//officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportByTime(getUnitId(),startTime);
			//if(officeSalaryImport!=null&&officeSalaryImport.getSalaryTime()!=null){
			//	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			//	startTime=sdf.format(officeSalaryImport.getSalaryTime());
			//}else{
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
				startTime=sdf.format(date);
			//}
		}
		officeSalaryImports=officeSalaryImportService.getOfficeSalaryImportByUnitIdAndTime(getUnitId(),startTime);
		officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportByTime(getUnitId(),startTime);
		if(officeSalaryImport==null){
			officeSalaryImport=new OfficeSalaryImport();
		}
		return SUCCESS;
	}
	public String mySalaryList(){//TODO
		if(this.isSalaryManage()){
			if(StringUtils.isNotBlank(importId)){
				officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(importId);
				officeSalarySort=officeSalarySortService.getOfficeSalarySortByImportId(importId);
				if(officeSalarySort==null){
					officeSalarySort=new OfficeSalarySort();
				}
				if(officeSalarySort!=null){
					int time=officeSalarySort.getSortAmount();
					String canbreak="1";
					int k=4;
					times.add("1");
					if(org.apache.commons.lang3.StringUtils.isNotBlank(officeSalarySort.getSort1())){
						colsMap.put(canbreak, 3);
					}
					for (int i = 1; i <= time; i++) {
						if(org.apache.commons.lang3.StringUtils.isNotBlank(getProperty(officeSalarySort,"sort"+i))){
							times.add(getProperty(officeSalarySort,"sort"+i));
							canbreak=getProperty(officeSalarySort,"sort"+i);
							k=1;
						}
							colsMap.put(canbreak, k);
						k++;
					}
				}
				officeSalaryList=officeSalaryService.getOfficeSalaryByUnitIdAndCardnumber(getUnitId(), importId, null,cardnumber,getPage());
				if(CollectionUtils.isNotEmpty(officeSalaryList)){
					Collections.sort(officeSalaryList, new Comparator<OfficeSalary>() {
						@Override
						public int compare(OfficeSalary o1, OfficeSalary o2) {
							int i=StringUtils.isBlank(o1.getSerialNumbers())?0:Integer.parseInt(o1.getSerialNumbers());
							int j=StringUtils.isBlank(o2.getSerialNumbers())?0:Integer.parseInt(o2.getSerialNumbers());
							return i-j;
						}
					});
				}
			}else{
				officeSalaryList=new ArrayList<OfficeSalary>();
				officeSalaryImport=new OfficeSalaryImport();
				officeSalarySort=new OfficeSalarySort();
			}
		}else{
			if(StringUtils.isNotBlank(importId)){
				officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(importId);
				officeSalarySort=officeSalarySortService.getOfficeSalarySortByImportId(importId);
				if(officeSalarySort!=null){
					int time=officeSalarySort.getSortAmount();
					String canbreak="1";
					int k=4;
					times.add("1");
					if(org.apache.commons.lang3.StringUtils.isNotBlank(officeSalarySort.getSort1())){
						colsMap.put(canbreak, 3);
					}
					for (int i = 1; i <= time; i++) {
						if(org.apache.commons.lang3.StringUtils.isNotBlank(getProperty(officeSalarySort,"sort"+i))){
							times.add(getProperty(officeSalarySort,"sort"+i));
							canbreak=getProperty(officeSalarySort,"sort"+i);
							k=1;
						}
							colsMap.put(canbreak, k);
						k++;
					}
				}
				officeSalaryList=officeSalaryService.getOfficeSalaryByUnitIdAndCardnumber(getUnitId(), importId, getLoginUser().getUserId(),null,getPage());
				if(CollectionUtils.isNotEmpty(officeSalaryList)){
					Collections.sort(officeSalaryList, new Comparator<OfficeSalary>() {
						@Override
						public int compare(OfficeSalary o1, OfficeSalary o2) {
							int i=StringUtils.isBlank(o1.getSerialNumbers())?0:Integer.parseInt(o1.getSerialNumbers());
							int j=StringUtils.isBlank(o2.getSerialNumbers())?0:Integer.parseInt(o2.getSerialNumbers());
							return i-j;
						}
					});
				}
			}
			else{
				officeSalaryList=new ArrayList<OfficeSalary>();
				officeSalaryImport=new OfficeSalaryImport();
				officeSalarySort=new OfficeSalarySort();
			}
		}
		return SUCCESS;
	}
	
	private String getProperty(Object bean , String name) {
		try {
			return BeanUtils.getProperty(bean, name);
		}catch(Exception e) {
			return "";
		}
	}
	public String salaryType(){
		officeSalaryImports=officeSalaryImportService.getOfficeSalaryImportByUnitIdPage(getUnitId(),getPage());
		return SUCCESS;
	}
	public String salaryTypeSave(){
		try {
			String monthTimeName=officeSalaryImport.getMonthtime();
			officeSalaryImports=officeSalaryImportService.getOfficeSalaryImportByUnitIdList(getUnitId());
			officeSalaryImport=officeSalaryImportService.getOfficeSalaryImportById(officeSalaryImport.getId());
			for (OfficeSalaryImport item : officeSalaryImports) {
				if(!StringUtils.equals(item.getId(), officeSalaryImport.getId())&&
						StringUtils.equals(item.getMonthtime(), monthTimeName)){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("项次名称一样,请重新修改!");
					return SUCCESS;
				}
			}
			
			String salaryTime="";
			String regexx = "^[0-9]*$";//验证序号
			Date salaryTi=null;
			if(StringUtils.isNotBlank(monthTimeName)){
				boolean isNumberx=match(regexx, monthTimeName.substring(0, 4));
				boolean isNumberxx=match(regexx, monthTimeName.substring(5, 6));
				boolean isNumberxxx=match(regexx, monthTimeName.substring(6, 7));
				if(isNumberx&&isNumberxx){
					if(isNumberxxx){
						salaryTime=monthTimeName.substring(0, 4)+"-"+monthTimeName.substring(5, 7);
					}else{
						salaryTime=monthTimeName.substring(0, 4)+"-"+"0"+monthTimeName.substring(5, 6);
					}
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
					salaryTi=sdf.parse(salaryTime);
				}
			}
			
			officeSalaryImport.setMonthtime(monthTimeName);
			officeSalaryImport.setSalaryTime(salaryTi);
			officeSalaryImportService.update(officeSalaryImport);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("项次修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("项次修改失败!");
		}
		return SUCCESS;
	}
	public String salaryTypeDelete(){
		try {
			officeSalaryImportService.deleteAll(getUnitId(), checkid);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("项次删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("项次删除失败！");
		}
		return SUCCESS;
	}
	
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public OfficeSalary getOfficeSalary() {
		return officeSalary;
	}
	public void setOfficeSalary(OfficeSalary officeSalary) {
		this.officeSalary = officeSalary;
	}
	public List<OfficeSalary> getOfficeSalaryList() {
		return officeSalaryList;
	}
	public void setOfficeSalaryList(List<OfficeSalary> officeSalaryList) {
		this.officeSalaryList = officeSalaryList;
	}
	public OfficeSalaryImport getOfficeSalaryImport() {
		return officeSalaryImport;
	}
	public void setOfficeSalaryImport(OfficeSalaryImport officeSalaryImport) {
		this.officeSalaryImport = officeSalaryImport;
	}
	public List<OfficeSalaryImport> getOfficeSalaryImports() {
		return officeSalaryImports;
	}
	public void setOfficeSalaryImports(List<OfficeSalaryImport> officeSalaryImports) {
		this.officeSalaryImports = officeSalaryImports;
	}
	public void setOfficeSalaryService(OfficeSalaryService officeSalaryService) {
		this.officeSalaryService = officeSalaryService;
	}
	public void setOfficeSalaryImportService(
			OfficeSalaryImportService officeSalaryImportService) {
		this.officeSalaryImportService = officeSalaryImportService;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getImportId() {
		return importId;
	}
	public void setImportId(String importId) {
		this.importId = importId;
	}
	public String getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String[] getCheckid() {
		return checkid;
	}
	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}
	public boolean isSalaryManage() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), OFFICE_SALARY_MANAGE);
		boolean flag;
		if(role == null){
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}
	public void setSalaryManage(boolean salaryManage) {
		this.salaryManage = salaryManage;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public boolean isRegistOff() {
		String standardValue = systemIniService
				.getValue("SALARY_SORT");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}
	public void setRegistOff(boolean isRegistOff) {
		this.isRegistOff = isRegistOff;
	}
	public OfficeSalarySort getOfficeSalarySort() {
		return officeSalarySort;
	}
	public void setOfficeSalarySort(OfficeSalarySort officeSalarySort) {
		this.officeSalarySort = officeSalarySort;
	}
	public void setOfficeSalarySortService(
			OfficeSalarySortService officeSalarySortService) {
		this.officeSalarySortService = officeSalarySortService;
	}
	public List<String> getTimes() {
		return times;
	}
	public void setTimes(List<String> times) {
		this.times = times;
	}
	public Map<String,Integer> getColsMap() {
		return colsMap;
	}
	public void setColsMap(Map<String,Integer> colsMap) {
		this.colsMap = colsMap;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	
	
}
