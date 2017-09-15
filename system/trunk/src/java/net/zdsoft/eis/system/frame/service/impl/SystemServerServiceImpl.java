package net.zdsoft.eis.system.frame.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.zdsoft.eis.base.common.entity.MailServerDto;
import net.zdsoft.eis.base.common.service.MailServerService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.core.SmsClientWrapper;
import net.zdsoft.eis.system.constant.SystemConstant;
import net.zdsoft.eis.system.frame.dto.SmsServerDto;
import net.zdsoft.eis.system.frame.service.SystemServerService;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.util.ConfigFileUtils;
import net.zdsoft.leadin.util.PWD;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author chenzy
 * @since 1.0
 * @version $Id: SystemServerServiceImpl.java,v 1.5 2007/01/18 11:01:29 jiangl Exp $
 */
public class SystemServerServiceImpl implements SystemServerService {
	private PWD pwd = new PWD();

	public SmsServerDto getSmsConfig() throws IOException {
		SmsServerDto dto = new SmsServerDto();
		Properties pro = new Properties();
		String fileName = ConfigFileUtils.getStoreConfigPath()
				+ SmsConstant.SMS_CONFIG_FILE;
		File file = new File(fileName);
		if (!file.exists()) {
			return dto;
		}
		InputStream in = new FileInputStream(file);

		if (in == null) {
			return dto;
		} else {
			pro.load(in);
			in.close();
		}
		dto.setServer(pro.getProperty(SmsConstant.SMS_SERVER));
		dto.setPort(pro.getProperty(SmsConstant.SMS_PORT));
		dto.setWorkingServerName(pro
				.getProperty(SystemConstant.SMS_WORKINGSERVERNAME));
		dto.setLocalName(pro.getProperty(SmsConstant.SMS_LOCALNAME));
		dto.setLocalPwd(PWD
				.decode(pro.getProperty(SmsConstant.SMS_LOCALPWD)));
		return dto;
	}

	public void saveMailConfig(MailServerDto dto) throws IOException {
		// 配置文件
		Properties pro = new Properties();
		String fileName = ConfigFileUtils.getStoreConfigPath()
				+ MailServerDto.MAIL_CONFIG_FILE;
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		pro.load(new FileInputStream(fileName));

		// 保存配置
		pro.setProperty(MailServerDto.MAIL_IP, dto.getIp());
		pro.setProperty(MailServerDto.MAIL_NEEDCONFIRM, String.valueOf(dto
				.getNeedconfirm()));
		pro.setProperty(MailServerDto.MAIL_CONFIRMUSERNAME, dto
				.getConfirmusername());
		if (!dto.getConfirmuserpwd().equals(BaseConstant.PASSWORD_VIEWABLE)) {
			pwd.setPassword(dto.getConfirmuserpwd());
			pro.setProperty(MailServerDto.MAIL_CONFIRMUSERPWD, pwd.encode());
		}
		pro.setProperty(MailServerDto.MAIL_DISPLAYADDRESS, dto
				.getDisplayaddress());
		pro.store(new FileOutputStream(fileName), fileName);

		// 初始化邮件服务器配置
		MailServerService mailServer = (MailServerService) ContainerManager
				.getComponent("mailServerService");
		mailServer.reload();
	}

	public void saveSmsConfig(SmsServerDto dto) throws RuntimeException,IOException {
		SmsClientWrapper client = (SmsClientWrapper) ContainerManager
		.getComponent("oaMsgClient");
		//用来判断账号是否通过
		client.test(dto.getServer(), dto.getPort(), dto.getLocalName(),dto.getLocalPwd());
		// 配置文件
		Properties pro = new Properties();
		String fileName = ConfigFileUtils.getStoreConfigPath()
				+ SmsConstant.SMS_CONFIG_FILE;
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		pro.load(new FileInputStream(fileName));

		// 保存配置
		pro.setProperty(SmsConstant.SMS_SERVER, dto.getServer());
		pro.setProperty(SmsConstant.SMS_PORT, dto.getPort());
		// pro.setProperty(SystemConstant.SMS_WORKINGSERVERNAME, dto
		// .getWorkingServerName());
		pro.setProperty(SmsConstant.SMS_LOCALNAME, dto.getLocalName());
		if (!dto.getLocalPwd().equals(BaseConstant.PASSWORD_VIEWABLE)) {
			pwd.setPassword(dto.getLocalPwd());
			dto.setLocalPwd(pwd.encode());
			pro.setProperty(SmsConstant.SMS_LOCALPWD, dto.getLocalPwd());
		}
		pro.store(new FileOutputStream(fileName), fileName);
		// 初始化短信服务器配置
		client.reload();
	}

}
