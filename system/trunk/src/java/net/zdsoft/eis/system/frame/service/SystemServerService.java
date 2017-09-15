package net.zdsoft.eis.system.frame.service;

import java.io.IOException;

import net.zdsoft.eis.base.common.entity.MailServerDto;
import net.zdsoft.eis.system.frame.dto.SmsServerDto;
/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author chenzy
 * @since 1.0
 * @version $Id: SystemServerService.java,v 1.1 2006/12/15 08:16:51 chenzy Exp $
 */
public interface SystemServerService {
	/**
	 * 保存邮件服务器配置信息
	 * @param dto
	 */
	abstract void saveMailConfig( MailServerDto dto)  throws IOException;
	/**
	 * 保存通信服务器配置信息
	 * @param dto
	 */
	abstract void saveSmsConfig(SmsServerDto dto)  throws IOException;

	/**
	 * 获取短信服务器配置信息
	 * @return
	 */
	public SmsServerDto getSmsConfig()  throws IOException;
}
