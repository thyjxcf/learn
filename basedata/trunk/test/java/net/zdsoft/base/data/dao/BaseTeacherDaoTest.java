package net.zdsoft.base.data.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.data.dao.BaseTeacherDao;
import net.zdsoft.eis.base.data.entity.BaseTeacher;

public class BaseTeacherDaoTest extends BaseDataDaoTestCase {

    @Autowired
	private BaseTeacherDao baseTeacherDao ;

	
	public void testInsertTeacher() {
	    BaseTeacher teacher = new BaseTeacher();
		teacher.setDeptid("40288099200D327E01200D327E3B0000");
		teacher.setUnitid("40288099200D113701200D1137FC0000");
		teacher.setTchId("00001");
		teacher.setName("河北省教育厅");
		teacher.setSex("1");
		teacher.setEusing("11");
		teacher.setRegionCode("130000");
		teacher.setCreationTime(new Date());
		teacher.setModifyTime(new Date());
		teacher.setIsdeleted(false);
		
		baseTeacherDao.insertTeacher(teacher);
		//this.setComplete();
	}

//	public void testGetTeacherById() {
//		Teacher teacher = teacherDao.getTeacherById("40288099200D377101200D3771910000");
//		log.debug("name:"+teacher.getName());
//		log.debug("sex:"+teacher.getSex()+"-end");
//	}
	
	
	
	
	
	
	
	
	
	
}
