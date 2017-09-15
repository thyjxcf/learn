package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.constant.enumeration.VersionType;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.eisu.base.common.service.EisuClassService;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.commons.lang.StringUtils;

public class ClassDivAction extends ObjectDivAction<EisuClass> {

	private static final long serialVersionUID = -6480641904049122784L;

	protected EisuClassService eisuClassService;

	private String specialtyId;
	private boolean preGraduateInclude = true;
	private String acadyear;
	private String semester;
	private String teachAreaId;// 校区id
	private String openAcadyear;// 开设学年学期（年级）
	private String className;
	private GradeService gradeService;
	
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	@Override
	protected List<EisuClass> getDatasByUnitId() {
		List<EisuClass> list = null;
		if (!preGraduateInclude && StringUtils.isNotBlank(acadyear)) {
			list = eisuClassService.getClassByAcadyearTeaId(getUnitId(),
					teachAreaId, acadyear, teacherId);
		} else {
			list = eisuClassService.getClasses(getUnitId());
		}
		if (StringUtils.isBlank(teacherId))
			teacherId = getLoginInfo().getUser().getTeacherid();
		if (stusysShowPopedom) {
			List<String> listOfClassId = subsystemPopedomService
					.getPopedomClassIds(teacherId);
			List<EisuClass> resultList = new ArrayList<EisuClass>();
			for (EisuClass ec : list) {
				if (listOfClassId.contains(ec.getId())) {
					resultList.add(ec);
				}
			}
			list = resultList;
		}
		boolean eduadmAdmin=false;
		if(eduadmSubsystemService!=null){
			eduadmAdmin = eduadmSubsystemService.isEduadmRole(getUnitId(),
					teacherId);
		}
		if (eduadmShowPopedom && !eduadmAdmin) {
			List<String> listOfClassId = eduadmSubsystemService
					.getCourseClassIdsByTeacherId(getUnitId(), acadyear,
							semester, teacherId);
			List<EisuClass> resultList = new ArrayList<EisuClass>();
			for (EisuClass ec : list) {
				if (listOfClassId.contains(ec.getId())) {
					resultList.add(ec);
				}
			}
			list = resultList;
		}
		if (achiShowPopedom && !eduadmAdmin) {
			List<String> listOfClassId = eduadmSubsystemService
					.getAchiClassIdsByTeacherId(getUnitId(), acadyear,
							semester, teacherId);
			List<EisuClass> resultList = new ArrayList<EisuClass>();
			for (EisuClass ec : list) {
				if (listOfClassId.contains(ec.getId())) {
					resultList.add(ec);
				}
			}
			list = resultList;
		}
		if("eis".equals(getSystemDeploySchVersion())){
			for (EisuClass cls : list) {
				Grade grade = gradeService.getGrade(cls.getGradeId());
		        if (grade != null) {
		            cls.setClassname(grade.getGradename() + cls.getClassname());
		        }
			}
		}
		if (StringUtils.isNotBlank(className)) {
			List<EisuClass> resultList = new ArrayList<EisuClass>();
			for (EisuClass cls : list) {
				if (cls.getClassname().indexOf(className) != -1) {
					resultList.add(cls);
				}

			}
			list = resultList;
		}
		
		return list;
	}

	@Override
	protected List<EisuClass> getDatasByConditon() {
		List<EisuClass> list = null;
		if (StringUtils.isNotBlank(teacherId)) {// 根据年级教师获取班级
			list = eisuClassService.getClassesByOpenacadyear(getUnitId(),
					openAcadyear, teacherId);
		} else if (StringUtils.isNotBlank(specialtyId)
				&& StringUtils.isNotBlank(openAcadyear)) {// 根据专业年级获取班级
			list = eisuClassService.getClassesBySpecialtyId(specialtyId,
					openAcadyear);
		} else if (StringUtils.isNotBlank(specialtyId)) {// 根据专业获取班级
			list = eisuClassService.getClassesBySpecialtyId(specialtyId);
		} else if (StringUtils.isNotBlank(teachAreaId)
				&& StringUtils.isNotBlank(openAcadyear)) {// 校区和班级
			list = eisuClassService.getClassesByTeachAreaIdAndOpenAcadyear(
					getUnitId(), teachAreaId, openAcadyear);
		} else if (StringUtils.isNotBlank(teachAreaId)) {// 根据校区获取班级
			list = eisuClassService.getClassesByAreaId(teachAreaId);
		} else if (StringUtils.isNotBlank(openAcadyear)) {// 根据年级获取班级
			list = eisuClassService.getClassesByOpenAcadyear(getUnitId(),
					openAcadyear);
		} else {
			if (!preGraduateInclude && StringUtils.isNotBlank(acadyear)) {
				list = eisuClassService.getClassByAcadyearTeaId(getUnitId(),
						teachAreaId, acadyear, teacherId);
			} else {
				list = eisuClassService.getClasses(getUnitId());
			}
		}
		if (StringUtils.isBlank(teacherId))
			teacherId = getLoginInfo().getUser().getTeacherid();
		if (stusysShowPopedom) {
			List<String> listOfClassId = subsystemPopedomService
					.getPopedomClassIds(teacherId);
			List<EisuClass> resultList = new ArrayList<EisuClass>();
			for (EisuClass ec : list) {
				if (listOfClassId.contains(ec.getId())) {
					resultList.add(ec);
				}
			}
			list = resultList;
		}
		if (eduadmShowPopedom) {
			boolean eduadmAdmin = eduadmSubsystemService.isEduadmRole(getUnitId(),
					teacherId);
			if(!eduadmAdmin){
				List<String> listOfClassId = eduadmSubsystemService
						.getCourseClassIdsByTeacherId(getUnitId(), acadyear,
								semester, teacherId);
				List<EisuClass> resultList = new ArrayList<EisuClass>();
				for (EisuClass ec : list) {
					if (listOfClassId.contains(ec.getId())) {
						resultList.add(ec);
					}
				}
				list = resultList;
			}
		}

		if (achiShowPopedom) {
			boolean eduadmAdmin = eduadmSubsystemService.isEduadmRole(getUnitId(),
					teacherId);
			if(!eduadmAdmin){
				List<String> listOfClassId = eduadmSubsystemService
						.getAchiClassIdsByTeacherId(getUnitId(), acadyear,
								semester, teacherId);
				List<EisuClass> resultList = new ArrayList<EisuClass>();
				for (EisuClass ec : list) {
					if (listOfClassId.contains(ec.getId())) {
						resultList.add(ec);
					}
				}
				list = resultList;
			}
		}
		if("eis".equals(getSystemDeploySchVersion())){
			for (EisuClass cls : list) {
				Grade grade = gradeService.getGrade(cls.getGradeId());
		        if (grade != null) {
		            cls.setClassname(grade.getGradename() + cls.getClassname());
		        }
			}
		}
		if (StringUtils.isNotBlank(className)) {
			List<EisuClass> resultList = new ArrayList<EisuClass>();
			for (EisuClass cls : list) {
				if (cls.getClassname().indexOf(className) != -1) {
					resultList.add(cls);
				}
			}
			list = resultList;
		}
		return list;
	}
	public String getSystemDeploySchVersion(){
		if (systemDeployService == null) {
			systemDeployService = (SystemDeployService) ContainerManager
					.getComponent("systemDeployService");
		}
		if(VersionType.EISU == systemDeployService.getVersionType()){
			return "eisu";
		}
		return "eis";
	}
	@Override
	protected void toObject(EisuClass eisuClass, SimpleObject object) {
		if (eisuClass == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(eisuClass.getId());
		object.setObjectCode(eisuClass.getClasscode());
		object.setObjectName(eisuClass.getClassname());
		object.setUnitId(eisuClass.getSchid());
	}

	public String getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}

	public void setEisuClassService(EisuClassService eisuClassService) {
		this.eisuClassService = eisuClassService;
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

	public String getOpenAcadyear() {
		return openAcadyear;
	}

	public void setOpenAcadyear(String openAcadyear) {
		this.openAcadyear = openAcadyear;
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

}
