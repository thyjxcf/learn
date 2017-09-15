package net.zdsoft.eis.base.tree.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class EisClassTreeXmlAction extends BaseTreeXmlModelAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BasicClassService basicClassService;
    private GradeService gradeService;

    private XLoadTreeItemConverter<BasicClass> basicClassTreeItemConverter;
    private XLoadTreeItemConverter<Grade> gradeTreeItemConverter;

    /**
     * 点击校区节点，展示年级
     * 
     * @return
     * @throws Exception
     */
    public String expandGrade() throws Exception {
        List<Grade> list = null;
        String schoolId = treeParam.getUnitId();
        list = gradeService.getGrades(schoolId);
        List<XLoadTreeItem> items = gradeTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);
    }

    /**
     * 点击年级节点，展示班级
     * 
     * @return
     * @throws Exception
     */
    public String expandClass() throws Exception {
    	List<BasicClass> list = null;
        String gradeId = treeParam.getGradeId();
        list = basicClassService.getClassesByGrade(gradeId);
        List<XLoadTreeItem> items = basicClassTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);
    }

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setBasicClassTreeItemConverter(
            XLoadTreeItemConverter<BasicClass> basicClassTreeItemConverter) {
        this.basicClassTreeItemConverter = basicClassTreeItemConverter;
    }

	public void setGradeTreeItemConverter(
			XLoadTreeItemConverter<Grade> gradeTreeItemConverter) {
		this.gradeTreeItemConverter = gradeTreeItemConverter;
	}
}
