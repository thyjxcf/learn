package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.data.entity.Mcodelist;

public interface BaseMcodeListDao {

    /**
     * 根据微代码ID编号获得微代信息
     * 
     * @param mcodeId 微代码ID编号
     * @return
     */
    public Mcodelist getMcodeList(String mcodeId);

    /**
     * 得到子系统中所有可维护微代码类型 subSystem=-1时，表示得到所有子系统的所有可维护微代码类型
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getMaintainMcodeLists(Integer subSystem);

    /**
     * 得到子系统中所有可启停微代码类型 subSystem=-1时，表示得到所有子系统
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getActiveMcodeLists(Integer subSystem);

    /**
     * 得到子系统中所有仅可查看微代码类型 subSystem=-1时，表示得到所有子系统
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getOnlyVisiableMcodeLists(Integer subSystem);

    /**
     * 得到子系统中所有非不可见微代码类型 subSystem=-1时，表示得到所有子系统
     * 
     * @param subSystem
     * @return
     */
    public List<Mcodelist> getExceptNovisiableMcodeLists(Integer subSystem);
}
