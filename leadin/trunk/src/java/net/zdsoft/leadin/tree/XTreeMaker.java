package net.zdsoft.leadin.tree;

import org.apache.commons.lang.StringUtils;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * <p>XTree树基类</p>
 * @author lilj
 * @since 1.0
 * @version $Id: XTreeMaker.java,v 1.7 2006/12/25 06:43:33 zhaosf Exp $
 */

public abstract class XTreeMaker {
    /**
     * 树节点点击触发的方法名
     */
    public static final String TREE_CLICK_METHOD = "javascript:treeItemClick";

    protected ThreadLocal<Integer> xtreeItemCount = new ThreadLocal<Integer>();

    protected String rootNodeName = "tree";

    protected XLoadTreeItem xLoadTreeItem;

    protected String contextPath;

    /**
     * 是否所有的树结点名称点击都需要开放链接。
     */

    public XTreeMaker() {
        xtreeItemCount.set(Integer.valueOf(0));
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
 
    protected String makeXTreeItemName() {
        Integer value = xtreeItemCount.get();
        int cnt = 0;
        if (null == value) {
            cnt = 1;
        } else {
            cnt = value.intValue() + 1;
        }
        xtreeItemCount.set(Integer.valueOf(cnt));
        return "node" + String.valueOf(cnt);
    }

    protected String indent(int level) {
        StringBuffer margin = new StringBuffer();
        for (int i = 0; i < level; i++)
            margin.append("\t");

        return margin.toString();
    }

    @SuppressWarnings("deprecation")
    protected String newWebFXTreeItem(XLoadTreeItem treeItem) {
        return treeItem.toString();
    }

    protected String newWebFXTree(String rootText) {
        StringBuffer sbXTree = new StringBuffer("var ");
        sbXTree.append(this.rootNodeName);
        sbXTree.append(" = ");
        sbXTree.append("new WebFXTree('");
        sbXTree.append(rootText);
        sbXTree.append("');\n");
        return sbXTree.toString();
    }

    protected String newWebFXTree(XLoadTreeItem treeRoot) {
        StringBuffer sbXTree = new StringBuffer("var ");
        sbXTree.append(treeRoot.getNodeName());
        sbXTree.append(" = ");
        sbXTree.append("new WebFXTree('");
        sbXTree.append(treeRoot.getText());
        if (treeRoot.getAction() != null && treeRoot.getAction().trim().length() > 0) {
            sbXTree.append("','" + treeRoot.getAction());
        }
        sbXTree.append("');\n");
        return sbXTree.toString();
    }

    public static String getAction(String action) {
        StringBuffer sb = new StringBuffer(TREE_CLICK_METHOD);
        sb.append("(\\'");
        sb.append(action);
        sb.append("\\')");
        return sb.toString();
    }

    public static String getAction(String action, int itemType) {
        StringBuffer sb = new StringBuffer(TREE_CLICK_METHOD);
        sb.append("(\\'");
        sb.append(action);
        sb.append("\\',\\'");
        sb.append(itemType);
        sb.append("\\')");
        return sb.toString();
    }

    public static String getAction(String action, int itemType, String name) {
        StringBuffer sb = new StringBuffer(TREE_CLICK_METHOD);
        sb.append("(\\'");
        sb.append(action);
        sb.append("\\',\\'");
        sb.append(itemType);
        sb.append("\\',\\'");
        sb.append(name);
        sb.append("\\')");
        return sb.toString();
    }

    public static String getAction(String action, int itemType, String name, String parentId) {
        StringBuffer sb = new StringBuffer(TREE_CLICK_METHOD);
        sb.append("(\\'");
        sb.append(action);
        sb.append("\\',\\'");
        sb.append(itemType);
        sb.append("\\',\\'");
        sb.append(name);
        sb.append("\\',\\'");
        sb.append(parentId);
        sb.append("\\')");
        return sb.toString();
    }
    
    public static String getAction(String action,String name, String unitId) {
        StringBuffer sb = new StringBuffer(TREE_CLICK_METHOD);
        sb.append("(\\'");
        sb.append(action);
        sb.append("\\',\\'");
        sb.append(name);
        sb.append("\\',\\'");
        sb.append(unitId);
        sb.append("\\')");
        return sb.toString();
    }

    /**
     * 生成一个js脚本，点击项目时激活treeItemClick（id,type,name）
     * @param id
     * @return
     */
    public static String creatAction(String id) {
        String action;

        if (id == null)
            id = "";

        // 因为一下3个将被'扩起来，所以要替换里面的'为\'
        id = id.replaceAll("'", "\\'");

        action = TREE_CLICK_METHOD + "('" + id + "')";
        return action;
    }

    /**
     * 生成一个js脚本，点击项目时激活treeItemClick（id,type,name）
     * 
     * @param id
     * @param type
     * @param name
     * @return
     */
    public static String creatAction(String id, int type, String name) {
        return creatAction(id, type, name, "");
    }

    /**
     * 生成一个js脚本，点击项目时激活treeItemClick（id,type,name,param4）
     * 
     * @param id
     * @param type
     * @param name
     * @return
     */
    public static String creatAction(String id, int type, String name, String param4) {
        return creatAction(id, type, name, param4, "");
    }

    public static String creatAction(String id, int type, String name, String param4, String param5) {
        String action;

        if (id == null)
            id = "";
        if (name == null)
            name = "";

        if (param4 == null)
            param4 = "";
        if (param5 == null)
            param5 = "";

        // 因为一下4个将被'扩起来，所以要替换里面的'为\'
        id = id.replaceAll("'", "\\'");
        name = name.replaceAll("'", "\\'");
        param4 = param4.replaceAll("'", "\\'");
        param5 = param5.replaceAll("'", "\\'");

        String optionParams = "";//可选参数            
        if (StringUtils.isNotBlank(param4)) {
            optionParams += ",'" + param4 + "'";
        }
        if (StringUtils.isNotBlank(param5)) {
            optionParams += ",'" + param5 + "'";
        }
        action = TREE_CLICK_METHOD + "('" + id + "','" + type + "','" + name + "'" + optionParams
                + ")";
        return action;
    }
}
