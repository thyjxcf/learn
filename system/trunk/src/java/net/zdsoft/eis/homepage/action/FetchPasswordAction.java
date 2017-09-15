/**
 * 
 */
package net.zdsoft.eis.homepage.action;

import com.atlassian.mail.server.SMTPMailServer;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.MailServerService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.keelcnet.util.GeneralUtil;
import net.zdsoft.leadin.mail.Email;

/**
 * <p>
 * ZDSoft学籍系统（stusys）V3.5
 * </p>
 * 
 * @author zhaosf
 * @since 1.0
 * @version $Id: FetchPasswordAction.java,v 1.3 2007/01/19 02:57:03 zhaosf Exp $
 */

public class FetchPasswordAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    private UserService userService;// 用户信息
    private MailServerService mailServerService;

    private String loginName;// 登录名
    private String userEmail;// 邮箱

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    /**
     * 找回密码
     * 
     * @param archiveDto
     * @param sendUnitNamesStr
     * @param receUnitId
     * @param unitList
     */
    public String sendMail() {

        if (Validators.isEmpty(loginName)) {
            addFieldError("loginName", "用户名不能为空，请输入！");
        }

        if (Validators.isEmpty(userEmail)) {
            addFieldError("userEmail", "邮件地址不能为空，请输入！");
        }
        if (hasFieldErrors())
            return INPUT;

        if (!Validators.isEmail(userEmail)) {
            addFieldError("userEmail", "邮件地址格式不正确！");
            return INPUT;
        }

        String userMail = "";
        try {
            User user = userService.getUserByUserName(loginName);
            if (null == user || user.getName() == null) {
                addFieldError("loginName", "不存在此用户！");
                return INPUT;
            }

            userMail = user.getEmail();
            if (null == userMail)
                userMail = "";
            if (!userMail.equals(userEmail)) {
                addFieldError("userEmail", "您输入的邮件地址不正确，请确认后重新输入！");
                return INPUT;
            }

            log.info("发送邮件...");

            Email mail = new Email(userMail); // 接受邮件地址
            mail.setEncoding(GeneralUtil.getCharacterEncoding()); // 编码
            mail.setSubject("找回密码"); // 邮件标题
            mail.setBody("您好！账号：" + loginName + "    密码：" + user.findClearPassword()); // 邮件内容
            mail.setFromName("统一平台"); // 发送者名称
            mail.setMimeType("text/plain");// html: text/html
            // 以下几行是因为有些邮件服务器无法接收无发件人信息的邮件，比如163.com邮箱
            // 所以增加了发件人信息代码。发件人取自于邮件服务器设置时的发件人地址。
            try {
                mail.setFrom(mailServerService.getMailServerInfo().getDisplayaddress());
            } catch (Exception e) {
                log.debug("读取邮件服务器配置信息失败");
            }
            if (mail.getFrom() == null || mail.getFrom().trim().length() == 0) {
                mail.setFrom("mail_server@zdsoft.net");
            }
            log.debug("邮件发件人：" + mail.getFrom());

            SMTPMailServer mailServer = mailServerService.getSMTPMailServer();
            mailServer.setPrefix("统一平台提醒您：");
            mailServer.send(mail);

            log.info("发送邮件成功！");
        } catch (Exception e) {
            addActionError("邮件发送" + userMail + "失败！");
            return INPUT;
        }

        return SUCCESS;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setMailServerService(MailServerService mailServerService) {
        this.mailServerService = mailServerService;
    }



}
