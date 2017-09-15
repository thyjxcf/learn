package net.zdsoft.office.bulletin.entity;

import java.io.Serializable;
/**
 * office_bulletin_set
 * @author 
 * 
 */
public class OfficeBulletinSet implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String needAudit;
	/**
	 * 
	 */
	private String auditId;
	
	private String auditNames;
	private String roleCode;

	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
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
	public void setNeedAudit(String needAudit){
		this.needAudit = needAudit;
	}
	/**
	 * 获取
	 */
	public String getNeedAudit(){
		return this.needAudit;
	}
	/**
	 * 设置
	 */
	public void setAuditId(String auditId){
		this.auditId = auditId;
	}
	/**
	 * 获取
	 */
	public String getAuditId(){
		return this.auditId;
	}
	public String getAuditNames() {
		return auditNames;
	}
	public void setAuditNames(String auditNames) {
		this.auditNames = auditNames;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}