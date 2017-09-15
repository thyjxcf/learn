/* 
 * @(#)ChartElementConverter.java    Created on 2013-1-10
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart;

import java.util.Comparator;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-10 上午10:00:17 $
 */
public interface ChartElementConverter {

	public static interface StackedConverter<E> {

		/**
		 * 将统计出来的数据对象转化为图表元素
		 * 
		 * @param e
		 * @return
		 */
		public StackedChartElement convert(E e);

	}

	public static interface SingleConverter<E> {

		/**
		 * 将统计出来的数据对象转化为图表元素
		 * 
		 * @param e
		 * @return
		 */
		public SingleChartElement convert(E e);

		/**
		 * x 轴排序
		 * 
		 * @return
		 */
		public Comparator<String> labelComparator();

		/**
		 * 图例排序
		 * 
		 * @return
		 */
		public Comparator<String> legendComparator();
	}

}
