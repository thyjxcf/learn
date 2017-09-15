package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SystemIni;

public interface SystemIniDao {

    /**
     * 根据id获得系统设置
     * 
     * @param id
     * @return
     */
    public SystemIni getSystemIni(Long id);

    /**
     * 系统设置
     * 
     * @param iniId
     * @return
     */
    public SystemIni getSystemIni(String iniId);

    /**
     * 根据visible标志返回相应的系统设置
     * 
     * @param visible
     * @return
     */
    public List<SystemIni> getSystemInis(Integer visible);
    
    /**
     * 
     * @param iniId
     * @param nowValue
     */
    public void updateNowValue(String iniId, String nowValue);


}
