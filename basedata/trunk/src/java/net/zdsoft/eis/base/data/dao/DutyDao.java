package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.entity.Duty;

public interface DutyDao {
    /**
     * 增加
     * 
     * @param duty
     */
    public void insertDuty(Duty duty);

    /**
     * 更新
     * 
     * @param duty
     */
    public void updateDuty(Duty duty);

    /**
     * 根据ids删除
     * 
     * @param dutyId
     */
    public void deleteDuty(String... dutyId);

    /**
     * 根据单位ids进行删除
     * 
     * @param unitIds
     */
    public void deleteUnitDuty(String... unitIds);

    /**
     * 根据unitid得到列表
     * 
     * @param unitId
     * @return
     */
    public List<Duty> getDuties(String unitId);

    /**
     * 根据unitid得到MAP
     * 
     * @param unitId
     * @return
     */
    public Map<String, Duty> getDutyMap(String unitId);
}
