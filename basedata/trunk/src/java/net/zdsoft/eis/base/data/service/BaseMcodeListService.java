package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.data.entity.Mcodelist;

public interface BaseMcodeListService {

    /**
     * 根据微代码编号获得微代码信息
     * 
     * @param mcodeId 微代码编号
     * @return
     */
    public Mcodelist getMcodeList(String mcodeId);

    /**
     * 获得子系统下所有可维护微代码类型
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getMaintainMcodeLists(Integer subSystem);

    /**
     * 获得子系统下所有可起停微代码类型
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getActiveMcodeLists(Integer subSystem);

    /**
     * 获得子系统下所有仅可见微代码类型
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getOnlyVisiableMcodeLists(Integer subSystem);

    /**
     * 获得子系统下所有非不可见微代码类型
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getExceptNovisiableMcodeLists(Integer subSystem);

}
