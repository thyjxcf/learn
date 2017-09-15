package net.zdsoft.eis.base.data.sync.service.impl;

import java.util.List;
import java.util.Map;

import com.winupon.syncdata.basedata.entity.son.MqTeacherDuty;

import net.zdsoft.eis.base.data.entity.BaseTeacherDuty;
import net.zdsoft.eis.base.data.service.BaseTeacherDutyService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.BatchHandlable;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

public class TeacherDutySyncServiceImpl extends
        AbstractHandlerTemplate<BaseTeacherDuty, MqTeacherDuty> implements
        BatchHandlable<BaseTeacherDuty> {

    private BaseTeacherDutyService baseTeacherDutyService;

    public void setBaseTeacherDutyService(BaseTeacherDutyService baseTeacherDutyService) {
        this.baseTeacherDutyService = baseTeacherDutyService;
    }

    @Override
    public void addData(BaseTeacherDuty e) throws BusinessErrorException {
        baseTeacherDutyService.addTeacherDuty(e);
    }

    @Override
    public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
        baseTeacherDutyService.deleteTeacherDutiesByIds(new String[] { id }, eventSource);
    }

    @Override
    public BaseTeacherDuty fetchOldEntity(String id) {
        return null;
    }

    @Override
    public void updateData(BaseTeacherDuty e) throws BusinessErrorException {
        baseTeacherDutyService.updateTeacherDuty(e);
    }

    @Override
    public void addDatas(List<BaseTeacherDuty> entities) throws BusinessErrorException {
        baseTeacherDutyService.addTeacherDuties(entities);

    }

    @Override
    public void deleteDatas(String[] ids, EventSourceType eventSource) throws BusinessErrorException {
        baseTeacherDutyService.deleteTeacherDutiesByIds(ids, eventSource);

    }

    @Override
    public void updateDatas(List<BaseTeacherDuty> entities) throws BusinessErrorException {
        baseTeacherDutyService.updateTeacherDuties(entities);
    }

    @Override
    public Map<String, BaseTeacherDuty> fetchOldEntities(String[] ids) {
        return null;
    }
}
