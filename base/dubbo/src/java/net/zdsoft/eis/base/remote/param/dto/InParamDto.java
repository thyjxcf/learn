/**
 * 调用webservice的接口参数 
 */
package net.zdsoft.eis.base.remote.param.dto;

/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: InParamDto.java,v 1.2 2006/12/30 08:07:20 zhaosf Exp $
 */

public class InParamDto {
	
	private String unitguid;//学校guid
	private String unionid;//学校编号
	private String unitname;//学校名称
	private String loginname;//登录名
	private String pwd;//密码	
	private String version;//接口版本

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUnionid() {
		return unionid;
	}

	public String getUnitguid() {
		return unitguid;
	}

	public String getUnitname() {
		return unitname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public void setUnitguid(String unitguid) {
		this.unitguid = unitguid;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	
	
}
