/* 
 * @(#)OfcChartAction.java    Created on 2013-1-9
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.zdsoft.eis.base.chart.ChartConf;
import net.zdsoft.eis.base.chart.ChartElementConverter;
import net.zdsoft.eis.base.chart.ChartUtils;
import net.zdsoft.eis.base.chart.SingleChartElement;
import net.zdsoft.eis.base.chart.StackedChartElement;
import net.zdsoft.eis.frame.action.ChartAction;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-9 下午02:01:33 $
 */
public class OfcChartAction extends ChartAction {
	private static final long serialVersionUID = -4206891991990265552L;

	private boolean singleSeries;// 是否单个系列

	private ChartConf buildConf() {
		ChartConf conf = new ChartConf();
		conf.addLegend("语文").addLegend("数学").addLegend("英语");
		return conf;
	}

	private List<StackedChartElement> buildData() {
		List<StackedChartElement> datas = new ArrayList<StackedChartElement>();

		StackedChartElement e = new StackedChartElement("张三");
		e.addValue(90).addValue(80).addValue(70);
		datas.add(e);

		e = new StackedChartElement("李四");
		e.addValue(85).addValue(75).addValue(65);
		datas.add(e);

		e = new StackedChartElement("王五");
		e.addValue(80).addValue(90).addValue(60);
		datas.add(e);

		return datas;
	}

	public String showChart1() throws Exception {
		ChartConf conf = buildConf();
		List<StuArchi> datas = buildStuArchi();
		return responseChart(ChartUtils.genChart(datas,
				new ChartElementConverter.SingleConverter<StuArchi>() {

					@Override
					public SingleChartElement convert(StuArchi e) {
						SingleChartElement s = new SingleChartElement();
						s.setLabelOrder(e.getStuCode());
						s.setLabel(e.getStuName());
						s.setLegendOrder(String.valueOf(e.getSubjectCode()));
						s.setLegend(e.getSubjectName());
						s.setValue(e.getScore());

						return s;
					}

					@Override
					public Comparator<String> labelComparator() {
						return ChartUtils.stringComparator();
					}

					@Override
					public Comparator<String> legendComparator() {
						return ChartUtils.numberComparator();
					}
				}));

	}

	private List<StuArchi> buildStuArchi() {
		List<StuArchi> datas = new ArrayList<StuArchi>();

		StuArchi e = new StuArchi("01", "张三", 1, "语文", 93);
		datas.add(e);
		e = new StuArchi("02", "李四", 1, "语文", 92);
		datas.add(e);
		e = new StuArchi("03", "王五", 1, "语文", 91);
		datas.add(e);

		if (singleSeries)
			return datas;

		// e = new StuArchi("01","张三",2, "数学", 83);
		// datas.add(e);
		e = new StuArchi("02", "李四", 2, "数学", 82);
		datas.add(e);
		e = new StuArchi("03", "王五", 2, "数学", 81);
		datas.add(e);

		e = new StuArchi("01", "张三", 3, "英语", 73);
		datas.add(e);
		e = new StuArchi("03", "王五", 3, "英语", 71);
		datas.add(e);
		e = new StuArchi("02", "李四", 3, "英语", 72);
		datas.add(e);

		return datas;
	}

	public void setSingleSeries(boolean singleSeries) {
		this.singleSeries = singleSeries;
	}

}
