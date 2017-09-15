/* 
 * @(#)ChartElement.java    Created on 2013-1-9
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-9 下午03:22:58 $
 */
public class StackedChartElement {
	private String label;
	private List<Number> values = new ArrayList<Number>();

	public StackedChartElement() {
		super();
	}

	public StackedChartElement(String label) {
		super();
		this.label = label;
	}

	public StackedChartElement addValue(Number value) {
		values.add(value);
		return this;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Number> getValues() {
		return values;
	}

}
