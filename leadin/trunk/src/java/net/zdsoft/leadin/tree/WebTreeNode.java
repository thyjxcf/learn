/* 
 * @(#)WebTreeNode.java    Created on 2006-9-29
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/tree/WebTreeNode.java,v 1.2 2006/12/13 07:51:27 luxm Exp $
 */
package net.zdsoft.leadin.tree;

/**
 * @author luxingmu
 * @version $Revision: 1.2 $, $Date: 2006/12/13 07:51:27 $
 */
public class WebTreeNode extends TreeNode {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8026171637439815211L;
	private TreeNode parent;

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public WebTreeNode(String text) {
		super(text);
	}

	public WebTreeNode(String text, String action) {
		super(text, action);
	}

	public WebTreeNode(String text, String action, TreeNode parent,
			String icon, String openIcon) {
		super(text, action);
		setParent(parent);
		setIcon(icon);
		setOpenIcon(openIcon);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("new WebFXTreeItem('" + getText() + "'");
		String[] argArray = { getAction(),
				getParent() != null ? getParent().getNodeName() : null,
				getIcon(), getOpenIcon() };
		specialCombineConstructArg(sb, argArray);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * @param sb
	 * @param argArray
	 */
	protected void specialCombineConstructArg(StringBuilder sb,
			String[] argArray) {
		int argLength = checkSuffixArgLength(argArray);
		for (int i = 0; i < argLength; i++) {
			if (argArray[i] != null) {
				if(i != 1 && i != 2 && i != 3){   //在生成JS时 某些参数为对象参数，不需要在值两边加引号
					sb.append(",'" + argArray[i] + "'");
				}else{
					sb.append("," + argArray[i] + "");
				}
			} else {
				sb.append(",null");
			}
		}
	}
}
