package net.zdsoft.eis.system.frame.dto;

import java.io.Serializable;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author chenzy
 * @since 1.0
 * @version $Id: SmsServerDto.java,v 1.2 2007/01/09 10:04:15 jiangl Exp $
 */
public class SmsServerDto implements Serializable {
    private static final long serialVersionUID = -4215687247680351325L;

    private String server;

	private String port;

	private String workingServerName;

	private String localName;

	private String localPwd;

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getLocalPwd() {
		return localPwd;
	}

	public void setLocalPwd(String localPwd) {
		this.localPwd = localPwd;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getWorkingServerName() {
		return workingServerName;
	}

	public void setWorkingServerName(String workingServerName) {
		this.workingServerName = workingServerName;
	}
		
}
