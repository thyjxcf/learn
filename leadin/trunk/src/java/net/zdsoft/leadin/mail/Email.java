package net.zdsoft.leadin.mail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import net.zdsoft.keelcnet.util.GeneralUtil;

import com.atlassian.mail.MailException;


/*
 * <p>城域综合信息平台</p>
 * <p>CNet3.0</p>
 * <p>Copyright (c) 2003</p>
 * <p>Company: ZDSoft</p>
 * @author taoy
 * @since 1.0
 * @version $Id: Email.java,v 1.2 2007/01/09 10:04:05 jiangl Exp $
 */
public class Email extends com.atlassian.mail.Email {
    public Email(String to) {
        super(to);
        super.setMimeType("text/html");
        super.setEncoding(GeneralUtil.getCharacterEncoding());
    }

    /* (non-Javadoc)
     * @see com.atlassian.mail.Email#getMultipart()
     */
    public Multipart getMultipart() {
        Multipart multipart = super.getMultipart();

        if (multipart == null) {
            multipart = new MimeMultipart();
            super.setMultipart(multipart);
        }

        return multipart;
    }

    private static MimeBodyPart createAttachmentMimeBodyPart(String path,
        String rename) throws MessagingException {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        javax.activation.DataSource source = new FileDataSource(path);
        attachmentPart.setDataHandler(new DataHandler(source));

        if (rename == null) {
            StringTokenizer st = new StringTokenizer(path, "\\/");

            do {
                rename = st.nextToken();
            } while (st.hasMoreTokens());
        } else if (rename.indexOf('.') == -1) {
            String suffix = path.substring(path.lastIndexOf('.'));
            rename.concat(suffix);
        }

        try {
            rename = MimeUtility.encodeWord(rename,
                    GeneralUtil.getCharacterEncoding(), null);
            attachmentPart.setFileName(rename);
        } catch (UnsupportedEncodingException e) {
            throw new MessagingException(e.getMessage());
        }

        return attachmentPart;
    }

    public void addAttachment(String filepath, String fileRename)
        throws MailException {
        if (filepath == null) {
            throw new IllegalArgumentException("filepath is null!");
        }

        try {
            MimeBodyPart attachmentPart = createAttachmentMimeBodyPart(filepath,
                    fileRename);
            this.getMultipart().addBodyPart(attachmentPart);
        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    public void addAttachment(File file) throws MailException {
        if ((file == null) || (false == file.exists())) {
            throw new MailException((file == null) ? "文件为空!"
                                                   : ("文件 [" +
                file.getAbsolutePath() + "] 没有找到!"));
        }

        try {
            this.addAttachment(file.getCanonicalPath(), null);
        } catch (IOException e) {
            throw new MailException(e);
        }
    }

    public void addAttachment(File file, String fileRename)
        throws MailException {
        if ((file == null) || (false == file.exists())) {
            throw new MailException((file == null) ? "文件为空!"
                                                   : ("文件 [" +
                file.getAbsolutePath() + "] 没有找到!"));
        }

        try {
            this.addAttachment(file.getCanonicalPath(), fileRename);
        } catch (IOException e) {
            throw new MailException(e);
        }
    }

    public void addAttachments(String[] filepath) throws MailException {
        if (filepath == null) {
            throw new IllegalArgumentException("filepath is null!");
        }

        for (int i = 0; i < filepath.length; i++) {
            this.addAttachment(filepath[i], null);
        }
    }

    public void addAttachments(File[] file) throws MailException {
        if (file == null) {
            throw new IllegalArgumentException("file is null!");
        }

        for (int i = 0; i < file.length; i++) {
            this.addAttachment(file[i]);
        }
    }
}
