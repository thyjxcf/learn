package net.zdsoft.eis.base.data.sync.service.impl;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.data.service.BaseCodeRuleService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

import com.winupon.syncdata.basedata.entity.son.MqCodeRule;

public class CodeRuleSyncServiceImpl extends AbstractHandlerTemplate<CodeRule, MqCodeRule> {
private BaseCodeRuleService baseCodeRuleService;

	public void setBaseCodeRuleService(BaseCodeRuleService baseCodeRuleService) {
	this.baseCodeRuleService = baseCodeRuleService;
}

	@Override
	public void addData(CodeRule e) throws BusinessErrorException {
		baseCodeRuleService.saveUnitCodeRule(e);
		
	}

	@Override
	public void deleteData(String arg0, EventSourceType arg1) throws BusinessErrorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CodeRule fetchOldEntity(String id) {
		return null;
	}

	@Override
	public void updateData(CodeRule e) throws BusinessErrorException {
		baseCodeRuleService.updateUnitCodeRule(e);
		
	}

}
