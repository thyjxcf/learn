/* 
 * @(#)AbstractTransact.java    Created on Jan 21, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.affair;

import com.opensymphony.xwork2.ActionContext;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.LoginInfo;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 21, 2011 2:43:27 PM $
 */
public abstract class AbstractTransact implements Transactable {
    private AffairService affairService;
    private UnitService unitService;

    public void setAffairService(AffairService affairService) {
        this.affairService = affairService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public AbstractTransact() {
        AffairHelper.register(this);
    }

    /**
     * 更新待办事项完成标识
     */
    public void updateAffairCompleteSign() {
        affairService.updateAffairCompleteSign(this);
    }

    /**
     * 完成后，删除事项
     */
    public void deleteAffair() {
        affairService.deleteAffair(this);
    }

    /**
     * 默认取当前用户所在单位id
     */
    public String getReceiverId() {
        return getLoginUnitId();
    }

    /**
     * 取顶级单位id
     * 
     * @return
     */
    protected String getTopUnitId() {
        Unit unit = unitService.getTopEdu();
        if (null != unit) {
            return unit.getId();
        } else {
            return null;
        }
    }

    /**
     * 取当前登录用户所在单位id
     * 
     * @return
     */
    protected String getLoginUnitId() {
        LoginInfo loginInfo = (LoginInfo) ActionContext.getContext().getSession().get(
                BaseConstant.SESSION_LOGININFO);
        if (null != loginInfo) {
            return loginInfo.getUnitID();
        } else {
            return null;
        }

    }
}
