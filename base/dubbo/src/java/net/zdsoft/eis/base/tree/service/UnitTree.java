package net.zdsoft.eis.base.tree.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.param.TreeNode;
import net.zdsoft.eis.base.tree.param.UnitTreeNode;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author linqz
 * @version $Revision: 1.12 $, $Date: 2007/02/08 06:00:37 $
 */
public class UnitTree extends XTreeMaker {
	private static Logger log = LoggerFactory.getLogger(UnitTree.class);
	private static final String DEFAULT_UNIT_ID = BaseConstant.ZERO_GUID;

	private UnitService unitService;
	private DeptService deptService;
	private UserService userService;

	private SchoolService schoolService;
	private McodedetailService mcodedetailService;

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public String getZTree(String unitId, String contextPath) throws Exception {
		return composeZTree(contextPath, unitId);
	}
	
	public String getDirectZTree(String unitId, String contextPath) throws Exception {
		return composeDirectZTree(contextPath, unitId);
	}

	public String getZSearchTree(String unitId, String contextPath)
			throws Exception {
		return composeZSearchTree(contextPath, unitId);
	}

	public String getCurrentUnitZTree(String unitId, String contextPath)
			throws Exception {
		Unit unit = unitService.getUnit(unitId);
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		TreeNode treeNode = new TreeNode();
		treeNode.setId(unit.getId());
		treeNode.setParentId(unit.getParentid());
		treeNode.setName(unit.getName());
		treeNode.setIcon(contextPath + TreeConstant.ZTREE_ICON_PATH_PREFIX
				+ "01.png");
		treeNode.setOpen(true);
		tnList.add(treeNode);
		return JSON.toJSONString(tnList);
	}
	
	public String getSubEduZTree(String unitId, String contextPath){
		if (StringUtils.isBlank(unitId)) {
			Unit topUnit = unitService.getTopEdu();
			unitId = topUnit.getId();
		}
		List<Unit> list = unitService.getUnderlingUnits(unitId, false);
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Unit unit : list) {
				if(unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
					continue;
				}
				TreeNode treeNode = new TreeNode();
				treeNode.setId(unit.getId());
				treeNode.setParentId(unit.getParentid());
				treeNode.setName(unit.getName());
				treeNode.setIsParent("true");
				treeNode.setType(TreeConstant.TREE_UNIT);
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "01.png");
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}
	
	/**
	 * 下属学校
	 * @param unitId
	 * @param contextPath
	 * @return
	 */
	public String getSubSchoolZTree(String unitId, String contextPath){
		if (StringUtils.isBlank(unitId)) {
			Unit topUnit = unitService.getTopEdu();
			unitId = topUnit.getId();
		}
		List<Unit> list = unitService.getAllUnits(unitId, true);
		Collections.sort(list, new Comparator<Unit>() {
			public int compare(Unit o1, Unit o2) {
				return o1.getUnitclass().compareTo(o2.getUnitclass());
			}
		});
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Unit unit : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(unit.getId());
				treeNode.setParentId(unit.getParentid());
				treeNode.setName(unit.getName());
				treeNode.setType(TreeConstant.TREE_UNIT);
				treeNode.setIcon(contextPath
				+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "01.png");
				if(unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
					treeNode.setIsParent("true");
				}
				if (unit.getId().equals(unitId))
					treeNode.setOpen(true);
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}
	/**
	 * 直属学校 TODO
	 * @param unitId
	 * @param contextPath
	 * @return
	 */
	public String getDirectSchoolTypeZTree(String unitId, String contextPath){
		Unit topUnit = new Unit();
		if (StringUtils.isBlank(unitId)) {
			topUnit = unitService.getTopEdu();
			unitId = topUnit.getId();
		}else{
			topUnit = unitService.getUnit(unitId);
		}
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		
		TreeNode treeNode1 = new TreeNode();
		treeNode1.setId(topUnit.getId());
		treeNode1.setParentId(BaseConstant.ZERO_GUID);
		treeNode1.setName(topUnit.getName());
		treeNode1.setDetailName(topUnit.getName());
		treeNode1.setType(TreeConstant.TREE_UNIT);
		treeNode1.setIcon(contextPath
				+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "01.png");
		treeNode1.setIsParent("true");
		treeNode1.setOpen(true);
		tnList.add(treeNode1);
		List<Mcodedetail> mcodedetails = mcodedetailService.getAllMcodeDetails("DM-XXLB");
		for(Mcodedetail mcodedetail: mcodedetails){
			TreeNode treeNode = new TreeNode();
			treeNode.setId(mcodedetail.getThisId());
			treeNode.setParentId(unitId);
			treeNode.setName(mcodedetail.getContent());
			treeNode.setDetailName(mcodedetail.getContent());
			treeNode.setType(TreeConstant.TREE_GROUP);
			treeNode.setIcon(contextPath
					+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "06.png");
			treeNode.setIsParent("true");
			tnList.add(treeNode);
		}
		List<Unit> list = unitService.getUnderlingSchools(unitId);
		Set<String> unitIdSet = new HashSet<String>();
		for(Unit unit:list){
			unitIdSet.add(unit.getId());
		}
		List<School> schools = schoolService.getSchools(unitIdSet.toArray(new String[0]));
		if (CollectionUtils.isNotEmpty(schools)) {
			for (School school : schools) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(school.getId());
				if(StringUtils.isBlank(school.getSchoolType())){
					treeNode.setParentId("99");
				}else{
					treeNode.setParentId(school.getSchoolType());
				}
				treeNode.setName(school.getName());
				treeNode.setDetailName(school.getName());
				treeNode.setType(TreeConstant.TREE_UNIT);
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "01.png");
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}
	
	public String getAllUserZTree(String unitId, String contextPath){
		List<User> list = userService.getUsers(unitId);
		Set<String> userIds = new HashSet<String>();
		for (User user : list) {
			userIds.add(user.getId());
		}
		Map<String, String> userMap = userService.getUserDetailNamesMap(userIds.toArray(new String[0]));
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (User user : list) {
				if(userMap.get(user.getId()) == null){
					continue;
				}
				TreeNode treeNode = new TreeNode();
				treeNode.setId(user.getId());
				treeNode.setParentId(unitId);
				treeNode.setName(user.getRealname());
				treeNode.setDetailName(userMap.get(user.getId()));
				treeNode.setIsParent("false");
				treeNode.setType(TreeConstant.TREE_USER);
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "03.png");
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}
	
	public String getAllDeptZTree(String unitId, String contextPath){
		Unit unit = unitService.getUnit(unitId);
		List<Dept> list = deptService.getDepts(unitId);
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Dept dept : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(dept.getId());
				treeNode.setParentId(unitId);
				treeNode.setName(dept.getDeptname());
				treeNode.setDetailName(dept.getDeptname()+"("+unit.getName()+")");
				treeNode.setIsParent("false");
				treeNode.setType(TreeConstant.TREE_DEPT);
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "02.png");
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}

	protected String composeZTree(String contextPath, String unitId)
			throws Exception {
		if (StringUtils.isBlank(unitId)) {
			Unit topUnit = unitService.getTopEdu();
			unitId = topUnit.getId();
		}
		List<Unit> list = unitService.getAllUnits(unitId, true);
		Collections.sort(list, new Comparator<Unit>() {
			public int compare(Unit o1, Unit o2) {
				return o1.getUnitclass().compareTo(o2.getUnitclass());
			}
		});
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Unit unit : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(unit.getId());
				treeNode.setParentId(unit.getParentid());
				treeNode.setName(unit.getName());
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "01.png");
				if (unit.getId().equals(unitId))
					treeNode.setOpen(true);
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}
	
	protected String composeDirectZTree(String contextPath, String unitId)
			throws Exception {
		if (StringUtils.isBlank(unitId)) {
			Unit topUnit = unitService.getTopEdu();
			unitId = topUnit.getId();
		}
		List<Unit> list = unitService.getUnderlingUnits(unitId, true);
		Collections.sort(list, new Comparator<Unit>() {
			public int compare(Unit o1, Unit o2) {
				return o1.getUnitclass().compareTo(o2.getUnitclass());
			}
		});
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Unit unit : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(unit.getId());
				treeNode.setParentId(unit.getParentid());
				treeNode.setName(unit.getName());
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "01.png");
				if (unit.getId().equals(unitId))
					treeNode.setOpen(true);
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}

	protected String composeZSearchTree(String contextPath, String unitId)
			throws Exception {
		if (StringUtils.isBlank(unitId)) {
			Unit topUnit = unitService.getTopEdu();
			unitId = topUnit.getId();
		}
		List<Unit> list = unitService.getAllUnits(unitId, true);
		Map<String, School> schoolMap = schoolService.getSchoolMap();
		Collections.sort(list, new Comparator<Unit>() {
			public int compare(Unit o1, Unit o2) {
				return o1.getUnitclass().compareTo(o2.getUnitclass());
			}
		});
		List<UnitTreeNode> tnList = new ArrayList<UnitTreeNode>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Unit unit : list) {
				UnitTreeNode treeNode = new UnitTreeNode();
				treeNode.setId(unit.getId());
				treeNode.setParentId(unit.getParentid());
				treeNode.setName(unit.getName());
				treeNode.setIcon(contextPath
						+ TreeConstant.ZTREE_ICON_PATH_PREFIX + "01.png");
				if (schoolMap.containsKey(unit.getId())) {
					School sch = schoolMap.get(unit.getId());
					if (StringUtils.isNotBlank(sch.getSchoolPropStat()))
						treeNode.setSchoolPropStat(sch.getSchoolPropStat());
					else
						treeNode.setSchoolPropStat(StringUtils.EMPTY);
					if (StringUtils.isNotBlank(sch.getSchoolTypeGroup()))
						treeNode.setSchoolTypeGroup(sch.getSchoolTypeGroup());
					else
						treeNode.setSchoolTypeGroup(StringUtils.EMPTY);
					if (StringUtils.isNotBlank(sch.getLedgerSchoolType()))
						treeNode.setLedgerSchoolType(sch.getLedgerSchoolType());
					else
						treeNode.setLedgerSchoolType(StringUtils.EMPTY);
				}
				if (StringUtils.isNotBlank(unit.getUnitEducationType()))
					treeNode.setUnitEducationType(unit.getUnitEducationType());
				else
					treeNode.setUnitEducationType(StringUtils.EMPTY);
				if (unit.getId().equals(unitId))
					treeNode.setOpen(true);
				tnList.add(treeNode);
			}
		}
		return JSON.toJSONString(tnList);
	}

	public String getXTree(String unitId, String type, String contextPath)
			throws Exception {
		String rootText = "所有单位";
		List<Unit> list;
		if (unitId != null && unitId.length() > 0) {
			if (type != null && type.length() > 0)
				list = unitService.getUnderlingUnits(unitId,
						Integer.valueOf(type));
			else
				list = unitService.getUnderlingUnits(unitId);
			Unit unitRoot = unitService.getUnit(unitId);
			if (unitRoot == null) {
				return null;
			}
			rootText = unitRoot.getName();
		} else {
			if (type != null && type.length() > 0)
				list = unitService.getUnderlingUnits(DEFAULT_UNIT_ID,
						Integer.valueOf(type));
			else
				list = unitService.getUnderlingUnits(DEFAULT_UNIT_ID);
		}

		return composeXTree(new XLoadTreeItem(rootText, rootNodeName, "0"),
				list, rootText, unitId, type, contextPath);
	}

	protected String composeXTree(XLoadTreeItem parentNode, List<Unit> list,
			String rootText, String unitId, String type, String contextPath)
			throws Exception {
		StringBuffer sbXTree = new StringBuffer();
		// 让root节点也可以触发事件
		Unit unitRoot = unitService.getUnit(unitId);

		XLoadTreeItem rootItem;
		if (unitRoot != null) {
			rootItem = new XLoadTreeItem(rootText, rootNodeName, "0",
					getAction(unitId, unitRoot.getUnitclass(),
							unitRoot.getName()));
			sbXTree.append(this.newWebFXTree(rootItem));
		} else {
			rootItem = new XLoadTreeItem(rootText, rootNodeName, "0",
					getAction(unitId));
			sbXTree.append(this.newWebFXTree(rootItem));
		}

		String actionUrl;

		Collections.sort(list, new Comparator<Unit>() {
			public int compare(Unit o1, Unit o2) {
				return o1.getUnitclass().compareTo(o2.getUnitclass());
			}
		});

		for (Iterator<Unit> it = list.iterator(); it.hasNext();) {
			Unit unit = (Unit) it.next();
			XLoadTreeItem unitItem = new XLoadTreeItem();
			unitItem.setText(unit.getName());

			unitItem.setAction(getAction(unit.getId(), unit.getUnitclass(),
					unit.getName()));
			unitItem.setInputValue(unit.getId());
			unitItem.setNodeName(makeXTreeItemName());

			if (unit.getUnitclass() == Unit.UNIT_CLASS_EDU) {
				unitItem.setIcon("'" + contextPath
						+ TreeConstant.TREE_ICON_PATH_PREFIX + "edu.gif'");
				unitItem.setOpenIcon("'" + contextPath
						+ TreeConstant.TREE_ICON_PATH_PREFIX
						+ "edu_selected.gif'");
			}
			if (unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL) {
				unitItem.setIcon("'" + contextPath
						+ TreeConstant.TREE_ICON_PATH_PREFIX + "school.gif'");
				unitItem.setOpenIcon("'" + contextPath
						+ TreeConstant.TREE_ICON_PATH_PREFIX
						+ "school_selected.gif'");
			}

			if (type != null && type.length() > 0)
				actionUrl = "/common/xtree/unittreexml.action?unitId="
						+ unit.getId() + "&type=" + type;
			else
				actionUrl = "/common/xtree/unittreexml.action?unitId="
						+ unit.getId();

			unitItem.setXmlSrc(contextPath + actionUrl);

			sbXTree.append("var " + unitItem.getNodeName() + " = "
					+ newWebFXTreeItem(unitItem) + ";\n");

			sbXTree.append(parentNode.getNodeName() + ".add("
					+ unitItem.getNodeName() + ");\n");
		}
		log.debug(sbXTree.toString());
		return sbXTree.toString();
	}

}
