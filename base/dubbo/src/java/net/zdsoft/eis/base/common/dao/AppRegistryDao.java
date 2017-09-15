package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.AppRegistry;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: AppRegistryDao.java,v 1.10 2007/01/17 05:31:43 zhaosf Exp $
 */
public interface AppRegistryDao {
    public void insertAppRegistry(AppRegistry appRegistry);

    public void deleteAppRegistry(String[] appRegistryIds);

    public void updateAppRegistry(AppRegistry appRegistry);

    public void saveOrUpdate(AppRegistry appRegistry);

    /**
     * 更新排序号
     * 
     * @param idsList
     */
    public void updateDisplayOrder(List<Object[]> idsList);

    /**
     * 根据ID取产品注册信息
     * 
     * @param appId
     *            ID
     * @return AppRegistry对象
     */
    public AppRegistry getAppRegistryById(String appId);
    
    /**
     * 根据ids获得注册信息列表
     * @param appRegistryIds
     * @return
     */
    public List<AppRegistry> getAppRegistrys(String[] appRegistryIds);
    /**
     * 取得一个注册信息列表
     * 
     * @author linqzh 2007-8-15
     * @param unitId
     * @param type
     * @param sysId
     * @return
     */
    public List<AppRegistry> getAppRegistries(String unitId, String type, String sysId);

    /**
     * 取在用的应用注册信息
     * 
     * @param unitid
     *            单位ID
     * @return
     */
    public List<AppRegistry> getAppRegistriesUsing(String unitid);

    /**
     * 取在用的应用注册信息
     * 
     * @param unitid
     *            单位ID
     * @return
     */
    public List<AppRegistry> getAppRegistries(String unitid);

    /**
     * 根据id取排序号
     * 
     * @param ids
     *            应用id
     * @param asc
     *            是否升序
     * @return
     */
    public List<Object[]> getDisplayOrder(String[] ids, boolean asc);

    /**
     * 根据单位id和供下属单位访问取应用注册信息
     * 
     * @param unitids
     *            单位id
     * @param underlingUnitUse
     *            是否供下属单位使用
     * @return
     */
    public List<AppRegistry> getAppRegistriesByUnitidsAndUse(String[] unitids,
            String underlingUnitUse);

    /**
     * 取某单位内的最大排序号
     * 
     * @param unitid
     *            单位id
     * @return
     */
    public int getMaxDisplayOrder(String unitid);

    /**
     * 判断应用注册码是否重复
     * 
     * @param appid
     *            应用注册id
     * @param appcode
     *            应用注册码
     * @return
     */
    public boolean isRepeatAppCode(String appid, String appcode);

    /**
     * 判断应用注册名称是否重复
     * 
     * @param appid
     *            应用注册id
     * @param appname
     *            应用注册名称
     * @param unitid
     *            单位id
     * @return
     */
    public boolean isRepeatAppName(String appid, String appname, String unitid);
}
