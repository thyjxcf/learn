/* 
 * 
 */
package net.zdsoft.eis.base.data.action;

import javax.servlet.http.HttpServletResponse;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.service.client.PassportClient;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: SyncUserAction.java, v 1.0 2007-7-19 下午05:30:40 zhaosf Exp $
 */
public class SyncUserAction extends BaseAction {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7889871069809179466L;

    private BaseUserService baseUserService;
    private Account account = new Account();

    // =====================================
    private String email;
    private String id;
    private String realName;

    private String password;
    // =====================================

    private String auth;

    public Object getModel() {
        return account;
    }

    public String syncPassword() throws Exception {
        account.setId(id);
        account.setPassword(password);

        HttpServletResponse response = getResponse();
        if (account == null) {
            ServletUtils.print(response, ERROR);
        } else {
            if (!PassportClient.getInstance().isValidSyncAccountAuth(account.getId(), auth)) {
                ServletUtils.print(response, ERROR);
            } else {
                try {
                    syncUserPassword();
                } catch (Exception e) {
                    ServletUtils.print(response, ERROR);
                    return SUCCESS;
                }
                ServletUtils.print(response, SUCCESS);
            }
        }
        return SUCCESS;
    }

    public String execute() throws Exception {
        account.setId(id);
        account.setEmail(email);
        account.setRealName(realName);
        account.setPassword(password);

        log.debug("---password:" + password);

        HttpServletResponse response = getResponse();
        if (account == null) {
            ServletUtils.print(response, ERROR);
        } else {
            // if (!PassportClient.isValidSyncAccountAuth(account.getId(), auth)) {
            // ServletUtils.print(response, ERROR);
            // }
            // else {
            try {
                saveUser();
            } catch (Exception e) {
                ServletUtils.print(response, ERROR + " code: " + e.getMessage());
                return SUCCESS;
            }
            ServletUtils.print(response, SUCCESS);
            // }
        }
        return SUCCESS;
    }

    private void syncUserPassword() throws Exception {
        User userDto = baseUserService.getUserByAccountId(account.getId());
        if (null == userDto)
            return;

        String password = account.getPassword();
        if (null != password) {
            password = password.trim();            
            userDto.setPassword(password);
        }
        baseUserService.updateUserByPassport(userDto);
    }

    /**
     * 更新用户信息
     */
    private void saveUser() throws Exception {
        User userDto = baseUserService.getUserByAccountId(account.getId());
        if (null == userDto)
            return;

        String email = account.getEmail();
        if (null != email) {
            email = email.trim();
            if (email.length() > 50) {
                email = email.substring(0, 50);
            }
            userDto.setEmail(email);
        }

        String realName = account.getRealName();
        if (null != realName) {
            realName = realName.trim();
            userDto.setRealname(realName);
        }

        String password = account.getPassword();

        if (null != password) {
            password = password.trim();
            userDto.setPassword(password);
        }
        baseUserService.updateUserByPassport(userDto);
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBaseUserService(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

}
