package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.subsystemcall.entity.TeachGroup;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

public class TeachGroupTreeItemConverter extends
		XLoadTreeItemConverter<TeachGroup> {

	@Override
	protected void toTreeItem(TeachGroup e, TreeParam param, XLoadTreeItem item) {
       	//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getName());
    	
		item.setText(e.getName());
		int itemType = EisuTreeConstant.ITEMTYPE_TR_GROUP;
		item.setIcon("group.gif");
		item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getName()));
		if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
				.getTreeDepth(EisuTreeConstant.ITEMTYPE_TEACHER)) {
			item.setXmlSrc("/common/xtree/groupTeacherTreeXml.action");
		}
		item.setItemType(itemType);
		item.setItemLinkParams("parentId=" + e.getId() + "&parentType="
				+ Institute.PARENT_INSTITUTE + "&unitId=" + e.getUnitId());
	}

}
