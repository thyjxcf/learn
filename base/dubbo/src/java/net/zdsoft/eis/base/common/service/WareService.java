package net.zdsoft.eis.base.common.service;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.Ware;

public interface WareService {
    /**
     * 根据商品id数组取起映射信息
     * @param wareIds
     * @return
     */
    public Map<String,Ware> getWares(String[] wareIds);

}
