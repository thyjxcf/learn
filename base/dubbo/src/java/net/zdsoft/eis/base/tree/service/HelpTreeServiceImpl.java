package net.zdsoft.eis.base.tree.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.BasicModule;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.service.BasicModuleService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * 
 * @author zhaosf
 * @since 1.0
 * @version $Id: HelpTree.java, v 1.0 2007-12-3 下午02:03:43 zhaosf Exp $
 */
public class HelpTreeServiceImpl extends XTreeMaker implements HelpTreeService {
    private static Logger log = LoggerFactory.getLogger(HelpTreeServiceImpl.class);

    private SubSystemService subSystemService;// 子系统
    private SystemVersionService systemVersionService;// 平台

    public String getXTree(String contextPath, BasicModuleService basicModuleService,
            Set<Integer> subsystems, int unitClass) throws Exception {
        SystemVersion sv = systemVersionService.getSystemVersion();
        String rootText = sv.getName();

        XLoadTreeItem parentNode = new XLoadTreeItem(rootText, rootNodeName, "0");

        StringBuffer sbXTree = new StringBuffer();
        sbXTree.append(this.newWebFXTree(parentNode));

        for (Integer subsystemId : subsystems) {
            sbXTree.append(getXTree(contextPath, basicModuleService, parentNode, subsystemId,
                    unitClass, BaseConstant.PLATFORM_TEACHER));
        }
        return sbXTree.toString();
    }

    public String getXTree(String contextPath, BasicModuleService basicModuleService,
            int subsystem, int unitClass, int platform) throws Exception {
        return getXTree(contextPath, basicModuleService, null, subsystem, unitClass, platform);
    }

    private String getXTree(String contextPath, BasicModuleService basicModuleService,
            XLoadTreeItem parentNode, int subsystem, int unitClass, int platform) throws Exception {
        SubSystem system = subSystemService.getSubSystem(subsystem);
        if (null == system) {
            log.error("找不到子系统：subsystemId=" + subsystem);
            return "";
        }

        String rootText = system.getName();
        String path = system.getHelpPath(platform);

        List<BasicModule> list = basicModuleService.getBasicModules(platform, subsystem, unitClass, Long
                .valueOf(-1));

        StringBuffer sbXTree = new StringBuffer();
        if (null == parentNode) {
            parentNode = new XLoadTreeItem(rootText, rootNodeName, "0");
            sbXTree.append(this.newWebFXTree(parentNode));
            sbXTree.append(composeXTree(contextPath, basicModuleService, parentNode, list,
                    subsystem, unitClass, path, platform));
        } else {
            XLoadTreeItem item = new XLoadTreeItem();
            item.setText(rootText);
            item.setNodeName(makeXTreeItemName());
            item.setInputValue(String.valueOf(system.getId()));
            String imgPrefix = "'" + contextPath + TreeConstant.TREE_ICON_PATH_PREFIX;
            item.setIcon(imgPrefix + "foldericon.png'");
            item.setOpenIcon(imgPrefix + "openfoldericon.png'");

            sbXTree.append("var " + item.getNodeName() + " = " + newWebFXTreeItem(item) + ";\n");
            sbXTree.append(parentNode.getNodeName() + ".add(" + item.getNodeName() + ");\n");
            sbXTree.append(composeXTree(contextPath, basicModuleService, item, list, subsystem,
                    unitClass, path, platform));
        }

        return sbXTree.toString();
    }

    protected String composeXTree(String contextPath, BasicModuleService basicModuleService,
            XLoadTreeItem parentNode, List<BasicModule> list, int subsystem, int unitClass,
            String path, int platform) throws Exception {
        if (null == list || list.size() == 0)
            return "";

        String imgPrefix = "'" + contextPath + TreeConstant.TREE_ICON_PATH_PREFIX;
        StringBuffer sbXTree = new StringBuffer();
        for (Iterator<BasicModule> it = list.iterator(); it.hasNext();) {
            BasicModule basicModule = (BasicModule) it.next();

            XLoadTreeItem item = new XLoadTreeItem();
            item.setText(basicModule.getName());
            item.setNodeName(makeXTreeItemName());
            item.setInputValue(String.valueOf(basicModule.getId()));

            if ("dir".equals(basicModule.getType())) {
                item.setIcon(imgPrefix + "foldericon.png'");
                item.setOpenIcon(imgPrefix + "openfoldericon.png'");
            } else {
                item.setIcon(imgPrefix + "file.png'");
                item.setOpenIcon(imgPrefix + "file.png'");
                item.setAction(getAction(path + basicModule.getMid().toLowerCase() + ".htm"));
            }

            sbXTree.append("var " + item.getNodeName() + " = " + newWebFXTreeItem(item) + ";\n");

            sbXTree.append(parentNode.getNodeName() + ".add(" + item.getNodeName() + ");\n");

            if ("dir".equals(basicModule.getType())) {
                List<BasicModule> subList = basicModuleService.getBasicModules(platform,
                        subsystem, unitClass, basicModule.getId());
                sbXTree.append(composeXTree(contextPath, basicModuleService, item, subList,
                        subsystem, unitClass, path, platform));
            }
        }
        log.debug(sbXTree.toString());
        return sbXTree.toString();
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setSystemVersionService(SystemVersionService systemVersionService) {
        this.systemVersionService = systemVersionService;
    }

}
