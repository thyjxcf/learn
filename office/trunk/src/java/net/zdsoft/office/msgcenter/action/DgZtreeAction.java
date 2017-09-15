package net.zdsoft.office.msgcenter.action;

import net.zdsoft.eis.base.tree.service.DeptTree;
import net.zdsoft.eis.base.tree.service.UnitTree;
import net.zdsoft.eis.component.addressbook.service.CustomGroupService;
import net.zdsoft.eis.frame.action.BaseAction;

import org.apache.struts2.ServletActionContext;

/**
 * @author chens
 */
@SuppressWarnings("serial")
public class DgZtreeAction extends BaseAction {

	private UnitTree unitTreeService;
	private DeptTree deptTreeService;
	private CustomGroupService customGroupService;

	private String unitId;
	private String treeJsonCode;
	
	private String treeType;//2:人员，4：部门

	public String userGroupZtree(){
		setTreeJsonCode(customGroupService.getZTree(getLoginUser().getUnitId(),getLoginUser().getUserId(), ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	public String unitGroupZtree(){
		setTreeJsonCode(customGroupService.getUnitGroupZTree(getLoginUser().getUnitId(),getLoginUser().getUserId(), ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	public String currentUnitZtree(){
		setTreeJsonCode(deptTreeService.getZTree(getLoginUser().getUnitId(), ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	/**
	 * 顶级单位
	 * @return
	 */
	public String topUnitZtree(){
		setTreeJsonCode(deptTreeService.getZTree(null, ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	/**
	 * 所有教办即下属教育局
	 * @return
	 */
	public String subEduZtree(){
		setTreeJsonCode(unitTreeService.getSubEduZTree(null, ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	/**
	 * 所有学校
	 * @return
	 */
	public String subSchoolZtree(){
		setTreeJsonCode(unitTreeService.getSubSchoolZTree(null, ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	public String directSchoolTypeZtree(){
		setTreeJsonCode(unitTreeService.getDirectSchoolTypeZTree(null, ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	public String unitAllUserZtree(){
		setTreeJsonCode(unitTreeService.getAllUserZTree(unitId, ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	public String unitAllDeptZtree(){
		setTreeJsonCode(unitTreeService.getAllDeptZTree(unitId, ServletActionContext
				.getRequest().getContextPath()));
		return SUCCESS;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getTreeJsonCode() {
		return treeJsonCode;
	}

	public void setTreeJsonCode(String treeJsonCode) {
		this.treeJsonCode = treeJsonCode;
	}

	public void setUnitTreeService(UnitTree unitTreeService) {
		this.unitTreeService = unitTreeService;
	}
	
	public void setDeptTreeService(DeptTree deptTreeService) {
		this.deptTreeService = deptTreeService;
	}

	public void setCustomGroupService(CustomGroupService customGroupService) {
		this.customGroupService = customGroupService;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}
	
}