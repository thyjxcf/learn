package net.zdsoft.eis.system.frame.service;

import java.util.List;
import java.util.Set;

import net.zdsoft.eis.system.frame.entity.FlowDiagram;

public interface FlowDiagramService {
	/**
	 * 获取流程图
	 * @param subSystem
	 * @param unitClass
	 * @return
	 */
	public List<FlowDiagram> getFlowDiagramList(int subSystem,int unitClass);
	
	/**
	 * 获取组装好的流程图
	 * @param subSystem
	 * @param unitClass
	 * @param platform
	 * @param modSet
	 * @return
	 */
	public List<FlowDiagram> getDiagramHtmlList(int subSystem,
			int unitClass,int platform,Set<Integer> modSet);
}
