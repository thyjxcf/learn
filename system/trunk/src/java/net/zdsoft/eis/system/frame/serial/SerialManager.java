/* 
 * @(#)Serial.java    Created on Jul 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.system.frame.serial;

import java.util.Set;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 7, 2010 3:57:09 PM $
 */
public interface SerialManager {
    /**
     * 校验是否注册和过期日期，返回“”是通过。登录时使用
     * 
     * @return
     */
    public String verifySerial();

    /**
     * 取得过期日期
     * 
     * @param serial
     * @return
     */
    public String getExpireDate();

    /**
     * 返回模块的id
     * 
     * @param serial
     * @return
     */
    public Set<Integer> getModuleIds();
    
    /**
     * 返回子系统的id
     * @return
     */
    public Set<Integer> getSubsystems();

    /**
     * 授权给用户的可用单位数，增加单位时使用
     * 
     * @return 0 表示无限制
     */
    public int getUnitCountLimit();

    /**
     * 授权给用户的每个单位可以新增的用户数
     * 
     * @return 0 表示无限制
     */

    public int getUserCountLimit() ;
    
    /**
     * 是否超出了单位数限制
     * @param cnt
     * @return
     */
    public boolean isOverUnitCountLimit(int cnt);

}
