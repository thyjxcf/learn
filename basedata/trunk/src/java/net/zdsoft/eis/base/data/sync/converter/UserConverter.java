package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqUser;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class UserConverter implements SyncObjectConvertable<User, MqUser> {

	@Override
	public void toEntity(MqUser m, User e) {
		e.setName(m.getUsername());
		e.setMark(m.getUserState());
		e.setType(m.getUserType());
		e.setRealname(m.getRealName());
		e.setTeacherid(m.getOwnerId());
		e.setUnitid(m.getUnitId());
		e.setEmail(m.getEmail());
		e.setOrderid(m.getDisplayOrder());
		e.setSex(String.valueOf(m.getSex()));
		e.setRegion(m.getRegionCode());
		e.setOwnerType(Long.valueOf(m.getOwnerType()).intValue());
		e.setSequence(m.getSequence());
		e.setAccountId(m.getAccountId());
		e.setChargeNumber(m.getChargeNumber());
		e.setChargeNumberType(m.getChargenumberType());
		e.setId(m.getId());
		e.setOrderStatus(m.getOrderStatus());
		e.setNickName(m.getNickName());
		e.setPassword(m.getPassword());
		e.setDeptid(m.getDeptId());
	}

	@Override
	public void toMq(User e, MqUser m) {
		m.setUsername(e.getName());
		m.setUserState(e.getMark());
		m.setUserType(e.getType());
		m.setRealName(e.getRealname());
		m.setOwnerId(e.getTeacherid());
		m.setUnitId(e.getUnitid());
		m.setEmail(e.getEmail());
		m.setDisplayOrder(e.getOrderid());
		m.setSex(Integer.valueOf(e.getSex()));
		m.setRegionCode(e.getRegion());
		m.setOwnerType(e.getOwnerType());
		m.setSequence(e.getSequence());
		m.setAccountId(e.getAccountId());
		m.setChargeNumber(e.getChargeNumber());
		m.setChargenumberType(e.getChargeNumberType());
		m.setId(e.getId());
		m.setOrderStatus(e.getOrderStatus());
		m.setNickName(e.getNickName());
		m.setPassword(e.findEncodePassword());
		m.setDeptId(e.getDeptid());
	}

	public static void main(String[] args) {
		net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("m", "e",
				User.class, true);
	}
}
