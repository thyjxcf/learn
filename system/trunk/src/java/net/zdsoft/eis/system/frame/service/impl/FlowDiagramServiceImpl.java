package net.zdsoft.eis.system.frame.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SimpleModule;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SimpleModuleService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.eis.system.frame.dao.FlowDiagramDao;
import net.zdsoft.eis.system.frame.entity.FlowDiagram;
import net.zdsoft.eis.system.frame.entity.FlowDiagramDetail;
import net.zdsoft.eis.system.frame.service.FlowDiagramDetailService;
import net.zdsoft.eis.system.frame.service.FlowDiagramService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class FlowDiagramServiceImpl extends DefaultCacheManager implements
		FlowDiagramService {

	private FlowDiagramDao flowDiagramDao;

	private FlowDiagramDetailService flowDiagramDetailService;

	private ModuleService moduleService;

	private SimpleModuleService simpleModuleService;

	public void setFlowDiagramDao(FlowDiagramDao flowDiagramDao) {
		this.flowDiagramDao = flowDiagramDao;
	}

	public void setFlowDiagramDetailService(
			FlowDiagramDetailService flowDiagramDetailService) {
		this.flowDiagramDetailService = flowDiagramDetailService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setSimpleModuleService(SimpleModuleService simpleModuleService) {
		this.simpleModuleService = simpleModuleService;
	}

	@Override
	public List<FlowDiagram> getFlowDiagramList(final int subSystem,
			final int unitClass) {
		return getEntitiesFromCache(new CacheEntitiesParam<FlowDiagram>() {

			public List<FlowDiagram> fetchObjects() {
				return flowDiagramDao.getFlowDiagramList(subSystem, unitClass);
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_FLOW_DIAGRAM_LIST + subSystem
						+ unitClass;
			}
		});
	}

	public List<FlowDiagram> getDiagramHtmlList(final int subSystem,
			final int unitClass, int platform, Set<Integer> modSet) {
		List<FlowDiagram> flowList = getFlowDiagramList(subSystem, unitClass);
		for (FlowDiagram fd : flowList) {
			List<FlowDiagramDetail> detailList = flowDiagramDetailService
					.getFlowDiagramDetailList(fd.getId());
			fd.setHtml(assembleFlowDiagram(detailList, modSet, platform));
		}
		return flowList;
	}

	private void assembleFlowDiagramItem(String parentId, int platform,
			Map<String, List<FlowDiagramDetail>> detailMap,
			Map<String, FlowDiagramItem> itemMap, Set<Integer> modSet) {
		Map<Integer, Module> moduleMap = moduleService.getAllModuleMap();
		Map<Integer, SimpleModule> SimpleModuleMap = simpleModuleService
				.getModulesMap();

		List<FlowDiagramDetail> tempList = detailMap.get(parentId);
		FlowDiagramItem parentItem = itemMap.get(parentId);

		if (CollectionUtils.isNotEmpty(tempList)) {
			Collections.sort(tempList, new Comparator<FlowDiagramDetail>() {
				@Override
				public int compare(FlowDiagramDetail o1, FlowDiagramDetail o2) {
					return o1.getOrderId() - o2.getOrderId();
				}
			});
			for (FlowDiagramDetail detail : tempList) {
				String modelName = null;
				int unitClass = 0;
				boolean isExists = false;
				// 系统内置的开始
				if (detail.getModelId() == -1) {
					isExists = true;
				} else {
					if (detail.getPlatform() == BaseConstant.PLATFORM_TEACHER) {
						if (moduleMap.containsKey(detail.getModelId())) {
							isExists = true;
							modelName = moduleMap.get(detail.getModelId())
									.getName();
							unitClass = moduleMap.get(detail.getModelId())
									.getUnitclass();
						}
					} else {
						if (SimpleModuleMap.containsKey(detail.getModelId())) {
							isExists = true;
							modelName = SimpleModuleMap
									.get(detail.getModelId()).getName();
							unitClass = SimpleModuleMap
									.get(detail.getModelId()).getUnitclass();
						}
					}
				}
				// 如果模块不存在 则本模块及其所有下级的模块都不显示
				if (isExists) {
					boolean first = false;
					boolean permission = false;
					if (detail.getModelId() == -1) {
						permission = true;
						first = true;
						modelName = "开始";
					} else {
						// 如果没有权限 就本模块灰掉 下级模块仍然显示
						if (platform == BaseConstant.PLATFORM_TEACHER) {
							if (modSet.contains(detail.getModelId())
									&& detail.getPlatform() == BaseConstant.PLATFORM_TEACHER)
								permission = true;
						} else {
							if (platform == detail.getPlatform())
								permission = true;
						}
						if (BaseConstant.ZERO_GUID.equals(detail.getParentId()))
							first = true;
					}
					FlowDiagramItem item = new FlowDiagramItem(modelName,
							detail.getModelId(), detail.getPlatform(),
							unitClass, permission, first);
					if (first)
						parentItem = item;
					else
						parentItem.add(item);
					itemMap.put(detail.getId(), item);
					assembleFlowDiagramItem(detail.getId(), platform,
							detailMap, itemMap, modSet);
				}
			}
			itemMap.put(parentId, parentItem);
		}
	}

	private String assembleFlowDiagram(List<FlowDiagramDetail> detailList,
			Set<Integer> modSet, int platform) {
		Map<String, List<FlowDiagramDetail>> detailMap = new HashMap<String, List<FlowDiagramDetail>>();
		Map<String, FlowDiagramItem> itemMap = new HashMap<String, FlowDiagramItem>();

		for (FlowDiagramDetail detail : detailList) {
			List<FlowDiagramDetail> tempList = detailMap.get(detail
					.getParentId());
			if (CollectionUtils.isEmpty(tempList))
				tempList = new ArrayList<FlowDiagramDetail>();
			tempList.add(detail);
			detailMap.put(detail.getParentId(), tempList);
		}
		assembleFlowDiagramItem(BaseConstant.ZERO_GUID, platform, detailMap,
				itemMap, modSet);

		StringBuffer html = new StringBuffer("");
		FlowDiagramItem t = itemMap.get(BaseConstant.ZERO_GUID);
		Map<String, FlowDiagramItem> map = new HashMap<String, FlowDiagramItem>();
		int maxStep = asyFlowDiagramItem(t, map);
		html.append("<table border='0' cellspacing='0' cellpadding='0' class='flow-table'>");
		Map<Integer, Integer> mapEnd = new HashMap<Integer, Integer>();
		// 补充完整item
		for (int jj = 0; jj < maxStep + 1; jj++) {
			FlowDiagramItem parentItem = null;
			for (int ii = 0; ii < t.getRows(); ii++) {
				String key = ii + "-" + (jj);
				FlowDiagramItem tt = map.get(key);
				if (tt == null) {
					FlowDiagramItem emptyItem = new FlowDiagramItem();
					emptyItem.setParent(parentItem);
					map.put(key, emptyItem);
				} else {
					parentItem = map.get(key).getParent();
				}
			}
		}
		for (int i = 0; i < t.getRows(); i++) {
			html.append("<tr>");
			for (int j = 0; j < maxStep + 1; j++) {
				String key = i + "-" + (j);
				FlowDiagramItem tt = map.get(key);
				String sp = "@";
				if (tt == null || StringUtils.isBlank(tt.getName())) {
					sp = "<td class='line'>&nbsp;</td>";
					if (mapEnd.get(j) != null) {
						if (i == tt.getParent().getRow())
							sp = "<td class='line h-line3'>&nbsp;</td>";
						else
							sp = "<td class='line h-line1'>&nbsp;</td>";
					}
				} else if (tt.getIndex() == 0) {
					if (tt.parent != null
							&& tt.parent.getFlowDiagramList().size() > 1) {
						if (tt.getRow() == (tt.parent.getRow()))
							sp = "<td class='line w-line2'>&nbsp;</td>";
						else
							sp = "<td class='line h-line4'>&nbsp;</td>";
						mapEnd.put(tt.getCol(), tt.getRow());
					} else {
						sp = "<td class='line w-line1'>&nbsp;</td>";
					}
				} else if (tt.getIndex() == (tt.parent.getFlowDiagramList()
						.size() - 1)) {
					if (tt.getRow() == (tt.parent.getRow()))
						sp = "<td class='line w-line3'>&nbsp;</td>";
					else
						sp = "<td class='line h-line5'>&nbsp;</td>";
					mapEnd.remove(tt.getCol());
				} else if (tt.getRow() == (tt.parent.getRow())) {
					sp = "<td class='line w-line4'>&nbsp;</td>";
				} else {
					sp = "<td class='line h-line2'>&nbsp;</td>";
				}
				if (j != 0)
					html.append(sp);

				if (tt != null && StringUtils.isNotBlank(tt.getName())) {
					String style = "state state1";
					String title = "";
					if (tt.getPlatform() == BaseConstant.PLATFORM_TEACHER) {
						if (tt.getUnitClass() == Unit.UNIT_CLASS_EDU) {
							style = "state state3";
							title = "教育局端";
						} else if (tt.getUnitClass() == Unit.UNIT_CLASS_SCHOOL) {
							style = "state state2";
							title = "学校端";
						}
					} else {
						if (tt.getPlatform() == BaseConstant.PLATFORM_STUPLATFORM) {
							style = "state state4";
							title = "学生端";
						} else {
							style = "state state4";
							title = "家长端";
						}
					}
					if (tt.isFirst()) {
						if (tt.getModelId() == -1)
							html.append("<td class='state state1' align='center'><span>"
									+ tt.getName() + "</span></td>");
						else {
							if (tt.isPermission())
								html.append("<td class='"
										+ style
										+ "' title='"
										+ title
										+ "'><span><a href='javascript:void(0)' onclick='clickModule("
										+ tt.getModelId() + ");return false;'>"
										+ tt.getName() + "</a></span></td>");
							else
								html.append("<td class='state state5' title='无权限操作("
										+ title
										+ ")'><span>"
										+ tt.getName()
										+ "</span></td>");
						}
					} else {
						if (tt.isPermission())
							html.append("<td class='"
									+ style
									+ "' title='"
									+ title
									+ "'><span><a href='javascript:void(0)' onclick='clickModule("
									+ tt.getModelId() + ");return false;'>"
									+ tt.getName() + "</a></span></td>");
						else
							html.append("<td class='state state5' title='无权限操作("
									+ title
									+ ")'><span>"
									+ tt.getName()
									+ "</span></td>");
					}
				} else {
					html.append("<td class='state'>&nbsp;</td>");
				}
			}
			html.append("</tr>");
		}
		html.append("</table>");
		return html.toString();
	}

	private int asyFlowDiagramItem(FlowDiagramItem t,
			Map<String, FlowDiagramItem> map) {
		int maxCols = 0;
		FlowDiagramItem pt = t.getParent();
		if (pt == null)
			t.setCol(0);
		else {
			t.setCol(pt.getCol() + 1);
		}
		int row = (t.getRows() + 1) / 2 - 1;
		if (pt == null)
			t.setRow(row);
		List<FlowDiagramItem> list = t.getFlowDiagramList();
		int rowIndex = 0;
		for (FlowDiagramItem flowDiagramItem : list) {
			flowDiagramItem.setPreNodeRows(rowIndex
					+ flowDiagramItem.parent.getPreNodeRows());
			flowDiagramItem.setRow((flowDiagramItem.getRows() + 1) / 2 - 1
					+ (flowDiagramItem.parent.getPreNodeRows() + rowIndex));
			rowIndex += flowDiagramItem.getRows();
		}
		maxCols = maxCols > t.getCol() ? maxCols : t.getCol();
		if (CollectionUtils.isNotEmpty(t.getFlowDiagramList())) {
			map.put(t.getRow() + "-" + t.getCol(), t);
			for (FlowDiagramItem flowDiagramItem : t.getFlowDiagramList()) {
				int maxCols_ = asyFlowDiagramItem(flowDiagramItem, map);
				maxCols = maxCols > maxCols_ ? maxCols : maxCols_;
			}
		} else {
			map.put(t.getRow() + "-" + t.getCol(), t);
		}
		return maxCols;
	}

	private static class FlowDiagramItem {
		String name;
		int modelId;
		int platform;
		int unitClass;
		boolean permission;
		boolean first;
		FlowDiagramItem parent;
		int preNodeRows;
		int row;
		int col;
		int index;

		public FlowDiagramItem() {

		}

		public FlowDiagramItem(String name, int modelId, int platform,
				int unitClass, boolean permission, boolean first) {
			this.name = name;
			this.modelId = modelId;
			this.platform = platform;
			this.unitClass = unitClass;
			this.permission = permission;
			this.first = first;
		}

		private List<FlowDiagramItem> flowDiagramList = new ArrayList<FlowDiagramItem>();

		/**
		 * 附加一个子项
		 */
		public void add(FlowDiagramItem t) {
			t.setParent(this);
			t.setIndex(flowDiagramList.size());
			flowDiagramList.add(t);
		}

		/**
		 * 所包含的最大行数
		 * 
		 * @return
		 */
		public int getRows() {
			int r = 0;
			if (CollectionUtils.isEmpty(flowDiagramList)) {
				r = 1;
			}
			for (FlowDiagramItem t : flowDiagramList) {
				r += t.getRows();
			}
			return r;
		}

		public int getRow() {
			return row;
		}

		public FlowDiagramItem getParent() {
			return parent;
		}

		public void setParent(FlowDiagramItem parent) {
			this.parent = parent;
		}

		public List<FlowDiagramItem> getFlowDiagramList() {
			return flowDiagramList;
		}

		public String getName() {
			return name;
		}

		public int getPreNodeRows() {
			return preNodeRows;
		}

		public void setPreNodeRows(int preNodeRows) {
			this.preNodeRows = preNodeRows;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getModelId() {
			return modelId;
		}

		public int getPlatform() {
			return platform;
		}

		public boolean isPermission() {
			return permission;
		}

		public int getUnitClass() {
			return unitClass;
		}

		public boolean isFirst() {
			return first;
		}
	}

}
