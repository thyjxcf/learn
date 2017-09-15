package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.SpecialtyType;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

public class SpecialtyTypeTreeItemConverter extends XLoadTreeItemConverter<SpecialtyType> {

	@Override
	protected void toTreeItem(SpecialtyType e, TreeParam param,
			XLoadTreeItem item) {
		//对SimpleObject置值
      	item.setId(e.getId());
    	item.setObjectName(e.getTypeName());
    	
        int itemType = EisuTreeConstant.ITEMTYPE_SPECIALTY_TYPE;
        item.setText(e.getTypeName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getTypeName()));
        item.setIcon("specialty.gif");
        if(EisuTreeConstant.getTreeDepth(param.getLayer())>=
        	EisuTreeConstant.getTreeDepth(EisuTreeConstant.ITEMTYPE_SPECIALTY_TYPE)){
        	item.setXmlSrc("/common/xtree/specialtyCatalogTreeXml.action");//专业目录
        }

        item.setItemType(itemType);
        item.setItemLinkParams("parentId=" + e.getId());
		
	}

}
