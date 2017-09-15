/*
 * Class:   BusinessStepDto.java
 * Author:  Sophie Dong
 * Copyright (c) 2006 winupon Networks, Inc. All rights reserved.
 * 
 * Last modified & Comments:
 * <BR>$BusinessStepDto.java$
 * <BR>Revision 1.00  2008-3-7  上午09:48:12  dongxx
 * <BR>first version
 * <BR>
 */
package net.zdsoft.eis.base.auditflow.template.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.auditflow.template.entity.AbstractBusinessStep;
import net.zdsoft.eis.frame.dto.BaseDto;

/**
 * 审核步骤
 * 
 * @author dongxx
 */
public class BusinessStepDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	/**
	 * 审核单位级别
	 */
	private Integer auditUnitLevel;

	/**
	 * 审核顺序号
	 */
	private Integer auditOrder;

	/**
	 * 审核单位
	 */
	private String operateUnit;
	
	/**
	 * 审核单位名称
	 */
	private String unitname;

	/**
	 * 审核结果
	 */
	private Integer state;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 审核时间
	 */
	private Date auditDate;

	/**
	 * 申请单位id
	 */
	private String applyUnitId;

	/**
	 * 审核意见
	 */
	private String opinion;

	/**
	 * 申请id
	 */
	private String applyId;

	/**
	 * 操作人
	 */
	private String operateUser;
	
	/**
	 * 用于批量审核，传入步骤id数组
	 */
	private String[] stepids;
	
//	1：教育局审核且允许学校导入招生信息；2：教育局审核且不允许学校导入分派信息；3、学校自主招生  (默认为 2)
	private String recruitMode;

	public static void toEntity(BusinessStepDto dto, AbstractBusinessStep entity) {
		entity.setId(dto.getId());
		entity.setApplyId(dto.getApplyId());
		entity.setApplyUnitId(dto.getApplyUnitId());
		entity.setAuditDate(dto.getAuditDate());
		entity.setAuditOrder(dto.getAuditOrder());
		entity.setAuditUnitLevel(dto.getAuditUnitLevel());
		entity.setBusinessType(dto.getBusinessType());
		entity.setOperateUnit(dto.getOperateUnit());
		entity.setOperateUser(dto.getOperateUser());
		entity.setOpinion(dto.getOpinion());
		entity.setState(dto.getState());
	}

	public static void toDto(AbstractBusinessStep entity, BusinessStepDto dto) {
		dto.setId(entity.getId());
		dto.setApplyId(entity.getApplyId());
		dto.setApplyUnitId(entity.getApplyUnitId());
		dto.setAuditDate(entity.getAuditDate());
		dto.setAuditOrder(entity.getAuditOrder());
		dto.setAuditUnitLevel(entity.getAuditUnitLevel());
		dto.setBusinessType(entity.getBusinessType());
		dto.setOperateUnit(entity.getOperateUnit());
		dto.setOperateUser(entity.getOperateUser());
		dto.setOpinion(entity.getOpinion());
		dto.setState(entity.getState());
	}

	public static List<BusinessStepDto> toDtoList(
			List<AbstractBusinessStep> entities) {
		List<BusinessStepDto> dtos = new ArrayList<BusinessStepDto>(entities
				.size());
		for (AbstractBusinessStep entity : entities) {
			BusinessStepDto dto = new BusinessStepDto();
			toDto(entity, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyUnitId() {
		return applyUnitId;
	}

	public void setApplyUnitId(String applyUnitId) {
		this.applyUnitId = applyUnitId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getAuditOrder() {
		return auditOrder;
	}

	public void setAuditOrder(Integer auditOrder) {
		this.auditOrder = auditOrder;
	}

	public Integer getAuditUnitLevel() {
		return auditUnitLevel;
	}

	public void setAuditUnitLevel(Integer auditUnitLevel) {
		this.auditUnitLevel = auditUnitLevel;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getOperateUnit() {
		return operateUnit;
	}

	public void setOperateUnit(String operateUnit) {
		this.operateUnit = operateUnit;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String[] getStepids() {
		return stepids;
	}

	public void setStepids(String[] stepids) {
		this.stepids = stepids;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getRecruitMode() {
		return recruitMode;
	}

	public void setRecruitMode(String recruitMode) {
		this.recruitMode = recruitMode;
	}

}
