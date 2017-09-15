package net.zdsoft.eis.base.tree.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.tree.service.FastTreeNode;
import net.zdsoft.eis.frame.action.BaseAction;

/**
 * @author zhangza
 * @date 2010-6-2
 */
public class RegionTree2Action extends BaseAction {
	private static final long serialVersionUID = 2771303601830395859L;

	private RegionService regionService;
	private static String regionStr = null;
	private String codeField;
	private String valueField;

	@Override
	public String execute() throws Exception {
		if (regionStr == null)
			regionStr = wrapperTreeData();
		return SUCCESS;
	}

	private String wrapperTreeData() {
		List<Region> rgs = regionService.getRegions();

		Collections.sort(rgs, new Comparator<Region>() {
			public int compare(Region o1, Region o2) {
				return o1.getFullCode().compareTo(o2.getFullCode());
			}
		});

		FastTreeNode tree = new FastTreeNode("00", "根节点", "'fulltext':'根节点'");

		Iterator<Region> it = rgs.iterator();
		String code;
		while (it.hasNext()) {
			Region region = it.next();
			code = region.getRegionCode().trim();
			if (code.length() == 2) {
				if (code.equals("00"))
					continue;
				String json = "'fulltext':'" + region.getFullName().trim()
						+ "'";
				tree.addChild(new FastTreeNode(region.getFullCode(), region
						.getRegionName(), json));
			} else if (code.length() == 4) {
				FastTreeNode tree1 = tree.getChild(code.substring(0, 2)
						+ "0000");
				if (tree1 == null)
					continue;// 没找到父节点，跳过
				String json = "'fulltext':'" + region.getFullName().trim()
						+ "'";
				tree1.addChild(new FastTreeNode(region.getFullCode(), region
						.getRegionName(), json));
			} else if (code.length() == 6) {
				FastTreeNode treeChild = tree.getChild(code.substring(0, 2)
						+ "0000");
				FastTreeNode tree2 = treeChild.getChild(code.substring(0, 4)
						+ "00");
				if (tree2 == null)
					continue;// 没找到父节点，跳过
				String json = "'fulltext':'" + region.getFullName().trim()
						+ "'";
				tree2.addChild(new FastTreeNode(region.getFullCode(), region
						.getRegionName(), json));
			} else {
				// 无法识别数据，跳过
				continue;
			}
		}
		return tree.toString();
	}

	public String getRegionStr() {
		return regionStr;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public String getCodeField() {
		return codeField;
	}

	public void setCodeField(String codeField) {
		this.codeField = codeField;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

}
