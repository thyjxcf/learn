package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.frame.dao.FlowDiagramDetailDao;
import net.zdsoft.eis.system.frame.entity.FlowDiagramDetail;

public class FlowDiagramDetailDaoImpl extends BaseDao<FlowDiagramDetail>
		implements FlowDiagramDetailDao {

	private static final String SQL_FIND_FLOW_DIAGRAM_DETAIL_BY_FLOW_DIAGRAM_ID = "select * from sys_flow_diagram_detail where tfd_id=?";

	@Override
	public FlowDiagramDetail setField(ResultSet rs) throws SQLException {
		FlowDiagramDetail detail=new FlowDiagramDetail();
		detail.setId(rs.getString("id"));
		detail.setModelId(rs.getInt("model_id"));
		detail.setParentId(rs.getString("parent_id"));
		detail.setPlatform(rs.getInt("platform"));
		detail.setOrderId(rs.getInt("order_id"));
		return detail;
	}

	public List<FlowDiagramDetail> getFlowDiagramDetailList(String flowDiagramId) {
		return query(SQL_FIND_FLOW_DIAGRAM_DETAIL_BY_FLOW_DIAGRAM_ID,
				new Object[] { flowDiagramId }, new MultiRow());
	}

}
