package net.zdsoft.eis.system.frame.service;

import java.util.List;

import net.zdsoft.eis.system.frame.entity.FlowDiagramDetail;

public interface FlowDiagramDetailService {
	/**
	 * 根据主流程id获取流程明细
	 * @param flowDiagramId
	 * @return
	 */
	public List<FlowDiagramDetail> getFlowDiagramDetailList(String flowDiagramId) ;
}
