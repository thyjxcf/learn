/* 
 * @(#)EisStudentTreeXmlAction.java    Created on Jun 2, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.leadin.tree.XLoadTreeItem;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 2, 2011 4:48:19 PM $
 */
public class EisStudentTreeXmlAction extends BaseTreeXmlModelAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -197831873482257861L;

    private BasicClassService basicClassService;
    private StudentService studentService;

    private XLoadTreeItemConverter<BasicClass> basicClassTreeItemConverter;
    private XLoadTreeItemConverter<Student> studentTreeItemConverter;

    /**
     * 点击年级节点，展示班级
     * 
     * @return
     * @throws Exception
     */
    public String expandClass() throws Exception {
        List<BasicClass> list = null;
        String schoolId = treeParam.getUnitId();
        if (treeParam.isGraduateClass()) {
            list = basicClassService.getClasses(schoolId,treeParam.getAcadyear());
        } else {
            list = basicClassService.getClasses(schoolId);
        }
       
        List<XLoadTreeItem> items = basicClassTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);
    }

    /**
     * 点击班级节点，展示学生
     * 
     * @return
     * @throws Exception
     */
    public String expandStudent() throws Exception {
        List<Student> list = studentService.getStudents(treeParam.getClassId());
        List<XLoadTreeItem> items = studentTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);

    }

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void setBasicClassTreeItemConverter(
            XLoadTreeItemConverter<BasicClass> basicClassTreeItemConverter) {
        this.basicClassTreeItemConverter = basicClassTreeItemConverter;
    }

    public void setStudentTreeItemConverter(XLoadTreeItemConverter<Student> studentTreeItemConverter) {
        this.studentTreeItemConverter = studentTreeItemConverter;
    }

}
