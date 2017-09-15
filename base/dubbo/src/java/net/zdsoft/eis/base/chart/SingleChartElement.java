/* 
 * @(#)ChartElementFixed.java    Created on 2013-1-10
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart;


/**
 * 固定形式的数据元素
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-10 上午10:15:15 $
 */
public class SingleChartElement {
	private String label;// x轴上的标签
	private String labelOrder;// 排序
	private String legend;// 图例
	private String legendOrder;
	private Number value;// 值

	public SingleChartElement() {
		super();
	}

	public SingleChartElement(String label, String legend, Number value) {
		super();
		this.label = label;
		this.legend = legend;
		this.value = value;
	}

	public String getLabelOrder() {
		return labelOrder;
	}

	public void setLabelOrder(String labelOrder) {
		this.labelOrder = labelOrder;
	}

	public String getLegendOrder() {
		return legendOrder;
	}

	public void setLegendOrder(String legendOrder) {
		this.legendOrder = legendOrder;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

}
