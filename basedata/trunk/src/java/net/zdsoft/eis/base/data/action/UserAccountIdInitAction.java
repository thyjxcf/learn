package net.zdsoft.eis.base.data.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.frame.action.BaseAction;

/* 
 * <p>数字校园2.0</p>
 * 用户account_id,sequence,owner_type,region_code的初始化Action
 * 陕西从数字校园1.0升级到数字校园2.0中，
 * 原来的user表没有这四个字段，需要在部署的时候从原来的库及passport初始化这四个字段
 * @author fangb
 * @since  2.0
 * @version $Id:,v 2.0  Exp UserAccountIdInitAction.java, v 2.0 2008-9-22 下午01:20:56  Exp $
 */

public class UserAccountIdInitAction extends BaseAction {
    private static final long serialVersionUID = 1641408101224320395L;
    
    private BaseUserService baseUserService;

	public String returnSuccess() {
		return SUCCESS;
	}

	public String execute() {
		promptMessageDto
				.addOperation(new String[] { "返回",
						"accountInit-admin.action" });
		List<String> idsWithNoAccount = null;

		try {
			idsWithNoAccount = baseUserService.initAccountId();
		} catch (Exception e) {
			this.promptMessageDto.setErrorMessage("初始化失败，错误信息："
					+ e.getMessage());
			return PROMPTMSG;
		}

		promptMessageDto.setOperateSuccess(true);
		if (idsWithNoAccount == null || idsWithNoAccount.isEmpty()) {
			this.promptMessageDto.setPromptMessage("成功初始化用户表中的accountId");
		} else {
			this.promptMessageDto
					.setPromptMessage("初始化部分用户的accountId,下面主键的用户在Passprot中没有对应的Account信息，无法初始化:<br>"
							+ StringUtils.join(idsWithNoAccount.iterator(),
									"<br>"));
		}
		return PROMPTMSG;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

}
