package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqTeacher;

import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class TeacherConverter implements
		SyncObjectConvertable<BaseTeacher, MqTeacher> {

	@Override
	public void toEntity(MqTeacher m, BaseTeacher e) {

		e.setPname(m.getOldName());
		e.setBirthday(m.getBirthday());
		e.setPerNative(m.getNativePlace());
		e.setNation(m.getNation());
		e.setPolity(m.getPolity());
		e.setPolityJoin(m.getPolityJoin());
		e.setStulive(m.getAcademicQualification());
		e.setFstime(m.getGraduateTime());
		e.setFsschool(m.getGraduateSchool());
		e.setMajor(m.getMajor());
		e.setWorkdate(m.getWorkDate());
		e.setTitle(m.getTitle());
		e.setOfficeTel(m.getOfficeTel());
		e.setRegistertype(m.getRegisterType());
		e.setRegister(m.getRegisterPlace());
		e.setEmail(m.getEmail());
		e.setRemark(m.getRemark());
		e.setCardNumber(m.getCardNumber());
		e.setHomepage(m.getHomepage());
		e.setLinkPhone(m.getLinkPhone());
		e.setLinkAddress(m.getLinkAddress());
		e.setPostalcode(m.getPostalcode());
		e.setRegionCode(m.getRegionCode());
		e.setChargeNumber(m.getChargeNumber());
		e.setChargeNumberType(m.getChargeNumberType());
		e.setTchId(m.getTeacherCode());
		e.setName(m.getTeacherName());
		e.setSex(String.valueOf(m.getSex()));
		e.setEusing(m.getIncumbencySign());
		e.setIdcard(m.getIdentityCard());
		e.setPersonTel(m.getMobilePhone());
		e.setDeptid(m.getDeptId());
		e.setUnitid(m.getUnitId());
		e.setDisplayOrder(Long.valueOf(m.getDisplayOrder()).intValue());
		e.setId(m.getId());
	}

	@Override
	public void toMq(BaseTeacher e, MqTeacher m) {
		m.setOldName(e.getPname());
		m.setBirthday(m.getBirthday());
		m.setNativePlace(e.getPerNative());
		m.setNation(e.getNation());
		m.setPolity(e.getPolity());
		m.setPolityJoin(e.getPolityJoin());
		m.setAcademicQualification(e.getStulive());
		m.setGraduateTime(e.getFstime());
		m.setGraduateSchool(e.getFsschool());
		m.setMajor(e.getMajor());
		m.setWorkDate(e.getWorkdate());
		m.setTitle(e.getTitle());
		m.setOfficeTel(e.getOfficeTel());
		m.setRegisterType(e.getRegistertype());
		m.setRegisterPlace(e.getRegister());
		m.setEmail(e.getEmail());
		m.setRemark(e.getRemark());
		m.setCardNumber(e.getCardNumber());
		m.setHomepage(e.getHomepage());
		m.setLinkPhone(e.getLinkPhone());
		m.setLinkAddress(e.getLinkAddress());
		m.setPostalcode(e.getPostalcode());
		m.setRegionCode(e.getRegionCode());
		m.setChargeNumber(e.getChargeNumber());
		m.setChargeNumberType(e.getChargeNumberType());
		m.setTeacherCode(e.getTchId());
		m.setTeacherName(e.getName());
		m.setSex(Integer.valueOf(e.getSex()));
		m.setIncumbencySign(e.getEusing());
		m.setIdentityCard(e.getIdcard());
		m.setMobilePhone(e.getPersonTel());
		m.setDeptId(e.getDeptid());
		m.setUnitId(e.getUnitid());
		m.setDisplayOrder(e.getDisplayOrder());
		m.setId(e.getId());
	}

	public static void main(String[] args) {
		net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("m", "e",
				BaseTeacher.class, true);
	}

}
