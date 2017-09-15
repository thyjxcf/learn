/* 
 * @(#)Chart.java    Created on 2013-1-9
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-9 下午03:01:05 $
 */
public class ChartConf {
//	private boolean labelOrder;// 是否有标签排序列
//	private boolean labelInt;// 是否数值类型
//	private boolean legendOrder;// 是否有图标排序列
//	private boolean legendInt;// 是否数值类型
	private List<String> legends = new ArrayList<String>();// 图例

	public ChartConf addLegend(String legend) {
		legends.add(legend);
		return this;
	}

	public List<String> getLegends() {
		return legends;
	}

}
