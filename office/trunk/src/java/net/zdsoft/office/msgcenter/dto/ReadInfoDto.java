package net.zdsoft.office.msgcenter.dto;

import java.util.List;

/**
 * @author chens
 * @version 创建时间：2015-4-1 上午10:20:17
 * 
 */
public class ReadInfoDto {

	private String unitName;//单位名称
	private List<String> userDeptNameList;//人员姓名-部门list
	
	private List<String> userIdList;
	private List<String> detailNames;
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public List<String> getUserDeptNameList() {
		return userDeptNameList;
	}
	public void setUserDeptNameList(List<String> userDeptNameList) {
		this.userDeptNameList = userDeptNameList;
	}
	public List<String> getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}
	public List<String> getDetailNames() {
		return detailNames;
	}
	public void setDetailNames(List<String> detailNames) {
		this.detailNames = detailNames;
	}
	
}
