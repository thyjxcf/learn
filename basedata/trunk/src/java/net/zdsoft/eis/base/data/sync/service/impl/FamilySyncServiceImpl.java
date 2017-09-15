package net.zdsoft.eis.base.data.sync.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.data.service.BaseStudentFamilyService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.BatchHandlable;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

import com.winupon.syncdata.basedata.entity.son.MqFamily;

public class FamilySyncServiceImpl extends AbstractHandlerTemplate<Family, MqFamily> implements
        BatchHandlable<Family> {

    private BaseStudentFamilyService baseStudentFamilyService;

    public void setBaseStudentFamilyService(BaseStudentFamilyService baseStudentFamilyService) {
        this.baseStudentFamilyService = baseStudentFamilyService;
    }

    @Override
    public void addData(Family e) throws BusinessErrorException {
        baseStudentFamilyService.addFamily(e);
    }

    @Override
    public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
        baseStudentFamilyService.deleteFamiliesByFamilyIds(new String[] { id }, eventSource);
    }

    @Override
    public Family fetchOldEntity(String id) {
        return new Family();
    }

    @Override
    public void updateData(Family e) throws BusinessErrorException {
        baseStudentFamilyService.updateFamily(e);
    }

    @Override
    public void addDatas(List<Family> entities) throws BusinessErrorException {
        baseStudentFamilyService.addFamilies(entities);

    }

    @Override
    public void deleteDatas(String[] ids, EventSourceType eventSource) throws BusinessErrorException {
        baseStudentFamilyService.deleteFamiliesByFamilyIds(ids, eventSource);

    }

    @Override
    public void updateDatas(List<Family> entities) throws BusinessErrorException {
        baseStudentFamilyService.updateFamilies(entities);
    }

    @Override
    public Map<String, Family> fetchOldEntities(String[] ids) {
        return null;
    }

}
