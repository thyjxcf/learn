package net.zdsoft.eis.sms.entity;

/**
 * 类说明
 *
 * @author jiangf
 * @version 创建时间：2011-8-9 下午03:20:46
 */

public class SmsStat {
	
	private String id;

	private String deptName; // 统计的分组名

	private String deptId; // 统计的分组ID

	private String sucessItemsCount; // 成功发送的条数

	private String failItemsCount; // 失败发送的条数

	private boolean haveChild = true;

	private String htmlSucessItemsCount;

	private String htmlFailItemsCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSucessItemsCount() {
		return sucessItemsCount;
	}

	public void setSucessItemsCount(String sucessItemsCount) {
		this.sucessItemsCount = sucessItemsCount;
	}

	public String getFailItemsCount() {
		return failItemsCount;
	}

	public void setFailItemsCount(String failItemsCount) {
		this.failItemsCount = failItemsCount;
	}

	public boolean isHaveChild() {
		return haveChild;
	}

	public void setHaveChild(boolean haveChild) {
		this.haveChild = haveChild;
	}

	public String getHtmlSucessItemsCount() {
		return htmlSucessItemsCount;
	}

	public void setHtmlSucessItemsCount(String htmlSucessItemsCount) {
		this.htmlSucessItemsCount = htmlSucessItemsCount;
	}

	public String getHtmlFailItemsCount() {
		return htmlFailItemsCount;
	}

	public void setHtmlFailItemsCount(String htmlFailItemsCount) {
		this.htmlFailItemsCount = htmlFailItemsCount;
	}
}
