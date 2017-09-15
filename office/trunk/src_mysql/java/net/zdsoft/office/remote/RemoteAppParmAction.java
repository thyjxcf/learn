package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.office.basic.constant.OfficeAppEnum;
import net.zdsoft.office.basic.entity.OfficeAppParm;
import net.zdsoft.office.basic.service.OfficeAppParmService;

public class RemoteAppParmAction extends OfficeJsonBaseAction{
	
	private OfficeAppParmService officeAppParmService;
	private UnitService unitService;
	
	private String unitId;
	
	public void getAppList(){
		Map<String, OfficeAppParm> map = officeAppParmService.getMapByUnitId(unitId, true);
		Unit unit = unitService.getUnit(unitId);
		//获取所有app模块
		List<OfficeAppParm> applist = OfficeAppEnum.getOfficeAppList(unit.getUnitclass());
		//启用的模块list
		List<OfficeAppParm> usinglist = new ArrayList<OfficeAppParm>();
		for(OfficeAppParm ent : applist){
			if(map.containsKey(ent.getType())){//说明该单位设置过该模块权限
				OfficeAppParm app = map.get(ent.getType());
				if(app != null && app.getIsUsing()){
					usinglist.add(ent);
				}
			}else{
				if(ent.getIsUsing()){//如果该模块默认启用
					usinglist.add(ent);
				}
			}
		}
		
		JSONArray array = new JSONArray();
		for(OfficeAppParm ent : usinglist){
			JSONObject json = new JSONObject();
			json.put("type", ent.getType());
			json.put("name", OfficeAppEnum.getRemarkByType(ent.getType()));
			array.add(json);
		}
		
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOfficeAppParmService(
			OfficeAppParmService officeAppParmService) {
		this.officeAppParmService = officeAppParmService;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
}
