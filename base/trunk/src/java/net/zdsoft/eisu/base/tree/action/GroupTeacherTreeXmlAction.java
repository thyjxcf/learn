package net.zdsoft.eisu.base.tree.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class GroupTeacherTreeXmlAction extends TeachGroupTreeXmlAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8648466853419285231L;
	
	private EduadmSubsystemService eduadmSubsystemService;

	private XLoadTreeItemConverter<Teacher> groupTeacherTreeItemConverter;

	public void setGroupTeacherTreeItemConverter(
			XLoadTreeItemConverter<Teacher> groupTeacherTreeItemConverter) {
		this.groupTeacherTreeItemConverter = groupTeacherTreeItemConverter;
	}

	protected List<XLoadTreeItem> getGroupItems() {
		List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();
		// 教师
//		List<Teacher> list = eduadmSubsystemService.getGroupTeacherList(
//				treeParam.getUnitId(), treeParam.getParentId());
//		List<XLoadTreeItem> _items = groupTeacherTreeItemConverter
//				.buildTreeItems(list, treeParam);
//		items.addAll(_items);
		return items;
	}

	/**
	 * 点击教研组节点，展示教师
	 * 
	 * @return
	 * @throws Exception
	 */
	public String expandGroupTeacher() throws Exception {
		List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();
		items.addAll(getGroupItems());
		return buildTreeXml(items);
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}


}
