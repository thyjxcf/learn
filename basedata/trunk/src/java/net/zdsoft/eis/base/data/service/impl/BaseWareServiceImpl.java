package net.zdsoft.eis.base.data.service.impl;


import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.data.dao.BaseWareDao;
import net.zdsoft.eis.base.data.service.BaseWareService;
import net.zdsoft.eis.base.sync.EventSourceType;

public class BaseWareServiceImpl implements BaseWareService {
	private BaseWareDao baseWareDao;

	public void setBaseWareDao(BaseWareDao baseWareDao) {
		this.baseWareDao = baseWareDao;
	}

	@Override
	public void deleteWare(String[] wareIds, EventSourceType eventSource) {
		baseWareDao.deleteWare(wareIds, eventSource);

	}

	@Override
	public void addWare(Ware ware) {
		baseWareDao.insertWare(ware);

	}

	@Override
	public void updateWare(Ware ware) {
		baseWareDao.updateWare(ware);

	}

}
