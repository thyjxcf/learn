package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observer;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.impl.BasicClassServiceImpl;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.dao.BaseClassDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.entity.BasicClassModelDto;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolSemesterService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.observe.ObserveKey;
import net.zdsoft.eis.base.observe.SystemDefaultBusinessRoleObserveParam;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.ClassNameFactory;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.FieldErrorException;
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
public class BaseClassServiceImpl extends BasicClassServiceImpl implements
		BaseClassService {

	private final static Logger log = LoggerFactory
			.getLogger(BaseClassServiceImpl.class);
	private StudentService studentService;
	private BaseClassDao baseClassDao;
	private BaseSchoolService baseSchoolService;
	private BaseSchoolSemesterService baseSchoolSemesterService;
	private BaseGradeService baseGradeService;
	private SystemIniService systemIniService;
	
	
	public void setBaseSchoolSemesterService(
			BaseSchoolSemesterService baseSchoolSemesterService) {
		this.baseSchoolSemesterService = baseSchoolSemesterService;
	}

	public void setBaseGradeService(BaseGradeService baseGradeService) {
		this.baseGradeService = baseGradeService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setBaseClassDao(BaseClassDao baseClassDao) {
		this.baseClassDao = baseClassDao;
	}

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}

	@Override
	public void addClass(BasicClass cls) {
		BasicClass oldDto = new BasicClass();
		BasicClass newDto = cls;
		baseClassDao.insertClass(cls);
		saveClassEx(oldDto, newDto);
	}

	@Override
	public void updateClass(BasicClass cls) {
		BasicClass oldDto = new BasicClass();
		BasicClass newDto = cls;
		String classid = cls.getId();
		oldDto = this.getClass(classid);
		baseClassDao.updateClass(cls);
		saveClassEx(oldDto, newDto);
	}

	private void saveClassEx(BasicClass oldDto, BasicClass newDto) {
		// 添加观察者对象,改变更新标志，并通知观察者已经发生变化
		List<Observer> observers = ObserverHelper
				.getObservers(ObserveKey.BUSINESSROLE.getValue());
		for (Observer observer : observers) {
			this.addObserver(observer);
		}
		log.debug("通知对象个数=" + this.countObservers());

		this.setChanged();
		this.notifyObservers(new SystemDefaultBusinessRoleObserveParam(oldDto,
				newDto));
	}

	public void saveClass(BasicClass dto) {
		BasicClass oldDto = new BasicClass();
		BasicClass newDto = dto;
		String classid = dto.getId();
		if (checkClass(dto)) {
			if (StringUtils.isNotBlank(classid)) {// 修改保存
				oldDto = this.getClass(classid);
				dto.setDisplayOrder(oldDto.getDisplayOrder());
			}
		}
		String acadyear = dto.getAcadyear();
		if (acadyear == null) {
			acadyear = semesterService.getCurrentAcadyear();
		}
		if (acadyear == null || acadyear.length() != 9) {
			acadyear = "0000-0000";
		}
		dto.setAcadyear(acadyear);

		try {
			if (0 == dto.getDisplayOrder()) {
				int max = baseClassDao
						.getMaxOrder(dto.getSchid(), dto.getAcadyear(), dto
								.getSection(), dto.getSchoolinglen());
				dto.setDisplayOrder(max + 1);
			} else {
				dto.setDisplayOrder(dto.getDisplayOrder());
			}
		} catch (Exception e) {
			log.error("get max order number from classes error! schoolId["
					+ dto.getSchid() + ", acadyear[" + dto.getAcadyear()
					+ "], section[" + dto.getSection() + "], schoolingLen["
					+ dto.getSchoolinglen() + "]", e);
			dto.setDisplayOrder(1);
		}

		if (StringUtils.isEmpty(classid)) {
			baseClassDao.insertClass(dto);
		} else {
			baseClassDao.updateClass(dto);
		}

		saveClassEx(oldDto, newDto);
	}

	public void createClassBatch(BasicClassModelDto dto) {
		int amount = dto.getBatchaddamount();
		BasicClass[] entities = new BasicClass[amount];
		String originalClasscode = dto.getClasscode();
		boolean flagvalue = true;
		int index = 0;
		for (int i = 0; i < amount; i++) {
			// classcode是否已经存在
			boolean flag = baseClassDao.isExistsClassCode(dto.getSchid(), dto
					.getClasscode());
			if (flag) {
				flagvalue = false;
				String systemDeploySchool = systemIniService
						.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
				if(StringUtils.equals(systemDeploySchool, BaseConstant.SYS_DEPLOY_SCHOOL_SXRRT)){
					throw new BusinessErrorException("批量新增班级失败，每个年级下班级数量应小于100个！<br>");
				}else{
					throw new BusinessErrorException("批量新增班级" + dto.getClasscode()
							+ "失败：已经存在相同编码的班级，请更改编码后重试！<br>"
							+ ClassNameFactory.getInstance().getClassNameDyn(dto));
				}
			}

			String classCode = dto.getClasscode();

			// 班级名称200602004
			String subClassCode = classCode.substring(classCode.length() - 2);
			long code = convertStrToNumRight(subClassCode);
			boolean useKh = systemIniService.getBooleanValue(BasicClass.CLASSNAME_USE_BRACKETS);
	        String className = "";
	        if(useKh)
	        	className = "(" + StringUtils.leftPad(String.valueOf(code), 2, "0") + ")班";
	        else
	        	className = StringUtils.leftPad(String.valueOf(code), 2, "0") + "班";
			dto.setClassname(className);

			// 检查班级名称是否已存在，并把所有同名的班级名称返回
			boolean bTemp = baseClassDao.isExistsClassName(dto.getSchid(), dto
					.getSection(), dto.getAcadyear(), dto.getClassname());
			if (bTemp) {
				flagvalue = false;
				throw new BusinessErrorException("批量新增时发现以下班级名称已存在，请修改！<br>"
						+ ClassNameFactory.getInstance().getClassNameDyn(dto));
			}

			BasicClass entity = dto;
			String acadyear = entity.getAcadyear();
			if (acadyear == null) {
				acadyear = semesterService.getCurrentAcadyear();
			}
			if (acadyear == null || acadyear.length() != 9) {
				acadyear = "0000-0000";
			}
			dto.setAcadyear(acadyear);

			if (0 == dto.getDisplayOrder()) {
				try {
					int max = baseClassDao.getMaxOrder(dto.getSchid(), dto
							.getAcadyear(), dto.getSection(), dto
							.getSchoolinglen());
					entity.setDisplayOrder(max + 1);
				} catch (Exception e) {
					flagvalue = false;
					log.error(
							"get max order number from classes error! schoolId["
									+ dto.getSchid() + ", acadyear["
									+ dto.getAcadyear() + "], section["
									+ dto.getSection() + "], schoolingLen["
									+ dto.getSchoolinglen() + "]", e);
					entity.setDisplayOrder(1);
				}
			} else {
				entity.setDisplayOrder(dto.getDisplayOrder() + index);
				index++;
			}

			entities[i] = entity;
			entity.setCreationTime(new Date());
			try {
				baseClassDao.insertClass(entity);
			} catch (Exception e) {
				throw new BusinessErrorException("批量新增班级失败：请查看日志文件！");
			}

			// 班级编号+1
			String nextClassCode = incrementString(classCode);
			dto.setClasscode(nextClassCode);
			dto.setId(null);
		}

		if (flagvalue)
			dto.setClasscode(originalClasscode);
	}

	private boolean checkClass(BasicClass dto) {
		boolean flag = true;
		// classcode在同一学校中是否已经存在
		boolean flagCode = baseClassDao.isExistsClassCode(dto.getSchid(), dto
				.getClasscode());
		// classname在同一年级中是否已经存在
		boolean flagName = baseClassDao.isExistsClassName(dto.getSchid(), dto
				.getSection(), dto.getAcadyear(), dto.getClassname());

		String classid = dto.getId();
		BasicClass oldDto = new BasicClass();
		if (StringUtils.isBlank(classid)) { // 新增保存
			// 判断classcode在同一学校中是否已经存在
			if (flagCode) {
				flag = false;
				String systemDeploySchool = systemIniService
						.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
				if(StringUtils.equals(systemDeploySchool, BaseConstant.SYS_DEPLOY_SCHOOL_SXRRT)){
					throw new FieldErrorException("classcode", "每个年级下班级数量应小于100个");
				}else{
					throw new FieldErrorException("classcode", "代码已存在，请更改");
				}
			}
			// 判断classname在同一年级中是否已经存在
			if (flagName) {
				flag = false;
				throw new FieldErrorException("classname", "班级名称已存在，请更改");
			}
		} else {
			// 修改保存
			oldDto = this.getClass(classid);
			// classcode修改过，判断是否重复
			if (!(dto.getClasscode().equals(oldDto.getClasscode())) && flagCode) {
				flag = false;
				String systemDeploySchool = systemIniService
						.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
					throw new FieldErrorException("classcode", "代码已存在，请更改");
			}
			// classname修改过，判断在同一年级中是否重复
			if (!(dto.getClassname().equals(oldDto.getClassname())) && flagName) {
				flag = false;
				throw new FieldErrorException("classname", "班级名称已存在，请更改");
			}
		}
		return flag;
	}

	/**
	 * 将以数字结尾的字符串数字部分加1，即数字格式的编号递增1个号码（例如：班级编号的递增）
	 * 
	 * @param numberStr
	 *            以数字结尾的字符串
	 * @return String
	 */
	private static String incrementString(String numberStr) {
		String rtnStr = null;

		if (numberStr == null || "".equals(numberStr)) {
			return rtnStr;
		}
		int length = numberStr.length();
		int pos = getNumPositionRight(numberStr, false);

		// 不是以数字结尾
		if (pos == -1) {
			return numberStr + "001";
		}

		// 以数字结尾
		long number = Long.parseLong(numberStr.substring(pos));
		number++;
		String tempStr = "" + number;
		int tempLength = tempStr.length();

		if (pos == 0) {
			// 全部是数字:20060205
			if (tempLength < length) {
				rtnStr = generatRepeatStr("0", (length - tempLength)) + tempStr;
			} else {
				rtnStr = tempStr;
			}
		} else {
			// 部分是数字，前半部分有非数字字符，如CM1201
			if (tempLength < (length - pos)) {
				rtnStr = numberStr.substring(0, pos)
						+ generatRepeatStr("0", (length - pos - tempLength))
						+ tempStr;
			} else {
				rtnStr = numberStr.substring(0, pos) + tempStr;
			}
		}

		return rtnStr;
	}

	/**
	 * 产生重复字符串的字符串
	 * 
	 * @param repeatStr
	 *            要重复的字符串
	 * @param repeatCount
	 *            重复的个数
	 * @return String
	 */
	private static String generatRepeatStr(String repeatStr, int repeatCount) {
		String rtnStr = "";

		if ((repeatCount < 1) || ("".equals(repeatStr)) || (repeatStr == null)) {
			return rtnStr;
		}

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < repeatCount; i++) {
			buf.append(repeatStr);
		}

		rtnStr = buf.toString();

		return rtnStr;
	}

	/**
	 * 得到一个字符串从右边数起的数字部分的起始位置，从0开始计数
	 * 
	 * @param str
	 * @param flagZero
	 *            是否碰到0时结束：true是（如：CODE200612，返回7），false否（如：CODE200612，返回4）
	 * @return int -1:字符串右边没有数字型数据
	 */
	private static int getNumPositionRight(String str, boolean flagZero) {
		int length = str.length();
		int i = length;
		while (i > 0) {
			char c = str.charAt(i - 1);

			// 如果不是数字
			if (!(Character.isDigit(c))) {
				break;
			}
			// 如果碰到0就结束
			if (flagZero) {
				if (c == '0')
					break;
			}
			i--;
		}

		// 若右边没有时，返回-1
		if (i == length) {
			i = -1;
		}
		return i;
	}

	public long convertStrToNumRight(String str) {
		int position = getNumPositionRight(str, false);
		if (position == -1) {
			return -1;
		}

		long number;
		try {
			number = Long.parseLong(str.substring(position));
		} catch (Exception ex) {
			return -1;
		}

		return number;
	}

	public void deleteClasses(String[] classIds)
			throws Exception {
		List<String> list = null;
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < classIds.length; i++) {
			list = this.isUseClass(null, classIds[i]);
			if (list.size() > 0) {
				// 在使用，不能删除
//				buf.append("<br><font color='#FF0000'>"
//						+ this.getClass(classIds[i]).getClassnamedynamic()
//						+ "</font>  在以下地方已被使用：<br>");
//				for (Iterator<String> it = list.iterator(); it.hasNext();) {
//					buf.append(it.next() + "<br>");
//				}
				buf.append(","+this.getClass(classIds[i]).getClassnamedynamic());
			} else {
				// 软删除
				this.deleteClass(classIds[i], EventSourceType.LOCAL);
			}
		}

		if (buf != null && !("").equals(buf.toString())) {
			throw new BusinessErrorException("删除提示：" + buf.toString().substring(1)
					+ "<br>以上班级已被使用，不能删除！");
		}

	}

	@Override
	public void deleteClass(String id, EventSourceType eventSource) {
		baseClassDao.deleteClass(id, eventSource);
	}

	public void updateGradeId(String schoolId, String gradeId, String acadyear,
			int section, String schoolingLength) {
		baseClassDao.updateGradeId(schoolId, gradeId, acadyear, section,
				schoolingLength);
	}

	public String getNextClassCodeWithGraduatedYear(String schid, int section,
			String acadyear) {
		String nextCode = "";
		BaseSchool basicSchoolinfo = baseSchoolService.getBaseSchool(schid);
		int schoolingLength = 0;
		if (section == 1) {
			schoolingLength = basicSchoolinfo.getGradeyear();
		} else if (section == 2) {
			schoolingLength = basicSchoolinfo.getJunioryear();
		} else if (section == 3) {
			schoolingLength = basicSchoolinfo.getSenioryear();
		}

		String maxCode = baseClassDao.getMaxClassCode(schid, section, acadyear);

		if (maxCode == null) {
			String sectionCode = "00" + section;
			nextCode = String.valueOf(Integer.valueOf(acadyear.substring(0, 4))
					+ schoolingLength)
					+ sectionCode.substring(sectionCode.length() - 2) + "001";
		} else {
			nextCode = incrementString(maxCode);
		}
		return nextCode;
	}

	public List<String> isUseClass(String schoolId, String classId) {
		List<String> list = new ArrayList<String>();
		int numStu = studentService.getStudentCount(classId);
		if (numStu > 0) {
			list.add("学生信息");
		}
		return list;
	}

    public boolean isExistsClass(String schoolId) {
        return baseClassDao.isExistsClass(schoolId);
    }

    public boolean isExistsClass(String schoolId, int section) {
        return baseClassDao.isExistsClass(schoolId, section);
    }

    public boolean isExistsClass(String schoolId, String enrollyear) {
        return baseClassDao.isExistsClass(schoolId, enrollyear);
    }

	@Override
	public void saveClassWithJob(String schoolId, String acadyear) throws Exception {
		List<String[]> sectionList = schoolService.getSchoolSections(schoolId);
        for (String[] sections : sectionList) {
            int section = Integer.valueOf(sections[1]);
            List<BasicClass> basicClassList = basicClassDao.getClasses(schoolId, section, acadyear, 0);
            if (CollectionUtils.isEmpty(basicClassList)) {
                // 第一学期
                SchoolSemester schSem = baseSchoolSemesterService.getCurrentAcadyear(schoolId);
                if (StringUtils.isBlank(schSem.getId())) {
                    Semester semester = semesterService.getSemester(schSem.getAcadyear(), schSem.getSemester());
                    // 创建学年学期
                    schSem.setSchid(schoolId);
                    schSem.setRegisterdate(new Date());// 注册日期
                    schSem.setClasshour(semester.getClasshour());// 课长
                    try {
                        baseSchoolSemesterService.saveSemester(schSem);
                    }
                    catch (Exception e) {
                        continue;
                    }
                }
                // 第二学期
                SchoolSemester schSemester2 = baseSchoolSemesterService.getSemester(schoolId, acadyear, "2");// 学校
                if (schSemester2 == null) {
                    Semester semester2 = semesterService.getSemester(acadyear, "2");// 教育局
                    if (semester2 == null) {
                        System.out.println("------------------教育局第二学期未设置-----------------------");
                    }
                    else {
                        // 创建学年学期
                        SchoolSemester schSem2 = new SchoolSemester();
                        schSem2.setSchid(schoolId);
                        schSem2.setAcadyear(semester2.getAcadyear());
                        schSem2.setSemester(semester2.getSemester());
                        schSem2.setAmperiods(semester2.getAmPeriods());
                        schSem2.setPmperiods(semester2.getPmPeriods());
                        schSem2.setNightperiods(semester2.getNightPeriods());
                        schSem2.setWorkbegin(semester2.getWorkBegin());
                        schSem2.setWorkend(semester2.getWorkEnd());
                        schSem2.setSemesterbegin(semester2.getSemesterBegin());
                        schSem2.setSemesterend(semester2.getSemesterEnd());
                        schSem2.setEdudays(semester2.getEduDays());
                        schSem2.setRegisterdate(new Date());// 注册日期
                        schSem2.setClasshour(semester2.getClasshour());// 课长
                        baseSchoolSemesterService.saveSemester(schSem2);
                        
                    }
                }
//                BasicClass basicClass = new BasicClass();
//                basicClass.setSection(section);
//                basicClass.setAcadyear(acadyear);// 年级--入学学年
//                String[] clsdef = getClassDefaultInfo(schoolId, section, acadyear);
//                basicClass.setClasscode(clsdef[1]);// 班级代码
//                basicClass.setClassname(clsdef[2]);// 班级名称
//                basicClass.setDatecreated(new Date());// 建班年月
//                basicClass.setArtsciencetype(0);// 文理类型
//                basicClass.setSchoolinglen(Integer.valueOf(clsdef[5]));// 学制
//                basicClass.setIsdeleted(false);
//                basicClass.setSchid(schoolId);
//                basicClass.setSubschoolid(schoolId);// 此处未考虑分校区
//                basicClass.setGradeId(createGrade(basicClass));
//                try {
//                    saveClass(basicClass);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
		
	}
	/*
     * 初始化数据：年级，班级代码，班级名称的前半部分，班级类型，学制
     */
    private String[] getClassDefaultInfo(String schid, int section, String acadyear) {
        String[] retStr = new String[6];

        /*
         * 得到入学学年的下拉框（值是入学学年，显示是年级）
         */
        // 学制
        int _schoolinglen = getSchoolinglen(schid, section);
        // 当前学年(格式：2005-2006)
        String _currentAcadyear = semesterService.getCurrentAcadyear();
        // 当“（当前学年-入学学年）>学制” 时，入学学年赋值为当前学年
        int endyear = Integer.parseInt(_currentAcadyear.substring(5));
        int startyear = Integer.parseInt(acadyear.substring(0, 4));
        int grade = endyear - startyear;
        if (grade > _schoolinglen)
            acadyear = _currentAcadyear;
        String _classcode = getNextClassCodeWithGraduatedYear(schid, section, acadyear);
        retStr[1] = _classcode;

        /*
         * 班级名称
         */
        // 将数值型字符串转换为数字，从右边开始转换
        long code = convertStrToNumRight(_classcode.substring(_classcode.length() - 3));
        boolean useKh = systemIniService.getBooleanValue(BasicClass.CLASSNAME_USE_BRACKETS);
        String _classname;
        if (useKh) {
            _classname = "(" + StringUtils.leftPad("" + code, 2, "0") + ")班";
        }
        else {
            _classname = StringUtils.leftPad("" + code, 2, "0") + "班";
        }

        retStr[2] = _classname;

        /*
         * 班级名称前缀
         */
        String _preClassname = ClassNameFactory.getInstance().getClassNameDyn(schid, acadyear, section, _schoolinglen,
                null);
        retStr[3] = _preClassname;

        /*
         * 学制
         */
        retStr[5] = "" + _schoolinglen;

        return retStr;
    }

    private int getSchoolinglen(String schid, int section) {
        int len = baseSchoolService.getSchoolingLen(schid, section);
        // 如果没有维护各学段的学制
        if (len == 0) {
            if (1 == section)
                len = 6;
            if (2 == section)
                len = 3;
            if (3 == section)
                len = 3;
            if (0 == section)
                len = 3;
        }
        return len;
    }
    private String createGrade(BasicClass basicClass) {
        Grade grade = baseGradeService.getGrade(basicClass.getSchid(), basicClass.getAcadyear(),
                basicClass.getSection());
        String gradeId;
        if (grade == null) {
            gradeId = UUIDGenerator.getUUID();
            if (grade == null) {
                Grade basegrade = new Grade();
                basegrade.setAcadyear(basicClass.getAcadyear());
                basegrade.setAmLessonCount(4);
                basegrade.setCreationTime(new Date());
                basegrade.setId(gradeId);
                basegrade.setIsdeleted(false);
                basegrade.setIsGraduated(0);
                basegrade.setNightLessonCount(0);
                basegrade.setPmLessonCount(4);
                basegrade.setSchid(basicClass.getSchid());
                basegrade.setSchoolinglen(basicClass.getSchoolinglen());
                basegrade.setSection(basicClass.getSection());
                basegrade.setTeacherId(null);
                ClassNameFactory.getInstance().setGradeDyn(basegrade);
                baseGradeService.saveGrade(basegrade);
            }
        }
        else {
            gradeId = grade.getId();
        }
        return gradeId;
    }
    
    @Override
    public String getNextClassCode(String schoolId, String section, String acadyear, int schoolingLength) {
        String value = systemIniService.getValue("SYSTEM.CLASSCODE");
        
        String classCode = null;
        // 1=毕业年份+2位学段+2位序号
        if ("1".equals(value)) {
            int graduatedYear = NumberUtils.toInt(StringUtils.substringBefore(acadyear, "-")) + schoolingLength;
            String sectionStr = StringUtils.leftPad(section, 2, "0");
            String maxCode = baseClassDao.getMaxClassCodeByPrefix(schoolId, graduatedYear + sectionStr, 8);
            if(StringUtils.isBlank(maxCode))
                classCode = graduatedYear + sectionStr + "01";
            else
                classCode = "" + (NumberUtils.toLong(maxCode) + 1);
        }
        // 2=入学年份+2位序号
        else if ("2".equals(value)) {
            int enrollYear = NumberUtils.toInt(StringUtils.substringBefore(acadyear, "-"));
            String maxCode = baseClassDao.getMaxClassCodeByPrefix(schoolId, enrollYear + "", 6);
            if(StringUtils.isBlank(maxCode))
                classCode = enrollYear + "01";
            else
                classCode = "" + (NumberUtils.toLong(maxCode) + 1);
        }
        // 3=入学年份+2位学段+2位序号,默认取这种
        else {
            int enrollYear = NumberUtils.toInt(StringUtils.substringBefore(acadyear, "-"));
            String sectionStr = StringUtils.leftPad(section, 2, "0");
            String maxCode = baseClassDao.getMaxClassCodeByPrefix(schoolId, enrollYear + sectionStr, 8);
            if(StringUtils.isBlank(maxCode))
                classCode = enrollYear + sectionStr + "01";
            else
                classCode = "" + (NumberUtils.toLong(maxCode) + 1);
        }
        return classCode;
    }

	@Override
	public void updateClassSchLength(List<BasicClass> updataList) {
		//暂时使用这个
		for(BasicClass cls : updataList)
			this.updateClass(cls);
	}
}
