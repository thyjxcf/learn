package net.zdsoft.eisu.base.tree.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.subsystemcall.entity.TeachGroup;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class TeachGroupTreeXmlAction extends InstituteTreeXmlAction {

	private static final long serialVersionUID = 5037412620450346180L;

	private EduadmSubsystemService eduadmSubsystemService;

	private XLoadTreeItemConverter<TeachGroup> teachGroupTreeItemConverter;

	public void setTeachGroupTreeItemConverter(
			XLoadTreeItemConverter<TeachGroup> teachGroupTreeItemConverter) {
		this.teachGroupTreeItemConverter = teachGroupTreeItemConverter;
	}

	protected List<XLoadTreeItem> getGroupItems() {
		List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();
		// 课程节点
		List<TeachGroup> list = new ArrayList<TeachGroup>();//.getTeachGroupList(
//				treeParam.getUnitId(), treeParam.getParentId());

		List<XLoadTreeItem> _items = teachGroupTreeItemConverter
				.buildTreeItems(list, treeParam);
		items.addAll(_items);
		return items;
	}

	/**
	 * 点击院系节点，展示教研组
	 * 
	 * @return
	 * @throws Exception
	 */
	public String expandTeachGroup() throws Exception {
		List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();
		items.addAll(getGroupItems());
		return buildTreeXml(items);
	}

	public String expandInstituteGroup() throws Exception {
		List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();
		// 系节点
		items.addAll(getInstituteItems());
		// 教研组节点
		if (EisuTreeConstant.getTreeDepth(treeParam.getLayer()) >= EisuTreeConstant
				.getTreeDepth(EisuTreeConstant.ITEMTYPE_TR_GROUP)) {
			// 课程节点
			List<TeachGroup> list = new ArrayList<TeachGroup>();
//				eduadmSubsystemService.getTeachGroupList(
//					treeParam.getUnitId(), treeParam.getParentId());

			List<XLoadTreeItem> _items = teachGroupTreeItemConverter
					.buildTreeItems(list, treeParam);
			items.addAll(_items);
		}
		return buildTreeXml(items);
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

}