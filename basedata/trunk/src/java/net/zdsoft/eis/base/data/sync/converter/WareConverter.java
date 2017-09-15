package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqWare;

import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class WareConverter implements SyncObjectConvertable<Ware, MqWare>  {

	@Override
	public void toEntity(MqWare m, Ware e) {
//		e.setIsdeleted(m.getIsdeleted());
//		e.setCreationTime(m.getCreationTime());
//		e.setModifyTime(m.getModifyTime());
		e.setCode(m.getCode());
		e.setName(m.getName());
		e.setWareFee(m.getWareFee());
		e.setState(m.getState());
		e.setServerId(m.getServerId());
		e.setServerTypeId(m.getServerTypeId());
		e.setSubscriberType(m.getSubscriberType());
		e.setNums(m.getNums());
		e.setOrderType(m.getOrderType());
		e.setUnitClass(m.getUnitClass());
		e.setRole(m.getRole());
		e.setExperienceMonth(m.getExperienceMonth());
		e.setIsFee(m.isFee()?1:0);
		e.setTeacherRule(m.getTeacherRule());
		e.setStudentRule(m.getStudentRule());
		e.setFamilyRule(m.getFamilyRule());
		e.setAdminRule(m.getAdminRule());
		e.setServerCode(m.getServerCode());
		e.setId(m.getId());

		
	}

	@Override
	public void toMq(Ware e, MqWare m) {
		m.setCode(e.getCode());
		m.setName(e.getName());
		m.setWareFee(e.getWareFee());
		m.setState(e.getState());
		m.setServerId(e.getServerId());
		m.setServerTypeId(e.getServerTypeId());
		m.setSubscriberType(e.getSubscriberType());
		m.setNums(e.getNums());
		m.setOrderType(e.getOrderType());
		m.setUnitClass(e.getUnitClass());
		m.setRole(e.getRole());
		m.setExperienceMonth(e.getExperienceMonth());
		m.setFee(e.getIsFee()==1?true:false);
		m.setTeacherRule(e.getTeacherRule());
		m.setStudentRule(e.getStudentRule());
		m.setFamilyRule(e.getFamilyRule());
		m.setAdminRule(e.getAdminRule());
		m.setServerCode(e.getServerCode());
		m.setId(e.getId());
	}

}
