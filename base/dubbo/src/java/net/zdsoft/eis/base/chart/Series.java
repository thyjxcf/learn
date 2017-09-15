/* 
 * @(#)Series.java    Created on 2013-1-11
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-11 下午02:34:13 $
 */
public class Series {
	private String label;

	public Series(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
