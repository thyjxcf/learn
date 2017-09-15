package net.zdsoft.office.remote;

import net.zdsoft.eis.frame.action.BaseAction;

public class RemoteDownloadAttachmentAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4590022281672283026L;
	
	
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String downloadAtt(){
		return SUCCESS;
	}

	
}
