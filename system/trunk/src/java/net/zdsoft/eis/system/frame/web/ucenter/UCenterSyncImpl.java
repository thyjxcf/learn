package net.zdsoft.eis.system.frame.web.ucenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseLoginInfo;

import org.hzdysoft.uc.model.Result;
import org.hzdysoft.uc.sync.UcSync;

/**
 * 
 * @author weixh
 * @since 2016-12-1 上午10:58:50
 */
public class UCenterSyncImpl implements UcSync {
	/* 
	 * @see org.hzdysoft.uc.sync.UcSync#AfterAuthentication(javax.servlet.http.HttpServletRequest, org.hzdysoft.uc.model.Result)
	 * 认证成功后会调用该方法，需要在这里实现第三方应用的登录后初始化工作
	 */
	@Override
	public void AfterAuthentication(HttpServletRequest req, Result result) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			BaseLoginInfo login = (BaseLoginInfo) session
					.getAttribute(BaseConstant.SESSION_LOGININFO);
			if(login != null){
				return;
			}
		}
		String username = result.getUID();
//		username="yiyi";
		req.setAttribute("uid", username);
	}

	/* 
	 * @see org.hzdysoft.uc.sync.UcSync#IsAuthentication(javax.servlet.http.HttpServletRequest)
	 * 当页面需要通过认证才能访问所需的条件，通常aspx页面才需要认证，比如css,js文件等，不需要认证就能访问
	 */
	@Override
	public boolean IsAuthentication(HttpServletRequest req) {
		return true;
	}

	/* 
	 * @see org.hzdysoft.uc.sync.UcSync#IsForcedLogin(javax.servlet.http.HttpServletRequest)
	 * 当未认证时需要强制登录所需的条件，例如，网站后台路径 则这里需要返回true，必须是登录后才能访问
	 */
	@Override
	public boolean IsForcedLogin(HttpServletRequest req) {
		return true;
	}

}
