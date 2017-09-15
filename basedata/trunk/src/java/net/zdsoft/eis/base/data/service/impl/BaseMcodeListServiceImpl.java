package net.zdsoft.eis.base.data.service.impl;

import java.util.List;

import net.zdsoft.eis.base.data.dao.BaseMcodeListDao;
import net.zdsoft.eis.base.data.entity.Mcodelist;
import net.zdsoft.eis.base.data.service.BaseMcodeListService;

public class BaseMcodeListServiceImpl implements BaseMcodeListService {
    private BaseMcodeListDao baseMcodeListDao;

    public void setBaseMcodeListDao(BaseMcodeListDao baseMcodeListDao) {
        this.baseMcodeListDao = baseMcodeListDao;
    }

    public Mcodelist getMcodeList(String mcodeId) {
        return baseMcodeListDao.getMcodeList(mcodeId);
    }

    public List<Mcodelist> getActiveMcodeLists(Integer subSystem) {
        return baseMcodeListDao.getActiveMcodeLists(subSystem);
    }

    public List<Mcodelist> getMaintainMcodeLists(Integer subSystem) {
        return baseMcodeListDao.getMaintainMcodeLists(subSystem);
    }

    public List<Mcodelist> getOnlyVisiableMcodeLists(Integer subSystem) {
        return baseMcodeListDao.getOnlyVisiableMcodeLists(subSystem);
    }

    public List<Mcodelist> getExceptNovisiableMcodeLists(Integer subSystem) {
        return baseMcodeListDao.getExceptNovisiableMcodeLists(subSystem);
    }

}
