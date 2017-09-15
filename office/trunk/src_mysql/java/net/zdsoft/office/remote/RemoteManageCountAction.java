package net.zdsoft.office.remote;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.subsystemcall.service.OfficedocSubsystemService;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.dto.OfficeConvertDto;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;
import net.zdsoft.office.repaire.service.OfficeRepaireService;

public class RemoteManageCountAction extends OfficeJsonBaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeWorkReportService officeWorkReportService;
	private OfficeRepaireService officeRepaireService;
	private OfficedocSubsystemService officedocSubsystemService;
	
	private String userId;
	private String unitId;

	public void manageCount(){
		List<OfficeConvertDto> auditList = officeConvertFlowService.getAuditList(unitId, userId, String.valueOf(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_1), ConvertFlowConstants.OFFICE_ALL, null, null);
		jsonMap.put("lotusCount", auditList.size());
		List<OfficeWorkReport> officeWorkeports = officeWorkReportService.getOfficeWorkReportList(userId, null, null, null,null,null,null);
		jsonMap.put("reportCount", officeWorkeports.size());
		int repaireCount = officeRepaireService.getOfficeRepaireMangeListH5Count(userId, unitId, null, null, new String[]{"1","2"}, null, null, null);
		jsonMap.put("repairsCount", repaireCount);
		if(officedocSubsystemService!=null){
			Map<String, Integer> officedocNum = officedocSubsystemService.getOfficedocNum(userId, unitId);
			jsonMap.put("officedocCount", officedocNum.get("receiveSuperviseHandle")+officedocNum.get("receiveUnSuperviseHandle")+officedocNum.get("sendHandle")+officedocNum.get("receiveSign"));
		}else{
			jsonMap.put("officedocCount", 0);
		}
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setOfficeConvertFlowService(OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeWorkReportService(OfficeWorkReportService officeWorkReportService) {
		this.officeWorkReportService = officeWorkReportService;
	}

	public void setOfficeRepaireService(OfficeRepaireService officeRepaireService) {
		this.officeRepaireService = officeRepaireService;
	}

	public void setOfficedocSubsystemService(OfficedocSubsystemService officedocSubsystemService) {
		this.officedocSubsystemService = officedocSubsystemService;
	}
	
}
