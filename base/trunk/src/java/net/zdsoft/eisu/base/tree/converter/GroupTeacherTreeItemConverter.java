package net.zdsoft.eisu.base.tree.converter;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

public class GroupTeacherTreeItemConverter extends
		XLoadTreeItemConverter<Teacher> {

	@Override
	protected void toTreeItem(Teacher e, TreeParam param, XLoadTreeItem item) {
    	//对SimpleObject置值
	  	item.setId(e.getId());
    	item.setObjectCode(e.getTchId());
    	item.setObjectName(e.getName());
//    	item.setUnitiveCode("");
//		item.setGroupId(e.getDeptid());
//		item.setUnitId(e.getUnitid());
		
		int itemType = EisuTreeConstant.ITEMTYPE_TEACHER;
		item.setText(e.getName());
		item.setAction(XTreeMaker.creatAction(e.getId(), itemType, e.getName()));
		// 根据性别显示男女图标
		if (BaseConstant.SEX_MALE.equals(e.getSex())) {
			item.setIcon("male.gif");
		} else if (BaseConstant.SEX_FEMALE.equals(e.getSex())) {
			item.setIcon("female.gif");
		} else {
			item.setIcon("unknown_sex.gif");
		}
		item.setItemType(itemType);
		item.setItemLinkParams("unitId=" + e.getUnitid());

	}

}
