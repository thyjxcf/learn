package net.zdsoft.eis.base.data.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.BaseSchtypeSectionDao;
import net.zdsoft.eis.base.data.entity.SchtypeSection;
import net.zdsoft.eis.base.data.service.BaseSchtypeSectionService;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public class BaseSchtypeSectionServiceImpl implements BaseSchtypeSectionService {

    private BaseSchtypeSectionDao baseSchtypeSectionDao;

    public void setBaseSchtypeSectionDao(BaseSchtypeSectionDao baseSchtypeSectionDao) {
        this.baseSchtypeSectionDao = baseSchtypeSectionDao;
    }

    @Override
    public void addSchtypeSection(SchtypeSection schtypeSection) {
        baseSchtypeSectionDao.insertSchtypeSection(schtypeSection);
    }

    @Override
    public void updateSchtypeSection(SchtypeSection schtypeSection) {
        baseSchtypeSectionDao.updateSchtypeSection(schtypeSection);
    }

    @Override
    public void deleteSchtypeSection(String[] schtypeSectionIds, EventSourceType eventSource) {
        baseSchtypeSectionDao.deleteSchtypeSection(schtypeSectionIds, eventSource);
    }

    @Override
    public SchtypeSection getSchtypeSection(String schtypeSectionId) {
        return baseSchtypeSectionDao.getSchtypeSection(schtypeSectionId);
    }

    @Override
    public List<SchtypeSection> getSchtypeSections() {
        return baseSchtypeSectionDao.getSchtypeSections();
    }

    @Override
    public Map<String, SchtypeSection> getSchtypeSections(String[] schtypeSectionIds) {
        return baseSchtypeSectionDao.getSchtypeSections(schtypeSectionIds);
    }

}
