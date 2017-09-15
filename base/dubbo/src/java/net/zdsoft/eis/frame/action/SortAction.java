package net.zdsoft.eis.frame.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;

public class SortAction<V> extends BaseAction {
	private static final long serialVersionUID = 3386001045340454925L;
	private String sortColumn;
    private String sortType;

	protected void sort(List<V> list) {
		if (StringUtils.isNotBlank(sortColumn)) {
			Collections.sort(list, new Comparator<V>() {
				@Override
				public int compare(V o1, V o2) {
					int ret = 0;
					try {
						String methodName = "get"
								+ sortColumn.substring(0, 1).toUpperCase()
								+ sortColumn.substring(1);
						String c1 = String.valueOf(MethodUtils
								.invokeExactMethod(o1, methodName, null));
						String c2 = String.valueOf(MethodUtils
								.invokeExactMethod(o2, methodName, null));
						if (sortType.equals("1"))
							ret = c1.compareTo(c2);
						else
							ret = c2.compareTo(c1);

					} catch (NoSuchMethodException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
					return ret;
				}
			});
		}
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

}
