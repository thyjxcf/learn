package net.zdsoft.eis.base.data.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.dao.BaseStudentFamilyDao;
import net.zdsoft.eis.base.data.entity.FamilyImport;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.BusinessUtils;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.ObjectUtils;
import net.zdsoft.leadin.common.dao.SystemCommonDao;
import net.zdsoft.leadin.dataimport.core.ImportData;
import net.zdsoft.leadin.dataimport.core.ImportObject;
import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.exception.ErrorFieldException;
import net.zdsoft.leadin.dataimport.exception.ImportErrorException;
import net.zdsoft.leadin.dataimport.param.DataImportParam;
import net.zdsoft.leadin.dataimport.service.impl.AbstractDataImportService;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author hexq
 * @version $Revision: 1.0 $, $Date: Oct 28, 2009 14:43:37 PM $
 */
public class FamilyImportServiceImpl extends AbstractDataImportService {

	 private static final Logger log = LoggerFactory.getLogger(FamilyImportServiceImpl.class);
	 
	//家庭成员信息
	static Set<String> familyFieldSet = new HashSet<String>();
	
	
	//有关联关系的字段
    static Set<String> relatedFieldSet = new HashSet<String>();
    
    static {
    	
    	//参见family_import.xml的配置
    	//共25个字段
    	familyFieldSet.add("realname");
    	familyFieldSet.add("relation");
    	familyFieldSet.add("sex");
    	familyFieldSet.add("birthday");
    	familyFieldSet.add("identitycard");
    	familyFieldSet.add("linkphone");
    	familyFieldSet.add("linkaddress");
    	familyFieldSet.add("mobilephone");
    	familyFieldSet.add("officetel");
    	familyFieldSet.add("isguardian");
    	familyFieldSet.add("chargenumber");
    	familyFieldSet.add("nation");
    	familyFieldSet.add("workcode");
    	familyFieldSet.add("duty");
    	familyFieldSet.add("professioncode");
    	familyFieldSet.add("dutylevel");
    	familyFieldSet.add("maritalstatus");
    	familyFieldSet.add("company");
    	familyFieldSet.add("politicalstatus");
    	familyFieldSet.add("culture");
    	familyFieldSet.add("emigrationplace");
    	familyFieldSet.add("homepage");
    	familyFieldSet.add("postalcode");
    	familyFieldSet.add("email");
    	familyFieldSet.add("remark");
    	familyFieldSet.add("chargenumber");
    	familyFieldSet.add("identitycardType");
    	familyFieldSet.add("health");
    	        
        //------------------------------关联字段----------------------
        
        // 出生日期和身份证号中的出生日期要匹配
        relatedFieldSet.add("identitycard");
        relatedFieldSet.add("birthday");
        relatedFieldSet.add("sex");
        
    }
	
    SchoolService schoolService;				//学校信息
    StudentFamilyService studentFamilyService; //学生家庭成员信息
    BaseStudentFamilyDao baseStudentFamilyDao; //学生家庭成员信息
    SystemCommonDao systemCommonDao;		   //公用的sql处理信息
    StudentService studentService;	   //导入信息处理
    
	public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public void setStudentFamilyService(StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
		this.systemCommonDao = systemCommonDao;
	}
	
	public void setBaseStudentFamilyDao(BaseStudentFamilyDao baseStudentFamilyDao) {
		this.baseStudentFamilyDao = baseStudentFamilyDao;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
     * 导出
     */
    public List<List<String[]>> exportDatas(ImportObject importObject,
            String[] cols) {
        List<List<String[]>> dataList = new ArrayList<List<String[]>>();    
        return dataList; 
    }

	public void importDatas(DataImportParam param, Reply reply) throws ImportErrorException {
		log.debug("进入到FamilyImportServiceImpl实现类的importDatas()方法中，进行业务处理。");
    	
        int errPos = 0;
        try {
            log.debug("==============准备参数信息================");
            
            // 取得导入文件对象
            ImportData importData = param.getImportData();
    
            //参数信息
            String unitId = param.getUnitId();//单位
            String isConvered = param.getCovered();   //是否覆盖             
                                    
            // 取出导入数据list
            List<Object> listOfImportData = importData.getListOfImportDataObject();
            
            //导入列Set
            List<String> colNames = importData.getListOfImportDataName();//导入列
            Set<String> colNameSet = new HashSet<String>();
            for (String col : colNames) {
                colNameSet.add(col);
            }
            
            log.debug("==============准备相关数据================");
            
            //惟一性数据
//            Map<String, Student> unitiveCodeMap = new HashMap<String, Student>();	 	// 学号(保证一个学校内唯一)
//            Set<String> unitiveCodeList = new HashSet<String>();
            Map<String, Student> stuCodeMap = new HashMap<String, Student>();		// 学号(保证一个学校内唯一)
            Set<String> stuCodeList = new HashSet<String>();
            for (int i = 0; i < listOfImportData.size(); i++) {
            	FamilyImport family = (FamilyImport) listOfImportData.get(i);
            	stuCodeList.add(family.getStucode());
//            	unitiveCodeList.add(family.getUnitivecode());
            }
            List<Student> stuList = studentService.getStudents(unitId, stuCodeList.toArray(new String[0]));
            if(CollectionUtils.isNotEmpty(stuList)){
            	for(Student stu : stuList){
            		if(!stuCodeMap.containsKey(stu.getStucode())){
            			stuCodeMap.put(stu.getStucode(), stu);
            		}
            	}
            }
//            unitiveCodeMap.putAll(studentService.getStudentMapByUnitiveCodes(unitiveCodeList.toArray(new String[0])));
            
            log.debug("==============验证数据有效性并导入数据================"); 
            List<FamilyImport> updateList = new ArrayList<FamilyImport>();	//家庭成员
            List<FamilyImport> insertList = new ArrayList<FamilyImport>();
            
            //地区码信息
            String regionCode = schoolService.getSchool(unitId).getRegion();
            
			//单个家庭成员处理
            for (int i = 0; i < listOfImportData.size(); i++) {
                errPos = i;
                FamilyImport family = (FamilyImport) listOfImportData.get(i);

                //地区码
                family.setRegioncode(regionCode);
                family.setSchid(unitId);
               
                Student stu =  null;
                try { 
                	
                	
                	//---------验证个人主页验证--------------------
                	if (StringUtils.isNotBlank(family.getHomepage())) {
                		 Pattern p = Pattern.compile("^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$");
                         Matcher m = p.matcher(family.getHomepage());
                         if(!m.matches()){
                        	 throw new ErrorFieldException("格式无效。","homepage");
                         }
					}
                	
                	//----------判断与学号对应的学生情况------------
//                	stu  = unitiveCodeMap.get(family.getUnitivecode());
                	stu = stuCodeMap.get(family.getStucode());
                	if (null == stu) {
                		throw new ErrorFieldException("在本校中不存在。","stucode");
					} else if (!stu.getStuname().trim().equals(family.getStuname().trim())){
						throw new ErrorFieldException("与该行学号对应的学生其系统中的姓名和导入文件的姓名不一致，系统中：" 
								+ stu.getStuname()+ "。" ,"stuname");
					} else {
						family.setStuid(stu.getId());
					}
                	//姓名：去掉中英文空格
                	if(StringUtils.isNotBlank(stu.getStuname())){
                        stu.setStuname(stu.getStuname().replaceAll(" ","").replaceAll(" ", ""));
                    }
                    if(StringUtils.isNotBlank(family.getStuname())){
                        family.setStuname(family.getStuname().replaceAll(" ","").replaceAll(" ", ""));
                    }
	           		//----------关联字段判断-------------------------
                    validateRelatedField(colNameSet,family,importData, i);
                    
                    //出生年月
                    if(StringUtils.isNotBlank(family.getBirthday())){
						String ss = family.getBirthday()+"01";
						String date = ss.substring(0, 4)+"-"+ss.substring(4, 6)+"-"+"01";
						family.setBirthday(date);
					}
                    
                    //----------验证手机号码的惟一性--------------------
                    if (StringUtils.isNotBlank(family.getMobilephone())) {
                    	 List<Family> familyList = studentFamilyService
     					.getFamilies(family.getStuid(), family.getMobilephone());
                    	 
                    	 for (Family fam : familyList) {
 							if (!fam.getRelation().equals(family.getRelation()) || !fam.getName().equals(family.getRealname())) {
 								throw new ErrorFieldException("该生已有家长使用该手机号，家长姓名："
 										+ fam.getName() ,"mobilephone");
 							}
                    	 }
					}
                    
                    //---------判断是否存在---------------------------
                   if (baseStudentFamilyDao.isFamilyExist(unitId, family.getStuid(), family.getRealname(), family.getRelation())) {
                	   updateList.add(family);
                   } else {
                	   insertList.add(family);
                   }
                    
                } catch (ErrorFieldException e) {                    
                    this.disposeError(importData, i, e.getField(), e
                            .getMessage());
                    continue;
                }
                
            }
            
            log.debug("==============正在导入数据================");            
            processTable(isConvered, importData, reply,
                updateList, insertList);
                     
            log.debug("==============导入数据成功================");
    
        } catch (Exception e) {
            log.error("第 "+errPos+" 个家庭成员错误",e);
            throw new ImportErrorException("导入出错");
        }
    
    }
	
	
	 /**
     * 处理非正式学生表
     * @param isConvered
     * @param importData
     * @param reply
     * @param updateList
     * @param insertList
	 * @throws ParseException 
     */
    protected void processTable(String isConvered, ImportData importData, Reply reply,
            List<FamilyImport> updateList, List<FamilyImport> insertList ) throws ParseException {
       
    	//更新操作
    	int cnt = 0;
        if (updateList.size() > 0 && "1".equals(isConvered)) {
            List<String> familySqlList = new ArrayList<String>();
            List<String> userSqlList = new ArrayList<String>();
            
            for (FamilyImport fam : updateList) {
                buildUpdateSql(fam, importData, familySqlList, userSqlList);
            }
            
            if(familySqlList.size()> 0){
                cnt = systemCommonDao.batchUpdate(familySqlList.toArray(new String[0]));                    
                reply.addActionMessage("更新家庭成员信息：" + cnt + " 条。");
            }
            
            if (userSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(userSqlList.toArray(new String[0]));
				reply.addActionMessage("更新家庭成员扣费号码信息：" + cnt + " 条。");
			}
        } 
        
        //插入操作
        if(insertList.size() > 0){
        	List<String> familySqlList = new ArrayList<String>();
        	for (FamilyImport fam : insertList) {
                buildInsertSql(fam, importData, familySqlList);
            }
            cnt = systemCommonDao.batchUpdate(familySqlList.toArray(new String[0]));
            reply.addActionMessage("新增家庭成员信息：" + cnt + " 条。");
        }else{
        	reply.addActionMessage("新增家庭成员信息：0 条。");
        }
        
    }
        
    
    /**
     * 验证关联字段
     * 
     * @param colNameSet
     * @param fam
     * @throws ErrorFieldException
     */
    private void validateRelatedField(Set<String> colNameSet,
            FamilyImport fam, ImportData importData, int i)
            throws ErrorFieldException {
        String _field = "stucode";
        List<String> cols = new ArrayList<String>();
        List<String> errs = new ArrayList<String>();
       
        //验证身份证号是否是有效的
        if (StringUtils.isNotBlank(fam.getIdentitycard())) {
        	_field = "identitycard";
        	if(StringUtils.isBlank(fam.getIdentitycardType())){
        		cols.add(_field);
	            errs.add("该身份证件号没有对应的身份证件类型信息！");     
        	}else{
        		if("1".equals(fam.getIdentitycardType())){
        			String result = BusinessUtils.validateIdentityCard(fam.getIdentitycard(), false);
        			if(StringUtils.isNotBlank(result)){
        				cols.add(_field);
       	             	errs.add("不是有效的身份证号。");
        			}
        		}
        	}
			
			if(cols.size() > 0){
	            disposeError(importData, i, cols.toArray(new String[0]), errs
	                    .toArray(new String[0]));
	            throw new ErrorFieldException();
	        }
			
			//核对身份证中性别和家庭成员的性别是否一致
//			if (StringUtils.isNotBlank(fam.getSex())) {
//				int sexInt = 0;
//				if (fam.getIdentitycard().length() == 18) {
//					sexInt = Integer.valueOf(fam.getIdentitycard().substring(16,17));
//				} else {
//					sexInt = Integer.valueOf(fam.getIdentitycard().substring(14));
//				}
//				int temp = 1;//男
//				//18位身份证第17位如果是奇数，表示男，是偶数表示女
//				//15位身份证第15位如果是奇数，表示男，是偶数表示女
//				if (sexInt % 2 == 0) {
//					temp = 2;//女
//				}
//				
//				if (temp != Integer.valueOf(fam.getSex())) {
//					 _field = "sex";
//					 cols.add(_field);
//		             errs.add("身份证中的性别信息和家庭成员的性别不一致。"); 
//				}
//				
//				if(cols.size() > 0){
//		            disposeError(importData, i, cols.toArray(new String[0]), errs
//		                    .toArray(new String[0]));
//		            throw new ErrorFieldException();
//		        }
//			}
//			
//			if(cols.size() > 0){
//	            disposeError(importData, i, cols.toArray(new String[0]), errs
//	                    .toArray(new String[0]));
//	            throw new ErrorFieldException();
//	        }
			
			//核对身份证中出生日期和家庭成员的出生日期是否一致
//			if (StringUtils.isNotBlank(fam.getBirthday())) {
//				String stuBirth = fam.getBirthday();
//	        	if (fam.getBirthday() != null) {
//	        		if (stuBirth.split("-").length == 3) {
//	                    String bir2 = stuBirth.split("-")[1];
//	                    String bir3 = stuBirth.split("-")[2];
//	                    if (bir2.length() == 1)
//	                        bir2 = "0" + bir2;
//	                    if (bir3.length() == 1)
//	                        bir3 = "0" + bir3;
//	                    stuBirth = stuBirth.split("-")[0] + "-" + bir2 + "-" + bir3;
//	                }
//	        	}
//	        	
//	        	String birth = BusinessUtils.getDateStrFromIdentityNo(fam.getIdentitycard());
//	            if (null != birth && !(birth.equals(stuBirth))) {
//	                _field = "birthday";
//	                cols.add(_field);
//	                errs.add("身份证中的出生日期和家庭成员的出生日期不一致。");                
//	            }
//			}
//			
//			if(cols.size() > 0){
//	            disposeError(importData, i, cols.toArray(new String[0]), errs
//	                    .toArray(new String[0]));
//	            throw new ErrorFieldException();
//	        }
        }
    }
    
    
    /**
     * 组织插入sql
     * 
     * @param family
     * @param importData
     * @param famSqlList
     * @throws ParseException 
     */
    private void buildInsertSql(FamilyImport family, ImportData importData, 
    		List<String> famSqlList) throws ParseException {
        
        Map<String, ImportObjectNode> nodeMap = importData.getMapOfNodesName();
        List<String> colNames = importData.getListOfImportDataName();//导入列
        
        StringBuffer sb = new StringBuffer(); 
        StringBuffer sbValue = new StringBuffer();
        
        //覆盖数据 
        sb.append("insert into base_family(id,student_id,school_id,creation_time,modify_time,is_deleted,region_code,event_source");
        for (String col : colNames) {
        	if(StringUtils.isEmpty(col)) continue;
            Object obj = ObjectUtils.getProperty(family, col);//从family中取值
            String inValue = String.valueOf(obj);		
            ImportObjectNode node = nodeMap.get(col);
            String dbcol = node.getDbname();				//数据库表使用的字段值
            String outValue = getValueForSQL(node, inValue);
            if("birthday".equals(col)) {//出生年月
				//年月(yyyyMM)，  模板不能设置为Date类型，  在此再特意处理一次
				outValue = getFormattedDateByDayForSQL(inValue);
			}
            if(familyFieldSet.contains(col)){
                sb.append("," + dbcol);
            	sbValue.append("," + outValue);
            }
        } 
        
        sb.append(") values ('").append(UUIDGenerator.getUUID()).append("','").append(family.getStuid()).
           append("','").append(family.getSchid()).append("',").append(getFormattedTimeForSql()).
           append(",").append(getFormattedTimeForSql()).append(",0,'").append(family.getRegioncode()).
           append("',").append(EventSourceType.LOCAL.getValue()).append(sbValue.toString()).append(") ");
        famSqlList.add(sb.toString());
    }
	
	
	/**
     * 组织更新sql
     * 
     * @param family
     * @param importData
     * @param famSqlList
	 * @throws ParseException 
     */
    private void buildUpdateSql(FamilyImport family, ImportData importData, 
    		List<String> famSqlList, List<String> userSqlList
    		) throws ParseException {
        
        Map<String, ImportObjectNode> nodeMap = importData.getMapOfNodesName();
        List<String> colNames = importData.getListOfImportDataName();//导入列
        
        StringBuffer famSql = new StringBuffer(); 
        StringBuffer userSql = new StringBuffer();
        
        //覆盖数据 
        famSql.append("update base_family set modify_time = ").
        		append(getFormattedTimeForSql()).append(",event_source=").append(EventSourceType.LOCAL.getValue());
        boolean isBlank;
        for (String col : colNames) {
        	isBlank = false;
        	if(StringUtils.isEmpty(col)) continue;
            Object obj = ObjectUtils.getProperty(family, col);//从family中取值
            String inValue = String.valueOf(obj);		
            ImportObjectNode node = nodeMap.get(col);
            String dbcol = node.getDbname();				//数据库表使用的字段值
            String outValue = getValueForSQL(node, inValue);
            if("birthday".equals(col)) {//出生年月
				//年月(yyyyMM)，  模板不能设置为Date类型，  在此再特意处理一次
				outValue = getFormattedDateByDayForSQL(inValue);
			}
            //过滤掉家长姓名和关系
            if((!"relation".equals(col) || !"realname".equals(col)) 
            		&& familyFieldSet.contains(col) && !isBlank){
                famSql.append("," + dbcol + " = " + outValue);
            }
        } 
        
        //家庭成员
        famSql.append(" where school_id = '").append(family.getSchid()).append("' and student_id = '").
        		append(family.getStuid()).append("' and is_deleted = '0' and relation = '").append(family.getRelation()).
        		append("' and real_name = '").append(family.getRealname()).append("'");
        
        famSqlList.add(famSql.toString());
        
        //用户表
        if (StringUtils.isNotBlank(family.getChargenumber())) {
			userSql
					.append(" update base_user set base_user.charge_number = '")
					.append(family.getChargenumber())
					.append("',event_source=")
					.append(EventSourceType.LOCAL.getValue())
					.append(
							" where base_user.owner_id = (select id from base_family where rownum = 1 and school_id = '")
					.append(family.getSchid()).append("' and student_id = '")
					.append(family.getStuid()).append(
							"' and is_deleted = 0 and relation = '").append(
							family.getRelation()).append("' and real_name = '")
					.append(family.getRealname()).append("')");
			userSqlList.add(userSql.toString());
		}
    }
}

