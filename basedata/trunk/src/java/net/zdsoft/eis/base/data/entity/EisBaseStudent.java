package net.zdsoft.eis.base.data.entity;

import net.zdsoft.eis.base.common.entity.Student;

/**
 * 
 * @author weixh
 * @since 2016-3-1 上午10:15:39
 */
@SuppressWarnings("serial")
public class EisBaseStudent extends Student {
	
	private String userName;//账号
	private String password;//密码
	private String sexStr; //性别  男 、女
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}
	
}
