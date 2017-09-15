package net.zdsoft.eis.remote;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.eis.frame.util.RemoteCallUtils;


public class RemoteSysOptionAction extends RemoteBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6491445331384641804L;
	private SysOptionService sysOptionService;
  
	public void remoteSysOptionValue() throws IOException, Exception{
		String optionCode = getParamValue("optionCode");
		
		if (StringUtils.isBlank(optionCode)) {
			sendResult(RemoteCallUtils.convertError("远程调用optionCode不能为空").toString());
			return;
		}
		String value = sysOptionService.getValue(optionCode);

		JSONObject json = new JSONObject();
		json.put("value", value);
		sendResult(RemoteCallUtils.convertJson(json).toString());
	}
	
	
	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}
}
