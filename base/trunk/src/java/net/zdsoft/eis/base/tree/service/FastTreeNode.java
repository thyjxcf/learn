package net.zdsoft.eis.base.tree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快速树结构。树的根节点和子节点是一样的。 构建好树节点后，调用根节点的toString()即可生成快速树的数据结构。
 * 
 * @author zhangza
 * @date 2010-7-7
 */
public class FastTreeNode {
    private String id;
    private String text;
    private String otherJson;
    @SuppressWarnings("unused")
    private String icon;
    private List<FastTreeNode> children = new ArrayList<FastTreeNode>();// 按照存放顺序
    private Map<String, FastTreeNode> childrenMap = new HashMap<String, FastTreeNode>();// 为getchild使用

    // icon，（icon）type，checked

    /**
     * @param id
     * @param text
     * @param otherJson
     *            //例如："'checked':0,'fulltext':'这样','other':'那样'"
     *            //其中checked 选择框选中状态 0 1 2
     */
    public FastTreeNode(String id, String text, String otherJson) {
        this.id = id;
        this.text = text;
        this.otherJson = otherJson;
    }

    public FastTreeNode(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public void addChild(FastTreeNode treeNode) {
        children.add(treeNode);
        childrenMap.put(treeNode.getId(), treeNode);
    }

    /**
     * 会查找所有层级的下级子节点
     * @param id
     * @return
     */
    public FastTreeNode getChild(String id) {
        FastTreeNode child = childrenMap.get(id);
        if(child == null){
            child = getChildMore(id,this.getChildren());
        }
        return child;
    }
    
    public List<FastTreeNode> getChildren(){
        return this.children;
    }

    //迭代查找所有的下级子节点
    private FastTreeNode getChildMore(String id,List<FastTreeNode> children){
        for(FastTreeNode child : children){
            FastTreeNode node = child.getChild(id);
            if(node != null) return node;
            if(child.hasChildren()) getChildMore(id,child.getChildren());
        }
        return null;
    }
    
    
    public boolean hasChildren(){
        return children.size()==0?false:true;
    }
    /*
     * {'id':'980000','text':'其他省','pid':'98','children':[{
     * 'id':'710000','text':'台湾省','pid':'71'},{'id':'810000','text':'香港特别行政区','pid':'81'},
     * {
     * 'id':'820000','text':'澳门特别行政区','pid':'82'},{'id':'990000','text':'海外','pid':'99'}
     * ]};
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{'id':'");
        sb.append(id);
        sb.append("','text':'");
        sb.append(text);
        sb.append("'");
        if (otherJson != null && otherJson.trim().length() > 0) {
            sb.append(",");
            sb.append(this.otherJson);
        }
        if (!this.children.isEmpty()) {
            sb.append(",'children':[");
            for (FastTreeNode treeNode : children) {
                sb.append(treeNode.toString());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        FastTreeNode rootTreeNode = new FastTreeNode("1001", "节点喜讯你");

        FastTreeNode childTreeNode = new FastTreeNode("1002", "子节点2");
        FastTreeNode childTreeNode2 = new FastTreeNode("100001", "子节点001");
        childTreeNode.addChild(childTreeNode2);
        rootTreeNode.addChild(childTreeNode);

        childTreeNode = new FastTreeNode("1003", "子节点3");
        rootTreeNode.addChild(childTreeNode);

        childTreeNode = new FastTreeNode("1004", "子节点4");
        rootTreeNode.addChild(childTreeNode);

        System.out.println(rootTreeNode.toString());
    }

}
