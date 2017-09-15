/**
 * 
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.leadin.exception.FormatException;

/**
 * @author yanb
 * 
 */
public interface BaseSystemIniService extends SystemIniService {

    /**
     * 保存系统选项重取默认值
     * 
     * @param iniId
     */
    public void saveDefaultValue(String iniId);

    /**
     * 保存
     * 
     * @param iniId
     * @param nowValue
     */
    abstract void saveMod(String iniId, String nowValue);

    /**
     * 保存系统选项，同时验证值是否超过数据库中最大长度
     * 
     * @param systemIniDtos
     * @throws FormatException
     */
    public void saveAllMod(SystemIni[] systemIniDtos) throws FormatException;
    

    /**
     * * 根据可用的子系统中列出子系统下对应的平台参数 
     * @param isVisible 为1时前台平台基础信息中显示，为0时后台管理中显示
     *
     *@author "yangk"
     * Sep 15, 2010 7:02:40 PM
     * @return
     */
    public List<SystemIni> getVisibleSystemIni(int isVisible);
    
}
