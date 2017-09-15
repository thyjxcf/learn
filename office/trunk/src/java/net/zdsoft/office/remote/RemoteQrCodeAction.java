package net.zdsoft.office.remote;

import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.frame.action.BaseAction;

import org.apache.commons.lang.StringUtils;

public class RemoteQrCodeAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4590022281672283026L;
	
	private SysOptionService sysOptionService;
	
	private String androidPath = "";
	private String iosPath = "";
	
	public String execute() {
		return SUCCESS;
	}

	//下载apk
	public String download(){
		String s = getRequest().getServerName()+":"+getRequest().getServerPort()+getRequest().getContextPath();
		String fileUrl=sysOptionService.getValue("FILE.URL");
		if(StringUtils.isNotBlank(fileUrl)){
			if (!fileUrl.endsWith("/")) {
				fileUrl += "/";
			}
			androidPath = fileUrl+"mobile/androidoa.apk";
			iosPath = fileUrl+"mobile/ios-install.html";
		}	
		return SUCCESS;
	}

	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}

	public String getAndroidPath() {
		return androidPath;
	}

	public void setAndroidPath(String androidPath) {
		this.androidPath = androidPath;
	}

	public String getIosPath() {
		return iosPath;
	}

	public void setIosPath(String iosPath) {
		this.iosPath = iosPath;
	}
	
}
