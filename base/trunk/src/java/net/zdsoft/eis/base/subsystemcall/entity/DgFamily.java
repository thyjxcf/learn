package net.zdsoft.eis.base.subsystemcall.entity;

import net.zdsoft.eis.base.common.entity.Family;

public class DgFamily extends Family {
	private static final long serialVersionUID = -4363770540059433134L;

	public StudentInformalFamilyTemp toFamilyStore(){
		StudentInformalFamilyTemp sftent =new StudentInformalFamilyTemp();
			sftent.setId(this.getId());
			sftent.setStudentId(this.getStudentId());
			sftent.setSchoolId(this.getSchoolId());
			sftent.setRelation(this.getRelation());
			sftent.setRealName(this.getName());
			sftent.setNation(this.getNation());
			sftent.setLinkAddress(this.getLinkAddress());
			sftent.setRegionCode(this.getRegisterPlace());
			sftent.setMobilePhone(this.getMobilePhone());
			sftent.setIsGuardian(this.isGuardian()==true?1:0);
			sftent.setCompany(this.getCompany());
			sftent.setDuty(this.getDuty());
			sftent.setRemark(this.getRemark());
			sftent.setIdentityCard(this.getIdentityCard());
			sftent.setIdentitycardType(this.getIdentitycardType());
		return sftent;
	}
	
}
