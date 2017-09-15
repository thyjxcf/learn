package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqServer;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class ServerConverter implements SyncObjectConvertable<Server, MqServer>  {

	@Override
	public void toEntity(MqServer m, Server e) {
//		e.setIsdeleted(m.isDeleted());
//		e.setCreationTime(m.getCreationTime());
//		e.setModifyTime(m.getModifyTime());
		e.setName(m.getName());
		e.setStatus(m.getStatus());
		e.setCode(m.getCode());
		e.setInterfaceType(m.getInterfaceType());
		e.setCapabilityurl(m.getCapabilityurl());
		e.setIntroduceurl(m.getIntroduceurl());
		e.setIndexUrl(m.getIndexUrl());
		e.setLinkPhone(m.getLinkPhone());
		e.setLinkMan(m.getLinkMan());
		e.setAppoint(m.getAppoint());
		e.setServerKey(m.getServerKey());
		e.setProtocol(m.getProtocol());
		e.setDomain(m.getDomain());
		e.setPort(Long.valueOf(m.getPort()).intValue());
		e.setServerCode(m.getServerCode());
		e.setServerTypeId(m.getServerTypeId());
		e.setPassport(m.isPassport());
		e.setId(m.getId());
		e.setSeSyncType(m.getBaseSyncType());
		e.setContext(m.getContext());
	}

	@Override
	public void toMq(Server e, MqServer m) {
		m.setName(e.getName());
		m.setStatus(e.getStatus());
		m.setCode(e.getCode());
		m.setInterfaceType(e.getInterfaceType());
		m.setCapabilityurl(e.getCapabilityurl());
		m.setIntroduceurl(e.getIntroduceurl());
		m.setIndexUrl(e.getIndexUrl());
		m.setLinkPhone(e.getLinkPhone());
		m.setLinkMan(e.getLinkMan());
		m.setAppoint(e.getAppoint());
		m.setServerKey(e.getServerKey());
		m.setProtocol(e.getProtocol());
		m.setDomain(e.getDomain());
		m.setPort(e.getPort());
		m.setServerCode(e.getServerCode());
		m.setServerTypeId(e.getServerTypeId());
		m.setPassport(e.isPassport());
		m.setId(e.getId());
		m.setBaseSyncType(e.getSeSyncType());
		m.setContext(e.getContext());
		
	}

}
