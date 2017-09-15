/*
 * Created on 2004-10-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.cache;

/**
 * 用来标识是否能够被缓存的接口，没有用到
 * 
 * @deprecated
 * @author liangxiao
 * @version $Revision: 1.4 $, $Date: 2007/01/11 03:59:32 $
 */
public interface Cacheable {

    /**
     * 取得id
     * 
     * @return id
     */
    public String getId();
}