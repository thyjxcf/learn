package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.dao.AppRegistryDao;
import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.AppRegistryService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.xml.XMLConfigReader;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: AppRegistryServiceImpl.java,v 1.22 2007/01/29 07:02:08 zhaosf Exp $
 */
public class AppRegistryServiceImpl implements AppRegistryService {

    private static final Logger log = LoggerFactory.getLogger(AppRegistryServiceImpl.class);

    private static final String SYS_APP_CONFIG = "sys_application.xml";// 系统默认配置
    private static final String PRODUCT_CONFIG = "product.xml";// 产品信息

    private static final String UP = "up";// 上移
    // private static final String DOWN = "down";//下移

    private AppRegistryDao appRegistryDao;// 应用注册信息
    private SubSystemService subSystemService;// 子系统
    private UnitService unitService;// 单位信息

    public void setAppRegistryDao(AppRegistryDao appRegistryDao) {
        this.appRegistryDao = appRegistryDao;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void saveAppRegistry(AppRegistry appRegistry) throws Exception {
        // 判断注册名称是否重复
        if (appRegistryDao.isRepeatAppName(appRegistry.getId(), appRegistry.getAppname(),
                appRegistry.getUnitid())) {
            throw new ItemExistsException("注册名称重复", "appname");
        }

        // 如果顶级单位注册应用系统,则注册名称不能与本地自带的子系统名称重复,
        // 否则显示应用系统列表时名称会重复
        Unit unit = unitService.getUnit(appRegistry.getUnitid());
        if ((Unit.UNIT_CLASS_EDU == unit.getUnitclass() && Unit.UNIT_EDU_TOP == unit
                .getUnittype())
                || (Unit.UNIT_CLASS_SCHOOL == unit.getUnitclass())) {
            if (subSystemService.isRepeatSubSystemName(appRegistry.getAppname())) {
                throw new ItemExistsException("注册名称与已启用的子系统名称重复", "appname");
            }
        }

        // 判断注册码是否重复
        if (appRegistryDao.isRepeatAppCode(appRegistry.getId(), appRegistry.getAppcode())) {
            throw new ItemExistsException("注册码重复，不能同时注册多个相同的应用", "appcodeShow");
        }

        String appid = appRegistry.getId();
        if ("".equals(appid))
            appid = null;

        // 图标和描述
        Map<String, AppRegistry> map = this.getAppMapFromConfig();
        AppRegistry app = (AppRegistry) map.get(appRegistry.getSysid());
        if (null != app) {
            appRegistry.setSysname(app.getSysname());
            appRegistry.setImage(app.getImage());
            appRegistry.setDescription(app.getDescription());
            appRegistry.setXurl(app.getXurl());
            appRegistry.setSharedataurl(app.getSharedataurl());
            appRegistry.setTesturl(app.getTesturl());
            appRegistry.setType(app.getType());
            appRegistry.setParameters(app.getParameters());
            appRegistry.setCheckuserurl(app.getCheckuserurl());
            appRegistry.setDatabasepass(app.getDatabasepass());
            appRegistry.setIsencoded(app.getIsencoded());
            appRegistry.setIslogin(app.getIslogin());
        } else {
            // 应用系统已去掉,须删除此应用注册
            log.info("产品标识为" + appRegistry.getSysid() + "已删除");

            if (null != appid) {
                this.deleteAppRegistries(new String[] { appRegistry.getId() });
                return;
            }
        }

        // 保存应用注册信息
        if (null == appid) {
            int max = appRegistryDao.getMaxDisplayOrder(appRegistry.getUnitid()) + 1;
            if (max > Integer.MAX_VALUE) {
                log.error("排序号超出整数范围");
                max = 0;
            }
            appRegistry.setDisplayorder(max);// 排序号
            appRegistryDao.insertAppRegistry(appRegistry);
        } else {
            appRegistryDao.saveOrUpdate(appRegistry);
        }
    }

    public void deleteAppRegistries(String[] appIds) {
        appRegistryDao.deleteAppRegistry(appIds);
    }

    public void updateDisplayOrder(String[] appIds, String targetId, String operateType) {
        List<Object[]> saveList = new ArrayList<Object[]>();

        AppRegistry targetApp = (AppRegistry) appRegistryDao.getAppRegistryById(targetId);
        List<Object[]> appList = null;
        if (UP.equals(operateType)) {// 上移
            appList = appRegistryDao.getDisplayOrder(appIds, true);// 升序
        } else {// 下移
            appList = appRegistryDao.getDisplayOrder(appIds, false);// 降序
        }

        int order = 0;
        if (null != targetApp.getDisplayorder()) {
            order = targetApp.getDisplayorder();
        }
        for (Iterator<Object[]> iter = appList.iterator(); iter.hasNext();) {
            Object[] objs = (Object[]) iter.next();
            int curOrder = 0;
            if (null != objs[0]) {
                curOrder = Integer.parseInt(String.valueOf(objs[0]));
            }
            objs[0] = order;
            order = curOrder;
            saveList.add(objs);
        }
        saveList.add(new Object[] { order, targetId });
        appRegistryDao.updateDisplayOrder(saveList);
    }

    public AppRegistry getAppRegistry(String unitId, String type, String sysId) {
        List<AppRegistry> listOfAppRegistry = appRegistryDao.getAppRegistries(unitId, type, sysId);
        AppRegistry appRegistry;
        if (null != listOfAppRegistry && listOfAppRegistry.size() > 0) {
            appRegistry = listOfAppRegistry.get(0);
        } else {
            appRegistry = new AppRegistry();
        }
        return appRegistry;
    }

    public AppRegistry getAppRegistry(String appId) {
        return appRegistryDao.getAppRegistryById(appId);
    }

    public List<AppRegistry> getAppRegistries(String unitId) {
        return appRegistryDao.getAppRegistries(unitId);
    }

    public List<AppRegistry> getAppRegistries(String[] appIds) {
        return appRegistryDao.getAppRegistrys(appIds);

    }

    public List<AppRegistry> getAppRegistriesDefaultPerm(String unitId) {
        List<AppRegistry> rtnList = new ArrayList<AppRegistry>();

        // 取本单位在用的应用系统
        List<AppRegistry> entityList1 = appRegistryDao.getAppRegistriesUsing(unitId);
        for (Object object : entityList1) {
            AppRegistry entity = (AppRegistry) object;
            if (BaseConstant.STR_NO.equals(entity.getIslogin()))
                continue;

            rtnList.add(entity);
        }

        // 取本单位的所有上级单位
        String[] ids = unitService.getAllParentUnitIds(unitId, false);
        if (ArrayUtils.isEmpty(ids))
            return rtnList;

        // 取所有上级单位可供访问的应用系统
        List<AppRegistry> entityList2 = appRegistryDao.getAppRegistriesByUnitidsAndUse(ids,
                BaseConstant.STR_YES);
        for (Object object : entityList2) {
            AppRegistry entity = (AppRegistry) object;
            if (BaseConstant.STR_NO.equals(entity.getIslogin()))
                continue;

            rtnList.add(entity);
        }
        return rtnList;
    }

    public List<String[]> getAppsFromConfig() {
        List<String[]> list = new ArrayList<String[]>();

        String[] appConfig = { SYS_APP_CONFIG };
        for (int i = 0; i < appConfig.length; i++) {
            final XMLConfigReader res = new XMLConfigReader(appConfig[i], "app");
            Enumeration<String> enumeration = res.getKeys();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String sysName = res.getString(key, "appName");
                list.add(new String[] { key, sysName });
            }
        }
        return list;
    }

    public List<AppRegistry> getProductApps() {
        List<AppRegistry> list = new ArrayList<AppRegistry>();
        AppRegistry app = null;

        String[] appConfig = { PRODUCT_CONFIG };
        for (int i = 0; i < appConfig.length; i++) {
            final XMLConfigReader res = new XMLConfigReader(appConfig[i], "app");
            Enumeration<String> enumeration = res.getKeys();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();

                app = new AppRegistry();
                app.setSysid(key);
                app.setSysname(res.getString(key, "appName"));
                app.setAppname(res.getString(key, "appName"));
                app.setImage(res.getString(key, "image"));
                app.setDescription(res.getString(key, "description"));
                list.add(app);
            }
        }
        return list;
    }

    /**
     * 取配置的产品信息
     */
    public Map<String, AppRegistry> getAppMapFromConfig() {
        Map<String, AppRegistry> map = new HashMap<String, AppRegistry>();
        AppRegistry app = null;

        String[] appConfig = { SYS_APP_CONFIG };
        for (int i = 0; i < appConfig.length; i++) {
            final XMLConfigReader res = new XMLConfigReader(appConfig[i], "app");
            Enumeration<String> enumeration = res.getKeys();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();

                app = new AppRegistry();
                app.setSysid(key);
                app.setSysname(res.getString(key, "appName"));
                app.setAppname(res.getString(key, "appName"));
                app.setImage(res.getString(key, "image"));
                app.setDescription(res.getString(key, "description"));
                app.setXurl(res.getString(key, "xurl"));
                app.setSharedataurl(res.getString(key, "shareDataService"));
                app.setTesturl(res.getString(key, "testService"));
                app.setSharedatasign(res.getString(key, "shareDataSign"));
                app.setType(res.getString(key, "type"));
                app.setParameters(res.getString(key, "parameters"));
                app.setCheckuserurl(res.getString(key, "service"));
                app.setDatabasepass(res.getString(key, "dataBasePass"));
                app.setIsencoded(res.getString(key, "isDBPassEncoded"));
                app.setUrlExample(res.getString(key, "urlExample"));
                String isInWork = res.getString(key, "isInWork");
                if (BaseConstant.STR_YES.equals(isInWork)) {// 由运营管理
                    app.setIslogin(BaseConstant.STR_NO);
                } else {
                    app.setIslogin(BaseConstant.STR_YES);
                }
                map.put(key, app);
            }
        }
        return map;
    }

}
