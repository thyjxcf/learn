package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.data.entity.BasicClassModelDto;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.util.ClassNameFactory;
import net.zdsoft.eis.frame.action.BaseSemesterAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.FieldErrorException;
import net.zdsoft.leadin.util.HtmlUtil;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: BasicClassAdminAction.java,v 1.25 2007/01/22 07:35:42 zhanghh Exp $
 */
public class BasicClassAdminAction extends BaseSemesterAction implements ModelDriven<Object> {
    private static final long serialVersionUID = 1L;

    // 与页面交互的班级ModelDto
    protected BasicClassModelDto basicClassModelDto = new BasicClassModelDto();

    // service
    private BaseGradeService baseGradeService;
    protected BaseClassService baseClassService;
    protected BaseSchoolService baseSchoolService;
    protected StudentService studentService;
    protected McodeService mcodeService;
    protected TeacherService teacherService;

    private List<Grade> gradeList;

    // 学校中所该有的学段，当前学期DTO
    private List<String[]> sectionList;
    private Map<String, Mcode> poolMap = new HashMap<String, Mcode>();

    private String showAllClass;
    private String[] retStr;
    private String acadyearchange; 
    private String schidchange;
    private int sectionchange;
    private String gradeidchange;
    private TeachPlaceService teachPlaceService;
    // 从页面传来的值若在DTO的属性中，则被此DTO封装
    public Object getModel() {
        return basicClassModelDto;
    }

    // 年级，班级列表
    public String execute() throws Exception {
        String schid = basicClassModelDto.getSchid();
        if (schid == null || "".equals(schid)) {
            schid = this.getLoginInfo().getUnitID();
            if (schid == null || "".equals(schid)) {
                this.addActionError("没有取到单位（学校）Id编号！");
                return ERROR;
            }
        }
        basicClassModelDto.setSchid(schid);

        // 检查前提信息有没有设置
        String ret = this.preAdd(schid);
        if (PROMPTMSG.equals(ret)) {
            return PROMPTMSG;
        }
        // 年级、班级列表
        List<Grade> gradeList = baseGradeService.getGrades(schid);
        if (CollectionUtils.isNotEmpty(gradeList)) {
        	Collections.sort(gradeList, new Comparator<Grade>() {
        		public int compare(Grade o1, Grade o2) {
        			String str1 = (4 - o2.getSection()) + o2.getAcadyear();
        			String str2 = (4 - o1.getSection()) + o1.getAcadyear();
        			
        			return str1.compareTo(str2);
        		}
        	});
        }
         
        // 设置年级组长
        Map<String, Grade> mapOfGradeByKey = baseGradeService.getGradeMap(schid);
        Grade basicGradeFromMap;
        for (Grade basicGradeDto : gradeList) {
            basicGradeFromMap = mapOfGradeByKey.get(basicGradeDto.getSchid()
                    + basicGradeDto.getAcadyear() + basicGradeDto.getSection()
                    + basicGradeDto.getSchoolinglen());
            if (basicGradeFromMap != null) {
                basicGradeDto.setTeacherId(basicGradeFromMap.getTeacherId());
            }
        }

        List<BasicClass> classList = new ArrayList<BasicClass>();
        String gradeid = basicClassModelDto.getGradeid();
//        if (StringUtils.isNotBlank(gradeid)) {
//            StringTokenizer st = new StringTokenizer(gradeid, "|");
//            if (st.countTokens() > 0) {
//                String[] array = new String[st.countTokens()];
//
//                for (int i = 0; i < array.length; i++) {
//                    array[i] = st.nextToken();
//                }
//                int sec = Integer.parseInt(array[0]);
//                int len = Integer.parseInt(array[2]);
//                classList = baseClassService.getClasses(schid, sec, array[1], len);
//            }
//        } else if (classList.size() == 0) {
//            classList = baseClassService.getClasses(schid, -1, null);
//        }
        if (CollectionUtils.isNotEmpty(gradeList) && StringUtils.isBlank(gradeid)) {
        	gradeid = gradeList.get(0).getId();
            basicClassModelDto.setGradeid(gradeid);
        }
        if (StringUtils.isNotBlank(gradeid)) {
            classList = baseClassService.getClassesByGrade(gradeid);
        }else {
            classList = baseClassService.getClasses(schid, -1, null);
        }
        if(StringUtils.isNotBlank(showAllClass)){
        	classList = baseClassService.getClasses(schid, -1, null);
        }
        // 设置每个班级的学生数
        setstuCount(classList);
        // 为班级设置所关联的学生即班长
        Set<String> studentIds = new HashSet<String>();
        for (BasicClass baseClass : classList) {
            studentIds.add(baseClass.getStuid());
        }
        Map<String, Student> studentMap;
        if (CollectionUtils.isNotEmpty(studentIds)) 
        	studentMap = studentService.getStudentMap(studentIds.toArray(new String[0]));
        else 
        	studentMap = new HashMap<String, Student>();
        for (BasicClass baseClass : classList) {
            String stuId = baseClass.getStuid();
            Student monitor = studentMap.get(stuId);
            if (monitor != null)
                baseClass.setMonitor(monitor.getStuname());
        }

        // 学段，班级类型，文理类型（先从Map池中取，若没有则新建）
        Mcode sectionMcode = poolMap.get("sectionMcode");
        if (sectionMcode == null) {
            sectionMcode = mcodeService.getMcode("DM-RKXD");
            poolMap.put("sectionMcode", sectionMcode);
        }
        Map<String, String> sectionMap = sectionMcode.getCodeMap();

        Mcode classtypeMcode = poolMap.get("classtypeMcode");
        if (classtypeMcode == null) {
            classtypeMcode = mcodeService.getMcode("DM-BJLX");
            poolMap.put("classtypeMcode", classtypeMcode);
        }
        Map<String, String> classtypeMap = classtypeMcode.getCodeMap();

        Mcode artsciencetypeMcode = poolMap.get("artsciencetypeMcode");
        if (artsciencetypeMcode == null) {
            artsciencetypeMcode = mcodeService.getMcode("DM-BJWLLX");
            poolMap.put("artsciencetypeMcode", artsciencetypeMcode);
        }
        Map<String, String> artsciencetypeMap = artsciencetypeMcode.getCodeMap();

        // 将班主任ID转换成姓名在页面上显示的String[id,name]列表list
        List<String[]> teacheridList = getTeacherListHtml(schid);

        basicClassModelDto.setGradeList(gradeList);
        basicClassModelDto.setClassList(classList);
        basicClassModelDto.setSectionMap(sectionMap);
        basicClassModelDto.setClasstypeMap(classtypeMap);
        basicClassModelDto.setArtsciencetypeMap(artsciencetypeMap);
        basicClassModelDto.setTeacheridList(teacheridList);
        basicClassModelDto.setSchid(schid);
        return SUCCESS;
    }

    private void setstuCount(List<BasicClass> list) {
        String[] classIds = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            BasicClass cls = (BasicClass) list.get(i);
            classIds[i] = cls.getId();
        }
        Map<String, Integer> map = studentService.getStudentCountMap(classIds);
        Integer count;
        for (int i = 0; i < list.size(); i++) {
            BasicClass cls = (BasicClass) list.get(i);
            count = map.get(cls.getId());
            if (null != count) {
                cls.setStucount(count);
            }
        }
    }

    private List<String[]> getTeacherListHtml(String unitid) {
        List<Teacher> list = teacherService.getTeachers(unitid);
        ArrayList<String[]> htmlList = new ArrayList<String[]>();
        for (Iterator<Teacher> it = list.iterator(); it.hasNext();) {
            Teacher teacher = (Teacher) it.next();
            htmlList.add(new String[] { teacher.getId(), teacher.getName() });
        }
        return htmlList;
    }

    // 新增班级
    public String add() throws Exception {
        String schid =  this.getLoginInfo().getUnitID();
        basicClassModelDto.setSchid(schid);
        //basicClassModelDto.setGradeid("");
        String ret = this.preAdd(schid);
        if ("success".equals(ret)) {
            // 初始化页面显示数据
            iniModel(schid);
            return SUCCESS;
        } else {
            return PROMPTMSG;
        }
    }

    // 批量新增班级
    public String batchAdd() throws Exception {
        String schid = basicClassModelDto.getSchid();
        if (schid == null || "".equals(schid)) {
            schid = this.getLoginInfo().getUnitID();
            if (schid == null || "".equals(schid)) {
                this.addActionError("没有取到单位（学校）Id编号！");
                return ERROR;
            }
        }
        basicClassModelDto.setSchid(schid);
        String ret = this.preAdd(schid);
        if ("success".equals(ret)) {
            // 初始化页面显示数据
            iniModel(schid);
            return SUCCESS;
        } else {
            return PROMPTMSG;
        }
    }

    // 编辑班级
    public String edit() throws Exception {
        String classid = basicClassModelDto.getId();
        BasicClass tempDto = baseClassService.getClass(classid);
        if (StringUtils.isNotEmpty(tempDto.getStuid())) {
            Student stu = studentService.getStudent(tempDto.getStuid());
            tempDto.setMonitor(stu.getStuname());
        }
        Grade ent = baseGradeService.getGrade(tempDto.getGradeId());
        // DtoFactory.copyToDTOFromDTO(tempDto, basicClassModelDto);
        BasicClassModelDto.dtoToModelDto(tempDto, basicClassModelDto);
        //班级名称前缀
        basicClassModelDto.setPreClassname(ent.getGradename());
        basicClassModelDto.setTeachPlaceId(tempDto.getTeachPlaceId());
        List<String> listOfTeacherId = new ArrayList<String>();
        String teacherId = basicClassModelDto.getTeacherid();
        String viceTeacherId = basicClassModelDto.getViceTeacherId();
        if (teacherId != null)
            listOfTeacherId.add(teacherId);
        if (viceTeacherId != null)
            listOfTeacherId.add(viceTeacherId);
        Map<String, Teacher> mapOfTeacher;
        if (listOfTeacherId.size() > 0) {
            mapOfTeacher = teacherService.getTeacherMap(listOfTeacherId.toArray(new String[0]));
            if (mapOfTeacher != null && mapOfTeacher.size() > 0) {
                Teacher empDto =  mapOfTeacher.get(teacherId);
                if (empDto != null)
                    basicClassModelDto.setTeachername(empDto.getName());
                empDto = mapOfTeacher.get(viceTeacherId);
                if (empDto != null)
                    basicClassModelDto.setViceTeacherName(empDto.getName());
            }
        }

        // 初始化页面显示数据
        String schid = basicClassModelDto.getSchid();
        if (schid == null || "".equals(schid)) {
            schid = this.getLoginInfo().getUnitID();
            if (schid == null || "".equals(schid)) {
                this.addActionError("没有取到单位（学校）Id编号！");
                return ERROR;
            }
        }
        basicClassModelDto.setSchid(schid);
        if(StringUtils.isNotBlank(basicClassModelDto.getTeachPlaceId())){
        	TeachPlace teachPlace = teachPlaceService.getTeachPlace(basicClassModelDto.getTeachPlaceId());
        	basicClassModelDto.setTeachPlaceName(teachPlace.getPlaceName());
        }

        String rtn = this.preAdd(schid);
        if ("success".equals(rtn)) {
            // 初始化页面显示数据
            iniModel(schid);
            return SUCCESS;
        } else {
            return PROMPTMSG;
        }
    }

    // 删除班级（软删除）
    public String delete() throws Exception {
        if (basicClassModelDto.getCheckid() != null && basicClassModelDto.getCheckid().length > 0) {
            // 返回一个PormptMessageDto
            String schid = basicClassModelDto.getSchid();
            if (schid == null || "".equals(schid)) {
                schid = this.getLoginInfo().getUnitID();
            }
            basicClassModelDto.setSchid(schid);
            try {//界面操作 是否发送消息默认传true
                baseClassService.deleteClasses(basicClassModelDto.getCheckid());
            } catch (BusinessErrorException e) {
                promptMessageDto.setPromptMessage(e.getMessage());
                promptMessageDto.setOperateSuccess(false);
                return SUCCESS;
            } catch (Exception e) {
                promptMessageDto.setPromptMessage(e.getMessage());
                promptMessageDto.setOperateSuccess(false);
                return SUCCESS;
            }
            promptMessageDto.setPromptMessage("成功删除！");
            promptMessageDto.setOperateSuccess(true);
        }
        return SUCCESS;
    }

    // 保存班级
    public String save() throws Exception {
        String schid = basicClassModelDto.getSchid();
        if (schid == null || "".equals(schid)) {
            schid = this.getLoginInfo().getUnitID();
            basicClassModelDto.setSchid(schid);
        }
        // 返回一个PormptMessageDto
        Grade grade = baseGradeService.getGrade(schid, basicClassModelDto
                .getAcadyear(), basicClassModelDto.getSection());
        String gradeId;
        if (grade == null) {
            gradeId = UUIDGenerator.getUUID();
            Grade basegrade = new Grade();
            basegrade.setId(gradeId);
            basegrade.setAcadyear(basicClassModelDto.getAcadyear());
            basegrade.setAmLessonCount(4);
            basegrade.setCreationTime(new Date());
            basegrade.setIsdeleted(false);
            basegrade.setIsGraduated(0);
            basegrade.setNightLessonCount(0);
            basegrade.setPmLessonCount(4);
            basegrade.setSchid(basicClassModelDto.getSchid());
            basegrade.setSchoolinglen(basicClassModelDto.getSchoolinglen());
            basegrade.setSection(basicClassModelDto.getSection());
            basegrade.setTeacherId(null);
                
            ClassNameFactory.getInstance().setGradeDyn(basegrade);
            baseGradeService.saveGrade(basegrade);
        } else {
            gradeId = grade.getId();
        }
        basicClassModelDto.setGradeId(gradeId);
        try {
            baseClassService.saveClass((BasicClass) basicClassModelDto);
        } catch (FieldErrorException e) {
            //this.addFieldError(e.getField(), e.getMessage());
            // 初始化页面显示数据
            // iniModel(schid);
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setPromptMessage("保存班级失败:"+e.getMessage());
            return SUCCESS;
        } catch (Exception e) {
            //this.addActionError("保存班级失败:"+e.getMessage());
            // 初始化页面显示数据
            // iniModel(schid);
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setPromptMessage("保存班级失败:"+e.getMessage());
            return SUCCESS;
        }
        promptMessageDto.setPromptMessage("班级保存成功！");
        promptMessageDto.setOperateSuccess(true);
        /**
        promptMessageDto.addOperation(new String[] { "返回", "basicClassAdmin.action" });
        promptMessageDto.addHiddenText(new String[] { "gradeid", basicClassModelDto.getGradeid() });
        if (classid == null || "".equals(classid)) {
            // 如果是新增班级
            promptMessageDto.addOperation(new String[] { "新增下一个", "basicClassAdmin-Add.action" });
            promptMessageDto.addHiddenText(new String[] { "schid", basicClassModelDto.getSchid() });
            promptMessageDto.addHiddenText(new String[] { "section",
                    String.valueOf(basicClassModelDto.getSection()) });
            promptMessageDto.addHiddenText(new String[] { "acadyear",
                    basicClassModelDto.getAcadyear() });
            promptMessageDto.addHiddenText(new String[] { "schoolinglen",
                    "" + basicClassModelDto.getSchoolinglen() });
            promptMessageDto.addHiddenText(new String[] { "classtype",
                    basicClassModelDto.getClasstype() });
            promptMessageDto.addHiddenText(new String[] { "artsciencetype",
                    String.valueOf(basicClassModelDto.getArtsciencetype()) });
            promptMessageDto.addHiddenText(new String[] { "datecreated",
                    DateUtils.date2StringByDay(basicClassModelDto.getGraduatedate()) });
            // promptMessageDto.addHiddenText(new String[] { "teacherid",
            // basicClassModelDto.getTeacherid() });
            promptMessageDto.addHiddenText(new String[] { "subschoolid",
                    basicClassModelDto.getSubschoolid() });
        }**/
        return SUCCESS;
    }

    // 批量新增班级确认
    public String confirm() throws Exception {
        // 初始化页面显示数据
        String schid = basicClassModelDto.getSchid();
        if (schid == null || "".equals(schid)) {
            schid = this.getLoginInfo().getUnitID();
        }
        basicClassModelDto.setSchid(schid);
        // 返回一个PromptMessageDto
        Grade grade = baseGradeService.getGrade(basicClassModelDto.getSchid(), basicClassModelDto
                .getAcadyear(), basicClassModelDto.getSection());
        String gradeId;
        if (grade == null) {
            gradeId = UUIDGenerator.getUUID();

            Grade basegrade = new Grade();
            basegrade.setAcadyear(basicClassModelDto.getAcadyear());
            basegrade.setAmLessonCount(4);
            basegrade.setCreationTime(new Date());          
            basegrade.setId(gradeId);
            basegrade.setIsdeleted(false);
            basegrade.setIsGraduated(0);
            basegrade.setNightLessonCount(0);
            basegrade.setPmLessonCount(4);
            basegrade.setSchid(basicClassModelDto.getSchid());
            basegrade.setSchoolinglen(basicClassModelDto.getSchoolinglen());
            basegrade.setSection(basicClassModelDto.getSection());
            basegrade.setTeacherId(null);
            //动态产生年级名字
            ClassNameFactory.getInstance().setGradeDyn(basegrade);
            baseGradeService.saveGrade(basegrade);
        } else {
            gradeId = grade.getId();
        }
        basicClassModelDto.setGradeId(gradeId);
        try {
            baseClassService.createClassBatch(basicClassModelDto);
        } catch (Exception e) {
            // 若不是成功的
            promptMessageDto.setPromptMessage(e.getMessage());
            promptMessageDto.setOperateSuccess(false);
            // 在同一页面显示错误
            //this.addActionError(promptMessageDto.getPromptMessage());
            //iniModel(schid);
            return SUCCESS;
        }
        promptMessageDto.setPromptMessage("批量增加班级保存成功！");
        promptMessageDto.setOperateSuccess(true);
        //promptMessageDto.addOperation(new String[] { "返回", "basicClassAdmin.action" });
        //promptMessageDto.addHiddenText(new String[] { "gradeid", basicClassModelDto.getGradeid() });
        return SUCCESS;
    }

    /*
     * 当页面中改变学段时，变化的数据：年级，班级代码，班级名称的前半部分，班级类型，学制 
     * String schid, int section, String acadyear
     */
    public String doChangeSection() {
        retStr = new String[7];
        /*
         * 得到入学学年的下拉框（值是入学学年，显示是年级）
         */
        // 学制
        int _schoolinglen = getSchoolinglen(schidchange, sectionchange);
        // 当前学年(格式：2005-2006)
        String _currentAcadyear = semesterService.getCurrentAcadyear();
        // 当“（当前学年-入学学年）>学制” 时，入学学年赋值为当前学年
        int endyear = Integer.parseInt(_currentAcadyear.substring(5));
        int startyear = Integer.parseInt(acadyearchange.substring(0, 4));
        int grade = endyear - startyear;
        if (grade > _schoolinglen)
            acadyearchange = _currentAcadyear;

        // 入学学年
        String _acadyearHtml = HtmlUtil.getSelectHtmlTag("", getAcadyearGradeHtml(sectionchange,
                _schoolinglen, _currentAcadyear), false);
//        Grade entity = baseGradeService.getGrade(schidchange, acadyearchange, sectionchange);
//        String _acadyearHtml = HtmlUtil.getSelectHtmlTag(entity.getId(), getAcadyearGradeHtml(schidchange, sectionchange), false);
        
        retStr[0] = _acadyearHtml;

        /*
         * 班级代码
         */
//        String _classcode = baseClassService.getNextClassCodeWithGraduatedYear(schidchange, sectionchange,
//        		acadyearchange);
        String _classcode = baseClassService.getNextClassCode(schidchange, "" + sectionchange, acadyearchange, _schoolinglen);
        retStr[1] = _classcode;

        /*
         * 班级名称
         */
        // 将数值型字符串转换为数字，从右边开始转换
        long code = baseClassService.convertStrToNumRight(_classcode
                .substring(_classcode.length() - 2));
        boolean useKh = systemIniService.getBooleanValue(BasicClass.CLASSNAME_USE_BRACKETS);
        String _classname = "";
        if(useKh)
        	 _classname = "("
                + org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0") + ")班";
        else
        	_classname = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0") + "班";
        retStr[2] = _classname;

        /*
         * 班级名称前缀
         */
        // String _preClassname = ClassNameFactory.getInstance().getClassNameDyn(
        // null, acadyear, section, schid);
//        String _preClassname = ClassNameFactory.getInstance().getClassNameDyn(schidchange, acadyearchange,
//        		sectionchange, _schoolinglen, null);
        //retStr[3] = _preClassname;

        /*
         * 班级类型下拉框
         */
        String key = "classtypeMcode_" + sectionchange;
        Mcode classtypeMcode = poolMap.get(key);
        if (classtypeMcode == null) {
            classtypeMcode = mcodeService.getMcodeFaintness("DM-BJLX", String.valueOf(sectionchange));
            poolMap.put("classtypeMcode", classtypeMcode);
        }
        String _classtypeHtml = classtypeMcode.getHtmlTag(basicClassModelDto.getClasstype());
        retStr[4] = _classtypeHtml;

        List<Grade> gradeListinfos = baseGradeService.getGrades(schidchange, sectionchange);
        if(CollectionUtils.isNotEmpty(gradeListinfos)){
//        	Grade entityGreade = baseGradeService.getGrade(schidchange, acadyearchange, sectionchange);
        	Grade entityGreade = gradeListinfos.get(0);
        	if(entityGreade!=null){
        		//年纪名称
        		retStr[3] = entityGreade.getGradename();
        		// 学制
        		retStr[5] = "" + entityGreade.getSchoolinglen();
        		retStr[6] = "" + entityGreade.getId();
        	}
        }
        	
        return SUCCESS;
    }

    public int getSchoolinglen(String schid, int section) {
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

    /*
     * 当页面中改变年级（即入学学年）时，变化的数据：班级代码，班级名称的前半部分 用buffalo访问该方法
     */
    public String doChangeAcadyear() {
        retStr = new String[5];
        /*
         * 班级代码
         */
        Grade entityGreade = baseGradeService.getGrade(schidchange, acadyearchange, sectionchange);
        String _classcode = baseClassService.getNextClassCode(schidchange, "" + sectionchange, entityGreade.getAcadyear(), entityGreade.getSchoolinglen());
//        String _classcode = baseClassService.getNextClassCodeWithGraduatedYear(schidchange, sectionchange,
//                acadyearchange);
        retStr[0] = _classcode;
        /*
         * 班级名称
         */
        // 将数值型字符串转换为数字，从右边开始转换
        long code = baseClassService.convertStrToNumRight(_classcode
                .substring(_classcode.length() - 2));
        boolean useKh = systemIniService.getBooleanValue(BasicClass.CLASSNAME_USE_BRACKETS);
        String _classname = "";
        if(useKh)
        	_classname = "("
                + org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0") + ")班";
        else
        	_classname = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0") + "班";
        retStr[1] = _classname;
        /*
         * 班级名称前缀
         */
//        String _preClassname = ClassNameFactory.getInstance().getClassNameDyn(schidchange, acadyearchange,
//                sectionchange);
        
        if(entityGreade!=null){
        	retStr[2] = entityGreade.getGradename();
        	retStr[3] = "" + entityGreade.getId();
        	retStr[4] = "" +entityGreade.getSchoolinglen();
        }
        
        return SUCCESS;
    }

    /*
     * 当页面中改变年制时，变化的数据：年级下拉框 用buffalo访问该方法
     */
    public String[] doChangeSchoolinglen(String schid, int section, String acadyear, String len) {
        String[] rtnStr = new String[4];
        int schoolinglen = Integer.parseInt(len);
        /*
         * 得到入学学年的下拉框（值是入学学年，显示是年级）
         */
        // 当前学年(格式：2005-2006)
        String _currentAcadyear = semesterService.getCurrentAcadyear();
        // 当“（当前学年-入学学年）>学制” 时，入学学年赋值为当前学年
        int endyear = Integer.parseInt(_currentAcadyear.substring(5));
        int startyear = Integer.parseInt(acadyear.substring(0, 4));
        int grade = endyear - startyear;
        if (grade > schoolinglen)
            acadyear = _currentAcadyear;

        // 入学学年
        String _acadyearHtml = HtmlUtil.getSelectHtmlTag(acadyear, getAcadyearGradeHtml(section,
                schoolinglen, _currentAcadyear), false);
        rtnStr[0] = _acadyearHtml;

        /*
         * 班级代码
         */
        String _classcode = baseClassService.getNextClassCodeWithGraduatedYear(schid, section,
                acadyear);
        rtnStr[1] = _classcode;

        /*
         * 班级名称
         */
        // 将数值型字符串转换为数字，从右边开始转换
        long code = baseClassService.convertStrToNumRight(_classcode
                .substring(_classcode.length() - 3));
        boolean useKh = systemIniService.getBooleanValue(BasicClass.CLASSNAME_USE_BRACKETS);
        String _classname = "";
        if(useKh)
        	_classname = "(" + org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0") + ")班";
        else
        	_classname = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0") + "班";
        rtnStr[2] = _classname;

        /*
         * 班级名称前缀
         */
        String _preClassname = ClassNameFactory.getInstance().getClassNameDyn(schid, acadyear,
                section);
        rtnStr[3] = _preClassname;

        return rtnStr;
    }

//    private List<String[]> getAcadyearGradeHtml(String schoolId, int section) {
//        List<Grade> grades = baseGradeService.getGrades(schoolId, section);
//        List<String[]> list = new ArrayList<String[]>();
//        for (Grade grade : grades) {
//            list.add(new String[] { grade.getId(), grade.getGradename(), grade.getAcadyear() });
//        }
//        return list;
//    }
    private List<String[]> getAcadyearGradeHtml(int section, int schoolinglen,
            String currentAcadyear) {
    	List<String[]> list = new ArrayList<String[]>();
        String schoolId = getLoginInfo().getUnitID();
//        Map<String, Grade> gradeMap = baseGradeService.getGradeMap(schoolId);
        List<Grade> gradeList = baseGradeService.getGrades(schoolId, section);
//        String acadyear = currentAcadyear;
        for(Grade ent :gradeList){
        	list.add(new String[] { ent.getAcadyear(), ent.getGradename() });
//        	String endYear = acadyear.substring(0, 4);
//            String startYear = String.valueOf(Integer.parseInt(endYear) - 1);
//            acadyear = startYear + "-" + endYear;
        }
        //ClassNameFactory cnf = ClassNameFactory.getInstance();
//        for (int i = 1; i <= schoolinglen; i++) {
//            //String gradeName = cnf.getGradeNameDyn(section, i);
//            
//            // 已经毕业的年级不能再增加班级
//            Grade grade = gradeMap.get(schoolId + acadyear + section
//                    + schoolinglen);
//            if (null != grade && grade.getIsGraduated() == 1)
//                continue;
//
//            //list.add(new String[] { acadyear, gradeName });
//            list.add(new String[] { acadyear, grade.getGradename() });
//            String endYear = acadyear.substring(0, 4);
//            String startYear = String.valueOf(Integer.parseInt(endYear) - 1);
//            acadyear = startYear + "-" + endYear;
//        }
        return list;
    }

    // 新增或列出班级之前的处理判断
    private String preAdd(String schid) {
        // 判断是否有学校存在,如果存在返回false,如果不存在返回true;
        //boolean flagExist = baseSchoolService.isExistSchoolCode(schid);
        School flagExist = baseSchoolService.getSchool(schid);
        if (flagExist==null ||(flagExist.getIsdeleted())) { 
            this.promptMessageDto = new PromptMessageDto();
            String msg = "未取到学校信息，请先维护学校基本信息！";

            promptMessageDto.setPromptMessage(msg);
            promptMessageDto.setOperateSuccess(false);
            // promptMessageDto.addOperation(new
            // String[]{"设置学校信息","schoolinfo.action"});
            log.error(msg);
            return PROMPTMSG;
        }
        // 判断学校信息中维护的学校类型能不能得到所该有的学段列表
        sectionList = baseSchoolService.getSchoolSections(schid);
        if (sectionList == null || sectionList.size() < 1) {
            this.promptMessageDto = new PromptMessageDto();
            String msg = "未取到有效的学校类别信息，请先维护！";
            promptMessageDto.setPromptMessage(msg);
            promptMessageDto.setOperateSuccess(false);
            // promptMessageDto.addOperation(new
            // String[]{"设置学校信息","schoolinfo.action"});
            log.error(msg);
            return PROMPTMSG;
        }

        // 判断有无当前学期数据
        String acadyear = semesterService.getCurrentAcadyear();
        if (acadyear == null) {
            this.promptMessageDto = new PromptMessageDto();
            String msg = "未取到当前学年学期信息，请先维护！";

            promptMessageDto.setPromptMessage(msg);
            promptMessageDto.setOperateSuccess(false);
            // promptMessageDto.addOperation(new
            // String[]{"维护学年学期","basicSemesterAdmin.action"});

            log.error(msg);
            return "promptmsg";
        }

        // 判断在当前学年中有没有超过年制而没有毕业的班级
        List<BasicClass> overList = baseClassService.getOverSchoolinglenClasses(schid, acadyear);
        if (overList != null && overList.size() > 0) {
            String tempAcadyear = null;
            int tempSchoolinglen = 0;
            String grayear = null;
            StringBuffer buf = new StringBuffer();

            buf.append("不能继续，存在下列班级已超过规定学制没有毕业：<br><br>");
            for (Iterator<BasicClass> it = overList.iterator(); it.hasNext();) {
                BasicClass classDto = (BasicClass) it.next();
                tempAcadyear = classDto.getAcadyear();
                tempSchoolinglen = classDto.getSchoolinglen();
                grayear = net.zdsoft.keel.util.StringUtils.calAcadyear(tempAcadyear,
                        tempSchoolinglen);

                buf.append("<font color='#FF0000'>");
                buf.append(classDto.getClassnamedynamic());
                buf.append("</font>");
                buf.append(" （入学学年：" + tempAcadyear);
                buf.append("，毕业学年：" + grayear + "）");
                buf.append("<br>");
            }
            buf.append("<br>请先做毕业结业操作！");

            this.promptMessageDto = new PromptMessageDto();
            promptMessageDto.setPromptMessage(buf.toString());
            promptMessageDto.setOperateSuccess(false);
            // promptMessageDto.addOperation(
            // new
            // String[]{"转入毕业结业",contextPath+"/stusys/sch/stugraduate/studentGraduateAdmin.action"});
            // promptMessageDto.addHiddenText(new
            // String[]{"graduateAcadyear",grayear});

            log.error("不能继续，在当前学年中还存在超过规定学制还没有毕业的班级：请先做毕业操作！");
            return PROMPTMSG;
        }
        return SUCCESS;
    }

    // 打开编辑班级页面或批量新增页面时，初始化页面显示的数
    private void iniModel(String schid) {
        // 当前学年(格式：2005-2006)
        String currentAcadyear = semesterService.getCurrentAcadyear();
        // 班级ID、学段、入学学年、学制
        String classId = basicClassModelDto.getId();
        int section = basicClassModelDto.getSection();
        String acadyear = basicClassModelDto.getAcadyear();
        int schoolinglen = basicClassModelDto.getSchoolinglen();
        Grade entity = null;
        String gradeid = basicClassModelDto.getGradeid();
        if (StringUtils.isBlank(classId)) {
            // ------------------新增或批量新增---------------------
            // 先检查当前有没有选择查看的年级，若有则给学段和入学学年赋初值
            
            if (StringUtils.isNotBlank(gradeid)) {
            	entity = baseGradeService.getGrade(gradeid);
            }else{
            	entity = baseGradeService.getGrade(schid, currentAcadyear, section);
            }
            if(entity!=null){
        		section = entity.getSection();
        		acadyear = entity.getAcadyear(); 
        		//班级名称前缀
        		basicClassModelDto.setPreClassname(entity.getGradename()); 
        	}else{
        		List<Grade> gradeListinfos = baseGradeService.getGrades(schid, section);
        		if(CollectionUtils.isNotEmpty(gradeListinfos)){
        			basicClassModelDto.setPreClassname(gradeListinfos.get(0).getGradename());
        		}
        	}
            /*
             * 学段下拉列表框
             */
            // 学段还为空是，就赋学校所拥有的第一个学段
            // 学段下拉框
            // if (section == -1)
            // section = -1;
            List<Grade> gradeList = baseGradeService.getGrades(schid);
            Set<String> sectionSet = new HashSet<String>();
            for(Grade ent : gradeList){
            	sectionSet.add(String.valueOf(ent.getSection()));
            }
            List<String[]> sectionTempList = new ArrayList<String[]>();
            if (sectionList == null || sectionList.size() == 0) {
                sectionList = baseSchoolService.getSchoolSections(schid);
            }
            boolean flag = false;
            for (Iterator<String[]> it = sectionList.iterator(); it.hasNext();) {
                String[] sections = it.next();
                //新增班级，没有的年级，不显示
                if(!sectionSet.contains(String.valueOf(sections[1])))
                	continue;
                sectionTempList.add(new String[] { sections[1], sections[2] });
                // 判断当前传入的默认section是否在列表
                if (Integer.parseInt(sections[1]) == section)
                    flag = true;
            }
            // 若不在列表中，并且section==“”时，则显示列表中第一个的section
            if ("".equals(section) || !flag) {
                section = Integer.valueOf(((String[]) sectionList.get(0))[1]);
            }

            basicClassModelDto.setSection(section);
            String sectionHtml = HtmlUtil.getSelectHtmlTag(String.valueOf(section),sectionTempList, false);
            basicClassModelDto.setSectionHtml(sectionHtml);

            /*
             * 得到入学学年的下拉框（值是入学学年，显示是年级）
             */
            // 学制（如果没有维护学制时，就以默认学制显示）
            if (schoolinglen == 0) {
                schoolinglen = getSchoolinglen(schid, section);
            }

            // 入学学年还为空时，就赋当前学年的值
            if (StringUtils.isBlank(acadyear)) {
                acadyear = currentAcadyear;
                basicClassModelDto.setAcadyear(acadyear);
            }
            List<String[]> openYearList =  getAcadyearGradeHtml(section,schoolinglen, currentAcadyear);
            String acadyearHtml = HtmlUtil.getSelectHtmlTag(acadyear, openYearList, false);
            basicClassModelDto.setAcadyearHtml(acadyearHtml);

            /*
             * 班级代码(如20060204)
             */
            String classcode = basicClassModelDto.getClasscode();
            if (classcode == null || "".equals(classcode)) {
//                classcode = baseClassService.getNextClassCodeWithGraduatedYear(schid, section,
//                        acadyear);
            	classcode = baseClassService.getNextClassCode(schid, "" + section,
                        acadyear, schoolinglen);
                basicClassModelDto.setClasscode(classcode);
            }

            /*
             * 班级名称
             */
            // 将数值型字符串转换为数字，从右边开始转换
            String classname = basicClassModelDto.getClassname();
            if (classname == null || "".equals(classname) && classcode.length() > 3) {
            	boolean useKh = systemIniService.getBooleanValue(BasicClass.CLASSNAME_USE_BRACKETS);
                long code = baseClassService.convertStrToNumRight(classcode.substring(classcode.length() - 2));
                if(useKh)
                	classname = "("
                        + org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0")
                        + ")班";
                else
                	classname = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(code), 2, "0")
                            + "班";
                basicClassModelDto.setClassname(classname);
            }

            /*
             * 建班年月
             */
            Date datecreated = basicClassModelDto.getDatecreated();
            if (datecreated == null) {
                datecreated = new Date();
                basicClassModelDto.setDatecreated(datecreated);
            }

            /*
             * 学制
             */
            String schoolinglenHtml = HtmlUtil.getSelectHtmlTag("" + schoolinglen, this
                    .getSchoolinglenListHtml(1), true);
            basicClassModelDto.setSchoolinglenHtml(schoolinglenHtml);
//            basicClassModelDto.setSchoolinglen(schoolinglen);
            if(entity!=null){
            	basicClassModelDto.setSchoolinglen(entity.getSchoolinglen());
            }else{
            	// 没有选择年级，也不存在当前学年对应的年级，只能从年级列表中选择一个
            	entity = baseGradeService.getGrade(schid, openYearList.get(0)[0], section);
            	basicClassModelDto.setSchoolinglen(entity.getSchoolinglen());
            }
            

        } else {
            // ------------------编辑---------------------
            /*
             * 学段的Map
             */
            Mcode sectionMcode = poolMap.get("sectionMcode");
            if (sectionMcode == null) {
                sectionMcode = mcodeService.getMcode("DM-RKXD");
                poolMap.put("sectionMcode", sectionMcode);
            }
            Map<String, String> sectionMap = sectionMcode.getCodeMap();
            basicClassModelDto.setSectionMap(sectionMap);

            /*
             * 学制
             */
//            int endyear = Integer.parseInt(currentAcadyear.substring(5));
//            int startyear = Integer.parseInt(acadyear.substring(0, 4));
//            int grade = endyear - startyear;
//            String schoolinglenHtml = HtmlUtil.getSelectHtmlTag("" + schoolinglen, this
//                    .getSchoolinglenListHtml(grade), true);
//            basicClassModelDto.setSchoolinglenHtml(schoolinglenHtml);
            
        }

//        String preClassname = ClassNameFactory.getInstance().getClassNameDyn(schid, acadyear,
//                section);
//        if(entity!=null){
//        	basicClassModelDto.setPreClassname(entity.getGradename()); 
//    	}else{
//    		List<Grade> gradeListinfos = baseGradeService.getGrades(schid, section);
//    		if(CollectionUtils.isNotEmpty(gradeListinfos)){
//    			basicClassModelDto.setPreClassname(gradeListinfos.get(0).getGradename());
//    		}
//    	}

        /*
         * 班级类型下拉框
         */
        String key = "classtypeMcode_" + section;
        Mcode classtypeMcode = poolMap.get(key);
        if (classtypeMcode == null) {
            classtypeMcode = mcodeService.getMcodeFaintness("DM-BJLX", String.valueOf(section));
            poolMap.put("classtypeMcode", classtypeMcode);
        }
        String classtypeHtml = classtypeMcode.getHtmlTag(basicClassModelDto.getClasstype());
        basicClassModelDto.setClasstypeHtml(classtypeHtml);

        /*
         * 文理类型下拉框
         */
        Mcode artsciencetypeMcode = poolMap.get("artsciencetypeMcode");
        if (artsciencetypeMcode == null) {
            artsciencetypeMcode = mcodeService.getMcode("DM-BJWLLX");
            poolMap.put("artsciencetypeMcode", artsciencetypeMcode);
        }
        String artsciencetype = String.valueOf(basicClassModelDto.getArtsciencetype());
        if (artsciencetype == null || "".equals(artsciencetype)) {
            artsciencetype = "0";
        }
        String artsciencetypeHtml = artsciencetypeMcode.getHtmlTag(artsciencetype);
        basicClassModelDto.setArtsciencetypeHtml(artsciencetypeHtml);

        /*
         * 班主任下拉框
         */
        // List teacheridList = employeeService.getEmployeeListHtml(schid);
        // String teacheridHtml = HtmlUtil.getSelectHtmlTag(basicClassModelDto
        // .getTeacherid(), teacheridList, true);
        // basicClassModelDto.setTeacheridHtml(teacheridHtml);
        /*
         * 班长下拉框
         */
        String stuid = basicClassModelDto.getStuid();

        String stuidHtml = HtmlUtil.getSelectHtmlTag(stuid, getStudentListHtml(schid,
                basicClassModelDto.getId()), true);
        basicClassModelDto.setStuidHtml(stuidHtml);

        /*
         * 分校区下拉框
         */
        String subschoolidHtml = null;
        List<String[]> subschoolListHtml = getSubschoolListHtml(schid);
        if (subschoolListHtml == null || subschoolListHtml.size() == 0) {
            // 如果学校没有添加分校区，则subschoolid＝schid，在新增时赋此值
            if (basicClassModelDto.getSubschoolid() == null
                    || "".equals(basicClassModelDto.getSubschoolid())) {
                basicClassModelDto.setSubschoolid(schid);
            }
        } else if (subschoolListHtml.size() == 1) {
            // 如果学校有分校区，且只有一个时（新增学校时默认添加的），在新增时直接赋值此分校区
            if (basicClassModelDto.getSubschoolid() == null
                    || "".equals(basicClassModelDto.getSubschoolid())) {
                String subschid = ((String[]) subschoolListHtml.get(0))[0];
                basicClassModelDto.setSubschoolid(subschid);
            }
        } else {
            subschoolidHtml = HtmlUtil.getSelectHtmlTag(basicClassModelDto.getSubschoolid(),
                    subschoolListHtml, true);
        }
        basicClassModelDto.setSubschoolidHtml(subschoolidHtml);

    }

    private List<String[]> getSubschoolListHtml(String schid) {
        List<SubSchool> subschoolDtoList = baseSchoolService.getSubSchools(schid);
        if (subschoolDtoList == null) {
            return null;
        }
        List<String[]> selectList = new ArrayList<String[]>();
        for (Iterator<SubSchool> it = subschoolDtoList.iterator(); it.hasNext();) {
            SubSchool dto = it.next();
            selectList.add(new String[] { dto.getId(), dto.getName() });
        }
        return selectList;
    }

    private List<String[]> getStudentListHtml(String schid, String classid) {
        List<String[]> selectList = new ArrayList<String[]>();
        if (classid == null || "".equals(classid)) {
            return selectList;
        }

        List<Student> dtoList = studentService.getStudents(classid);
        for (Iterator<Student> it = dtoList.iterator(); it.hasNext();) {
            Student dto = (Student) it.next();
            selectList.add(new String[] { dto.getId(), dto.getStuname() });
        }

        return selectList;
    }

    /*
     * 产生学年学制的下拉列表框List，最大的学制是9年
     */
    private List<String[]> getSchoolinglenListHtml(int start) {
        List<String[]> list = new ArrayList<String[]>();
        for (int i = start; i <= 9; i++) {
            String[] arr = new String[] { "" + i, i + " 年" };
            list.add(arr);
        }

        return list;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    // 由Spring容器来调用此方法设置basicClassService的值
    public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
        this.baseSchoolService = baseSchoolService;
    }

    public void setMcodeService(McodeService mcodeService) {
        this.mcodeService = mcodeService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public void setBaseClassService(BaseClassService baseClassService) {
        this.baseClassService = baseClassService;
    }

    public void setBaseGradeService(BaseGradeService baseGradeService) {
        this.baseGradeService = baseGradeService;
    }
    
    public void setRetStr(String[] retStr) {
		this.retStr = retStr;
	}
    public String[] getRetStr() {
		return retStr;
	}

	public String getAcadyearchange() {
		return acadyearchange;
	}

	public void setAcadyearchange(String acadyearchange) {
		this.acadyearchange = acadyearchange;
	}

	public String getSchidchange() {
		return schidchange;
	}

	public void setSchidchange(String schidchange) {
		this.schidchange = schidchange;
	}

	public int getSectionchange() {
		return sectionchange;
	}

	public void setSectionchange(int sectionchange) {
		this.sectionchange = sectionchange;
	}

	public String getShowAllClass() {
		return showAllClass;
	}

	public void setShowAllClass(String showAllClass) {
		this.showAllClass = showAllClass;
	}

	public String getGradeidchange() {
		return gradeidchange;
	}

	public void setGradeidchange(String gradeidchange) {
		this.gradeidchange = gradeidchange;
	}

	public void setTeachPlaceService(TeachPlaceService teachPlaceService) {
		this.teachPlaceService = teachPlaceService;
	}

	
}
