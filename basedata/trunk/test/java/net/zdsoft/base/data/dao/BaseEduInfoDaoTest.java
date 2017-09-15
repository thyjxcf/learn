package net.zdsoft.base.data.dao;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.data.dao.BaseEduInfoDao;

public class BaseEduInfoDaoTest extends BaseDataDaoTestCase {

    private BaseEduInfoDao baseEduInfoDao;

    public void setBaseEduInfoDao(BaseEduInfoDao baseEduInfoDao) {
        this.baseEduInfoDao = baseEduInfoDao;
    }

    public void testInsertEduInfo() {
        EduInfo eduInfo = new EduInfo();
        eduInfo.setId("40288099200D113701200D1137FC0000");
        eduInfo.setEduCode("130000");
        eduInfo.setCreationTime(new Date());
        eduInfo.setModifyTime(new Date());
        eduInfo.setIsdeleted(false);

        baseEduInfoDao.insertEduInfo(eduInfo);
        // this.setComplete();
    }

}
