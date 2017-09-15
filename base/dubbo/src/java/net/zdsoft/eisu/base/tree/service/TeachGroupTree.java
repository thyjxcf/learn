package net.zdsoft.eisu.base.tree.service;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.subsystemcall.entity.TeachGroup;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class TeachGroupTree extends InstituteTree {

	private XLoadTreeItemConverter<TeachGroup> teachGroupTreeItemConverter;

	public void setTeachGroupTreeItemConverter(
			XLoadTreeItemConverter<TeachGroup> teachGroupTreeItemConverter) {
		this.teachGroupTreeItemConverter = teachGroupTreeItemConverter;
	}

	@Override
	protected void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {
		item.setXmlSrc("/common/xtree/instituteGroupTreeXml.action");
		item.setItemLinkParams("parentId=" + param.getUnitId() + "&parentType="
				+ Institute.PARENT_SCHOOL);
	}

	@Override
	protected void addSchoolChildNodes(XLoadTreeItem parentNode, TreeParam param) {
		super.addSchoolChildNodes(parentNode, param);

		// 学校子节点：教研组
		if (EisuTreeConstant.getTreeDepth(param.getLayer()) >= EisuTreeConstant
				.getTreeDepth(EisuTreeConstant.ITEMTYPE_TR_GROUP)) {
			List<TeachGroup> groupList = new ArrayList<TeachGroup>();
			// 显示院系时，只显示学校的直属专业，否则显示学校下的所有专业
			if (param.isShowInstitute()) {
				return;
			} else {
//				groupList = eduadmSubsystemService.getTeachGroupList(
//						param.getUnitId(), null);
			}

			List<String> ids = new ArrayList<String>();
			boolean eduadmAdmin = true;
			if (eduadmSubsystemService != null && param.isEduadmShowPopedom()) {
//				// 教务
//				eduadmAdmin = eduadmSubsystemService.isEduadmAdmin(
//						param.getUnitId(), param.getTeacherId());
//				if (!eduadmAdmin) {
//					ids = eduadmSubsystemService.getInstitutetByTeacherId(
//							param.getUnitId(), param.getTeacherId());
//				}
			}
			for (TeachGroup e : groupList) {
				if (param.isEduadmShowPopedom() && !eduadmAdmin) {
					if (!ids.contains(e.getInstituteId()))
						continue;
				}
				teachGroupTreeItemConverter.buildTreeItem(e, param, parentNode,
						makeXTreeItemName());
			}
		}

	}

}
