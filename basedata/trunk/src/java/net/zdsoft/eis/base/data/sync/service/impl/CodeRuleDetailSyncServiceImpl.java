package net.zdsoft.eis.base.data.sync.service.impl;

import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.data.service.BaseCodeRuleService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

import com.winupon.syncdata.basedata.entity.son.MqCodeRuleDetail;

public class CodeRuleDetailSyncServiceImpl extends AbstractHandlerTemplate<CodeRuleDetail, MqCodeRuleDetail>{
	private BaseCodeRuleService baseCodeRuleService;

	public void setBaseCodeRuleService(BaseCodeRuleService baseCodeRuleService) {
	this.baseCodeRuleService = baseCodeRuleService;
}
	@Override
	public void addData(CodeRuleDetail e) throws BusinessErrorException {
		baseCodeRuleService.saveUnitCodeRuleListFromMq(e);
	}

	@Override
	public void deleteData(String id, EventSourceType e) throws BusinessErrorException {
		baseCodeRuleService.deleteUnitCodeRuleList4Mq(id, e);
		
	}

	@Override
	public CodeRuleDetail fetchOldEntity(String id) {
		return null;
	}

	@Override
	public void updateData(CodeRuleDetail e) throws BusinessErrorException {
		baseCodeRuleService.updateUnitCodeRuleListFromMq(e);
	}
}
