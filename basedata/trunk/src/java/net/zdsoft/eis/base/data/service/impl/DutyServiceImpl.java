package net.zdsoft.eis.base.data.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.DutyDao;
import net.zdsoft.eis.base.data.entity.Duty;
import net.zdsoft.eis.base.data.service.DutyService;

public class DutyServiceImpl implements DutyService {
    private DutyDao dutyDao;

    public void setDutyDao(DutyDao dutyDao) {
        this.dutyDao = dutyDao;
    }

    public void saveDuty(Duty... dutys) {
        for (Duty duty : dutys) {
            if (null != duty.getId() && !"".equals(duty.getId())) {
                dutyDao.updateDuty(duty);
            } else {
                dutyDao.insertDuty(duty);
            }
        }
    }

    public void deleteUnitDuty(String... unitIds) {
        dutyDao.deleteUnitDuty(unitIds);
    }

    public void deleteDuty(String... dutyIds) {
        dutyDao.deleteDuty(dutyIds);
    }

    public List<Duty> getDuties(String unitId) {
        return dutyDao.getDuties(unitId);
    }

    public Map<String, Duty> getDutyMap(String unitId) {
        return dutyDao.getDutyMap(unitId);
    }

}
