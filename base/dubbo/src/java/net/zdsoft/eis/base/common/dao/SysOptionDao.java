package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SysOption;

public interface SysOptionDao {

    /**
     * 运营参数
     * 
     * @param optionCode
     * @return
     */
    public SysOption getSysOption(String optionCode);
    public List<SysOption> getSysOptions();

}
