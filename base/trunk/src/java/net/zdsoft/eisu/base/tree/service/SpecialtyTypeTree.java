package net.zdsoft.eisu.base.tree.service;

import java.util.List;

import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.base.tree.service.EduTree;
import net.zdsoft.eisu.base.common.entity.SpecialtyType;
import net.zdsoft.eisu.base.common.service.SpecialtyTypeService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class SpecialtyTypeTree extends EduTree {
	
	private SpecialtyTypeService specialtyTypeService;
	private XLoadTreeItemConverter<SpecialtyType> specialtyTypeTreeItemConverter;
	
	@Override
    protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {

        // 学校子节点：专业类别
        if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_SPECIALTY_TYPE)) {
            List<SpecialtyType> specialtyTypes = specialtyTypeService.getSpecialtyTypes(param.getUnitId());
            for (SpecialtyType e : specialtyTypes) {
            	specialtyTypeTreeItemConverter.buildTreeItem(e, param, parentNode, makeXTreeItemName());
            }
        }

    }

	public void setSpecialtyTypeService(SpecialtyTypeService specialtyTypeService) {
		this.specialtyTypeService = specialtyTypeService;
	}

	public void setSpecialtyTypeTreeItemConverter(
			XLoadTreeItemConverter<SpecialtyType> specialtyTypeTreeItemConverter) {
		this.specialtyTypeTreeItemConverter = specialtyTypeTreeItemConverter;
	}

}
