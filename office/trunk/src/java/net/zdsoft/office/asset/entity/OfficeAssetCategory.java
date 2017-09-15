package net.zdsoft.office.asset.entity;

import net.zdsoft.eis.frame.client.BaseEntity;


/**
 * office_asset_category
 * @author 
 * 
 */
public class OfficeAssetCategory extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 类别名称
	 */
	private String assetName;
	/**
	 * 处室负责人
	 */
	private String deptLeaderId;
	/**
	 * 分管校领导
	 */
	private String leaderId;
	
	private boolean is_DeptLeader;
	private boolean is_Leader;
	private boolean is_master;
	private boolean is_meeting;

	//辅助字段
	private String leaderName;
	private String deptLeaderName;
	
	
	public String getDeptLeaderName() {
		return deptLeaderName;
	}
	public void setDeptLeaderName(String deptLeaderName) {
		this.deptLeaderName = deptLeaderName;
	}
	public String getDeptLeaderId() {
		return deptLeaderId;
	}
	public void setDeptLeaderId(String deptLeaderId) {
		this.deptLeaderId = deptLeaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	/**
	 * 设置
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置
	 */
	public void setAssetName(String assetName){
		this.assetName = assetName;
	}
	/**
	 * 获取
	 */
	public String getAssetName(){
		return this.assetName;
	}
	/**
	 * 设置
	 */
	public void setLeaderId(String leaderId){
		this.leaderId = leaderId;
	}
	/**
	 * 获取
	 */
	public String getLeaderId(){
		return this.leaderId;
	}
	
	public boolean isIs_master() {
		return is_master;
	}
	public void setIs_master(boolean is_master) {
		this.is_master = is_master;
	}
	public boolean isIs_DeptLeader() {
		return is_DeptLeader;
	}
	public void setIs_DeptLeader(boolean is_DeptLeader) {
		this.is_DeptLeader = is_DeptLeader;
	}
	public boolean isIs_Leader() {
		return is_Leader;
	}
	public void setIs_Leader(boolean is_Leader) {
		this.is_Leader = is_Leader;
	}
	public boolean isIs_meeting() {
		return is_meeting;
	}
	public void setIs_meeting(boolean is_meeting) {
		this.is_meeting = is_meeting;
	}
	
}