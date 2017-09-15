package net.zdsoft.eis.base.tree.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeNode;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

public class DeptTree extends XTreeMaker {
	private static final String DEFAULT_GROUP_ID = BaseConstant.ZERO_GUID;

	private UnitService unitService;
	private DeptService deptService;
	private UserService userService;

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getXTree(String unitId, String deptId, String contextPath)
			throws Exception {
		Unit unit = unitService.getUnit(unitId);
		if (unit == null) {
			return null;
		}
		String rootText = unit.getName();
		List<Dept> list = null;
		if (StringUtils.isNotBlank(deptId)) {
			list = new ArrayList<Dept>();
			Dept dept = deptService.getDept(deptId);
			if (dept != null) {
				list.add(dept);
			}
		} else {
			list = deptService.getDirectDepts(unitId);
		}
		return composeXTree(new XLoadTreeItem(rootText, rootNodeName, "0"),
				list, rootText, unitId, contextPath);
	}

	public String composeXTree(XLoadTreeItem parentNode, List<Dept> list,
			String rootText, String unitId, String contextPath)
			throws Exception {
		StringBuffer sbTree = new StringBuffer();
		XLoadTreeItem rootItem = new XLoadTreeItem(rootText, rootNodeName, "0",
				getAction(DEFAULT_GROUP_ID, Dept.TOP_GROUP_NAME, unitId));
		sbTree.append(this.newWebFXTree(rootItem));
		if (list != null) {
			Iterator<Dept> ite = list.iterator();
			Dept dept;
			while (ite.hasNext()) {
				dept = (Dept) ite.next();
				XLoadTreeItem deptItem = new XLoadTreeItem();
				deptItem.setText(dept.getDeptname());
				deptItem.setAction(getAction(dept.getId(), dept.getDeptname(),
						dept.getUnitId()));
				deptItem.setInputValue(dept.getId());
				deptItem.setNodeName(makeXTreeItemName());
				deptItem.setXmlSrc(contextPath
						+ "/common/xtree/depttreexml.action?deptId="
						+ dept.getId() + "&&deptName=" + dept.getDeptname()
						+ "&&unitId=" + unitId);
				deptItem.setIcon("'" + contextPath
						+ TreeConstant.TREE_ICON_PATH_PREFIX
						+ "department.gif'");
				deptItem.setOpenIcon("'" + contextPath
						+ TreeConstant.TREE_ICON_PATH_PREFIX
						+ "department_selected.gif'");
				sbTree.append("var " + deptItem.getNodeName() + " = "
						+ newWebFXTreeItem(deptItem) + ";\n");
				sbTree.append(parentNode.getNodeName() + ".add("
						+ deptItem.getNodeName() + ");\n");
			}
		}
		return sbTree.toString();
	}
	
	public String getZTree(String unitId, String contextPath){
		Unit unit ;
		if (StringUtils.isBlank(unitId)) {
			unit = unitService.getTopEdu();
			unitId = unit.getId();
		}else{
			unit = unitService.getUnit(unitId);
		}
		List<Dept> list = deptService.getDepts(unitId);
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Dept dept : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(dept.getId());
				treeNode.setParentId(BaseConstant.ZERO_GUID);
				treeNode.setName(dept.getDeptname());
				treeNode.setDetailName(dept.getDeptname()+"("+unit.getName()+")");
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "02.png");
				treeNode.setType(TreeConstant.TREE_DEPT);
				tnList.add(treeNode);
			}
			Map<String,List<User>> deptUsersMap = userService.getDeptUsersMap(unitId);
			for (Dept dept : list) {
				List<User> users = deptUsersMap.get(dept.getId());
				for(User user:users){
					TreeNode treeNode = new TreeNode();
					treeNode.setId(user.getId());
					treeNode.setParentId(dept.getId());
					treeNode.setName(user.getRealname());
					treeNode.setDetailName(user.getRealname()+"("+user.getUnitName()+"-"+user.getDeptName()+")");
					treeNode.setIcon(contextPath
							+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "03.png");
					treeNode.setType(TreeConstant.TREE_USER);
					tnList.add(treeNode);
				}
			}
		}
		return JSON.toJSONString(tnList);
	}

}
