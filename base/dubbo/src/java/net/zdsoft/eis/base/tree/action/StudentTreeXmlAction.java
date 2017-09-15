package net.zdsoft.eis.base.tree.action;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eisu.base.common.util.SortUtils;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * <p>动态加载的学生XML，提供给学校端的学生树使用。
 * @author lilj
 * @since 1.0
 * @version $Id: StudentTreeXmlAction.java,v 1.9 2006/12/25 06:43:33 zhaosf Exp $
 */

public class StudentTreeXmlAction extends BaseTreeXmlAction {
    private static final long serialVersionUID = 5740457142786405432L;

    /**
     * 班级guid
     */
    private String classid;

    /**
     * 学生基本信息service
     */
    private StudentService studentService;

    /**
     * 动态展开的学生树xml
     */
    private String studentTreeXml;

    /**
     * 树的类别。see TreeConstant 中TREETYPE_*
     */
    private String treeType;

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public StudentTreeXmlAction() {

    }

    public String execute() throws Exception {
        List<Student> stuList = null;
        switch (Integer.parseInt(treeType)) {
        case TreeConstant.TREETYPE_STU_NORMAL:
            stuList = studentService.getStudents(classid);
            break;
        case TreeConstant.TREETYPE_STU_FOR_ABNORMAL:
            stuList = studentService.getStudentsForAbnormalflow(classid);
            break;
        case TreeConstant.TREETYPE_STU_GRADUATE:
            stuList = studentService.getStudentsForGraduated(classid);
            break;
        case TreeConstant.TREETYPE_STU_GRADUATING:
            stuList = studentService.getAllStudents(classid);
            break;
        default:
            stuList = studentService.getStudents(classid);
        }
        //排序，留级的学生排在后面
        if(CollectionUtils.isNotEmpty(stuList)){
        	SortUtils<Student> sort = new SortUtils<Student>();
        	sort.sort(stuList, "getId", stuList.get(0).getSchid());
        }
        this.studentTreeXml = getStudentTreeXml(stuList);

        return SUCCESS;
    }

    /**
     * 组装成<满足动态XTree格式要求>的学生树xml
     * 
     * @param stuList 学生DtoList
     * @return
     */
    private String getStudentTreeXml(List<Student> stuList) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("tree");
        Element item = null;

        if (stuList != null && stuList.size() > 0) {
            for (Iterator<Student> iterator = stuList.iterator(); iterator.hasNext();) {
                item = root.addElement("tree");
                Student stuInfoDto = (Student) iterator.next();
                item.addAttribute("text", stuInfoDto.getStuname());
                item.addAttribute("action", getAction(stuInfoDto.getId(), TreeConstant.ITEMTYPE_STUDENT,
                        stuInfoDto.getStuname(), classid));
            }
        }

        return this.xmlDomToString(doc);
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTreeXML() {
        return this.treeXML;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public String getStudentTreeXml() {
        return studentTreeXml;
    }

    public void setStudentTreeXml(String studentTreeXml) {
        this.studentTreeXml = studentTreeXml;
    }

}
