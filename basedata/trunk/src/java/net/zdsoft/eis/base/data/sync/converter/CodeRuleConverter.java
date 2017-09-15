package net.zdsoft.eis.base.data.sync.converter;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

import com.winupon.syncdata.basedata.entity.son.MqCodeRule;

public class CodeRuleConverter implements
		SyncObjectConvertable<CodeRule, MqCodeRule> {

	@Override
	public void toEntity(MqCodeRule m, CodeRule e) {
		e.setUnitId(m.getUnitId());
		e.setAutomatism(m.getAutomatism());
		e.setCodeType(m.getCodeType());
		if (StringUtils.isNotBlank(m.getSection())) {
			e.setSection(Integer.parseInt(m.getSection()));
		}
		e.setId(m.getId());
		e.setSystemInit(e.isSystemInit());

	}

	@Override
	public void toMq(CodeRule e, MqCodeRule m) {
		m.setUnitId(e.getUnitId());
		m.setAutomatism(e.getAutomatism());
		m.setCodeType(e.getCodeType());
		m.setSection(String.valueOf(e.getSection()));
		m.setSystemInit(e.isSystemInit());
		m.setId(e.getId());

	}

}
