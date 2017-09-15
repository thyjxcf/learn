package net.zdsoft.eis.base.tree.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.subsystemcall.service.SubsystemPopedomService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * 学校端使用的学生树。展开层次：学校名称-[分校区]-班级-学生
 * 其中学生列表是动态加载的xml树节点
 * 数据权限需要控制和不需要控制，都可使用，只需设置needPopedomSturole即可
 * @author lilj
 * @since 1.0
 * @version $Id: StudentTree.java,v 1.17 2007/02/01 14:06:56 zhaosf Exp $
 */

public class StudentTree extends XTreeMaker {
	private Logger log = LoggerFactory.getLogger(StudentTree.class);

	private SchoolService schoolService;
	private BasicClassService basicClassService;
	private SemesterService semesterService;

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	/**
	 * 获取学生XTree树
	 * 
	 * @param schId
	 * @param allLinkOpen
	 * @param treeType 树的类别。see TreeConstant
	 *            中TREETYPE_*
	 * @param graduateAcadyear
	 * @param needPopedom 是否需要数据权限控制。 在需要控制的情况下，再判别平台参数设置中的“是否启用学籍数据权限”
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String getXTree(String schId, boolean allLinkOpen, String treeType,
			String graduateAcadyear, boolean needPopedom, User user,
			SubsystemPopedomService popedomService) throws Exception {

		if (StringUtils.isBlank(treeType))
			treeType = "" + TreeConstant.TREETYPE_STU_NORMAL;

		String rootText = schoolService.getSchool(schId).getName();

		List<SubSchool> list = schoolService.getSubSchools(schId);
		XLoadTreeItem root = new XLoadTreeItem(rootText, this.rootNodeName,
				schId, allLinkOpen ? getAction(schId,
						TreeConstant.ITEMTYPE_SCHOOL, rootText) : null);

		if (list.size() > 1) {// 如果分校区大于1个，则显示分校区
			return this.composeSubSchXTree(root, list, rootText, allLinkOpen,
					treeType, graduateAcadyear, needPopedom, user,
					popedomService);
		} else {// 分校区不大于1个，无需显示分校区
			return composeXTree(root, getClassListBySchid(schId, needPopedom,
					treeType, graduateAcadyear, user.getId(), popedomService),
					rootText, allLinkOpen, treeType);
		}

	}

	/**
	 * 无分校区（即只有一个校区）时，组装的XTree树。学校下直接是班级
	 * 
	 * @param parentNode
	 * @param list 班级DtoList
	 * @return
	 */
	protected String composeXTree(XLoadTreeItem parentNode,
			List<BasicClass> list, String rootText, boolean allLinkOpen,
			String treeType) {
		StringBuffer sbXTree = new StringBuffer();
		sbXTree.append(newWebFXTree(rootText));
		sbXTree.append(composeClassXTree(parentNode, list, allLinkOpen,
				treeType));
		log.debug(sbXTree.toString());
		return sbXTree.toString();
	}

	/**
	 * 组装班级子树
	 * 
	 * @param parentNode
	 * @param classList 班级DtoList
	 * @return
	 */
	protected String composeClassXTree(XLoadTreeItem parentNode,
			List<BasicClass> classList, boolean allLinkOpen, String treeType) {
		StringBuffer sbXTree = new StringBuffer();

		String sIcon = "webFXTreeConfig.classIcon";
		String sOpenIcon = "webFXTreeConfig.openClassIcon";

		String[] classIds = new String[classList.size()];
		for (int i = 0; i < classList.size(); i++) {
			classIds[i] = ((BasicClass) classList.get(i)).getId();
		}

		// Map<String, Integer> mapOfStudentCount =
		// basicClassService.getStuInfoCountByClassIds(classIds);

		for (Iterator<BasicClass> iteratorCls = classList.iterator(); iteratorCls
				.hasNext();) {
			BasicClass classDto = (BasicClass) iteratorCls.next();
			XLoadTreeItem classItem = new XLoadTreeItem();
			String count = "";
			// String count =
			// String.valueOf(mapOfStudentCount.get(classDto.getId()));
			// if (count != null && !"null".equals(count)){
			// count = " - " + count + "人";
			// }
			// else{
			// count = "";
			// }

			classItem.setText(classDto.getClassnamedynamic() + count);

			// 班级下的学生为动态展开树
			classItem.setXmlSrc(contextPath
					+ "/common/xtree/studenttreexml.action?classid="
					+ classDto.getId() + "&treeType=" + treeType);
			classItem.setInputValue(classDto.getId());
			classItem.setNodeName(makeXTreeItemName());
			classItem.setIcon(sIcon);
			classItem.setOpenIcon(sOpenIcon);
			if (allLinkOpen) {
				classItem.setAction(getAction(classDto.getId(),
						TreeConstant.ITEMTYPE_CLASS, classDto
								.getClassnamedynamic()));
			}
			sbXTree.append("var " + classItem.getNodeName() + " = "
					+ newWebFXTreeItem(classItem) + ";\n");

			sbXTree.append(parentNode.getNodeName() + ".add("
					+ classItem.getNodeName() + ");\n");

		}
		return sbXTree.toString();

	}

	/**
	 * 有分校区（即大于一个校区）时，组装的XTree树。学校下先是分校区，分校区下再是班级
	 * 
	 * @param parentNode
	 * @param list 分校区dtoList
	 * @return
	 */
	protected String composeSubSchXTree(XLoadTreeItem parentNode,
			List<SubSchool> list, String rootText, boolean allLinkOpen,
			String treeType, String graduateAcadyear, boolean needPopedom,
			User user, SubsystemPopedomService popedomService) throws Exception {
		StringBuffer sbXTree = new StringBuffer();
		sbXTree.append(newWebFXTree(rootText));
		String sIcon = "webFXTreeConfig.unitIcon";
		String sOpenIcon = "webFXTreeConfig.openUnitIcon";
		for (Iterator<SubSchool> iterator = list.iterator(); iterator.hasNext();) {
			SubSchool subSch = (SubSchool) iterator.next();

			XLoadTreeItem subSchNode = new XLoadTreeItem();
			subSchNode.setText(subSch.getName());
			subSchNode.setInputValue(subSch.getId());
			subSchNode.setNodeName(makeXTreeItemName());
			subSchNode.setIcon(sIcon);
			subSchNode.setOpenIcon(sOpenIcon);
			if (allLinkOpen) {
				subSchNode.setAction(getAction(subSch.getId(),
						TreeConstant.ITEMTYPE_SUBSCHOOL, subSch.getName()));
			}

			sbXTree.append("var " + subSchNode.getNodeName() + " = "
					+ newWebFXTreeItem(subSchNode) + ";\n");

			List<BasicClass> classList = getClassListBySubschid(subSch.getId(),
					needPopedom, treeType, graduateAcadyear, user.getId(),
					popedomService);

			sbXTree.append(composeClassXTree(subSchNode, classList,
					allLinkOpen, treeType));
			sbXTree.append(parentNode.getNodeName() + ".add("
					+ subSchNode.getNodeName() + ");\n");

		}
		log.debug(sbXTree.toString());
		return sbXTree.toString();
	}

	private List<BasicClass> getClassListBySubschid(String subschid,
			boolean needPopedom, String treeType, String graduateAcadyear,
			String userId, SubsystemPopedomService popedomService) {
		List<BasicClass> classList = null;
		if (needPopedom && popedomService.isPopedomUsed()) {
			int classState = TreeConstant.getClassState(Integer
					.parseInt(treeType));
			classList = popedomService.getPopedomClassesByCampus(subschid,
					classState, graduateAcadyear, userId);
		} else {
			switch (Integer.parseInt(treeType)) {
			case TreeConstant.TREETYPE_STU_GRADUATE:
				classList = basicClassService.getGraduatedClasses(subschid,
						graduateAcadyear);
				break;
			default:
				classList = basicClassService.getClassesByCampusId(subschid);
			}
		}
		return classList;
	}

	// 根据学校ID得到班级DTO列表
	private List<BasicClass> getClassListBySchid(String schid,
			boolean needPopedom, String treeType, String graduateAcadyear,
			String userId, SubsystemPopedomService popedomService)
			throws Exception {
		List<BasicClass> list;
		if (needPopedom && popedomService.isPopedomUsed()) {
			int classState = TreeConstant.getClassState(Integer
					.parseInt(treeType));
			list = popedomService.getPopedomClassesBySch(schid, classState,
					graduateAcadyear, userId);
		} else {
			switch (Integer.parseInt(treeType)) {
			case TreeConstant.TREETYPE_STU_GRADUATE:
				list = basicClassService.getGraduatedClasses(schid,
						graduateAcadyear);
				break;
			case TreeConstant.TREETYPE_STU_GRADUATING:
				if (graduateAcadyear == null)
					graduateAcadyear = semesterService.getCurrentAcadyear();
				list = basicClassService.getGraduatingClasses(schid,
						graduateAcadyear);
				break;
			default:
				list = basicClassService.getClasses(schid);
			}
		}

		return list;
	}
}
