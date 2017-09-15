package net.zdsoft.eis.base.util;

import java.net.MalformedURLException;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.keelcnet.config.ContainerManager;

/**
 * <p>
 * ZDSoft学籍系统（stusys）V3.5
 * </p>
 * 
 * @author zhaosf
 * @since 1.0
 * @version $Id: SoapProxy.java,v 1.4 2007/01/09 10:03:13 jiangl Exp $
 */
public final class SoapProxy {
    private static final String SYSTEM_REMOTESERVER_MAIN = "SYSTEM.REMOTESERVER-MAIN";// 数据报送主服务器地址
    private static final String XFIRE_WEBSERVICE_PATH = "/services";// ws路径
    public static final String WEBROOT = "";// 根目录
    
    public static String SERVICES_URL;

    @SuppressWarnings("unchecked")
    public final static Object newInstance(Class clazz) throws Exception {
        SystemIniService systemIniService = (SystemIniService) ContainerManager
                .getComponent("systemIniService");
        SystemIni dto = systemIniService.getSystemIni(SYSTEM_REMOTESERVER_MAIN);
        if (null == dto) {
            throw new RuntimeException("找不到数据报送主服务器地址信息");
        }
        String value = dto.getNowValue();
        if (null == value || "".equals(value)) {
            throw new RuntimeException("数据报送主服务器地址为空");
        } else {
            value = value.trim();
            SERVICES_URL = value + WEBROOT + XFIRE_WEBSERVICE_PATH
                    + "/";
        }

        Service serviceModel = new ObjectServiceFactory().create(clazz);
        String url = SERVICES_URL + clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1);

        Object service = null;
        try {
            service = new XFireProxyFactory().create(serviceModel, url);
        } catch (MalformedURLException ex) {
            throw ex;
        }

        return service;
    }

    /**
     * 取webservice对象
     * 
     * @author linqzh Mar 27, 2008
     * @param serviceUrl
     *            完整的webservice地址，如：http://etoh.winupon.com/service/EtohService
     * @param claszz 取得对象得类
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getWebServiceObject(String serviceUrl, Class claszz) throws Exception {
        Object o;
        try {
            Service serviceModel = new ObjectServiceFactory().create(claszz);
            o = new XFireProxyFactory().create(serviceModel, serviceUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }
}
