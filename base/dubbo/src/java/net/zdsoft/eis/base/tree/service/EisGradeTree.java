package net.zdsoft.eis.base.tree.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class EisGradeTree extends EduTree{

	private GradeService gradeService;
    private XLoadTreeItemConverter<Grade> gradeTreeItemConverter;
	
    
    @Override
    public void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
        item.setXmlSrc("/common/xtree/eisGradeTreeXml.action");
        item.setItemLinkParams("unitId=" + param.getUnitId());
    }
    
	@Override
	protected void addSchoolChildNodes(XLoadTreeItem schoolItem, TreeParam param) {
		// TODO Auto-generated method stub
		super.addSchoolChildNodes(schoolItem, param);
		if (TreeConstant.getTreeDepth(param.getLayer()) >= TreeConstant
                .getTreeDepth(TreeConstant.ITEMTYPE_GRADE)) {
            String schoolId = param.getUnitId();
            
            List<Grade> gradeList = gradeService.getGrades(schoolId);
            for (Grade e : gradeList) {
            	gradeTreeItemConverter
                        .buildTreeItem(e, param, schoolItem, makeXTreeItemName());
            }
        }
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setGradeTreeItemConverter(
			XLoadTreeItemConverter<Grade> gradeTreeItemConverter) {
		this.gradeTreeItemConverter = gradeTreeItemConverter;
	}
}
