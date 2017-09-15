package net.zdsoft.eis.base.tree.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.tree.service.UnitTree;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * @author linqz
 * @version $Revision: 1.7 $, $Date: 2006/10/26 13:32:36 $
 */
public class UnitTreeAction extends BaseTreeAction {
	private static final long serialVersionUID = 8040272990430562433L;

	private UnitTree unitTreeService;

	// 下面两个属性是专用属性
	private UnitService unitService;

	private String flag;
	private String unitId;
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setUnitTreeService(UnitTree unitTreeService) {
		this.unitTreeService = unitTreeService;
	}

	public String getTreeJSCode() {
		return treeJSCode;
	}

	public String execute() throws Exception {

		// 这是转出异动的专用代码
		// 如果单位的Id不为空且标志为1，进到if中
		if (null != unitId && "1".equals(flag)) {
			String loginUnitId = getLoginInfo().getUnitID();
			// 只有顶级单位不是全国教育部的时候，这里显示的是操作者所在省的下级单位
			if (!loginUnitId.equalsIgnoreCase(unitId)) {
				Unit topUnit = unitService.getUnit(unitId);
				if (topUnit.getRegion().equals(Unit.TOP_UNIT_REGION_CODE)) {
					Unit unit = unitService.getUnit(loginUnitId);
					String unionId = unit.getUnionid();
					List<Unit> list = unitService.getUnitsByUnionCode(
							unionId.substring(0, 2), Unit.UNIT_CLASS_EDU);
					if (CollectionUtils.isNotEmpty(list)) {
						unitId = list.get(0).getId();
					}
				}
			}
		}

		setTreeJSCode(unitTreeService.getXTree(unitId, type,
				ServletActionContext.getRequest().getContextPath()));
		return SUCCESS;
	}

	public String unitZTree() throws Exception {
		setTreeJsonCode(unitTreeService.getZTree(unitId, ServletActionContext
				.getRequest().getContextPath()));
		return COMMON_TREE_JSON;
	}
	
	public String directUnitZTree() throws Exception {
		if(StringUtils.isBlank(unitId)){
			unitId = getLoginInfo().getUnitID();
		}
		setTreeJsonCode(unitTreeService.getDirectZTree(unitId, ServletActionContext
				.getRequest().getContextPath()));
		return COMMON_TREE_JSON;
	}
	
	public String unitZSerachTree() throws Exception {
		setTreeJsonCode(unitTreeService.getZSearchTree(unitId, ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	/**
	 * 只有本单位的zTree,通讯录编辑调用
	 * @return
	 * @throws Exception
	 */
	public String currentUnitZTree() throws Exception {
		if(StringUtils.isBlank(unitId)){
			unitId = getLoginInfo().getUnitID();
		}
		setTreeJsonCode(unitTreeService.getCurrentUnitZTree(unitId, ServletActionContext
				.getRequest().getContextPath()));
		return COMMON_TREE_JSON;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
