/* 
 * @(#)ChartUtils.java    Created on 2013-1-9
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-9 下午02:09:20 $
 */
public final class ChartUtils {
	private static Logger log = LoggerFactory.getLogger(ChartUtils.class);

	public static ChartJqPlot genChart(List<?> list,
			final ChartElementConverter.SingleConverter converter) {
		// 将用户原始数据进行转化
		List<SingleChartElement> eles = new ArrayList<SingleChartElement>();
		for (Object object : list) {
			SingleChartElement e = converter.convert(object);
			eles.add(e);
		}

		// 整理数据：以key=legend进行分组
		Map<String, OrderObject> labelMap = new HashMap<String, OrderObject>();
		Map<String, OrderObject> legendMap = new HashMap<String, OrderObject>();
		Map<String, List<SingleChartElement>> dataMap = new HashMap<String, List<SingleChartElement>>();
		for (SingleChartElement e : eles) {
			String legend = e.getLegend();

			labelMap.put(e.getLabel(), new OrderObject(e.getLabelOrder(), e.getLabel()));
			legendMap.put(legend, new OrderObject(e.getLegendOrder(), e.getLegend()));

			List<SingleChartElement> values = dataMap.get(legend);
			if (values == null) {
				values = new ArrayList<SingleChartElement>();
				dataMap.put(legend, values);
			}
			values.add(e);
		}

		// 图例排序
		List<OrderObject> legendObjs = new ArrayList<OrderObject>(legendMap.values());
		Collections.sort(legendObjs, new Comparator<OrderObject>() {
			@Override
			public int compare(OrderObject o1, OrderObject o2) {
				return converter.legendComparator().compare(o1.getOrder(), o2.getOrder());
			}
		});

		// label排序
		List<OrderObject> labelObjs = new ArrayList<OrderObject>(labelMap.values());
		Collections.sort(labelObjs, new Comparator<OrderObject>() {
			@Override
			public int compare(OrderObject o1, OrderObject o2) {
				return converter.labelComparator().compare(o1.getOrder(), o2.getOrder());
			}
		});
		List<String> labels = new ArrayList<String>();
		for (OrderObject oo : labelObjs) {
			labels.add(oo.getObject());
		}

		// 填充数据并将其整理成图表所需格式
		List<List<SingleChartElement>> datas = new ArrayList<List<SingleChartElement>>();
		List<String> legends = new ArrayList<String>();
		for (OrderObject oo : legendObjs) {
			String legend = oo.getObject();
			legends.add(legend);

			List<SingleChartElement> element = dataMap.get(legend);

			// 填补数据
			if (element.size() < labels.size()) {
				Set<String> existsLabels = new HashSet<String>();
				for (SingleChartElement e : element) {
					existsLabels.add(e.getLabel());
				}
				for (String label : labels) {
					if (existsLabels.contains(label))
						continue;

					SingleChartElement e = new SingleChartElement(label, legend, 0);
					e.setLabelOrder(labelMap.get(label).getOrder());
					e.setLegendOrder(legendMap.get(legend).getOrder());
					element.add(e);
				}
			}
			// label排序
			Collections.sort(element, new Comparator<SingleChartElement>() {
				@Override
				public int compare(SingleChartElement o1, SingleChartElement o2) {
					return converter.labelComparator().compare(o1.getLabelOrder(),
							o2.getLabelOrder());
				}
			});

			datas.add(element);
		}

		ChartJqPlot chart = new ChartJqPlot();
		chart.setDatas(datas);
		chart.setLegends(legends);
		chart.setLabels(labels);
		return chart;
	}

	/**
	 * 排序对象
	 * 
	 * @author zhaosf
	 * @version $Revision: 1.0 $, $Date: 2013-1-17 上午09:39:09 $
	 */
	private static class OrderObject {
		private String order;
		private String object;

		public OrderObject(String order, String object) {
			super();
			this.order = order;
			this.object = object;
		}

		public String getOrder() {
			return order;
		}

		public String getObject() {
			return object;
		}
	}

	public static Comparator<String> stringComparator() {
		return new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		};
	}

	public static Comparator<String> numberComparator() {
		return new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.valueOf(o1).intValue() - Integer.valueOf(o2).intValue();
			}
		};
	}

	public static ChartJqPlot genChart(ChartConf conf, List<?> list,
			ChartElementConverter.StackedConverter converter) {
		// 将用户原始数据转化图表数据
		List<StackedChartElement> eles = new ArrayList<StackedChartElement>();
		for (Object object : list) {
			StackedChartElement e = converter.convert(object);
			eles.add(e);
		}

		// x轴数据集合
		List<String> labels = new ArrayList<String>();

		// y轴数据集合
		List<List<SingleChartElement>> datas = new ArrayList<List<SingleChartElement>>(conf
				.getLegends().size());
		for (int i = 0; i < conf.getLegends().size(); i++) {
			List<SingleChartElement> colDatas = new ArrayList<SingleChartElement>();
			datas.add(colDatas);
		}
		for (StackedChartElement ce : eles) {
			String le = ce.getLabel();
			// if(StringUtils.isEmpty(l)){
			// log.error("label 不能为空");
			// continue;
			// }

			labels.add(le);

			List<Number> values = ce.getValues();
			for (int i = 0; i < values.size(); i++) {
				Number v = values.get(i);

				// 表示某一列的数据集合
				List<SingleChartElement> colDatas = datas.get(i);
				if (colDatas == null) {
					colDatas = new ArrayList<SingleChartElement>();
					datas.add(i, colDatas);
				}
				colDatas.add(new SingleChartElement(le, null, v));
			}

		}

		ChartJqPlot chart = new ChartJqPlot();
		chart.setDatas(datas);
		chart.setLegends(conf.getLegends());
		chart.setLabels(labels);
		return chart;
	}

}
