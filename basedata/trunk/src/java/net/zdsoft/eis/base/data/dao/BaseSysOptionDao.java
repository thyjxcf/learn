package net.zdsoft.eis.base.data.dao;

import java.util.List;


import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseSysOptionDao {
    /**
     * 增加
     * @param sysOption
     */
    public void insertSysOption(SysOption sysOption);
    
    /**
     * 更新
     * @param sysOption
     */
    public void updateSysOption(SysOption sysOption);
    
    /**
     * 删除 （硬删）
     * @param sysOptionId
     * @param eventSource
     */
    public void deleteSysOption(String sysOptionId, EventSourceType eventSource);
    
    /**
     * 批量更新基础系统参数
     * 
     * @author "yangk" Sep 19, 2010 9:36:40 PM
     * @param sysOptions
     */
    public void batchUpdateSysOption(SysOption[] sysOptions);

    /**
     * 得到所有的基础系统参数
     * 
     * @author "yangk" Sep 19, 2010 2:25:50 PM
     * @return
     */
    public List<SysOption> getSysOptions();
}
