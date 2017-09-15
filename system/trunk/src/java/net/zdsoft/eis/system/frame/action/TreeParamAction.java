package net.zdsoft.eis.system.frame.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.frame.action.BaseAction;

public class TreeParamAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private UnitIniService unitIniService;

	private String nowValue;
    private String unitIniId;
	private UnitIni[] treeParams;
    private String jsonError;
    private void loadTreeParams() {
        String unitId = this.getLoginInfo().getUnitID();
        List<UnitIni> paramList = unitIniService.getUnitTreeParamOptions(unitId);
        treeParams = new UnitIni[ paramList.size() ];
        for (int i=0; i<paramList.size(); i++) {
            treeParams[i] = paramList.get(i);
        }
    }

    public String listParam() throws Exception {
        loadTreeParams();
        return SUCCESS;
    }

    public String saveParam() {
	   	try {
	        String[] ids  = unitIniId.split(",");
			String[] nowValues = nowValue.split(",");
	        loadTreeParams();
			for (int i = 0; i < ids.length; i++) {
	            for (UnitIni ini : treeParams ) {
	                if (ini.getId() == Long.parseLong(ids[i].trim())) {
	                    ini.setNowValue(nowValues[i].trim());
	                }
	            }
			}
	        unitIniService.saveUnitOptions(treeParams);
			} catch (Exception e) {
				jsonError = e.getMessage();
		}
	    return SUCCESS;
	}

    public void setNowValue(String nowValue) {
        this.nowValue=nowValue;
    }
    public String getNowValue() {
        return this.nowValue;
    }
    
    public void setTreeParams(UnitIni[] treeParams) {
        this.treeParams=treeParams;
    }
    public UnitIni[] getTreeParams() {
        return this.treeParams;
    }

	public void setUnitIniService(UnitIniService unitIniService) {
        this.unitIniService=unitIniService;
    }

    public void setUnitIniId(String unitIniId) {
        this.unitIniId=unitIniId;
    }
    public String getUnitIniId() {
        return this.unitIniId;
    }

	public String getJsonError() {
		return jsonError;
	}

	public void setJsonError(String jsonError) {
		this.jsonError = jsonError;
	}

}
