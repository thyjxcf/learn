package net.zdsoft.eis.base.data.entity;

// default package

import java.util.Date;

import net.zdsoft.eis.base.common.entity.Teacher;

public class BaseTeacher extends Teacher {
    private static final long serialVersionUID = -800857156179534639L;
    public static final int EMPID_LENGTH = 8;// 职工编号最大长度
	
    //==========================================辅助字段===============
    private String[] arrayIds;
    private boolean insertWithUser;
    private Date startDate; // 年龄范围的开始时间
    private Date endDate; // 年龄范围的结束时间
    private char unitUseType; // 单位使用类别    
    private String loginName; // 登录帐号
    private Integer userState;//用户状态
    private String userStateStr;     
    private Long sequence;
    private String registertypeContent;// 户口类型
    private String researchGroupId; //教研组id
    private String oldMobile;
    
	public String[] getArrayIds() {
		return arrayIds;
	}
	public void setArrayIds(String[] arrayIds) {
		this.arrayIds = arrayIds;
	}
	public boolean isInsertWithUser() {
		return insertWithUser;
	}
	public void setInsertWithUser(boolean insertWithUser) {
		this.insertWithUser = insertWithUser;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public char getUnitUseType() {
		return unitUseType;
	}
	public void setUnitUseType(char unitUseType) {
		this.unitUseType = unitUseType;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getUserState() {
		return userState;
	}
	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	public String getUserStateStr() {
		return userStateStr;
	}
	public void setUserStateStr(String userStateStr) {
		this.userStateStr = userStateStr;
	}
	public Long getSequence() {
		return sequence;
	}
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	public String getRegistertypeContent() {
		return registertypeContent;
	}
	public void setRegistertypeContent(String registertypeContent) {
		this.registertypeContent = registertypeContent;
	}
	public String getResearchGroupId() {
		return researchGroupId;
	}
	public void setResearchGroupId(String researchGroupId) {
		this.researchGroupId = researchGroupId;
	}
	public String getOldMobile() {
		return oldMobile;
	}
	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}
	
}