package net.zdsoft.eis.base.common.entity;

import java.io.Serializable;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author chenzy
 * @since 1.0
 * @version $Id: MailServerDto.java,v 1.1 2006/12/15 08:17:14 chenzy Exp $
 */
public class MailServerDto implements Serializable {
    private static final long serialVersionUID = 6927708186548579115L;
    

    // 邮件和通信服务的配置文件
    public static final String MAIL_CONFIG_FILE = "mailserver.properties";

    // ----邮件服务属性名称
    public static final String MAIL_IP = "ip";
    public static final String MAIL_NEEDCONFIRM = "needconfirm";
    public static final String MAIL_CONFIRMUSERNAME = "confirmusername";
    public static final String MAIL_CONFIRMUSERPWD = "confirmuserpwd";
    public static final String MAIL_DISPLAYADDRESS = "displayaddress";
    
    private String ip; // 服务器IP
    private int needconfirm; // 服务器是否需要验证，0不需要；1需要
    private String confirmusername; // 验证用户名
    private String confirmuserpwd; // 验证密码
    private String displayaddress; // 显示发送地址

    public MailServerDto() {
    }

    public String getConfirmusername() {
        return confirmusername;
    }

    public void setConfirmusername(String confirmusername) {
        this.confirmusername = confirmusername;
    }

    public String getConfirmuserpwd() {
        return confirmuserpwd;
    }

    public void setConfirmuserpwd(String confirmuserpwd) {
        this.confirmuserpwd = confirmuserpwd;
    }

    public String getDisplayaddress() {
        return displayaddress;
    }

    public void setDisplayaddress(String displayaddress) {
        this.displayaddress = displayaddress;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getNeedconfirm() {
        return needconfirm;
    }

    public void setNeedconfirm(int needconfirm) {
        this.needconfirm = needconfirm;
    }
}
