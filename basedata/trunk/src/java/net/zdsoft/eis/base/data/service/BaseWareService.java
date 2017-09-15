package net.zdsoft.eis.base.data.service;


import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseWareService {
	public void addWare(Ware ware);
	 public void deleteWare(String[] wareIds,EventSourceType eventSource);
	 public void updateWare(Ware ware);
}
