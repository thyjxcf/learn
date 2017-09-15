package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;

/** 
 * @author  lufeng 
 * @version 创建时间：2016-7-19 下午03:22:05 
 * 类说明 
 */
public interface StuTeaQueryService {
	
	public List<User> getStudentInfo(String unitid,String name , String username , Pagination page);
}
