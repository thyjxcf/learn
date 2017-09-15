package net.zdsoft.eis.base.tree.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.leadin.tree.XTreeMaker;

public class UnitTreeXmlAction extends BaseTreeXmlAction {
    private static final long serialVersionUID = -4397004319660519601L;

    private UnitService unitService;

    private String unitId;
    private String unitTreeXml;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public String getUnitTreeXml() {
        return unitTreeXml;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String execute() throws Exception {
        log.debug("unitId=" + unitId);
        List<Unit> list;
        if (type != null && type.length() > 0)
            list = unitService.getUnderlingUnits(unitId, Integer.valueOf(type));
        else
            list = unitService.getUnderlingUnits(unitId);

        unitTreeXml = getUnitTreeXml(list);
        return SUCCESS;
    }

    /**
     * 组装成<满足动态XTree格式要求>的部门树xml
     * 
     * @param unitList Unit entity list
     * @return
     */
    private String getUnitTreeXml(List<Unit> unitList) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("tree");
        Element item = null;

        String actionUrl;

        if (unitList != null) {
            Collections.sort(unitList, new Comparator<Unit>() {
                public int compare(Unit o1, Unit o2) {
                    return o1.getUnitclass().compareTo(o2.getUnitclass());
                }
            });
            
            String path = getRequest().getContextPath() + TreeConstant.TREE_ICON_PATH_PREFIX;
            for (Iterator<Unit> iterator = unitList.iterator(); iterator.hasNext();) {
                item = root.addElement("tree");
                Unit unit = (Unit) iterator.next();
                item.addAttribute("text", unit.getName());
                item.addAttribute("action", XTreeMaker.creatAction(unit.getId(), unit
                        .getUnitclass(), unit.getName()));

                if (type != null && type.length() > 0) {
                    actionUrl = "/common/xtree/unittreexml.action?unitId=" + unit.getId()
                            + "&type=" + type;
                } else {
                    actionUrl = "/common/xtree/unittreexml.action?unitId=" + unit.getId();
                }

                if (unit.getUnitclass() == Unit.UNIT_CLASS_EDU) {
                    item.addAttribute("icon", path + "edu.gif");
                    item.addAttribute("openIcon", path + "edu_selected.gif");
                }
                if (unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL) {
                    item.addAttribute("icon", path + "school.gif");
                    item.addAttribute("openIcon", path + "school_selected.gif");
                }

                item.addAttribute("src", ServletActionContext.getRequest().getContextPath()
                        + actionUrl);
            }

        }

        return this.xmlDomToString(doc);
    }

}
