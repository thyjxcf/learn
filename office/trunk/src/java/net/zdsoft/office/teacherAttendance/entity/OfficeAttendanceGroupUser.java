package net.zdsoft.office.teacherAttendance.entity;


import java.io.Serializable;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * 考勤组成员
 * @author 
 * 
 */
public class OfficeAttendanceGroupUser extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String groupId;
	/**
	 * 
	 */
	private String userId;

	/**
	 * 设置
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	/**
	 * 获取
	 */
	public String getGroupId(){
		return this.groupId;
	}
	/**
	 * 设置
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取
	 */
	public String getUserId(){
		return this.userId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfficeAttendanceGroupUser other = (OfficeAttendanceGroupUser) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
}