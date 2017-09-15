package net.zdsoft.eis.base.common.dao;

import java.util.List;
import net.zdsoft.eis.base.common.entity.UnitIni;

public interface UnitIniDao {
    /**
     * 保存unitid单位设置信息
     * 
     * @param unitIni
     */
    public void insertUnitIni(UnitIni unitIni);

    /**
     * 更新
     * 
     * @param unitIni
     */
    public void updateUnitIni(UnitIni unitIni);

    /**
     * 得到unitid单位某iniid项设置的信息
     * 
     * @param unitId
     * @param iniId
     * @return
     */
    public UnitIni getUnitIni(String unitId, String iniId);

   /**
     * 得到unitid单位所有iniid项设置的信息
     * 
     * @param unitId
     * @param iniId
     * @return
     */
    public List<UnitIni> getUnitIniList(String unitId) ;

}
