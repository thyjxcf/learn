package net.zdsoft.office.remote;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.eis.frame.util.RemoteCallUtils;

import org.apache.commons.lang.StringUtils;

public class RemoteWeikeAction extends RemoteBaseAction{
	
	private static final long serialVersionUID = 1L;
	private UnitService unitService;
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public void manageCount(){
		try {
			String userId = getParamValue("userId");
			if(StringUtils.isBlank(userId)){
				sendResult(RemoteCallUtils.convertError("userId不能为空").toString());
				return;
			}
			
			User user = userService.getUser(userId);
			if(user == null){
				sendResult(RemoteCallUtils.convertError("用户信息不存在或非教师登录！").toString());
				return;
			}
			
			Unit unit = unitService.getUnit(user.getUnitid());
			
			String repairCode = String.valueOf(WeikeAppConstant.getAppModuleId(WeikeAppConstant.REPAIR, unit.getUnitclass()));
			String bulletinCode = String.valueOf(WeikeAppConstant.getAppModuleId(WeikeAppConstant.BULLETIN, unit.getUnitclass()));
			String workreportCode = String.valueOf(WeikeAppConstant.getAppModuleId(WeikeAppConstant.WORK_REPORT, unit.getUnitclass()));
			
			JSONArray array = new JSONArray();
				
				JSONObject js1 = new JSONObject();
				js1.put("code", WeikeAppConstant.AUDIT_DATA);
				js1.put("number", 199);
				JSONObject js2 = new JSONObject();
				js2.put("code", WeikeAppConstant.OFFICEDOC_SEND);
				js2.put("number", 5);
				JSONObject js3 = new JSONObject();
				js3.put("code", WeikeAppConstant.OFFICEDOC_RECEIVE);
				js3.put("number", 20);
				JSONObject js4 = new JSONObject();
				js4.put("code", workreportCode);//工作汇报
				js4.put("number", 105);
				JSONObject js5 = new JSONObject();
				js5.put("code", repairCode);//报修
				js5.put("number", 3);
				JSONObject js6 = new JSONObject();
				js6.put("code", bulletinCode);//通知公告
				js6.put("number", 0);
				array.add(js1);
				array.add(js2);
				array.add(js3);
				array.add(js4);
				array.add(js5);
				array.add(js6);
				sendResult(RemoteCallUtils.convertJsons(array).toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
}
