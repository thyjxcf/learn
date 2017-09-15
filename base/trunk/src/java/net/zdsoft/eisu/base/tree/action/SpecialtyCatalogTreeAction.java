package net.zdsoft.eisu.base.tree.action;

import net.zdsoft.eis.base.tree.action.BaseTreeModelAction;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;

public class SpecialtyCatalogTreeAction extends BaseTreeModelAction {
	private static final long serialVersionUID = -4130018863828482070L;
	protected TreeService specialtyTypeTree;
	
	@Override
	protected TreeService getTreeService() {
		return specialtyTypeTree;
	}
	
	/**
     * 专业类别 -> 专业目录
     * 
     * @return
     * @throws Exception
     */
    public String specialtyCatalogTree() throws Exception {
        treeParam.setLayer(EisuTreeConstant.ITEMTYPE_SPECIALTY_CATALOG);
        return buildTree();
    }

	public void setSpecialtyTypeTree(TreeService specialtyTypeTree) {
		this.specialtyTypeTree = specialtyTypeTree;
	}

}
