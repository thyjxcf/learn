package net.zdsoft.leadin.mail;

import com.atlassian.mail.MailException;
import com.atlassian.mail.server.MailServer;
import com.atlassian.mail.server.PopMailServer;
import com.atlassian.mail.server.SMTPMailServer;
import com.atlassian.mail.server.impl.SMTPMailServerImpl;
import com.atlassian.mail.server.managers.XMLMailServerManager;

import com.opensymphony.util.TextUtils;

import net.zdsoft.keelcnet.config.BootstrapManager;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;


/*
 * 邮件服务器管理类
 * 默认读取store目录下的cnet-mail.cfg.xml文件
 *
 * <p>城域综合信息平台</p>
 * <p>CNet3.0</p>
 * <p>Copyright (c) 2003</p>
 * <p>Company: ZDSoft</p>
 * @author taoy
 * @since 1.0
 * @version $Id: CNetMailServerManager.java,v 1.1 2007/01/12 08:32:38 chenzy Exp $
 */
public class MailServerManager extends XMLMailServerManager {
    private static final String TAG_ROOT = "mail-servers";

    public MailServerManager() {
    }

    /**
     * 读取配置信息
     * 读取顺序：
     * 1、jar中配置文件
     * 2、store目录下的配置文件
     */
    protected InputStream getConfigurationInputStream(String resource) {
        try {
            File file = new File(BootstrapManager
                                               .getStoreHome(), resource);

            if (file.isFile()) {
                return new FileInputStream(file);
            }
        } catch (Exception e) {
        }

        return super.getConfigurationInputStream(resource);
    }

    /**
     * 删除邮件服务器
     */
    public void delete(Long mailServerId) throws MailException {
        super.delete(mailServerId);
        refresh();
    }

    /**
     * 更新邮件服务器
     */
    public void update(MailServer mailServer) throws MailException {
        refresh();
    }

    /* (non-Javadoc)
     * @see com.atlassian.mail.server.MailServerManager#getDefaultSMTPMailServer()
     */
    public SMTPMailServer getDefaultSMTPMailServer() throws MailException {
        return super.getDefaultSMTPMailServer();
    }

    /**
     * 保存信息到配置文件
     * @throws MailException
     */
    @SuppressWarnings("unchecked")
    private void refresh() throws MailException {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement(TAG_ROOT);
        SMTPMailServer smtpMailServer;

        for (Iterator iterator = getSmtpMailServers().iterator();
                iterator.hasNext(); addMailServerXml(root, smtpMailServer)) {
            smtpMailServer = (SMTPMailServer) iterator.next();
        }

        PopMailServer popMailServer;

        for (Iterator iterator = getPopMailServers().iterator();
                iterator.hasNext(); addMailServerXml(root, popMailServer)) {
            popMailServer = (PopMailServer) iterator.next();
        }

        saveXmlDocument(doc);
    }

    /**
     * 创建smtp邮件服务器
     */
    public synchronized Long create(MailServer mailServer)
        throws MailException {
        Long id = super.create(mailServer);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement(TAG_ROOT);
        addMailServerXml(root, mailServer);
        saveXmlDocument(doc);

        return id;
    }

    /**
     * 添加邮件服务器
     * @param root
     * @param mailServer
     */
    private void addMailServerXml(Element root, MailServer mailServer) {
        Element smtpServerElem = root.addElement("smtp-server");
        smtpServerElem.addElement("name").setText(mailServer.getName());

        if (mailServer.getDescription() != null) {
            smtpServerElem.addElement("description").setText(mailServer.getDescription());
        }

        if (mailServer.getUsername() != null) {
            smtpServerElem.addElement("username").setText(mailServer.getUsername());
        }

        if (mailServer.getPassword() != null) {
            smtpServerElem.addElement("password").setText(mailServer.getPassword());
        }

        if (mailServer instanceof SMTPMailServerImpl) {
            SMTPMailServerImpl smtpMailServer = (SMTPMailServerImpl) mailServer;
            String from = smtpMailServer.getDefaultFrom();

            if (from != null) {
                smtpServerElem.addElement("defaultFrom").setText(from);
            }

            String prefix = smtpMailServer.getPrefix();

            if (prefix != null) {
                smtpServerElem.addElement("prefix").setText(prefix);
            }

            String host = smtpMailServer.getHostname();

            if (TextUtils.stringSet(host)) {
                smtpServerElem.addElement("hostname").setText(host);
                smtpServerElem.addElement("sessionServer").setText(Boolean.toString(
                        false));
            }

            String jndiLocation = smtpMailServer.getJndiLocation();

            if (TextUtils.stringSet(jndiLocation)) {
                smtpServerElem.addElement("jndi-location").setText(smtpMailServer.getJndiLocation());
                smtpServerElem.addElement("sessionServer").setText(Boolean.toString(
                        true));
            }
        }
    }

    /**
     * 保存邮件服务器配置信息
     * @param doc
     * @throws MailException
     */
    private void saveXmlDocument(Document doc) throws MailException {
        try {
            saveDocumentTo(doc,
                    BootstrapManager.getStoreHome(),
                getConfigFile());
        } catch (IOException e) {
            throw new MailException("无法保存 " + getConfigFile() + " 到 " +
                BootstrapManager.getStoreHome() + " 目录!", e);
        }
    }
    
    private void saveDocumentTo(Document doc, String folder,
        String file_name) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        File file = new File(folder, file_name);

        if (!file.exists()) {
            file.createNewFile();
        }

        XMLWriter writer = null;

        try {
            FileWriter out = new FileWriter(file);
            writer = new XMLWriter(out, format);
            writer.write(doc);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
