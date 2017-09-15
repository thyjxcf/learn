package net.zdsoft.eis.base.tree.converter;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

public class GradeTreeItemConverter extends XLoadTreeItemConverter<Grade>{

	@Override
	protected void toTreeItem(Grade e, TreeParam param, XLoadTreeItem item) {
		//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectName(e.getGradename());
    	
        int itemType = TreeConstant.ITEMTYPE_GRADE;
        item.setText(e.getGradename());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getGradename()));
        item.setIcon("class.gif");

        if (TreeConstant.getTreeDepth(param.getLayer()) >= TreeConstant
                .getTreeDepth(TreeConstant.ITEMTYPE_CLASS)) {
            item.setXmlSrc("/common/xtree/eisBasicClassTreeXml.action");
        }

        item.setItemType(itemType);
        item.setItemLinkParams("gradeId=" + e.getId()+"&unitId="+e.getSchid());
	}

}
