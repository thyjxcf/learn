package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.tree.service.TreeService;

/**
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2014-3-24 上午11:11:59 $
 */

public class RegionTreeAction extends BaseTreeModelAction {
	private static final long serialVersionUID = 4491368631713477163L;

	protected TreeService regionTreeService;

	public void setRegionTreeService(TreeService regionTreeService) {
		this.regionTreeService = regionTreeService;
	}

	protected TreeService getTreeService() {
		return regionTreeService;
	}

	/**
	 * 行政区划
	 * 
	 * @return
	 * @throws Exception
	 */
	public String regionTree() throws Exception {
		return buildTree();
	}

}
