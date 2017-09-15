/* 
 * @(#)WebCheckBoxTreeNode.java    Created on 2006-10-1
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/tree/WebCheckBoxTreeNode.java,v 1.6 2007/01/10 08:53:07 luxm Exp $
 */
package net.zdsoft.leadin.tree;

/**
 * @author luxingmu
 * @version $Revision: 1.6 $, $Date: 2007/01/10 08:53:07 $
 */
public class WebCheckBoxTreeNode extends WebTreeNode {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6383000918233299307L;
	private String inputName;
	private String inputValue;
	private boolean checked;
	private boolean disabled;
	private String htmlComponent;
	private String extraNodeDivStyle;

	public String getExtraNodeDivStyle() {
		return extraNodeDivStyle;
	}

	public void setExtraNodeDivStyle(String extraNodeDivStyle) {
		this.extraNodeDivStyle = extraNodeDivStyle;
	}

	public String getHtmlComponent() {
		return htmlComponent;
	}

	public void setHtmlComponent(String htmlComponent) {
		this.htmlComponent = htmlComponent;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public WebCheckBoxTreeNode(String text) {
		this(text, null, null, null, false, false, null, null, null, null);
	}
	
	
	public WebCheckBoxTreeNode(String text, String action) {
		this(text, null, null, action, false, false, null, null, null, null);
	}
	
	public WebCheckBoxTreeNode(String text,String icon,String openIcon) {
		this(text, null, null, null, false, false, null, icon, openIcon);
	}
	
	public WebCheckBoxTreeNode(String text,String inputName,String inputValue,String action,boolean checked,boolean disabled,TreeNode parent,String icon,String openIcon) {
		this(text, inputName, inputValue, action, checked, disabled, parent, icon, openIcon, null);
	}
	
	public WebCheckBoxTreeNode(String text,String inputName,String inputValue,String action,boolean checked,boolean disabled,TreeNode parent,String icon,String openIcon,String htmlComponent) {
		this(text, inputName, inputValue, action, checked, disabled, parent, icon, openIcon, htmlComponent, null);
	}
	
	public WebCheckBoxTreeNode(String text,String inputName,String inputValue,String action,boolean checked,boolean disabled,TreeNode parent,String icon,String openIcon,String htmlComponent,String extraNodeDivStyle) {
		super(text,action);
		this.inputName = inputName;
		this.inputValue = inputValue;
		this.checked = checked;
		this.disabled = disabled;
		this.htmlComponent = htmlComponent;
		this.extraNodeDivStyle = extraNodeDivStyle;
		setParent(parent);
		setIcon(icon);
		setOpenIcon(openIcon);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("new WebFXCheckBoxTreeItem('" + getText() + "'");
		String[] argArray = {getInputName(),getInputValue(),getAction(),Boolean.toString(isChecked()),Boolean.toString(isDisabled()),
				getParent() != null ? getParent().getNodeName() : null,
				getIcon(), getOpenIcon(),getHtmlComponent(),getExtraNodeDivStyle()};
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
				if(i != 3 && i != 4 && i != 5 && i != 6 && i != 7){ //在生成JS时 某些参数为对象参数，不需要在值两边加引号
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
