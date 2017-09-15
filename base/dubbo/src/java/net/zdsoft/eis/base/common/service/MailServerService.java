package net.zdsoft.eis.base.common.service;

import java.io.IOException;

import com.atlassian.mail.MailException;
import com.atlassian.mail.server.PopMailServer;
import com.atlassian.mail.server.SMTPMailServer;

import net.zdsoft.eis.base.common.entity.MailServerDto;

/*
 * @author Brave Tao
 * @since 2004-9-28
 * @version $Id: CNetMailServerService.java,v 1.1 2006/12/15 08:17:30 chenzy Exp $
 * @since
 */
public interface MailServerService {
	/**
	 * 设置改变时可以调用该方法
	 */
	public void reload();

	/**
	 * 目前支持smtp邮件服务器
	 * 
	 * @return
	 */
	public SMTPMailServer getSMTPMailServer() throws MailException;

	/**
	 * 未实现
	 * 
	 * @return
	 */
    public PopMailServer getPOPMailServer();
    
    /**
     * 获取邮件服务器配置信息
     * @return
     */
    public MailServerDto getMailServerInfo()  throws IOException;
}
