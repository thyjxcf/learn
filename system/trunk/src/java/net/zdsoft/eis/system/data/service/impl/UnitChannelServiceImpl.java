package net.zdsoft.eis.system.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.system.constant.SystemConstant;
import net.zdsoft.eis.system.data.dao.UnitChannelDao;
import net.zdsoft.eis.system.data.entity.UnitChannel;
import net.zdsoft.eis.system.data.service.UnitChannelService;

/**
 * @author yanb
 * 
 */
public class UnitChannelServiceImpl implements UnitChannelService {

    private UnitChannelDao unitChannelDao;

    public void setUnitChannelDao(UnitChannelDao unitChannelDao) {
        this.unitChannelDao = unitChannelDao;
    }

    public void saveUnitChannels(List<UnitChannel> unitChannelList) {
        String unitId;
        if (unitChannelList != null && unitChannelList.size() > 0) {
            unitId = unitChannelList.get(0).getUnitid();
            unitChannelDao.deleteUnitChannel(unitId);
            for (UnitChannel channelIni : unitChannelList) {
                unitChannelDao.insertUnitChannel(channelIni);
            }
        }
    }

    public void initUnitChannel(String unitId, int unitClass) {
        List<UnitChannel> list = getUnitChannels(
                SystemConstant.DEFAULT_EDU_GUID, unitClass);
        UnitChannel ini;
        List<UnitChannel> listOfChannel = new ArrayList<UnitChannel>();
        for (UnitChannel channelIni : list) {
            ini = new UnitChannel();
            ini.setUnitid(unitId);
            ini.setCode(channelIni.getCode());
            ini.setDisplay(channelIni.getDisplay());
            ini.setMark(channelIni.getMark());
            ini.setName(channelIni.getName());
            ini.setOrderid(channelIni.getOrderid());
            ini.setUrl(channelIni.getUrl());
            ini.setDisplayLevel(0);
            listOfChannel.add(ini);
        }
        saveUnitChannels(listOfChannel);
    }

    public List<UnitChannel> getUnitChannels(String unitId, int unitClass) {
        return unitChannelDao.getUnitChannels(unitId, unitClass);
    }

    public List<UnitChannel> getUnitChannels(String unitId, int unitClass, int displayLevel) {
        return unitChannelDao.getUnitChannels(unitId, unitClass, displayLevel);
    }

}
