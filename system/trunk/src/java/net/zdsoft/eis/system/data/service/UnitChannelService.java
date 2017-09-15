package net.zdsoft.eis.system.data.service;

import java.util.List;

import net.zdsoft.eis.system.data.entity.UnitChannel;

/**
 * @author yanb
 * 
 */
public interface UnitChannelService {

    /**
     * 单位频道设置
     * 
     * @param unitChannelList
     */
    public void saveUnitChannels(List<UnitChannel> unitChannelList);

    /**
     * 初始化单位信息
     * 
     * @param unitId
     * @param unitClass
     */
    public void initUnitChannel(String unitId, int unitClass);

    /**
     * 得到所有的单位频道设置信息
     * 
     * @param unitId
     * @param unitClass
     */
    public List<UnitChannel> getUnitChannels(String unitId, int unitClass);

    /**
     * 
     * @param unitId
     * @param unitClass
     * @param displayLevel
     * @return
     */
    public List<UnitChannel> getUnitChannels(String unitId, int unitClass, int displayLevel);
}
