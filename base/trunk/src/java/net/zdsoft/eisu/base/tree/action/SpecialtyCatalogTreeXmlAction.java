package net.zdsoft.eisu.base.tree.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.tree.action.BaseTreeXmlModelAction;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eisu.base.common.entity.SpecialtyCatalog;
import net.zdsoft.eisu.base.common.service.SpecialtyCatalogService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class SpecialtyCatalogTreeXmlAction extends BaseTreeXmlModelAction{
	private static final long serialVersionUID = 2031763384399286045L;
	
	private SpecialtyCatalogService specialtyCatalogService;
	private XLoadTreeItemConverter<SpecialtyCatalog> specialtyCatalogTreeItemConverter;

	/**
     * 点击专业类别节点，展示专业目录
     * 
     * @return
     * @throws Exception
     */
    public String expandSpecialtyCatalog() throws Exception {
        List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();

        // 节点
       // items.addAll(getDeptItems());

        // 节点
        if (EisuTreeConstant.getTreeDepth(treeParam.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_SPECIALTY_CATALOG)) {
            List<SpecialtyCatalog> list = specialtyCatalogService.getBySpecialtyTypeId(treeParam.getParentId());
            List<XLoadTreeItem> _items = specialtyCatalogTreeItemConverter.buildTreeItems(list, treeParam);
            items.addAll(_items);
        }

        return buildTreeXml(items);
    }

	public void setSpecialtyCatalogService(
			SpecialtyCatalogService specialtyCatalogService) {
		this.specialtyCatalogService = specialtyCatalogService;
	}

	public void setSpecialtyCatalogTreeItemConverter(
			XLoadTreeItemConverter<SpecialtyCatalog> specialtyCatalogTreeItemConverter) {
		this.specialtyCatalogTreeItemConverter = specialtyCatalogTreeItemConverter;
	}
}
