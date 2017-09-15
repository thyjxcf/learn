package net.zdsoft.eis.base.tree.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.subsystemcall.service.SubsystemPopedomService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * 学校端使用的班级树。展开层次：学校名称-[分校区]-班级
 * 数据权限需要控制和不需要控制，都可使用，只需设置needPopedomSturole即可
 * @author lilj
 * @since 1.0
 * @version $Id: ClassTree.java,v 1.22 2007/01/24 07:16:52 linqz Exp $
 */

public class ClassTree extends XTreeMaker {
    private Logger log = LoggerFactory.getLogger(ClassTree.class);

    private SchoolService schoolService;
    private BasicClassService basicClassService;

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public ClassTree() {
    }

    /**
     * 获取班级XTree树
     * 
     * @param schId
     * @param allLinkOpen
     * @param treeType 班级树类型
     * @param graduateAcadyear 毕业学年（主要用于显示毕业班级树时使用）
     * @param graduatesign 已毕业标志
     * @param needPopedom 是否需要数据权限控制。 在需要控制的情况下，再判别平台参数设置中的“是否启用学籍数据权限”
     * @param user 登陆用户
     * @param popedomService 权限
     * @param curAcadyear 当前学年
     * @return 
     * @throws Exception
     */
    public String getXTree(String schId, boolean allLinkOpen, String treeType,
            String graduateAcadyear, String graduatesign, boolean needPopedom, User user,
            SubsystemPopedomService popedomService, String curAcadyear) throws Exception {

        if (StringUtils.isBlank(treeType))
            treeType = "" + TreeConstant.TREETYPE_CLASS_NORMAL;

        String rootText = schoolService.getSchool(schId).getName();
        log.debug(rootText);
        List<SubSchool> list = schoolService.getSubSchools(schId);
        XLoadTreeItem root = new XLoadTreeItem(rootText, this.rootNodeName, schId,
                allLinkOpen ? getAction(schId, TreeConstant.ITEMTYPE_SCHOOL) : null);

        if (list.size() > 1) {// 如果分校区大于1个，则显示分校区
            return this.composeSubSchXTree(root, list, allLinkOpen, treeType, graduateAcadyear,
                    graduatesign, needPopedom, user, popedomService, curAcadyear);
        } else {// 分校区不大于1个，无需显示分校区

            return composeXTree(root, getClassListBySchid(schId, needPopedom, treeType,
                    graduatesign, graduateAcadyear, user, popedomService, curAcadyear));
        }

    }

    /**
     * 无分校区（即只有一个校区）时，组装的XTree树。学校下直接是班级
     * 
     * @param parentNode
     * @param list 班级DtoList
     * @return
     */
    protected String composeXTree(XLoadTreeItem parentNode, List<BasicClass> list) {
        StringBuffer sbXTree = new StringBuffer();
        sbXTree.append(this.newWebFXTree(parentNode));
        sbXTree.append(composeClassXTree(parentNode, list));
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
    protected String composeClassXTree(XLoadTreeItem parentNode, List<BasicClass> classList) {
        if (classList == null) {
            classList = new ArrayList<BasicClass>();
        }
        StringBuffer sbXTree = new StringBuffer();

        String sIcon = "webFXTreeConfig.classIcon";
        String sOpenIcon = "webFXTreeConfig.openClassIcon";

        String[] classIds = new String[classList.size()];
        for (int i = 0; i < classList.size(); i++) {
            classIds[i] = ((BasicClass) classList.get(i)).getId();
        }

        for (Iterator<BasicClass> iteratorCls = classList.iterator(); iteratorCls.hasNext();) {

            BasicClass classDto = (BasicClass) iteratorCls.next();
            XLoadTreeItem classItem = new XLoadTreeItem();

            if ("1".equals(classDto.getGraduatesign() + "")) {
                classItem.setText(classDto.getClassnamedynamic() + "(已毕业)");
            } else {
                classItem.setText(classDto.getClassnamedynamic());
            }
            classItem.setInputValue(classDto.getId());
            classItem.setNodeName(makeXTreeItemName());
            classItem.setAction(getAction(classDto.getId(),
                    TreeConstant.ITEMTYPE_CLASS, classDto.getClassnamedynamic()));
            classItem.setIcon(sIcon);
            classItem.setOpenIcon(sOpenIcon);

            sbXTree.append("var " + classItem.getNodeName() + " = " + newWebFXTreeItem(classItem)
                    + ";\n");

            sbXTree.append(parentNode.getNodeName() + ".add(" + classItem.getNodeName() + ");\n");

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
    protected String composeSubSchXTree(XLoadTreeItem parentNode, List<SubSchool> list,
            boolean allLinkOpen, String treeType, String graduateAcadyear, String graduatesign,
            boolean needPopedom, User user, SubsystemPopedomService popedomService, String curAcadyear)
            throws Exception {
        StringBuffer sbXTree = new StringBuffer();
        sbXTree.append(this.newWebFXTree(parentNode));
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
                        TreeConstant.ITEMTYPE_SUBSCHOOL));
            }

            sbXTree.append("var " + subSchNode.getNodeName() + " = " + newWebFXTreeItem(subSchNode)
                    + ";\n");

            List<BasicClass> classList = getClassListBySubschid(subSch.getId(), needPopedom,
                    treeType, graduatesign, graduateAcadyear, user, popedomService, curAcadyear);

            sbXTree.append(composeClassXTree(subSchNode, classList));
            sbXTree.append(parentNode.getNodeName() + ".add(" + subSchNode.getNodeName() + ");\n");

        }
        log.debug(sbXTree.toString());
        return sbXTree.toString();
    }

    // 根据学校ID得到班级DTO列表
    protected List<BasicClass> getClassListBySchid(String schid, boolean needPopedom,
            String treeType, String graduatesign, String graduateAcadyear, User user,
            SubsystemPopedomService popedomService, String curAcadyear) throws Exception {
        List<BasicClass> list;
        if (needPopedom && popedomService.isPopedomUsed()) {
            int classState = TreeConstant.getClassState(Integer.parseInt(treeType), graduatesign);
            list = popedomService.getPopedomClassesBySch(schid, classState, graduateAcadyear,
                    user.getId());
        } else {
            switch (Integer.parseInt(treeType)) {
            case TreeConstant.TREETYPE_CLASS_NORMAL:
                if ("1".equals(graduatesign)) {
                    list = basicClassService.getClasses(schid, curAcadyear);
                } else {
                    list = basicClassService.getClasses(schid);
                }                
                break;
            case TreeConstant.TREETYPE_CLASS_GRADUATING:
                if ("1".equals(graduatesign)) {
                    list = basicClassService.getGraduatedClasses(schid, graduateAcadyear);
                } else {
                    list = basicClassService.getGraduatingClasses(schid, graduateAcadyear);
                }
                break;
            default:
                list = basicClassService.getClasses(schid, curAcadyear);
            }
        }

        return list;
    }

    // 根据分校区ID得到班级DTO列表
    protected List<BasicClass> getClassListBySubschid(String subschid, boolean needPopedom,
            String treeType, String graduatesign, String graduateAcadyear, User user,
            SubsystemPopedomService popedomService, String curAcadyear) throws Exception {
        List<BasicClass> list;
        if (needPopedom && popedomService.isPopedomUsed()) {
            int classState = TreeConstant.getClassState(Integer.parseInt(treeType), graduatesign);
            list = popedomService.getPopedomClassesByCampus(subschid, classState, graduateAcadyear,
                    user.getId());
        } else {
            switch (Integer.parseInt(treeType)) {
            case TreeConstant.TREETYPE_CLASS_NORMAL:
                list = basicClassService.getClassesByCampusId(subschid);
                break;
            case TreeConstant.TREETYPE_CLASS_GRADUATING:
                if ("1".equals(graduatesign)) {
                    list = basicClassService.getGraduatedClassesByCampusId(subschid,
                            graduateAcadyear);
                } else {
                    list = basicClassService.getGraduatingClassesByCampusId(subschid,
                            graduateAcadyear);
                }
                break;
            default:
                list = basicClassService.getClassesByCampusId(subschid);
            }
        }

        return list;
    }

}
