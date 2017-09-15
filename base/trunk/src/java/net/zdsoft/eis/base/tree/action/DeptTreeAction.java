package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.tree.service.DeptTree;

import org.apache.struts2.ServletActionContext;

public class DeptTreeAction extends BaseTreeAction {
    private static final long serialVersionUID = 1071633641525969162L;

    private DeptTree deptTreeService;

    private String deptId;
    private String unitId;

    public String execute() throws Exception {
        treeJSCode = deptTreeService.getXTree(unitId, deptId, ServletActionContext.getRequest()
                .getContextPath());
        return SUCCESS;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setDeptTreeService(DeptTree deptTreeService) {
        this.deptTreeService = deptTreeService;
    }
}
