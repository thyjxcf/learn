package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.subsystemcall.entity.ChargeStuPay;

public interface ChargeSubsystemService {
	public List<ChargeStuPay> getStuFeeDetail (String unitId,User user,String stuId,String acadyea,String term);
}
