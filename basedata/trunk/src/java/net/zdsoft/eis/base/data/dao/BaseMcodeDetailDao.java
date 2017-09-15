/* 
 * @(#)BaseMcodedetailDao.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao;

import net.zdsoft.eis.base.common.entity.Mcodedetail;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 10:08:03 AM $
 */
public interface BaseMcodeDetailDao {

    public void insertMcodeDetail(Mcodedetail mcodedetail);

    public void updateMcodeDetail(Mcodedetail mcodedetail);

    /**
     * 根据明细项的主键ID转换微代码明细项的isUsing字段，如果为0改为1，为1改为0
     * 
     * @param id 明细项的主键ID return 返回修改后的状态
     * @param using 是否使用
     */
    public void updateStateChange(String id, int using);

    /**
     * 删除微代码
     * 
     * @param ids
     */
    public void deleteMcodeDetail(String[] ids);

    /**
     * 得到mcodeid微代码类型中可用排序号
     * 
     * @param mcodeId
     * @return
     */
    public Integer getAvaOrderId(String mcodeId);
    
    
    /**
     * 根据明细项id获得此明细项
     * 
     * @param id 明细项id
     * @return Mcodedetail
     */
    public Mcodedetail getMcodeDetail(String id);
}
