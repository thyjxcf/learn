/* 
 * @(#)TopUnitRegisterAction.java    Created on Jul 2, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.action;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployUtils;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.leadin.util.UUIDGenerator;

/**
 * 顶级单位管理员
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 2, 2010 3:09:51 PM $
 */
public class TopUnitAdministratorAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private BaseUserService baseUserService;
	private PassportAccountService passportAccountService;

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public Reply changeUserName(String loginName, String oName, String password) {
		Reply reply = new Reply();

		User userDto = baseUserService.getTopUser();
		if (!oName.equals(userDto.getName())) {
			reply.addActionError("原管理员账号错误！");
			return reply;
		}
		String pwd = userDto.findClearPassword();
		if (!pwd.equals(password)) {
			PWD pwdUtils = new PWD(password);
			if (!pwd.equals(pwdUtils.encode())) {
				reply.addActionError("管理员密码输入错误！");
				return reply;
			}
		}

		if (null != loginName) {
			if (passportAccountService.queryAccountByUsername(loginName
					.substring(0, loginName.indexOf("|pwd|"))) != null) {
				reply.addActionError("该账号已经被人注册，请重新输入！");
				return reply;
			}
			userDto.setName(loginName.substring(0, loginName.indexOf("|pwd|")));
			userDto.setPassword(loginName
					.substring(loginName.indexOf("|pwd|") + 5));
			if (org.apache.commons.lang.StringUtils.isBlank(userDto
					.getAccountId())) {
				userDto.setAccountId(UUIDGenerator.getUUID());
			}
		}
		try {
			baseUserService.updateUser(userDto, true);
		} catch (Exception e) {
			e.printStackTrace();
			reply.addActionError(e.getMessage());
		}
		reply.addActionMessage("账号修改成功!");
		return reply;
	}

	public Reply cmtUser(String username, String pwd) {
		Reply reply = new Reply();
		if (org.apache.commons.lang.StringUtils.isBlank(username)
				|| org.apache.commons.lang.StringUtils.isBlank(pwd)) {
			reply.addActionError("请输入账号和密码！");
			return reply;
		}
		User user = baseUserService.getTopUser();
		if (user != null) {
			if (username.equalsIgnoreCase(user.getName())) {
				if (pwd.equalsIgnoreCase(user.findClearPassword())) {
					reply.addActionMessage("验证成功");
					reply.setValue(SystemDeployUtils.getEssVerifyKey());
					return reply;
				}
			}
		}
		reply.addActionError("验证失败！");
		return reply;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

}
