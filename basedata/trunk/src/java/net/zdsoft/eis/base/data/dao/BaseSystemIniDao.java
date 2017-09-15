/**
 * 
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SystemIni;

/**
 * @author yanb
 * 
 */
public interface BaseSystemIniDao {

    /**
     * 
     * @param iniId
     * @param nowValue
     */
    public void updateNowValue(String iniId, String nowValue);

    /**
     * 获得所有的系统设置
     * 
     * @return
     */
    public List<SystemIni> getAllSystemInis();

}
