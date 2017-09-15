/* 
 * @(#)ChartAction.java    Created on 2013-1-9
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.action;

import java.io.IOException;

import net.zdsoft.eis.base.chart.ChartJqPlot;
import net.zdsoft.keel.util.ServletUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-9 下午01:55:59 $
 */
public class ChartAction extends BaseAction {
	private static final long serialVersionUID = 2467102973693320822L;

	public String responseChart(ChartJqPlot chart) {
		try {
			ServletUtils.print(getResponse(), chart.toString());
		} catch (IOException e) {
			log.error("输出图表错误", e);
		}

		return NONE;
	}
}
