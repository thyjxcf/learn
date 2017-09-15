/* 
 * @(#)BaseMcodeDetailService.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.McodedetailService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 10:39:25 AM $
 */
public interface BaseMcodeDetailService extends McodedetailService {

    /**
     * 更新微代码启用停用状态
     * 
     * @param mcodelistId
     * @param mcodedetailId
     * @param usingStatus
     * @return 如果该微代码类型不允许启停,则返回false;设置成功则返回true
     */
    public boolean updateUsingStatus(String mcodelistId, String mcodedetailId);

    /**
     * 更新微代码明细中thisId和content,orderid
     * 
     * @param mcodedetailDto
     */
    public void updateMcodeDetail(Mcodedetail mcodedetailDto);

    /**
     * 保存微代码明细
     * 
     * @param mcodedetailDto
     */
    public void insertMcodeDetail(Mcodedetail mcodedetailDto);

    /**
     * 删除微代码in ids
     * 
     * @param ids
     */
    public void deleteMcodeDetail(String[] ids);

    /**
     * 获取mcodeid微代码类型中可用排序号
     * 
     * @param mcodeid
     * @return
     */
    public Integer getAvaOrderId(String mcodeid);

    /**
     * 根据微代码明细id，得到微代码内容串
     * 
     * @param ids
     * @return
     */
    public String[] getMcodeContentByIds(String[] ids);
    
    
    /**
     * 根据id获得微代码项获得微代码项
     * 
     * @param id
     * @return Mcodedetail
     */
    public Mcodedetail getMcodeDetail(String id);


}
