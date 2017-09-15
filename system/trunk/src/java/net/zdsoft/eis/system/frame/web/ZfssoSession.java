package net.zdsoft.eis.system.frame.web;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;

import net.zdsoft.eis.base.common.entity.LoginLog;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.base.common.service.LoginLogService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.deploy.SystemDeployUtils;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.system.data.service.UserLoginService;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.keelcnet.util.GeneralUtil;
import net.zdsoft.leadin.dataimport.subsystemcall.LoginUser;
import net.zdsoft.leadin.util.RequestUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.zfca.tp.cas.client.ZfssoBean;
import com.zfsoft.zfca.tp.cas.client.ZfssoSetsessionService;

public class ZfssoSession implements ZfssoSetsessionService{
	
	protected transient final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Boolean chkUserSession(ZfssoBean zfssobean) {
		Boolean res=false;
		LoginInfo loginInfo = null;  //此处User类为示例类，请使用自身项目类(用户实体类bean)
        //校验用户session是否存在，存在返回true，失败返回false;
		if(null!=zfssobean.getSession().getAttribute(BaseConstant.SESSION_LOGININFO)){  //此处user可以根据项目实际情况调整
			loginInfo =(LoginInfo) zfssobean.getSession().getAttribute(BaseConstant.SESSION_LOGININFO);
		}
		//session不为空时，判断与认证传递过来的用户是否一致
		if(null != loginInfo && (zfssobean.getYhm().equalsIgnoreCase(loginInfo.getUser().getName()))){
			res=true;
		}
		return res;
	}

	@Override
	public Boolean setUserSession(ZfssoBean zfssobean) {
		Boolean res=false;
		//请采用项目实际情况进行登录用户session设置
		if(null==zfssobean.getYhlx()||"".equalsIgnoreCase(zfssobean.getYhlx())){ //如果自身项目不需要区分登录用户账号类型，则不需要此判断
            //集成zfca认证模式时，如果不是从门户点击链接，yhlx可能为空，查询的sql语句要另外处理。
			LoginInfo loginInfo = null;
			UserService userService = (UserService) ContainerManager.getComponent("userService");
			// 学生登录
			User user = userService.getUserByUserName(zfssobean.getYhm());
			if(user != null) {
				Set<Integer> activeSubSytem = new HashSet<Integer>();
				UnitService unitService = (UnitService) ContainerManager.getComponent("unitService");
				Unit unit = unitService.getUnit(user.getUnitid());
				switch (user.getOwnerType()) {
				case User.STUDENT_LOGIN:
					break;
				case User.FAMILY_LOGIN:
					loginInfo = new LoginInfo(user, unit, activeSubSytem);
					break;
				case User.OTHER_LOGIN:
					break;
				default:
					UserLoginService userLoginService = (UserLoginService) ContainerManager.getComponent("userLoginService");
					loginInfo = userLoginService.initLoginInfo(zfssobean.getYhm());
					break;
				}
				int serverId = 0;
				int serverTypeId = 0;
				ServerService serverService = (ServerService) ContainerManager.getComponent("serverService");
				Server app = serverService.getServerByServerCode(SystemDeployUtils
						.getCurrentDeployAppCode());
				if (null != app) {
					serverId = Integer.parseInt(app.getId());
					serverTypeId = Long.valueOf(app.getServerTypeId()).intValue();
				}
				
				try {
					// 写入登录日志
					String remoteAddr = RequestUtils.getRealRemoteAddr(zfssobean.getRequest());
					Date clickDate = new Date();
					LoginLog log = new LoginLog();
					if (StringUtils.isNotBlank(loginInfo.getUser().getAccountId())) {
						log.setAccountId(loginInfo.getUser().getAccountId());
						log.setCreationTime(clickDate);
						log.setLoginTime(clickDate);
						log.setRegionCode(loginInfo.getUser().getRegion());
						log.setRemoteIp(remoteAddr);
						log.setServerId(serverId);
						log.setServerTypeId(serverTypeId);
						log.setUnitId(loginInfo.getUnitID());
						log.setUserType(loginInfo.getUser().getOwnerType());
						LoginLogService loginLogService = (LoginLogService) ContainerManager.getComponent("loginLogService");
						loginLogService.insert(log);
					} else {
						this.log.error("user:" + loginInfo.getUser().getName()
								+ " have no accountId!");
					}
					
				} catch (Exception e) {
					log.error("add login log error!", e);
				}
				// 记录登录ip
				loginInfo.setClientIP(RequestUtils.getRealRemoteAddr(zfssobean.getRequest()));
				zfssobean.getSession().setAttribute(BaseConstant.SESSION_LOGININFO, loginInfo);
				
				LoginUser loginUser = new LoginUser();
				loginUser.setUnitId(loginInfo.getUnitID());
				loginUser.setUserId(loginInfo.getUser().getGuid());
				loginUser.setUserIntId(loginInfo.getUser().getIntId());
			    UserSetService userSetService = (UserSetService) ContainerManager.getComponent("userSetService");
				loginUser.setSkin(userSetService.getSkinByUserId(zfssobean.getRequest().getSession().getServletContext(),
						loginInfo.getUser().getId(), loginInfo.getUser().getOwnerType(), true));
				UserSet us = userSetService.getUserSetByUserId(loginInfo.getUser().getId());
				if(us == null){
					us = new UserSet();
				}
				if(StringUtils.isBlank(us.getLayout())){
					us.setLayout(UserSet.LAYOUT_UP);
					us.setTheme(UserSet.THEME_DEFAULT);
				}
				loginUser.setBgColor(us.getBackgroundColor());
				loginUser.setBgImg(us.getBackgroundImg());
				loginUser.setLayout(us.getLayout());
				loginUser.setTheme(us.getTheme());
				zfssobean.getSession().setAttribute(BaseConstant.SESSION_LOGINUSER, loginUser);
				
				Cookie c = new Cookie("user_login_id", "");
				c.setVersion(0);
				c.setMaxAge(86400);
				c.setValue(GeneralUtil.urlEncode(zfssobean.getYhm()));
				zfssobean.getResponse().addCookie(c);
				res=true;
		}else{
			}
		}
		return res;
	}

}
