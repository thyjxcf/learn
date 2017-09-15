package net.zdsoft.base.data.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.dao.BaseUnitDao;
import net.zdsoft.eis.base.data.entity.BaseUnit;

public class BaseUnitDaoTest extends BaseDataDaoTestCase {

    @Autowired
    private BaseUnitDao baseUnitDao;

    public void testInsertUnit() {
        BaseUnit unit = new BaseUnit();
        unit.setUnionid("13");
        unit.setName("河北省教育厅");
        unit.setUnitclass(1);
        unit.setUnittype(1);
        unit.setMark(1);
        unit.setRegionlevel(2);
        unit.setUsetype(1);
        unit.setAuthorized(1);
        unit.setUnitusetype("01");
        unit.setRegion("130000");
        unit.setEtohSchoolId("1130100001");
        unit.setParentid(BaseConstant.ZERO_GUID);
        unit.setOrderid("130000");
        unit.setCreationTime(new Date());
        unit.setModifyTime(new Date());
        unit.setIsdeleted(false);

        baseUnitDao.insertUnit(unit);
        // this.setComplete();

    }

}
