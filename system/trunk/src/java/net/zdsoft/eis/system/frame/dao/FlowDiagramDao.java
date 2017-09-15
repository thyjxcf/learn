package net.zdsoft.eis.system.frame.dao;

import java.util.List;

import net.zdsoft.eis.system.frame.entity.FlowDiagram;

public interface FlowDiagramDao {
	/**
	 * 获取其流程图List
	 * @param subSystem
	 * @param unitClass
	 * @return
	 */
	public List<FlowDiagram> getFlowDiagramList(int subSystem,int unitClass);

}
