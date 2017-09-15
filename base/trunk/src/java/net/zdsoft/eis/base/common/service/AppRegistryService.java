package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.AppRegistry;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: AppRegistryService.java,v 1.9 2007/01/15 02:21:59 zhaosf Exp $
 */
public interface AppRegistryService {

    /**
     * 保存应用注册信息
     * 
     * @param appRegistry
     */
    public void saveAppRegistry(AppRegistry appRegistry) throws Exception;

    /**
     * 根据id删除应用注册信息
     * 
     * @param appIds 应用注册id
     */
    public void deleteAppRegistries(String[] appIds);

    /**
     * 更新排序号
     * 
     * @param appIds 操作对象id
     * @param targetId 目录对象id
     * @param operateType 操作类型
     */
    public void updateDisplayOrder(String[] appIds, String targetId, String operateType);

    /**
     * 根据id取应用注册信息
     * 
     * @param appId
     * @return
     */
    public AppRegistry getAppRegistry(String appId);

    /**
     * 取得一个注册信息列表
     * 
     * @param unitId
     * @param type
     * @param sysId
     * @return
     */
    public AppRegistry getAppRegistry(String unitId, String type, String sysId);

    /**
     * 取所用的应用注册信息
     * 
     * @param unitId 单位ID
     * @return
     */
    public List<AppRegistry> getAppRegistries(String unitId);

    /**
     * 根据id取应用注册信息
     * 
     * @param appIds 应用注册id
     */
    public List<AppRegistry> getAppRegistries(String[] appIds);

    /**
     * 取有默认权限且在用的应用注册信息
     * 
     * @param unitId 单位ID
     * @return
     */
    public List<AppRegistry> getAppRegistriesDefaultPerm(String unitId);

    /**
     * 取配置的应用平台信息
     * 
     * @return
     */
    public List<AppRegistry> getProductApps();

    /**
     * 取配置的应用平台信息
     * 
     * @return
     */
    public List<String[]> getAppsFromConfig();

    /**
     * 取应用系统信息map
     * 
     * @return
     */
    public Map<String, AppRegistry> getAppMapFromConfig();

}
