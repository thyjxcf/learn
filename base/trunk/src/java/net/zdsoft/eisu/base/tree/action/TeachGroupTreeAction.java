package net.zdsoft.eisu.base.tree.action;

import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.service.TreeService;

public class TeachGroupTreeAction extends InstituteTreeAction{
private static final long serialVersionUID = -7677439699138629595L;
	
	private TreeService teachGroupTree;
	
	public void setTeachGroupTree(TreeService teachGroupTree) {
        this.teachGroupTree = teachGroupTree;
    }

    protected TreeService getTreeService() {
        return teachGroupTree;
    }
    
	/**
	 * 院系-> 教研组 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String teachGroupTree() throws Exception {
		treeParam.setLayer(TreeConstant.ITEMTYPE_TR_GROUP);
		return buildTree();
	}
}
