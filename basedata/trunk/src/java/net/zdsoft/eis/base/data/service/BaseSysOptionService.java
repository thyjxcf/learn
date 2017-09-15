package net.zdsoft.eis.base.data.service;

import java.util.List;


import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.FormatException;

public interface BaseSysOptionService extends SysOptionService {
    /**
     * 增加
     * @param sysOption
     */
    public void addSysOption(SysOption sysOption);
    
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
     * 保存系统选项，同时验证值是否超过数据库中最大长度
     * 
     * @author "yangk" Sep 19, 2010 7:42:43 PM
     * @param sysOptions
     */
    public void saveSysOptions(SysOption[] sysOptions) throws FormatException;

    /**
     * 得到全部基础系统参数
     * 
     * @author "yangk" Sep 19, 2010 2:14:47 PM
     * @return
     */
    public List<SysOption> getSysOptions();
}
