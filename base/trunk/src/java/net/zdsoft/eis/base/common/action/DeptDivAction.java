package net.zdsoft.eis.base.common.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.simple.entity.SimpleObject;

public class DeptDivAction extends ObjectDivAction<Dept> {

	private static final long serialVersionUID = 2224565345284779511L;

	private String parentId;

	private DeptService deptService;

	@Override
	protected List<Dept> getDatasByConditon() {
		return deptService.getDepts(getUnitId());
	}

	@Override
	protected List<Dept> getDatasByParentId() {
		if (StringUtils.isBlank(parentId)) {
			return deptService.getDirectDepts(getUnitId());
		} else {
			if(BaseConstant.ZERO_GUID.equals(parentId)){
				return deptService.getDirectDepts(getUnitId());
			}
			return deptService.getDeptsByParentId(parentId);
		}
	}

	@Override
	protected void toObject(Dept dept, SimpleObject object) {
		if (dept == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(dept.getId());
		object.setObjectCode(dept.getDeptCode());
		object.setObjectName(dept.getDeptname());
		object.setUnitId(dept.getUnitId());
		object.setGroupId(dept.getParentid());
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
