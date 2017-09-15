package net.zdsoft.eis.base.tree.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.tree.converter.RegionTreeItemConverter;
import net.zdsoft.leadin.tree.XLoadTreeItem;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2014-3-24 上午11:20:06 $
 */
public class RegionTreeXmlAction extends BaseTreeXmlModelAction {
	private static final long serialVersionUID = -2406592717281280136L;

	private RegionTreeItemConverter regionTreeItemConverter;
	private RegionService regionService;
	public void setRegionTreeItemConverter(RegionTreeItemConverter regionTreeItemConverter) {
		this.regionTreeItemConverter = regionTreeItemConverter;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * 点击上级行政区划，展示下级行政区划
	 * 
	 * @return
	 * @throws Exception
	 */
	public String expandRegion() throws Exception {
		List<Region> list = null;
		if(StringUtils.isNotBlank(treeParam.getType())){
			list = regionService.getSubRegions(treeParam.getParentId(),treeParam.getType());
		}else{
			list = regionService.getSubRegions(treeParam.getParentId());
		}
		//东莞人事-教师档案-个人信息   籍贯选择使用（所在籍贯为东莞市可选择到对应的街镇，外省市则选择到对应的市）
		if(treeParam.isDgPersonnel()){
			//四个直辖市
//			boolean isDirectCity = StringUtils.startsWithAny(treeParam.getParentId(), new String[]{"11","12","31","50"});
			if((StringUtils.length(treeParam.getParentId()) == 4 
					&& !treeParam.getParentId().startsWith("4419"))){
				return null;
			}
		}
		List<XLoadTreeItem> items = regionTreeItemConverter.buildTreeItems(list, treeParam);
		return buildTreeXml(items);
	}
}
