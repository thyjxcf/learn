package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqSchool;

import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class SchoolConverter implements
		SyncObjectConvertable<BaseSchool, MqSchool> {

	@Override
	public void toEntity(MqSchool m, BaseSchool e) {
		e.setEnglishname(m.getEnglishName());
		e.setAddress(m.getAddress());
		e.setPartymaster(m.getPartyMaster());
		e.setDatecreated(m.getBuildDate());
		e.setAnniversary(m.getAnniversary());
		e.setRegiontype(String.valueOf(m.getRegionType()));
		e.setRegioneconomy(String.valueOf(m.getRegionEconomy()));
		e.setRegionnation(String.valueOf(m.getRegionNation()));
		e.setGradeage(m.getGradeAge());
		e.setJuniorage(m.getJuniorAge());
		e.setPrimarylang(m.getPrimaryLang());
		e.setSecondarylang(m.getSecondaryLang());
		e.setRecruitregion(m.getRecruitRegion());
		e.setOrganizationcode(m.getOrganizationCode());
		e.setIntroduction(m.getIntroduction());
		e.setArea(m.getArea());
		e.setShortName(m.getShortName());
		e.setInfantyear(m.getInfantYear());
		e.setInfantage(m.getInfantAge());
		e.setName(m.getSchoolName());
		e.setCode(m.getSchoolCode());
		e.setRegion(m.getRegionCode());
		e.setShoolmaster(m.getSchoolmaster());
		e.setRunschtype(String.valueOf(m.getRunSchoolType()));
		e.setType(m.getSchoolType());
		e.setGradeyear(m.getGradeYear());
		e.setJunioryear(m.getJuniorYear());
		e.setSenioryear(m.getSeniorYear());
		e.setSections(m.getSections());
		e.setId(m.getId());
		e.setEtohSchoolId(m.getSerialNumber());
	}

	@Override
	public void toMq(BaseSchool e, MqSchool m) {
		m.setEnglishName(e.getEnglishname());
		m.setAddress(e.getAddress());
		m.setPartyMaster(e.getPartymaster());
		m.setBuildDate(e.getDatecreated());
		m.setAnniversary(e.getAnniversary());
		m.setRegionType(Integer.valueOf(e.getRegiontype()));
		m.setRegionEconomy(Integer.valueOf(e.getRegioneconomy()));
		m.setRegionNation(Integer.valueOf(e.getRegionnation()));
		m.setGradeAge(e.getGradeage());
		m.setJuniorAge(e.getJuniorage());
		m.setPrimaryLang(e.getPrimarylang());
		m.setSecondaryLang(e.getSecondarylang());
		m.setRecruitRegion(e.getRecruitregion());
		m.setOrganizationCode(e.getOrganizationcode());
		m.setIntroduction(e.getIntroduction());
		m.setArea(e.getArea());
		m.setShortName(e.getShortName());
		m.setInfantYear(e.getInfantyear());
		m.setInfantAge(e.getInfantage());
		m.setSchoolName(e.getName());
		m.setSchoolCode(e.getCode());
		m.setRegionCode(e.getCode());
		m.setSchoolmaster(e.getShoolmaster());
		m.setRunSchoolType(Integer.valueOf(e.getRunschtype()));
		m.setSchoolType(e.getType());
		m.setGradeYear(e.getGradeyear());
		m.setJuniorYear(e.getJunioryear());
		m.setSeniorYear(e.getSenioryear());
		m.setSections(e.getSections());
		m.setId(e.getId());
		m.setSerialNumber(e.getEtohSchoolId());
	}

	public static void main(String[] args) {
		net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("m", "e",
				BaseSchool.class, true);
	}
}
