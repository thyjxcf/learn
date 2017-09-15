/*
 * Created on 2004-10-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.cache;

import java.io.Serializable;

import net.zdsoft.keel.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可缓存到内存中的对象，主要用来存放定时刷新的对象
 * 
 * @author liangxiao
 * @version $Revision: 1.9 $, $Date: 2007/10/16 03:25:24 $
 */
public class CachedObject implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3401800549129912831L;

    private static Logger logger = LoggerFactory.getLogger(CachedObject.class);

    private long startTimeMillis = 0;
    private long refreshPeriod = 0;
    private boolean isUpdating = false;
    private Object object = null;

    /**
     * 构造方法
     */
    public CachedObject() {
    }

    /**
     * 构造方法
     * 
     * @param refreshPeriod
     *            刷新周期，以秒为单位
     */
    public CachedObject(int refreshPeriod) {
        setRefreshPeriod(refreshPeriod);
    }

    /**
     * 设置刷新周期，以秒为单位
     * 
     * @param refreshPeriod
     */
    public void setRefreshPeriod(int refreshPeriod) {
        this.refreshPeriod = refreshPeriod * 1000;
    }

    /**
     * 判断是否已经过期，需要刷新
     * 
     * @return
     */
    public synchronized boolean needsRefresh() {
        boolean needsRefresh = object == null
                || (startTimeMillis + refreshPeriod < System
                        .currentTimeMillis() && !isUpdating);
        if (needsRefresh) {
            isUpdating = true;
        }
        return needsRefresh;
    }

    /**
     * 取得缓存的对象
     * 
     * @return
     */
    public Object getObject() {
        return object;
    }

    /**
     * 设置缓存的对象
     * 
     * @param object
     */
    public void setObject(Object object) {
        this.object = object;
        startTimeMillis = System.currentTimeMillis();
        isUpdating = false;

        if (logger.isDebugEnabled()) {
            logger.debug("Cache[" + object + "] refreshed at "
                    + DateUtils.currentDate2String());
        }
    }

}
