package net.zdsoft.eis.base.data.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.frame.client.BaseEntity;

public class UserAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String mobilePhone;
	private Date birthday;
	private String type; // 用户类型:11：中小学生、12：在职教师、13：退休教师、14：在校大学生、15：家长
	private Integer ownerType;
	private int Sex;
	private String homepage;
	private String email;
	private String extraId;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getType() {
		if (User.STUDENT_LOGIN == ownerType) {
			type = "11";
		} else if (User.FAMILY_LOGIN == ownerType) {
			type = "15";
		} else {
			if (StringUtils.isBlank(type)
					|| BasedataConstants.EMPLOYEE_PLURALITY.equals(type) || // 兼职
					BasedataConstants.EMPLOYEE_INCUMBENCY.equals(type) || // 在职
					BasedataConstants.EMPLOYEE_PROBATION.equals(type) || // 试用
					BasedataConstants.EMPLOYEE_BORROW.equals(type) || // 借用
					BasedataConstants.EMPLOYEE_TEMP.equals(type)// 临时
			) {
				type = "12";
			} else {
				type = "13";
			}
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExtraId() {
		return extraId;
	}

	public void setExtraId(String extraId) {
		this.extraId = extraId;
	}

}
