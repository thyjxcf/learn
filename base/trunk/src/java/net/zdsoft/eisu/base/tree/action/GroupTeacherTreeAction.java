package net.zdsoft.eisu.base.tree.action;

import net.zdsoft.eis.base.tree.TreeConstant;

public class GroupTeacherTreeAction extends TeachGroupTreeAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1360412818911693593L;
	
	/**
	 * 院系-> 教研组 ->教师
	 * 
	 * @return
	 * @throws Exception
	 */
	public String teachGroupTeacherTree() throws Exception {
		treeParam.setLayer(TreeConstant.ITEMTYPE_TEACHER);
		return buildTree();
	}

}
