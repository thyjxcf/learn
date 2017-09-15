package net.zdsoft.eis.base.tree.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class DeptTreeXmlAction extends BaseTreeXmlModelAction {
    private static final long serialVersionUID = -5095481309397148781L;

    private String deptId;
    public String deptTreeXml;
    private DeptService deptService;

    public String execute() throws Exception {
        deptTreeXml = getDeptTreeXml(deptService.getDeptsByParentId(deptId));
        return SUCCESS;
    }

    public String getDeptTreeXml(List<Dept> list) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("tree");
        Element item = null;
        if (list != null) {
            Iterator<Dept> ite = list.iterator();
            Dept dept;
            while (ite.hasNext()) {
                dept = ite.next();
                item = root.addElement("tree");
                item.addAttribute("text", dept.getDeptname());
                item.addAttribute("action", getAction(dept.getId(), dept.getDeptname(), dept
                        .getUnitId()));
                item.addAttribute("src", ServletActionContext.getRequest().getContextPath()
                        + "/common/xtree/depttreexml.action?deptId=" + dept.getId());
                item.addAttribute("icon", getContextPath() + TreeConstant.TREE_ICON_PATH_PREFIX
                        + "department.gif");
                item.addAttribute("openIcon", getContextPath() + TreeConstant.TREE_ICON_PATH_PREFIX
                        + "department_selected.gif");
            }
        }

        return this.xmlDomToString(doc);
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public void setDeptTreeXml(String deptTreeXml) {
        this.deptTreeXml = deptTreeXml;
    }

    // ======================改造后==============================
    private XLoadTreeItemConverter<Dept> deptTreeItemConverter;

    public void setDeptTreeItemConverter(XLoadTreeItemConverter<Dept> deptTreeItemConverter) {
        this.deptTreeItemConverter = deptTreeItemConverter;
    }

    /**
     * 点击学校或部门节点，展示部门
     * 
     * @return
     * @throws Exception
     */
    public String expandDept() throws Exception {
        List<XLoadTreeItem> items = getDeptItems();

        return buildTreeXml(items);
    }

    protected List<XLoadTreeItem> getDeptItems() {
        List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();

        // 部门节点
        if (TreeConstant.getTreeDepth(treeParam.getLayer()) >= TreeConstant
                .getTreeDepth(TreeConstant.ITEMTYPE_DEPARTMENT)) {
            List<Dept> list = deptService.getDeptsByParentId(treeParam.getParentId());
            List<XLoadTreeItem> _items = deptTreeItemConverter.buildTreeItems(list, treeParam);
            items.addAll(_items);
        }

        return items;
    }
}
