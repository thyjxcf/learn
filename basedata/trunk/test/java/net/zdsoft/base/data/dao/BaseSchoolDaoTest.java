package net.zdsoft.base.data.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.data.dao.BaseSchoolDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;

public class BaseSchoolDaoTest extends BaseDataDaoTestCase {

    @Autowired
	private BaseSchoolDao baseSchoolDao;
	
	public void testInsertSchool() {
	    BaseSchool school = new BaseSchool();
		school.setId("40288099200B83A801200B83A8C61111");
		school.setName("just-test");
		school.setCode("1300000002");
		school.setRegion("130000");
		school.setType("25");
		school.setEtohSchoolId("1130100001");
		school.setGradeyear(6);
		school.setGradeage(8);
		school.setJunioryear(3);
		school.setJuniorage(13);
		school.setSenioryear(3);
		school.setInfantyear(3);
		school.setInfantage(1);
		school.setCreationTime(new Date());
		school.setModifyTime(new Date());
		school.setIsdeleted(false);
		
		baseSchoolDao.insertSchool(school);
		//this.setComplete();
	}

	
}
