package net.zdsoft.eis.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.GradeDao;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 根据班级名称生成规则动态生成班级名称，单例
 * 
 * 班级名称生成规则，班级名称生成规则有两种：
 * 规则1：小一(1)班 初一(1)班
 * 规则2: 2006级小(1)班 2006级初(1)班
 */
public class ClassNameFactory {
	Logger log = LoggerFactory.getLogger(ClassNameFactory.class);

	private static final String INIID = "SYSTEM.CLASSNAME";
	private static ClassNameFactory me = new ClassNameFactory();

	// Spring容器中得到相关Beans
	private static SystemIniService systemIniService;
	private static BasicClassService basicClassService;
	private static McodedetailService mcodedetailService;
	private static SemesterService semesterService;
	private static GradeService gradeService;
	private static GradeDao gradeDao;

	public ClassNameFactory() {
	}

	public static ClassNameFactory getInstance() {
		if (null == systemIniService) {
			systemIniService = (SystemIniService) ContainerManager
					.getComponent("systemIniService");
		}
		if (null == basicClassService) {
			basicClassService = (BasicClassService) ContainerManager
					.getComponent("basicClassService");
		}
		if (null == semesterService) {
			semesterService = (SemesterService) ContainerManager
					.getComponent("semesterService");
		}
		if (null == gradeService) {
			gradeService = (GradeService) ContainerManager
					.getComponent("gradeService");
		}
		if (null == gradeDao) {
			gradeDao = (GradeDao) ContainerManager.getComponent("gradeDao");
		}
		if (null == mcodedetailService) {
			mcodedetailService = (McodedetailService) ContainerManager
					.getComponent("mcodedetailService");
		}
		return me;
	}

	// =====================班级名称====================
	public String getClassNameDyn(String classId) {
		BasicClass cls = basicClassService.getClass(classId);
		return getClassNameDyn(cls.getSchid(), cls.getAcadyear(),
				cls.getSection(), cls.getSchoolinglen(), cls.getClassname());
	}

	public String getClassNameDyn(BasicClass cls) {
		return getClassNameDyn(cls.getSchid(), cls.getAcadyear(),
				cls.getSection(), cls.getSchoolinglen(), cls.getClassname());
	}

	public String getClassNameDyn(BasicClass cls, String curAcadyear) {
		Grade grade = gradeService.getGrade(cls.getGradeId());
		if (grade != null) {
			cls.setGradeName(grade.getGradename());
			return grade.getGradename() + cls.getClassname();
		}
		return getClassNameDyn(cls.getAcadyear(), cls.getSection(),
				cls.getSchoolinglen(), cls.getClassname(), curAcadyear);
	}

	public String getClassNameDyn(String schid, String enrollyear, int section) {
		return getClassNameDyn(schid, enrollyear, section, -1, "");
	}

	public String getClassNameDyn(String schoolId, String enrollyear,
			int section, int schoolinglen, String className) {
		String curacadyear = semesterService.getCurrentAcadyear();
		Grade grade = gradeDao.getGrade(schoolId, enrollyear, section);
		if (grade != null && StringUtils.isNotBlank(grade.getGradename())) {
			return grade.getGradename()
					+ (StringUtils.isBlank(className) ? "" : className);
		}
		return this.getClassNameDyn(enrollyear, section, schoolinglen,
				className, curacadyear);
	}

	private String getClassNameDyn(String enrollyear, int section,
			int schoolinglen, String className, String curAcadyear) {
		if (null == className) {
			className = "";
		}
		return getGradeNameDyn(enrollyear, section, schoolinglen, curAcadyear)
				+ className;
	}

	// =====================年级名称====================
	/**
	 * 根据学段，年级级别得到年级名称
	 * 
	 * @param section
	 * @param grade
	 * @return
	 */
	public String getGradeNameDyn(String gradeCode) {
		if (StringUtils.isBlank(gradeCode) || gradeCode.length() != 2)
			return null;

		int section = Integer.parseInt(gradeCode.substring(0, 1));
		int grade = Integer.parseInt(gradeCode.substring(1, 2));

		return getGradeNameDyn(section, grade);
	}

	/**
	 * 根据学段，年级级别得到年级名称
	 * 
	 * @param section
	 * @param grade
	 * @return
	 */
	public String getGradeNameDyn(String schid, int section, int grade) {
		String curacadyear = semesterService.getCurrentAcadyear();
		if (StringUtils.isBlank(curacadyear)) {
			return "请维护当前学年学期";
		}
		if (StringUtils.isBlank(schid)) {
			return getGradeNameDyn(section, grade,
					getEnrollyear(curacadyear, grade));
		}
		Grade mygrade = gradeDao.getGrade(schid,
				getEnrollyear(curacadyear, grade), section);
		if (mygrade != null && StringUtils.isNotBlank(mygrade.getGradename())) {
			return mygrade.getGradename();
		}
		return getGradeNameDyn(section, grade,
				getEnrollyear(curacadyear, grade));
	}

	/**
	 * 根据学段，年级级别得到年级名称
	 * 
	 * @param section
	 * @param grade
	 * @return
	 */
	public String getGradeNameDyn(int section, int grade) {
		String curacadyear = semesterService.getCurrentAcadyear();
		if (StringUtils.isBlank(curacadyear)) {
			return "请维护当前学年学期";
		}
		return getGradeNameDyn(section, grade,
				getEnrollyear(curacadyear, grade));
	}

	/**
	 * 得到入学学年
	 * 
	 * @param acadyear
	 * @param grade
	 * @return
	 */
	private static String getEnrollyear(String acadyear, int grade) {
		if (org.apache.commons.lang.StringUtils.isBlank(acadyear)
				|| acadyear.length() != 9 || grade < 1)
			return "";

		String tempStartYear = acadyear.substring(0, 4);
		String tempEndYear = acadyear.substring(5);
		String startYear = String.valueOf(Integer.parseInt(tempStartYear)
				- (grade - 1));
		String endYear = String.valueOf(Integer.parseInt(tempEndYear)
				- (grade - 1));

		return startYear + "-" + endYear;
	}

	/**
	 * 根据规则生成年级名称
	 * 
	 * @param section
	 *            年段
	 * @param grade
	 *            年级
	 * @param enrollyear
	 *            入学学年
	 * @return
	 */
	public String getGradeNameDyn(int section, int grade, String enrollyear) {
		String sectionName = (String) NameFactory.sectionMap.get(section);
		String gradeName = null;
		if ("1".equals(systemIniService.getSystemIni(INIID).getNowValue())) {
			if (StringUtils
					.isBlank(NameFactory.yearMap.get(new Integer(grade)))) {
				gradeName = "异常年级-" + section + grade;
			} else {
				gradeName = sectionName
						+ (String) NameFactory.yearMap.get(new Integer(grade));
			}
		} else {
			// 规则2: 2006级小 2006级初
			String year;
			if (null == enrollyear) {
				year = "";
			} else {
				year = enrollyear.substring(0, 4);
				year += "级";
			}
			gradeName = year + sectionName;
		}
		return gradeName;
	}

	public String getGradeNameDyn(Grade grade) {
		return getGradeNameDyn(grade.getSchid(), grade.getAcadyear(),
				grade.getSection(), -1);
	}

	public String getGradeNameDyn(String schoolId, String enrollyear,
			int section, int schoolingLen) {
		String curacadyear = semesterService.getCurrentAcadyear();
		return getGradeNameDyn(enrollyear, section, schoolingLen, curacadyear);
	}

	public String getGradeNameDyn(Grade grade, String curAcadyear) {
		return getGradeNameDyn(grade.getAcadyear(), grade.getSection(),
				grade.getSchoolinglen(), curAcadyear);
	}

	/**
	 * 根据入学学年、所属学段、学制、当前学年和命名规则，并且按照规则动态生成班级名称 说明：学制主要用于查看已经毕业的年级生成年级名称时使用
	 * 
	 * @param enrollyear
	 *            入学学年(格式：2005-2006)
	 * @param section
	 *            学段，微代码（DM-RKXD）:０幼儿园，１小学，２初中，３高中
	 * @param schoolinglen
	 *            学制
	 * @param curAcadyear
	 *            当前学年
	 * 
	 * @return String
	 */
	public String getGradeNameDyn(String enrollyear, int section,
			int schoolinglen, String curAcadyear) {
		if (curAcadyear == null) {
			return "(非法入学学年)";
		}

		int grade = 1
				+ NumberUtils.toInt(StringUtils.substringBefore(curAcadyear,
						"-"))
				- NumberUtils.toInt(StringUtils
						.substringBefore(enrollyear, "-"));
		if (grade <= 0) {
			return "(非法入学学年)";
		}

		// String sectionName = NameFactory.sectionMap.get(section);
		SystemIni ini = systemIniService.getSystemIni(INIID);
		String value = null;
		if (ini != null) {
			value = ini.getNowValue();
		}

		if (StringUtils.isBlank(value))
			value = "1";

		String gradeNameDyn;
		// 从微代码中取出年级规则，有几个固定变量：enrollyear，表示入学学年
		Map<String, Mcodedetail> detailMap = mcodedetailService
				.getMcodeDetailMap("DM-RKXD-" + section);
		if (MapUtils.isEmpty(detailMap))
			return "(非法入学学年)";
		// 判断年级是否已超出规定学制
		if (schoolinglen > 0) {
			grade = (grade > schoolinglen ? schoolinglen : grade);
		}
		Mcodedetail detail = detailMap.get("" + grade);
		if (detail == null)
			return "(非法入学学年)";
		gradeNameDyn = detail.getContent();
		gradeNameDyn = StringUtils.replace(gradeNameDyn, "{enrollyear}",
				StringUtils.substringBefore(enrollyear, "-"));
		return gradeNameDyn;
	}

	// =====================年级代码和名称 ====================
	public void setGradeCodeDyn(Grade grade) {
		String curAcadyear = semesterService.getCurrentAcadyear();
		grade.setGradeCodeDyn(curAcadyear);
	}

	public void setGradeDyn(Grade grade) {
		if (grade == null)
			return;
		String curAcadyear = semesterService.getCurrentAcadyear();
		Grade mygrade = gradeDao.getGrade(grade.getSchid(),
				grade.getAcadyear(), grade.getSection());
		String gradeName = null;
		if (mygrade != null && StringUtils.isNotBlank(mygrade.getGradename())) {
			gradeName = mygrade.getGradename();
		} else {
			gradeName = getGradeNameDyn(grade.getAcadyear(),
					grade.getSection(), grade.getSchoolinglen(), curAcadyear);
		}
		grade.setGradename(gradeName);
		grade.setGradeCodeDyn(curAcadyear);
	}

	public void setGradeDyn(Grade grade, String curAcadyear) {
		if (grade == null) {
			return;
		}
		grade.setGradename(getGradeNameDyn(grade.getAcadyear(),
				grade.getSection(), grade.getSchoolinglen(), curAcadyear));
		grade.setGradeCodeDyn(curAcadyear);
	}

	// =====================班级列表====================
	/**
	 * 批量生成动态显示的班级名称
	 * 
	 * @param schoolId
	 * @param classList
	 * @return
	 */
	public List<BasicClass> buildClassDyn(String schoolId,
			List<BasicClass> classList) {
		String curAcadyear = semesterService.getCurrentAcadyear();
		return buildClassDyn(classList, curAcadyear);
	}

	public List<BasicClass> buildClassDyn(List<BasicClass> classList,
			String curAcadyear) {
		if (StringUtils.isEmpty(curAcadyear)) {
			return buildClassDyn(classList);
		}
		for (BasicClass cls : classList) {
			String classNameDyn = this.getClassNameDyn(cls, curAcadyear);
			cls.setClassnamedynamic(classNameDyn);
		}
		return classList;
	}

	/**
	 * 批量生成动态显示的班级名称
	 * 
	 * @param classList
	 * @return
	 */
	public List<BasicClass> buildClassDyn(List<BasicClass> classList) {
		if (classList.size() < 1)
			return classList;

		String curacadyear = semesterService.getCurrentAcadyear();
		for (Iterator<BasicClass> it = classList.iterator(); it.hasNext();) {
			BasicClass cls = it.next();
			String classNameDyn = this.getClassNameDyn(cls, curacadyear);
			cls.setClassnamedynamic(classNameDyn);
		}
		return classList;
	}

	/**
	 * 批量生成动态显示的班级名称
	 * 
	 * @param list
	 *            存放了BasicClass的list列表
	 * @return List
	 */
	public List<String[]> buildClassNameDyn(String schoolId,
			List<BasicClass> classList) {
		List<String[]> dynList = new ArrayList<String[]>();

		String curAcadyear = semesterService.getCurrentAcadyear();
		for (BasicClass cls : classList) {
			String classNameDyn = this.getClassNameDyn(cls, curAcadyear);
			cls.setClassnamedynamic(classNameDyn);
			dynList.add(new String[] { cls.getId(), classNameDyn });
		}
		return dynList;
	}

	// =====================班级Map====================
	/**
	 * 批量生成动态显示的班级名称
	 * 
	 * @param list
	 *            存放了BasicClass的list列表
	 * @return Map 存放key=classid
	 */
	public Map<String, BasicClass> buildClassDynMap(List<BasicClass> classList) {
		Map<String, BasicClass> dynMap = new HashMap<String, BasicClass>();

		if (classList.size() < 1)
			return dynMap;

		String curacadyear = semesterService.getCurrentAcadyear();
		for (BasicClass cls : classList) {
			String classNameDyn = this.getClassNameDyn(cls, curacadyear);
			cls.setClassnamedynamic(classNameDyn);
			dynMap.put(cls.getId(), cls);
		}
		return dynMap;
	}

	/**
	 * 批量生成动态显示的班级名称
	 * 
	 * @param list
	 *            存放了BasicClass的list列表
	 * @return Map 存放key=classid,value=classnamedyn
	 */
	public Map<String, String> buildClassNameDynMap(String schoolId,
			List<BasicClass> classList) {
		String curAcadyear = semesterService.getCurrentAcadyear();

		Map<String, String> dynMap = new LinkedHashMap<String, String>();// new
																			// HashMap<String,
																			// String>();
		for (BasicClass cls : classList) {
			String classNameDyn = this.getClassNameDyn(cls, curAcadyear);
			cls.setClassnamedynamic(classNameDyn);
			dynMap.put(cls.getId(), classNameDyn);
		}
		return dynMap;
	}

	// =====================年级列表====================
	public List<Grade> buildGradeDyn(String schoolId, List<Grade> gradeList) {
		String curAcadyear = semesterService.getCurrentAcadyear();
		return buildGradeDyn(schoolId, gradeList, curAcadyear);
	}

	public List<Grade> buildGradeDyn(String schoolId, List<Grade> gradeList,
			String curAcadyear) {
		if (null == curAcadyear) {
			curAcadyear = semesterService.getCurrentAcadyear();
		}
		for (Grade grade : gradeList) {
			setGradeDyn(grade, curAcadyear);
		}
		return gradeList;
	}

	// =====================年级Map====================
	public Map<String, Grade> buildGradeDynMap(String schoolId,
			List<Grade> gradeList) {
		String curAcadyear = semesterService.getCurrentAcadyear();

		Map<String, Grade> dynMap = new HashMap<String, Grade>();
		for (Grade grade : gradeList) {
			String gradeId = grade.getId();
			setGradeDyn(grade, curAcadyear);

			dynMap.put(gradeId, grade);
		}
		return dynMap;
	}

	public static void setMcodedetailService(
			McodedetailService mcodedetailService) {
		ClassNameFactory.mcodedetailService = mcodedetailService;
	}

}
