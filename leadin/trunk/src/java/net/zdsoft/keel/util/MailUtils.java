/* 
 * @(#)MailUtils.java    Created on 2005-4-1
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/MailUtils.java,v 1.6 2007/01/11 01:17:24 liangxiao Exp $
 */
package net.zdsoft.keel.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件工具类
 * 
 * @author liangxiao
 * @version $Revision: 1.6 $, $Date: 2007/01/11 01:17:24 $
 */
public class MailUtils {

    private static Logger logger = LoggerFactory.getLogger(MailUtils.class);

    private static String charSet = "UTF-8";

    private MailUtils() {
    }

    /**
     * 设置字符集
     * 
     * @param charSet
     *            字符集
     */
    public static void setCharSet(String charSet) {
        MailUtils.charSet = charSet;
    }

    /**
     * 发送邮件
     * 
     * @param smtpServer
     *            smtp服务器地址，类似smtp.21cn.com
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param address
     *            接收方邮箱
     * @param subject
     *            邮件标题
     * @param content
     *            邮件内容
     * @return 成功true，否则false
     */
    public static boolean send(String smtpServer, final String username,
            final String password, String address, String subject,
            String content) {
        return send(smtpServer, username, password, address, subject, content,
                null);
    }

    /**
     * 发送带附件的邮件
     * 
     * @param smtpServer
     *            smtp服务器地址，类似smtp.21cn.com
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param address
     *            接收方邮箱
     * @param subject
     *            邮件标题
     * @param content
     *            邮件内容
     * @param attachs
     *            附件的路径
     * @return 成功true，否则false
     */
    public static boolean send(String smtpServer, final String username,
            final String password, String address, String subject,
            String content, String[] attachs) {
        String mimeType = "text/plain; charset=" + charSet;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.auth", "true");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(props, authenticator);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setSentDate(new Date());
            message
                    .setFrom(new InternetAddress(
                            username.indexOf("@") == -1 ? (username + "@" + smtpServer
                                    .substring(smtpServer.indexOf('.') + 1))
                                    : username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                    address));
            message.setSubject(subject);

            if (attachs != null && attachs.length != 0) {
                MimeMultipart mimeMultipart = new MimeMultipart();
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(content, mimeType);
                mimeMultipart.addBodyPart(mimeBodyPart);

                for (int i = 0; i < attachs.length; i++) {
                    MimeBodyPart attachMBP = new MimeBodyPart();
                    String[] addressAndTitle = attachs[i].split(";");
                    FileDataSource fds = new FileDataSource(addressAndTitle[0]);
                    attachMBP.setDataHandler(new DataHandler(fds));
                    attachMBP.setFileName(MimeUtility.encodeWord(
                            addressAndTitle[1], charSet, null));
                    mimeMultipart.addBodyPart(attachMBP);
                }
                message.setContent(mimeMultipart);
            }
            else {
                message.setContent(content, mimeType);
            }
            Transport.send(message);
        }
        catch (Exception e) {
            logger.error("Could not send mail", e);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        MailUtils.setCharSet("utf-8");
        System.out.println(MailUtils.send("mail.winupon.com",
                "edupass@winupon.com", "zdsoft", "lunawing@21cn.com", "test",
                "欢迎您"));
    }
}
