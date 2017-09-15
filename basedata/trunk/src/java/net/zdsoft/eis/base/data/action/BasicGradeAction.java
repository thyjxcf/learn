/* 
 * @(#)BasicGradeAction.java    Created on 2007-7-18
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.SchoolSemesterService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
/**
 * 
 * @todo 
 * @author 年级信息设置
 * @date 
 */
public class BasicGradeAction extends BaseAction {

    private static final long serialVersionUID = 1082572036636058857L;
    private BaseGradeService baseGradeService;
    private BasicClassService basicClassService;
    private SchoolService schoolService;
    private McodeService mcodeService;
    private TeacherService teacherService;
    private BaseSchoolService baseSchoolService;
    private SchoolSemesterService schoolSemesterService;
    private List<Grade> gradeList;
    private List<String[]> sectionList;

    // 保存学段的map
    private Map<String, String> sectionMap;
    private Grade grade;
    private List<String> openYearList;
    private String section;
    private String acadyear;
    private int schoolinglen;  //学制长度
    //private String switchOn = "no"; //年级名称是否可以修改
    private String gradeId;
    public String getNewGradeId() {
        return UUIDGenerator.getUUID();
    }
    
    /**
     * 年级设置
     * @return
     */
    public String getBasicGradeList() {
    	String cuUnitId = getLoginInfo().getUnitID();
        try {
            sectionList = schoolService.getSchoolSections(cuUnitId);
            if (CollectionUtils.isEmpty(sectionList)) {
                gradeList = new ArrayList<Grade>();
                return SUCCESS;
            }
            if (StringUtils.isBlank(section)) {
                section = sectionList.get(0)[1];
            }
            gradeList = baseGradeService.getGrades(cuUnitId, NumberUtils.toInt(section));
            List<Grade> gradeListInfo = baseGradeService.getGradesContainDelete(cuUnitId, NumberUtils.toInt(section));
            if(CollectionUtils.isEmpty(gradeListInfo)) {
                for(int i=0;i<sectionList.size();i++){
                	if(sectionList.get(i)[1].equals(section))
                		gradeList = baseGradeService.initGrades(cuUnitId, Integer.parseInt(section));
                	else{
                		//初始化所有年级信息
                		baseGradeService.initGrades(cuUnitId, Integer.parseInt(sectionList.get(i)[1]));
                	}
                }
            }
            // 取得学校信息
            BaseSchool basicSchoolinfoDto = baseSchoolService.getBaseSchool(getUnitId());
            // 分别取得小学、初中、高中和幼儿园的学制
    		if (BaseConstant.SECTION_INFANT.equals(section)) { 
            	schoolinglen = basicSchoolinfoDto.getInfantyear();
            }
            else if (BaseConstant.SECTION_PRIMARY.equals(section)) {
            	schoolinglen = basicSchoolinfoDto.getGradeyear();
            }
            else if (BaseConstant.SECTION_JUNIOR.equals(section)) {
            	schoolinglen = basicSchoolinfoDto.getJunioryear();
            }
            else if (BaseConstant.SECTION_HIGH_SCHOOL.equals(section)) {
            	schoolinglen = basicSchoolinfoDto.getSenioryear();
            }
//            if (sectionList.size() > 0) {
//                gradeList = baseGradeService.initGrades(cuUnitId, Integer.parseInt(section));
//            }
//            else {
//                gradeList = new ArrayList<Grade>();   
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        Map<String, Teacher> teacherMap = teacherService.getTeacherMap(cuUnitId);
        if(CollectionUtils.isNotEmpty(gradeList)){
        	for (Grade grade : gradeList) {
        		if(org.apache.commons.lang.StringUtils.isNotEmpty(grade.getTeacherId())){
        			Teacher t = teacherMap.get(grade.getTeacherId());
        			if(t == null){
        				grade.setTeacherId(null);
        				grade.setTeacherName("教师已被删除，请选择年级组长后重新保存年级信息");
        			}else{
        				grade.setTeacherName(t.getName());
        			}
        		}
        	}
        }
        sectionMap = mcodeService.getMcode("DM-RKXD").getCodeMap();
//        Collections.sort(gradeList, new Comparator<BasicGradeDto>() {
//
//            public int compare(BasicGradeDto o1, BasicGradeDto o2) {
//                return o2.getAcadyear().compareTo(o1.getAcadyear());
//            }
//        });
        return SUCCESS;
    }

    public String takeGradeName(String unitId, String acadyear, int section,
            int schoolingLength) {
        return baseGradeService.getGradeName(unitId, acadyear, section,
                schoolingLength);
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }
    public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}

    public String saveGrade(){
    	 try {
    		 List<Grade> list = new ArrayList<Grade>();
    		 list.add(grade);
    		 baseGradeService.saveGradeAsyncClass(list);
//             baseGradeService.saveGrade(grade);
             this.promptMessageDto.setOperateSuccess(true);
             this.promptMessageDto.setPromptMessage("年级信息保存成功！");
         }
         catch (Exception e) {
             this.promptMessageDto.setOperateSuccess(true);
             this.promptMessageDto.setPromptMessage(e.getMessage());
         }
    	return SUCCESS;
    }
    
    public String saveGradeList(){
    	try {
    		baseGradeService.saveGradeAsyncClass(gradeList);
//            baseGradeService.saveGrades(gradeList);
            this.promptMessageDto.setOperateSuccess(true);
            this.promptMessageDto.setPromptMessage("年级信息保存成功！");
        }
        catch (Exception e) {
        	this.promptMessageDto.setOperateSuccess(false);
            this.promptMessageDto.setPromptMessage("年级信息保存失败！"+e );     
            System.out.println(e); 
        }
    	return SUCCESS;
    }
    /**
     * 新增年级
     * @return
     */
    public String addNewGrade(){
    	String currentUnitId = getUnitId();
    	sectionList = schoolService.getSchoolSections(currentUnitId);
//    	gradeList = baseGradeService.getGrades(currentUnitId, NumberUtils.toInt(section));
    	openYearList = baseGradeService.getOpenYearList(currentUnitId,NumberUtils.toInt(section));
    	grade = new Grade();
    	grade.setSchid(getUnitId());
    	grade.setSection(NumberUtils.toInt(section));
    	grade.setAcadyear(openYearList.get(0));
    	grade.setGradename(baseGradeService.getGradeNameByDyn(grade));
    	getCurrentYear();
    	return SUCCESS;
    }

    /**
     * 删除年级
     * @return
     */
    public String deleteGrade(){
        try {
            Grade grade = baseGradeService.getGrade(gradeId);
            if (grade == null) {
                return SUCCESS;
            }
            List<BasicClass> list = basicClassService.getClassesByGrade(gradeId);
            if (list.size() > 0) {
            	this.promptMessageDto.setOperateSuccess(false);
                this.promptMessageDto.setErrorMessage("该年级下存在班级信息,请先删除班级信息,才能删除年级信息！");
                return SUCCESS;
            }
            //界面操作 是否发送消息默认置为true
            baseGradeService.deleteGrade(gradeId, EventSourceType.LOCAL);
            this.promptMessageDto.setOperateSuccess(true);
            this.promptMessageDto.setPromptMessage("成功删除年级信息！");
        }
        catch (Exception e) {
        	this.promptMessageDto.setOperateSuccess(false);
        	this.promptMessageDto.setErrorMessage(e.getMessage());
        }
    	return SUCCESS;
    }
    
    public String doChangeOpenYear(){
    	Grade newgrade = new Grade();
    	newgrade.setSchid(getUnitId());
    	newgrade.setAcadyear(acadyear);
    	newgrade.setSection(NumberUtils.toInt(section));
    	String gradeName = baseGradeService.getGradeNameByDyn(newgrade);
    	this.promptMessageDto.setPromptMessage(gradeName);
    	return SUCCESS;
    }
    
    public Map<String, String> getSectionMap() {
        return sectionMap;
    }

    public void setSectionMap(Map<String, String> sectionMap) {
        this.sectionMap = sectionMap;
    }

    public void setMcodeService(McodeService mcodeService) {
        this.mcodeService = mcodeService;
    }

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

	public List<String[]> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<String[]> sectionList) {
		this.sectionList = sectionList;
	}


	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

    public void setBaseGradeService(BaseGradeService baseGradeService) {
        this.baseGradeService = baseGradeService;
    }

    public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    public void setSchoolSemesterService(
			SchoolSemesterService schoolSemesterService) {
		this.schoolSemesterService = schoolSemesterService;
	}

    /**
     * 是否允许修改年级名称
     * @return
     */
	public String getSwitchOn() {
		boolean gradeNameReset = systemIniService.getBooleanValue(BasicClass.GRADENAME_IS_RESET);
		if(gradeNameReset)
			return "yes";
		return "no";
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
   public List<String> getOpenYearList() {
		return openYearList;
	}

	public void setOpenYearList(List<String> openYearList) {
		this.openYearList = openYearList;
	}

	public int getSchoolinglen() {
		return schoolinglen;
	}

	public void setSchoolinglen(int schoolinglen) {
		this.schoolinglen = schoolinglen;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public int getCurrentSemYear() {
		SchoolSemester basicSemesterDto = schoolSemesterService.getCurrentAcadyear(getUnitId());
		int currentYear=0;
        if(basicSemesterDto != null){
        	String acadyear = basicSemesterDto.getAcadyear();
        	currentYear = Integer.valueOf(acadyear.substring(0, 4));
        }
		return currentYear;
	}

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

}
