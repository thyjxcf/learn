package net.zdsoft.office.remote;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.dto.OfficeConvertDto;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;

public class RemoteConvertFlowAction extends OfficeJsonBaseAction{
	
	private OfficeConvertFlowService officeConvertFlowService;
	
	
	private String unitId;
	private String userId;
	private String dataType;
	private int type;//业务数据类型  请假、物品等
	private String searchStr;
	
	//审批列表
	public void getAuditList(){
		Pagination page = getPage();
		List<OfficeConvertDto> list = officeConvertFlowService.getAuditList(unitId, userId, dataType, type, searchStr, page);
		List<OfficeConvertDto> allList = officeConvertFlowService.getAuditList(unitId, userId, String.valueOf(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_1), ConvertFlowConstants.OFFICE_ALL, null, null);
		
		JSONArray array = new JSONArray();
		for(OfficeConvertDto dto : list){
			JSONObject json = new JSONObject();
			json.put("type", dto.getType());
			json.put("title", dto.getTitle());
			json.put("subtitle", dto.getSubtitle());
			json.put("dataStr", dto.getDateStr());
			json.put("businessId", dto.getBusinessId());
			json.put("taskId", dto.getTaskId());
			
			array.add(json);
		}
		
		jsonMap.put("count", allList.size());
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	//申请列表
	public void getApplyList(){
		Pagination page = getPage();
		List<OfficeConvertDto> list = officeConvertFlowService.getApplyList(userId, type, getPage());
		JSONArray array = new JSONArray();
		for(OfficeConvertDto dto : list){
			JSONObject json = new JSONObject();
			json.put("type", dto.getType());
			json.put("title", dto.getTitle());
			json.put("subtitle", dto.getSubtitle());
			json.put("dataStr", dto.getDateStr());
			json.put("businessId", dto.getBusinessId());
			
			array.add(json);
		}
		
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
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
	
	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}
	
}
