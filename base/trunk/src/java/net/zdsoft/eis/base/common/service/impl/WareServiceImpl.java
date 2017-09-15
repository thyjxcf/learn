package net.zdsoft.eis.base.common.service.impl;

import java.util.Map;

import net.zdsoft.eis.base.common.dao.WareDao;
import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.common.service.WareService;

public class WareServiceImpl implements WareService {
    private WareDao wareDao;

    public void setWareDao(WareDao wareDao) {
        this.wareDao = wareDao;
    }

    public Map<String, Ware> getWares(String[] wareIds) {
        return wareDao.getWares(wareIds);
    }
}
