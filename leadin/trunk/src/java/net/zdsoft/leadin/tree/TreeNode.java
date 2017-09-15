/* 
 * @(#)TreeNode.java    Created on 2006-9-29
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/tree/TreeNode.java,v 1.1 2006/11/03 05:41:39 luxm Exp $
 */
package net.zdsoft.leadin.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luxingmu
 * @version $Revision: 1.1 $, $Date: 2006/11/03 05:41:39 $
 */
public class TreeNode implements Serializable {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8981351220011346214L;

	private String nodeName;
	
	private String text;

	private String action;
	
	private String icon;
	private String openIcon;

	private List<TreeNode> childrenList = new ArrayList<TreeNode>();
	
	public TreeNode(String text) {
		this.text = text;
	}

	public TreeNode(String text, String action) {
		this.text = text;
		this.action = action;
	}
	
	/**
	 * 添加节点
	 * @param child
	 */
	public void addChildNode(TreeNode child){
		this.childrenList.add(child);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeNode> getChildrenList() {
		return childrenList;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpenIcon() {
		return openIcon;
	}

	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}
	
	/**
	 * @param argArray
	 * @return
	 */
	protected int checkSuffixArgLength(String[] argArray) {
		int argLength = argArray.length;
		for (int i = argArray.length - 1; i >= 0; i--) {
			if (argArray[i] == null) {
				argLength = argLength-1;
			} else {
				break;
			}
		}
		return argLength;
	}

}
