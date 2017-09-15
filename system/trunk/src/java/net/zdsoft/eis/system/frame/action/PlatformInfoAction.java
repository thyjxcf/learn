package net.zdsoft.eis.system.frame.action;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;

public class PlatformInfoAction extends BaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private UnitService unitService;

    private LoginInfo loginInfo;
    public boolean isTopUnit;

    public String execute() throws Exception {
        // 权限判断
        loginInfo = getLoginInfo();
        String unitId = loginInfo.getUnitID();
        Unit unitDto = unitService.getUnit(unitId);
        if (unitDto != null
                && Unit.TOP_UNIT_GUID.equals(unitDto.getParentid())) {
            isTopUnit = true;
        }
        else {
            isTopUnit = false;
        }
        return SUCCESS;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

}
