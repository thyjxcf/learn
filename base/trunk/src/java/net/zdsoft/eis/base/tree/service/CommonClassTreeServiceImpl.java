package net.zdsoft.eis.base.tree.service;

import java.util.Iterator;
import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 学校端使用的学生树。展开层次：学校名称-班级-学生 其中学生列表是动态加载的xml树节点 供其它子系统调用的通用班级学生树
 * 
 * @author zhaosf
 * @since 1.0
 * @version $Id: CommonClassTree.java, v 1.0 2007-11-23 上午10:15:43 zhaosf Exp $
 */

public class CommonClassTreeServiceImpl extends XTreeMaker implements CommonClassTreeService {
    private Logger log = LoggerFactory.getLogger(CommonClassTreeServiceImpl.class);

    @SuppressWarnings("unused")
    private final static String TREE_LEVEL_CLASS = "1";// 班级级别
    private final static String TREE_LEVEL_STU = "2";// 学生级别

    private SchoolService schoolService;
    private BasicClassService basicClassService;

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * 获取学生XTree树
     */
    public String getXTree(String schid, String contextPath, String treeType, boolean allLinkOpen,
            String treeLevel, List<BasicClass> classList) throws Exception {

        String rootText = schoolService.getSchool(schid).getName();
        if (treeType == null) {
            treeType = "" + TreeConstant.TREETYPE_STU_NORMAL;
        }
        if (null == classList) {
            classList = this.getClassListBySchid(schid);
        }
        List<SubSchool> list = schoolService.getSubSchools(schid);
        XLoadTreeItem root = new XLoadTreeItem(rootText, this.rootNodeName, schid,
                allLinkOpen ? getAction(schid, TreeConstant.ITEMTYPE_SCHOOL, rootText) : null);

        if (list.size() > 1) {// 如果分校区大于1个，则显示分校区
            log.info("暂不支持分校区");
            return newWebFXTree(rootText);
        } else {// 分校区不大于1个，无需显示分校区
            return composeXTree(root, classList, contextPath, treeType, allLinkOpen, treeLevel);
        }

    }

    /**
     * 无分校区（即只有一个校区）时，组装的XTree树。学校下直接是班级
     * 
     * @param parentNode
     * @param list 班级DtoList
     * @return
     */
    protected String composeXTree(XLoadTreeItem parentNode, List<BasicClass> list,
            String contextPath, String treeType, boolean allLinkOpen, String treeLevel) {
        StringBuffer sbXTree = new StringBuffer();
        sbXTree.append(this.newWebFXTree(parentNode));
        sbXTree.append(composeClassXTree(parentNode, list, contextPath, treeType, allLinkOpen,
                treeLevel));
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
    protected String composeClassXTree(XLoadTreeItem parentNode, List<BasicClass> classList,
            String contextPath, String treeType, boolean allLinkOpen, String treeLevel) {
        StringBuffer sbXTree = new StringBuffer();

        String sIcon = "webFXTreeConfig.classIcon";
        String sOpenIcon = "webFXTreeConfig.openClassIcon";

        for (Iterator<BasicClass> iteratorCls = classList.iterator(); iteratorCls.hasNext();) {
            BasicClass classDto = (BasicClass) iteratorCls.next();
            XLoadTreeItem classItem = new XLoadTreeItem();

            classItem.setText(classDto.getClassnamedynamic());

            if (TREE_LEVEL_STU.equals(treeLevel)) {
                // 班级下的学生为动态展开树
                classItem.setXmlSrc(contextPath + "/common/xtree/studenttreexml.action?classid="
                        + classDto.getId() + "&treeType=" + treeType);
            }

            classItem.setInputValue(classDto.getId());
            classItem.setNodeName(makeXTreeItemName());
            classItem.setIcon(sIcon);
            classItem.setOpenIcon(sOpenIcon);
            if (allLinkOpen) {
                classItem.setAction(getAction(classDto.getId(), TreeConstant.ITEMTYPE_CLASS,
                        classDto.getClassnamedynamic()));
            }
            sbXTree.append("var " + classItem.getNodeName() + " = " + newWebFXTreeItem(classItem)
                    + ";\n");

            sbXTree.append(parentNode.getNodeName() + ".add(" + classItem.getNodeName() + ");\n");

        }

        return sbXTree.toString();
    }

    protected List<BasicClass> getClassListBySchid(String schid) throws Exception {
        return basicClassService.getClasses(schid);
    }

}
