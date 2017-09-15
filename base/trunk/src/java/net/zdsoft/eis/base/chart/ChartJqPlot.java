/* 
 * @(#)ChartJqPlot.java    Created on 2013-1-11
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-11 下午01:09:21 $
 */
public class ChartJqPlot {
	private List<List<SingleChartElement>> datas = new ArrayList<List<SingleChartElement>>();
	private Collection<String> legends;
	private List<Series> series = new ArrayList<Series>();
	private Collection<String> labels;

	public void addElements(List<SingleChartElement> elements) {
		datas.add(elements);
	}

	public List<List<SingleChartElement>> getDatas() {
		return datas;
	}

	public void setDatas(List<List<SingleChartElement>> datas) {
		this.datas = datas;
	}

	public Collection<String> getLegends() {
		return legends;
	}

	public void setLegends(Collection<String> legends) {
		for (String legend : legends) {
			series.add(new Series(legend));
		}
		this.legends = legends;
	}

	public Collection<String> getLabels() {
		return labels;
	}

	public void setLabels(Collection<String> labels) {
		this.labels = labels;
	}

	@Override
	public String toString() {
		JSONArray _datas = new JSONArray();
		for (List<SingleChartElement> list : datas) {
			JSONArray jsonArray = new JSONArray();
			for (SingleChartElement d : list) {
				JSONArray arr = new JSONArray();
				arr.add(d.getLabel());
				arr.add(d.getValue());
				jsonArray.add(arr);
			}
			_datas.add(jsonArray);
		}

		JSONObject chart = new JSONObject();

		chart.put("datas", _datas);
		chart.put("ticks", labels);
		chart.put("series", series);

		return chart.toString();
	}
}
