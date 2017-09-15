package net.zdsoft.office.salary.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.leadin.dataimport.core.ImportData;
import net.zdsoft.leadin.dataimport.core.ImportObject;
import net.zdsoft.leadin.dataimport.exception.ErrorFieldException;
import net.zdsoft.leadin.dataimport.exception.ImportErrorException;
import net.zdsoft.leadin.dataimport.param.DataImportParam;
import net.zdsoft.leadin.dataimport.service.impl.AbstractDataImportService;
import net.zdsoft.office.salary.entity.OfficeSalary;
import net.zdsoft.office.salary.entity.OfficeSalaryImport;
import net.zdsoft.office.salary.entity.OfficeSalarySort;
import net.zdsoft.office.salary.service.OfficeSalaryService;

import org.apache.commons.lang.StringUtils;

public class OfficeSalaryImportDataServiceImpl extends AbstractDataImportService{
	
	private TeacherService teacherService;
	private UserService userService;
	private OfficeSalaryService officeSalaryService;
	private SystemIniService systemIniService;
	@Override
	public void importDatas(DataImportParam param, Reply reply)
			throws ImportErrorException {
		try {
			// 取得导入文件对象
			ImportData importData = param.getImportData();
			// 导入列set
			Set<String> colNameSet = new HashSet<String>();
			List<String> colNames = importData.getListOfImportDataName();// 导入列
			for (String col : colNames) {
				colNameSet.add(col);
			}
			String excelName = param.getSubtitle();
			Map<String, String> mapOfParam = param.getCustomParamMap();
			String unitId = mapOfParam.get("unitId"); //单位id
			
			String salaryTime="";
			
			String regexx = "^[0-9]*$";//验证序号
			Date salaryTi=null;
			if(StringUtils.isNotBlank(excelName)){
				boolean isNumberx=match(regexx, excelName.substring(0, 4));
				boolean isNumberxx=match(regexx, excelName.substring(5, 6));
				boolean isNumberxxx=match(regexx, excelName.substring(6, 7));
				if(isNumberx&&isNumberxx){
					if(isNumberxxx){
						salaryTime=excelName.substring(0, 4)+"-"+excelName.substring(5, 7);
					}else{
						salaryTime=excelName.substring(0, 4)+"-"+"0"+excelName.substring(5, 6);
					}
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
					salaryTi=sdf.parse(salaryTime);
				}
			}
			//取出导入数据
			List<Object> listOfImportData = importData.getListOfImportDataObject();
			List<OfficeSalary> insertList=new ArrayList<OfficeSalary>();
			OfficeSalaryImport officeSalaryImport=new OfficeSalaryImport();
			
			Set<String> cardSet=new HashSet<String>();
			boolean sort=false;
			sort=this.isRegistOff();
			System.out.println("分类是否开启:"+(sort?"已开启":"未开启"));
			for(int i=(sort?2:1);i<listOfImportData.size();i++){
				OfficeSalary topic = (OfficeSalary) listOfImportData.get(i);
				if(topic!=null&&StringUtils.isNotBlank(topic.getCardnumber())){
					cardSet.add(topic.getCardnumber());	
				}
			}
			
			OfficeSalarySort officeSalarySort=null;
			if(sort){
				officeSalarySort=new OfficeSalarySort();
			}
			Map<String,Teacher> teacherMap=teacherService.getTeacherByIdentityCards(cardSet.toArray(new String[0]));
			boolean canInsert=true;
			for (int i =0; i < listOfImportData.size(); i++) {
				OfficeSalary topic = (OfficeSalary) listOfImportData.get(i);
				try {
					OfficeSalary officeSalary=new OfficeSalary();
					
					if(i==(sort?1:0)&&topic!=null){
						if(!StringUtils.equals(topic.getRealname().trim(), "姓名")){
							throw new ErrorFieldException("规则不符合,第一行请填写姓名汉字列项", "realname");
						}else if(!StringUtils.equals(topic.getSerialNumbers().trim(), "序号")){
							throw new ErrorFieldException("规则不符合,第一行请填写序号汉字列项", "serialNumbers");
						}else if(!StringUtils.equals(topic.getCardnumber().trim(), "身份证")){
							throw new ErrorFieldException("规则不符合,第一行请填写身份证汉字列项", "cardnumber");
						}else{
							officeSalaryImport.setId(UUIDUtils.newId());officeSalaryImport.setUnitId(unitId);officeSalaryImport.setCreateTime(new Date());officeSalaryImport.setSalaryTime(salaryTi);
							officeSalaryImport.setSerialNumbers(StringUtils.trim(topic.getSerialNumbers()));officeSalaryImport.setRealname(StringUtils.trim(topic.getRealname()));
							officeSalaryImport.setCardnumber(StringUtils.trim(topic.getCardnumber()));officeSalaryImport.setMonthtime(excelName);
							officeSalaryImport.setSalary1(StringUtils.trim(topic.getSalary1()));officeSalaryImport.setSalary2(StringUtils.trim(topic.getSalary2()));
							officeSalaryImport.setSalary3(StringUtils.trim(topic.getSalary3()));officeSalaryImport.setSalary4(StringUtils.trim(topic.getSalary4()));
							officeSalaryImport.setSalary5(StringUtils.trim(topic.getSalary5()));officeSalaryImport.setSalary6(StringUtils.trim(topic.getSalary6()));
							officeSalaryImport.setSalary7(StringUtils.trim(topic.getSalary7()));officeSalaryImport.setSalary8(StringUtils.trim(topic.getSalary8()));
							officeSalaryImport.setSalary9(StringUtils.trim(topic.getSalary9()));officeSalaryImport.setSalary10(StringUtils.trim(topic.getSalary10()));
							officeSalaryImport.setSalary11(StringUtils.trim(topic.getSalary11()));officeSalaryImport.setSalary12(StringUtils.trim(topic.getSalary12()));
							officeSalaryImport.setSalary13(StringUtils.trim(topic.getSalary13()));officeSalaryImport.setSalary14(StringUtils.trim(topic.getSalary14()));
							officeSalaryImport.setSalary15(StringUtils.trim(topic.getSalary15()));officeSalaryImport.setSalary16(StringUtils.trim(topic.getSalary16()));
							officeSalaryImport.setSalary17(StringUtils.trim(topic.getSalary17()));officeSalaryImport.setSalary18(StringUtils.trim(topic.getSalary18()));
							officeSalaryImport.setSalary19(StringUtils.trim(topic.getSalary19()));officeSalaryImport.setSalary20(StringUtils.trim(topic.getSalary20()));
							officeSalaryImport.setSalary21(StringUtils.trim(topic.getSalary21()));officeSalaryImport.setSalary22(StringUtils.trim(topic.getSalary22()));
							officeSalaryImport.setSalary23(StringUtils.trim(topic.getSalary23()));officeSalaryImport.setSalary24(StringUtils.trim(topic.getSalary24()));
							officeSalaryImport.setSalary25(StringUtils.trim(topic.getSalary25()));officeSalaryImport.setSalary26(StringUtils.trim(topic.getSalary26()));
							officeSalaryImport.setSalary27(StringUtils.trim(topic.getSalary27()));officeSalaryImport.setSalary28(StringUtils.trim(topic.getSalary28()));
							officeSalaryImport.setSalary29(StringUtils.trim(topic.getSalary29()));officeSalaryImport.setSalary30(StringUtils.trim(topic.getSalary30()));
							officeSalaryImport.setSalary31(StringUtils.trim(topic.getSalary31()));officeSalaryImport.setSalary32(StringUtils.trim(topic.getSalary32()));
							officeSalaryImport.setSalary33(StringUtils.trim(topic.getSalary33()));officeSalaryImport.setSalary34(StringUtils.trim(topic.getSalary34()));
							officeSalaryImport.setSalary35(StringUtils.trim(topic.getSalary35()));officeSalaryImport.setSalary36(StringUtils.trim(topic.getSalary36()));
							officeSalaryImport.setSalary37(StringUtils.trim(topic.getSalary37()));officeSalaryImport.setSalary38(StringUtils.trim(topic.getSalary38()));
							officeSalaryImport.setSalary39(StringUtils.trim(topic.getSalary39()));officeSalaryImport.setSalary40(StringUtils.trim(topic.getSalary40()));
							officeSalaryImport.setSalary41(StringUtils.trim(topic.getSalary41()));officeSalaryImport.setSalary42(StringUtils.trim(topic.getSalary42()));
							officeSalaryImport.setSalary43(StringUtils.trim(topic.getSalary43()));officeSalaryImport.setSalary44(StringUtils.trim(topic.getSalary44()));
							officeSalaryImport.setSalary45(StringUtils.trim(topic.getSalary45()));officeSalaryImport.setSalary46(StringUtils.trim(topic.getSalary46()));
							officeSalaryImport.setSalary47(StringUtils.trim(topic.getSalary47()));officeSalaryImport.setSalary48(StringUtils.trim(topic.getSalary48()));
							officeSalaryImport.setSalary49(StringUtils.trim(topic.getSalary49()));officeSalaryImport.setSalary50(StringUtils.trim(topic.getSalary50()));
							officeSalaryImport.setSalary51(StringUtils.trim(topic.getSalary51()));officeSalaryImport.setSalary52(StringUtils.trim(topic.getSalary52()));
							officeSalaryImport.setSalary53(StringUtils.trim(topic.getSalary53()));officeSalaryImport.setSalary54(StringUtils.trim(topic.getSalary54()));
							officeSalaryImport.setSalary55(StringUtils.trim(topic.getSalary55()));officeSalaryImport.setSalary56(StringUtils.trim(topic.getSalary56()));officeSalaryImport.setSalary57(StringUtils.trim(topic.getSalary57()));
							officeSalaryImport.setSalary58(StringUtils.trim(topic.getSalary58()));officeSalaryImport.setSalary59(StringUtils.trim(topic.getSalary59()));officeSalaryImport.setSalary60(StringUtils.trim(topic.getSalary60()));
							officeSalaryImport.setSalary61(StringUtils.trim(topic.getSalary61()));officeSalaryImport.setSalary62(StringUtils.trim(topic.getSalary62()));officeSalaryImport.setSalary63(StringUtils.trim(topic.getSalary63()));
							officeSalaryImport.setSalary64(StringUtils.trim(topic.getSalary64()));officeSalaryImport.setSalary65(StringUtils.trim(topic.getSalary65()));officeSalaryImport.setSalary66(StringUtils.trim(topic.getSalary66()));
							officeSalaryImport.setSalary67(StringUtils.trim(topic.getSalary67()));officeSalaryImport.setSalary68(StringUtils.trim(topic.getSalary68()));officeSalaryImport.setSalary69(StringUtils.trim(topic.getSalary69()));
							officeSalaryImport.setSalary70(StringUtils.trim(topic.getSalary70()));officeSalaryImport.setSalary71(StringUtils.trim(topic.getSalary71()));officeSalaryImport.setSalary72(StringUtils.trim(topic.getSalary72()));
							officeSalaryImport.setSalary73(StringUtils.trim(topic.getSalary73()));officeSalaryImport.setSalary74(StringUtils.trim(topic.getSalary74()));officeSalaryImport.setSalary75(StringUtils.trim(topic.getSalary75()));
							officeSalaryImport.setSalary76(StringUtils.trim(topic.getSalary76()));officeSalaryImport.setSalary77(StringUtils.trim(topic.getSalary77()));officeSalaryImport.setSalary78(StringUtils.trim(topic.getSalary78()));
							officeSalaryImport.setSalary79(StringUtils.trim(topic.getSalary79()));officeSalaryImport.setSalary80(StringUtils.trim(topic.getSalary80()));officeSalaryImport.setSalary81(StringUtils.trim(topic.getSalary81()));
							officeSalaryImport.setSalary82(StringUtils.trim(topic.getSalary82()));officeSalaryImport.setSalary83(StringUtils.trim(topic.getSalary83()));officeSalaryImport.setSalary84(StringUtils.trim(topic.getSalary84()));
							officeSalaryImport.setSalary85(StringUtils.trim(topic.getSalary85()));officeSalaryImport.setSalary86(StringUtils.trim(topic.getSalary86()));officeSalaryImport.setSalary87(StringUtils.trim(topic.getSalary87()));
							officeSalaryImport.setSalary88(StringUtils.trim(topic.getSalary88()));officeSalaryImport.setSalary89(StringUtils.trim(topic.getSalary89()));officeSalaryImport.setSalary90(StringUtils.trim(topic.getSalary90()));
							officeSalaryImport.setSalary91(StringUtils.trim(topic.getSalary91()));officeSalaryImport.setSalary92(StringUtils.trim(topic.getSalary92()));officeSalaryImport.setSalary93(StringUtils.trim(topic.getSalary93()));
							officeSalaryImport.setSalary94(StringUtils.trim(topic.getSalary94()));officeSalaryImport.setSalary95(StringUtils.trim(topic.getSalary95()));officeSalaryImport.setSalary96(StringUtils.trim(topic.getSalary96()));
							officeSalaryImport.setSalary97(StringUtils.trim(topic.getSalary97()));
						}
						//if(StringUtils.equals("office_salary_information", excelName)){
						//	//验证excel是否改名
						//	throw new ErrorFieldException("请维护excel项次","serialNumbers");
						//}
					}
					
					if(sort&&i==0){
						officeSalarySort.setId(UUIDUtils.newId());officeSalarySort.setCreateTime(new Date());officeSalarySort.setUnitId(unitId);officeSalarySort.setImportId(officeSalaryImport.getId());
						officeSalarySort.setSort1(StringUtils.trim(topic.getSalary1()));officeSalarySort.setSort2(StringUtils.trim(topic.getSalary2()));officeSalarySort.setSort3(StringUtils.trim(topic.getSalary3()));
						officeSalarySort.setSort4(StringUtils.trim(topic.getSalary4()));officeSalarySort.setSortAmount(colNameSet.size()-3);
						officeSalarySort.setSort5(StringUtils.trim(topic.getSalary5()));officeSalarySort.setSort6(StringUtils.trim(topic.getSalary6()));officeSalarySort.setSort7(StringUtils.trim(topic.getSalary7()));
						officeSalarySort.setSort8(StringUtils.trim(topic.getSalary8()));officeSalarySort.setSort9(StringUtils.trim(topic.getSalary9()));officeSalarySort.setSort10(StringUtils.trim(topic.getSalary10()));
						officeSalarySort.setSort11(StringUtils.trim(topic.getSalary11()));officeSalarySort.setSort12(StringUtils.trim(topic.getSalary12()));officeSalarySort.setSort13(StringUtils.trim(topic.getSalary13()));
						officeSalarySort.setSort14(StringUtils.trim(topic.getSalary14()));officeSalarySort.setSort15(StringUtils.trim(topic.getSalary15()));officeSalarySort.setSort16(StringUtils.trim(topic.getSalary16()));
						officeSalarySort.setSort17(StringUtils.trim(topic.getSalary17()));officeSalarySort.setSort18(StringUtils.trim(topic.getSalary18()));officeSalarySort.setSort19(StringUtils.trim(topic.getSalary19()));
						officeSalarySort.setSort20(StringUtils.trim(topic.getSalary20()));officeSalarySort.setSort21(StringUtils.trim(topic.getSalary21()));officeSalarySort.setSort22(StringUtils.trim(topic.getSalary22()));
						officeSalarySort.setSort23(StringUtils.trim(topic.getSalary23()));officeSalarySort.setSort24(StringUtils.trim(topic.getSalary24()));officeSalarySort.setSort25(StringUtils.trim(topic.getSalary25()));
						officeSalarySort.setSort26(StringUtils.trim(topic.getSalary26()));officeSalarySort.setSort27(StringUtils.trim(topic.getSalary27()));officeSalarySort.setSort28(StringUtils.trim(topic.getSalary28()));
						officeSalarySort.setSort29(StringUtils.trim(topic.getSalary29()));officeSalarySort.setSort30(StringUtils.trim(topic.getSalary30()));officeSalarySort.setSort31(StringUtils.trim(topic.getSalary31()));
						officeSalarySort.setSort32(StringUtils.trim(topic.getSalary32()));officeSalarySort.setSort33(StringUtils.trim(topic.getSalary33()));officeSalarySort.setSort34(StringUtils.trim(topic.getSalary34()));
						officeSalarySort.setSort35(StringUtils.trim(topic.getSalary35()));officeSalarySort.setSort36(StringUtils.trim(topic.getSalary36()));officeSalarySort.setSort37(StringUtils.trim(topic.getSalary37()));
						officeSalarySort.setSort38(StringUtils.trim(topic.getSalary38()));officeSalarySort.setSort39(StringUtils.trim(topic.getSalary39()));officeSalarySort.setSort40(StringUtils.trim(topic.getSalary40()));
						officeSalarySort.setSort41(StringUtils.trim(topic.getSalary41()));officeSalarySort.setSort42(StringUtils.trim(topic.getSalary42()));officeSalarySort.setSort43(StringUtils.trim(topic.getSalary43()));
						officeSalarySort.setSort44(StringUtils.trim(topic.getSalary44()));officeSalarySort.setSort45(StringUtils.trim(topic.getSalary45()));officeSalarySort.setSort46(StringUtils.trim(topic.getSalary46()));
						officeSalarySort.setSort47(StringUtils.trim(topic.getSalary47()));officeSalarySort.setSort48(StringUtils.trim(topic.getSalary48()));officeSalarySort.setSort49(StringUtils.trim(topic.getSalary49()));
						officeSalarySort.setSort50(StringUtils.trim(topic.getSalary50()));officeSalarySort.setSort51(StringUtils.trim(topic.getSalary51()));officeSalarySort.setSort52(StringUtils.trim(topic.getSalary52()));
						officeSalarySort.setSort53(StringUtils.trim(topic.getSalary53()));officeSalarySort.setSort54(StringUtils.trim(topic.getSalary54()));officeSalarySort.setSort55(StringUtils.trim(topic.getSalary55()));
						officeSalarySort.setSort56(StringUtils.trim(topic.getSalary56()));officeSalarySort.setSort57(StringUtils.trim(topic.getSalary57()));officeSalarySort.setSort58(StringUtils.trim(topic.getSalary58()));
						officeSalarySort.setSort59(StringUtils.trim(topic.getSalary59()));officeSalarySort.setSort60(StringUtils.trim(topic.getSalary60()));officeSalarySort.setSort61(StringUtils.trim(topic.getSalary61()));
						officeSalarySort.setSort62(StringUtils.trim(topic.getSalary62()));officeSalarySort.setSort63(StringUtils.trim(topic.getSalary63()));officeSalarySort.setSort64(StringUtils.trim(topic.getSalary64()));
						officeSalarySort.setSort65(StringUtils.trim(topic.getSalary65()));officeSalarySort.setSort66(StringUtils.trim(topic.getSalary66()));officeSalarySort.setSort67(StringUtils.trim(topic.getSalary67()));
						officeSalarySort.setSort68(StringUtils.trim(topic.getSalary68()));officeSalarySort.setSort69(StringUtils.trim(topic.getSalary69()));officeSalarySort.setSort70(StringUtils.trim(topic.getSalary70()));
						officeSalarySort.setSort71(StringUtils.trim(topic.getSalary71()));officeSalarySort.setSort72(StringUtils.trim(topic.getSalary72()));officeSalarySort.setSort73(StringUtils.trim(topic.getSalary73()));
						officeSalarySort.setSort74(StringUtils.trim(topic.getSalary74()));officeSalarySort.setSort75(StringUtils.trim(topic.getSalary75()));officeSalarySort.setSort76(StringUtils.trim(topic.getSalary76()));
						officeSalarySort.setSort77(StringUtils.trim(topic.getSalary77()));officeSalarySort.setSort78(StringUtils.trim(topic.getSalary78()));officeSalarySort.setSort79(StringUtils.trim(topic.getSalary79()));
						officeSalarySort.setSort80(StringUtils.trim(topic.getSalary80()));officeSalarySort.setSort81(StringUtils.trim(topic.getSalary81()));officeSalarySort.setSort82(StringUtils.trim(topic.getSalary82()));
						officeSalarySort.setSort83(StringUtils.trim(topic.getSalary83()));officeSalarySort.setSort84(StringUtils.trim(topic.getSalary84()));officeSalarySort.setSort85(StringUtils.trim(topic.getSalary85()));
						officeSalarySort.setSort86(StringUtils.trim(topic.getSalary86()));officeSalarySort.setSort87(StringUtils.trim(topic.getSalary87()));officeSalarySort.setSort88(StringUtils.trim(topic.getSalary88()));
						officeSalarySort.setSort89(StringUtils.trim(topic.getSalary89()));officeSalarySort.setSort90(StringUtils.trim(topic.getSalary90()));officeSalarySort.setSort91(StringUtils.trim(topic.getSalary91()));
						officeSalarySort.setSort92(StringUtils.trim(topic.getSalary92()));officeSalarySort.setSort93(StringUtils.trim(topic.getSalary93()));officeSalarySort.setSort94(StringUtils.trim(topic.getSalary94()));
						officeSalarySort.setSort95(StringUtils.trim(topic.getSalary95()));officeSalarySort.setSort96(StringUtils.trim(topic.getSalary96()));officeSalarySort.setSort97(StringUtils.trim(topic.getSalary97()));
					}
					
				if(i>(sort?1:0)){
					String regex = "^[0-9]*$";//验证序号
					boolean isNumber=match(regex, topic.getSerialNumbers().trim());
					if(StringUtils.isNotBlank(topic.getSerialNumbers().trim())){
						if(isNumber){
							String serNumber=topic.getSerialNumbers().trim();
							officeSalary.setSerialNumbers(serNumber);
						}else{
							canInsert=false;
							throw new ErrorFieldException("序号只能为整数", "serialNumbers");
						}
					}
					String regex2 = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";//验证身份证是否是15位或18位
					boolean isCardNumber=match(regex2,topic.getCardnumber().trim());
					if(StringUtils.isNotBlank(topic.getCardnumber().trim())&&isCardNumber){
						String cardNumber=topic.getCardnumber().trim();
						officeSalary.setCardnumber(cardNumber);
					}else{
						canInsert=false;
						throw new ErrorFieldException("该身份证位数有误", "cardnumber");
					}
					
					if(StringUtils.isNotBlank(topic.getCardnumber().trim())){
						//身份证不能为空，先验证教师表，再验证用户表
						if(teacherMap==null&&teacherMap.size()<=0){
							canInsert=false;
							throw new ErrorFieldException("身份证对应的用户不存在,请重新维护身份证！", "cardnumber");
						}else if(teacherMap.get(topic.getCardnumber().trim())!=null){
							Teacher teacher=teacherMap.get(topic.getCardnumber().trim());
							Map<String, User> userMap = userService.getUserMapByOwner(2, new String[]{teacher.getId()});
							if(StringUtils.isNotBlank(teacher.getName())&&!StringUtils.equals(teacher.getName(), topic.getRealname().trim())){
								canInsert=false;
								throw new ErrorFieldException("身份证对应的该用户不一致,请维护该用户姓名。", "realname");
							}else if(userMap!=null && userMap.get(teacher.getId())!=null){
								officeSalary.setUserId((userMap.get(teacher.getId()).getId()));
								officeSalary.setRealname((userMap.get(teacher.getId()).getRealname()));
								officeSalary.setCardnumber(teacher.getIdcard());
							}else{
								canInsert=false;
								throw new ErrorFieldException("身份证对应的该用户不存在,请重新维护身份证。", "cardnumber");
							}
						}else{
							canInsert=false;
							throw new ErrorFieldException("身份证对应的该用户不存在,请重新维护身份证。", "cardnumber");
						}
					}
					if(StringUtils.isNotBlank(topic.getRealname().trim())){
						List<User> userList = userService.getUsersByUnitIdAndName(topic.getRealname().trim(),unitId);
						if(userList==null || userList.size()<=0){
							canInsert=false;
							throw new ErrorFieldException("该用户不存在。", "realname");
						}else if(userList.size()==1){
							if(userList.get(0)!=null && userList.get(0).getRealname()!=null && userList.get(0).getRealname().equals(topic.getRealname().trim())){
								officeSalary.setUserId((userList.get(0).getId()));
								officeSalary.setRealname((userList.get(0).getRealname()));
								officeSalary.setCardnumber(topic.getCardnumber().trim());
							}else{
								canInsert=false;
							    throw new ErrorFieldException("该用户不存在。", "realname");
							}
						}else{
							List<User> nameUserList=new ArrayList<User>();
							Set<String> idSet=new HashSet<String>();
							for (User user : userList) {
								idSet.add(user.getTeacherid());
							}
							Map<String,Teacher> teachMap=teacherService.getTeacherMap(idSet.toArray(new String[0]));
							for(User item:userList){
								if(item == null 
										|| StringUtils.isBlank(item.getRealname())
										|| (teacherMap==null && teacherMap.isEmpty())
										||!teachMap.containsKey(item.getTeacherid())){
									continue;
								}
								
								boolean isNameAndIdCard = StringUtils.equals(item.getRealname(), StringUtils.trim(topic.getRealname()))
										&& StringUtils.equals(teachMap.get(item.getTeacherid()).getIdcard(), topic.getCardnumber());
								
								if(isNameAndIdCard) {
									nameUserList.add(item);
								}
								
							}
							if(nameUserList==null || nameUserList.size()<=0){
								canInsert=false;
								throw new ErrorFieldException("该用户不存在。", "realname");
							}else if(nameUserList.size()==1){
								officeSalary.setUserId((nameUserList.get(0).getId()));
								officeSalary.setRealname((nameUserList.get(0).getRealname()));
								officeSalary.setCardnumber(topic.getCardnumber().trim());
							}else{
								canInsert=false;
								throw new ErrorFieldException("单位中相同姓名相同身份证用户有多个，请维护身份证。", "realname");
							}
						}
					}
					
					//增加数据
					officeSalary.setUnitId(unitId);officeSalary.setId(UUIDUtils.newId());officeSalary.setImportId(officeSalaryImport.getId());
					officeSalary.setSalary1(StringUtils.trim(topic.getSalary1()));officeSalary.setSalary2(StringUtils.trim(topic.getSalary2()));
					officeSalary.setSalary3(StringUtils.trim(topic.getSalary3()));officeSalary.setSalary4(StringUtils.trim(topic.getSalary4()));
					officeSalary.setSalary5(StringUtils.trim(topic.getSalary5()));officeSalary.setSalary6(StringUtils.trim(topic.getSalary6()));
					officeSalary.setSalary7(StringUtils.trim(topic.getSalary7()));officeSalary.setSalary8(StringUtils.trim(topic.getSalary8()));
					officeSalary.setSalary9(StringUtils.trim(topic.getSalary9()));officeSalary.setSalary10(StringUtils.trim(topic.getSalary10()));
					officeSalary.setSalary11(StringUtils.trim(topic.getSalary11()));officeSalary.setSalary12(StringUtils.trim(topic.getSalary12()));
					officeSalary.setSalary13(StringUtils.trim(topic.getSalary13()));officeSalary.setSalary14(StringUtils.trim(topic.getSalary14()));
					officeSalary.setSalary15(StringUtils.trim(topic.getSalary15()));officeSalary.setSalary16(StringUtils.trim(topic.getSalary16()));
					officeSalary.setSalary17(StringUtils.trim(topic.getSalary17()));officeSalary.setSalary18(StringUtils.trim(topic.getSalary18()));
					officeSalary.setSalary19(StringUtils.trim(topic.getSalary19()));officeSalary.setSalary20(StringUtils.trim(topic.getSalary20()));
					officeSalary.setSalary21(StringUtils.trim(topic.getSalary21()));officeSalary.setSalary22(StringUtils.trim(topic.getSalary22()));
					officeSalary.setSalary23(StringUtils.trim(topic.getSalary23()));officeSalary.setSalary24(StringUtils.trim(topic.getSalary24()));
					officeSalary.setSalary25(StringUtils.trim(topic.getSalary25()));officeSalary.setSalary26(StringUtils.trim(topic.getSalary26()));
					officeSalary.setSalary27(StringUtils.trim(topic.getSalary27()));officeSalary.setSalary28(StringUtils.trim(topic.getSalary28()));
					officeSalary.setSalary29(StringUtils.trim(topic.getSalary29()));officeSalary.setSalary30(StringUtils.trim(topic.getSalary30()));
					officeSalary.setSalary31(StringUtils.trim(topic.getSalary31()));officeSalary.setSalary32(StringUtils.trim(topic.getSalary32()));
					officeSalary.setSalary33(StringUtils.trim(topic.getSalary33()));officeSalary.setSalary34(StringUtils.trim(topic.getSalary34()));
					officeSalary.setSalary35(StringUtils.trim(topic.getSalary35()));officeSalary.setSalary36(StringUtils.trim(topic.getSalary36()));
					officeSalary.setSalary37(StringUtils.trim(topic.getSalary37()));officeSalary.setSalary38(StringUtils.trim(topic.getSalary38()));
					officeSalary.setSalary39(StringUtils.trim(topic.getSalary39()));officeSalary.setSalary40(StringUtils.trim(topic.getSalary40()));
					officeSalary.setSalary41(StringUtils.trim(topic.getSalary41()));officeSalary.setSalary42(StringUtils.trim(topic.getSalary42()));
					officeSalary.setSalary43(StringUtils.trim(topic.getSalary43()));officeSalary.setSalary44(StringUtils.trim(topic.getSalary44()));
					officeSalary.setSalary45(StringUtils.trim(topic.getSalary45()));officeSalary.setSalary46(StringUtils.trim(topic.getSalary46()));
					officeSalary.setSalary47(StringUtils.trim(topic.getSalary47()));officeSalary.setSalary48(StringUtils.trim(topic.getSalary48()));officeSalary.setSalary49(StringUtils.trim(topic.getSalary49()));
					officeSalary.setSalary50(StringUtils.trim(topic.getSalary50()));officeSalary.setSalary51(StringUtils.trim(topic.getSalary51()));officeSalary.setSalary52(StringUtils.trim(topic.getSalary52()));
					officeSalary.setSalary53(StringUtils.trim(topic.getSalary53()));officeSalary.setSalary54(StringUtils.trim(topic.getSalary54()));officeSalary.setSalary55(StringUtils.trim(topic.getSalary55()));
					officeSalary.setSalary56(StringUtils.trim(topic.getSalary56()));officeSalary.setSalary57(StringUtils.trim(topic.getSalary57()));officeSalary.setSalary58(StringUtils.trim(topic.getSalary58()));
					officeSalary.setSalary59(StringUtils.trim(topic.getSalary59()));officeSalary.setSalary60(StringUtils.trim(topic.getSalary60()));officeSalary.setSalary61(StringUtils.trim(topic.getSalary61()));
					officeSalary.setSalary62(StringUtils.trim(topic.getSalary62()));officeSalary.setSalary63(StringUtils.trim(topic.getSalary63()));officeSalary.setSalary64(StringUtils.trim(topic.getSalary64()));
					officeSalary.setSalary65(StringUtils.trim(topic.getSalary65()));officeSalary.setSalary66(StringUtils.trim(topic.getSalary66()));officeSalary.setSalary67(StringUtils.trim(topic.getSalary67()));
					officeSalary.setSalary68(StringUtils.trim(topic.getSalary68()));officeSalary.setSalary69(StringUtils.trim(topic.getSalary69()));officeSalary.setSalary70(StringUtils.trim(topic.getSalary70()));
					officeSalary.setSalary71(StringUtils.trim(topic.getSalary71()));officeSalary.setSalary72(StringUtils.trim(topic.getSalary72()));officeSalary.setSalary73(StringUtils.trim(topic.getSalary73()));
					officeSalary.setSalary74(StringUtils.trim(topic.getSalary74()));officeSalary.setSalary75(StringUtils.trim(topic.getSalary75()));officeSalary.setSalary76(StringUtils.trim(topic.getSalary76()));
					officeSalary.setSalary77(StringUtils.trim(topic.getSalary77()));officeSalary.setSalary78(StringUtils.trim(topic.getSalary78()));officeSalary.setSalary79(StringUtils.trim(topic.getSalary79()));
					officeSalary.setSalary80(StringUtils.trim(topic.getSalary80()));officeSalary.setSalary81(StringUtils.trim(topic.getSalary81()));officeSalary.setSalary82(StringUtils.trim(topic.getSalary82()));
					officeSalary.setSalary83(StringUtils.trim(topic.getSalary83()));officeSalary.setSalary84(StringUtils.trim(topic.getSalary84()));officeSalary.setSalary85(StringUtils.trim(topic.getSalary85()));
					officeSalary.setSalary86(StringUtils.trim(topic.getSalary86()));officeSalary.setSalary87(StringUtils.trim(topic.getSalary87()));officeSalary.setSalary88(StringUtils.trim(topic.getSalary88()));
					officeSalary.setSalary89(StringUtils.trim(topic.getSalary89()));officeSalary.setSalary90(StringUtils.trim(topic.getSalary90()));officeSalary.setSalary91(StringUtils.trim(topic.getSalary91()));
					officeSalary.setSalary92(StringUtils.trim(topic.getSalary92()));officeSalary.setSalary93(StringUtils.trim(topic.getSalary93()));officeSalary.setSalary94(StringUtils.trim(topic.getSalary94()));
					officeSalary.setSalary95(StringUtils.trim(topic.getSalary95()));officeSalary.setSalary96(StringUtils.trim(topic.getSalary96()));officeSalary.setSalary97(StringUtils.trim(topic.getSalary97()));
					insertList.add(officeSalary);
					}
				} catch (ErrorFieldException e) {
					this.disposeError(importData, i, e.getField(), e.getMessage());
					continue;
				}
		}
			
			//批量插入
			if(insertList.size()>0&&officeSalaryImport.getUnitId()!=null&&canInsert){
				officeSalaryService.batchInsertOrUpdate(insertList, officeSalaryImport,officeSalarySort);
			}
			if(canInsert){
				reply.addActionMessage("新增数据：" + insertList.size() + " 条。");
			}else{
				reply.addActionMessage("新增数据：" + 0 + " 条。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportErrorException("导入出错:");
		}
		
	}
	
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
		}
	@Override
	public List<List<String[]>> exportDatas(ImportObject importObject,
			String[] cols) {
		List<List<String[]>> dataList = new ArrayList<List<String[]>>();
		return dataList;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeSalaryService(OfficeSalaryService officeSalaryService) {
		this.officeSalaryService = officeSalaryService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}
	public boolean isRegistOff() {
		String standardValue = systemIniService
				.getValue("SALARY_SORT");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}
}
