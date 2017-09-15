package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqTeacherDuty;

import net.zdsoft.eis.base.data.entity.BaseTeacherDuty;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class TeacherDutyConverter implements
		SyncObjectConvertable<BaseTeacherDuty, MqTeacherDuty> {

	@Override
	public void toEntity(MqTeacherDuty m, BaseTeacherDuty e) {
		e.setId(m.getId());
		e.setTeacherId(m.getTeacherId());
		e.setDutyCode(m.getDutyCode());
	}

	@Override
	public void toMq(BaseTeacherDuty e, MqTeacherDuty m) {
		m.setId(e.getId());
		m.setTeacherId(e.getTeacherId());
		m.setDutyCode(e.getDutyCode());
	}

}
