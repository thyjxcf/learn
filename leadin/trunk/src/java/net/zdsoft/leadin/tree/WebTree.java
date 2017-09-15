/* 
 * @(#)WebTree.java    Created on 2006-9-29
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/tree/WebTree.java,v 1.1 2006/11/03 05:41:39 luxm Exp $
 */
package net.zdsoft.leadin.tree;

/**
 * @author luxingmu
 * @version $Revision: 1.1 $, $Date: 2006/11/03 05:41:39 $
 */
public class WebTree extends TreeNode {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5202741554697356065L;
	private String behavior;

	public WebTree(String text) {
		super(text);
		setNodeName("tree");
	}
	
	public WebTree(String text,String action,String behavior, String icon, String openIcon){
		super(text,action);
		this.behavior = behavior;
		setIcon(icon);
		setOpenIcon(openIcon);
		setNodeName("tree");
	}
	
	public String toString(){
		if(behavior == null || behavior.trim().equals("")){
			behavior = "classic";
		}
		StringBuilder sb = new StringBuilder();
		int count = 0;
		sb.append("var "+getNodeName()+" = new WebFXTree('"+getText()+"'");
		String[] argArray = {getAction(),getBehavior(),getIcon(),getOpenIcon()};
		combineConstructArg(sb, argArray);
		sb.append(");\n");
		String nodeNamePrefix = this.getNodeName()+"_node_";
		for (TreeNode treeNode : getChildrenList()) {
			if(treeNode.getChildrenList().isEmpty()){
				sb.append(getNodeName()+".add("+treeNode.toString()+");\n");
			}else{
				treeNode.setNodeName(nodeNamePrefix+count);
				count++;
				sb.append("var "+treeNode.getNodeName()+" = "+treeNode.toString()+";\n");
				sb.append(getNodeName()+".add("+treeNode.getNodeName()+");\n");
				sb.append(generateChildrenScript(treeNode,nodeNamePrefix,count));
			}
		}
		return sb.toString();
	}

	/**
	 * @param sb
	 * @param argArray
	 */
	protected void combineConstructArg(StringBuilder sb, String[] argArray) {
		int argLength = checkSuffixArgLength(argArray);
		for (int i = 0; i < argLength; i++) {
			if(argArray[i] != null){
				sb.append(",'"+argArray[i]+"'");
			}else{
				sb.append(",null");
			}
		}
	}
	
	public String generateChildrenScript(TreeNode parent,String nodeNamePrefix,int nodeCount){
		StringBuilder sb = new StringBuilder();
		for (TreeNode treeNode : parent.getChildrenList()) {
			if(treeNode.getChildrenList().isEmpty()){
				sb.append(parent.getNodeName()+".add("+treeNode.toString()+");\n");
			}else{
				treeNode.setNodeName(nodeNamePrefix+nodeCount);
				nodeCount++;
				sb.append("var "+treeNode.getNodeName()+" = "+treeNode.toString()+";\n");
				sb.append(parent.getNodeName()+".add("+treeNode.getNodeName()+");\n");
				sb.append(generateChildrenScript(treeNode,nodeNamePrefix,nodeCount));
			}
		}
		return sb.toString();
	}
	
	

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public static void main(String[] args) {
        WebTree tree = new WebTree("test");
        tree.addChildNode(new WebCheckBoxTreeNode("aa"));
        tree.addChildNode(new WebCheckBoxTreeNode("bb"));
        TreeNode node = new WebCheckBoxTreeNode("cc");
        node.addChildNode(new WebCheckBoxTreeNode("ee"));
        node.addChildNode(new WebCheckBoxTreeNode("ff"));
        node.addChildNode(new WebCheckBoxTreeNode("gg"));
        tree.addChildNode(node);
        tree.addChildNode(new WebCheckBoxTreeNode("dd"));
        System.out.println(tree.toString());
    }
}
