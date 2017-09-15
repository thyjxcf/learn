package net.zdsoft.eis.system.frame.service.impl;

import java.util.List;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.eis.system.frame.dao.FlowDiagramDetailDao;
import net.zdsoft.eis.system.frame.entity.FlowDiagramDetail;
import net.zdsoft.eis.system.frame.service.FlowDiagramDetailService;

public class FlowDiagramDetailServiceImpl extends DefaultCacheManager implements
		FlowDiagramDetailService {

	private FlowDiagramDetailDao flowDiagramDetailDao;

	public void setFlowDiagramDetailDao(
			FlowDiagramDetailDao flowDiagramDetailDao) {
		this.flowDiagramDetailDao = flowDiagramDetailDao;
	}

	@Override
	public List<FlowDiagramDetail> getFlowDiagramDetailList(
			final String flowDiagramId) {
		return getEntitiesFromCache(new CacheEntitiesParam<FlowDiagramDetail>() {

			public List<FlowDiagramDetail> fetchObjects() {
				return flowDiagramDetailDao
						.getFlowDiagramDetailList(flowDiagramId);
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_FLOW_DIAGRAM_DETAIL_LIST
						+ flowDiagramId;
			}
		});
	}
}
