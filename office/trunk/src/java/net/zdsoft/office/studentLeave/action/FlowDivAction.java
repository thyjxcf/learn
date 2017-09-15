package net.zdsoft.office.studentLeave.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.base.common.action.ObjectDivAction;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;

@SuppressWarnings("serial")
public class FlowDivAction extends ObjectDivAction<Flow>{

	private FlowManageService flowManageService;
	private String type;
	
	
	@Override
	protected void toObject(Flow flow, SimpleObject object) {
		if (flow == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(flow.getFlowId());
		object.setObjectName(flow.getFlowName());
		//object.setUnitId(flow.get);
	}
	
	@Override
	protected List<Flow> getDatasByUnitId() {
		if(StringUtils.equals(type, "1")){
			return flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_GENERAL,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}else if(StringUtils.equals(type, "2")){
			return flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_TEMPORARY,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}else if(StringUtils.equals(type, "3")){
			return flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_LIVE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}else if(StringUtils.equals(type, "4")){
			return flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_LONG,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}else{
			return new ArrayList<Flow>();
		}
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
