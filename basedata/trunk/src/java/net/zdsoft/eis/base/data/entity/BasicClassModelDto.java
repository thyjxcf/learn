package net.zdsoft.eis.base.data.entity;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.leadin.util.RegUtils;


/* 
 * 页面交互时，其它成员变量
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: BasicClassModelDto.java,v 1.2 2006/11/03 03:22:56 dongzk Exp $
 */
public class BasicClassModelDto extends BasicClass {
    private static final long serialVersionUID = -1509437527722679497L;
    
    private List<Grade> gradeList;				//年级列表
	private List<BasicClass> classList;				//班级列表
	private Map<String,String> sectionMap;				//学段Map
	private Map<String,String> classtypeMap;			//班级类型微代码Map
	private Map<String,String> artsciencetypeMap;		//文理类型微代码Map
	private List<String[]> teacheridList;			//老师select html List
	private String[] checkid;			//删除班级id
	private String sectionHtml;			//学段select html
	private String acadyearHtml;		//学年列表
	private String classtypeHtml;		//班级类型select html
	private String artsciencetypeHtml;	//文理类型select html
	private String teacheridHtml;		//班主任select html
	private String stuidHtml;			//班长select html
	private String subschoolidHtml;		//分校区select html
	private String schoolinglenHtml;	//学年学制select html
	private String preClassname;		//班级名称的前半部分
	private int batchaddamount;			//批量新增班级数量
	
	private String gradeid;				//用于组合信息section|acadyear|schoolinglen
	
	


	public BasicClassModelDto(){
	}

	public int getBatchaddamount() {
		return batchaddamount;
	}

	public void setBatchaddamount(int batchaddamount) {
		this.batchaddamount = batchaddamount;
	}

	public List<BasicClass> getClassList() {
		return classList;
	}

	public void setClassList(List<BasicClass> classList) {
		this.classList = classList;
	}

	public List<Grade> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}
	
	public Map<String,String> getSectionMap() {
		return sectionMap;
	}
	
	public void setSectionMap(Map<String,String> sectionMap) {
		this.sectionMap = sectionMap;
	}
	
	public Map<String,String> getClasstypeMap() {
		return classtypeMap;
	}
	
	public void setClasstypeMap(Map<String,String> classtypeMap) {
		this.classtypeMap = classtypeMap;
	}
	
	public Map<String,String> getArtsciencetypeMap() {
		return artsciencetypeMap;
	}

	public void setArtsciencetypeMap(Map<String,String> artsciencetypeMap) {
		this.artsciencetypeMap = artsciencetypeMap;
	}

	public List<String[]> getTeacheridList() {
		return teacheridList;
	}

	public void setTeacheridList(List<String[]> teacheridList) {
		this.teacheridList = teacheridList;
	}
	
	public String[] getCheckid() {
		return checkid;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}
	
	public String getSectionHtml() {
		return sectionHtml;
	}

	public void setSectionHtml(String sectionHtml) {
		this.sectionHtml = sectionHtml;
	}
	
	public String getAcadyearHtml() {
		return acadyearHtml;
	}

	public void setAcadyearHtml(String acadyearHtml) {
		this.acadyearHtml = acadyearHtml;
	}

	public String getClasstypeHtml() {
		return classtypeHtml;
	}

	public void setClasstypeHtml(String classtypeHtml) {
		this.classtypeHtml = classtypeHtml;
	}

	public String getPreClassname() {
		return preClassname;
	}

	public void setPreClassname(String preClassname) {
		this.preClassname = preClassname;
	}

	public String getTeacheridHtml() {
		return teacheridHtml;
	}

	public void setTeacheridHtml(String teacheridHtml) {
		this.teacheridHtml = teacheridHtml;
	}

	public String getStuidHtml() {
		return stuidHtml;
	}

	public void setStuidHtml(String stuidHtml) {
		this.stuidHtml = stuidHtml;
	}
	
	public String getArtsciencetypeHtml() {
		return artsciencetypeHtml;
	}

	public void setArtsciencetypeHtml(String artsciencetypeHtml) {
		this.artsciencetypeHtml = artsciencetypeHtml;
	}
	
	public String getSubschoolidHtml() {
		return subschoolidHtml;
	}

	public void setSubschoolidHtml(String subschoolidHtml) {
		this.subschoolidHtml = subschoolidHtml;
	}



	public String getSchoolinglenHtml() {
		return schoolinglenHtml;
	}

	public void setSchoolinglenHtml(String schoolinglenHtml) {
		this.schoolinglenHtml = schoolinglenHtml;
	}

	public static void dtoToModelDto(BasicClass dto ,BasicClassModelDto modelDto){
		
		modelDto.setSchid(dto.getSchid());
		modelDto.setClasscode(dto.getClasscode());
		modelDto.setClassname(dto.getClassname());
		modelDto.setAcadyear(dto.getAcadyear());
		modelDto.setSection(dto.getSection());
		modelDto.setHonor(dto.getHonor());
		modelDto.setDatecreated(dto.getDatecreated());
		modelDto.setClasstype(dto.getClasstype());
		modelDto.setArtsciencetype(dto.getArtsciencetype());
		modelDto.setGraduatesign(dto.getGraduatesign());
		modelDto.setGraduatedate(dto.getGraduatedate());
		modelDto.setSchoolinglen(dto.getSchoolinglen());
		modelDto.setTeacherid(dto.getTeacherid());
		modelDto.setStuid(dto.getStuid());
		modelDto.setSubschoolid(dto.getSubschoolid());
		modelDto.setStucount(dto.getStucount());
		modelDto.setClassnamedynamic(dto.getClassnamedynamic());
		modelDto.setName(dto.getName());
		modelDto.setTeachername(dto.getTeachername());
		modelDto.setMonitor(dto.getMonitor());
		modelDto.setSubschoolName(dto.getSubschoolName());
		modelDto.setId(dto.getId());
        modelDto.setViceTeacherId(dto.getViceTeacherId());
	}
	
	public static void main(String[] args) {
        String regex = "[\u4e00-\u9fa5a-zA-Z0-9_]+";
        boolean ret = RegUtils.regexValidator(regex, "lin_qzzd打tnet");
        System.out.println(ret);
	}

	public String getGradeid() {
		return gradeid;
	}

	public void setGradeid(String gradeid) {
		this.gradeid = gradeid;
	}
	
}



