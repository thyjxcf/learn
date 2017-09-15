/**
 * 
 */
package net.zdsoft.eis.base.data.action;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.passport.service.client.PassportClient;

/** 
 * @author zhaosf
 * @since 1.0
 * @version $Id: PassportAccountAction.java, v 1.0 2007-8-3 上午11:16:25 zhaosf Exp $
 */

public class PassportAccountAction extends BaseAction  {
	
	private static final long serialVersionUID = 5062189197698101255L;
	
	private PassportAccountService passportAccountService;
	
	private BaseUserService baseUserService;
	
	private String serverId;
	
	private String userName;
	
	@Override
	public String execute() throws Exception {
		//将数据库中现有用户添加到passport中
		passportAccountService.addAccounts();
				 
		 // 输出success字符串
        ServletUtils.print(this.getResponse(), SUCCESS);
        
		return SUCCESS;
	}
	
	public String syncAdmin() throws Exception{
	    User user = baseUserService.getTopUser();
	    if (StringUtils.isBlank(user.getAccountId())){
	    	user.setAccountId(UUIDGenerator.getUUID());
    	}
    
	    log.error("----(not error but for recording)sync Admin user accountId = " + user.getAccountId());
	    
	    if (PassportClient.getInstance().queryAccount(user.getAccountId()) != null){
	    	passportAccountService.modifyAccount(user, null);    	
	    }
	    else{
	    	passportAccountService.addAccount(user);
//	    	baseUserService.updateUser(user, true);
	    }
        
        // 输出success字符串
       ServletUtils.print(this.getResponse(), SUCCESS);
       return SUCCESS;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBaseUserService(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

}
