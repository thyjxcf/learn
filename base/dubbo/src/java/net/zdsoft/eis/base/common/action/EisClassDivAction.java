package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.eisu.base.common.service.EisuClassService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class EisClassDivAction extends ObjectDivAction<BasicClass> {

	private static final long serialVersionUID = -6480641904049122784L;

	protected BasicClassService basicClassService;

	private boolean preGraduateInclude = true;
	private String acadyear;
	private String semester;
	private String teachAreaId;// 校区id
	private String gradeId;// 开设学年学期（年级）
	private String className;
	private String schoolId;

	@Override
	protected List<BasicClass> getDatasByConditon() {
		List<BasicClass> list = null;
		if (StringUtils.isNotBlank(teacherId)) {// 根据年级教师获取班级
			list = basicClassService.getClassesByGradeId(getUnitId(), gradeId, teacherId);
		}else if (StringUtils.isNotBlank(gradeId)) {// 根据年级获取班级
			list = basicClassService.getClassesByGrade(gradeId);
		}else if(StringUtils.isNotEmpty(schoolId)){
			list=basicClassService.getClasses(schoolId); //根据学校获取班级
		}
		if(StringUtils.isNotEmpty(className) && CollectionUtils.isNotEmpty(list)){
			List<BasicClass> listName = new ArrayList<BasicClass>();
			for(BasicClass basicClass:listName){
				if(basicClass.getClassnamedynamic().indexOf(className) != -1){
					listName.add(basicClass);
				}
			}
			list=listName;
		}
		return list;
	}

	@Override
	protected void toObject(BasicClass basicClass, SimpleObject object) {
		if (basicClass == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(basicClass.getId());
		object.setObjectCode(basicClass.getClasscode());
		object.setObjectName(basicClass.getClassnamedynamic());
		object.setUnitId(basicClass.getSchid());
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public boolean isPreGraduateInclude() {
		return preGraduateInclude;
	}

	public void setPreGraduateInclude(boolean preGraduateInclude) {
		this.preGraduateInclude = preGraduateInclude;
	}

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public String getTeachAreaId() {
		return teachAreaId;
	}

	public void setTeachAreaId(String teachAreaId) {
		this.teachAreaId = teachAreaId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	

}
