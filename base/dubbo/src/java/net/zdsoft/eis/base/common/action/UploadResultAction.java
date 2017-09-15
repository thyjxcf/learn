package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.frame.action.BaseAction;

public class UploadResultAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private String fileerror;
	
	public String execute(){
		return SUCCESS;
	}

	public String getFileerror() {
		return fileerror;
	}

	public void setFileerror(String fileerror) {
		this.fileerror = fileerror;
	}

}
