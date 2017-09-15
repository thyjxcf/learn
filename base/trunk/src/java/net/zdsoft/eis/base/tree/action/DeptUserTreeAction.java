package net.zdsoft.eis.base.tree.action;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;

/**
 * @author chens
 * @version 创建时间：2015-1-10 下午12:01:30
 * 
 */
@SuppressWarnings("serial")
public class DeptUserTreeAction extends BaseTreeAction {

	private UnitService unitService;
	private DeptService deptService;
	private UserService userService;
	
	private int height;//宏定义的选择界面高度
	private String unitId;
	private List<Dept> depts;
	private Map<String, List<User>> deptUsersMap;
	
	@Override
	public String execute() throws Exception {
		depts = deptService.getDepts(unitId);
		deptUsersMap = userService.getDeptUsersMap(unitId);
		return SUCCESS;
	}
	
	public String deptUserAllTree() {
		Unit unit = unitService.getUnit(unitId);
		depts = deptService.getDepts(unitId);
		for(Dept dept:depts){
			dept.setUnitName(unit.getName());
		}
		deptUsersMap = userService.getDeptUsersMap(unitId);
		return SUCCESS;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public Map<String, List<User>> getDeptUsersMap() {
		return deptUsersMap;
	}

	public void setDeptUsersMap(Map<String, List<User>> deptUsersMap) {
		this.deptUsersMap = deptUsersMap;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
}
