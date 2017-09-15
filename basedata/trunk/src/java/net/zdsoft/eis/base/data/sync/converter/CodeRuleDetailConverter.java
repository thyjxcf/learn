package net.zdsoft.eis.base.data.sync.converter;

import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

import com.winupon.syncdata.basedata.entity.son.MqCodeRuleDetail;

public class CodeRuleDetailConverter implements
SyncObjectConvertable<CodeRuleDetail, MqCodeRuleDetail> {

	@Override
	public void toEntity(MqCodeRuleDetail m, CodeRuleDetail e) {
		e.setRuleId(m.getRuleId());
		e.setDataType(m.getDataType());
		e.setRulePosition(m.getRulePosition());
		e.setRuleNumber(m.getRuleNumber());
		e.setConstant(m.getConstant());
		e.setRemark(m.getRemark());
		e.setInSerialNumber(m.isInSerialNumber());
		e.setId(m.getId());

		
	}

	@Override
	public void toMq(CodeRuleDetail e, MqCodeRuleDetail m) {
		m.setRuleId(e.getRuleId());
		m.setDataType(e.getDataType());
		m.setRulePosition(e.getRulePosition());
		m.setRuleNumber(e.getRuleNumber());
		m.setConstant(e.getConstant());
		m.setRemark(e.getRemark());
		m.setInSerialNumber(e.isInSerialNumber());
		m.setId(e.getId());
		
		
	}

}
