package net.zdsoft.base.data.dao;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.dao.BaseDeptDao;

public class BaseDeptDaoTest extends BaseDataDaoTestCase {

	private BaseDeptDao baseDeptDao;

	public void setBaseDeptDao(BaseDeptDao baseDeptDao) {
        this.baseDeptDao = baseDeptDao;
    }
	
	public void testInsertDept() {
		Dept dept = new Dept();
		dept.setUnitId("40288099200D113701200D1137FC0000");
		dept.setDeptname("教育局管理员组");
		dept.setDeptCode("000001");
		dept.setJymark(1);
		dept.setParentid(BaseConstant.ZERO_GUID);
		dept.setOrderid(1);
		dept.setCreationTime(new Date());
		dept.setModifyTime(new Date());
		dept.setIsdeleted(false);
		
		baseDeptDao.insertDept(dept);
		//this.setComplete();
	}

	
}
