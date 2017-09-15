/* 
 * @(#)NewRegionTree.java    Created on 2014-3-24
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.converter.RegionTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.leadin.tree.XLoadTreeItem;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2014-3-24 上午10:38:20 $
 */
public class RegionTree extends AbstractTreeService {
	private RegionTreeItemConverter regionTreeItemConverter;
	private RegionService regionService;

	public void setRegionTreeItemConverter(RegionTreeItemConverter regionTreeItemConverter) {
		this.regionTreeItemConverter = regionTreeItemConverter;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	@Override
	public XLoadTreeItem getTree(TreeParam param) {
		Region region = null;
		// 全国结点
		if(StringUtils.isNotBlank(param.getRegionCode())){
			region = regionService.getRegion(param.getRegionCode());
		}else{
			region = regionService.getRegion("00");
		}
		if(region == null){
			region = new Region();
			region.setId(BaseConstant.ZERO_GUID);
			region.setFullCode("00");
			region.setFullName("中国");
			region.setRegionName("中国");
			region.setRegionCode("00");
		}
		XLoadTreeItem root = regionTreeItemConverter.buildTreeItem(region, param, null, "tree");
		root.setAction("");
		root.setXmlSrc(null);

		List<Region> regions = null;
		if(StringUtils.isNotEmpty(param.getRegionCode())){
			regions = regionService.getSubRegions(param.getRegionCode(),param.getType());
		}else if(StringUtils.isNotEmpty(param.getType())){
			regions = regionService.getSubRegions("", param.getType());
		}else{
			regions = regionService.getSubRegions("");
		}
		for (Region e : regions) {
			regionTreeItemConverter.buildTreeItem(e, param, root, makeXTreeItemName());
		}
		return root;
	}
}
