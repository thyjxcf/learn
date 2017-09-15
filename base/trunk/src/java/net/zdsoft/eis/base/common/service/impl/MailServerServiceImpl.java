package net.zdsoft.eis.base.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.zdsoft.eis.base.common.entity.MailServerDto;
import net.zdsoft.eis.base.common.service.MailServerService;
import net.zdsoft.leadin.util.ConfigFileUtils;
import net.zdsoft.leadin.util.PWD;

import org.springframework.beans.factory.InitializingBean;

import com.atlassian.mail.MailException;
import com.atlassian.mail.server.PopMailServer;
import com.atlassian.mail.server.SMTPMailServer;
import com.atlassian.mail.server.impl.SMTPMailServerImpl;


/*
 * @author Brave Tao
 * @since 2004-9-28
 * @version $Id: CNetMailServerServiceImpl.java,v 1.1 2006/12/15 08:17:40 chenzy Exp $
 * @since
 */
public class MailServerServiceImpl implements InitializingBean,MailServerService {
    private SMTPMailServer smtpMailServer;

    public void afterPropertiesSet() throws Exception {
    }

	/**
     * 设置改变时可以调用该方法
     */
    public void reload() {
        smtpMailServer = null;
    }

    /**
     * 目前支持smtp邮件服务器
     * @return
     */
    public SMTPMailServer getSMTPMailServer() throws MailException {
        if (smtpMailServer == null) {
        	MailServerDto dto = null;
        	try{
        		dto = getMailServerInfo();
        	}catch(Exception e){
        		throw new MailException("读取邮件服务器配置出错！" + e);
        	}

            if ((dto.getIp() == null) || (dto.getIp().length() == 0)) {
                throw new MailException("邮件服务器未配置！");
            }
            if (dto.getNeedconfirm() == 1) {
				smtpMailServer = new SMTPMailServerImpl(new Long(1), "服务器名",
						"服务器描述", "邮件来源", "邮件主题的前缀", false, dto.getIp(), dto
								.getConfirmusername(), dto.getConfirmuserpwd());
			} else {
				smtpMailServer = new SMTPMailServerImpl(new Long(1), "服务器名",
						"服务器描述", "邮件来源", "邮件主题的前缀", false, dto.getIp(), null, null);

			}
        }

        return smtpMailServer;
    }

    public PopMailServer getPOPMailServer() {
        throw new UnsupportedOperationException("未实现...");
    }


    public MailServerDto getMailServerInfo() throws IOException {
        MailServerDto dto = new MailServerDto();
        Properties pro = new Properties();
        String fileName = ConfigFileUtils.getStoreConfigPath()
                + MailServerDto.MAIL_CONFIG_FILE;
        File file = new File(fileName);
        if (!file.exists()) {
            return dto;
        }
        InputStream in = new FileInputStream(file);
        pro.load(in);
        in.close();
        
        dto.setIp(pro.getProperty(MailServerDto.MAIL_IP));
        dto.setNeedconfirm(Integer.valueOf(pro
                .getProperty(MailServerDto.MAIL_NEEDCONFIRM)));
        dto.setConfirmusername(pro
                .getProperty(MailServerDto.MAIL_CONFIRMUSERNAME));
        dto.setConfirmuserpwd(PWD.decode(pro
                .getProperty(MailServerDto.MAIL_CONFIRMUSERPWD)));
        dto.setDisplayaddress(pro
                .getProperty(MailServerDto.MAIL_DISPLAYADDRESS));
        return dto;
    }
}
