package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.frame.dao.FlowDiagramDao;
import net.zdsoft.eis.system.frame.entity.FlowDiagram;

public class FlowDiagramDaoImpl extends BaseDao<FlowDiagram> implements
		FlowDiagramDao {

	private static final String SQL_FIND_FLOW_DIAGRAM_BY_SUBSYSTEM = "select * from sys_flow_diagram where subsystem =? and unit_class=? and is_using=? order by order_id";

	@Override
	public FlowDiagram setField(ResultSet rs) throws SQLException {
		FlowDiagram flowDiagram = new FlowDiagram();
		flowDiagram.setId(rs.getString("id"));
		flowDiagram.setTfdName(rs.getString("tfd_name"));
		flowDiagram.setSubsystem(rs.getInt("subsystem"));
		flowDiagram.setUnitClass(rs.getInt("unit_class"));
		flowDiagram.setIsUsing(rs.getInt("is_using"));
		return flowDiagram;
	}

	public List<FlowDiagram> getFlowDiagramList(int subSystem,int unitClass) {
		return query(SQL_FIND_FLOW_DIAGRAM_BY_SUBSYSTEM, new Object[] {
				subSystem,unitClass, FlowDiagram.IS_USING_USE }, new MultiRow());
	}

}
