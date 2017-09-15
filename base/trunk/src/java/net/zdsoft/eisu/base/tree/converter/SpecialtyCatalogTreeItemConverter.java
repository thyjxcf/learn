package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.SpecialtyCatalog;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

public class SpecialtyCatalogTreeItemConverter extends
		XLoadTreeItemConverter<SpecialtyCatalog> {

	@Override
	protected void toTreeItem(SpecialtyCatalog e, TreeParam param,
			XLoadTreeItem item) {
		//对SimpleObject置值
    	item.setId(e.getId());
    	item.setObjectCode(e.getSpecialtyCode());
    	item.setObjectName(e.getSpecialtyName());
//		item.setGroupId(e.getSpecialtyTypeId());
//		item.setUnitId(e.getUnitId());
		
		
        int itemType = EisuTreeConstant.ITEMTYPE_SPECIALTY_CATALOG;
        item.setText(e.getSpecialtyName());
        item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getSpecialtyName(), e.getSpecialtyTypeId()));

        // 图标
        item.setIcon("specialty.gif");

        item.setItemType(itemType);
		
	}

}
