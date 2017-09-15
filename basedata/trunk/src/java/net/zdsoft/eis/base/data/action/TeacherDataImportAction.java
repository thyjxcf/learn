/* 
 * @(#)NewTeacherDataImportAction.java    Created on 2009-10-19
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.action.DataImportBaseAction;
import net.zdsoft.leadin.dataimport.param.DataImportPageParam;
import net.zdsoft.leadin.dataimport.param.DataImportViewParam;

/**
 * @author hexq
 * @version $Revision: 1.0 $, $Date: 2009-10-19 下午11:24:00 $
 */
public class TeacherDataImportAction extends DataImportBaseAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -903872557092238894L;

	// 构造方法
	public TeacherDataImportAction() {
	}

	public String main() {
		return SUCCESS;
	}

	@Override
	public String subExecute() throws Exception {
		setCovered("1"); // 默认覆盖

		return SUCCESS;
	}

	public DataImportPageParam getPageParam() {
		DataImportPageParam param = new DataImportPageParam(
				"/basedata/teacher", "teacherAdmin-importMain");
		param.setHasTask(false);
		return param;
	}

	public DataImportViewParam getViewParam() {
		DataImportViewParam param = new DataImportViewParam(
				"teacher_import.xml", getObjectName(), "teacherImportService");
		return param;
	}

	@Override
	public List<String[]> getParamsList() {
		return new ArrayList<String[]>();
	}

}
