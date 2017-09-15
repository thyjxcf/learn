package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.service.SchoolSemesterService;
import net.zdsoft.eis.base.common.service.impl.GradeServiceImpl;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.dao.BaseGradeDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.observe.ObserveKey;
import net.zdsoft.eis.base.observe.SystemDefaultBusinessRoleObserveParam;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.ClassNameFactory;
import net.zdsoft.leadin.exception.OperationNotAllowedException;
import net.zdsoft.leadin.observe.ObserverHelper;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanb
 * 
 */
public class BaseGradeServiceImpl extends GradeServiceImpl implements
		BaseGradeService {
	private static final Logger log = LoggerFactory
			.getLogger(BaseGradeServiceImpl.class);

	private BaseGradeDao baseGradeDao;
	private BaseSchoolService baseSchoolService;
	private SchoolSemesterService schoolSemesterService;
	protected BaseClassService baseClassService;

	public void setBaseGradeDao(BaseGradeDao baseGradeDao) {
		this.baseGradeDao = baseGradeDao;
	}

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
        this.baseSchoolService = baseSchoolService;
    }

    public void setSchoolSemesterService(
			SchoolSemesterService schoolSemesterService) {
		this.schoolSemesterService = schoolSemesterService;
	}

    public void setBaseClassService(BaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}
	// ===================以上是set======================
	public void addGrade(Grade grade) {
	    ClassNameFactory.getInstance().setGradeCodeDyn(grade);
		Grade oldGrade = new Grade();
		Grade newGrade = grade;
		baseGradeDao.insertGrade(grade);
		saveGradeEx(oldGrade, newGrade);
	}

	public void updateGrade(Grade grade) {
	    ClassNameFactory.getInstance().setGradeCodeDyn(grade);
	    
		// 得到原来的年级和修改后新的年级
		Grade oldGrade = getBaseGrade(grade.getId());
		Grade newGrade = grade;
		baseGradeDao.updateGrade(grade);
		saveGradeEx(oldGrade, newGrade);
	}

	public void saveGrade(Grade grade) {
	    if (StringUtils.isBlank(grade.getGradeCode())) {
            ClassNameFactory.getInstance().setGradeCodeDyn(grade);
        }
	    
		// 得到原来的年级和修改后新的年级
		Grade oldGrade = getBaseGrade(grade.getId());
		if (oldGrade != null) {
			grade.setIsGraduated(oldGrade.getIsGraduated());
			System.out.println(grade.getTeacherId()); 
			baseGradeDao.updateGrade(grade);
		} else {
			oldGrade = new Grade();
			int maxValue = baseGradeDao.getMaxOrder(grade.getSchid());
			grade.setDisplayOrder(maxValue + 1);
			grade.setIsGraduated(0);
			baseGradeDao.insertGrade(grade);
		}

		saveGradeEx(oldGrade, grade);
	}

	private void saveGradeEx(Grade oldGrade, Grade newGrade) {
		// 添加观察者对象,改变更新标志，并通知观察者已经发生变化
		List<Observer> observers = ObserverHelper
				.getObservers(ObserveKey.BUSINESSROLE.getValue());
		for (Observer observer : observers) {
			this.addObserver(observer);
		}
		log.debug("通知对象个数=" + this.countObservers());

		this.setChanged();
		this.notifyObservers(new SystemDefaultBusinessRoleObserveParam(
				oldGrade, newGrade));
	}

	public void saveGrades(List<Grade> gradeList) throws Exception {
		List<String> list = new ArrayList<String>();
		String key;
		int i = 0;
		for (Grade grade : gradeList) {
			i++;
			key = grade.getSchid() + grade.getAcadyear() + grade.getSection()
					+ grade.getSchoolinglen();
			if (!list.contains(key)) {
				list.add(key);
			} else {
				throw new Exception("第" + i + "条记录的入学学年和学制与第"
						+ (list.indexOf(key) + 1) + "记录重复,请修改！");
			}
		}
		for (Grade grade : gradeList) {
			saveGrade(grade);
		}
	}

	public void deleteGrade(String gradeId, EventSourceType eventSource) {
		baseGradeDao.deleteGrade(gradeId, eventSource);
	}

	public Grade getBaseGrade(String gradeId) {
		Grade grade = baseGradeDao.getGrade(gradeId);
		if (null == grade) {
			return null;
		}

		ClassNameFactory.getInstance().setGradeDyn(grade);
		return grade;
	}

	public int updateGrade(String schId, int amNumber, int pmNumber,
			int nightNumber) {
		return baseGradeDao.updateGrade(schId, amNumber, pmNumber, nightNumber);
	}

	public List<Grade> getBaseGrades(String schoolId, int section) {
		List<Grade> listOfEntity = baseGradeDao
				.getBaseGrades(schoolId, section);

		String curAcadyear = semesterService.getCurrentAcadyear();

		for (Grade grade : listOfEntity) {	
			ClassNameFactory.getInstance().setGradeDyn(grade, curAcadyear);
		}
		return listOfEntity;
	}

	public List<Grade> initGrades(String unitId) throws OperationNotAllowedException {
		List<String[]> sectionList = baseSchoolService.getSchoolSections(unitId);
		if (CollectionUtils.isEmpty(sectionList)) {
			throw new OperationNotAllowedException("初始化年级信息失败，请将学校信息基本信息维护完整！");
		}
		List<Grade> gradeList = null;
		List<Grade> gradeListRet = new ArrayList<Grade>();
		String acadyear = semesterService.getCurrentAcadyear();
		BaseSchool basicSchoolinfoDto = baseSchoolService.getBaseSchool(unitId);
		int gradeYear = basicSchoolinfoDto.getGradeyear();
		int juniorYear = basicSchoolinfoDto.getJunioryear();
		int seniorYear = basicSchoolinfoDto.getSenioryear();
		int[] sectionLength = { gradeYear, juniorYear, seniorYear };
		int currentYear = Integer.valueOf(acadyear.substring(0, 4));
		int section;

		List<Grade> listOfBasicGradeDto = baseGradeDao
				.getOverSchoolinglenGrades(unitId, acadyear);
		for (Grade basicGrade : listOfBasicGradeDto) {
			updateGraduate(unitId, basicGrade.getAcadyear(), basicGrade
					.getSection(), basicGrade.getSchoolinglen());
		}

		List<Integer> listOfSectionByGrade = baseGradeDao.getSections(unitId);
		for (Integer ses : listOfSectionByGrade) {
			boolean contain = false;
			for (String[] s : sectionList) {
				if (Integer.parseInt(s[1]) == ses) {
					contain = true;
					break;
				}
			}
			if (!contain) {
				baseGradeDao.deleteGrade(unitId, ses);
				continue;
			}
		}
		Map<String, Grade> map = getGradeMap(unitId);
		for (int j = 0; j < sectionList.size(); j++) {
			section = Integer.parseInt(sectionList.get(j)[1]);
			if (section == -1) {
				return gradeListRet;
			}
			gradeList = getBaseGrades(unitId, section);
			gradeListRet.addAll(gradeList);

			String acadYear;
			Grade basicGradeDto;
			for (int i = 0; i < sectionLength[section - 1]; i++) {
				acadYear = String.valueOf(currentYear - i) + "-"
						+ String.valueOf(currentYear - i + 1);
				basicGradeDto = map.get(unitId + acadYear + section
						+ sectionLength[section - 1]);
				if (basicGradeDto == null) {
					basicGradeDto = new Grade();
					basicGradeDto.setAmLessonCount(4);
					basicGradeDto.setPmLessonCount(4);
					basicGradeDto.setNightLessonCount(0);
					basicGradeDto.setId(UUIDGenerator.getUUID());
					basicGradeDto.setAcadyear(acadYear);
					basicGradeDto.setSchid(unitId);
					basicGradeDto.setSection(section);
					if(section==9)
						basicGradeDto.setSchoolinglen(3);
					else
						basicGradeDto.setSchoolinglen(sectionLength[section - 1]);
					basicGradeDto.setIsGraduated(0);
					
					ClassNameFactory.getInstance().setGradeDyn(basicGradeDto);
					saveGrade(basicGradeDto);
					gradeListRet.add(basicGradeDto);
				}
				// String schoolingLength = "";
				// try {
				// if (basicGradeDto.getSchoolinglen() == 0) {
				// schoolingLength = null;
				// } else {
				// schoolingLength =
				// String.valueOf(basicGradeDto.getSchoolinglen());
				// }
				// } catch (Exception e) {
				// schoolingLength = null;
				// }
				// baseClassService.updateGradeId(basicGradeDto.getSchid(),
				// basicGradeDto.getId(), basicGradeDto.getAcadyear(),
				// basicGradeDto
				// .getSection(), schoolingLength);
			}
		}
		return gradeListRet;
	}

	public List<Grade> initGrades(String schoolId, int section) {
//		List<String[]> sectionList = baseSchoolService.getSchoolSections(schoolId);
//		List<Grade> gradeList = null;
//		SchoolSemester basicSemesterDto = schoolSemesterService
//				.getCurrentAcadyear(schoolId);
//		BaseSchool basicSchoolinfoDto = baseSchoolService.getBaseSchool(schoolId);
//		int Infantyear = basicSchoolinfoDto.getInfantyear();
//		int gradeYear = basicSchoolinfoDto.getGradeyear();
//		int juniorYear = basicSchoolinfoDto.getJunioryear();
//		int seniorYear = basicSchoolinfoDto.getSenioryear();
//		int[] sectionLength = {Infantyear, gradeYear, juniorYear, seniorYear };
//		int currentYear = Integer.valueOf(basicSemesterDto.getAcadyear()
//				.substring(0, 4));
//		// 自动升级的时候，学年学期设置的课时数会不同，导致这里显示的也要跟着变更。
//		/*
//		 * if (basicSemesterDto != null) { // 修改课时数的时候，同时修改年级表中的课时数
//		 * updateGrade(schoolId, basicSemesterDto.getAmperiods(),
//		 * basicSemesterDto.getPmperiods(), basicSemesterDto
//		 * .getNightperiods()); }
//		 */
//
//		 if (sectionList.size() > 0) {
//			if (section == -1 || "".equals(section))
//				section = Integer.parseInt(sectionList.get(0)[1]);
//			if (section == -1) {
//				gradeList = new ArrayList<Grade>();
//				return gradeList;
//			}
//			gradeList = getBaseGrades(schoolId, section);
//			Map<String, Grade> map = getGradeMap(schoolId);
//
//			String acadYear;
//			Grade basicGradeDto;
//			if(CollectionUtils.isEmpty(gradeList)){
//				for (int i = 0; i < sectionLength[section - 1]; i++) {
//					acadYear = String.valueOf(currentYear - i) + "-"
//							+ String.valueOf(currentYear - i + 1);
//					basicGradeDto = map.get(schoolId + acadYear + section
//							+ sectionLength[section - 1]);
//					if (basicGradeDto == null) {
//						basicGradeDto = new Grade();
//						if (basicSemesterDto != null) {
//							basicGradeDto.setAmLessonCount(basicSemesterDto
//									.getAmperiods());
//							basicGradeDto.setPmLessonCount(basicSemesterDto
//									.getPmperiods());
//							basicGradeDto.setNightLessonCount(basicSemesterDto
//									.getNightperiods());
//						} else {
//							basicGradeDto.setAmLessonCount(4);
//							basicGradeDto.setPmLessonCount(4);
//							basicGradeDto.setNightLessonCount(0);
//						}
//						basicGradeDto.setId(UUIDGenerator.getUUID());
//						basicGradeDto.setAcadyear(acadYear);
//						basicGradeDto.setSchid(schoolId);
//						basicGradeDto.setSection(section);
//						basicGradeDto.setSchoolinglen(sectionLength[section - 1]);
//						basicGradeDto.setIsGraduated(0);
//						ClassNameFactory.getInstance().setGradeDyn(basicGradeDto);
//						saveGrade(basicGradeDto);
//						
//						gradeList.add(basicGradeDto);
//					}
//				}
//			}
//		} else {
//			gradeList = new ArrayList<Grade>();
//		}
//		return gradeList;
		// 取得学校的学段信息
        List<String[]> sectionList = baseSchoolService.getSchoolSections(schoolId);
        if (CollectionUtils.isEmpty(sectionList) || section < 0)
            return new ArrayList<Grade>();

        // 取得当前学年学期信息
        SchoolSemester basicSemesterDto = schoolSemesterService.getCurrentAcadyear(schoolId);
        if(basicSemesterDto == null)
            return new ArrayList<Grade>();
        
        String acadyear = basicSemesterDto.getAcadyear();
        // 取得学校信息
        BaseSchool basicSchoolinfoDto = baseSchoolService.getBaseSchool(schoolId);

        int studyYear = 0;
        // 分别取得小学、初中、高中和幼儿园的学制
        if (section == NumberUtils.toInt(BaseConstant.SECTION_INFANT)) {
        	//幼儿园默认3
            studyYear = basicSchoolinfoDto.getInfantyear()==0?3:basicSchoolinfoDto.getInfantyear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_PRIMARY)) {
            studyYear = basicSchoolinfoDto.getGradeyear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_JUNIOR)) {
            studyYear = basicSchoolinfoDto.getJunioryear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_HIGH_SCHOOL)) {
            studyYear = basicSchoolinfoDto.getSenioryear();
        }//特殊处理
        else if (section == 9) {
            studyYear = basicSchoolinfoDto.getSenioryear();
        }
        int currentYear = Integer.valueOf(acadyear.substring(0, 4));
        
        List<Grade> grades = baseGradeDao.getBaseGradesWithGraduated(schoolId, section);
        Set<String> acadyearSet = new HashSet<String>();
        for(Grade grade : grades) {
            if(grade.getSection() == section)
                acadyearSet.add(grade.getAcadyear());
        }
        String acadYear;
        Grade basicGradeDto;
        List<Grade> gradeList = new ArrayList<Grade>();
        for (int i = 0; i < studyYear; i++) {
            acadYear = String.valueOf(currentYear - i) + "-" + String.valueOf(currentYear - i + 1);
            if(acadyearSet.contains(acadYear)) {
                continue;
            }
            basicGradeDto = new Grade();
            if (basicSemesterDto != null) {
				basicGradeDto.setAmLessonCount(basicSemesterDto.getAmperiods());
				basicGradeDto.setPmLessonCount(basicSemesterDto.getPmperiods());
				basicGradeDto.setNightLessonCount(basicSemesterDto.getNightperiods());
			} else {
				basicGradeDto.setAmLessonCount(4);
				basicGradeDto.setPmLessonCount(4);
				basicGradeDto.setNightLessonCount(0);
			}
            basicGradeDto.setId(UUIDGenerator.getUUID());
            basicGradeDto.setAcadyear(acadYear);
            basicGradeDto.setSchid(schoolId);
            basicGradeDto.setSection(section);
            basicGradeDto.setSchoolinglen(studyYear);
            basicGradeDto.setIsGraduated(0);
            ClassNameFactory.getInstance().setGradeDyn(basicGradeDto);
            saveGrade(basicGradeDto);
            gradeList.add(basicGradeDto);
        }
        return gradeList;
	}

	public Map<String, Grade> getGradeMap(String schoolId) {
		Map<String, Grade> mapOfGrade = baseGradeDao.getGradeMap(schoolId);

		List<Grade> listOfBasicGrade = new ArrayList<Grade>();
		for (Grade grade : mapOfGrade.values()) {
			listOfBasicGrade.add(grade);
		}
		buildGradeDynMap(listOfBasicGrade);
		return mapOfGrade;
	}

	// =====================年级Map====================
	private Map<String, Grade> buildGradeDynMap(List<Grade> gradeList) {
		String curAcadyear = semesterService.getCurrentAcadyear();

		Map<String, Grade> dynMap = new HashMap<String, Grade>();
		for (Grade grade : gradeList) {
			String gradeId = grade.getId();
			ClassNameFactory.getInstance().setGradeDyn(grade, curAcadyear);

			dynMap.put(gradeId, grade);
		}
		return dynMap;
	}
	
	public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId, String acadyear, Integer[] section) {
		List<Grade> listOfEntity = baseGradeDao.getBaseGradesBySchidSectionAcadyear(schoolId, acadyear, section);
		String curAcadyear = semesterService.getCurrentAcadyear();
		for (Grade grade : listOfEntity) {	
			ClassNameFactory.getInstance().setGradeDyn(grade, curAcadyear);
		}
		return listOfEntity;
	}
	
	public Map<String, Grade> getGradeDynMap(String[] gradeIds) {
		List<Grade> gradeList = getGradesByIds(gradeIds);
		if (CollectionUtils.isNotEmpty(gradeList)) {
			return buildGradeDynMap(gradeList);
		} else {
			return new HashMap<String, Grade>();
		}
	}

	@Override
	public List<Grade> getBaseGradesWithGraduated(String schoolId, int section) {
		return baseGradeDao.getBaseGradesWithGraduated(schoolId, section);
	}

	@Override
	public List<Grade> getOverSchoolinglenGrades(String schoolId,
			String curAcadyear) {
		return baseGradeDao.getOverSchoolinglenGrades(schoolId, curAcadyear);
	}
	
	/**
	 * 查找该年级组长的所有负责年级
	 * @param schoolId
	 * @param teacherId
	 * @return
	 */
    public List<Grade> getBaseGradesByTeacherId(String schoolId, String teacherId) {
    	return baseGradeDao.getBaseGradesByTeacherId(schoolId, teacherId);
    }

	@Override
	public List<String> getOpenYearList(String schoolId, int section) {
		// 取得当前学年学期信息
        SchoolSemester basicSemesterDto = schoolSemesterService.getCurrentAcadyear(schoolId);
        if(basicSemesterDto == null)
            return new ArrayList<String>();
        String acadyear = basicSemesterDto.getAcadyear();
        int currentYear = Integer.valueOf(acadyear.substring(0, 4));
		// 取得学校信息
        BaseSchool basicSchoolinfoDto = baseSchoolService.getBaseSchool(schoolId);

        int studyYear = 0;
        // 分别取得小学、初中、高中和幼儿园的学制
        if (section == NumberUtils.toInt(BaseConstant.SECTION_INFANT)) {
            studyYear = basicSchoolinfoDto.getInfantyear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_PRIMARY)) {
            studyYear = basicSchoolinfoDto.getGradeyear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_JUNIOR)) {
            studyYear = basicSchoolinfoDto.getJunioryear();
        }
        else if (section == NumberUtils.toInt(BaseConstant.SECTION_HIGH_SCHOOL)) {
            studyYear = basicSchoolinfoDto.getSenioryear();
        }
       
        List<Grade> grades = baseGradeDao.getBaseGrades(schoolId, section);
        Set<String> acadyearSet = new HashSet<String>();
        for(Grade grade : grades) {
            if(grade.getSection() == section)
                acadyearSet.add(grade.getAcadyear());
        }
        String acadYear;
        List<String> openYearList = new ArrayList<String>();
        for (int i = 0; i < studyYear; i++) {
            acadYear = String.valueOf(currentYear - i) + "-" + String.valueOf(currentYear - i + 1);
            if(acadyearSet.contains(acadYear))
            	continue;
            openYearList.add(acadYear);
        }
		return openYearList;
	}

	@Override
	public List<Grade> getGradesContainDelete(String cuUnitId, int section) {
		List<Grade> listOfEntity = baseGradeDao.getGrades(cuUnitId, section);
		return listOfEntity;
	}

	@Override
	public String getGradeNameByDyn(Grade newgrade) {
		ClassNameFactory.getInstance().setGradeDyn(newgrade);
		return newgrade.getGradename();
	}

	@Override
	public void saveGradeAsyncClass(List<Grade> list) throws Exception {
		//判断那些年级的学制有变化
		List<Grade> updatas = new ArrayList<Grade>();
		List<Grade> adds = new ArrayList<Grade>();
		List<BasicClass> updataList = new ArrayList<BasicClass>();
		for(Grade newgrade : list){
			if(StringUtils.isNotBlank(newgrade.getId())){
				updatas.add(newgrade);
				Grade old = baseGradeDao.getGrade(newgrade.getId());
				if(old.getSchoolinglen()!=newgrade.getSchoolinglen()){
					List<BasicClass> classList = baseClassService.getClassesByGrade(newgrade.getId());
					for(BasicClass cls : classList)
						cls.setSchoolinglen(newgrade.getSchoolinglen());
					updataList.addAll(classList);
				}
			}else{
				adds.add(newgrade);
			}
		}
		//修改班级对应的学制
		baseClassService.updateClassSchLength(updataList);
		if(adds.size()>0)
			saveGrades(adds);
		if(updatas.size()>0)
			saveGrades(updatas);
	}

}
