package net.zdsoft.base.data.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.dao.BaseUserDao;
import net.zdsoft.keel.util.UUIDUtils;

public class BaseUserDaoTest extends BaseDataDaoTestCase {

    @Autowired
    private BaseUserDao baseUserDao;

    public void testInsertUser() {
        User user = new User();
        user.setUnitid("40288099200D113701200D1137FC0000");
        user.setAccountId(UUIDUtils.newId());
        user.setTeacherid("40288099200D377101200D3771910000");
        user.setOwnerType(2);
        user.setName("admin");
        user.setRealname("河北省教育厅");
        user.setPassword("12345678");
        user.setMark(1);
        user.setType(0);
        user.setRegion("130000");
        user.setOrderid(1);
        user.setCreationTime(new Date());
        user.setModifyTime(new Date());
        user.setIsdeleted(false);

        baseUserDao.insertUser(user);
        // this.setComplete();
    }

}
