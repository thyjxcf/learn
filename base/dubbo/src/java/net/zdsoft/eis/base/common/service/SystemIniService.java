package net.zdsoft.eis.base.common.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SystemIni;

public interface SystemIniService {
    
    /**
     * 取参数的nowvalue，如果为空，则取dvalue
     * @param iniId
     * @return
     */
    public String getValue(String iniId);
    
    /**
     * 取参数的nowvalue，如果为空，则取dvalue
     * @param iniId
     * @return
     */
    public boolean getBooleanValue(String iniId);
    
    /**
     * 获取指定iniId的SystemIni对象
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
    public List<SystemIni> getSystemIniByViewable(Integer visible);
    
    /**
     * 
     * @param iniId
     * @param nowValue
     */
    public void updateNowValue(String iniId, String nowValue);


}
