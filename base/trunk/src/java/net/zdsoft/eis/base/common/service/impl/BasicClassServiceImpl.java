package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import net.zdsoft.eis.base.common.dao.BasicClassDao;
import net.zdsoft.eis.base.common.dao.GradeDao;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.KinClass;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.util.ClassNameFactory;

import org.apache.commons.lang3.StringUtils;

public class BasicClassServiceImpl extends Observable implements BasicClassService {
	protected BasicClassDao basicClassDao;
    protected SemesterService semesterService;
    protected SchoolService schoolService;
    private McodedetailService mcodedetailService;// 微代码信息
    private GradeService gradeService;
    private GradeDao gradeDao;
    

	public void setGradeDao(GradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

    public void setBasicClassDao(BasicClassDao basicClassDao) {
        this.basicClassDao = basicClassDao;
    }
    
    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public void setMcodedetailService(McodedetailService mcodedetailService) {
        this.mcodedetailService = mcodedetailService;
    }

    @Override
    public BasicClassDao getClassDao() {
        return basicClassDao;
    }
    // ====================以上为set方法==============

    public void updateGraduateSign(String classId, int sign) {
        basicClassDao.updateGraduateSign(classId, sign);
    }

    public BasicClass getClass(String classId) {
        BasicClass entity = basicClassDao.getClass(classId);
        if (entity == null)
            return null;

        this.setClassNameDyn(entity);
        return entity;
    }

    public BasicClass getClass(String classId, String currentAcadyear) {
        BasicClass entity = basicClassDao.getClass(classId);
        if (entity == null)
            return null;

        this.setClassNameDyn(entity, currentAcadyear);
        return entity;
    }

    public List<BasicClass> getClasses(String[] classIds) {
        List<BasicClass> entityList = basicClassDao.getClasses(classIds);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getClasses(String[] classIds, String curAcadYear) {
        List<BasicClass> entityList = basicClassDao.getClasses(classIds);
        return this.setClassNameDyn(entityList, curAcadYear);
    }

    public Map<String, BasicClass> getClassMap(String[] classIds) {
        Map<String, BasicClass> classMap = new HashMap<String, BasicClass>();
        List<BasicClass> classList = getClasses(classIds);
        for (int i = 0; i < classList.size(); i++) {
            BasicClass cls = classList.get(i);
            classMap.put(cls.getId(), cls);
        }
        return classMap;
    }

    public Map<String, BasicClass> getClassMap(String[] classIds, String curAcadyear) {
        Map<String, BasicClass> classMap = new HashMap<String, BasicClass>();
        List<BasicClass> classList = getClasses(classIds, curAcadyear);
        for (int i = 0; i < classList.size(); i++) {
            BasicClass cls = classList.get(i);
            classMap.put(cls.getId(), cls);
        }
        return classMap;
    }

    // ======================按teacherId查询班级，权限使用=======================
    public List<BasicClass> getClassesByTeacherId(String teacherId) {
        List<BasicClass> entityList = basicClassDao.getClassesByTeacherId(teacherId);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getClassesByTeacherId(String campusId, String teacherId) {
        List<BasicClass> entityList = basicClassDao.getClassesByTeacherId(campusId, teacherId);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatingClassesByTeacherId(String teacherId,
            String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatingClassesByTeacherId(teacherId,
                graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatingClassesByTeacherId(String campusId, String teacherId,
            String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatingClassesByTeacherId(campusId,
                teacherId, graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatedClassesByTeacherId(String teacherId, String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatedClassesByTeacherId(teacherId,
                graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatedClassesByTeacherId(String campusId, String teacherId,
            String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatedClassesByTeacherId(campusId,
                teacherId, graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    public Map<String, List<BasicClass>> getClassesMapKeyTeacherId(String schoolId) {
        List<BasicClass> entityList = getClasses(schoolId);
        List<BasicClass> list = ClassNameFactory.getInstance().buildClassDyn(schoolId, entityList);

        Map<String, List<BasicClass>> map = new HashMap<String, List<BasicClass>>();
        List<BasicClass> classPerTeacherList = new ArrayList<BasicClass>();
        for (Object object : list) {
            BasicClass dto = (BasicClass) object;
            String[] teachers = new String[] { dto.getTeacherid(), dto.getViceTeacherId() };
            for (String teacherId : teachers) {
                if (null != teacherId && teacherId.trim().length() > 0) {
                    if (map.containsKey(teacherId)) {
                        classPerTeacherList = map.get(teacherId);
                    } else {
                        classPerTeacherList = new ArrayList<BasicClass>();
                    }
                    classPerTeacherList.add(dto);
                    map.put(teacherId, classPerTeacherList);
                }
            }
        }
        return map;
    }

    // ======================按年级组长teacherId查询班级，权限使用=======================
    public List<BasicClass> getClassesByGradeTeacherId(String gradeTeacherId) {
        return getClassesByGradeTeacherId(gradeTeacherId, null);
    }
    
    public List<BasicClass> getClassesByGradeTeacherIdOrClassTeacherId(String teacherId){
   	 List<BasicClass> entityList = basicClassDao.getClassesByGradeTeacherIdOrClassTeacherId(teacherId,null);
        return this.setClassNameDyn(entityList);
   }

    public List<BasicClass> getClassesByGradeTeacherId(String gradeTeacherId, String campusId) {
        List<BasicClass> entityList = basicClassDao.getClassesByGradeTeacherId(gradeTeacherId,
                campusId);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatingClassesByGradeTeacherId(String gradeTeacherId,
            String graduateAcadyear) {
        return this.getGraduatingClassesByGradeTeacherId(gradeTeacherId, null, graduateAcadyear);
    }

    public List<BasicClass> getGraduatingClassesByGradeTeacherId(String gradeTeacherId,
            String campusId, String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatingClassesByGradeTeacherId(
                gradeTeacherId, campusId, graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatedClassesByGradeTeacherId(String gradeTeacherId,
            String graduateAcadyear) {
        return this.getGraduatedClassesByGradeTeacherId(gradeTeacherId, null, graduateAcadyear);
    }

    public List<BasicClass> getGraduatedClassesByGradeTeacherId(String gradeTeacherId,
            String campusId, String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatedClassesByGradeTeacherId(
                gradeTeacherId, campusId, graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    // ======================按schoolId查询班级=======================

    public List<BasicClass> getAllClasses(String schoolId) {
        return basicClassDao.getAllClasses(schoolId);
    }

    // 根据学校ID得到班级列表，并接所属学段、班级编码分别升序，入学学年降序排序
    public List<BasicClass> getClasses(String schoolId) {
        return getClasses(schoolId, -1);
    }

    public List<BasicClass> getClasses(String schoolId, int section) {
        return getClasses(schoolId, section, null);
    }

    public List<BasicClass> getClasses(String schoolId, int section, String enrollyear) {
        return getClasses(schoolId, section, enrollyear, 0);
    }

    public List<BasicClass> getClasses(String schoolId, int section, String enrollyear,
            int schoolingLen) {
        List<BasicClass> entityList = basicClassDao.getClasses(schoolId, section, enrollyear,
                schoolingLen);
        Map<String,Grade> gradeMap =gradeService.getGradeMapBySchid(schoolId);
        for (BasicClass basicClass : entityList) {
        	Grade grade=gradeMap.get(basicClass.getGradeId());
        	if (grade!=null&&StringUtils.isNotBlank(grade.getGradename())) {
				basicClass.setClassnamedynamic(grade.getGradename()+basicClass.getClassname());
			}
		}
        return entityList;
    }

    public List<BasicClass> getClasses(String schoolId, String curAcadyear) {
        List<BasicClass> entityList = basicClassDao.getClasses(schoolId, curAcadyear);
        return this.setClassNameDyn(entityList, curAcadyear);
    }
    
    public List<BasicClass> getClassesBy(String schoolId, String curAcadyear, int section){
    	List<BasicClass> entityList = basicClassDao.getClassesBy(schoolId, curAcadyear, section);
        return this.setClassNameDyn(entityList, curAcadyear);
    }

    public List<BasicClass> getOverSchoolinglenClasses(String schoolId, String curAcadyear) {
        List<BasicClass> entityList = basicClassDao.getOverSchoolinglenClasses(schoolId,
                curAcadyear);
        return this.setClassNameDyn(schoolId, entityList);
    }

    public List<BasicClass> getGraduatingClasses(String schoolId, String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao
                .getGraduatingClasses(schoolId, graduateAcadyear);
        return this.setClassNameDyn(schoolId, entityList);
    }

    public List<BasicClass> getGraduatedClasses(String schoolId, String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatedClasses(schoolId, graduateAcadyear);
        return this.setClassNameDyn(schoolId, entityList);
    }

    // ======================按campusId查询班级=======================
    public List<BasicClass> getClassesByCampusId(String campusId) {
        List<BasicClass> entityList = basicClassDao.getClassesByCampusId(campusId);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatingClassesByCampusId(String campusId, String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatingClassesByCampusId(campusId,
                graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    public List<BasicClass> getGraduatedClassesByCampusId(String campusId, String graduateAcadyear) {
        List<BasicClass> entityList = basicClassDao.getGraduatedClassesByCampusId(campusId,
                graduateAcadyear);
        return this.setClassNameDyn(entityList);
    }

    // ======================根据班级查询年级=======================
    private List<Grade> buildGradeName(String schoolId, List<Grade> list, String curAcadyear) {
        if (null == curAcadyear) {
            curAcadyear = semesterService.getCurrentAcadyear();
        }

        List<Grade> rtnList = new ArrayList<Grade>();
        for (Iterator<Grade> it = list.iterator(); it.hasNext();) {
            Grade grade = it.next();
            int schoolinglen = grade.getSchoolinglen();

            // 取得年级级别
            int gradeInt = grade.getGradeInt(curAcadyear);

            // 如果年级级别大于了学制则不要再添加
            if (gradeInt > schoolinglen) {
                continue;
            }
            if (StringUtils.isNotBlank(grade.getId())) {
            	Grade grade_=gradeDao.getGrade(grade.getId());
            	if(grade_ == null){
            		continue;
            	}
            	grade.setGradename(grade_.getGradename());
            	grade.setSubschoolId(grade_.getSubschoolId());//zengzt  sql语法班级字段没有直接再这里给值
			}
            
            // 得到年级名称
            //ClassNameFactory.getInstance().setGradeDyn(grade, curAcadyear);
            grade.setGrade(gradeInt);

            // 添加到新表列中
            rtnList.add(grade);
        }

        return rtnList;
    }

    public List<Grade> getGrades(String schoolId) {
        return getGrades(schoolId, null);
    }

    public List<Grade> getGrades(String schoolId, String curAcadyear) {
        List<Grade> list = null;
        if (curAcadyear != null) {
            list = basicClassDao.getGrades(schoolId, curAcadyear);
        } else {
            list = basicClassDao.getGrades(schoolId);
        }
        return buildGradeName(schoolId, list, curAcadyear);
    }

    public List<Grade> getGraduatingGrades(String schoolId, String graduateAcadyear) {
        List<Grade> list = basicClassDao.getGraduatingGrades(schoolId, graduateAcadyear);
        return buildGradeName(schoolId, list, graduateAcadyear);
    }

    public List<Grade> getGradesByHeadTeacherId(String schoolId, String teacherId) {
        List<Grade> list = basicClassDao.getGradesByTeacherId(schoolId, teacherId);
        return buildGradeName(schoolId, list, null);
    }

    public Map<String, Grade> getGradeMap(String schoolId) {
        List<Grade> gradeList = getGrades(schoolId);
        Map<String, Grade> gradeMap = new HashMap<String, Grade>();
        for (Grade grade : gradeList) {
            gradeMap.put(String.valueOf(grade.getSection()) + String.valueOf(grade.getGrade()),
                    grade);
        }
        return gradeMap;
    }

    // ======================根据年级查询班级=======================
    public List<BasicClass> getClassesByGrade(Grade grade) {
        return getClassesByGrade(grade, null);
    }

    public List<BasicClass> getClassesByGrade(Grade grade, String curAcadyear) {
        List<BasicClass> listClass = null;
        if (grade.getSubschoolId() == null) {
            listClass = basicClassDao.getClassesByGrade(grade.getSchid(), grade.getSection(), grade
                    .getAcadyear(), grade.getSchoolinglen());
        } else {
            listClass = basicClassDao.getClassesByGrade(grade.getSchid(), grade.getSubschoolId(),
                    grade.getSection(), grade.getAcadyear(), grade.getSchoolinglen());
        }

        if (curAcadyear == null) {
            return ClassNameFactory.getInstance().buildClassDyn(listClass);
        } else {
            return ClassNameFactory.getInstance().buildClassDyn(listClass, curAcadyear);
        }
    }

    // ======================根据同类级查询班级=======================

    public List<KinClass> getKinClass(String schoolId) {
        // 取同类班级相关字段信息
        List<KinClass> list = basicClassDao.getKinClasses(schoolId);

        // 当前学年
        int endyear = 0;
        String currentAcadyear = semesterService.getCurrentAcadyear();
        endyear = Integer.parseInt(currentAcadyear.substring(5));


        // 取文理类型微代码
        Map<String, Map<String, String>> detailMap = mcodedetailService
                .getContent2Map(new String[] { "DM-BJWLLX" });

        List<KinClass> listKinClass = new ArrayList<KinClass>();
        for (Iterator<KinClass> it = list.iterator(); it.hasNext();) {
            KinClass kinCls = it.next();

            int section = kinCls.getSection();
            String acadyear = kinCls.getAcadyear();
            int schoolinglen = kinCls.getSchoolinglen();
            String artsciencetype = kinCls.getType();

            // 取得年级级别
            int grade = 0;
            int startyear = Integer.parseInt(acadyear.substring(0, 4));
            grade = endyear - startyear;
      
            // 如果年级级别大于了学制则不要再添加
            if (grade > schoolinglen) {
                continue;
            }
            kinCls.setGrade(grade);

            // 得到年级名称
            String gradeName = ClassNameFactory.getInstance().getGradeNameDyn(schoolId,section, grade);
            kinCls.setGradename(gradeName);

            // 同类班级代码、名称
            kinCls.setKinClassCode(section + String.valueOf(grade) + artsciencetype);
            kinCls.setKinClassName(gradeName + detailMap.get("DM-BJWLLX").get(artsciencetype));

            // 添加到新表列中
            listKinClass.add(kinCls);
        }

        return listKinClass;
    }

    public List<String> getClassType(String schoolId, int section, String acadyear) {
        return basicClassDao.getClassType(schoolId, section, acadyear);
    }

    public List<BasicClass> getClassesByKinClass(String schoolId, int section, String enrollyear,
            String artScienceType, String curAcadyear) {
        List<BasicClass> entityList = basicClassDao.getClassesByKinClass(schoolId, section,
                enrollyear, artScienceType);
        return ClassNameFactory.getInstance().buildClassDyn(entityList, curAcadyear);
    }

    public List<BasicClass> getClassesByKinClass(Grade grade, String artScienceType) {
        List<BasicClass> listClass = null;
        if (grade.getSubschoolId() == null) {
            listClass = basicClassDao.getClassesByKinClass(grade.getSchid(), grade.getSection(),
                    grade.getAcadyear(), grade.getSchoolinglen(), artScienceType);
        } else {
            listClass = basicClassDao.getClassesByKinClass(grade.getSchid(),
                    grade.getSubschoolId(), grade.getSection(), grade.getAcadyear(), grade
                            .getSchoolinglen(), artScienceType);
        }

        String curAcadyear = (Integer.parseInt(grade.getAcadyear().split("-")[0])
                + grade.getGrade() - 1)
                + "-"
                + (Integer.parseInt(grade.getAcadyear().split("-")[1]) + grade.getGrade() - 1);
        return this.setClassNameDyn(listClass, curAcadyear);

    }

    public List<BasicClass> getClassesByKinClass(String schoolId, String kinClassCode) {
        String currentAcadyear = semesterService.getCurrentAcadyear();// 取当前学年

        int section = Integer.parseInt(kinClassCode.substring(0, 1)); // 学段
        int grade = Integer.parseInt(kinClassCode.substring(1, 2)); // 年级
        String type = kinClassCode.substring(2, 3); // 班级文理类型
        int schoolinglen = schoolService.getSchoolingLen(schoolId, section); // 学制

        int currentAcadyearEnd = Integer.parseInt(currentAcadyear.substring(5));
        String acadyearStart = String.valueOf(currentAcadyearEnd - grade);
        String acadyearEnd = String.valueOf(Integer.parseInt(acadyearStart) + 1);
        String acadyear = acadyearStart + "-" + acadyearEnd; // 入学学年

        List<BasicClass> classList = basicClassDao.getClassesByKinClass(schoolId, section,
                acadyear, schoolinglen, type);
        return classList;
    }

    // ======================Map=======================
    public Map<String, BasicClass> getClassMap(String schoolId) {
        List<BasicClass> entityList = getClasses(schoolId);
        Map<String, BasicClass> map = ClassNameFactory.getInstance().buildClassDynMap(entityList);
        return map;
    }

    public Map<String, BasicClass> getClassMap(String schoolId, String curAcadyear) {
        List<BasicClass> dtoList = getClasses(schoolId, curAcadyear);
        Map<String, BasicClass> map = new HashMap<String, BasicClass>();
        for (BasicClass cls : dtoList) {
            map.put(cls.getId(), cls);
        }
        return map;
    }

    public Map<String, String> getClassNameMap(String schoolId) {
        List<BasicClass> entityList = getClasses(schoolId);
        Map<String, String> map = ClassNameFactory.getInstance().buildClassNameDynMap(schoolId,
                entityList);
        return map;
    }

    public Map<String, String> getGraduateYearMap(String schoolId) {
        return basicClassDao.getGraduateYearMap(schoolId);
    }

    public Map<String, BasicClass> getGradeNameByUnitivecode(String[] unitivecode) {
        return basicClassDao.getClassMapByUnitivecodes(unitivecode);
    }

    // ======================自定义报表使用=======================
    public List<String[]> getGradeNames(int unitClass, String unitId) {
        List<String[]> list = new ArrayList<String[]>();

        if (Unit.UNIT_CLASS_SCHOOL == unitClass) {
            List<Grade> gradeList = this.getGrades(unitId);
            for (Grade grade : gradeList) {
                list.add(new String[] { grade.getSection() + String.valueOf(grade.getGrade()),
                        grade.getGradename() });
            }
        } else {
            // 取教育局学制微代码信息
            ClassNameFactory gn = ClassNameFactory.getInstance();
            List<Mcodedetail> mcodeXZList = mcodedetailService.getMcodeDetails("DM-JYJXZ");
            for (Iterator<Mcodedetail> iter = mcodeXZList.iterator(); iter.hasNext();) {
                Mcodedetail detail = (Mcodedetail) iter.next();
                int section = Integer.parseInt(detail.getThisId());
                int nz = Integer.parseInt(detail.getContent());// 年制
                for (int i = 0; i < nz; i++) {
                    int grade = i + 1;
                    String grdname = gn.getGradeNameDyn(null,section, grade);
                    list.add(new String[] { String.valueOf(section) + String.valueOf(grade),
                            grdname });
                }
            }
        }
        return list;
    }

    public Map<String, String> getGradeNameMap(int unitClass, String unitId) {
        Map<String, String> map = new HashMap<String, String>();
        List<String[]> list = getGradeNames(unitClass, unitId);
        for (String[] arr : list) {
            map.put(arr[0], arr[1]);
        }
        return map;
    }

    public List<String[]> getAllClassNames(String schoolId) {
        List<BasicClass> entityList = basicClassDao.getAllClasses(schoolId);
        List<String[]> list = ClassNameFactory.getInstance()
                .buildClassNameDyn(schoolId, entityList);
        return list;
    }

    public Map<String, String> getAllClassMap(String schoolId) {
        List<BasicClass> entityList = basicClassDao.getAllClasses(schoolId);
        Map<String, String> map = ClassNameFactory.getInstance().buildClassNameDynMap(schoolId,
                entityList);
        return map;
    }

    // =============私有方法 ==================================
    // 设置生成动态的班级名称
    private List<BasicClass> setClassNameDyn(String schoolId, List<BasicClass> list) {
        return ClassNameFactory.getInstance().buildClassDyn(schoolId, list);
    }

    // 设置生成动态的班级名称
    private List<BasicClass> setClassNameDyn(List<BasicClass> list) {
        return ClassNameFactory.getInstance().buildClassDyn(list);
    }

    // 设置生成动态的班级名称
    private List<BasicClass> setClassNameDyn(List<BasicClass> list, String curacadyear) {
        return ClassNameFactory.getInstance().buildClassDyn(list, curacadyear);
    }

    // 设置生成动态的班级名称
    private void setClassNameDyn(BasicClass cls) {
        String classnamedynamic = ClassNameFactory.getInstance().getClassNameDyn(cls);
        cls.setClassnamedynamic(classnamedynamic);
    }

    // 设置生成动态的班级名称
    private void setClassNameDyn(BasicClass cls, String curacadyear) {
        String classnamedynamic = ClassNameFactory.getInstance().getClassNameDyn(cls, curacadyear);
        cls.setClassnamedynamic(classnamedynamic);
    }

    public String[] getClassidByUserid(String userid, String subschoolid) {
        return basicClassDao.getClassidByUserid(userid, subschoolid);
    }

    //======================开设学年===============
    @Override
    public List<BasicClass> getClassesByOpenAcadyear(String schoolId, String openAcadyear) {        
    	List<BasicClass> listClass = basicClassDao.getClassesByOpenAcadyear(schoolId, openAcadyear);
        return ClassNameFactory.getInstance().buildClassDyn(listClass);
    }

	@Override
	public List<BasicClass> getClassesBySchoolIdGradeId(String schoolId,
			String gradeId) {
		List<BasicClass> classList=basicClassDao.getClassesBySchoolIdGradeId(schoolId, gradeId);
		//Map<String, BasicClass> classMap=new HashMap<String, BasicClass>();
		//for (BasicClass basicClass : classList) {
		//	if (!classMap.containsKey(basicClass.getId())) {
		//		classMap.put(basicClass.getId(), basicClass);
		//	}
		//}
		return ClassNameFactory.getInstance().buildClassDyn(classList);
	}

    @Override
    public List<BasicClass> getClassesByGrade(String gradeId) {
        List<BasicClass> listClass = basicClassDao.getClassesByGradeId(gradeId);
        return ClassNameFactory.getInstance().buildClassDyn(listClass);
    }

	@Override
	public void updateGraduateSign(String[] classIds, int sign) {
		basicClassDao.updateGraduateSign(classIds, sign);
	}

	@Override
	public List<Grade> getNotGraduatingGrades(String schoolId, String acadyear) {
		 List<Grade> list = basicClassDao.getNotGraduatingGrades(schoolId, acadyear);
	     return buildGradeName(schoolId, list, acadyear);
	}
	
	@Override
    public List<BasicClass> getClassesByGradeAll(String gradeId) {
        List<BasicClass> listClass = basicClassDao.getClassesByGradeIdAll(gradeId);
        return ClassNameFactory.getInstance().buildClassDyn(listClass);
    }

	@Override
    public List<BasicClass> getClassesByGradeIds(String schId, String[] gradeIds) {
        List<BasicClass> listClass = basicClassDao.getClassesByGradeIds(schId, gradeIds);
        return ClassNameFactory.getInstance().buildClassDyn(listClass);
    }
	
	@Override
	public List<BasicClass> getClassesByGradeId(String schoolId,
			String gradeId, String teacherId) {
		List<BasicClass> listClass = null;
		if(StringUtils.isNotBlank(teacherId)) {
			 listClass = basicClassDao.getClassesByGradeId(schoolId, gradeId, teacherId);
		}else {
			listClass = basicClassDao.getClassesByGradeId(gradeId);
		}
		return ClassNameFactory.getInstance().buildClassDyn(listClass);
	}
	
	@Override
	public List<BasicClass> getClassesByTeacherIdAll(String schoolId, String teacherId) {
		return basicClassDao.getClassesByTeacherIdAll(schoolId, teacherId);
	}

	@Override
	public Map<String, BasicClass> getClassMapWithDeleted(String[] classIds) {
		Map<String, BasicClass> classMapWithDeleted = basicClassDao.getClassMapWithDeleted(classIds);
		List<BasicClass> listClass = new ArrayList<BasicClass>();
		for(Map.Entry<String, BasicClass> entry:classMapWithDeleted.entrySet()){
			listClass.add(entry.getValue());
		}
		return ClassNameFactory.getInstance().buildClassDynMap(listClass);
	}
	
}
