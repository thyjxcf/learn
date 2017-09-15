package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.frame.action.BaseAction;

@SuppressWarnings("serial")
public class TipMsgAction extends BaseAction {

	private String msg;
	private String subMsg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}
	
	public String execute(){
		
		return SUCCESS;
	}
}
