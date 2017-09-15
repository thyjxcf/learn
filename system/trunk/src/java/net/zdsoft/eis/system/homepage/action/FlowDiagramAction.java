package net.zdsoft.eis.system.homepage.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.service.DataCenterSubsystemService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.frame.entity.FlowDiagram;
import net.zdsoft.eis.system.frame.service.FlowDiagramService;

public class FlowDiagramAction extends BaseAction {

	private static final long serialVersionUID = -2036287314704129609L;

	private String subsystemName;

	private SubSystemService subSystemService;

	private ServerService serverService;

	private List<FlowDiagram> flowDiagramList = new ArrayList<FlowDiagram>();

	private FlowDiagramService flowDiagramService;

    private DataCenterSubsystemService dataCenterSubsystemService;

	public String execute() {
		int unitClass = 0;
		switch (getLoginInfo().getUser().getOwnerType()) {
		case User.STUDENT_LOGIN:
			platform = BaseConstant.PLATFORM_STUPLATFORM;
			break;
		case User.FAMILY_LOGIN:
			platform = BaseConstant.PLATFORM_FAMPLATFORM;
			break;
		case User.OTHER_LOGIN:
			break;
		default:
			break;
		}
		if (BaseConstant.PLATFORM_STUPLATFORM == platform) {
			unitClass = 3;
		} else if (BaseConstant.PLATFORM_FAMPLATFORM == platform) {
			unitClass = 4;
		} else if (BaseConstant.PLATFORM_TEACHER == platform) {
			unitClass = getLoginInfo().getUnitClass();
		}
		SubSystem system = subSystemService.getSubSystem(appId);
		if (systemDeployService.isConnectPassport()) {// 从passpor
			if (system != null) {
				Server server = serverService.getServerByServerCode(system
						.getCode());
				if (server != null)
					subsystemName = server.getName();
			}
		} else {
			if (system != null)
				subsystemName = system.getName();
		}
		if (BaseConstant.PLATFORM_BACKGROUND != platform) {
			flowDiagramList = flowDiagramService.getDiagramHtmlList(
					appId, unitClass, platform, getLoginInfo()
							.getAllModSet());
		}
		return SUCCESS;
	}

	public boolean getIsHaveLastData() {
		if(dataCenterSubsystemService != null){
        	return dataCenterSubsystemService.isHaveLastYearData(getUnitId());
        }
		return false;
	}

	public boolean getIsPreEdu() {
		if(dataCenterSubsystemService != null){
        	return dataCenterSubsystemService.isPreEdu(getUnitId());
        }
		return false;
	}
	
	public void setDataCenterSubsystemService(
			DataCenterSubsystemService dataCenterSubsystemService) {
		this.dataCenterSubsystemService = dataCenterSubsystemService;
	}
	
	public List<FlowDiagram> getFlowDiagramList() {
		return flowDiagramList;
	}

	public String getSubsystemName() {
		return subsystemName;
	}

	public void setSubsystemName(String subsystemName) {
		this.subsystemName = subsystemName;
	}

	public void setFlowDiagramService(FlowDiagramService flowDiagramService) {
		this.flowDiagramService = flowDiagramService;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
}
