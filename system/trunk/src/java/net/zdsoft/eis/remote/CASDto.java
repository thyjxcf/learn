package net.zdsoft.eis.remote;

import java.io.Serializable;

/**
 * 
 * @author weixh
 * @since 2017-1-14 下午3:50:32
 */
@SuppressWarnings("serial")
public class CASDto implements Serializable{
	private String casUrl;
	private String serviceUrl;
	
	public String getCasUrl() {
		return casUrl;
	}
	public void setCasUrl(String casUrl) {
		this.casUrl = casUrl;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
}
