package net.zdsoft.eis.system.data.dao;

import java.util.List;

import net.zdsoft.eis.system.data.entity.UnitChannel;

/**
 * @author yanb
 * 
 */
public interface UnitChannelDao {
    /**
     * 增加
     * 
     * @param unitChannel
     */
    public void insertUnitChannel(UnitChannel unitChannel);

    /**
     * 根据单位id删除数据
     */
    public void deleteUnitChannel(String unitId);

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
