package net.zdsoft.office.basic.action;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.office.basic.constant.OfficeAppEnum;
import net.zdsoft.office.basic.entity.OfficeAppParm;
import net.zdsoft.office.basic.service.OfficeAppParmService;

public class OfficeAppParmAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeAppParmService officeAppParmService;
	private List<OfficeAppParm> applist;
	private OfficeAppParm app = new OfficeAppParm();;
	
	@Override
	public String execute() throws Exception {
		Map<String, OfficeAppParm> map = officeAppParmService.getMapByUnitId(getUnitId(), false);
		
		applist = OfficeAppEnum.getOfficeAppList(getLoginInfo().getUnitClass());
		for(OfficeAppParm ent : applist){
			if(map.containsKey(ent.getType())){//说明该单位设置过该模块权限
				OfficeAppParm app = map.get(ent.getType());
				if(app != null){
					ent.setUnitId(app.getUnitId());
					ent.setIsUsing(app.getIsUsing());
				}
			}else{
				ent.setUnitId(getLoginInfo().getUnitID());//默认unitId
			}
		}
		
		return SUCCESS;
	}
	
	public String save(){
		OfficeAppParm ent =officeAppParmService.getOfficeAppParmByUnitId(app.getUnitId(), app.getType());
		try {
			if(ent != null){
				ent.setIsUsing(app.getIsUsing());
				officeAppParmService.update(ent);
			}else{
				officeAppParmService.save(app);
			}
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("程序异常："+e.getMessage());
		}
	
		return SUCCESS;
	}
	
	public void setOfficeAppParmService(
			OfficeAppParmService officeAppParmService) {
		this.officeAppParmService = officeAppParmService;
	}

	public List<OfficeAppParm> getApplist() {
		return applist;
	}

	public OfficeAppParm getApp() {
		return app;
	}

	public void setApp(OfficeAppParm app) {
		this.app = app;
	}
	
}
