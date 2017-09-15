package net.zdsoft.eis.homepage.action;

import java.io.IOException;

import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.frame.action.BaseAction;

public class ImAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private SystemDeployService systemDeployService;

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	private String username;
	private String auth;

	public String imMsgRedirectToOffice() {
		String office = systemDeployService.getOfficeUrl();
		String url = office + "/outerap/im/draftMessageList.htm?username="
				+ username + "&auth=" + auth;
		try {
			getResponse().sendRedirect(url);
		} catch (IOException e) {
			log.error("从IM中跳转到办公中心失败", e);
		}
		return SUCCESS;
	}

	public String imIndexRedirectToOffice() {
		String office = systemDeployService.getOfficeUrl();
		String url = office + "/outerap/im/index.htm?username=" + username
				+ "&auth=" + auth;
		try {
			getResponse().sendRedirect(url);
		} catch (IOException e) {
			log.error("从IM中跳转到办公中心失败", e);
		}
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
