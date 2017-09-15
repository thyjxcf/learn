/* 
 * @(#)BaseStudentTreeAction.java    Created on May 18, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.action;

import net.zdsoft.eis.base.tree.TreeConstant;

/**
 * 共用的学生树
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 18, 2011 2:14:07 PM $
 */
public class BaseStudentTreeAction extends BaseClassTreeAction {
    private static final long serialVersionUID = -1295538299897716735L;

    private String showGrade = "false";
    /**
     * 班级 -> 学生
     * 
     * @return
     * @throws Exception
     */
    public String studentTree() throws Exception {
        treeParam.setShowEdu(true);
        treeParam.setShowSch(true);
        if(showGrade.equals("true")) {
        	treeParam.setShowEisGrade(true);
        }
        treeParam.setLayer(TreeConstant.ITEMTYPE_STUDENT);
        return buildTree();
    }
	public String getShowGrade() {
		return showGrade;
	}
	public void setShowGrade(String showGrade) {
		this.showGrade = showGrade;
	}
}
